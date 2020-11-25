package projet.modele;

public class GreenCase extends Case{
    private String color;

    public GreenCase(){
        super();
        color = "g";
    }

    @Override
    public String getColor() {
        return color;
    }

    public void printCase() {
        if (isPresent()) {
            System.out.print(color + " ");
        }else{
            System.out.print("  ");
        }
    }
}
