package android.text.format;

public class TimeMigrationUtils {
    private TimeMigrationUtils() {}

    public static String formatMillisWithFixedFormat(long timeMillis) {
        return new TimeFormatter().formatMillisWithFixedFormat(timeMillis);
    }
}
