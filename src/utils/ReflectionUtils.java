package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ReflectionUtils {
    public static Set<Class<?>> findAllClassesUsingClassLoader(String packageName, String interfaceName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        assert stream != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(l -> getClass(l, packageName))
                .filter(l ->
                    l != null && l.getInterfaces().length > 0 &&
                    Arrays.stream(l.getInterfaces())
                            .anyMatch(i -> i.getName().endsWith(interfaceName)))
                .collect(Collectors.toSet());
    }

    private static Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));

        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }

    public static <T extends ICompiler<?>> T instantiateCompilerClass(Class<?> targetClass) throws IllegalAccessException {
        Optional<Constructor<?>> defaultConstructor = Arrays.stream(targetClass.getConstructors()).
                filter(c -> c.getParameterCount() == 0)
                .findFirst();

        if (defaultConstructor.isPresent()) {
            try {
                return (T) defaultConstructor.get().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                System.out.println("We couldn't create a new instance of " + targetClass.getName());
            }
        }
        throw new IllegalAccessException("Default constructor is not present");
    }

}
