package test.file;
public class SecondTest{
    public class VulnerableExample {
        public static void main(String[] args) throws Exception {
            String apiKey = "AKIAIOSFODNN7EXAMPLE"; // High entropy key
            String query = "SELECT * FROM users WHERE name = '" + args[0] + "'";
            Runtime.getRuntime().exec("rm -rf /");
            ObjectInputStream ois = new ObjectInputStream(System.in);
        }
    }

}