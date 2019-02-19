package singlenode

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}
import singlenode.Messages.Start

object Main extends App {
  val main: Behavior[String] = Behaviors.setup { ctx ⇒
    //Create actors
    val sender = ctx.spawn(Sender.sendBehaviour, "sender")
    val receiver = ctx.spawn(Receiver.replyBehaviour, "receiver")

    //Send the start/initiate message to sender to start the communication of actors
    Behaviors.receiveMessage { text ⇒
      sender ! Start(text, receiver)
      Behaviors.same
    }
  }

  val system: ActorSystem[String] = ActorSystem(main, "main")

  system ! "Ales, Maja in Luka."
}
