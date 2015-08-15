package com.mls.common

/**
 * Created by zhangzhikuan on 15/8/14.
 */
object LineParser {
  def wrapper(url:String,ip:String,code:String,delay:String,domain:String):(String,(Long,Float))= {

    //获取收敛后得URL地址
    val array = url.split("\\s+")
    var simpleUrl = ip + "kuan"
    if (array.length >= 2) {
      //抛去？后的参数
      simpleUrl = array(1).split("\\?")(0).replaceAll("[0-9]", "")
    }


    //声称收敛好得code
    var simpleCode = "xxx"
    if (code.startsWith("3")) {
      simpleCode = "3xx"
    }
    else if (code.startsWith("4")) {
      simpleCode = "4xx"
    }
    else if (code.startsWith("5")) {
      simpleCode = "5xx"
    }
    else if (code.startsWith("2")) {
      simpleCode = "2xx"
    }

    //计算延时时间
    var _delay = 0f;
    //某些行没有延时时间
    if (!delay.trim.equals("-")) {

      //因为要尝试N个upstearm，所以延时时间可能有多个，以逗号分隔
      val arr = delay.split(",")

      //将所有得延时时间相加
      for (arg <- arr) {
        _delay = _delay + arg.toFloat
      }

      //将单位换算成毫秒
      _delay = _delay * 1000

    }



    //将延时时间进行收敛
    var timeStr = "20"

    if (_delay <= 20) {
      timeStr = "20"
    }
    else if (_delay <= 50) {
      timeStr = "50"
    }


    else if (_delay <= 100) {
      timeStr = "100"
    }

    else if (_delay <= 200) {
      timeStr = "200"
    }

    else if (_delay <= 500) {
      timeStr = "500"
    }
    else if (_delay <= 1000) {
      timeStr = "1000"
    }
    else {
      timeStr = "9999"
    }

    val line= (domain+ simpleUrl + ":" + simpleCode + ":" + timeStr, (1L, _delay))
    line
  }
}
