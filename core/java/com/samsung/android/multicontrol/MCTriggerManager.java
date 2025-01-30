package com.samsung.android.multicontrol;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.p009os.Looper;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputMonitor;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;

/* loaded from: classes5.dex */
public class MCTriggerManager {
  Context mContext;
  InputChannel mDexInputChannel;
  InputMonitor mDexInputMonitor;
  MCInputEventReceiver mDexInputReceiver;
  InputChannel mInputChannel;
  InputMonitor mInputMonitor;
  MCInputEventReceiver mInputReceiver;
  Looper mLooper;
  SemDesktopModeManager semDesktopModeManager;
  public final String TAG_PREFIX = SemMultiControlManager.TAG_PREFIX;
  private final String TAG =
      SemMultiControlManager.TAG_PREFIX + MCTriggerManager.class.getSimpleName();
  boolean isEnabled = false;
  private boolean isDexEnabled = false;
  private SemDesktopModeManager.DesktopModeListener desktopModeListener =
      new SemDesktopModeManager
          .DesktopModeListener() { // from class:
                                   // com.samsung.android.multicontrol.MCTriggerManager.1
        @Override // com.samsung.android.desktopmode.SemDesktopModeManager.DesktopModeListener
        public void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
          if (semDesktopModeState.state == 4 && semDesktopModeState.getDisplayType() == 102) {
            if (!MCTriggerManager.this.isDexEnabled) {
              MCTriggerManager.this.enable(false);
              MCTriggerManager.this.enable(true);
            }
            MCTriggerManager.this.isDexEnabled = true;
            return;
          }
          if (MCTriggerManager.this.isDexEnabled) {
            MCTriggerManager.this.enable(false);
          }
          MCTriggerManager.this.isDexEnabled = false;
        }
      };

  public MCTriggerManager(Context context, Looper looper) {
    this.mContext = context;
    this.mLooper = looper;
  }

  private void registerDesktopModeListener() {
    SemDesktopModeManager semDesktopModeManager =
        (SemDesktopModeManager) this.mContext.getSystemService(Context.SEM_DESKTOP_MODE_SERVICE);
    this.semDesktopModeManager = semDesktopModeManager;
    if (semDesktopModeManager == null) {
      return;
    }
    semDesktopModeManager.registerListener(this.desktopModeListener);
  }

  private void unregisterDesktopModeListener() {
    SemDesktopModeManager semDesktopModeManager = this.semDesktopModeManager;
    if (semDesktopModeManager != null) {
      semDesktopModeManager.unregisterListener(this.desktopModeListener);
    }
  }

  private boolean isDesktopModeEnabled() {
    SemDesktopModeManager semDesktopModeManager = this.semDesktopModeManager;
    if (semDesktopModeManager == null) {
      return false;
    }
    SemDesktopModeState semDesktopModeState = semDesktopModeManager.getDesktopModeState();
    return semDesktopModeState.state == 4 && semDesktopModeState.getDisplayType() == 102;
  }

  public void enable(boolean enable) {
    try {
      if (enable) {
        if (this.isEnabled) {
          return;
        }
        Log.m98i(this.TAG, "[enable] true");
        this.isEnabled = true;
        InputManager inputManager = (InputManager) this.mContext.getSystemService("input");
        InputMonitor monitorGestureInput = inputManager.monitorGestureInput("MultiControl_0", 0);
        this.mInputMonitor = monitorGestureInput;
        InputChannel inputChannel = monitorGestureInput.getInputChannel();
        this.mInputChannel = inputChannel;
        this.mInputReceiver =
            new MCInputEventReceiver(
                this.mContext, 0, this.mInputMonitor, inputChannel, this.mLooper);
        if (isDesktopModeEnabled()) {
          DisplayManager dm =
              (DisplayManager) this.mContext.getSystemService(Context.DISPLAY_SERVICE);
          Context displayContext = this.mContext.createDisplayContext(dm.getDisplay(2));
          InputManager inputManager2 = (InputManager) displayContext.getSystemService("input");
          InputMonitor monitorGestureInput2 =
              inputManager2.monitorGestureInput("MultiControl_2", 2);
          this.mDexInputMonitor = monitorGestureInput2;
          InputChannel inputChannel2 = monitorGestureInput2.getInputChannel();
          this.mDexInputChannel = inputChannel2;
          this.mDexInputReceiver =
              new MCInputEventReceiver(
                  this.mContext, 2, this.mDexInputMonitor, inputChannel2, this.mLooper);
        }
        registerDesktopModeListener();
        return;
      }
      if (!this.isEnabled) {
        return;
      }
      this.isEnabled = false;
      Log.m98i(this.TAG, "[enable] false");
      InputMonitor inputMonitor = this.mInputMonitor;
      if (inputMonitor != null) {
        inputMonitor.dispose();
      }
      MCInputEventReceiver mCInputEventReceiver = this.mInputReceiver;
      if (mCInputEventReceiver != null) {
        mCInputEventReceiver.dispose();
      }
      InputMonitor inputMonitor2 = this.mDexInputMonitor;
      if (inputMonitor2 != null) {
        inputMonitor2.dispose();
      }
      MCInputEventReceiver mCInputEventReceiver2 = this.mDexInputReceiver;
      if (mCInputEventReceiver2 != null) {
        mCInputEventReceiver2.dispose();
      }
      this.mDexInputReceiver = null;
      this.mInputReceiver = null;
      this.mInputMonitor = null;
      this.mDexInputMonitor = null;
      unregisterDesktopModeListener();
    } catch (Exception e) {
      Log.m97e(this.TAG, "[enableTriggerDetection]", e);
    }
  }

  public void setTriggerThreshold(int threshold) {
    try {
      MCInputEventReceiver mCInputEventReceiver = this.mInputReceiver;
      if (mCInputEventReceiver != null) {
        mCInputEventReceiver.setTriggerThreshold(threshold);
      }
      MCInputEventReceiver mCInputEventReceiver2 = this.mDexInputReceiver;
      if (mCInputEventReceiver2 != null) {
        mCInputEventReceiver2.setTriggerThreshold(threshold);
      }
    } catch (Exception e) {
      Log.m97e(this.TAG, "[setTriggerThreshold]", e);
    }
  }

  public void dump() {
    Log.m94d(this.TAG, "mInputChannel=" + this.mInputChannel);
    Log.m94d(this.TAG, "mInputReceiver=" + this.mInputReceiver);
    Log.m94d(this.TAG, "mDexInputChannel=" + this.mDexInputChannel);
    Log.m94d(this.TAG, "mDexInputReceiver=" + this.mDexInputReceiver);
  }
}
