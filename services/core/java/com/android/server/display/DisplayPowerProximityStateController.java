package com.android.server.display;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.display.DisplayManagerInternal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.TimeUtils;
import com.android.server.display.utils.SensorUtils;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public final class DisplayPowerProximityStateController {
  static final int MSG_PROXIMITY_SENSOR_DEBOUNCED = 1;
  static final int PROXIMITY_POSITIVE = 1;
  static final int PROXIMITY_SENSOR_POSITIVE_DEBOUNCE_DELAY = 0;
  static final int PROXIMITY_UNKNOWN = -1;
  public Clock mClock;
  public DisplayDeviceConfig mDisplayDeviceConfig;
  public int mDisplayId;
  public final DisplayPowerProximityStateHandler mHandler;
  public boolean mIgnoreProximityUntilChanged;
  public boolean mIsEarsenseProximity;
  public boolean mIsProximitySensorOnFoldingSide;
  public boolean mIsViewTypeCover;
  public final Runnable mNudgeUpdatePowerState;
  public boolean mPendingWaitForNegativeProximityLocked;
  public Sensor mProximitySensor;
  public boolean mProximitySensorEnabled;
  public float mProximityThreshold;
  public boolean mScreenOffBecauseOfProximity;
  public final SensorManager mSensorManager;
  public final String mTag;
  public boolean mWaitingForNegativeProximity;
  public final WakelockController mWakelockController;
  public final Object mLock = new Object();
  public final SensorEventListener mProximitySensorListener =
      new SensorEventListener() { // from class:
                                  // com.android.server.display.DisplayPowerProximityStateController.1
        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {}

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
          if (DisplayPowerProximityStateController.this.mProximitySensorEnabled) {
            long uptimeMillis = DisplayPowerProximityStateController.this.mClock.uptimeMillis();
            boolean z = false;
            float f = sensorEvent.values[0];
            if (f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON
                && f < DisplayPowerProximityStateController.this.mProximityThreshold) {
              z = true;
            }
            Slog.m72d(
                DisplayPowerProximityStateController.this.mTag,
                "[api] onSensorChanged: proximity: " + z);
            DisplayPowerProximityStateController.this.handleProximitySensorEvent(uptimeMillis, z);
          }
        }
      };
  public int mPendingProximity = -1;
  public long mPendingProximityDebounceTime = -1;
  public int mProximity = -1;
  public boolean mSkipRampBecauseOfProximityChangeToNegative = false;
  public int mSensorPositiveDebounceDelay = -1;
  public int mSensorNegativeDebounceDelay = -1;

  interface Clock {
    long uptimeMillis();
  }

  public DisplayPowerProximityStateController(
      WakelockController wakelockController,
      DisplayDeviceConfig displayDeviceConfig,
      Looper looper,
      Runnable runnable,
      int i,
      SensorManager sensorManager,
      Injector injector) {
    this.mClock = (injector == null ? new Injector() : injector).createClock();
    this.mWakelockController = wakelockController;
    this.mHandler = new DisplayPowerProximityStateHandler(looper);
    this.mNudgeUpdatePowerState = runnable;
    this.mDisplayDeviceConfig = displayDeviceConfig;
    this.mDisplayId = i;
    this.mTag = "DisplayPowerProximityStateController[" + this.mDisplayId + "]";
    this.mSensorManager = sensorManager;
    loadProximitySensor();
  }

  public void updatePendingProximityRequestsLocked() {
    synchronized (this.mLock) {
      this.mWaitingForNegativeProximity |= this.mPendingWaitForNegativeProximityLocked;
      this.mPendingWaitForNegativeProximityLocked = false;
      if (this.mIgnoreProximityUntilChanged) {
        this.mWaitingForNegativeProximity = false;
      }
    }
  }

  public void cleanup() {
    setProximitySensorEnabled(false);
  }

  public boolean isProximitySensorAvailable() {
    return this.mProximitySensor != null;
  }

  public boolean setPendingWaitForNegativeProximityLocked(boolean z) {
    synchronized (this.mLock) {
      if (z) {
        if (!this.mPendingWaitForNegativeProximityLocked) {
          this.mPendingWaitForNegativeProximityLocked = true;
          return true;
        }
      }
      return false;
    }
  }

  public void updateProximityState(
      DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, int i) {
    this.mSkipRampBecauseOfProximityChangeToNegative = false;
    this.mSensorPositiveDebounceDelay = displayPowerRequest.proximityPositiveDebounce;
    this.mSensorNegativeDebounceDelay = displayPowerRequest.proximityNegativeDebounce;
    int i2 = displayPowerRequest.coverType;
    this.mIsViewTypeCover = i2 == 8 || i2 == 15 || i2 == 16 || i2 == 17;
    if (this.mProximitySensor != null) {
      if (displayPowerRequest.useProximitySensor
          && isProximitySensorValidState(displayPowerRequest)) {
        setProximitySensorEnabled(true);
        if (!this.mScreenOffBecauseOfProximity
            && this.mProximity == 1
            && !this.mIgnoreProximityUntilChanged) {
          this.mScreenOffBecauseOfProximity = true;
          sendOnProximityPositiveWithWakelock();
        }
      } else if (this.mWaitingForNegativeProximity
          && this.mScreenOffBecauseOfProximity
          && this.mProximity == 1
          && i != 1
          && isProximitySensorValidState(displayPowerRequest)) {
        setProximitySensorEnabled(true);
      } else {
        setProximitySensorEnabled(false);
        this.mWaitingForNegativeProximity = false;
      }
      if (this.mScreenOffBecauseOfProximity) {
        if (this.mProximity != 1 || this.mIgnoreProximityUntilChanged) {
          this.mScreenOffBecauseOfProximity = false;
          this.mSkipRampBecauseOfProximityChangeToNegative = true;
          sendOnProximityNegativeWithWakelock();
          return;
        }
        return;
      }
      return;
    }
    setProximitySensorEnabled(false);
    this.mWaitingForNegativeProximity = false;
    this.mIgnoreProximityUntilChanged = false;
    if (this.mScreenOffBecauseOfProximity) {
      this.mScreenOffBecauseOfProximity = false;
      this.mSkipRampBecauseOfProximityChangeToNegative = true;
      sendOnProximityNegativeWithWakelock();
    }
  }

  public boolean shouldSkipRampBecauseOfProximityChangeToNegative() {
    return this.mSkipRampBecauseOfProximityChangeToNegative;
  }

  public boolean isScreenOffBecauseOfProximity() {
    return this.mScreenOffBecauseOfProximity;
  }

  public void ignoreProximitySensorUntilChanged() {
    this.mHandler.sendEmptyMessage(2);
  }

  public void notifyDisplayDeviceChanged(DisplayDeviceConfig displayDeviceConfig) {
    this.mDisplayDeviceConfig = displayDeviceConfig;
    loadProximitySensor();
  }

  public void dumpLocal(PrintWriter printWriter) {
    printWriter.println();
    printWriter.println("DisplayPowerProximityStateController:");
    synchronized (this.mLock) {
      printWriter.println(
          "  mPendingWaitForNegativeProximityLocked="
              + this.mPendingWaitForNegativeProximityLocked);
    }
    printWriter.println("  mDisplayId=" + this.mDisplayId);
    printWriter.println("  mWaitingForNegativeProximity=" + this.mWaitingForNegativeProximity);
    printWriter.println("  mIgnoreProximityUntilChanged=" + this.mIgnoreProximityUntilChanged);
    printWriter.println("  mProximitySensor=" + this.mProximitySensor);
    printWriter.println("  mProximitySensorEnabled=" + this.mProximitySensorEnabled);
    printWriter.println("  mProximityThreshold=" + this.mProximityThreshold);
    printWriter.println("  mProximity=" + proximityToString(this.mProximity));
    printWriter.println("  mPendingProximity=" + proximityToString(this.mPendingProximity));
    printWriter.println(
        "  mPendingProximityDebounceTime="
            + TimeUtils.formatUptime(this.mPendingProximityDebounceTime));
    printWriter.println("  mScreenOffBecauseOfProximity=" + this.mScreenOffBecauseOfProximity);
    printWriter.println(
        "  mSkipRampBecauseOfProximityChangeToNegative="
            + this.mSkipRampBecauseOfProximityChangeToNegative);
  }

  public void ignoreProximitySensorUntilChangedInternal() {
    if (this.mIgnoreProximityUntilChanged || this.mProximity != 1) {
      return;
    }
    this.mIgnoreProximityUntilChanged = true;
    Slog.m76i(this.mTag, "Ignoring proximity");
    this.mNudgeUpdatePowerState.run();
  }

  public final void sendOnProximityPositiveWithWakelock() {
    this.mWakelockController.acquireWakelock(1);
    this.mHandler.post(this.mWakelockController.getOnProximityPositiveRunnable());
  }

  public final void sendOnProximityNegativeWithWakelock() {
    this.mWakelockController.acquireWakelock(2);
    this.mHandler.post(this.mWakelockController.getOnProximityNegativeRunnable());
  }

  public final void loadProximitySensor() {
    int i = this.mDisplayId;
    int i2 = (i == 0 || (i == 1 && PowerManagerUtil.SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY)) ? 8 : 0;
    DisplayDeviceConfig.SensorData proximitySensor = this.mDisplayDeviceConfig.getProximitySensor();
    Sensor findSensor =
        SensorUtils.findSensor(this.mSensorManager, proximitySensor.type, proximitySensor.name, i2);
    this.mProximitySensor = findSensor;
    if (findSensor != null) {
      this.mProximityThreshold = Math.min(findSensor.getMaximumRange(), 5.0f);
      this.mIsEarsenseProximity =
          this.mProximitySensor.getName().contains("Palm")
              || this.mProximitySensor.getName().contains("Ear Hover");
      this.mIsProximitySensorOnFoldingSide = this.mProximitySensor.semIsOnFoldingSide();
    }
  }

  public final boolean isProximitySensorValidState(
      DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
    int i;
    int i2 = displayPowerRequest.dualScreenPolicy;
    if (displayPowerRequest.coverClosed) {
      return false;
    }
    return !PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY
        || ((i = this.mDisplayId) == 0 && i2 != 1)
        || (i == 1 && !this.mIsProximitySensorOnFoldingSide && i2 == 1);
  }

  public final void setProximitySensorEnabled(boolean z) {
    if (z) {
      if (this.mProximitySensorEnabled) {
        return;
      }
      this.mProximitySensorEnabled = true;
      this.mIgnoreProximityUntilChanged = false;
      Slog.m72d(this.mTag, "setProximitySensorEnabled::registerListener");
      this.mSensorManager.registerListener(
          this.mProximitySensorListener, this.mProximitySensor, 3, this.mHandler);
      return;
    }
    if (this.mProximitySensorEnabled) {
      this.mProximitySensorEnabled = false;
      this.mProximity = -1;
      this.mIgnoreProximityUntilChanged = false;
      this.mPendingProximity = -1;
      this.mHandler.removeMessages(1);
      Slog.m72d(this.mTag, "setProximitySensorEnabled::unregisterListener");
      this.mSensorManager.unregisterListener(this.mProximitySensorListener);
      if (this.mWakelockController.releaseWakelock(3)) {
        this.mPendingProximityDebounceTime = -1L;
      }
    }
  }

  public final void handleProximitySensorEvent(long j, boolean z) {
    if (this.mProximitySensorEnabled) {
      int i = this.mPendingProximity;
      if (i != 0 || z) {
        if (i == 1 && z) {
          return;
        }
        this.mHandler.removeMessages(1);
        if (z) {
          this.mPendingProximity = 1;
          int i2 = this.mSensorPositiveDebounceDelay;
          if (i2 == -1) {
            i2 = 0;
          }
          this.mPendingProximityDebounceTime =
              j + i2 + ((this.mIsEarsenseProximity && this.mIsViewTypeCover) ? 50 : 0);
          this.mWakelockController.acquireWakelock(3);
        } else {
          this.mPendingProximity = 0;
          this.mPendingProximityDebounceTime =
              j + (this.mSensorNegativeDebounceDelay != -1 ? r10 : 50);
          this.mWakelockController.acquireWakelock(3);
        }
        debounceProximitySensor();
      }
    }
  }

  public final void debounceProximitySensor() {
    if (!this.mProximitySensorEnabled
        || this.mPendingProximity == -1
        || this.mPendingProximityDebounceTime < 0) {
      return;
    }
    if (this.mPendingProximityDebounceTime <= this.mClock.uptimeMillis()) {
      if (this.mProximity != this.mPendingProximity) {
        this.mIgnoreProximityUntilChanged = false;
        Slog.m76i(this.mTag, "No longer ignoring proximity [" + this.mPendingProximity + "]");
      }
      this.mProximity = this.mPendingProximity;
      this.mNudgeUpdatePowerState.run();
      if (this.mWakelockController.releaseWakelock(3)) {
        this.mPendingProximityDebounceTime = -1L;
        return;
      }
      return;
    }
    this.mHandler.sendMessageAtTime(
        this.mHandler.obtainMessage(1), this.mPendingProximityDebounceTime);
  }

  public class DisplayPowerProximityStateHandler extends Handler {
    public DisplayPowerProximityStateHandler(Looper looper) {
      super(looper, null, true);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 1) {
        DisplayPowerProximityStateController.this.debounceProximitySensor();
      } else {
        if (i != 2) {
          return;
        }
        DisplayPowerProximityStateController.this.ignoreProximitySensorUntilChangedInternal();
      }
    }
  }

  public final String proximityToString(int i) {
    return i != -1 ? i != 0 ? i != 1 ? Integer.toString(i) : "Positive" : "Negative" : "Unknown";
  }

  public boolean getPendingWaitForNegativeProximityLocked() {
    boolean z;
    synchronized (this.mLock) {
      z = this.mPendingWaitForNegativeProximityLocked;
    }
    return z;
  }

  public boolean getWaitingForNegativeProximity() {
    return this.mWaitingForNegativeProximity;
  }

  public boolean shouldIgnoreProximityUntilChanged() {
    return this.mIgnoreProximityUntilChanged;
  }

  public Handler getHandler() {
    return this.mHandler;
  }

  public int getPendingProximity() {
    return this.mPendingProximity;
  }

  public int getProximity() {
    return this.mProximity;
  }

  public long getPendingProximityDebounceTime() {
    return this.mPendingProximityDebounceTime;
  }

  public SensorEventListener getProximitySensorListener() {
    return this.mProximitySensorListener;
  }

  class Injector {
    public Clock createClock() {
      return new Clock() { // from class:
                           // com.android.server.display.DisplayPowerProximityStateController$Injector$$ExternalSyntheticLambda0
        @Override // com.android.server.display.DisplayPowerProximityStateController.Clock
        public final long uptimeMillis() {
          long uptimeMillis;
          uptimeMillis = SystemClock.uptimeMillis();
          return uptimeMillis;
        }
      };
    }
  }

  public boolean isProximityPositive() {
    return this.mProximity == 1;
  }
}
