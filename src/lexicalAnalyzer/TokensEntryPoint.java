package lexicalAnalyzer;

import lexicalAnalyzer.tokens.Reserved;
import utils.ReflectionUtils;
import lexicalAnalyzer.common.ETokenKey;
import lexicalAnalyzer.common.IToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static utils.ReflectionUtils.instantiateCompilerClass;

public class TokensEntryPoint {
    final static String INPUT_FILE_PATH = System.getProperty("user.dir")+"/src/lexicalAnalyzer/resources/InputValues.txt";
    final static String TOKENS_PACKAGE = "lexicalAnalyzer.tokens";
    final static String TOKEN_INTERFACE = "IToken";
    final static List<List<Map<ETokenKey, String>>> totalTokens = new LinkedList<>();

    private TokensEntryPoint() {}

    public static void generateToken() throws IOException {
        readText().forEach(line -> {
            List<Map<ETokenKey, String>> tokensPerLine = new ArrayList<>();
            Arrays.stream(line.split("\\s")).forEach(w -> {
                final String value = w;
                ReflectionUtils.findAllClassesUsingClassLoader(TOKENS_PACKAGE, TOKEN_INTERFACE).stream().filter(l -> {
                    try {
                        IToken token = instantiateCompilerClass(l);
                        return token.analyze(value);
                    } catch (IllegalAccessException e) {
                        System.out.println("Problem with class " + l.getName() + " error: " + e.getMessage());
                        return false;
                    }
                }).findFirst().ifPresent(f -> {
                    try {
                        IToken token = instantiateCompilerClass(f);
                        tokensPerLine.add(token.generateToken(value));
                    } catch (IllegalAccessException e) {
                        System.out.println("Problem with class " + f.getName() + " error: " + e.getMessage());
                    }
                });
            });
            totalTokens.add(tokensPerLine);
        });
    }

    private static List<String> readText() throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("There was some problem reading the file");
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }



    public static List<List<Map<ETokenKey, String>>> getTotalTokens() {
        return  totalTokens;
    }
}