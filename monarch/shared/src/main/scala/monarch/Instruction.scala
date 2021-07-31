package monarch

sealed trait Instruction[-Accessed, +Changed]

object Instruction {
  def assign[A, Attribs, Target <: Field[A, Attribs], Accessed](
    target: Target,
    expr: Expr[A, Accessed]
  ): Instruction[Accessed, Target] = Assign(target, expr)

  private[monarch] final case class Assign[A, Accessed, Assigned <: Field[A, ?]](
    target: Assigned,
    expr: Expr[A, Accessed]
  ) extends Instruction[Accessed, Assigned]
}

object InstructionUsage {
  def example() = {
    val targetField = Field.define("target").as[String]
    ()
  }
}
