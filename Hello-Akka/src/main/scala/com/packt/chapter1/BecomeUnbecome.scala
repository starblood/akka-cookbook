package com.packt.chapter1


import akka.actor.{Props, ActorSystem, Actor}

class BecomeUnBecomeActor extends Actor {
  def receive: Receive = {
    case true => context.become(isStateTrue)
    case false => context.become(isStateFalse)
    case _ => println("don't know what you want to say !!")
  }

  def isStateTrue: Receive = {
    case msg: String => println(s"$msg")
    case false => context.become(isStateFalse)
  }

  def isStateFalse: Receive = {
    case msg: Int => println(s"$msg")
    case true => context.become(isStateTrue)
  }
}

object BecomeUnbecome {
  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("HelloAkka")
    val becomeUnBecome = actorSystem.actorOf(Props[BecomeUnBecomeActor])

    becomeUnBecome ! true
    becomeUnBecome ! "Hello how are you?"
    becomeUnBecome ! false
    becomeUnBecome ! 1100
    becomeUnBecome ! true
    becomeUnBecome ! "What do u do?"
  }
}
