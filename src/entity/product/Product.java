package entity.product;

/**
 * Represents a product with a name, price, and quantity.
 * Provides methods to get and set these attributes with validation.
 */
public class Product {
    private String name;
    private double price;
    private int quantity;

    /**
     * Constructs a Product with the specified name, price, and quantity.
     *
     * @param name     the name of the product
     * @param price    the price of the product
     * @param quantity the quantity of the product
     * @throws IllegalArgumentException if name is null or empty, price is negative, or quantity is negative
     */
    public Product(String name, double price, int quantity) {
        setName(name);
        setPrice(price);
        setQuantity(quantity);
    }

    /**
     * Constructs a Product with default values.
     * Name is set to "Unknown", price to 0.0, and quantity to 0.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name the name to set
     * @throws IllegalArgumentException if name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }
        this.name = name;
    }

    /**
     * Gets the price of the product.
     *
     * @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price the price to set
     * @throws IllegalArgumentException if price is negative
     */
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    /**
     * Gets the quantity of the product.
     *
     * @return the quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product.
     *
     * @param quantity the quantity to set
     * @throws IllegalArgumentException if quantity is negative
     */
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }
}
