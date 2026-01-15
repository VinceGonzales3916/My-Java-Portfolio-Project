  package MyJavaPackage; // Define package
  import java.util.Scanner; // Import Scanner for input
  public class overloadCalculator { // Main class
  
      public static void main(String[] args) {
          Scanner scanner = new Scanner(System.in); // Scanner for user input
          int choice; // Variable to store menu choice
  
          System.out.println("Welcome to Calculator"); // Welcome message
  
          do { // Menu loop
              System.out.println("\n==== Overload Calculator ====");
              System.out.println("1. Add"); // Option 1
              System.out.println("2. Subtract"); // Option 2
              System.out.println("3. Multiply"); // Option 3
              System.out.println("4. Divide"); // Option 4
              System.out.println("5. Exit"); // Option 5
              System.out.print("Enter your choice: ");
              choice = scanner.nextInt(); // Get user choice
  
              if (choice >= 1 && choice <= 4) { // Valid operation
                  System.out.print("Enter first number: ");
                  double num1 = scanner.nextDouble(); // Accept first number (decimal)
                  System.out.print("Enter second number: ");
                  double num2 = scanner.nextDouble(); // Accept second number (decimal)
  
                  switch (choice) { // Perform chosen operation
                      case 1:
                          System.out.println("Result: " + add(num1, num2)); // Calls correct overloaded add
                          break;
                      case 2:
                          System.out.println("Result: " + subtract(num1, num2)); // Calls correct overloaded subtract
                          break;
                      case 3:
                          System.out.println("Result: " + multiply(num1, num2)); // Calls correct overloaded multiply
                          break;
                      case 4:
                          System.out.println("Result: " + divide(num1, num2)); // Calls correct overloaded divide
                          break;
                  }
              } else if (choice != 5) { // Invalid menu input
                  System.out.println("Invalid choice. Try again.");
              }
  
          } while (choice != 5); // Repeat until exit
  
          System.out.println("Calculator exited. Goodbye!"); // Exit message
          scanner.close(); // Close scanner
      }
  
      // ----------------- Overloaded Methods -----------------
  
      // Addition (int)
      public static int add(int a, int b) {
          return a + b;
      }
  
      // Addition (double)
      public static double add(double a, double b) {
          return a + b;
      }
  
      // Subtraction (int)
      public static int subtract(int a, int b) {
          return a - b;
      }
  
      // Subtraction (double)
      public static double subtract(double a, double b) {
          return a - b;
      }
  
      // Multiplication (int)
      public static int multiply(int a, int b) {
          return a * b;
      }
  
      // Multiplication (double)
      public static double multiply(double a, double b) {
          return a * b;
      }
  
      // Division (int)
      public static double divide(int a, int b) {
          if (b == 0) { // Check divide by zero
              System.out.println("Error: Cannot divide by zero!");
              return 0;
          }
          return (double) a / b; // Cast to double
      }
  
      // Division (double)
      public static double divide(double a, double b) {
          if (b == 0) { // Check divide by zero
              System.out.println("Error: Cannot divide by zero!");
              return 0;
          }
          return a / b;
      }
  }
