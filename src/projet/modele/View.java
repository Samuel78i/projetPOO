package projet.modele;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import static javax.swing.GroupLayout.Alignment.*;


public class View extends JFrame {
    private JPanel panel= new JPanel();
    private JPanel lvlProgression = new JPanel();
    private final Controller controller;
    private Board board;
    private String userName;
    public User u = new User();
    private int level;
    private int scoreTotal;
    private int lastTotalScore = 0;
    private int scoreOfTheGame;

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

    public String getUserName(){ return userName;}
    public void setUserName(String s){ userName = s;}
    public int getLevel(){ return level;}
    public void setLevel(int i){ level = i;}
    public void setBoard(Board b){ board = b;}
    public void setScoreOfTheGame(int i){ scoreOfTheGame = i;}
    public void setLastTotalScore(int i){ lastTotalScore = i;}
    public int getLastTotalScore(){ return lastTotalScore;}
    public void setScoreTotal(int i){ scoreTotal = i;}
    public int getScoreTotal(){ return scoreTotal;};

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
        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user[0] = editTextArea.getText();
                editTextArea.setText("");
                userName = user[0];
                u.inFile();
                launchMenu();
            }
        });
    }

    public void launchMenu() {
        panel.removeAll();
        JButton play = new JButton("Play");
        JButton rules = new JButton("Rules");
        JButton quit = new JButton("Quit");

        play.addActionListener((event) -> {
            controller.play();
        });
        rules.addActionListener((event) -> {
            controller.rules();
        });
        quit.addActionListener((event) -> {
            controller.quit();
        });
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
        Icon icon = new ImageIcon(map.get(s));
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
        for (int i = 1; i<=board.getWidth(); i++){
            for (int j = 1; j<=board.getHeight(); j++){
                if(board.getBoard()[i][j].isPresent()) {
                    createJlabel(board.getBoard()[i][j].getColor(), i, j);
                }
            }
        }
    }

    public void update() {
        panel.removeAll();

        if(isTheGameWin()){
            controller.gameWon();
        }else if(isTheGameOver()){
            controller.gameLost();
        }else {
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
            updateScore();
            panel.revalidate();
            repaint();
        }
    }

    public void displayGameOver() {
        panel.removeAll();

        JTextArea loose = new JTextArea("PERDU");
        JButton replay = new JButton("REJOUER");
        replay.addActionListener((event) -> {
            update();
        });

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

        layout.setHorizontalGroup(layout.createSequentialGroup() .addGroup(layout.createParallelGroup(LEADING).addComponent(j).addComponent(j1).addComponent(j2)));
        layout.setVerticalGroup(layout.createSequentialGroup().addComponent(j).addComponent(j1).addComponent(j2));
        lvlProgression.add(j);
        lvlProgression.add(j1);
        lvlProgression.add(j2);
    }


    public void launchRules(){
        panel.removeAll();
        Icon icon = new ImageIcon("./Resources/YellowSquare.jpeg");
        JLabel jl = new JLabel(icon);
        JButton back = new JButton("BACK");
        back.addActionListener((event) -> {
            controller.menu();
        });
        panel.add(jl);
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

        if(scoreTotal>0){
            lastTotalScore = scoreTotal;
        }
        updateScore();

        this.getContentPane().setLayout(new GridLayout(1,0));
        this.getContentPane().add(lvlProgression);

        this.repaint();
    }
}

