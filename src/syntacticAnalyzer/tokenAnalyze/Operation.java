package syntacticAnalyzer.tokenAnalyze;

import lexicalAnalyzer.common.ETokenKey;
import syntacticAnalyzer.common.ETense;
import syntacticAnalyzer.common.ITokenAnalyze;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class Operation implements ITokenAnalyze {

    private final ETense OPERATION_TENSE_KEY = ETense.OPERATION;
    private static final Pattern OPERATION_PATTERN;

    static {
        OPERATION_PATTERN = getOperationPattern();
    }


    public static Pattern getOperationPattern() {
        String operatorPattern = "(" + ETokenKey.ID + "|" +ETokenKey.NUM + ")";
        String mathToken = ETokenKey.MATHEMATICAL_OP.toString();
        return Pattern.compile("^" + operatorPattern +  mathToken + operatorPattern + "$");
    }

    @Override
    public Map<ETense, List<String>> generateTense(List<String> tokenKeys, List<String> tokenValues) {
        List<String> values = new ArrayList<>();
        String mathOperator = tokenValues.get(tokenKeys.indexOf(ETokenKey.MATHEMATICAL_OP.toString()));
        values.add(mathOperator);
        values.addAll(tokenValues.stream().filter(v -> !v.equals(mathOperator)).toList());
        return Map.of(OPERATION_TENSE_KEY, values);
    }

    @Override
    public Boolean analyze(String value) {
        return getOperationPattern().matcher(value.replace(" ", "")).matches();
    }

}