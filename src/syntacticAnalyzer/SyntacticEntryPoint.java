package syntacticAnalyzer;

import lexicalAnalyzer.TokensEntryPoint;
import lexicalAnalyzer.common.ETokenKey;
import syntacticAnalyzer.common.ETense;
import syntacticAnalyzer.common.ITokenAnalyze;
import utils.ReflectionUtils;

import java.io.IOException;
import java.util.*;

import static utils.ReflectionUtils.instantiateCompilerClass;

public class SyntacticEntryPoint {

    private static final List<List<Map<ETokenKey, String>>> TOKENS;
    final static String TENSES_PACKAGE = "syntacticAnalyzer.tokenAnalyze";
    final static String SYNTACTIC_INTERFACE = "ITokenAnalyze";
    final static List<Map<ETense, List<String>>> totalTenses = new LinkedList<>();

    static {
        try {
            TokensEntryPoint.generateToken();
            TOKENS = TokensEntryPoint.getTotalTokens();
        } catch (IOException e) {
            throw new RuntimeException("We couldn't access to tokens");
        }
    }


    private static void generateTenses() {
        TOKENS.forEach(line ->
            line.forEach(l ->
                ReflectionUtils.findAllClassesUsingClassLoader(TENSES_PACKAGE, SYNTACTIC_INTERFACE).stream().filter(t -> {
                    try {
                        ITokenAnalyze token = instantiateCompilerClass(t);
                        return token.analyze(l.keySet().stream().findFirst().get());
                    } catch (IllegalAccessException e) {
                        System.out.println("Problem with class " + t.getName() + " error: " + e.getMessage());
                        return false;
                    }
                }).findFirst().ifPresent(f -> {
                    Map<ETense, List<String>> tense = null;
                    try {
                        ITokenAnalyze token = instantiateCompilerClass(f);
                        tense = token.generateTense(line);
                    } catch (IllegalAccessException e) {
                        System.out.println("Problem with class " + f.getName() + " error: " + e.getMessage());
                    }
                    totalTenses.add(tense);
                })
            )
        );
    }


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
        System.out.println(totalTenses.size());
    }
}
