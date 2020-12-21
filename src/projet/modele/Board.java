package projet.modele;

import java.util.Random;

public class Board {
    private final Case[][] board;
    private final int width;
    private final int height;
    private int score;

    public Board(int width, int height){
        this.width = width;
        this.height = height;
        board = new Case[height +2][width+2];
        initBoard();
    }

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public Case[][] getBoard(){
        return board;
    }

    public void initBoard(){
        Random ran =new Random();
        for(int i = 0; i< height +2; i++){
            for(int j =0; j<width+2; j++){
                int random = ran.nextInt(4);
                if(random == 1){
                    board[i][j] = new GreenCase();
                }
                if(random == 2){
                    board[i][j] = new BlueCase();
                }
                if(random == 3){
                    board[i][j] = new RedCase();
                }
                if(random == 0) {
                    board[i][j] = new YellowCase();
                }
            }
        }
        initAroundBoard();
    }

    private void initAroundBoard() {
        for (int i = 0; i <= height +1; i++) {
            board[i][0].erased();
        }
        for (int i = 0; i <= height +1; i++) {
            board[i][height +1].erased();
        }
        for (int i = 0; i <=width+1; i++) {
            board[0][i].erased();
        }
        for (int i = 0; i <= width+1; i++) {
            board[width+1][i].erased();
        }
    }

    public void destroy(int h, int w){
        if (board[h][w].isPresent()) {
            boolean sameColor = false;
            if (board[h - 1][w].isPresent() && board[h - 1][w].getColor().equals(board[h][w].getColor())) {
                sameColor = true;
            }
            if (board[h][w - 1].isPresent() && board[h][w - 1].getColor().equals(board[h][w].getColor())) {
                sameColor = true;
            }
            if (board[h][w + 1].isPresent() && board[h][w + 1].getColor().equals(board[h][w].getColor())) {
                sameColor = true;
            }
            if (board[h + 1][w].isPresent() && board[h + 1][w].getColor().equals(board[h][w].getColor())) {
                sameColor = true;
            }
            if (sameColor) {
                destroyAround(h, w);
            }
        }
        makeThemDrop(1,1);
        if(needsToSlide()){
            makeThemSlide(1,1);
        }
        score = score * score * 10;
    }


    public void destroyAround(int h, int w){
        board[h][w].erased();
        score++;
        if (board[h-1][w].isPresent() && board[h - 1][w].getColor().equals(board[h][w].getColor())){
            destroyAround(h-1, w);
        }
        if (board[h][w-1].isPresent() && board[h][w - 1].getColor().equals(board[h][w].getColor())) {
            destroyAround(h, w - 1);
        }
        if (board[h][w+1].isPresent() && board[h][w + 1].getColor().equals(board[h][w].getColor())){
            destroyAround(h, w+1);
        }
        if (board[h+1][w].isPresent() && board[h + 1][w].getColor().equals(board[h][w].getColor())){
            destroyAround(h+1, w);
        }

    }


    public void makeThemDrop(int x, int y){
        for(int i = x; i< height; i++) {
            for (int j = y; j <= width; j++) {
                if(board[i][j].isPresent()){
                    if(!board[i+1][j].isPresent()){
                        board[i+1][j]=board[i][j];
                        board[i][j] = new Case(false);
                        makeThemDrop(i-1, j);
                    }
                }
            }
        }
    }

    private boolean needsToSlide() {
        for(int i = 2; i<= width; i++) {
            if (board[height][i].isPresent() && !board[height][i - 1].isPresent()) {
                return true;
            }
        }
        return false;
    }

    public void makeThemSlide(int x, int y){
        while(needsToSlide()) {
            for (int i = 1; i < width; i++) {
                if (!board[height][i].isPresent()) {
                    slide(height, i + 1);
                }
            }
        }
    }

    public void slide(int h, int w) {
        for (int i = h; i >= 1; i--) {
            board[i][w - 1] = board[i][w];
            board[i][w] = new Case(false);
        }
        if (w + 1 < width) {
            slide(height, w + 1);
        }
    }

    public void printCurrentBoard(){
        System.out.println("Here is your current board");
        for(int i = 1; i<= height; i++) {
            for (int j = 1; j <= width; j++) {
                board[i][j].printCase();
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean gameOver() {
        for (Case[] h : board) {
            for (Case w : h) {
                if (w.isPresent()) return false;
            }
        }
        return true;
    }

}
