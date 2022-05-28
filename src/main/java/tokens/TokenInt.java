package tokens;

public class TokenInt extends Token {
    public final int value;

    public TokenInt(int token) {
        value = token;
    }

    public String toString() {
        return String.format("%d", value);
    }
}
