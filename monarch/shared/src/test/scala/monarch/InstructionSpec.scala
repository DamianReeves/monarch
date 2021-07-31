package monarch

import zio.test.*
import zio.test.Assertion.*

object InstructionSpec extends DefaultRunnableSpec {
  def spec = suite("Instruction Spec")(
    test("It should be possible to assign to an assignable field") {
      val assignableField = Field.mutable("assignable").as[BigDecimal]
      val actual          = assignableField := Expr.succeed(BigDecimal(0))
      assert(actual.debug)(equalTo("assignable := 0"))
    }
  )
}
