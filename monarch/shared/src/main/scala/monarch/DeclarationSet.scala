package monarch

sealed trait DeclarationSet { self =>
  type Append[That <: DeclarationSet] <: DeclarationSet
  def ++[That <: DeclarationSet](that: That): Append[That]

  def declaredFields: List[Field.Untyped] = declarationsUntyped.map(_.declaredField)
  def declarationsUntyped: List[Declaration.Untyped]
}
object DeclarationSet       {

  type Empty                       = Empty.type
  type :*:[A, B <: DeclarationSet] = Cons[A, B]
  type Singleton[A]                = Cons[A, Empty]
  case object Empty                                                             extends DeclarationSet {
    type Append[That <: DeclarationSet] = That
    def ++[That <: DeclarationSet](that: That): Append[That] = that
    def declarationsUntyped: List[Declaration.Untyped]       = Nil
  }
  sealed case class Cons[A, B <: DeclarationSet](head: Declaration[A], tail: B) extends DeclarationSet { self =>
    type Append[That <: DeclarationSet] = Cons[A, tail.Append[That]]
    def ++[That <: DeclarationSet](that: That): Append[That] = Cons(head, tail ++ that)
    def declarationsUntyped: List[Declaration.Untyped]       = head :: tail.declarationsUntyped
  }

  val empty: DeclarationSet                                   = Empty
  def singleton[A](declaration: Declaration[A]): Singleton[A] = Cons(declaration, Empty)
}

object :*: {
  def unapply[A, B](tuple: (A, B)): Some[(A, B)] = Some(tuple)
}
