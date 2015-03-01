package s_mach.example.stock

/**
 * This class is used to track orders of a particular stock trading symbol
 * made by interested buyers and sellers
 *
 * Resource: http://en.wikipedia.org/wiki/Order_book_%28trading%29
 */
trait OrderBook {
  // TODO: to avoid NPE construction issues, change all of these vals here to
  // TODO: defs. They can still be implemented using vals in derived classes

  val symbol: String

  // TODO: does the method process *all* matches? or just one? I would suggest
  // TODO: making this process just one match and return true if done. This
  // TODO: would allow callers to decide how long to continuously handle matches
  /**
   * Executes matches in the OrderBook. A match is defined as the instance
   * when the Order in the front most position of the sellerQueue's price
   * is equal to or less than the price of the Order in the front most position
   * of the buyerQueue.
   *
   * Note: Priority goes to OLDEST BuyOrder with a buyer price >= seller price.
   */
  def matchHandler()

  // TODO: can this operation fail? If so, what information would be helpful to
  // TODO: caller when there is a failure?
  // TODO: is this the same function as Exchange? If so, should share same name
  def processOrder(order: Order)

  // TODO: I would suggest returning Seq to avoid committing to a particular
  // TODO: collection impl. Also, if None is used to mean that there are no
  // TODO: buyers, then I would suggest just using Array.empty to indicate
  // TODO: are no buyers
  // TODO: In Scala, convention is to drop "get" from the name of getters. So
  // TODO: this is simply "buyers"
  def getBuyers: Option[Array[Order]]

  // TODO: same as above
  def getSellers: Option[Array[Order]]
}

object OrderBook {
  def apply(symbol: String) = new OrderBookImpl(symbol)
}