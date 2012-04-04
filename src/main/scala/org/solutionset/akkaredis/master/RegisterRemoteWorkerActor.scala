package org.solutionset.akkaredis.master

import akka.actor.{ActorRef, Actor}
import org.solutionset.akkaredis.Messages._
import akka.event.Logging


/**
 * Created with IntelliJ IDEA.
 * User: logan.giorda
 * Date: 4/2/12
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */

class RegisterRemoteWorkerActor(jobControllerActor: ActorRef) extends Actor {

  val log = Logging(context.system, self)

  override def receive = {
    case StartWorker(actorPath) => {
      println("remote worker actor from " + actorPath + " is requesting to do work")
      jobControllerActor ! StartWorker(actorPath)
    }
    case _ => {
      println("received an unknown message type")
    }
  }


}
