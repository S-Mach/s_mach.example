package stock.src

/**
 * Order represents single instance of order for Exchange transaction.
 */
sealed trait Order {
  /**
   * The unique identifier for any Order
   */
  val orderID: Long = Order.nextID
  /**
   * The symbol distinguishing OrderBook used for trading
   */
  val symbol: String

  val price: Double

  val timestamp: Long = System.currentTimeMillis()

  val cancellationToken: String

  val targetOrderID: Long

  def equals(order2: Order): Boolean = {
    System.out.println("Comparing: "+ this.symbol + ", " + this.price + " vs. " + order2.symbol + ", " + order2.price)
    if(this.symbol == order2.symbol && this.price == order2.price){
      true
    }
    else{
      System.out.println("not equal")
      false

    }
  }
}

/**
* Companion object used for ID generation
*/
object Order {
  private var n: Long = 0L

  def nextID: Long = {
    n += 1
    n
  }
}
/**
 * Case class of Order representing a new transaction
 * @param symbol distinguishes market symbol for OrderBook trading
 * @param price
 * @param cancellationToken String used to verify cancellation requests
 */
case class NewBuyOrder(symbol: String, price: Double, cancellationToken: String = "cancel") extends Order { override val targetOrderID = orderID}

/**
 * Case class of Order representing a new transaction
 * @param symbol distinguishes market symbol for OrderBook trading
 * @param price
 * @param cancellationToken String used to verify cancellation requests
 */
case class NewSellOrder(symbol: String, price: Double, cancellationToken: String = "cancel") extends Order { override val targetOrderID = orderID}
/**
 * Case class of Order representing cancellation of previous Order
 * @param symbol Distinguishes market symbol for OrderBook trading
 * @param price The current price of target cancellation order
 * @param cancellationToken String used to verify cancellation requests
 * @param targetOrderID The ID of desired cancellation order
 */
case class CancelBuyOrder(symbol: String, price: Double, cancellationToken: String, targetOrderID: Long) extends Order

/**
 * Case class of Order representing cancellation of previous Order
 * @param symbol Distinguishes market symbol for OrderBook trading
 * @param price The current price of target cancellation order
 * @param cancellationToken String used to verify cancellation requests
 * @param targetOrderID The ID of desired cancellation order
 */
case class CancelSellOrder(symbol: String, price: Double, cancellationToken: String, targetOrderID: Long) extends Order
