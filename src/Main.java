import Utils.ReflectionUtils;
import common.ETokenKey;
import common.IToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        List<Map<ETokenKey, String>> tokens = new ArrayList<>();
        final String INPUT_FILE_PATH = System.getProperty("user.dir")+"/src/resources/InputValues.txt";
        final String TOKENS_PACKAGE = "tokens";

        try(BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while((line = reader.readLine()) != null) {
                final String value = line;
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
                        tokens.add(instantiateTokenClass(f).generateToken(value));
                    } catch (IllegalAccessException e) {
                        System.out.println("Problem with class " + f.getName() + " error: " + e.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            System.out.println("There was some problem reading the file");
            System.out.println("Error: " + e.getMessage());
        }
        tokens.forEach(a -> a.entrySet().forEach(System.out::println));
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
}