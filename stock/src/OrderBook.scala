package stock.src

import java.util.Comparator
import java.util.Collections
/**
 * This class is used to track orders of a particular stock trading symbol
 * made by interested buyers and sellers
 *
 * Resource: http://en.wikipedia.org/wiki/Order_book_%28trading%29
 */
class OrderBook(val symbol: String) {

  private final val defaultQueueSize = 11

  /**
   * buyerComparator sorted buy highest price & lowest timestamp
   */
  val buyerComparator = new Comparator[Order] {
    def compare(order1: Order, order2: Order): Int ={
      val price = 0 - order1.price.compareTo(order2.price)
      if(price==0){
        return order1.timestamp.compareTo(order2.timestamp)
      }
      price
    }
  }
  /**
   * sellerComparator sorted by lowest price & lowest timestamp
   */
  val sellerComparator = new Comparator[Order] {
    def compare(order1: Order, order2: Order): Int ={
      val price = order1.price.compareTo(order2.price)
      if(price==0){
        return order1.timestamp.compareTo(order2.timestamp)
      }
       price
    }
  }
  /**
   * Queue used to organize Buyer Orders
   */
  var buyerQueue = new java.util.PriorityQueue[Order](defaultQueueSize, buyerComparator)
  /**
   * Queue used to organize Seller Orders
   */
  var sellerQueue = new java.util.PriorityQueue[Order](defaultQueueSize, sellerComparator)

  /**
   * Method used to handle a match, defined as the instance
   * a buyerQueue price aligns with a sellerQueue Price
   */
  def matchHandler(): Unit = {
    if (!buyerQueue.isEmpty() && !sellerQueue.isEmpty())
    {
      if (buyerQueue.peek().equals(sellerQueue.peek())) {
        System.out.println("Match Found!")
        val price = buyerQueue.poll().price
        sellerQueue.poll()
        System.out.println("Order fulfilled: " + symbol + ", $" + price)
        matchHandler()
      }
    }
  }
}

