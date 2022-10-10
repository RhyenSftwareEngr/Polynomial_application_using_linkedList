import java.util.*;


public class Polynomial {
    private LinkedList<Term> terms;
    private int[] coef;  // coefficients
    private int deg;     // degree of polynomial (0 for the zero polynomial)

    public Polynomial() {
        this.terms = new LinkedList<Term>();
    }

    public LinkedList getList() {
        return terms;
    }

    public void input() {
        System.out.println("Please enter a polynomial in standard form");
        Scanner myScanner1 = new Scanner(System.in);
        String input = myScanner1.nextLine();

        String[] co = input.split("\\+|(?=\\-)"); //Splits to isolate each term via "+" or "-" character. Keeps the "-" character for negative coefficients
        String[] expo = input.split("\\+|(?=\\-)");
        int[] coef = new int[co.length];
        int[] exp = new int[expo.length];


        for (int x = 0; x < co.length; x++) {
            co[x].substring(0, co[x].indexOf("x") + 1); //Removes all characters after the "x" character
            if (co[x].equals("x")) { //Checks to see if the coefficient array consists of just an "x". Makes coeffiecient = 1
                coef[x] = 1;
            } else if (co[x].equals("-x")) { //Checks to see if the coefficient array consists of just an "-x". Makes coeffiecient = -1
                coef[x] = -1;
            } else if (!co[x].contains("x") && co[x].contains("^")) { //Checks to see if the array may simply contain a number without the "x" character, but with an exponent
                int coefficient = Integer.parseInt(co[x].substring(0, co[x].indexOf("^")));
                int exponent = Integer.parseInt(co[x].substring(co[x].indexOf("^") + 1));
                double power = Math.round(Math.pow(coefficient, exponent));

                expo[x] = "" + power; //Adds and replaces coefficient the element in the expo array at x position.
                coef[x] = (int) power; //Converts power into an int and adds it into the coef array.
            } else if (co[x].contains("x")) { //Gets all numbers before the x and parses into an int array
                coef[x] = Integer.parseInt(co[x].substring(0, co[x].indexOf("x")));
            } else { //Applies if the element is only the number by itself with no "x" variable or "^" character
                coef[x] = Integer.parseInt(co[x]);
            }
        }

        for (int y = 0; y < expo.length; y++) {
            if (!expo[y].contains("x") && !expo[y].contains("^")) { //Checks to see if element consists of only digits.
                expo[y] = "0";
                exp[y] = Integer.parseInt(expo[y]);
                terms.add(new Term(coef[y], exp[y]));
            } else if (expo[y].contains("^")) {
                expo[y] = expo[y].substring(expo[y].indexOf("^") + 1); //Returns all numbers after the "^" character"
                exp[y] = Integer.parseInt(expo[y]);
                terms.add(new Term(coef[y], exp[y]));
            } else { //If the character, "^", is not in string, exponent will always be 1.
                expo[y] = "1";
                exp[y] = Integer.parseInt(expo[y]);
                terms.add(new Term(coef[y], exp[y]));
            }
        }
    }


    public void AddTerm(int coefficient, int exponent) {
        int i = 0;
        boolean looping = true;

        while (looping && i < terms.size()) { //If i is less than exponent, we move on through linked list. Should sort to descending order
            if (exponent < terms.get(i).getExp()) {
                i = i + 1;
            } else {
                looping = false;
            }
        }
        terms.add(i, new Term(coefficient, exponent)); //Exit while loop and create a new term at i index

        int n = terms.size(); //placeholder reference
        int temp =0;
        for(int k=0;k<n;k++){ //goes through length of the terms linkedlist
            for(int j=1;j<n-k;j++){ //references the length of the list
                if(this.terms.get(j-1).getExp()==this.terms.get(j).getExp()){ //compares exponents of two list indexes from terms linkedlist
                    temp = this.terms.get(j-1).getCoef()+this.terms.get(j).getCoef(); //adds coefficient
                    this.terms.set(j, new Term(temp,this.terms.get(j-1).getExp())); //allows unique location
                    this.terms.set(j-1, new Term(0,0)); //If location unused, exponents and coeffcients of 0 are placed there
                }
            }
        }
        for(int s=0;s<this.terms.size();s++){ //removes all unused places from terms linkedlist
            if (this.terms.get(s).getCoef()==0&&this.terms.get(s).getExp()==0){
                this.terms.remove(s);
            }
        }
    }

    public String toString() {
        String output1 = "";
        for (int i = 0; i < terms.size(); i++) {
            if (terms.get(i).getCoef() < 0) {//Only applies for negative coefficients
                if (terms.get(i).getExp() == 0) { //Checks to see if exponent is 0. Returns coefficient by itself as x will always = 1
                    output1 += terms.get(i).getCoef();
                } else if (terms.get(i).getCoef() == 1 && terms.get(i).getExp() == 1) { //These next 3 if statements check to see if the coefficients or exponents are 1.
                    output1 += "-x";
                } else if (terms.get(i).getCoef() == 1) {
                    output1 += "-x^" + terms.get(i).getExp();
                } else if (terms.get(i).getExp() == 1) {
                    output1 += +terms.get(i).getCoef() + "x";
                } else {
                    output1 += terms.get(i).getCoef() + "x^" + terms.get(i).getExp();
                }
            }
            else if (terms.get(i).getCoef() > 0) { //This only applies for positive coefficients
                if (terms.get(i).getExp() == 0) { //Checks to see if exponent is 0. Returns coefficient by itself as x will always = 1
                    output1 += "+" + terms.get(i).getCoef();
                }
                else if(terms.get(i).getCoef() == 1 && terms.get(i).getExp() == 1) { //These next 3 if statements check to see if the coefficients or exponents are 1.
                    output1 += "+x";}
                else if (terms.get(i).getCoef() == 1) {
                    output1 += "+x^" + terms.get(i).getExp();}
                else if (terms.get(i).getExp() == 1) {
                    output1 += "+" + terms.get(i).getCoef() + "x";}
                else {
                    output1 += "+" + terms.get(i).getCoef() + "x^" + terms.get(i).getExp();
                }
            }
        }
        if (output1.startsWith("+")) { //Removes "+" character at the beginning of polynomial if present
            output1 = output1.substring(1);
        }
        return output1;
    }



    public double eval() { //method call that allows user to input an x_value
        System.out.println("Please enter the value you want for x: ");
        Scanner myScanner1 = new Scanner(System.in);
        int ex = myScanner1.nextInt();
        double result = 0;

        for (int x = 0; x < terms.size(); x++) {
            double power = Math.pow(ex, terms.get(x).getExp()); //Raises x up to exponent power.
            result += terms.get(x).getCoef() * power;
        }
        System.out.println("Evaluation Result: "+result);
        return result;
    }

    public void addition(Polynomial poly2) {
        LinkedList exponents = new LinkedList<Integer>();
        Polynomial poly3 = new Polynomial(); //Both polynomials will add together to create a new polynomial

        for (int x = 0; x < terms.size(); x++) {
            int result = terms.get(x).getCoef();
            exponents.add(terms.get(x).getExp()); //Adds all exponents from first polynomial into a linkedlist containing only these exponents
            for (int y = 0; y < poly2.terms.size(); y++) {
                if (poly2.terms.get(y).getExp() == terms.get(x).getExp()) { //Iterates through second polynomial and checks to see if any exponents equal to the first polynomial
                    result += poly2.terms.get(y).getCoef(); //Adds coefficients of polynomials with like-exponents together
                }
            }
            poly3.AddTerm(result, terms.get(x).getExp());
        }

        for (int z = 0; z < poly2.terms.size(); z++) { //Iterates through second polynomial list again
            if (!exponents.contains(poly2.terms.get(z).getExp())) { //Checks to see if an exponent exists in the exponents linkedlist for the second polynomial.
                int i = 0;                                          //Sorts and appends term from the second polynomial to the new linkedlist created earlier if exponent that exists in the second polynomial doesn't exist in the exponents linkedlist.
                boolean looping = true;
                    while (looping && i < terms.size()) { //If i is less than exponent of the first polynomial's term, we move on through linked list. Should sort to descending order
                    if (poly2.terms.get(z).getExp() < terms.get(i).getExp()) {
                        i = i + 1;
                    } else {
                        looping = false;
                    }
                }
                poly3.AddTerm(poly2.terms.get(z).getCoef(), poly2.terms.get(z).getExp());
            }
        }
        System.out.println(poly3);
    }

    public void subtraction(Polynomial poly2) {
        LinkedList exponents = new LinkedList<Integer>();
        Polynomial poly3 = new Polynomial(); //Both polynomials will add together to create a new polynomial

        for (int x = 0; x < terms.size(); x++) {
            int result = terms.get(x).getCoef();
            exponents.add(terms.get(x).getExp()); //Adds all exponents from first polynomial into a linkedlist containing only these exponents
            for (int y = 0; y < poly2.terms.size(); y++) {
                if (poly2.terms.get(y).getExp() == terms.get(x).getExp()) { //Iterates through second polynomial and checks to see if any exponents equal to the first polynomial
                    result -= poly2.terms.get(y).getCoef(); //Adds coefficients of polynomials with like-exponents together
                }
            }
            poly3.AddTerm(result, terms.get(x).getExp());
        }

        for (int z = 0; z < poly2.terms.size(); z++) { //Iterates through second polynomial list again
            if (!exponents.contains(poly2.terms.get(z).getExp())) { //Checks to see if an exponent exists in the exponents linkedlist for the second polynomial.
                int i = 0;                                          //Sorts and appends term from the second polynomial to the new linkedlist created earlier if exponent that exists in the second polynomial doesn't exist in the exponents linkedlist.
                boolean looping = true;
                while (looping && i < terms.size()) { //If i is less than exponent of the first polynomial's term, we move on through linked list. Should sort to descending order
                    if (poly2.terms.get(z).getExp() < terms.get(i).getExp()) {
                        i = i + 1;
                    } else {
                        looping = false;
                    }
                }
                poly3.AddTerm(poly2.terms.get(z).getCoef(), poly2.terms.get(z).getExp());
            }
        }
        System.out.println(poly3);
    }

    public void Multiply(Polynomial poly2) { //takes in secondary polynomial
        Polynomial poly3 = new Polynomial(); //Functional class
        Polynomial poly4 = new Polynomial(); //placeholder

        int Coef = 0;
        int Expo = 0;       //loop creates the multiplied coefficients and stores it in poly3
        for (int i = 0; i < this.terms.size(); i++) {
            for (int j = 0; j < poly2.terms.size(); j++) {
                Coef = this.terms.get(i).getCoef() * poly2.terms.get(j).getCoef();
                Expo = this.terms.get(i).getExp() + poly2.terms.get(j).getExp();
                poly3.AddTerm(Coef, Expo); //
            }
        }
        int n = poly3.terms.size(); //Reference pointer
        int temp = 0;
        for (int i = 0; i < n; i++) { //loop that starts from first index and checks backwards per iteration
            for (int j = 1; j < n - i; j++) {
                if (poly3.terms.get(j - 1).getExp() == poly3.terms.get(j).getExp()) {
                    temp = poly3.terms.get(j - 1).getCoef() + poly3.terms.get(j).getCoef();
                    poly3.terms.set(j, new Term(temp, poly3.terms.get(j - 1).getExp()));
                    poly3.terms.set(j - 1, new Term(0, 0));
                }
            }
        }

        for (int i = 0; i < poly3.terms.size(); i++) { //Loop that adds in the values into the place holder polynomial if the exponent and coefficient are not zero
            if (poly3.terms.get(i).getCoef() != 0 && poly3.terms.get(i).getExp() != 0) {
                poly4.AddTerm(poly3.terms.get(i).getCoef(), poly3.terms.get(i).getExp());
            }
            else{
                poly4.AddTerm(poly3.terms.get(i).getCoef(), poly3.terms.get(i).getExp());
            }
        }
        System.out.println(poly4);
    }
}