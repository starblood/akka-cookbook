package com.packt.chapter2


import akka.actor.{Actor, ActorSystem, Props}
import com.packt.chapter2.ex.ParentActor
case object CreateChild
case class Greet(msg: String)

class ChildActor extends Actor {
  override def receive: Receive = {
    case Greet(msg) => println(s"My parent [${self.path.parent}] greeted to me [${self.path}] $msg")
  }
}

class ParentActor extends Actor {
  override def receive: Receive = {
    case ex.CreateChild =>
      val child = context.actorOf(Props[ChildActor], "child")
      child ! Greet("Hello Child")
  }
}

object ParentChild {
  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("Supervision")
    val parent = actorSystem.actorOf(Props[ParentActor], "parent")
    parent ! ex.CreateChild
  }
}
