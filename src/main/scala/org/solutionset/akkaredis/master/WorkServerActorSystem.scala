package org.solutionset.akkaredis.master

import akka.kernel.Bootable
import akka.remote.RemoteLifeCycleEvent
import com.typesafe.config.{Config, ConfigFactory}
import akka.dispatch.{PriorityGenerator, UnboundedPriorityMailbox}
import akka.actor.{PoisonPill, Address, Props, ActorSystem}
import org.solutionset.akkaredis.Messages.Begin
import akka.event.Logging

class WorkServerActorSystem extends Bootable {

  
  
  override def startup = {
    System.err.println("STARTING up the ServerActorSystem")
  }
  // load config for server system, basically that it's hosted on 2552
  val system = ActorSystem("WorkServerSys", ConfigFactory.load.getConfig("WorkServerSys"))

  // create various server-side actors to manage remote WorkerActors
  val workSchedulerActor = system.actorOf(Props[WorkSchedulerActor], "WorkSchedulerActor")
  val jobControllerActor = system.actorOf(Props(new JobControllerActor(workSchedulerActor)), "JobControllerActor")
  val remoteActorListener = system.actorOf(Props(new RemoteClientEventListener(jobControllerActor)), "RemoteClientEventListener")
  val registerRemoteWorkerActor = system.actorOf(Props(new RegisterRemoteWorkerActor(jobControllerActor)), "RegisterRemoteWorkerActor")

  workSchedulerActor ! Begin

  system.eventStream.subscribe(remoteActorListener, classOf[RemoteLifeCycleEvent])

  override def shutdown = {
    System.err.println("SHUTTING DOWN the ServerActorSystem...goodbye!")
  }
}

class MyPriorityMailbox(settings: ActorSystem.Settings, config: Config) extends UnboundedPriorityMailbox (

  PriorityGenerator {
    case Address => 0
    case PoisonPill => 3
    case otherwise => 1
  }
)

object WorkServerApp {
  def main(args: Array[String]) {
    new WorkServerActorSystem
  }
}