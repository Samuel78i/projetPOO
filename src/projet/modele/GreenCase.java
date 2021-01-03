package projet.modele;

public class GreenCase extends Case{
    private final String color;

    public GreenCase(){
        super();
        color = "green";
    }

    @Override
    public String getColor() {
        return color;
    }

    public void printCase() {
        if (isPresent()) {
            System.out.print("g  ");
        }else{
            System.out.print("-  ");
        }
    }
}
