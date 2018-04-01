package com.sununiq.github.basic.concurrent.akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

case object Greet

case class WhoToGreet(who: String)

case class Greeting(message: String)

class Greeter extends Actor {
  var greeting = "original message"

  def receive = {
    case WhoToGreet(who) =>
      greeting = s"hello, $who"
      println(greeting)
    case Greet => sender ! println(Greeting(greeting))
    case _ => println("Oops, nothing to print.")
  }
}

class SimpleActor extends Actor {

  def receive = {
    case "hello" => println("您好！")
    case _ => println("您是?")
  }
}

/**
  * ! 用于发送消息
  */
object Main extends App {

  testGreeting()

  private def testGreeting(): Unit = {
    val system = ActorSystem("helloakka")
    val greeter = system.actorOf(Props[Greeter], "greeter")

    greeter.tell(WhoToGreet("akka"), ActorRef.noSender)

    greeter ! WhoToGreet("akka")

    greeter ! Greet
  }

  private def testSimple(): Unit = {
    val system = ActorSystem("HelloSystem")
    // 缺省的Actor构造函数
    val helloActor = system.actorOf(Props[SimpleActor], name = "simpleActor")
    helloActor ! "hello"
    helloActor ! "喂"
  }
}