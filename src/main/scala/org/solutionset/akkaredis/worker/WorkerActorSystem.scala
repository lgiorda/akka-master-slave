package org.solutionset.akkaredis.worker

import akka.kernel.Bootable
import com.typesafe.config.ConfigFactory
import akka.actor.{Address, ActorSystem}
import org.solutionset.akkaredis.Messages.StartWorker


class WorkerActorSystem (config: String, port: Int) extends Bootable {

  val system = ActorSystem("WorkerSys", ConfigFactory.load.getConfig(config))
  val registerRemoteWorkerActor = system.actorFor("akka://WorkServerSys@127.0.0.1:2552/user/RegisterRemoteWorkerActor")
  // why can't we get the port from the config?
  val myAddr = new Address("akka", "WorkerSys", "127.0.0.1", port)

  registerRemoteWorkerActor ! StartWorker(myAddr.toString)

  def startup  = println("a worker system came online")
  def shutdown = system.shutdown()

}

object WorkerApp {
  def main(args: Array[String]) {
    new WorkerActorSystem(args(0), args(1).toInt)
  }
}
