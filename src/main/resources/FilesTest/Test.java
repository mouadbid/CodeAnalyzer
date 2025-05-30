package test.file;
import java.io.FileInputStream;

public class Test {

    public void Hello(){
        int z ;
        System.out.println("Bonjour a tous");
    }

    public void Hello2(){
        int z ;
        System.out.println("Bonjour a tous");
    }

    public void C(int r){
        int a ;
        int z;
        r= 0;
        a = 1 + 1 + r ;
        System.out.println(a);
    }

    public void SecondC(int r){
        int c ;
        int z;
        r= 0;
        c = 1 + 1 + r ;
        System.out.println(c);
    }

    public void Firstline() throws Exception {
        FileInputStream fis = new FileInputStream("fichier.txt");
        //fuite des resources
    }


    public void runCommand(String userInput) {
        try {
            //user input is directly passed to exec()
            Runtime.getRuntime().exec("ping " + userInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findUser(String username) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db", "user", "pass");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM users WHERE name = '" + username + "'";
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void receiveObject(Socket clientSocket) {
        try {
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

            Object obj = ois.readObject();

            System.out.println("Received: " + obj);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void Secondline() throws Exception {
        FileInputStream file2 = new FileInputStream("fichier.txt");
        file2.close();
        //fichier est ferme
    }

    public void Thirdline() throws Exception {
        try(FileInputStream fis = new FileInputStream("fichier.txt")){
            //try methode est ignore
        }
    }

}
