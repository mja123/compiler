package lexicalAnalyzer.common;

import java.util.Map;
import utils.ICompiler;

public interface IToken extends ICompiler {
    Map<ETokenKey, String> generateToken(String value);
}
