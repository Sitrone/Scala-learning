package com.sununiq.github.basic.hof

object Algs extends App {

  def insert(x: Int, list: List[Int]): List[Int] = list match {
    case Nil => List(x)
    case h :: _ if x <= h => x :: list
    case h :: t if x > h => List(h) ++ insert(x, t)
  }

  def insertSort(list: List[Int]): List[Int] = list match {
    case Nil => Nil
    case h :: t => insert(h, insertSort(t))
  }

  def maybeTwice(b: Boolean, i: () => Int) = if (b) i() else 0

  maybeTwice(b = true, () => {
    println("hi")
    1 + 41
  })

  println(insertSort(List(23, 23, 321, 123, 123, 122, 323, 213)))

}
