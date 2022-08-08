package com.teenkung.itemserialnumber;

public enum RegisterLevel {
    unLockable, nonUnlockable;


    public String getString() {
        if (this == unLockable) {
            return "Unlockable";
        } else if (this == nonUnlockable) {
            return "NonUnlockable";
        } else {
            return "UNKNOWN";
        }
    }
}
