package com.sununiq.github.basic.scala99

import scala.annotation.tailrec

/**
  * http://aperiodic.net/phil/scala/s-99/
  * scala 99练习题（1 - 15）
  * SICP 的简化版
  * 模式匹配真乃神器
  * 尾递归，模式匹配，list常见集合操作
  */
object Scala99 extends App {
  private val list1 = List(1, 1, 2, 3, 5, 8)
  private val list2 = List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)
  private val list3 = List((4, 'a), (1, 'b), (2, 'c), (2, 'a), (1, 'd), (4, 'e))

  println(P02.lastButOne(list1))

  println(P03.kthElement(5, list1))

  println(P05.reverse(list1))

  println(P08.compress(list2))

  println(P09.pack(list2))

  println(P10.encode(list2))

  println(P11.encodeModified(list2))

  println(P12.encode(list3))

  println(P13.encodeDirect1(list2))

  println(P14.duplicate(list1))

  println(P15.duplicateN(3, list1))
}

/**
  * Find the last element of a list.
  */
object P01 {

  def lastItem0[T](list: List[T]): T = list.last

  @tailrec
  def lastItem[T](list: List[T]): T = list match {
    case h :: Nil => h
    case _ :: t => lastItem(t)
    case _ => throw new IllegalArgumentException
  }
}

/**
  * Find the last but one element of a list.
  */
object P02 {

  def lastButOne0[T](list: List[T]): T = list.takeRight(0).head

  @tailrec
  def lastButOne[T](list: List[T]): T = list match {
    case h :: _ :: Nil => h
    case _ :: _ :: t => lastButOne(t)
    case _ => throw new IllegalArgumentException
  }
}

/**
  * Find the Kth element of a list
  */
object P03 {

  def kthElement0[T](n: Int, list: List[T]): T = list(n)

  @tailrec
  def kthElement[T](n: Int, list: List[T]): T = list match {
    case h :: _ if n == 0 => h
    case _ :: t => kthElement(n - 1, t)
    case _ => throw new IllegalArgumentException
  }
}

/**
  * Find the number of elements of a list.
  */
object P04 {

  def size0[T](list: List[T]): Int = list.size

  def size[T](list: List[T]): Int = {
    @tailrec
    def sizeHelper[A](list: List[A], size: Int): Int = (list, size) match {
      case (Nil, s) => s
      case (_ :: t, s) => sizeHelper(t, s + 1)
    }

    sizeHelper(list, 0)
  }

  //  参考答案，纯函数式解法
  // More pure functional solution, with folds.
  def lengthFunctional[A](ls: List[A]): Int = ls.foldLeft(0) { (c, _) => c + 1 }
}


/**
  * Reverse a list
  */
object P05 {

  def reverse0[T](list: List[T]): List[T] = list.reverse

  def reverse[T](list: List[T]): List[T] = {
    @tailrec
    def helper[A](list: List[A], result: List[A]): List[A] = list match {
      case Nil => result
      case h :: t => helper(t, h :: result)
    }

    helper(list, Nil)
  }

  // 参考答案，纯函数式解法
  // Pure functional
  def reverseFunctional[A](ls: List[A]): List[A] =
    ls.foldLeft(List[A]()) { (r, h) => h :: r }
}

/**
  * Find out whether a list is a palindrome
  */
object P06 {

  def isPalindrome[T](list: List[T]): Boolean = list == list.reverse
}

/**
  * Flatten a nested list structure.
  */
object P07 {

  def flatten(list: List[Any]): List[Any] = list flatMap {
    case ms: List[_] => flatten(ms)
    case e => List(e)
  }
}

/**
  * Eliminate consecutive duplicates of list elements.
  */
object P08 {

  def compress[T](ls: List[T]): List[T] = {

    @tailrec
    def helper[A](ls: List[A], result: List[A]): List[A] = ls match {
      case Nil => result
      case h :: Nil => result :+ h
      case h :: s :: _ => if (h == s) helper(ls.tail, result) else helper(ls.tail, result :+ h)
    }

    helper(ls, Nil)
  }

  // 参考答案
  // Functional.
  def compressFunctional[A](ls: List[A]): List[A] =
    ls.foldRight(List[A]()) { (h, r) =>
      if (r.isEmpty || r.head != h) h :: r
      else r
    }
}

/**
  * Pack consecutive duplicates of list elements into sublists
  */
object P09 {

  def pack[A](ls: List[A]): List[List[A]] = {

    @tailrec
    def helper[T](ls: List[T], result: List[List[T]]): List[List[T]] = ls match {
      case Nil => result
      case h :: t => helper(t.dropWhile(_ == h), result :+ (h :: t.takeWhile(_ == h)))
    }

    helper(ls, List())
  }

  def pack2[A](ls: List[A]): List[List[A]] = {
    if (ls.isEmpty) List(List())
    else {
      val (packed, next) = ls span {
        _ == ls.head
      }
      if (next == Nil) List(packed)
      else packed :: pack2(next)
    }
  }
}

/**
  * Run-length encoding of a list.
  */
object P10 {

  def encode[A](ls: List[A]): List[(Int, A)] = {
    P09.pack(ls).map {
      subList => (subList.size, subList.head)
    }
  }
}

/**
  * Modified run-length encoding
  */
object P11 {

  def encodeModified[A](ls: List[A]): List[Any] = {
    P09.pack(ls) map {
      subList => if (subList.size == 1) subList.head else (subList.size, subList.head)
    }
  }
}

/**
  * Decode a run-length encoded list.
  */
object P12 {

  @tailrec
  private def repeat[A](n: Int)(f: => A) {
    if (n > 0) {
      f
      repeat(n - 1)(f)
    }
  }

  def encode[A](ls: List[(Int, A)]): List[A] = {
    ls flatMap { t =>
      List.fill(t._1)(t._2)
    }
  }
}

/**
  * Run-length encoding of a list (direct solution)
  */
object P13 {

  def encodeDirect1[A](ls: List[A]): List[(Int, A)] = {
    @tailrec
    def helper[T](ls: List[T], result: List[(Int, T)]): List[(Int, T)] = ls match {
      case Nil => result
      case h :: t => val temp = h :: t.takeWhile(_ == h)
        helper(t.dropWhile(_ == h), result :+ (temp.length, temp.head))
    }

    helper(ls, Nil)
  }


  def encodeDirect2[A](ls: List[A]): List[(Int, A)] = {
    if (ls.isEmpty) Nil
    else {
      val (packed, next) = ls span {
        _ == ls.head
      }
      (packed.length, packed.head) :: encodeDirect2(next)
    }
  }
}

/**
  * Duplicate the elements of a list
  */
object P14 {

  def duplicate[A](ls: List[A]): List[A] = ls flatMap {
    List.fill(2)(_)
  }
}

/**
  * Duplicate the elements of a list a given number of times
  */
object P15 {

  def duplicateN[A](n: Int, ls: List[A]): List[A] = ls flatMap {
    List.fill(n)(_)
  }
}