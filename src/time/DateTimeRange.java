/*
 * Copyright (C) 2020 Alonso del Arte
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package time;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Alonso del Arte
 */
public class DateTimeRange implements Comparable<DateTimeRange>, Serializable {
    
    private static final long serialVersionUID = 20200620L;
    
    private final LocalDateTime startTime;
    private final LocalDateTime finishTime;
    
    public LocalDateTime getStart() {
        return this.startTime;
    }
    
    public LocalDateTime getEnd() {
        return this.finishTime;
    }
    
    public long getDuration(ChronoUnit unit) {
        return unit.between(this.startTime, this.finishTime);
    }
    
    private boolean containsProperly(DateTimeRange other) {
        return (this.startTime.isBefore(other.startTime) 
                && this.finishTime.isAfter(other.finishTime));
    }

    public boolean contains(LocalDateTime time) {
        if (time.equals(this.startTime) || time.equals(this.finishTime)) {
            return true;
        }
        return (time.isAfter(this.startTime) && time.isBefore(this.finishTime));
    }
    
    public boolean contains(DateTimeRange other) {
        if (this.equals(other)) {
            return true;
        }
        if (this.startTime.equals(other.startTime)) {
            return this.finishTime.isAfter(other.finishTime);
        }
        if (this.finishTime.equals(other.finishTime)) {
            return this.startTime.isBefore(other.startTime);
        }
        return this.containsProperly(other);
    }
    
    public boolean overlaps(DateTimeRange other) {
        return (this.contains(other.startTime) 
                || this.contains(other.finishTime));
    }

    public DateTimeRange merge(DateTimeRange other) {
        if (!this.overlaps(other)) {
            String excMsg = "Can't merge non-overlapping ranges " 
                    + this.toString() + " and " + other.toString();
            throw new IllegalArgumentException(excMsg);
        }
        LocalDateTime mergeStart, mergeEnd;
        if (this.startTime.isBefore(other.startTime)) {
            mergeStart = this.startTime;
        } else {
            mergeStart = other.startTime;
        }
        if (this.finishTime.isAfter(other.finishTime)) {
            mergeEnd = this.finishTime;
        } else {
            mergeEnd = other.finishTime;
        }
        return new DateTimeRange(mergeStart, mergeEnd);
    }

    public DateTimeRange[] split(DateTimeRange intervening) {
        DateTimeRange preCut = new DateTimeRange(this.startTime, 
                intervening.startTime);
        DateTimeRange postCut = new DateTimeRange(intervening.finishTime, 
                this.finishTime);
        DateTimeRange[] array = {preCut, postCut};
        return array;
    }

    @Override
    public String toString() {
        return this.startTime.toString() + " to " + this.finishTime.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        final DateTimeRange other = (DateTimeRange) obj;
        if (!this.startTime.equals(other.startTime)) {
            return false;
        }
        return this.finishTime.equals(other.finishTime);
    }

    @Override
    public int hashCode() {
        return 239 * this.startTime.hashCode() + this.finishTime.hashCode();
    }
    
    @Override
    public int compareTo(DateTimeRange other) {
        int comparison = this.startTime.compareTo(other.startTime);
        if (comparison == 0) {
            return this.finishTime.compareTo(other.finishTime);
        } else {
            return comparison;
        }
    }

    public DateTimeRange(LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start)) {
            String excMsg = "Range can't start (" + start.toString() 
                    + ") before it ends (" + end.toString() + ")";
            throw new IllegalArgumentException(excMsg);
        }
        this.startTime = start;
        this.finishTime = end;
    }
    
}
