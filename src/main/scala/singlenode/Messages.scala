package singlenode

import akka.actor.typed.ActorRef

object Messages {
  trait Msg
  final case class Req(text: String, sender: ActorRef[Resp]) extends Msg
  final case class Resp(text: String) extends Msg
  final case class Start(text: String, receiver: ActorRef[Req]) extends Msg
}
