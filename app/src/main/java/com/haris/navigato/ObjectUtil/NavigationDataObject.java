package com.haris.navigato.ObjectUtil;

import com.akexorcist.googledirection.model.Coordination;

import java.io.Serializable;

/**
 * Created by hp on 5/30/2018.
 */

public class NavigationDataObject extends Object implements Serializable {
    private int id;
    private Coordination coordination;
    private Coordination endCoordination;
    private StepDataObject stepDataObject;


    public NavigationDataObject(Coordination coordination, StepDataObject stepDataObject) {
        this.coordination = coordination;
        this.stepDataObject = stepDataObject;
    }

    public NavigationDataObject(Coordination coordination, Coordination endCoordination, StepDataObject stepDataObject) {
        this.coordination = coordination;
        this.endCoordination = endCoordination;
        this.stepDataObject = stepDataObject;
    }

    public NavigationDataObject(int id, Coordination coordination, Coordination endCoordination, StepDataObject stepDataObject) {
        this.id = id;
        this.coordination = coordination;
        this.endCoordination = endCoordination;
        this.stepDataObject = stepDataObject;
    }

    public Coordination getCoordination() {
        return coordination;
    }

    public void setCoordination(Coordination coordination) {
        this.coordination = coordination;
    }

    public StepDataObject getStepDataObject() {
        return stepDataObject;
    }

    public void setStepDataObject(StepDataObject stepDataObject) {
        this.stepDataObject = stepDataObject;
    }

    public Coordination getEndCoordination() {
        return endCoordination;
    }

    public void setEndCoordination(Coordination endCoordination) {
        this.endCoordination = endCoordination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
