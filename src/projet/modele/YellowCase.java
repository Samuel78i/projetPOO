package projet.modele;

public class YellowCase extends Case{
    private String color;

    public YellowCase(){
        super();
        color = "y";
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
