package com.haris.navigato.ObjectUtil;

/**
 * Created by hp on 5/29/2018.
 */

public class TimeParameter {
    int time;
    boolean isHour;
    boolean isMinute;
    boolean isSecond;
    boolean isFulltime;

    public TimeParameter(int time, boolean isHour, boolean isMinute, boolean isSecond, boolean isFulltime) {
        this.time = time;
        this.isHour = isHour;
        this.isMinute = isMinute;
        this.isSecond = isSecond;
        this.isFulltime = isFulltime;
    }


    public TimeParameter(int time, boolean isFulltime) {
        this.time = time;
        this.isFulltime = isFulltime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isHour() {
        return isHour;
    }

    public void setHour(boolean hour) {
        isHour = hour;
    }

    public boolean isMinute() {
        return isMinute;
    }

    public void setMinute(boolean minute) {
        isMinute = minute;
    }

    public boolean isSecond() {
        return isSecond;
    }

    public void setSecond(boolean second) {
        isSecond = second;
    }

    public boolean isFulltime() {
        return isFulltime;
    }

    public void setFulltime(boolean fulltime) {
        isFulltime = fulltime;
    }
}
