package syntacticAnalyzer.common;

import lexicalAnalyzer.common.ETokenKey;
import utils.ICompiler;

import java.util.List;
import java.util.Map;

public interface ITokenAnalyze extends ICompiler {
    Map<ETense, List<String>> generateTense(List<String> tokenKeys, List<String> tokenValues);
}
