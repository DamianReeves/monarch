package monarch

import scala.annotation.implicitNotFound

@implicitNotFound(
  "You are trying to assign to a field whose attributes do not allow assignment. Attributes of the field are: ${Attrib}"
)
sealed trait IsAssignable[-Attrib] {}

object IsAssignable {
  implicit def isAssignable[Attrib](implicit ev: Attrib <:< Field.Attribute.Assignable): IsAssignable[Attrib] =
    new IsAssignable[Attrib] {}
}
