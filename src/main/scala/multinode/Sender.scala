package multinode

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import singlenode.Messages.{Msg, Req, Resp, Start}

object Sender {
  val sendBehaviour: Behavior[Msg] = Behaviors.receive {
    (ctx, msg) => msg match {
      //Response from the Receiver (Pong)
      case Resp(text) =>
        ctx.log.info(text)
        Behaviors.stopped

      //Starts the messaging (the main method)
      case Start(text, sendTo) =>
        //message must be of type Req(..) and second value must be of type ActorRef[Resp] - the sender
        sendTo ! Req(text, ctx.self)
        Behaviors.same
    }
  }
}
