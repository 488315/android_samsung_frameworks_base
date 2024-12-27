package com.android.server.display;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.ArraySet;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceControl;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.mode.DisplayModeDirector;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class DisplayDevice {
    public static final Display.Mode EMPTY_DISPLAY_MODE = new Display.Mode.Builder().build();
    public final Context mContext;
    public Rect mCurrentDisplayRect;
    public Rect mCurrentLayerStackRect;
    public Surface mCurrentSurface;
    public DisplayDeviceInfo mDebugLastLoggedDeviceInfo;
    public final DisplayAdapter mDisplayAdapter;
    public final IBinder mDisplayToken;
    public final boolean mIsAnisotropyCorrectionEnabled;
    public final String mUniqueId;
    public int mCurrentLayerStack = -1;
    public int mCurrentFlags = 0;
    public int mCurrentOrientation = -1;
    public DisplayDeviceConfig mDisplayDeviceConfig = null;

    public DisplayDevice(
            DisplayAdapter displayAdapter,
            IBinder iBinder,
            String str,
            Context context,
            boolean z) {
        this.mDisplayAdapter = displayAdapter;
        this.mDisplayToken = iBinder;
        this.mUniqueId = str;
        this.mContext = context;
        this.mIsAnisotropyCorrectionEnabled = z;
    }

    public void applyPendingDisplayDeviceInfoChangesLocked() {}

    public void dumpLocked(PrintWriter printWriter) {
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        printWriter,
                        this.mUniqueId,
                        "mDisplayToken=",
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                printWriter,
                                this.mDisplayAdapter.mName,
                                "mUniqueId=",
                                new StringBuilder("mAdapter=")));
        m.append(this.mDisplayToken);
        printWriter.println(m.toString());
        StringBuilder m2 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("mCurrentLayerStack="),
                                        this.mCurrentLayerStack,
                                        printWriter,
                                        "mCurrentFlags="),
                                this.mCurrentFlags,
                                printWriter,
                                "mCurrentOrientation="),
                        this.mCurrentOrientation,
                        printWriter,
                        "mCurrentLayerStackRect=");
        m2.append(this.mCurrentLayerStackRect);
        printWriter.println(m2.toString());
        printWriter.println("mCurrentDisplayRect=" + this.mCurrentDisplayRect);
        printWriter.println("mCurrentSurface=" + this.mCurrentSurface);
    }

    public Display.Mode getActiveDisplayModeAtStartLocked() {
        return EMPTY_DISPLAY_MODE;
    }

    public boolean getDexEnabledStateLocked() {
        return false;
    }

    public DisplayDeviceConfig getDisplayDeviceConfig() {
        if (this.mDisplayDeviceConfig == null) {
            this.mDisplayDeviceConfig =
                    DisplayDeviceConfig.getConfigFromPmValues(
                            this.mContext, this.mDisplayAdapter.mFeatureFlags);
        }
        return this.mDisplayDeviceConfig;
    }

    public abstract DisplayDeviceInfo getDisplayDeviceInfoLocked();

    public int getDisplayIdToMirrorLocked() {
        return 0;
    }

    public Point getDisplaySurfaceDefaultSizeLocked() {
        DisplayDeviceInfo displayDeviceInfoLocked = getDisplayDeviceInfoLocked();
        int i = displayDeviceInfoLocked.width;
        int i2 = displayDeviceInfoLocked.height;
        if (this.mIsAnisotropyCorrectionEnabled && displayDeviceInfoLocked.type == 2) {
            float f = displayDeviceInfoLocked.yDpi;
            if (f > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                float f2 = displayDeviceInfoLocked.xDpi;
                if (f2 > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    if (f2 > f * 1.025f) {
                        i2 = (int) (((i2 * f2) / f) + 0.5d);
                    } else if (1.025f * f2 < f) {
                        i = (int) (((i * f) / f2) + 0.5d);
                    }
                }
            }
        }
        return isRotatedLocked() ? new Point(i2, i) : new Point(i, i2);
    }

    public final Point[] getSupportedResolutionsLocked() {
        ArraySet arraySet = new ArraySet(2);
        for (Display.Mode mode : getDisplayDeviceInfoLocked().supportedModes) {
            arraySet.add(new Point(mode.getPhysicalWidth(), mode.getPhysicalHeight()));
        }
        Point[] pointArr = new Point[arraySet.size()];
        arraySet.toArray(pointArr);
        Arrays.sort(pointArr, new DisplayDevice$$ExternalSyntheticLambda0());
        return pointArr;
    }

    public Display.Mode getSystemPreferredDisplayModeLocked() {
        return EMPTY_DISPLAY_MODE;
    }

    public Display.Mode getUserPreferredDisplayModeLocked() {
        return EMPTY_DISPLAY_MODE;
    }

    public abstract boolean hasStableUniqueId();

    public boolean isFirstDisplay() {
        return false;
    }

    public boolean isRotatedLocked() {
        int i = this.mCurrentOrientation;
        return i == 1 || i == 3;
    }

    public boolean isWindowManagerMirroringLocked() {
        return false;
    }

    public void onOverlayChangedLocked() {}

    public void performTraversalLocked(SurfaceControl.Transaction transaction) {}

    public Runnable requestDisplayStateLocked(
            int i, float f, float f2, DisplayOffloadSessionImpl displayOffloadSessionImpl) {
        return null;
    }

    public Runnable requestDisplayStateLocked(
            int i,
            float f,
            float f2,
            DisplayOffloadSessionImpl displayOffloadSessionImpl,
            int i2,
            ArrayList arrayList) {
        return requestDisplayStateLocked(i, f, f2, displayOffloadSessionImpl);
    }

    public void setAutoLowLatencyModeLocked(boolean z) {}

    public void setDesiredDisplayModeSpecsLocked(
            DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {}

    public void setGameContentTypeLocked(boolean z) {}

    public void setRequestedColorModeLocked(int i) {}

    public void setUserPreferredDisplayModeLocked(Display.Mode mode) {}

    public void setWindowManagerMirroringLocked(boolean z) {}

    public void updateDexEnabledStateLocked(boolean z) {}
}
