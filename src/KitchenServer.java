/**
 * Receives a new order and should init the cooking process.
 * Use a threadpool to represent the kitchen and submit a dummy task that
 * just sleeps and modifies the status of an order.
 * Juse a seprate thread pool for cooking in the Kitchen Server.
 * Add random sleep intervals to KitchenServer methods to represent network delay.
 * You may use a CompletableFuture object to set the return value of async with the complete()-method.
 * Use newFixedThreadPool(). myPool.submit(task) to submit a task for execution.
 */
public class KitchenServer extends AbstractKitchenServer {


}
