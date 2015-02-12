package s_mach.example.stock

import java.util.Comparator
import java.util.PriorityQueue


/**
 * This class is used to track orders of a particular stock trading symbol
 * made by interested buyers and sellers
 *
 * Resource: http://en.wikipedia.org/wiki/Order_book_%28trading%29
 */
trait OrderBook {

  val symbol: String

  val defaultQueueSize: Int = 11
  /**
   * buyerComparator sorted buy highest price & lowest timestamp
   */
  def buyerComparator: Comparator[Order]
  /**
   * sellerComparator sorted by lowest price & lowest timestamp
   */
  def sellerComparator: Comparator[Order]
  /**
   * Queue used to organize Buyer Orders
   */
  def buyerQueue: PriorityQueue[Order]
  /**
   * Queue used to organize Seller Orders
   */
  def sellerQueue: PriorityQueue[Order]

  /**
   * Executes matches in the OrderBook. A match is defined as the instance
   * when the Order in the front most position of the sellerQueue's price
   * is equal to or less than the price of the Order in the front most position
   * of the buyerQueue.
   *
   * Note: Priority goes to OLDEST BuyOrder with a buyer price >= seller price.
   */
  def matchHandler()
}

object OrderBook {
  def apply(symbol: String) = new OrderBookImpl(symbol)
}