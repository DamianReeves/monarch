package monarch

sealed trait Field[+A, +Attribs] extends Serializable { self =>
  type Key <: String
}

object Field {

  def define[K <: String & Singleton](key: K): DefinePartiallyApplied[K] = new DefinePartiallyApplied[K](key)

  object Attribute {
    type Default
    type Assignable <: Default
    type Readable <: Default
  }

  private[monarch] final case class AssignableField[K <: String & Singleton, +A, +Attribs](key: K)
      extends Field[A, Attribute.Assignable] {
    type Key = K
  }

  final class DefinePartiallyApplied[K <: String & Singleton](val key: K) extends AnyVal {
    def as[A]: Field[A, Attribute.Assignable] = AssignableField(key)
  }
}
