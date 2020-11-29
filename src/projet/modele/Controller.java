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


    public void iconClicked(int w, int h) {
        board.destroy(w, h);
        view.update();
    }
}
