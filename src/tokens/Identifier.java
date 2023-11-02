package tokens;

import common.ETokenKey;
import common.IToken;

import java.util.Map;
import java.util.regex.Pattern;

public class Identifier implements IToken {
    private final ETokenKey TOKEN_KEY_ID = ETokenKey.ID;
    private static final Pattern LETTERS_PATTERN = Pattern.compile("^[a-z]+$", Pattern.CASE_INSENSITIVE);
    private static final Pattern ALPHA_NUMERIC_PATTERN = Pattern.compile("^[a-z][\\da-z]+$", Pattern.CASE_INSENSITIVE);

    private static boolean onlyLettersMatches(String target) {
        return LETTERS_PATTERN.matcher(target).matches();
    }

    private static boolean onlyAlphaNumericMatches(String target) {
        return ALPHA_NUMERIC_PATTERN.matcher(target).matches();
    }

    public static Pattern getLettersPattern() {
        return LETTERS_PATTERN;
    }

    public static Pattern getAlphaNumericPattern() {
        return ALPHA_NUMERIC_PATTERN;
    }

    @Override
    public Boolean analyze(String value) {
        return onlyLettersMatches(value) || onlyAlphaNumericMatches(value);
    }

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(TOKEN_KEY_ID, value);
    }



    public static void main(String[] args) {
        String bla = "";
        Identifier id = new Identifier();
//        System.out.println("Numbers " + id.onlyNumbersMatches(bla));
        System.out.println("Letters " + id.onlyLettersMatches(bla));
        System.out.println("AlphaNumeric " + id.onlyAlphaNumericMatches(bla));
    }
}
