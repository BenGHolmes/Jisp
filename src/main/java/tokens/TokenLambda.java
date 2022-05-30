package tokens;

import environment.Environment;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TokenLambda extends TokenFn {
    public final Environment parentEnv;
    public final List<String> args;
    public final Token exp;

    public TokenLambda(Token exp, List<Token> args, Environment parentEnv) {
        this.exp = exp;
        this.args = args.stream().map(Token::toString).collect(Collectors.toList());
        this.parentEnv = parentEnv;
    }

    public Token exec(List<Token> vals) {
        if (args.size() != vals.size()) {
            String message = String.format("expected %d arguments but got %d", args.size(), vals.size());
            return new TokenError(message);
        }

        HashMap<String, Token> vars = new HashMap<>();
        for (int i=0; i<args.size(); i++) {
            vars.put(args.get(i), parentEnv.eval(vals.get(i)));
        }

        Environment env = new Environment(vars, parentEnv);

        return env.eval(exp);
    }

    public String toString() {
        return String.format("lambda (%s) %s", String.join(" ", args), exp.toString());
    }
}
