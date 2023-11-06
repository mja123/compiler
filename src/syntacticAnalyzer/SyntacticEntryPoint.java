package syntacticAnalyzer;

import lexicalAnalyzer.TokensEntryPoint;
import lexicalAnalyzer.common.ETokenKey;
import syntacticAnalyzer.common.ETense;
import syntacticAnalyzer.common.ITokenAnalyze;
import utils.ReflectionUtils;

import java.util.*;

import static utils.ReflectionUtils.instantiateCompilerClass;

public class SyntacticEntryPoint {

    private static final List<List<Map<ETokenKey, String>>> TOKENS;
    final static String TENSES_PACKAGE = "syntacticAnalyzer.tokenAnalyze";
    final static String SYNTACTIC_INTERFACE = "ITokenAnalyze";
    final static List<Map<ETense, List<String>>> totalTenses = new LinkedList<>();

    private SyntacticEntryPoint() {}

    static {
        TOKENS = TokensEntryPoint.getTotalTokens();
    }

    /**
     * Generating tenses per tokens in each line
     */
    private static void generateTenses() {
        // Join all keys and values in the line in one string
        TOKENS.forEach(line -> {
            StringBuilder keys = new StringBuilder();
            StringBuilder values = new StringBuilder();
            line.stream().flatMap(
                    r -> r.entrySet().stream()
            ).forEach(v -> {
                keys.append(v.getKey().toString())
                    .append(" ");
                values.append(v.getValue())
                    .append(" ");
            });
            keys.deleteCharAt(keys.length() - 1);
            values.deleteCharAt(values.length() - 1);
            // Finding classes that are implementing SYNTACTIC_INTERFACE in TENSES_PACKAGES
            ReflectionUtils.findClasses(TENSES_PACKAGE, SYNTACTIC_INTERFACE).stream().filter(t -> {
                try {
                    // Instantiating classes to analyze if current key matches with class pattern
                    ITokenAnalyze token = instantiateCompilerClass(t);
                    return token.analyze(keys.toString());
                } catch (IllegalAccessException e) {
                    System.out.println("Problem with class " + t.getName() + " error: " + e.getMessage());
                    return false;
                }
            }).findFirst().ifPresent(f -> {
                Map<ETense, List<String>> tense = null;
                try {
                    ITokenAnalyze token = instantiateCompilerClass(f);
                    List<String> keysList = Arrays.stream(keys.toString().split("\\s")).toList();
                    List<String> valuesList = Arrays.stream(values.toString().split("\\s")).toList();
                    // Generating tense from class matched
                    tense = token.generateTense(keysList, valuesList);
                } catch (IllegalAccessException e) {
                    System.out.println("Problem with class " + f.getName() + " error: " + e.getMessage());
                }
                totalTenses.add(tense);
            });

        });
    }

    /**
     * Checking tokens generated from file
     */
    private static void readTokens() {
        int counter = 0;
        for (List<Map<ETokenKey, String>> line : TOKENS) {
            System.out.println("**** Line " + counter++ + " ****");
            line.forEach(f -> f.entrySet().forEach(System.out::println));
        }
    }

    public static void main(String[] args) {
//        readTokens();
        generateTenses();
        totalTenses.forEach(t -> t.entrySet().forEach(System.out::println));
//        System.out.println(totalTenses.size());
    }
}
