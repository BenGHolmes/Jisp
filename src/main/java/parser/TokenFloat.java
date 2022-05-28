package parser;

public class TokenFloat extends Token {
    public final float value;

    public TokenFloat(float token) {
        value = token;
    }

    public String toString() {
        return String.format("%f", value);
    }
}
