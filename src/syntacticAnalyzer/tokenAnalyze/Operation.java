package syntacticAnalyzer.tokenAnalyze;

import lexicalAnalyzer.common.ETokenKey;
import syntacticAnalyzer.common.ETense;
import syntacticAnalyzer.common.ITokenAnalyze;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Operation implements ITokenAnalyze {

    private final ETense OPERATION_TENSE_KEY = ETense.OPERATION;

    @Override
    public Map<ETense, List<String>> generateTense(List<Map<ETokenKey, String>> token) {
        return Map.of(OPERATION_TENSE_KEY, token.get(0).values().stream().toList());
    }

    @Override
    public Boolean analyze(ETokenKey value) {
        return true;
    }
}
