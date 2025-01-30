package android.sysprop;

import android.os.SystemProperties;
import java.util.Locale;
import java.util.Optional;

/* loaded from: classes.dex */
public abstract class SurfaceFlingerProperties {
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

    public static Optional has_wide_color_display() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ro.surface_flinger.has_wide_color_display")));
    }

    public static Optional has_HDR_display() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ro.surface_flinger.has_HDR_display")));
    }

    public static Optional enable_frame_rate_override() {
        return Optional.ofNullable(tryParseBoolean(SystemProperties.get("ro.surface_flinger.enable_frame_rate_override")));
    }
}
