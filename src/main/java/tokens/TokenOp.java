package tokens;

import java.util.List;

public class TokenOp extends TokenFn {
    public String opStr;

    public TokenOp(String opStr) {
        this.opStr = opStr;

    }

    public Token exec(List<Token> vals) {
        if (vals.size() != 2) {
            String message = String.format("%s expected 2 arguments but got %d", opStr, vals.size());
            return new TokenError(message);
        }

        Token t1 = vals.get(0);
        Token t2 = vals.get(1);

        // Error handling first arg
        if (!(t1 instanceof TokenInt || t1 instanceof TokenFloat)) {
            String message = String.format("a must be an integer or float in (%s a b)", opStr);
            return new TokenError(message);
        }

        // Error handling second arg
        if (!(t2 instanceof TokenInt || t2 instanceof TokenFloat)) {
            String message = String.format("b must be an integer or float in (%s a b)", opStr);
            return new TokenError(message);
        }

        float v1 = Float.parseFloat(t1.toString());
        float v2 = Float.parseFloat(t2.toString());

        boolean isInt = (t1 instanceof TokenInt) && (t2 instanceof TokenInt);

        return switch (opStr) {
            case "+" -> (isInt) ? new TokenInt((int)(v1 + v2)) : new TokenFloat(v1 + v2);
            case "-" -> (isInt) ? new TokenInt((int)(v1 - v2)) : new TokenFloat(v1 - v2);
            case "*" -> (isInt) ? new TokenInt((int)(v1 * v2)) : new TokenFloat(v1 * v2);
            case "/" -> (isInt) ? new TokenInt((int)(v1 / v2)) : new TokenFloat(v1 / v2);
            case ">" -> new TokenBool(v1 > v2);
            case "<" -> new TokenBool(v1 < v2);
            case ">=" -> new TokenBool(v1 >= v2);
            case "<=" -> new TokenBool(v1 <= v2);
            case "=" -> new TokenBool(v1 == v2);
            default -> new TokenError("illegal operator: " + opStr);
        };
    }

    public String toString() {
        return opStr;
    }
}