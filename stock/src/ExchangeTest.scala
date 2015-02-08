import stock.src._

object ExchangeTest {

  def main (args: Array[String]) {

    val amzn = new OrderBook("AMZN")
    val msft = new OrderBook("MSFT")
    val appl = new OrderBook("APPL")
    val goog = new OrderBook("GOOG")
    val nasdaq = new Exchange("NASDAQ", amzn, msft, appl, goog)
    System.out.println(nasdaq.exchangeName + " market created.\nContains " + nasdaq.size + " entities: " + nasdaq.entities)

    val amznBuyFor50 = new BuyOrder ("AMZN", 50, "cancel")
    val amznBuyFor30 = new BuyOrder ("AMZN", 30, "cancel")
    val amznBuyFor100 = new BuyOrder ("AMZN", 100, "cancel")

    val amznSellFor300 = new SellOrder ("AMZN", 300, "cancel")
    val amznSellFor1000 = new SellOrder ("AMZN", 1000, "cancel")

    nasdaq.orderRequest(amznBuyFor50)
    nasdaq.orderRequest(amznBuyFor30)
    nasdaq.orderRequest(amznBuyFor100)
    nasdaq.orderRequest(amznSellFor300)
    nasdaq.orderRequest(amznSellFor1000)

    System.out.println(nasdaq.validateSymbol("AMZN").toString)

    val cancelPrev = new CancelBuyOrder ("AMZN", 50, "cancel", amznBuyFor50.orderID)

    nasdaq.orderRequest(cancelPrev)

    System.out.println(nasdaq.validateSymbol("AMZN").toString)

    val amznSellFor30 = new SellOrder("AMZN", 30, "cancel")
    val amznSellFor150 = new SellOrder("AMZN", 150)
    nasdaq.orderRequest(amznSellFor30)
    nasdaq.orderRequest(amznSellFor150)

    System.out.println(nasdaq.validateSymbol("AMZN").toString)
  }
}
