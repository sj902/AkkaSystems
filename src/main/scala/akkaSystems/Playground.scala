package akkaSystems

import akka.actor.{Actor, ActorSystem, Props}

object Playground extends App {

  val actorSystem = ActorSystem("helloWorld")

  println(actorSystem.name)

  // Actors are uniquely identified within an actor system
  // Messages are async
  // Each actor may respond differently
  // Actors are really encapsulated

  class WordCountActor extends Actor {
    // internal data
    var totalWords = 0

    // behaviour
    override def receive: Receive = {
      case message: String =>
        println(s"[wc]: received message ${message.toString}")
        totalWords += message.split(" ").length
        println()
      case msg => println(s"[word count] Can't understand ${msg.toString}")
    }
  }

  // Instantiate
  val wordCounter = actorSystem.actorOf(Props[WordCountActor], "wordCount")
  val anotherWordCounter = actorSystem.actorOf(Props[WordCountActor], "anotherWordCount")

  // Communicate

  wordCounter ! "Learning akka" // tell
  anotherWordCounter ! "second message"
}
