import environment.*;
import parser.*;
import tokens.Token;
import tokens.TokenBool;
import tokens.TokenOp;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * Command line Lisp interpreter.
 */
public class Jisp {
    public static Environment global;

    public static void main(String[] args) {
        global =  createGlobalEnv();

        if (args.length > 0) {
            System.out.println("Loading and executing startup scripts:");
            for (String filename : args) {
                try {
                    for (String line : Files.lines(Paths.get(filename)).collect(Collectors.toList())) {
                        Token token = Parser.parse(line);
                        global.eval(token);
                    }

                    System.out.printf("    [x] %s\n", filename);
                } catch (Exception e) {
                    System.out.printf("Failed to read file %s\n", filename);
                    e.printStackTrace();
                }
            }
        }

        System.out.println();
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
