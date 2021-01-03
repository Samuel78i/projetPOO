package projet.modele;

public class Case {
    private boolean present;

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
       System.out.print("-  ");
    }
}
