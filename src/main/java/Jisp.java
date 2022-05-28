import java.util.Scanner;


/**
 * Command line Lisp interpreter.
 */
public class Jisp {
    public static void main(String[] args) {
        // TODO: If file given, load that first

        promptLoop();
    }

    /** Infinite prompt-read-eval-print loop for the interpreter. **/
    private static void promptLoop() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("jisp> ");
            try {
                // Read next line of input
                String input = scanner.nextLine();
                Parser.parse(input);

                // Echo for now. TODO: Parse and execute
                System.out.println(input);
            } catch (Exception e) {
                // Print stack trace and reset the scanner in case it's stuck in a weird state
                e.printStackTrace();
                scanner = new Scanner(System.in);
            }
        }
    }
}
