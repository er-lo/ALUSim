// Erick Lopez
// Computer Systems
// Summer 2024
// 4-bit MIPS ALU Simulation in Java
//

import java.util.Scanner;

public class ALUSimulator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String inputString;
        String[] brokenUpString;
        boolean stillRunning = true;

        // loop program until exited
        while (stillRunning) {
            // get user input
            System.out.println();
            System.out.println("Input your command as a single line. e.g., Input Format: \"opcode operand1 operand2\"");
            System.out.println("Enter your input: ");
            inputString = input.nextLine();
            System.out.println();

            brokenUpString = inputString.split(" ");

            System.out.println(brokenUpString[0]);
            System.out.println(brokenUpString[1]);
            System.out.println(brokenUpString[2]);

        }
    }

    // TODO: Write Two's Compliment Function

    // TODO: Write ADD Function

    // TODO: Write SUB Function

    // TODO: Write AND Function

    // TODO: Write OR Function

    // TODO: Write SLT Function
}