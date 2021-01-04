package projet.modele;

import java.util.Scanner;

public class Player {
    private String name;
    private final Scanner scanAnswer;

    public Player(){
        name = "The secret guy";
        scanAnswer = new Scanner(System.in);
    }

    public void setName(String n){
        name = n;
    }

    public boolean wantToPlay(){
        System.out.println("Do you want to play ?(yes/no)");
        String a = scanAnswer.nextLine();
        return a.equals("yes");
    }

    public void askSetName(){
        System.out.println("Do you want to set a name ?(yes/no)");
        String a = scanAnswer.nextLine();
        if(a.equals("yes")){
            System.out.print("So, what's your name ?");
            a = scanAnswer.nextLine();
            setName(a);
        }
    }

    public int askDestroyX(Board b){
        System.out.println("which line do you want to click ?");
        int a = scanAnswer.nextInt();

        if(a<b.getHeight() && a>0) {
            return a;
        }else{
            return wrongDestroy(b);
        }
    }

    public int wrongDestroy(Board b){
        System.out.println("The number you choose isn't part of the board. Please try again");
        int a = scanAnswer.nextInt();
        if(a<b.getHeight() && a>0) {
            return a;
        }else{
            return wrongDestroy(b);
        }
    }
    public int askDestroyY(Board b){
        System.out.println("Now the column ?");
        int a = scanAnswer.nextInt();
        if(a<b.getWidth() && a>0) {
            return a;
        }else{
            return wrongDestroy(b);
        }
    }

    public boolean playAgain(){
        System.out.println("Nice game" + name + ", good job. Thanks for playing");
        System.out.println("Do you want to play again ?(yes/no)");
        String a = scanAnswer.nextLine();
        return a.equals("yes");
    }
}
