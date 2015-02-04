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

  val isBuyer: Boolean

  val price: Double

  val timestamp: Long = System.currentTimeMillis()

  val cancellationToken: String

  val targetOrderID: Long

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
 * @param isBuyer Boolean value determining buyer or seller
 * @param price
 * @param cancellationToken String used to verify cancellation requests
 */
case class NewOrder(symbol: String, isBuyer: Boolean, price: Double, cancellationToken: String) extends Order { override val targetOrderID = orderID}

/**
 * Case class of Order representing cancellation of previous Order
 * @param symbol Distinguishes market symbol for OrderBook trading
 * @param isBuyer Boolean value determining buyer or seller
 * @param price The current price of target cancellation order
 * @param cancellationToken String used to verify cancellation requests
 * @param targetOrderID The ID of desired cancellation order
 */
case class CancelOrder(symbol: String, isBuyer: Boolean, price: Double, cancellationToken: String, targetOrderID: Long) extends Order

//sealed abstract class Order(){
//  /**
//   * The unique identifier for any Order
//   */
//  val orderID = Order.nextID
//  /**
//   * The symbol distinguishing OrderBook used for trading
//   */
//  val symbol: String
//
//  val price: Double
//
//  val timestamp: Long = System.currentTimeMillis()
//
//  val cancellationToken: String
// }
//