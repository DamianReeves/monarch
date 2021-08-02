package object monarch {
  type DictField[K <: String & Singleton, +A, +Attribs] = Field[A, Attribs] {
    type Key = K
  }

  val DictField = Field

  type ReadOnlyField[+A, +Attribs] = Field[A, Attribs & Field.Attribute.ReadOnly]
  val ReadOnlyField = Field

  type Variable[+A, +Attribs] = Field[A, Attribs & Field.Attribute.Assignable]
  val Variable = Field
}
