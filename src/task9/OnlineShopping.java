package task9;

public class OnlineShopping {



    static class Product {
        int productId;
        String productName;
        double price;

        Product(int productId, String productName, double price) {
            this.productId = productId;
            this.productName = productName;
            this.price = price;
        }

        void displayProduct() {
            System.out.println("Product ID: " + productId);
            System.out.println("Product Name: " + productName);
            System.out.println("Price: " + price);
        }
    }

    static class Electronics extends Product {
        int warranty;

        Electronics(int productId, String productName, double price, int warranty) {
            super(productId, productName, price);
            this.warranty = warranty;
        }

        void display() {
            displayProduct();
            System.out.println("Warranty: " + warranty + " years");
        }
    }

    static class Clothing extends Product {
        String size;

        Clothing(int productId, String productName, double price, String size) {
            super(productId, productName, price);
            this.size = size;
        }

        void display() {
            displayProduct();
            System.out.println("Size: " + size);
        }
    }

    static class Food extends Product {
        String expiryDate;

        Food(int productId, String productName, double price, String expiryDate) {
            super(productId, productName, price);
            this.expiryDate = expiryDate;
        }

        void display() {
            displayProduct();
            System.out.println("Expiry Date: " + expiryDate);
        }
    }

    public static void main(String[] args) {

        Electronics e = new Electronics(
                101, "Laptop", 50000, 2);

        Clothing c = new Clothing(
                102, "T-Shirt", 1000, "L");

        Food f = new Food(
                103, "Biscuits", 100, "30-12-2026");

        System.out.println("Electronics");
        e.display();

        System.out.println(" Clothing");
        c.display();

        System.out.println(" Food ");
        f.display();
    }
}