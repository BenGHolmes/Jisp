package tokens;

public class TokenSymbol extends Token {
    public final String value;

    public TokenSymbol(String token) {
        value = token;
    }

    public String toString() {
        return value;
    }
}
