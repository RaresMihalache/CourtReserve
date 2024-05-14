package com.example.demo.utils;

import com.lambdaworks.crypto.SCryptUtil;

public class Encrypting {
    public static final int N = 16384; //cpu cost param
    public static final int r = 8; //memory cost param
    public static final int p = 1; //parallelization param

    public static String encrypt(String password) {
        return SCryptUtil.scrypt(password, N, r, p);
    }

    public static Boolean check(String password, String stored) {
        try {
            return SCryptUtil.check(password, stored);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
