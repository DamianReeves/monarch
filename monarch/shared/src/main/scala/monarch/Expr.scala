package monarch

sealed trait Expr[+A, -Fields]

object Expr {
  import Field.Attribute
  def access[A, Attribs, F <: Field[A, Attribs]](field: F)(implicit ev: Attribs <:< Attribute.Readable): Expr[A, F] =
    Access[A, F](field)

  final case class Access[+A, F <: Field[A, ?]](field: F) extends Expr[A, F]
}
