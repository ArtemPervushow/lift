package com.pervushov.test.enter;

import java.util.Scanner;

/**
 * Created by a.pervushov on 12.12.2017.
 */
public interface TechPanel {
    int enterFloorNumber(Scanner userScanner);
    int enterSpeed(Scanner userScanner, Integer height);
    int enterTiming(Scanner userScanner);
    int enterHeight(Scanner userScanner);
}
