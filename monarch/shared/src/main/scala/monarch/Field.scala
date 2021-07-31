package monarch

sealed trait Field[+A, +Attribs] extends Serializable { self =>
  import Field.*
  type Key <: String
  def key: Key

  def :=[A1 >: A, F](expr: Expr[A1, F])(implicit
    ev: Attribs <:< Attribute.Assignable
  ): Instruction[F, Field[A1, Attribs]] = assign(expr)

  def assign[A1 >: A, F](expr: Expr[A1, F])(implicit
    ev: Attribs <:< Attribute.Assignable
  ): Instruction[F, Field[A1, Attribs]] =
    Instruction.assign(self, expr)
}

object Field {

  def immutable[K <: String & Singleton](key: K): ReadOnlyPartiallyApplied[K] = new ReadOnlyPartiallyApplied[K](key)
  def mutable[K <: String & Singleton](key: K): DefinePartiallyApplied[K]     = new DefinePartiallyApplied[K](key)
  def readOnly[K <: String & Singleton](key: K): ReadOnlyPartiallyApplied[K]  = new ReadOnlyPartiallyApplied[K](key)

  object Attribute {
    type Default
    type Assignable <: Default
    type Readable <: Default
    type ReadOnly <: Readable
  }

  private[monarch] final case class Mutable[K <: String & Singleton, +A, +Attribs](key: K)
      extends Field[A, Attribute.Assignable & Attribs] {
    type Key = K
  }

  private[monarch] final case class Immutable[K <: String & Singleton, +A, +Attribs](key: K)
      extends Field[A, Attribute.ReadOnly & Attribs] {
    type Key = K
  }

  final class DefinePartiallyApplied[K <: String & Singleton](val key: K) extends AnyVal {
    def as[A]: Field[A, Attribute.Assignable & Attribute.Readable] = Mutable[K, A, Attribute.Readable](key)
  }

  final class ReadOnlyPartiallyApplied[K <: String & Singleton](val key: K) extends AnyVal {
    def as[A]: Field[A, Attribute.ReadOnly] = Immutable[K, A, Nothing](key)
  }
}
