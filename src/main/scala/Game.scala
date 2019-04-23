import FieldValue.FieldValue

import scala.collection.{immutable, mutable}

object Game extends App with BattleGroundOps {

  val battlegroundPlayer1: immutable.Seq[mutable.MutableList[FieldValue]] = fillBattleGround
  val battlegroundPlayer2: immutable.Seq[mutable.MutableList[FieldValue]] = fillBattleGround

  val pShipLocationsPlayer1 = List((4, 'D'))
  val pShipLocationsPlayer2 = List((3, 'C'))
  val qShipLocationsPlayer1 = List((1, 'A'))
  val qShipLocationsPlayer2 = List((2, 'B'))

  pShipLocationsPlayer1.foreach(coord => placeShip(FieldValue.PShip, (2, 1), coord._1, coord._2, battlegroundPlayer1))
  pShipLocationsPlayer2.foreach(coord => placeShip(FieldValue.PShip, (2, 1), coord._1, coord._2, battlegroundPlayer2))
  qShipLocationsPlayer1.foreach(coord => placeShip(FieldValue.QShip, (1, 1), coord._1, coord._2, battlegroundPlayer1))
  qShipLocationsPlayer2.foreach(coord => placeShip(FieldValue.QShip, (1, 1), coord._1, coord._2, battlegroundPlayer2))

  //Place P ships for player 1
  println(s"Player 1 Board:\n${battlegroundPlayer1}")

  //Place P ships for player 2
  println(s"Player 2 Board:\n${battlegroundPlayer2}")

  val player1Hits = List(('A', 1), ('B', 2), ('B', 2), ('B', 3))
  val player2Hits = List(('A', 1), ('B', 2), ('B', 3), ('A',1), ('D',1), ('E',1), ('D',4), ('D',4), ('D',5), ('D',5))

  hitMissiles(player1Hits, player1Hits, player2Hits, battlegroundPlayer2, battlegroundPlayer1, battlegroundPlayer2)
  println(s"Battle ground after hit Player 1\n ${battlegroundPlayer1}")
  println(s"Battle ground after hit Player 2\n ${battlegroundPlayer2}")

  declareWinner(battlegroundPlayer1, battlegroundPlayer2)
}
