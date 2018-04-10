package com.sununiq.github.basic.alf

import java.awt.event.{ActionEvent, ActionListener}

import javax.swing.JButton

/**
  * 隐式转换
  */
object Preamble {

  /**
    * scala 2.12版本之后不需要此类隐式转换，Scala支持SAM转换
    *
    * @param action
    * @return
    */
  implicit def makeAction(action: (ActionEvent) => Unit) = {
    new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = action(e)
    }
  }

  implicit def print[T](t: T): Unit = println(t)

  implicit final class PrintEveryThing[T](t: T) {
    def print(): Unit = println(t)
  }

}

class MyRichInt(val self: Int) {
  def +%(n: Int): Int = self + (self / n)
}

object MyRichInt {
  implicit def int2MyRichInt(n: Int): MyRichInt = new MyRichInt(n)
}

object TestImplicit extends App {

  import Preamble._

  "ab".print()

  Nil.print()


  var counter = 0
  val button = new JButton("Increment")
  button.addActionListener((_: ActionEvent) => counter + 1)
  //  button.addActionListener { (_: ActionEvent) => counter + 1 }
  //  button.addActionListener { _ => counter + 1 }

  import MyRichInt._
  println(120 +% 10)
}
