package lexicalAnalyzer.tokens;

import lexicalAnalyzer.common.ETokenKey;
import lexicalAnalyzer.common.IToken;

import java.util.Map;
import java.util.regex.Pattern;

public class Identifier implements IToken {
    private final ETokenKey ID_TOKEN_KEY = ETokenKey.ID;
    private final Pattern LETTERS_PATTERN;
    private final Pattern ALPHA_NUMERIC_PATTERN;

    public Identifier() {
        ALPHA_NUMERIC_PATTERN = getLettersPattern();
        LETTERS_PATTERN = getAlphaNumericPattern();
    }

    public Pattern getAlphaNumericPattern() {
        return Pattern.compile("^[a-z][\\da-z]+$", Pattern.CASE_INSENSITIVE);
    }

    public Pattern getLettersPattern() {
        return Pattern.compile("^[a-z]+$", Pattern.CASE_INSENSITIVE);
    }

    private boolean onlyLettersMatches(String target) {
        return LETTERS_PATTERN.matcher(target).matches();
    }

    private boolean onlyAlphaNumericMatches(String target) {
        return ALPHA_NUMERIC_PATTERN.matcher(target).matches();
    }


    // Matching No-reserved words with only letters or alphanumeric words started by a letter
    @Override
    public Boolean analyze(String value) {
        return !Reserved.isReservedWord(value) && (onlyLettersMatches(value) || onlyAlphaNumericMatches(value));
    }

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(ID_TOKEN_KEY, value);
    }
}
