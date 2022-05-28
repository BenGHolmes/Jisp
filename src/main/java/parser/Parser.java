package parser;

import java.util.*;

public class Parser {
    /** Parse a Lisp string into nested arrays of tokens **/
    public static List<String> parse(String s) {
        Deque<String> tokens = tokenize(s);
        int i=1;
        while(!tokens.isEmpty()) {
            System.out.printf("%d: %s\n", i++, tokens.pop());
        }

        return new ArrayList<>();
    }

    private static Deque<String> tokenize(String s) {
        return new ArrayDeque<String>(Arrays.asList(
                s.replace("(", " ( ")
                        .replace(")", " ) ")
                        .trim()
                        .split(" ")
                ));
    }
}
