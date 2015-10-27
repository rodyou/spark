package com.mls.monitor

import scala.util.matching.Regex

/**
 * Created by Kuan on 15/10/27.
 */
class NginxLine(line: String) extends Serializable{

  /*
  * 返回格式为(key,(count,value))
  * 其中key为自定义key
  * count＝1L 代表此记录有一条
  * value=reqTime 代表此记录请求时间
  * */
  def newLine(): (String, (Long, Float)) = {
    val ret = this.parse()
    val domain = ret._2
    val url = ret._3
    val respCode = ret._4
    val reqTime = ret._5

    (domain + url + ":" + toConvergeRespCode(respCode) + ":" + toConvergeTime(reqTime), (1L, reqTime))
  }

  private def parse() = {

    /*
      "[16/Aug/2015:23:31:58 +0800] [www.meilishuo.com] [POST /aj/wallet/getpayamount HTTP/1.1] [400] [1.5,0.5]"
    */
    val arr = line.split( """\]\s*\[""")
    val time = arr(0).stripPrefix("[").split( """\s+""")(0)
    val domain = arr(1)
    val url = arr(2).split( """\s+""")(1)
    val respCode = arr(3)
    val reqTime = arr(4).stripSuffix("]")
    (time, domain, url, respCode, sumTime(reqTime))
  }


  /**
   * 因为Nginx可能向后台请求多次，因此需要把所有的时间加一起
   * @param timeStr
   * @return
   */
  private def sumTime(timeStr: String): Float = {
    //对字符串进行解析
    val arr = timeStr.split( """,""")
    //重新计算值
    var time = 0f;
    arr.foreach(t => time = t.toFloat * 1000 + time)
    time
  }


  /**
   * 收敛返回码
   * @param respCode
   */
  private def toConvergeRespCode(respCode: String): String = {

    if (respCode.startsWith("3")) {
      return "3xx"
    }

    if (respCode.equals("400")) {
      return "400"
    }
    if (respCode.startsWith("4")) {
      return "4xx"
    }

    if (respCode.startsWith("5")) {
      return "5xx"
    }

    if (respCode.startsWith("2")) {
      return "2xx"
    } else {
      return "Nothing"
    }

  }


  /**
   * 根据时间进行内敛转换
   * @param time
   * @return
   */
  private def toConvergeTime(time: Float): String = {
    //开始比较

    if (time <= 20) {
      return "20"
    }

    if (time <= 50) {
      return "50"
    }

    if (time <= 100) {
      return "100"
    }

    if (time <= 200) {
      return "200"
    }

    else if (time <= 500) {
      return "500"
    }
    else if (time <= 1000) {
      return "1000"
    }

    return "9999"
  }

}

object NginxLine {
  def main(args: Array[String]) {
    val o = new NginxLine("[16/Aug/2015:23:31:58 +0800] [www.meilishuo.com] [POST /aj/wallet/getpayamount HTTP/1.1] [400] [195]")
    print(o.newLine())

  }
}
