package projet.modele;

public class BlueCase extends Case{
    private String color;

    public BlueCase(){
        super();
        color = "blue";
    }

    @Override
    public String getColor() {
        return color;
    }

    public void printCase(){
        if (isPresent()) {
            System.out.print("b  ");
        }else{
            System.out.print("-  ");
        }
    }
}
