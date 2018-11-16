package com.sununiq.scala.model

class Employee(val number: Int, val name: String) {
  // default constructor
  def this() {
    this(0, null)
  }
}