package com.sununiq.github.basic.concurrent.futures

import java.util.concurrent.TimeUnit

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * future 与 Promise
  * https://docs.scala-lang.org/zh-cn/overviews/core/futures.html
  * https://github.com/gongbp/scala-in-practice/blob/master/doc/13%20Scala%E4%B8%AD%E7%9A%84%E5%BC%82%E6%AD%A5%E7%BC%96%E7%A8%8B%E4%B9%8B%20Future.markdown
  * future 支持同步和异步调用，串联调用常用map映射
  */
object FutureExample extends App {

  //  combineMuti()
//  val f = exceptionExample()
//  println(Await.result(f, 1 seconds))

  private def failureFuture(): Unit = {
    val f = Future {
      2 / 0
    }

    f.failed.foreach {
      case _: NullPointerException =>
        println("I'd be amazed if this printed out.")
    }
  }

  /**
    * 异步计算，组合结果，阻塞等待结果
    */
  def combineMuti(): Unit = {
    val f1 = Future {
      TimeUnit.SECONDS.sleep(1)
      "f1"
    }
    val f2 = Future {
      TimeUnit.SECONDS.sleep(2)
      "f2"
    }
    val f3 = Future {
      TimeUnit.SECONDS.sleep(3)
      2342
    }
    val f4 = Future.sequence(Seq(f1, f2, f3))
    val results = Await.result(f4, 4 second)

    println(results)
  }

  /**
    * for 推导式
    */
  def useForComprehension(): Unit = {
    val f1 = Future {
      TimeUnit.SECONDS.sleep(1)
      "f1"
    }
    val f2 = Future {
      TimeUnit.SECONDS.sleep(2)
      "f2"
    }
    val f3 = Future {
      TimeUnit.SECONDS.sleep(3)
      2342
    }

    val f4: Future[(String, String, Int)] =
      for {
        r2 <- f2
        r3 <- f3
        r1 <- f1
      } yield (r1.take(1), r2.drop(1), r3 + 1)

    val (f1Str, f2Str, f3Int) = Await.result(f4, 4.seconds)
    println(s"f1: $f1Str, f2: $f2Str, f3: $f3Int")

  }

  def exceptionExample() = {
    Future(6 / 0) recover { case e: ArithmeticException => 0 } // result: 0
    Future(6 / 0) recover { case e: IllegalArgumentException => 0 } // result: exception
    Future(6 / 2) recover { case e: ArithmeticException => 0 } // result: 3
  }
}
