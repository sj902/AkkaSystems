package akkaSystems

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ACtorCapabilities extends App {
class SimpleActor extends Actor{
  override def receive: Receive = {
    case "hi" => context.sender ! "hello there"
    case message:String => println(s"[simple actor] ${self.path} Received message $message")
    case SayHiTo(ref) => ref ! "hi"
    case Forward(content, ref) => ref forward content // message is forwarded
  }
}

  val system = ActorSystem("actorCapabilities")
  val simpleActor = system.actorOf(Props[SimpleActor], "simpleActor")

  // 1. Messages should be immutable
  // 2. Messages should be serializable

  // contezt.self === self ~~~ this

  simpleActor ! "hello"

  // Actors can reply to themselves

  val alice = system.actorOf(Props[SimpleActor], "alice")
  val bob = system.actorOf(Props[SimpleActor], "bob")

  case class SayHiTo(ref: ActorRef)

  // Can send message to sender using context.sender
  alice ! SayHiTo(bob)


  // dead letters - messages that aren't sent to anyone are catched here
  alice ! "hi"

  // Actors can forward messages
  // forwarding => sending message with original sender

  case class Forward(content: String, ref: ActorRef)

  alice ! Forward("hello", bob)





}
