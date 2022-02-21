package akkaSystems

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.event.Logging

object ActorLoggingDemo extends App {
  
  // Explicit Logging
  class SimpleActorWithLogger extends Actor {
    val logger = Logging(context.system, this)
    
    override def receive: Receive = {
      case message => logger.info(message.toString)
    }
  }
  
  val system = ActorSystem("system")
  
  val ac = system.actorOf(Props[SimpleActorWithLogger], "ac")
  
//  ac ! "df"
  
  
  class ActorWithLogging extends Actor with ActorLogging{
    override def receive: Receive = {
      case (a, b) => log.info("two messages {} {}",a, b)
      case message => log.info(message.toString)
    }
  }
  
  val ac1 = system.actorOf(Props[ActorWithLogging], "ac1")
  
  ac1 ! (42, 65)
  
}
