package com.samsung.android.server.util;

import android.hardware.display.DisplayManagerGlobal;
import android.hardware.display.DisplayManagerInternal;
import android.text.TextUtils;
import android.util.Slog;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.IWindowManager;
import com.android.server.LocalServices;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.util.PackageSpecialManagementList;
import java.util.List;

/* loaded from: classes2.dex */
public class FullScreenAppsSupportUtils {
    public boolean mCached;
    public float mCachedDisplayMaxAspectRatio;
    public float mCachedDisplayMaxAspectRatioWithCutout;
    public int mCachedFullScreenAppsSupportMode;
    public final List mDefaultFullScreenList;
    public DisplayManagerInternal mDisplayManagerInternal;
    public IWindowManager mWindowManager;

    public abstract class LazyHolder {
        public static FullScreenAppsSupportUtils sUtils = new FullScreenAppsSupportUtils();
    }

    public static FullScreenAppsSupportUtils get() {
        return LazyHolder.sUtils;
    }

    public FullScreenAppsSupportUtils() {
        this.mWindowManager = null;
        this.mDefaultFullScreenList = new PackageSpecialManagementList(PackageFeature.FULL_SCREEN);
    }

    public boolean containsInDefaultFullScreenList(String str) {
        List list;
        return (TextUtils.isEmpty(str) || (list = this.mDefaultFullScreenList) == null || !list.contains(str)) ? false : true;
    }

    public void invalidateCache() {
        synchronized (this) {
            this.mCached = false;
        }
    }

    public int getFullScreenAppsSupportMode() {
        int i;
        synchronized (this) {
            if (this.mCached) {
                return this.mCachedFullScreenAppsSupportMode;
            }
            updateCachedInfo();
            synchronized (this) {
                i = this.mCachedFullScreenAppsSupportMode;
            }
            return i;
        }
    }

    public float getDisplayMaxAspectRatio(boolean z) {
        float cachedDisplayMaxAspectRatio;
        synchronized (this) {
            if (this.mCached) {
                return getCachedDisplayMaxAspectRatio(z);
            }
            updateCachedInfo();
            synchronized (this) {
                cachedDisplayMaxAspectRatio = getCachedDisplayMaxAspectRatio(z);
            }
            return cachedDisplayMaxAspectRatio;
        }
    }

    public final float getCachedDisplayMaxAspectRatio(boolean z) {
        if (z) {
            return this.mCachedDisplayMaxAspectRatioWithCutout;
        }
        return this.mCachedDisplayMaxAspectRatio;
    }

    public boolean supportsDisplayCutout() {
        return (getFullScreenAppsSupportMode() & 2) != 0;
    }

    public boolean supportsMaxAspectRatio() {
        return (getFullScreenAppsSupportMode() & 1) != 0;
    }

    public final void updateCachedInfo() {
        if (this.mDisplayManagerInternal == null) {
            DisplayManagerInternal displayManagerInternal = (DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class);
            this.mDisplayManagerInternal = displayManagerInternal;
            if (displayManagerInternal == null) {
                Slog.w("FullScreenAppsSupportUtils", "DisplayManagerInternal is null.");
                return;
            }
        }
        DisplayInfo displayInfo = new DisplayInfo();
        int[] displayIds = DisplayManagerGlobal.getInstance().getDisplayIds(true);
        float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        int i = 0;
        float f2 = 0.0f;
        for (int i2 : displayIds) {
            this.mDisplayManagerInternal.getNonOverrideDisplayInfo(i2, displayInfo);
            if (displayInfo.type == 1) {
                int i3 = displayInfo.logicalWidth;
                int i4 = displayInfo.logicalHeight;
                float max = Math.max(i3, i4) / Math.min(i3, i4);
                if (f < max) {
                    f = max;
                }
                DisplayCutout displayCutout = displayInfo.displayCutout;
                if (displayCutout != null && !displayCutout.isEmpty()) {
                    i |= 2;
                    int safeInsetLeft = i3 - (displayInfo.displayCutout.getSafeInsetLeft() + displayInfo.displayCutout.getSafeInsetRight());
                    int safeInsetTop = i4 - (displayInfo.displayCutout.getSafeInsetTop() + displayInfo.displayCutout.getSafeInsetBottom());
                    max = Math.max(safeInsetLeft, safeInsetTop) / Math.min(safeInsetLeft, safeInsetTop);
                }
                if (max > 1.86f) {
                    i |= 1;
                }
                if (f2 < max) {
                    f2 = max;
                }
            }
        }
        synchronized (this) {
            this.mCachedFullScreenAppsSupportMode = i;
            this.mCachedDisplayMaxAspectRatio = f2;
            this.mCachedDisplayMaxAspectRatioWithCutout = f;
            this.mCached = true;
        }
        Slog.i("FullScreenAppsSupportUtils", "FullScreenAppsSupportMode=0x" + Integer.toHexString(i) + ", DisplayMaxAspectRatio" + f2 + ", DisplayMaxAspectRatioWithCutout" + f);
    }
}
