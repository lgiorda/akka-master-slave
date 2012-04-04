package org.solutionset.akkaredis

/**
 * Created with IntelliJ IDEA.
 * User: logan.giorda
 * Date: 4/3/12
 * Time: 10:05 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * group these by who they're sent to/from
 */
object Messages {

  case class StartWorker(actorPath: String)

  case class StopWorker(actorPath: String)

  case class Task(taskNumber: Int)

  case class TaskFinished(taskNumber: Int)

  case object Begin

  case object Stop

  case object SendWork

  case class Delay(time: Int)

}
