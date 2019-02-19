package multinode

import akka.actor.typed.Behavior
import akka.actor.typed.receptionist.{Receptionist, ServiceKey}
import akka.actor.typed.scaladsl.Behaviors
import singlenode.Messages.{Req, Resp}

object Receiver {
  val ReceiverServiceKey = ServiceKey[Req]("receiverService")

  val replyService: Behavior[Req] =
    Behaviors.setup { ctx ⇒
      ctx.system.receptionist ! Receptionist.Register(ReceiverServiceKey, ctx.self)

      Behaviors.receiveMessage[Req] {
        case Req(text, replyTo) ⇒
          ctx.log.info("Message received, echoing it back.")

          //message must be of type Resp(..)
          replyTo ! Resp(text)
          Behaviors.stopped
      }
    }
}
