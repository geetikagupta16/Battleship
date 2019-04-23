import FieldValue.FieldValue

import scala.collection.{immutable, mutable}

trait BattleGroundOps {

  val width = 5
  val height = 'E'

  def placeShip(shipType: FieldValue, dimension: (Int, Int), posX: Int, posY: Char, battleGround: immutable.Seq[mutable.MutableList[FieldValue]]) = {
    (0 to dimension._2 - 1).foreach { yIndex: Int =>
      val list: mutable.Seq[FieldValue] = battleGround(posY.%(65) + yIndex)
      (0 to dimension._1 - 1).foreach { xIndex =>
        list.update(posX + xIndex - 1, shipType)
      }
    }
  }

  def isHit(xLocation: Int, yLocation: Char, battleGround: immutable.Seq[mutable.MutableList[FieldValue]]) = {
    battleGround(yLocation % 65)(xLocation - 1) match {
      case FieldValue.PShip => battleGround(yLocation % 65).update(xLocation - 1, FieldValue.Destroyed)
        true
      case FieldValue.QShip => battleGround(yLocation % 65).update(xLocation - 1, FieldValue.PartiallyDestroyed)
        true
      case FieldValue.PartiallyDestroyed => battleGround(yLocation % 65).update(xLocation - 1, FieldValue.Destroyed)
        true
      case FieldValue.Destroyed => battleGround(yLocation % 65).update(xLocation - 1, FieldValue.Destroyed)
        false
      case _ => battleGround(yLocation % 65).update(xLocation - 1, FieldValue.Miss)
        false
    }
  }

  def hitMissiles(hitLocations: List[(Char, Int)], player1Hits: List[(Char, Int)], player2Hits: List[(Char, Int)], battleGround: immutable.Seq[mutable.MutableList[FieldValue]],
                  battleGround1: immutable.Seq[mutable.MutableList[FieldValue]],
                  battleGround2: immutable.Seq[mutable.MutableList[FieldValue]], onStrike: String = "1"): immutable.Seq[mutable.MutableList[FieldValue]] = {
    (hitLocations, player1Hits, player2Hits) match {
      case (head :: tail, _ :: _, _ :: remElements) =>
        val hitStatus = isHit(head._2, head._1, battleGround)
        if (hitStatus && onStrike == "1") {
          hitMissiles(tail, tail, player2Hits, battleGround2, battleGround1, battleGround2, "1")
        } else if (hitStatus && onStrike == "2") {
          hitMissiles(player2Hits, player1Hits, tail, battleGround1, battleGround1, battleGround2, "2")
        } else if (!hitStatus && onStrike == "1") {
          hitMissiles(player2Hits, tail, remElements, battleGround1, battleGround1, battleGround2, "2")
        } else {
          hitMissiles(player1Hits, player1Hits, tail, battleGround2, battleGround1, battleGround2, "1")
        }
      case (head :: tail, _ :: _, Nil) => isHit(head._2, head._1, battleGround)
        hitMissiles(tail, tail, player2Hits, battleGround2, battleGround1, battleGround2, "1")
      case (head :: tail, Nil, _ :: _) => isHit(head._2, head._1, battleGround)
        hitMissiles(tail, player1Hits, tail, battleGround1, battleGround1, battleGround2, "2")
      case (Nil, _, _) => battleGround
    }
  }

  def fillBattleGround = {
    for {_ <- 'A' to height} yield mutable.MutableList.fill[FieldValue](width)(FieldValue.Water)
  }

  def isShipsDestroyed(battleGround: immutable.Seq[mutable.MutableList[FieldValue]]) = {
    battleGround.flatMap(list => list.filter { fieldValue => fieldValue.equals(FieldValue.PShip) || fieldValue.equals(FieldValue.QShip) || fieldValue.equals(FieldValue.PartiallyDestroyed)
    }).toList.isEmpty
  }

  def declareWinner(battleGround1: immutable.Seq[mutable.MutableList[FieldValue]],
                    battleGround2: immutable.Seq[mutable.MutableList[FieldValue]]) = {
    if (isShipsDestroyed(battleGround1)) {
      println("Player 2 is Winner !!")
    } else if (isShipsDestroyed(battleGround2)) {
      println("Player 1 is Winner !!")
    } else {
      println("Winner not decided !!")
    }
  }
}
