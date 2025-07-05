# E-commerce System

## Description
This is a simple e-commerce system that allows users to manage their shopping cart. It includes features for adding products, viewing products, and checking out.

## Features
- Add products to the cart
- Remove products from the cart
- Update product quantities in the cart
- View products in the cart
- Checkout process
- Shipping products

## Architecture
The system is designed with a modular architecture, allowing for easy expansion and maintenance. The main components include:
```aiignore
src/
├── entity/
│   ├── Cart.java
│   ├── Customer.java
│   └── product/
│       ├── Expirable.java
│       ├── ExpirableProduct.java
│       ├── ExpirableShippableProduct.java
│       ├── Product.java
│       ├── Shippable.java
│       └── ShippableProduct.java
├── exception/
│   ├── EmptyCartException.java
│   ├── ExpiredProductException.java
│   ├── InsufficientBalanceException.java
│   └── InsufficientQuantityException.java
├── Main.java
└── service/
    ├── CheckoutService.java
    └── ShippingService.java
```

## Testing
The Main class includes a main method that runs the application. You can test the functionality by running this class.

### Test Functions

- 'createCustomerTest()': Tests the creation of a customer.

![createCustomerTest.png](images/createCustomerTest.png)

- 'createProductTest()': Tests the creation of a product.

![createProductTest.png](images/createProductTest.png)

- 'addToCartTest()': Tests adding a product to the cart.

![addToCartTest1.png](images/addToCartTest1.png)
![addToCartTest2.png](images/addToCartTest2.png)

- 'removeFromCartTest()': Tests removing a product from the cart.

![removeFromCartTest1.png](images/removeFromCartTest1.png)
![removeFromCartTest2.png](images/removeFromCartTest2.png)

- 'updateCartTest()': Tests updating the quantity of a product in the cart.

![updateCartTest1.png](images/updateCartTest1.png)
![updateCartTest2.png](images/updateCartTest2.png)
![updateCartTest3.png](images/updateCartTest3.png)

- 'checkoutTest()': Tests the checkout process.

![checkoutTest1.png](images/checkoutTest1.png)
![checkoutTest2.png](images/checkoutTest2.png)

- 'successfulCheckoutTest()': Tests a successful checkout with sufficient balance.

![successfulCheckoutTest1.png](images/successfulCheckoutTest1.png)
![successfulCheckoutTest2.png](images/successfulCheckoutTest2.png)
![successfulCheckoutTest3.png](images/successfulCheckoutTest3.png)