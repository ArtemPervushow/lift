package com.pervushov.test.lift;

import com.pervushov.test.enter.EnterUtils;
import com.pervushov.test.enter.TechPanel;
import com.pervushov.test.enter.UserPanel;
import com.pervushov.test.enter.UserThread;

import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by a.pervushov on 12.12.2017.
 */
public class LiftImpl implements Lift{

    private PriorityBlockingQueue<LiftTask> liftDestinationQueue = new PriorityBlockingQueue<LiftTask>();
    private UserPanel userPanel = new UserPanelImpl();
    private TechPanel techPanel = new TechPanelImpl();

    public UserPanel getUserPanel() {
        return userPanel;
    }

    private int numberOfFloors = 0;

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    private int height = 0;
    private int speed = 0;
    private int timing = 0;
    private float currentPosition = 0;

    private static final int MIN_FLOORS = 5;
    private static final int MAX_FLOORS = 20;

    public void startWork(){
        callibrate();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Start? (q- no, anySymbol- yes)");
        if (!"q".equals(scanner.next())) {
            run();
        }
    }

    @Override
    public void run(){
        UserThread userThread = new UserThread(this);
        userThread.start();

        while (true){
            LiftTask task = null;
            try {
                task = liftDestinationQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            moving(task.getDestFloor());
        }
    }

    @Override
    public void callibrate() {
        Scanner scanner = new Scanner(System.in);

        numberOfFloors = techPanel.enterFloorNumber(scanner);

        height = techPanel.enterHeight(scanner);

        speed = techPanel.enterSpeed(scanner, height);

        timing = techPanel.enterTiming(scanner);
    }

    @Override
    public void addLiftTask(LiftTask liftTask){
        liftDestinationQueue.add(liftTask);
    }

    private void moving(int destFloor){
        while (true) {
            if(currentPosition % height == 0){
                System.out.println("Near the floor " + currentPosition / height);
            }
            if (destFloor * height == currentPosition) {
                openClose();
                return;
            } else {
                move(destFloor);
            }
        }
    }

    private void openClose(){
        System.out.println("Doors open");
        try {
            Thread.sleep(timing*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Doors closing");
    }

    private void move(int destination){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        float direction = 0;
        if(currentPosition < destination * height)
            direction = speed;
        else
            direction = (-1) * speed;

        currentPosition+=direction;
    }

    class UserPanelImpl implements UserPanel{
        @Override
        public  int enterDestNumber(Scanner userScanner, Integer maxFloors){
            System.out.println("Enter number of dest floor");
            int floorNumber = EnterUtils.enterPositiveInt(userScanner);

            if(floorNumber < 1 || floorNumber > maxFloors){
                System.out.println("Number of floors must be between " + 1 + " and " + maxFloors);
                floorNumber = (Integer) EnterUtils.handleBadUserEnter(userScanner, EnterUtils::enterPositiveInt);
            }

            return floorNumber;
        }
    }

    class TechPanelImpl implements TechPanel{
        @Override
        public int enterHeight(Scanner userScanner){
            System.out.println("Enter heights");
            int floorNumber = EnterUtils.enterPositiveInt(userScanner);

            return floorNumber;
        }

        @Override
        public int enterTiming(Scanner userScanner){
            System.out.println("Enter doors timing");
            int timing = EnterUtils.enterPositiveInt(userScanner);

            return timing;
        }

        @Override
        public  int enterFloorNumber(Scanner userScanner){
            System.out.println("Enter number of floors");
            int floorNumber = EnterUtils.enterPositiveInt(userScanner);

            if(floorNumber < LiftImpl.MIN_FLOORS || floorNumber > LiftImpl.MAX_FLOORS){
                System.out.println("Number of floors must be between " + LiftImpl.MIN_FLOORS + " and " + LiftImpl.MAX_FLOORS);
                floorNumber = (Integer) EnterUtils.handleBadUserEnter(userScanner, this::enterFloorNumber);
            }

            return floorNumber;
        }

        @Override
        public  int enterSpeed(Scanner userScanner, Integer height){
            System.out.println("Enter lift speed (m/sec)");
            int speed = EnterUtils.enterPositiveInt(userScanner);

            if(speed > height || height % speed != 0){
                System.out.println("Speed must be propotional and less than height!");
                speed = (Integer) EnterUtils.handleBadUserEnterWithBiArgs(userScanner, height, this::enterSpeed);
            }

            return speed;
        }
    }
}
