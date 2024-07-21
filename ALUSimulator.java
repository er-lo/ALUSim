// Erick Lopez
// Computer Systems
// Summer 2024
// 4-bit MIPS ALU Simulation in Java
//

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ALUSimulator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String opcode, fileName, inputString;
        int[] operand1, operand2;
        String[] brokenUpString = { "", "", "" };
        String[] fileStrings = new String[21];

        fileName = "";
        inputString = "";

        // code to accept file name argument
        if (args.length == 0) {
            System.out.println("Please enter the file name: ");
            fileName = input.nextLine();
        } else if (args.length == 1) {
            fileName = args[0];
        } else if (args.length >= 2) {
            System.out.println("Seems like too many arguments were entered. Only the first argument will be used.");
        }

        // open the file and store the line into a string array
        try {
            Scanner file = new Scanner(new File(fileName));

            int i = 0;
            while (file.hasNextLine()) {
                fileStrings[i] = file.nextLine();
                i++;
            }

            file.close();
        } catch (FileNotFoundException e) {
        }

        for (int i = 0; i < fileStrings.length - 1; i++) {
            System.out.println(fileStrings[i]);
            brokenUpString = fileStrings[i].split(" ");

            // perform checks on the user input
            opcode = checkOpcode(input, brokenUpString[0].toLowerCase());
            operand1 = checkAndConvertOperand(input, brokenUpString[1]);
            operand2 = checkAndConvertOperand(input, brokenUpString[2]);

            // call the ALU with correct opcode and operands
            ALU(opcode, operand1, operand2);
        }

        ////////////////////////////////////////////////////////////////////////////////
        // The code below was the initial code to accept arguments before I realized
        // that we needed to create an input file and read from that.
        // I decided to keep it in as we can just uncomment the code below and comment
        // the code above to execute however we want.
        ////////////////////////////////////////////////////////////////////////////////

        // code to accept arguments, cases for each length of argument input
        // also case for no arguments provided
        // if (args.length == 1) {
        // brokenUpString[0] = args[0];
        // System.out.println("Please enter the first operand");
        // brokenUpString[1] = input.nextLine();
        // System.out.println("Please enter the second operand");
        // brokenUpString[2] = input.nextLine();
        // } else if (args.length == 2) {
        // brokenUpString[0] = args[0];
        // brokenUpString[1] = args[1];
        // System.out.println("Please enter the second operand");
        // brokenUpString[2] = input.nextLine();
        // } else if (args.length == 3) {
        // brokenUpString[0] = args[0];
        // brokenUpString[1] = args[1];
        // brokenUpString[2] = args[2];
        // } else if (args.length > 3) {
        // brokenUpString[0] = args[0];
        // brokenUpString[1] = args[1];
        // brokenUpString[2] = args[2];
        // System.out.println("Too many arguments were entered. Only the first three
        // will be accepted.");
        // } else {
        // System.out.println("Input your command as a single line. e.g., Input Format:
        // \"opcode operand1 operand2\"");
        // System.out.println("Enter your input: ");
        // inputString = input.nextLine();
        // brokenUpString = inputString.split(" ");
        // }
        // perform checks on the user input
        // opcode = checkOpcode(input, brokenUpString[0].toLowerCase());
        // operand1 = checkAndConvertOperand(input, brokenUpString[1]);
        // operand2 = checkAndConvertOperand(input, brokenUpString[2]);

        // call the ALU with correct opcode and operands
        // ALU(opcode, operand1, operand2);

        // close scanner
        input.close();
    }

    ////////////////////////////////////////////////////////////////////////////////
    // Again the following function is really only used when arguments are entered
    // manually. It will check to ensure the opcode is a valid entry.
    ////////////////////////////////////////////////////////////////////////////////

    // Function to check the opcode input and correct the opcode if it's not
    // correct.
    public static String checkOpcode(Scanner input, String opcode) {
        // we check to see if the opcode entered matches any of the ones we set
        // if not we loop through until we get a correct one
        while (!(opcode.equals("add") || opcode.equals("sub") || opcode.equals("and") || opcode.equals("or")
                || opcode.equals("slt"))) {
            System.out.println("Issue with the opcode entered. Please enter valid opcode:");
            opcode = input.nextLine().toLowerCase();
        }

        return opcode;
    }

    ////////////////////////////////////////////////////////////////////////////////
    // Same case as the function above.
    // It will check to ensure the operands are valid entries.
    ////////////////////////////////////////////////////////////////////////////////

    // Function to check if operands are 4 bit and actually binary. Also converts to
    // an array of ints for calculations.
    public static int[] checkAndConvertOperand(Scanner input, String operand) {
        int[] convertedOperand = { 0, 0, 0, 0 };

        boolean isValidLength = false;
        // loop in case the operand is the 4 bit length
        while (!isValidLength) {

            if (operand.length() == 4) {
                isValidLength = true;
            } else {
                System.out.println("Not a valid binary number. Please enter a valid binary number:");
                operand = input.nextLine();
            }
        }

        boolean isValidBinary = false;

        // loop in case the value entered isn't actually binary
        // if any value is not equal to a 1 or 0 then we kick out of the for loop and
        // ask for a new binary value
        while (!isValidBinary) {
            for (int i = 0; i <= 3; i++) {
                convertedOperand[i] = Integer.parseInt(String.valueOf(operand.charAt(i)));
                if (convertedOperand[i] == 0 || convertedOperand[i] == 1) {
                    isValidBinary = true;
                } else {
                    isValidBinary = false;
                    break;
                }
            }

            if (isValidBinary == false) {
                System.out.println("Not a valid binary number. Please enter a valid binary number:");
                operand = input.nextLine();
            }
        }

        // return the binary value in an array to make life easier
        return convertedOperand;
    }

    public static void ALU(String opcode, int[] operand1, int[] operand2) {

        int[] resultingOperand = { 0, 0, 0, 0 }; // array to store final result
        int[] carryOut = { 0, 0, 0, 0 }; // array to store carryOut as we loop through the calculations
        int carryIn = 0; // initial carryIn is 0
        int overflowDetectionResult = 0; // int to store overflowDetection
        int zeroDetectionResult = 0; // int to store zerDetection
        String result = "";

        // first check is to see if sub or slt is being requested
        // if either is, we then performs two's compliment on the second operand
        // this makes life easier
        if (opcode.equals("sub") || opcode.equals("slt")) {
            operand2 = twosCompliment(operand2);
        }

        // Loop through each bit to perform actions bit by bit
        for (int i = 3; i >= 0; i--) {
            if (opcode.equals("add") || opcode.equals("sub") || opcode.equals("slt")) {
                // since add, sub and slt are essentially all doing the same thing we can run
                // all of the code in one place
                // with add we run the adder with the two original operands
                // with sub and slt we run the adder with the original operand1 and the two's
                // compliment of operand2
                carryOut[i] = adder(operand1[i], operand2[i], carryIn);
                if (carryOut[i] == 3) {
                    // if the result is = 3, then we had a case where
                    // operand1 = 1, operand2 = 1, carryIn = 1
                    // totalling 3, meaning we have a result of 1
                    // and a carryOut of 1
                    resultingOperand[i] = 1;
                    carryOut[i] = 1;
                    result = resultingOperand[i] + result;
                } else if (carryOut[i] == 2) {
                    // if the result is = 2, then we had a case where
                    // two values input were 1 and the third was 0
                    // totalling 2, meaning we have a result of 0
                    // and a carryOut of 1
                    resultingOperand[i] = 0;
                    carryOut[i] = 1;
                    result = resultingOperand[i] + result;
                } else if (carryOut[i] == 1) {
                    // if the result is = 1, then we had a case where
                    // two values input were 0 and the third was 1
                    // totalling 1, meaning we have a result of 1
                    // and a carryOut of 0
                    resultingOperand[i] = 1;
                    carryOut[i] = 0;
                    result = resultingOperand[i] + result;
                } else {
                    // if the result is = 0, then we had a case where
                    // all three values were 0
                    // totalling 0, meaning we have a result of 0
                    // and a carryOut of 0
                    resultingOperand[i] = 0;
                    carryOut[i] = 0;
                    result = resultingOperand[i] + result;
                }
                carryIn = carryOut[i];
            } else if (opcode.equals("and")) {
                // for "and" we call the andOperation function and store the results in the
                // resultingOperand array.
                // we also store the final result in the result string
                resultingOperand[i] = andOperation(operand1[i], operand2[i]);
                result = resultingOperand[i] + result;
            } else if (opcode.equals("or")) {
                // same as the "and" we call the Operation function and store the results in the
                // resultingOperand array.
                // we also store the final result in the result string
                resultingOperand[i] = orOperation(operand1[i], operand2[i]);
                result = resultingOperand[i] + result;
            }
        }

        // storing overflow and zero detection results after calling the functions.
        overflowDetectionResult = overflowDetection(carryOut);
        zeroDetectionResult = inverter(zeroDetection(resultingOperand));

        // printing out the final results in the format requested per the assignment
        // sheet.
        if (opcode.equals("slt")) {
            if (resultingOperand[0] == 1) {
                System.out.println(opcode.toUpperCase() + ": 0001");
            } else {
                System.out.println(opcode.toUpperCase() + ": 0000");
            }
        } else {
            System.out.println(
                    opcode.toUpperCase() + ": " + result + ", " + overflowDetectionResult + ", "
                            + zeroDetectionResult);
        }

        System.out.println();
    }

    public static int adder(int operand1, int operand2, int carryIn) {
        // this was the simplest function, just take in the three operands and add them
        // together
        return operand1 + operand2 + carryIn;
    }

    // No need to write a subtraction function as all we're doing is addition with
    // the two's compliment of the second operand.

    public static int andOperation(int operand1, int operand2) {
        // need to check if both operands are equal to one and if true return 1
        // if they both aren't 1 then we return 0
        if (operand1 == 1 && operand2 == 1) {
            return 1;
        }
        return 0;
    }

    public static int orOperation(int operand1, int operand2) {
        // need to check if both operands are equal to 1 and if true return 1
        // or if one operand is equal to 1 then return 1
        // if they both aren't then return 0
        if ((operand1 == 1 && operand2 == 1) || (operand1 == 1 && operand2 == 0) || (operand1 == 0 && operand2 == 1)) {
            return 1;
        }
        return 0;
    }

    public static int inverter(int operandBit) {
        // this inverts the bit that's passed in
        // 1 into 0, 0 into 1
        if (operandBit == 1) {
            return operandBit = 0;
        }
        return operandBit = 1;
    }

    public static int[] twosCompliment(int[] operand) {
        int[] one = { 0, 0, 0, 1 }; // array to store a representation of a 1 bit
        int[] carryOut = { 0, 0, 0, 0 }; // array to store carryOut as we loop through the calculations
        int[] twosCompliment = { 0, 0, 0, 0 }; // array to store the result two's compliment
        int carryIn = 0;

        // perform one's compliment of the provided operand
        for (int i = 0; i <= 3; i++) {
            operand[i] = inverter(operand[i]);
        }

        // add one to the one's compliment operand to complete two's compliment
        // this is the same procedure ran on addition and subtration
        for (int i = 3; i >= 0; i--) {
            carryOut[i] = adder(operand[i], one[i], carryIn);
            if (carryOut[i] == 3) {
                twosCompliment[i] = 1;
                carryOut[i] = 1;
            } else if (carryOut[i] == 2) {
                twosCompliment[i] = 0;
                carryOut[i] = 1;
            } else if (carryOut[i] == 1) {
                twosCompliment[i] = 1;
                carryOut[i] = 0;
            } else {
                twosCompliment[i] = 0;
                carryOut[i] = 0;
            }
            carryIn = carryOut[i];
        }
        return twosCompliment;
    }

    public static int zeroDetection(int[] result) {
        // need to check if any bits are one, if they are then we return a 1
        // and go ahead and break out of the loop to save resources
        // if all bits are zero we return 0 just like discussed in the lecture
        // this then gets inverted after this function is executed
        int zeroDetection = 0;
        for (int i = 0; i <= 3; i++) {
            if (result[i] == 1) {
                zeroDetection = 1;
                break;
            }
        }
        return zeroDetection;
    }

    public static int overflowDetection(int[] carryOut) {
        // need to check if the sign bit (position 0) flips per the lecture
        // this is checked by checking the last carryout and the next to last carryout
        // in this case this is carryOut[0] and carryOut[1]
        // perform an exclusive or on the two values
        // if either value is a 1 while the other is a 0 then we return 1
        // if values are both either 0s or 1s we return 0

        if ((carryOut[0] == 1 && carryOut[1] == 0) || (carryOut[0] == 0 && carryOut[1] == 1)) {
            return 1;
        }

        return 0;
    }
}