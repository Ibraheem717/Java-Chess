class Tower extends Troops 
{

    public Tower (int X, int Y) {
        super(X, Y);
        Symbol = "T";
        index = 4;
    }

    public boolean IfLegal(int [] OldCoords) {
        if (OldCoords[0] == x || OldCoords[1] == y) {
            return true;
        }
        return false;
    }

    public int [] FindLimit (int side, int number, int [][] coords) {
        int [] k = new int []{0,8};
        for (int [] i: coords) {
            if (i[side] > number) {
                if (k[1] > i[side]) {
                    k[1] = i[side]; }
            }
            if (i[side] < number) {
                if (k[0] < i[side]) {
                    k[0] = i[side]; }
            }
            
        }
        return k;
    }

    private int [][] Lengths(int Start, boolean side, int limit, int increment) {
        int [][] arr = new int [0][2];
        for (int i = Start, counter=0; i != limit; i+=increment, counter++) {
            arr = Increase(arr);
            if (side) {
                arr[counter][0] = x;
                arr[counter][1] = i;
            }
            else {
                arr[counter][0] = i;
                arr[counter][1] = y;
            }
        }
        return arr;
    }

    public int [][][] LocateCapturepoints() {
        return new int [][][] {Lengths(y, true, 8, 1), Lengths(y, true, -1, -1),
                            Lengths(x, false, 8, 1), Lengths(x, false, -1, -1)};
    }
}