package akkaSystems

import akka.actor.{Actor, ActorSystem}

object Ex1 extends App {

  val actorSystem = ActorSystem("system")

  class CounterActor extends Actor{
    var counter = 0
    override def receive: Receive ={
      case "increment" => counter+=1
      case "decrement" => counter-=1
      case "print" => println(s"counter: $counter")

    }
  }

  class BankAccount extends Actor{
    var counter = 0
    override def receive: Receive ={
      case "deposit" => counter+=1
      case "withdraw" => counter-=1
      case "statement" => println(s"counter: $counter")

    }
  }

}

/*
// Sending a message
message is stored in actor's mailbox
Thread safe


// Processing a message
A thread is scheduled to run this actor
messages are dequeued from the mailbox
Thread invokes the handler for each message
at some point, the actor is unscheduled



Hence actors are single threaded
message processing is atomic. thread will not release the actor in middle of a message


Guarantees
At most once delivery
For every sender receiver pair, the message order is maintained
 */
