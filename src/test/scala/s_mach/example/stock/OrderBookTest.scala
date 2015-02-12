package s_mach.example.stock

import org.scalatest.{Matchers, FlatSpec}

class OrderBookTest extends FlatSpec with Matchers {

  "OrderBook" should "construct an order correctly" in {
    val amzn = OrderBook("AMZN")
    assert(amzn.symbol == "AMZN")
    val buyOrder = new BuyOrder("AMZN", 50, 5)
    //PQ ordering and priority
    amzn.processOrder(buyOrder)
    amzn.getBuyers match {
      case Some(arr) => println(arr(0).price)
      case None => println("error")
    }

    //match testing

    //adding removing

    //

  }
}