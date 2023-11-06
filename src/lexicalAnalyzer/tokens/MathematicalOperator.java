package lexicalAnalyzer.tokens;

import lexicalAnalyzer.common.ETokenKey;
import lexicalAnalyzer.common.IToken;

import java.util.Map;
import java.util.regex.Pattern;

public class MathematicalOperator implements IToken {
    private final ETokenKey TOKEN_KEY_MATH_OP = ETokenKey.MATHEMATICAL_OP;
    private static final Pattern MATH_OP_PATTERN;

    static {
        MATH_OP_PATTERN = getMathOpPattern();
    }

    public static Pattern getMathOpPattern() {
        return Pattern.compile("^[-+/*]$");
    }

    @Override
    public Boolean analyze(String value) {
        return MATH_OP_PATTERN.matcher(value).matches();
    }

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(TOKEN_KEY_MATH_OP, value);
    }
}


