package projet.modele;
import java.util.Random;

public class Case {
    private boolean present=false;

    public Case(){
        present = true;
    }
    public Case(boolean b){
        present = b;
    }

    public String getColor(){
       return "";
    }

    public boolean isPresent(){
        return present;
    }

    public void erased(){
        present = false;
    }

    public void printCase() {
       System.out.print("  ");
    }
}
