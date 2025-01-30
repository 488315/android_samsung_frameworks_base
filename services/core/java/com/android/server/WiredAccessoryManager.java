package com.android.server;

import android.R;
import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.UEventObserver;
import android.os.IInstalld;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import com.android.server.input.InputManagerService;
import com.samsung.android.audio.Rune;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public final class WiredAccessoryManager implements InputManagerService.WiredAccessoryCallbacks {
  public static final String TAG = "WiredAccessoryManager";
  public final AudioManager mAudioManager;
  public final Context mContext;
  public final WiredAccessoryExtconObserver mExtconObserver;
  public int mHeadsetState;
  public final InputManagerService mInputManager;
  public final WiredAccessoryObserver mObserver;
  public int mSwitchValues;
  public final boolean mUseDevInputEventForAudioJack;
  public final PowerManager.WakeLock mWakeLock;
  public final Object mLock = new Object();
  public final Handler mHandler =
      new Handler(
          Looper.myLooper(), null, true) { // from class: com.android.server.WiredAccessoryManager.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
          int i = message.what;
          if (i == 1) {
            WiredAccessoryManager.this.setDevicesState(
                message.arg1, message.arg2, (String) message.obj);
            WiredAccessoryManager.this.mWakeLock.release();
          } else if (i == 2) {
            WiredAccessoryManager.this.onSystemReady();
            WiredAccessoryManager.this.mWakeLock.release();
          } else {
            if (i != 3) {
              return;
            }
            WiredAccessoryManager.this.showToast();
            WiredAccessoryManager.this.mWakeLock.release();
          }
        }
      };
  public boolean mBikeMode = false;

  public WiredAccessoryManager(Context context, InputManagerService inputManagerService) {
    PowerManager.WakeLock newWakeLock =
        ((PowerManager) context.getSystemService("power")).newWakeLock(1, TAG);
    this.mWakeLock = newWakeLock;
    newWakeLock.setReferenceCounted(false);
    this.mAudioManager = (AudioManager) context.getSystemService("audio");
    this.mInputManager = inputManagerService;
    this.mUseDevInputEventForAudioJack =
        context.getResources().getBoolean(R.bool.config_supportPreRebootSecurityLogs);
    this.mExtconObserver = new WiredAccessoryExtconObserver();
    this.mObserver = new WiredAccessoryObserver();
    this.mContext = context;
    if (Rune.SEC_AUDIO_BIKE_MODE) {
      context
          .getContentResolver()
          .registerContentObserver(
              Settings.Secure.getUriFor("isBikeMode"),
              false,
              new ContentObserver(
                  new Handler()) { // from class: com.android.server.WiredAccessoryManager.1
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                  super.onChange(z);
                  WiredAccessoryManager.this.handleBikeMode();
                }
              });
    }
  }

  public final void onSystemReady() {
    if (this.mUseDevInputEventForAudioJack) {
      int i = this.mInputManager.getSwitchState(-1, -256, 2) == 1 ? 4 : 0;
      if (this.mInputManager.getSwitchState(-1, -256, 4) == 1) {
        i |= 16;
      }
      if (this.mInputManager.getSwitchState(-1, -256, 6) == 1) {
        i |= 64;
      }
      notifyWiredAccessoryChanged(0L, i, 84);
    }
    this.mObserver.init();
  }

  @Override // com.android.server.input.InputManagerService.WiredAccessoryCallbacks
  public void notifyWiredAccessoryChanged(long j, int i, int i2) {
    synchronized (this.mLock) {
      int i3 = (this.mSwitchValues & (~i2)) | i;
      this.mSwitchValues = i3;
      int i4 = i3 & 84;
      int i5 = 0;
      if (i4 != 0) {
        if (i4 == 4) {
          i5 = 2;
        } else if (i4 == 16 || i4 == 20) {
          i5 = 1;
        } else if (i4 == 64) {
          i5 = 32;
        }
      }
      updateLocked("h2w", i5 | (this.mHeadsetState & (-36)));
    }
  }

  @Override // com.android.server.input.InputManagerService.WiredAccessoryCallbacks
  public void systemReady() {
    synchronized (this.mLock) {
      this.mWakeLock.acquire();
      this.mHandler.sendMessage(this.mHandler.obtainMessage(2, 0, 0, null));
    }
  }

  public final void updateLocked(String str, int i) {
    boolean z;
    boolean z2;
    int i2 = i & 63;
    int i3 = i2 & 4;
    int i4 = i2 & 8;
    int i5 = i2 & 35;
    if (this.mHeadsetState == i2) {
      Log.e(TAG, "No state change.");
      return;
    }
    if (i5 == 35) {
      Log.e(TAG, "Invalid combination, unsetting h2w flag");
      z = false;
    } else {
      z = true;
    }
    if (i3 == 4 && i4 == 8) {
      Log.e(TAG, "Invalid combination, unsetting usb flag");
      z2 = false;
    } else {
      z2 = true;
    }
    if (!z && !z2) {
      Log.e(TAG, "invalid transition, returning ...");
      return;
    }
    this.mWakeLock.acquire();
    if (Rune.SEC_AUDIO_BIKE_MODE && this.mBikeMode) {
      if (i5 != 0) {
        Slog.w(TAG, "Bike mode is ON. Earphones disabled");
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, 0, 0, null));
      }
    } else if (!this.mBikeMode || i5 == 0) {
      Log.i(TAG, "MSG_NEW_DEVICE_STATE");
      this.mHandler.sendMessage(this.mHandler.obtainMessage(1, i2, this.mHeadsetState, str));
    }
    this.mHeadsetState = i2;
  }

  public final void setDevicesState(int i, int i2, String str) {
    synchronized (this.mLock) {
      int i3 = 1;
      int i4 = 63;
      while (i4 != 0) {
        if ((i3 & i4) != 0) {
          setDeviceStateLocked(i3, i, i2, str);
          i4 &= ~i3;
        }
        i3 <<= 1;
      }
    }
  }

  public final void setDeviceStateLocked(int i, int i2, int i3, String str) {
    int i4 = i2 & i;
    if (i4 != (i3 & i)) {
      int i5 = 0;
      int i6 = i4 != 0 ? 1 : 0;
      int i7 = 4;
      if (i == 1) {
        i5 = -2147483632;
      } else if (i == 2) {
        i7 = 8;
      } else if (i == 32) {
        i7 = IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
      } else if (i == 4) {
        i7 = IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
      } else if (i == 8) {
        i7 = IInstalld.FLAG_USE_QUOTA;
      } else {
        if (i != 16) {
          Slog.e(TAG, "setDeviceState() invalid headset type: " + i);
          return;
        }
        i7 = 1024;
      }
      if (i7 != 0) {
        this.mAudioManager.setWiredDeviceConnectionState(i7, i6, "", str);
      }
      if (i5 != 0) {
        this.mAudioManager.setWiredDeviceConnectionState(i5, i6, "", str);
      }
    }
  }

  public class WiredAccessoryObserver extends UEventObserver {
    public final List mUEventInfo = makeObservedUEventList();

    public WiredAccessoryObserver() {}

    public void init() {
      int i;
      synchronized (WiredAccessoryManager.this.mLock) {
        char[] cArr = new char[1024];
        for (int i2 = 0; i2 < this.mUEventInfo.size(); i2++) {
          UEventInfo uEventInfo = (UEventInfo) this.mUEventInfo.get(i2);
          try {
            try {
              FileReader fileReader = new FileReader(uEventInfo.getSwitchStatePath());
              int read = fileReader.read(cArr, 0, 1024);
              fileReader.close();
              int parseInt = Integer.parseInt(new String(cArr, 0, read).trim());
              if (parseInt > 0) {
                updateStateLocked(uEventInfo.getDevPath(), uEventInfo.getDevName(), parseInt);
              }
            } catch (Exception e) {
              Slog.e(
                  WiredAccessoryManager.TAG,
                  "Error while attempting to determine initial switch state for "
                      + uEventInfo.getDevName(),
                  e);
            }
          } catch (FileNotFoundException unused) {
            Slog.w(
                WiredAccessoryManager.TAG,
                uEventInfo.getSwitchStatePath()
                    + " not found while attempting to determine initial switch state");
          }
        }
      }
      for (i = 0; i < this.mUEventInfo.size(); i++) {
        startObserving("DEVPATH=" + ((UEventInfo) this.mUEventInfo.get(i)).getDevPath());
      }
    }

    public final List makeObservedUEventList() {
      ArrayList arrayList = new ArrayList();
      if (!WiredAccessoryManager.this.mUseDevInputEventForAudioJack) {
        UEventInfo uEventInfo = new UEventInfo("h2w", 1, 2, 32);
        if (uEventInfo.checkSwitchExists()) {
          arrayList.add(uEventInfo);
        } else {
          Slog.w(WiredAccessoryManager.TAG, "This kernel does not have wired headset support");
        }
      }
      UEventInfo uEventInfo2 = new UEventInfo("usb_audio", 4, 8, 0);
      if (uEventInfo2.checkSwitchExists()) {
        arrayList.add(uEventInfo2);
      } else {
        Slog.w(WiredAccessoryManager.TAG, "This kernel does not have usb audio support");
      }
      UEventInfo uEventInfo3 = new UEventInfo("ch_hdmi_audio", 16, 0, 0);
      if (uEventInfo3.checkSwitchExists()) {
        arrayList.add(uEventInfo3);
      } else {
        UEventInfo uEventInfo4 = new UEventInfo("hdmi_audio", 16, 0, 0);
        if (uEventInfo4.checkSwitchExists()) {
          arrayList.add(uEventInfo4);
        } else {
          UEventInfo uEventInfo5 = new UEventInfo("hdmi", 16, 0, 0);
          if (uEventInfo5.checkSwitchExists()) {
            arrayList.add(uEventInfo5);
          } else {
            Slog.w(WiredAccessoryManager.TAG, "This kernel does not have HDMI audio support");
          }
        }
      }
      return arrayList;
    }

    public void onUEvent(UEventObserver.UEvent uEvent) {
      try {
        String str = uEvent.get("DEVPATH");
        String str2 = uEvent.get("SWITCH_NAME");
        int parseInt = Integer.parseInt(uEvent.get("SWITCH_STATE"));
        synchronized (WiredAccessoryManager.this.mLock) {
          updateStateLocked(str, str2, parseInt);
        }
      } catch (NumberFormatException unused) {
        Slog.e(WiredAccessoryManager.TAG, "Could not parse switch state from event " + uEvent);
      }
    }

    public final void updateStateLocked(String str, String str2, int i) {
      for (int i2 = 0; i2 < this.mUEventInfo.size(); i2++) {
        UEventInfo uEventInfo = (UEventInfo) this.mUEventInfo.get(i2);
        if (str.equals(uEventInfo.getDevPath())) {
          if ("ch_hdmi_audio".equals(uEventInfo.getDevName())) {
            if ((i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT) == 0) {
              Slog.v(WiredAccessoryManager.TAG, "This is for DVI and No-Speaker HDMI Device");
              return;
            }
            str2 = String.valueOf(i);
            i = i == -1 ? 0 : 1;
            Slog.v(WiredAccessoryManager.TAG, "HDMI config " + str2 + ", state " + i);
          }
          WiredAccessoryManager wiredAccessoryManager = WiredAccessoryManager.this;
          wiredAccessoryManager.updateLocked(
              str2, uEventInfo.computeNewHeadsetState(wiredAccessoryManager.mHeadsetState, i));
          return;
        }
      }
    }

    public final class UEventInfo {
      public final String mDevName;
      public final int mState1Bits;
      public final int mState2Bits;
      public final int mStateNbits;

      public UEventInfo(String str, int i, int i2, int i3) {
        this.mDevName = str;
        this.mState1Bits = i;
        this.mState2Bits = i2;
        this.mStateNbits = i3;
      }

      public String getDevName() {
        return this.mDevName;
      }

      public String getDevPath() {
        return String.format(Locale.US, "/devices/virtual/switch/%s", this.mDevName);
      }

      public String getSwitchStatePath() {
        return String.format(Locale.US, "/sys/class/switch/%s/state", this.mDevName);
      }

      public boolean checkSwitchExists() {
        return new File(getSwitchStatePath()).exists();
      }

      public int computeNewHeadsetState(int i, int i2) {
        int i3 = this.mState1Bits;
        int i4 = this.mState2Bits;
        int i5 = this.mStateNbits;
        int i6 = ~(i3 | i4 | i5);
        if (i2 != 1) {
          i3 = i2 == 2 ? i4 : i2 == i5 ? i5 : 0;
        }
        return (i & i6) | i3;
      }
    }
  }

  public class WiredAccessoryExtconObserver extends ExtconStateObserver {
    public final List mExtconInfos =
        ExtconUEventObserver.ExtconInfo.getExtconInfoForTypes(
            new String[] {"HEADPHONE", "MICROPHONE", "HDMI", "LINE-OUT"});

    public WiredAccessoryExtconObserver() {}

    @Override // com.android.server.ExtconStateObserver
    public Pair parseState(ExtconUEventObserver.ExtconInfo extconInfo, String str) {
      int[] iArr = {0, 0};
      if (extconInfo.hasCableType("HEADPHONE")) {
        WiredAccessoryManager.updateBit(iArr, 2, str, "HEADPHONE");
      }
      if (extconInfo.hasCableType("MICROPHONE")) {
        WiredAccessoryManager.updateBit(iArr, 1, str, "MICROPHONE");
      }
      if (extconInfo.hasCableType("HDMI")) {
        WiredAccessoryManager.updateBit(iArr, 16, str, "HDMI");
      }
      if (extconInfo.hasCableType("LINE-OUT")) {
        WiredAccessoryManager.updateBit(iArr, 32, str, "LINE-OUT");
      }
      return Pair.create(Integer.valueOf(iArr[0]), Integer.valueOf(iArr[1]));
    }

    @Override // com.android.server.ExtconStateObserver
    public void updateState(ExtconUEventObserver.ExtconInfo extconInfo, String str, Pair pair) {
      synchronized (WiredAccessoryManager.this.mLock) {
        int intValue = ((Integer) pair.first).intValue();
        int intValue2 = ((Integer) pair.second).intValue();
        WiredAccessoryManager wiredAccessoryManager = WiredAccessoryManager.this;
        wiredAccessoryManager.updateLocked(
            str,
            (intValue2 & intValue)
                | (wiredAccessoryManager.mHeadsetState & (~((~intValue2) & intValue))));
      }
    }
  }

  public static void updateBit(int[] iArr, int i, String str, String str2) {
    iArr[0] = iArr[0] | i;
    if (str.contains(str2 + "=1")) {
      iArr[0] = iArr[0] | i;
      iArr[1] = i | iArr[1];
      return;
    }
    if (str.contains(str2 + "=0")) {
      iArr[0] = iArr[0] | i;
      iArr[1] = (~i) & iArr[1];
    }
  }

  public final void handleBikeMode() {
    int i = 0;
    this.mBikeMode =
        Settings.Secure.getInt(this.mContext.getContentResolver(), "isBikeMode", 0) == 1;
    this.mWakeLock.acquire();
    if (this.mBikeMode) {
      Slog.w(TAG, "Earphones are disabled in bike mode");
      if (this.mHeadsetState != 0) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, 0, 0, null));
      }
    } else {
      int i2 = this.mHeadsetState;
      this.mHeadsetState = 0;
      i = i2;
    }
    this.mHandler.sendMessage(this.mHandler.obtainMessage(1, i, this.mHeadsetState, "h2w"));
    if (this.mBikeMode) {
      return;
    }
    this.mHeadsetState = i;
  }

  public final void showToast() {
    Toast.makeText(
            new ContextThemeWrapper(this.mContext, R.style.Theme.DeviceDefault.Light),
            this.mContext.getResources().getString(R.string.managed_profile_label_badge),
            1)
        .show();
  }
}
