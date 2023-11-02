package tokens;

import common.ETokenKey;
import common.IToken;

import java.util.Map;
import java.util.regex.Pattern;

public class NumericalAssignation implements IToken {

    private final ETokenKey TOKEN_KEY_ASSIGNATION = ETokenKey.ASSIGNATION_OP;
    private static final Pattern ASSIGNATION_PATTERN;

    static {
        ASSIGNATION_PATTERN = getAssignationPattern();
    }

    private static Pattern getAssignationPattern() {
        String assignationPart = "\\s*=\\s*";
        String identifierPart = "[\\da-z]+";
        String valuePart = "\\d+";
        return Pattern.compile(identifierPart + assignationPart + valuePart);
    }

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(TOKEN_KEY_ASSIGNATION, value);
    }

    @Override
    public Boolean analyze(String value) {
        return ASSIGNATION_PATTERN.matcher(value).matches();
    }

}
