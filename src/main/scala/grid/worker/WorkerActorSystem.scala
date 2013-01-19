package grid.worker

import com.typesafe.config.ConfigFactory

import akka.actor.ActorSystem
import akka.actor.Address
import grid.controller.StartWorker

object WorkerActorSystem {

  def main(args: Array[String]): Unit = {
    val argsList = args.toList

    // load the configuration
    val config = ConfigFactory.load().getConfig(argsList.first)
    val system = ActorSystem("WorkerSys", config)

    // get the reference to the remote server actor
    val registerRemoteWorkerActor = system
      .actorFor("akka://WorkServerSys@127.0.0.1:2552/user/RegisterRemoteWorkerActor");

    // create the worker address message
    val myAddr = new Address("akka", "WorkerSys", "127.0.0.1", Integer.parseInt(argsList.last));

    // send a message to server to register this worker instance
    registerRemoteWorkerActor.tell(new StartWorker(myAddr.toString));
  }
}