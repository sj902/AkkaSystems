package akkaSystems

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object AkkaConfig extends App {
  
  class ConfigActor extends Actor with ActorLogging{
    override def receive: Receive ={
      case message =>
        log.info(message.toString)
    }
  }
  
  // inline
  val configString =
    """
      |akka {
      |   loglevel = "ERROR"
      |}
    """.stripMargin
  
  val config = ConfigFactory.parseString(configString)
  
  val sys1 = ActorSystem("sys", ConfigFactory.load(config))
  
  val ac = sys1.actorOf(Props[ConfigActor])
  ac ! "ff"

  val defaultConfFileSystem = ActorSystem("system")


  // file - from resources/application.conf
  val defaultConfFileActor = defaultConfFileSystem.actorOf(Props[ConfigActor])
  ac ! "gg"
  defaultConfFileActor ! "hh"

  // separate from same file
  val specialCOnf = ConfigFactory.load().getConfig("mySpecialConfig")
  val specialConfFileSystem = ActorSystem("specialSystem", ConfigFactory.load(specialCOnf))

  val specialConfFileActor = specialConfFileSystem.actorOf(Props[ConfigActor])
  specialConfFileActor ! "ii"
  
  
  //from anotherFile
  
  val separateConf = ConfigFactory.load("dev/dev.conf")
  println(separateConf.getString("akka.loglevel"))
  
  
}

