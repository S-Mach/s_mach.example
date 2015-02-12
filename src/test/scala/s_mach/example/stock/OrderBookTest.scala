package s_mach.example.stock

import org.scalatest.{Matchers, FlatSpec}

class OrderBookTest extends FlatSpec with Matchers {

  "OrderBook" should "construct an order correctly" in {
    val amzn = OrderBook("AMZN")
    assert(amzn.symbol == "AMZN")
    val buyOrder = new BuyOrder("AMZN", 50, 5)
    //PQ ordering and priority
    assert(amzn.addBuyer(buyOrder))
    assert(amzn.addSeller(buyOrder)==false)
    //match testing

    //adding removing

    //

  }
}