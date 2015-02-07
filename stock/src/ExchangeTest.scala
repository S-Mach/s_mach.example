import stock.src._

object ExchangeTest {

  def main (args: Array[String]) {

    val amzn = new OrderBook("AMZN")
    val msft = new OrderBook("MSFT")
    val appl = new OrderBook("APPL")
    val goog = new OrderBook("GOOG")
    val nasdaq = new Exchange("NASDAQ", amzn, msft, appl, goog)
    System.out.println(nasdaq.exchangeName + " market created.\nContains " + nasdaq.size + " entities: " + nasdaq.entities)

    val amznBuyFor50 = new NewBuyOrder ("AMZN", 50, "cancel")
    val amznBuyFor30 = new NewBuyOrder ("AMZN", 30, "cancel")
    val amznBuyFor100 = new NewBuyOrder ("AMZN", 100, "cancel")

    nasdaq.orderRequest(amznBuyFor50)
    nasdaq.orderRequest(amznBuyFor30)
    nasdaq.orderRequest(amznBuyFor100)

    System.out.println("AMZN bids available: " + nasdaq.validateSymbol("AMZN").buyerQueue.size())

    val cancelPrev = new CancelBuyOrder ("AMZN", 50, "cancel", amznBuyFor50.orderID)

    nasdaq.orderRequest(cancelPrev)

    System.out.println("AMZN bids available: " + nasdaq.validateSymbol("AMZN").buyerQueue.size())

    val amznSellFor30 = new NewSellOrder("AMZN", 30, "cancel")
    nasdaq.orderRequest(amznSellFor30)

    System.out.println("AMZN bids available: " + nasdaq.validateSymbol("AMZN").buyerQueue.size())


  }
}
