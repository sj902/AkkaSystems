package FaultTolerance

import java.lang

import akka.actor.{Actor, ActorSystem, OneForOneStrategy, Props}
import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}

class SupervisionSpec extends TestKit(ActorSystem("SupervisionSpec"))
  with WordSpecLike
  with BeforeAndAfterAll
  with ImplicitSender {
  
  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }
  

  
  
}


object SupervisionSpec {
  
  class Supervisor extends Actor{
    
    override val supervisorStrategy = OneForOneStrategy(){
      case _: NullPointerException => Restart
      case _: IllegalArgumentException => Stop
      case _: RuntimeException => Resume
      case _: Exception => Escalate
    }
    
    override def receive: Receive = {
      case props: Props => val childRef = context.actorOf(props)
        sender() ! childRef
    }
  }
  
  class FussyWordCounter extends Actor {
    
    var words = 0
    
    override def receive: Receive = {
      case "" => throw new NullPointerException("Empty Sentence")
      
      case sentence: String =>
        if (sentence.length > 20) throw new RuntimeException("too long")
        
        else if (!Character.isUpperCase(sentence(0))) throw new IllegalArgumentException("must be uppercase")

        else words +=  sentence.split(" ").length
      case "" => throw new Exception("not a string")
      
    }
  }
  
}