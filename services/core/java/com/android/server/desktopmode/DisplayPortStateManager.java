package com.android.server.desktopmode;

import android.content.Context;
import android.util.IndentingPrintWriter;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeState;

/* loaded from: classes2.dex */
public class DisplayPortStateManager {
  public static final String TAG = "[DMS]" + DisplayPortStateManager.class.getSimpleName();
  public final Context mContext;
  public int mDisplayPortState;
  public boolean mDualModeEnabled;
  public boolean mExternalDisplayModeDual;
  public final SettingsHelper.OnSettingChangedListener mExternalDisplayModeListener;
  public boolean mHdmiAutoEnterEnabled;
  public final SettingsHelper.OnSettingChangedListener mHdmiAutoEnterListener;
  public boolean mHdmiDisplayConnected;
  public boolean mHighResolutionsForExternalEnabled;
  public final SettingsHelper.OnSettingChangedListener mHighResolutionsForExternalListener;
  public final Object mLock;
  public boolean mStandaloneModeEnabled;
  public final StateManager.StateListener mStateListener;

  public DisplayPortStateManager(
      Context context, IStateManager iStateManager, SettingsHelper settingsHelper) {
    StateManager.StateListener stateListener = new StateManager.StateListener() { // from class:
          // com.android.server.desktopmode.DisplayPortStateManager.1
          @Override // com.android.server.desktopmode.StateManager.StateListener
          public void onUserChanged(State state) {
            synchronized (DisplayPortStateManager.this.mLock) {
              DisplayPortStateManager displayPortStateManager = DisplayPortStateManager.this;
              displayPortStateManager.mHdmiAutoEnterEnabled =
                  displayPortStateManager.isHdmiAutoEnterEnabled();
              DisplayPortStateManager.this.mDualModeEnabled =
                  DisplayPortStateManager.isDualModeEnabled(state);
              DisplayPortStateManager.this.mStandaloneModeEnabled =
                  DisplayPortStateManager.isStandaloneModeEnabled(state);
              DisplayPortStateManager displayPortStateManager2 = DisplayPortStateManager.this;
              displayPortStateManager2.mExternalDisplayModeDual =
                  displayPortStateManager2.isExternalDisplayModeDualEnabled();
              DisplayPortStateManager.this.mHdmiDisplayConnected = state.isHdmiConnected();
              if (!DisplayPortStateManager.this.isHighResolutionsForExternalEnabled()) {
                DisplayPortStateManager displayPortStateManager3 = DisplayPortStateManager.this;
                displayPortStateManager3.setDisplayPortStateLocked(
                    displayPortStateManager3.getSettingState(), false);
              }
              DisplayPortStateManager displayPortStateManager4 = DisplayPortStateManager.this;
              displayPortStateManager4.mHighResolutionsForExternalEnabled =
                  displayPortStateManager4.isHighResolutionsForExternalEnabled();
              DisplayPortStateManager displayPortStateManager5 = DisplayPortStateManager.this;
              displayPortStateManager5.setHighResolutionsForExternalEnabledLocked(
                  displayPortStateManager5.mHighResolutionsForExternalEnabled);
            }
          }

          @Override // com.android.server.desktopmode.StateManager.StateListener
          public void onExternalDisplayConnectionChanged(State state) {
            boolean isHdmiConnected = state.isHdmiConnected();
            synchronized (DisplayPortStateManager.this.mLock) {
              if (DisplayPortStateManager.this.mHdmiDisplayConnected != isHdmiConnected) {
                DisplayPortStateManager.this.mHdmiDisplayConnected = isHdmiConnected;
                if (!DisplayPortStateManager.this.mHdmiDisplayConnected
                    && !DisplayPortStateManager.this.mDualModeEnabled) {
                  DisplayPortStateManager displayPortStateManager = DisplayPortStateManager.this;
                  displayPortStateManager.setDisplayPortStateLocked(
                      displayPortStateManager.getSettingState(), false);
                }
              }
            }
          }

          @Override // com.android.server.desktopmode.StateManager.StateListener
          public void onDualModeSetDesktopMode(State state, boolean z) {
            if (z) {
              return;
            }
            DisplayPortStateManager.this.setDualModeEnabled(false);
          }

          @Override // com.android.server.desktopmode.StateManager.StateListener
          public void onDualModeStopLoadingScreen(boolean z) {
            if (z) {
              DisplayPortStateManager.this.setDualModeEnabled(true);
            }
          }

          @Override // com.android.server.desktopmode.StateManager.StateListener
          public void onSetDesktopMode(State state, boolean z) {
            if (z) {
              return;
            }
            DisplayPortStateManager.this.setStandaloneModeEnabled(false);
          }

          @Override // com.android.server.desktopmode.StateManager.StateListener
          public void onStopLoadingScreen(boolean z) {
            if (z) {
              DisplayPortStateManager.this.setStandaloneModeEnabled(true);
            }
          }
        };
    this.mStateListener = stateListener;
    SettingsHelper.OnSettingChangedListener onSettingChangedListener =
        new SettingsHelper.OnSettingChangedListener("hdmi_auto_enter") { // from class:
          // com.android.server.desktopmode.DisplayPortStateManager.2
          @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
          public void onSettingChanged(String str, int i) {
            boolean parseBoolean = Boolean.parseBoolean(str);
            synchronized (DisplayPortStateManager.this.mLock) {
              if (DisplayPortStateManager.this.mHdmiAutoEnterEnabled != parseBoolean) {
                DisplayPortStateManager.this.mHdmiAutoEnterEnabled = parseBoolean;
                if (!DisplayPortStateManager.this.mHdmiDisplayConnected
                    && !DisplayPortStateManager.this.mDualModeEnabled) {
                  DisplayPortStateManager displayPortStateManager = DisplayPortStateManager.this;
                  displayPortStateManager.setDisplayPortStateLocked(
                      displayPortStateManager.getSettingState(), false);
                }
              }
            }
          }
        };
    this.mHdmiAutoEnterListener = onSettingChangedListener;
    SettingsHelper.OnSettingChangedListener onSettingChangedListener2 =
        new SettingsHelper.OnSettingChangedListener(
            "high_resolutions_for_external") { // from class:
          // com.android.server.desktopmode.DisplayPortStateManager.3
          @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
          public void onSettingChanged(String str, int i) {
            boolean parseBoolean = Boolean.parseBoolean(str);
            synchronized (DisplayPortStateManager.this.mLock) {
              if (DisplayPortStateManager.this.mHighResolutionsForExternalEnabled != parseBoolean) {
                DisplayPortStateManager.this.mHighResolutionsForExternalEnabled = parseBoolean;
                DisplayPortStateManager.this.setHighResolutionsForExternalEnabledLocked(
                    parseBoolean);
              }
            }
          }
        };
    this.mHighResolutionsForExternalListener = onSettingChangedListener2;
    SettingsHelper.OnSettingChangedListener onSettingChangedListener3 =
        new SettingsHelper.OnSettingChangedListener("external_display_mode") { // from class:
          // com.android.server.desktopmode.DisplayPortStateManager.4
          @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
          public void onSettingChanged(String str, int i) {
            boolean equals = "dual".equals(str);
            synchronized (DisplayPortStateManager.this.mLock) {
              if (DisplayPortStateManager.this.mExternalDisplayModeDual != equals) {
                DisplayPortStateManager.this.mExternalDisplayModeDual = equals;
                if (!DisplayPortStateManager.this.mHdmiDisplayConnected
                    && !DisplayPortStateManager.this.mDualModeEnabled) {
                  DisplayPortStateManager displayPortStateManager = DisplayPortStateManager.this;
                  displayPortStateManager.setDisplayPortStateLocked(
                      displayPortStateManager.getSettingState(), false);
                }
              }
            }
          }
        };
    this.mExternalDisplayModeListener = onSettingChangedListener3;
    this.mLock = new Object();
    this.mDisplayPortState = -1;
    this.mHdmiDisplayConnected = false;
    this.mHdmiAutoEnterEnabled = false;
    this.mDualModeEnabled = false;
    this.mStandaloneModeEnabled = false;
    this.mHighResolutionsForExternalEnabled = false;
    this.mExternalDisplayModeDual = false;
    this.mContext = context;
    iStateManager.registerListener(stateListener);
    settingsHelper.registerListener(onSettingChangedListener);
    settingsHelper.registerListener(onSettingChangedListener2);
    settingsHelper.registerListener(onSettingChangedListener3);
  }

  public final boolean isHdmiAutoEnterEnabled() {
    return DesktopModeSettings.getSettings(
        this.mContext.getContentResolver(), "hdmi_auto_enter", false);
  }

  public static boolean isDualModeEnabled(State state) {
    SemDesktopModeState desktopModeState = state.getDesktopModeState();
    return desktopModeState.getDisplayType() == 102
        && desktopModeState.enabled == 4
        && desktopModeState.state == 0;
  }

  public static boolean isStandaloneModeEnabled(State state) {
    SemDesktopModeState desktopModeState = state.getDesktopModeState();
    return desktopModeState.getDisplayType() == 101
        && desktopModeState.enabled == 4
        && desktopModeState.state == 0;
  }

  public final boolean isExternalDisplayModeDualEnabled() {
    return "dual"
        .equals(
            DesktopModeSettings.getSettings(
                this.mContext.getContentResolver(), "external_display_mode", "dual"));
  }

  public final boolean getSettingState() {
    if (DesktopModeFeature.SUPPORT_STANDALONE) {
      return this.mExternalDisplayModeDual
          && (this.mHdmiAutoEnterEnabled || this.mStandaloneModeEnabled);
    }
    return this.mHdmiAutoEnterEnabled;
  }

  public final void setDisplayPortStateLocked(boolean z, boolean z2) {
    int i = this.mHighResolutionsForExternalEnabled ? 0 : ((z ? 1 : 0) << 4) | (z2 ? 1 : 0);
    if (this.mDisplayPortState != i) {
      this.mDisplayPortState = i;
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(TAG, "setDisplayPortState(), state=0x" + Integer.toHexString(i));
      }
      Utils.writeFile("/sys/class/dp_sec/dex", i, z || z2);
    }
  }

  public final boolean isHighResolutionsForExternalEnabled() {
    return DesktopModeSettings.getSettings(
        this.mContext.getContentResolver(), "high_resolutions_for_external", false);
  }

  public final void setHighResolutionsForExternalEnabledLocked(boolean z) {
    Log.m7d(TAG, "setHighResolutionsForExternalEnabledLocked(), enabled=" + z);
    if (this.mHdmiDisplayConnected || this.mDualModeEnabled) {
      return;
    }
    if (z) {
      setDisplayPortStateLocked(false, false);
    } else {
      setDisplayPortStateLocked(getSettingState(), false);
    }
  }

  public final void setDualModeEnabled(boolean z) {
    synchronized (this.mLock) {
      if (this.mDualModeEnabled != z) {
        this.mDualModeEnabled = z;
        boolean z2 = this.mHdmiDisplayConnected;
        if (z2 && z) {
          setDisplayPortStateLocked(true, false);
          setDisplayPortStateLocked(true, true);
        } else if (z2) {
          setDisplayPortStateLocked(false, false);
        } else {
          setDisplayPortStateLocked(getSettingState(), false);
        }
      }
    }
  }

  public final void setStandaloneModeEnabled(boolean z) {
    synchronized (this.mLock) {
      if (this.mStandaloneModeEnabled != z) {
        this.mStandaloneModeEnabled = z;
        setDisplayPortStateLocked(getSettingState(), false);
      }
    }
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    synchronized (this.mLock) {
      indentingPrintWriter.println(
          "Current " + DisplayPortStateManager.class.getSimpleName() + " state:");
      indentingPrintWriter.increaseIndent();
      indentingPrintWriter.println("mDisplayPortState=" + this.mDisplayPortState);
      indentingPrintWriter.println("mHdmiDisplayConnected=" + this.mHdmiDisplayConnected);
      indentingPrintWriter.println("mHdmiAutoEnterEnabled=" + this.mHdmiAutoEnterEnabled);
      indentingPrintWriter.println("mDualModeEnabled=" + this.mDualModeEnabled);
      indentingPrintWriter.println("mStandaloneModeEnabled=" + this.mStandaloneModeEnabled);
      indentingPrintWriter.println("mExternalDisplayModeDual=" + this.mExternalDisplayModeDual);
      indentingPrintWriter.println(
          "mHighResolutionsForExternalEnabled=" + this.mHighResolutionsForExternalEnabled);
      indentingPrintWriter.decreaseIndent();
    }
  }
}
