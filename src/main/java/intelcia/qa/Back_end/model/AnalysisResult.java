package intelcia.qa.Back_end.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AnalysisResult {

    private ArrayList<String> methods;
    private ArrayList<String> variables;
    private HashSet<String> expressions;
    private HashSet<String> notImportantVariables;
    private ArrayList<ArrayList<String>> identicMethods;
    private List<String> leakResources;

    // Getters et setters

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(ArrayList<String> methods) {
        this.methods = methods;
    }

    public List<String> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<String> variables) {
        this.variables = variables;
    }

    public HashSet<String> getExpressions() {
        return expressions;
    }

    public void setExpressions(HashSet<String> expressions) {
        this.expressions = expressions;
    }

    public HashSet<String> getNotImportantVariables() {
        return notImportantVariables;
    }

    public void setNotImportantVariables(HashSet<String> notImportantVariables) {
        this.notImportantVariables = notImportantVariables;
    }

    public ArrayList<ArrayList<String>> getIdenticMethods() {
        return identicMethods;
    }

    public void setIdenticMethods(ArrayList<ArrayList<String>> identicMethods) {
        this.identicMethods = identicMethods;
    }

    public List<String> getLeakResources() {
        return leakResources;
    }

    public void setLeakResources(List<String> leakResources) {
        this.leakResources = leakResources;
    }
}
