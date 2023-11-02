import common.ETokenKey;
import common.IToken;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String value = "asdf";
        List<Map<ETokenKey, String>> tokens = new ArrayList<>();

        Utils.findAllClassesUsingClassLoader("tokens").stream().filter(l -> {
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