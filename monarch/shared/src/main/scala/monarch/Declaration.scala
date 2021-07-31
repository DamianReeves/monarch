package monarch

import scala.annotation.tailrec

sealed trait Declaration { self =>
  import Declaration.*
  def ++(that: Declaration): Declaration = Both(self, that)
  def fold[S](initial: S)(f: (S, Declaration.Single[?]) => S): S = {
    @tailrec
    def loop(initial: S, self: Declaration, queue: List[Declaration]): S =
      self match {
        case Empty               =>
          queue match {
            case Nil           => initial
            case head :: queue => loop(initial, head, queue)
          }
        case decl @ Single(_, _) =>
          val next = f(initial, decl)
          queue match {
            case Nil           => next
            case head :: queue => loop(next, head, queue)

          }
        case Both(left, right)   => loop(initial, left, right :: queue)
      }
    loop(initial, self, Nil)
  }
}
object Declaration       {

  //def property[A, Attrib](field: Field[A, Attrib], expr: InitExpr[A]): Declaration[A, Any] = ???
  case object Empty                                                       extends Declaration
  final case class Single[A](field: Field[A, Nothing], expr: InitExpr[A]) extends Declaration
  final case class Both(left: Declaration, right: Declaration)            extends Declaration
}
