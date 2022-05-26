package com.Testing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BigDecimalUtil {
    public static BigDecimal getSumationResult( List<BigDecimal> listOfBigDecimals ){
        Iterator itr = listOfBigDecimals.iterator();
        BigDecimal result = new BigDecimal("0");
        while( itr.hasNext() ){
            result = result.add( (BigDecimal) itr.next() );
        }
        return result;
    }

    public static BigDecimal getSubractionResult( List<BigDecimal> listOfBigDecimals ){
        Iterator itr = listOfBigDecimals.iterator();
        BigDecimal result = (BigDecimal) itr.next();
        while( itr.hasNext() ){
            result = result.subtract( (BigDecimal) itr.next() );
        }
        return result;
    }

    public static BigDecimal getMultplierResult( List<BigDecimal> listOfBigDecimals ){
        Iterator itr = listOfBigDecimals.iterator();
        BigDecimal result = (BigDecimal) itr.next();
        while( itr.hasNext() ){
            result = result.multiply( (BigDecimal) itr.next() );
        }
        return result;
    }

    public static void main(String[] args) {
        List<BigDecimal> listObBigDecimals = new ArrayList<>();
        listObBigDecimals.add(new BigDecimal("11.0"));
        listObBigDecimals.add(new BigDecimal("12.0"));
        listObBigDecimals.add(new BigDecimal("13.0"));
        System.out.println("Sum of List : " + BigDecimalUtil.getSumationResult( listObBigDecimals ));
        System.out.println("Difference of List : " + BigDecimalUtil.getSubractionResult( listObBigDecimals ));
        System.out.println("Multiple of List : " + BigDecimalUtil.getMultplierResult( listObBigDecimals ));

    }
}
