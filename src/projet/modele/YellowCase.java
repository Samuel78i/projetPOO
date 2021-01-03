package projet.modele;

public class YellowCase extends Case{
    private final String color;

    public YellowCase(){
        super();
        color = "yellow";
    }

    @Override
    public String getColor() {
        return color;
    }

    public void printCase(){
        if (isPresent()) {
            System.out.print("y  ");
        }else{
            System.out.print("-  ");
        }
    }
}
