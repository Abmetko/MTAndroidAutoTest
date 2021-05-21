package core.utils;

import java.io.IOException;


public class ADBCommandExecutor {

    public static void runShellCommand(String command){
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (process != null) {
                process.waitFor();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        //unlock device screen
        runShellCommand("adb shell input keyevent 26");
    }
}