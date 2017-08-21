/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.accenture.performanceevaluation.model;

import java.io.Serializable;

/**
 *
 * @author altius
 */
public class ShiftTime implements Serializable {

    private Integer shiftTimeId;
    private String shiftTime;

    public Integer getShiftTimeId() {
        return shiftTimeId;
    }

    public void setShiftTimeId(int shiftTimeId) {
        this.shiftTimeId = shiftTimeId;
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime;
    }

    @Override
    public String toString() {
        return "ShiftTime{" + "shiftTimeId=" + shiftTimeId + ", shiftTime=" + shiftTime + '}';
    }

}
