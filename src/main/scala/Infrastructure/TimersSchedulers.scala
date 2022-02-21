package Infrastructure

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.actor.TypedActor.context

import scala.concurrent.duration.DurationInt

object TimersSchedulers extends App{
  class SimpleActor extends Actor with ActorLogging {
    override def receive: Receive = {
      case x=> println(x)
    }
  }
  
  val system = ActorSystem("sytem")
  
  val actor = system.actorOf(Props[SimpleActor])
  
  system.log.info("Reminder for SimpleActor")
  implicit val executionContext = system.dispatcher
  system.scheduler.scheduleOnce(1 seconds){
    actor ! "reminder"
  }
  
  val routine = system.scheduler.schedule(1 seconds, 2 seconds){
    actor ! "reminder"
  }
  
  system.scheduler.scheduleOnce(10 seconds) {
    routine.cancel()
  }
  
  
  
}
