package com.sununiq.github.basic.alf

/**
  * java scala型变
  * 里氏替换：子类对象能够替换父类对象，而程序逻辑不变。
  */
trait Covariance {

  /**
    * 根据Liskov替换原则，f1能够接受的参数，f2也能接受。
    * 在这里f1接受的Bird类型，f2显然可以接受，因为Bird对象可以被当做其父类Animal的对象来使用
    */
  def f1(x: Animal): Bird

  /**
    * 看返回类型，f1的返回值可以被当做Animal的实例使用，
    * f2的返回值可以被当做Bird的实例使用，当然也可以被当做Animal的实例使用。
    */
  def f2(x: Bird): Animal
}

class Animal

class Bird extends Animal