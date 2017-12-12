package com.pervushov.test.lift;

/**
 * Created by a.pervushov on 12.12.2017.
 */
public class LiftTask implements Comparable<LiftTask>{
    private int destFloor = 0;

    public int getDestFloor() {
        return destFloor;
    }

    private boolean inside = false;

    public LiftTask(int destFloor, boolean inside) {
        this.destFloor = destFloor;
        this.inside = inside;
    }

    @Override
    public int compareTo(LiftTask o) {
        if(inside == o.inside)
            return 0;
        if(inside == true && o.inside == false)
            return 1;
        else
            return -1;
    }
}
