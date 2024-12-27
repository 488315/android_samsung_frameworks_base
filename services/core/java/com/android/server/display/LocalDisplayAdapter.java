package com.android.server.display;

import android.R;
import android.app.ActivityThread;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.sidekick.SidekickInternal;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.DisplayMetrics;
import android.util.DisplayUtils;
import android.util.LongSparseArray;
import android.util.MathUtils;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.DisplayCutout;
import android.view.DisplayEventReceiver;
import android.view.DisplayShape;
import android.view.RoundedCorners;
import android.view.SurfaceControl;

import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.display.color.ColorDisplayService;
import com.android.server.display.config.EvenDimmerBrightnessData;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.display.mode.DisplayModeDirector;
import com.android.server.display.notifications.DisplayNotificationManager;
import com.android.server.lights.LightsManager;
import com.android.server.lights.LightsService;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class LocalDisplayAdapter extends DisplayAdapter {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ColorDisplayService.ColorDisplayServiceInternal mCdsi;
    public final LongSparseArray mDevices;
    public final DisplayNotificationManager mDisplayNotificationManager;
    public int mEvenDimmerStrength;
    public final Injector mInjector;
    public final boolean mIsBootDisplayModeSupported;
    public Context mOverlayContext;
    public final SurfaceControlProxy mSurfaceControlProxy;

    public final class BacklightAdapter {
        public final LightsService.LightImpl mBacklight;
        public final IBinder mDisplayToken;
        public boolean mForceSurfaceControl = false;
        public final boolean mIsFirstDisplay;
        public final SurfaceControlProxy mSurfaceControlProxy;
        public final boolean mUseSurfaceControlBrightness;

        public BacklightAdapter(
                IBinder iBinder, boolean z, SurfaceControlProxy surfaceControlProxy, long j) {
            this.mDisplayToken = iBinder;
            this.mSurfaceControlProxy = surfaceControlProxy;
            surfaceControlProxy.getClass();
            boolean displayBrightnessSupport = SurfaceControl.getDisplayBrightnessSupport(iBinder);
            this.mUseSurfaceControlBrightness = displayBrightnessSupport;
            this.mIsFirstDisplay = z;
            SurfaceControl.StaticDisplayInfo staticDisplayInfo =
                    SurfaceControl.getStaticDisplayInfo(j);
            boolean z2 = staticDisplayInfo != null && staticDisplayInfo.isInternal;
            if (!displayBrightnessSupport && z) {
                this.mBacklight =
                        ((LightsManager) LocalServices.getService(LightsManager.class)).getLight(0);
            } else if (displayBrightnessSupport || !z2) {
                this.mBacklight = null;
            } else {
                this.mBacklight =
                        ((LightsManager) LocalServices.getService(LightsManager.class)).getLight(9);
            }
        }

        public final void setBacklight(float f, float f2, float f3, float f4, int i, int i2) {
            if (!this.mUseSurfaceControlBrightness && !this.mForceSurfaceControl) {
                LightsService.LightImpl lightImpl = this.mBacklight;
                if (lightImpl != null) {
                    if (Float.isNaN(f3)) {
                        Slog.w("LightsService", "Brightness is not valid: " + f3);
                        return;
                    } else {
                        synchronized (lightImpl) {
                            int brightnessFloatToInt =
                                    BrightnessSynchronizer.brightnessFloatToInt(f3);
                            if (brightnessFloatToInt <= 255) {
                                int i3 =
                                        brightnessFloatToInt
                                                & IDnsResolverUnsolicitedEventListener
                                                        .DNS_HEALTH_RESULT_TIMEOUT;
                                brightnessFloatToInt = i3 | (i3 << 16) | (-16777216) | (i3 << 8);
                            }
                            lightImpl.setLightLocked(brightnessFloatToInt, 0, 0, 0);
                        }
                        return;
                    }
                }
                return;
            }
            if (BrightnessSynchronizer.floatEquals(f, Float.NaN)) {
                SurfaceControlProxy surfaceControlProxy = this.mSurfaceControlProxy;
                IBinder iBinder = this.mDisplayToken;
                surfaceControlProxy.getClass();
                SurfaceControl.setDisplayBrightness(iBinder, f3);
                return;
            }
            StringBuilder sb = new StringBuilder("surface lcd : ");
            sb.append(PowerManagerUtil.brightnessToString(f3, i));
            sb.append(", ");
            sb.append(PowerManagerUtil.brightnessToString(f, i2));
            sb.append(", ");
            sb.append(this.mIsFirstDisplay ? "main" : "sub");
            sb.append(" +");
            Slog.d("LocalDisplayAdapter", sb.toString());
            SurfaceControlProxy surfaceControlProxy2 = this.mSurfaceControlProxy;
            IBinder iBinder2 = this.mDisplayToken;
            surfaceControlProxy2.getClass();
            SurfaceControl.setDisplayBrightness(iBinder2, f, f2, f3, f4);
            StringBuilder sb2 = new StringBuilder("surface lcd : ");
            sb2.append(PowerManagerUtil.brightnessToString(f3, i));
            sb2.append(", ");
            sb2.append(PowerManagerUtil.brightnessToString(f, i2));
            sb2.append(", ");
            sb2.append(this.mIsFirstDisplay ? "main" : "sub");
            sb2.append(" -");
            Slog.d("LocalDisplayAdapter", sb2.toString());
        }

        public final String toString() {
            return "BacklightAdapter [useSurfaceControl="
                    + this.mUseSurfaceControlBrightness
                    + " (force_anyway? "
                    + this.mForceSurfaceControl
                    + "), backlight="
                    + this.mBacklight
                    + "]";
        }
    }

    public final class DisplayModeRecord {
        public final Display.Mode mMode;

        public DisplayModeRecord(SurfaceControl.DisplayMode displayMode, float[] fArr) {
            int i = displayMode.width;
            int i2 = displayMode.height;
            float f = displayMode.peakRefreshRate;
            float f2 = displayMode.vsyncRate;
            int[] iArr = displayMode.supportedHdrTypes;
            AtomicInteger atomicInteger = DisplayAdapter.NEXT_DISPLAY_MODE_ID;
            this.mMode =
                    new Display.Mode(
                            DisplayAdapter.NEXT_DISPLAY_MODE_ID.getAndIncrement(),
                            i,
                            i2,
                            f,
                            f2,
                            false,
                            fArr,
                            iArr);
        }

        public final boolean hasMatchingMode(SurfaceControl.DisplayMode displayMode) {
            return this.mMode.getPhysicalWidth() == displayMode.width
                    && this.mMode.getPhysicalHeight() == displayMode.height
                    && Float.floatToIntBits(this.mMode.getRefreshRate())
                            == Float.floatToIntBits(displayMode.peakRefreshRate)
                    && Float.floatToIntBits(this.mMode.getVsyncRate())
                            == Float.floatToIntBits(displayMode.vsyncRate);
        }

        public final String toString() {
            return "DisplayModeRecord{mMode=" + this.mMode + "}";
        }
    }

    public final class Injector {
        public ProxyDisplayEventReceiver mReceiver;
    }

    public final class LocalDisplayDevice extends DisplayDevice {
        public int mActiveColorMode;
        public int mActiveModeId;
        public float mActiveRenderFrameRate;
        public SurfaceControl.DisplayMode mActiveSfDisplayMode;
        public final int mActiveSfDisplayModeAtStartId;
        public boolean mAllmRequested;
        public boolean mAllmSupported;
        public final BacklightAdapter mBacklightAdapter;
        public float mBrightnessState;
        public int mCommittedState;
        public int mConnectedHdcpLevel;
        public float mCurrentHdrSdrRatio;
        public int mDefaultModeGroup;
        public int mDefaultModeId;
        public final DisplayModeDirector.DesiredDisplayModeSpecs mDisplayModeSpecs;
        public boolean mDisplayModeSpecsInvalid;
        public int mDisplayStateCount;
        public DisplayEventReceiver.FrameRateOverride[] mFrameRateOverrides;
        public boolean mGameContentTypeRequested;
        public boolean mGameContentTypeSupported;
        public boolean mHavePendingChanges;
        public Display.HdrCapabilities mHdrCapabilities;
        public DisplayDeviceInfo mInfo;
        public final boolean mIsFirstDisplay;
        public int mLastResolution;
        public final long mPhysicalDisplayId;
        public int mRequestedState;
        public float mSdrBrightnessState;
        public SurfaceControl.DisplayMode[] mSfDisplayModes;
        public boolean mSidekickActive;
        public final SidekickInternal mSidekickInternal;
        public int mState;
        public final ArrayList mStateChangeCallbacks;
        public int mStateOverride;
        public SurfaceControl.StaticDisplayInfo mStaticDisplayInfo;
        public final ArrayList mSupportedColorModes;
        public final SparseArray mSupportedModes;
        public int mSystemPreferredModeId;
        public Display.Mode mUserPreferredMode;
        public int mUserPreferredModeId;

        /* renamed from: com.android.server.display.LocalDisplayAdapter$LocalDisplayDevice$1, reason: invalid class name */
        public final class AnonymousClass1 implements Runnable {
            public final /* synthetic */ boolean val$brightnessChanged;
            public final /* synthetic */ float val$brightnessState;
            public final /* synthetic */ DisplayOffloadSessionImpl val$displayOffloadSession;
            public final /* synthetic */ ArrayList val$displayStateListeners;
            public final /* synthetic */ int val$displayType;
            public final /* synthetic */ int val$oldState;
            public final /* synthetic */ long val$physicalDisplayId;
            public final /* synthetic */ float val$sdrBrightnessState;
            public final /* synthetic */ int val$state;
            public final /* synthetic */ boolean val$stateChanged;
            public final /* synthetic */ int val$stateOverride;
            public final /* synthetic */ boolean val$stateOverrideChanged;
            public final /* synthetic */ IBinder val$token;

            public AnonymousClass1(
                    boolean z,
                    boolean z2,
                    int i,
                    ArrayList arrayList,
                    int i2,
                    long j,
                    boolean z3,
                    float f,
                    float f2,
                    int i3,
                    int i4,
                    DisplayOffloadSessionImpl displayOffloadSessionImpl,
                    IBinder iBinder) {
                this.val$stateChanged = z;
                this.val$stateOverrideChanged = z2;
                this.val$stateOverride = i;
                this.val$displayStateListeners = arrayList;
                this.val$displayType = i2;
                this.val$physicalDisplayId = j;
                this.val$brightnessChanged = z3;
                this.val$brightnessState = f;
                this.val$sdrBrightnessState = f2;
                this.val$oldState = i3;
                this.val$state = i4;
                this.val$displayOffloadSession = displayOffloadSessionImpl;
                this.val$token = iBinder;
            }

            public final void applyColorMatrixBasedDimming(float f) {
                int constrainedMap =
                        (int)
                                (MathUtils.constrainedMap(
                                                90.0f,
                                                FullScreenMagnificationGestureHandler.MAX_SCALE,
                                                FullScreenMagnificationGestureHandler.MAX_SCALE,
                                                LocalDisplayDevice.this
                                                                        .mDisplayDeviceConfig
                                                                        .mEvenDimmerBrightnessData
                                                                == null
                                                        ? 0.0f
                                                        : r0.mTransitionPoint,
                                                f)
                                        + 0.5d);
                if (LocalDisplayAdapter.this.mEvenDimmerStrength < 0
                        || MathUtils.abs(r0 - constrainedMap) > 1.0f
                        || constrainedMap <= 1) {
                    LocalDisplayAdapter.this.mEvenDimmerStrength = constrainedMap;
                }
                LocalDisplayAdapter localDisplayAdapter = LocalDisplayAdapter.this;
                boolean z =
                        ((float) localDisplayAdapter.mEvenDimmerStrength)
                                > FullScreenMagnificationGestureHandler.MAX_SCALE;
                if (localDisplayAdapter.mCdsi == null) {
                    localDisplayAdapter.mCdsi =
                            (ColorDisplayService.ColorDisplayServiceInternal)
                                    LocalServices.getService(
                                            ColorDisplayService.ColorDisplayServiceInternal.class);
                }
                ColorDisplayService.ColorDisplayServiceInternal colorDisplayServiceInternal =
                        LocalDisplayAdapter.this.mCdsi;
                if (colorDisplayServiceInternal != null) {
                    colorDisplayServiceInternal.applyEvenDimmerColorChanges(constrainedMap, z);
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:43:0x0139  */
            /* JADX WARN: Removed duplicated region for block: B:46:0x014e  */
            /* JADX WARN: Removed duplicated region for block: B:48:? A[RETURN, SYNTHETIC] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    Method dump skipped, instructions count: 349
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException(
                        "Method not decompiled:"
                            + " com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.AnonymousClass1.run():void");
            }

            public final void setDisplayBrightness(float f, float f2) {
                float interpolate;
                float interpolate2;
                DisplayDeviceConfig displayDeviceConfig;
                if (Float.isNaN(f) || Float.isNaN(f2)) {
                    return;
                }
                LocalDisplayDevice localDisplayDevice = LocalDisplayDevice.this;
                SystemClock.uptimeMillis();
                localDisplayDevice.getClass();
                Trace.traceBegin(
                        131072L,
                        "setDisplayBrightness(id="
                                + this.val$physicalDisplayId
                                + ", brightnessState="
                                + f
                                + ", sdrBrightnessState="
                                + f2
                                + ")");
                if (f == -1.0f) {
                    interpolate = -1.0f;
                } else {
                    try {
                        DisplayDeviceConfig displayDeviceConfig2 =
                                LocalDisplayDevice.this.getDisplayDeviceConfig();
                        EvenDimmerBrightnessData evenDimmerBrightnessData =
                                displayDeviceConfig2.mEvenDimmerBrightnessData;
                        interpolate =
                                evenDimmerBrightnessData != null
                                        ? evenDimmerBrightnessData.mBrightnessToBacklight
                                                .interpolate(f)
                                        : displayDeviceConfig2.mBrightnessToBacklightSpline
                                                .interpolate(f);
                    } finally {
                        Trace.traceEnd(131072L);
                    }
                }
                if (f2 == -1.0f) {
                    interpolate2 = -1.0f;
                } else {
                    DisplayDeviceConfig displayDeviceConfig3 =
                            LocalDisplayDevice.this.getDisplayDeviceConfig();
                    EvenDimmerBrightnessData evenDimmerBrightnessData2 =
                            displayDeviceConfig3.mEvenDimmerBrightnessData;
                    interpolate2 =
                            evenDimmerBrightnessData2 != null
                                    ? evenDimmerBrightnessData2.mBrightnessToBacklight.interpolate(
                                            f2)
                                    : displayDeviceConfig3.mBrightnessToBacklightSpline.interpolate(
                                            f2);
                }
                float nitsFromBacklight =
                        LocalDisplayDevice.this
                                .getDisplayDeviceConfig()
                                .getNitsFromBacklight(interpolate);
                float nitsFromBacklight2 =
                        LocalDisplayDevice.this
                                .getDisplayDeviceConfig()
                                .getNitsFromBacklight(interpolate2);
                if (LocalDisplayAdapter.this.mFeatureFlags.mEvenDimmerFlagState.isEnabled()
                        && (displayDeviceConfig = LocalDisplayDevice.this.mDisplayDeviceConfig)
                                != null
                        && displayDeviceConfig.mEvenDimmerBrightnessData != null) {
                    applyColorMatrixBasedDimming(f);
                }
                LocalDisplayDevice.this.mBacklightAdapter.setBacklight(
                        interpolate2,
                        nitsFromBacklight2,
                        interpolate,
                        nitsFromBacklight,
                        BrightnessSynchronizer.brightnessFloatToInt(f),
                        BrightnessSynchronizer.brightnessFloatToInt(f2));
                Trace.traceCounter(
                        131072L,
                        "ScreenBrightness",
                        BrightnessSynchronizer.brightnessFloatToInt(f));
                Trace.traceCounter(
                        131072L,
                        "SdrScreenBrightness",
                        BrightnessSynchronizer.brightnessFloatToInt(f2));
                if (LocalDisplayDevice.this.getDisplayDeviceConfig().mSdrToHdrSpline != null) {
                    float max =
                            (nitsFromBacklight == -1.0f || nitsFromBacklight2 == -1.0f)
                                    ? Float.NaN
                                    : Math.max(1.0f, nitsFromBacklight / nitsFromBacklight2);
                    if (!BrightnessSynchronizer.floatEquals(
                            LocalDisplayDevice.this.mCurrentHdrSdrRatio, max)) {
                        synchronized (LocalDisplayAdapter.this.mSyncRoot) {
                            LocalDisplayDevice localDisplayDevice2 = LocalDisplayDevice.this;
                            localDisplayDevice2.mCurrentHdrSdrRatio = max;
                            localDisplayDevice2.updateDeviceInfoLocked();
                        }
                    }
                }
            }

            public final void setDisplayState(int i) {
                int i2;
                long j;
                String str;
                int i3;
                long j2;
                boolean isEnabled =
                        LocalDisplayAdapter.this.mFeatureFlags.mDisplayOffloadFlagState.isEnabled();
                if (isEnabled) {
                    if (this.val$displayOffloadSession != null
                            && !DisplayManagerInternal.DisplayOffloadSession
                                    .isSupportedOffloadState(i)) {
                        DisplayOffloadSessionImpl displayOffloadSessionImpl =
                                this.val$displayOffloadSession;
                        if (displayOffloadSessionImpl.mDisplayOffloader != null
                                && displayOffloadSessionImpl.mIsActive) {
                            Trace.traceBegin(131072L, "DisplayOffloader#stopOffload");
                            try {
                                displayOffloadSessionImpl.mDisplayOffloader.stopOffload();
                                displayOffloadSessionImpl.mIsActive = false;
                                if (DisplayOffloadSessionImpl.DEBUG) {
                                    android.util.Slog.i("DisplayOffloadSessionImpl", "stopOffload");
                                }
                            } finally {
                            }
                        }
                    }
                } else if (LocalDisplayDevice.this.mSidekickActive) {
                    Trace.traceBegin(131072L, "SidekickInternal#endDisplayControl");
                    try {
                        LocalDisplayDevice.this.mSidekickInternal.endDisplayControl();
                        Trace.traceEnd(131072L);
                        LocalDisplayDevice.this.mSidekickActive = false;
                    } finally {
                    }
                }
                int i4 = LocalDisplayAdapter.$r8$clinit;
                if (i != 1) {
                    i2 = 4;
                    if (i != 6) {
                        if (i != 3) {
                            if (i == 4 && !PowerManagerUtil.SEC_FEATURE_AOD_LOOK_CHARGING_UI) {
                                i2 = 3;
                            }
                            i2 = 2;
                        } else {
                            if (!PowerManagerUtil.SEC_FEATURE_AOD_LOOK_CHARGING_UI) {
                                i2 = 1;
                            }
                            i2 = 2;
                        }
                    }
                } else {
                    i2 = 0;
                }
                Trace.traceBegin(
                        131072L,
                        "setDisplayState(id="
                                + this.val$physicalDisplayId
                                + ", state="
                                + Display.stateToString(i)
                                + ")");
                try {
                    StringBuilder sb = new StringBuilder("display_state - ");
                    sb.append(LocalDisplayDevice.this.mDisplayStateCount);
                    sb.append(" : ");
                    sb.append(Display.stateToString(LocalDisplayDevice.this.mRequestedState));
                    sb.append(" -> ");
                    sb.append(Display.stateToString(i));
                    sb.append(" (");
                    int i5 = this.val$displayType;
                    int i6 = PowerManagerUtil.AUTO_BRIGHTNESS_TYPE;
                    sb.append(i5 != -1 ? i5 != 1 ? i5 != 2 ? null : "SUB" : "MAIN" : "EXTERNAL");
                    if (this.val$stateOverrideChanged) {
                        try {
                            str = ",L:" + this.val$stateOverride;
                        } catch (Throwable th) {
                            th = th;
                            j = 131072;
                            Trace.traceEnd(j);
                            throw th;
                        }
                    } else {
                        str = "";
                    }
                    sb.append(str);
                    sb.append(")");
                    Slog.wk("LocalDisplayAdapter", sb.toString());
                    long currentTimeMillis = System.currentTimeMillis();
                    if (i == 2) {
                        PowerManagerUtil.ScreenOnProfiler screenOnProfiler =
                                PowerManagerUtil.sCurrentScreenOnProfiler;
                        int i7 = LocalDisplayDevice.this.mRequestedState;
                        screenOnProfiler.getClass();
                        screenOnProfiler.mDisplayStartTime = SystemClock.uptimeMillis();
                        if (PowerManagerUtil.ScreenOnProfiler.CHECK_FRAME) {
                            PowerManagerUtil.ScreenOnProfiler.mFramePrevTime =
                                    PowerManagerUtil.ScreenOnProfiler.getFrameTimeFromDriver();
                        }
                        if (i7 != 1) {
                            PowerManagerUtil.ScreenOnProfiler.mFramePass = true;
                        }
                    } else if (i == 1) {
                        PowerManagerUtil.ScreenOffProfiler screenOffProfiler =
                                PowerManagerUtil.sCurrentScreenOffProfiler;
                        screenOffProfiler.getClass();
                        screenOffProfiler.mDisplayStartTime = SystemClock.uptimeMillis();
                    }
                    SurfaceControlProxy surfaceControlProxy =
                            LocalDisplayAdapter.this.mSurfaceControlProxy;
                    IBinder iBinder = this.val$token;
                    surfaceControlProxy.getClass();
                    SurfaceControl.setDisplayPowerMode(iBinder, i2);
                    if (i == 2) {
                        PowerManagerUtil.ScreenOnProfiler screenOnProfiler2 =
                                PowerManagerUtil.sCurrentScreenOnProfiler;
                        screenOnProfiler2.getClass();
                        long uptimeMillis = SystemClock.uptimeMillis();
                        i3 = i2;
                        if (screenOnProfiler2.mDisplayStartTime == 0) {
                            screenOnProfiler2.mDisplayStartTime =
                                    screenOnProfiler2.mWakeUpStartTime;
                        }
                        screenOnProfiler2.mDisplayDuration =
                                (int) (uptimeMillis - screenOnProfiler2.mDisplayStartTime);
                        screenOnProfiler2.noteFrameStart(this.val$displayType);
                    } else {
                        i3 = i2;
                        if (i == 1) {
                            PowerManagerUtil.sCurrentScreenOffProfiler.noteDisplayEnd();
                        }
                    }
                    StringBuilder sb2 = new StringBuilder("display_state - ");
                    sb2.append(LocalDisplayDevice.this.mDisplayStateCount);
                    sb2.append(" : ");
                    sb2.append(Display.stateToString(i));
                    sb2.append(" (");
                    int i8 = this.val$displayType;
                    sb2.append(i8 != -1 ? i8 != 1 ? i8 != 2 ? null : "SUB" : "MAIN" : "EXTERNAL");
                    sb2.append(")");
                    Slog.wk(
                            "LocalDisplayAdapter",
                            sb2.toString()
                                    + " took "
                                    + (System.currentTimeMillis() - currentTimeMillis)
                                    + " ms");
                    LocalDisplayDevice localDisplayDevice = LocalDisplayDevice.this;
                    int i9 = localDisplayDevice.mDisplayStateCount + 1;
                    localDisplayDevice.mDisplayStateCount = i9;
                    if (i9 > 10000) {
                        localDisplayDevice.mDisplayStateCount = 0;
                    }
                    localDisplayDevice.mRequestedState = i;
                    int i10 = i3;
                    Trace.traceEnd(131072L);
                    synchronized (LocalDisplayAdapter.this.mSyncRoot) {
                        try {
                            LocalDisplayDevice localDisplayDevice2 = LocalDisplayDevice.this;
                            localDisplayDevice2.mCommittedState = i;
                            if (CoreRune.FW_VRR_PERFORMANCE
                                    && !localDisplayDevice2.mStateChangeCallbacks.isEmpty()) {
                                Iterator it =
                                        LocalDisplayDevice.this.mStateChangeCallbacks.iterator();
                                while (it.hasNext()) {
                                    ((Runnable) it.next()).run();
                                }
                                LocalDisplayDevice.this.mStateChangeCallbacks.clear();
                            }
                            LocalDisplayDevice.this.updateDeviceInfoLocked();
                        } finally {
                        }
                    }
                    if (!isEnabled) {
                        if (!Display.isSuspendedState(i) || i == 1) {
                            return;
                        }
                        LocalDisplayDevice localDisplayDevice3 = LocalDisplayDevice.this;
                        if (localDisplayDevice3.mSidekickInternal == null
                                || localDisplayDevice3.mSidekickActive) {
                            return;
                        }
                        try {
                            LocalDisplayDevice localDisplayDevice4 = LocalDisplayDevice.this;
                            localDisplayDevice4.mSidekickActive =
                                    localDisplayDevice4.mSidekickInternal.startDisplayControl(i);
                            Trace.traceEnd(j2);
                            return;
                        } finally {
                        }
                    }
                    if (this.val$displayOffloadSession == null
                            || !DisplayManagerInternal.DisplayOffloadSession
                                    .isSupportedOffloadState(i)) {
                        return;
                    }
                    DisplayOffloadSessionImpl displayOffloadSessionImpl2 =
                            this.val$displayOffloadSession;
                    if (displayOffloadSessionImpl2.mDisplayOffloader == null
                            || displayOffloadSessionImpl2.mIsActive) {
                        return;
                    }
                    try {
                        displayOffloadSessionImpl2.mIsActive =
                                displayOffloadSessionImpl2.mDisplayOffloader.startOffload();
                        if (DisplayOffloadSessionImpl.DEBUG) {
                            android.util.Slog.d(
                                    "DisplayOffloadSessionImpl",
                                    "startOffload = " + displayOffloadSessionImpl2.mIsActive);
                        }
                        Trace.traceEnd(131072L);
                    } finally {
                    }
                } catch (Throwable th2) {
                    th = th2;
                    j = 131072;
                }
            }
        }

        /* renamed from: -$$Nest$mnotifyStateChangeFinish, reason: not valid java name */
        public static void m462$$Nest$mnotifyStateChangeFinish(
                LocalDisplayDevice localDisplayDevice, ArrayList arrayList, int i, int i2, int i3) {
            localDisplayDevice.getClass();
            if (arrayList == null || arrayList.isEmpty()) {
                return;
            }
            PowerManagerUtil.StopwatchLogger stopwatchLogger =
                    new PowerManagerUtil.StopwatchLogger();
            stopwatchLogger.mStartTimeMillis = System.currentTimeMillis();
            arrayList.forEach(
                    new LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda2(
                            stopwatchLogger, i, i2, i3, 0));
        }

        public LocalDisplayDevice(
                IBinder iBinder,
                long j,
                SurfaceControl.StaticDisplayInfo staticDisplayInfo,
                SurfaceControl.DynamicDisplayInfo dynamicDisplayInfo,
                SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs,
                boolean z) {
            super(
                    LocalDisplayAdapter.this,
                    iBinder,
                    DeviceIdleController$$ExternalSyntheticOutline0.m(j, "local:"),
                    LocalDisplayAdapter.this.mContext,
                    LocalDisplayAdapter.this.mFeatureFlags.mPixelAnisotropyCorrectionEnabled
                            .isEnabled());
            this.mSupportedModes = new SparseArray();
            this.mSupportedColorModes = new ArrayList();
            this.mDisplayModeSpecs = new DisplayModeDirector.DesiredDisplayModeSpecs();
            this.mState = 0;
            this.mCommittedState = 0;
            this.mBrightnessState = Float.NaN;
            this.mSdrBrightnessState = Float.NaN;
            this.mCurrentHdrSdrRatio = Float.NaN;
            this.mDefaultModeId = -1;
            this.mSystemPreferredModeId = -1;
            this.mUserPreferredModeId = -1;
            this.mActiveSfDisplayModeAtStartId = -1;
            this.mActiveModeId = -1;
            this.mFrameRateOverrides = new DisplayEventReceiver.FrameRateOverride[0];
            this.mStateOverride = 0;
            this.mRequestedState = 0;
            this.mDisplayStateCount = 0;
            this.mLastResolution = 0;
            this.mStateChangeCallbacks = new ArrayList();
            this.mPhysicalDisplayId = j;
            this.mIsFirstDisplay = z;
            updateDisplayPropertiesLocked(
                    staticDisplayInfo, dynamicDisplayInfo, desiredDisplayModeSpecs);
            this.mSidekickInternal =
                    (SidekickInternal) LocalServices.getService(SidekickInternal.class);
            this.mBacklightAdapter =
                    new BacklightAdapter(
                            iBinder, z, LocalDisplayAdapter.this.mSurfaceControlProxy, j);
            this.mActiveSfDisplayModeAtStartId = dynamicDisplayInfo.activeDisplayModeId;
        }

        public static SurfaceControl.DisplayMode getModeById(
                SurfaceControl.DisplayMode[] displayModeArr, int i) {
            for (SurfaceControl.DisplayMode displayMode : displayModeArr) {
                if (displayMode.id == i) {
                    return displayMode;
                }
            }
            Slog.e("LocalDisplayAdapter", "Can't find display mode with id " + i);
            return null;
        }

        public static boolean refreshRatesEquals(List list, float[] fArr) {
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.size() != fArr.length) {
                return false;
            }
            for (int i = 0; i < arrayList.size(); i++) {
                if (Float.floatToIntBits(((Float) arrayList.get(i)).floatValue())
                        != Float.floatToIntBits(fArr[i])) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.android.server.display.DisplayDevice
        public final void applyPendingDisplayDeviceInfoChangesLocked() {
            if (this.mHavePendingChanges) {
                this.mInfo = null;
                this.mHavePendingChanges = false;
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public final void dumpLocked(PrintWriter printWriter) {
            super.dumpLocked(printWriter);
            StringBuilder m =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("mPhysicalDisplayId="),
                            this.mPhysicalDisplayId,
                            printWriter,
                            "mDisplayModeSpecs={");
            m.append(this.mDisplayModeSpecs);
            m.append("}");
            printWriter.println(m.toString());
            StringBuilder m2 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                            .m(
                                                                    new StringBuilder(
                                                                            "mDisplayModeSpecsInvalid="),
                                                                    this.mDisplayModeSpecsInvalid,
                                                                    printWriter,
                                                                    "mActiveModeId="),
                                                    this.mActiveModeId,
                                                    printWriter,
                                                    "mActiveColorMode="),
                                            this.mActiveColorMode,
                                            printWriter,
                                            "mDefaultModeId="),
                                    this.mDefaultModeId,
                                    printWriter,
                                    "mUserPreferredModeId="),
                            this.mUserPreferredModeId,
                            printWriter,
                            "mState=");
            m2.append(Display.stateToString(this.mState));
            printWriter.println(m2.toString());
            printWriter.println("mCommittedState=" + Display.stateToString(this.mCommittedState));
            StringBuilder m3 =
                    KillPolicyManager$$ExternalSyntheticOutline0.m(
                            new StringBuilder("mBrightnessState="),
                            this.mBrightnessState,
                            printWriter,
                            "mBacklightAdapter=");
            m3.append(this.mBacklightAdapter);
            printWriter.println(m3.toString());
            StringBuilder m4 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                    new StringBuilder("mAllmSupported="),
                                                    this.mAllmSupported,
                                                    printWriter,
                                                    "mAllmRequested="),
                                            this.mAllmRequested,
                                            printWriter,
                                            "mGameContentTypeSupported="),
                                    this.mGameContentTypeSupported,
                                    printWriter,
                                    "mGameContentTypeRequested="),
                            this.mGameContentTypeRequested,
                            printWriter,
                            "mStaticDisplayInfo=");
            m4.append(this.mStaticDisplayInfo);
            printWriter.println(m4.toString());
            printWriter.println("mSfDisplayModes=");
            for (SurfaceControl.DisplayMode displayMode : this.mSfDisplayModes) {
                printWriter.println("  " + displayMode);
            }
            printWriter.println("mActiveSfDisplayMode=" + this.mActiveSfDisplayMode);
            printWriter.println("mActiveRenderFrameRate=" + this.mActiveRenderFrameRate);
            printWriter.println("mSupportedModes=");
            for (int i = 0; i < this.mSupportedModes.size(); i++) {
                printWriter.println("  " + this.mSupportedModes.valueAt(i));
            }
            printWriter.println("mSupportedColorModes=" + this.mSupportedColorModes);
            printWriter.println("mDisplayDeviceConfig=" + this.mDisplayDeviceConfig);
        }

        public final int findMatchingModeIdLocked(int i) {
            SurfaceControl.DisplayMode modeById = getModeById(this.mSfDisplayModes, i);
            if (modeById == null) {
                Slog.e("LocalDisplayAdapter", "Invalid display mode ID " + i);
                return -1;
            }
            for (int i2 = 0; i2 < this.mSupportedModes.size(); i2++) {
                DisplayModeRecord displayModeRecord =
                        (DisplayModeRecord) this.mSupportedModes.valueAt(i2);
                if (displayModeRecord.hasMatchingMode(modeById)) {
                    return displayModeRecord.mMode.getModeId();
                }
            }
            return -1;
        }

        public final Display.Mode findMode(int i) {
            for (int i2 = 0; i2 < this.mSupportedModes.size(); i2++) {
                Display.Mode mode = ((DisplayModeRecord) this.mSupportedModes.valueAt(i2)).mMode;
                if (mode.getModeId() == i) {
                    return mode;
                }
            }
            return null;
        }

        public final int findSfDisplayModeIdLocked(int i, int i2) {
            DisplayModeRecord displayModeRecord = (DisplayModeRecord) this.mSupportedModes.get(i);
            if (displayModeRecord == null) {
                return -1;
            }
            int i3 = -1;
            for (SurfaceControl.DisplayMode displayMode : this.mSfDisplayModes) {
                if (displayModeRecord.hasMatchingMode(displayMode)) {
                    if (i3 == -1) {
                        i3 = displayMode.id;
                    }
                    if (displayMode.group == i2) {
                        return displayMode.id;
                    }
                }
            }
            return i3;
        }

        public final int findUserPreferredModeIdLocked(Display.Mode mode) {
            if (mode == null) {
                return -1;
            }
            for (int i = 0; i < this.mSupportedModes.size(); i++) {
                Display.Mode mode2 = ((DisplayModeRecord) this.mSupportedModes.valueAt(i)).mMode;
                if (mode.matches(
                        mode2.getPhysicalWidth(),
                        mode2.getPhysicalHeight(),
                        mode2.getRefreshRate())) {
                    return mode2.getModeId();
                }
            }
            return -1;
        }

        @Override // com.android.server.display.DisplayDevice
        public final Display.Mode getActiveDisplayModeAtStartLocked() {
            return findMode(findMatchingModeIdLocked(this.mActiveSfDisplayModeAtStartId));
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:31:0x0185  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x018e  */
        /* JADX WARN: Removed duplicated region for block: B:46:0x01c7  */
        /* JADX WARN: Type inference failed for: r11v16 */
        /* JADX WARN: Type inference failed for: r11v4 */
        /* JADX WARN: Type inference failed for: r11v5 */
        @Override // com.android.server.display.DisplayDevice
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final com.android.server.display.DisplayDeviceConfig getDisplayDeviceConfig() {
            /*
                Method dump skipped, instructions count: 480
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.getDisplayDeviceConfig():com.android.server.display.DisplayDeviceConfig");
        }

        @Override // com.android.server.display.DisplayDevice
        public final DisplayDeviceInfo getDisplayDeviceInfoLocked() {
            DensityMapping.Entry entry;
            int round;
            if (this.mInfo == null) {
                SurfaceControl.DisplayMode modeById =
                        (CoreRune.FW_VRR_RESOLUTION_POLICY && this.mStaticDisplayInfo.isInternal)
                                ? getModeById(
                                        this.mSfDisplayModes,
                                        findSfDisplayModeIdLocked(
                                                this.mDefaultModeId, this.mDefaultModeGroup))
                                : this.mActiveSfDisplayMode;
                DisplayDeviceInfo displayDeviceInfo = new DisplayDeviceInfo();
                this.mInfo = displayDeviceInfo;
                displayDeviceInfo.width = modeById.width;
                displayDeviceInfo.height = modeById.height;
                displayDeviceInfo.modeId = this.mActiveModeId;
                displayDeviceInfo.renderFrameRate = this.mActiveRenderFrameRate;
                int i = this.mUserPreferredModeId;
                displayDeviceInfo.defaultModeId = i != -1 ? i : this.mDefaultModeId;
                displayDeviceInfo.userPreferredModeId = i;
                SparseArray sparseArray = this.mSupportedModes;
                int size = sparseArray.size();
                Display.Mode[] modeArr = new Display.Mode[size];
                for (int i2 = 0; i2 < size; i2++) {
                    modeArr[i2] = ((DisplayModeRecord) sparseArray.valueAt(i2)).mMode;
                }
                displayDeviceInfo.supportedModes = modeArr;
                DisplayDeviceInfo displayDeviceInfo2 = this.mInfo;
                displayDeviceInfo2.colorMode = this.mActiveColorMode;
                displayDeviceInfo2.allmSupported = this.mAllmSupported;
                displayDeviceInfo2.gameContentTypeSupported = this.mGameContentTypeSupported;
                displayDeviceInfo2.supportedColorModes = new int[this.mSupportedColorModes.size()];
                for (int i3 = 0; i3 < this.mSupportedColorModes.size(); i3++) {
                    this.mInfo.supportedColorModes[i3] =
                            ((Integer) this.mSupportedColorModes.get(i3)).intValue();
                }
                DisplayDeviceInfo displayDeviceInfo3 = this.mInfo;
                displayDeviceInfo3.hdrCapabilities = this.mHdrCapabilities;
                displayDeviceInfo3.appVsyncOffsetNanos = modeById.appVsyncOffsetNanos;
                displayDeviceInfo3.presentationDeadlineNanos = modeById.presentationDeadlineNanos;
                displayDeviceInfo3.state = this.mState;
                displayDeviceInfo3.committedState = this.mCommittedState;
                displayDeviceInfo3.uniqueId = this.mUniqueId;
                DisplayAddress.Physical fromPhysicalDisplayId =
                        DisplayAddress.fromPhysicalDisplayId(this.mPhysicalDisplayId);
                DisplayDeviceInfo displayDeviceInfo4 = this.mInfo;
                displayDeviceInfo4.address = fromPhysicalDisplayId;
                DensityMapping densityMapping = getDisplayDeviceConfig().mDensityMapping;
                if (densityMapping == null) {
                    round = (int) ((this.mStaticDisplayInfo.density * 160.0f) + 0.5d);
                } else {
                    DisplayDeviceInfo displayDeviceInfo5 = this.mInfo;
                    int i4 = displayDeviceInfo5.width;
                    int i5 = displayDeviceInfo5.height;
                    int i6 = (i5 * i5) + (i4 * i4);
                    DensityMapping.Entry entry2 = DensityMapping.Entry.ZEROES;
                    DensityMapping.Entry[] entryArr = densityMapping.mSortedDensityMappingEntries;
                    int length = entryArr.length;
                    int i7 = 0;
                    while (true) {
                        if (i7 >= length) {
                            entry = null;
                            break;
                        }
                        entry = entryArr[i7];
                        if (entry.squaredDiagonal > i6) {
                            break;
                        }
                        i7++;
                        entry2 = entry;
                    }
                    if (entry2.squaredDiagonal == i6) {
                        round = entry2.density;
                    } else {
                        if (entry == null) {
                            entry = entry2;
                            entry2 = DensityMapping.Entry.ZEROES;
                        }
                        double sqrt = Math.sqrt(entry2.squaredDiagonal);
                        double sqrt2 = Math.sqrt(entry.squaredDiagonal);
                        double sqrt3 = Math.sqrt(i6) - sqrt;
                        int i8 = entry.density;
                        round =
                                (int)
                                        Math.round(
                                                ((sqrt3 * (i8 - r7)) / (sqrt2 - sqrt))
                                                        + entry2.density);
                    }
                }
                displayDeviceInfo4.densityDpi = round;
                DisplayDeviceInfo displayDeviceInfo6 = this.mInfo;
                displayDeviceInfo6.xDpi = modeById.xDpi;
                displayDeviceInfo6.yDpi = modeById.yDpi;
                SurfaceControl.StaticDisplayInfo staticDisplayInfo = this.mStaticDisplayInfo;
                displayDeviceInfo6.deviceProductInfo = staticDisplayInfo.deviceProductInfo;
                int i9 = this.mConnectedHdcpLevel;
                if (i9 != 0) {
                    staticDisplayInfo.secure = i9 >= 2;
                }
                if (staticDisplayInfo.secure) {
                    displayDeviceInfo6.flags = 12;
                }
                LocalDisplayAdapter localDisplayAdapter = LocalDisplayAdapter.this;
                if (localDisplayAdapter.mOverlayContext == null) {
                    localDisplayAdapter.mOverlayContext =
                            ActivityThread.currentActivityThread().getSystemUiContext();
                }
                Resources resources = localDisplayAdapter.mOverlayContext.getResources();
                DisplayDeviceInfo displayDeviceInfo7 = this.mInfo;
                int i10 = displayDeviceInfo7.flags;
                displayDeviceInfo7.flags = i10 | 1;
                if (!this.mIsFirstDisplay) {
                    int i11 = 0;
                    if (this.mStaticDisplayInfo.isInternal) {
                        displayDeviceInfo7.flags = 8388801 | i10;
                    } else {
                        if (!resources.getBoolean(R.bool.config_mms_content_disposition_support)) {
                            this.mInfo.flags |= 128;
                        }
                        if (fromPhysicalDisplayId != null) {
                            if (localDisplayAdapter.mOverlayContext == null) {
                                localDisplayAdapter.mOverlayContext =
                                        ActivityThread.currentActivityThread().getSystemUiContext();
                            }
                            int[] intArray =
                                    localDisplayAdapter
                                            .mOverlayContext
                                            .getResources()
                                            .getIntArray(
                                                    R.array.vendor_required_apps_managed_device);
                            if (intArray != null) {
                                int port = fromPhysicalDisplayId.getPort();
                                int length2 = intArray.length;
                                while (true) {
                                    if (i11 >= length2) {
                                        break;
                                    }
                                    if (intArray[i11] == port) {
                                        this.mInfo.flags |= 16;
                                        break;
                                    }
                                    i11++;
                                }
                            }
                        }
                    }
                } else if (resources.getBoolean(R.bool.config_navBarNeedsScrim)
                        || (Build.IS_EMULATOR
                                && SystemProperties.getBoolean(
                                        "ro.boot.emulator.circular", false))) {
                    this.mInfo.flags |= 256;
                }
                if (DisplayCutout.getMaskBuiltInDisplayCutout(resources, this.mInfo.uniqueId)) {
                    this.mInfo.flags |= 2048;
                }
                Display.Mode maximumResolutionDisplayMode =
                        DisplayUtils.getMaximumResolutionDisplayMode(this.mInfo.supportedModes);
                int physicalWidth =
                        maximumResolutionDisplayMode == null
                                ? this.mInfo.width
                                : maximumResolutionDisplayMode.getPhysicalWidth();
                int physicalHeight =
                        maximumResolutionDisplayMode == null
                                ? this.mInfo.height
                                : maximumResolutionDisplayMode.getPhysicalHeight();
                if (this.mStaticDisplayInfo.isInternal) {
                    DisplayDeviceInfo displayDeviceInfo8 = this.mInfo;
                    int i12 = physicalWidth;
                    int i13 = physicalHeight;
                    displayDeviceInfo8.displayCutout =
                            DisplayCutout.fromResourcesRectApproximation(
                                    resources,
                                    displayDeviceInfo8.uniqueId,
                                    i12,
                                    i13,
                                    displayDeviceInfo8.width,
                                    displayDeviceInfo8.height,
                                    displayDeviceInfo8.densityDpi,
                                    false);
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    displayMetrics.setTo(resources.getDisplayMetrics());
                    DisplayDeviceInfo displayDeviceInfo9 = this.mInfo;
                    displayMetrics.density = displayDeviceInfo9.densityDpi / 160.0f;
                    displayDeviceInfo9.roundedCorners =
                            RoundedCorners.fromCustomResources(
                                    resources,
                                    displayDeviceInfo9.uniqueId,
                                    i12,
                                    i13,
                                    displayDeviceInfo9.width,
                                    displayDeviceInfo9.height,
                                    displayMetrics);
                }
                DisplayDeviceInfo displayDeviceInfo10 = this.mInfo;
                displayDeviceInfo10.installOrientation = this.mStaticDisplayInfo.installOrientation;
                displayDeviceInfo10.displayShape =
                        DisplayShape.fromResources(
                                resources,
                                displayDeviceInfo10.uniqueId,
                                physicalWidth,
                                physicalHeight,
                                displayDeviceInfo10.width,
                                displayDeviceInfo10.height);
                this.mInfo.name = getDisplayDeviceConfig().mName;
                if (this.mStaticDisplayInfo.isInternal) {
                    DisplayDeviceInfo displayDeviceInfo11 = this.mInfo;
                    displayDeviceInfo11.type = 1;
                    displayDeviceInfo11.touch = 1;
                    displayDeviceInfo11.flags |= 2;
                    if (displayDeviceInfo11.name == null) {
                        displayDeviceInfo11.name = resources.getString(R.string.imTypeCustom);
                    }
                } else {
                    DisplayDeviceInfo displayDeviceInfo12 = this.mInfo;
                    displayDeviceInfo12.type = 2;
                    displayDeviceInfo12.touch = 2;
                    displayDeviceInfo12.flags |= 64;
                    if (displayDeviceInfo12.name == null) {
                        displayDeviceInfo12.name =
                                localDisplayAdapter
                                        .mContext
                                        .getResources()
                                        .getString(R.string.imTypeHome);
                    }
                }
                DisplayDeviceInfo displayDeviceInfo13 = this.mInfo;
                displayDeviceInfo13.frameRateOverrides = this.mFrameRateOverrides;
                displayDeviceInfo13.flags |= 8192;
                displayDeviceInfo13.brightnessMaximum = 1.0f;
                displayDeviceInfo13.brightnessDefault = getDisplayDeviceConfig().mBrightnessDefault;
                this.mInfo.hdrSdrRatio = this.mCurrentHdrSdrRatio;
            }
            return this.mInfo;
        }

        @Override // com.android.server.display.DisplayDevice
        public final Display.Mode getSystemPreferredDisplayModeLocked() {
            return findMode(this.mSystemPreferredModeId);
        }

        @Override // com.android.server.display.DisplayDevice
        public final Display.Mode getUserPreferredDisplayModeLocked() {
            return this.mUserPreferredMode;
        }

        @Override // com.android.server.display.DisplayDevice
        public final boolean hasStableUniqueId() {
            return true;
        }

        @Override // com.android.server.display.DisplayDevice
        public final boolean isFirstDisplay() {
            return this.mIsFirstDisplay;
        }

        public final void onActiveDisplayModeChangedLocked(float f, int i) {
            SurfaceControl.DisplayMode displayMode;
            int i2;
            if (this.mActiveSfDisplayMode.id == i && this.mActiveRenderFrameRate == f) {
                return;
            }
            this.mActiveSfDisplayMode = getModeById(this.mSfDisplayModes, i);
            int findMatchingModeIdLocked = findMatchingModeIdLocked(i);
            this.mActiveModeId = findMatchingModeIdLocked;
            if (findMatchingModeIdLocked == -1) {
                Slog.w(
                        "LocalDisplayAdapter",
                        "In unknown mode after setting allowed modes, activeModeId=" + i);
            } else if (CoreRune.FW_VRR_POLICY) {
                Slog.d(
                        "LocalDisplayAdapter",
                        "updateActiveModeLocked for d="
                                + this.mPhysicalDisplayId
                                + ", mActiveModeId="
                                + this.mActiveModeId
                                + ", mActiveSfDisplayMode="
                                + this.mActiveSfDisplayMode);
            }
            if (CoreRune.FW_VRR_RESOLUTION_POLICY
                    && this.mIsFirstDisplay
                    && (displayMode = this.mActiveSfDisplayMode) != null
                    && this.mLastResolution != (i2 = displayMode.width * displayMode.height)) {
                this.mLastResolution = i2;
                this.mDisplayModeSpecsInvalid = true;
                Slog.d("LocalDisplayAdapter", "Reset modespecs due to resolution change!");
            }
            this.mActiveRenderFrameRate = f;
            updateDeviceInfoLocked();
        }

        @Override // com.android.server.display.DisplayDevice
        public final void onOverlayChangedLocked() {
            updateDeviceInfoLocked();
        }

        @Override // com.android.server.display.DisplayDevice
        public final Runnable requestDisplayStateLocked(
                int i, float f, float f2, DisplayOffloadSessionImpl displayOffloadSessionImpl) {
            return requestDisplayStateLocked(i, f, f2, displayOffloadSessionImpl, 0, null);
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x004c  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x0053  */
        @Override // com.android.server.display.DisplayDevice
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Runnable requestDisplayStateLocked(
                int r18,
                float r19,
                float r20,
                com.android.server.display.DisplayOffloadSessionImpl r21,
                int r22,
                java.util.ArrayList r23) {
            /*
                r17 = this;
                r1 = r17
                r13 = r18
                r4 = r22
                int r0 = r1.mState
                r2 = 0
                r3 = 1
                if (r0 == r13) goto Le
                r5 = r3
                goto Lf
            Le:
                r5 = r2
            Lf:
                float r0 = r1.mBrightnessState
                int r0 = (r0 > r19 ? 1 : (r0 == r19 ? 0 : -1))
                if (r0 != 0) goto L1e
                float r0 = r1.mSdrBrightnessState
                int r0 = (r0 > r20 ? 1 : (r0 == r20 ? 0 : -1))
                if (r0 == 0) goto L1c
                goto L1e
            L1c:
                r9 = r2
                goto L1f
            L1e:
                r9 = r3
            L1f:
                android.os.SystemClock.uptimeMillis()
                int r0 = r1.mStateOverride
                if (r0 == r4) goto L28
                r6 = r3
                goto L29
            L28:
                r6 = r2
            L29:
                if (r5 != 0) goto L32
                if (r9 != 0) goto L32
                if (r6 == 0) goto L30
                goto L32
            L30:
                r0 = 0
                return r0
            L32:
                android.os.IBinder r15 = r1.mDisplayToken
                int r12 = r1.mState
                com.android.server.display.DisplayDeviceInfo r0 = r1.mInfo
                int r2 = r0.type
                if (r2 != r3) goto L48
                int r0 = r0.flags
                r2 = 8388608(0x800000, float:1.17549435E-38)
                r0 = r0 & r2
                if (r0 == 0) goto L46
                r0 = 2
            L44:
                r7 = r0
                goto L4a
            L46:
                r7 = r3
                goto L4a
            L48:
                r0 = -1
                goto L44
            L4a:
                if (r5 == 0) goto L51
                r1.mState = r13
                r17.updateDeviceInfoLocked()
            L51:
                if (r6 == 0) goto L55
                r1.mStateOverride = r4
            L55:
                com.android.server.display.LocalDisplayAdapter$LocalDisplayDevice$1 r16 = new com.android.server.display.LocalDisplayAdapter$LocalDisplayDevice$1
                long r10 = r1.mPhysicalDisplayId
                r0 = r16
                r1 = r17
                r2 = r5
                r3 = r6
                r4 = r22
                r5 = r23
                r6 = r7
                r7 = r10
                r10 = r19
                r11 = r20
                r13 = r18
                r14 = r21
                r0.<init>(r2, r3, r4, r5, r6, r7, r9, r10, r11, r12, r13, r14, r15)
                return r16
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.requestDisplayStateLocked(int,"
                        + " float, float, com.android.server.display.DisplayOffloadSessionImpl,"
                        + " int, java.util.ArrayList):java.lang.Runnable");
        }

        @Override // com.android.server.display.DisplayDevice
        public final void setAutoLowLatencyModeLocked(boolean z) {
            if (this.mAllmRequested == z) {
                return;
            }
            this.mAllmRequested = z;
            if (!this.mAllmSupported) {
                Slog.d(
                        "LocalDisplayAdapter",
                        "Unable to set ALLM because the connected display does not support ALLM.");
                return;
            }
            SurfaceControlProxy surfaceControlProxy = LocalDisplayAdapter.this.mSurfaceControlProxy;
            IBinder iBinder = this.mDisplayToken;
            surfaceControlProxy.getClass();
            SurfaceControl.setAutoLowLatencyMode(iBinder, z);
        }

        @Override // com.android.server.display.DisplayDevice
        public final void setDesiredDisplayModeSpecsLocked(
                DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            int i = desiredDisplayModeSpecs.baseModeId;
            if (i == 0) {
                return;
            }
            int findSfDisplayModeIdLocked = findSfDisplayModeIdLocked(i, this.mDefaultModeGroup);
            if (findSfDisplayModeIdLocked < 0) {
                Slog.w(
                        "LocalDisplayAdapter",
                        "Ignoring request for invalid base mode id "
                                + desiredDisplayModeSpecs.baseModeId);
                if (getDisplayDeviceInfoLocked().type == 1) {
                    return;
                }
                updateDeviceInfoLocked();
                return;
            }
            boolean z = this.mDisplayModeSpecsInvalid;
            DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs2 =
                    this.mDisplayModeSpecs;
            if (z || !desiredDisplayModeSpecs.equals(desiredDisplayModeSpecs2)) {
                this.mDisplayModeSpecsInvalid = false;
                desiredDisplayModeSpecs2.getClass();
                desiredDisplayModeSpecs2.baseModeId = desiredDisplayModeSpecs.baseModeId;
                desiredDisplayModeSpecs2.allowGroupSwitching =
                        desiredDisplayModeSpecs.allowGroupSwitching;
                SurfaceControl.RefreshRateRanges refreshRateRanges =
                        desiredDisplayModeSpecs2.primary;
                SurfaceControl.RefreshRateRange refreshRateRange = refreshRateRanges.physical;
                SurfaceControl.RefreshRateRanges refreshRateRanges2 =
                        desiredDisplayModeSpecs.primary;
                SurfaceControl.RefreshRateRange refreshRateRange2 = refreshRateRanges2.physical;
                refreshRateRange.min = refreshRateRange2.min;
                refreshRateRange.max = refreshRateRange2.max;
                SurfaceControl.RefreshRateRange refreshRateRange3 = refreshRateRanges.render;
                SurfaceControl.RefreshRateRange refreshRateRange4 = refreshRateRanges2.render;
                refreshRateRange3.min = refreshRateRange4.min;
                refreshRateRange3.max = refreshRateRange4.max;
                SurfaceControl.RefreshRateRanges refreshRateRanges3 =
                        desiredDisplayModeSpecs2.appRequest;
                SurfaceControl.RefreshRateRange refreshRateRange5 = refreshRateRanges3.physical;
                SurfaceControl.RefreshRateRanges refreshRateRanges4 =
                        desiredDisplayModeSpecs.appRequest;
                SurfaceControl.RefreshRateRange refreshRateRange6 = refreshRateRanges4.physical;
                refreshRateRange5.min = refreshRateRange6.min;
                refreshRateRange5.max = refreshRateRange6.max;
                SurfaceControl.RefreshRateRange refreshRateRange7 = refreshRateRanges3.render;
                SurfaceControl.RefreshRateRange refreshRateRange8 = refreshRateRanges4.render;
                refreshRateRange7.min = refreshRateRange8.min;
                refreshRateRange7.max = refreshRateRange8.max;
                if (desiredDisplayModeSpecs.mIdleScreenRefreshRateConfig == null) {
                    desiredDisplayModeSpecs2.mIdleScreenRefreshRateConfig = null;
                } else {
                    desiredDisplayModeSpecs2.mIdleScreenRefreshRateConfig =
                            new SurfaceControl.IdleScreenRefreshRateConfig(
                                    desiredDisplayModeSpecs
                                            .mIdleScreenRefreshRateConfig
                                            .timeoutMillis);
                }
                LocalDisplayAdapter.this.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda0(
                                        0),
                                this,
                                this.mDisplayToken,
                                new SurfaceControl.DesiredDisplayModeSpecs(
                                        findSfDisplayModeIdLocked,
                                        desiredDisplayModeSpecs2.allowGroupSwitching,
                                        desiredDisplayModeSpecs2.primary,
                                        desiredDisplayModeSpecs2.appRequest,
                                        desiredDisplayModeSpecs2.mIdleScreenRefreshRateConfig)));
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public final void setGameContentTypeLocked(boolean z) {
            if (this.mGameContentTypeRequested == z) {
                return;
            }
            this.mGameContentTypeRequested = z;
            SurfaceControlProxy surfaceControlProxy = LocalDisplayAdapter.this.mSurfaceControlProxy;
            IBinder iBinder = this.mDisplayToken;
            surfaceControlProxy.getClass();
            SurfaceControl.setGameContentType(iBinder, z);
        }

        @Override // com.android.server.display.DisplayDevice
        public final void setRequestedColorModeLocked(int i) {
            if (this.mActiveColorMode == i) {
                return;
            }
            if (this.mSupportedColorModes.contains(Integer.valueOf(i))) {
                this.mActiveColorMode = i;
                LocalDisplayAdapter.this.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda0(
                                        1),
                                this,
                                this.mDisplayToken,
                                Integer.valueOf(i)));
            } else {
                Slog.w(
                        "LocalDisplayAdapter",
                        "Unable to find color mode " + i + ", ignoring request.");
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public final void setUserPreferredDisplayModeLocked(Display.Mode mode) {
            Display.Mode mode2;
            int i;
            int i2 = this.mUserPreferredModeId;
            if (i2 == -1) {
                i2 = this.mDefaultModeId;
            }
            this.mUserPreferredMode = mode;
            if (mode == null && (i = this.mSystemPreferredModeId) != -1) {
                this.mDefaultModeId = i;
            }
            if (mode != null && (mode.isRefreshRateSet() || mode.isResolutionSet())) {
                int physicalWidth = mode.getPhysicalWidth();
                int physicalHeight = mode.getPhysicalHeight();
                float refreshRate = mode.getRefreshRate();
                int i3 = 0;
                while (true) {
                    if (i3 >= this.mSupportedModes.size()) {
                        mode2 = null;
                        break;
                    }
                    mode2 = ((DisplayModeRecord) this.mSupportedModes.valueAt(i3)).mMode;
                    if (mode2.matchesIfValid(physicalWidth, physicalHeight, refreshRate)) {
                        break;
                    } else {
                        i3++;
                    }
                }
                if (mode2 != null) {
                    this.mUserPreferredMode = mode2;
                }
            }
            int findUserPreferredModeIdLocked =
                    findUserPreferredModeIdLocked(this.mUserPreferredMode);
            this.mUserPreferredModeId = findUserPreferredModeIdLocked;
            if (findUserPreferredModeIdLocked == -1) {
                findUserPreferredModeIdLocked = this.mDefaultModeId;
            }
            if (i2 == findUserPreferredModeIdLocked) {
                return;
            }
            updateDeviceInfoLocked();
            LocalDisplayAdapter localDisplayAdapter = LocalDisplayAdapter.this;
            if (localDisplayAdapter.mIsBootDisplayModeSupported) {
                int i4 = this.mUserPreferredModeId;
                SurfaceControlProxy surfaceControlProxy = localDisplayAdapter.mSurfaceControlProxy;
                if (i4 == -1) {
                    IBinder iBinder = this.mDisplayToken;
                    surfaceControlProxy.getClass();
                    SurfaceControl.clearBootDisplayMode(iBinder);
                } else {
                    int findSfDisplayModeIdLocked =
                            findSfDisplayModeIdLocked(
                                    this.mUserPreferredMode.getModeId(), this.mDefaultModeGroup);
                    IBinder iBinder2 = this.mDisplayToken;
                    surfaceControlProxy.getClass();
                    SurfaceControl.setBootDisplayMode(iBinder2, findSfDisplayModeIdLocked);
                }
            }
        }

        public final void updateDeviceInfoLocked() {
            this.mInfo = null;
            LocalDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 2);
        }

        /* JADX WARN: Removed duplicated region for block: B:120:0x02aa  */
        /* JADX WARN: Removed duplicated region for block: B:126:0x0348  */
        /* JADX WARN: Removed duplicated region for block: B:129:0x0354  */
        /* JADX WARN: Removed duplicated region for block: B:132:0x0360  */
        /* JADX WARN: Removed duplicated region for block: B:135:0x0368  */
        /* JADX WARN: Removed duplicated region for block: B:138:0x0362  */
        /* JADX WARN: Removed duplicated region for block: B:139:0x0356  */
        /* JADX WARN: Removed duplicated region for block: B:140:0x034a  */
        /* JADX WARN: Removed duplicated region for block: B:141:0x02bb  */
        /* JADX WARN: Removed duplicated region for block: B:164:0x02ac  */
        /* JADX WARN: Removed duplicated region for block: B:169:0x01fc A[LOOP:8: B:167:0x01f6->B:169:0x01fc, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:173:0x0212  */
        /* JADX WARN: Removed duplicated region for block: B:176:0x0264  */
        /* JADX WARN: Removed duplicated region for block: B:182:0x027c  */
        /* JADX WARN: Removed duplicated region for block: B:185:0x028c  */
        /* JADX WARN: Removed duplicated region for block: B:193:0x0223  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean updateDisplayPropertiesLocked(
                android.view.SurfaceControl.StaticDisplayInfo r21,
                android.view.SurfaceControl.DynamicDisplayInfo r22,
                android.view.SurfaceControl.DesiredDisplayModeSpecs r23) {
            /*
                Method dump skipped, instructions count: 876
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.updateDisplayPropertiesLocked(android.view.SurfaceControl$StaticDisplayInfo,"
                        + " android.view.SurfaceControl$DynamicDisplayInfo,"
                        + " android.view.SurfaceControl$DesiredDisplayModeSpecs):boolean");
        }
    }

    public final class LocalDisplayEventListener {
        public LocalDisplayEventListener() {}
    }

    public final class ProxyDisplayEventReceiver extends DisplayEventReceiver {
        public final LocalDisplayEventListener mListener;

        public ProxyDisplayEventReceiver(
                Looper looper, LocalDisplayEventListener localDisplayEventListener) {
            super(looper, 0, 3);
            this.mListener = localDisplayEventListener;
        }

        public final void onFrameRateOverridesChanged(
                long j, long j2, DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr) {
            LocalDisplayEventListener localDisplayEventListener = this.mListener;
            synchronized (LocalDisplayAdapter.this.mSyncRoot) {
                try {
                    LocalDisplayDevice localDisplayDevice =
                            (LocalDisplayDevice) LocalDisplayAdapter.this.mDevices.get(j2);
                    if (localDisplayDevice == null) {
                        return;
                    }
                    if (!Arrays.equals(
                            frameRateOverrideArr, localDisplayDevice.mFrameRateOverrides)) {
                        localDisplayDevice.mFrameRateOverrides = frameRateOverrideArr;
                        localDisplayDevice.updateDeviceInfoLocked();
                    }
                } finally {
                }
            }
        }

        public final void onHdcpLevelsChanged(long j, int i, int i2) {
            LocalDisplayEventListener localDisplayEventListener = this.mListener;
            synchronized (LocalDisplayAdapter.this.mSyncRoot) {
                try {
                    LocalDisplayDevice localDisplayDevice =
                            (LocalDisplayDevice) LocalDisplayAdapter.this.mDevices.get(j);
                    if (localDisplayDevice == null) {
                        return;
                    }
                    if (i > i2) {
                        localDisplayDevice.getClass();
                        Slog.w(
                                "LocalDisplayAdapter",
                                DualAppManagerService$$ExternalSyntheticOutline0.m(
                                        i,
                                        i2,
                                        "HDCP connected level: ",
                                        " is larger than max level: ",
                                        ", ignoring request."));
                    } else if (localDisplayDevice.mConnectedHdcpLevel != i) {
                        localDisplayDevice.mConnectedHdcpLevel = i;
                        localDisplayDevice.updateDeviceInfoLocked();
                    }
                } finally {
                }
            }
        }

        public final void onHotplug(long j, long j2, boolean z) {
            LocalDisplayEventListener localDisplayEventListener = this.mListener;
            synchronized (LocalDisplayAdapter.this.mSyncRoot) {
                try {
                    if (z) {
                        LocalDisplayAdapter.this.tryConnectDisplayLocked(j2);
                    } else {
                        LocalDisplayAdapter localDisplayAdapter = LocalDisplayAdapter.this;
                        LocalDisplayDevice localDisplayDevice =
                                (LocalDisplayDevice) localDisplayAdapter.mDevices.get(j2);
                        if (localDisplayDevice != null) {
                            localDisplayAdapter.mDevices.remove(j2);
                            localDisplayAdapter.sendDisplayDeviceEventLocked(localDisplayDevice, 3);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onHotplugConnectionError(long j, int i) {
            LocalDisplayAdapter.this.mDisplayNotificationManager.onHotplugConnectionError();
        }

        public final void onModeChanged(long j, long j2, int i, long j3) {
            LocalDisplayEventListener localDisplayEventListener = this.mListener;
            synchronized (LocalDisplayAdapter.this.mSyncRoot) {
                try {
                    LocalDisplayDevice localDisplayDevice =
                            (LocalDisplayDevice) LocalDisplayAdapter.this.mDevices.get(j2);
                    if (localDisplayDevice == null) {
                        return;
                    }
                    localDisplayDevice.onActiveDisplayModeChangedLocked(1.0E9f / j3, i);
                } finally {
                }
            }
        }
    }

    public class SurfaceControlProxy {}

    public LocalDisplayAdapter(
            DisplayManagerService.SyncRoot syncRoot,
            Context context,
            Handler handler,
            DisplayAdapter.Listener listener,
            DisplayManagerFlags displayManagerFlags,
            DisplayNotificationManager displayNotificationManager,
            Injector injector) {
        super(syncRoot, context, handler, listener, "LocalDisplayAdapter", displayManagerFlags);
        this.mDevices = new LongSparseArray();
        this.mEvenDimmerStrength = -1;
        this.mDisplayNotificationManager = displayNotificationManager;
        this.mInjector = injector;
        injector.getClass();
        this.mSurfaceControlProxy = new SurfaceControlProxy();
        Trace.traceBegin(32L, "getBootDisplayModeSupport");
        try {
            boolean bootDisplayModeSupport = SurfaceControl.getBootDisplayModeSupport();
            Trace.traceEnd(32L);
            this.mIsBootDisplayModeSupported = bootDisplayModeSupport;
        } catch (Throwable th) {
            Trace.traceEnd(32L);
            throw th;
        }
    }

    public final void registerLocked() {
        Looper looper = this.mHandler.getLooper();
        LocalDisplayEventListener localDisplayEventListener = new LocalDisplayEventListener();
        Injector injector = this.mInjector;
        injector.getClass();
        injector.mReceiver = new ProxyDisplayEventReceiver(looper, localDisplayEventListener);
        this.mSurfaceControlProxy.getClass();
        for (long j : DisplayControl.getPhysicalDisplayIds()) {
            tryConnectDisplayLocked(j);
        }
    }

    public final void tryConnectDisplayLocked(long j) {
        SurfaceControlProxy surfaceControlProxy = this.mSurfaceControlProxy;
        surfaceControlProxy.getClass();
        IBinder physicalDisplayToken = DisplayControl.getPhysicalDisplayToken(j);
        if (physicalDisplayToken != null) {
            surfaceControlProxy.getClass();
            SurfaceControl.StaticDisplayInfo staticDisplayInfo =
                    SurfaceControl.getStaticDisplayInfo(j);
            if (staticDisplayInfo == null) {
                Slog.w("LocalDisplayAdapter", "No valid static info found for display device " + j);
                return;
            }
            surfaceControlProxy.getClass();
            SurfaceControl.DynamicDisplayInfo dynamicDisplayInfo =
                    SurfaceControl.getDynamicDisplayInfo(j);
            if (dynamicDisplayInfo == null) {
                Slog.w(
                        "LocalDisplayAdapter",
                        "No valid dynamic info found for display device " + j);
                return;
            }
            if (dynamicDisplayInfo.supportedDisplayModes == null) {
                Slog.w("LocalDisplayAdapter", "No valid modes found for display device " + j);
                return;
            }
            if (dynamicDisplayInfo.activeDisplayModeId < 0) {
                Slog.w("LocalDisplayAdapter", "No valid active mode found for display device " + j);
                return;
            }
            if (dynamicDisplayInfo.activeColorMode < 0) {
                Slog.w("LocalDisplayAdapter", "No valid active color mode for display device " + j);
                dynamicDisplayInfo.activeColorMode = -1;
            }
            surfaceControlProxy.getClass();
            SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs =
                    SurfaceControl.getDesiredDisplayModeSpecs(physicalDisplayToken);
            if (desiredDisplayModeSpecs == null) {
                Slog.w(
                        "LocalDisplayAdapter",
                        "Desired display mode specs from SurfaceFlinger are null");
                return;
            }
            LocalDisplayDevice localDisplayDevice = (LocalDisplayDevice) this.mDevices.get(j);
            if (localDisplayDevice != null) {
                if (localDisplayDevice.updateDisplayPropertiesLocked(
                        staticDisplayInfo, dynamicDisplayInfo, desiredDisplayModeSpecs)) {
                    sendDisplayDeviceEventLocked(localDisplayDevice, 2);
                    return;
                }
                return;
            }
            int i = 0;
            boolean z = this.mDevices.size() == 0;
            if (CoreRune.FW_VRR_RESOLUTION_POLICY && staticDisplayInfo.isInternal) {
                SurfaceControl.DisplayMode[] displayModeArr =
                        dynamicDisplayInfo.supportedDisplayModes;
                int length = displayModeArr.length;
                int i2 = 0;
                int i3 = 0;
                float f = 0.0f;
                int i4 = 0;
                while (i < length) {
                    SurfaceControl.DisplayMode displayMode = displayModeArr[i];
                    int i5 = length;
                    int i6 = displayMode.width;
                    int i7 = displayMode.height;
                    int i8 = i6 * i7;
                    int i9 = i2 * i3;
                    if (i8 >= i9) {
                        float f2 = displayMode.peakRefreshRate;
                        if (f2 >= 59.99f && (i8 > i9 || f2 < f)) {
                            i4 = Arrays.asList(displayModeArr).indexOf(displayMode);
                            f = f2;
                            i2 = i6;
                            i3 = i7;
                        }
                    }
                    i++;
                    length = i5;
                }
                dynamicDisplayInfo.activeDisplayModeId = i4;
            }
            LocalDisplayDevice localDisplayDevice2 =
                    new LocalDisplayDevice(
                            physicalDisplayToken,
                            j,
                            staticDisplayInfo,
                            dynamicDisplayInfo,
                            desiredDisplayModeSpecs,
                            z);
            this.mDevices.put(j, localDisplayDevice2);
            sendDisplayDeviceEventLocked(localDisplayDevice2, 1);
        }
    }
}
