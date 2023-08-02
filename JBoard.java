import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.awt.event.MouseEvent;
import java.awt.Image;

class JBoard {

    Troops selectedPiece=null;
    JFrame frame = new JFrame();
    Player Current;
    Player Opppnent;
    Troops Piece;
    Boolean Check = false;
    Player templayer;
    JPanel JP;
    Image [] Imgs;
    int [] CoordsOld;
    int [] CoordsNew;

    public JFrame GetFrame() {
        return frame; }

    public int [] GetCoordsOld () {
        return CoordsOld; }

    public int [] GetCoordsNew () {
        return CoordsNew; }

    public void SetOldCoords(int i, int j) {
        CoordsOld = new int [] {i,j};
    }

    public void SetNewCoords(int i, int j) {
        CoordsNew = new int [] {i,j}; }

    public JBoard (Image [] Images, Player p1, Player p2) {
        frame = new JFrame();
        frame.setBounds(10, 10, 512, 532);
        Current=p1;
        Opppnent=p2;
        Imgs = Images;

        JP = new JPanel() {
            public void paint(Graphics g) {
                boolean black = false;
                for (int i = 0; i < 8; i++){
                    for (int j = 0; j < 8; j++) {
                        if (black)
                            g.setColor(Color.gray);
                        else
                            g.setColor(Color.white);
                        g.fillRect(j*64, i*64, 64, 64);
                        black = !black;
                    }
                    black = !black;
                }
                for (Troops [] Group: p1.GetAll())
                    for (Troops inTroops: Group)
                        g.drawImage(Imgs[inTroops.GetIndex() + p1.FindStartSide()], inTroops.GetBY(), inTroops.GetBX(), this);
                for (Troops [] Group: p2.GetAll())
                    for (Troops inTroops: Group)
                        g.drawImage(Imgs[inTroops.GetIndex() + p2.FindStartSide()], inTroops.GetBY(), inTroops.GetBX(), this);
            }
        }; 

                
        frame.add(JP);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);
    }

    public void InteractWithBoard() {


        frame.addMouseMotionListener(new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {
                if (selectedPiece!=null) {
                    selectedPiece.bx = e.getY()-32;
                    selectedPiece.by = e.getX()-32;
                    frame.repaint();
                }
            }

            public void mouseMoved(MouseEvent e) {}
            
        });

        frame.addMouseListener(new MouseInputListener() {
            public void mouseClicked(MouseEvent e) {}

            public void mousePressed(MouseEvent e) {
                selectedPiece = Current.GetPiece(e.getY(), e.getX());
                if (selectedPiece!=null) 
                    SetOldCoords(selectedPiece.GetX(), selectedPiece.GetY());
            }

            public void mouseReleased(MouseEvent e) {
                if (selectedPiece != null) {
                        SetNewCoords(e.getY()/64, e.getX()/64);
                    
                    if (!Arrays.equals(CoordsNew, CoordsOld)) {
                        Piece = Opppnent.GetPiece(e.getY(), e.getX());
                        selectedPiece.MakeChanges(CoordsNew);
                        if (Current.LegalMove(CoordsOld, selectedPiece, Piece, Opppnent) &&
                            Current.CheckForOverlap(selectedPiece, Opppnent, CoordsOld) &&
                            Opppnent.CheckForCheck(Current)){
                            System.out.println("Cool");

                            // if (Current.GetCapture()) 
                            //     Opppnent.RemovePiece(Piece);

                            if (!Current.CheckForCheck(Opppnent)) {
                                System.out.println("Check");
                                if (Current.CheckForCheckMate(Opppnent)){
                                    System.out.println("Yay" + Current + " Wins!");
                                    System.exit(0);
                                }
                            }

                            Current.Flush();
                            Opppnent.Flush();
                            templayer = Current;
                            Current = Opppnent;
                            Opppnent = templayer;
                        }
                        else{
                            System.out.println("Illegal"); 
                            selectedPiece.MakeChanges(CoordsOld);
                            if (Current.GetCapture())
                                Opppnent.restore();
                        }
                    }
                    else
                        selectedPiece.MakeChanges(CoordsOld);
                    frame.repaint(); 
                    selectedPiece=null;
                }
            }

            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            public void mouseDragged(MouseEvent e) {}
            public void mouseMoved(MouseEvent e) {}
        });
    }
    
}
