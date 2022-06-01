
package com.epam.webproject.entity;
import java.util.Date;

public class BusyDate extends Entity {
    private int id;
    private Date arrivalTime;
    private Date releaseDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    // FIXME IN ALL Entities : equals, hashcode & tostring 
}
