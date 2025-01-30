package com.android.server.vibrator;

import android.os.Binder;
import android.os.IVibratorStateListener;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.VibratorInfo;
import android.os.vibrator.PrebakedSegment;
import android.os.vibrator.PrimitiveSegment;
import android.os.vibrator.RampSegment;
import android.util.Slog;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.server.vibrator.VibratorHelper;
import java.util.function.Consumer;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes3.dex */
public final class VibratorController {
  public volatile float mCurrentAmplitude;
  public volatile boolean mIsUnderExternalControl;
  public volatile boolean mIsVibrating;
  public final Object mLock;
  public final NativeWrapper mNativeWrapper;
  public boolean mSupportEnhancedSamsungHapticPattern;
  public boolean mSupportFrequencyControl;
  public boolean mSupportHapticEngine;
  public boolean mSupportIndexWideBand;
  public boolean mSupportIntensityControl;
  public boolean mSupportPrebakedHapticPattern;
  public final int mVibratorGroup;
  public volatile VibratorInfo mVibratorInfo;
  public volatile boolean mVibratorInfoLoadSuccessful;
  public final RemoteCallbackList mVibratorStateListeners;
  private int mVibratorType;

  public interface OnVibrationCompleteListener {
    void onComplete(int i, long j);
  }

  public VibratorController(int i, OnVibrationCompleteListener onVibrationCompleteListener) {
    this(i, onVibrationCompleteListener, new NativeWrapper());
  }

  public VibratorController(
      int i, OnVibrationCompleteListener onVibrationCompleteListener, NativeWrapper nativeWrapper) {
    this.mLock = new Object();
    this.mVibratorStateListeners = new RemoteCallbackList();
    this.mNativeWrapper = nativeWrapper;
    nativeWrapper.init(i, onVibrationCompleteListener);
    VibratorInfo.Builder builder = new VibratorInfo.Builder(i);
    this.mVibratorInfoLoadSuccessful = nativeWrapper.getInfo(builder);
    this.mVibratorInfo = builder.build();
    if (!this.mVibratorInfoLoadSuccessful) {
      Slog.e(
          "VibratorController",
          "Vibrator controller initialization failed to load some HAL info for vibrator " + i);
    }
    this.mVibratorGroup = initVibratorGroup();
  }

  public boolean registerVibratorStateListener(IVibratorStateListener iVibratorStateListener) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        if (this.mVibratorStateListeners.register(iVibratorStateListener)) {
          lambda$notifyListenerOnVibrating$0(iVibratorStateListener, this.mIsVibrating);
          Binder.restoreCallingIdentity(clearCallingIdentity);
          return true;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return false;
      }
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public boolean unregisterVibratorStateListener(IVibratorStateListener iVibratorStateListener) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return this.mVibratorStateListeners.unregister(iVibratorStateListener);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void reloadVibratorInfoIfNeeded() {
    if (this.mVibratorInfoLoadSuccessful) {
      return;
    }
    synchronized (this.mLock) {
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
    }
  }

  public boolean isVibratorInfoLoadSuccessful() {
    return this.mVibratorInfoLoadSuccessful;
  }

  public VibratorInfo getVibratorInfo() {
    return this.mVibratorInfo;
  }

  public boolean isVibrating() {
    return this.mIsVibrating;
  }

  public float getCurrentAmplitude() {
    return this.mCurrentAmplitude;
  }

  public boolean isUnderExternalControl() {
    return this.mIsUnderExternalControl;
  }

  public boolean hasCapability(long j) {
    return this.mVibratorInfo.hasCapability(j);
  }

  public void setExternalControl(boolean z) {
    if (this.mVibratorInfo.hasCapability(8L)) {
      synchronized (this.mLock) {
        this.mIsUnderExternalControl = z;
        this.mNativeWrapper.setExternalControl(z);
      }
    }
  }

  public void updateAlwaysOn(int i, PrebakedSegment prebakedSegment) {
    if (this.mVibratorInfo.hasCapability(64L)) {
      synchronized (this.mLock) {
        if (prebakedSegment == null) {
          this.mNativeWrapper.alwaysOnDisable(i);
        } else {
          this.mNativeWrapper.alwaysOnEnable(
              i, prebakedSegment.getEffectId(), prebakedSegment.getEffectStrength());
        }
      }
    }
  }

  public void setAmplitude(float f) {
    synchronized (this.mLock) {
      if (this.mVibratorInfo.hasCapability(4L)) {
        this.mNativeWrapper.setAmplitude(f);
      }
      if (this.mIsVibrating) {
        this.mCurrentAmplitude = f;
      }
    }
  }

  /* renamed from: on */
  public long m107on(long j, long j2) {
    long m112on;
    synchronized (this.mLock) {
      m112on = this.mNativeWrapper.m112on(j, j2);
      if (m112on > 0) {
        this.mCurrentAmplitude = -1.0f;
        notifyListenerOnVibrating(true);
      }
    }
    return m112on;
  }

  /* renamed from: on */
  public long m108on(PrebakedSegment prebakedSegment, long j) {
    long perform;
    synchronized (this.mLock) {
      perform =
          this.mNativeWrapper.perform(
              prebakedSegment.getEffectId(), prebakedSegment.getEffectStrength(), j);
      if (perform > 0) {
        this.mCurrentAmplitude = -1.0f;
        notifyListenerOnVibrating(true);
      }
    }
    return perform;
  }

  /* renamed from: on */
  public long m109on(PrimitiveSegment[] primitiveSegmentArr, long j) {
    long compose;
    if (!this.mVibratorInfo.hasCapability(32L)) {
      return 0L;
    }
    synchronized (this.mLock) {
      compose = this.mNativeWrapper.compose(primitiveSegmentArr, j);
      if (compose > 0) {
        this.mCurrentAmplitude = -1.0f;
        notifyListenerOnVibrating(true);
      }
    }
    return compose;
  }

  /* renamed from: on */
  public long m110on(RampSegment[] rampSegmentArr, long j) {
    long composePwle;
    if (!this.mVibratorInfo.hasCapability(1024L)) {
      return 0L;
    }
    synchronized (this.mLock) {
      composePwle =
          this.mNativeWrapper.composePwle(
              rampSegmentArr, this.mVibratorInfo.getDefaultBraking(), j);
      if (composePwle > 0) {
        this.mCurrentAmplitude = -1.0f;
        notifyListenerOnVibrating(true);
      }
    }
    return composePwle;
  }

  public void off() {
    synchronized (this.mLock) {
      this.mNativeWrapper.off();
      this.mCurrentAmplitude = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      notifyListenerOnVibrating(false);
    }
  }

  public void reset() {
    setExternalControl(false);
    off();
  }

  public String toString() {
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

  public final void notifyListenerOnVibrating(final boolean z) {
    if (this.mIsVibrating != z) {
      this.mIsVibrating = z;
      this.mVibratorStateListeners.broadcast(
          new Consumer() { // from class:
            // com.android.server.vibrator.VibratorController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              VibratorController.this.lambda$notifyListenerOnVibrating$0(
                  z, (IVibratorStateListener) obj);
            }
          });
    }
  }

  /* renamed from: notifyStateListener, reason: merged with bridge method [inline-methods] */
  public final void lambda$notifyListenerOnVibrating$0(
      IVibratorStateListener iVibratorStateListener, boolean z) {
    try {
      iVibratorStateListener.onVibrating(z);
    } catch (RemoteException | RuntimeException e) {
      Slog.e("VibratorController", "Vibrator state listener failed to call", e);
    }
  }

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

    /* renamed from: on */
    private static native long m111on(long j, long j2, long j3);

    private static native void performCommonInputff(long j, int[] iArr, boolean z, int i);

    private static native long performComposedEffect(
        long j, PrimitiveSegment[] primitiveSegmentArr, long j2);

    private static native long performEffect(long j, long j2, long j3, long j4);

    private static native void performHapticEngine(long j, int[] iArr, int i);

    private static native void performPrebakedHapticPattern(long j, long j2, long j3, boolean z);

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

    public void init(int i, OnVibrationCompleteListener onVibrationCompleteListener) {
      this.mNativePtr = nativeInit(i, onVibrationCompleteListener);
      long nativeFinalizer = getNativeFinalizer();
      if (nativeFinalizer != 0) {
        NativeAllocationRegistry.createMalloced(
                VibratorController.class.getClassLoader(), nativeFinalizer)
            .registerNativeAllocation(this, this.mNativePtr);
      }
    }

    public boolean isAvailable() {
      return isAvailable(this.mNativePtr);
    }

    /* renamed from: on */
    public long m112on(long j, long j2) {
      return m111on(this.mNativePtr, j, j2);
    }

    public void off() {
      off(this.mNativePtr);
    }

    public void setAmplitude(float f) {
      setAmplitude(this.mNativePtr, f);
    }

    public long perform(long j, long j2, long j3) {
      return performEffect(this.mNativePtr, j, j2, j3);
    }

    public long compose(PrimitiveSegment[] primitiveSegmentArr, long j) {
      return performComposedEffect(this.mNativePtr, primitiveSegmentArr, j);
    }

    public long composePwle(RampSegment[] rampSegmentArr, int i, long j) {
      return performPwleEffect(this.mNativePtr, rampSegmentArr, i, j);
    }

    public void setExternalControl(boolean z) {
      setExternalControl(this.mNativePtr, z);
    }

    public void alwaysOnEnable(long j, long j2, long j3) {
      alwaysOnEnable(this.mNativePtr, j, j2, j3);
    }

    public void alwaysOnDisable(long j) {
      alwaysOnDisable(this.mNativePtr, j);
    }

    public boolean getInfo(VibratorInfo.Builder builder) {
      return getInfo(this.mNativePtr, builder);
    }

    public boolean supportsPrebakedHapticPattern() {
      return supportsPrebakedHapticPattern(this.mNativePtr);
    }

    public boolean supportsHapticEngine() {
      return supportsHapticEngine(this.mNativePtr);
    }

    public boolean supportsEnhancedSamsungHapticPattern() {
      return supportsEnhancedSamsungHapticPattern(this.mNativePtr);
    }

    public boolean supportIntensityControl() {
      return supportIntensityControl(this.mNativePtr);
    }

    public void setIntensity(long j) {
      setIntensity(this.mNativePtr, j);
    }

    public boolean supportsFrequencyControl() {
      return supportsFrequencyControl(this.mNativePtr);
    }

    public void setFrequencyType(long j) {
      setFrequencyType(this.mNativePtr, j);
    }

    public void performHapticEngine(int[] iArr, int i) {
      performHapticEngine(this.mNativePtr, iArr, i);
    }

    public void performCommonInputff(int[] iArr, boolean z, int i) {
      performCommonInputff(this.mNativePtr, iArr, z, i);
    }

    public void performPrebakedHapticPattern(long j, long j2, boolean z) {
      performPrebakedHapticPattern(this.mNativePtr, j, j2, z);
    }

    public int getNumberOfPrebakedHapticPattern() {
      return getNumberOfPrebakedHapticPattern(this.mNativePtr);
    }

    public void setForceTouchAmplitude(long j) {
      setForceTouchAmplitude(this.mNativePtr, j);
    }

    public boolean supportsFoldState() {
      return supportsFoldState(this.mNativePtr);
    }

    public void setFoldState(boolean z) {
      setFoldState(this.mNativePtr, z);
    }

    public boolean supportsTentState() {
      return supportsTentState(this.mNativePtr);
    }

    public void setTentState(boolean z) {
      setTentState(this.mNativePtr, z);
    }

    public int[] getAmplitudeList(int i) {
      return getAmplitudeList(this.mNativePtr, i);
    }

    public long sehPerformEffect(long j, long j2, Vibration vibration, boolean z) {
      return sehPerformEffect(this.mNativePtr, j, j2, vibration, z);
    }

    public boolean supportsHasFeature() {
      return supportsHasFeature(this.mNativePtr);
    }

    public boolean hasFeature(String str) {
      return hasFeature(this.mNativePtr, str);
    }
  }

  public final int initVibratorGroup() {
    int i = 1;
    this.mVibratorType = 1;
    this.mSupportHapticEngine = supportsHapticEngine();
    this.mSupportIntensityControl = supportIntensityControl();
    this.mSupportFrequencyControl = supportsFrequencyControl();
    this.mSupportPrebakedHapticPattern = supportsPrebakedHapticPattern();
    this.mSupportEnhancedSamsungHapticPattern = supportsEnhancedSamsungHapticPattern();
    boolean hasFeature = hasFeature("INDEX_WIDE_BAND");
    this.mSupportIndexWideBand = hasFeature;
    if (this.mSupportPrebakedHapticPattern) {
      if (this.mSupportHapticEngine || hasFeature) {
        this.mVibratorType = 5;
        i = 4;
      } else {
        this.mVibratorType = 9;
        i = 2;
      }
    } else if (this.mSupportHapticEngine) {
      if (this.mSupportEnhancedSamsungHapticPattern) {
        this.mVibratorType = 7;
        i = 3;
      } else {
        this.mVibratorType = 6;
        i = 2;
      }
    } else if (this.mSupportIntensityControl) {
      this.mVibratorType = 2;
      i = 2;
    }
    Slog.d(
        "VibratorController",
        "motor group: "
            + VibratorHelper.getVibrationTypeToString(i)
            + " ("
            + this.mVibratorType
            + ")");
    return i;
  }

  public int getMotorType() {
    return this.mVibratorType;
  }

  public int getVibratorGroup() {
    return this.mVibratorGroup;
  }

  public boolean isSupportHapticEngine() {
    return this.mSupportHapticEngine;
  }

  public boolean isSupportIntensityControl() {
    return this.mSupportIntensityControl;
  }

  public boolean isSupportFrequencyControl() {
    return this.mSupportFrequencyControl;
  }

  public boolean isSupportPrebakedHapticPattern() {
    return this.mSupportPrebakedHapticPattern;
  }

  public boolean isSupportEnhancedSamsungHapticPattern() {
    return this.mSupportEnhancedSamsungHapticPattern;
  }

  public boolean isSupportIndexWideBand() {
    return this.mSupportIndexWideBand;
  }

  public boolean supportsPrebakedHapticPattern() {
    return this.mNativeWrapper.supportsPrebakedHapticPattern();
  }

  public boolean supportsHapticEngine() {
    return this.mNativeWrapper.supportsHapticEngine();
  }

  public boolean supportsEnhancedSamsungHapticPattern() {
    return this.mNativeWrapper.supportsEnhancedSamsungHapticPattern();
  }

  public boolean supportIntensityControl() {
    return this.mNativeWrapper.supportIntensityControl();
  }

  public void setIntensity(long j) {
    this.mNativeWrapper.setIntensity(j);
  }

  public boolean supportsFrequencyControl() {
    return this.mNativeWrapper.supportsFrequencyControl();
  }

  public void setFrequencyType(long j) {
    this.mNativeWrapper.setFrequencyType(j);
  }

  public void performHapticEngine(int[] iArr, int i) {
    this.mNativeWrapper.performHapticEngine(iArr, i);
    notifyListenerOnVibrating(true);
  }

  public void performPrebakedHapticPattern(long j, long j2, boolean z) {
    this.mNativeWrapper.performPrebakedHapticPattern(j, j2, z);
    notifyListenerOnVibrating(true);
  }

  public void performCommonInputff(int[] iArr, boolean z, int i) {
    this.mNativeWrapper.performCommonInputff(iArr, z, i);
    notifyListenerOnVibrating(true);
  }

  public boolean supportsFoldState() {
    return this.mNativeWrapper.supportsFoldState();
  }

  public void setFoldState(boolean z) {
    this.mNativeWrapper.setFoldState(z);
  }

  public int[] getAmplitudeList(int i) {
    return this.mNativeWrapper.getAmplitudeList(i);
  }

  public boolean hasFeature(String str) {
    return this.mNativeWrapper.hasFeature(str);
  }
}
