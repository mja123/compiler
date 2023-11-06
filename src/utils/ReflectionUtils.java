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
    /**
     * Collecting all classes in packageName that implements interfaceName
     * @param packageName target package to check classes
     * @param interfaceName target interface implemented by classes in packageName
     * @return Classes found
     */
    public static Set<Class<?>> findClasses(String packageName, String interfaceName) {
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

    /**
     * Parsing class name
     * @param className full class name
     * @param packageName class package
     * @return parsed class
     */
    private static Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));

        } catch (ClassNotFoundException e) {
            System.out.println("We couldn't access to " + className + ": error " + e.getMessage());
        }
        return null;
    }

    /**
     * Instantiating targetClass
     * @param targetClass class to be instantiated
     * @return new T object
     * @param <T> Using to ICompiler hierarchy
     * @throws IllegalAccessException throw if there is no default constructor
     */
    public static <T extends ICompiler> T instantiateCompilerClass(Class<?> targetClass) throws IllegalAccessException {
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
