package tokenGenerator.tokens;

import tokenGenerator.common.ETokenKey;
import tokenGenerator.common.IToken;

import java.util.Map;
import java.util.regex.Pattern;

public class MathematicalOperation implements IToken {
    private final ETokenKey TOKEN_KEY_MATH_OP = ETokenKey.MATHEMATICAL_OP;

    @Override
    public Boolean analyze(String value) {
        String digitsPart = "\\d+";
        String operationPart = "[+-/*]";

        Pattern mathOperationPatter = Pattern
                .compile("^" + digitsPart + operationPart + digitsPart + "$");

        return mathOperationPatter.matcher(value).matches();
    }

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(TOKEN_KEY_MATH_OP, value);
    }
}


