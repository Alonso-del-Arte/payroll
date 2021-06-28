/*
 * Copyright (C) 2021 Alonso del Arte
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
 * Represents a period of local time, without regard for time zones.
 * @author Alonso del Arte
 */
public class DateTimeRange implements Comparable<DateTimeRange>, Serializable {
    
    private static final long serialVersionUID = 20200620L;
    
    private final LocalDateTime startTime;
    private final LocalDateTime finishTime;
    
    // TODO: Write Javadoc for these and other public functions
    public LocalDateTime getStart() {
        return this.startTime;
    }
    
    public LocalDateTime getEnd() {
        return this.finishTime;
    }
    
    public long getDuration(ChronoUnit unit) {
        return unit.between(this.startTime, this.finishTime);
    }
    
    /**
     * Determines whether another time range is properly contained in this time 
     * range. Depending on the purpose, this may or may not be a significant 
     * difference from the other containment functions.
     * @param other The time range to be tested for containment within this 
     * range.
     * @return True if and only if this time range begins before 
     * <code>other</code> begins and ends after <code>other</code> ends. A 
     * difference of one second is sufficient (e.g., this time range begins one 
     * second before <code>other</code> and ends one second after 
     * <code>other</code>.
     */
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

    /**
     * Sole constructor. Requires a start time and an end time.
     * @param start When the time range starts. Must not be after  
     * <code>end</code>. Should not be null. For example, 8:00 a.m. on January 
     * 4, 2021.
     * @param end When the time range ends. Must not be prior to 
     * <code>start</code>. Should not be null. For example, 7:59 a.m. on January 
     * 18, 2021.
     * @throws IllegalArgumentException If <code>end</code> is prior to 
     * <code>start</code>.
     * @throws NullPointerException If either <code>start</code> or 
     * <code>end</code> is null.
     */
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
