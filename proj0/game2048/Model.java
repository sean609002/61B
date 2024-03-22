package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        if(side == Side.WEST){
            board.setViewingPerspective(Side.WEST);
        }else if(side == Side.EAST){
            board.setViewingPerspective(Side.EAST);
        }else if(side == Side.SOUTH){
            board.setViewingPerspective(Side.SOUTH);
        }
        changed = moveToNorth(board);
        board.setViewingPerspective(Side.NORTH);
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }
    //when the board is tilted to the north, move every pieces to the right place
    //if nothing happened,return false
    //otherwise, return true
    public boolean moveToNorth(Board board){
        //an array that stored booleans which indicates if it is merged
        boolean[][] mergedBoard = new boolean[board.size()][board.size()];
        boolean flag = false;
        for(int row = board.size() - 2; row >= 0; row--){
            //scan the row from the row 1 row below the top to the bottom one
            for(int col = 0; col < board.size();col++){
                Tile curTile = board.tile(col,row);
                Tile[] myColArr = colArr(col,board);
                if(curTile != null && shouldMoveUp(col,row,board,mergedBoard)){
                    flag = true;
                    int[] mySwitch = findSwitch(curTile,col,row,myColArr,mergedBoard);
                    boolean ifmerged = board.move(mySwitch[0], mySwitch[1],curTile);
                    score += ifmerged? curTile.value() * 2 : 0;
                    mergedBoard[mySwitch[0]][mySwitch[1]] = ifmerged;
                }
            }
        }
        return flag;
    }
    //return an array of Tiles on the specific column for this current tile
    //index indicates which row it's at
    public Tile[] colArr(int col,Board board){
        Tile[] colTile = new Tile[board.size()];
        for(int row = 0; row < board.size(); row++){
            colTile[row] = board.tile(col,row);
        }
        return colTile;
    }

    //check if current tile should move up
    //if square above is empty, return true
    //otherwise,check if we can merge
    public boolean shouldMoveUp(int col,int row,Board board,boolean[][] mergedBoard){
        int colAbove = col;
        int rowAbove = row + 1;
        Tile tileAbove = board.tile(colAbove,rowAbove);
        if(tileAbove == null){//tile above is empty
            return true;
        }else{
            //if tile above isn't merged and it has the same value as current tile,return true
            return !mergedBoard[colAbove][rowAbove] && tileValueCompare(tileAbove,board.tile(col,row));
        }
    }
    //find the exact col and row where current tile should place
    //this method is made for MOVING UP only
    //col and row is needed because curTile's row() and col() will only works for a situation where the board is tilted to the north
    public int[] findSwitch(Tile curTile,int col,int row,Tile[] myColArr,boolean[][] mergedBoard){
        //iterate through the tiles above current tile to find the best candidate
        int candidateRow = -1;
        for(int myRow = row + 1; myRow < myColArr.length; myRow++){
            if(myColArr[myRow] == null){
                candidateRow = myRow;
            }else{
                if(!mergedBoard[col][myRow] && tileValueCompare(curTile,myColArr[myRow])){
                    candidateRow = myRow;
                }
                break;
            }
        }
        return new int[]{col,candidateRow};
    }

    public boolean tileValueCompare(Tile t1,Tile t2){
        return t1.value() == t2.value();
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        for(int i = 0;i < b.size();i++){
            for(int j = 0;j < b.size();j++){
                if(b.tile(j,i) == null){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        for(int i = 0;i < b.size();i++){
            for(int j = 0;j < b.size();j++){
                Tile tileOnTheBoard = b.tile(j,i);
                if(tileOnTheBoard != null && tileOnTheBoard.value() ==MAX_PIECE){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        if(emptySpaceExists(b)){
            return true;
        }
        if(sameAdjacentTiles(b)){
            return true;
        }
        return false;
    }
    /**
     * return true if there are at least 2 adjacent tiles of the same number
     * otherwise,return false
     */
    public static boolean sameAdjacentTiles(Board b){
        for(int i = 0;i < b.size();i++){
            for(int j = 0;j < b.size();j++){
                if(ifSameAsRightOrBottom(b,i,j)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean ifSameAsRightOrBottom(Board b,int row,int col){
        Tile curTile = b.tile(col,row);//current tile to be compared with
        Tile[] compare = new Tile[2];
        if((col + 1) < b.size()){
            //store the tile on the right
            compare[0] = b.tile(col + 1,row);
        }
        if((row + 1) < b.size()){
            //store the tile below
            compare[1] = b.tile(col,row + 1);
        }
        for(Tile element : compare){
            if(element != null && curTile.value() == element.value()){
                return true;
            }
        }
        return false;
    }

    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
