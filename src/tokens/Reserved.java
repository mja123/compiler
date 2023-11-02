package tokens;

import common.ETokenKey;
import common.IToken;

import java.util.Map;

public class Reserved implements IToken {

    private final ETokenKey TOKEN_KEY_RESERVED = ETokenKey.RESERVED;

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(TOKEN_KEY_RESERVED, value);
    }

    @Override
    public Boolean analyze(String value) {
        return false;
    }
}
