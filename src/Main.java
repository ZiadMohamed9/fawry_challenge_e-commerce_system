import model.Cart;
import model.Customer;
import model.product.ExpirableShippableProduct;
import model.product.Product;
import model.product.ShippableProduct;
import service.CheckoutService;

import java.time.LocalDate;

/**
 * Main class to run the e-commerce application tests.
 * This class contains methods to test customer creation, product creation,
 * cart operations, and the checkout process.
 */
public class Main {
    /**
     * Main method to run the tests.
     * It creates a customer, performs various cart operations,
     * and tests the checkout process.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Test customer creation with various scenarios
        createCustomerTest();

        // Test product creation with various scenarios
        createProductTest();

        // Create a customer
        Customer alice = new Customer("Alice", "alice@example.com", "01000000000", 10.0);
        System.out.println("Customer 1: " + alice.getName() + ", Balance: $" + alice.getBalance());
        System.out.println("_______________________________________________________________________________________");

        Cart aliceCart = alice.getCart();

        // Test cart operations
        addToCartTest(aliceCart);
        aliceCart.clear();

        removeFromCartTest(aliceCart);
        aliceCart.clear();

        updateCartTest(aliceCart);

        // Test checkout process
        checkoutTest(alice);
        successfulCheckoutTest(alice);

    }

    /**
     * Tests a successful checkout process for a customer.
     * It creates products, adds them to the cart, sets a sufficient balance,
     * and performs the checkout operation.
     *
     * @param alice the customer to perform the checkout
     */
    private static void successfulCheckoutTest(Customer alice) {
        // Create some products
        Product cheese = new ExpirableShippableProduct("Cheese", 5.0, 10, LocalDate.of(2025, 12, 31), 1.0);
        Product bread = new ExpirableShippableProduct("Bread", 2.0, 5, LocalDate.of(2025, 12, 31), 0.5);
        Product mobile = new ShippableProduct("Mobile Phone", 200.0, 2, 0.3);
        Product scratchCard = new Product("Scratch Card", 1.0, 100);

        // Add products to the cart
        Cart aliceCart = alice.getCart();
        aliceCart.add(cheese, 2);
        aliceCart.add(bread, 5);
        aliceCart.add(mobile, 1);
        aliceCart.add(scratchCard, 10);
        System.out.println("________________________________________________________________________________________");

        // Set a sufficient balance for checkout
        alice.setBalance(500.0);

        System.out.println("Alice's cart before checkout: " + aliceCart);
        System.out.println("Alice's balance before checkout: $" + alice.getBalance());
        System.out.println("_______________________________________________________________________________________");

        // Perform checkout
        try {
            CheckoutService.checkout(alice);
        } catch (IllegalArgumentException e) {
            System.out.println("Checkout failed: " + e.getMessage());
        } finally {
            System.out.println("_______________________________________________________________________________________");
            System.out.println("Alice's cart after checkout: " + aliceCart);
            System.out.println("Alice's balance after checkout: $" + alice.getBalance());
            System.out.println("_______________________________________________________________________________________");
        }

        // Check product quantities after checkout
        System.out.println("Cheese quantity after checkout: " + cheese.getQuantity());
        System.out.println("Bread quantity after checkout: " + bread.getQuantity());
        System.out.println("Mobile Phone quantity after checkout: " + mobile.getQuantity());
        System.out.println("Scratch Card quantity after checkout: " + scratchCard.getQuantity());
    }

    /**
     * Tests the checkout process with various scenarios including errors.
     * It checks for null customers, empty carts, insufficient quantities,
     * out-of-stock products, expired products, and insufficient balance.
     *
     * @param alice the customer to perform the checkout
     */
    private static void checkoutTest(Customer alice) {
        // Check out null customer
        try {
            CheckoutService.checkout(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("___________________________________");

        // Check out customer with empty cart
        try {
            CheckoutService.checkout(alice);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("___________________________________");

        // Create a product to add to the cart
        Cart aliceCart = alice.getCart();
        Product product = new Product("Test Product", 5.0, 10);

        // Check out with insufficient quantity
        try {
            aliceCart.clear();
            aliceCart.add(product, 5);
            System.out.println(aliceCart);
            product.setQuantity(1);
            CheckoutService.checkout(alice);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(aliceCart);
        System.out.println("___________________________________");

        // Product is out of stock
        try {
            aliceCart.clear();
            product.setQuantity(10);
            aliceCart.add(product, 5);
            System.out.println(aliceCart);
            product.setQuantity(0);
            CheckoutService.checkout(alice);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(aliceCart);
        System.out.println("___________________________________");

        // Expired product
        try {
            ExpirableShippableProduct expiredProduct = new ExpirableShippableProduct("Expired Product", 15.0, 5, LocalDate.of(2023, 1, 1), 2.0);
            aliceCart.clear();
            aliceCart.add(expiredProduct, 2);
            System.out.println(aliceCart);
            CheckoutService.checkout(alice);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(aliceCart);
        System.out.println("___________________________________");

        // Check out with insufficient balance
        try {
            aliceCart.clear();
            product.setQuantity(10);
            aliceCart.add(product, 5);
            System.out.println(aliceCart);
            alice.setBalance(0.0);
            CheckoutService.checkout(alice);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(aliceCart);
        System.out.println("___________________________________");
    }

    /**
     * Tests various cart operations including adding, removing, and updating products.
     * It checks for null products, invalid quantities, and other edge cases.
     *
     * @param aliceCart the cart to perform operations on
     */
    private static void updateCartTest(Cart aliceCart) {
        // Attempt to update a null product
        try {
            aliceCart.updateProductQuantity(null, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Create a valid product
        Product product = new Product("Valid Product", 10.0, 5);

        // Attempt to update a product not in the cart
        try {
            System.out.println(aliceCart);
            aliceCart.updateProductQuantity(product, 2);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Add the product to the cart first, then update it
        try {
            aliceCart.add(product, 3);
            System.out.println(aliceCart);

            // Update the product quantity to a valid amount
            aliceCart.updateProductQuantity(product, 2);
            System.out.println(aliceCart);

            // Attempt to update the product quantity to an invalid amount
            aliceCart.updateProductQuantity(product, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println(aliceCart);
        }

        // Attempt to update the product quantity to a negative amount
        try {
            aliceCart.updateProductQuantity(product, -1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Attempt to update the product quantity to zero
        try {
            aliceCart.updateProductQuantity(product, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Tests the removal of products from the cart.
     * It checks for null products, products not in the cart,
     * and valid removal operations.
     *
     * @param aliceCart the cart to perform removal operations on
     */
    private static void removeFromCartTest(Cart aliceCart) {
        // Attempt to remove a null product
        try {
            aliceCart.remove(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Create a valid product
        Product product = new Product("Valid Product", 10.0, 5);

        // Attempt to remove a product not in the cart
        try {
            System.out.println(aliceCart);
            aliceCart.remove(product);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Add the product to the cart first, then remove it
        try {
            aliceCart.add(product, 3);
            System.out.println(aliceCart);

            aliceCart.remove(product);
            System.out.println(aliceCart);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Tests adding products to the cart with various scenarios.
     * It checks for null products, invalid quantities, and valid additions.
     *
     * @param aliceCart the cart to perform addition operations on
     */
    private static void addToCartTest(Cart aliceCart) {
        // Add null product
        try {
            aliceCart.add(null, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Create a valid product
        Product product = new Product("Valid Product", 10.0, 5);

        // Add product with negative quantity
        try {
            aliceCart.add(product, -1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Add product with zero quantity
        try {
            aliceCart.add(product, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Add product with insufficient quantity
        try {
            aliceCart.add(product, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Add product with valid quantity, then add more quantity
        try {
            aliceCart.add(product, 5);
            System.out.println(aliceCart);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Add over the available quantity
        try {
            aliceCart.add(product, 3);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Tests the creation of products with various scenarios.
     * It checks for invalid product names, prices, quantities,
     * and weights for shippable products.
     */
    private static void createProductTest() {
        // Invalid product name
        try {
            Product invalidProduct = new Product(" ", 10.0, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Invalid price
        try {
            Product invalidProduct = new Product("Valid Product", -10.0, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Invalid quantity
        try {
            Product invalidProduct = new Product("Valid Product", 10.0, -5);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Invalid weight for a shippable product
        try {
            ShippableProduct invalidShippableProduct = new ShippableProduct("Shippable Product", 20.0, 5, -1.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Invalid weight for an expirable shippable product
        try {
            ExpirableShippableProduct invalidExpirableShippableProduct = new ExpirableShippableProduct("Expirable Shippable Product", 30.0, 5, LocalDate.of(2025, 12, 1), -1.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Tests the creation of customers with various scenarios.
     * It checks for invalid names, emails, phone numbers, and balances.
     */
    static void createCustomerTest() {
        // Invalid name
        try {
            Customer invalidCustomer = new Customer("", "person@example.com", "01000000000", 1000.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Invalid email
        try {
            Customer invalidCustomer = new Customer("Person", "invalid-email", "01000000000", 1000.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Invalid phone number
        try {
            Customer invalidCustomer = new Customer("Person", "person@example.com", "0100000", 1000.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Negative balance
        try {
            Customer invalidCustomer = new Customer("Person", "person@example.com", "01000000000", -1000.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}