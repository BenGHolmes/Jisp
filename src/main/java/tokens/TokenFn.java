package tokens;

import java.util.List;

abstract public class TokenFn extends Token {


    abstract public Token exec(List<Token> vals);
}
