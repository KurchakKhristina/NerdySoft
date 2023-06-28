package example;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private User user;
    private List<Product> products;

    private Order(User user, List<Product> products) {
        this.user = user;
        this.products = products;
    }

    public static Order createOrder(User user, List<Product> products) {
        return new Order(user, products);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public static class OrderBuilder {
        private User user;
        private List<Product> products;

        public OrderBuilder() {
            this.products = new ArrayList<>();
        }

        public OrderBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public OrderBuilder addProduct(Product product) {
            this.products.add(product);
            return this;
        }

        public Order build() {
            return new Order(user, products);
        }
    }
}
