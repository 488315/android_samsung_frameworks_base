package com.android.server.display;

import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.IDisplayManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.MathUtils;
import android.util.Slog;
import android.view.SurfaceControlHdrLayerInfoListener;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.display.utils.DebugUtils;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.display.IRefreshRateToken;

import java.util.ArrayDeque;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HighBrightnessModeController {
    public static final boolean DEBUG = DebugUtils.isDebuggable("HighBrightnessModeController");
    public static final boolean FEATURE_SUPPORT_HDR_SOLUTION =
            SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_HW_HDR");
    public static final boolean FEATURE_SUPPORT_PHOTOHDR =
            SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_MMFW_SUPPORT_PHOTOHDR");
    static final float HBM_TRANSITION_POINT_INVALID = Float.POSITIVE_INFINITY;
    public float mAmbientLux;
    public float mBrightness;
    public final float mBrightnessMax;
    public final float mBrightnessMin;
    public final HighBrightnessModeController$Injector$$ExternalSyntheticLambda0 mClock;
    public final Context mContext;
    public int mDisplayStatsId;
    public boolean mForceHbmChangeCallback;
    public final Handler mHandler;
    public final Runnable mHbmChangeCallback;
    public DisplayDeviceConfig.HighBrightnessModeData mHbmData;
    public int mHbmMode;
    public int mHbmStatsState;
    public HdrBrightnessDeviceConfig mHdrBrightnessCfg;
    public final HdrListener mHdrListener;
    public int mHeight;
    public HighBrightnessModeMetadata mHighBrightnessModeMetadata;
    public final Injector mInjector;
    public boolean mIsAutoBrightnessEnabled;
    public boolean mIsAutoBrightnessOffByState;
    public boolean mIsBlockedByLowPowerMode;
    public boolean mIsHdrLayerPresent;
    public boolean mIsInAllowedAmbientRange;
    public boolean mIsTimeAvailable;
    public final int mLogLevel;
    public float mMaxDesiredHdrSdrRatio;
    public final HighBrightnessModeController$$ExternalSyntheticLambda0 mRecalcRunnable;
    public IBinder mRegisteredDisplayToken;
    public final int mSDRDimming;
    public final SettingsObserver mSettingsObserver;
    public final boolean mStaticVRR;
    public boolean mSupportHdrSolution = false;
    public final boolean mSupportPhotoHdr;
    public final IBinder mSurfaceFlinger;
    public final float mThresRatio;
    public int mThrottlingReason;
    public float mUnthrottledBrightness;
    public boolean mUseTimeAllowance;
    public int mWidth;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface HdrBrightnessDeviceConfig {
        float getHdrBrightnessFromSdr(float f, float f2);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class HdrListener extends SurfaceControlHdrLayerInfoListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public boolean mIsBrightnessScaledUp = false;
        public boolean mIsBrightnessScaledUpPrev = false;
        public final IDisplayManager mDisplayManager =
                IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
        public IRefreshRateToken mRefreshRateToken = null;
        public final IBinder mToken = new Binder();

        public HdrListener() {}

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0083, code lost:

           if (r4 >= r10.mThresRatio) goto L22;
        */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0085, code lost:

           r1 = true;
        */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x0092, code lost:

           r19.mIsBrightnessScaledUp = r1;
        */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0096, code lost:

           if (r10.mLogLevel > 0) goto L34;
        */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x009a, code lost:

           if (r1 == r19.mIsBrightnessScaledUpPrev) goto L33;
        */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x009d, code lost:

           r7 = "HighBrightnessModeController";
        */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x00a0, code lost:

           r1 = new java.lang.StringBuilder("brt scaled up: ");
           r1.append(r19.mIsBrightnessScaledUp);
           r1.append(", SDR Dimming: ");
           com.android.server.ServiceKeeper$$ExternalSyntheticOutline0.m(r19.this$0.mSDRDimming, r22, ", HdrLayerSize: ", "x", r1);
           com.android.server.ServiceKeeper$$ExternalSyntheticOutline0.m(r23, r0, "(", "), DisplaySize: ", r1);
           r1.append(r19.this$0.mWidth);
           r1.append("x");
           com.android.server.ServiceKeeper$$ExternalSyntheticOutline0.m(r19.this$0.mHeight, r7, "(", "), deviceRatio: ", r1);
           r1.append(r5);
           r1.append(" maxAvailSize: ");
           r1.append(r2);
           r1.append("x");
           com.android.server.ServiceKeeper$$ExternalSyntheticOutline0.m(r18, r3, "(", "), availRatio: ", r1);
           r1.append(r4);
           r1.append("(");
           r1.append(r19.this$0.mThresRatio);
           r1.append(")");
           r7 = "HighBrightnessModeController";
           android.util.Slog.d(r7, r1.toString());
        */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x0087, code lost:

           r1 = false;
        */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x008f, code lost:

           if (r5 >= r10.mThresRatio) goto L22;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onHdrInfoChanged(
                android.os.IBinder r20,
                final int r21,
                final int r22,
                final int r23,
                int r24,
                final float r25) {
            /*
                Method dump skipped, instructions count: 558
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.display.HighBrightnessModeController.HdrListener.onHdrInfoChanged(android.os.IBinder,"
                        + " int, int, int, int, float):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public final Uri mLowPowerModeSetting;
        public boolean mStarted;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.mLowPowerModeSetting = Settings.Global.getUriFor("low_power");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            updateLowPower();
        }

        public final void updateLowPower() {
            boolean z =
                    Settings.Global.getInt(
                                    HighBrightnessModeController.this.mContext.getContentResolver(),
                                    "low_power",
                                    0)
                            != 0;
            if (z == HighBrightnessModeController.this.mIsBlockedByLowPowerMode) {
                return;
            }
            if (HighBrightnessModeController.DEBUG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        "Settings.Global.LOW_POWER_MODE enabled: ",
                        "HighBrightnessModeController",
                        z);
            }
            HighBrightnessModeController highBrightnessModeController =
                    HighBrightnessModeController.this;
            highBrightnessModeController.mIsBlockedByLowPowerMode = z;
            highBrightnessModeController.updateHbmMode();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x00d6 A[Catch: all -> 0x00bf, TryCatch #3 {all -> 0x00bf, blocks: (B:6:0x00b3, B:9:0x00ba, B:11:0x00d6, B:13:0x00de, B:39:0x00c3, B:36:0x00c7, B:34:0x00cd), top: B:5:0x00b3, inners: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x010c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public HighBrightnessModeController(
            com.android.server.display.HighBrightnessModeController.Injector r12,
            android.os.Handler r13,
            int r14,
            int r15,
            android.os.IBinder r16,
            java.lang.String r17,
            float r18,
            float r19,
            com.android.server.display.DisplayDeviceConfig.HighBrightnessModeData r20,
            com.android.server.display.HighBrightnessModeController.HdrBrightnessDeviceConfig r21,
            java.lang.Runnable r22,
            com.android.server.display.HighBrightnessModeMetadata r23,
            android.content.Context r24) {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.HighBrightnessModeController.<init>(com.android.server.display.HighBrightnessModeController$Injector,"
                    + " android.os.Handler, int, int, android.os.IBinder, java.lang.String, float,"
                    + " float,"
                    + " com.android.server.display.DisplayDeviceConfig$HighBrightnessModeData,"
                    + " com.android.server.display.HighBrightnessModeController$HdrBrightnessDeviceConfig,"
                    + " java.lang.Runnable, com.android.server.display.HighBrightnessModeMetadata,"
                    + " android.content.Context):void");
    }

    public final long calculateRemainingTime(long j) {
        long j2;
        if (!deviceSupportsHbm()) {
            return 0L;
        }
        long j3 = this.mHighBrightnessModeMetadata.mRunningStartTimeMillis;
        if (j3 > 0) {
            if (j3 > j) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                "Start time set to the future. curr: ", j, ", start: ");
                m.append(j3);
                Slog.e("HighBrightnessModeController", m.toString());
                this.mHighBrightnessModeMetadata.mRunningStartTimeMillis = j;
                j3 = j;
            }
            j2 = j - j3;
        } else {
            j2 = 0;
        }
        boolean z = DEBUG;
        if (z) {
            Slog.d(
                    "HighBrightnessModeController",
                    "Time already used after current session: " + j2);
        }
        long j4 = j - this.mHbmData.timeWindowMillis;
        Iterator it = this.mHighBrightnessModeMetadata.mEvents.iterator();
        while (it.hasNext()) {
            HbmEvent hbmEvent = (HbmEvent) it.next();
            if (hbmEvent.mEndTimeMillis < j4) {
                it.remove();
            } else {
                j2 += hbmEvent.mEndTimeMillis - Math.max(hbmEvent.mStartTimeMillis, j4);
            }
        }
        if (z) {
            Slog.d("HighBrightnessModeController", "Time already used after all sessions: " + j2);
        }
        return Math.max(0L, this.mHbmData.timeMaxMillis - j2);
    }

    public final boolean deviceSupportsHbm() {
        return (this.mHbmData == null || this.mHighBrightnessModeMetadata == null) ? false : true;
    }

    public final float getCurrentBrightnessMax() {
        return (!deviceSupportsHbm() || isHbmCurrentlyAllowed())
                ? this.mBrightnessMax
                : this.mHbmData.transitionPoint;
    }

    public final float getHdrBrightnessValue() {
        HdrBrightnessDeviceConfig hdrBrightnessDeviceConfig = this.mHdrBrightnessCfg;
        if (hdrBrightnessDeviceConfig != null) {
            float hdrBrightnessFromSdr =
                    hdrBrightnessDeviceConfig.getHdrBrightnessFromSdr(
                            this.mBrightness, this.mMaxDesiredHdrSdrRatio);
            if (hdrBrightnessFromSdr != -1.0f) {
                return hdrBrightnessFromSdr;
            }
        }
        float currentBrightnessMax = getCurrentBrightnessMax();
        float f = this.mBrightnessMax;
        float f2 = this.mBrightness;
        float f3 = this.mBrightnessMin;
        return MathUtils.map(f3, currentBrightnessMax, f3, f, f2);
    }

    public HdrListener getHdrListener() {
        return this.mHdrListener;
    }

    public final boolean isHbmCurrentlyAllowed() {
        return this.mIsAutoBrightnessEnabled
                && this.mIsTimeAvailable
                && this.mIsInAllowedAmbientRange
                && !this.mIsBlockedByLowPowerMode;
    }

    public boolean isThermalThrottlingActive() {
        float f = this.mUnthrottledBrightness;
        float f2 = this.mHbmData.transitionPoint;
        return f > f2 && this.mBrightness <= f2 && this.mThrottlingReason == 1;
    }

    public final void onBrightnessChanged(int i, float f, float f2) {
        if (deviceSupportsHbm()) {
            this.mBrightness = f;
            this.mUnthrottledBrightness = f2;
            this.mThrottlingReason = i;
            long j = this.mHighBrightnessModeMetadata.mRunningStartTimeMillis;
            boolean z = false;
            boolean z2 = j != -1;
            if (f > this.mHbmData.transitionPoint && !this.mIsHdrLayerPresent) {
                z = true;
            }
            if (z2 != z) {
                getClass();
                long uptimeMillis = SystemClock.uptimeMillis();
                if (z) {
                    this.mHighBrightnessModeMetadata.mRunningStartTimeMillis = uptimeMillis;
                } else {
                    this.mHighBrightnessModeMetadata.mEvents.addFirst(
                            new HbmEvent(j, uptimeMillis));
                    this.mHighBrightnessModeMetadata.mRunningStartTimeMillis = -1L;
                    if (DEBUG) {
                        Slog.d(
                                "HighBrightnessModeController",
                                "New HBM event: "
                                        + this.mHighBrightnessModeMetadata.mEvents.peekFirst());
                    }
                }
            }
            recalculateTimeAllowance();
        }
    }

    public final void recalculateTimeAllowance() {
        long j;
        if (this.mUseTimeAllowance) {
            getClass();
            long uptimeMillis = SystemClock.uptimeMillis();
            long calculateRemainingTime = calculateRemainingTime(uptimeMillis);
            DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData = this.mHbmData;
            boolean z = calculateRemainingTime >= highBrightnessModeData.timeMinMillis;
            boolean z2 =
                    !z
                            && calculateRemainingTime > 0
                            && this.mBrightness > highBrightnessModeData.transitionPoint;
            boolean z3 = z || z2;
            this.mIsTimeAvailable = z3;
            ArrayDeque arrayDeque = this.mHighBrightnessModeMetadata.mEvents;
            if (this.mBrightness > highBrightnessModeData.transitionPoint) {
                j = uptimeMillis + calculateRemainingTime;
            } else if (z3 || arrayDeque.size() <= 0) {
                j = -1;
            } else {
                long j2 = uptimeMillis - this.mHbmData.timeWindowMillis;
                j =
                        (((Math.max(j2, ((HbmEvent) arrayDeque.peekLast()).mStartTimeMillis)
                                                        + this.mHbmData.timeMinMillis)
                                                - j2)
                                        + uptimeMillis)
                                - calculateRemainingTime;
            }
            if (DEBUG) {
                StringBuilder m =
                        FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                                "HBM recalculated.  IsAllowedWithoutRestrictions: ",
                                z,
                                ", isOnlyAllowedToStayOn: ",
                                z2,
                                ", remainingAllowedTime: ");
                m.append(calculateRemainingTime);
                m.append(", isLuxHigh: ");
                m.append(this.mIsInAllowedAmbientRange);
                m.append(", isHBMCurrentlyAllowed: ");
                m.append(isHbmCurrentlyAllowed());
                m.append(", isHdrLayerPresent: ");
                m.append(this.mIsHdrLayerPresent);
                m.append(", mMaxDesiredHdrSdrRatio: ");
                m.append(this.mMaxDesiredHdrSdrRatio);
                m.append(", isAutoBrightnessEnabled: ");
                m.append(this.mIsAutoBrightnessEnabled);
                m.append(", mIsTimeAvailable: ");
                m.append(this.mIsTimeAvailable);
                m.append(", mIsInAllowedAmbientRange: ");
                m.append(this.mIsInAllowedAmbientRange);
                m.append(", mIsBlockedByLowPowerMode: ");
                m.append(this.mIsBlockedByLowPowerMode);
                m.append(", mBrightness: ");
                m.append(this.mBrightness);
                m.append(", mUnthrottledBrightness: ");
                m.append(this.mUnthrottledBrightness);
                m.append(", mThrottlingReason: ");
                m.append(BrightnessInfo.briMaxReasonToString(this.mThrottlingReason));
                m.append(", RunningStartTimeMillis: ");
                m.append(this.mHighBrightnessModeMetadata.mRunningStartTimeMillis);
                m.append(", nextTimeout: ");
                m.append(j != -1 ? j - uptimeMillis : -1L);
                m.append(", events: ");
                m.append(arrayDeque);
                Slog.d("HighBrightnessModeController", m.toString());
            }
            if (j != -1) {
                Handler handler = this.mHandler;
                HighBrightnessModeController$$ExternalSyntheticLambda0
                        highBrightnessModeController$$ExternalSyntheticLambda0 =
                                this.mRecalcRunnable;
                handler.removeCallbacks(highBrightnessModeController$$ExternalSyntheticLambda0);
                handler.postAtTime(highBrightnessModeController$$ExternalSyntheticLambda0, j + 1);
            }
        }
        updateHbmMode();
    }

    public final void resetHbmData(
            int i,
            int i2,
            IBinder iBinder,
            String str,
            DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData,
            HdrBrightnessDeviceConfig hdrBrightnessDeviceConfig) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mHbmData = highBrightnessModeData;
        this.mHdrBrightnessCfg = hdrBrightnessDeviceConfig;
        this.mDisplayStatsId = str.hashCode();
        IBinder iBinder2 = this.mRegisteredDisplayToken;
        if (iBinder2 != null) {
            this.mHdrListener.unregister(iBinder2);
            this.mIsHdrLayerPresent = false;
        }
        SettingsObserver settingsObserver = this.mSettingsObserver;
        HighBrightnessModeController highBrightnessModeController =
                HighBrightnessModeController.this;
        highBrightnessModeController.mIsBlockedByLowPowerMode = false;
        if (settingsObserver.mStarted) {
            highBrightnessModeController
                    .mContext
                    .getContentResolver()
                    .unregisterContentObserver(settingsObserver);
            settingsObserver.mStarted = false;
        }
        if (deviceSupportsHbm()) {
            IBinder iBinder3 = this.mRegisteredDisplayToken;
            if (iBinder3 != iBinder) {
                if (iBinder3 != null) {
                    this.mHdrListener.unregister(iBinder3);
                    this.mIsHdrLayerPresent = false;
                }
                this.mRegisteredDisplayToken = iBinder;
                if (iBinder != null) {
                    this.mHdrListener.register(iBinder);
                }
            }
            DisplayDeviceConfig.HighBrightnessModeData highBrightnessModeData2 = this.mHbmData;
            if (highBrightnessModeData2.timeWindowMillis == 0
                    && highBrightnessModeData2.timeMaxMillis == 0
                    && highBrightnessModeData2.timeMinMillis == 0) {
                this.mUseTimeAllowance = false;
                this.mIsTimeAvailable = true;
            } else {
                this.mUseTimeAllowance = true;
            }
            recalculateTimeAllowance();
            if (this.mHbmData.allowInLowPowerMode) {
                return;
            }
            this.mIsBlockedByLowPowerMode = false;
            if (settingsObserver.mStarted) {
                return;
            }
            HighBrightnessModeController.this
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(
                            settingsObserver.mLowPowerModeSetting, false, settingsObserver, -1);
            settingsObserver.mStarted = true;
            settingsObserver.updateLowPower();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateHbmMode() {
        /*
            r8 = this;
            boolean r0 = r8.deviceSupportsHbm()
            r1 = 1
            r2 = 2
            r3 = 0
            if (r0 != 0) goto Lb
        L9:
            r0 = r3
            goto L18
        Lb:
            boolean r0 = r8.mIsHdrLayerPresent
            if (r0 == 0) goto L11
            r0 = r2
            goto L18
        L11:
            boolean r0 = r8.isHbmCurrentlyAllowed()
            if (r0 == 0) goto L9
            r0 = r1
        L18:
            r4 = 3
            if (r0 != r2) goto L29
            float r5 = r8.getHdrBrightnessValue()
            com.android.server.display.DisplayDeviceConfig$HighBrightnessModeData r6 = r8.mHbmData
            float r6 = r6.transitionPoint
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 <= 0) goto L29
            r5 = r2
            goto L38
        L29:
            if (r0 != r1) goto L37
            float r5 = r8.mBrightness
            com.android.server.display.DisplayDeviceConfig$HighBrightnessModeData r6 = r8.mHbmData
            float r6 = r6.transitionPoint
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 <= 0) goto L37
            r5 = r4
            goto L38
        L37:
            r5 = r1
        L38:
            int r6 = r8.mHbmStatsState
            if (r5 != r6) goto L3e
            goto L95
        L3e:
            if (r6 != r4) goto L42
            r6 = r1
            goto L43
        L42:
            r6 = r3
        L43:
            if (r5 != r4) goto L47
            r7 = r1
            goto L48
        L47:
            r7 = r3
        L48:
            if (r6 == 0) goto L86
            if (r7 != 0) goto L86
            boolean r6 = r8.mIsAutoBrightnessEnabled
            if (r6 != 0) goto L56
            boolean r7 = r8.mIsAutoBrightnessOffByState
            if (r7 == 0) goto L56
            r1 = 6
            goto L87
        L56:
            if (r6 != 0) goto L5a
            r1 = 7
            goto L87
        L5a:
            boolean r6 = r8.mIsInAllowedAmbientRange
            if (r6 != 0) goto L5f
            goto L87
        L5f:
            boolean r1 = r8.mIsTimeAvailable
            if (r1 != 0) goto L65
            r1 = r2
            goto L87
        L65:
            boolean r1 = r8.isThermalThrottlingActive()
            if (r1 == 0) goto L6d
            r1 = r4
            goto L87
        L6d:
            boolean r1 = r8.mIsHdrLayerPresent
            if (r1 == 0) goto L73
            r1 = 4
            goto L87
        L73:
            boolean r1 = r8.mIsBlockedByLowPowerMode
            if (r1 == 0) goto L79
            r1 = 5
            goto L87
        L79:
            float r1 = r8.mBrightness
            com.android.server.display.DisplayDeviceConfig$HighBrightnessModeData r2 = r8.mHbmData
            float r2 = r2.transitionPoint
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 > 0) goto L86
            r1 = 9
            goto L87
        L86:
            r1 = r3
        L87:
            int r2 = r8.mDisplayStatsId
            com.android.server.display.HighBrightnessModeController$Injector r4 = r8.mInjector
            r4.getClass()
            r4 = 416(0x1a0, float:5.83E-43)
            com.android.internal.util.FrameworkStatsLog.write(r4, r2, r5, r1)
            r8.mHbmStatsState = r5
        L95:
            int r1 = r8.mHbmMode
            if (r1 != r0) goto L9d
            boolean r1 = r8.mForceHbmChangeCallback
            if (r1 == 0) goto La6
        L9d:
            r8.mForceHbmChangeCallback = r3
            r8.mHbmMode = r0
            java.lang.Runnable r8 = r8.mHbmChangeCallback
            r8.run()
        La6:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.HighBrightnessModeController.updateHbmMode():void");
    }
}
