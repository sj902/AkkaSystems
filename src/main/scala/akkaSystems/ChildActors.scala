package akkaSystems

import akka.actor.{Actor, ActorSystem, Props}

object ChildActors extends App {

  val as = ActorSystem("Children")

  object Parent{
    case class CreateChild(name: String)
    case class TellChild(message: String)
  }


  class Parent extends Actor{

    import Parent._
    override def receive: Receive = {
      case CreateChild(name) =>
        println(s"${self.path} creating Child")
        val childRef = context.actorOf(Props[Child], name)
    }
  }

  class Child extends Actor{
    override def receive: Receive = {
      case message => println(s"${self.path} got: $message")
    }
  }
}
