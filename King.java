import java.lang.Math;

class King extends Troops
{


    public King (int X, int Y) {
        super(X, Y);
        Symbol = "K";
        index = 0;
    }

    public boolean IfLegal(int [] Coords) {
        int Xdiff = Math.abs(x - Coords[0]);
        int Ydiff = Math.abs(y - Coords[1]);
        if (Ydiff == 1 || Xdiff == 1) {
            return true;
        }
        return false;
    }

    public int [][][] LocateCapturepoints() {
        int [][][] arr = new int [8][0][2];
        int counter = 0;
        for (int i = -1; i < 2; i++) 
            for (int j = -1; j < 2; j++) 
                if (!(i == 0 && j == 0)) {
                    arr[counter] = GetCapture(i, j);
                    counter++;
                }  
        return arr;
    }
}