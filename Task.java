/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multiplan_;

import java.io.Serializable;
import java.time.LocalTime;

/**
 *
 * @author malee
 */
class Task implements Serializable{
    private String name;
    private String dueDate;
    private boolean completed;
    private int hoursAllocated;
    private LocalTime startTime;
    
    public Task(String name, String dueDate) {
        this.name = name;
        this.dueDate = dueDate;
        this.completed = false;
        this.hoursAllocated = 0;
    }

    public String getName() {
        return name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getHoursAllocated() {
        return hoursAllocated;
    }

    public void setHoursAllocated(int hours) {
        this.hoursAllocated = hours;
    }
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
}