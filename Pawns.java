import java.lang.Math;

class Pawns extends Troops
{
    boolean First;
    boolean Side;

    public Pawns (int X, int Y, boolean SW) {
        super(X, Y);
        First = true;
        Side = SW;
        Symbol = "P";
        index = 5;
    }

    private int Advance (int amount) {
        if (Side)
            return x-amount;
        return x+amount;
    }

    public boolean IfLegal(int [] OldCoords) {
        if (y != OldCoords[1])
            return false;
        if (First) 
            if (OldCoords[0] == Advance(-2)){
                First=false;
                System.out.println("Here");
                return true;}
        if (OldCoords[0] == Advance(-1)){
            First=false;
            System.out.println("No Here" + Advance(-1) + " " + OldCoords[0]);
            return true;}
        System.out.println("WHATS GOING ON HERE");
        return false;
    }

    public boolean CheckForCapture (int [] Old, Troops CaptureTarget) {
        System.out.println(Advance(-1) + " : " + CaptureTarget.GetX() + " : " + x + " : " + Old[0]);
        if (x == CaptureTarget.GetX() && Advance(-1) == Old[0]) 
            if (Math.abs(Old[1] - CaptureTarget.GetY()) == 1) {
                return true; }
        return false;
        
    }   

    public int [][] GetCapture (int nx, int ny) {
        if (nx > 7 || nx < 0 || ny > 7 || ny < 0)
            return new int [][] {new int [] {x, y}};
        return new int [][] {new int [] {x, y}, new int [] {nx, ny}};
    }

    public int [][][] LocateMovePoints() {
        int [][][] arr = new int [3][0][2];
        
        if (First)
            arr[0] = new int [][] { new int [] {x, y} , new int [] {Advance(1), y} , new int [] {Advance(2), y } };
        else
            arr[0] = GetCapture(Advance(1), y);
        int counter = 1;
        for (int [][] i: LocateCapturepoints()){
            arr[counter] = i;
            counter++;
        }
        return arr;
    }
    
    public int [][][] LocateCapturepoints() {
        int [][][] arr = new int[2][0][2];
        int counter = 0;
        for (int i = y - 1; i < y+2; i += 2) {  
            arr[counter] = GetCapture(Advance(1), i);
            counter++;
        }
        return arr;
    }
}