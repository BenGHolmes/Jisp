package tokens;

public class TokenBool extends Token {
    public final boolean value;

    public TokenBool(boolean token) {
        value = token;
    }

    public String toString() {
        return value ? "True" : "False";
    }
}
