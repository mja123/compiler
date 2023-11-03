package tokenReader;

import tokenGenerator.TokensEntryPoint;
import tokenGenerator.common.ETokenKey;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            TokensEntryPoint.generateToken();
            List<List<Map<ETokenKey, String>>> tokens = TokensEntryPoint.getTotalTokens();
            tokens.forEach(lines -> {
                System.out.println("**** Line " + tokens.indexOf(lines) + " ****");
                lines.forEach(f -> f.entrySet().forEach(System.out::println));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
