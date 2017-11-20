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

    public Strategy(String name, Date date) {
        this.name = name;
        this.creationDate = date;
        this.latestModificationDate = date;
        this.positions = new ArrayList<Position>();
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
    }
    public void removePosition(Position p) {
        this.positions.remove(p);
    }

}
