package model.product;

/**
 * Represents a product that can be shipped, extending the Product class.
 * Implements the Shippable interface to provide weight functionality.
 */
public class ShippableProduct extends Product implements Shippable{
    private double weight;

    /**
     * Constructs a ShippableProduct with the specified name, price, quantity, and weight.
     *
     * @param name     the name of the product
     * @param price    the price of the product
     * @param quantity the quantity of the product
     * @param weight   the weight of the product
     * @throws IllegalArgumentException if weight is less than or equal to zero
     */
    public ShippableProduct(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        setWeight(weight);
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
     * @throws IllegalArgumentException if weight is less than or equal to zero
     */
    @Override
    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero.");
        }
        this.weight = weight;
    }
}
