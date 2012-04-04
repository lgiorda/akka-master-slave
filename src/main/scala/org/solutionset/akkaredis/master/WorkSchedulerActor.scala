package org.solutionset.akkaredis.master

import java.util.concurrent.TimeUnit
import akka.actor.Actor
import akka.util.Duration
import org.solutionset.akkaredis.Messages.{SendWork, Delay}
import akka.event.Logging

/**
 *  This class pretty much regulates the frequency of work to ship out
 *  to our WorkerActors
 */
class WorkSchedulerActor extends Actor {

  val log = Logging(context.system, this)

  override def receive = {
    case Delay(time) => {
      context.system.scheduler.scheduleOnce(Duration.create(time, TimeUnit.SECONDS), sender, SendWork)
    }
    case SendWork => {
      log.info("Received work sending request")
      context.system.scheduler.scheduleOnce (Duration.create(1000, TimeUnit.MILLISECONDS), sender, SendWork)
    }
  }

}
