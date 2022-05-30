import environment.*;
import parser.*;
import tokens.Token;
import tokens.TokenBool;
import tokens.TokenOp;

import java.util.Arrays;
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
        Environment global = createGlobalEnv();

        while (true) {
            System.out.print("jisp> ");
            try {
                // Read next line of input
                String input = scanner.nextLine();
                Token token = Parser.parse(input);
                Token result = global.eval(token);
                printToken(result);
            } catch (Exception e) {
                // Print stack trace and reset the scanner in case it's stuck in a weird state
                e.printStackTrace();
                scanner = new Scanner(System.in);
            }
        }
    }

    private static void printToken(Token token) {
        System.out.println(token.toString());
    }

    private static Environment createGlobalEnv() {
        Environment env = new Environment();
        env.assign("True", new TokenBool(true));
        env.assign("False", new TokenBool(false));
        for (String op : Arrays.asList("+","-","*","/",">","<",">=","<=","=")) {
            env.assign(op, new TokenOp(op));
        }
        return env;
    }
}
