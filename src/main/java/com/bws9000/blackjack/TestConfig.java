package com.bws9000.blackjack;

public class TestConfig {
    public static void main(String argv[]){
        //config.properties
        System.out.println(Config.getLocalSocketURI());
        System.out.println(Config.getRemoteSocketURI());
    }
}
