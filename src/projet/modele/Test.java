package projet.modele;

public class Test {
    public static void main(String[] args){
        Board b = new Board(9,9);
        Controller c = new Controller(b);
        View v = new View(b, c);
        c.setView(v);
        v.setVisible(true);
        //Game g = new Game();
        //g.play();
    }
}
