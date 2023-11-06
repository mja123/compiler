package lexicalAnalyzer.tokens;

import lexicalAnalyzer.common.ETokenKey;
import lexicalAnalyzer.common.IToken;

import java.util.Map;
import java.util.regex.Pattern;

public class AssignationOperator implements IToken {

    private final ETokenKey TOKEN_KEY_ASSIGNATION = ETokenKey.ASSIGNATION_OP;
    private static final Pattern ASSIGNATION_PATTERN;

    static {
        ASSIGNATION_PATTERN = getAssignationPattern();
    }

    public static Pattern getAssignationPattern() {
        return Pattern.compile("^=$");
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
