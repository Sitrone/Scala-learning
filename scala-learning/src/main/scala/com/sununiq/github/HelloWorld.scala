package com.sununiq.github

import java.io.File
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Files, Path, SimpleFileVisitor}

object HelloWorld extends App {
  println("Hello Scala.")

  implicit def makeFileVisitor(f: Path => Unit): SimpleFileVisitor[Path] {
    def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult
  } = new SimpleFileVisitor[Path] {
    override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = {
      f(file)
      FileVisitResult.CONTINUE
    }
  }

  Files.walkFileTree(new File("").toPath, (f: Path) => println(f))
}
