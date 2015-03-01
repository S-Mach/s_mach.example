package s_mach.example.stock

import java.util.{Comparator, PriorityQueue}

import scala.util.control._


/**
 * This class is used to track orders of a particular stock trading symbol
 * made by interested buyers and sellers
 *
 * Resource: http://en.wikipedia.org/wiki/Order_book_%28trading%29
 */
class OrderBookImpl(val symbol: String) extends OrderBook {

  val defaultQueueSize: Int = 11
  /**
   * buyerComparator sorted buy highest price & lowest timestamp
   */
  // TODO: to use Scala PriorityQueue, these need to be converted to Ordering
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

  // TODO: make buyerQueue and sellerQueue vals. While the queues themselves are
  // TODO: mutable, this impl doesn't need to create new collections after
  // TODO: construction
  /**
   * Queue used to organize Buyer Orders
   */
  // TODO: use Scala PriorityQueue instead of Java http://www.scala-lang.org/api/2.11.5/index.html#scala.collection.mutable.PriorityQueue
  private var buyerQueue = new PriorityQueue[Order](defaultQueueSize, buyerComparator)
  /**
   * Queue used to organize Seller Orders
   */
  private var sellerQueue = new PriorityQueue[Order](defaultQueueSize, sellerComparator)

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

  def processOrder(order: Order) = {
    order match{
      case buyOrder: BuyOrder => buyerQueue.add(buyOrder)
      case sellOrder: SellOrder => sellerQueue.add(sellOrder)
      case cancelBuyOrder: CancelBuyOrder => {
        buyerQueue.remove(getOrderByID(buyerQueue, cancelBuyOrder.targetOrderID))
      }
      case cancelSellOrder: CancelSellOrder => {
        sellerQueue.remove(getOrderByID(sellerQueue, cancelSellOrder.targetOrderID))
      }
    }
  }


  def getBuyers : Option[Array[Order]] = {
    if(buyerQueue.isEmpty){
      None
    }
    else {
      // TODO: to create a defensive copy should be able to use:
      // TODO: import scala.collection.JavaConverters._
      // TODO: buyerQueue.iterator.asScala.toArray
      // TODO: though once these are Scala collections you can just use .toArray
      val bqCpy = new PriorityQueue[Order](buyerQueue)
      val buyers = new Array[Order](bqCpy.size())
      var x = 0

      while(!bqCpy.isEmpty) {
        buyers(x) = bqCpy.poll()
        x+=1
      }
      Some(buyers)
    }
  }
  def getSellers : Option[Array[Order]] = {
    if(sellerQueue.isEmpty){
      None
    }
    else {
      // TODO: same as above
      val sqCpy = new PriorityQueue[Order](sellerQueue)
      val sellers = new Array[Order](sqCpy.size())
      var x = 0
      while(!sqCpy.isEmpty) {
        sellers(x) = sqCpy.poll()
        x+=1
      }
      Some(sellers)
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
    // TODO: rewrite as recursive method to remove breaks (idiomatic Scala uses
    // TODO: no breaks)
    val loop = new Breaks
    loop.breakable {
      while (bqCpy.peek().price >= price) {

        if (bqCpy.peek().price >= price
          & bqCpy.peek().timestamp < lowest.timestamp) {
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
   * Look up Order by OrderID within particular OrderBook.buyerQueue or OrderBook.sellerQueue
   * @param queue OrderBook.buyerQueue or OrderBook.sellerQueue containing desired Order
   * @param id OrderID desired
   * @return Order based on OrderID
   */
  private def getOrderByID(
    // TODO: use Scala PriorityQueue
    queue: java.util.PriorityQueue[Order],
    id: Long): Option[Order] = {
    // TODO: once using Scala PriorityQueue, searching for element becomes
    // TODO: trivial (queue.find(_.orderId == id)) so this method may not be
    // TODO: needed
    val iterator = queue.iterator()
    while(iterator.hasNext){
      val current = iterator.next()
      if (current.orderID == id){
        return Some(current)
      }
    }
    None
  }
  /**
   * Returns all OrderBook Orders organized by Buyer/Seller price and timestamp
   * @return String of OrderBook values
   */
  override def toString(): String = {
    val buyer = new PriorityQueue[Order](buyerQueue)
    val seller = new PriorityQueue[Order](sellerQueue)
    // TODO: when building strings, most efficient way is to use StringBuilder
    var res: String = symbol + ": \n"
    var curr: Order = null
    var position = 12
    res = res + "Buyers: \n"
    // TODO: convert these to map (or foreach append to StringBuilder)
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

    // TODO don't need return
    return res
  }
}
