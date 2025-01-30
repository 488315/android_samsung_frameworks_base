package com.samsung.android.server.util;

import android.util.Slog;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class DisplayCompatPolicies extends ConcurrentHashMap implements PackageFeatureCallback {

    public abstract class LazyHolder {
        public static final DisplayCompatPolicies sDisplayCompatPolicies = new DisplayCompatPolicies();
    }

    public static boolean isForcedResizeable(int i) {
        return i == 3 || i == 1 || i == 6;
    }

    public static boolean isForcedResizeableByMetaData(int i) {
        return i == 5;
    }

    public static boolean isForcedUnresizeable(int i) {
        return i == 4 || i == 2;
    }

    public static DisplayCompatPolicies getDisplayCompatPolicies() {
        return LazyHolder.sDisplayCompatPolicies;
    }

    public static int getSizeChangesSupported(int i) {
        if (isForcedResizeableByMetaData(i)) {
            return 2;
        }
        if (isForcedResizeable(i)) {
            return 3;
        }
        return isForcedUnresizeable(i) ? 1 : 0;
    }

    private DisplayCompatPolicies() {
        PackageFeature.DISPLAY_COMPAT.registerCallback(this);
    }

    public int getPolicy(String str) {
        int intValue;
        synchronized (this) {
            Integer num = (Integer) get(str);
            intValue = num != null ? num.intValue() : 0;
        }
        return intValue;
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
    public void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData) {
        synchronized (this) {
            clear();
            for (Map.Entry entry : packageFeatureData.entrySet()) {
                String str = (String) entry.getKey();
                String str2 = (String) entry.getValue();
                if ("w".equals(str2)) {
                    put(str, 3);
                } else if ("b".equals(str2)) {
                    put(str, 4);
                } else {
                    Slog.w("PackageFeature", "UnknownDisplayCompatPolicy: packageName=" + str + ", value=" + str2);
                }
            }
        }
    }
}
