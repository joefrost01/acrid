package compute

import scalacl._
import scala.math._

/**
 * @author ${user.name}
 */
object App {

  def main(args: Array[String]): Unit = {

    implicit val context = Context.best(GPU) // prefer CPUs ? use Context.best(CPU)
    println(context)
    val rng = (100 until 10000000).cl // transform the Range into a CLIntRange
    // ops done asynchronously on the GPU (except synchronous sum) :
    val t1 = System.currentTimeMillis()
    val sum = rng.map(_ * 2).zipWithIndex.map(p => p._1 * p._2).sum
    println("sum = " + sum)
    val t2 = System.currentTimeMillis()
    println(t2 - t1)
  }

}
