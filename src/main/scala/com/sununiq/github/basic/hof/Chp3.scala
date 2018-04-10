package com.sununiq.github.basic.hof

import scala.annotation.tailrec

object Chp3 {

  sealed trait List[+A]

  case object Nil extends List[Nothing]

  case class Cons[+A](head: A, tail: List[A]) extends List[A]

  object List {
    def sum(ints: List[Int]): Int = ints match {
      case Nil => 0
      case Cons(x, xs) => x + sum(xs)
    }

    def product(ds: List[Double]): Double = ds match {
      case Nil => 1.0
      case Cons(0.0, _) => 0.0
      case Cons(x, xs) => x * product(xs)
    }

    def apply[A](as: A*): List[A] =
      if (as.isEmpty) Nil
      else Cons(as.head, apply(as.tail: _*))

    def tail[A](list: List[A]): List[A] = list match {
      case Nil => Nil
      case Cons(_, t) => t
    }

    def setHead[A](a: A, list: List[A]): List[A] = list match {
      case Nil => List(a)
      case Cons(h, t) => Cons(a, t)
    }

    def drop[A](list: List[A], n: Int): List[A] = {
      @tailrec
      def helper[T](list: List[T], n: Int, result: List[T]): List[T] = (n, list) match {
        case (0, _) => result
        case (_, Nil) => result
        case (_, Cons(h, tail)) => helper(tail, n - 1, Cons(h, result))
      }

      if (n <= 0) list
      else helper(list, n, Nil)
    }

    def dropWhile[A](list: List[A], f: A => Boolean): List[A] = {
      @tailrec
      def helper[T](list: List[T], f: T => Boolean, result: List[T]): List[T] = list match {
        case Nil => result
        case Cons(h, t) => if (f(h)) result else helper(t, f, Cons(h, result))
      }

      helper(list, f, Nil)
    }

    def append[A](l1: List[A], l2: List[A]): List[A] = l1 match {
      case Nil => l2
      case Cons(h, t) => Cons(h, append(t, l2))
    }
  }

  def init[A](list: List[A]): List[A] = {
    list match {
      case Nil => Nil
      case Cons(_, Nil) => Nil
      case Cons(h, t) => Cons(h, init(t))
    }
  }

  /**
    * 抽象sum和product为右折叠
    */
  def foldRight[A, B](as: List[A], z: B)(f: (A, B) => B): B = {
    as match {
      case Nil => z
      case Cons(x, xs) => f(x, foldRight(xs, z)(f))
    }
  }

  def sum2(ns: List[Int]): Int = foldRight(ns, 0)((x, y) => x + y)

  def product2(ns: List[Double]): Double = foldRight(ns, 1.0)(_ * _)

  def main(args: Array[String]): Unit = {
    import List._
    val ls: List[Int] = List(1, 2, 3, 4, 5)
    println(dropWhile(ls, (x: Int) => x == 4))
  }
}
