package com.sununiq.github.basic.concurrent.akka

import akka.actor.{ActorSystem, Props}
import com.sununiq.github.basic.concurrent.akka.ds.{LocalAkka, RemoteAkka}
import com.typesafe.config.ConfigFactory

/**
  * akka 远程调用例子
  * ref: http://peaceland.cn/2016/06/17/akka/
  */
object RemoteAkkaTest extends App {
  val config = ConfigFactory.load.getConfig("remote")
  val remoteSystem = ActorSystem("remoteSystem", config)
  val remoteActor = remoteSystem.actorOf(Props[RemoteAkka], "remoteActor")
}

object LocalAkkaTest extends App {
  val config = ConfigFactory.load.getConfig("local")
  val localSystem = ActorSystem("localSystem", config)
  val localActor = localSystem.actorOf(Props[LocalAkka], "localActor")
  localActor ! "hello"
}