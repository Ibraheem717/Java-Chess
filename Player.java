import java.util.*;

class Player 
{
    Boolean Start = false;
    int StartInt = 0;
    King King;
    String name;
    int [][][] AllCoords;
    int [][][] CaptureCoords;
    Troops [][] All = new Troops [6][];
    Troops [][] OldAll = new Troops [6][];
    boolean Capture = false;

    public Player(boolean White) {
        name = "white";
        if (White == true) {
            Start = true; 
            StartInt = 7;
            name = "black";
        }
        SetUpPawn(White);
        SetUpTower();
        SetUpKnight();
        SetUpBishop();
        SetUpQueen();
        SetUpKing();
    }

    public boolean GetCapture() {
        return Capture; }

    public int FindStartSide () {
        if (Start) 
            return 6;
        return 0;
    }

    public void SwitchCapture() {
        Capture = false; }

    public boolean GetStart() {
        return Start; }

    public Troops [][] GetAll() {
        return All; }

    public Troops [][] GetOldAll() {
        return OldAll; }

    public King GetKing() {
        return King; }

    public Troops GetPiece (int mouseX, int mouseY) {
        for (Troops [] Group : All)
            for (Troops indi : Group)
                if (mouseX/64 == indi.GetX() && mouseY/64 == indi.GetY())
                    return indi;
        return null;
    }

    public void Flush() {
        AllCoords = null;
        CaptureCoords = null;
    }

    public void Replace () {
        OldAll = Arrays.copyOf(All, All.length);  }

    public void restore() {
        System.out.println(" New Arr: "+ Arrays.deepToString(All));
        System.out.println(" Old Arr: "+ Arrays.deepToString(OldAll));
        All = Arrays.copyOf(OldAll, OldAll.length); 
        System.out.println(" New New Arr: "+ Arrays.deepToString(All));
        OldAll = null; 
    }
    
    public boolean LegalMove(int [] Old, Troops Piece, Troops CapturePiece, Player Oppo) {
        boolean Found = false;
        boolean TrueCapture = false;

        Found = Piece.IfLegal(Old);
        System.out.println(2343);
        if (CapturePiece != null) {
            System.out.println("\n Here I am \n");
            Found = Piece.CheckForCapture(Old, CapturePiece);
            if (Found) {
                TrueCapture = true;
                Oppo.Replace();
                
                Oppo.RemovePiece(CapturePiece);
            }
        }
        Capture = TrueCapture;
        System.out.println("Found: " + Found + CapturePiece);
        return Found;
    }

    public boolean CheckForOverlap (Troops Piecer, Player Other, int [] old) {
        // int [][] temp = (Limit(Piecer.LeadUp(old), new Player [] {this, Other}, 0, Piecer));
        // System.out.println(Arrays.deepToString(Piecer.LeadUp(old)));
        // System.out.println(Arrays.deepToString(temp));
        if (Arrays.deepEquals(Piecer.LeadUp(old), Limit(Piecer.LeadUp(old), new Player [] {this, Other}, 0, Piecer)))
            return true;
        return false;
    }

    public boolean CheckForCheck(Player userPlayer) {
        boolean check = true;
        AllCoords = new int [0][0][2];
        CaptureCoords = new int [0][0][2];
        for (Troops [] Group: All)
            for (Troops indi: Group)
                for (int [][] arr: LimitCrossing(indi.LocateCapturepoints(), userPlayer))  
                    AllCoords = Increase_Third(AllCoords, arr);
        // for (int [][] a: AllCoords)
        //     System.out.println(this.name + " CaptureCoords: " + Arrays.deepToString(a));
        // System.out.println(userPlayer.name + " King Coordinates: " + Arrays.toString(new int [] {userPlayer.GetKing().GetX(), userPlayer.GetKing().GetY()}));
        for (int [][] arr: AllCoords)
            for (int [] Coords: arr)
                if (Arrays.equals(Coords, new int [] {userPlayer.GetKing().GetX(), userPlayer.GetKing().GetY()})){
                    check = false;
                    CaptureCoords = Increase_Third(CaptureCoords, arr);
                }
        if (!check) 
                System.out.println("Falsifying here!!!!!");
        return check;
    }

    private int CountTroops(int [] arr, Player user) {
        int i = 0;
        for (Troops [] list : user.GetAll())
            for (Troops j : list)
                if (arr[0] == j.GetX() && arr[1] == j.GetY())
                    i++;
        return i;
    }

    private int [][] Limit (int [][] List, Player [] User, int start, Troops Piece) {
        int counter;
        for (counter = start; counter < List.length; counter++)
            for (Player users: User)
                for (Troops [] arr: users.GetAll())
                    for (Troops i: arr)
                        if (List[counter][0] == i.GetX() && List[counter][1] == i.GetY())
                            if (Piece != null)
                                if (List[counter][0] == Piece.GetX() && List[counter][1] == Piece.GetY() && CountTroops(List[counter], this) == 1)
                                    return Arrays.copyOfRange(List, 0, counter+1);
                            
                            else if (users.GetStart() == this.GetStart())
                                return Arrays.copyOfRange(List, 0, counter);
                            else
                                return Arrays.copyOfRange(List, 0, counter+1);
        return Arrays.copyOfRange(List, 0, counter);
    }

    public int [][][] LimitCrossing(int [][][] Points, Player Other) {
        int [][][] temp = new int [0][0][2];

        for (int [][] locs: Points)
            if (locs.length>1)
                temp = Increase_Third(temp, Limit(locs, new Player [] {this, Other}, 1, null));

        return temp;
    }

    public int [][][] Increase_Third(int [][][] arr, int [][] point) {
        int [][][] Arr = Arrays.copyOf(arr, arr.length+1);
        Arr[arr.length] = point;
        return Arr;
    }

    public int [][] Increase(int [][] arr, int [] newValue) {
        int [][] Arr = Arrays.copyOf(arr, arr.length+1); 
        Arr[arr.length] = newValue;
        return Arr;
    }

    private int Block (int [][] CaptureString, int [][][] OpppnentAllMoves) {
        int counter = 0;
        for (int [] CapturePoints: CaptureString)
            for (int [][] LocMove : OpppnentAllMoves)
                for (int [] Loc : LocMove)
                    if (Arrays.equals(Loc, CapturePoints)){
                        System.out.println(this.name + " Preventtion: " + Arrays.deepToString(LocMove));
                        counter++;
                    }
        return counter;
    }

    private int [][] LimitPawn(int [][] arr, Troops Piece, Player Other) {
        int [][] correction = new int [0][2];
        for (int [] Loc: arr){
            if (Loc[0] == Piece.GetX()) 

                if (GetPiece(Loc[0]*64, Loc[1]*64) == null && Other.GetPiece(Loc[0]*64, Loc[1]*64) == null)
                    correction = Increase(correction, Loc);
            
            else if (Loc[1] != Piece.GetY()) {
                
                if (GetPiece(Loc[0]*64, Loc[1]*64) == null && Other.GetPiece(Loc[0]*64, Loc[1]*64) == null)
                    correction = Increase(correction, Loc);
            }
        }
        return correction;
    }

    public boolean CheckForCheckMate(Player userPlayer) {
        if (CaptureCoords!=null) {
            int [][] temp = new int [0][2];
            int [][][] OpppnentAllMoves = new int [0][0][2];
            for (Troops [] Group: userPlayer.GetAll())
                for (Troops indi: Group)
                    for (int [][] arr: userPlayer.LimitCrossing(indi.LocateMovePoints(), this))
                        if (indi.getClass() == Pawns.class)
                            OpppnentAllMoves = Increase_Third(OpppnentAllMoves, LimitPawn(arr, indi, userPlayer));
                        else if (indi.getClass() == King.class);
                        else
                            OpppnentAllMoves = Increase_Third(OpppnentAllMoves, arr);
            int counter=0;
            for (int [][] CaptureString: CaptureCoords)
                counter += Block(CaptureString, OpppnentAllMoves);
            // for (int [][] a: OpppnentAllMoves)
            // System.out.println(userPlayer.name + " CaptureCoords: " + Arrays.deepToString(a));
            // System.out.println(this.name + " CaptureCoords: " + Arrays.deepToString(CaptureCoords));
            // System.out.println(counter + " " + CaptureCoords.length);
            if (counter<=CaptureCoords.length){
                for (int [][] arr : userPlayer.LimitCrossing(userPlayer.GetKing().LocateCapturepoints(), userPlayer))
                    if (arr.length>1)
                        temp = Increase(temp, arr[1]);
                System.out.println(userPlayer.name + " temp: " + Arrays.deepToString(temp));
                counter=0;
                for (int [] l: temp)
                    counter += Block(new int [][] {l}, AllCoords);
                if (counter>=temp.length)
                    return true;
            }
        }
        return false;
    }

    public void RemovePiece (Troops Piece) {
        for (int over = 0; over < All.length; over++) 
            if (All[over][0].getClass() == Piece.getClass()) {
                Troops [] arr = new Troops[All[over].length - 1];
                for (int i = 0, j = 0; i < All[over].length; i++, j++)
                    if (All[over][i] != Piece)
                        arr[j] = All[over][i];
                    else
                        j--;
                All[over] = arr;
            }
        SwitchCapture();
    }

    public Troops GetTroop (int [] Coords) {
        for (Troops [] Type : All)
            for (Troops Piece : Type) 
                if (Coords[0] == Piece.GetX())
                    if (Coords[1] == Piece.GetY())
                        return Piece;
        return new Troops(100, 100); 
    }

    public void SetUpPawn(boolean UpDown) {
        Pawns [] Pawn = new Pawns[8];
        int PStart = 1;
        if (StartInt == 7) 
            PStart = 6;
        
        for (int i = 0; i < Pawn.length; i++) 
            Pawn[i] = new Pawns(PStart , i, UpDown);
        
        All[0] = Pawn;
    }

    public void SetUpTower() {
        Tower [] Towers = new Tower[2];
        for (int i = 0; i < Towers.length; i++) 
            Towers[i] = new Tower(StartInt , i*7);
        
        All[1] = Towers;
    } 

    public void SetUpKnight() {
        Knight [] Knights = new Knight[2];
        int x = 1;
        for (int i = 0; i < Knights.length; i++) {
            Knights[i] = new Knight(StartInt, x);
            x = 6;}
        
        All[2] = Knights;
    } 

    public void SetUpBishop() {
        Bishop [] Bishops = new Bishop[2];
        for (int i = 0; i < Bishops.length; i++) 
            Bishops[i] = new Bishop(StartInt , i * 3 + 2);
        
        All[3] = Bishops;
    } 

    public void SetUpQueen() {
        Queen [] Queens = new Queen[1];
        for (int i = 0; i < Queens.length; i++) 
            Queens[i] = new Queen(StartInt , 3);
        
        All[4] = Queens;
    } 

    public void SetUpKing() {
        King [] Kings = new King[1];
        for (int i = 0; i < Kings.length; i++) 
            Kings[i] = new King(StartInt , 4);
        King = Kings[0];
        All[5] = Kings;
    } 
}