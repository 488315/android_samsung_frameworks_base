package android.net;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.android.internal.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Deprecated
/* loaded from: classes3.dex */
public class NetworkBadging {
    public static final int BADGING_4K = 30;
    public static final int BADGING_HD = 20;
    public static final int BADGING_NONE = 0;
    public static final int BADGING_SD = 10;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Badging {
    }

    private NetworkBadging() {
    }

    public static Drawable getWifiIcon(int i, int i2, Resources.Theme theme) {
        return Resources.getSystem().getDrawable(getWifiSignalResource(i), theme);
    }

    private static int getWifiSignalResource(int i) {
        if (i == 0) {
            return R.drawable.ic_wifi_signal_0;
        }
        if (i == 1) {
            return R.drawable.ic_wifi_signal_1;
        }
        if (i == 2) {
            return R.drawable.ic_wifi_signal_2;
        }
        if (i == 3) {
            return R.drawable.ic_wifi_signal_3;
        }
        if (i == 4) {
            return R.drawable.ic_wifi_signal_4;
        }
        throw new IllegalArgumentException("Invalid signal level: " + i);
    }
}
