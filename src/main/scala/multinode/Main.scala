package multinode

import akka.actor.typed.receptionist.Receptionist
import akka.actor.typed.receptionist.Receptionist.Listing
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}
import multinode.Receiver.ReceiverServiceKey
import singlenode.Messages.Start

object Main extends App {
  val guardian: Behavior[Nothing] = Behaviors.setup[Listing] { ctx ⇒
    ctx.system.receptionist ! Receptionist.Subscribe(ReceiverServiceKey, ctx.self)

    val replyActor = ctx.spawnAnonymous(Receiver.replyService)

    Behaviors.receiveMessagePartial[Listing] {
      case ReceiverServiceKey.Listing(listings) if listings.nonEmpty ⇒
        listings.foreach(
          remoteReplyActor ⇒ ctx.spawnAnonymous(Sender.sendBehaviour) ! Start("Hi", remoteReplyActor)
        )

        Behaviors.same
    }
  }.narrow

  val system: ActorSystem[Nothing] = ActorSystem(guardian, "main")

//  system ! "Ales, Maja in Luka."
}
