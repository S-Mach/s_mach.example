package s_mach.example.stock

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
class ExchangeImpl(val exchangeName: String, val orderbooks: OrderBook*) extends Exchange {
  /**
   * Number of Entities within Exchange
   */
  def size = orderbooks.length

  /**
   * Processes orders
   * @param order Attempts to fulfill passed Order
   */
  def orderRequest(order: Order): Unit = {

      validateSymbol(order.symbol) match {
        case Some(workbook) => {
          order match {
            case buyOrder: BuyOrder => workbook.buyerQueue.add(buyOrder);
              println("Buy Order Placed.")

            case sellOrder: SellOrder => workbook.sellerQueue.add(sellOrder);
              println("Sell Order Placed.")

            case cancelBuyOrder: CancelBuyOrder =>
              workbook.buyerQueue.remove(getOrderByID(workbook.buyerQueue, cancelBuyOrder.targetOrderID))
              println("Buy Order Removed.")

            case cancelSellOrder: CancelSellOrder =>
              workbook.sellerQueue.remove(getOrderByID(workbook.sellerQueue, order.targetOrderID))
              println("Sell Order Removed.")
          }
        workbook.matchHandler()
      }
      case None => println("No symbol found")
    }
  }


  /**
   * Checks for existing OrderBook in orderbooks collection based on symbol
   * @param symbol OrderBook symbol
   * @return OrderBook match or None if non-existent
   */
   def validateSymbol(symbol: String): Option[OrderBook] = {
    for (ob <- orderbooks if ob.symbol == symbol) {
      return Some(ob)
    }
    None
  }

  /**
   * Look up Order by OrderID within particular OrderBook.buyerQueue or OrderBook.sellerQueue
 * @param queue OrderBook.buyerQueue or OrderBook.sellerQueue containing desired Order
   * @param id OrderID desired
   * @return Order based on OrderID
   */
  def getOrderByID(queue: java.util.PriorityQueue[Order], id: Long): Option[Order] = {

    val iterator = queue.iterator()
    while(iterator.hasNext){
      val current = iterator.next()
      if (current.orderID == id){
        return Some(current)
      }
    }
    None
  }

  /**
   * Offers all available OrderBook entities within Exchange
   * @return
   */
  def entities: Seq[String] = {
    for (ob <- orderbooks) yield ob.symbol
  }

}