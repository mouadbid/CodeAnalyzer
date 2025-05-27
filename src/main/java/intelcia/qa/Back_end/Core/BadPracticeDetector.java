package intelcia.qa.Back_end.Core;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BadPracticeDetector {

    public static void main(String[] args) throws FileNotFoundException {
        // 🔍 Charge le fichier Java à analyser
        FileInputStream in = new FileInputStream("src\\main\\resources\\FilesTest\\Test.java");
        CompilationUnit cu = new JavaParser().parse(in).getResult().get();

        // 👇 Visite l'arbre syntaxique pour détecter les erreurs
        cu.accept(new StringEqualityVisitor(), null);
    }

    private static class StringEqualityVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(BinaryExpr be, Void arg) {
            super.visit(be, arg);

            // 🔎 Vérifie si c'est une comparaison avec ==
            if (be.getOperator() == BinaryExpr.Operator.EQUALS) {
                Expression left = be.getLeft();
                Expression right = be.getRight();

                // Affiche un avertissement (on ne vérifie pas les types ici, on fait simple)
                System.out.println("Mauvaise pratique : '==' trouvé à la ligne " +
                        be.getBegin().map(pos -> pos.line).orElse(-1) +
                        " => utilisez '.equals()' pour comparer des objets.");
            }
        }
    }
}
