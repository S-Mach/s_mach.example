package s_mach.example.stock

import org.scalatest.{Matchers, FlatSpec}

class ExchangeTest extends FlatSpec with Matchers {

  "Exchange" should "construct an exchange correctly" in {
    val amzn = new OrderBook("AMZN")
    val msft = new OrderBook("MSFT")
    val appl = new OrderBook("APPL")
    val goog = new OrderBook("GOOG")

    val nasdaq = Exchange("NASDAQ", amzn, msft, appl, goog)
    assert(nasdaq.exchangeName == "NASDAQ")
    assert(nasdaq.size == 4)
    assert(nasdaq.validateSymbol("TYLR") == None)

  }
  it should "validate symbols, place orders, and fulfill transactions appropriately" in {

    val nasdaq = Exchange("NASDAQ", new OrderBook("AMZN"))

    nasdaq.orderRequest(new BuyOrder("AMZN", 50, 5))


    nasdaq.orderRequest(new SellOrder("AMZN", 60, 100))
    nasdaq.orderRequest(new SellOrder("AMZN", 65, 1000))
    nasdaq.orderRequest(new SellOrder("AMZN", 70, 200))

    nasdaq.orderRequest(new BuyOrder("AMZN", 55, 10))

    nasdaq.validateSymbol("AMZN") match {
      case Some(wb) => {
        assert(wb.buyerQueue.size() == 2)
        assert(wb.sellerQueue.size() == 3)
        println(wb.toString)
      }
      case None => assert(false)
    }

    nasdaq.orderRequest(new BuyOrder("AMZN", 60, 150))
    // Transaction occurs. Buys all $60/100 shares in exchange, still desires 50 shares

    nasdaq.validateSymbol("AMZN") match {
      case Some(wb) => {
        assert(wb.buyerQueue.size() == 3)
        assert(wb.sellerQueue.size() == 2)
        println(wb.toString)
      }
      case None => assert(false)
    }

    nasdaq.orderRequest(new SellOrder("AMZN", 50, 10))
    // Transaction occurs. Sells all $50/100 shares in exchange, should hit oldest price match

    nasdaq.validateSymbol("AMZN") match {
      case Some(wb) => {
        println(wb.toString)
      }
      case None => assert(false)
    }



  }
}
  //order request handling

/**
 * Developer Notes: Tyler Lucas
 *
 * The method oldestMatchHandler in OrderBook is used to find the oldest available buyer
 * with a price that matches the current sell price. This method is successful for all
 * instances of a lower match price with a a lower timestamp that still fulfills seller price
 * EXCEPT for one currently unfixable bug: if both orders are placed within a millisecond of each other,
 * the higher priced order is fulfilled in the transaction REGARDLESS of timestamp. This is due to the limitation
 * of the system timestamp Long. 
 */


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

