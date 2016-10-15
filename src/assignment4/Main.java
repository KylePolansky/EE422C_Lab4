/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment4; // cannot be in default package
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        readInput();
        
        /* Write your code above */
        System.out.flush();

    }

    /**
     * Loop that reads input from kb and processes the input commands
     */
    private static void readInput() {
        while (true) {
            System.out.print("critters>");
            String input = kb.nextLine();
            String[] inputSplit = input.split("\\s");
            try {
                switch (inputSplit[0]) {
                    case "quit":
                        if (checkInputLength(input,1)) {
                            System.exit(0);
                        }
                        break;
                    case "show":
                        if (checkInputLength(input,1)) {
                            Critter.displayWorld();
                        }
                        break;
                    case "step":
                        if (checkInputLength(input,1,2)) {
                            int numSteps = 1;
                            if (inputSplit.length == 2) {
                                numSteps = Integer.parseInt(inputSplit[1]);
                            }
                            for (int i = 0; i < numSteps; i++) {
                                Critter.worldTimeStep();
                            }
                        }
                        break;
                    case "seed":
                        if (checkInputLength(input,2)) {
                        	Critter.setSeed(Integer.parseInt(inputSplit[1]));
                        }
                        break;
                    case "make":
                        if (checkInputLength(input,2,3)) {
                            int numCritters = 1;
                            if (inputSplit.length == 3) {
                                numCritters = Integer.parseInt(inputSplit[2]);
                            }
                            for (int i = 0; i < numCritters; i++) {
                                Critter.makeCritter(myPackage + "." + inputSplit[1]); //prepend the package name
                            }
                        }
                        break;
                    case "stats":
                        if (checkInputLength(input,2)) {
                            String classname = myPackage + "." + inputSplit[1];

                            List<Critter> critters = Critter.getInstances(classname);
                            Class.forName(classname).getMethod("runStats", java.util.List.class).invoke(null,critters);
                        }
                        break;
                    default:
                        printInvalidOutput(input);
                        break;
                }
            } catch (Exception e) {
                System.out.println("error processing: " + input);
            }
        }
    }

    /**
     * Check the input length (number of parameters)
     * @param command the command to check
     * @param terms the number of terms, including the command keyword
     * @return true if the input length is valid, false and print an error message if it is not valid
     */
    private static boolean checkInputLength(String command, int terms) {
        return checkInputLength(command, terms, terms);
    }

    /**
     * Check the input length (number of parameters)
     * @param command the command to check
     * @param terms the number of terms, including the command keyword
     * @param optionalTerms If a command has 2 forms (ex. optional parameters), the 2nd number of parameters
     * @return true if the input length is valid, false and print an error message if it is not valid
     */
    private static boolean checkInputLength(String command, int terms, int optionalTerms) {
        if (command.split("\\s").length == terms || command.split("\\s").length == optionalTerms) {
            return true;
        }
        printInvalidOutput(command);
        return false;
    }

    /**
     * Print invalid command
     * @param command the command that is invalid
     */
    private static void printInvalidOutput(String command) {
        System.out.println("invalid command: " + command);
    }
}
