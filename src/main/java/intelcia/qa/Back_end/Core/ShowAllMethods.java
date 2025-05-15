package intelcia.qa.Back_end.Core;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.NameExpr;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ShowAllMethods {

    private String FileName ;
    private CompilationUnit cu ;
    private static final Set<String> RESOURCE_TYPES = Set.of(
            "FileInputStream", "FileOutputStream", "BufferedReader", "BufferedWriter",
            "Socket", "ServerSocket", "Scanner", "Connection", "Statement",
            "ResultSet", "ZipInputStream", "ObjectInputStream", "PrintWriter"
    );

    public ShowAllMethods(String FileName) throws Exception{
        this.FileName = FileName ;
        try (FileInputStream in = new FileInputStream(FileName)) {
            JavaParser parser = new JavaParser();
            this.cu = parser.parse(in).getResult().orElseThrow(() -> new Exception("unable to find file : "+FileName));
        }
    }

    private ShowAllMethods(){

    }

    public ArrayList<String> Allmethods() throws Exception {

        ArrayList<String> methods = new ArrayList<>();

            cu.findAll(MethodDeclaration.class).forEach(method -> {
                methods.add(method.getNameAsString());
            });
        return methods;
    }

    public ArrayList<String> Allvariables() throws Exception{

        ArrayList<String> variables = new ArrayList<>();

            cu.findAll(VariableDeclarator.class).forEach(var-> {
                variables.add(var.getNameAsString());
            });

        return variables;
    }


    public HashSet<String> extractNameExpressions() throws Exception{
        HashSet<String> usedVariables = new HashSet<>();

            cu.findAll(NameExpr.class).forEach(expr->{
                if(!expr.toString().equals("System")) {
                    usedVariables.add(expr.getNameAsString());
                }
            });

        return usedVariables;
    }




}
