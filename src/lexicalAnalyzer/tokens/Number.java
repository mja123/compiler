package lexicalAnalyzer.tokens;

import lexicalAnalyzer.common.ETokenKey;
import lexicalAnalyzer.common.IToken;

import java.util.Map;
import java.util.regex.Pattern;

public class Number implements IToken {

    private final ETokenKey NUM_TOKEN_KEY = ETokenKey.NUM;
    private final Pattern NUMBERS_PATTERN;

    public Number() {
        NUMBERS_PATTERN = getNumbersPattern();
    }

    public Pattern getNumbersPattern() {
        return Pattern.compile("^\\d+$");
    }

    @Override
    public Boolean analyze(String value) {
        return NUMBERS_PATTERN.matcher(value).matches();
    }

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(NUM_TOKEN_KEY, value);
    }
}
