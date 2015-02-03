/**
 * This class is used to demonstrate a stock exchange simulation
 * leveraging Scala tools and functionality.
 * @param name Name of exchange entity. (EX: NASDAQ)
 * @param ob Accepts multiple instances of OrderBook for particular stocks
 *           EX: OrderBook with symbol "MSFT", OrderBook with symbol "AMZN"
 *
 */
class Exchange (name: String, ob: OrderBook*){}


