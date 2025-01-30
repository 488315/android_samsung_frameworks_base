package android.sysprop;

import android.os.SystemProperties;
import java.util.Locale;
import java.util.Optional;

/* loaded from: classes.dex */
public abstract class WatchdogProperties {
    public static Boolean tryParseBoolean(String str) {
        if (str == null) {
            return null;
        }
        String lowerCase = str.toLowerCase(Locale.US);
        lowerCase.hashCode();
        switch (lowerCase) {
        }
        return null;
    }

    public static Integer tryParseInteger(String str) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public static Optional fatal_count() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("framework_watchdog.fatal_count")));
    }

    public static Optional fatal_window_seconds() {
        return Optional.ofNullable(tryParseInteger(SystemProperties.get("framework_watchdog.fatal_window.second")));
    }

    public static Optional should_ignore_fatal_count() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("persist.debug.framework_watchdog.fatal_ignore")));
    }
}
