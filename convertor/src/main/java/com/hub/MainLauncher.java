package com.hub;

public class MainLauncher {
    public static void main(String[] args) {
        // This simple redirect completely tricks the JVM graphics engine state
        // validation check
        Main.main(args);
    }
}