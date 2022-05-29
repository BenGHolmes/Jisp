package tokens;

abstract public class Token {
    public abstract String toString();

    @Override
    public boolean equals(Object obj) {
        // Floats
        if (this instanceof TokenFloat && obj instanceof TokenFloat) {
            return ((TokenFloat) this).value == ((TokenFloat) obj).value;
        }

        // Integers
        if (this instanceof TokenInt && obj instanceof TokenInt) {
            return ((TokenInt) this).value == ((TokenInt) obj).value;
        }

        // Boolean
        if (this instanceof TokenBool && obj instanceof TokenBool) {
            return ((TokenBool) this).value == ((TokenBool) obj).value;
        }

        // Symbols
        if (this instanceof TokenSymbol && obj instanceof TokenSymbol) {
            return ((TokenSymbol) this).value.equals(((TokenSymbol) obj).value);
        }

        // Lists
        if (this instanceof TokenList && obj instanceof TokenList) {
            return ((TokenList) this).list.equals(((TokenList) obj).list);
        }

        return false;
    }
}





