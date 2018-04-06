package com.sununiq.github.basic.scala99

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

/**
  * Scala99练习题(16 - 30)
  */
object Scala99_01 extends App {
  private val list1 = List(1, 1, 2, 3, 5, 8)
  private val list2 = List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)
  private val list3 = List((4, 'a), (1, 'b), (2, 'c), (2, 'a), (1, 'd), (4, 'e))
  private val list4 = List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)

  println(P16.dropN(2, list1))

  println(P17.split2(3, list1))

  println(P18.slice1(1, 2, list1))

  println(P19.rotate(3, list4))

  println(P20.removeAt(3, list1))
}

/**
  * Drop every Nth element from a list
  */
object P16 {

  def dropN[A](n: Int, list: List[A]): List[A] = {

    @tailrec
    def helper[T](n: Int, list: List[T], result: List[T]): List[T] = (n, list) match {
      case (0, _ :: t) => result ::: t
      case (_, Nil) => result
      case (_, h :: t) => helper(n - 1, t, result :+ h)
    }

    helper(n, list, Nil)
  }
}

/**
  * Split a list into two parts
  */
object P17 {

  def split[A](n: Int, ls: List[A]): (List[A], List[A]) = ls splitAt n

  def split1[A](n: Int, ls: List[A]): (List[A], List[A]) = {
    @tailrec
    def helper[T](n: Int, ls: List[T], result: (List[T], List[T])): (List[T], List[T]) = (n, ls) match {
      case (0, t) => (result._1, t)
      case (_, Nil) => result
      case (_, h :: t) => helper(n - 1, t, (result._1 :+ h, result._2))
    }

    helper(n, ls, (Nil, Nil))
  }

  def split2[A](n: Int, ls: List[A]): (List[A], List[A]) = (ls.take(n), ls.drop(n))
}

/**
  * Extract a slice from a list
  */
object P18 {

  def slice[A](start: Int, end: Int, ls: List[A]): List[A] = ls.slice(start, start + end - start)

  /**
    * 命令式解法
    */
  def slice1[A](start: Int, end: Int, ls: List[A]): List[A] = {
    val listBuffer = ListBuffer[A]()
    val lo = start max 0
    val hi = end min ls.length

    var i = 0
    while (i < ls.length) {
      if (i >= lo && i <= hi) {
        listBuffer.append(ls(i))
      }
      i += 1
    }
    listBuffer.toList
  }

  def slice2[A](start: Int, end: Int, ls: List[A]): List[A] =
    (start, end, ls) match {
      case (_, _, Nil) => Nil
      case (_, e, _) if e <= 0 => Nil
      case (s, e, h :: tail) if s <= 0 => h :: slice2(0, e - 1, tail)
      case (s, e, _ :: tail) => slice2(s - 1, e - 1, tail)
    }

}

/**
  * Rotate a list N places to the left.
  */
object P19 {

  def rotate[A](n: Int, ls: List[A]): List[A] = (ls.take(n).reverse ::: ls.drop(n).reverse).reverse

}

/**
  * Remove the Kth element from a list
  */
object P20 {

  def removeAt[A](n: Int, ls: List[A]): (List[A], A) = {
    if (n < 0 || n > ls.length) throw new IllegalArgumentException
    (ls.take(n) ::: ls.drop(n + 1), ls(n))
  }
}
