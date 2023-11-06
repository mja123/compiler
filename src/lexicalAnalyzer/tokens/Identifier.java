package lexicalAnalyzer.tokens;

import lexicalAnalyzer.common.ETokenKey;
import lexicalAnalyzer.common.IToken;

import java.util.Map;
import java.util.regex.Pattern;

public class Identifier implements IToken {
    private final ETokenKey TOKEN_KEY_ID = ETokenKey.ID;
    private static final Pattern LETTERS_PATTERN;
    private static final Pattern ALPHA_NUMERIC_PATTERN;


    static {
        ALPHA_NUMERIC_PATTERN = getLettersPattern();
        LETTERS_PATTERN = getAlphaNumericPattern();
    }

    public static Pattern getLettersPattern() {
        return Pattern.compile("^[a-z][\\da-z]+$", Pattern.CASE_INSENSITIVE);
    }

    public static Pattern getAlphaNumericPattern() {
        return Pattern.compile("^[a-z]+$", Pattern.CASE_INSENSITIVE);
    }

    private static boolean onlyLettersMatches(String target) {
        return LETTERS_PATTERN.matcher(target).matches();
    }

    private static boolean onlyAlphaNumericMatches(String target) {
        return ALPHA_NUMERIC_PATTERN.matcher(target).matches();
    }



    @Override
    public Boolean analyze(String value) {
        return !Reserved.isReservedWord(value) && (onlyLettersMatches(value) || onlyAlphaNumericMatches(value));
    }

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(TOKEN_KEY_ID, value);
    }
}
