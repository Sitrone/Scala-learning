package com.sununiq.github.basic.concurrent.akka.ds

import akka.actor.Actor

class RemoteAkka extends Actor {

  def receive = {
    case msg: String =>
      println("remoteActor received [" + msg + "] from " + sender)
      sender ! "Hello guy"

    case _ => println("Error !")
  }
}
