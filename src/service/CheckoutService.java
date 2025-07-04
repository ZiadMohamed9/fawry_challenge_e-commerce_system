package service;

import model.Cart;
import model.Customer;
import model.product.Expirable;
import model.product.Product;
import model.product.Shippable;

import java.util.HashMap;
import java.util.Map;

public class CheckoutService {
    public static void checkout(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }

        Cart cart = customer.getCart();
        if (cart.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Please add items to the cart before checkout.");
        }

        Map<Shippable, Integer> shippableItems = new HashMap<>();
        for (var entry : cart.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            validateProduct(product, quantity);
            if (product instanceof Shippable shippable) {
                shippableItems.put(shippable, quantity);
            }
        }
        ShippingService shippingService = new ShippingService(shippableItems);
        double shippingFees = shippingService.calculateShippingCost();

        double itemsCost = cart.getItemsTotalCost();

        double totalCost = itemsCost + shippingFees;
        if (totalCost > customer.getBalance()) {
            throw new IllegalArgumentException("Insufficient balance. Total cost: " + totalCost + ", Available balance: " + customer.getBalance());
        }

        if (!shippableItems.isEmpty()) {
            System.out.println("------------- Shipment notice -------------");
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

        System.out.println("------------- Checkout receipt -------------");
        System.out.printf("%-20s %10s%n", "Item", "Total Cost");
        for (var entry : cart.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double itemCost = product.getPrice() * quantity;
            System.out.printf("%-20s %10.2f%n", quantity + "x " + product.getName(), itemCost);
            product.setQuantity(product.getQuantity() - quantity);
        }
        System.out.println("--------------------------------------");
        System.out.printf("%-20s %10.2f%n", "Subtotal", itemsCost);
        System.out.printf("%-20s %10.2f%n", "Shipping", shippingFees);
        System.out.printf("%-20s %10.2f%n", "Amount", totalCost);
        System.out.println("--------------------------------------");

        customer.setBalance(customer.getBalance() - totalCost);
        shippingService.shipItems();
        cart.clear();

        System.out.println("Checkout successful! Remaining balance: " + customer.getBalance());
    }

    private static void validateProduct(Product product, int quantity) {
        if (product.getQuantity() == 0) {
            throw new IllegalArgumentException("Product is out of stock: " + product.getName());
        }
        if (product.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }
        if (product instanceof Expirable expirable) {
            if (expirable.isExpired()) {
                throw new IllegalArgumentException("Product is expired: " + product.getName());
            }
        }
    }
}
