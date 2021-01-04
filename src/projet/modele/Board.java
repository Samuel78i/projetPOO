package projet.modele;

import java.util.LinkedList;
import java.util.Random;

public class Board {
    private final Case[][] board;
    private final int width;
    private final int height;
    private int score;
    private boolean win;
    private int petOnBoard = 3;

    //Constructeur aléatoire
    public Board(int width, int height){
        Random r = new Random();
        int random = r.nextInt(2);
        this.width = width;
        this.height = height;
        board = new Case[height +2][width+2];
        win = false;
        initBoard();
        if (random== 0) {
            int wall = r.nextInt(3);
            putWall(wall);
        }else{
            putPet();
        }
        makeTheBoardMoreWinnable();
    }

    //Constructeur en fonction du niveau
    public Board(int width, int height, int lvl){
        this.width = width;
        this.height = height;
        board = new Case[height +2][width+2];
        win = false;
        initBoard();
        if(lvl < 5){
            putPet();
        }
        if(lvl >= 5 && lvl < 10){
            putWall(0);
        }
        if(lvl >= 10 && lvl < 15){
            putWall(2);
        }
        if(lvl >= 15){
            putWall(1);
        }
        makeTheBoardMoreWinnable();
    }

    //Constructeur pour gagner en 1 coup
    public Board(int w, int h, boolean win){
        this.width = w;
        this.height = h;
        board = new Case[height +2][width+2];
        if(win){
            this.win = true;
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
    }

    public void makeTheBoardMoreWinnable(){
        Random ran = new Random();
        for(int i = 1; i< height +1; i++) {
            for (int j = 1; j < width + 1; j++) {
                if (!(board[i][j] instanceof WallCase) && !(board[i][j] instanceof PetCase) && board[i][j].isPresent()) {
                    if (!aMoveIsPossible(i, j) && !sameColorInDiagonal(i, j)) {
                        //cette liste represente toute les couleurs entourant la case actuelle
                        LinkedList<String> colorAround = giveMeColorAround(i, j);
                        int random = ran.nextInt(colorAround.size());
                        boolean randomNotGood = false;
                        if (colorAround.get(random).equals("wall") || colorAround.get(random).equals("pet")) {
                            randomNotGood = true;
                        }
                        random = 0;
                        while (randomNotGood) {
                            if (!colorAround.get(random).equals("wall") && !colorAround.get(random).equals("pet")) {
                                randomNotGood = false;
                                break;
                            }
                            random++;
                        }
                        if (colorAround.get(random).equals("green")) {
                            board[i][j] = new GreenCase();
                        }
                        if (colorAround.get(random).equals("red")) {
                            board[i][j] = new RedCase();
                        }
                        if (colorAround.get(random).equals("blue")) {
                            board[i][j] = new BlueCase();
                        }
                        if (colorAround.get(random).equals("yellow")) {
                            board[i][j] = new YellowCase();
                        }
                    }
                }
            }
        }
    }

    public void putPet(){
        deleteFirstLine();
        int petOnBoard = 0;
        Random r = new Random();
        for(int i = 1; i<width + 1; i++){
            if(!(board[1][i] instanceof PetCase)) {
                int random = r.nextInt(3);
                if (random == 0) {
                    board[1][i] = new PetCase();
                    petOnBoard++;
                }
            }
            if (petOnBoard == this.petOnBoard){
                break;
            }
        }
        if (petOnBoard < this.petOnBoard){
            putPet();
        }
    }

    public void deleteFirstLine(){
        for(int i = 1; i<width + 1; i++){
            board[1][i].erased();
        }
    }

    public void putWall(int lvl){
        //schema d'obstacles predefinis
        if(lvl == 0){
            board[2][5] = new WallCase();
            board[3][5] = new WallCase();
            board[5][3] = new WallCase();
            board[5][2] = new WallCase();
            board[5][7] = new WallCase();
            board[5][8] = new WallCase();
            board[7][5] = new WallCase();
            board[8][5] = new WallCase();
        }
        if(lvl == 1){
            board[2][3] = new WallCase();
            board[2][4] = new WallCase();
            board[2][6] = new WallCase();
            board[2][7] = new WallCase();
            board[3][4] = new WallCase();
            board[3][6] = new WallCase();
            board[4][4] = new WallCase();
            board[4][6] = new WallCase();
            board[5][5] = new WallCase();
        }
        if(lvl == 2){
            board[4][3] = new WallCase();
            board[4][4] = new WallCase();
            board[4][5] = new WallCase();
            board[4][6] = new WallCase();
            board[4][7] = new WallCase();
            board[5][5] = new WallCase();
            board[6][5] = new WallCase();
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

    //initialisation de du contour du plateau avec des cases non présentes
    private void initAroundBoard() {
        for (int i = 0; i <= height +1; i++) {
            board[i][0].erased();
        }
        for (int i = 0; i <= height +1; i++) {
            board[i][width +1].erased();
        }
        for (int i = 0; i <=width+1; i++) {
            board[0][i].erased();
        }
        for (int i = 0; i <= width+1; i++) {
            board[height+1][i].erased();
        }
    }

    public void destroy(int h, int w){
        if (board[h][w].isPresent() && !(board[h][w] instanceof WallCase) && !(board[h][w] instanceof PetCase)){
            if (aMoveIsPossible(h, w)) {
                //je detruit les cases
                destroyAround(h, w);
                //je fait chuter les cases
                makeThemDrop(1,1);
                //je verifie si des animaux sont tombés
                checkIfPetDropped();
            }
        }
        //si besoin de faire glisser je fais glisser
        if(needsToSlide()){
            makeThemSlide();
        }
        score = score * score;
    }


    public void destroyAround(int h, int w){
        board[h][w].erased();
        score++;
        //verification des couleurs autours de la case actuelle
        if (board[h-1][w].isPresent() && !(board[h-1][w] instanceof WallCase) && board[h - 1][w].getColor().equals(board[h][w].getColor())){
            destroyAround(h-1, w);
        }
        if (board[h][w-1].isPresent() && !(board[h][w - 1] instanceof WallCase) && board[h][w - 1].getColor().equals(board[h][w].getColor())) {
            destroyAround(h, w - 1);
        }
        if (board[h][w+1].isPresent() && !(board[h][w+1] instanceof WallCase)&& board[h][w + 1].getColor().equals(board[h][w].getColor())){
            destroyAround(h, w+1);
        }
        if (board[h+1][w].isPresent() && !(board[h+1][w] instanceof WallCase) && board[h + 1][w].getColor().equals(board[h][w].getColor())){
            destroyAround(h+1, w);
        }

    }

    public void rocket(int w){
        //suppession d'une colonne
        for(int i = 1; i< height + 1; i++){
            if(board[i][w].isPresent() && !(board[i][w] instanceof WallCase) && !(board[i][w] instanceof PetCase)) {
                board[i][w].erased();
                score++;
            }
        }
        makeThemDrop(1, 1);
        checkIfPetDropped();
        if(needsToSlide()){
            makeThemSlide();
        }
        score = score * score;
    }

    public void makeThemDrop(int x, int y){
        for(int i = x; i< height; i++) {
            for (int j = y; j <= width; j++) {
                if(board[i][j].isPresent()){
                    if(!board[i+1][j].isPresent() && !(board[i][j] instanceof WallCase)){
                        //je deplace la case d'un cran vers le bas
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
            //toute une colonne a besoin de glisser
            if (board[height][i].isPresent() && !board[height][i - 1].isPresent() && !(board[height][i] instanceof WallCase)) {
                return true;
            }
        }
        for(int i = 1; i<height ; i++){
            for(int j = 2; j<width + 1; j++){
                //une case a besoin de glisser sur un obstacle
                if(board[i][j].isPresent() && board[i+1][j] instanceof WallCase && board[i+1][j-1] instanceof WallCase && !board[i][j-1].isPresent()){
                    return true;
                }
                //une case doit tomber d'un obstacle
                if(!(board[i][j] instanceof WallCase) && board[i][j].isPresent() && board[i+1][j] instanceof WallCase && !board[i+1][j-1].isPresent()){
                    return true;
                }
            }
        }
        return false;
    }

    public void makeThemSlide(){
        while(needsToSlide()) {
            for(int i = 1; i<height ; i++){
                for(int j = 2; j<width+1; j++) {
                    if (board[height][j].isPresent() && !board[height][j - 1].isPresent() && !(board[height][j] instanceof WallCase)) {
                        slide(height, j);
                    }
                    if(board[i][j].isPresent() && board[i+1][j] instanceof WallCase && board[i+1][j-1] instanceof WallCase && !board[i][j-1].isPresent()){
                        slideOnAWall(i , j);
                    }
                    if(!(board[i][j] instanceof WallCase) && board[i][j].isPresent() && board[i+1][j] instanceof WallCase && !board[i+1][j-1].isPresent()){
                        slideDownAWall(i , j);
                    }
                }
            }
        }
        makeThemDrop(1, 1);
    }

    public void slide(int h, int w) {
        for (int i = h; i >= 1; i--) {
            if(!(board[i][w - 1].isPresent())) {
                if(!(board[i][w] instanceof WallCase)) {
                    //je deplace la case d'un cran vers la gauche
                    board[i][w - 1] = board[i][w];
                    board[i][w] = new Case(false);
                }else {
                    break;
                }
            }
        }
        if (w + 1 < width) {
            slide(height, w + 1);
        }
    }

    public void slideOnAWall(int h, int w){
        for (int i = h; i >= 1; i--) {
            if(!board[i][w - 1].isPresent()){
                if(!(board[i][w] instanceof WallCase)) {
                    //je deplace la case d'un cran vers la gauche
                    board[i][w - 1] = board[i][w];
                    board[i][w] = new Case(false);
                }else {
                    break;
                }
            }
        }
        if (w + 1 < width) {
            //Si la prochaine case est bien sur un obstacle
            if(board[h+1][w+1] instanceof WallCase) {
                slideOnAWall(h, w + 1);
            }
        }
    }

    public void slideDownAWall(int h, int w) {
        if (board[h][w].isPresent() && !(board[h][w] instanceof WallCase)) {
            //je fais tomber une case d'un obstacle
            board[h + 1][w - 1] = board[h][w];
            board[h][w] = new Case(false);
            makeThemDrop(1, 1);
            if(board[h+1][w+1] instanceof WallCase) {
                slideOnAWall(h, w + 1);
            }
        }
    }

    public void checkIfPetDropped(){
        for(int i = 1; i< width + 1; i++){
            if(board[height][i].isPresent() && board[height][i] instanceof  PetCase){
                board[height][i].erased();
                petOnBoard--;
            }
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
                if(board[i][j].isPresent() && !(board[i][j] instanceof WallCase)  && !(board[i][j] instanceof PetCase)) {
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
        return board[h + 1][w].isPresent() && board[h + 1][w].getColor().equals(board[h][w].getColor());
    }

    public boolean sameColorInDiagonal(int h, int w){
        if (board[h - 1][w - 1].isPresent() && board[h - 1][w - 1].getColor().equals(board[h][w].getColor())) {
            return true;
        }
        if (board[h - 1][w + 1].isPresent() && board[h - 1][w + 1].getColor().equals(board[h][w].getColor())) {
            return true;
        }
        if (board[h + 1][w + 1].isPresent() && board[h + 1][w + 1].getColor().equals(board[h][w].getColor())) {
            return true;
        }
        return board[h + 1][w - 1].isPresent() && board[h + 1][w - 1].getColor().equals(board[h][w].getColor());
    }

    public LinkedList<String> giveMeColorAround(int h, int w){
        LinkedList<String> result = new LinkedList<>();
        if(board[h - 1][w - 1].isPresent() && !(board[h - 1][w - 1] instanceof WallCase)){
            result.add(board[h - 1][w - 1].getColor());
        }
        if(board[h - 1][w].isPresent() && !(board[h - 1][w] instanceof WallCase)){
            result.add(board[h - 1][w].getColor());
        }
        if(board[h - 1][w + 1].isPresent() && !(board[h - 1][w + 1] instanceof WallCase)){
            result.add(board[h - 1][w + 1].getColor());
        }
        if(board[h][w - 1].isPresent() && !(board[h][w - 1] instanceof WallCase)){
            result.add(board[h][w - 1].getColor());
        }
        if(board[h][w + 1].isPresent() && !(board[h][w + 1] instanceof WallCase)){
            result.add(board[h][w + 1].getColor());
        }
        if(board[h + 1][w - 1].isPresent() && !(board[h + 1][w - 1] instanceof WallCase)){
            result.add(board[h + 1][w - 1].getColor());
        }
        if(board[h + 1][w].isPresent() && !(board[h][w] instanceof WallCase)){
            result.add(board[h + 1][w].getColor());
        }
        if(board[h + 1][w + 1].isPresent() && !(board[h + 1][w + 1] instanceof WallCase)){
            result.add(board[h + 1][w + 1].getColor());
        }
        return result;
    }

    public boolean gameWin() {
        //si il n'y a plus d'animaux la parti est gagné
        if(petOnBoard == 0){
            return true;
        }
        for(int i = 1; i<= height; i++) {
            for (int j = 1; j <= width; j++) {
                if (board[i][j].isPresent() && !(board[i][j] instanceof WallCase)) {
                    return false;
                }
            }
        }
        return true;
    }
}
