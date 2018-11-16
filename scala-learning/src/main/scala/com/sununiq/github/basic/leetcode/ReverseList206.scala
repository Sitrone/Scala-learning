package com.sununiq.github.basic.leetcode


object ReverseList206 extends App {

  val root = ListNode(1)
  root.next(2).next(3).next(4).next(5)

  val node: ListNode = reverseList(root)

  import com.sununiq.github.basic.alf.Preamble._
  node.print()


  def reverseList(head: ListNode): ListNode = {
    var pre: ListNode = null
    var cur = head
    while (cur != null) {
      val next = cur.next
      cur.next = pre
      pre = cur
      cur = next
    }
    pre
  }
}
