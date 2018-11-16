package com.sununiq.github.basic.tool

import java.lang.reflect.{InvocationHandler, Method, Proxy}
import java.util.Properties

class PropertiesProxy {
  private var properties: Properties = _

  private def processMethodName(methodName: String): String = methodName match {
    case s if s.startsWith("get") => methodName(3) + methodName.drop(3)
    case s if s.startsWith("has") => methodName(3) + methodName.drop(3)
    case s if s.startsWith("is") => methodName(2) + methodName.drop(2)
    case _ => methodName
  }

  private def camel2dot(methodName: String): String = {
    val builder = new StringBuilder(methodName.length)
    methodName.foreach { c =>
      if (c.isUpper) {
        builder.append('.')
      }
      builder.append(c.toLower)
    }
    builder.toString()
  }

  def to[T](clazz: Class[T]): T = {
    val value: AnyRef = Proxy.newProxyInstance(clazz.getClassLoader, Array(clazz), new ProxyInvocationHandler())
    value.asInstanceOf[T]
  }

  //TODO 还是有问题
  class ProxyInvocationHandler extends InvocationHandler {
    def invoke(proxy: Object, method: Method, args: Array[Object]): Object = {
      val methodName = camel2dot(processMethodName(method.getName))
      val originValue: String = properties.get(methodName).toString
      if (method.getReturnType == java.lang.Integer.TYPE) {
        originValue
      }else if (method.getReturnType == java.lang.Boolean.TYPE) {
        originValue
      } else{
        throw new IllegalArgumentException()
      }
//      type[method.getReturnType]
//      method.getReturnType match {
//        case Int.getClass => originValue.toInt
//        case _: Class[Long] => originValue.toLong
//        case _: Class[Boolean] => originValue.toBoolean
//        case _: Class[String] => originValue
//        case _: Class[Double] => originValue.toDouble
//      }
    }
  }

}

object PropertiesProxy {

  def from(properties: Properties): PropertiesProxy = {
    val proxy = new PropertiesProxy()
    proxy.properties = properties
    proxy
  }
}

trait Config {
  def httpPort(): Int
}

object Test extends App {
  private val properties = new Properties()
  properties.put("http.port", 8080.asInstanceOf[Object])
  private val proxy = PropertiesProxy.from(properties)

  private val config: Config = proxy.to(classOf[Config])
  println(config.httpPort())
}
