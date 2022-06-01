package com.epam.webproject.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Order extends Entity {
    private int id;
    private Date reservationTime;
    private boolean isInPending;
    private boolean isApproved;
    private boolean isRejected;
    private BigDecimal price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Date reservationTime) {
        this.reservationTime = reservationTime;
    }

    public boolean getIsInPending() {
        return isInPending;
    }

    public void setInPending(boolean inPending) {
        isInPending = inPending;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean getIsRejected() {
        return isRejected;
    }

    public void setRejected(boolean rejected) {
        isRejected = rejected;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    // FIXME IN ALL Entities : equals, hashcode & tostring 
}
