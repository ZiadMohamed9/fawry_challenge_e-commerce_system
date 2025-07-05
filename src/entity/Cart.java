package entity;

import entity.product.Product;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a shopping cart that holds products and their quantities.
 * Provides methods to add, remove, update products, clear the cart, and check if it's empty.
 */
public class Cart {
    private final Map<Product, Integer> products;
    private double itemsTotalCost;

    /**
     * Constructs an empty cart.
     */
    public Cart() {
        this.products = new HashMap<>();
        this.itemsTotalCost = 0;
    }

    /**
     * Adds a product to the cart with the specified quantity.
     * Throws an exception if the product is null, quantity is less than or equal to zero,
     * or if the requested quantity exceeds available stock.
     *
     * @param product  The product to add.
     * @param quantity The quantity of the product to add.
     */
    public void add(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        int totalQuantity = products.getOrDefault(product, 0) + quantity;
        if (totalQuantity > product.getQuantity()) {
            throw new IllegalArgumentException("Insufficient quantity available for product: " + product.getName());
        }
        products.put(product, totalQuantity);
        itemsTotalCost += product.getPrice() * quantity;

        System.out.println("Added " + quantity + " of " + product.getName() + " to the cart. Current total price: " + itemsTotalCost);
    }

    /**
     * Removes a product from the cart.
     * Throws an exception if the product is null or not found in the cart.
     *
     * @param product The product to remove.
     */
    public void remove(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (!products.containsKey(product)) {
            throw new IllegalArgumentException("Product not found in cart: " + product.getName());
        }

        int quantity = products.get(product);
        itemsTotalCost -= product.getPrice() * quantity;
        products.remove(product);

        System.out.println("Removed " + product.getName() + " from the cart. Current total price: " + itemsTotalCost);
    }

    /**
     * Updates the quantity of a product in the cart.
     * Throws an exception if the product is null, quantity is less than or equal to zero,
     * or if the requested quantity exceeds available stock.
     *
     * @param product  The product to update.
     * @param quantity The new quantity of the product.
     */
    public void updateProductQuantity(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (!products.containsKey(product)) {
            throw new IllegalArgumentException("Product not found in cart: " + product.getName());
        }
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Insufficient quantity available for product: " + product.getName());
        }

        int currentQuantity = products.get(product);
        itemsTotalCost -= product.getPrice() * currentQuantity;
        products.put(product, quantity);
        itemsTotalCost += product.getPrice() * quantity;

        System.out.println("Updated " + product.getName() + " quantity to " + quantity + ". Current total price: " + itemsTotalCost);
    }

    /**
     * Clears the cart, removing all products and resetting the total cost.
     */
    public void clear() {
        products.clear();
        itemsTotalCost = 0;

        System.out.println("Cart cleared. Current total price: " + itemsTotalCost);
    }

    /**
     * Checks if the cart is empty.
     *
     * @return true if the cart is empty, false otherwise.
     */
    public boolean isEmpty() {
        return products.isEmpty();
    }

    /**
     * Returns the products in the cart along with their quantities.
     *
     * @return A map of products and their quantities.
     */
    public Map<Product, Integer> getProducts() {
        return products;
    }

    /**
     * Returns the total cost of items in the cart.
     *
     * @return The total cost of items in the cart.
     */
    public double getItemsTotalCost() {
        return itemsTotalCost;
    }

    /**
     * Returns a string representation of the cart, including products, their quantities, and total price.
     *
     * @return A string representation of the cart.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cart:\n");
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            sb.append(entry.getKey().getName())
              .append(" - Quantity: ")
              .append(entry.getValue())
              .append(", Price: ")
              .append(entry.getKey().getPrice())
              .append("\n");
        }
        sb.append("Total Price: ").append(itemsTotalCost);
        return sb.toString();
    }
}
