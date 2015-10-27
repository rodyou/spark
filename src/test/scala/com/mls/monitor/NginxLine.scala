package com.mls.monitor

import scala.util.matching.Regex

/**
 * Created by Kuan on 15/10/27.
 */
class NginxLine(line:String) {

  val regex = """\[(.*?)\]\s*\[(.*?)\]\s*\[.*?\s+(.*?)\s+.*?\]\s*\[(.*?)\]\s*\[(.*?)\]""".r

  def parse(): (String,String,String,Int,Float) ={
    val regex(time,domain,url,respCode,reqTime) = line
    (time,domain,url,respCode.toInt,reqTime.toFloat)
  }

}

object NginxLine{
  def main(args: Array[String]) {
    val o = new NginxLine("[2015] [www.meilishuo.com] [GET mls Tail]    [200] [0.1]")
    val ret  = o.parse()
    println(ret._3)
    println(ret._5)
    println(ret._4)
    println(ret._2)
    print(ret._1)

  }
}
