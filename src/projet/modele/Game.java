package projet.modele;

public class Game {
    private final Player p;
    private Board b;

    public Game(){
        p = new Player();
        b = new Board(9, 9);
    }

    public void play(){
        if(p.wantToPlay()) {
            p.askSetName();
            boolean keepPlaying = true;
            while (keepPlaying) {
                b.printCurrentBoard();
                keepPlaying = !b.gameOver();
                if(!keepPlaying){
                    break;
                }
                b.destroy(p.askDestroyX(b), p.askDestroyY(b));
            }
            if(p.playAgain()){
                playAgain();
            }
        }
    }

    public void playAgain(){
        b= new Board(10,10);
        boolean keepPlaying = true;
        while (keepPlaying) {
            keepPlaying = !b.gameOver();
            b.printCurrentBoard();
            b.destroy(p.askDestroyX(b), p.askDestroyY(b));
        }
        if(p.playAgain()){
            playAgain();
        }
    }
}
