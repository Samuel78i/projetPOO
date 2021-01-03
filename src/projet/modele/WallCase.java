package projet.modele;

public class WallCase extends Case{
    private final String color;

    public WallCase(){
        super();
        color = "wall";
    }

    @Override
    public String getColor() {
        return color;
    }

    public void printCase() {
        if (isPresent()) {
            System.out.print("W  ");
        }else{
            System.out.print("-  ");
        }
    }
}
