package lexicalAnalyzer.tokens;

import lexicalAnalyzer.common.ETokenKey;
import lexicalAnalyzer.common.IToken;

import java.util.Map;
import java.util.regex.Pattern;

public class Number implements IToken {

    private final ETokenKey TOKEN_KEY_NUM = ETokenKey.NUM;
    private static final Pattern NUMBERS_PATTERN;

    static {
        NUMBERS_PATTERN = getNumbersPattern();
    }

    public static Pattern getNumbersPattern() {
        return Pattern.compile("^\\d+$");
    }

    @Override
    public Boolean analyze(String value) {
        return NUMBERS_PATTERN.matcher(value).matches();
    }

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(TOKEN_KEY_NUM, value);
    }
}
