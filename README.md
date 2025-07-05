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