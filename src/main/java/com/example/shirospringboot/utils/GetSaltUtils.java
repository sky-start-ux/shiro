package com.example.shirospringboot.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

public class GetSaltUtils {
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    public static String getSalt(){
        return randomNumberGenerator.nextBytes(4).toHex();
    }
}
