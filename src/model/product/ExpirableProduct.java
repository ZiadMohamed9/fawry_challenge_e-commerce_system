package model.product;

import java.time.LocalDate;

/**
 * Represents a product that has an expiration date.
 */
public class ExpirableProduct extends Product implements Expirable{
    private final LocalDate expirationDate;

    /**
     * Constructs an ExpirableProduct with the specified name, price, quantity, and expiration date.
     *
     * @param name           the name of the product
     * @param price          the price of the product
     * @param quantity       the quantity of the product
     * @param expirationDate the expiration date of the product
     */
    public ExpirableProduct(String name, double price, int quantity, LocalDate expirationDate) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
    }

    /**
     * Returns the expiration date of the product.
     *
     * @return the expiration date of the product
     */
    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    /**
     * Checks if the product is expired.
     * A product is considered expired if the current date is after its expiration date.
     *
     * @return true if the product is expired, false otherwise
     */
    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }
}
