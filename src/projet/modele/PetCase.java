package projet.modele;

public class PetCase extends Case {
    private final String color;

    public PetCase() {
        super();
        color = "pet";
    }

    @Override
    public String getColor() {
        return color;
    }

    public void printCase() {
        if (isPresent()) {
            System.out.print("P  ");
        } else {
            System.out.print("-  ");
        }
    }
}

