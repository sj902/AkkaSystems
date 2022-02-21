package FaultTolerance

import java.io.File

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

import scala.io.Source

object Backoff extends App {
  class FileBasedPersistentActor extends Actor with ActorLogging{
    
    var dataSource: Source = null
  
    override def preStart(): Unit = {
      log.info("Persistent actor starting")
    }
  
    override def postStop(): Unit = {
      log.warning("persistent actor has stopped")
    }
  
    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      log.warning("persistent actor restarting")
    }
    
    override def receive: Receive = {
      case ReadFile =>
        if(dataSource == null){
          dataSource = Source.fromFile(new File("src/main/resources/testfiles/a_i.txt"))
          log.info(s"read data + ${dataSource.getLines().toList}")
        }
    }
  }
  
  case object ReadFile
  
  val system = ActorSystem("sys")
  
  val simpleActor = system.actorOf(Props[FileBasedPersistentActor], "simpleActor")
  
  
  simpleActor ! ReadFile
  
  
  
  
}
