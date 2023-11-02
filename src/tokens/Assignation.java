package tokens;

import common.ETokenKey;
import common.IToken;

import java.util.Map;

public class Assignation implements IToken {

    private final ETokenKey TOKEN_KEY_ASSIGNATION = ETokenKey.ASSIGNATION_OP;

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(TOKEN_KEY_ASSIGNATION, value);
    }

    @Override
    public Boolean analyze(String value) {
        return false;
    }
}
