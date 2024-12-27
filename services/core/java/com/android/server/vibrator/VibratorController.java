package com.android.server.vibrator;

import android.os.IVibratorStateListener;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.VibratorInfo;
import android.os.vibrator.PrebakedSegment;
import android.os.vibrator.PrimitiveSegment;
import android.os.vibrator.RampSegment;
import android.util.IndentingPrintWriter;
import android.util.Slog;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticOutline0;

import libcore.util.NativeAllocationRegistry;

import com.samsung.android.server.vibrator.VibratorHelper;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class VibratorController {
    public volatile float mCurrentAmplitude;
    public volatile boolean mIsUnderExternalControl;
    public volatile boolean mIsVibrating;
    public final NativeWrapper mNativeWrapper;
    public final boolean mSupportEnhancedSamsungHapticPattern;
    public final boolean mSupportFrequencyControl;
    public final boolean mSupportHapticEngine;
    public final boolean mSupportIndexWideBand;
    public final boolean mSupportIntensityControl;
    public final boolean mSupportPrebakedHapticPattern;
    public final int mVibratorGroup;
    public volatile VibratorInfo mVibratorInfo;
    public volatile boolean mVibratorInfoLoadSuccessful;
    private int mVibratorType;
    public final Object mLock = new Object();
    public final RemoteCallbackList mVibratorStateListeners = new RemoteCallbackList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class NativeWrapper {
        public long mNativePtr = 0;

        private static native void alwaysOnDisable(long j, long j2);

        private static native void alwaysOnEnable(long j, long j2, long j3, long j4);

        private static native int[] getAmplitudeList(long j, int i);

        private static native boolean getInfo(long j, VibratorInfo.Builder builder);

        private static native long getNativeFinalizer();

        private static native int getNumberOfPrebakedHapticPattern(long j);

        private static native boolean hasFeature(long j, String str);

        private static native boolean isAvailable(long j);

        private static native long nativeInit(
                int i, OnVibrationCompleteListener onVibrationCompleteListener);

        private static native void off(long j);

        private static native long on(long j, long j2, long j3);

        private static native void performCommonInputff(long j, int[] iArr, boolean z, int i);

        private static native long performComposedEffect(
                long j, PrimitiveSegment[] primitiveSegmentArr, long j2);

        private static native long performEffect(long j, long j2, long j3, long j4);

        private static native void performHapticEngine(long j, int[] iArr, int i);

        private static native void performPrebakedHapticPattern(
                long j, long j2, long j3, boolean z);

        private static native long performPwleEffect(
                long j, RampSegment[] rampSegmentArr, int i, long j2);

        private static native long sehPerformEffect(
                long j, long j2, long j3, Vibration vibration, boolean z);

        private static native void setAmplitude(long j, float f);

        private static native void setExternalControl(long j, boolean z);

        private static native void setFoldState(long j, boolean z);

        private static native void setForceTouchAmplitude(long j, long j2);

        private static native void setFrequencyType(long j, long j2);

        private static native void setIntensity(long j, long j2);

        private static native void setTentState(long j, boolean z);

        private static native boolean supportIntensityControl(long j);

        private static native boolean supportsEnhancedSamsungHapticPattern(long j);

        private static native boolean supportsFoldState(long j);

        private static native boolean supportsFrequencyControl(long j);

        private static native boolean supportsHapticEngine(long j);

        private static native boolean supportsHasFeature(long j);

        private static native boolean supportsPrebakedHapticPattern(long j);

        private static native boolean supportsTentState(long j);

        public final void alwaysOnDisable(long j) {
            alwaysOnDisable(this.mNativePtr, j);
        }

        public final void alwaysOnEnable(long j, long j2, long j3) {
            alwaysOnEnable(this.mNativePtr, j, j2, j3);
        }

        public final long compose(PrimitiveSegment[] primitiveSegmentArr, long j) {
            return performComposedEffect(this.mNativePtr, primitiveSegmentArr, j);
        }

        public final long composePwle(RampSegment[] rampSegmentArr, int i, long j) {
            return performPwleEffect(this.mNativePtr, rampSegmentArr, i, j);
        }

        public final int[] getAmplitudeList(int i) {
            return getAmplitudeList(this.mNativePtr, i);
        }

        public final boolean getInfo(VibratorInfo.Builder builder) {
            return getInfo(this.mNativePtr, builder);
        }

        public final int getNumberOfPrebakedHapticPattern() {
            return getNumberOfPrebakedHapticPattern(this.mNativePtr);
        }

        public final boolean hasFeature(String str) {
            return hasFeature(this.mNativePtr, str);
        }

        public final void init(int i, OnVibrationCompleteListener onVibrationCompleteListener) {
            this.mNativePtr = nativeInit(i, onVibrationCompleteListener);
            long nativeFinalizer = getNativeFinalizer();
            if (nativeFinalizer != 0) {
                NativeAllocationRegistry.createMalloced(
                                VibratorController.class.getClassLoader(), nativeFinalizer)
                        .registerNativeAllocation(this, this.mNativePtr);
            }
        }

        public final boolean isAvailable() {
            return isAvailable(this.mNativePtr);
        }

        public final void off() {
            off(this.mNativePtr);
        }

        public final long on(long j, long j2) {
            return on(this.mNativePtr, j, j2);
        }

        public final long perform(long j, long j2, long j3) {
            return performEffect(this.mNativePtr, j, j2, j3);
        }

        public final void performCommonInputff(int[] iArr, boolean z, int i) {
            performCommonInputff(this.mNativePtr, iArr, z, i);
        }

        public final void performHapticEngine(int[] iArr, int i) {
            performHapticEngine(this.mNativePtr, iArr, i);
        }

        public final void performPrebakedHapticPattern(long j, long j2, boolean z) {
            performPrebakedHapticPattern(this.mNativePtr, j, j2, z);
        }

        public final long sehPerformEffect(long j, long j2, Vibration vibration, boolean z) {
            return sehPerformEffect(this.mNativePtr, j, j2, vibration, z);
        }

        public final void setAmplitude(float f) {
            setAmplitude(this.mNativePtr, f);
        }

        public final void setExternalControl(boolean z) {
            setExternalControl(this.mNativePtr, z);
        }

        public final void setFoldState(boolean z) {
            setFoldState(this.mNativePtr, z);
        }

        public final void setForceTouchAmplitude(long j) {
            setForceTouchAmplitude(this.mNativePtr, j);
        }

        public final void setFrequencyType(long j) {
            setFrequencyType(this.mNativePtr, j);
        }

        public final void setIntensity(long j) {
            setIntensity(this.mNativePtr, j);
        }

        public final void setTentState(boolean z) {
            setTentState(this.mNativePtr, z);
        }

        public final boolean supportIntensityControl() {
            return supportIntensityControl(this.mNativePtr);
        }

        public final boolean supportsEnhancedSamsungHapticPattern() {
            return supportsEnhancedSamsungHapticPattern(this.mNativePtr);
        }

        public final boolean supportsFoldState() {
            return supportsFoldState(this.mNativePtr);
        }

        public final boolean supportsFrequencyControl() {
            return supportsFrequencyControl(this.mNativePtr);
        }

        public final boolean supportsHapticEngine() {
            return supportsHapticEngine(this.mNativePtr);
        }

        public final boolean supportsHasFeature() {
            return supportsHasFeature(this.mNativePtr);
        }

        public final boolean supportsPrebakedHapticPattern() {
            return supportsPrebakedHapticPattern(this.mNativePtr);
        }

        public final boolean supportsTentState() {
            return supportsTentState(this.mNativePtr);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OnVibrationCompleteListener {
        void onComplete(int i, long j);
    }

    public VibratorController(
            int i,
            OnVibrationCompleteListener onVibrationCompleteListener,
            NativeWrapper nativeWrapper) {
        int i2;
        this.mNativeWrapper = nativeWrapper;
        nativeWrapper.init(i, onVibrationCompleteListener);
        VibratorInfo.Builder builder = new VibratorInfo.Builder(i);
        this.mVibratorInfoLoadSuccessful = nativeWrapper.getInfo(builder);
        this.mVibratorInfo = builder.build();
        if (!this.mVibratorInfoLoadSuccessful) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i,
                    "Vibrator controller initialization failed to load some HAL info for vibrator ",
                    "VibratorController");
        }
        this.mVibratorType = 1;
        this.mSupportHapticEngine = nativeWrapper.supportsHapticEngine();
        this.mSupportIntensityControl = nativeWrapper.supportIntensityControl();
        this.mSupportFrequencyControl = nativeWrapper.supportsFrequencyControl();
        this.mSupportPrebakedHapticPattern = nativeWrapper.supportsPrebakedHapticPattern();
        this.mSupportEnhancedSamsungHapticPattern =
                nativeWrapper.supportsEnhancedSamsungHapticPattern();
        boolean hasFeature = nativeWrapper.hasFeature("INDEX_WIDE_BAND");
        this.mSupportIndexWideBand = hasFeature;
        if (this.mSupportPrebakedHapticPattern) {
            if (this.mSupportHapticEngine || hasFeature) {
                this.mVibratorType = 5;
                i2 = 4;
            } else {
                this.mVibratorType = 9;
                i2 = 2;
            }
        } else if (this.mSupportHapticEngine) {
            if (this.mSupportEnhancedSamsungHapticPattern) {
                this.mVibratorType = 7;
                i2 = 3;
            } else {
                this.mVibratorType = 6;
                i2 = 2;
            }
        } else if (this.mSupportIntensityControl) {
            this.mVibratorType = 2;
            i2 = 2;
        } else {
            i2 = 1;
        }
        StringBuilder sb = new StringBuilder("motor group: ");
        VibratorHelper vibratorHelper = VibratorHelper.sInstance;
        sb.append(
                i2 != 1
                        ? i2 != 2
                                ? i2 != 3
                                        ? i2 != 4
                                                ? "SEM_SUPPORTED_VIBRATION_NONE"
                                                : "SEM_SUPPORTED_VIBRATION_TYPE_D"
                                        : "SEM_SUPPORTED_VIBRATION_TYPE_C"
                                : "SEM_SUPPORTED_VIBRATION_TYPE_B"
                        : "SEM_SUPPORTED_VIBRATION_TYPE_A");
        sb.append(" (");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                sb, this.mVibratorType, ")", "VibratorController");
        this.mVibratorGroup = i2;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Vibrator (id=" + this.mVibratorInfo.getId() + "):");
        indentingPrintWriter.increaseIndent();
        StringBuilder m =
                DesktopModeService$$ExternalSyntheticOutline0.m(
                        DesktopModeService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("isVibrating = "),
                                this.mIsVibrating,
                                indentingPrintWriter,
                                "isUnderExternalControl = "),
                        this.mIsUnderExternalControl,
                        indentingPrintWriter,
                        "currentAmplitude = ");
        m.append(this.mCurrentAmplitude);
        indentingPrintWriter.println(m.toString());
        StringBuilder m2 =
                DesktopModeService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("vibratorInfoLoadSuccessful = "),
                        this.mVibratorInfoLoadSuccessful,
                        indentingPrintWriter,
                        "vibratorStateListener size = ");
        m2.append(this.mVibratorStateListeners.getRegisteredCallbackCount());
        indentingPrintWriter.println(m2.toString());
        this.mVibratorInfo.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    public final int getMotorType() {
        return this.mVibratorType;
    }

    public final void notifyListenerOnVibrating(final boolean z) {
        if (this.mIsVibrating != z) {
            this.mIsVibrating = z;
            this.mVibratorStateListeners.broadcast(
                    new Consumer() { // from class:
                                     // com.android.server.vibrator.VibratorController$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            VibratorController vibratorController = VibratorController.this;
                            boolean z2 = z;
                            IVibratorStateListener iVibratorStateListener =
                                    (IVibratorStateListener) obj;
                            vibratorController.getClass();
                            try {
                                iVibratorStateListener.onVibrating(z2);
                            } catch (RemoteException | RuntimeException e) {
                                Slog.e(
                                        "VibratorController",
                                        "Vibrator state listener failed to call",
                                        e);
                            }
                        }
                    });
        }
    }

    public final void off() {
        synchronized (this.mLock) {
            this.mNativeWrapper.off();
            this.mCurrentAmplitude = FullScreenMagnificationGestureHandler.MAX_SCALE;
            notifyListenerOnVibrating(false);
        }
    }

    public final long on(long j, long j2) {
        long on;
        synchronized (this.mLock) {
            try {
                on = this.mNativeWrapper.on(j, j2);
                if (on > 0) {
                    this.mCurrentAmplitude = -1.0f;
                    notifyListenerOnVibrating(true);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return on;
    }

    public final void reloadVibratorInfoIfNeeded() {
        if (this.mVibratorInfoLoadSuccessful) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (this.mVibratorInfoLoadSuccessful) {
                    return;
                }
                int id = this.mVibratorInfo.getId();
                VibratorInfo.Builder builder = new VibratorInfo.Builder(id);
                this.mVibratorInfoLoadSuccessful = this.mNativeWrapper.getInfo(builder);
                this.mVibratorInfo = builder.build();
                if (!this.mVibratorInfoLoadSuccessful) {
                    Slog.e("VibratorController", "Failed retry of HAL getInfo for vibrator " + id);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setAmplitude(float f) {
        synchronized (this.mLock) {
            try {
                if (this.mVibratorInfo.hasCapability(4L)) {
                    this.mNativeWrapper.setAmplitude(f);
                }
                if (this.mIsVibrating) {
                    this.mCurrentAmplitude = f;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setExternalControl(boolean z) {
        if (this.mVibratorInfo.hasCapability(8L)) {
            synchronized (this.mLock) {
                this.mIsUnderExternalControl = z;
                this.mNativeWrapper.setExternalControl(z);
            }
        }
    }

    public final String toString() {
        return "VibratorController{mVibratorInfo="
                + this.mVibratorInfo
                + ", mVibratorInfoLoadSuccessful="
                + this.mVibratorInfoLoadSuccessful
                + ", mIsVibrating="
                + this.mIsVibrating
                + ", mCurrentAmplitude="
                + this.mCurrentAmplitude
                + ", mIsUnderExternalControl="
                + this.mIsUnderExternalControl
                + ", mVibratorStateListeners count="
                + this.mVibratorStateListeners.getRegisteredCallbackCount()
                + '}';
    }

    public final void updateAlwaysOn(int i, PrebakedSegment prebakedSegment) {
        if (this.mVibratorInfo.hasCapability(64L)) {
            synchronized (this.mLock) {
                try {
                    if (prebakedSegment == null) {
                        this.mNativeWrapper.alwaysOnDisable(i);
                    } else {
                        this.mNativeWrapper.alwaysOnEnable(
                                i,
                                prebakedSegment.getEffectId(),
                                prebakedSegment.getEffectStrength());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
