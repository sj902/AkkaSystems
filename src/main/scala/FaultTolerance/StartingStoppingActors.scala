package FaultTolerance

import akka.actor.{Actor, ActorLogging, ActorSystem, Kill, PoisonPill, Props}

object StartingStoppingActors extends App {
  val sys = ActorSystem("System")
  
  object StartChild
  
  class LifecycleAcotr extends Actor with ActorLogging{
  
    override def preStart(): Unit = log.info("I'm Starting")
  
    override def postStop(): Unit = log.info("I've Stopped")
    
    override def receive: Receive = {
      case StartChild => context.actorOf(Props[LifecycleAcotr], "child")
    }
  }
  
//  val parent = sys.actorOf(Props[LifecycleAcotr], "parent")
  
//  parent ! StartChild
  
//  parent ! Kill
  
  
  /*
  restart
   */
  
  object Fail
  
  object FailChild
  
  object CheckChild
  
  object Check
  
  
  class Parent extends Actor with ActorLogging{
    
    private val child = context.actorOf(Props[Child],"supervisedChild")
    
    override def receive: Receive = {
      case FailChild => {
        child ! Fail
      }

      case CheckChild => {
        child ! Check
      }
      
    }
  }
  
  class Child extends Actor with ActorLogging{
  
    override def preStart(): Unit = log.info("su[ervixsed child started")
    override def postStop(): Unit = log.info("su[ervixsed child stopped")
  
  
    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      log.info(s"restarting because ${reason.getMessage}")
    }
  
    override def postRestart(reason: Throwable): Unit = {
      log.info(s"restarted")
    }
    
    override def receive: Receive = {
      case Fail => log.warning("will fail now")
        throw new RuntimeException("I failed")

      case Check => log.info("alive")
    }
  }
  
  
  val supervisor = sys.actorOf(Props[Parent], "supervisor")
  
  supervisor ! FailChild
  
  supervisor ! CheckChild
  
}
