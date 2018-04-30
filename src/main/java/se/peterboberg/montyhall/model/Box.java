package se.peterboberg.montyhall.model;

public class Box {

    private final BoxNumber boxNumber;
    private boolean moneyInside = false;

    public Box(BoxNumber boxNumber) {
        this.boxNumber = boxNumber;
    }

    public BoxNumber getBoxNumber() {
        return boxNumber;
    }

    public boolean isMoneyInside() {
        return moneyInside;
    }

    public void setMoneyInside(boolean moneyInside) {
        this.moneyInside = moneyInside;
    }
}
