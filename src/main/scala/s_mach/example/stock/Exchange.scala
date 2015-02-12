package s_mach.example.stock

/**
 * This class is used to demonstrate a stock exchange simulation
 * leveraging Scala tools and functionality.
 * exchangeName Name of exchange entity. (EX: NASDAQ)
 * orderbooks Accepts multiple instances of OrderBook for particular stocks
 *           EX: OrderBook with symbol "MSFT", OrderBook with symbol "AMZN"
 *
 * Ideas for general exchange structure generated from:
 * Resource: http://falconair.github.io/2015/01/05/financial-exchange.html
 */
trait Exchange {

  def exchangeName: String
  def orderbooks: Seq[OrderBook]
  /**
   * Number of Entities within Exchange
   */
  def size : Int

  /**
   * Processes orders
   * @param order Attempts to fulfill passed Order
   */
  def orderRequest(order: Order): Unit

  /**
   * Checks for existing OrderBook in orderbooks collection based on symbol
   * @param symbol OrderBook symbol
   * @return OrderBook match or null if non-existent
   */
  def validateSymbol(symbol: String): Option[OrderBook]

  /**
   * Offers all available OrderBook entities within Exchange
   * @return
   */
  def entities: Seq[String]
}

object Exchange {
  def apply(exchangeName: String, orderbooks: OrderBook*) : Exchange =
    new ExchangeImpl(exchangeName, orderbooks:_*)
}