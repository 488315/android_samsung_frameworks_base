package com.android.server.wm;

import android.util.Slog;

import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
