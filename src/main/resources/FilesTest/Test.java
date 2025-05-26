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
        int c ;
        int z;
        r= 0;
        c = 1 + 1 + r ;
        System.out.println(c);
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
