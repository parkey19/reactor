package com.parkey.reactor;

import java.util.Arrays;

public class Exam1 {
    public static void main(String[] args) {

        Exam1 exam1 = new Exam1();
        String solution = exam1.solution(3, 2, new int[]{2, 1, 1, 0, 1});
        System.out.println(solution);

        String solution1 = exam1.solution(2, 0, new int[]{2});
        System.out.println(solution1);

    }

    // 3, 2
    //U=3,L=2,C=[2,1,1,0,1]
    // 11100 3
    // 10001 2

    // 10100
    // 11100 3
    // 2, 1, 1, 0, 1
    public String solution(int u, int l, int[] c) {

        int zero = 0;
        int one = 1;

        StringBuilder upSb = new StringBuilder();
        StringBuilder lowSb = new StringBuilder();

        for (int i = 0; i < c.length; i++) {

            int rowSum = c[i];

            if (rowSum == 2) {
                upSb.append(one);
                lowSb.append(one);
                u -= one;
                l -= one;
            } else if (rowSum == 1) {
                if (u > 0) {
                    upSb.append("1");
                    lowSb.append("0");
                    u--;
                } else {
                    upSb.append("0");
                    lowSb.append("1");
                    l--;
                }

            } else  {
                upSb.append(zero);
                lowSb.append(zero);
            }

        }


        // if and only if the sum of both rows is now 0 a solution was found otherwise return "IMPOSSIBLE"
        return u == 0 && l == 0 ? upSb.toString() + "," + lowSb.toString() : "IMPOSSIBLE";
    }
}
