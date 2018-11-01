/**
 * Created by hadoop on 3/1/18.
 */
package com.java;

public class Test{
    public enum StageStatus {
        ACTIVE,
        COMPLETE,
        FAILED,
        PENDING,
        SKIPPED;
    }
    public static void main(String[] args) {
        StageStatus  s = StageStatus.ACTIVE;
        System.out.println(s.toString());

    }
}

