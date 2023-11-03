package tokenGenerator;

import tokenGenerator.tokens.Reserved;
import tokenGenerator.utils.ReflectionUtils;
import tokenGenerator.common.ETokenKey;
import tokenGenerator.common.IToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TokensEntryPoint {
    final static String INPUT_FILE_PATH = System.getProperty("user.dir")+"/src/tokenGenerator/resources/InputValues.txt";
    final static String TOKENS_PACKAGE = "tokenGenerator.tokens";
    final static List<List<Map<ETokenKey, String>>> totalTokens = new LinkedList<>();

    private TokensEntryPoint() {}

    public static void generateToken() throws IOException {
        readText().forEach(line -> {
            List<Map<ETokenKey, String>> tokensPerLine = new ArrayList<>();
            String[] lineSplit;
            if (Reserved.isReservedWord(line.split("\\s")[0])) {
                lineSplit = line.split("\\s");
            } else {
                lineSplit = line.split("\n");
            }
            Arrays.stream(lineSplit).forEach(w -> {
                final String value = w.replace(" ","");
                ReflectionUtils.findAllClassesUsingClassLoader(TOKENS_PACKAGE).stream().filter(l -> {
                    try {
                        IToken token = instantiateTokenClass(l);
                        return token.analyze(value);
                    } catch (IllegalAccessException e) {
                        System.out.println("Problem with class " + l.getName() + " error: " + e.getMessage());
                        return false;
                    }
                }).findFirst().ifPresent(f -> {
                    try {
                        tokensPerLine.add(instantiateTokenClass(f).generateToken(value));
                    } catch (IllegalAccessException e) {
                        System.out.println("Problem with class " + f.getName() + " error: " + e.getMessage());
                    }
                });
            });
            if (!tokensPerLine.isEmpty()) totalTokens.add(tokensPerLine);
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

    private static IToken instantiateTokenClass(Class<?> targetClass) throws IllegalAccessException {
        Optional<Constructor<?>> defaultConstructor = Arrays.stream(targetClass.getConstructors()).
                filter(c -> c.getParameterCount() == 0)
                .findFirst();

        if (defaultConstructor.isPresent()) {
            try {
                return (IToken) defaultConstructor.get().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                System.out.println("We couldn't create a new instance of " + targetClass.getName());
            }
        }
        throw new IllegalAccessException("Default constructor is not present");
    }


    public static List<List<Map<ETokenKey, String>>> getTotalTokens() {
        return  totalTokens;
    }
}