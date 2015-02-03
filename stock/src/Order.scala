package stock.src

/**
 * Order represents single instance of order for Exchange transaction.
 */
sealed abstract class Order(){
  /**
   * The unique identifier for any Order
   */
  val orderID = Order.nextID
  /**
   * The symbol distinguishing OrderBook used for trading
   */
  val symbol: String

  val price: Double

  val timestamp: Long = System.currentTimeMillis()
 }

/**
 * Companion object used for ID generation
 */
object Order {
  private var n: Long = 0L

  def nextID = n = n + 1
}

/**
 * Case class of Order representing a new transaction
 * @param symbol distinguishes market symbol for OrderBook trading
 * @param isBuyer Boolean value determining buyer or seller
 * @param price
 * @param cancellationToken String used to verify cancellation requests
 */
case class NewOrder(symbol: String, isBuyer: Boolean, price: Double, cancellationToken: String) extends Order{}

/**
 * Case class of Order representing cancellation of previous Order
 * @param symbol Distinguishes market symbol for OrderBook trading
 * @param targetOrderID The ID of desired cancellation order
 * @param price The current price of target cancellation order
 * @param cancellationToken String used to verify cancellation requests
 */
case class CancelOrder(symbol: String, targetOrderID: Long, price: Double, cancellationToken: String) extends Order{}

