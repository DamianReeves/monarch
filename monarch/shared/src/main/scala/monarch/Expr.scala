package monarch
import monarch.Expr.Succeed
import monarch.Expr.Access

sealed trait Expr[+A, +Fields] { self =>

  def debug: String =
    self match {
      case Succeed(value) => value.toString()
      case Access(field)  => s"{{${field.key}}}"
    }
}

object Expr {
  import Field.Attribute

  def access[A, Attribs](field: Field[A, Attribs])(implicit
    ev: Attribs <:< Attribute.Readable
  ): Expr[A, Field[A, Attribs]] =
    Access[A, Field[A, Attribs]](field)

  def succeed[A](value: => A): Expr[A, Nothing] = Succeed(value)

  final case class Access[+A, F <: Field[A, ?]](field: F) extends Expr[A, F]
  final case class Succeed[+A](value: A)                  extends Expr[A, Nothing]
}
