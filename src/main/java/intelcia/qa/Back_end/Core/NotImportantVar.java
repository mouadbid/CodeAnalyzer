package intelcia.qa.Back_end.Core;

import java.util.ArrayList;
import java.util.HashSet;

public class NotImportantVar {
    public  NotImportantVar(){}

    public HashSet<String> VariablesNotImportanat(ArrayList<String> allVar , HashSet<String> usedVar){
        HashSet<String> unesccaryVar = new HashSet<>();
        for(String i : allVar){
            if(!usedVar.contains(i))
                unesccaryVar.add(i);
        }
        return unesccaryVar;
    }
}
