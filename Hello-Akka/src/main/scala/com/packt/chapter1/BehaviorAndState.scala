package com.packt.chapter1

import akka.actor.Actor


class SummingActor extends Actor {
  // state inside the actor
  var sum = 0
  // behaviour which is applied on the state
  override def receive: Receive = {
    // receives message an integer
    case x: Int => sum = sum + x
      println(s"my state as sum is $sum")
      // receives default message
    case _ => println("I don't know what are you talking about")
  }
}

class SummingActorWithConstructor(initialSum: Int) extends Actor {
  // state inside the actor
  var sum = 0
  // behaviour which is applied on the state
  override def receive: Receive = {
    // receives message an integer
    case x: Int => sum = initialSum + sum + x
      println(s"my state as sum is $sum")
    // receives default message
    case _ => println("I don't know what are you talking about")
  }
}

import akka.actor.Props
import akka.actor.ActorSystem
object BehaviorAndState {
  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("HelloAkka")
    val actor = actorSystem.actorOf(Props[SummingActor], "summingActor")
    println(actor.path)

    val summingActor = actorSystem.actorOf(Props(classOf[SummingActorWithConstructor], 10), "summingActorWithConstructor")
    println(summingActor.path)

    actor ! 1
    while (true) {
      Thread.sleep(3000)
      actor ! 1
    }
  }
}
