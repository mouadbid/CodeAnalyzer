package intelcia.qa.Back_end.Core;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;

import java.io.FileInputStream;
import java.util.*;

public class IdenticMethode {

    private String FileName ;
    private CompilationUnit cu ;

    public IdenticMethode(String FileName) throws Exception{
        this.FileName = FileName ;
        try(FileInputStream in = new FileInputStream(FileName)){
            JavaParser parser = new JavaParser() ;
            this.cu = parser.parse(in).getResult().orElseThrow(()->new Exception("File does not existe"+FileName));
        }
    }

    public ArrayList<ArrayList<String>> identicMethods(String FileName) throws Exception {
        IdenticMethode obj = new IdenticMethode(FileName);
        ArrayList<ArrayList<String>> IdenticM = new ArrayList<>();

        List<MethodDeclaration> methods = obj.cu.findAll(MethodDeclaration.class);
        Set<String> alreadyGrouped = new HashSet<>();

        for (int i = 0; i < methods.size(); i++) {
            MethodDeclaration method_i = methods.get(i);
            String name_i = method_i.getNameAsString();

            if (alreadyGrouped.contains(name_i)) continue;

            Optional<BlockStmt> body_i_opt = method_i.getBody();
            if (body_i_opt.isEmpty()) continue;

            String body_i = body_i_opt.get().toString().replaceAll("\\s+", "");

            ArrayList<String> group = new ArrayList<>();
            group.add(name_i);
            alreadyGrouped.add(name_i);

            for (int j = i + 1; j < methods.size(); j++) {
                MethodDeclaration method_j = methods.get(j);
                String name_j = method_j.getNameAsString();

                if (alreadyGrouped.contains(name_j)) continue;

                Optional<BlockStmt> body_j_opt = method_j.getBody();
                if (body_j_opt.isEmpty()) continue;

                String body_j = body_j_opt.get().toString().replaceAll("\\s+", "");

                if (body_i.equals(body_j)) {
                    group.add(name_j);
                    alreadyGrouped.add(name_j);
                }
            }

            if (group.size() > 1) {
                IdenticM.add(group);
            }
        }

        return IdenticM;
    }


}