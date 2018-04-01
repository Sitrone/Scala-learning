println("Hello scala")

val states = List("Alabama", "Alaska", "Virginta", "Wyoming") //这是一个list
val r = for {
  s <- states
} yield s.toUpperCase
states.map(_.toUpperCase)
