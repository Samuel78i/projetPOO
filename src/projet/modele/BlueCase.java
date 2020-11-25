package projet.modele;

public class BlueCase extends Case{
    private String color;

    public BlueCase(){
        super();
        color = "b";
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
