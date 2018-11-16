package com.sununiq.scala.controller

import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RestController}

@RestController
class HelloController {

  @RequestMapping(value = Array("/hello"))
  def echo(@RequestBody body: String): String = {
    body
  }
}
