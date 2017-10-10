package com.packt.chapter1

import akka.actor.{Actor, ActorSystem, Props}
import akka.dispatch.{PriorityGenerator, UnboundedPriorityMailbox}
import com.typesafe.config.Config


class MyPriorityActor extends Actor {
  override def receive: Receive = {
    case x: Int => println(x)
    case x: String => println(x)
    case x: Long => println(x)
    case x => println(x)
  }
}

class MyPriorityActorMailBox(settings: ActorSystem.Settings, config: Config)
  extends UnboundedPriorityMailbox(PriorityGenerator {
  case x: Int => 1
  case x: String => 0
  case x: Long => 2
  case _ => 3
})

object PriorityMailBox {
  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("HelloAkka")
    val myPriorityActor = actorSystem.actorOf(Props[MyPriorityActor].withDispatcher("prio-dispatcher"))

    myPriorityActor ! 6.0
    myPriorityActor ! 1
    myPriorityActor ! 5.0
    myPriorityActor ! 3
    myPriorityActor ! "Hello"
    myPriorityActor ! 5
    myPriorityActor ! "I am priority actor"
    myPriorityActor ! "I process string messages first, then integer, long and others"
  }
}
