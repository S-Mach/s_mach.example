package s_mach.example.stock

import java.util.Comparator
import java.util.PriorityQueue
import scala.util.control._


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
      val seller = sellerQueue.peek()


      if (buyerQueue.peek().price >= seller.price) {

        println("Match Found!")
        val matchedBuyer = getOldestMatch(seller.price)
        matchedBuyer match {
          case Some(buyer) => {
            if (buyer.qty > seller.qty) {
              buyer.qty = buyer.qty - seller.qty
              sellerQueue.poll()
            }
            else if (buyer.qty < seller.qty) {
              seller.qty = seller.qty - buyer.qty
              buyerQueue.remove(buyer)
            }
            else { //they're equal!
              buyerQueue.remove(buyer)
              sellerQueue.poll()
            }
            matchHandler()
          }
          case None => println("No Match Error")
        }

      }
    }

  }

  /**
   * Finds oldest available order match based on Price and desired Queue
   * @param price desired price to match. Match price must be >= price
   * @return Oldest available match
   */
  private def getOldestMatch(price: Double): Option[Order] = {
    val bqCpy = new PriorityQueue[Order](buyerQueue)
    var lowest = bqCpy.peek()
    val loop = new Breaks
    loop.breakable {
      while (bqCpy.peek().price >= price) {

        if (bqCpy.peek().price >= price & bqCpy.peek().timestamp < lowest.timestamp) {
          lowest = bqCpy.peek()
        }
        bqCpy.poll()
        if ( bqCpy.isEmpty ) loop.break()
      }
    }
    if(lowest.price >= price ) {Some(lowest)}
    else None
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
      res = res + position + ": $" + curr.price + " ," + "Shares: " + curr.qty + ", Time: " + curr.timestamp + "\n"
      position += 1
    }
    res = res + "Sellers: \n"
    position = 1
    while(!seller.isEmpty){
      curr = seller.poll()
      res = res + position + ": $" + curr.price + " ," + "Shares: " + curr.qty + ", Time: " + curr.timestamp + "\n"
      position += 1
    }

    return res
  }
}

