package monarch

sealed trait Field[+A, +Attribs] extends Serializable { self =>
  type Key <: String
  def key: Key
}

object Field {

  def variable[K <: String & Singleton](key: K): DefinePartiallyApplied[K]   = new DefinePartiallyApplied[K](key)
  def readOnly[K <: String & Singleton](key: K): ReadOnlyPartiallyApplied[K] = new ReadOnlyPartiallyApplied[K](key)

  object Attribute {
    type Default
    type Assignable <: Default
    type Readable <: Default
    type ReadOnly <: Readable
  }

  private[monarch] final case class Var[K <: String & Singleton, +A, +Attribs](key: K)
      extends Field[A, Attribute.Assignable & Attribs] {
    type Key = K
  }

  private[monarch] final case class Val[K <: String & Singleton, +A, +Attribs](key: K)
      extends Field[A, Attribute.ReadOnly & Attribs] {
    type Key = K
  }

  final class DefinePartiallyApplied[K <: String & Singleton](val key: K) extends AnyVal {
    def as[A]: Field[A, Attribute.Assignable & Attribute.Readable] = Var[K, A, Attribute.Readable](key)
  }

  final class ReadOnlyPartiallyApplied[K <: String & Singleton](val key: K) extends AnyVal {
    def as[A]: Field[A, Attribute.ReadOnly] = Val[K, A, Nothing](key)
  }
}
