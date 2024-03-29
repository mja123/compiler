package lexicalAnalyzer.tokens;

import lexicalAnalyzer.common.ETokenKey;
import lexicalAnalyzer.common.IToken;

import java.util.Arrays;
import java.util.Map;

public class Reserved implements IToken {

    private final ETokenKey RESERVED_TOKEN_KEY = ETokenKey.RESERVED;
    private static final EReservedWords[] RESERVED_WORDS = EReservedWords.values();

    public static boolean isReservedWord(String value) {
        return Arrays.stream(RESERVED_WORDS).anyMatch(r -> r.getValue().equalsIgnoreCase(value));
    }

    @Override
    public Map<ETokenKey, String> generateToken(String value) {
        return Map.of(RESERVED_TOKEN_KEY, value);
    }


    // Checking if value is a reserved word
    @Override
    public Boolean analyze(String value) {
        return isReservedWord(value);
    }

    private enum EReservedWords {
        IF("if"),
        ELSE("else"),
        NULL("null"),
        DEF("def")
        ;

        private final String value;

        EReservedWords(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}

