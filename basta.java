import java.util.Scanner;

public class basta {
    public static void main(String[] args) {
      Scanner s = new Scanner(System.in);  

      System.out.print("Enter a number: ");
      double input = s.nextDouble();

      if (input % 4 || input % 400) {
          System.out.println("The number is divisible by 4.");
      } else {
        System.out.println("The number is not divisible by 4");
      }


    }
}
