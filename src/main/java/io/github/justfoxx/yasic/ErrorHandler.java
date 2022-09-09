package io.github.justfoxx.yasic;

public class ErrorHandler {
    public static void error(int line,String message) {
        Main.logger.error("Error on line " + line + ": " + message);
        System.exit(64);
    }
}
