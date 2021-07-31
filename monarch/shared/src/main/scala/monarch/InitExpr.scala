package monarch

sealed trait InitExpr[+A] {}
object InitExpr           {
  def succeed[A](value: => A): InitExpr[A] = Succeed(value)
  final case class Succeed[+A](value: A) extends InitExpr[A]
}
