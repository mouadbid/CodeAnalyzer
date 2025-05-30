package intelcia.qa.Back_end.model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aimodel {

    private Map<String , List<String>> Bugs ;
    private String Correction ;

    public Aimodel(){
        this.Bugs = new HashMap<String, List<String>>();
    }

    public Map<String, List<String>> getBugs() {
        return Bugs;
    }

    public void setBugs(Map<String, List<String>> bugs) {
        Bugs = bugs;
    }
}
