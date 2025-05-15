package intelcia.qa.Back_end.Core;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.TryStmt;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class LeakResource {

    private String FileName ;
    private CompilationUnit cu ;
    private static final Set<String> RESOURCE_TYPES = Set.of(
            "FileInputStream", "FileOutputStream", "BufferedReader", "BufferedWriter",
            "Socket", "ServerSocket", "Scanner", "Connection", "Statement",
            "ResultSet", "ZipInputStream", "ObjectInputStream", "PrintWriter"
    );

    public LeakResource(String FileName) throws Exception{
        this.FileName = FileName ;
        try (FileInputStream in = new FileInputStream(FileName)) {
            JavaParser parser = new JavaParser();
            this.cu = parser.parse(in).getResult().orElseThrow(() -> new Exception("unable to find file : "+FileName));
        }
    }
    private LeakResource(){}

    public List<String> ObjectCreation() {
        List<String> Altes = new ArrayList<>();
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            method.findAll(ObjectCreationExpr.class).forEach(creation -> {
                String type = creation.getType().asString();

                // Vérifie si c'est une ressource sensible
                if (!RESOURCE_TYPES.contains(type)) return;

                // Vérifie si la ressource est utilisée dans try-with-resources
                Optional<TryStmt> tryStmt = creation.findAncestor(TryStmt.class);
                if (tryStmt.isPresent()) {
                    boolean isInResources = tryStmt.get().getResources().stream()
                            .anyMatch(res -> res.toString().contains(creation.toString()));
                    if (isInResources) return; // Pas une fuite
                }

                // Vérifie s'il y a un appel à .close() dans la méthode
                boolean closed = method.findAll(MethodCallExpr.class).stream()
                        .anyMatch(call -> call.getNameAsString().equals("close"));

                if (!closed) {
                    int line = creation.getBegin().map(p -> p.line).orElse(-1);
                    String methodName = method.getNameAsString();
                    Altes.add("Resource leak detected in method '" +
                            methodName + "' at line " + line + ": " + type + " is not closed.");
                }
            });
        });
        return Altes;
    }
}
