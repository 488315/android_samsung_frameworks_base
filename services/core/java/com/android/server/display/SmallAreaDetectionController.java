package com.android.server.display;

import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.DeviceConfigInterface;
import android.util.ArrayMap;
import android.util.SparseArray;

import com.android.internal.os.BackgroundThread;
import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.pm.pkg.PackageStateInternal;

import java.util.Map;

final class SmallAreaDetectionController {
    public final Context mContext;
    public final PackageManagerInternal mPackageManager;
    public final Object mLock = new Object();
    public final Map mAllowPkgMap = new ArrayMap();

    public final class OnPropertiesChangedListener
            implements DeviceConfig.OnPropertiesChangedListener {
        public OnPropertiesChangedListener() {}

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            if (properties.getKeyset().contains("small_area_detection_allowlist")) {
                SmallAreaDetectionController.this.updateAllowlist(
                        properties.getString("small_area_detection_allowlist", (String) null));
            }
        }
    }

    public final class PackageReceiver implements PackageManagerInternal.PackageListObserver {
        public PackageReceiver() {}

        @Override // android.content.pm.PackageManagerInternal.PackageListObserver
        public final void onPackageAdded(String str, int i) {
            float floatValue;
            synchronized (SmallAreaDetectionController.this.mLock) {
                try {
                    floatValue =
                            ((ArrayMap) SmallAreaDetectionController.this.mAllowPkgMap)
                                            .containsKey(str)
                                    ? ((Float)
                                                    ((ArrayMap)
                                                                    SmallAreaDetectionController
                                                                            .this
                                                                            .mAllowPkgMap)
                                                            .get(str))
                                            .floatValue()
                                    : 0.0f;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (floatValue > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                SmallAreaDetectionController smallAreaDetectionController =
                        SmallAreaDetectionController.this;
                int appId = UserHandle.getAppId(i);
                smallAreaDetectionController.getClass();
                SmallAreaDetectionController.setSmallAreaDetectionThreshold(floatValue, appId);
            }
        }
    }

    public SmallAreaDetectionController(
            Context context, DeviceConfigInterface deviceConfigInterface) {
        this.mContext = context;
        PackageManagerInternal packageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        this.mPackageManager = packageManagerInternal;
        deviceConfigInterface.addOnPropertiesChangedListener(
                "display_manager",
                BackgroundThread.getExecutor(),
                new OnPropertiesChangedListener());
        packageManagerInternal.getPackageList(new PackageReceiver());
    }

    private static native void nativeSetSmallAreaDetectionThreshold(int i, float f);

    private static native void nativeUpdateSmallAreaDetection(int[] iArr, float[] fArr);

    public static void setSmallAreaDetectionThreshold(float f, int i) {
        nativeSetSmallAreaDetectionThreshold(i, f);
    }

    public final void putToAllowlist(String str) {
        String[] split = str.split(":");
        if (split.length == 2) {
            try {
                String str2 = split[0];
                Float valueOf = Float.valueOf(split[1]);
                valueOf.floatValue();
                ((ArrayMap) this.mAllowPkgMap).put(str2, valueOf);
            } catch (Exception unused) {
            }
        }
    }

    public void updateAllowlist(String str) {
        ArrayMap arrayMap = new ArrayMap();
        synchronized (this.mLock) {
            try {
                ((ArrayMap) this.mAllowPkgMap).clear();
                if (str != null) {
                    for (String str2 : str.split(",")) {
                        putToAllowlist(str2);
                    }
                } else {
                    for (String str3 : this.mContext.getResources().getStringArray(17236319)) {
                        putToAllowlist(str3);
                    }
                }
                if (((ArrayMap) this.mAllowPkgMap).isEmpty()) {
                    return;
                }
                arrayMap.putAll(this.mAllowPkgMap);
                SparseArray sparseArray = new SparseArray(arrayMap.size());
                for (String str4 : arrayMap.keySet()) {
                    Float f = (Float) arrayMap.get(str4);
                    f.floatValue();
                    PackageStateInternal packageStateInternal =
                            this.mPackageManager.getPackageStateInternal(str4);
                    if (packageStateInternal != null) {
                        sparseArray.put(packageStateInternal.getAppId(), f);
                    }
                }
                int[] iArr = new int[sparseArray.size()];
                float[] fArr = new float[sparseArray.size()];
                for (int i = 0; i < sparseArray.size(); i++) {
                    iArr[i] = sparseArray.keyAt(i);
                    fArr[i] = ((Float) sparseArray.valueAt(i)).floatValue();
                }
                updateSmallAreaDetection(iArr, fArr);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void updateSmallAreaDetection(int[] iArr, float[] fArr) {
        nativeUpdateSmallAreaDetection(iArr, fArr);
    }
}
