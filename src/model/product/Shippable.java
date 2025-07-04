package model.product;

/**
 * Interface representing a shippable product.
 * It provides methods to get the name and weight of the product,
 * as well as to set the weight.
 */
public interface Shippable {
    /**
     * Gets the name of the product.
     *
     * @return the name of the product
     */
    String getName();

    /**
     * Gets the weight of the product.
     *
     * @return the weight of the product
     */
    double getWeight();

    /**
     * Sets the weight of the product.
     *
     * @param weight the new weight of the product
     */
    void setWeight(double weight);
}
