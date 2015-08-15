package com.mls.common

import org.apache.spark.streaming.Time
import org.apache.spark.streaming.dstream.DStream
import org.slf4j.LoggerFactory
import redis.clients.jedis.Jedis

import scala.util.parsing.json.JSONObject

/**
 * Created by zhangzhikuan on 15/8/14.
 */
object RedisDist {

  val redisPort=6579

  val redisHost="172.16.11.99"

  val log = LoggerFactory.getLogger("yun")

  def dist (rs:DStream[(String, (Long, Float))]){
    rs.foreachRDD((rdd, time: Time) => {

      rdd.foreachPartition((iter: Iterator[(String, (Long, Float))]) => {
        val redis = new Jedis(redisHost, redisPort)
        try {
          //插入到redis中，或者数据库中
          while (iter.hasNext) {
            val item = iter.next()

            val key = item._1


            //此处的value 是（Long,Long）
            val value = item._2

            //总次数
            val count = value._1

            //所有的延时
            val delay = value._2


            val map = Map("count" -> count,
              "delay" -> delay
            )

            val redis_key = key + ":" + time.milliseconds
            redis.set(redis_key, JSONObject(map).toString())
          }
        }
        catch {
          case e: RuntimeException => log.error("excpetion",e)
          case e: Exception => log.error("excpetion",e)
        } finally {
          if (redis != null) {
            redis.close()
          }
        }

      })
    })
  }
}
