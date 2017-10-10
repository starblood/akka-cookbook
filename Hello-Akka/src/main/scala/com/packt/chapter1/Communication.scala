package com.packt.chapter1

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.util.Random._


object Messages {
  case class Done(randomNumber: Int)
  case object GiveMeRandomNumber
  case class Start(actorRef: ActorRef)
}


class RandomNumberGenerator extends Actor {
  import Messages._

  override def receive: Receive = {
    case GiveMeRandomNumber => println("received a message to generate a random integer")
      val randomNumber = nextInt
      sender ! Done(randomNumber)
  }
}

class QueryActor extends Actor {
  import Messages._

  override def receive: Receive = {
    case Start(actorRef) => println(s"send me the next random number")
      actorRef ! GiveMeRandomNumber
    case Done(randomNumber) => println(s"received a random number $randomNumber")
  }
}
object Communication {
  import Messages._

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem("HelloAkka")
    val randomNumberGenerator = actorSystem.actorOf(Props[RandomNumberGenerator], "randomNumberGeneratorActor")
    val queryActor = actorSystem.actorOf(Props[QueryActor], "queryActor")

    queryActor ! Start(randomNumberGenerator)
  }
}
