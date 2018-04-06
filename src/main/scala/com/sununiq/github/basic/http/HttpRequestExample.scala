package com.sununiq.github.basic.http

import scalaj.http.{Http, HttpOptions}
import spray.json.DefaultJsonProtocol._
import spray.json._

/**
  * 使用scalaj-http发送http请求
  * 测试地址：https://httpbin.org/
  */
object HttpRequestExample extends App {
  //  sendGetRequest()
  sendPostRequest()

  println(Map("key" -> "workd").toJson.toString())

  def sendGetRequest(): Unit = {
    val response = Http("https://www.baidu.com").asString
    println(response.body)
  }

  def sendPostRequest(): Unit = {
    val data = Map("key" -> "word")
    val response = Http("http://httpbin.org/post?age=18")
      .postData(data.toJson.toString())
      .header("Content-Type", "application/json")
      .header("User-Agent", "Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.83 Safari/535.11")
      .option(HttpOptions.connTimeout(10000)).asString

    println(response.statusLine)
    println(response.body)
  }
}