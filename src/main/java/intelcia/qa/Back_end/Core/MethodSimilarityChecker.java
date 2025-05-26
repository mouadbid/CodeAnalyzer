import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;

import java.io.FileInputStream;
import java.util.*;

public class MethodSimilarityChecker {

    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("src/main/java/Test.java");
        CompilationUnit cu = StaticJavaParser.parse(in);

        List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);
        detectSimilarMethods(methods);
    }

    public static void detectSimilarMethods(List<MethodDeclaration> methods) {
        Map<String, List<String>> normalizedCodeToMethodNames = new HashMap<>();

        for (MethodDeclaration method : methods) {
            if (method.getBody().isPresent()) {
                String normalized = normalize(method.getBody().get().toString());

                normalizedCodeToMethodNames
                        .computeIfAbsent(normalized, k -> new ArrayList<>())
                        .add(method.getNameAsString());
            }
        }

        System.out.println("Méthodes similaires détectées :");
        for (Map.Entry<String, List<String>> entry : normalizedCodeToMethodNames.entrySet()) {
            if (entry.getValue().size() > 1) {
                System.out.println(" - " + entry.getValue());
            }
        }
    }

    // Normalisation : supprime espaces, noms de variables, etc.
    private static String normalize(String code) {
        // Enlève tous les espaces et retours à la ligne
        code = code.replaceAll("\\s+", "");

        // Remplace les noms de variables, par exemple i, j, a, b par VAR
        code = code.replaceAll("\\b[a-zA-Z]{1,2}\\b", "VAR");

        // Remplace les chiffres
        code = code.replaceAll("\\b\\d+\\b", "NUM");

        // Supprime les commentaires (/* */ ou //)
        code = code.replaceAll("//.*?\\n|/\\*(.|\\R)*?\\*/", "");

        return code;
    }
}
