package s_mach.example.stock

/**
 * This class is used to track orders of a particular stock trading symbol
 * made by interested buyers and sellers
 *
 * Resource: http://en.wikipedia.org/wiki/Order_book_%28trading%29
 */
trait OrderBook {

  val symbol: String
  /**
   * Executes matches in the OrderBook. A match is defined as the instance
   * when the Order in the front most position of the sellerQueue's price
   * is equal to or less than the price of the Order in the front most position
   * of the buyerQueue.
   *
   * Note: Priority goes to OLDEST BuyOrder with a buyer price >= seller price.
   */
  def matchHandler()

  def processOrder(order: Order)

  def getBuyers: Option[Array[Order]]

  def getSellers: Option[Array[Order]]
}

object OrderBook {
  def apply(symbol: String) = new OrderBookImpl(symbol)
}