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

    // TODO: Write ADD Function

    // TODO: Write SUB Function
    // this will be the same as the add function just make sure to call two's
    // compliment of the second number

    // TODO: Write AND Function

    // TODO: Write OR Function

    // TODO: Write SLT Function

    // TODO: Write Two's Compliment Function

    // TODO: Write Zero Detection Funtion
}