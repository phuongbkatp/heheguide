package com.haris.navigato.ObjectUtil;

public class UniversalVariable {

    public boolean condition;
    public String result;

    public UniversalVariable() {
    }

    public UniversalVariable(boolean condition) {
        this.condition = condition;
    }

    public UniversalVariable(String result) {
        this.result = result;
    }

    public UniversalVariable(boolean condition, String result) {
        this.condition = condition;
        this.result = result;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
