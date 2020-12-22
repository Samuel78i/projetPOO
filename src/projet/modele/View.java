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



public class View extends JFrame {
    private JPanel panel= new JPanel();
    private Controller controller;
    private Board board;
    private String userName;
    private int level;

    public View(Board b, Controller c){
        board = b;
        controller = c;
        this.setTitle("GAME");
        this.setSize(b.getWidth()*100, b.getHeight()*100);
        launchUserSelection();
        this.getContentPane().add(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void launchUserSelection() {
        panel.removeAll();

        JButton inputButton = new JButton("Send");
        JTextArea editTextArea = new JTextArea("Type Here!");

        editTextArea.setBackground(Color.LIGHT_GRAY);
        editTextArea.setForeground(Color.BLACK);
        editTextArea.setSize(600,600);

        FlowLayout layout = new FlowLayout();
        layout.setVgap(15);
        panel.setLayout(layout);

        panel.add(editTextArea);
        panel.add(inputButton);

        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        final String[] user = {""};
        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user[0] = editTextArea.getText();
                editTextArea.setText("");
                userName = user[0];
                userInFile();
                launchMenu();
            }
        });
    }

    public void userInFile() {
        boolean userIsPresent = false;
        String file ="/home/samuel/IdeaProjects/poo/Resources/userList.txt";
        String line;
        String name;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                name = findUserName(line);
                if (name == userName) {
                    userIsPresent = true;
                    level = findLevel(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        if(!userIsPresent){
            try {
                FileWriter myWriter = new FileWriter(file);
                myWriter.write(makeUserLineForFile());
                myWriter.close();
            }catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    public String makeUserLineForFile(){
        return userName + "|" + level + "\n";
    }

    public int findLevel(String line) {
        boolean number = false;
        String ch = "";
        int result;

        for(int i = 1; i<line.length(); i++){
            if(number){
                ch = line.substring(i-1, i);
            }
            if(line.substring(i-1, i) == "|"){
                 number = true;
            }

        }
        result = Integer.parseInt(ch);
        return result;
    }

    public String findUserName(String line) {
        boolean nameNotFull = true;
        String result = "";
        for(int i = 1; i<line.length(); i++){
            if(line.substring(i-1, i) == "|"){
                nameNotFull = false;
            }
            if(nameNotFull){
                result = line.substring(i-1, i);
            }
        }
        return result;
    }

    public void launchMenu() {
        panel.removeAll();
        JButton play = new JButton("Play");
        JButton rules = new JButton("Rules");
        JButton quit = new JButton("quit");

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
        map.put("yellow", "/home/samuel/IdeaProjects/poo/Resources/YellowSquare.jpeg");
        map.put("blue", "/home/samuel/IdeaProjects/poo/Resources/BlueSquare.png");
        map.put("red", "/home/samuel/IdeaProjects/poo/Resources/RedSquare.png");
        map.put("green" , "/home/samuel/IdeaProjects/poo/Resources/GreenSquare.png");
        Icon icon = new ImageIcon(map.get(s));
        JLabel jl = new JLabel(icon);
        int finalI = i;
        int finalJ = j;
        jl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.iconClicked(finalI, finalJ);
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
        if(isTheGameOver()){
            board = new Board(board.getWidth(), board.getHeight());
            controller.setBoard(board);
        }
        for (int i = 1; i<=board.getWidth(); i++){
            for (int j = 1; j<=board.getHeight(); j++){
                if(board.getBoard()[i][j].isPresent()) {
                    createJlabel(board.getBoard()[i][j].getColor(), i, j);
                }else{
                    JLabel jl = new JLabel();
                    panel.add(jl);
                }
            }
        }
        panel.revalidate();
        repaint();
    }


    public void launchRules(){
        panel.removeAll();
        Icon icon = new ImageIcon("/home/samuel/IdeaProjects/poo/Resources/YellowSquare.jpeg");
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

    public void launchGame() {
        panel.removeAll();
        panel.setLayout(new GridLayout(board.getWidth(), board.getHeight()));
        initCase();
        panel.revalidate();
        this.repaint();
    }
}

