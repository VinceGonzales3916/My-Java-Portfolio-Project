     package MyJavaPackage; // Define package
     
     import java.util.Scanner; // Import Scanner for input
     
     public class calculatorMethod { // Main class
     
         public static void main(String[] args) {
             Scanner scanner = new Scanner(System.in); // Scanner for user input
             int choice; // Variable to store menu choice
     
             System.out.println("Welcome to my Calculator"); // Welcome message
     
             do { // Menu loop
                 System.out.println("\n==== Simple Calculator ====");
                 System.out.println("1. Add"); // Option 1
                 System.out.println("2. Subtract"); // Option 2
                 System.out.println("3. Multiply"); // Option 3
                 System.out.println("4. Divide"); // Option 4
                 System.out.println("5. Exit"); // Option 5
                 System.out.print("Enter your choice: ");
                 choice = scanner.nextInt(); // Get user choice
     
                 if (choice >= 1 && choice <= 4) { // Valid operation
                     System.out.print("Enter first number: ");
                     int num1 = scanner.nextInt(); // First number input
     
                     System.out.print("Enter second number: ");
                     int num2 = scanner.nextInt(); // Second number input
     
                     switch (choice) { // Perform chosen operation
                         case 1:
                             System.out.println("Result: " + sum(num1, num2)); // Addition
                             break;
                         case 2:
                             System.out.println("Result: " + minus(num1, num2)); // Subtraction
                             break;
                         case 3:
                             System.out.println("Result: " + multiply(num1, num2)); // Multiplication
                             break;
                         case 4:
                             System.out.println("Result: " + divide(num1, num2)); // Division
                             break;
                     }
                 } else if (choice != 5) { // Invalid menu input
                     System.out.println("Invalid choice. Try again.");
                 }
     
             } while (choice != 5); // Repeat until exit
     
             System.out.println("Calculator exited. Goodbye!"); // Exit message
             scanner.close(); // Close scanner
         }
     
         // Addition method
         public static int sum(int a, int b) {
             return a + b;
         }
     
         // Subtraction method
         public static int minus(int a, int b) {
             return a - b;
         }
     
         // Multiplication method
         public static int multiply(int a, int b) {
             return a * b;
         }
     
         // Division method
         public static double divide(int a, int b) {
             if (b == 0) { // Check divide by zero
                 System.out.println("Error: Cannot divide by zero!");
                 return 0;
             }
             return (double) a / b; // Return division result as double
         }
     }
