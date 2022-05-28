import java.util.Scanner;

/**
 * Command line Lisp interpreter.
 */
public class Jisp {
    public static void main(String[] args) {
        // TODO: If file given, load that first

        promptLoop();
    }

    /**
     *
     */
    private static void promptLoop() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("jisp> ");
            try {
                String input = scanner.nextLine();
                System.out.println(input);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
