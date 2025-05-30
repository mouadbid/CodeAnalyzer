package intelcia.qa.Back_end.Core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityHotspot {

    private final String FileName;

    public SecurityHotspot(String FileName) {
        this.FileName = FileName;
    }

    // Pattern to detect suspicious variable names for secrets
    private static final Pattern suspiciousNamePattern = Pattern.compile(
            ".*(secret|token|credential|auth|api[_\\.-]?key).*", Pattern.CASE_INSENSITIVE
    );

    // Pattern to detect string assignments to suspicious variables
    private static final Pattern stringAssignmentPattern = Pattern.compile(
            "(String|var)?\\s*(\\w*(secret|token|credential|auth|api[_\\.-]?key)\\w*)\\s*=\\s*\"([^\"]+)\"",
            Pattern.CASE_INSENSITIVE
    );

    // Pattern to detect SQL statements built with concatenation
    private static final Pattern sqlPattern = Pattern.compile(
            "execute(Query|Update)?\\s*\\(.*\".*\"\\s*\\+"
    );

    // Entropy calculator
    private static double calculateEntropy(String input) {
        int[] freq = new int[256];
        for (char c : input.toCharArray()) {
            freq[c]++;
        }
        double entropy = 0.0;
        int len = input.length();
        for (int f : freq) {
            if (f > 0) {
                double p = (double) f / len;
                entropy -= p * (Math.log(p) / Math.log(2));
            }
        }
        return entropy;
    }

    // Main analysis method
    public ArrayList<String> securityproblems() throws Exception {
        ArrayList<String> problems = new ArrayList<>();
        String line;
        int lineNumber = 0;

        try (BufferedReader file = new BufferedReader(new FileReader(FileName))) {
            while ((line = file.readLine()) != null) {
                lineNumber++;

                // Runtime.exec() detection
                if (line.contains("Runtime.getRuntime().exec(")) {
                    problems.add("Use of Runtime.exec() at line: " + lineNumber);
                }

                // Hardcoded secret detection
                Matcher secretMatcher = stringAssignmentPattern.matcher(line);
                if (secretMatcher.find()) {
                    String varName = secretMatcher.group(2);
                    String value = secretMatcher.group(4);

                    if (suspiciousNamePattern.matcher(varName).matches()) {
                        double entropy = calculateEntropy(value);
                        if (entropy > 3.5) { // Adjustable threshold
                            problems.add("Potential hardcoded secret at line " + lineNumber +
                                    ": variable '" + varName + "' with high-entropy value \"" + value + "\"");
                        }
                    }
                }

                // SQL injection detection
                Matcher sqlMatcher = sqlPattern.matcher(line);
                if (sqlMatcher.find()) {
                    problems.add("Possible SQL injection (string concatenation in execute) at line: " + lineNumber);
                }

                // Unsafe deserialization detection
                if (line.contains("new ObjectInputStream(")) {
                    problems.add("Use of ObjectInputStream (unsafe deserialization) at line: " + lineNumber);
                }
            }
        }

        return problems;
    }
}
