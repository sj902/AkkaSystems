package Infrastructure

import akka.actor.{Actor, ActorLogging, ActorSystem, Props, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinPool, RoundRobinRoutingLogic, Router}

object Routers extends App {
  
  /*
  1. Manual router
   */
  
  val system = ActorSystem("system")
  val master = system.actorOf(Props[Master])
  
  class Master extends Actor {
    private val slaves = for (_ <- 1 to 5) yield {
      val slave = context.actorOf(Props[Slave])
      context.watch(slave)
      ActorRefRoutee(slave)
    }
    
    private var router = Router(RoundRobinRoutingLogic(), slaves)
    
    override def receive: Receive = {
      case Terminated(ref) =>
        router = router.removeRoutee(ref)
        val slave = context.actorOf(Props[Slave])
        context.watch(slave)
        router = router.addRoutee(slave)
      case message => router.route(message, sender())
    }
  }
  
  class Slave extends Actor with ActorLogging {
    override def receive: Receive = {
      case message => log.info(message.toString)
    }
  }
  
//
//  for (i <- 1 to 10)
//    master ! i
  
  /*
  2.1Pool Router
   */
  
  val poolMaster = system.actorOf(RoundRobinPool(5).props(Props[Slave]), "master")
  
    for (i <- 1 to 10)
      poolMaster ! i
  
  
  // poisonpill and kill are not routed
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
