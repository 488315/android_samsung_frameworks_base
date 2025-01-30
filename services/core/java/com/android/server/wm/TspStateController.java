package com.android.server.wm;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.game.SemGameManager;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.sepunion.Log;
import com.samsung.android.sepunion.SemCustomDumpManager;

/* loaded from: classes3.dex */
public class TspStateController {
  public final Runnable clearDeadzoneHole;
  public final TspGripCommand m3rdPartyGameTspCommand;
  public final TspGripCommand m3rdPartyTspCommand;
  public final TspGripCommand m3rdPartyTspCommandForIme;
  public boolean mAwake;
  public Context mContext;
  public final TspGripCommand mCurrentCommand;
  public final TspGripCommand mCustomTspCommand;
  public ArrayMap mDeadzoneHoleMap;
  public final TspGripCommand mDeviceDefaultTspCommand;
  public final DeviceSize mDeviceSize;
  public String mFocusedWindow;
  public SemGameManager mGameManager;

  /* renamed from: mH */
  public final HandlerC2981H f1748mH;
  public WindowState mImeTargetWindow;
  public WindowState mImeWindow;
  public boolean mImeWindowVisible;
  public boolean mIsEnabledCustomSetting;
  public boolean mIsImmShowing;
  public boolean mIsPortrait;
  public String mLastDeadzoneHole;
  public WindowState mLastFocusedWindow;
  public String mLastLandCmd;
  public boolean mLastNoteMode;
  public String mLastPortCmd;
  public String mReserveLandCmd;
  public String mReservePortCmd;
  public SemInputDeviceManager mSemInputDeviceManager;
  public SettingsObserver mSettingsObserver;
  public TspDebug mTspDebug;
  public int mTspDebugSetting;
  public String mTspThresholdSetting;
  public static final Uri URI_SETTING_TSP_THRESHOLD =
      Settings.Secure.getUriFor("setting_tsp_threshold");
  public static final Uri URI_SETTING_TSP_DEBUG = Settings.Secure.getUriFor("setting_tsp_debug");

  public final class HoleInfo {
    public int direction;
    public int endY;
    public int startY;

    public HoleInfo(int i, int i2, int i3) {
      this.direction = i;
      this.startY = i2;
      this.endY = i3;
    }
  }

  /* renamed from: com.android.server.wm.TspStateController$H */
  public final class HandlerC2981H extends Handler {
    public HandlerC2981H() {}

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 1) {
        TspStateController.this.updateWindowPolicyInner((WindowState) message.obj);
      } else if (i == 2) {
        TspStateController.this.updateImePolicyInner((WindowState) message.obj);
      } else {
        if (i != 3) {
          return;
        }
        TspStateController.this.finishScreenTurningOnInner();
      }
    }
  }

  public TspStateController(Context context) {
    this.mDeviceDefaultTspCommand = new TspGripCommand(CoreRune.IS_TABLET_DEVICE ? 3 : 1);
    this.m3rdPartyTspCommand = new TspGripCommand(CoreRune.IS_TABLET_DEVICE ? 6 : 4);
    this.m3rdPartyTspCommandForIme = new TspGripCommand(CoreRune.IS_TABLET_DEVICE ? 9 : 7);
    this.m3rdPartyGameTspCommand = new TspGripCommand(10);
    this.mCustomTspCommand = new TspGripCommand(13);
    this.mCurrentCommand = new TspGripCommand(13);
    this.mLastNoteMode = true;
    this.mIsPortrait = false;
    this.mIsEnabledCustomSetting = false;
    this.mDeadzoneHoleMap = new ArrayMap();
    this.mTspThresholdSetting = "";
    this.mTspDebugSetting = 0;
    HandlerC2981H handlerC2981H = new HandlerC2981H();
    this.f1748mH = handlerC2981H;
    this.mSemInputDeviceManager = null;
    this.clearDeadzoneHole =
        new Runnable() { // from class:
                         // com.android.server.wm.TspStateController$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            TspStateController.this.clearDeadzoneHole();
          }
        };
    this.mAwake = true;
    this.mContext = context;
    SettingsObserver settingsObserver = new SettingsObserver(handlerC2981H);
    this.mSettingsObserver = settingsObserver;
    settingsObserver.observe();
    DeviceSize deviceSize = new DeviceSize();
    this.mDeviceSize = deviceSize;
    initDefaultValues();
    updateCustomValue();
    this.mTspDebug = new TspDebug(deviceSize.initWidth, deviceSize.initHeight);
    toggleTspDebug();
    Log.addLogString("tspstatemanager", "init");
    writeTspCommandToSysfs("0,0,0,0", 3);
    ((SemCustomDumpManager) this.mContext.getSystemService("semcustomdump"))
        .setDumpState("tspstatemanager", (String) null);
  }

  public final void initDefaultValues() {
    this.mDeviceDefaultTspCommand.parse(this.mDeviceSize, null, false);
    this.m3rdPartyTspCommand.parse(this.mDeviceSize, null, false);
    this.m3rdPartyTspCommandForIme.parse(this.mDeviceSize, null, false);
    if (CoreRune.FW_USE_SMALLER_GRIPZONE_ON_GAME) {
      this.m3rdPartyGameTspCommand.parse(this.mDeviceSize, null, false);
    }
  }

  public final void updateCustomValue() {
    String str = this.mTspThresholdSetting;
    if (TextUtils.isEmpty(str)) {
      str = this.mContext.getResources().getString(R.string.ext_media_status_mounted_ro);
    }
    Slog.d("TspStateManager", "updateCustomValue customSetting=" + str);
    this.mIsEnabledCustomSetting = false;
    if (TextUtils.isEmpty(str)) {
      return;
    }
    this.mIsEnabledCustomSetting =
        this.mCustomTspCommand.parse(this.mDeviceSize, parseCommand(0, str), false);
    String parseCommand = parseCommand(1, str);
    this.m3rdPartyTspCommand.parse(this.mDeviceSize, parseCommand, false);
    this.m3rdPartyTspCommandForIme.parse(this.mDeviceSize, parseCommand, false);
  }

  public final String parseCommand(int i, String str) {
    if (TextUtils.isEmpty(str)) {
      return null;
    }
    String[] split = str.split(KnoxVpnFirewallHelper.DELIMITER);
    if (split.length <= i) {
      return null;
    }
    return split[i];
  }

  public void toggleTspDebug() {
    this.mTspDebug.setEnabled(
        Settings.Secure.getInt(this.mContext.getContentResolver(), "setting_tsp_debug", 0) == 1);
  }

  public void updateTspCustomCommand() {
    initDefaultValues();
    updateCustomValue();
    Log.addLogString("tspstatemanager", "onChange " + this.mCustomTspCommand);
    updateWindowPolicy(this.mLastFocusedWindow);
  }

  public void systemReady() {
    SemInputDeviceManager semInputDeviceManager =
        (SemInputDeviceManager) this.mContext.getSystemService("SemInputDeviceManagerService");
    this.mSemInputDeviceManager = semInputDeviceManager;
    if (semInputDeviceManager == null) {
      Slog.d("TspStateManager", "systemReady: failed to get the service");
    }
  }

  public void setDefaultDisplaySizeDensity(int i, int i2, int i3, int i4) {
    if (CoreRune.SAFE_DEBUG) {
      Slog.d(
          "TspStateManager",
          "setDefaultDisplaySizeDensity initWidth="
              + i3
              + ", initHeight="
              + i4
              + ", w="
              + i
              + ", h="
              + i2);
    }
    this.mDeviceSize.set(i, i2, i3, i4);
    initDefaultValues();
    updateCustomValue();
    TspDebug tspDebug = this.mTspDebug;
    DeviceSize deviceSize = this.mDeviceSize;
    tspDebug.setInitDisplaySize(deviceSize.initWidth, deviceSize.initHeight);
  }

  public void updateImeTargetWindow(WindowState windowState) {
    this.mImeTargetWindow = windowState;
  }

  public void updateImeWindowVisibility(boolean z) {
    this.mImeWindowVisible = z;
    if (z) {
      return;
    }
    this.mImeWindow = null;
  }

  public void updateWindowsPolicy(
      WindowState windowState, WindowState windowState2, WindowState windowState3) {
    if (windowState2 != null && windowState2 != windowState3) {
      if (windowState2 == windowState) {
        updateWindowPolicy(windowState);
      }
    } else if (this.mImeWindowVisible && this.mImeTargetWindow == windowState) {
      updateImePolicy(windowState3);
    } else if (windowState != null) {
      updateWindowPolicy(windowState);
    }
  }

  public void updateWindowPolicy(WindowState windowState) {
    this.mLastFocusedWindow = windowState;
    removeAllMessages();
    this.f1748mH.sendMessageDelayed(Message.obtain(this.f1748mH, 1, windowState), 500L);
  }

  public void updateImePolicy(WindowState windowState) {
    removeAllMessages();
    this.f1748mH.sendMessage(Message.obtain(this.f1748mH, 2, windowState));
  }

  public void finishScreenTurningOn() {
    if (this.f1748mH.hasMessages(3)) {
      this.f1748mH.removeMessages(3);
    }
    this.f1748mH.sendMessage(Message.obtain(this.f1748mH, 3));
  }

  public final void removeAllMessages() {
    if (this.f1748mH.hasMessages(1)) {
      this.f1748mH.removeMessages(1);
    }
    if (this.f1748mH.hasMessages(2)) {
      this.f1748mH.removeMessages(2);
    }
  }

  public final void updateWindowPolicyInner(WindowState windowState) {
    WindowState windowState2;
    boolean isTspNoteMode;
    boolean z = true;
    boolean z2 = false;
    if (CoreRune.FW_TSP_SIP_MODE) {
      boolean z3 = this.mIsImmShowing;
      if (z3 && (windowState != this.mImeTargetWindow || !this.mImeWindowVisible)) {
        this.mIsImmShowing = false;
        writeTspCommandToSysfs("0", 5);
      } else if (!z3 && this.mImeWindowVisible && windowState == this.mImeTargetWindow) {
        this.mIsImmShowing = true;
        writeTspCommandToSysfs("1", 5);
      }
    }
    if (CoreRune.FW_TSP_NOTE_MODE
        && windowState != null
        && this.mLastNoteMode != (isTspNoteMode = windowState.isTspNoteMode())) {
      this.mLastNoteMode = isTspNoteMode;
      writeTspCommandToSysfs(isTspNoteMode ? "1" : "0", 6);
    }
    if (CoreRune.FW_TSP_DEADZONE) {
      if (windowState != null
          && this.mImeTargetWindow == windowState
          && (windowState2 = this.mImeWindow) != null
          && this.mImeWindowVisible) {
        updateImePolicy(windowState2);
        return;
      }
      this.mCurrentCommand.set(this.mDeviceDefaultTspCommand);
      if (windowState != null) {
        boolean isSystemWindow = isSystemWindow(windowState);
        if (isSystemWindow) {
          z2 = isSystemWindow;
        } else {
          String str = windowState.getAttrs().packageName;
          if (str == null
              || (!str.startsWith("com.sec.android.") && !str.startsWith("com.samsung."))) {
            z = false;
          }
          z2 = z;
        }
        if (!z2) {
          if (CoreRune.FW_USE_SMALLER_GRIPZONE_ON_GAME && isForegroundGame()) {
            this.mCurrentCommand.set(this.m3rdPartyGameTspCommand);
          } else {
            this.mCurrentCommand.set(this.m3rdPartyTspCommand);
          }
        }
        this.mFocusedWindow = windowState.toString();
        this.mCurrentCommand.parse(this.mDeviceSize, windowState.getTspDeadzone());
      }
      if (CoreRune.SAFE_DEBUG) {
        Slog.d(
            "TspStateManager",
            "updateWindowPolicy focusedWindow="
                + windowState
                + ", isDeviceDefault="
                + z2
                + ", command="
                + this.mCurrentCommand);
      }
      updateTspCommand(this.mCurrentCommand);
    }
  }

  public final boolean isSystemWindow(WindowState windowState) {
    return windowState.getAttrs().type >= 2000 && windowState.getAttrs().type <= 2999;
  }

  public final boolean isForegroundGame() {
    if (this.mGameManager == null) {
      this.mGameManager = new SemGameManager();
    }
    try {
      SemGameManager semGameManager = this.mGameManager;
      if (semGameManager != null) {
        return semGameManager.isForegroundGame();
      }
      return false;
    } catch (Exception e) {
      Slog.e("TspStateManager", "Exception in checking isForegroundGame, " + e.toString());
      return false;
    }
  }

  public final void updateTspCommand(TspGripCommand tspGripCommand) {
    if (this.mIsEnabledCustomSetting) {
      tspGripCommand.set(this.mCustomTspCommand);
    }
    String makePortCommand = makePortCommand(tspGripCommand);
    if (!makePortCommand.equals(this.mLastPortCmd) && this.mIsPortrait) {
      writeTspCommandToSysfs(makePortCommand, 1);
      this.mLastPortCmd = makePortCommand;
    }
    this.mReservePortCmd = makePortCommand;
    String makeLandCommand = makeLandCommand(tspGripCommand);
    if (!makeLandCommand.equals(this.mLastLandCmd) && !this.mIsPortrait) {
      writeTspCommandToSysfs(makeLandCommand, 2);
      this.mLastLandCmd = makeLandCommand;
    }
    this.mReserveLandCmd = makeLandCommand;
  }

  public final String makePortCommand(TspGripCommand tspGripCommand) {
    StringBuilder sb = new StringBuilder();
    sb.append("1,");
    sb.append(tspGripCommand.mPortEdgeZone);
    sb.append(',');
    sb.append(tspGripCommand.mPortX1);
    sb.append(',');
    sb.append(tspGripCommand.mPortX2);
    sb.append(',');
    sb.append(tspGripCommand.mPortY1);
    if (CoreRune.FW_TSP_DEADZONE_V3) {
      sb.append(',');
      sb.append(tspGripCommand.mPortX3);
      sb.append(',');
      sb.append(tspGripCommand.mPortY2);
    }
    return sb.toString();
  }

  public final String makeLandCommand(TspGripCommand tspGripCommand) {
    return "2,1,"
        + tspGripCommand.mLandEdgeZone
        + ','
        + tspGripCommand.mLandX1
        + ','
        + tspGripCommand.mLandTopRejectWidth
        + ','
        + tspGripCommand.mLandBottomRejectWidth
        + ','
        + tspGripCommand.mLandTopGripWidth
        + ','
        + tspGripCommand.mLandBottomGripWidth;
  }

  public final void updateImePolicyInner(WindowState windowState) {
    Bundle tspDeadzone;
    if (CoreRune.FW_TSP_SIP_MODE && !this.mIsImmShowing) {
      this.mIsImmShowing = true;
      writeTspCommandToSysfs("1", 5);
    }
    if (CoreRune.FW_TSP_DEADZONE) {
      if (!CoreRune.IS_TABLET_DEVICE) {
        this.mCurrentCommand.reset();
      }
      String string =
          Settings.Secure.getString(this.mContext.getContentResolver(), "default_input_method");
      if (TextUtils.isEmpty(string)
          || (!string.contains("SamsungKeypad") && !string.contains("honeyboard"))) {
        this.mCurrentCommand.set(this.m3rdPartyTspCommandForIme);
      }
      if (windowState != null && (tspDeadzone = windowState.getTspDeadzone()) != null) {
        this.mCurrentCommand.parse(this.mDeviceSize, tspDeadzone);
      }
      if (CoreRune.SAFE_DEBUG) {
        Slog.d(
            "TspStateManager",
            "updateImePolicy imeWindow=" + windowState + ", command=" + this.mCurrentCommand);
      }
      this.mImeWindow = windowState;
      updateTspCommand(this.mCurrentCommand);
    }
  }

  public final void finishScreenTurningOnInner() {
    if (this.mIsPortrait && this.mReservePortCmd != null) {
      Log.addLogString("tspstatemanager", "finishScreenTurningOn TSP_COMMAND_TYPE_PORT");
      writeTspCommandToSysfsInner(this.mReservePortCmd, 1, true);
      this.mLastPortCmd = this.mReservePortCmd;
    } else if (this.mReserveLandCmd != null) {
      Log.addLogString("tspstatemanager", "finishScreenTurningOn TSP_COMMAND_TYPE_LAND");
      writeTspCommandToSysfsInner(this.mReserveLandCmd, 2, true);
      this.mLastLandCmd = this.mReserveLandCmd;
    }
  }

  public void setDeadzoneHole(Bundle bundle) {
    if (CoreRune.SAFE_DEBUG) {
      Slog.d("TspStateManager", "setDeadzoneHole ");
    }
    if (bundle == null) {
      Slog.w("TspStateManager", "setDeadzoneHole hole is null");
      return;
    }
    String string = bundle.getString("dead_zone_process_name", null);
    if (string == null) {
      Slog.w("TspStateManager", "setDeadzoneHole invalid name key");
      return;
    }
    int i = bundle.getInt("dead_zone_direction", 0);
    int i2 = bundle.getInt("dead_zone_port_y1", 0);
    int i3 = bundle.getInt("dead_zone_port_y2", 0);
    Slog.d(
        "TspStateManager",
        "holeName: " + string + ", direction: " + i + ", startY: " + i2 + ", endY: " + i3);
    if (!CoreRune.FW_TSP_DEADZONEHOLE_LAND && i > 2) {
      Slog.d("TspStateManager", "does not support top/bottom deadzone hole!");
      return;
    }
    ArrayMap arrayMap = new ArrayMap();
    synchronized (this.mDeadzoneHoleMap) {
      if (i == 0 || i2 < 0 || i3 < 0 || i2 == i3) {
        if (this.mDeadzoneHoleMap.containsKey(string)) {
          this.mDeadzoneHoleMap.remove(string);
        }
      } else {
        this.mDeadzoneHoleMap.put(string, new HoleInfo(i, Math.min(i2, i3), Math.max(i2, i3)));
      }
      int size = this.mDeadzoneHoleMap.size();
      for (int i4 = 0; i4 < size; i4++) {
        ArrayMap arrayMap2 = this.mDeadzoneHoleMap;
        HoleInfo holeInfo = (HoleInfo) arrayMap2.get(arrayMap2.keyAt(i4));
        if (holeInfo != null) {
          Rect rect = (Rect) arrayMap.get(Integer.valueOf(holeInfo.direction));
          if (rect == null) {
            rect = new Rect(0, holeInfo.startY, 0, holeInfo.endY);
          } else {
            rect.top = Math.min(rect.top, holeInfo.startY);
            rect.bottom = Math.max(rect.bottom, holeInfo.endY);
          }
          arrayMap.put(Integer.valueOf(holeInfo.direction), rect);
        }
      }
    }
    adjustToDisplaySize(arrayMap);
  }

  public final void adjustToDisplaySize(ArrayMap arrayMap) {
    int size = arrayMap.size();
    this.f1748mH.removeCallbacks(this.clearDeadzoneHole);
    if (size == 0) {
      clearDeadzoneHole();
      return;
    }
    for (int i = 0; i < size; i++) {
      int intValue = ((Integer) arrayMap.keyAt(i)).intValue();
      Rect rect = (Rect) arrayMap.get(Integer.valueOf(intValue));
      DeviceSize deviceSize = this.mDeviceSize;
      int tspHeightPixel =
          TspGripCommand.getTspHeightPixel(deviceSize.height, deviceSize.initHeight, rect.top);
      DeviceSize deviceSize2 = this.mDeviceSize;
      writeDeadzoneHoleCmd(
          intValue,
          tspHeightPixel,
          TspGripCommand.getTspHeightPixel(
              deviceSize2.height, deviceSize2.initHeight, rect.bottom));
    }
  }

  public final void writeDeadzoneHoleCmd(int i, int i2, int i3) {
    String str = "0," + i + ',' + i2 + ',' + i3;
    if (CoreRune.SAFE_DEBUG) {
      Slog.d("TspStateManager", "setDeadzoneHole " + this.mLastDeadzoneHole + " --> " + str);
    }
    if (str.equals(this.mLastDeadzoneHole)) {
      return;
    }
    this.mLastDeadzoneHole = str;
    Log.addLogString("tspstatemanager", "setDeadzoneHole");
    writeTspCommandToSysfs(this.mLastDeadzoneHole, 3);
  }

  public final void writeTspCommandToSysfs(String str, int i) {
    writeTspCommandToSysfsInner(str, i, false);
  }

  public final void writeTspCommandToSysfsInner(final String str, final int i, final boolean z) {
    new Thread(
            new Runnable() { // from class:
                             // com.android.server.wm.TspStateController$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                TspStateController.this.lambda$writeTspCommandToSysfsInner$0(z, i, str);
              }
            },
            "tspStateManager")
        .start();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$writeTspCommandToSysfsInner$0(boolean z, int i, String str) {
    if (this.mSemInputDeviceManager != null) {
      if (this.mAwake || z) {
        if (i == 5) {
          Log.addLogString("tspstatemanager", "[" + i + "]" + str);
          this.mSemInputDeviceManager.setSipMode(Integer.parseInt(str));
        } else if (i == 6) {
          Log.addLogString("tspstatemanager", "[" + i + "]" + str);
          this.mSemInputDeviceManager.setNoteMode(Integer.parseInt(str));
        } else {
          if (this.mFocusedWindow != null) {
            Log.addLogString(
                "tspstatemanager", "[" + i + "]window : " + str + ": " + this.mFocusedWindow);
            this.mFocusedWindow = null;
          } else {
            Log.addLogString("tspstatemanager", "[" + i + "]" + str);
          }
          this.mSemInputDeviceManager.setGripData(str);
        }
        Slog.d("TspStateManager", "wrote command: cmd=" + str + ", type=" + i);
        if (i != 5) {
          this.mTspDebug.updateTspState(this.mContext, str, i);
          return;
        }
        return;
      }
      return;
    }
    Slog.d("TspStateManager", "mSemInputDeviceManager is null");
  }

  public void setOrientation(boolean z) {
    if (CoreRune.FW_TSP_DEADZONE) {
      setOrientation(z, false);
    }
  }

  public void setOrientation(boolean z, boolean z2) {
    if (this.mIsPortrait != z || z2) {
      this.mIsPortrait = z;
      synchronized (this.mDeadzoneHoleMap) {
        this.mDeadzoneHoleMap.clear();
      }
      if (!this.f1748mH.hasCallbacks(this.clearDeadzoneHole)) {
        this.f1748mH.postDelayed(this.clearDeadzoneHole, 150L);
      }
      if (this.mIsPortrait) {
        String str = this.mReservePortCmd;
        if (str == null) {
          Slog.e("TspStateManager", "setOrientation mReservePortCmd is null.");
          return;
        }
        if (!str.equals(this.mLastPortCmd)) {
          Log.addLogString(
              "tspstatemanager", "setOrientation TSP_COMMAND_TYPE_PORT : " + this.mReservePortCmd);
          writeTspCommandToSysfs(this.mReservePortCmd, 1);
          this.mLastPortCmd = this.mReservePortCmd;
          return;
        }
        Log.addLogString("tspstatemanager", "setOrientation TSP_COMMAND_TYPE_SAME");
        writeTspCommandToSysfs("2,0", 4);
        return;
      }
      if (this.mReserveLandCmd == null) {
        Slog.e("TspStateManager", "setOrientation mReserveLandCmd is null.");
        return;
      }
      Log.addLogString(
          "tspstatemanager", "setOrientation TSP_COMMAND_TYPE_LAND : " + this.mReserveLandCmd);
      writeTspCommandToSysfs(this.mReserveLandCmd, 2);
      this.mLastLandCmd = this.mReserveLandCmd;
    }
  }

  public final void clearDeadzoneHole() {
    writeDeadzoneHoleCmd(0, 0, 0);
  }

  public void startedWakingUp() {
    this.mAwake = true;
    if (CoreRune.FW_TSP_SIP_MODE) {
      writeTspCommandToSysfs(this.mImeWindowVisible ? "1" : "0", 5);
    }
    if (CoreRune.FW_TSP_NOTE_MODE && this.mFocusedWindow != null) {
      writeTspCommandToSysfs(this.mLastNoteMode ? "1" : "0", 6);
    }
    if (CoreRune.FW_TSP_DEADZONE) {
      setOrientation(this.mIsPortrait, true);
    }
  }

  public void startedGoingToSleep() {
    this.mAwake = false;
  }

  public class SettingsObserver extends ContentObserver {
    public SettingsObserver(Handler handler) {
      super(handler);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
      if (uri == null) {
        return;
      }
      if (TspStateController.URI_SETTING_TSP_THRESHOLD.equals(uri)) {
        TspStateController tspStateController = TspStateController.this;
        tspStateController.mTspThresholdSetting =
            Settings.Secure.getString(
                tspStateController.mContext.getContentResolver(), "setting_tsp_threshold");
        TspStateController.this.updateTspCustomCommand();
      } else if (TspStateController.URI_SETTING_TSP_DEBUG.equals(uri)) {
        TspStateController tspStateController2 = TspStateController.this;
        tspStateController2.mTspDebugSetting =
            Settings.Secure.getInt(
                tspStateController2.mContext.getContentResolver(), "setting_tsp_debug", 0);
        TspStateController.this.toggleTspDebug();
      }
    }

    public void observe() {
      ContentResolver contentResolver = TspStateController.this.mContext.getContentResolver();
      contentResolver.registerContentObserver(
          TspStateController.URI_SETTING_TSP_THRESHOLD, false, this, -1);
      contentResolver.registerContentObserver(
          TspStateController.URI_SETTING_TSP_DEBUG, false, this, -1);
      TspStateController tspStateController = TspStateController.this;
      tspStateController.mTspThresholdSetting =
          Settings.Secure.getString(
              tspStateController.mContext.getContentResolver(), "setting_tsp_threshold");
      TspStateController tspStateController2 = TspStateController.this;
      tspStateController2.mTspDebugSetting =
          Settings.Secure.getInt(
              tspStateController2.mContext.getContentResolver(), "setting_tsp_debug", 0);
    }
  }

  public class DeviceSize {
    public int initWidth = -1;
    public int initHeight = -1;
    public int width = -1;
    public int height = -1;

    public void set(int i, int i2, int i3, int i4) {
      this.width = i;
      this.height = i2;
      this.initWidth = i3;
      this.initHeight = i4;
    }
  }

  public void printLastGripCmd() {
    Slog.d("TspStateManager", "lastGripCmd=" + this.mTspDebug.updateDebugString().toString());
  }

  public class TspDebug {
    public boolean mEnabled;
    public String mHoleCommand;
    public int mInitDisplayHeight;
    public int mInitDisplayWidth;
    public String mLandCommand;
    public String mLastCommand;
    public String mLastNoteMode;
    public String mPortCommand;

    public TspDebug(int i, int i2) {
      setInitDisplaySize(i, i2);
    }

    public void updateTspState(Context context, String str, int i) {
      if (i == 1) {
        this.mPortCommand = str;
        this.mLastCommand = str;
      } else if (i == 2) {
        this.mLandCommand = str;
        this.mLastCommand = str;
      } else if (i == 3) {
        this.mHoleCommand = str;
      } else if (i == 6) {
        this.mLastNoteMode = str;
      }
      writeToSettings(context, updateDebugString());
    }

    public StringBuilder updateDebugString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.mInitDisplayWidth);
      sb.append(',');
      sb.append(this.mInitDisplayHeight);
      sb.append('/');
      sb.append(this.mPortCommand);
      sb.append('/');
      sb.append(this.mLandCommand);
      sb.append('/');
      sb.append(this.mHoleCommand);
      sb.append('/');
      sb.append(this.mLastCommand);
      return sb;
    }

    public final void writeToSettings(Context context, StringBuilder sb) {
      if (this.mEnabled) {
        Settings.Secure.putString(
            context.getContentResolver(), "setting_last_grip_cmd", sb.toString());
        Settings.Secure.putString(
            context.getContentResolver(), "setting_last_note_mode", this.mLastNoteMode);
      }
    }

    public void setInitDisplaySize(int i, int i2) {
      this.mInitDisplayWidth = i;
      this.mInitDisplayHeight = i2;
    }

    public void setEnabled(boolean z) {
      this.mEnabled = z;
    }
  }
}
