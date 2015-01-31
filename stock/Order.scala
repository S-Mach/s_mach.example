
abstract class OrderBookRequest
case class NewOrder(timestamp: Long, tradeID: String, symbol: String, qty: Long, isBuy: Boolean, price: Option[Double]) extends OrderBookRequest
case class Cancel(timestamp: Long, order: NewOrder) extends OrderBookRequest
case class Amend(timestamp: Long, order:NewOrder, newPrice:Option[Double], newQty:Option[Long]) extends OrderBookRequest

abstract class OrderBookResponse
case class Filled(timestamp: Long, price: Double, qty: Long, order: Array[NewOrder]) extends OrderBookResponse
case class Acknowledged(timestamp: Long, request: OrderBookRequest) extends OrderBookResponse
case class Rejected(timestamp: Long, error: String, request: OrderBookRequest) extends OrderBookResponse
case class Canceled(timestamp: Long, reason: String, order: NewOrder) extends OrderBookResponse

abstract class MarketDataEvent
case class LastSalePrice(timestamp: Long, symbol: String, price: Double, qty: Long, volume: Long) extends MarketDataEvent
case class BBOChange(timestamp: Long, symbol: String, bidPrice:Option[Double], bidQty:Option[Long], offerPrice:Option[Double], offerQty:Option[Long]) extends MarketDataEvent

class OrderBook(symbol: String) {

  case class Order(timestamp: Long, tradeID: String, symbol: String, var qty: Long, isBuy: Boolean, var price: Option[Double], newOrderEvent: NewOrder)

}

