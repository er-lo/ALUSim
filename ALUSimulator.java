// Erick Lopez
// Computer Systems
// Summer 2024
// 4-bit MIPS ALU Simulation in Java
//

import java.util.Scanner;

public class ALUSimulator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String inputString, opcode;
        int[] operand1, operand2;
        String[] brokenUpString;
        boolean stillRunning = true;

        // loop program until exited
        while (stillRunning) {
            inputString = "";
            // get user input
            System.out.println();
            System.out.println("Input your command as a single line. e.g., Input Format: \"opcode operand1 operand2\"");
            System.out.println("Enter your input: ");
            inputString = input.nextLine();
            System.out.println();

            brokenUpString = inputString.split(" ");
            opcode = checkOpcode(input, brokenUpString[0].toLowerCase());
            operand1 = checkAndConvertOperand(input, brokenUpString[1]);
            operand2 = checkAndConvertOperand(input, brokenUpString[2]);

            if (opcode.equals("add")) {
                System.out.println(java.util.Arrays.toString(add(operand1, operand2)));
            }

            if (opcode.equals("sub")) {
                System.out.println(java.util.Arrays.toString(add(operand1, twosCompliment(operand2))));
            }
            // System.out.println("Output: " + opcode + " " +
            // java.util.Arrays.toString(operand1) + " "
            // + java.util.Arrays.toString(operand2));

            stillRunning = false;

        }

        input.close();
    }

    // Function to check the opcode input and correct opcode if it's not correct.
    public static String checkOpcode(Scanner input, String opcode) {
        while (!(opcode.equals("add") || opcode.equals("sub") || opcode.equals("and") || opcode.equals("or")
                || opcode.equals("slt"))) {
            System.out.println("Issue with the opcode entered. Please enter valid opcode:");
            opcode = input.nextLine().toLowerCase();
        }

        return opcode;
    }

    // Function to check if operands are 4 bit and actually binary. Also converts to
    // an array of ints for calculations.
    public static int[] checkAndConvertOperand(Scanner input, String operand) {
        int[] convertedOperand = { 0, 0, 0, 0 };

        boolean isValidLength = false;
        while (!isValidLength) {

            if (operand.length() == 4) {
                isValidLength = true;
            } else {
                System.out.println("Not a valid binary number. Please enter a valid binary number:");
                operand = input.nextLine();
            }
        }

        boolean isValidBinary = false;

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

        return convertedOperand;
    }

    public static void ALU(String opcode, int[] operand1, int[] operand2) {}

    // TODO: Write ADD Function
    public static int[] add(int[] operand1, int[] operand2){
        int[] resultedOperand = { 0, 0, 0, 0 };
        int[] carryOut = { 0, 0, 0, 0 };
        int carryIn = 0;

        for (int i = 3; i >= 0; i-- ){
            carryOut[i] = operand1[i] + operand2[i] + carryIn;
            if (carryOut[i] == 2) {
                resultedOperand[i] = 0;
                carryOut[i] = 1;
            } else if (carryOut[i] == 1) {
                resultedOperand[i] = 1;
                carryOut[i] = 0;
            } else {
                resultedOperand[i] = 0;
                carryOut[i] = 0;
            }
            carryIn = carryOut[i];
        }


        return resultedOperand;
    }

    // No need to write a subtraction function as all we're doing is addition with the two's compliment of the second operand.

    // TODO: Write AND Function

    // TODO: Write OR Function

    // TODO: Write SLT Function

    // TODO: Write Two's Compliment Function
    public static int[] twosCompliment(int[] operand){
        int[] one = { 0, 0, 0, 1 };
        for(int i = 0; i <= 3; i++) {
            if (operand[i] == 1) {
                operand[i] = 0;
            } else {
                operand[i] = 1;
            }
        }
        return add(operand, one);
    }

    // TODO: Write Zero Detection Funtion
}