package intelcia.qa.Back_end.Core;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class SecurityHotspot {

    String FileName ;
    public SecurityHotspot(String FileName){
        this.FileName = FileName;
    }
    public ArrayList<String> securityproblems() throws Exception{
        ArrayList<String> problems = new ArrayList<>();
        String line ;
        int lineNumber = 0 ;
        try(BufferedReader file = new BufferedReader(new FileReader(FileName))){
            while((line=file.readLine())!=null){
                lineNumber++;
                //can do injection in system(you do comand directly with system)
                if(line.contains("Runtime.getRuntime().exec(")){
                    problems.add("Use of Runtime.exec() at line:"+lineNumber);
                }
                //sql injection like php
                if(line.matches(".*Statement.*execute.*\\+.*")){
                    problems.add("SQL concatenation in Statement execution at line:"+lineNumber);
                }
                //it let user connect with server directly
                if(line.contains("new ObjectInputStream(")){
                    problems.add("Use of ObjectInputStream (deserialization) at line:"+lineNumber);
                }

            }
        }

        return problems;
    }
}
