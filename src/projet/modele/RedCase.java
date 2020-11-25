package projet.modele;

public class RedCase extends Case {
    private String color;

    public RedCase(){
        super();
        color = "r";
    }

    @Override
    public String getColor() {
        return color;
    }

    public void printCase(){
        if (isPresent()) {
            System.out.print(color + " ");
        }else{
            System.out.print("  ");
        }
    }
}
