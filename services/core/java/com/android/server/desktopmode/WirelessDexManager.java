package com.android.server.desktopmode;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplayParameter;
import android.hardware.display.SemWifiDisplayParameterListener;
import android.hardware.display.SemWifiDisplayStatus;
import android.hardware.display.WifiDisplay;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.IndentingPrintWriter;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.ServiceThread;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class WirelessDexManager {
  public static final String TAG = "[DMS]" + WirelessDexManager.class.getSimpleName();
  public final Context mContext;
  public final DisplayManager mDisplayManager;
  public final Handler mHandler;
  public final InputManager mInputManager;
  public long mMotionEventStartTime;
  public final ContentResolver mResolver;
  public final IStateManager mStateManager;
  public String mTvTizenVersion;
  public final UiManager mUiManager;
  public final WindowManagerService mWindowManager;
  public boolean mIsLowLatencyMode = false;
  public boolean mIsPointerEventListener = false;
  public boolean mIsPointerIconListener = false;
  public boolean mIsWirelessDexEntered = false;
  public int mReducedLatency = -1;
  public int mPointerIconSync = -1;
  public int mDesktopDisplayId = -1;
  public int mFrequencyValue = 0;
  public String mConnectedDeviceName = "";
  public final Runnable mRunnableEnableLowLatencyMode = new Runnable() { // from class:
        // com.android.server.desktopmode.WirelessDexManager$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
          WirelessDexManager.this.lambda$new$0();
        }
      };
  public final Runnable mRunnableDisableLowLatencyMode = new Runnable() { // from class:
        // com.android.server.desktopmode.WirelessDexManager$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
          WirelessDexManager.this.lambda$new$1();
        }
      };
  public SemWifiDisplayParameterListener mSemWifiDisplayParameterListener =
      new SemWifiDisplayParameterListener() { // from class:
        // com.android.server.desktopmode.WirelessDexManager.1
        public void onParametersChanged(List list) {
          if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
              SemWifiDisplayParameter semWifiDisplayParameter = (SemWifiDisplayParameter) it.next();
              String key = semWifiDisplayParameter.getKey();
              String value = semWifiDisplayParameter.getValue();
              if (DesktopModeFeature.DEBUG) {
                Log.m7d(
                    WirelessDexManager.TAG,
                    "onParametersChanged (Key : " + key + ", Value : " + value + ")");
              }
              if ("wfd_sec_dex_support".equals(key)) {
                if ("yes".equals(value)) {
                  WirelessDexManager.this.mReducedLatency = 1;
                } else {
                  WirelessDexManager.this.mReducedLatency = 0;
                }
              } else if ("wfd_sec_dex_mouse_support".equals(key)) {
                if ("yes".equals(value)) {
                  WirelessDexManager.this.mPointerIconSync = 1;
                } else {
                  WirelessDexManager.this.mPointerIconSync = 0;
                }
              } else if ("notify".equals(key) && "weak_network".equals(value)) {
                WirelessDexManager.this.showToastWeakNetwork();
              } else if ("frequency".equals(key)) {
                try {
                  WirelessDexManager.this.mFrequencyValue = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                  Log.m9e(WirelessDexManager.TAG, "Wrong number format=" + value, e);
                }
              } else if ("tizenVer".equals(key)) {
                if (DesktopModeFeature.DEBUG) {
                  Log.m7d(WirelessDexManager.TAG, "TV Tizen version : " + value);
                }
                WirelessDexManager.this.mTvTizenVersion = value;
              }
            }
          }
        }
      };
  public final InputManager.SemOnPointerIconChangedListener mPointerIconChangedListener =
      new InputManager.SemOnPointerIconChangedListener() { // from class:
        // com.android.server.desktopmode.WirelessDexManager.2
        public void onPointerIconChanged(int i, Bitmap bitmap, float f, float f2) {
          WirelessDexManager.this.setWirelessDeXPointerIcon(i);
        }
      };
  public final WindowManagerPolicyConstants.PointerEventListener mDesktopPointerEventListener =
      new WindowManagerPolicyConstants.PointerEventListener() { // from class:
        // com.android.server.desktopmode.WirelessDexManager.3
        public void onPointerEvent(MotionEvent motionEvent) {
          int action = motionEvent.getAction();
          if (SystemClock.uptimeMillis() - WirelessDexManager.this.mMotionEventStartTime > 1000) {
            if (action == 2 || action == 7 || action == 0 || action == 1) {
              if (motionEvent.isFromSource(8194) || motionEvent.isFromSource(16386)) {
                WirelessDexManager.this.mMotionEventStartTime = SystemClock.uptimeMillis();
                if (!WirelessDexManager.this.mIsLowLatencyMode) {
                  WirelessDexManager.this.mHandler.post(
                      WirelessDexManager.this.mRunnableEnableLowLatencyMode);
                }
                WirelessDexManager.this.mHandler.removeCallbacks(
                    WirelessDexManager.this.mRunnableDisableLowLatencyMode);
                WirelessDexManager.this.mHandler.postDelayed(
                    WirelessDexManager.this.mRunnableDisableLowLatencyMode, 5000L);
              }
            }
          }
        }
      };

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0() {
    setLowLatencyModeEnabled(true);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$1() {
    setLowLatencyModeEnabled(false);
  }

  public class Receiver extends BroadcastReceiver {
    public Receiver() {}

    public void register() {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.HDMI_PLUGGED");
      intentFilter.addAction("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE");
      WirelessDexManager.this.mContext.registerReceiverAsUser(
          this, UserHandle.ALL, intentFilter, null, null);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(WirelessDexManager.TAG, "onReceive(), action=" + action);
      }
      State state = WirelessDexManager.this.mStateManager.getState();
      if ("android.intent.action.HDMI_PLUGGED".equals(action)) {
        if (intent.getBooleanExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, false)) {
          if (state.isDexOnPcOrWirelessDexConnected()) {
            if (DesktopModeFeature.DEBUG) {
              Log.m7d(WirelessDexManager.TAG, "Disconnect Wireless DeX / DFP when HDMI plugged");
            }
            WirelessDexManager.this.mStateManager.notifyDisplayDisconnectionRequest(1001);
            WirelessDexManager.this.mStateManager.notifyDisplayDisconnectionRequest(1000);
          }
          WirelessDexManager.this.disconnect();
          return;
        }
        return;
      }
      if ("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE".equals(action)) {
        int intExtra = intent.getIntExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, -1);
        boolean booleanExtra = intent.getBooleanExtra("by_user", true);
        if (DesktopModeFeature.DEBUG) {
          Log.m7d(
              WirelessDexManager.TAG,
              "WifiDisplay Connection state="
                  + intExtra
                  + ", disconnectedByUser="
                  + booleanExtra
                  + ", isWirelessDex="
                  + state.isWirelessDexConnected()
                  + ", mIsWirelessDexEntered="
                  + WirelessDexManager.this.mIsWirelessDexEntered);
        }
        if (intExtra != 0 || booleanExtra) {
          return;
        }
        WirelessDexManager.this.showToastToNotifyNetworkDisconnection();
      }
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  public WirelessDexManager(
      Context context,
      IStateManager iStateManager,
      UiManager uiManager,
      DisplayManager displayManager,
      InputManager inputManager,
      ServiceThread serviceThread,
      WindowManagerService windowManagerService) {
    this.mContext = context;
    this.mResolver = context.getContentResolver();
    this.mDisplayManager = displayManager;
    displayManager.semRegisterWifiDisplayParameterListener(
        this.mSemWifiDisplayParameterListener, null);
    this.mInputManager = inputManager;
    this.mWindowManager = windowManagerService;
    this.mStateManager = iStateManager;
    iStateManager.registerListener(new StateListener());
    this.mHandler = new Handler(serviceThread.getLooper());
    this.mUiManager = uiManager;
    new Receiver().register();
  }

  public final void setWirelessDeXPointerIcon(int i) {
    if (i != 1) {
      switch (i) {
        case FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_PURPOSE_AND_MODES_INFO /* 10122 */:
          i = 1014;
          break;
        case FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_GENERAL_INFO /* 10123 */:
          i = 1015;
          break;
        case FrameworkStatsLog.RKP_ERROR_STATS /* 10124 */:
          i = 1017;
          break;
        case FrameworkStatsLog.KEYSTORE2_CRASH_STATS /* 10125 */:
          i = 1016;
          break;
      }
      setWifiDisplayParameters("wfd_sec_uibc_mouse_cursor_idx", String.valueOf(i));
    }
    i = 1000;
    setWifiDisplayParameters("wfd_sec_uibc_mouse_cursor_idx", String.valueOf(i));
  }

  public final void setLowLatencyModeEnabled(boolean z) {
    if (this.mIsLowLatencyMode != z) {
      this.mIsLowLatencyMode = z;
      setWifiDisplayParameters("wfd_sec_low_latency_mode", z ? "on" : "off");
    }
  }

  public final void setWifiDisplayParameters(String str, String str2) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "setWifiDisplayParameters(key=" + str + ", value=" + str2 + ")");
    }
    this.mDisplayManager.semRequestWifiDisplayParameter(
        "setparams", new SemWifiDisplayParameter(str, str2));
  }

  public final void showToastWeakNetwork() {
    State state = this.mStateManager.getState();
    if (state.isWirelessDexConnected() && state.getDesktopModeState().compareTo(4, 0)) {
      ToastManager.showToast(
          Utils.getDisplayContext(this.mContext, state.getDesktopDisplayId()),
          String.format(
              this.mContext.getString(R.string.gnss_service), this.mContext.getString(17042504)));
    }
  }

  public final void showQosDialog(State state) {
    if (isQosDialogClosedDevice()) {
      return;
    }
    if (TextUtils.isEmpty(this.mTvTizenVersion) || this.mTvTizenVersion.equals("TIZEN-WFD-SINK")) {
      this.mUiManager.showDialog(state.getDesktopDisplayId(), 12, null);
      return;
    }
    int i = this.mFrequencyValue;
    if (i < 5160 || i > 5865) {
      this.mUiManager.showDialog(state.getDesktopDisplayId(), 11, null);
    }
  }

  public final void showToastToNotifyNetworkDisconnection() {
    if (TextUtils.isEmpty(this.mConnectedDeviceName)) {
      return;
    }
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(
          TAG,
          "["
              + this.mConnectedDeviceName
              + "] has been disconnected by something other than the user request.");
    }
    Context context = this.mContext;
    ToastManager.showToast(
        context,
        String.format(
            context.getString(R.string.forward_intent_to_owner), this.mConnectedDeviceName));
  }

  public final boolean isQosDialogClosedDevice() {
    String settings =
        DesktopModeSettings.getSettings(
            this.mResolver, "wireless_dex_qos_notification_deleted", (String) null);
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "Address List : " + settings);
    }
    if (settings == null) {
      return false;
    }
    String connectedDeviceAddress = getConnectedDeviceAddress();
    return !"".equals(connectedDeviceAddress) && settings.contains(connectedDeviceAddress);
  }

  public final String getConnectedDeviceAddress() {
    WifiDisplay activeDisplay = this.mDisplayManager.getWifiDisplayStatus().getActiveDisplay();
    return activeDisplay != null ? activeDisplay.getDeviceAddress() : "";
  }

  public final void setConnectedDeviceName() {
    WifiDisplay activeDisplay = this.mDisplayManager.getWifiDisplayStatus().getActiveDisplay();
    this.mConnectedDeviceName = activeDisplay != null ? activeDisplay.getDeviceName() : "";
  }

  public void disconnect() {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "disconnect()");
    }
    SemWifiDisplayStatus semGetWifiDisplayStatus = this.mDisplayManager.semGetWifiDisplayStatus();
    if (semGetWifiDisplayStatus == null || semGetWifiDisplayStatus.getActiveDisplay() == null) {
      return;
    }
    if (semGetWifiDisplayStatus.getConnectedState() == 2
        || semGetWifiDisplayStatus.getConnectedState() == 3) {
      this.mDisplayManager.disconnectWifiDisplay();
    }
  }

  public void setDefaultSettings() {
    DesktopModeSettings.setSettings(this.mResolver, "wireless_dex", 2);
    DesktopModeSettings.setSettings(this.mResolver, "wireless_dex_scan_device", false);
  }

  public class StateListener extends StateManager.StateListener {
    public StateListener() {}

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onUserChanged(State state) {
      WirelessDexManager.this.setDefaultSettings();
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onDualModeStopLoadingScreen(boolean z) {
      if (z) {
        State state = WirelessDexManager.this.mStateManager.getState();
        WirelessDexManager.this.mIsWirelessDexEntered = state.isWirelessDexConnected();
        WirelessDexManager.this.mDesktopDisplayId = state.getDesktopDisplayId();
        if (WirelessDexManager.this.mIsWirelessDexEntered) {
          if (!TextUtils.isEmpty(WirelessDexManager.this.mTvTizenVersion)) {
            WirelessDexManager.this.mIsPointerEventListener = true;
            WirelessDexManager.this.mWindowManager.registerPointerEventListener(
                WirelessDexManager.this.mDesktopPointerEventListener,
                WirelessDexManager.this.mDesktopDisplayId);
          }
          WirelessDexManager.this.showQosDialog(state);
          if (WirelessDexManager.this.mPointerIconSync == 1) {
            WirelessDexManager.this.mIsPointerIconListener = true;
            WirelessDexManager.this.mInputManager.semRegisterOnPointerIconChangedListener(
                WirelessDexManager.this.mPointerIconChangedListener, null);
          }
          DesktopModeSettings.setSettings(
              WirelessDexManager.this.mResolver,
              "uibc_finger_enabled",
              WirelessDexManager.this.mPointerIconSync != 1);
          WirelessDexManager.this.setConnectedDeviceName();
        }
      }
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onDualModeStartLoadingScreen(boolean z) {
      if (z || !WirelessDexManager.this.mIsWirelessDexEntered) {
        return;
      }
      if (WirelessDexManager.this.mIsPointerEventListener) {
        WirelessDexManager.this.mWindowManager.unregisterPointerEventListener(
            WirelessDexManager.this.mDesktopPointerEventListener,
            WirelessDexManager.this.mDesktopDisplayId);
        WirelessDexManager.this.mIsPointerEventListener = false;
      }
      if (WirelessDexManager.this.mIsPointerIconListener) {
        WirelessDexManager.this.mInputManager.semUnregisterOnPointerIconChangedListener(
            WirelessDexManager.this.mPointerIconChangedListener);
        WirelessDexManager.this.mIsPointerIconListener = false;
      }
      WirelessDexManager.this.mIsWirelessDexEntered = false;
      WirelessDexManager.this.mIsLowLatencyMode = false;
      WirelessDexManager.this.mConnectedDeviceName = "";
      WirelessDexManager.this.removeNotifications();
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(WirelessDexManager.TAG, "onDualModeStartLoadingScreen() finish");
      }
    }

    @Override // com.android.server.desktopmode.StateManager.StateListener
    public void onExternalDisplayConnectionChanged(State state) {
      if (state.isWirelessDexConnected()) {
        return;
      }
      WirelessDexManager.this.mReducedLatency = -1;
      WirelessDexManager.this.mPointerIconSync = -1;
      WirelessDexManager.this.mFrequencyValue = 0;
      WirelessDexManager.this.mTvTizenVersion = null;
      DesktopModeSettings.setSettings(
          WirelessDexManager.this.mResolver, "uibc_finger_enabled", false);
    }
  }

  public final void removeNotifications() {
    this.mUiManager.removeNotification(204);
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println("Current " + WirelessDexManager.class.getSimpleName() + " state:");
    indentingPrintWriter.increaseIndent();
    indentingPrintWriter.println("mFrequencyValue=" + this.mFrequencyValue);
    indentingPrintWriter.println("mIsLowLatencyMode=" + this.mIsLowLatencyMode);
    indentingPrintWriter.println("mIsWirelessDexEntered=" + this.mIsWirelessDexEntered);
    indentingPrintWriter.println("mPointerIconSync=" + this.mPointerIconSync);
    indentingPrintWriter.println("mReducedLatency=" + this.mReducedLatency);
    indentingPrintWriter.println("mTvTizenVersion=" + this.mTvTizenVersion);
    indentingPrintWriter.decreaseIndent();
  }
}
