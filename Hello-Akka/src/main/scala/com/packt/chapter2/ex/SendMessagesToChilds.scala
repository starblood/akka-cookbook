package com.packt.chapter2.ex

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

case class DoubleValue(x: Int)
case object CreateChild
case object Send
case class Response(x: Int)

class DoubleActor extends Actor {
  override def receive: Receive = {
    case DoubleValue(number) =>
      println(s"${self.path.name} Got the number $number")
      sender ! Response(number * 2)
  }
}

class ParentActor extends Actor {
  val random = new scala.util.Random
  var childs = scala.collection.mutable.ListBuffer[ActorRef]()

  override def receive: Receive = {
    case CreateChild =>
      childs ++= List(context.actorOf(Props[DoubleActor]))
    case Send =>
      println(s"Sending message to child")
      childs.foreach { child => child ! DoubleValue(random.nextInt(10)) }
    case Response(x) => println(s"Parent: Response from child ${sender.path.name} is $x")
  }
}

object SendMessagesToChilds {
  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("Hello-Akka")
    val parent = actorSystem.actorOf(Props[ParentActor], "parent")
    parent ! CreateChild
    parent ! CreateChild
    parent ! CreateChild
    parent ! Send
  }
}
