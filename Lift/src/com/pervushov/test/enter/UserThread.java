package com.pervushov.test.enter;

import com.pervushov.test.lift.LiftImpl;
import com.pervushov.test.lift.LiftTask;

import java.util.Scanner;

/**
 * Created by a.pervushov on 12.12.2017.
 */
public class UserThread extends Thread {
    private LiftImpl lift;

    public UserThread(LiftImpl lift) {
        this.lift = lift;
    }

    @Override
    public void run() {
        while (true){
            Scanner userScanner = new Scanner(System.in);
            System.out.println("Are you inside? (y- inside, q- exit, anySymbol- outside)");
            String command = userScanner.next();

            LiftTask liftTask = null;
            if("q".equals(command)){
                System.out.println("Bye!");
                return;
            } else if("y".equals(command)){
                liftTask = new LiftTask(lift.getUserPanel().enterDestNumber(userScanner, lift.getNumberOfFloors()), true);
            } else {
                liftTask = new LiftTask(lift.getUserPanel().enterDestNumber(userScanner, lift.getNumberOfFloors()), false);
            }

            lift.addLiftTask(liftTask);
        }
    }

}
