package s_mach.example.stock

import org.scalatest.{Matchers, FlatSpec}

class OrderBookTest extends FlatSpec with Matchers {

  "OrderBook" should "construct an order correctly" in {
    val amzn = new OrderBook("AMZN")
    assert(amzn.symbol == "AMZN")

    //PQ ordering and priority

    //match testing

    //adding removing

    //

  }
}