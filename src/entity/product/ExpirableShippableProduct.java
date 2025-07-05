package entity.product;

import java.time.LocalDate;

/**
 * Represents a product that is both expirable and shippable.
 * This class extends the Product class and implements Expirable and Shippable interfaces.
 */
public class ExpirableShippableProduct extends Product implements Expirable, Shippable{
    private final LocalDate expirationDate;
    private double weight;

/**
     * Constructs an ExpirableShippableProduct with the specified name, price, quantity, expiration date, and weight.
     *
     * @param name the name of the product
     * @param price the price of the product
     * @param quantity the quantity of the product
     * @param expirationDate the expiration date of the product
     * @param weight the weight of the product
     * @throws IllegalArgumentException if weight is less than or equal to zero
     */
    public ExpirableShippableProduct(String name, double price, int quantity, LocalDate expirationDate, double weight) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
        setWeight(weight);
    }

    /**
     * Returns the expiration date of the product.
     *
     * @return the expiration date
     */
    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    /**
     * Returns the name of the product.
     *
     * @return the name of the product
     */
    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    /**
     * Returns the weight of the product.
     *
     * @return the weight of the product
     */
    @Override
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the product.
     *
     * @param weight the weight to set
     * @throws IllegalArgumentException if the weight is less than or equal to zero
     */
    @Override
    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero.");
        }
        this.weight = weight;
    }
}
