package monarch

sealed trait Scope[-Variables] {}
//sealed trait RootScope extends Scope
object Scope                   {
  val empty: Scope[Any]  = Empty
  def root(): Scope[Any] = Root()
  final case class Root() extends Scope[Any]
  case object Empty       extends Scope[Any]
}
//final class Scope(private val map: Map[Field[?, ?], AnyRef])
