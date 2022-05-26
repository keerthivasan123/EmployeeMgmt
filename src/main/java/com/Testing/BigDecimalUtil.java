package com.Testing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BigDecimalUtil {
    public static BigDecimal getSumationResult( List<BigDecimal> listOfBigDecimals ){
        if( listOfBigDecimals == null ) throw new NullPointerException("Invalid List");
        BigDecimal result = new BigDecimal("0");
        for( BigDecimal num : listOfBigDecimals ){
            result = result.add( num );
        }
        return result;
    }

    public static BigDecimal getSumationResult( BigDecimal num1, BigDecimal num2 ){
        if( num1 == null || num2 == null ) throw new NullPointerException("Invalid Input");
        return num1.add(num2);
    }

    public static BigDecimal getSubractionResult( List<BigDecimal> listOfBigDecimals ){
        if( listOfBigDecimals == null ) throw new NullPointerException("Invalid List");
        BigDecimal result = new BigDecimal("0");
        for( BigDecimal num : listOfBigDecimals ){
            result = result.subtract( num );
        }
        return result;
    }

    public static BigDecimal getSubractionResult( BigDecimal num1, BigDecimal num2 ){
        if( num1 == null || num2 == null ) throw new NullPointerException("Invalid Input");
        return num1.subtract(num2);
    }

    public static BigDecimal getMultplierResult( List<BigDecimal> listOfBigDecimals ){
        if( listOfBigDecimals == null ) throw new NullPointerException("Invalid List");
        BigDecimal result = new BigDecimal("1");
        for( BigDecimal num : listOfBigDecimals ){
            result = result.multiply( num );
        }
        return result;
    }

    public static BigDecimal getMultplierResult( BigDecimal num1, BigDecimal num2 ){
        if( num1 == null || num2 == null ) throw new NullPointerException("Invalid Input");
        return num1.multiply(num2);
    }

    public static void main(String[] args) {
        try {
            List<BigDecimal> listObBigDecimals = new ArrayList<>();
            listObBigDecimals.add(new BigDecimal("11.0"));
            listObBigDecimals.add(new BigDecimal("12.0"));
            listObBigDecimals.add(new BigDecimal("13.0"));
            System.out.println("Sum of List : " + BigDecimalUtil.getSumationResult( listObBigDecimals ));
            System.out.println("Difference of List : " + BigDecimalUtil.getSubractionResult( listObBigDecimals ));
            System.out.println("Multiple of List : " + BigDecimalUtil.getMultplierResult( listObBigDecimals ));

            System.out.println("Sum of Two Number : " + BigDecimalUtil.getSumationResult( new BigDecimal("10"), new BigDecimal("12") ));
            System.out.println("Difference of Two Number : " + BigDecimalUtil.getSubractionResult( new BigDecimal("15"), new BigDecimal("10") ));
            System.out.println("Multiple of Two Number : " + BigDecimalUtil.getMultplierResult( new BigDecimal("13"), new BigDecimal("12") ));
        } catch ( Exception e ){
            System.out.println(e.toString());
        }
    }
}
