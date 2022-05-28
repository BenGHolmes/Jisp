package parser;

import tokens.*;

import java.util.*;

public class Parser {
    /** Parse a Lisp string into nested arrays of tokens **/
    public static Token parse(String s) throws ParserException {
        Deque<String> tokens = tokenize(s);
        return buildTree(tokens);
    }

    /** Split an input string into a list of individual tokens **/
    private static Deque<String> tokenize(String s) {
        return new ArrayDeque<String>(Arrays.asList(
                s.replace("(", " ( ")
                        .replace(")", " ) ")
                        .trim()
                        .split(" ")
                ));
    }

    private static Token buildTree(Deque<String> tokenStrings) throws ParserException {
        if (tokenStrings.isEmpty()) {throw new ParserException();}

        String next = tokenStrings.pop();
        if (next.equals("(")) {
            // Start of a list
            List<Token> tokens = new ArrayList<>();

            // While we haven't reached the matching end, add tokens to this list
            while (!tokenStrings.isEmpty() && !tokenStrings.peek().equals(")")) {
                tokens.add(buildTree(tokenStrings));
            }

            // Make sure we didn't run out of input inside this list
            if (tokenStrings.isEmpty()) {throw new ParserException();}

            // Pop the closing brace
            tokenStrings.pop();

            // Return token
            return new TokenList(tokens);

        } else if (isInt(next)) {
            return new TokenInt(Integer.parseInt(next));
        } else if (isFloat(next)) {
            return new TokenFloat(Float.parseFloat(next));
        } else {
            return new TokenSymbol(next);
        }
    }

    private static boolean isFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
