package syntacticAnalyzer.tokenAnalyze;

import lexicalAnalyzer.common.ETokenKey;
import syntacticAnalyzer.common.ETense;
import syntacticAnalyzer.common.ITokenAnalyze;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Assignation implements ITokenAnalyze {
    private final Pattern ASSIGNATION_PATTERN;
    private final ETense ASSIGNATION_TENSE_KEY;

    public Assignation() {
        this.ASSIGNATION_PATTERN = getASSIGNATION_PATTERN();
        this.ASSIGNATION_TENSE_KEY = ETense.ASSIGNATION;
    }

    // ID = [ID,NUM,OPERATION]
    private Pattern getASSIGNATION_PATTERN() {
        String identifierToken = ETokenKey.ID.toString();
        String assignationToken = ETokenKey.ASSIGNATION_OP.toString();
        String valuePattern = "(" + ETokenKey.ID + "|" + ETokenKey.NUM + "|" + ETense.OPERATION + ")";
        return Pattern.compile("^" + identifierToken + assignationToken + valuePattern + "$");
    }

    @Override
    public Map<ETense, List<String>> generateTense(List<String> tokenKeys, List<String> tokenValues) {
        List<String> values = new ArrayList<>(tokenValues.stream()
                .filter(v -> !v.equals(tokenValues.get(tokenKeys.indexOf(ETokenKey.ASSIGNATION_OP.toString()))))
                .toList());
        return Map.of(ASSIGNATION_TENSE_KEY, values);
    }

    @Override
    public Boolean analyze(String value) {
        return ASSIGNATION_PATTERN.matcher(value.replace(" ", "")).matches();
    }
}
