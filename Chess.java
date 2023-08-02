import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;

class Chess 
{

    public static Troops selectedPiece=null;
    public static void main (String [] args) throws IOException{
        BufferedImage allImages = ImageIO.read(new File("chess.png"));
        Image [] Imgs = new Image [12];
        int ind = 0;
        for(int y=0;y<400;y+=200)
            for(int x=0;x<1200;x+=200) {
                Imgs[ind]=allImages.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                ind++;
            }    
        Player PersonOne;
        Player PersonTwo;
        String Colour = "rg";
        if (Colour.equals("Y")) {
            PersonOne = new Player(true);
            PersonTwo = new Player(false);
        }
        else {
            PersonOne = new Player(false);
            PersonTwo = new Player(true);
        }
        String [][] Board = new String [8][8];

        Run(PersonOne, PersonTwo, Board, Imgs);
    }



    public static void Run(Player x, Player y, String [][] Board, Image [] Imgs) {
        Player CurrentPlayer = FindCurrentPlayer(x, y);
        Player OtherPlayer = FindOtherPlayer(x, y);
        JBoard boardJFrame = new JBoard(Imgs, CurrentPlayer, OtherPlayer);
        boardJFrame.InteractWithBoard();
    }

    public static Player FindOtherPlayer(Player x, Player y) {
        if (x.GetStart() == false) {
            return x;
        }
        return y;
    }

    public static Player FindCurrentPlayer(Player x, Player y) {
        if (x.GetStart() == true) {
            return x;
        }
        return y;
    }
}