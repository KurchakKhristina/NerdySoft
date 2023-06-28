import example.*;

import java.time.LocalDate;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        User user1 = User.createUser("Alice", 32);
        User user2 = User.createUser("Bob", 19);
        User user3 = User.createUser("Charlie", 20);
        User user4 = User.createUser("John", 27);


        Product realProduct1 = ProductFactory.createRealProduct("Product A", 20.50, 10, 25);
        Product realProduct2 = ProductFactory.createRealProduct("Product B", 50, 6, 17);

        Product virtualProduct1 = ProductFactory.createVirtualProduct("Product C", 100, "xxx", LocalDate.of(2023, 5, 12));
        Product virtualProduct2 = ProductFactory.createVirtualProduct("Product D", 81.25, "yyy", LocalDate.of(2024, 6, 20));


        List<Order> orders = new ArrayList<>() {{
            add(Order.createOrder(user1, List.of(realProduct1, virtualProduct1, virtualProduct2)));
            add(Order.createOrder(user2, List.of(realProduct1, realProduct2)));
            add(Order.createOrder(user3, List.of(realProduct1, virtualProduct2)));
            add(Order.createOrder(user4, List.of(virtualProduct1, virtualProduct2, realProduct1, realProduct2)));
        }};


        System.out.println("1. Create singleton class VirtualProductCodeManager \n");
        var isUsed = false;
        System.out.println("Is code used: " + isUsed + "\n");

        Product mostExpensive = getMostExpensiveProduct(orders);
        System.out.println("2. Most expensive product: " + mostExpensive + "\n");

        Product mostPopular = getMostPopularProduct(orders);
        System.out.println("3. Most popular product: " + mostPopular + "\n");

        double averageAge = calculateAverageAge(realProduct2, orders);
        System.out.println("4. Average age is: " + averageAge + "\n");

        Map<Product, List<User>> productUserMap = getProductUserMap(orders);
        System.out.println("5. Map with products as keys and list of users as value \n");
        productUserMap.forEach((key, value) -> System.out.println("key: " + key + " " + "value: " + value + "\n"));


        List<Product> productsByPrice = sortProductsByPrice(List.of(realProduct1, realProduct2, virtualProduct1, virtualProduct2));
        System.out.println("6. a) List of products sorted by price: " + productsByPrice + "\n");
        List<Order> ordersByUserAgeDesc = sortOrdersByUserAgeDesc(orders);
        System.out.println("6. b) List of orders sorted by user agge in descending order: " + ordersByUserAgeDesc + "\n");


        Map<Order, Integer> result = calculateWeightOfEachOrder(orders);
        System.out.println("7. Calculate the total weight of each order \n");
        result.forEach((key, value) -> System.out.println("order: " + key + " " + "total weight: " + value + "\n"));
    }

    private static Product getMostExpensiveProduct(List<Order> orders) {
        Product mostExpensiveProduct = null;
        double maxPrice = Double.MIN_VALUE;

        for (Order order : orders) {
            List<Product> products = order.getProducts();
            for (Product product : products) {
                if (product.getPrice() > maxPrice) {
                    maxPrice = product.getPrice();
                    mostExpensiveProduct = product;
                }
            }
        }

        return mostExpensiveProduct;
    }

    private static Product getMostPopularProduct(List<Order> orders) {
        Map<Product, Integer> productCountMap = new HashMap<>();

        for (Order order : orders) {
            List<Product> products = order.getProducts();
            for (Product product : products) {
                int count = productCountMap.getOrDefault(product, 0);
                productCountMap.put(product, count + 1);
            }
        }

        Product mostPopularProduct = null;
        int maxCount = 0;

        for (Map.Entry<Product, Integer> entry : productCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostPopularProduct = entry.getKey();
            }
        }

        return mostPopularProduct;
    }

    private static double calculateAverageAge(Product product, List<Order> orders) {
        int totalAge = 0;
        int userCount = 0;

        for (Order order : orders) {
            User user = order.getUser();
            List<Product> products = order.getProducts();
            if (products.contains(product)) {
                totalAge += user.getAge();
                userCount++;
            }
        }

        if (userCount > 0) {
            return (double) totalAge / userCount;
        } else {
            return 0d;
        }
    }

    private static Map<Product, List<User>> getProductUserMap(List<Order> orders) {
        Map<Product, List<User>> productUserMap = new HashMap<>();

        for (Order order : orders) {
            User user = order.getUser();
            List<Product> products = order.getProducts();
            for (Product product : products) {
                List<User> userList = productUserMap.getOrDefault(product, new ArrayList<>());
                userList.add(user);
                productUserMap.put(product, userList);
            }
        }

        return productUserMap;
    }

    private static List<Product> sortProductsByPrice(List<Product> products) {
        List<Product> sortedProducts = new ArrayList<>(products);
        sortedProducts.sort(Comparator.comparingDouble(Product::getPrice));
        return sortedProducts;
    }

    private static List<Order> sortOrdersByUserAgeDesc(List<Order> orders) {
        List<Order> sortedOrders = new ArrayList<>(orders);
        sortedOrders.sort(Comparator.comparingInt(order -> order.getUser().getAge()));
        Collections.reverse(sortedOrders);
        return sortedOrders;
    }

    private static Map<Order, Integer> calculateWeightOfEachOrder(List<Order> orders) {
        Map<Order, Integer> weightMap = new HashMap<>();

        for (Order order : orders) {
            List<Product> products = order.getProducts();
            int totalWeight = 0;
            for (Product product : products) {
                if (product instanceof RealProduct) {
                    totalWeight += ((RealProduct) product).getWeight();
                }
            }
            weightMap.put(order, totalWeight);
        }

        return weightMap;
    }
}