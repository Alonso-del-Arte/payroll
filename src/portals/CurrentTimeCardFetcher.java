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
package portals;

import entities.Employee;
import payroll.TimeCard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import time.DateTimeRange;

/**
 * Fetches the current time card for a given employee from the employee records 
 * data directory. If the employee doesn't have a current time card, a new, 
 * blank time card is created. Pay periods are assumed to last one week each, 
 * starting on a Monday at 12:00 a.m. and ending on a Sunday at 11:59 p.m.
 * @author Alonso del Arte
 */
public class CurrentTimeCardFetcher {

    private final Employee cardOwner;
    private TimeCard fetchedCard;

    private File cardFile;

    private void findCard() throws ClassNotFoundException, IOException {
        if (!DataDirectoryInitializer.hasBeenSet()) {
            DataDirectoryInitializer.setDir();
        }
        File dir = DataDirectoryInitializer.getDir();
        TimeCardFilter filter = new TimeCardFilter();
        File[] matches = dir.listFiles(filter);
        boolean cardNotYetFound = true;
        TimeCard currCard;
        File currFile;
        int counter = 0;
        while (cardNotYetFound && counter < matches.length) {
            currFile = matches[counter];
            FileInputStream fileStream = new FileInputStream(currFile);
            ObjectInputStream objStream = new ObjectInputStream(fileStream);
            currCard = (TimeCard) objStream.readObject();
            if (currCard.getEmployee().equals(this.cardOwner) && currCard.isCurrent()) {
                this.fetchedCard = currCard;
                this.cardFile = currFile;
                cardNotYetFound = false;
            }
            counter++;
        }
    }

    private static LocalDate findNearMonday() {
        LocalDate today = LocalDate.now();
        DayOfWeek adjustToMonday = today.getDayOfWeek();
        return today.minusDays(adjustToMonday.ordinal());
    }

    private void writeCard() throws IOException {
        FileOutputStream fileStream = new FileOutputStream(this.cardFile);
        try (ObjectOutputStream objStream
                = new ObjectOutputStream(fileStream)) {
            objStream.writeObject(this.fetchedCard);
        }
    }

    private void addNewCard()
            throws IOException {
        if (!DataDirectoryInitializer.hasBeenSet()) {
            DataDirectoryInitializer.setDir();
        }
        String pathStr = DataDirectoryInitializer.getDir().getPath()
                + File.separatorChar + "PersProgcard"
                + this.fetchedCard.hashCode() + ".dat";
        File file = new File(pathStr);
        FileOutputStream fileStream = new FileOutputStream(file);
        try (ObjectOutputStream objStream
                = new ObjectOutputStream(fileStream)) {
            objStream.writeObject(this.fetchedCard);
        }
        this.cardFile = file;
    }

    private void writeNewCard() throws IOException {
        LocalDate monday = findNearMonday();
        LocalDateTime start = LocalDateTime.of(monday, LocalTime.MIDNIGHT);
        LocalDateTime end = start.plusDays(7).minusMinutes(1);
        DateTimeRange period = new DateTimeRange(start, end);
        this.fetchedCard = new TimeCard(this.cardOwner, period);
        this.addNewCard();
    }

    // TODO: Figure out more elegant demo algorithm
    public static TimeCard writeNewDemoCard(TimeCard card) throws IOException {
        LocalDate monday = findNearMonday();
        LocalDateTime start = LocalDateTime.of(monday, LocalTime.MIDNIGHT);
        LocalDateTime end = start.plusDays(7).minusMinutes(1);
        DateTimeRange period = new DateTimeRange(start, end);
        TimeCard demoCard = new TimeCard(card.getEmployee(), period);
        LocalTime workDayBegin = LocalTime.of(8, 0);
        LocalTime lunchBegin = LocalTime.of(12, 0);
        LocalTime lunchEnd = LocalTime.of(13, 0);
        LocalTime workDayEnd = LocalTime.of(17, 0);
        DateTimeRange block;
        for (LocalDate day = monday;
                LocalDateTime.of(day, lunchBegin).isBefore(LocalDateTime.now());
                day = day.plusDays(1)) {
            block = new DateTimeRange(LocalDateTime.of(day, workDayBegin),
                    LocalDateTime.of(day, lunchBegin));
            demoCard.addTimeBlock(block);
        }
        for (LocalDate day = monday;
                LocalDateTime.of(day, workDayEnd).isBefore(LocalDateTime.now());
                day = day.plusDays(1)) {
            block = new DateTimeRange(LocalDateTime.of(day, lunchEnd),
                    LocalDateTime.of(day, workDayEnd));
            demoCard.addTimeBlock(block);
        }
        return demoCard;
    }

    public TimeCard retrieveCard() {
        return this.fetchedCard;
    }

    public void putCardBack() {
        // TODO: Write tests for appropriate state of paramater-less put-back
    }

    public void putCardBack(TimeCard card) throws IOException {
        this.fetchedCard = card;
        this.writeCard();
    }

    public Employee getEmployee() {
        return this.cardOwner;
    }

    public CurrentTimeCardFetcher(Employee employee)
            throws ClassNotFoundException, IOException {
        this.cardOwner = employee;
        this.fetchedCard = null;
        this.findCard();
        if (this.fetchedCard == null) {
            this.writeNewCard();
        }
    }

}
