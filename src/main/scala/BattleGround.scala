
object FieldValue extends Enumeration {
  type FieldValue = Value
  val PShip = Value("P")
  val QShip = Value("Q")
  val Water = Value("W")
  val PartiallyDestroyed = Value("PD")
  val Destroyed = Value("D")
  val Miss = Value("M")
}