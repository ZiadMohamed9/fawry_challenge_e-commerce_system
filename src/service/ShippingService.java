package service;

import model.product.Shippable;

import java.util.Map;

/**
 * ShippingService is responsible for calculating the shipping cost of shippable items
 * and handling the shipping process.
 */
public class ShippingService {
    private final Map<Shippable, Integer> shippableItems;

    /**
     * Constructs a ShippingService with a map of shippable items and their quantities.
     *
     * @param shippableItems a map where keys are shippable items and values are their quantities
     * @throws IllegalArgumentException if the shippableItems map is null or empty
     */
    public ShippingService(Map<Shippable, Integer> shippableItems) {
        if (shippableItems == null) {
            throw new IllegalArgumentException("Shippable items cannot be null or empty.");
        }
        this.shippableItems = shippableItems;
    }

    /**
     * Calculates the total shipping cost based on the weight of each shippable item.
     *
     * @return the total shipping cost
     */
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

    /**
     * Ships the items by printing their details and the total shipping cost.
     */
    public void shipItems() {
        for (var entry : shippableItems.entrySet()) {
            Shippable item = entry.getKey();
            int quantity = entry.getValue();
            System.out.println("Shipping item: " + item.getName() +
                    " with weight: " + item.getWeight() + "kg"
                    + " - Quantity: " + quantity);
        }
        System.out.println("Total shipping cost: $" + calculateShippingCost());
    }
}
