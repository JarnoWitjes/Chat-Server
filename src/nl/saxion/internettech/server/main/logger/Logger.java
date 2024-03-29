package nl.saxion.internettech.server.main.logger;

import nl.saxion.internettech.server.main.logger.data.ConsoleColors;

public class Logger {

    public static final int LOG_LEVEL_ERROR = 0;
    public static final int LOG_LEVEL_WARNING = 1;
    public static final int LOG_LEVEL_INFO = 2;
    public static final int LOG_LEVEL_DEBUG = 3;

    private int logLevel;

    private static Logger instance;

    private Logger() {
        logLevel = LOG_LEVEL_INFO;
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }

    public void logError(String message) {
        if (logLevel >= LOG_LEVEL_ERROR) {
            System.out.println(ConsoleColors.RED_BOLD + "[ERROR] "
                    + ConsoleColors.RED + message
                    + ConsoleColors.RESET
            );
        }
    }

    public void logWarning(String message) {
        if (logLevel >= LOG_LEVEL_WARNING) {
            System.out.println(ConsoleColors.YELLOW_BOLD + "[WARNING] "
                    + ConsoleColors.YELLOW + message
                    + ConsoleColors.RESET
            );
        }
    }

    public void logInfo(String message) {
        if (logLevel >= LOG_LEVEL_INFO) {
            System.out.println(ConsoleColors.BLUE_BOLD + "[INFO] "
                    + ConsoleColors.BLUE + message
                    + ConsoleColors.RESET
            );
        }
    }

    public void logDebug(String message) {
        if (logLevel >= LOG_LEVEL_DEBUG) {
            System.out.println(ConsoleColors.PURPLE_BOLD + "[DEBUG] "
            + ConsoleColors.PURPLE + message
            + ConsoleColors.RESET);
        }
    }

    public void setLogLevel(int level) {
        if (level >= 0 && level <= 3) {
            logLevel = level;
        }
    }
}