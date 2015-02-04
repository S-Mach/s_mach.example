import stock.src._

object ExchangeTest {

  def main (args: Array[String]) {

    val amzn = new OrderBook("AMZN")
    val msft = new OrderBook("MSFT")
    val appl = new OrderBook("APPL")
    val goog = new OrderBook("GOOG")
    val nasdaq = new Exchange("NASDAQ", amzn, msft, appl, goog)
    System.out.println(nasdaq.name + " market created.\nContains " + nasdaq.size + " entities: " + nasdaq.entities)

    val newOrder = new NewOrder ("AMZN", true, 50, "cancel")

    nasdaq.orderRequest(newOrder)
    System.out.println("AMZN offers available: " + nasdaq.validateSymbol("AMZN").buyerQueue.size())

    val cancelPrev = new CancelOrder ("AMZN", true, 50, "cancel", newOrder.orderID)
    nasdaq.orderRequest(cancelPrev)
    System.out.println("AMZN offers available: " + nasdaq.validateSymbol("AMZN").buyerQueue.size())

  }
}
