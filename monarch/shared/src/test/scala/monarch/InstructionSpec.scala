package monarch

import zio.test.*
import zio.test.Assertion.*

object InstructionSpec extends DefaultRunnableSpec {
  def spec = suite("Instruction Spec")(
    test("It should be possible to assign to an assignable field") {
      val assignableField = Field.mutable("assignable").as[BigDecimal]
      //val readonlyField   = Field.immutable("readOnly").as[BigDecimal]
      val actual          = assignableField := Expr.succeed(BigDecimal(0))
      //readonlyField := Expr.succeed(BigDecimal(0))
      assert(actual.debug)(equalTo("assignable := 0"))

    },
    testM("It should NOT be possible to assign to a read-only variable") {
      val readonlyField = Field.immutable("readOnly").as[BigDecimal]
      assertM(typeCheck("readonlyField := Expr.succeed(BigDecimal(0))"))(isLeft)
    }
  )
}
