package com.android.server.wm;

import android.app.AppGlobals;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Slog;

import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MultiTaskingAppCompatAspectRatioOverrides {
    public final MinAspectRatioOverrides mMinAspectRatioOverrides = new MinAspectRatioOverrides();

    public final class MinAspectRatioOverrides {
        public final SystemUserMinAspectRatioOverrides mSystemOverrides;
        public final PackageFeatureUserChange mUserOverrides =
                new PackageFeatureUserChange(
                        8,
                        PackageFeatureUserChangePersister.ASPECT_RATIO_DIRECTORY,
                        "FixedAspectRatioPackageMap",
                        null,
                        true,
                        new MultiTaskingAppCompatAspectRatioOverrides$MinAspectRatioOverrides$$ExternalSyntheticLambda0(
                                this));

        public MinAspectRatioOverrides() {
            this.mSystemOverrides =
                    MultiTaskingAppCompatAspectRatioOverrides.this
                    .new SystemUserMinAspectRatioOverrides();
        }
    }

    class SystemUserMinAspectRatioOverrides extends ConcurrentHashMap
            implements PackageFeatureCallback {
        public SystemUserMinAspectRatioOverrides() {
            PackageFeature.MIN_ASPECT_RATIO.registerCallback(this);
        }

        @Override // com.samsung.android.server.packagefeature.PackageFeatureCallback
        public final void onPackageFeatureDataChanged(PackageFeatureData packageFeatureData) {
            float parseFloat;
            float parseFloat2;
            synchronized (this) {
                clear();
                for (Map.Entry entry : packageFeatureData.entrySet()) {
                    String str = (String) entry.getKey();
                    String str2 = (String) entry.getValue();
                    if (TextUtils.isEmpty(str2)) {
                        put(str, Float.valueOf(1.7777778f));
                    } else {
                        try {
                            String[] split = str2.split(":");
                            parseFloat = Float.parseFloat(split[0]);
                            parseFloat2 = Float.parseFloat(split[1]);
                        } catch (Exception e) {
                            Slog.w(
                                    "MultiTaskingAppCompat",
                                    "Fail to put min aspect ratio, packageName="
                                            + str
                                            + ", value="
                                            + str2,
                                    e);
                        }
                        if (parseFloat == 16.0f && parseFloat2 == 9.0f) {
                            put(str, Float.valueOf(1.7777778f));
                        } else {
                            if (parseFloat == 4.0f && parseFloat2 == 3.0f) {
                                put(str, Float.valueOf(1.3333334f));
                            }
                            put(str, Float.valueOf(1.7777778f));
                        }
                    }
                }
            }
        }
    }

    public static int getUserMinAspectRatioOverrideCode(int i, String str) {
        try {
            return AppGlobals.getPackageManager().getUserMinAspectRatio(str, i);
        } catch (RemoteException e) {
            Slog.w(
                    "MultiTaskingAppCompat",
                    SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0
                            .m(
                                    i,
                                    "Exception thrown retrieving aspect ratio user override."
                                        + " packageName=",
                                    str,
                                    ", userId="),
                    e);
            return 0;
        }
    }

    public final float getSystemMinAspectRatio(String str) {
        Float f;
        float f2;
        MinAspectRatioOverrides minAspectRatioOverrides = this.mMinAspectRatioOverrides;
        synchronized (minAspectRatioOverrides) {
            SystemUserMinAspectRatioOverrides systemUserMinAspectRatioOverrides =
                    minAspectRatioOverrides.mSystemOverrides;
            synchronized (systemUserMinAspectRatioOverrides) {
                f = (Float) systemUserMinAspectRatioOverrides.get(str);
            }
            f2 = -1.0f;
            if (f != null) {
                float floatValue = f.floatValue();
                if (floatValue > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    f2 = floatValue;
                }
            }
        }
        return f2;
    }

    public final float getUserOrSystemMinAspectRatio(int i, String str) {
        int userMinAspectRatioOverrideCode = getUserMinAspectRatioOverrideCode(i, str);
        float f =
                userMinAspectRatioOverrideCode == 4
                        ? 1.7777778f
                        : userMinAspectRatioOverrideCode == 3 ? 1.3333334f : -1.0f;
        if (f != -1.0f) {
            return f;
        }
        float systemMinAspectRatio = getSystemMinAspectRatio(str);
        if (systemMinAspectRatio != -1.0f) {
            return systemMinAspectRatio;
        }
        return -1.0f;
    }
}
