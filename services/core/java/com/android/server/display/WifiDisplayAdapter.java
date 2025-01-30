package com.android.server.display;

import android.R;
import android.app.BroadcastOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.hardware.display.IDisplayManager;
import android.hardware.display.IWifiDisplayConnectionCallback;
import android.hardware.display.SemDlnaDevice;
import android.hardware.display.SemWifiDisplayConfig;
import android.hardware.display.SemWifiDisplayParameter;
import android.hardware.display.WifiDisplay;
import android.hardware.display.WifiDisplaySessionInfo;
import android.hardware.display.WifiDisplayStatus;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.IInstalld;
import android.provider.Settings;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.Surface;
import android.view.SurfaceControl;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.samsung.android.hardware.display.IRefreshRateToken;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.wifi.SemWifiManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class WifiDisplayAdapter extends DisplayAdapter {
  public WifiDisplay mActiveDisplay;
  public int mActiveDisplayState;
  public WifiDisplay[] mAvailableDisplays;
  public final BroadcastReceiver mBroadcastReceiver;
  public WifiDisplayStatus mCurrentStatus;
  public DisplayDeviceInfo mDefaultDisplayDeviceInfo;
  public WifiDisplayController mDisplayController;
  public WifiDisplayDevice mDisplayDevice;
  public WifiDisplay[] mDisplays;
  public DlnaController mDlnaController;
  public int mFeatureState;
  public final WifiDisplayHandler mHandler;
  public IRefreshRateToken mIRefreshRateToken;
  public boolean mPendingStatusChangeBroadcast;
  public final PersistentDataStore mPersistentDataStore;
  public WifiDisplay[] mRememberedDisplays;
  public int mScanState;
  public WifiDisplaySessionInfo mSessionInfo;
  public final boolean mSupportsProtectedBuffers;
  public VolumeController mVolumeController;
  public final WifiDisplayController.Listener mWifiDisplayListener;

  public final boolean isMtkChipset() {
    return false;
  }

  public WifiDisplayAdapter(
      DisplayManagerService.SyncRoot syncRoot,
      Context context,
      Handler handler,
      DisplayAdapter.Listener listener,
      PersistentDataStore persistentDataStore) {
    super(syncRoot, context, handler, listener, "WifiDisplayAdapter");
    WifiDisplay[] wifiDisplayArr = WifiDisplay.EMPTY_ARRAY;
    this.mDisplays = wifiDisplayArr;
    this.mAvailableDisplays = wifiDisplayArr;
    this.mRememberedDisplays = wifiDisplayArr;
    this.mBroadcastReceiver =
        new BroadcastReceiver() { // from class: com.android.server.display.WifiDisplayAdapter.13
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String action = intent.getAction();
            if ("android.server.display.wfd.DISCONNECT".equals(action)) {
              synchronized (WifiDisplayAdapter.this.getSyncRoot()) {
                WifiDisplayAdapter.this.requestDisconnectLocked();
              }
              return;
            }
            if ("com.samsung.intent.action.SEC_PRESENTATION_START".equals(action)) {
              try {
                String stringExtra = intent.getStringExtra("ownerPackageName");
                int intExtra = intent.getIntExtra("displayID", -1);
                Slog.d(
                    "WifiDisplayAdapter",
                    "SEM_PRESENTATION_START displayID : "
                        + intExtra
                        + ", PackageName : "
                        + stringExtra);
                if (intExtra <= -1 || intExtra == 1) {
                  return;
                }
                WifiDisplayAdapter.this.sendPresentationDisplayInfo(true, intExtra, stringExtra);
                return;
              } catch (Exception e) {
                Slog.e("WifiDisplayAdapter", "SEM_PRESENTATION_START Error : " + e.toString());
                return;
              }
            }
            if ("com.samsung.intent.action.SEC_PRESENTATION_STOP".equals(action)) {
              try {
                String stringExtra2 = intent.getStringExtra("ownerPackageName");
                int intExtra2 = intent.getIntExtra("displayID", -1);
                Slog.d(
                    "WifiDisplayAdapter",
                    "SEM_PRESENTATION_STOP displayID : "
                        + intExtra2
                        + ", PackageName : "
                        + stringExtra2);
                if (intExtra2 <= -1 || intExtra2 == 1) {
                  return;
                }
                WifiDisplayAdapter.this.sendPresentationDisplayInfo(false, intExtra2, stringExtra2);
                return;
              } catch (Exception e2) {
                Slog.e("WifiDisplayAdapter", "SEM_PRESENTATION_STOP Error : " + e2.toString());
                return;
              }
            }
            if ("com.samsung.intent.action.ROTATION_CHANGED".equals(action)) {
              synchronized (WifiDisplayAdapter.this.getSyncRoot()) {
                WifiDisplayAdapter.this.rotationDisplayDeviceLocked(
                    intent.getIntExtra("rotation", 0));
              }
              return;
            }
            if ("android.intent.action.USER_SWITCHED".equals(action)) {
              WifiDisplayAdapter.this.enableScreenInTile(
                  context2, intent.getIntExtra("android.intent.extra.user_handle", -1));
            }
          }
        };
    this.mWifiDisplayListener =
        new WifiDisplayController
            .Listener() { // from class: com.android.server.display.WifiDisplayAdapter.14
          @Override // com.android.server.display.WifiDisplayController.Listener
          public void onFeatureStateChanged(int i) {
            synchronized (WifiDisplayAdapter.this.getSyncRoot()) {
              if (WifiDisplayAdapter.this.mFeatureState != i) {
                WifiDisplayAdapter.this.mFeatureState = i;
                WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
                if (WifiDisplayAdapter.this.mFeatureState == 2) {
                  WifiDisplayAdapter.this.mDisplays = WifiDisplay.EMPTY_ARRAY;
                }
              }
            }
          }

          @Override // com.android.server.display.WifiDisplayController.Listener
          public void onScanStarted() {
            synchronized (WifiDisplayAdapter.this.getSyncRoot()) {
              if (WifiDisplayAdapter.this.mScanState != 1) {
                if (WifiDisplayAdapter.this.mActiveDisplay != null) {
                  Slog.i("WifiDisplayAdapter", "onScanStarted in counnected status");
                  WifiDisplayAdapter wifiDisplayAdapter = WifiDisplayAdapter.this;
                  wifiDisplayAdapter.mDisplays =
                      new WifiDisplay[] {wifiDisplayAdapter.mActiveDisplay};
                } else {
                  Slog.i("WifiDisplayAdapter", "onScanStarted");
                  WifiDisplayAdapter.this.mDisplays = WifiDisplay.EMPTY_ARRAY;
                }
                WifiDisplayAdapter.this.mScanState = 1;
                WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
              }
            }
          }

          @Override // com.android.server.display.WifiDisplayController.Listener
          public void onScanResults(WifiDisplay[] wifiDisplayArr2) {
            synchronized (WifiDisplayAdapter.this.getSyncRoot()) {
              WifiDisplay[] applyWifiDisplayAliases =
                  WifiDisplayAdapter.this.mPersistentDataStore.applyWifiDisplayAliases(
                      wifiDisplayArr2);
              boolean z =
                  !Arrays.equals(
                      WifiDisplayAdapter.this.mAvailableDisplays, applyWifiDisplayAliases);
              for (int i = 0; !z && i < applyWifiDisplayAliases.length; i++) {
                z =
                    applyWifiDisplayAliases[i].canConnect()
                        != WifiDisplayAdapter.this.mAvailableDisplays[i].canConnect();
              }
              if (z) {
                WifiDisplayAdapter.this.mAvailableDisplays = applyWifiDisplayAliases;
                WifiDisplayAdapter.this.fixRememberedDisplayNamesFromAvailableDisplaysLocked();
                WifiDisplayAdapter.this.updateDisplaysLocked();
                WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
              }
            }
          }

          @Override // com.android.server.display.WifiDisplayController.Listener
          public void onScanFinished() {
            synchronized (WifiDisplayAdapter.this.getSyncRoot()) {
              if (WifiDisplayAdapter.this.mScanState != 0) {
                WifiDisplayAdapter.this.mScanState = 0;
                WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
              }
            }
          }

          @Override // com.android.server.display.WifiDisplayController.Listener
          public void onDisplayConnecting(WifiDisplay wifiDisplay) {
            synchronized (WifiDisplayAdapter.this.getSyncRoot()) {
              WifiDisplay applyWifiDisplayAlias =
                  WifiDisplayAdapter.this.mPersistentDataStore.applyWifiDisplayAlias(wifiDisplay);
              if (WifiDisplayAdapter.this.mActiveDisplayState != 1
                  || WifiDisplayAdapter.this.mActiveDisplay == null
                  || !WifiDisplayAdapter.this.mActiveDisplay.equals(applyWifiDisplayAlias)) {
                WifiDisplayAdapter.this.mActiveDisplayState = 1;
                WifiDisplayAdapter.this.mActiveDisplay = applyWifiDisplayAlias;
                WifiDisplayAdapter wifiDisplayAdapter = WifiDisplayAdapter.this;
                wifiDisplayAdapter.mDisplays =
                    new WifiDisplay[] {wifiDisplayAdapter.mActiveDisplay};
                WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
              }
            }
          }

          @Override // com.android.server.display.WifiDisplayController.Listener
          public void onDisplayConnectionFailed() {
            synchronized (WifiDisplayAdapter.this.getSyncRoot()) {
              if (WifiDisplayAdapter.this.mActiveDisplayState != 0
                  || WifiDisplayAdapter.this.mActiveDisplay != null) {
                WifiDisplayAdapter.this.mActiveDisplayState = 0;
                WifiDisplayAdapter.this.mActiveDisplay = null;
                WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
              }
            }
          }

          @Override // com.android.server.display.WifiDisplayController.Listener
          public void onDisplayConnected(
              WifiDisplay wifiDisplay, Surface surface, int i, int i2, int i3, String str) {
            synchronized (WifiDisplayAdapter.this.getSyncRoot()) {
              WifiDisplay applyWifiDisplayAlias =
                  WifiDisplayAdapter.this.mPersistentDataStore.applyWifiDisplayAlias(wifiDisplay);
              if (surface != null) {
                WifiDisplayAdapter.this.addDisplayDeviceLocked(
                    applyWifiDisplayAlias, surface, i, i2, i3);
              } else {
                if (WifiDisplayAdapter.this.mPersistentDataStore.rememberWifiDisplay(
                    applyWifiDisplayAlias)) {
                  WifiDisplayAdapter.this.mPersistentDataStore.saveIfNeeded();
                  WifiDisplayAdapter.this.updateRememberedDisplaysLocked();
                }
                applyWifiDisplayAlias.setEmptySurface(true);
              }
              if (WifiDisplayAdapter.this.mActiveDisplayState != 2
                  || WifiDisplayAdapter.this.mActiveDisplay == null
                  || !WifiDisplayAdapter.this.mActiveDisplay.equals(applyWifiDisplayAlias)
                  || WifiDisplayAdapter.this.mActiveDisplay.isEmptySurface()) {
                if (applyWifiDisplayAlias.getMode() == 3) {
                  WifiDisplayAdapter.this.mFeatureState = 3;
                }
                WifiDisplayAdapter.this.mActiveDisplayState = 2;
                WifiDisplayAdapter.this.mActiveDisplay = applyWifiDisplayAlias;
                WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
              }
              WifiDisplayAdapter.this.setAllowWifiScan(false);
            }
          }

          @Override // com.android.server.display.WifiDisplayController.Listener
          public void onDisplaySessionInfo(WifiDisplaySessionInfo wifiDisplaySessionInfo) {
            synchronized (WifiDisplayAdapter.this.getSyncRoot()) {
              WifiDisplayAdapter.this.mSessionInfo = wifiDisplaySessionInfo;
              WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
            }
          }

          @Override // com.android.server.display.WifiDisplayController.Listener
          public void onDisplayChanged(
              WifiDisplay wifiDisplay, Surface surface, int i, int i2, int i3) {
            synchronized (WifiDisplayAdapter.this.getSyncRoot()) {
              Slog.i("WifiDisplayAdapter", "onDisplayChanged");
              WifiDisplay applyWifiDisplayAlias =
                  WifiDisplayAdapter.this.mPersistentDataStore.applyWifiDisplayAlias(wifiDisplay);
              if (WifiDisplayAdapter.this.mActiveDisplay != null
                  && WifiDisplayAdapter.this.mActiveDisplay.hasSameAddress(applyWifiDisplayAlias)) {
                if (!WifiDisplayAdapter.this.mActiveDisplay.equals(applyWifiDisplayAlias)) {
                  WifiDisplayAdapter.this.mActiveDisplay = applyWifiDisplayAlias;
                  WifiDisplayAdapter.this.renameDisplayDeviceLocked(
                      applyWifiDisplayAlias.getFriendlyDisplayName());
                } else {
                  WifiDisplayAdapter.this.updateDisplaySurfaceLocked(surface, i, i2);
                }
                WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
              }
            }
          }

          @Override // com.android.server.display.WifiDisplayController.Listener
          public void onDisplayDisconnected() {
            synchronized (WifiDisplayAdapter.this.getSyncRoot()) {
              WifiDisplayAdapter.this.removeDisplayDeviceLocked();
              if (WifiDisplayAdapter.this.mActiveDisplayState != 0
                  || WifiDisplayAdapter.this.mActiveDisplay != null) {
                WifiDisplayAdapter.this.mDlnaController.sendDisconnectionRequestBroadcast();
                WifiDisplayAdapter.this.mActiveDisplayState = 0;
                WifiDisplayAdapter.this.mActiveDisplay = null;
                WifiDisplayAdapter.this.mAvailableDisplays = WifiDisplay.EMPTY_ARRAY;
                WifiDisplayAdapter.this.mDisplays = WifiDisplay.EMPTY_ARRAY;
                WifiDisplayAdapter.this.scheduleStatusChangedBroadcastLocked();
              }
              WifiDisplayAdapter.this.setAllowWifiScan(true);
            }
            Settings.Global.putInt(
                WifiDisplayAdapter.this.getContext().getContentResolver(), "wifi_display_on", 0);
          }
        };
    if (!context.getPackageManager().hasSystemFeature("android.hardware.wifi.direct")) {
      throw new RuntimeException("WiFi display was requested, but there is no WiFi Direct feature");
    }
    this.mHandler = new WifiDisplayHandler(handler.getLooper());
    this.mPersistentDataStore = persistentDataStore;
    this.mSupportsProtectedBuffers =
        context.getResources().getBoolean(R.bool.config_telephony5gNonStandalone);
    enableScreenInTile(context, context.getUserId());
  }

  @Override // com.android.server.display.DisplayAdapter
  public void dumpLocked(PrintWriter printWriter) {
    super.dumpLocked(printWriter);
    printWriter.println("mCurrentStatus=" + getWifiDisplayStatusLocked());
    printWriter.println("mFeatureState=" + this.mFeatureState);
    printWriter.println("mScanState=" + this.mScanState);
    printWriter.println("mActiveDisplayState=" + this.mActiveDisplayState);
    printWriter.println("mActiveDisplay=" + this.mActiveDisplay);
    printWriter.println("mDisplays=" + Arrays.toString(this.mDisplays));
    printWriter.println("mAvailableDisplays=" + Arrays.toString(this.mAvailableDisplays));
    printWriter.println("mRememberedDisplays=" + Arrays.toString(this.mRememberedDisplays));
    printWriter.println("mPendingStatusChangeBroadcast=" + this.mPendingStatusChangeBroadcast);
    printWriter.println("mSupportsProtectedBuffers=" + this.mSupportsProtectedBuffers);
    if (this.mDisplayController == null) {
      printWriter.println("mDisplayController=null");
      return;
    }
    printWriter.println("mDisplayController:");
    IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
    indentingPrintWriter.increaseIndent();
    DumpUtils.dumpAsync(getHandler(), this.mDisplayController, indentingPrintWriter, "", 200L);
  }

  @Override // com.android.server.display.DisplayAdapter
  public void registerLocked() {
    super.registerLocked();
    updateRememberedDisplaysLocked();
    getHandler()
        .post(
            new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.1
              @Override // java.lang.Runnable
              public void run() {
                WifiDisplayAdapter wifiDisplayAdapter = WifiDisplayAdapter.this;
                wifiDisplayAdapter.mDisplayController =
                    new WifiDisplayController(
                        wifiDisplayAdapter.getContext(),
                        WifiDisplayAdapter.this.getHandler(),
                        WifiDisplayAdapter.this.mWifiDisplayListener,
                        WifiDisplayAdapter.this.mPersistentDataStore);
                WifiDisplayAdapter wifiDisplayAdapter2 = WifiDisplayAdapter.this;
                wifiDisplayAdapter2.mDlnaController =
                    new DlnaController(
                        wifiDisplayAdapter2.getContext(), WifiDisplayAdapter.this.getHandler());
                WifiDisplayAdapter wifiDisplayAdapter3 = WifiDisplayAdapter.this;
                wifiDisplayAdapter3.mVolumeController =
                    new VolumeController(
                        wifiDisplayAdapter3.getHandler(),
                        WifiDisplayAdapter.this.mDisplayController,
                        WifiDisplayAdapter.this.mDlnaController);
                IntentFilter intentFilter =
                    new IntentFilter("android.server.display.wfd.DISCONNECT");
                intentFilter.addAction("com.samsung.intent.action.ROTATION_CHANGED");
                intentFilter.addAction("com.samsung.intent.action.SEC_PRESENTATION_START");
                intentFilter.addAction("com.samsung.intent.action.SEC_PRESENTATION_STOP");
                intentFilter.addAction("android.intent.action.USER_SWITCHED");
                WifiDisplayAdapter.this
                    .getContext()
                    .registerReceiverAsUser(
                        WifiDisplayAdapter.this.mBroadcastReceiver,
                        UserHandle.ALL,
                        intentFilter,
                        null,
                        WifiDisplayAdapter.this.mHandler);
              }
            });
  }

  public void requestStartScanLocked(final boolean z) {
    Slog.d("WifiDisplayAdapter", "requestStartScanLocked");
    this.mAvailableDisplays = WifiDisplay.EMPTY_ARRAY;
    getHandler()
        .post(
            new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.2
              @Override // java.lang.Runnable
              public void run() {
                if (WifiDisplayAdapter.this.mDisplayController != null) {
                  WifiDisplayAdapter.this.mDisplayController.requestStartScan(z);
                }
              }
            });
  }

  public void requestStopScanLocked() {
    Slog.d("WifiDisplayAdapter", "requestStopScanLocked");
    getHandler()
        .post(
            new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.3
              @Override // java.lang.Runnable
              public void run() {
                if (WifiDisplayAdapter.this.mDisplayController != null) {
                  WifiDisplayAdapter.this.mDisplayController.requestStopScan();
                }
              }
            });
  }

  public void requestConnectLocked(final String str) {
    Slog.d("WifiDisplayAdapter", "requestConnectLocked: address=" + str);
    getHandler()
        .post(
            new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.4
              @Override // java.lang.Runnable
              public void run() {
                if (WifiDisplayAdapter.this.mDisplayController != null) {
                  WifiDisplayAdapter.this.mDisplayController.requestConnect(str);
                }
              }
            });
  }

  public void requestPauseLocked() {
    Slog.d("WifiDisplayAdapter", "requestPauseLocked");
    getHandler()
        .post(
            new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.5
              @Override // java.lang.Runnable
              public void run() {
                if (WifiDisplayAdapter.this.mDisplayController != null) {
                  WifiDisplayAdapter.this.mDisplayController.requestPause();
                }
              }
            });
  }

  public void requestResumeLocked() {
    Slog.d("WifiDisplayAdapter", "requestResumeLocked");
    getHandler()
        .post(
            new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.6
              @Override // java.lang.Runnable
              public void run() {
                if (WifiDisplayAdapter.this.mDisplayController != null) {
                  WifiDisplayAdapter.this.mDisplayController.requestResume();
                }
              }
            });
  }

  public void requestDisconnectLocked() {
    Slog.d("WifiDisplayAdapter", "requestDisconnectedLocked");
    getHandler()
        .post(
            new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.7
              @Override // java.lang.Runnable
              public void run() {
                if (WifiDisplayAdapter.this.mDisplayController != null) {
                  WifiDisplayAdapter.this.mDisplayController.requestDisconnect();
                }
              }
            });
  }

  public void requestRenameLocked(String str, String str2) {
    Slog.d("WifiDisplayAdapter", "requestRenameLocked: address=" + str + ", alias=" + str2);
    if (str2 != null) {
      str2 = str2.trim();
      if (str2.isEmpty() || str2.equals(str)) {
        str2 = null;
      }
    }
    String str3 = str2;
    WifiDisplay rememberedWifiDisplay = this.mPersistentDataStore.getRememberedWifiDisplay(str);
    if (rememberedWifiDisplay != null
        && !Objects.equals(rememberedWifiDisplay.getDeviceAlias(), str3)) {
      if (this.mPersistentDataStore.rememberWifiDisplay(
          new WifiDisplay(
              str,
              rememberedWifiDisplay.getDeviceName(),
              str3,
              false,
              false,
              false,
              rememberedWifiDisplay.getPrimaryDeviceType()))) {
        this.mPersistentDataStore.saveIfNeeded();
        updateRememberedDisplaysLocked();
        scheduleStatusChangedBroadcastLocked();
      }
    }
    WifiDisplay wifiDisplay = this.mActiveDisplay;
    if (wifiDisplay == null || !wifiDisplay.getDeviceAddress().equals(str)) {
      return;
    }
    renameDisplayDeviceLocked(this.mActiveDisplay.getFriendlyDisplayName());
  }

  public void requestForgetLocked(String str) {
    Slog.d("WifiDisplayAdapter", "requestForgetLocked: address=" + str);
    if (this.mPersistentDataStore.forgetWifiDisplay(str)) {
      this.mPersistentDataStore.saveIfNeeded();
      updateRememberedDisplaysLocked();
      scheduleStatusChangedBroadcastLocked();
    }
    WifiDisplay wifiDisplay = this.mActiveDisplay;
    if (wifiDisplay == null || !wifiDisplay.getDeviceAddress().equals(str)) {
      return;
    }
    requestDisconnectLocked();
  }

  public WifiDisplayStatus getWifiDisplayStatusLocked() {
    if (this.mCurrentStatus == null) {
      this.mCurrentStatus =
          new WifiDisplayStatus(
              this.mFeatureState,
              this.mScanState,
              this.mActiveDisplayState,
              this.mActiveDisplay,
              this.mDisplays,
              this.mSessionInfo);
    }
    Slog.d("WifiDisplayAdapter", "getWifiDisplayStatusLocked: result=" + this.mCurrentStatus);
    return this.mCurrentStatus;
  }

  public final void updateDisplaysLocked() {
    boolean z;
    ArrayList arrayList =
        new ArrayList(this.mAvailableDisplays.length + this.mRememberedDisplays.length);
    boolean[] zArr = new boolean[this.mAvailableDisplays.length];
    for (WifiDisplay wifiDisplay : this.mRememberedDisplays) {
      int i = 0;
      while (true) {
        WifiDisplay[] wifiDisplayArr = this.mAvailableDisplays;
        if (i >= wifiDisplayArr.length) {
          z = false;
          break;
        } else {
          if (wifiDisplay.equals(wifiDisplayArr[i])) {
            z = true;
            zArr[i] = true;
            break;
          }
          i++;
        }
      }
      if (!z) {
        if (wifiDisplay.equals(this.mActiveDisplay)) {
          arrayList.add(this.mActiveDisplay);
        } else {
          arrayList.add(
              new WifiDisplay(
                  wifiDisplay.getDeviceAddress(),
                  wifiDisplay.getDeviceName(),
                  wifiDisplay.getDeviceAlias(),
                  false,
                  false,
                  true,
                  wifiDisplay.getPrimaryDeviceType()));
        }
      }
    }
    int i2 = 0;
    while (true) {
      WifiDisplay[] wifiDisplayArr2 = this.mAvailableDisplays;
      if (i2 < wifiDisplayArr2.length) {
        WifiDisplay wifiDisplay2 = wifiDisplayArr2[i2];
        WifiDisplay wifiDisplay3 =
            new WifiDisplay(
                wifiDisplay2.getDeviceAddress(),
                wifiDisplay2.getDeviceName(),
                wifiDisplay2.getDeviceAlias(),
                true,
                wifiDisplay2.canConnect(),
                zArr[i2],
                wifiDisplay2.getPrimaryDeviceType());
        wifiDisplay3.setSamsungDeviceType(wifiDisplay2.getSamsungDeviceType());
        wifiDisplay3.setSamsungDeviceIcon(wifiDisplay2.getSamsungDeviceIcon());
        wifiDisplay3.setBluetoothMacAddress(wifiDisplay2.getBluetoothMacAddress());
        wifiDisplay3.setScreenSharingHashedDi(wifiDisplay2.getScreenSharingHashedDi());
        wifiDisplay3.setDeviceInfo(wifiDisplay2.getDeviceInfo());
        arrayList.add(wifiDisplay3);
        i2++;
      } else {
        this.mDisplays = (WifiDisplay[]) arrayList.toArray(WifiDisplay.EMPTY_ARRAY);
        return;
      }
    }
  }

  public final void updateRememberedDisplaysLocked() {
    this.mRememberedDisplays = this.mPersistentDataStore.getRememberedWifiDisplays();
    this.mActiveDisplay = this.mPersistentDataStore.applyWifiDisplayAlias(this.mActiveDisplay);
    this.mAvailableDisplays =
        this.mPersistentDataStore.applyWifiDisplayAliases(this.mAvailableDisplays);
    updateDisplaysLocked();
  }

  public final void fixRememberedDisplayNamesFromAvailableDisplaysLocked() {
    int i = 0;
    boolean z = false;
    while (true) {
      WifiDisplay[] wifiDisplayArr = this.mRememberedDisplays;
      if (i >= wifiDisplayArr.length) {
        break;
      }
      WifiDisplay wifiDisplay = wifiDisplayArr[i];
      WifiDisplay findAvailableDisplayLocked =
          findAvailableDisplayLocked(wifiDisplay.getDeviceAddress());
      if (findAvailableDisplayLocked != null && !wifiDisplay.equals(findAvailableDisplayLocked)) {
        Slog.d(
            "WifiDisplayAdapter",
            "fixRememberedDisplayNamesFromAvailableDisplaysLocked: updating remembered display to "
                + findAvailableDisplayLocked);
        this.mRememberedDisplays[i] = findAvailableDisplayLocked;
        z |=
            this.mPersistentDataStore.rememberWifiDisplay(
                new WifiDisplay(
                    findAvailableDisplayLocked.getDeviceAddress(),
                    findAvailableDisplayLocked.getDeviceName(),
                    (String) null,
                    false,
                    false,
                    false,
                    findAvailableDisplayLocked.getPrimaryDeviceType()));
      }
      i++;
    }
    if (z) {
      this.mPersistentDataStore.saveIfNeeded();
    }
  }

  public final WifiDisplay findAvailableDisplayLocked(String str) {
    for (WifiDisplay wifiDisplay : this.mAvailableDisplays) {
      if (wifiDisplay.getDeviceAddress().equals(str)) {
        return wifiDisplay;
      }
    }
    return null;
  }

  public final void addDisplayDeviceLocked(
      WifiDisplay wifiDisplay, Surface surface, int i, int i2, int i3) {
    int i4;
    IBinder createDisplay;
    removeDisplayDeviceLocked();
    if (this.mPersistentDataStore.rememberWifiDisplay(wifiDisplay)) {
      this.mPersistentDataStore.saveIfNeeded();
      updateRememberedDisplaysLocked();
      scheduleStatusChangedBroadcastLocked();
    }
    boolean z = (i3 & 1) != 0;
    if (z) {
      i4 = this.mSupportsProtectedBuffers ? 268435564 : 268435556;
    } else {
      i4 = 268435552;
    }
    int i5 = (i3 & 4) != 0 ? 1 : (i3 & 8) != 0 ? 3 : 0;
    if (wifiDisplay.getMode() == 3) {
      i4 = 134217728 | ((i4 | 128 | 536870912) & (-65));
    }
    if (wifiDisplay.getMode() == 2) {
      i4 = 67108864 | ((i4 | 128 | 536870912) & (-65));
    }
    int i6 =
        ((wifiDisplay.getFlags() & 32) != 0 || wifiDisplay.getMode() == 1) ? i4 | 536870912 : i4;
    if (wifiDisplay.getMode() == 0) {
      setIRefreshRate(true);
    }
    String friendlyDisplayName = wifiDisplay.getFriendlyDisplayName();
    String deviceAddress = wifiDisplay.getDeviceAddress();
    if (isMtkChipset()) {
      createDisplay = DisplayControl.createDisplay(friendlyDisplayName + "isWifiDpyForHWC", z);
    } else {
      createDisplay = DisplayControl.createDisplay(friendlyDisplayName, z);
    }
    WifiDisplayDevice wifiDisplayDevice =
        new WifiDisplayDevice(
            createDisplay, friendlyDisplayName, i, i2, 60.0f, i6, deviceAddress, surface, i5);
    this.mDisplayDevice = wifiDisplayDevice;
    sendDisplayDeviceEventLocked(wifiDisplayDevice, 1);
  }

  public final void removeDisplayDeviceLocked() {
    WifiDisplayDevice wifiDisplayDevice = this.mDisplayDevice;
    if (wifiDisplayDevice != null) {
      wifiDisplayDevice.destroyLocked();
      sendDisplayDeviceEventLocked(this.mDisplayDevice, 3);
      this.mDisplayDevice = null;
      setIRefreshRate(false);
    }
  }

  public final void renameDisplayDeviceLocked(String str) {
    WifiDisplayDevice wifiDisplayDevice = this.mDisplayDevice;
    if (wifiDisplayDevice == null || wifiDisplayDevice.getNameLocked().equals(str)) {
      return;
    }
    this.mDisplayDevice.setNameLocked(str);
    sendDisplayDeviceEventLocked(this.mDisplayDevice, 2);
  }

  public final void updateDisplaySurfaceLocked(Surface surface, int i, int i2) {
    Slog.i("WifiDisplayAdapter", "updateDisplaySurfaceLocked");
    WifiDisplayDevice wifiDisplayDevice = this.mDisplayDevice;
    if (wifiDisplayDevice != null) {
      wifiDisplayDevice.updateSurfaceLocked(surface, i, i2);
    }
  }

  public final void scheduleStatusChangedBroadcastLocked() {
    this.mCurrentStatus = null;
    if (this.mPendingStatusChangeBroadcast) {
      return;
    }
    this.mPendingStatusChangeBroadcast = true;
    this.mHandler.sendEmptyMessage(1);
  }

  public final void handleSendStatusChangeBroadcast() {
    synchronized (getSyncRoot()) {
      if (this.mPendingStatusChangeBroadcast) {
        this.mPendingStatusChangeBroadcast = false;
        Intent intent = new Intent("android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED");
        intent.addFlags(1073741824);
        intent.putExtra(
            "android.hardware.display.extra.WIFI_DISPLAY_STATUS",
            (Parcelable) getWifiDisplayStatusLocked());
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setDeliveryGroupPolicy(1);
        getContext().sendBroadcastAsUser(intent, UserHandle.ALL, null, makeBasic.toBundle());
      }
    }
  }

  public void requestStartScanLocked(final int i, final int i2) {
    Slog.d("WifiDisplayAdapter", "requestStartScanLocked : " + i + ", interval : " + i2);
    this.mAvailableDisplays = WifiDisplay.EMPTY_ARRAY;
    getHandler()
        .post(
            new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.8
              @Override // java.lang.Runnable
              public void run() {
                if (WifiDisplayAdapter.this.mDisplayController != null) {
                  WifiDisplayAdapter.this.mDisplayController.requestStartScan(i, i2);
                }
              }
            });
  }

  public void requestConnectLocked(
      final SemWifiDisplayConfig semWifiDisplayConfig,
      final IWifiDisplayConnectionCallback iWifiDisplayConnectionCallback) {
    getHandler()
        .post(
            new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.9
              @Override // java.lang.Runnable
              public void run() {
                if (WifiDisplayAdapter.this.mDisplayController != null) {
                  WifiDisplayAdapter.this.mDisplayController.requestConnect(
                      semWifiDisplayConfig, iWifiDisplayConnectionCallback);
                }
              }
            });
  }

  public boolean isWifiDisplayWithPinSupported(String str) {
    Slog.d("WifiDisplayAdapter", "isWifiDisplayWithPinSupported");
    WifiDisplayController wifiDisplayController = this.mDisplayController;
    if (wifiDisplayController != null) {
      return wifiDisplayController.isWifiDisplayWithPinSupported(str);
    }
    return false;
  }

  public VolumeController getVolumeControllerInstance() {
    return this.mVolumeController;
  }

  public void setDlnaDevice(SemDlnaDevice semDlnaDevice, IBinder iBinder) {
    WifiDisplayController wifiDisplayController;
    if (this.mDlnaController.setDlnaDevice(semDlnaDevice, iBinder)
        && (wifiDisplayController = this.mDisplayController) != null
        && wifiDisplayController.isConnected()) {
      int connectionState = semDlnaDevice.getConnectionState();
      if (connectionState == 1) {
        requestPauseLocked();
      } else if (connectionState == 0 || connectionState == 2) {
        requestResumeLocked();
      }
    }
  }

  public SemDlnaDevice getDlnaDevice() {
    return this.mDlnaController.getDlnaDevice();
  }

  public int getScreenSharingStatus() {
    return this.mDisplayController.getScreenSharingStatus();
  }

  public void setScreenSharingStatus(int i) {
    this.mDisplayController.setScreenSharingStatus(i);
  }

  public void setWifiDisplayParam(String str, String str2) {
    WifiDisplayController wifiDisplayController = this.mDisplayController;
    if (wifiDisplayController == null || !wifiDisplayController.isConnected()) {
      return;
    }
    this.mDisplayController.setParam(str, str2);
  }

  public void fitToActiveDisplayLocked(boolean z) {
    Slog.d("WifiDisplayAdapter", "fitToActiveDisplayLocked, status : " + z);
    if (this.mPersistentDataStore.rememberActiveDisplayFitStatus(z)) {
      this.mPersistentDataStore.saveIfNeeded();
    }
    sendBroadcastScreenRatio(!z);
  }

  public boolean isFitToActiveDisplayLocked() {
    boolean rememberedActiveDisplayFitStatus =
        this.mPersistentDataStore.getRememberedActiveDisplayFitStatus();
    Slog.d(
        "WifiDisplayAdapter", "isFitToActiveDisplayLocked : " + rememberedActiveDisplayFitStatus);
    return rememberedActiveDisplayFitStatus;
  }

  public boolean requestSetWifiDisplayParameters(List list) {
    WifiDisplayController wifiDisplayController = this.mDisplayController;
    if (wifiDisplayController != null) {
      return wifiDisplayController.requestSetWifiDisplayParameters(list);
    }
    return false;
  }

  public boolean requestWifiDisplayParameter(
      String str, SemWifiDisplayParameter semWifiDisplayParameter) {
    WifiDisplayController wifiDisplayController = this.mDisplayController;
    if (wifiDisplayController != null) {
      return wifiDisplayController.requestWifiDisplayParameter(str, semWifiDisplayParameter);
    }
    return false;
  }

  public void initializeMcfManager() {
    WifiDisplayController wifiDisplayController = this.mDisplayController;
    if (wifiDisplayController != null) {
      wifiDisplayController.initializeMcfManager();
    }
  }

  public void setDefaultDisplayDeviceInfoLocked(DisplayDeviceInfo displayDeviceInfo) {
    this.mDefaultDisplayDeviceInfo = displayDeviceInfo;
  }

  public final void enableScreenInTile(Context context, int i) {
    try {
      IPackageManager.Stub.asInterface(ServiceManager.getService("package"))
          .setComponentEnabledSetting(
              new ComponentName(
                  KnoxCustomManagerService.SMARTMIRRORING_PACKAGE_NAME,
                  "com.samsung.android.smartmirroring.tile.ScreenSharingTile"),
              1,
              1,
              i,
              context.getPackageName());
    } catch (Exception e) {
      Slog.w("WifiDisplayAdapter", "Cannot component enabled for user + " + i);
      e.printStackTrace();
    }
  }

  public final void sendPresentationDisplayInfo(final boolean z, final int i, final String str) {
    getHandler()
        .post(
            new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.10
              @Override // java.lang.Runnable
              public void run() {
                Handler handler = WifiDisplayAdapter.this.getHandler();
                int i2 = i;
                boolean z2 = z;
                WifiDisplayAdapter.this
                    .getHandler()
                    .sendMessage(handler.obtainMessage(21, i2, z2 ? 1 : 0, str));
              }
            });
  }

  public final void rotationDisplayDeviceLocked(int i) {
    WifiDisplayDevice wifiDisplayDevice = this.mDisplayDevice;
    if (wifiDisplayDevice != null) {
      wifiDisplayDevice.setRotationLocked(i);
      sendDisplayDeviceEventLocked(this.mDisplayDevice, 2);
    }
  }

  public final void sendBroadcastScreenRatio(final boolean z) {
    getHandler()
        .post(
            new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.11
              @Override // java.lang.Runnable
              public void run() {
                Intent intent = new Intent("com.samsung.intent.action.SET_SCREEN_RATIO_VALUE");
                intent.putExtra("screenratiovalue", z);
                WifiDisplayAdapter.this.getContext().sendBroadcastAsUser(intent, UserHandle.ALL);
                SystemProperties.set("wlan.wfd.screenratiovalue", String.valueOf(z));
              }
            });
  }

  public final void setAllowWifiScan(final boolean z) {
    this.mHandler.post(
        new Runnable() { // from class: com.android.server.display.WifiDisplayAdapter.12
          @Override // java.lang.Runnable
          public void run() {
            SemWifiManager semWifiManager =
                (SemWifiManager) WifiDisplayAdapter.this.getContext().getSystemService("sem_wifi");
            if (semWifiManager != null) {
              semWifiManager.setAllowWifiScan(z);
            }
          }
        });
  }

  public final void setIRefreshRate(boolean z) {
    IDisplayManager asInterface;
    if (z) {
      if (this.mIRefreshRateToken != null
          || (asInterface = IDisplayManager.Stub.asInterface(ServiceManager.getService("display")))
              == null) {
        return;
      }
      try {
        Slog.i("WifiDisplayAdapter", "setIRefreshRate acquire");
        this.mIRefreshRateToken =
            asInterface.acquireLowRefreshRateToken((IBinder) null, "WifiDisplayAdapter");
        return;
      } catch (RemoteException e) {
        Slog.e("WifiDisplayAdapter", "setIRefreshRate acquire exception : " + e);
        return;
      }
    }
    if (this.mIRefreshRateToken != null) {
      try {
        Slog.i("WifiDisplayAdapter", "setIRefreshRate release");
        this.mIRefreshRateToken.release();
      } catch (RemoteException e2) {
        Slog.e("WifiDisplayAdapter", "setIRefreshRate exception : " + e2);
      }
      this.mIRefreshRateToken = null;
    }
  }

  public final class WifiDisplayDevice extends DisplayDevice {
    public final DisplayAddress mAddress;
    public int mFlags;
    public int mHeight;
    public DisplayDeviceInfo mInfo;
    public Display.Mode mMode;
    public String mName;
    public int mPendingChanges;
    public final float mRefreshRate;
    public int mRotation;
    public Surface mSurface;
    public int mWidth;

    @Override // com.android.server.display.DisplayDevice
    public boolean hasStableUniqueId() {
      return true;
    }

    public WifiDisplayDevice(
        IBinder iBinder,
        String str,
        int i,
        int i2,
        float f,
        int i3,
        String str2,
        Surface surface,
        int i4) {
      super(WifiDisplayAdapter.this, iBinder, "wifi:" + str2, WifiDisplayAdapter.this.getContext());
      this.mRotation = 0;
      this.mName = str;
      this.mWidth = i;
      this.mHeight = i2;
      this.mRefreshRate = f;
      this.mFlags = i3;
      this.mAddress = DisplayAddress.fromMacAddress(str2);
      this.mSurface = surface;
      this.mMode = DisplayAdapter.createMode(i, i2, f);
      this.mRotation = i4;
      this.mPendingChanges |= 1;
    }

    public void destroyLocked() {
      Surface surface = this.mSurface;
      if (surface != null) {
        surface.release();
        this.mSurface = null;
      }
      DisplayControl.destroyDisplay(getDisplayTokenLocked());
    }

    public void setNameLocked(String str) {
      this.mName = str;
      this.mInfo = null;
    }

    public void setRotationLocked(int i) {
      this.mRotation = i;
      this.mInfo = null;
    }

    public void updateSurfaceLocked(Surface surface, int i, int i2) {
      Slog.i("WifiDisplayAdapter", "updateSurfaceLocked = " + this.mName);
      if (WifiDisplayAdapter.this.mDisplayDevice.mSurface.equals(surface)
          && this.mWidth == i
          && this.mHeight == i2) {
        return;
      }
      WifiDisplayAdapter wifiDisplayAdapter = WifiDisplayAdapter.this;
      wifiDisplayAdapter.sendDisplayDeviceEventLocked(wifiDisplayAdapter.mDisplayDevice, 2);
      Surface surface2 = this.mSurface;
      if (surface2 != null) {
        surface2.release();
      }
      WifiDisplayAdapter.this.sendTraversalRequestLocked();
      this.mSurface = surface;
      this.mWidth = i;
      this.mHeight = i2;
      this.mMode = DisplayAdapter.createMode(i, i2, 60.0f);
      this.mInfo = null;
      this.mPendingChanges |= 3;
    }

    @Override // com.android.server.display.DisplayDevice
    public void performTraversalLocked(SurfaceControl.Transaction transaction) {
      if (this.mSurface != null) {
        if ((this.mPendingChanges & 2) != 0) {
          transaction.setDisplaySize(getDisplayTokenLocked(), this.mWidth, this.mHeight);
        }
        if ((this.mPendingChanges & 1) != 0) {
          setSurfaceLocked(transaction, this.mSurface);
        }
      }
      this.mPendingChanges = 0;
    }

    @Override // com.android.server.display.DisplayDevice
    public DisplayDeviceInfo getDisplayDeviceInfoLocked() {
      if (this.mInfo == null) {
        DisplayDeviceInfo displayDeviceInfo = new DisplayDeviceInfo();
        this.mInfo = displayDeviceInfo;
        displayDeviceInfo.name = this.mName;
        displayDeviceInfo.uniqueId = getUniqueId();
        DisplayDeviceInfo displayDeviceInfo2 = this.mInfo;
        displayDeviceInfo2.width = this.mWidth;
        displayDeviceInfo2.height = this.mHeight;
        displayDeviceInfo2.modeId = this.mMode.getModeId();
        this.mInfo.defaultModeId = this.mMode.getModeId();
        DisplayDeviceInfo displayDeviceInfo3 = this.mInfo;
        displayDeviceInfo3.supportedModes = new Display.Mode[] {this.mMode};
        displayDeviceInfo3.presentationDeadlineNanos = 1000000000 / ((int) this.mRefreshRate);
        displayDeviceInfo3.flags = this.mFlags;
        displayDeviceInfo3.rotation = this.mRotation;
        displayDeviceInfo3.type = 3;
        displayDeviceInfo3.address = this.mAddress;
        displayDeviceInfo3.touch = 2;
        displayDeviceInfo3.setAssumedDensityForExternalDisplay(this.mWidth, this.mHeight);
        this.mInfo.flags |= IInstalld.FLAG_FORCE;
        if (WifiDisplayAdapter.this.mDefaultDisplayDeviceInfo != null) {
          this.mInfo.supportedColorModes =
              WifiDisplayAdapter.this.mDefaultDisplayDeviceInfo.supportedColorModes;
        }
      }
      return this.mInfo;
    }
  }

  public final class WifiDisplayHandler extends Handler {
    public WifiDisplayHandler(Looper looper) {
      super(looper, null, true);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      if (message.what != 1) {
        return;
      }
      WifiDisplayAdapter.this.handleSendStatusChangeBroadcast();
    }
  }
}
