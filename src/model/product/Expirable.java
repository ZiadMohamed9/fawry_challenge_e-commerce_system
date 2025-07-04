package model.product;

import java.time.LocalDate;

/**
 * Interface for products that have an expiration date.
 */
public interface Expirable {
    /**
     * Gets the expiration date of the product.
     *
     * @return the expiration date
     */
    LocalDate getExpirationDate();

    /**
     * Checks if the product is expired.
     *
     * @return true if the product is expired, false otherwise
     */
    boolean isExpired();
}
