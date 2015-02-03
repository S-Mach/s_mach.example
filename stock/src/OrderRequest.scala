package stock.src

/**
 * Trait representing the ability to process orders
 */
trait OrderRequest{
  /**
   * Processes orders
   * @param order Attempts to fulfill passed Order
   */
  def orderRequest(order: Order): Unit ={}
}

