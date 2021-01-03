package projet.modele;

public class RedCase extends Case {
    private final String color;

    public RedCase(){
        super();
        color = "red";
    }

    @Override
    public String getColor() {
        return color;
    }

    public void printCase(){
        if (isPresent()) {
            System.out.print("r  ");
        }else{
            System.out.print("-  ");
        }
    }
}
