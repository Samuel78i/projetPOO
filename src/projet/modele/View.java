package projet.modele;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import static javax.swing.GroupLayout.Alignment.*;


public class View extends JFrame {
    private final JPanel panel= new JPanel();
    private final JPanel lvlProgression = new JPanel();
    private final Controller controller;
    private Board board;
    private String userName;
    public User u = new User();
    private int level;
    private int scoreTotal;
    private int lastTotalScore = 0;
    private int scoreOfTheGame;
    private boolean rocketInUse = false;
    private boolean rocketNotUsed = true;


    public View(Board b, Controller c){
        u.setView(this);
        board = b;
        controller = c;
        controller.setU(u);
        this.setTitle("GAME");
        this.setSize(b.getWidth()*150, b.getHeight()*75);
        launchUserSelection();
        this.getContentPane().add(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //tout les getters et setters
    public String getUserName(){ return userName;}
    public int getLevel(){ return level;}
    public void setLevel(int i){ level = i;}
    public void setBoard(Board b){ board = b;}
    public void setScoreOfTheGame(int i){ scoreOfTheGame = i;}
    public void setLastTotalScore(int i){ lastTotalScore = i;}
    public int getLastTotalScore(){ return lastTotalScore;}
    public void setScoreTotal(int i){ scoreTotal = i;}
    public int getScoreTotal(){ return scoreTotal;}
    public boolean getRocketInUse(){ return rocketInUse;}
    public void setRocketInUse(boolean b){ rocketInUse = b;}
    public void setRocketNotUsed(boolean b){ rocketNotUsed = b;}

    private void launchUserSelection() {
        panel.removeAll();
        JButton inputButton = new JButton("Send");
        JTextField editTextArea = new JTextField("Enter User Name");

        editTextArea.setBackground(Color.white);
        editTextArea.setForeground(Color.black);
        editTextArea.setBorder(BorderFactory.createLineBorder(new Color(65, 100, 225)));

        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 100;
        panel.add(editTextArea, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        panel.add(inputButton, gbc);
        panel.setSize(500, 500);

        final String[] user = {""};
        inputButton.addActionListener(e -> {
            // je recupere le text entre par l'utilisateur
            user[0] = editTextArea.getText();
            editTextArea.setText("");
            userName = user[0];
            u.inFile();
            launchMenu();
        });
    }

    public void launchMenu() {
        panel.removeAll();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 100;

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;

        JButton play = new JButton("Play");
        JButton rules = new JButton("Rules");
        JButton quit = new JButton("Quit");

        play.addActionListener((event) -> controller.play());
        rules.addActionListener((event) -> controller.rules());
        quit.addActionListener((event) -> controller.quit());
        panel.add(play);
        panel.add(rules);
        panel.add(quit);
        panel.revalidate();
        this.repaint();
    }

    public void createJlabel(String s, int i, int j){
        Map<String, String> map = new HashMap<>();
        map.put("yellow", "./Resources/YellowSquare.jpeg");
        map.put("blue", "./Resources/BlueSquare.png");
        map.put("red", "./Resources/RedSquare.png");
        map.put("green" , "./Resources/GreenSquare.png");
        map.put("wall", "./Resources/WallSquare.jpg");
        map.put("pet", "./Resources/PetSquare.jpg");
        ImageIcon icon = new ImageIcon(map.get(s));
        JLabel jl = new JLabel(icon);
        jl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.iconClicked(i, j);
            }
        });
        panel.add(jl);
    }

    public void initCase(){
        // initialisation du plateau de la vue
        for (int i = 1; i<=board.getWidth(); i++){
            for (int j = 1; j<=board.getHeight(); j++){
                if(board.getBoard()[i][j].isPresent()) {
                    createJlabel(board.getBoard()[i][j].getColor(), i, j);
                }else{
                    panel.add(new JLabel());
                }
            }
        }
    }

    public void update() {
        panel.removeAll();
        updateScore();
        if(isTheGameWin()){
            controller.gameWon();
        }else if(isTheGameOver()){
            controller.gameLost();
        }else {
            //mise a jour de la vue grâce aux nouvelles informations
            for (int i = 1; i <= board.getWidth(); i++) {
                for (int j = 1; j <= board.getHeight(); j++) {
                    if (board.getBoard()[i][j].isPresent()) {
                        createJlabel(board.getBoard()[i][j].getColor(), i, j);
                    } else {
                        JLabel jl = new JLabel();
                        panel.add(jl);
                    }
                }
            }
            panel.revalidate();
            repaint();
        }
    }

    public void displayGameOver() {
        panel.removeAll();

        JTextArea loose = new JTextArea("PERDU");
        JButton replay = new JButton("REJOUER");
        replay.addActionListener((event) -> update());

        panel.add(loose);
        panel.add(replay);
        panel.revalidate();
        this.repaint();
    }

    public void updateScore() {
        int tmp = board.getScore();
        scoreTotal += tmp;
        scoreOfTheGame += tmp;

        lvlProgression.removeAll();
        GroupLayout layout = new GroupLayout(lvlProgression);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        lvlProgression.setLayout(layout);

        JLabel j = new JLabel("Vous etes au niveau " + level);
        JLabel j1 = new JLabel("Score Actuel " + scoreOfTheGame);
        JLabel j2 = new JLabel("Score Total " + scoreTotal);

        if(scoreOfTheGame > 250 && rocketNotUsed) {
            //affichage du bonus
            JButton rocket = new JButton(new ImageIcon("./Resources/rocket.jpg"));
            rocket.addActionListener((event) -> rocketInUse = !rocketInUse);

            layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(LEADING).addComponent(j).addComponent(j1).addComponent(j2).addComponent(rocket)));
            layout.setVerticalGroup(layout.createSequentialGroup().addComponent(j).addComponent(j1).addComponent(j2).addComponent(rocket));
            lvlProgression.add(j);
            lvlProgression.add(j1);
            lvlProgression.add(j2);
            lvlProgression.add(rocket);
        }else {
            //bonus non disponible
            layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(LEADING).addComponent(j).addComponent(j1).addComponent(j2)));
            layout.setVerticalGroup(layout.createSequentialGroup().addComponent(j).addComponent(j1).addComponent(j2));
            lvlProgression.add(j);
            lvlProgression.add(j1);
            lvlProgression.add(j2);
        }
    }


    public void launchRules(){
        panel.removeAll();
        panel.setLayout(new GridLayout(4, 0));
        JLabel j1 = new JLabel("Pour detruire une Case il faut cliquer sur une case qui est collé a une case de la même couleur.");
        JLabel j2 = new JLabel("Il y a 2 mode de jeu, si des animaux sont présent , il faut les faire tomber");
        JLabel j3 = new JLabel("Sinon il faut tout détruire !");

        JButton back = new JButton("BACK");
        back.addActionListener((event) -> controller.menu());

        panel.add(j1);
        panel.add(j2);
        panel.add(j3);
        panel.add(back);
        panel.revalidate();
        this.repaint();
    }

    public void quit(){
       this.dispose();
    }

    public boolean isTheGameOver(){
        return controller.isTheGameOver();
    }
    public boolean isTheGameWin(){
        return controller.isTheGameWin();
    }

    public void launchGame() {
        panel.removeAll();
        panel.setLayout(new GridLayout(board.getWidth(), board.getHeight()));
        initCase();
        panel.revalidate();

        //ceci sert à ce que le scoretotal ne soit pas réinitialiser à 0 après une défaite si l'uitlisateur avait déjà joué
        if(scoreTotal>0){
            lastTotalScore = scoreTotal;
        }
        updateScore();

        this.getContentPane().setLayout(new GridLayout(1,0));
        this.getContentPane().add(lvlProgression);

        this.repaint();
    }
}

