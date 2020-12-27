package projet.modele;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Board {
    private final Case[][] board;
    private final int width;
    private final int height;
    private int score;
    private boolean win;

    public Board(int width, int height){
        this.width = width;
        this.height = height;
        board = new Case[height +2][width+2];
        win = false;
        initBoard();
    }

    public Board(int w, int h, boolean win){
        this.width = w;
        this.height = h;
        board = new Case[height +2][width+2];
        this.win = win;
        if(win) {
            initWinBoard();
        }
    }

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public int getScore() {
        int tmp = score;
        score = 0;
        return tmp;
    }

    public Case[][] getBoard(){
        return board;
    }

    public boolean getWin(){ return win;}

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
        makeTheBoardMoreWinnable();
    }

    public void makeTheBoardMoreWinnable(){
        Random ran = new Random();
        for(int i = 1; i< height +1; i++) {
            for (int j = 1; j < width + 1; j++) {
                if(!aMoveIsPossible(i, j) && !sameColorInDia(i, j)){
                    LinkedList<String> colorAround = giveMeColorAround(i ,j);
                    int random = ran.nextInt(colorAround.size());
                    if(colorAround.get(random).equals("green")){
                        board[i][j] = new GreenCase();
                    }
                    if(colorAround.get(random).equals("red")){
                        board[i][j] = new RedCase();
                    }
                    if(colorAround.get(random).equals("blue")){
                        board[i][j] = new BlueCase();
                    }
                    if(colorAround.get(random).equals("yellow")){
                        board[i][j] = new YellowCase();
                    }
                }
            }
        }
    }

    public void initWinBoard(){
        for(int i = 0; i< height +2; i++) {
            for (int j = 0; j < width + 2; j++) {
                board[i][j] = new GreenCase();
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
            if (aMoveIsPossible(h, w)) {
                destroyAround(h, w);
            }
        }
        makeThemDrop(1,1);
        if(needsToSlide()){
            makeThemSlide(1,1);
        }
        score = score * 10;
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
        for(int i = 1; i<= height; i++) {
            for (int j = 1; j <= width; j++) {
                if(board[i][j].isPresent()) {
                    if (aMoveIsPossible(i, j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean aMoveIsPossible(int h, int w) {
        if (board[h - 1][w].isPresent() && board[h - 1][w].getColor().equals(board[h][w].getColor())) {
            return true;
        }
        if (board[h][w - 1].isPresent() && board[h][w - 1].getColor().equals(board[h][w].getColor())) {
            return true;
        }
        if (board[h][w + 1].isPresent() && board[h][w + 1].getColor().equals(board[h][w].getColor())) {
            return true;
        }
        if (board[h + 1][w].isPresent() && board[h + 1][w].getColor().equals(board[h][w].getColor())) {
            return true;
        }
        return false;
    }

    public boolean sameColorInDia(int h, int w){
        if (board[h - 1][w - 1].isPresent() && board[h - 1][w - 1].getColor().equals(board[h][w].getColor())) {
            return true;
        }
        if (board[h - 1][w + 1].isPresent() && board[h - 1][w + 1].getColor().equals(board[h][w].getColor())) {
            return true;
        }
        if (board[h + 1][w + 1].isPresent() && board[h + 1][w + 1].getColor().equals(board[h][w].getColor())) {
            return true;
        }
        if (board[h + 1][w - 1].isPresent() && board[h + 1][w - 1].getColor().equals(board[h][w].getColor())) {
            return true;
        }
        return false;
    }

    public LinkedList<String> giveMeColorAround(int h, int w){
        LinkedList<String> result = new LinkedList<String>();
        if(board[h - 1][w - 1].isPresent()){
            result.add(board[h - 1][w - 1].getColor());
        }
        if(board[h - 1][w].isPresent()){
            result.add(board[h - 1][w].getColor());
        }
        if(board[h - 1][w + 1].isPresent()){
            result.add(board[h - 1][w + 1].getColor());
        }
        if(board[h][w - 1].isPresent()){
            result.add(board[h][w - 1].getColor());
        }
        if(board[h][w + 1].isPresent()){
            result.add(board[h][w + 1].getColor());
        }
        if(board[h + 1][w - 1].isPresent()){
            result.add(board[h + 1][w - 1].getColor());
        }
        if(board[h + 1][w].isPresent()){
            result.add(board[h + 1][w].getColor());
        }
        if(board[h + 1][w + 1].isPresent()){
            result.add(board[h + 1][w + 1].getColor());
        }
        return result;
    }

    public boolean gameWin() {
        for(int i = 1; i<= height; i++) {
            for (int j = 1; j <= width; j++) {
                if (board[i][j].isPresent()) {
                    return false;
                }
            }
        }
        return true;
    }
}
