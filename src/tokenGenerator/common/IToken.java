package tokenGenerator.common;

import java.util.Map;

public interface IToken {
    Boolean analyze(String value);
    Map<ETokenKey, String> generateToken(String value);
}
