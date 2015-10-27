package com.mls.monitor

import scala.util.matching.Regex

/**
 * Created by Kuan on 15/10/27.
 */
class NginxLine(line: String) {

  private val regex = """\[(.*?)\s+.*?\]\s*\[(.*?)\]\s*\[.*?\s+(.*?)\s+.*?\]\s*\[(.*?)\]\s*\[(.*?)\]""".r

  def regexParse(): (String, String, String, Int, Float) = {
    val regex(time, domain, url, respCode, reqTime) = line
    (time, domain, url, respCode.toInt, reqTime.toFloat)
  }

  def parse(): (String, String, String, Int, Float) = {
    //val regex(time, domain, url, respCode, reqTime) = line


    val arr = line.split( """\]\s*\[""")
    /*
      "[16/Aug/2015:23:31:58 +0800] [www.meilishuo.com] [POST /aj/wallet/getpayamount HTTP/1.1] [400] [195]"
    */
    val time = arr(0).stripPrefix("[").split( """\s+""")(0)
    val domain = arr(1)
    val url = arr(2).split( """\s+""")(1)
    val respCode = arr(3)
    val reqTime = arr(4).stripSuffix("]")
    (time, domain, url, respCode.toInt, reqTime.toFloat)
  }
}

object NginxLine {
  def main(args: Array[String]) {
    val o = new NginxLine("[16/Aug/2015:23:31:58 +0800] [www.meilishuo.com] [POST /aj/wallet/getpayamount HTTP/1.1] [400] [195]")
    val ret = o.parse()
    println(ret._1)
    println(ret._2)
    println(ret._3)
    println(ret._4)
    print(ret._5)

  }
}
