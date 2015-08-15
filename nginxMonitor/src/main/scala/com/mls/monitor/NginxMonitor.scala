package com.mls.monitor

import java.io.File

import com.mls.common.{LineParser, RedisDist}
import com.typesafe.config.ConfigFactory
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.slf4j.LoggerFactory


/**
 *
 *
 *
 * Created by zhangzhikuan on 15/8/10.
 */
object NginxMonitor {

  def main(args: Array[String]) {
    //判断参数的个数是否等于4
    if (args.length != 1) {
      System.err.println( s"""
                             | Usage: Monitor <brokers> <topics>
                             | <brokers> is a list of one or more Kafka brokers
                             | <topics> is a list of one or more kafka topics to consume from
                             | <redisHost> is a redis server which result send to
        """.stripMargin)
      System.exit(1)
    }

    //解析参数
    val Array(config) = args

    val conf=ConfigFactory.parseFile(new File(config))

    val slideDuration= conf.getInt("slideDuration")
    val brokers= conf.getString("brokers")
    val topics= conf.getString("topics")
    val appName= conf.getString("appName")
    val checkpoint= conf.getString("checkpoint")

    //域名
    val domain= conf.getString("domain")

    //正则表达式
    val regex= conf.getString("regex").r



    val ip_index= conf.getInt("ip_index")
    val url_index= conf.getInt("url_index")
    val code_index= conf.getInt("code_index")
    val delay_index= conf.getInt("delay_index")


    val log = LoggerFactory.getLogger("yun")

    //创建spark的配置文件
    val sparkConf = new SparkConf().setAppName(appName)
    //创建上下文，并且设置每30秒对日记进行处理
    val ssc = new StreamingContext(sparkConf, Seconds(slideDuration))
    //如果此任务停止了，那么将从此处进行恢复
    ssc.checkpoint(checkpoint)

    //解析topic
    val topicsSet = topics.split(",").toSet
    //kafka的配置文件
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
    //工作流
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicsSet)



    //读取所有的行信息，_2的意思是读取body信息
    //KAFKA属于key-value的结构，因此读取_2的value值，忽略key

    val lines = messages.map(_._2).filter(line=>line.trim.size >0).map(mapFunc = line => {


      //try {
        //对日志进行匹配,此处没有做错误处理
        //正则表达式
        //val regex(ip, _, _, url, code, _, _, _, _, _, _, delay, _, _) = line.trim
        val m = regex.findAllIn(line.trim)

        println(m)

        val ip = m.group(ip_index)
        val url = m.group(url_index)
        val code = m.group(code_index)
        val delay = m.group(delay_index)

        val wrapperLine = LineParser.wrapper(url, ip, code, delay, domain)

        wrapperLine
        /**
      }catch {
        case ex: Exception => {
          log.error("excpetion",ex)
          return
        }

      }
          **/
    })

    val add=(x:(Long,Float),y:(Long,Float)) =>{
      (x._1+y._1,x._2+y._2)
    }
    val subtraction=(x:(Long,Float),y:(Long,Float)) =>{
      (x._1-y._1,x._2-y._2)
    }
    //计算
    val rs=lines.reduceByKeyAndWindow(add,subtraction,Seconds(slideDuration),Seconds(slideDuration))

    rs.print(1000)
    RedisDist.dist(rs)

    // Start the computation
    ssc.start()
    ssc.awaitTermination()
  }
}