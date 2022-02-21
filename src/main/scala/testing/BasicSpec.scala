package testing

import akka.actor.{Actor, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}
import testing.BasicSpec.SimpleActor

class BasicSpec extends TestKit(ActorSystem("BasicSpec"))
  with ImplicitSender with WordSpecLike with BeforeAndAfterAll {
  
  // setup
  override def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }
  
  
  "Simple actor" should{ // Suite
   "send back same message in" in {
   val echoActor = system.actorOf(Props[SimpleActor])
     
     val msg = "Hello test"
     echoActor ! msg
     expectMsg(msg)
   }
    
    "and this in" in{ // Test
    
    }
    
  }
  
}

object BasicSpec {
  class SimpleActor extends Actor {
    override def receive: Receive = {
      case message => sender() ! message
    }
  }
}
