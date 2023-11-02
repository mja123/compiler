package tokens;

import common.ETokenKey;
import common.IToken;

import java.util.Map;
import java.util.regex.Pattern;

public class Number implements IToken {

    private final ETokenKey TOKEN_KEY_NUM = ETokenKey.NUM;
    private final Pattern NUMBERS_PATTERN = Pattern.compile("^\\d+$");

    boolean onlyNumbersMatches(String target) {
        return NUMBERS_PATTERN.matcher(target).matches();
    }

    @Override
    public Boolean analyze(String value) {
        return false;
    }

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(TOKEN_KEY_NUM, value);
    }
}
