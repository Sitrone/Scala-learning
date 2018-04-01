package com.sununiq.github.basic.alf

import java.awt.event.{ActionEvent, ActionListener}

import javax.swing.JButton

/**
  * Scala：隐式类型，可以对现有的类库进行扩展，缺点，代码可读性降低，debug难度增加
  * kotlin：通过扩展函数来对现有类库进行扩展，实现更优雅，并且inline加持，有时开销甚至更小
  */
class ImplictFeature {

  implicit def makeAction(action: (ActionEvent) => Unit) = {
    new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = action(e)
    }
  }

  var counter = 0
  val button = new JButton("Increment")
  button.addActionListener((event: ActionEvent) => counter + 1)
  //  button.addActionListener { (_: ActionEvent) => counter + 1 }
  //  button.addActionListener { _ => counter + 1 }
}

object ControlFlow {
  def runInThread(block: () => Unit): Unit = {
    new Thread() {
      override def run(): Unit = {
        block()
      }
    }.start()
  }

  /**
    * 换名调用，在参数声明和调用该函数参数的地方略去(),但保留=>
    *
    * @param block
    */
  def runInThread1(block: => Unit): Unit = {
    new Thread() {
      override def run(): Unit = {
        block
      }
    }.start()
  }

  def testRunInThread(): Unit = {
    runInThread {
      () => println("Hello world")
    }
    runInThread1 {
      println("Hello world")
      println("换名调用")
    }
  }

  def sum(lst: List[Int]): Int = lst match {
    case Nil => 0
    case h :: t => h + sum(t)
  }

  def main(args: Array[String]): Unit = {

  }
}
