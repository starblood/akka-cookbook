package com.packt.chapter1

import akka.actor.{Actor, ActorSystem, Props}
import akka.dispatch.ControlMessage

// Messages that extend ControlMessage trait will be handled with priority by control aware mailboxes.
case object MyControlMessage extends ControlMessage

class Logger extends Actor {
  override def receive: Receive = {
    case MyControlMessage => println("Oh, I have to process Control message first")
    case x => println(x.toString)
  }
}

object ControlAwareMailbox {
  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("HelloAkka")
    val actor = actorSystem.actorOf(Props[Logger].withDispatcher("control-aware-dispatcher"))
    actor ! "hello"
    actor ! "how are"
    actor ! "you"
    actor ! MyControlMessage
  }
}
