class Queen extends Troops
{
    public Queen (int X, int Y) {
        super(X, Y);
        Symbol = "Q";
        index = 1;
    }

    public boolean IfLegal(int [] Coords) {
        if (Coords[0] == x || Coords[1] == y) {
            return true; }
        int Grad = Math.abs(Coords[0] - x)/(Coords[1] - y);
        if (Grad*Grad == 1) {
            return true; }
        return false;
    }

    private int [][] Lengths_Bishop (int sx, int yx, int incrementX, int incrementY, int limitx, int limity) {
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

    private int [][] Lengths_Tower(int Start, boolean side, int limit, int increment) {
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

    public int [][][] LocateCapturepoints () {
        return new int [][][] {Lengths_Bishop(x, y, -1, 1, -1, 8), Lengths_Bishop(x, y, 1, -1, 8, -1),
            Lengths_Bishop(x, y, 1, 1, 8, 8), Lengths_Bishop(x, y, -1, -1, -1, -1), 
            Lengths_Tower(y, true, 8, 1), Lengths_Tower(y, true, -1, -1),
            Lengths_Tower(x, false, 8, 1), Lengths_Tower(x, false, -1, -1)};
    }
}