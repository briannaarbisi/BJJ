package team5.bjj;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Hannah on 11/16/17.
 */

public class Strategy {
    private String name;
    private Date creationDate;
    private Date latestModificationDate;
    private ArrayList<Position> positions;
    private ArrayList< ArrayList<Move> > moves;

    public Strategy(String name, Date date) {
        this.name = name;
        this.creationDate = date;
        this.latestModificationDate = date;
        this.positions = new ArrayList<Position>();
        this.moves = new ArrayList< ArrayList<Move> >();
    }

    /* Getters and Setters */

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public Date getLatestModificationDate() {
        return this.latestModificationDate;
    }
    public void setLatestModificationDate(Date date) {
        this.latestModificationDate = date;
    }

    public ArrayList<Position> getPositions() {
        return this.positions;
    }
    public void addPosition(Position p) {
        this.positions.add(p);
        this.moves.add(new ArrayList<Move>());
    }
    public void removePosition(Position p) {
        int index = this.positions.indexOf(p);
        this.positions.remove(index);
        this.moves.remove(index);
    }

    public ArrayList<Move> getMoves(Position p) {
        // Get the index of the Position to use to get its corresponding list of moves
        int index = this.positions.indexOf(p);
        return this.moves.get(index);
    }
    public void addMove(Position p, Move m) {
        int index = this.positions.indexOf(p);
        this.moves.get(index).add(m);
    }
    public void removeMove(Position p, Move m) {
        int index = this.positions.indexOf(p);
        this.moves.get(index).remove(m);
    }
}
