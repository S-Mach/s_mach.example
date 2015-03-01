package s_mach.example.stock

/**
 * Order represents single instance of order for Exchange transaction.
 */
sealed trait Order {
  // TODO: to avoid NPE construction issues, change all of these vals here to
  // TODO: defs. They can still be implemented using vals in derived classes

  // TODO: Instead of a global order id, wouldn't it make more sense for the
  // TODO: order book to assign the order id?
  /**
   * The unique identifier for any Order
   */
  val orderID: Long = Order.nextID

  /**
   * The symbol distinguishing OrderBook used for trading
   */
  val symbol: String
  /**
   * Price of Order
   */
  val price: Double
  /**
   * Quantity of order
   */
  var qty: Int

  val timestamp: Long = System.currentTimeMillis()

  // TODO: just like order id, the order book should probably issue the cancel
  // TODO: token
  // TODO: since only cancel orders will have a cancel token
  /**
   * String used when cancelling particular order.
   * Verifies intent and ownership of order.
   */
  val cancellationToken: String

  // TODO: since the semantics of assigning targetOrderId only makes sense for
  // TODO: certain orders, why not just move the decl to those derived classes?
  /**
   * Variable describing the target of the order.
   * For typical buy and sell orders, targetId == orderId.
   * For cancellation orders, targetId == desired orderId to cancel.
   */
  val targetOrderID: Long


  // TODO: when designing with a base sealed trait, don't feel like you have
  // TODO: to force the root trait to carry fields that don't make sense for all
  // TODO: derived classes. Users can always downcast to discover those fields
  // TODO: for when they make sense.
  // TODO: I recommend keeping only orderId and timestamp in the base trait here
}

/**
* Companion object used for ID generation
*/
object Order {
  // TODO: make me thread safe
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
*  @param qty
 * @param cancellationToken String used to verify cancellation requests
 */
case class BuyOrder(
  symbol: String,
  price: Double,
  // TODO: var in case classes should be avoided whenever possible. Make this is
  // TODO: a val and use the copy method to assign a new qty
  var qty: Int,
  cancellationToken: String = "cancel")
  extends Order { override val targetOrderID = orderID}
/**
 * Case class of Order representing a new transaction
 * @param symbol distinguishes market symbol for OrderBook trading
 * @param price
 * @param qty
 * @param cancellationToken String used to verify cancellation requests
 */
case class SellOrder(
  symbol: String,
  price: Double,
  // TODO: var in case classes should be avoided whenever possible. Make this is
  // TODO: a val and use the copy method to assign a new qty
  var qty: Int,
  cancellationToken: String = "cancel")
  extends Order { override val targetOrderID = orderID}
/**
 * Case class of Order representing cancellation of previous Order
 * @param symbol Distinguishes market symbol for OrderBook trading
 * @param price The current price of target cancellation order
 * @param cancellationToken String used to verify cancellation requests
 * @param targetOrderID The ID of desired cancellation order
 */
case class CancelBuyOrder(
  symbol: String,
  price: Double,
  cancellationToken: String,
  targetOrderID: Long)
  extends Order {var qty = 0}
/**
 * Case class of Order representing cancellation of previous Order
 * @param symbol Distinguishes market symbol for OrderBook trading
 * @param price The current price of target cancellation order
 * @param cancellationToken String used to verify cancellation requests
 * @param targetOrderID The ID of desired cancellation order
 */
case class CancelSellOrder(
 symbol: String,
 price: Double,
 cancellationToken: String,
 targetOrderID: Long)
 extends Order {var qty = 0}


