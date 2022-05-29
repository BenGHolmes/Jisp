package tokens;

public class TokenError extends Token {
    public final String message;
    public TokenError(String token) {
        message = token;
    }

    public String toString() {
        return "ERROR: " + message;
    }
}
