package environment;

import tokens.*;

import java.util.HashMap;
import java.util.List;

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

    public Token find(String name) {
        // Check this env
        Token res = env.get(name);

        // If not found, check parent env
        if (res == null && parent != null) {
            res = parent.find(name);
        }

        // If not found, return error
        if (res == null) {
            res = new TokenError("couldn't find variable: " + name);
        }

        return res;
    }

    public Token eval(Token token) {
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
        return switch (op) {
            case "quote", "q" -> evalQuote(tokenList);
            case "atom?" -> evalAtom(tokenList);
            case "eq?" -> evalEq(tokenList);
            case "car" -> evalCar(tokenList);
            case "cdr" -> evalCdr(tokenList);
            case "cons" -> evalCons(tokenList);
            case "cond" -> evalCond(tokenList);
            case "null?" -> evalNull(tokenList);
            case "if" -> evalIf(tokenList);
            case "set!" -> evalSet(tokenList);
            case "define" -> evalDefine(tokenList);
            default -> new TokenError("unrecognized command: " + op);
        };
    }

    private Token evalQuote(TokenList tokenList) {
        return tokenList.second();
    }

    private Token evalAtom(TokenList tokenList) {
        Token res = eval(tokenList.second());
        return new TokenBool(!(res instanceof TokenList));
    }

    private Token evalEq(TokenList tokenList) {
        Token v1 = eval(tokenList.get(1));
        Token v2 = eval(tokenList.get(2));
        boolean equal = !(v1 instanceof TokenList) && v1.equals(v2);
        return new TokenBool(equal);
    }

    private Token evalCar(TokenList tokenList) {
        Token res = eval(tokenList.second());
        if (res instanceof TokenList) {
            TokenList resList = (TokenList) res;
            if (resList.list.size() < 1) return new TokenError("attempted to get first element of empty list");
            else return resList.first();
        } else {
            return new TokenError("attempted to get first element of atomic");
        }
    }

    private Token evalCdr(TokenList tokenList) {
        Token res = eval(tokenList.second());
        if (res instanceof TokenList) {
            TokenList resList = (TokenList) res;
            if (resList.list.size() < 1) return new TokenError("attempted to get first element of empty list");
            else return resList.tail();
        } else {
            return new TokenError("attempted to get first element of atomic");
        }
    }

    private Token evalCons(TokenList tokenList) {
        Token v1 = eval(tokenList.get(1));
        Token v2 = eval(tokenList.get(2));
        if (! (v2 instanceof TokenList)) {
            return new TokenError("exp2 in (cons exp1 exp2) must evaluate to a list");
        }

        List<Token> tokens = ((TokenList) v2).list;
        tokens.add(0, v1);

        return new TokenList(tokens);
    }

    private Token evalCond(TokenList tokenList) {
        TokenBool tokenTrue = new TokenBool(true);
        for (Token em : tokenList.tail().list) {
            if (! (em instanceof TokenList)) return new TokenError("invalid use of (cond (p1 e1) ... (pn en))");
            TokenList emList = (TokenList) em;
            Token p = emList.first();
            Token e = emList.second();

            if (eval(p).equals(tokenTrue)) {
                return eval(e);
            }
        }

        return new TokenError("no branch of cond evaluated to true");
    }

    private Token evalNull(TokenList tokenList) {
        Token res = eval(tokenList.second());
        boolean isNull = (res instanceof TokenList) && (((TokenList) res).list.size() == 0);
        return new TokenBool(isNull);
    }

    private Token evalIf(TokenList tokenList) {
        TokenBool tokenTrue = new TokenBool(true);
        Token cond = eval(tokenList.second());
        if (cond.equals(tokenTrue)) {
            return eval(tokenList.get(2));
        } else {
            return eval(tokenList.get(3));
        }
    }

    private Token evalSet(TokenList tokenList) {
        String var = tokenList.second().toString();
        Token val = eval(tokenList.get(2));

        if (find(var) instanceof TokenError) {
            return new TokenError("attempted to set undefined variable: " + var);
        }

        // FIXME: Must be able to update variables in parent env too
        env.put(var, val);
        return val;
    }

    private Token evalDefine(TokenList tokenList) {
        String var = tokenList.second().toString();
        Token val = eval(tokenList.get(2));

        env.put(var, val);
        return val;
    }
}
