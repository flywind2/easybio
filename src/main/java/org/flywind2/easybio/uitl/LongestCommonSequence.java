/**
 * 
 */
package org.flywind2.easybio.uitl;

import htsjdk.samtools.util.SequenceUtil;

/**
 * @author flywind2.su@gmail.com
 * @date 2018年3月21日
 * @version 1.0
 */
public class LongestCommonSequence {

    public static int getLongestCommonSubsequence(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }

    public static String getLongestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        if (strs.length == 1)
            return strs[0];

        int minLen = strs.length + 1;

        for (String str : strs) {
            if (minLen > str.length()) {
                minLen = str.length();
            }
        }

        for (int i = 0; i < minLen; i++) {
            for (int j = 0; j < strs.length - 1; j++) {
                String s1 = strs[j];
                String s2 = strs[j + 1];
                if (s1.charAt(i) != s2.charAt(i)) {
                    return s1.substring(0, i);
                }
            }
        }

        return strs[0].substring(0, minLen);
    }
    
    public static boolean isOneEditDistance(String s, String t) {
        if(s==null || t==null)
            return false;
     
        int m = s.length();
        int n = t.length();
     
        if(Math.abs(m-n)>3){
            return false;
        }
     
        int i=0; 
        int j=0; 
        int count=0;
     
        while(i<m&&j<n){
            if(s.charAt(i)==t.charAt(j)){
                i++;
                j++;
            }else{
                count++;
                if(count>3)
                    return false;
     
                if(m>n){
                    i++;
                }else if(m<n){
                    j++;
                }else{
                    System.out.println(i+"\t"+j);
                    i++;
                    j++;
                }
            }
        }
     
        if(i<m||j<n){
            count++;
        }
        
        //System.out.println(i+"\t"+j);
     
        if(count<=3)
            return true;
     
        return false;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String a = "TACCTGGCCACTCCCAGCTGCGACATGGTTCTGCAGATCACTGCTAGGTCCCCCCGAGAGCCTCTGGGCTAGCAGAGACACTTGCTGTCTGGAGTCCACCAAAGCACAGAAGATAAGTGTTGGCAATAATAAAATTGGAAAACAAACATA";
        String b = "TGTCCCACGTCTCTTTGCTTTCCAACTTTCTAATTGCCAACACTTATCTTCTGTGCTTTGGTGGACTCCAGACAGCAAGTGTCCCTGCTAGCCCACAGGCTCTCGGGGGGAACTAGCAGGGCACTGCAGAACCATGTCGCAGCTGAGAGTG";
        //String c = SequenceUtil.reverseComplement(b);
        String c = "CACTCTCAGCTGCGACATGGTTCTGCAGTGCCCTGCTAGTTCCCCCCGAGAGCCTGTGGGCTAGCAGGGACACTTGCTGTCTGGAGTCCACCAAAGCACAGAAGATAAGTGTTGGCAATTAGAAAGTTGGAAAGCAAAGAGACGTGGGACA";
        System.out.println(getLongestCommonSubsequence(a, b));
        
        System.out.println(getLongestCommonPrefix(new String[]{a,b}));
        
        System.out.println(isOneEditDistance(a, b));
        System.out.println(isOneEditDistance(a, c));

    }

}
