package monarch

sealed trait Declaration[+A] { self =>
  type FieldRepr <: Field[A, Field.Attribute.Default]
  def declaredField: FieldRepr
}

object Declaration {

  type Untyped = Declaration[?]

  def let[A, Attribs, F <: ReadOnlyField[A, Attribs]](field: F, expr: InitExpr[A]): Declaration[A] =
    Let[A, Attribs, F](field, expr)

  def variable[A, Attribs, F <: Variable[A, Attribs]](field: F, expr: InitExpr[A]): Declaration[A] =
    Var[A, Attribs, F](field, expr)
  private final case class Let[+A, +Attribs, F <: ReadOnlyField[A, Attribs]](
    field: F,
    expr: InitExpr[A]
  ) extends Declaration[A] {
    type FieldRepr = F

    def declaredField: FieldRepr = field
  }

  private final case class Var[+A, +Attribs, F <: Variable[A, Attribs]](
    field: F,
    expr: InitExpr[A]
  ) extends Declaration[A] {
    type FieldRepr = F

    def declaredField: FieldRepr = field
  }

}
