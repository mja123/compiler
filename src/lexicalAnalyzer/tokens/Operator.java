package lexicalAnalyzer.tokens;

import lexicalAnalyzer.common.ETokenKey;
import lexicalAnalyzer.common.IToken;

import java.util.Arrays;
import java.util.Map;

public class Operator implements IToken {

    private final EOperator[] OPERATORS = EOperator.values();
    private final ETokenKey TOKEN_KEY_OPERATOR =  ETokenKey.CONDITIONAL_OP;

    @Override
    public Boolean analyze(String value) {
        return Arrays.stream(OPERATORS).anyMatch(o -> value.contains(o.getValue()));
    }

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(TOKEN_KEY_OPERATOR, value);
    }

    private enum EOperator {
        GRATER(">"),
        GRATER_EQUALS(">="),
        LESS("<"),
        LESS_EQUALS("<="),
        COMPARISON("=="),
        AND("&&"),
        OR("||");

        final String value;

        EOperator(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
