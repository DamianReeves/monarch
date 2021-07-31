package object monarch {
  type DictField[K <: String & Singleton, +A, +Attribs] = Field[A, Attribs] {
    type Key = K
  }

  val DictField = Field
}
