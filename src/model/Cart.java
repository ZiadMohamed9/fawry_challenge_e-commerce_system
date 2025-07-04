package model;

import model.product.Product;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private final Map<Product, Integer> products;
    private double itemsTotalCost;

    public Cart() {
        this.products = new HashMap<>();
        this.itemsTotalCost = 0;
    }

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

    public void clear() {
        products.clear();
        itemsTotalCost = 0;

        System.out.println("Cart cleared. Current total price: " + itemsTotalCost);
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public double getItemsTotalCost() {
        return itemsTotalCost;
    }

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
