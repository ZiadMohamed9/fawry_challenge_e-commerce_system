package service;

import entity.Cart;
import entity.Customer;
import entity.product.Expirable;
import entity.product.Product;
import entity.product.Shippable;
import exception.EmptyCartException;
import exception.ExpiredProductException;
import exception.InsufficientBalanceException;
import exception.InsufficientQuantityException;

import java.util.HashMap;
import java.util.Map;

/**
 * The CheckoutService class handles the checkout process for a customer.
 * It validates the customer's cart, calculates shipping costs, and processes the payment.
 */
public class CheckoutService {
    /**
     * Processes the checkout for a given customer.
     * Validates the customer's cart, calculates shipping fees, and processes the payment.
     *
     * @param customer The customer who is checking out.
     * @throws IllegalArgumentException if the customer is null.
     * @throws EmptyCartException if the customer's cart is empty.
     * @throws InsufficientBalanceException if the customer does not have enough balance to cover the total cost.
     * @throws InsufficientQuantityException if any product in the cart is out of stock or insufficient quantity is available.
     * @throws ExpiredProductException if any product in the cart is expired.
     */
    public static void checkout(Customer customer) {
        // Validate the customer
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }

        // Validate the customer's cart
        Cart cart = customer.getCart();
        if (cart.isEmpty()) {
            throw new EmptyCartException("Cart is empty. Please add items to the cart before checkout.");
        }

        // Create a map to hold shippable items and their quantities and to ship them later
        Map<Shippable, Integer> shippableItems = validateProductsAndGetShippableItems(cart);

        // Validate cart products and get shippable items
        ShippingService shippingService = new ShippingService(shippableItems);

        // Calculate shipping fees based on the shippable items and hence the total cost
        double shippingFees = shippingService.calculateShippingCost(); // Total shipping fees for the shippable items
        double itemsCost = cart.getItemsTotalCost(); // Total cost of items in the cart
        double totalCost = itemsCost + shippingFees; // Total cost including shipping fees

        // Check if the customer has enough balance to cover the total cost
        if (totalCost > customer.getBalance()) {
            throw new InsufficientBalanceException("Insufficient balance. Total cost: " + totalCost + ", Available balance: " + customer.getBalance());
        }

        // Print the shipping notice with item details and total weight
        printShippingNotice(shippableItems);

        // Print the checkout receipt with item details and total costs
        printCheckoutReceipt(cart);

        // Print the checkout summary with item costs, shipping fees, and total cost
        printCheckoutSummary(itemsCost, shippingFees, totalCost);

        // Process the payment and update the customer's balance
        customer.setBalance(customer.getBalance() - totalCost);

        // Ship the items using the ShippingService
        shippingService.shipItems();

        // Clear the cart after a successful checkout
        cart.clear();

        System.out.println("Checkout successful! Remaining balance: " + customer.getBalance());
    }

    /**
     * Prints the checkout summary including item costs, shipping fees, and total cost.
     *
     * @param itemsCost   The total cost of items in the cart.
     * @param shippingFees The total shipping fees for the shippable items.
     * @param totalCost   The total cost including items and shipping fees.
     */
    private static void printCheckoutSummary(double itemsCost, double shippingFees, double totalCost) {
        System.out.printf("%-20s %10.2f%n", "Subtotal", itemsCost);
        System.out.printf("%-20s %10.2f%n", "Shipping", shippingFees);
        System.out.printf("%-20s %10.2f%n", "Amount", totalCost);
        System.out.println("--------------------------------------------");
    }

    /**
     * Prints the checkout receipt with item details and total costs.
     *
     * @param cart The customer's cart containing products and their quantities.
     */
    private static void printCheckoutReceipt(Cart cart) {
        System.out.println("------------- Checkout Receipt -------------");
        System.out.printf("%-20s %10s%n", "Item", "Total Cost");
        for (var entry : cart.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double itemCost = product.getPrice() * quantity;
            System.out.printf("%-20s %10.2f%n", quantity + "x " + product.getName(), itemCost);
            product.setQuantity(product.getQuantity() - quantity);
        }
        System.out.println("--------------------------------------------");
    }

    /**
     * Prints the shipping notice with item details and total weight.
     *
     * @param shippableItems A map of shippable items and their quantities.
     */
    private static void printShippingNotice(Map<Shippable, Integer> shippableItems) {
        if (!shippableItems.isEmpty()) {
            System.out.println("------------- Shipment Notice -------------");
            System.out.printf("%-20s %10s%n", "Item", "Weight(kg)");
        }

        double totalWeight = 0.0;
        for (var entry : shippableItems.entrySet()) {
            Shippable item = entry.getKey();
            int quantity = entry.getValue();
            double weight = item.getWeight() * quantity;
            totalWeight += weight;
            System.out.printf("%-20s %10s%n", quantity + "x " + item.getName(), weight + "kg");
        }
        System.out.println("Total package weight: " + totalWeight + "kg");
        System.out.println("--------------------------------------------");
    }

    /**
     * Retrieves shippable items from the cart and validates them.
     *
     * @param cart The customer's cart containing products and their quantities.
     * @return A map of shippable items and their quantities.
     * @throws InsufficientQuantityException if any product is out of stock or insufficient quantity is available.
     * @throws ExpiredProductException if any product is expired.
     */
    private static Map<Shippable, Integer> validateProductsAndGetShippableItems(Cart cart) {
        Map<Shippable, Integer> shippableItems = new HashMap<>();
        for (var entry : cart.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            validateProduct(product, quantity);
            if (product instanceof Shippable shippable) {
                shippableItems.put(shippable, quantity);
            }
        }
        return shippableItems;
    }

    /**
     * Validates a product to ensure it is in stock, has sufficient quantity, and is not expired.
     *
     * @param product  The product to validate.
     * @param quantity The quantity of the product being purchased.
     * @throws InsufficientQuantityException if the product is out of stock or insufficient quantity is available.
     * @throws ExpiredProductException if the product is expired.
     */
    private static void validateProduct(Product product, int quantity) {
        if (product.getQuantity() == 0) {
            throw new InsufficientQuantityException("Product is out of stock: " + product.getName());
        }
        if (product.getQuantity() < quantity) {
            throw new InsufficientQuantityException("Insufficient stock for product: " + product.getName());
        }
        if (product instanceof Expirable expirable) {
            if (expirable.isExpired()) {
                throw new ExpiredProductException("Product is expired: " + product.getName());
            }
        }
    }
}
