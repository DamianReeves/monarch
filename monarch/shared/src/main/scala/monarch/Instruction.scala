package monarch

sealed trait Instruction[-Accessed, +Changed] { self =>
  import Instruction.*
  def debug: String =
    self match {
      case Assign(target, expr) =>
        s"${target.key} := ${expr.debug}"
    }
}

object Instruction {
  import Field.Attribute

  def assign[A, Attribs, Accessed](target: Field[A, Attribs], expr: Expr[A, Accessed])(implicit
    ev: Attribs <:< Attribute.Assignable
  ): Instruction[Accessed, Field[A, Attribs]] = Assign(target, expr)

  private[monarch] final case class Assign[A, Accessed, Assigned <: Field[A, ?]](
    target: Assigned,
    expr: Expr[A, Accessed]
  ) extends Instruction[Accessed, Assigned]
}

object InstructionUsage {
  def main(args: Array[String]) = {
    val targetField  = Field.mutable("target").as[Int]
    val sourceField  = Field.readOnly("intValue").as[Int]
    val accessExpr   = Expr.access(sourceField)
    val instructions = List(Instruction.assign(targetField, accessExpr))
    //Instruction.assign(sourceField, Expr.access(targetField))
    instructions.zipWithIndex.foreach { case (ins, idx) =>
      println(f"[$idx%04d] ${ins.debug}")
    }
  }
}
