package org.solutionset.akkaredis.worker

import akka.actor.Actor
import org.solutionset.akkaredis.Messages._
import akka.event.Logging



/**
 * Created with IntelliJ IDEA.
 * User: logan.giorda
 * Date: 4/2/12
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */

class WorkerActor extends Actor {

  val log = Logging(context.system, this)
  var instanceCounter = 0
  var messageProcessedCounter = 0

  override def preStart = {
    instanceCounter += 1
    log.info("Starting WorkerActor instance #" + instanceCounter + ", hashcode #" + this.hashCode)
  }

  override def receive = {
    case Stop => {
      log.info("WorkerActor " + this.hashCode + " was sent a Stop message")
      context.stop(self)
    }
    case Task(taskNumber) => {
      // have we processed enough messages?
      if (messageProcessedCounter == 100) {
        // tell the server enough messages for me and shut me down
        
        
        /** sender ! StopWorker() **/
        
        context.stop(self)
        context.system.shutdown()
      } else {
        log.info("Work packet from server -> " + taskNumber)
        messageProcessedCounter += 1
        sender ! TaskFinished(taskNumber)
      }
    }
  }


  override def postStop = {
    log.info("Stopping WorkerActor instance #" + instanceCounter + ", hashcode #" + this.hashCode)
    instanceCounter -= 1
  }

}
