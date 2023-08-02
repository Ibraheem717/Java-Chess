import java.lang.Math;

class Knight extends Troops
{
    public Knight (int X, int Y) {
        super(X, Y);
        Symbol = "Kn";
        index = 3;
    }
    
    public boolean IfLegal(int [] Coords) {
        int newX = Math.abs(x - Coords[0]);
        int newY = Math.abs(y - Coords[1]);
        if (newX == 2 && newY == 1) {
            return true;
        }
        if (newX == 1 && newY == 2) {
            return true;
        }
        return false;
    }

    public int [][] LeadUp (int [] arr) {
        return new int [][] {new int [] {x,y}}; }


    public int [][][] LocateCapturepoints () {
        int [][][] arr = new int [8][1][2];
        int counter = 0;
        int hori = 0;
        for (int vert = -2; vert < 3; vert++)
            if (vert!=0)
                for (hori = -2; hori < 3; hori++) 
                    if (hori!=0 && hori!=vert && hori!=vert*-1) {
                        arr[counter] = GetCapture(vert, hori);
                        counter++;
                    }
        return arr;
    }
}