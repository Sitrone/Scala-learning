package com.sununiq.github.basic.scala99

import com.sun.xml.internal.bind.v2.TODO

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
  * Scala99练习题(16 - 30)
  */
object Scala99_01 extends App {
  private val list1 = List(1, 2, 3, 5, 8)
  private val list2 = List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)
  private val list3 = List((4, 'a), (1, 'b), (2, 'c), (2, 'a), (1, 'd), (4, 'e))
  private val list4 = List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)

  println(P16.dropN(2, list1))

  println(P17.split2(3, list1))

  println(P18.slice1(1, 2, list1))

  println(P19.rotate(3, list4))

  println(P20.removeAt(3, list1))

  println(P21.insertAt1("'z", -1, list4))

  println(P22.range2(4, 9))

  println(P23.randomSelect(5, list4))

  println(P24.lotto(5, 49))

  println(P25.randomPermute(list4))

  println(P26.combinations(3, list1))
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

/**
  * Insert an element at a given position into a list
  */
object P21 {

  def insertAt[A](a: A, n: Int, ls: List[A]): List[A] = {
    if (n < 0 || n > ls.length) throw new IllegalArgumentException
    (ls.take(n) :+ a) ::: ls.drop(n)
  }

  def insertAt1[A](a: A, n: Int, ls: List[A]): List[A] = {

    @tailrec
    def helper[T](a: T, n: Int, ls: List[T], result: List[T]): List[T] = (n, ls) match {
      case (k, _) if k < 0 => a :: ls
      case (_, Nil) => result :+ a
      case (0, l) => result ::: a :: l
      case (_, h :: t) => helper(a, n - 1, t, result :+ h)
    }

    helper(a, n, ls, Nil)
  }

  def insertAt2[A](a: A, n: Int, ls: List[A]): List[A] = ls.splitAt(n) match {
    case (pre, post) => pre ::: (a :: post)
  }
}

/**
  * Create a list containing all integers within a given range.
  */
object P22 {

  def range(start: Int, end: Int): List[Int] = (start to end).toList

  def range1(start: Int, end: Int): List[Int] = {
    val listBuffer = ListBuffer[Int]()

    var i = start
    while (i <= end) {
      listBuffer.append(i)
      i += 1
    }
    listBuffer.toList
  }

  def range2(start: Int, end: Int): List[Int] = {
    @tailrec
    def helper(start: Int, end: Int, result: List[Int]): List[Int] = {
      if (start > end) result.reverse
      else helper(start + 1, end, start :: result)
    }

    helper(start, end, Nil)
  }

  // 参考答案
  // The classic functional approach would be to use `unfoldr`, which Scala
  // doesn't have.  So we'll write one and then use it.
  def unfoldRight[A, B](s: B)(f: B => Option[(A, B)]): List[A] =
    f(s) match {
      case None => Nil
      case Some((r, n)) => r :: unfoldRight(n)(f)
    }

  def rangeFunctional(start: Int, end: Int): List[Int] =
    unfoldRight(start) { n =>
      if (n > end) None
      else Some((n, n + 1))
    }
}

/**
  * Extract a given number of randomly selected elements from a list
  */
object P23 {

  import P20.removeAt

  def randomSelect[A](n: Int, ls: List[A]): List[A] =
    if (n <= 0) Nil
    else {
      val (rest, e) = removeAt(new Random().nextInt(ls.length), ls)
      e :: randomSelect(n - 1, rest)
    }
}

/**
  * Draw N different random numbers from the set 1..M
  */
object P24 {

  def lotto(n: Int, end: Int): List[Int] = {
    if (n < 0) Nil
    else new Random().nextInt(end) :: lotto(n - 1, end)
  }

  def lotto1(n: Int, end: Int): List[Int] = {

    @tailrec
    def helper(n: Int, end: Int, result: List[Int]): List[Int] = {
      if (n < 0) result.reverse
      else helper(n - 1, end, new Random().nextInt(end) :: result)
    }

    helper(n, end, Nil)
  }

  import P23.randomSelect

  def lotto2(n: Int, end: Int): List[Int] = randomSelect(n, List.range(1, end + 1))
}

/**
  * Generate a random permutation of the elements of a list
  */
object P25 {

  import P23.randomSelect

  def randomPermute[A](list: List[A]): List[A] = randomSelect(list.length, list)
}

/**
  * todo 难！！
  * Generate the combinations of K distinct objects chosen from the N elements of a list
  * C(K, N) = N * (N - 1) ... * (N-K)
  */
object P26 {

  // flatMapSublists is like list.flatMap, but instead of passing each element
  // to the function, it passes successive sublists of L.
  def flatMapSublists[A, B](ls: List[A])(f: List[A] => List[B]): List[B] =
    ls match {
      case Nil => Nil
      case sublist@_ :: tail => f(sublist) ::: flatMapSublists(tail)(f)
    }

  def combinations[A](n: Int, ls: List[A]): List[List[A]] =
    if (n == 0) List(Nil)
    else flatMapSublists(ls) { sl =>
      combinations(n - 1, sl.tail) map {
        sl.head :: _
      }
    }
}

/**
  * Group the elements of a set into disjoint subsets
  */
object P27{

  def group[A](ls :List[Int], target:List[A]) :List[List[A]] = {
    TODO

    return null
  }
}