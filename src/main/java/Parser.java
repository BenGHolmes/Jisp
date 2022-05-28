import java.util.*;

public class Parser {
    /** Parse a Lisp string into nested arrays of tokens **/
    public static List<String> parse(String s) {
        Deque<String> tokens = tokenize(s);
        System.out.println(tokens.pop());

        return new ArrayList<>();
    }

    private static Deque<String> tokenize(String s) {
        return new ArrayDeque<String>(Arrays.asList(
                s.replace("(", " ( ")
                        .replace(")", " ) ")
                        .split(" ")
                ));
    }
}
