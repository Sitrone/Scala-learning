package com.sununiq.github.basic.concurrent.akka.ds

import akka.actor.Actor

class LocalAkka extends Actor {

  val remoteActor = context.actorSelection("akka.tcp://remoteSystem@127.0.0.1:7789/user/remoteActor")

  def receive = {
    case "hello" =>
      remoteActor ! "Hello Remote~"

    case msg: String =>
      println("LocalActor received [" + msg + "] from " + sender)

    case _ => println("Error !")
  }
}
