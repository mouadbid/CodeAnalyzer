package intelcia.qa.Back_end.Core;

public class Main {
    public static void main(String args[]) throws Exception {
        String FileName = "src\\main\\resources\\FilesTest\\Test.java";
        ShowAllMethods extractor = new ShowAllMethods(FileName);
        NotImportantVar notImporatant = new NotImportantVar();
        System.out.println("Methods : ");
        System.out.println(extractor.Allmethods());
        System.out.println("\nvariabls : ");
        System.out.println(extractor.Allvariables());
        System.out.println("\nExpreseeions : ");
        System.out.println(extractor.extractNameExpressions());
        System.out.println("\nthose variables not important");
        System.out.println(notImporatant.VariablesNotImportanat(extractor.Allvariables(),extractor.extractNameExpressions()));
        IdenticMethode idm = new IdenticMethode(FileName);
        System.out.println("\n"+idm.identicMethods(FileName));
        System.out.println("des fuits possible : \n");
        LeakResource Test = new LeakResource(FileName);
        Test.ObjectCreation();

    }
}
