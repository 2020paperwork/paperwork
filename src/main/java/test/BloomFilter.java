package test;

import static test.Hash.*;

public class BloomFilter {
    private int m;
    // 布隆过滤器的长度
    public BloomFilter(int m)
    {
        this.m = m;
    }

    public int[] Enc(String[] str)
    {
        int[] ret = new int[m];
        for (int i = 0; i < str.length; i++)
        {
            ret[APHash(str[i]) % m > 0 ? APHash(str[i]) % m : -APHash(str[i]) % m] = 1;
            ret[BKDRHash(str[i]) % m > 0 ? BKDRHash(str[i]) % m : -BKDRHash(str[i]) % m] = 1;
            ret[SDBMHash(str[i]) % m > 0 ? SDBMHash(str[i]) % m : -SDBMHash(str[i]) % m] = 1;
        }

        return ret;
    }

    public int[] KeyGen(String[] str)
    {
        int[] ret = Enc(str);
        for (int i = 0; i < m; i++)
        {
            if (ret[i] ==0)
            {
                ret[i] = -1;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        String[] index = {"hello", "hi"};
        String[] key = {"hello"};
        BloomFilter bf = new BloomFilter(10);
        int[] ret = bf.Enc(index);
        int[] ret2 = bf.KeyGen(key);

        for (int i = 0; i < ret.length; i++)
        {
            System.out.print(ret[i] + "\t");
        }
        System.out.println();
        for (int i = 0; i < ret2.length; i++)
        {
            System.out.print(ret2[i] + "\t");
        }
    }
}
