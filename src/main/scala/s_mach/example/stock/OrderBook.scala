package s_mach.example.stock

import java.util.Comparator
import java.util.PriorityQueue


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
      var price = 0 - order1.price.compareTo(order2.price)
      if(price==0){
        price = order1.timestamp.compareTo(order2.timestamp)
      }
      price
    }
  }
  /**
   * sellerComparator sorted by lowest price & lowest timestamp
   */
  val sellerComparator = new Comparator[Order] {
    def compare(order1: Order, order2: Order): Int ={
      var price = order1.price.compareTo(order2.price)
      if(price==0){
        price = order1.timestamp.compareTo(order2.timestamp)
      }

       price
    }
  }
  /**
   * Queue used to organize Buyer Orders
   */
  var buyerQueue = new PriorityQueue[Order](defaultQueueSize, buyerComparator)
  /**
   * Queue used to organize Seller Orders
   */
  var sellerQueue = new PriorityQueue[Order](defaultQueueSize, sellerComparator)

  /**
   * Executes matches in the OrderBook. A match is defined as the instance
   * when the Order in the front most position of the sellerQueue's price
   * is equal to or less than the price of the Order in the front most position
   * of the buyerQueue.
   *
   * Note: Priority goes to OLDEST BuyOrder with a buyer price >= seller price.
   */
  def matchHandler(): Unit = {
    if (!buyerQueue.isEmpty() && !sellerQueue.isEmpty())
    {
      val price = sellerQueue.peek().price
      if (buyerQueue.peek().price >= price) {
        println("Match Found!")
        buyerQueue.remove(getOldestMatch(price, buyerQueue))
        sellerQueue.poll()
        println("Order fulfilled: " + symbol + ", $" + price)
        matchHandler()
      }
    }
  }

  /**
   * Finds oldest available order match based on Price and desired Queue
   * @param price desired price to match. Match price must be >= price
   * @param queue Specific queue to search for match
   * @return Oldest available match
   */
  private def getOldestMatch(price: Double, queue: PriorityQueue[Order]): Order = {
      if(queue.peek().price > price) {
        var lowest: Order = queue.peek()
        var curr: Order = null
        val iterator = queue.iterator()
        while (iterator.hasNext) {
          curr = iterator.next()
          if (curr.price >= price && curr.timestamp < lowest.timestamp) {
            lowest = curr
          }
        }
        lowest
      }
    else null
  }

  /**
   * Returns all OrderBook Orders organized by Buyer/Seller price and timestamp
   * @return String of OrderBook values
   */
  override def toString(): String = {
    val buyer = new PriorityQueue[Order](buyerQueue)
    val seller = new PriorityQueue[Order](sellerQueue)
    var res: String = symbol + ": \n"
    var curr: Order = null
    var position = 1
    res = res + "Buyers: \n"
    while(!buyer.isEmpty){
      curr = buyer.poll()
      res = res + position + ": $" + curr.price + " ," + curr.timestamp + "\n"
      position += 1
    }
    res = res + "Sellers: \n"
    position = 1
    while(!seller.isEmpty){
      curr = seller.poll()
      res = res + position + ": $" + curr.price + " ," + curr.timestamp + "\n"
      position += 1
    }

    return res
  }
}

