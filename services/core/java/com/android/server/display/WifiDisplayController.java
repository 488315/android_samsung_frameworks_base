package com.android.server.display;

import android.R;
import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.hardware.display.IWifiDisplayConnectionCallback;
import android.hardware.display.SemWifiDisplayConfig;
import android.hardware.display.SemWifiDisplayParameter;
import android.hardware.display.WifiDisplay;
import android.hardware.display.WifiDisplaySessionInfo;
import android.media.AudioManager;
import android.media.RemoteDisplay;
import android.net.ConnectivityManager;
import android.net.MacAddress;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pWfdInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.view.Surface;
import android.widget.Toast;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.android.internal.widget.LockPatternUtils;
import com.samsung.android.game.IGameManagerCallback;
import com.samsung.android.game.IGameManagerService;
import com.samsung.android.game.SemGameManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.wfd.WFDUibcManager;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifi.p2p.SemWifiP2pDevice;
import com.samsung.android.wifi.p2p.SemWifiP2pManager;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class WifiDisplayController implements DumpUtils.Dump {
  public WifiDisplay mAdvertisedDisplay;
  public int mAdvertisedDisplayFlags;
  public int mAdvertisedDisplayHeight;
  public Surface mAdvertisedDisplaySurface;
  public int mAdvertisedDisplayWidth;
  public AudioManager mAudioManager;
  public final BroadcastReceiver mAudioModeChangedReceiver;
  public IWifiDisplayConnectionCallback mCallback;
  public WifiP2pDevice mCancelingDevice;
  public WifiP2pDevice mConnectedDevice;
  public WifiP2pGroup mConnectedDeviceGroupInfo;
  public WifiP2pDevice mConnectingDevice;
  public int mConnectionRetriesLeft;
  public final Context mContext;
  public WifiP2pDevice mDesiredDevice;
  public boolean mDisconnectByUser;
  public WifiP2pDevice mDisconnectingDevice;
  public boolean mDiscoverPeersInProgress;
  public final IGameManagerCallback mGameCallback;
  public IGameManagerService mGameManagerService;
  public final Handler mHandler;
  public final BroadcastReceiver mInitiateReceiver;
  public boolean mIsDisplayVolumeControlSupported;
  public boolean mIsPortraitDisplay;
  public boolean mIsSupportInitiateMirroring;
  public boolean mIsUibcAvailable;
  public final Listener mListener;
  public String mLocalDeviceP2pMacAddress;
  public WifiDisplayMcfManager mMcfManager;
  public final RemoteDisplay.NativeListener mNativeListener;
  public NetworkInfo mNetworkInfo;
  public int mP2pFrequency;
  public final BroadcastReceiver mP2pReceiver;
  public final PersistentDataStore mPersistentDataStore;
  public int mPrevMusicStreamVolume;
  public RemoteDisplay mRemoteDisplay;
  public boolean mRemoteDisplayConnected;
  public String mRemoteDisplayInterface;
  public boolean mRenameCapablity;
  public boolean mScanRequested;
  public boolean mScreenWakeUpByUser;
  public final SemWifiP2pManager mSemWifiP2pManager;
  public WifiP2pDevice mThisDevice;
  public String mViewMode;
  public boolean mWfdEnabled;
  public boolean mWfdEnabling;
  public WFDUibcManager mWfdUibcManager;
  public final Runnable mWifiDisable;
  public boolean mWifiDisplayCertMode;
  public SemWifiDisplayConfig mWifiDisplayConfig;
  public boolean mWifiDisplayOnSetting;
  public WifiManager mWifiManager;
  public WifiP2pManager.Channel mWifiP2pChannel;
  public boolean mWifiP2pEnabled;
  public WifiP2pManager mWifiP2pManager;
  public boolean mWifiTurnedOnByController;
  public final RemoteDisplay.Listener remoteDisplayListener;
  public int mRequestedScanChannel = 1611;
  public int mRequestedScanInterval = 1000;
  public final ArrayList mAvailableWifiDisplayPeers = new ArrayList();
  public int mWifiDisplayWpsConfig = 4;
  public int mTransportMode = 0;
  public int mPrevAudioMode = 0;
  public List mParameterList = new ArrayList();
  public final Runnable mDiscoverPeers =
      new Runnable() { // from class: com.android.server.display.WifiDisplayController.15
        @Override // java.lang.Runnable
        public void run() {
          WifiDisplayController.this.tryDiscoverPeers(0, 10000);
        }
      };
  public final Runnable mConnectionTimeout =
      new Runnable() { // from class: com.android.server.display.WifiDisplayController.16
        @Override // java.lang.Runnable
        public void run() {
          if (WifiDisplayController.this.isVpnConnected()
              || WifiDisplayController.this.mConnectingDevice == null
              || WifiDisplayController.this.mConnectingDevice
                  != WifiDisplayController.this.mDesiredDevice) {
            return;
          }
          Slog.i(
              "WifiDisplayController",
              "Timed out waiting for Wifi display connection after 30 seconds: "
                  + WifiDisplayController.this.mConnectingDevice.deviceName);
          WifiDisplayController.this.handleConnectionFailure(2);
        }
      };
  public final Runnable mRtspTimeout =
      new Runnable() { // from class: com.android.server.display.WifiDisplayController.17
        @Override // java.lang.Runnable
        public void run() {
          if (WifiDisplayController.this.isVpnConnected()
              || WifiDisplayController.this.mConnectedDevice == null
              || WifiDisplayController.this.mRemoteDisplay == null
              || WifiDisplayController.this.mRemoteDisplayConnected) {
            return;
          }
          Slog.i(
              "WifiDisplayController",
              "Timed out waiting for Wifi display RTSP connection after 30 seconds: "
                  + WifiDisplayController.this.mConnectedDevice.deviceName);
          WifiDisplayController.this.handleConnectionFailure(2);
        }
      };

  public interface Listener {
    void onDisplayChanged(WifiDisplay wifiDisplay, Surface surface, int i, int i2, int i3);

    void onDisplayConnected(
        WifiDisplay wifiDisplay, Surface surface, int i, int i2, int i3, String str);

    void onDisplayConnecting(WifiDisplay wifiDisplay);

    void onDisplayConnectionFailed();

    void onDisplayDisconnected();

    void onDisplaySessionInfo(WifiDisplaySessionInfo wifiDisplaySessionInfo);

    void onFeatureStateChanged(int i);

    void onScanFinished();

    void onScanResults(WifiDisplay[] wifiDisplayArr);

    void onScanStarted();
  }

  public static boolean isPrimarySinkDeviceType(int i) {
    return i == 1 || i == 3;
  }

  public final int channelToFreq(int i) {
    if (i >= 1 && i <= 165) {
      return (i * 5) + (i <= 14 ? 2407 : 5000);
    }
    if (i != 0) {
      return i;
    }
    return -1;
  }

  public WifiDisplayController(
      Context context,
      Handler handler,
      Listener listener,
      PersistentDataStore persistentDataStore) {
    BroadcastReceiver broadcastReceiver =
        new BroadcastReceiver() { // from class: com.android.server.display.WifiDisplayController.19
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String action = intent.getAction();
            if ("android.net.wifi.p2p.STATE_CHANGED".equals(action)) {
              WifiDisplayController.this.handleStateChanged(
                  intent.getIntExtra("wifi_p2p_state", 1) == 2);
              return;
            }
            if ("android.net.wifi.p2p.PEERS_CHANGED".equals(action)) {
              WifiDisplayController.this.handlePeersChanged();
              String stringExtra = intent.getStringExtra("connectedDevAddress");
              WifiP2pDeviceList wifiP2pDeviceList =
                  (WifiP2pDeviceList) intent.getParcelableExtra("wifiP2pDeviceList");
              Intent intent2 = new Intent("com.samsung.intent.action.WIFI_P2P_PEERS_CHANGED");
              intent2.setPackage(KnoxCustomManagerService.SMARTMIRRORING_PACKAGE_NAME);
              intent2.putExtra("wifiP2pDeviceList", wifiP2pDeviceList);
              intent2.putExtra("connectedDevAddress", stringExtra);
              WifiDisplayController.this.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL);
              return;
            }
            if ("android.net.wifi.p2p.CONNECTION_STATE_CHANGE".equals(action)) {
              NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
              if (WifiDisplayController.this.mWifiDisplayConfig.getConnectionType() != 1) {
                Slog.w("WifiDisplayController", "do not handle p2p intent, action = " + action);
                return;
              }
              if (networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTING) {
                Slog.w("WifiDisplayController", "do not handle p2p CONNECTING state");
                return;
              }
              WifiDisplayController.this.handleConnectionChanged(networkInfo);
              WifiP2pInfo wifiP2pInfo = (WifiP2pInfo) intent.getParcelableExtra("wifiP2pInfo");
              Intent intent3 = new Intent("com.samsung.intent.action.WIFI_P2P_CONNECTION_CHANGED");
              intent3.setPackage(KnoxCustomManagerService.SMARTMIRRORING_PACKAGE_NAME);
              intent3.putExtra("networkInfo", networkInfo);
              intent3.putExtra("wifiP2pInfo", wifiP2pInfo);
              WifiDisplayController.this.mContext.sendBroadcastAsUser(intent3, UserHandle.ALL);
              return;
            }
            if ("android.net.wifi.p2p.THIS_DEVICE_CHANGED".equals(action)) {
              WifiDisplayController.this.mThisDevice =
                  (WifiP2pDevice) intent.getParcelableExtra("wifiP2pDevice");
              if (WifiDisplayController.this.mWfdEnabled
                  && WifiDisplayController.this.mDiscoverPeersInProgress
                  && WifiDisplayController.this.mWifiP2pChannel != null
                  && WifiDisplayController.this.mWifiP2pManager != null
                  && TextUtils.isEmpty(WifiDisplayController.this.mLocalDeviceP2pMacAddress)) {
                WifiDisplayController.this.mWifiP2pManager.requestDeviceInfo(
                    WifiDisplayController.this.mWifiP2pChannel,
                    new WifiP2pManager.DeviceInfoListener() { // from class:
                      // com.android.server.display.WifiDisplayController.19.1
                      @Override // android.net.wifi.p2p.WifiP2pManager.DeviceInfoListener
                      public void onDeviceInfoAvailable(WifiP2pDevice wifiP2pDevice) {
                        if (wifiP2pDevice == null
                            || TextUtils.isEmpty(wifiP2pDevice.deviceAddress)) {
                          return;
                        }
                        Log.d("WifiDisplayController", "onDeviceInfoAvailable");
                        WifiDisplayController.this.mLocalDeviceP2pMacAddress =
                            wifiP2pDevice.deviceAddress;
                      }
                    });
                return;
              }
              return;
            }
            if ("android.net.wifi.p2p.action.WIFI_P2P_REQUEST_RESPONSE_CHANGED".equals(action)) {
              boolean booleanExtra =
                  intent.getBooleanExtra("android.net.wifi.p2p.extra.REQUEST_RESPONSE", false);
              Slog.d(
                  "WifiDisplayController",
                  "Received WifiP2pManager.ACTION_WIFI_P2P_REQUEST_RESPONSE_CHANGED :"
                      + " requestAccepted = "
                      + booleanExtra);
              if (WifiDisplayController.this.mWifiDisplayCertMode
                  || WifiDisplayController.this.mConnectingDevice == null
                  || !WifiDisplayController.this.mWifiDisplayConfig.isPinRequest()) {
                return;
              }
              if (booleanExtra) {
                Slog.d("WifiDisplayController", "User accepted PIN connect");
                WifiDisplayController.this.mHandler.postDelayed(
                    WifiDisplayController.this.mConnectionTimeout, 30000L);
              } else {
                Slog.d("WifiDisplayController", "User cancelled PIN connect or timeout");
                WifiDisplayController.this.disconnect();
              }
            }
          }
        };
    this.mP2pReceiver = broadcastReceiver;
    this.mWifiDisable =
        new Runnable() { // from class: com.android.server.display.WifiDisplayController.30
          @Override // java.lang.Runnable
          public void run() {
            Slog.d(
                "WifiDisplayController",
                "try to turn off wifi, mWifiDisplayOnSetting : "
                    + WifiDisplayController.this.mWifiDisplayOnSetting);
            if (WifiDisplayController.this.mWifiDisplayOnSetting) {
              return;
            }
            if (WifiDisplayController.this.mWifiManager.isWifiEnabled()) {
              Slog.i("WifiDisplayController", "turn off wifi");
              WifiDisplayController.this.mWifiManager.setWifiEnabled(false);
            }
            WifiDisplayController.this.mWifiTurnedOnByController = false;
          }
        };
    this.mGameCallback =
        new IGameManagerCallback
            .Stub() { // from class: com.android.server.display.WifiDisplayController.31
          public void onGameFocusIn(String str) {
            Slog.d("WifiDisplayController", "onGameFocusIn. " + str);
            WifiDisplayController.this.setParam("lowl", Boolean.TRUE);
          }

          public void onGameFocusOut(String str) {
            Slog.d("WifiDisplayController", "onGameFocusOut. " + str);
            if (str.equals("KILL_YOURSELF")) {
              return;
            }
            WifiDisplayController.this.setParam("lowl", Boolean.FALSE);
          }
        };
    this.mAudioModeChangedReceiver =
        new BroadcastReceiver() { // from class: com.android.server.display.WifiDisplayController.32
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            if ("android.samsung.media.action.AUDIO_MODE".equals(intent.getAction())) {
              int intExtra = intent.getIntExtra("android.samsung.media.extra.AUDIO_MODE", 0);
              if (WifiDisplayController.this.mRemoteDisplayConnected) {
                WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                wifiDisplayController.sendVoipModeMessageIfNecessary(
                    wifiDisplayController.mAdvertisedDisplay, intExtra);
              }
            }
          }
        };
    this.mInitiateReceiver =
        new BroadcastReceiver() { // from class: com.android.server.display.WifiDisplayController.33
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String action = intent.getAction();
            if ("com.samsung.keyguard.KEYGUARD_STATE_UPDATE".equals(action)) {
              if (((PowerManager) WifiDisplayController.this.mContext.getSystemService("power"))
                  .isInteractive()) {
                boolean booleanExtra = intent.getBooleanExtra("bouncerShowing", false);
                Slog.d("WifiDisplayController", "Received KEYGUARD_STATE_UPDATE = " + booleanExtra);
                if (booleanExtra) {
                  WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                  wifiDisplayController.setParam("usls", wifiDisplayController.getScreenLockType());
                  return;
                } else if (((KeyguardManager)
                        WifiDisplayController.this.mContext.getSystemService("keyguard"))
                    .semIsKeyguardShowingAndNotOccluded()) {
                  WifiDisplayController.this.setParam("usls", "swipe");
                  return;
                } else {
                  WifiDisplayController.this.setParam("usls", "none");
                  return;
                }
              }
              return;
            }
            if ("android.intent.action.SCREEN_ON".equals(action)) {
              if (WifiDisplayController.this.mScreenWakeUpByUser) {
                Slog.i("WifiDisplayController", "Received ACTION_SCREEN_ON by user");
                if (((KeyguardManager)
                        WifiDisplayController.this.mContext.getSystemService("keyguard"))
                    .semIsKeyguardShowingAndNotOccluded()) {
                  WifiDisplayController.this.setParam("usls", "swipe");
                  return;
                }
                return;
              }
              Slog.i("WifiDisplayController", "Received ACTION_SCREEN_ON by controller");
              WifiDisplayController.this.mScreenWakeUpByUser = true;
              return;
            }
            if ("android.intent.action.SCREEN_OFF".equals(action)) {
              Slog.i("WifiDisplayController", "Received ACTION_SCREEN_OFF");
              WifiDisplayController.this.setParam("usls", "screen_off");
            }
          }
        };
    this.mNativeListener =
        new RemoteDisplay
            .NativeListener() { // from class: com.android.server.display.WifiDisplayController.34
          public void onNotify(int i, String str) {
            if (i == 1) {
              try {
                WifiDisplayController.this.mRenameCapablity =
                    new JSONObject(str).getBoolean("renameAvailable");
                return;
              } catch (JSONException e) {
                Slog.w("WifiDisplayController", e.toString());
                return;
              }
            }
            if (i == 2) {
              Slog.i("WifiDisplayController", "    onNotify received : NOTIFY_WFD_ENGINE_RESUME");
              WifiDisplayController.this.mContext.sendBroadcastAsUser(
                  new Intent("com.sec.android.smartview.WFD_ENGINE_RESUME"), UserHandle.ALL);
              return;
            }
            if (i == 3) {
              Slog.i("WifiDisplayController", "    onNotify received : NOTIFY_WFD_ENGINE_PAUSE");
              WifiDisplayController.this.mContext.sendBroadcastAsUser(
                  new Intent("com.sec.android.smartview.WFD_ENGINE_PAUSE"), UserHandle.ALL);
              return;
            }
            if (i == 7) {
              int parseInt = Integer.parseInt(str);
              WifiDisplayController.this.mTransportMode = parseInt % 2;
              StringBuilder sb = new StringBuilder();
              sb.append("    onNotify received : NOTIFY_TRANSPORT_MODE,  mTransportMode = ");
              sb.append(WifiDisplayController.this.mTransportMode == 1 ? "TCP" : "UDP");
              Slog.i("WifiDisplayController", sb.toString());
              if (parseInt <= 1) {
                WifiDisplayController.this.sendBroadcastTransportMode();
                return;
              }
              return;
            }
            if (i == 40) {
              try {
                WifiDisplayController.this.mIsSupportInitiateMirroring =
                    new JSONObject(str).getBoolean("isSupportInitiatedMirroring");
                Slog.i(
                    "WifiDisplayController",
                    "    onNotify received : NOTIFY_SUPPORT_INITIATE_MIRRORING = "
                        + WifiDisplayController.this.mIsSupportInitiateMirroring);
                return;
              } catch (JSONException e2) {
                Slog.w("WifiDisplayController", e2.toString());
                return;
              }
            }
            if (i == 50) {
              Slog.i("WifiDisplayController", "    onNotify received : NOTIFY_SCREEN_WAKE_UP");
              WifiDisplayController.this.wakeUpScreen();
              return;
            }
            if (i == 60) {
              try {
                WifiDisplayController.this.mIsDisplayVolumeControlSupported =
                    new JSONObject(str).getBoolean("isSupportDisplayVolumeControl");
                Log.i(
                    "WifiDisplayController",
                    "    onNotify received: [VolumeControl] NOTIFY_DISPLAY_VOLUME_SUPPORT = "
                        + WifiDisplayController.this.mIsDisplayVolumeControlSupported);
                WifiDisplayController.this.sendWifiDisplayVolumeSupportChangedBroadcast();
                if (WifiDisplayController.this.mIsDisplayVolumeControlSupported
                    && WifiDisplayController.this.mAdvertisedDisplay != null
                    && WifiDisplayController.this.mAdvertisedDisplay.getState() == 1) {
                  WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                  wifiDisplayController.mPrevMusicStreamVolume =
                      wifiDisplayController.mAudioManager.semGetStreamVolume(3, 25);
                  WifiDisplayController.this.mAudioManager.semSetStreamVolume(3, 15, 0, 25);
                  return;
                }
                return;
              } catch (JSONException e3) {
                Log.w("WifiDisplayController", e3.toString());
                return;
              }
            }
            if (i == 70) {
              Bundle bundle = new Bundle();
              try {
                JSONObject jSONObject = new JSONObject(str);
                bundle.putInt("minVol", jSONObject.getInt("minVol"));
                bundle.putInt("maxVol", jSONObject.getInt("maxVol"));
                bundle.putInt("curVol", jSONObject.getInt("curVol"));
                bundle.putBoolean("isMute", jSONObject.getBoolean("isMute"));
              } catch (JSONException e4) {
                Log.w("WifiDisplayController", e4.toString());
              }
              WifiDisplayController.this.sendDeviceVolumeChangedEvent(9, bundle);
              Log.i(
                  "WifiDisplayController", "    onNotify received :  NOTIFY_DISPLAY_VOLUME_STATUS");
              return;
            }
            if (i == 90) {
              Slog.i("WifiDisplayController", "    onNotify received : NOTIFY_SWTICH_TCP_FOR_DEMO");
              if (WifiDisplayController.this.mTransportMode != 1) {
                WifiDisplayController.this.mTransportMode = 1;
                WifiDisplayController.this.setParam("tcp", Boolean.TRUE);
                return;
              }
              return;
            }
            if (i == 100) {
              Slog.i(
                  "WifiDisplayController", "    onNotify received : NOTIFY_NOT_DEFINED : " + str);
              if (WifiDisplayController.this.mRemoteDisplayConnected) {
                WifiDisplayController wifiDisplayController2 = WifiDisplayController.this;
                wifiDisplayController2.sendWifiDisplayParameterEvent(
                    wifiDisplayController2.parseParametersFromEngine(
                        wifiDisplayController2.mAdvertisedDisplay, str));
                return;
              }
              return;
            }
            if (i == 9) {
              try {
                JSONObject jSONObject2 = new JSONObject(str);
                WifiDisplayController.this.mIsUibcAvailable =
                    jSONObject2.getBoolean("UibcAvailable");
                boolean z = jSONObject2.getBoolean("UibcSamsungMobile");
                Slog.i(
                    "WifiDisplayController",
                    "    onNotify received : NOTIFY_SUPPORT_UIBC = "
                        + WifiDisplayController.this.mIsUibcAvailable
                        + ", isSamsungMobile = "
                        + z);
                if (WifiDisplayController.this.mIsUibcAvailable) {
                  WifiDisplayController.this.mWfdUibcManager.start(false);
                  WifiDisplayController.this.mWfdUibcManager.setSinkDevice(z);
                } else {
                  WifiDisplayController.this.mWfdUibcManager.stop(false);
                }
                return;
              } catch (JSONException e5) {
                Slog.w("WifiDisplayController", e5.toString());
                return;
              }
            }
            if (i == 10) {
              int parseInt2 = Integer.parseInt(str);
              Slog.i(
                  "WifiDisplayController",
                  "    onNotify received : NOTIFY_ROTATION_CHANGED = " + parseInt2);
              WifiDisplayController.this.sendRemoteDisplayStateChangeEvent(
                  parseInt2 == 0 ? 8 : 9, 8);
              Intent intent = new Intent("com.samsung.intent.action.ROTATION_CHANGED");
              intent.putExtra("rotation", parseInt2);
              WifiDisplayController.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
              if (parseInt2 == 1 || parseInt2 == 3) {
                WifiDisplayController.this.mWfdUibcManager.setPortraitMode(true);
                return;
              } else {
                WifiDisplayController.this.mWfdUibcManager.setPortraitMode(false);
                return;
              }
            }
            Slog.w("WifiDisplayController", "onNotify Error,  msg : " + i);
          }
        };
    this.remoteDisplayListener =
        new RemoteDisplay
            .Listener() { // from class: com.android.server.display.WifiDisplayController.35
          public void onDisplayConnected(
              Surface surface, int i, int i2, int i3, int i4, String str) {
            if (WifiDisplayController.this.mConnectedDevice == null
                || WifiDisplayController.this.mRemoteDisplay == null) {
              return;
            }
            Slog.i(
                "WifiDisplayController",
                "onDisplayConnected, width : "
                    + i
                    + " , height : "
                    + i2
                    + " , flags : "
                    + WifiDisplayController.this.flagsToString(i3)
                    + "\nOpened RTSP connection with Wifi display: "
                    + WifiDisplayController.this.mConnectedDevice.deviceName);
            WifiDisplayController wifiDisplayController = WifiDisplayController.this;
            WifiDisplay wifiDisplayConfig =
                wifiDisplayController.setWifiDisplayConfig(
                    wifiDisplayController.createWifiDisplay(wifiDisplayController.mConnectedDevice),
                    i3);
            WifiDisplayController.this.advertiseDisplay(wifiDisplayConfig, surface, i, i2, i3);
            WifiDisplayController.this.mIsPortraitDisplay =
                ((i3 & 4) == 0 && (i3 & 8) == 0) ? false : true;
            WifiDisplayController.this.mWfdUibcManager.setUIBCNegotiagedResolution(i, i2);
            WifiDisplayController.this.mRemoteDisplayConnected = true;
            WifiDisplayController.this.mHandler.removeCallbacks(
                WifiDisplayController.this.mRtspTimeout);
            WifiDisplayController.this.sendSSRMRequestIntent(true);
            WifiDisplayController.this.sendEventToSemDeviceStatusListener(2, 6);
            WifiDisplayController.this.mParameterList = new ArrayList();
            WifiDisplayController wifiDisplayController2 = WifiDisplayController.this;
            wifiDisplayController2.parseParametersFromEngine(
                wifiDisplayController2.mAdvertisedDisplay, str);
            for (Map.Entry entry :
                WifiDisplayController.this.mAdvertisedDisplay.getParameters().entrySet()) {
              WifiDisplayController.this.mParameterList.add(
                  new SemWifiDisplayParameter((String) entry.getKey(), (String) entry.getValue()));
            }
            WifiDisplayController wifiDisplayController3 = WifiDisplayController.this;
            wifiDisplayController3.onConnectionSuccess(wifiDisplayController3.mParameterList);
            WifiDisplayController wifiDisplayController4 = WifiDisplayController.this;
            wifiDisplayController4.sendWifiDisplayParameterEvent(
                wifiDisplayController4.mParameterList);
            WifiDisplayController.this.sendWifiDisplayVolumeSupportChangedBroadcast();
            if (WifiDisplayController.this.mWifiDisplayCertMode) {
              Listener listener2 = WifiDisplayController.this.mListener;
              WifiDisplayController wifiDisplayController5 = WifiDisplayController.this;
              listener2.onDisplaySessionInfo(
                  wifiDisplayController5.getSessionInfo(
                      wifiDisplayController5.mConnectedDeviceGroupInfo, i4));
            }
            if (WifiDisplayController.this.mWifiDisplayConfig.isInitiateMirroringMode()) {
              WifiDisplayController.this.wakeUpScreen();
            }
            if (WifiDisplayController.this.mWifiDisplayConfig.getConnectionType() == 2
                || wifiDisplayConfig.getSamsungDeviceType() == 6) {
              WifiDisplayController.this.registerGameManagerCallback();
              IntentFilter intentFilter = new IntentFilter();
              intentFilter.addAction("android.samsung.media.action.AUDIO_MODE");
              WifiDisplayController.this.mContext.registerReceiver(
                  WifiDisplayController.this.mAudioModeChangedReceiver,
                  intentFilter,
                  null,
                  WifiDisplayController.this.mHandler);
              WifiDisplayController wifiDisplayController6 = WifiDisplayController.this;
              wifiDisplayController6.sendVoipModeMessageIfNecessary(
                  wifiDisplayController6.mAdvertisedDisplay,
                  WifiDisplayController.this.mAudioManager.getMode());
            }
            WifiDisplayController.this.handleWifiDisplayStateBroadcast(1);
            Slog.i(
                "WifiDisplayController",
                "onDisplayConnected wifidisplay "
                    + WifiDisplayController.this.mAdvertisedDisplay.toString());
          }

          public void onDisplayDisconnected() {
            Slog.d("WifiDisplayController", "onDisplayDisconnected");
            WifiDisplayController.this.sendSSRMRequestIntent(false);
            if (WifiDisplayController.this.mConnectedDevice != null
                && WifiDisplayController.this.mConnectedDevice
                    == WifiDisplayController.this.mDesiredDevice) {
              Slog.i(
                  "WifiDisplayController",
                  "Closed RTSP connection with Wifi display: "
                      + WifiDisplayController.this.mConnectedDevice.deviceName);
              WifiDisplayController.this.disconnect();
            }
            if (WifiDisplayController.this.mWifiDisplayConfig.isInitiateMirroringMode()) {
              try {
                WifiDisplayController.this.mContext.unregisterReceiver(
                    WifiDisplayController.this.mInitiateReceiver);
              } catch (IllegalArgumentException unused) {
                Log.w(
                    "WifiDisplayController",
                    "unregisterReceiver:: mInitiateReceiver not registered");
              }
            }
            WifiDisplayController.this.unregisterGameManagerCallback();
            try {
              WifiDisplayController.this.mContext.unregisterReceiver(
                  WifiDisplayController.this.mAudioModeChangedReceiver);
            } catch (IllegalArgumentException unused2) {
              Log.w(
                  "WifiDisplayController",
                  "unregisterReceiver:: mAudioModeChangedReceiver not registered");
            }
            WifiDisplayController.this.sendEventToSemDeviceStatusListener(0, 6);
          }

          public void onDisplayError(int i) {
            Slog.d("WifiDisplayController", "onDisplayError");
            WifiDisplayController.this.sendSSRMRequestIntent(false);
            if (i == 3) {
              Slog.e("WifiDisplayController", "HDCP Key is no written");
              WifiDisplayController.this.handleConnectionFailure(3);
              return;
            }
            if (WifiDisplayController.this.isVpnConnected()
                || WifiDisplayController.this.mConnectedDevice == null
                || WifiDisplayController.this.mConnectedDevice
                    != WifiDisplayController.this.mDesiredDevice) {
              return;
            }
            Slog.e(
                "WifiDisplayController",
                "Lost RTSP connection with Wifi display due to error "
                    + i
                    + ": "
                    + WifiDisplayController.this.mConnectedDevice.deviceName);
            WifiDisplayController.this.handleConnectionFailure(4);
          }

          public void onDisplayChanged(Surface surface, int i, int i2, int i3) {
            Slog.d("WifiDisplayController", "onDisplayChanged, width : " + i + ", height : " + i2);
            if (WifiDisplayController.this.mConnectedDevice == null
                || WifiDisplayController.this.mRemoteDisplay == null
                || WifiDisplayController.this.mAdvertisedDisplay == null) {
              return;
            }
            WifiDisplayController wifiDisplayController = WifiDisplayController.this;
            wifiDisplayController.advertiseDisplay(
                wifiDisplayController.mAdvertisedDisplay, surface, i, i2, i3);
          }
        };
    this.mContext = context;
    this.mHandler = handler;
    this.mListener = listener;
    this.mPersistentDataStore = persistentDataStore;
    this.mWifiDisplayConfig = new SemWifiDisplayConfig();
    this.mWifiP2pManager = (WifiP2pManager) context.getSystemService("wifip2p");
    this.mSemWifiP2pManager = (SemWifiP2pManager) context.getSystemService("sem_wifi_p2p");
    this.mWifiManager = (WifiManager) context.getSystemService("wifi");
    this.mAudioManager = (AudioManager) context.getSystemService("audio");
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.net.wifi.p2p.STATE_CHANGED");
    intentFilter.addAction("android.net.wifi.p2p.PEERS_CHANGED");
    intentFilter.addAction("android.net.wifi.p2p.CONNECTION_STATE_CHANGE");
    intentFilter.addAction("android.net.wifi.p2p.THIS_DEVICE_CHANGED");
    intentFilter.addAction("android.net.wifi.p2p.action.WIFI_P2P_REQUEST_RESPONSE_CHANGED");
    context.registerReceiver(broadcastReceiver, intentFilter, null, handler);
    ContentObserver contentObserver =
        new ContentObserver(
            handler) { // from class: com.android.server.display.WifiDisplayController.1
          @Override // android.database.ContentObserver
          public void onChange(boolean z, Uri uri) {
            WifiDisplayController.this.updateSettings();
          }
        };
    ContentResolver contentResolver = context.getContentResolver();
    contentResolver.registerContentObserver(
        Settings.Global.getUriFor("wifi_display_on"), false, contentObserver);
    contentResolver.registerContentObserver(
        Settings.Global.getUriFor("wifi_display_certification_on"), false, contentObserver);
    contentResolver.registerContentObserver(
        Settings.Global.getUriFor("wifi_display_wps_config"), false, contentObserver);
    this.mWfdUibcManager = new WFDUibcManager(context);
    this.mMcfManager = new WifiDisplayMcfManager(context, handler, persistentDataStore);
    Settings.Global.putInt(context.getContentResolver(), "wifi_display_on", 0);
  }

  public final void retrieveWifiP2pManagerAndChannel() {
    WifiP2pManager wifiP2pManager;
    if (this.mWifiP2pManager == null) {
      this.mWifiP2pManager = (WifiP2pManager) this.mContext.getSystemService("wifip2p");
    }
    if (this.mWifiP2pChannel != null || (wifiP2pManager = this.mWifiP2pManager) == null) {
      return;
    }
    this.mWifiP2pChannel =
        wifiP2pManager.initialize(this.mContext, this.mHandler.getLooper(), null);
  }

  public final void updateSettings() {
    ContentResolver contentResolver = this.mContext.getContentResolver();
    this.mWifiDisplayOnSetting = Settings.Global.getInt(contentResolver, "wifi_display_on", 0) != 0;
    boolean z = Settings.Global.getInt(contentResolver, "wifi_display_certification_on", 0) != 0;
    this.mWifiDisplayCertMode = z;
    this.mWifiDisplayWpsConfig = 4;
    if (z) {
      this.mWifiDisplayWpsConfig =
          Settings.Global.getInt(contentResolver, "wifi_display_wps_config", 4);
    }
    if (this.mWifiDisplayOnSetting) {
      if ("0".equals(SystemProperties.get("secmm.wfd.running", "0"))) {
        SystemProperties.set("secmm.wfd.running", "1");
        Slog.d("WifiDisplayController", "[SECMM] Starting remotedisplay from Controller..");
      }
      retrieveWifiP2pManagerAndChannel();
    } else {
      disableP2p();
    }
    updateWfdEnableState();
  }

  public void dump(PrintWriter printWriter, String str) {
    printWriter.println("mWifiDisplayOnSetting=" + this.mWifiDisplayOnSetting);
    printWriter.println("mWifiP2pEnabled=" + this.mWifiP2pEnabled);
    printWriter.println("mWfdEnabled=" + this.mWfdEnabled);
    printWriter.println("mWfdEnabling=" + this.mWfdEnabling);
    printWriter.println("mNetworkInfo=" + this.mNetworkInfo);
    printWriter.println("mScanRequested=" + this.mScanRequested);
    printWriter.println("mDiscoverPeersInProgress=" + this.mDiscoverPeersInProgress);
    printWriter.println("mDesiredDevice=" + describeWifiP2pDevice(this.mDesiredDevice));
    printWriter.println("mConnectingDisplay=" + describeWifiP2pDevice(this.mConnectingDevice));
    printWriter.println(
        "mDisconnectingDisplay=" + describeWifiP2pDevice(this.mDisconnectingDevice));
    printWriter.println("mCancelingDisplay=" + describeWifiP2pDevice(this.mCancelingDevice));
    printWriter.println("mConnectedDevice=" + describeWifiP2pDevice(this.mConnectedDevice));
    printWriter.println("mConnectionRetriesLeft=" + this.mConnectionRetriesLeft);
    printWriter.println("mRemoteDisplay=" + this.mRemoteDisplay);
    printWriter.println("mRemoteDisplayInterface=" + this.mRemoteDisplayInterface);
    printWriter.println("mRemoteDisplayConnected=" + this.mRemoteDisplayConnected);
    printWriter.println("mAdvertisedDisplay=" + this.mAdvertisedDisplay);
    printWriter.println("mAdvertisedDisplaySurface=" + this.mAdvertisedDisplaySurface);
    printWriter.println("mAdvertisedDisplayWidth=" + this.mAdvertisedDisplayWidth);
    printWriter.println("mAdvertisedDisplayHeight=" + this.mAdvertisedDisplayHeight);
    printWriter.println("mAdvertisedDisplayFlags=" + this.mAdvertisedDisplayFlags);
    printWriter.println(
        "mAvailableWifiDisplayPeers: size=" + this.mAvailableWifiDisplayPeers.size());
    Iterator it = this.mAvailableWifiDisplayPeers.iterator();
    while (it.hasNext()) {
      printWriter.println("  " + describeWifiP2pDevice((WifiP2pDevice) it.next()));
    }
  }

  public void requestStartScan(boolean z) {
    if (this.mScanRequested) {
      return;
    }
    this.mScanRequested = true;
    if (z) {
      enableP2p();
    }
    updateScanState();
  }

  public void requestStopScan() {
    if (this.mScanRequested) {
      this.mScanRequested = false;
      updateScanState();
    }
  }

  public void requestConnect(String str) {
    Iterator it = this.mAvailableWifiDisplayPeers.iterator();
    while (it.hasNext()) {
      WifiP2pDevice wifiP2pDevice = (WifiP2pDevice) it.next();
      if (wifiP2pDevice.deviceAddress.equals(str)) {
        Slog.d("WifiDisplayController", "requestConnect() " + wifiP2pDevice);
        connect(wifiP2pDevice);
      }
    }
    if (this.mDesiredDevice == null) {
      Log.e("WifiDisplayController", "Can not found desired device");
      onConnectionFailure(1);
    }
  }

  public void requestPause() {
    RemoteDisplay remoteDisplay = this.mRemoteDisplay;
    if (remoteDisplay != null) {
      remoteDisplay.pause();
    }
  }

  public void requestResume() {
    RemoteDisplay remoteDisplay = this.mRemoteDisplay;
    if (remoteDisplay != null) {
      remoteDisplay.resume();
    }
  }

  public void requestDisconnect() {
    this.mDisconnectByUser = true;
    disconnect();
  }

  public final void updateWfdEnableState() {
    Log.d(
        "WifiDisplayController",
        "updateWfdEnableState:: mWifiDisplayOnSetting = "
            + this.mWifiDisplayOnSetting
            + " mWifiP2pEnabled = "
            + this.mWifiP2pEnabled
            + " mWfdEnabled = "
            + this.mWfdEnabled
            + " mWfdEnabling = "
            + this.mWfdEnabling);
    if (this.mWifiDisplayOnSetting) {
      if (!this.mWifiP2pEnabled || this.mWfdEnabled || this.mWfdEnabling) {
        return;
      }
      this.mWfdEnabling = true;
      this.mSemWifiP2pManager.setScreenSharing(true);
      WifiP2pWfdInfo wifiP2pWfdInfo = new WifiP2pWfdInfo();
      wifiP2pWfdInfo.setEnabled(true);
      wifiP2pWfdInfo.setDeviceType(0);
      wifiP2pWfdInfo.setSessionAvailable(true);
      wifiP2pWfdInfo.setContentProtectionSupported(true);
      wifiP2pWfdInfo.setControlPort(7236);
      wifiP2pWfdInfo.setMaxThroughput(50);
      this.mWifiP2pManager.setWfdInfo(
          this.mWifiP2pChannel,
          wifiP2pWfdInfo,
          new WifiP2pManager
              .ActionListener() { // from class: com.android.server.display.WifiDisplayController.2
            @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
            public void onSuccess() {
              if (WifiDisplayController.this.mWfdEnabling) {
                WifiDisplayController.this.mWfdEnabling = false;
                WifiDisplayController.this.mWfdEnabled = true;
                WifiDisplayController.this.reportFeatureState();
                WifiDisplayController.this.updateScanState();
              }
            }

            @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
            public void onFailure(int i) {
              WifiDisplayController.this.mWfdEnabling = false;
            }
          });
      return;
    }
    if (this.mWfdEnabled || this.mWfdEnabling) {
      this.mSemWifiP2pManager.setScreenSharing(false);
      WifiP2pWfdInfo wifiP2pWfdInfo2 = new WifiP2pWfdInfo();
      wifiP2pWfdInfo2.setEnabled(false);
      this.mWifiP2pManager.setWfdInfo(
          this.mWifiP2pChannel,
          wifiP2pWfdInfo2,
          new WifiP2pManager
              .ActionListener() { // from class: com.android.server.display.WifiDisplayController.3
            @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
            public void onFailure(int i) {}

            @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
            public void onSuccess() {}
          });
    }
    this.mWfdEnabling = false;
    this.mWfdEnabled = false;
    reportFeatureState();
    updateScanState();
    disconnect();
  }

  public final void reportFeatureState() {
    final int computeFeatureState = computeFeatureState();
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.WifiDisplayController.4
          @Override // java.lang.Runnable
          public void run() {
            WifiDisplayController.this.mListener.onFeatureStateChanged(computeFeatureState);
          }
        });
  }

  public final int computeFeatureState() {
    if (this.mWifiP2pEnabled) {
      return this.mWifiDisplayOnSetting ? 3 : 2;
    }
    return 1;
  }

  public final void updateScanState() {
    Log.d(
        "WifiDisplayController",
        "updateScanState() mScanRequested = "
            + this.mScanRequested
            + ", mWfdEnabled = "
            + this.mWfdEnabled
            + ", mDiscoverPeersInProgress = "
            + this.mDiscoverPeersInProgress);
    if (this.mWfdEnabled
        && this.mScanRequested
        && (this.mDesiredDevice == null || this.mRemoteDisplayConnected)) {
      if (this.mDiscoverPeersInProgress) {
        return;
      }
      Log.i(
          "WifiDisplayController",
          "Starting Wifi display scan, mRemoteDisplayConnected = " + this.mRemoteDisplayConnected);
      this.mDiscoverPeersInProgress = true;
      handleScanStarted();
      tryDiscoverPeers(this.mRequestedScanChannel, this.mRequestedScanInterval);
      return;
    }
    if (this.mDiscoverPeersInProgress) {
      this.mHandler.removeCallbacks(this.mDiscoverPeers);
      WifiP2pDevice wifiP2pDevice = this.mDesiredDevice;
      if (wifiP2pDevice == null || wifiP2pDevice == this.mConnectedDevice) {
        Slog.i("WifiDisplayController", "Stopping Wifi display scan.");
        this.mDiscoverPeersInProgress = false;
        this.mRequestedScanChannel = 1611;
        stopPeerDiscovery();
        handleScanFinished();
      }
    }
  }

  public final void tryDiscoverPeers(int i, int i2) {
    Slog.e(
        "WifiDisplayController",
        "tryDiscoverPeers requestChannel="
            + i
            + ", isChannelConstrainedDiscoverySupported="
            + this.mWifiP2pManager.isChannelConstrainedDiscoverySupported());
    if (i == 0) {
      this.mWifiP2pManager.discoverPeers(this.mWifiP2pChannel, null);
    } else if (!this.mWifiP2pManager.isChannelConstrainedDiscoverySupported()) {
      this.mSemWifiP2pManager.discoverPeersOnSpecificChannel(
          i, (SemWifiP2pManager.ActionListener) null);
    } else if (i == 1611) {
      this.mWifiP2pManager.discoverPeersOnSocialChannels(this.mWifiP2pChannel, null);
    } else {
      this.mWifiP2pManager.discoverPeersOnSpecificFrequency(
          this.mWifiP2pChannel, channelToFreq(i), null);
    }
    this.mHandler.postDelayed(this.mDiscoverPeers, i2);
  }

  public final void stopPeerDiscovery() {
    Log.d("WifiDisplayController", "stopPeerDiscovery");
    this.mWifiP2pManager.stopPeerDiscovery(this.mWifiP2pChannel, null);
  }

  public final void requestPeers() {
    this.mWifiP2pManager.requestPeers(
        this.mWifiP2pChannel,
        new WifiP2pManager
            .PeerListListener() { // from class: com.android.server.display.WifiDisplayController.5
          @Override // android.net.wifi.p2p.WifiP2pManager.PeerListListener
          public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
            Slog.d("WifiDisplayController", "Received list of peers.");
            WifiDisplayController.this.mAvailableWifiDisplayPeers.clear();
            for (WifiP2pDevice wifiP2pDevice : wifiP2pDeviceList.getDeviceList()) {
              Slog.d(
                  "WifiDisplayController",
                  "  " + WifiDisplayController.describeWifiP2pDevice(wifiP2pDevice));
              if (WifiDisplayController.isWifiDisplay(wifiP2pDevice)
                  || wifiP2pDevice.equals(WifiDisplayController.this.mConnectingDevice)) {
                WifiDisplayController.this.mAvailableWifiDisplayPeers.add(wifiP2pDevice);
              }
            }
            if (WifiDisplayController.this.mDiscoverPeersInProgress) {
              WifiDisplayController.this.handleScanResults();
            }
          }
        });
  }

  public final void handleScanStarted() {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.WifiDisplayController.6
          @Override // java.lang.Runnable
          public void run() {
            WifiDisplayController.this.mListener.onScanStarted();
          }
        });
  }

  public final void handleScanResults() {
    SemWifiP2pDevice semWifiP2pDevice;
    int size = this.mAvailableWifiDisplayPeers.size();
    final WifiDisplay[] wifiDisplayArr = (WifiDisplay[]) WifiDisplay.CREATOR.newArray(size);
    for (int i = 0; i < size; i++) {
      WifiP2pDevice wifiP2pDevice = (WifiP2pDevice) this.mAvailableWifiDisplayPeers.get(i);
      wifiDisplayArr[i] = createWifiDisplay(wifiP2pDevice);
      if (!wifiP2pDevice.getVendorElements().isEmpty()
          && (semWifiP2pDevice = this.mSemWifiP2pManager.getSemWifiP2pDevice(wifiP2pDevice))
              != null) {
        String screenSharingHashedDi = semWifiP2pDevice.getScreenSharingHashedDi();
        if (!TextUtils.isEmpty(screenSharingHashedDi)) {
          Slog.d(
              "WifiDisplayController",
              "setScreenSharingHashedDi, deviceName : " + wifiP2pDevice.deviceName);
          wifiDisplayArr[i].setScreenSharingHashedDi(screenSharingHashedDi);
        }
      }
      updateDesiredDevice(wifiP2pDevice);
    }
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.WifiDisplayController.7
          @Override // java.lang.Runnable
          public void run() {
            WifiDisplayController.this.mListener.onScanResults(wifiDisplayArr);
          }
        });
  }

  public final void handleScanFinished() {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.WifiDisplayController.8
          @Override // java.lang.Runnable
          public void run() {
            WifiDisplayController.this.mListener.onScanFinished();
          }
        });
  }

  public final void updateDesiredDevice(WifiP2pDevice wifiP2pDevice) {
    String str = wifiP2pDevice.deviceAddress;
    WifiP2pDevice wifiP2pDevice2 = this.mDesiredDevice;
    if (wifiP2pDevice2 == null || !wifiP2pDevice2.deviceAddress.equals(str)) {
      return;
    }
    this.mDesiredDevice.update(wifiP2pDevice);
    WifiDisplay wifiDisplay = this.mAdvertisedDisplay;
    if (wifiDisplay == null || !wifiDisplay.getDeviceAddress().equals(str)) {
      return;
    }
    readvertiseDisplay(createWifiDisplay(this.mDesiredDevice));
  }

  public final void connect(WifiP2pDevice wifiP2pDevice) {
    WifiP2pDevice wifiP2pDevice2 = this.mDesiredDevice;
    if (wifiP2pDevice2 == null
        || wifiP2pDevice2.deviceAddress.equals(wifiP2pDevice.deviceAddress)) {
      WifiP2pDevice wifiP2pDevice3 = this.mConnectedDevice;
      if (wifiP2pDevice3 == null
          || wifiP2pDevice3.deviceAddress.equals(wifiP2pDevice.deviceAddress)
          || this.mDesiredDevice != null) {
        if (!this.mWfdEnabled) {
          Slog.i(
              "WifiDisplayController",
              "Ignoring request to connect to Wifi display because the  feature is currently"
                  + " disabled: "
                  + wifiP2pDevice.deviceName);
          return;
        }
        this.mDesiredDevice = wifiP2pDevice;
        this.mConnectionRetriesLeft = 3;
        updateConnection();
      }
    }
  }

  public final void disconnect() {
    Log.d("WifiDisplayController", "disconnect() : " + this.mConnectedDevice);
    if (!this.mRemoteDisplayConnected) {
      onConnectionFailure(1);
    }
    if (this.mWifiDisplayConfig.isSkipPinNumberConfirm()) {
      Slog.d("WifiDisplayController", "clear setPreparedAccountPin");
      this.mSemWifiP2pManager.setPreparedAccountPin(
          (String) null,
          (String) null,
          (String) null,
          new SemWifiP2pManager
              .ActionListener() { // from class: com.android.server.display.WifiDisplayController.9
            public void onFailure(int i) {}

            public void onSuccess() {}
          });
    }
    if (this.mWifiDisplayConfig.getConnectionType() == 2
        || this.mWifiDisplayConfig.getConnectionType() == 3) {
      if (this.mRemoteDisplay != null) {
        if (this.mWifiDisplayConfig.getConnectionType() == 3) {
          setParam("fcdc", Boolean.TRUE);
        }
        sendEventToSemDeviceStatusListener(3, 6);
        this.mRemoteDisplay.dispose();
        this.mRemoteDisplay = null;
        this.mRemoteDisplayInterface = null;
        this.mRemoteDisplayConnected = false;
        unadvertiseDisplay();
        return;
      }
      return;
    }
    this.mDesiredDevice = null;
    updateConnection();
  }

  public final void updateConnection() {
    WifiP2pDevice wifiP2pDevice;
    if (this.mWifiDisplayConfig.getConnectionType() != 1) {
      return;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("updateConnection, mConnectedDevice = ");
    sb.append(this.mConnectedDevice != null);
    sb.append(", mRemoteDisplay = ");
    sb.append(this.mRemoteDisplay != null);
    Slog.d("WifiDisplayController", sb.toString());
    retrieveWifiP2pManagerAndChannel();
    updateScanState();
    if (this.mRemoteDisplay != null
        && (wifiP2pDevice = this.mConnectedDevice) != null
        && wifiP2pDevice != this.mDesiredDevice) {
      Slog.i(
          "WifiDisplayController",
          "Stopped listening for RTSP connection on "
              + this.mRemoteDisplayInterface
              + " from Wifi display: "
              + this.mConnectedDevice.deviceName);
      this.mRemoteDisplay.dispose();
      this.mRemoteDisplay = null;
      this.mRemoteDisplayInterface = null;
      this.mRemoteDisplayConnected = false;
      this.mHandler.removeCallbacks(this.mRtspTimeout);
      this.mWifiP2pManager.setMiracastMode(0);
      unadvertiseDisplay();
    }
    if (this.mDisconnectingDevice != null) {
      return;
    }
    WifiP2pDevice wifiP2pDevice2 = this.mConnectedDevice;
    if (wifiP2pDevice2 != null && wifiP2pDevice2 != this.mDesiredDevice) {
      Slog.i(
          "WifiDisplayController",
          "Disconnecting from Wifi display: " + this.mConnectedDevice.deviceName);
      this.mDisconnectingDevice = this.mConnectedDevice;
      this.mConnectedDevice = null;
      this.mConnectedDeviceGroupInfo = null;
      unadvertiseDisplay();
      final WifiP2pDevice wifiP2pDevice3 = this.mDisconnectingDevice;
      WifiP2pGroup wifiP2pGroup = this.mConnectedDeviceGroupInfo;
      if (wifiP2pGroup != null && wifiP2pGroup.isGroupOwner()) {
        createConnectConfig(wifiP2pDevice3);
        this.mWifiP2pManager.removeClient(
            this.mWifiP2pChannel,
            MacAddress.fromString(wifiP2pDevice3.deviceAddress),
            new WifiP2pManager
                .ActionListener() { // from class:
                                    // com.android.server.display.WifiDisplayController.10
              @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
              public void onSuccess() {
                Log.i(
                    "WifiDisplayController",
                    "Disconnected from Wifi display: " + wifiP2pDevice3.deviceName);
                next();
              }

              @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
              public void onFailure(int i) {
                Log.i(
                    "WifiDisplayController",
                    "Failed to disconnect from Wifi display: "
                        + wifiP2pDevice3.deviceName
                        + ", reason="
                        + i);
                next();
              }

              public final void next() {
                if (WifiDisplayController.this.mDisconnectingDevice == wifiP2pDevice3) {
                  WifiDisplayController.this.unadvertiseDisplay();
                  WifiDisplayController.this.mDisconnectingDevice = null;
                  WifiDisplayController.this.updateConnection();
                }
              }
            });
      } else {
        this.mWifiP2pManager.removeGroup(
            this.mWifiP2pChannel,
            new WifiP2pManager
                .ActionListener() { // from class:
                                    // com.android.server.display.WifiDisplayController.11
              @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
              public void onSuccess() {
                Slog.i(
                    "WifiDisplayController",
                    "Disconnected from Wifi display: " + wifiP2pDevice3.deviceName);
                next();
              }

              @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
              public void onFailure(int i) {
                Slog.i(
                    "WifiDisplayController",
                    "Failed to disconnect from Wifi display: "
                        + wifiP2pDevice3.deviceName
                        + ", reason="
                        + i);
                next();
              }

              public final void next() {
                if (WifiDisplayController.this.mDisconnectingDevice == wifiP2pDevice3) {
                  WifiDisplayController.this.mDisconnectingDevice = null;
                  WifiDisplayController.this.updateConnection();
                }
              }
            });
        return;
      }
    }
    if (this.mCancelingDevice != null) {
      return;
    }
    WifiP2pDevice wifiP2pDevice4 = this.mConnectingDevice;
    if (wifiP2pDevice4 != null && wifiP2pDevice4 != this.mDesiredDevice) {
      Slog.i(
          "WifiDisplayController",
          "Canceling connection to Wifi display: " + this.mConnectingDevice.deviceName);
      this.mCancelingDevice = this.mConnectingDevice;
      this.mConnectingDevice = null;
      unadvertiseDisplay();
      this.mHandler.removeCallbacks(this.mConnectionTimeout);
      WifiP2pDevice wifiP2pDevice5 = this.mCancelingDevice;
      this.mWifiP2pManager.cancelConnect(this.mWifiP2pChannel, null);
      if (this.mCancelingDevice == wifiP2pDevice5) {
        this.mCancelingDevice = null;
        updateConnection();
        this.mHandler.removeCallbacks(this.mConnectionTimeout);
        if (this.mWifiDisplayConfig.isInitiateMirroringMode()) {
          try {
            this.mContext.unregisterReceiver(this.mInitiateReceiver);
            return;
          } catch (IllegalArgumentException unused) {
            Log.w("WifiDisplayController", "unregisterReceiver:: mInitiateReceiver not registered");
            return;
          }
        }
        return;
      }
      return;
    }
    if (this.mDesiredDevice == null) {
      if (this.mWifiDisplayCertMode) {
        this.mListener.onDisplaySessionInfo(getSessionInfo(this.mConnectedDeviceGroupInfo, 0));
      }
      unadvertiseDisplay();
      return;
    }
    WifiP2pDevice wifiP2pDevice6 = this.mConnectedDevice;
    if (wifiP2pDevice6 == null && wifiP2pDevice4 == null) {
      Slog.i(
          "WifiDisplayController", "Connecting to Wifi display: " + this.mDesiredDevice.deviceName);
      WifiP2pDevice wifiP2pDevice7 = this.mDesiredDevice;
      this.mConnectingDevice = wifiP2pDevice7;
      final WifiP2pConfig createConnectConfig = createConnectConfig(wifiP2pDevice7);
      WifiDisplay createWifiDisplay = createWifiDisplay(this.mConnectingDevice);
      createWifiDisplay.setMode(this.mWifiDisplayConfig.getMode());
      advertiseDisplay(createWifiDisplay, null, 0, 0, 0);
      if (this.mWifiDisplayConfig.isSkipPinNumberConfirm()) {
        SemWifiDisplayParameter parameter =
            this.mWifiDisplayConfig.getParameter("initparams", "tv_device_id");
        if (parameter != null) {
          setPreparedAccountPin(
              parameter.getValue(),
              this.mConnectingDevice.deviceAddress,
              new SemWifiP2pManager
                  .ActionListener() { // from class:
                                      // com.android.server.display.WifiDisplayController.12
                public void onSuccess() {
                  Slog.i("WifiDisplayController", "setPreparedAccountPin onSuccess");
                  WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                  wifiDisplayController.connectP2p(
                      wifiDisplayController.mDesiredDevice, createConnectConfig);
                }

                public void onFailure(int i) {
                  Slog.i("WifiDisplayController", "setPreparedAccountPin onFailure");
                  WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                  wifiDisplayController.connectP2p(
                      wifiDisplayController.mDesiredDevice, createConnectConfig);
                }
              });
          this.mWifiDisplayConfig.removeParameter("initparams", "tv_device_id");
          return;
        }
        return;
      }
      connectP2p(this.mDesiredDevice, createConnectConfig);
      return;
    }
    if (wifiP2pDevice6 == null || this.mRemoteDisplay != null) {
      return;
    }
    Inet4Address interfaceAddress = getInterfaceAddress(this.mConnectedDeviceGroupInfo);
    if (interfaceAddress == null) {
      Slog.i(
          "WifiDisplayController",
          "Failed to get local interface address for communicating with Wifi display: "
              + this.mConnectedDevice.deviceName);
      handleConnectionFailure(1);
      return;
    }
    this.mWifiP2pManager.setMiracastMode(1);
    String str =
        interfaceAddress.getHostAddress()
            + XmlUtils.STRING_ARRAY_SEPARATOR
            + getPortNumber(this.mConnectedDevice);
    this.mRemoteDisplayInterface = str;
    Slog.i(
        "WifiDisplayController",
        "Listening for RTSP connection on "
            + str
            + " from Wifi display: "
            + this.mConnectedDevice.deviceName);
    this.mRemoteDisplay =
        RemoteDisplay.listen(
            str,
            this.remoteDisplayListener,
            this.mHandler,
            this.mContext.getOpPackageName(),
            getEngineParameters(),
            this.mNativeListener);
    this.mHandler.postDelayed(this.mRtspTimeout, (this.mWifiDisplayCertMode ? 120 : 30) * 1000);
  }

  public final void connectP2p(final WifiP2pDevice wifiP2pDevice, WifiP2pConfig wifiP2pConfig) {
    Slog.i("WifiDisplayController", "connectP2p");
    this.mWifiP2pManager.connect(
        this.mWifiP2pChannel,
        wifiP2pConfig,
        new WifiP2pManager
            .ActionListener() { // from class: com.android.server.display.WifiDisplayController.13
          @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
          public void onSuccess() {
            Slog.i(
                "WifiDisplayController",
                "Initiated connection to Wifi display: " + wifiP2pDevice.deviceName);
            if (WifiDisplayController.this.mWifiDisplayConfig.isPinRequest()) {
              return;
            }
            WifiDisplayController.this.mHandler.postDelayed(
                WifiDisplayController.this.mConnectionTimeout, 30000L);
          }

          @Override // android.net.wifi.p2p.WifiP2pManager.ActionListener
          public void onFailure(int i) {
            if (WifiDisplayController.this.mConnectingDevice == wifiP2pDevice) {
              Slog.i(
                  "WifiDisplayController",
                  "Failed to initiate connection to Wifi display: "
                      + wifiP2pDevice.deviceName
                      + ", reason="
                      + i);
              WifiDisplayController.this.handleConnectionFailure(6);
              WifiDisplayController.this.mConnectingDevice = null;
            }
          }
        });
  }

  public final WifiDisplaySessionInfo getSessionInfo(WifiP2pGroup wifiP2pGroup, int i) {
    if (wifiP2pGroup == null) {
      return null;
    }
    Inet4Address interfaceAddress = getInterfaceAddress(wifiP2pGroup);
    return new WifiDisplaySessionInfo(
        !wifiP2pGroup.getOwner().deviceAddress.equals(this.mThisDevice.deviceAddress),
        i,
        wifiP2pGroup.getOwner().deviceAddress + " " + wifiP2pGroup.getNetworkName(),
        wifiP2pGroup.getPassphrase(),
        interfaceAddress != null ? interfaceAddress.getHostAddress() : "");
  }

  public final void handleStateChanged(boolean z) {
    this.mWifiP2pEnabled = z;
    if (this.mWifiDisplayConfig.getConnectionType() != 1) {
      Slog.w(
          "WifiDisplayController", "handleStateChanged, do not handle p2p intent, enabled = " + z);
      return;
    }
    if (this.mWifiDisplayOnSetting && !z) {
      Slog.w(
          "WifiDisplayController", "handleStateChanged, wifi p2p state turn off, but wfd state on");
      this.mScanRequested = false;
      Settings.Global.putInt(this.mContext.getContentResolver(), "wifi_display_on", 0);
      return;
    }
    updateWfdEnableState();
  }

  public final void handlePeersChanged() {
    if (this.mDiscoverPeersInProgress) {
      requestPeers();
    }
  }

  public static boolean contains(WifiP2pGroup wifiP2pGroup, WifiP2pDevice wifiP2pDevice) {
    return wifiP2pGroup != null
        && (wifiP2pGroup.getOwner().equals(wifiP2pDevice)
            || wifiP2pGroup.getClientList().contains(wifiP2pDevice));
  }

  public final void handleConnectionChanged(NetworkInfo networkInfo) {
    this.mNetworkInfo = networkInfo;
    if (this.mWfdEnabled && networkInfo.isConnected()) {
      if (this.mDesiredDevice != null || this.mWifiDisplayCertMode) {
        this.mWifiP2pManager.requestGroupInfo(
            this.mWifiP2pChannel,
            new WifiP2pManager.GroupInfoListener() { // from class:
              // com.android.server.display.WifiDisplayController.14
              @Override // android.net.wifi.p2p.WifiP2pManager.GroupInfoListener
              public void onGroupInfoAvailable(WifiP2pGroup wifiP2pGroup) {
                if (WifiDisplayController.this.mConnectingDevice != null
                    && !WifiDisplayController.contains(
                        wifiP2pGroup, WifiDisplayController.this.mConnectingDevice)) {
                  Slog.i(
                      "WifiDisplayController",
                      "Aborting connection to Wifi display because the current P2P group does not"
                          + " contain the device we expected to find: "
                          + WifiDisplayController.this.mConnectingDevice.deviceName
                          + ", group info was: "
                          + WifiDisplayController.describeWifiP2pGroup(wifiP2pGroup));
                  WifiDisplayController.this.handleConnectionFailure(6);
                  return;
                }
                if (WifiDisplayController.this.mDesiredDevice != null
                    && !WifiDisplayController.contains(
                        wifiP2pGroup, WifiDisplayController.this.mDesiredDevice)) {
                  WifiDisplayController.this.disconnect();
                  return;
                }
                if (WifiDisplayController.this.mWifiDisplayCertMode) {
                  boolean equals =
                      wifiP2pGroup
                          .getOwner()
                          .deviceAddress
                          .equals(WifiDisplayController.this.mThisDevice.deviceAddress);
                  if (equals && wifiP2pGroup.getClientList().isEmpty()) {
                    WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                    wifiDisplayController.mDesiredDevice = null;
                    wifiDisplayController.mConnectingDevice = null;
                    WifiDisplayController.this.mConnectedDeviceGroupInfo = wifiP2pGroup;
                    WifiDisplayController.this.updateConnection();
                  } else if (WifiDisplayController.this.mConnectingDevice == null
                      && WifiDisplayController.this.mDesiredDevice == null) {
                    WifiDisplayController wifiDisplayController2 = WifiDisplayController.this;
                    WifiP2pDevice next =
                        equals
                            ? wifiP2pGroup.getClientList().iterator().next()
                            : wifiP2pGroup.getOwner();
                    wifiDisplayController2.mDesiredDevice = next;
                    wifiDisplayController2.mConnectingDevice = next;
                    Slog.i(
                        "WifiDisplayController",
                        "CertMode incoming from sink"
                            + WifiDisplayController.describeWifiP2pDevice(
                                WifiDisplayController.this.mConnectingDevice));
                    WifiDisplayController wifiDisplayController3 = WifiDisplayController.this;
                    wifiDisplayController3.advertiseDisplay(
                        wifiDisplayController3.createWifiDisplay(
                            wifiDisplayController3.mConnectingDevice),
                        null,
                        0,
                        0,
                        0);
                  }
                }
                if (WifiDisplayController.this.mConnectingDevice == null
                    || WifiDisplayController.this.mConnectingDevice
                        != WifiDisplayController.this.mDesiredDevice) {
                  return;
                }
                Slog.i(
                    "WifiDisplayController",
                    "Connected to Wifi display: "
                        + WifiDisplayController.this.mConnectingDevice.deviceName);
                WifiDisplayController.this.mHandler.removeCallbacks(
                    WifiDisplayController.this.mConnectionTimeout);
                WifiDisplayController.this.mConnectedDeviceGroupInfo = wifiP2pGroup;
                WifiDisplayController wifiDisplayController4 = WifiDisplayController.this;
                wifiDisplayController4.mConnectedDevice = wifiDisplayController4.mConnectingDevice;
                WifiDisplayController.this.mConnectingDevice = null;
                WifiDisplayController.this.mP2pFrequency = wifiP2pGroup.getFrequency();
                WifiDisplayController.this.updateConnection();
              }
            });
        return;
      }
      return;
    }
    this.mConnectedDeviceGroupInfo = null;
    if (this.mConnectingDevice != null || this.mConnectedDevice != null) {
      disconnect();
    }
    if (this.mWfdEnabled) {
      requestPeers();
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:10:0x001b, code lost:

     if (r7 != 6) goto L25;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void handleConnectionFailure(int i) {
    Slog.i("WifiDisplayController", "Wifi display connection failed!");
    if (i != 1) {
      if (i == 2) {
        Toast.makeText(
                new ContextThemeWrapper(this.mContext, R.style.Theme.DeviceDefault.Light),
                17043291,
                0)
            .show();
      } else if (i == 3) {
        ContextThemeWrapper contextThemeWrapper =
            new ContextThemeWrapper(this.mContext, R.style.Theme.DeviceDefault.Light);
        Context context = this.mContext;
        Toast.makeText(
                contextThemeWrapper, context.getString(17043289, context.getString(17042870)), 0)
            .show();
      } else if (i == 5) {
        ContextThemeWrapper contextThemeWrapper2 =
            new ContextThemeWrapper(this.mContext, R.style.Theme.DeviceDefault.Light);
        Context context2 = this.mContext;
        Object[] objArr = new Object[1];
        WifiP2pDevice wifiP2pDevice = this.mDesiredDevice;
        objArr[0] = wifiP2pDevice != null ? wifiP2pDevice.deviceName : context2.getString(17042870);
        Toast.makeText(contextThemeWrapper2, context2.getString(17043292, objArr), 0).show();
      }
      onConnectionFailure(i);
      disconnect();
    }
    ContextThemeWrapper contextThemeWrapper3 =
        new ContextThemeWrapper(this.mContext, R.style.Theme.DeviceDefault.Light);
    Context context3 = this.mContext;
    Object[] objArr2 = new Object[1];
    WifiP2pDevice wifiP2pDevice2 = this.mDesiredDevice;
    objArr2[0] = wifiP2pDevice2 != null ? wifiP2pDevice2.deviceName : context3.getString(17042870);
    Toast.makeText(contextThemeWrapper3, context3.getString(17043290, objArr2), 0).show();
    onConnectionFailure(i);
    disconnect();
  }

  public final void advertiseDisplay(
      final WifiDisplay wifiDisplay,
      final Surface surface,
      final int i,
      final int i2,
      final int i3) {
    if (Objects.equals(this.mAdvertisedDisplay, wifiDisplay)
        && this.mAdvertisedDisplaySurface == surface
        && this.mAdvertisedDisplayWidth == i
        && this.mAdvertisedDisplayHeight == i2
        && this.mAdvertisedDisplayFlags == i3) {
      return;
    }
    final WifiDisplay wifiDisplay2 = this.mAdvertisedDisplay;
    final Surface surface2 = this.mAdvertisedDisplaySurface;
    this.mAdvertisedDisplay = wifiDisplay;
    this.mAdvertisedDisplaySurface = surface;
    this.mAdvertisedDisplayWidth = i;
    this.mAdvertisedDisplayHeight = i2;
    this.mAdvertisedDisplayFlags = i3;
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.WifiDisplayController.18
          @Override // java.lang.Runnable
          public void run() {
            WifiDisplay wifiDisplay3;
            Surface surface3;
            Surface surface4 = surface2;
            if ((surface4 != null && surface == null)
                || ((wifiDisplay3 = wifiDisplay2) != null
                    && surface4 == null
                    && !wifiDisplay3.hasSameAddress(wifiDisplay))) {
              WifiDisplayController.this.mListener.onDisplayDisconnected();
              WifiDisplayController.this.finish();
            } else {
              WifiDisplay wifiDisplay4 = wifiDisplay2;
              if (wifiDisplay4 != null && !wifiDisplay4.hasSameAddress(wifiDisplay)) {
                WifiDisplayController.this.mListener.onDisplayConnectionFailed();
                WifiDisplayController.this.finish();
              }
            }
            WifiDisplay wifiDisplay5 = wifiDisplay;
            if (wifiDisplay5 != null) {
              if (!wifiDisplay5.hasSameAddress(wifiDisplay2)) {
                WifiDisplayController.this.mListener.onDisplayConnecting(wifiDisplay);
              } else if (!wifiDisplay.equals(wifiDisplay2)
                  || ((surface3 = surface2) != null && surface3 != surface)) {
                WifiDisplayController.this.mListener.onDisplayChanged(
                    wifiDisplay, surface, i, i2, i3);
              }
              if (surface2 != null || surface == null) {
                return;
              }
              WifiDisplayController.this.mListener.onDisplayConnected(
                  wifiDisplay, surface, i, i2, i3, null);
            }
          }
        });
  }

  public final void unadvertiseDisplay() {
    advertiseDisplay(null, null, 0, 0, 0);
  }

  public final void readvertiseDisplay(WifiDisplay wifiDisplay) {
    advertiseDisplay(
        wifiDisplay,
        this.mAdvertisedDisplaySurface,
        this.mAdvertisedDisplayWidth,
        this.mAdvertisedDisplayHeight,
        this.mAdvertisedDisplayFlags);
  }

  public final WifiDisplay createWifiDisplay(WifiP2pDevice wifiP2pDevice) {
    WifiDisplay wifiDisplay =
        new WifiDisplay(
            wifiP2pDevice.deviceAddress,
            wifiP2pDevice.deviceName,
            (String) null,
            true,
            wifiP2pDevice.getWfdInfo() != null
                ? wifiP2pDevice.getWfdInfo().isSessionAvailable()
                : false,
            false,
            wifiP2pDevice.primaryDeviceType);
    SemWifiP2pDevice semWifiP2pDevice = this.mSemWifiP2pManager.getSemWifiP2pDevice(wifiP2pDevice);
    if (semWifiP2pDevice != null) {
      Slog.d(
          "WifiDisplayController",
          "createWifiDisplay() getSemWifiP2pDevice ::  "
              + semWifiP2pDevice.getDeviceName()
              + ", deviceType : "
              + semWifiP2pDevice.getDeviceType()
              + ", deviceIcon : "
              + semWifiP2pDevice.getDeviceIcon()
              + ", deviceInfo(screenSharingInfo) : "
              + semWifiP2pDevice.getScreenSharingInfo()
              + ", isFwInviteSupported : "
              + semWifiP2pDevice.isFwInviteSupported());
      wifiDisplay.setSamsungDeviceType(semWifiP2pDevice.getDeviceType());
      wifiDisplay.setSamsungDeviceIcon(semWifiP2pDevice.getDeviceIcon());
      wifiDisplay.setBluetoothMacAddress(getBluetoothMacAddress(wifiP2pDevice));
      wifiDisplay.setDeviceInfo(semWifiP2pDevice.getScreenSharingInfo());
    }
    return wifiDisplay;
  }

  public final String getBluetoothMacAddress(WifiP2pDevice wifiP2pDevice) {
    String parseMacAddressToUpperString;
    SemWifiP2pDevice semWifiP2pDevice = this.mSemWifiP2pManager.getSemWifiP2pDevice(wifiP2pDevice);
    if (semWifiP2pDevice == null || TextUtils.isEmpty(semWifiP2pDevice.getServiceData())) {
      return "";
    }
    try {
      byte[] stringToByte = stringToByte(semWifiP2pDevice.getServiceData());
      for (int i = 3; i < stringToByte.length; i++) {
        byte b = stringToByte[i];
        if ((b & 2) > 0) {
          if ((b & 1) > 0) {
            byte[] bArr = new byte[6];
            System.arraycopy(stringToByte, i + 7, bArr, 0, 6);
            parseMacAddressToUpperString = parseMacAddressToUpperString(bArr);
          } else {
            byte[] bArr2 = new byte[6];
            System.arraycopy(stringToByte, i + 1, bArr2, 0, 6);
            parseMacAddressToUpperString = parseMacAddressToUpperString(bArr2);
          }
          return parseMacAddressToUpperString;
        }
      }
      return "";
    } catch (Exception e) {
      Slog.w("WifiDisplayController", "getBluetoothMacAddress, error : " + e.toString());
      return "";
    }
  }

  public static Inet4Address getInterfaceAddress(WifiP2pGroup wifiP2pGroup) {
    try {
      Enumeration<InetAddress> inetAddresses =
          NetworkInterface.getByName(wifiP2pGroup.getInterface()).getInetAddresses();
      while (inetAddresses.hasMoreElements()) {
        InetAddress nextElement = inetAddresses.nextElement();
        if (nextElement instanceof Inet4Address) {
          return (Inet4Address) nextElement;
        }
      }
      Slog.w(
          "WifiDisplayController",
          "Could not obtain address of network interface "
              + wifiP2pGroup.getInterface()
              + " because it had no IPv4 addresses.");
      return null;
    } catch (SocketException e) {
      Slog.w(
          "WifiDisplayController",
          "Could not obtain address of network interface " + wifiP2pGroup.getInterface(),
          e);
      return null;
    }
  }

  public static int getPortNumber(WifiP2pDevice wifiP2pDevice) {
    return (wifiP2pDevice.deviceName.startsWith("DIRECT-")
            && wifiP2pDevice.deviceName.endsWith("Broadcom"))
        ? 8554
        : 7236;
  }

  public static boolean isWifiDisplay(WifiP2pDevice wifiP2pDevice) {
    WifiP2pWfdInfo wfdInfo = wifiP2pDevice.getWfdInfo();
    return wfdInfo != null
        && wfdInfo.isEnabled()
        && wfdInfo.isSessionAvailable()
        && isPrimarySinkDeviceType(wfdInfo.getDeviceType());
  }

  public static String describeWifiP2pDevice(WifiP2pDevice wifiP2pDevice) {
    return wifiP2pDevice != null ? wifiP2pDevice.toString().replace('\n', ',') : "null";
  }

  public static String describeWifiP2pGroup(WifiP2pGroup wifiP2pGroup) {
    return wifiP2pGroup != null ? wifiP2pGroup.toString().replace('\n', ',') : "null";
  }

  public static byte[] stringToByte(String str) {
    if (str == null || str.length() == 0) {
      return new byte[0];
    }
    int length = str.length() / 2;
    byte[] bArr = new byte[length];
    for (int i = 0; i < length; i++) {
      int i2 = i * 2;
      try {
        bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
      } catch (NumberFormatException e) {
        Slog.w("WifiDisplayController", "stringToByte, NumberFormatException : ", e);
      }
    }
    return bArr;
  }

  public static String parseMacAddressToUpperString(byte[] bArr) {
    StringBuilder sb = new StringBuilder(18);
    for (byte b : bArr) {
      if (sb.length() > 0) {
        sb.append(':');
      }
      sb.append(String.format("%02X", Byte.valueOf(b)));
    }
    return sb.toString();
  }

  public void requestStartScan(int i, int i2) {
    if (this.mScanRequested) {
      return;
    }
    Log.d("WifiDisplayController", "requestStartScan setChannel = " + i + ", interval = " + i2);
    this.mRequestedScanChannel = i;
    if (i2 <= 0) {
      this.mRequestedScanInterval = 1000;
    } else {
      this.mRequestedScanInterval = i2 * 1000;
    }
    this.mScanRequested = true;
    enableP2p();
    updateScanState();
  }

  public void requestConnect(
      SemWifiDisplayConfig semWifiDisplayConfig,
      IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback) {
    this.mCallback = iWifiDisplayConnectionCallback;
    this.mWifiDisplayConfig = semWifiDisplayConfig;
    Log.d("WifiDisplayController", "requestConnect:: " + semWifiDisplayConfig.toString());
    if (this.mWifiDisplayConfig.getConnectionType() == 1) {
      requestConnect(this.mWifiDisplayConfig.getP2pMacAddress());
      return;
    }
    if (this.mWifiDisplayConfig.getConnectionType() == 2
        || this.mWifiDisplayConfig.getConnectionType() == 3) {
      WifiP2pDevice wifiP2pDevice = new WifiP2pDevice();
      wifiP2pDevice.deviceAddress =
          this.mWifiDisplayConfig.getMode() == 3
              ? this.mWifiDisplayConfig.getIpAddress()
              : this.mWifiDisplayConfig.getP2pMacAddress();
      wifiP2pDevice.deviceName = this.mWifiDisplayConfig.getDeviceName();
      this.mConnectedDevice = wifiP2pDevice;
      this.mDesiredDevice = wifiP2pDevice;
      this.mRemoteDisplayInterface =
          this.mWifiDisplayConfig.getIpAddress()
              + XmlUtils.STRING_ARRAY_SEPARATOR
              + getPortNumber(wifiP2pDevice);
      requestStopScan();
      WifiDisplay createWifiDisplay = createWifiDisplay(this.mConnectedDevice);
      createWifiDisplay.setState(6);
      createWifiDisplay.setFlags(this.mWifiDisplayConfig.getFlags());
      createWifiDisplay.setMode(this.mWifiDisplayConfig.getMode());
      advertiseDisplay(createWifiDisplay, null, 0, 0, 0);
      sendEventToSemDeviceStatusListener(1, 6);
      Slog.i(
          "WifiDisplayController",
          "Listening for RTSP connection from Wifi display via AP : "
              + this.mConnectedDevice.deviceName);
      this.mRemoteDisplay =
          RemoteDisplay.listen(
              this.mRemoteDisplayInterface,
              this.remoteDisplayListener,
              this.mHandler,
              this.mContext.getOpPackageName(),
              getEngineParameters(),
              this.mNativeListener);
      this.mHandler.postDelayed(this.mRtspTimeout, 30000L);
    }
  }

  public void setParam(String str, Object obj) {
    RemoteDisplay remoteDisplay = this.mRemoteDisplay;
    if (remoteDisplay != null) {
      remoteDisplay.setParam(str, obj);
    }
  }

  public boolean isConnected() {
    return this.mAdvertisedDisplay != null;
  }

  public int getScreenSharingStatus() {
    if (isConnected()) {
      return this.mAdvertisedDisplay.getState();
    }
    return 7;
  }

  public void setScreenSharingStatus(int i) {
    WifiDisplay wifiDisplay = this.mAdvertisedDisplay;
    if (wifiDisplay == null || wifiDisplay.getState() == i) {
      return;
    }
    this.mAdvertisedDisplay.setState(i);
    sendRemoteDisplayStateChangeEvent(i, 7);
    sendWifiDisplayVolumeSupportChangedBroadcast();
  }

  public boolean isWifiDisplayWithPinSupported(String str) {
    Iterator it = this.mAvailableWifiDisplayPeers.iterator();
    while (it.hasNext()) {
      WifiP2pDevice wifiP2pDevice = (WifiP2pDevice) it.next();
      if (wifiP2pDevice.deviceAddress.equals(str)) {
        return wifiP2pDevice.wpsDisplaySupported() || wifiP2pDevice.wpsKeypadSupported();
      }
    }
    return false;
  }

  public boolean requestSetWifiDisplayParameters(List list) {
    if (this.mAdvertisedDisplay == null || list == null || list.isEmpty()) {
      return false;
    }
    Slog.d(
        "WifiDisplayController",
        "requestSetWifiDisplayParameters, parameters : " + list.toString());
    JSONArray jSONArray = new JSONArray();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      SemWifiDisplayParameter semWifiDisplayParameter = (SemWifiDisplayParameter) it.next();
      if (!TextUtils.isEmpty(semWifiDisplayParameter.getKey())
          && !TextUtils.isEmpty(semWifiDisplayParameter.getValue())) {
        jSONArray.put(semWifiDisplayParameter.toString());
      }
    }
    setParam("setp", jSONArray);
    return true;
  }

  public boolean requestWifiDisplayParameter(
      String str, SemWifiDisplayParameter semWifiDisplayParameter) {
    if (this.mAdvertisedDisplay == null || semWifiDisplayParameter == null) {
      return false;
    }
    Slog.d(
        "WifiDisplayController",
        "requestWifiDisplayParameter, parametersGroup : "
            + str
            + " parameter : "
            + semWifiDisplayParameter.toString());
    str.hashCode();
    switch (str) {
      case "initparams":
        setParam("setc", wifiDisplayParameterToJOSNArray("initparams", semWifiDisplayParameter));
        return true;
      case "setparams":
        setParam("setp", wifiDisplayParameterToJOSNArray("setparams", semWifiDisplayParameter));
        return true;
      case "getparams":
        return true;
      default:
        return false;
    }
  }

  public void initializeMcfManager() {
    if (Build.VERSION.SEM_PLATFORM_INT < 120100
        || this.mPersistentDataStore.getRememberedInitiatedDevices().isEmpty()) {
      return;
    }
    this.mMcfManager.initialize();
  }

  public final JSONArray wifiDisplayParameterToJOSNArray(
      String str, SemWifiDisplayParameter semWifiDisplayParameter) {
    char c;
    JSONArray jSONArray = new JSONArray();
    int hashCode = str.hashCode();
    if (hashCode == 1071665814) {
      if (str.equals("initparams")) {
        c = 1;
      }
      c = 65535;
    } else if (hashCode != 1402102888) {
      if (hashCode == 1616719964 && str.equals("getparams")) {
        c = 2;
      }
      c = 65535;
    } else {
      if (str.equals("setparams")) {
        c = 0;
      }
      c = 65535;
    }
    if (c == 0) {
      if (!TextUtils.isEmpty(semWifiDisplayParameter.getKey())
          && !TextUtils.isEmpty(semWifiDisplayParameter.getValue())) {
        jSONArray.put(semWifiDisplayParameter.toString());
      }
    } else if (c == 1
        && !TextUtils.isEmpty(semWifiDisplayParameter.getKey())
        && !TextUtils.isEmpty(semWifiDisplayParameter.getValue())) {
      try {
        jSONArray.put(
            new JSONObject()
                .put(semWifiDisplayParameter.getKey(), semWifiDisplayParameter.getValue()));
      } catch (JSONException unused) {
        Log.e("WifiDisplayController", "wifiDisplaySetParameterToJOSNArray JSONException");
      }
    }
    return jSONArray;
  }

  public final void enableP2p() {
    if (isWifiRestricted()) {
      return;
    }
    Log.d("WifiDisplayController", "enableP2p");
    if (this.mWifiManager.getWifiState() != 3) {
      Log.d(
          "WifiDisplayController",
          "turn on wifi by controller : wifiState = " + this.mWifiManager.getWifiState());
      this.mWifiTurnedOnByController = true;
      setAllowWifiScan(false);
    }
    retrieveWifiP2pManagerAndChannel();
    this.mWifiManager.setWifiEnabled(true);
  }

  public final void disableP2p() {
    Log.d(
        "WifiDisplayController",
        "disableP2p :: mConnectedDevice = "
            + this.mConnectedDevice
            + ", mWifiTurnedOnByController = "
            + this.mWifiTurnedOnByController
            + ", mWifiP2pEnabled = "
            + this.mWifiP2pEnabled);
    if (this.mWifiTurnedOnByController) {
      this.mHandler.postDelayed(this.mWifiDisable, 1000L);
      setAllowWifiScan(true);
    }
  }

  public final void onConnectionSuccess(List list) {
    try {
      IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback = this.mCallback;
      if (iWifiDisplayConnectionCallback != null) {
        iWifiDisplayConnectionCallback.onSuccess(list);
      }
    } catch (RemoteException unused) {
      Slog.w("WifiDisplayController", "Failed to onSuccess");
    }
  }

  public final void onConnectionFailure(int i) {
    try {
      IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback = this.mCallback;
      if (iWifiDisplayConnectionCallback != null) {
        iWifiDisplayConnectionCallback.onFailure(i);
      }
    } catch (RemoteException unused) {
      Slog.w("WifiDisplayController", "Failed to onFailure");
    }
  }

  public final String getScreenLockType() {
    int keyguardStoredPasswordQuality =
        new LockPatternUtils(this.mContext).getKeyguardStoredPasswordQuality(UserHandle.myUserId());
    if (keyguardStoredPasswordQuality == 0) {
      Slog.d("WifiDisplayController", "LockScreenType : Swipe");
      return "swipe";
    }
    if (keyguardStoredPasswordQuality == 32768) {
      Slog.d("WifiDisplayController", "LockScreenType : Bio");
      return "bio";
    }
    if (keyguardStoredPasswordQuality == 65536) {
      Slog.d("WifiDisplayController", "LockScreenType : Pattern");
      return "pattern";
    }
    if (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) {
      Slog.d("WifiDisplayController", "LockScreenType : Pin");
      return "pin";
    }
    if (keyguardStoredPasswordQuality != 262144
        && keyguardStoredPasswordQuality != 327680
        && keyguardStoredPasswordQuality != 393216) {
      return "none";
    }
    Slog.d("WifiDisplayController", "LockScreenType : Password");
    return "password";
  }

  public final void handleWifiDisplayStateBroadcast(final int i) {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.WifiDisplayController.20
          @Override // java.lang.Runnable
          public void run() {
            StringBuilder sb = new StringBuilder();
            sb.append("handleWifiDisplayStateBroadcast:: state = ");
            sb.append(i == 1 ? "ON" : "OFF");
            sb.append(", [VolumeControl] isVolumeControlSupported: ");
            sb.append(WifiDisplayController.this.mIsDisplayVolumeControlSupported);
            sb.append(", disconnectByUser: ");
            sb.append(WifiDisplayController.this.mDisconnectByUser);
            Slog.d("WifiDisplayController", sb.toString());
            Intent intent = new Intent("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE");
            intent.addFlags(67108864);
            intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, i);
            intent.putExtra(
                "isSupportDisplayVolumeControl",
                WifiDisplayController.this.mIsDisplayVolumeControlSupported);
            intent.putExtra("by_user", WifiDisplayController.this.mDisconnectByUser);
            intent.putExtra("isPortraitDisplay", WifiDisplayController.this.mIsPortraitDisplay);
            if (i == 1 && WifiDisplayController.this.mAdvertisedDisplay != null) {
              intent.putExtra("mode", WifiDisplayController.this.mAdvertisedDisplay.getMode());
            }
            WifiDisplayController.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
            if (WifiDisplayController.this.mIsDisplayVolumeControlSupported) {
              if (i == 1) {
                WifiDisplayController wifiDisplayController = WifiDisplayController.this;
                wifiDisplayController.mPrevMusicStreamVolume =
                    wifiDisplayController.mAudioManager.semGetStreamVolume(3, 25);
                WifiDisplayController.this.mAudioManager.semSetStreamVolume(3, 15, 0, 25);
              } else {
                int semGetStreamVolume =
                    WifiDisplayController.this.mAudioManager.semGetStreamVolume(3, 25);
                AudioManager audioManager = WifiDisplayController.this.mAudioManager;
                if (semGetStreamVolume == 15) {
                  semGetStreamVolume = WifiDisplayController.this.mPrevMusicStreamVolume;
                }
                audioManager.semSetStreamVolume(3, semGetStreamVolume, 0, 25);
              }
            }
            WifiDisplayController.this.mDisconnectByUser = false;
          }
        });
  }

  public final WifiP2pConfig createConnectConfig(WifiP2pDevice wifiP2pDevice) {
    WifiP2pConfig wifiP2pConfig = new WifiP2pConfig();
    if (wifiP2pDevice != null) {
      wifiP2pConfig =
          new WifiP2pConfig.Builder()
              .setDeviceAddress(MacAddress.fromString(wifiP2pDevice.deviceAddress))
              .enablePersistentMode(false)
              .build();
      SemWifiP2pDevice semWifiP2pDevice =
          this.mSemWifiP2pManager.getSemWifiP2pDevice(wifiP2pDevice);
      if (semWifiP2pDevice != null) {
        Slog.d("WifiDisplayController", "semDeviceType :" + semWifiP2pDevice.getDeviceType());
        if (this.mWifiDisplayConfig.getMode() == 1
            || this.mRequestedScanChannel != 1611
            || semWifiP2pDevice.getDeviceType() == 6) {
          Slog.d("WifiDisplayController", "set NETWORK_ID_PERSISTENT");
          wifiP2pConfig =
              new WifiP2pConfig.Builder()
                  .setDeviceAddress(MacAddress.fromString(wifiP2pDevice.deviceAddress))
                  .enablePersistentMode(true)
                  .build();
        }
        Slog.d("WifiDisplayController", "createConnectConfig:: name = " + wifiP2pDevice.deviceName);
        if (this.mWifiDisplayConfig.isPinRequest() && wifiP2pDevice.wpsDisplaySupported()) {
          wifiP2pConfig.wps.setup = 2;
        } else if (wifiP2pDevice.wpsPbcSupported()) {
          wifiP2pConfig.wps.setup = 0;
        } else if (wifiP2pDevice.wpsDisplaySupported()) {
          wifiP2pConfig.wps.setup = 2;
        } else if (wifiP2pDevice.wpsKeypadSupported()) {
          wifiP2pConfig.wps.setup = 1;
        } else {
          wifiP2pConfig.wps.setup = 0;
        }
      }
    }
    wifiP2pConfig.groupOwnerIntent = 13;
    Slog.d("WifiDisplayController", "set groupOwnerIntent : " + wifiP2pConfig.groupOwnerIntent);
    return wifiP2pConfig;
  }

  public final String getEngineParameters() {
    JSONObject jSONObject = new JSONObject();
    try {
      if (this.mWifiDisplayConfig.getConnectionType() == 1) {
        this.mWifiDisplayConfig.addParameter(
            "initparams", new SemWifiDisplayParameter("frequency", this.mP2pFrequency));
      } else if (this.mWifiDisplayConfig.getConnectionType() == 2
          && this.mWifiManager.getConnectionInfo() != null) {
        this.mWifiDisplayConfig.addParameter(
            "initparams",
            new SemWifiDisplayParameter(
                "frequency", this.mWifiManager.getConnectionInfo().getFrequency()));
      }
      if (this.mWifiDisplayConfig.getMode() == 0) {
        this.mWifiDisplayConfig.addParameter(
            "getparams", new SemWifiDisplayParameter("wfd_sec_view_mode"));
        this.mWifiDisplayConfig.addParameter(
            "getparams", new SemWifiDisplayParameter("wfd_sec_dmr_support"));
        this.mWifiDisplayConfig.addParameter(
            "getparams", new SemWifiDisplayParameter("wfd_sec_dmr_support_type"));
        this.mWifiDisplayConfig.addParameter(
            "getparams", new SemWifiDisplayParameter("wfd_sec_voip_mode"));
        this.mWifiDisplayConfig.addParameter(
            "getparams", new SemWifiDisplayParameter("wfd_sec_tv_ble_mac"));
        this.mWifiDisplayConfig.addParameter(
            "setparams",
            new SemWifiDisplayParameter(
                "wfd_sec_mirroring_uuid", this.mMcfManager.getInitiatedMirroringUuid()));
        if (Build.VERSION.SEM_PLATFORM_INT >= 150100) {
          this.mWifiDisplayConfig.addParameter(
              "getparams", new SemWifiDisplayParameter("wfd_sec_dmr_meta_check"));
        }
      }
      jSONObject.put("initparams", this.mWifiDisplayConfig.getJasonArrayParameters("initparams"));
      jSONObject.put("getparams", this.mWifiDisplayConfig.getJasonArrayParameters("getparams"));
      jSONObject.put("setparams", this.mWifiDisplayConfig.getJasonArrayParameters("setparams"));
    } catch (JSONException e) {
      Slog.w("WifiDisplayController", e.toString());
    }
    return jSONObject.toString();
  }

  public final void finish() {
    Log.d("WifiDisplayController", "finish");
    this.mConnectedDevice = null;
    this.mDesiredDevice = null;
    this.mDisconnectingDevice = null;
    this.mTransportMode = 0;
    this.mRenameCapablity = false;
    this.mIsSupportInitiateMirroring = false;
    this.mScreenWakeUpByUser = false;
    this.mIsUibcAvailable = false;
    this.mPrevAudioMode = 0;
    this.mWifiDisplayConfig = new SemWifiDisplayConfig();
    handleWifiDisplayStateBroadcast(0);
    this.mHandler.removeCallbacks(this.mRtspTimeout);
  }

  public final void sendEventToSemDeviceStatusListener(final int i, final int i2) {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.WifiDisplayController.22
          @Override // java.lang.Runnable
          public void run() {
            Slog.d("WifiDisplayController", "sendEventToSemDeviceStatusListener");
            Bundle bundle = new Bundle();
            if (i2 == 6) {
              bundle.putInt("status", i);
            }
            Message obtainMessage = WifiDisplayController.this.mHandler.obtainMessage(20, i2, 0);
            obtainMessage.setData(bundle);
            WifiDisplayController.this.mHandler.sendMessage(obtainMessage);
          }
        });
  }

  public final void sendBroadcastTransportMode() {
    StringBuilder sb = new StringBuilder();
    sb.append("sendBroadcastTransportMode mode : ");
    sb.append(this.mTransportMode == 1 ? "TCP" : "UDP");
    Slog.d("WifiDisplayController", sb.toString());
    Intent intent = new Intent("com.samsung.intent.action.WIFIDISPLAY_NOTI_CONNECTION_MODE");
    intent.addFlags(67108864);
    intent.putExtra("CONNECTION_MODE", this.mTransportMode);
    this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
  }

  public final void sendWifiDisplayVolumeSupportChangedBroadcast() {
    if (this.mRemoteDisplayConnected) {
      this.mHandler.post(
          new Runnable() { // from class: com.android.server.display.WifiDisplayController.23
            @Override // java.lang.Runnable
            public void run() {
              boolean z =
                  WifiDisplayController.this.mIsDisplayVolumeControlSupported
                      && WifiDisplayController.this.mAdvertisedDisplay != null
                      && WifiDisplayController.this.mAdvertisedDisplay.getState() == 6;
              Slog.d(
                  "WifiDisplayController",
                  "[VolumeControl] Send SEM_WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED " + z);
              Intent intent =
                  new Intent("com.samsung.intent.action.WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED");
              intent.putExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 1);
              intent.putExtra("isSupportDisplayVolumeControl", z);
              WifiDisplayController.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
            }
          });
    }
  }

  public final List parseParametersFromEngine(WifiDisplay wifiDisplay, String str) {
    Slog.i("WifiDisplayController", "parseParametersFromEngine : " + str);
    if (wifiDisplay != null && str != null) {
      try {
        ArrayList arrayList = new ArrayList();
        JSONObject jSONObject = new JSONObject(str);
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
          JSONArray jSONArray = jSONObject.getJSONArray(keys.next());
          for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            Iterator<String> keys2 = jSONObject2.keys();
            while (keys2.hasNext()) {
              String next = keys2.next();
              String string = jSONObject2.getString(next);
              if (this.mParameterList.contains(next)) {
                List list = this.mParameterList;
                if (!((SemWifiDisplayParameter) list.get(list.indexOf(next))).equals(string)) {
                  arrayList.add(new SemWifiDisplayParameter(next, string));
                  wifiDisplay.addParameter(next, string);
                  List list2 = this.mParameterList;
                  list2.remove(list2.indexOf(next));
                  this.mParameterList.add(new SemWifiDisplayParameter(next, string));
                }
              } else {
                arrayList.add(new SemWifiDisplayParameter(next, string));
                wifiDisplay.addParameter(next, string);
                this.mParameterList.add(new SemWifiDisplayParameter(next, string));
              }
              if (wifiDisplay.getMode() == 0
                  && next.equals("wfd_sec_tv_ble_mac")
                  && BluetoothAdapter.checkBluetoothAddress(string)
                  && this.mPersistentDataStore.rememberInitiatedDevice(string)) {
                Slog.d("WifiDisplayController", "add new initiated device list");
                this.mPersistentDataStore.saveIfNeeded();
                initializeMcfManager();
              }
              if ("wfd_sec_view_mode".equals(next) && !string.equals(this.mViewMode)) {
                this.mViewMode = string;
                SystemProperties.set("debug.wfd.viewmode", string);
              }
            }
          }
        }
        return arrayList;
      } catch (JSONException e) {
        Slog.w("WifiDisplayController", e.toString());
      }
    }
    return null;
  }

  public final void sendRemoteDisplayStateChangeEvent(final int i, final int i2) {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.WifiDisplayController.24
          @Override // java.lang.Runnable
          public void run() {
            Bundle bundle = new Bundle();
            bundle.putInt("status", i);
            Message obtainMessage = WifiDisplayController.this.mHandler.obtainMessage(20, i2, 0);
            obtainMessage.setData(bundle);
            Slog.d(
                "WifiDisplayController",
                "sendRemoteDisplayStateChangeEvent state : " + i + ", event : " + i2);
            WifiDisplayController.this.mHandler.sendMessage(obtainMessage);
          }
        });
  }

  public final void sendDeviceVolumeChangedEvent(final int i, final Bundle bundle) {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.WifiDisplayController.25
          @Override // java.lang.Runnable
          public void run() {
            Message obtainMessage = WifiDisplayController.this.mHandler.obtainMessage(22, i, 0);
            obtainMessage.setData(bundle);
            WifiDisplayController.this.mHandler.sendMessage(obtainMessage);
          }
        });
  }

  public final void sendWifiDisplayParameterEvent(final List list) {
    if (list != null) {
      Log.i(
          "WifiDisplayController",
          "sendWifiDisplayParameterEvent, parameters : " + list.toString());
      this.mHandler.post(
          new Runnable() { // from class: com.android.server.display.WifiDisplayController.26
            @Override // java.lang.Runnable
            public void run() {
              WifiDisplayController.this.mHandler.sendMessage(
                  WifiDisplayController.this.mHandler.obtainMessage(25, 17, 0, list));
            }
          });
    }
  }

  public final void sendVoipModeMessageIfNecessary(WifiDisplay wifiDisplay, int i) {
    int i2 = this.mPrevAudioMode;
    if (i2 != 3 && i == 3) {
      ArrayList arrayList = new ArrayList();
      if (wifiDisplay.isVoipModeSupported()) {
        arrayList.add(new SemWifiDisplayParameter("wfd_sec_voip_mode", "on"));
      } else {
        arrayList.add(new SemWifiDisplayParameter("wfd_sec_low_latency_mode", "on"));
      }
      requestSetWifiDisplayParameters(arrayList);
    } else if (i2 == 3 && i != 3) {
      ArrayList arrayList2 = new ArrayList();
      if (wifiDisplay.isVoipModeSupported()) {
        arrayList2.add(new SemWifiDisplayParameter("wfd_sec_voip_mode", "off"));
      } else {
        arrayList2.add(new SemWifiDisplayParameter("wfd_sec_low_latency_mode", "off"));
      }
      requestSetWifiDisplayParameters(arrayList2);
    }
    this.mPrevAudioMode = i;
  }

  public final void registerGameManagerCallback() {
    IBinder gMSBinder;
    try {
      if (this.mGameManagerService == null && (gMSBinder = SemGameManager.getGMSBinder()) != null) {
        this.mGameManagerService = IGameManagerService.Stub.asInterface(gMSBinder);
      }
      IGameManagerService iGameManagerService = this.mGameManagerService;
      if (iGameManagerService != null) {
        if (iGameManagerService.identifyForegroundApp() == 1) {
          Slog.i("WifiDisplayController", "Set low latency mode");
          setParam("lowl", Boolean.TRUE);
        }
        Slog.d(
            "WifiDisplayController",
            "registerCallback. ret: "
                + this.mGameManagerService.registerCallback(this.mGameCallback));
        return;
      }
      Slog.d("WifiDisplayController", "failed to get game manager");
    } catch (Exception e) {
      Slog.d("WifiDisplayController", "registerCallback failed." + e);
    }
  }

  public final void unregisterGameManagerCallback() {
    try {
      IGameManagerService iGameManagerService = this.mGameManagerService;
      if (iGameManagerService != null) {
        iGameManagerService.unregisterCallback(this.mGameCallback);
        this.mGameManagerService = null;
      }
    } catch (RemoteException e) {
      Slog.e("WifiDisplayController", "mGameManagerService.unregisterCallback error");
      e.printStackTrace();
    }
  }

  public final boolean isWifiRestricted() {
    Cursor cursor;
    try {
      cursor =
          this.mContext
              .getContentResolver()
              .query(
                  Uri.parse("content://com.sec.knox.provider/RestrictionPolicy4"),
                  null,
                  "isWifiEnabled",
                  new String[] {"true"},
                  null);
    } catch (NullPointerException e) {
      e.printStackTrace();
      cursor = null;
    }
    if (cursor != null) {
      cursor.moveToFirst();
      try {
        try {
          if (cursor.getString(cursor.getColumnIndex("isWifiEnabled")).equals("false")) {
            Log.e("WifiDisplayController", "Restriction policy block wifi change");
            return true;
          }
        } catch (Exception e2) {
          e2.printStackTrace();
        }
      } finally {
        cursor.close();
      }
    }
    return false;
  }

  public final void wakeUpScreen() {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.WifiDisplayController.27
          @Override // java.lang.Runnable
          public void run() {
            Slog.i("WifiDisplayController", "wakeUpScreen");
            PowerManager powerManager =
                (PowerManager) WifiDisplayController.this.mContext.getSystemService("power");
            if (powerManager.isInteractive()) {
              WifiDisplayController.this.mScreenWakeUpByUser = true;
            } else {
              WifiDisplayController.this.mScreenWakeUpByUser = false;
              powerManager.wakeUp(SystemClock.uptimeMillis(), 2, "WifiDisplay WakeUpScreen");
            }
            ((KeyguardManager) WifiDisplayController.this.mContext.getSystemService("keyguard"))
                .semDismissKeyguard();
          }
        });
  }

  public final String flagsToString(int i) {
    ArrayList arrayList = new ArrayList();
    if ((i & 1) != 0) {
      arrayList.add("SECURE");
    }
    if ((i & 2) != 0) {
      arrayList.add("LANDSCAPE");
    }
    if ((i & 4) != 0) {
      arrayList.add("PORTRAIT_90");
    }
    if ((i & 8) != 0) {
      arrayList.add("PORTRAIT_270");
    }
    if ((i & 16) != 0) {
      arrayList.add("AUDIO_ONLY");
    }
    if ((i & 32) != 0) {
      arrayList.add("HIGH_RESOLUTION_SUPPORT");
    }
    if ((i & 64) != 0) {
      arrayList.add("DMR_SUPPORT");
    }
    return arrayList.toString();
  }

  public final void sendSSRMRequestIntent(final boolean z) {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.WifiDisplayController.28
          @Override // java.lang.Runnable
          public void run() {
            Log.d("WifiDisplayController", "sendSSRMRequestIntent() : " + z);
            Intent intent = new Intent();
            intent.setAction("com.samsung.intent.action.SSRM_REQUEST");
            intent.putExtra("SSRM_STATUS_NAME", "ScreenMirroring_enable");
            intent.putExtra("SSRM_STATUS_VALUE", z);
            intent.putExtra("PID", Process.myPid());
            intent.putExtra("PackageName", WifiDisplayController.this.mContext.getPackageName());
            WifiDisplayController.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
          }
        });
  }

  public final void setAllowWifiScan(final boolean z) {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.WifiDisplayController.29
          @Override // java.lang.Runnable
          public void run() {
            SemWifiManager semWifiManager =
                (SemWifiManager) WifiDisplayController.this.mContext.getSystemService("sem_wifi");
            if (semWifiManager != null) {
              semWifiManager.setAllowWifiScan(z);
            }
          }
        });
  }

  public final WifiDisplay setWifiDisplayConfig(WifiDisplay wifiDisplay, int i) {
    wifiDisplay.setState(6);
    wifiDisplay.setFlags(this.mWifiDisplayConfig.getFlags());
    wifiDisplay.setMode(this.mWifiDisplayConfig.getMode());
    if (this.mWifiDisplayConfig.getMode() == 0) {
      if ((i & 64) != 0) {
        wifiDisplay.addParameter("wfd_sec_dmr_support", "enable");
      }
      if ((i & 32) != 0) {
        wifiDisplay.addParameter("high_resolution_mode", "support");
      }
    }
    return wifiDisplay;
  }

  public final void setPreparedAccountPin(
      String str, String str2, final SemWifiP2pManager.ActionListener actionListener) {
    if (TextUtils.isEmpty(str)
        || TextUtils.isEmpty(str2)
        || TextUtils.isEmpty(this.mLocalDeviceP2pMacAddress)) {
      return;
    }
    Log.d(
        "WifiDisplayController",
        "setPreparedAccountPin, deviceId : "
            + str
            + ", TV MacAddress : "
            + str2
            + " TV MacAddress byte array: "
            + Arrays.toString(macAddressStringToByteArray(str2)));
    Optional.ofNullable(
            getEncryptionKeyByHmacSha256(
                hexStringToByteArray(str), macAddressStringToByteArray(str2)))
        .ifPresent(
            new Consumer() { // from class:
                             // com.android.server.display.WifiDisplayController$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                WifiDisplayController.this.lambda$setPreparedAccountPin$0(
                    actionListener, (byte[]) obj);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setPreparedAccountPin$0(
      SemWifiP2pManager.ActionListener actionListener, byte[] bArr) {
    SecureRandom secureRandom = new SecureRandom();
    int[] randomPinNumber = getRandomPinNumber();
    byte[] bArr2 = new byte[16];
    secureRandom.nextBytes(bArr2);
    Log.d(
        "WifiDisplayController",
        "pinNumber = " + Arrays.toString(randomPinNumber).replaceAll("[^0-9]", ""));
    Log.i(
        "WifiDisplayController",
        "local mac address : "
            + this.mLocalDeviceP2pMacAddress
            + ", "
            + Arrays.toString(macAddressStringToByteArray(this.mLocalDeviceP2pMacAddress))
            + " , iv hex = "
            + byteArrayToHexString(bArr2)
            + " , iv byte array = "
            + Arrays.toString(bArr2));
    StringBuilder sb = new StringBuilder();
    sb.append("key byte array: ");
    sb.append(Arrays.toString(bArr));
    sb.append(" , key hex string: ");
    sb.append(byteArrayToHexString(bArr));
    Log.d("WifiDisplayController", sb.toString());
    try {
      byte[] encryptByAES256 =
          encryptByAES256(
              bArr,
              getEncryptData(
                  randomPinNumber, macAddressStringToByteArray(this.mLocalDeviceP2pMacAddress)),
              bArr2);
      Log.i(
          "WifiDisplayController",
          "encryptedValue : "
              + Arrays.toString(encryptByAES256)
              + " hex : "
              + byteArrayToHexString(encryptByAES256));
      this.mSemWifiP2pManager.setPreparedAccountPin(
          Arrays.toString(randomPinNumber).replaceAll("[^0-9]", ""),
          byteArrayToHexString(encryptByAES256),
          byteArrayToHexString(bArr2),
          actionListener);
    } catch (Exception e) {
      Log.e("WifiDisplayController", "setPreparedAccountPin failed : " + e);
    }
  }

  public static int[] getRandomPinNumber() {
    SecureRandom secureRandom = new SecureRandom();
    int[] iArr = new int[8];
    for (int i = 0; i < 8; i++) {
      iArr[i] = secureRandom.nextInt(10);
    }
    return iArr;
  }

  public static byte[] getEncryptionKeyByHmacSha256(byte[] bArr, byte[] bArr2) {
    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(new SecretKeySpec(bArr, "HmacSHA256"));
      return mac.doFinal(bArr2);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      Log.e("WifiDisplayController", "getEncryptionKeyByHmacSha256 Exception : " + e);
      return null;
    }
  }

  public static byte[] encryptByAES256(byte[] bArr, byte[] bArr2, byte[] bArr3) {
    SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
    cipher.init(1, secretKeySpec, new IvParameterSpec(bArr3));
    return cipher.doFinal(bArr2);
  }

  public static String byteArrayToHexString(byte[] bArr) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bArr) {
      sb.append(String.format("%02x", Integer.valueOf(b & 255)));
    }
    return sb.toString();
  }

  public static byte[] hexStringToByteArray(String str) {
    int length = str.length();
    byte[] bArr = new byte[length / 2];
    for (int i = 0; i < length; i += 2) {
      bArr[i / 2] =
          (byte)
              ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
    }
    return bArr;
  }

  public static byte[] macAddressStringToByteArray(String str) {
    String[] split = str.split(XmlUtils.STRING_ARRAY_SEPARATOR);
    byte[] bArr = new byte[6];
    for (int i = 0; i < 6; i++) {
      bArr[i] = (byte) Integer.parseInt(split[i], 16);
    }
    return bArr;
  }

  public static byte[] getEncryptData(int[] iArr, byte[] bArr) {
    byte[] bArr2 = new byte[16];
    int i = 0;
    int i2 = 0;
    while (i < 8) {
      bArr2[i2] = Byte.parseByte(Integer.toHexString(iArr[i]));
      i++;
      i2++;
    }
    System.arraycopy(bArr, 0, bArr2, i2, 6);
    byte[] bArr3 = new byte[2];
    new SecureRandom().nextBytes(bArr3);
    System.arraycopy(bArr3, 0, bArr2, i2 + 6, 2);
    return bArr2;
  }

  public final boolean isVpnConnected() {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) this.mContext.getSystemService("connectivity");
    if (connectivityManager != null
        && connectivityManager.getAllNetworks() != null
        && connectivityManager.getAllNetworks().length > 0) {
      for (Network network : connectivityManager.getAllNetworks()) {
        NetworkCapabilities networkCapabilities =
            connectivityManager.getNetworkCapabilities(network);
        if (networkCapabilities != null && networkCapabilities.hasTransport(4)) {
          Slog.i("WifiDisplayController", "Connection ended due to VPN.");
          handleConnectionFailure(5);
          return true;
        }
      }
    }
    return false;
  }
}
