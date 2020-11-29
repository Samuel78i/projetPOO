package projet.modele;

import javax.print.event.PrintJobAttributeListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class View extends JFrame {
    private JPanel panel= new JPanel();
    private Controller controller;
    private Board board;

    public View(Board b, Controller c){
        board = b;
        controller = c;
        this.setTitle("GAME");
        this.setSize(b.getWidth()*100, b.getHeight()*100);
        panel.setLayout(new GridLayout(board.getWidth(), board.getHeight()));
        initCase();
        this.getContentPane().add(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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

}

