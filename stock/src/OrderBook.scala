package stock.src

import java.util.Comparator

/**
 * This class is used to track orders of a particular stock trading symbol
 * made by interested buyers and sellers
 *
 * Resource: http://en.wikipedia.org/wiki/Order_book_%28trading%29
 */
class OrderBook(val symbol: String) {

  /**
   * Queue used to organize Buyer Orders
   */
  var buyerQueue = new java.util.PriorityQueue[Order]()
  /**
   * Queue used to organize Seller Orders
   */
  var sellerQueue = new java.util.PriorityQueue[Order]()

  /**
   * Method used to handle a match, defined as the instance
   * a buyerQueue price aligns with a sellerQueue Price
   */
  def matchHandler(): Unit = {

  }
}

