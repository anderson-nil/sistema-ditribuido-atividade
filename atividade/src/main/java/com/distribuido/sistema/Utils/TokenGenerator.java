package com.distribuido.sistema.Utils;

public class TokenGenerator {

    public static int generate() {
        int token = 0;
        for (int i = 5; i >= 0; i--) {
            int randomNumber = getRandomNumber();
            token += (randomNumber * Math.pow(10, i));
        }
        token *= 10;
        
        while (token % 2 != 0 || token % 3 != 0 || token % 5 != 0) {
            token++;
        }

        return token;
    }

    public static boolean isTokenValidate(int token) {
        return token % 2 == 0 && token % 3 == 0 && token % 5 == 0;
    }

    private static int getRandomNumber() {
        return (int) ((Math.random() * 9) + 1);
    }

}
