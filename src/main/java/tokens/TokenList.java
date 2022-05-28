package tokens;

import java.util.List;

public class TokenList extends Token {
    public final List<Token> list;

    public TokenList(List<Token> tokens) {
        list = tokens;
    }

    public String toString() {
        StringBuilder out = new StringBuilder("[ ");
        for (Token t : list) {
            out.append(t.toString());
            out.append(" ");
        }

        out.append("]");
        return out.toString();
    }
}
