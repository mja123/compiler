package Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ReflectionUtils {
    public static Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
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
                            .anyMatch(i -> i.getName().endsWith("IToken")))
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
}
