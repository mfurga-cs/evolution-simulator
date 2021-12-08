package model.utils;

public class Logger {

    private static final int ERROR = 0;
    private static final int WARNING = 1;
    private static final int DEBUG = 2;
    private static final int LOG = 3;

    private static final int verbosity = 2;

    public static void debug(String message) {
        if (Logger.verbosity < DEBUG) {
            return;
        }
        System.out.println("DEBUG: " + message);
    }

}
