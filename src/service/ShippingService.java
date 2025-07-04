package service;

import model.product.Shippable;

import java.util.Map;

public class ShippingService {
    private final Map<Shippable, Integer> shippableItems;

    public ShippingService(Map<Shippable, Integer> shippableItems) {
        if (shippableItems == null) {
            throw new IllegalArgumentException("Shippable items cannot be null or empty.");
        }
        this.shippableItems = shippableItems;
    }

    public double calculateShippingCost() {
        double totalCost = 0.0;
        if (shippableItems.isEmpty()) {
            return totalCost;
        }
        for (var entry : shippableItems.entrySet()) {
            Shippable item = entry.getKey();
            int quantity = entry.getValue();
            totalCost += item.getWeight() * 5 * quantity; // Assuming a flat rate of $5 per kg
        }
        return totalCost;
    }

    public void shipItems() {
        for (var entry : shippableItems.entrySet()) {
            Shippable item = entry.getKey();
            int quantity = entry.getValue();
            System.out.println("Shipping item: " + item.getName() + " with weight: " + item.getWeight() + "kg");
        }
        System.out.println("Total shipping cost: $" + calculateShippingCost());
    }
}
