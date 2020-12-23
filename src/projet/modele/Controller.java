package projet.modele;

import java.util.concurrent.TimeUnit;

public class Controller {
    private Board board;
    private View view;

    public Controller(Board b){
        board = b;
        view = null;
    }

    public void setView(View v){
        view = v;
    }

    public void setBoard(Board b){ board = b;}

    public void iconClicked(int w, int h) {
        board.destroy(w, h);
        view.update();
    }

    public void play(){
        view.launchGame();
    }

    public void rules(){
        view.launchRules();
    }

    public void quit(){
        view.quit();
    }

    public void menu(){
        view.launchMenu();
    }

    public boolean isTheGameOver() {
        return board.gameOver();
    }

    public boolean isTheGameWin() {return board.gameWin(); }
}
