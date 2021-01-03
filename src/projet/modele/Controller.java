package projet.modele;

public class Controller {
    private Board board;
    private View view;
    private User u;

    public Controller(Board b){
        board = b;
        view = null;
    }

    public void setView(View v){
        view = v;
    }

    public void setU(User user){ u = user;}

    public void iconClicked(int h, int w) {
        if(view.getRocketInUse()){
            board.rocket(w);
            view.setRocketNotUsed(false);
            view.setRocketInUse(false);
        }else {
            board.destroy(h, w);
        }
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

    public void gameWon(){
        if(board.getWin()) {
            board = new Board(board.getWidth(), board.getHeight(), true);
        }else {
            board = new Board(board.getWidth(), board.getHeight());
        }
        view.setBoard(board);
        view.setScoreOfTheGame(0);
        view.setLastTotalScore(view.getScoreTotal());
        view.setRocketInUse(false);
        view.setRocketNotUsed(true);
        u.updateLevel();
        view.update();
    }

    public void gameLost(){
        board = new Board(board.getWidth(), board.getHeight());
        view.setBoard(board);
        view.setScoreTotal(view.getLastTotalScore());
        view.setScoreOfTheGame(0);
        view.setRocketInUse(false);
        view.setRocketNotUsed(true);
        view.displayGameOver();
    }
}
