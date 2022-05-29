package environment;

import tokens.*;

import java.util.HashMap;

public class Environment {
    private HashMap<String, Token> env;
    private final Environment parent;

    public Environment() {
        env = new HashMap<>();
        parent = null;
    }

    public Environment(HashMap<String, Token> env) {
        this.env = new HashMap<>(env);
        parent = null;
    }

    public Environment(Environment parent) {
        env = new HashMap<>();
        this.parent = parent;
    }

    public Environment(HashMap<String, Token> env, Environment parent) {
        this.env = new HashMap<>(env);
        this.parent = parent;
    }

    public void assign(String name, Token token) {
        env.put(name, token);
    }

    public Token find(String name) throws EnvNotFoundException {
        Token res = env.get(name);
        if (res == null && parent != null) {res = parent.find(name);}
        if (res == null) {
            String message  = "couldn't find variable: " + name;
            throw new EnvNotFoundException(message);
        }

        return res;
    }

    public Token eval(Token token) throws EnvNotFoundException {
        if (token instanceof TokenSymbol) {
            // Variable reference
            return this.find(((TokenSymbol) token).value);
        } else if (!(token instanceof TokenList)) {
            // Constant literal
            return token;
        }

        // Must be a list at this point
        TokenList tokenList = (TokenList) token;
        String op = tokenList.first().toString();
        if (op.equals("quote") || op.equals("q")) {
            return tokenList.second();
        } else if (op.equals("atom?")) {
            Token res = eval(tokenList.second());
            return new TokenBool(!(res instanceof TokenList));
        } else if (op.equals("eq?")) {
            Token v1 = eval(tokenList.get(1));
            Token v2 = eval(tokenList.get(2));
            boolean equal = !(v1 instanceof TokenList) && v1.equals(v2);
            return new TokenBool(equal);
        }

        return new TokenSymbol("Not implemented yet :)");
    }
}
