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

    public Token get(int index) {
        return list.get(index);
    }

    public Token first() {
        return list.get(0);
    }

    public Token second() {
        return list.get(1);
    }

    public Token tail() {
        List<Token> tail = list.subList(1, list.size());
        return new TokenList(tail);
    }
}
