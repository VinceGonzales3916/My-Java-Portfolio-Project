package MyJavaPackage;

import java.util.Scanner;

public class canteenOrder {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Variables
        StringBuilder orderList = new StringBuilder();
        String confirm, itemName, more, idType;
        int choice, quantity;
        double total = 0.0, discount = 0.0, price = 0.0, orderCost, finalTotal, payment, change;
        boolean ordering = true;

        System.out.println("Welcome to the Canteen!");

        // Main ordering loop
        while (ordering) {
            // Display menu
            System.out.println("\n========== School Canteen Menu ==========");
            System.out.println("0. Cancel Order");
            System.out.println("1. Burger - ₱50");
            System.out.println("2. Spaghetti - ₱60");
            System.out.println("3. Sandwich - ₱40");
            System.out.println("4. Fried Rice & Fried Egg - ₱55");
            System.out.println("5. Hotdog - ₱35");
            System.out.println("6. Siomai Rice - ₱45");
            System.out.println("7. Milk Tea - ₱65");
            System.out.println("8. Iced Coffee - ₱55");
            System.out.println("9. Bottled Water - ₱20");
            System.out.println("10. Soft Drink - ₱30");
            System.out.print("Choose your order (0-10): ");
            choice = input.nextInt();
            input.nextLine(); // consume newline

            // Cancel order
            if (choice == 0) {
                System.out.print("Are you sure you want to cancel ordering? (yes/no): ");
                confirm = input.nextLine();
                if (confirm.equalsIgnoreCase("yes")) {
                    ordering = false;
                    break;
                } else {
                    continue;
                }
            }

            // Quantity
            System.out.print("Enter quantity: ");
            quantity = input.nextInt();
            input.nextLine();

            // Determine item name and price
            itemName = "";
            switch (choice) {
                case 1 -> { price = 50; itemName = "Burger"; }
                case 2 -> { price = 60; itemName = "Spaghetti"; }
                case 3 -> { price = 40; itemName = "Sandwich"; }
                case 4 -> { price = 55; itemName = "Fried Rice & Fried Egg"; }
                case 5 -> { price = 35; itemName = "Hotdog"; }
                case 6 -> { price = 45; itemName = "Siomai Rice"; }
                case 7 -> { price = 65; itemName = "Milk Tea"; }
                case 8 -> { price = 55; itemName = "Iced Coffee"; }
                case 9 -> { price = 20; itemName = "Bottled Water"; }
                case 10 -> { price = 30; itemName = "Soft Drink"; }
                default -> {
                    System.out.println("Invalid choice.");
                    continue;
                }
            }

            // Calculate cost
            orderCost = price * quantity;
            total += orderCost;

            // Save order details
            orderList.append(quantity)
                     .append(" x ")
                     .append(itemName)
                     .append(" (₱")
                     .append(String.format("%.2f", price))
                     .append(" each) = ₱")
                     .append(String.format("%.2f", orderCost))
                     .append("\n");

            // Ask to order more
            System.out.print("Do you want to order another item? (yes/no): ");
            more = input.nextLine();
            if (more.equalsIgnoreCase("no")) {
                ordering = false;
            }
        }

        // Nothing ordered
        if (total == 0.0) {
            System.out.println("\nNo items ordered. Thank you!");
            input.close();
            return;
        }

        // ID type for discount
        System.out.print("Enter your ID type (Student/Teacher/School Personnel/None): ");
        idType = input.nextLine();

        // Apply discount
        switch (idType.toLowerCase()) {
            case "student" -> {
                discount = total * 0.10;
                System.out.println("Student Discount: 10% applied.");
            }
            case "teacher" -> {
                discount = total * 0.05;
                System.out.println("Teacher Discount: 5% applied.");
            }
            case "school personnel" -> {
                discount = total * 0.03;
                System.out.println("Personnel Discount: 3% applied.");
            }
            default -> System.out.println("No discount applied.");
        }

        // Final total
        finalTotal = Math.max(total - discount, 0.0);

        // Payment
        payment = 0.0;
        while (true) {
            System.out.printf("Total to Pay: ₱%.2f\n", finalTotal);
            System.out.print("Enter amount of money to pay (enter 0 to cancel): ₱");
            payment = input.nextDouble();
            input.nextLine();

            if (payment == 0.0) {
                System.out.println("Payment cancelled. Transaction aborted.");
                input.close();
                return;
            }

            if (payment < finalTotal) {
                System.out.printf("Insufficient amount. You still owe ₱%.2f. Please enter full payment or 0 to cancel.\n",
                        (finalTotal - payment));
                continue;
            }
            break;
        }

        // Change
        change = payment - finalTotal;

        // Print receipt
        System.out.println("\n========== Receipt ==========");
        System.out.println("ID Type: " + idType);
        System.out.println("\nOrders:");
        System.out.print(orderList);
        System.out.printf("\nDiscount Applied: ₱%.2f\n", discount);
        System.out.printf("Total before Discount: ₱%.2f\n", total);
        System.out.printf("Final Total to Pay: ₱%.2f\n", finalTotal);
        System.out.printf("Amount Paid: ₱%.2f\n", payment);
        System.out.printf("Change: ₱%.2f\n", change);

        input.close();
    }
}
