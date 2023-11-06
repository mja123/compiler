package lexicalAnalyzer.tokens;

import lexicalAnalyzer.common.ETokenKey;
import lexicalAnalyzer.common.IToken;

import java.util.Map;
import java.util.regex.Pattern;

public class MathematicalOperator implements IToken {
    private final ETokenKey MATH_OP_TOKEN_KEY = ETokenKey.MATHEMATICAL_OP;
    private final Pattern MATH_OP_PATTERN;

    public MathematicalOperator() {
        MATH_OP_PATTERN = getMathOpPattern();
    }

    public Pattern getMathOpPattern() {
        return Pattern.compile("^[-+/*]$");
    }

    @Override
    public Boolean analyze(String value) {
        return MATH_OP_PATTERN.matcher(value).matches();
    }

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(MATH_OP_TOKEN_KEY, value);
    }
}


