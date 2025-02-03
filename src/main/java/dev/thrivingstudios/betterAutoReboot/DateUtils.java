package dev.thrivingstudios.betterAutoReboot;

public class DateUtils {
    public static String formatCountSimple(int countInSeconds) {
        if (countInSeconds < 0) {
            return "-1";
        }

        int hours = countInSeconds / 3600;
        int remainingSeconds = countInSeconds % 3600;

        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;

        StringBuilder result = new StringBuilder();

        if (hours > 0) {
            result.append(hours).append("h");
        }

        if ((minutes > 0 || hours > 0) && seconds == 0) {
            if (!result.toString().contains("h")) {
                result.append(minutes).append("m");
            }
        } else if (seconds > 0) {
            if (!result.toString().contains("h")) {
                if (minutes > 0) {
                    result.append(minutes).append("m").append(" ");
                }
                result.append(seconds).append("s");
            }
        }

        return result.toString();
    }

    public static String formatCountFull(int countInSeconds) {
        if (countInSeconds < 0) {
            return "-1";
        }

        int hours = countInSeconds / 3600;
        int remainingSeconds = countInSeconds % 3600;

        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;

        StringBuilder result = new StringBuilder();

        if (hours > 0) {
            result.append(hours).append(" hour");
            if (hours > 1) result.append("s");
            result.append(" ");
        }

        if (minutes > 0) {
            result.append(minutes).append(" minute");
            if (minutes > 1) result.append("s");
            result.append(" ");
        }

        if (seconds > 0 || result.isEmpty()) {
            result.append(seconds).append(" second");
            if (seconds > 1 || seconds == 0) result.append("s");
        }

        return result.toString().trim();
    }
}
