# About the project

Foodtasker is an UberEats clone in which customers can order takeaway food on through the customer mobile app, restaurants can accept and track orders

---

### Click here for the video [demo](http://80hr-prototype.com/). Feel free to [download APK file here](https://github.com/Kgotso-Koete/foodTasker-customer-driver-mobile-app/blob/master/app-debug.apk) for testing

---

### Technology stack

Android SDK using Java, Authentication using Facebook and JWT, payments using Stripe API, maps using Google Maps API

---

### Application overview and link to related code bases

The combined application consists of 4 components:

1. Back-end [separate repository](https://github.com/Kgotso-Koete/foodTasker-Restaurant-web-app): A back end that handles CRUD operations with an api specified in the `api_spec folder`.
2. Restaurant Front-end [separate repository](https://github.com/Kgotso-Koete/foodTasker-Restaurant-web-app): A web-based administrative front-end for restaurant employees to manage orders
3. Customer Front-end (part of this code base): An android mobile app for customers to use to place and pay for orders
4. Driver Front-end (part of this code base): An android mobile app for drivers to use to accept and deliver orders

---

### What this code base covers

This code is for the Customer and Driver front-end mobile app.

---

### Tutorial used

[Code4Startup](https://code4startup.com/) tutorial on building an UberEats clone based on the [foodTasker app tutorial](https://coderealprojects.com/projects/create-ubereats-mobile-side-with-android).

---

### Description and features

### General functionality

- Authenticate users using Facebook login and JWT
- Customer: Create and Read orders, Read meals
- Driver: Create and Read orders "on the way", Read orders "ready for delivery"

### The general page breakdown is as follows:

### Customer pages

- Sign in page (URL: `POST baseURL/api/social/convert-token`): Sign in via Facebook and have Facebook token converted into Django web server token
- Restaurants page (URL: `GET baseURL/api/customer/restaurants/`): List of restaurants and their addresses
    - Meals page (URL: `GET baseURL/api/customer/meals/?restaurant_id/`): List of meals offered by each restaurants
    - Meal: Each meal can be added to the checkout tray
- Checkout Tray (URL: `POST baseURL/api/customer/order/add/`): Customer can checkout by adding an address via Google maps and paying via Stripe
- Order page: List of restaurants and their addresses
    - List of items last ordered (URL: `GET baseURL/api/customer/order/latest/?access_token={{access_token}}`): List of items recently ordered
    - Show driver location on map (URL: `GET baseURL/api/customer/driver/location/?access_token={{access_token}}`): Each meal can be added to the checkout tray


### Driver pages

- Sign in page (URL: `POST baseURL/api/social/convert-token`): Sign in via Facebook and have Facebook token converted into Django web server token
- Orders page : List of available orders for the driver to pick
    - Get list of orders (URL: `GET baseURL/api/driver/orders/ready/`): List of items available for delivery
    - Assign delivery (URL: `Post baseURL/api/driver/orders/ready/?access_token={{access_token}}?order_id={{order_id}}`): Confirmation that the order was picked
- Order page: List of restaurants and their addresses
    - List of items last ordered (URL: `GET /api/driver/order/latest/?access_token={{access_token}}?order_id={{order_id}}`): List of items recently picked
    - Show driver location on map (URL: `GET baseURL/api/customer/driver/location/?access_token={{access_token}}`)
    - Mark order as 'Complete' (URL: `GET baseURL/api/driver/order/complete/?access_token={{access_token}}?order_id={{order_id}}`): Each meal can be added to the checkout tray
- Statistics page (URL: `POST /api/driver/revenue/?access_token={{access_token}}`): Driver's weekly revenue statistics

---

### How to run the code

Download the APK file in the project root folder and install on your phone or clone this repository and run on an Android Emulator via Android Studio

---

### Application Structure

- `manifests/` where key app settings are specified
- `java/com.example.foodtasker` for the main application logic
  - `Activities/` main pages for the app
  - `Adaptors` injecting data into the views and corresponding logic
  - `Fragments` views that are the 'children' of main pages
  - `Objects` data object model for JSON data received from and sent to API server
  - `utils` helpers for common tasks
  - `AppDatabase` build app database that lives on mobile device
  - `TrayDAO` commands for operations to be performed on database that lives on mobile device
- `res` Markup and styling
    - `drawable` custom app icons
    - `layout` xml markup templates for pages and child components
    - `menu` xml markup for navigation menu
    - `mipmap` icon for launcher icon on mobile device
    - `values` key global strings and colour theme
- `Gradle Scripts` Build and packaging scripts
    - `build.gradle` project level build settings file
    - `build.gradle` app level dependencies and secret keys

---

### Authentication

Requests are authenticated using the `Authorization` header with a valid JWT. Authentication is handled by djangoauth and django-rest-framework-social-oauth2 from the server side and Facebook login from client side.

---

### Payments

Stripe payment API was used (using test keys and test tokens)

---

### Timesheet log

- Back end

  - Version 1 (Code4Startup Tutorial): 65 hours
  - Version 2 (personal modifications): x hours (email + password login, if only I could find the time goodness)

---

### Acknowledgements

Special thanks to Leo Trieu's [Code4Startup](https://code4startup.com/projects) for a great tutorial. I had been looking for a tutorial for a real world 2 sided market application with geo-location features and I found one with thanks to Code4Startup. We need more of this kind of tutorial and less of 'Foo Bar'.

---

### License

The codebase is MIT licensed unless otherwise specified.

---

To be modified further by Kgotso Koete
<br/>
Johannesburg, South Africa
