package syntacticAnalyzer.tokenAnalyze;

import lexicalAnalyzer.common.ETokenKey;
import syntacticAnalyzer.common.ETense;
import syntacticAnalyzer.common.ITokenAnalyze;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Expression implements ITokenAnalyze {
    private final Pattern EXPRESSION_PATTERN;
    private final ETense EXPRESSION_TENSE_KEY;

    public Expression() {
        this.EXPRESSION_PATTERN = getEXPRESSION_PATTERN();
        this.EXPRESSION_TENSE_KEY = ETense.EXPRESSION;
    }

    private Pattern getEXPRESSION_PATTERN() {
        String operationToken = ETokenKey.CONDITIONAL_OP.toString();
        String operatorPattern = "(" + ETokenKey.ID + "|" + ETokenKey.NUM + "|" + ETense.OPERATION + ")";
        return Pattern.compile("^(" + operatorPattern + operationToken + operatorPattern + ")+$");
    }

    @Override
    public Map<ETense, List<String>> generateTense(List<String> tokenKeys, List<String> tokenValues) {
        String conditionalOperator = tokenValues.get(tokenKeys.indexOf(ETokenKey.CONDITIONAL_OP.toString()));

        List<String> values = new ArrayList<>();
        values.add(conditionalOperator);
        values.addAll(tokenValues.stream()
                .filter(v -> !v.equals(conditionalOperator))
                .toList());
        return Map.of(EXPRESSION_TENSE_KEY, values);
    }

    @Override
    public Boolean analyze(String value) {
        return EXPRESSION_PATTERN.matcher(value.replace(" ", "")).matches();
    }
}
