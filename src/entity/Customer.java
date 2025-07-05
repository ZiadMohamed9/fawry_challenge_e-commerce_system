package entity;

/**
 * Represents a customer in the e-commerce system.
 * Contains customer details such as name, email, phone number, balance, and a shopping cart.
 */
public class Customer {
    private String name;
    private String email;
    private String phoneNumber;
    private double balance;
    private Cart cart;

    /**
     * Constructs a Customer with the specified details.
     *
     * @param name        the name of the customer
     * @param email       the email address of the customer
     * @param phoneNumber the phone number of the customer
     * @param balance     the balance of the customer
     * @throws IllegalArgumentException if any of the parameters are invalid
     */
    public Customer(String name, String email, String phoneNumber, double balance) {
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setBalance(balance);
        this.cart = new Cart();
    }

    /**
     * Gets the name of the customer.
     *
     * @return the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     *
     * @param name the name to set
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty.");
        }
        this.name = name;
    }

    /**
     * Gets the email address of the customer.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the customer.
     *
     * @param email the email address to set
     * @throws IllegalArgumentException if the email is null, empty, or does not contain '@'
     */
    public void setEmail(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        this.email = email;
    }

    /**
     * Gets the phone number of the customer.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phoneNumber the phone number to set
     * @throws IllegalArgumentException if the phone number is null, empty, or not a 10-digit number
     */
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank() || !phoneNumber.matches("\\d{11}")) {
            throw new IllegalArgumentException("Phone number must be a 10-digit number.");
        }
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the balance of the customer.
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the balance of the customer.
     *
     * @param balance the balance to set
     * @throws IllegalArgumentException if the balance is negative
     */
    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.balance = balance;
    }

    /**
     * Gets the shopping cart of the customer.
     *
     * @return the shopping cart
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Sets the shopping cart for the customer.
     *
     * @param cart the cart to set
     * @throws IllegalArgumentException if the cart is null
     */
    public void setCart(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null.");
        }
        this.cart = cart;
    }
}
