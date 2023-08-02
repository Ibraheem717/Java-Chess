class Bishop extends Troops
{
    public Bishop (int X, int Y) {
        super(X, Y);
        Symbol = "B";
        index = 2;
    }

    public boolean IfLegal(int [] Coords) {
        int Grad = (Coords[0] - x)/(Coords[1] - y);
        System.out.println(Grad*Grad);
        if (Grad*Grad == 1) {
            return true;
        }
        return false;
    }

    private int [][] Lengths (int sx, int yx, int incrementX, int incrementY, int limitx, int limity) {
        int [][] arr = new int [0][2];
        while (sx != limitx && yx != limity){
            arr = Increase(arr);
            arr[arr.length-1][0]=sx;
            arr[arr.length-1][1]=yx;
            sx += incrementX;
            yx += incrementY;
        }
        return arr;
    }

    public int [][][] LocateCapturepoints () {
        // int [][][] arr = new int [4][0][2];

        // arr[0] = Lengths(x, y, -1, 1, -1, 8);
        // arr[1] = Lengths(x, y, 1, -1, 8, -1);
        // arr[2] = Lengths(x, y, 1, 1, 8, 8);
        // arr[3] = Lengths(x, y, -1, -1, -1, -1);
        // return arr;
        return new int [][][] {Lengths(x, y, -1, 1, -1, 8), Lengths(x, y, 1, -1, 8, -1),
                                Lengths(x, y, 1, 1, 8, 8), Lengths(x, y, -1, -1, -1, -1)};
    }
}