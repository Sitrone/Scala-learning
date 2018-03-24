package com.sununiq.github.basic.concurrent

import akka.actor.{Actor, ActorSystem, Props}

//class SimpleActor {
//  def receive : Unit= {
//    case "hello" => println("您好！")
//    case _       => println("您是?")
//  }
//}
//
//object Main extends App {
//  val system = ActorSystem("HelloSystem")
//  // 缺省的Actor构造函数
//  val helloActor = system.actorOf(Props[SimpleActor], name = "simpleActor")
//  helloActor ! "hello"
//  helloActor ! "喂"
//}