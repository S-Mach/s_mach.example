package s_mach.example.stock

import org.scalatest.{Matchers, FlatSpec}

class OrderBookTest extends FlatSpec with Matchers {

  //adding removing
  "OrderBook" should "construct orders correctly" in {
    val amzn = OrderBook("AMZN")
    assert(amzn.symbol == "AMZN")

    val buyOrder = new BuyOrder("AMZN", 50, 5)
    val buyOrder2 = new BuyOrder("AMZN", 40, 15)
    //PQ ordering and priority
    amzn.processOrder(buyOrder)
    amzn.processOrder(buyOrder2)
    amzn.getBuyers match {
      case Some(arr) => {
        assert(arr(0).price==50)
        assert(arr(0).qty==5)
        assert(arr(1).price==40)
        assert(arr(1).qty==15)
        assert(arr.length == 2)
      }
      case None => assert(false)
    }

    val sellOrder = new SellOrder("AMZN", 60, 10)
    amzn.processOrder(sellOrder)
    amzn.getSellers match {
      case Some(arr) => {
        assert(arr(0).price==60)
        assert(arr(0).qty==10)
        assert(arr.length == 1)
      }
      case None => assert(false)
    }
  }
  //match testing
}