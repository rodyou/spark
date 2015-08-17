package com.mls.monitor

import java.sql.Timestamp

import org.apache.spark.rdd.RDD
import org.slf4j.LoggerFactory
import redis.clients.jedis.Jedis
import scala.util.matching.Regex
import scala.util.parsing.json._
/**
 * Created by zhangzhikuan on 15/6/3.
 */
object ts {
  def main(args: Array[String]) {

              val line ="""140.207.54.77 - - [18/Aug/2015:00:36:26 +0800] "POST /WeChatPubPayAccountBgRetUrl.do HTTP/1.1" 200 7 "-" "Mozilla/4.0" "-"  "0.211" "10.5.6.19:7003 "0.185""""
        val arr = line.trim.split("""\s+?""")
    val delay_tmp=arr(arr.length-1)
    println(delay_tmp)

val delay =delay_tmp.substring(1,delay_tmp.size-1)
    println(delay)

  }

}

