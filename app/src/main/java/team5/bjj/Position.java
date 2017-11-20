package team5.bjj;

import java.util.ArrayList;

/**
 * Created by Hannah on 11/16/17.
 */

public class Position {
    private ArrayList<Move> entireListOfMoves;  // Contains the set of moves categorized with this position
    private String name;
    //private ArrayList< ArrayList<Move> > strategyMoves;
    // Need a way to be able to show different lists of moves for a specific position in different strategies

    public Position(String name) {
        this.name = name;
        this.entireListOfMoves = new ArrayList<Move>();
    }

    public Position(String name, ArrayList<Move> moveList) {
        this.name = name;
        this.entireListOfMoves = moveList;

    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Move> getEntireListOfMoves() {
        return this.entireListOfMoves;
    }
    public void addToListOfMoves(Move m) {
        this.entireListOfMoves.add(m);
    }

}
