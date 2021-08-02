package monarch

import zio.test.*

object DeclarationSetSpec extends DefaultRunnableSpec {
  def spec = suite("DeclarationSet Spec")(
    test("An Empty DeclarationSet should actually be empty") {
      val actual = DeclarationSet.empty
      assertTrue(actual.declarationsUntyped.isEmpty && actual.declaredFields.isEmpty)
    }
  )
}
