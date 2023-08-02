import java.util.*;
import java.lang.Math;

interface TroopsExtra {

    public boolean IfLegal (int [] coords);

    public boolean CheckForCapture (int [] old, Troops Cap);

    public int [][][] LocateCapturepoints ();
}

class Troops implements TroopsExtra
{
    int x;
    int y;
    int bx;
    int by;
    int index;
    String Symbol;

    public Troops (int X, int Y) {
        x = X;
        y = Y;
        bx = x * 64;
        by = y * 64;

    }

    public int GetIndex () {
        return index; }

    public String GetSymbol() {
        return Symbol;
    }

    public int GetX() {
        return x;
    } 

    public int GetBX() {
        return bx;
    } 

    public int GetBY() {
        return by;
    } 

    public void GetInfo() {
        System.out.println();
        System.out.println("Symbol: " + Symbol);
        System.out.println("X Coord: " + x);
        System.out.println("Y Coord: " + y);
        System.out.println();
    }

    public int GetY() {
        return y;
    }

    public int [][] Increase(int [][] arr) {
        int [][] Arr = Arrays.copyOf(arr, arr.length+1); 
        Arr[arr.length] = new int [2];
        return Arr;
    }

    public void MakeChanges(int [] Coord) {
        x = Coord[0];
        y = Coord[1];
        bx = x * 64;
        by = y * 64;
    }

    public void print () {
        System.out.println("\n" + Arrays.toString(new int [] {x, y}));
    }

    public void PRINT(int [][] Crossing) {
        System.out.println(111);
        for (int i = 0; i < Crossing.length; i++) {
            for (int j = 0; j < Crossing[i].length; j++) {
                System.out.print(Crossing[i][j]);
            }
            System.out.println("-------");
        }
    }

    public int [][] GetCapture (int nx, int ny) {
        if (x + nx > 7 || x + nx < 0 || y + ny > 7 || y + ny < 0)
            return new int [][] {new int [] {x, y}};
        return new int [][] {new int [] {x, y}, new int [] {x+nx, y+ny}};
    }

    public int [][] LeadUp (int [] arr) {
        int Len = Math.abs(arr[0] - x);
        if (arr[0] == x) {
            Len = Math.abs(arr[1] - y);
        }
        int [][] Coords = new int [Len][2];
        int Counter = 0;
        if (arr[0] > x) {
            for (int i = x; i < arr[0]; i++) {
                Coords[(Len-1) - Counter][0] = i;
                Counter++;
            }
        } 
        else if (arr[0] == x) {
            for (int i = 0; i < Len; i++) {
                Coords[Counter][0] = x;
                Counter++;
            }
        }
        else {
            for (int i = arr[0]+1; i < x+1; i++) {
                Coords[Counter][0] = i;
                Counter++;
            }
        }
        Counter = 0;
        if (arr[1] > y) {
            for (int i = y; i < arr[1]; i++) {
                Coords[(Len-1) - Counter][1] = i;
                Counter++;
            }
        } 
        else if (arr[1] == y) {
            for (int i = 0; i < Len; i++) {
                Coords[Counter][1] = y;
                Counter++;
            }
        }
        else {
            for (int i = arr[1]+1; i < y+1; i++) {
                Coords[Counter][1] = i;
                Counter++;
            }
        }
        return Coords;
    }

    public int [][][] LocateMovePoints () {
        return LocateCapturepoints(); }

    @Override
    public boolean IfLegal(int[] coords) {
        return false;
    }

    @Override
    public boolean CheckForCapture(int [] Old, Troops Cap) {
        return IfLegal(Old);
    }

    @Override
    public int[][][] LocateCapturepoints() {
        return null;
    }
}