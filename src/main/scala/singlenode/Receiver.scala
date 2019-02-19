package singlenode

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import singlenode.Messages.{Req, Resp}

object Receiver {
  //The context of this actor isn't needed..
  val replyBehaviour: Behavior[Req] = Behaviors.receiveMessage {
    case Req(text, sender) =>
      sender ! Resp(text) //message must be of type Resp(..)
      Behaviors.same
  }
}
