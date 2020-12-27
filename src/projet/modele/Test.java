package projet.modele;

public class Test {
    public static void main(String[] args){
       Board b = new Board(9,9, true);
       Controller c = new Controller(b);
       View v = new View(b, c);
       c.setView(v);
       v.setVisible(true);

    }
}
