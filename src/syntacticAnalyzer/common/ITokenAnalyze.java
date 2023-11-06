package syntacticAnalyzer.common;

import lexicalAnalyzer.common.ETokenKey;
import utils.ICompiler;

import java.util.List;
import java.util.Map;

public interface ITokenAnalyze extends ICompiler<ETokenKey> {
    Map<ETense, List<String>> generateTense(List<Map<ETokenKey,String>> token);
}
