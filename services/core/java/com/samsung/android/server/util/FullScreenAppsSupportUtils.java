package com.samsung.android.server.util;

import android.hardware.display.DisplayManagerInternal;
import android.hardware.display.IDisplayManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;
import android.view.DisplayCutout;
import android.view.DisplayInfo;

import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.util.PackageSpecialManagementList;

public final class FullScreenAppsSupportUtils {
    public boolean mCached;
    public int mCachedFullScreenAppsSupportMode;
    public final PackageSpecialManagementList mDefaultFullScreenList =
            new PackageSpecialManagementList(PackageFeature.FULL_SCREEN);
    public IDisplayManager mDisplayManager;
    public DisplayManagerInternal mDisplayManagerInternal;

    public abstract class LazyHolder {
        public static final FullScreenAppsSupportUtils sUtils = new FullScreenAppsSupportUtils();
    }

    public final int getFullScreenAppsSupportMode() {
        int i;
        synchronized (this) {
            try {
                if (!this.mCached) {
                    synchronized (this) {
                        try {
                            if (this.mDisplayManager == null) {
                                IDisplayManager asInterface =
                                        IDisplayManager.Stub.asInterface(
                                                ServiceManager.getService("display"));
                                this.mDisplayManager = asInterface;
                                if (asInterface == null) {
                                    Slog.w("FullScreenAppsSupportUtils", "DisplayManager is null.");
                                }
                            }
                            if (this.mDisplayManagerInternal == null) {
                                DisplayManagerInternal displayManagerInternal =
                                        (DisplayManagerInternal)
                                                LocalServices.getService(
                                                        DisplayManagerInternal.class);
                                this.mDisplayManagerInternal = displayManagerInternal;
                                if (displayManagerInternal == null) {
                                    Slog.w(
                                            "FullScreenAppsSupportUtils",
                                            "DisplayManagerInternal is null.");
                                }
                            }
                            IDisplayManager iDisplayManager = this.mDisplayManager;
                            DisplayManagerInternal displayManagerInternal2 =
                                    this.mDisplayManagerInternal;
                            DisplayInfo displayInfo = new DisplayInfo();
                            try {
                                int[] displayIds = iDisplayManager.getDisplayIds(true);
                                float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
                                int i2 = 0;
                                float f2 = 0.0f;
                                for (int i3 : displayIds) {
                                    displayManagerInternal2.getNonOverrideDisplayInfo(
                                            i3, displayInfo);
                                    if (displayInfo.type == 1) {
                                        int i4 = displayInfo.logicalWidth;
                                        int i5 = displayInfo.logicalHeight;
                                        float max = Math.max(i4, i5) / Math.min(i4, i5);
                                        if (f < max) {
                                            f = max;
                                        }
                                        DisplayCutout displayCutout = displayInfo.displayCutout;
                                        if (displayCutout != null && !displayCutout.isEmpty()) {
                                            i2 |= 2;
                                            int safeInsetRight =
                                                    i4
                                                            - (displayInfo.displayCutout
                                                                            .getSafeInsetRight()
                                                                    + displayInfo.displayCutout
                                                                            .getSafeInsetLeft());
                                            int safeInsetBottom =
                                                    i5
                                                            - (displayInfo.displayCutout
                                                                            .getSafeInsetBottom()
                                                                    + displayInfo.displayCutout
                                                                            .getSafeInsetTop());
                                            max =
                                                    Math.max(safeInsetRight, safeInsetBottom)
                                                            / Math.min(
                                                                    safeInsetRight,
                                                                    safeInsetBottom);
                                        }
                                        if (max > 1.86f) {
                                            i2 |= 1;
                                        }
                                        if (f2 < max) {
                                            f2 = max;
                                        }
                                    }
                                }
                                synchronized (this) {
                                    this.mCachedFullScreenAppsSupportMode = i2;
                                    this.mCached = true;
                                }
                                Slog.i(
                                        "FullScreenAppsSupportUtils",
                                        "FullScreenAppsSupportMode=0x"
                                                + Integer.toHexString(i2)
                                                + ", DisplayMaxAspectRatio"
                                                + f2
                                                + ", DisplayMaxAspectRatioWithCutout"
                                                + f);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        } finally {
                        }
                    }
                }
            } finally {
            }
        }
        synchronized (this) {
            i = this.mCachedFullScreenAppsSupportMode;
        }
        return i;
    }
}
