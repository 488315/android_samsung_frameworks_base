package android.view.contentcapture;

import android.content.Context;
import android.os.Build;
import android.provider.DeviceConfig;
import android.util.ArraySet;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public final class ContentCaptureHelper {
    private static final String TAG = "ContentCaptureHelper";
    public static boolean sDebug = true;
    public static boolean sVerbose = false;

    public static String getSanitizedString(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        return charSequence.length() + "_chars";
    }

    public static int getDefaultLoggingLevel() {
        return Build.IS_DEBUGGABLE ? 1 : 0;
    }

    public static void setLoggingLevel() {
        setLoggingLevel(DeviceConfig.getInt(Context.CONTENT_CAPTURE_MANAGER_SERVICE, ContentCaptureManager.DEVICE_CONFIG_PROPERTY_LOGGING_LEVEL, getDefaultLoggingLevel()));
    }

    public static void setLoggingLevel(int i) {
        String str = TAG;
        Log.i(str, "Setting logging level to " + getLoggingLevelAsString(i));
        sDebug = false;
        sVerbose = false;
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    sVerbose = true;
                } else {
                    Log.w(str, "setLoggingLevel(): invalud level: " + i);
                    return;
                }
            }
            sDebug = true;
        }
    }

    public static String getLoggingLevelAsString(int i) {
        if (i == 0) {
            return "OFF";
        }
        if (i == 1) {
            return "DEBUG";
        }
        if (i == 2) {
            return "VERBOSE";
        }
        return "UNKNOWN-" + i;
    }

    public static <T> ArrayList<T> toList(Set<T> set) {
        if (set == null) {
            return null;
        }
        return new ArrayList<>(set);
    }

    public static <T> ArraySet<T> toSet(List<T> list) {
        if (list == null) {
            return null;
        }
        return new ArraySet<>(list);
    }

    private ContentCaptureHelper() {
        throw new UnsupportedOperationException("contains only static methods");
    }
}
