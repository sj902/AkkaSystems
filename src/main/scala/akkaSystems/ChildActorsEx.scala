package akkaSystems

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akkaSystems.ChildActorsEx.WCM.{CountTask, Init, Reply}
import akkaSystems.ChildActorsEx.WCW.Count

object ChildActorsEx extends App {


  val sys = ActorSystem("sys")

  object WCM{
    case class Init(nChildren: Int)
    case class CountTask(text: String)
    case class Reply(count: Int)
  }

  class WCM extends Actor{

    def delegate(children: Seq[ActorRef], i: Int): Receive = {
      case CountTask(str) =>
      case Reply(n) => sender() ! n
    }

    override def receive: Receive = {
      case Init(n) =>
        val children = (1 to n).map(x => context.actorOf(Props[WCW], s"Child$x"))
        context.become(delegate(children, 0))
    }



  }

  object WCW{
    case class Count(text: String)
  }

  class WCW extends Actor{
    override def receive: Receive = {
      case Count(str) => sender() forward  Reply(str.split(" ").length)
    }
  }

  val ac = sys.actorOf(Props[WCM])

  ac ! Init(10)

  ac ! "Hello World"

}
