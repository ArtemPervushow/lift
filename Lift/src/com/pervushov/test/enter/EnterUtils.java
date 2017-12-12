package com.pervushov.test.enter;

import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by a.pervushov on 12.12.2017.
 */
public class EnterUtils {
    public static Integer enterPositiveInt(Scanner scanner){
        String userValue = scanner.next();

        Integer result = 0;

        try {
            result = Integer.parseInt(userValue);
        } catch (NumberFormatException ex){
            return (Integer) handleBadUserEnter(scanner, EnterUtils::enterPositiveInt);
        }

        if(result > 0){
            return result;
        } else {
            return (Integer) handleBadUserEnter(scanner, EnterUtils::enterPositiveInt);
        }
    }

    public static Object handleBadUserEnter(Scanner scanner, Function<Scanner, Object> function){
        System.out.println("You enter wrong number! Do you want try again? (q- no, anySymbol- yes)");
        String userValue = scanner.next();

        if("q".equals(userValue)){
            return 0;
        } else {
            System.out.println("Ok, let's try again!");
            return function.apply(scanner);
        }
    }

    public static Object handleBadUserEnterWithBiArgs(Scanner scanner, Integer height, BiFunction<Scanner, Integer, Object> function){
        System.out.println("You enter wrong number! Do you want try again? (q- no, anySymbol- yes)");
        String userValue = scanner.next();

        if("q".equals(userValue)){
            return 0;
        } else {
            System.out.println("Ok, let's try again!");
            return function.apply(scanner, height);
        }
    }
}
