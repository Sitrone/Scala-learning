package com.sununiq.github.basic.leetcode

class ListNode(var _x: Int = 0) {
  var next: ListNode = _
  var x: Int = _x

  def next(y: Int): ListNode = {
    val nextNode = ListNode(y)
    next = nextNode
    nextNode
  }


  override def toString = s"ListNode($next, $x)"
}

object ListNode {
  def apply(x: Int) = new ListNode(x)
}