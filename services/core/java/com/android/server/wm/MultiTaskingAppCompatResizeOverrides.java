package com.android.server.wm;

import android.util.Slog;

import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class MultiTaskingAppCompatResizeOverrides implements PackageFeatureCallback {
    public final Set mForceResizeAppList = new HashSet();
    public final Set mForceNonResizeAppList = new HashSet();
    public final ConcurrentHashMap mMetaDataCache = new ConcurrentHashMap();

    public MultiTaskingAppCompatResizeOverrides() {
        PackageFeature.DISPLAY_COMPAT.registerCallback(this);
    }

    @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
    public final void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData) {
        synchronized (this) {
            try {
                ((HashSet) this.mForceResizeAppList).clear();
                ((HashSet) this.mForceNonResizeAppList).clear();
                for (Map.Entry entry : packageFeatureData.entrySet()) {
                    String str = (String) entry.getKey();
                    String str2 = (String) entry.getValue();
                    if ("w".equals(str2)) {
                        ((HashSet) this.mForceResizeAppList).add(str);
                    } else if ("b".equals(str2)) {
                        ((HashSet) this.mForceNonResizeAppList).add(str);
                    } else {
                        Slog.w(
                                "MultiTaskingAppCompat",
                                "UnknownResizeOverrides: packageName=" + str + ", value=" + str2);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
