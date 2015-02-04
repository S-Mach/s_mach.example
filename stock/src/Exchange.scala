package stock.src

/**
 * This class is used to demonstrate a stock exchange simulation
 * leveraging Scala tools and functionality.
 * @param name Name of exchange entity. (EX: NASDAQ)
 * @param orderbooks Accepts multiple instances of OrderBook for particular stocks
 *           EX: OrderBook with symbol "MSFT", OrderBook with symbol "AMZN"
 *
 */
class Exchange(val name: String, val orderbooks: OrderBook*) extends OrderRequest {
  /**
   * Number of Entities within Exchange
   */
  def size = orderbooks.length


  /**
   * Processes orders
   * @param order Attempts to fulfill passed Order
   */
  def orderRequest(order: Order): Unit ={
    val workingOB = validateSymbol(order.symbol)
    order match {
      case NewOrder(order.symbol, true, order.price, order.cancellationToken) => workingOB.buyerQueue.add(order); System.out.println("Order Created.")
      case NewOrder(order.symbol,false, order.price, order.cancellationToken) => workingOB.sellerQueue.add(order); System.out.println("Order Created.")

      case CancelOrder(order.symbol, true, order.price, order.cancellationToken, order.targetOrderID) => workingOB.buyerQueue.remove(order); System.out.println("Order Removed.")
      case CancelOrder(order.symbol, false, order.price, order.cancellationToken, order.targetOrderID) => workingOB.sellerQueue.remove(order); System.out.println("Order Removed.")
    }
  }

  def validateSymbol(symbol: String): OrderBook = {
    for (ob <- orderbooks if ob.symbol == symbol) {
      return ob
    }
    null
  }

  def entities: Seq[String] = {
    for (ob <- orderbooks) yield ob.symbol
  }
}




