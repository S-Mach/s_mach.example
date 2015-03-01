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

  // TODO: isn't this just orderbooks.size?
  /**
   * Number of Entities within Exchange
   */
  def size : Int

  // TODO: when naming side-effectful methods use a verb first e.g.
  // TODO: handleOrderRequest
  // TODO: can this operation fail? If so, what information would be helpful to
  // TODO: caller when there is a failure?
  /**
   * Processes orders
   * @param order Attempts to fulfill passed Order
   */
  def orderRequest(order: Order): Unit

  // TODO: a validate method typically returns Boolean or a list of errors
  // TODO: Maybe a simpler interface to have orderbooks return
  // TODO: Map[Symbol,OrderBook]? Can consolidate orderbooks, size,
  // TODO: validateSymbol and entities
  /**
   * Checks for existing OrderBook in orderbooks collection based on symbol
   * @param symbol OrderBook symbol
   * @return OrderBook match or null if non-existent
   */
  def validateSymbol(symbol: String): Option[OrderBook]

  // TODO: is an 'entity' the same as a stock symbol?
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