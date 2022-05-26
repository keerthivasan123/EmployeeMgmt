package com.Testing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BigDecimalUtil {
    public static final BigDecimal ZERO = BigDecimal.ZERO;
    public static final BigDecimal ONE = BigDecimal.ONE;

    public static BigDecimal sum( List<BigDecimal> listOfBigDecimals ){
        if( listOfBigDecimals == null ) throw new NullPointerException("Invalid List");
        BigDecimal result = BigDecimalUtil.ZERO;
        for( BigDecimal num : listOfBigDecimals ){
            result = result.add( num );
        }
        return result;
    }

    public static BigDecimal sum( BigDecimal num1, BigDecimal num2 ){
        if( num1 == null || num2 == null ) throw new NullPointerException("Invalid Input");
        return num1.add(num2);
    }

    public static BigDecimal subtract( List<BigDecimal> listOfBigDecimals ){
        if( listOfBigDecimals == null ) throw new NullPointerException("Invalid List");
        BigDecimal result = BigDecimal.ZERO;
        for( BigDecimal num : listOfBigDecimals ){
            result = result.subtract( num );
        }
        return result;
    }

    public static BigDecimal subtract( BigDecimal num1, BigDecimal num2 ){
        if( num1 == null || num2 == null ) throw new NullPointerException("Invalid Input");
        return num1.subtract(num2);
    }

    public static BigDecimal multiply( List<BigDecimal> listOfBigDecimals ){
        if( listOfBigDecimals == null ) throw new NullPointerException("Invalid List");
        BigDecimal result = BigDecimal.ONE;
        for( BigDecimal num : listOfBigDecimals ){
            result = result.multiply( num );
        }
        return result;
    }

    public static BigDecimal multiply( BigDecimal num1, BigDecimal num2 ){
        if( num1 == null || num2 == null ) throw new NullPointerException("Invalid Input");
        return num1.multiply(num2);
    }

    public static BigDecimal convertToNegative( BigDecimal num ){
        return num.negate();
    }

    public static void main(String[] args) {
        try {
            List<BigDecimal> listObBigDecimals = new ArrayList<>();
            listObBigDecimals.add(new BigDecimal("11.0"));
            listObBigDecimals.add(new BigDecimal("12.0"));
            listObBigDecimals.add(new BigDecimal("13.0"));
            System.out.println("Sum of List : " + BigDecimalUtil.sum( listObBigDecimals ));
            System.out.println("Difference of List : " + BigDecimalUtil.subtract( listObBigDecimals ));
            System.out.println("Multiple of List : " + BigDecimalUtil.multiply( listObBigDecimals ));

            System.out.println("Sum of Two Number : " + BigDecimalUtil.sum( new BigDecimal("10"), new BigDecimal("12") ));
            System.out.println("Difference of Two Number : " + BigDecimalUtil.subtract( new BigDecimal("15"), new BigDecimal("10") ));
            System.out.println("Multiple of Two Number : " + BigDecimalUtil.multiply( new BigDecimal("13"), new BigDecimal("12") ));

            System.out.println("Negative : " + BigDecimalUtil.convertToNegative(new BigDecimal("12")));
        } catch ( Exception e ){
            System.out.println(e.toString());
        }
    }
}
