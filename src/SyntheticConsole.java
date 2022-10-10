package HelperFunctions;

import java.io.*;
import java.util.*;

public class SyntheticConsole{
    private BufferedReader in;
    private String polynomial, quotient;

    public SyntheticConsole() throws IOException {
            in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("\n---------------------------------");
            System.out.println("Press any key to continue.");
            in.read();
                        divideSynthetically();
        }
    public void divideSynthetically(){
        try{
            ArrayList<Term> divident = new ArrayList<Term>();
            ArrayList<Term> divisor = new ArrayList<Term>();
            System.out.println("******Solving by Synthetic Division!******");
            System.out.print("Please enter your equation: ");
            polynomial = in.readLine();
            divident = Utility.generateTerms(polynomial);
            System.out.print("Please enter your divident: ");
            quotient = in.readLine();
            divisor = Utility.generateTerms(quotient);
            divident = Utility.fillTerms(divident, Utility.findGreatestExponent(divident));
            divisor = Utility.fillTerms(divisor, Utility.findGreatestExponent(divisor));

            if (Utility.findGreatestExponent(divident) < Utility.findGreatestExponent(divisor)){
                System.out.println("Notice: Dividing polynomial with a larger exponent on the base is unsupported / is in beta.");
            }

            if (Utility.findGreatestExponent(divisor) > 2){
                System.out.println("Error: You cannot divide by a non-quadratic in this version.");
                divideSynthetically();
                return;
            }

            Solver.solveEquation(divident, divisor, false);
            System.out.println("---------------------------------");
            System.out.println(Solver.getSolution());
            Main main = new Main();
            main.showMenu();
        }
        catch(IOException e){
            System.out.println("Error: Input is not valid. Please try again.");
            divideSynthetically();
            return;
        }
    }
    public static void main (String [] args) throws IOException {
        new SyntheticConsole();
    }
}