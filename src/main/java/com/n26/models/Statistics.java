package com.n26.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class Statistics implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sum;
    private String avg;
    private String max;
    private String min;
    private long count;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }



    @Override
    public String toString() {
        return "Statistics{" +
                "sum=" + sum +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                ", avg=" + avg +
                '}';
    }
}
