package org.solutionset.akkaredis.master

import akka.actor.{ActorRef, Actor}
import akka.remote.{RemoteClientWriteFailed, RemoteClientError}
import akka.event.Logging
import org.solutionset.akkaredis.Messages.StopWorker


/**
 * Created with IntelliJ IDEA.
 * User: logan.giorda
 * Date: 4/2/12
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class will listen for errors from our remote WorkerActors
 * If there's a problematic Worker then we simply send a message to
 * the JobScheduler which removes that Worker from the system
 * @param inJobScheduler
 */
class RemoteClientEventListener(inJobScheduler: ActorRef) extends Actor {

  val log = Logging(context.system, this)

  override def preStart = log.debug("RemoteClientEventListener STARTING")

  override def receive = {

    case RemoteClientError(cause, remote, remoteAddress) => {
      log.info("Received remote client error at: " + remoteAddress)
      inJobScheduler ! StopWorker(remoteAddress.toString)
      log.info("Cause of error was: " + cause)
    }

    case RemoteClientWriteFailed(request, cause, remote, remoteAddress) => {
      log.info("Received remote client write fail event from address " + remoteAddress)
      inJobScheduler ! StopWorker(remoteAddress.toString)
    }

    case _ => log.debug("RemoteClientEventListener received unknown message")
  }

  override def postStop = log.debug("RemoteClientEventListener STOPPING")


}
