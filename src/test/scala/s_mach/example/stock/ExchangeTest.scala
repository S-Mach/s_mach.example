package s_mach.example.stock

import org.scalatest.{Matchers, FlatSpec}

class ExchangeTest extends FlatSpec with Matchers {

  "OrderBook" should "construct an order correctly" in {
    val amzn = new OrderBook("AMZN")
    assert(amzn.symbol == "AMZN")

//    amzn.symbol should equal("AMZN")
//
  }

  "Exchange" should "construct an order correctly" in {
    val amzn = new OrderBook("AMZN")
    val msft = new OrderBook("MSFT")
    val appl = new OrderBook("APPL")
    val goog = new OrderBook("GOOG")
    val nasdaq = Exchange("NASDAQ", amzn, msft, appl, goog)
  }

//  def main (args: Array[String]) {
//
//    val amzn = new OrderBook("AMZN")
//    val msft = new OrderBook("MSFT")
//    val appl = new OrderBook("APPL")
//    val goog = new OrderBook("GOOG")
//    val nasdaq = new Exchange("NASDAQ", amzn, msft, appl, goog)
//    println(nasdaq.exchangeName + " market created.\nContains " + nasdaq.size + " entities: " + nasdaq.entities)
//
//    val amznBuyFor50 = new BuyOrder ("AMZN", 50, "cancel")
//    val amznBuyFor30 = new BuyOrder ("AMZN", 30, "cancel")
//    val amznBuyFor100 = new BuyOrder ("AMZN", 100, "cancel")
//
//    val amznSellFor300 = new SellOrder ("AMZN", 300, "cancel")
//    val amznSellFor1000 = new SellOrder ("AMZN", 1000, "cancel")
//
//    nasdaq.orderRequest(amznBuyFor50)
//    nasdaq.orderRequest(amznBuyFor30)
//    nasdaq.orderRequest(amznBuyFor100)
//    nasdaq.orderRequest(amznSellFor300)
//    nasdaq.orderRequest(amznSellFor1000)
//
//    println(nasdaq.validateSymbol("AMZN").toString)
//
//    val cancelPrev = new CancelBuyOrder ("AMZN", 50, "cancel", amznBuyFor50.orderID)
//
//    nasdaq.orderRequest(cancelPrev)
//
//    println(nasdaq.validateSymbol("AMZN").toString)
//
//    val amznSellFor30 = new SellOrder("AMZN", 30, "cancel")
//    val amznSellFor150 = new SellOrder("AMZN", 150)
//    nasdaq.orderRequest(amznSellFor30)
//    nasdaq.orderRequest(amznSellFor150)
//
//    println(nasdaq.validateSymbol("AMZN").toString)
//  }
}