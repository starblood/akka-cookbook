package com.packt.chapter1

import akka.actor.ActorSystem

object HelloAkkaActorSystem {
  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("HelloAkka")
    println(actorSystem)
  }
}
