package HelperFunctions;

import javax.swing.*;
import java.util.ArrayList;

public class Solver{

    private static String sol = "";


    /* This method returns if the divident is monic
     * @return boolean if it is monic */
    private static boolean isMonic(ArrayList<Term> divident){
        return divident.get(0).getCoefficient() == 1;
    }
    /** This method SOLVES the equations! */
    public static void solveEquation(ArrayList<Term> divident, ArrayList<Term> divisor, boolean hasGUI){
        /* Time to get the coefficients and set up the arrays. */
        double [] [] board = new double [divident.size()][divisor.size()];
        if (board[0].length == 0) //RETURN IF THE ARRAY HAS NOTHING IN IT
            return;
        /* Let's fill the top of the board with the appropriate initial values. */
        for(int i = 0; i < divident.size(); i++){
            //System.out.println("COEFF: " + divident.get(i).getCoefficient());
            board[i][0] = divident.get(i).getCoefficient();
        }
        boolean monic = isMonic(divident);

        double [] multiSide = new double[divisor.size() + 1];
        /* Populate left most table. Update the monic restriction for flipping all values, too. */
        for(int i = divisor.size() - 1; i >0; i--){
            multiSide[divisor.size() - i] = ((monic) ? (divisor.get(i).getCoefficient() * -1) : (divisor.get(i).getCoefficient() * -1.0));  //times *-1
        }

        double [][] res = new double[divident.size()][2];
        double leadingCoef = divisor.get(0).getCoefficient();

        /* Now to go sequentially through the top list. And do the appropriate things. */
        for(int qq = 0; qq < divident.size(); qq++){
            /* Go down the table, adding every value. */
            double sum = 0;
            for(int p = 0; p < divisor.size(); p++){ //minus 2? //-1
                sum += board[qq][p];
            }
            res[qq][0] = sum;
            if (divident.get(0).getPower() - qq < divisor.get(0).getPower()){} //nothing.
            else{
                res[qq][1] = res[qq][0]/leadingCoef;
                /* Now to populate the rest of the board. */
                int x = qq+1; //wrong.;
                int y = divisor.size() - 1;
                //<= ?? // -2 or -1 //<= 0
                while (y > 0 && x <= divident.size() - 1) {
                    board[x][y] = res[qq][1] * multiSide[y];
                    x++;
                    y--;
                }
            }
        }
        outputPxRx(Utility.findGreatestExponent(divident), Utility.findGreatestExponent(divisor), res, hasGUI);
    }
    /** Insane amount of ternary operators.
     * Essentially outputs the solution */
    public static void outputPxRx(int expOne, int expTwo, double [][] res, boolean shouldOutput){
        StringBuilder quot = new StringBuilder();
        StringBuilder rem = new StringBuilder();
        int currEx = expOne - expTwo;
        int exp = res.length - (expOne - expTwo) - 2; //x -1
        for(int i = 0; i < res.length; i++){
            if (res[i][1] == 0 && res[i][0] != 0){
                if (currEx >= 0){
                    quot.append((res[i][0] > 0 && i != 0) ? ("+") : "").append((res[i][0] != 1 && res[i][0] != -1) ? (res[i][0]) : (res[i][0] == -1 && currEx != 0) ? ("-") : (res[i][0] == 1 && currEx == 0) ? (1) : ("")).append(((currEx != 0 && currEx != 1)) ? ("x^" + (currEx)) : (currEx == 1) ? ("x") : ("")).append(" ");
                    currEx--;
                }
                else{
                    rem.append((res[i][0] > 0 && i != 0) ? ("+") : "").append((res[i][0] != 1 && res[i][0] != -1) ? (res[i][0]) : (res[i][0] == -1 && exp != 0) ? ("-") : (res[i][0] == 1 && exp == 0) ? (1) : ("")).append((exp != 0 && exp != 1) ? ("x^" + exp) : (exp == 1) ? ("x") : ("")).append(" ");
                    exp--;
                }
            }
            else{
                if (res[i][1] != 0){
                    if (currEx >= 0){
                        quot.append((res[i][1] > 0 && i != 0) ? ("+") : "").append((res[i][1] != 1 && res[i][1] != -1) ? (res[i][1]) : (res[i][1] == -1 && currEx != 0) ? ("-") : (res[i][1] == 1 && currEx == 0) ? (1) : ("")).append((currEx != 0 && currEx != 1) ? ("x^" + currEx) : (currEx == 1) ? ("x") : ("")).append(" ");
                        currEx--;
                    }
                    else{
                        rem.append((res[i][1] > 0 && i != 0) ? ("+") : "").append((res[i][1] != 1 && res[i][1] != -1) ? (res[i][1]) : (res[i][1] == -1 && exp != 0) ? ("-") : (res[i][1] == 1 && exp == 0) ? (1) : ("")).append((exp != 0 && exp != 1) ? ("x^" + exp) : (exp == 1) ? ("x") : ("")).append(" ");
                        exp--;
                    }
                }
            }
        }
        if (shouldOutput)
            JOptionPane.showMessageDialog (null, "Solution: \nQ(x) = " + quot + "\nR(x) = " + ((rem.toString().equals("")) ? ("None") : (rem.toString())), "Solution", JOptionPane.WARNING_MESSAGE);
        sol =  ("Solution: \nQ(x) = " + quot + "\nR(x) = " + ((rem.toString().equals("")) ? ("None") : (rem.toString())));
    }

    public static String getSolution(){
        return sol;
    }
}
