package com.packt.chapter1


import akka.actor.{PoisonPill, Props, Actor, ActorSystem}
case object Stop

class ShutdownActor extends Actor {
  override def receive: Receive = {
    case msg: String => println(s"$msg")
    case Stop => context.stop(self)
  }
}

object Shutdown {
  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("HelloAkka")
    val shutdownActor1 = actorSystem.actorOf(Props[ShutdownActor], "shutdownActor1")
    val shutdownActor2 = actorSystem.actorOf(Props[ShutdownActor], "shutdownActor2")

    shutdownActor1 ! "hello"
    shutdownActor1 ! PoisonPill
    shutdownActor1 ! "Are you there?"

    shutdownActor2 ! "How are you"
    shutdownActor2 ! Stop
    shutdownActor2 ! "Are you still there?"
  }
}
