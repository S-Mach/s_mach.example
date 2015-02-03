package stock.src

/**
 * This class is used to demonstrate a stock exchange simulation
 * leveraging Scala tools and functionality.
 * @param name Name of exchange entity. (EX: NASDAQ)
 * @param orderbooks Accepts multiple instances of OrderBook for particular stocks
 *           EX: OrderBook with symbol "MSFT", OrderBook with symbol "AMZN"
 *
 */
class Exchange(name: String, orderbooks: OrderBook) extends OrderRequest {
  /**
   * Collection of OrderBook objects available for trading within Exchange
   */
  val OBCollection = orderbooks

}




