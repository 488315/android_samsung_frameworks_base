package android.text.format;

/* loaded from: classes4.dex */
public class TimeMigrationUtils {
    private TimeMigrationUtils() {
    }

    public static String formatMillisWithFixedFormat(long j) {
        return new TimeFormatter().formatMillisWithFixedFormat(j);
    }
}
