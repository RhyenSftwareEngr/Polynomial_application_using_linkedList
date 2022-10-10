import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in); // creating object of Scanner class
    static Polynomial poly1 = new Polynomial();
    static  Polynomial poly2 = new Polynomial();
    public static void main(String[] args) throws IOException {
        poly1.input();
        System.out.println("Poly 1: "+poly1.getList());
        poly2.input();
        System.out.println("Poly 2: "+poly2.getList());
        Main call = new Main();
        while (true) {
            call.showMenu(); //Displays the menu
            System.out.print("Make your choice: ");
        }
    }

    public void showMenu() throws IOException {
        System.out.println();
        System.out.println("1: Add");
        System.out.println("2: Subtract");
        System.out.println("3: Multiply");
        System.out.println("4: Divide(With special restrictions)");
        System.out.println("5: Evaluate");
        System.out.println("6: Quit");


        int ch = Integer.parseInt(sc.next()); // reading user's choice
        switch (ch) {
            case 1:
                System.out.println("\n\n");
//                Matrices.initializeMatrix();
                System.out.print("Your Results in Addition: ");
                poly1.addition(poly2);
                sc.nextLine();
                System.out.print("press enter to continue..." );
                sc.nextLine();

                break;
            case 2:
                System.out.println("\n\n");
                System.out.println("Your results in Subtraction");
                poly1.subtraction(poly2);
                sc.nextLine();
                System.out.print("press enter to continue...");
                sc.nextLine();
                break;
            case 3:
                System.out.println("\n\n");
                System.out.print("Your Results in Addition: ");
                poly1.Multiply(poly2);
                sc.nextLine();
                System.out.print("press enter to continue...");
                sc.nextLine();
                break;
            case 4:
                System.out.println("\n\n");
                System.out.println("Always use spaces in the format ax^2 + bx + c");
                SyntheticConsole call = new SyntheticConsole();
                call.divideSynthetically();
                sc.nextLine();
                System.out.print("press enter to continue...");
                sc.nextLine();
                break;
            case 5:
                System.out.println("\n\n");
                System.out.println("Choose a number from the options below");
                System.out.println("1: Evaluate Poly1");
                System.out.println("2: Evaluate Poly2");
                sc.nextLine();
                sc.nextLine();
                if(sc.equals("1")){
                    poly1.eval();
                }
                else{
                    poly2.eval();
                }
                System.out.print("press enter to continue...");
                sc.nextLine();
                break;
            case 6:
                System.out.println("\nThank your for using our program! Feel free to come back.");
                for(int index=0; index < 35; index++){
                    System.out.print("");
                }
                System.exit(0);
            default:
                System.err.println("Invalid choice! Please make a valid choice.");
        }
    }
}
