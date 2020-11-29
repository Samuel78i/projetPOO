package projet.modele;

public class Test {
    public static void main(String[] args){
       Board b = new Board(5,5);
       Controller c = new Controller(b);
       View v = new View(b, c);
       c.setView(v);
       v.setVisible(true);
    }
}
