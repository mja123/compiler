package lexicalAnalyzer.tokens;

import lexicalAnalyzer.common.ETokenKey;
import lexicalAnalyzer.common.IToken;

import java.util.Map;
import java.util.regex.Pattern;

public class AssignationOperator implements IToken {

    private final ETokenKey ASSIGNATION_TOKEN_KEY = ETokenKey.ASSIGNATION_OP;
    private final Pattern ASSIGNATION_PATTERN;

    public AssignationOperator() {
        this.ASSIGNATION_PATTERN = getAssignationPattern();
    }


    public Pattern getAssignationPattern() {
        return Pattern.compile("^=$");
    }

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(ASSIGNATION_TOKEN_KEY, value);
    }

    @Override
    public Boolean analyze(String value) {
        return ASSIGNATION_PATTERN.matcher(value).matches();
    }

}
