
package projet.modele;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class User {
    private final String file ="./Resources/userList.txt";
    private View v;

    public void setView(View vi){
        v = vi;
    }


    public void inFile(){
        boolean userIsPresent = false;
        String line;
        String name;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            reader.mark(0);
            while ((line = reader.readLine()) != null) {
                // parcours tout mon fichier a la recherche du pseudo corespondant
                if(line.length() != 0) {
                    name = findUserName(line);
                    if (name.equals(v.getUserName())) {
                        //pseudo present dans le fichier donc je met a jour les infos du jeu
                        userIsPresent = true;
                        v.setLevel(findLevel(line));
                        v.setScoreTotal(findTotalScore(line));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        if(!userIsPresent){
            makeNewLine();
        }
    }

    public void makeNewLine(){
        //Si le pseudo n'était pas présent on le rajoute
        try {
            StringBuffer inputBuffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                inputBuffer.append(line);
            }
            reader.close();

            line = makeUserLineForFile();
            inputBuffer.append('\n');
            inputBuffer.append(line);
            inputBuffer.append('\n');

            FileOutputStream fileOut = new FileOutputStream("./Resources/userList.txt");
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

    public String makeUserLineForFile(){
        return v.getUserName() + "|" + v.getLevel() + "#" + v.getScoreTotal() + '\n';
    }

    public int findLevel(String line) { return Integer.parseInt(line.substring(line.indexOf("|") +1, line.indexOf("#")));}

    public int findTotalScore(String line){ return Integer.parseInt(line.substring(line.indexOf("#") +1));}

    public String findUserName(String line) {
        return line.substring(0, line.indexOf("|"));
    }

    public void updateLevel(){
        v.setLevel(v.getLevel()+1);
        try {
            //reecriture dans mon fichier à partir des nouvelles infos
            BufferedReader file = new BufferedReader(new FileReader("./Resources/userList.txt"));
            StringBuffer inputBuffer = new StringBuffer();
            String line;
            String name;

            while ((line = file.readLine()) != null) {
                if(line.length() != 0) {
                    name = findUserName(line);
                    if (name.equals(v.getUserName())) {
                        line = makeUserLineForFile();
                    }
                }
                inputBuffer.append('\n');
                inputBuffer.append(line);
            }
            file.close();

            FileOutputStream fileOut = new FileOutputStream("./Resources/userList.txt");
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
        v.updateScore();
    }

}
