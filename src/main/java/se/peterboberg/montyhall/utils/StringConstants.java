package se.peterboberg.montyhall.utils;

public class StringConstants {

    public final static String STOP = "Stop";
    public final static String RUN = "Run";
    public final static String INVALID_INTEGER_NUMBER = "Invalid integer number";
    public final static String EMPTY_STRING = "";
    public static final String ZERO = "0";
    public static final String CANT_DECIDE = "Can't decide!";
    public static final String KEEP_FIRST_SELECTION = "Keep you first box!";
    public static final String CHANGE_YOUR_MIND = "Take the other box!";

    public static String textForNumberOfSimulations(String numberOfSimulations) {
        return "Running " + numberOfSimulations + " simulations...";
    }

    public static String textForBestChioce(String bestChoise) {
        return "Best choice: " + bestChoise;
    }

    public static String textForTimesTried(String simulations) {
        return "Number of times tried: " + simulations;
    }

    public static class Paths {

        public static final String MAIN_VIEW_PATH = "/fxml/main-view.fxml";
    }

    public static class ErrorTexts {

        public static final String NO_DELEGATE = "Delegate must be set to run simulations";
        public static final String SIMULATION_ALREADY_RUNNING = "Can not start an already running simulation again, " +
                "you need to stop this simulation before running start again";
    }
}
