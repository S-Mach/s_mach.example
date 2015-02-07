package stock.src

/**
 * This class is used to demonstrate a stock exchange simulation
 * leveraging Scala tools and functionality.
 * @param exchangeName Name of exchange entity. (EX: NASDAQ)
 * @param orderbooks Accepts multiple instances of OrderBook for particular stocks
 *           EX: OrderBook with symbol "MSFT", OrderBook with symbol "AMZN"
 *
 * Ideas for general exchange structure generated from:
 * Resource: http://falconair.github.io/2015/01/05/financial-exchange.html
 */
class Exchange(val exchangeName: String, val orderbooks: OrderBook*) extends OrderRequest {
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
      case NewBuyOrder(order.symbol, order.price, order.cancellationToken) => workingOB.buyerQueue.add(order); System.out.println("Order Placed.")
      case NewSellOrder(order.symbol, order.price, order.cancellationToken) => workingOB.sellerQueue.add(order); System.out.println("Order Placed.")

      case CancelBuyOrder(order.symbol, order.price, order.cancellationToken, order.targetOrderID) => {
        workingOB.buyerQueue.remove(getOrderByID(workingOB.buyerQueue, order.targetOrderID))
        System.out.println("Order Removed.")
      }
      case CancelSellOrder(order.symbol, order.price, order.cancellationToken, order.targetOrderID) => workingOB.sellerQueue.remove(order); System.out.println("Order Removed.")
        workingOB.sellerQueue.remove(getOrderByID(workingOB.sellerQueue, order.targetOrderID))
        System.out.println("Order Removed.")
    }
    workingOB.matchHandler()
  }


  def validateSymbol(symbol: String): OrderBook = {
    for (ob <- orderbooks if ob.symbol == symbol) {
      System.out.println("OrderBook Validated.")
      return ob
    }
    System.out.println("OrderBook: " + symbol + " not found in " + exchangeName)
    null
  }

  def getOrderByID(queue: java.util.PriorityQueue[Order], id: Long): Order ={
    val iterator = queue.iterator()
    while(iterator.hasNext){
      val current = iterator.next()
      if (current.orderID == id){
        return current
      }
    }
    return null
  }

  def entities: Seq[String] = {
    for (ob <- orderbooks) yield ob.symbol
  }
}




