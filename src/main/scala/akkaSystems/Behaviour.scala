package akkaSystems

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object Behaviour extends App {


  object Counter {
    case object Increment
    case object Decrement
    case object Print
  }

  class Counter extends Actor{
    import Counter._
    override def receive: Receive = {
      increment(0)
    }

    def increment(count: Int): Receive = {
      case Increment => context.become(increment( count + 1))
      case Decrement => context.become(increment( count - 1))
      case Print => println(count)
    }

  }

  val actorSystem = ActorSystem("as")
  val actor = actorSystem.actorOf(Props[Counter])

actor

}
