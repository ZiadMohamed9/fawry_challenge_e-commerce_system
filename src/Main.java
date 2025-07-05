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
        printLongLine();

        Cart aliceCart = alice.getCart();

        // Test cart operations
        addToCartTest(aliceCart);
        aliceCart.clear();
        printLongLine();

        removeFromCartTest(aliceCart);
        aliceCart.clear();
        printLongLine();

        updateCartTest(aliceCart);
        aliceCart.clear();
        printLongLine();

        // Test checkout process
        checkoutTest(alice);
        aliceCart.clear();

        successfulCheckoutTest(alice);
    }

    /**
     * Tests a successful checkout process for a customer.
     * It creates products, adds them to the cart, sets a sufficient balance,
     * and performs the checkout operation.
     *
     * @param customer the customer to perform the checkout
     */
    private static void successfulCheckoutTest(Customer customer) {
        System.out.println("----------------------Testing successful checkout------------------------------");

        // Create some products
        Product cheese = new ExpirableShippableProduct("Cheese", 5.0, 10, LocalDate.of(2025, 12, 31), 1.0);
        Product bread = new ExpirableShippableProduct("Bread", 2.0, 5, LocalDate.of(2025, 12, 31), 0.5);
        Product mobile = new ShippableProduct("Mobile Phone", 200.0, 2, 0.3);
        Product scratchCard = new Product("Scratch Card", 1.0, 100);

        // Add products to the cart
        Cart aliceCart = customer.getCart();
        aliceCart.add(cheese, 2);
        aliceCart.add(bread, 5);
        aliceCart.add(mobile, 1);
        aliceCart.add(scratchCard, 10);
        printLongLine();

        // Set a sufficient balance for checkout
        customer.setBalance(500.0);

        System.out.println("Alice's cart before checkout: " + aliceCart);
        System.out.println("Alice's balance before checkout: $" + customer.getBalance());
        printLongLine();

        // Perform checkout
        try {
            CheckoutService.checkout(customer);
        } catch (IllegalArgumentException e) {
            System.out.println("Checkout failed: " + e.getMessage());
        } finally {
            printLongLine();
            System.out.println("Alice's cart after checkout: " + aliceCart);
            System.out.println("Alice's balance after checkout: $" + customer.getBalance());
            printLongLine();
        }

        // Check product quantities after checkout
        System.out.println("Cheese quantity after checkout: " + cheese.getQuantity());
        System.out.println("Bread quantity after checkout: " + bread.getQuantity());
        System.out.println("Mobile Phone quantity after checkout: " + mobile.getQuantity());
        System.out.println("Scratch Card quantity after checkout: " + scratchCard.getQuantity());
    }

    /**
     * Tests the checkout process with various scenarios, including errors.
     * It checks for null customers, empty carts, insufficient quantities,
     * out-of-stock products, expired products, and insufficient balance.
     *
     * @param customer the customer to perform the checkout
     */
    private static void checkoutTest(Customer customer) {
        System.out.println("----------------------Testing checkout operations------------------------------");

        // Check out null customer
        try {
            System.out.println("Attempting to checkout with a null customer...");
            CheckoutService.checkout(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printLongLine();

        // Check out customer with empty cart
        try {
            System.out.println("Attempting to checkout with an empty cart...");
            CheckoutService.checkout(customer);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printLongLine();

        // Create a product to add to the cart
        System.out.println("Creating a product to add to the cart...");
        Product product = new Product("Test Product", 5.0, 10);
        System.out.println("Product created: " + product.getName() + ", Price: $" + product.getPrice() + ", Quantity: " + product.getQuantity());
        printLongLine();

        // Fetch the cart for the customer
        Cart aliceCart = customer.getCart();

        // Check out with insufficient quantity
        try {
            System.out.println("Attempting to checkout with insufficient product quantity...");
            printShortLine();

            // First, add the product with a valid quantity to the cart
            aliceCart.clear();
            System.out.println("Adding product to the cart...");
            aliceCart.add(product, 5);

            printCart(aliceCart);
            printShortLine();

            // Update the product quantity to be less than what is in the cart
            System.out.println("Updating product quantity to less than what is in the cart...");
            product.setQuantity(1);

            System.out.println("Checking out...");
            CheckoutService.checkout(customer);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(aliceCart);
        printLongLine();

        // Product is out of stock
        try {
            System.out.println("Attempting to checkout with an out-of-stock product...");
            printShortLine();

            // Reset the product quantity and add it to the cart
            aliceCart.clear();

            System.out.println("Resetting product quantity...");
            product.setQuantity(10);

            System.out.println("Adding product to the cart...");
            aliceCart.add(product, 5);

            printCart(aliceCart);
            printShortLine();

            // Set the product quantity to zero to simulate out of stock
            System.out.println("Setting product quantity to zero...");
            product.setQuantity(0);

            System.out.println("Checking out...");
            CheckoutService.checkout(customer);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(aliceCart);
        printLongLine();

        // Expired product
        try {
            System.out.println("Attempting to checkout with an expired product...");
            printShortLine();

            ExpirableShippableProduct expiredProduct = new ExpirableShippableProduct("Expired Product", 15.0, 5, LocalDate.of(2023, 1, 1), 2.0);
            aliceCart.clear();

            System.out.println("Adding expired product to the cart...");
            aliceCart.add(expiredProduct, 2);

            printCart(aliceCart);
            printShortLine();

            System.out.println("Checking out...");
            CheckoutService.checkout(customer);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(aliceCart);
        printLongLine();

        // Check out with insufficient balance
        try {
            System.out.println("Attempting to checkout with insufficient balance...");
            printShortLine();

            aliceCart.clear();

            System.out.println("Resetting product quantity...");
            product.setQuantity(10);

            System.out.println("Adding product to the cart...");
            aliceCart.add(product, 5);

            printCart(aliceCart);
            printShortLine();

            System.out.println("Setting Alice's balance to zero...");
            customer.setBalance(0.0);

            System.out.println("Checking out...");
            CheckoutService.checkout(customer);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(aliceCart);
        printLongLine();
    }

    /**
     * Tests various cart operations including adding, removing, and updating products.
     * It checks for null products, invalid quantities, and other edge cases.
     *
     * @param cart the cart to perform operations on
     */
    private static void updateCartTest(Cart cart) {
        System.out.println("----------------------Testing update cart operations------------------------------");
        printCart(cart);
        printLongLine();

        // Attempt to update a null product
        try {
            System.out.println("Attempting to update a null product...");
            cart.updateProductQuantity(null, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();

        // Create a valid product
        System.out.println("Creating a valid product...");
        Product product = new Product("Valid Product", 10.0, 5);
        System.out.println("Product created: " + product.getName() + ", Price: $" + product.getPrice() + ", Quantity: " + product.getQuantity());
        printLongLine();

        // Attempt to update a product not in the cart
        try {
            System.out.println("Attempting to update a product not in the cart...");
            cart.updateProductQuantity(product, 2);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();

        // Add the product to the cart first, then update it with over the available quantity
        try {
            System.out.println("Adding product to the cart...");
            cart.add(product, 3);
            printCart(cart);
            printShortLine();

            // Attempt to update the product quantity to an invalid amount
            System.out.println("Attempting to update the product quantity to an invalid amount...");
            cart.updateProductQuantity(product, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();

        // Attempt to update the product quantity to a negative amount
        try {
            System.out.println("Attempting to update the product quantity to a negative amount...");
            cart.updateProductQuantity(product, -1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();

        // Attempt to update the product quantity to zero
        try {
            System.out.println("Attempting to update the product quantity to zero...");
            cart.updateProductQuantity(product, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();

        // Update the product quantity to a valid amount
        try {
            System.out.println("Updating the product quantity to a valid amount...");
            cart.updateProductQuantity(product, 2);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();
    }

    /**
     * Tests the removal of products from the cart.
     * It checks for null products, products not in the cart,
     * and valid removal operations.
     *
     * @param cart the cart to perform removal operations on
     */
    private static void removeFromCartTest(Cart cart) {
        System.out.println("----------------------Testing remove from cart operations------------------------------");
        printCart(cart);
        printLongLine();

        // Attempt to remove a null product
        try {
            System.out.println("Attempting to remove a null product...");
            cart.remove(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();

        // Create a valid product
        System.out.println("Creating a valid product...");
        Product product = new Product("Valid Product", 10.0, 5);
        System.out.println("Product created: " + product.getName() + ", Price: $" + product.getPrice() + ", Quantity: " + product.getQuantity());
        printLongLine();

        // Attempt to remove a product not in the cart
        try {
            System.out.println("Attempting to remove a product not in the cart...");
            cart.remove(product);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();

        // Add the product to the cart first, then remove it
        try {
            System.out.println("Adding product to the cart...");
            cart.add(product, 3);
            printCart(cart);
            printShortLine();

            // Remove the product from the cart
            System.out.println("Removing product from the cart...");
            cart.remove(product);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();
    }

    /**
     * Tests adding products to the cart with various scenarios.
     * It checks for null products, invalid quantities, and valid additions.
     *
     * @param cart the cart to perform addition operations on
     */
    private static void addToCartTest(Cart cart) {
        System.out.println("----------------------Testing add to cart operations------------------------------");
        printCart(cart);
        printLongLine();

        // Add null product
        try {
            System.out.println("Attempting to add a null product...");
            cart.add(null, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();

        // Create a valid product
        System.out.println("Creating a valid product...");
        Product product = new Product("Valid Product", 10.0, 5);
        System.out.println("Product created: " + product.getName() + ", Price: $" + product.getPrice() + ", Quantity: " + product.getQuantity());
        printLongLine();

        // Add product with negative quantity
        try {
            System.out.println("Adding product with negative quantity...");
            cart.add(product, -1);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();

        // Add product with zero quantity
        try {
            System.out.println("Adding product with zero quantity...");
            cart.add(product, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();

        // Add product with insufficient quantity
        try {
            System.out.println("Adding product with insufficient quantity...");
            cart.add(product, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();

        // Add product with valid quantity, then add more quantity
        try {
            System.out.println("Adding product with valid quantity...");
            cart.add(product, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();

        // Add over the available quantity
        try {
            System.out.println("Adding product with more than available quantity...");
            cart.add(product, 3);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printCart(cart);
        printLongLine();
    }

    /**
     * Tests the creation of products with various scenarios.
     * It checks for invalid product names, prices, quantities,
     * and weights for shippable products.
     */
    private static void createProductTest() {
        System.out.println("----------------------Testing product creation------------------------------");

        // Invalid product name
        try {
            System.out.println("Attempting to create a product with an invalid name...");
            Product invalidProduct = new Product(" ", 10.0, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printLongLine();

        // Invalid price
        try {
            System.out.println("Attempting to create a product with an invalid price...");
            Product invalidProduct = new Product("Valid Product", -10.0, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printLongLine();

        // Invalid quantity
        try {
            System.out.println("Attempting to create a product with an invalid quantity...");
            Product invalidProduct = new Product("Valid Product", 10.0, -5);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printLongLine();

        // Invalid weight for a shippable product
        try {
            System.out.println("Attempting to create a shippable product with an invalid weight...");
            ShippableProduct invalidShippableProduct = new ShippableProduct("Shippable Product", 20.0, 5, -1.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printLongLine();

        // Invalid weight for an expirable shippable product
        try {
            System.out.println("Attempting to create an expirable shippable product with an invalid weight...");
            ExpirableShippableProduct invalidExpirableShippableProduct = new ExpirableShippableProduct("Expirable Shippable Product", 30.0, 5, LocalDate.of(2025, 12, 1), -1.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printLongLine();
    }

    /**
     * Tests the creation of customers with various scenarios.
     * It checks for invalid names, emails, phone numbers, and balances.
     */
    private static void createCustomerTest() {
        System.out.println("----------------------Testing customer creation------------------------------");

        // Invalid name
        try {
            System.out.println("Attempting to create a customer with an invalid name...");
            Customer invalidCustomer = new Customer("", "person@example.com", "01000000000", 1000.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printLongLine();

        // Invalid email
        try {
            System.out.println("Attempting to create a customer with an invalid email...");
            Customer invalidCustomer = new Customer("Person", "invalid-email", "01000000000", 1000.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printLongLine();

        // Invalid phone number
        try {
            System.out.println("Attempting to create a customer with an invalid phone number...");
            Customer invalidCustomer = new Customer("Person", "person@example.com", "0100000", 1000.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printLongLine();

        // Negative balance
        try {
            System.out.println("Attempting to create a customer with a negative balance...");
            Customer invalidCustomer = new Customer("Person", "person@example.com", "01000000000", -1000.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        printLongLine();
    }

    /**
     * Prints the contents of the cart.
     *
     * @param cart the cart to print
     */
    private static void printCart(Cart cart) {
        System.out.println(cart);
    }

    /**
     * Prints a long line for better readability in the console.
     */
    private static void printLongLine() {
        System.out.println("________________________________________________________________________________________");
    }

    /**
     * Prints a short line for better readability in the console.
     */
    private static void printShortLine() {
        System.out.println("____________________________________");
    }
}