package com.android.server.desktopmode;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.IndentingPrintWriter;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.ServiceThread;
import com.att.iqi.lib.metrics.p019hw.HwConstants;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.mcf.McfAdapter;
import com.samsung.android.mcf.McfBleAdapter;
import com.samsung.android.mcf.ble.BleAdapterCallback;
import com.samsung.android.mcf.ble.BleScanCallback;
import com.samsung.android.mcf.ble.BleScanFilter;
import com.samsung.android.mcf.ble.BleScanSettings;
import com.samsung.android.mcf.ble.BleScanner;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class McfManager {
  public static final String TAG = "[DMS]" + McfManager.class.getSimpleName();
  public static final byte[] mScanFilterData = {
    66, 4, 0, HwConstants.IQ_CONFIG_POS_WIFI_ENABLED, 0, 0, 0, 0, 8
  };
  public static final byte[] mScanFilterDataMask = {-1, -1, 0, -1, 0, 0, 0, 0, 8};
  public final BleAdapterCallback mBleAdapterCallback;
  public final BleAdvertiserServiceManager mBleAdvertiserServiceManager;
  public final BleScanCallback mBleScanCallback;
  public BleScanner mBleScanner;
  public int mBleStartScanReason;
  public final Context mContext;
  public int mLastBleScanFailedErrorCode;
  public String mLastBleScanResult;
  public McfAdapter mMcfAdapter;
  public final McfAdapter.McfAdapterListener mMcfAdapterListener;
  public McfBleAdapter mMcfBleAdapter;
  public final McfHandler mMcfHandler;
  public PowerManager mPowerManager;
  public final ContentResolver mResolver;
  public final SettingsHelper mSettingsHelper;
  public final StateManager.StateListener mStateListener;
  public final IStateManager mStateManager;
  public PowerManager.WakeLock mWakeLock;
  public final SettingsHelper.OnSettingChangedListener mWirelessDeXBleAddressSettingChangedListener;
  public String mBleScannerState = "BLE_SCANNER_STATE_MCF_ADAPTER_UNBIND";
  public long mBleAdvertiserServiceTimeout = -1;
  public boolean mIsRequestingBindMcfAdapter = false;
  public long mWakeLockTimeout = 0;
  public boolean mRegisterIntent = false;
  public final BroadcastReceiver mBroadcastReceiver =
      new BroadcastReceiver() { // from class: com.android.server.desktopmode.McfManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          if (DesktopModeFeature.DEBUG) {
            Log.m7d(McfManager.TAG, "onReceive intent.getAction():" + intent.getAction());
          }
          String action = intent.getAction();
          if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
            McfManager.this.mMcfHandler.removeMessages(701);
            McfManager.this.mMcfHandler.sendMessage(McfManager.this.mMcfHandler.obtainMessage(701));
          } else if ("com.samsung.bluetooth.adapter.action.BLE_STATE_CHANGED".equals(action)) {
            McfManager.this.mMcfHandler.removeMessages(702);
            McfManager.this.mMcfHandler.sendMessage(
                McfManager.this.mMcfHandler.obtainMessage(702, intent));
          } else if ("com.samsung.android.nearbyscanning".equals(action)) {
            McfManager.this.mMcfHandler.removeMessages(703);
            McfManager.this.mMcfHandler.sendMessage(McfManager.this.mMcfHandler.obtainMessage(703));
          }
        }
      };

  public McfManager(
      Context context,
      ServiceThread serviceThread,
      IStateManager iStateManager,
      SettingsHelper settingsHelper,
      BleAdvertiserServiceManager bleAdvertiserServiceManager) {
    StateManager.StateListener stateListener =
        new StateManager
            .StateListener() { // from class: com.android.server.desktopmode.McfManager.2
          @Override // com.android.server.desktopmode.StateManager.StateListener
          public void onBootCompleted() {
            McfManager.this.initialize(1001);
          }

          @Override // com.android.server.desktopmode.StateManager.StateListener
          public void onDualModeStopLoadingScreen(boolean z) {
            if (DesktopModeFeature.DEBUG) {
              Log.m10i(McfManager.TAG, "onDualModeStopLoadingScreen enter=" + z);
            }
            McfManager.this.mMcfHandler.removeMessages(103);
            McfManager.this.mMcfHandler.sendMessage(
                McfManager.this.mMcfHandler.obtainMessage(103, Boolean.valueOf(z)));
          }

          @Override // com.android.server.desktopmode.StateManager.StateListener
          public void onPackageStateChanged(State state) {
            if (DesktopModeFeature.DEBUG) {
              Log.m10i(McfManager.TAG, "onPackageStateChanged state=" + state);
            }
            Boolean bool =
                (Boolean) state.getPackageState().get("com.sec.android.desktopmode.uiservice");
            if (bool == null || !bool.booleanValue()) {
              return;
            }
            McfManager.this.mMcfHandler.removeMessages(104);
            McfManager.this.mMcfHandler.sendMessage(McfManager.this.mMcfHandler.obtainMessage(104));
          }
        };
    this.mStateListener = stateListener;
    this.mBleAdapterCallback =
        new BleAdapterCallback() { // from class: com.android.server.desktopmode.McfManager.3
          public void onMcfServiceStateChanged(int i, int i2) {
            if (DesktopModeFeature.DEBUG) {
              Log.m10i(McfManager.TAG, "onMcfServiceStateChanged state " + i);
            }
            McfManager.this.mMcfHandler.removeMessages(201);
            McfManager.this.mMcfHandler.sendMessage(
                McfManager.this.mMcfHandler.obtainMessage(201, Integer.valueOf(i)));
          }
        };
    this.mMcfAdapterListener =
        new McfAdapter
            .McfAdapterListener() { // from class: com.android.server.desktopmode.McfManager.4
          public void onServiceConnected(McfAdapter mcfAdapter) {
            if (DesktopModeFeature.DEBUG) {
              Log.m10i(McfManager.TAG, "McfAdapterListener onServiceConnected");
            }
            McfManager.this.mMcfHandler.removeMessages(301);
            McfManager.this.mMcfHandler.sendMessageDelayed(
                McfManager.this.mMcfHandler.obtainMessage(301, 0, 0, mcfAdapter), 100L);
          }

          public void onServiceDisconnected() {
            if (DesktopModeFeature.DEBUG) {
              Log.m10i(McfManager.TAG, "McfAdapterListener onServiceDisconnected");
            }
            McfManager.this.mMcfHandler.removeMessages(FrameworkStatsLog.f63x1c64b730);
            McfManager.this.mMcfHandler.sendMessage(
                McfManager.this.mMcfHandler.obtainMessage(FrameworkStatsLog.f63x1c64b730));
          }

          public void onServiceRemoteException() {
            Log.m8e(McfManager.TAG, "McfAdapterListener onServiceRemoteException");
          }
        };
    this.mBleScanCallback =
        new BleScanCallback() { // from class: com.android.server.desktopmode.McfManager.5
          public void onScanResult(ScanResult scanResult) {
            McfManager.this.mLastBleScanResult = scanResult.toString();
            if (McfManager.this.mBleAdvertiserServiceTimeout != 60000 && DesktopModeFeature.DEBUG) {
              Log.m10i(McfManager.TAG, "onScanResult result " + scanResult.toString());
            }
            McfManager.this.mMcfHandler.removeMessages(401);
            McfManager.this.mMcfHandler.sendMessage(McfManager.this.mMcfHandler.obtainMessage(401));
          }

          public void onScanFailed(int i) {
            McfManager.this.mLastBleScanFailedErrorCode = i;
            Log.m8e(McfManager.TAG, "onScanFailed errorCode " + i);
          }
        };
    SettingsHelper.OnSettingChangedListener onSettingChangedListener =
        new SettingsHelper.OnSettingChangedListener(
            "ble_mac_address_list") { // from class: com.android.server.desktopmode.McfManager.6
          @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
          public void onSettingChanged(String str, int i) {
            if (DesktopModeFeature.DEBUG) {
              Log.m10i(
                  McfManager.TAG,
                  "mWirelessDeXBleAddressSettingChangedListener onSettingChanged value=" + str);
            }
            McfManager.this.mMcfHandler.removeMessages(102);
            McfManager.this.mMcfHandler.sendMessage(
                McfManager.this.mMcfHandler.obtainMessage(102, str));
          }
        };
    this.mWirelessDeXBleAddressSettingChangedListener = onSettingChangedListener;
    this.mContext = context;
    this.mResolver = context.getContentResolver();
    this.mMcfHandler = new McfHandler(serviceThread.getLooper());
    this.mStateManager = iStateManager;
    iStateManager.registerListener(stateListener);
    this.mSettingsHelper = settingsHelper;
    settingsHelper.registerListener(onSettingChangedListener);
    this.mBleAdvertiserServiceManager = bleAdvertiserServiceManager;
    PowerManager powerManager = (PowerManager) context.getSystemService("power");
    this.mPowerManager = powerManager;
    this.mWakeLock = powerManager.newWakeLock(1, "DesktopMode:McfManager");
  }

  public void initialize(int i) {
    if (bleMacAddressListExists()) {
      registerIntent();
      bindMcfAdapter(i);
    }
  }

  public final void deinitialize() {
    unregisterIntent();
    unbindMcfAdapter();
    tryBleAdvertiserServiceUnbind(0L);
  }

  public final void registerIntent() {
    if (this.mRegisterIntent) {
      return;
    }
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "registerIntent");
    }
    this.mRegisterIntent = true;
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
    intentFilter.addAction("com.samsung.bluetooth.adapter.action.BLE_STATE_CHANGED");
    intentFilter.addAction("com.samsung.android.nearbyscanning");
    this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter);
  }

  public final void unregisterIntent() {
    if (this.mRegisterIntent) {
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(TAG, "unregisterIntent");
      }
      this.mRegisterIntent = false;
      this.mContext.unregisterReceiver(this.mBroadcastReceiver);
    }
  }

  public final void bindMcfAdapter(int i) {
    if (this.mMcfAdapter == null
        && !this.mIsRequestingBindMcfAdapter
        && isNearbyScanningOn(this.mContext)) {
      if (i != 1001 || isAirplaneModeOff(this.mContext)) {
        if (DesktopModeFeature.DEBUG) {
          Log.m10i(TAG, "bindMcfAdapter reason=" + bleStartScanReasonToString(i));
        }
        this.mBleStartScanReason = i;
        if (McfAdapter.bindService(this.mContext, this.mMcfAdapterListener)) {
          this.mIsRequestingBindMcfAdapter = true;
        }
      }
    }
  }

  public final void unbindMcfAdapter() {
    if (DesktopModeFeature.DEBUG) {
      Log.m10i(TAG, "unbindMcfAdapter");
    }
    bleScannerStopScan();
    McfBleAdapter mcfBleAdapter = this.mMcfBleAdapter;
    if (mcfBleAdapter != null) {
      mcfBleAdapter.close();
      this.mMcfBleAdapter = null;
    }
    McfAdapter mcfAdapter = this.mMcfAdapter;
    if (mcfAdapter != null) {
      mcfAdapter.unbindService();
      this.mMcfAdapter = null;
      this.mIsRequestingBindMcfAdapter = false;
    }
  }

  public final void bleScannerStopScan() {
    BleScanner bleScanner = this.mBleScanner;
    if (bleScanner != null) {
      bleScanner.stopScan(this.mBleScanCallback);
      this.mBleScanner = null;
      this.mBleScannerState = "BLE_SCANNER_STATE_STOP_SCAN_REQUEST";
    }
  }

  public final boolean bleMacAddressListExists() {
    String settings =
        DesktopModeSettings.getSettings(this.mResolver, "ble_mac_address_list", (String) null);
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "bleMacAddressListExists bleMacAddress=" + settings);
    }
    return settings != null;
  }

  public final String[] getBleMacAddressList() {
    String settings =
        DesktopModeSettings.getSettings(this.mResolver, "ble_mac_address_list", (String) null);
    if (settings != null) {
      return settings.split(",");
    }
    Log.m8e(TAG, "There is no bleMacAddress");
    return null;
  }

  public final void tryBleScannerStartScan(String[] strArr) {
    McfBleAdapter mcfBleAdapter = this.mMcfBleAdapter;
    if (mcfBleAdapter != null) {
      BleScanner bleScanner = mcfBleAdapter.getBleScanner();
      this.mBleScanner = bleScanner;
      if (bleScanner != null) {
        BleScanSettings.Builder builder = new BleScanSettings.Builder();
        builder.setTimeout(0);
        BleScanSettings build = builder.build();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (strArr != null) {
          for (String str : strArr) {
            if (BluetoothAdapter.checkBluetoothAddress(str)) {
              if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "tryBleScannerStartScan valid splitBleMac=" + str);
              }
              BleScanFilter.Builder builder2 = new BleScanFilter.Builder();
              builder2.setManufacturerData(117, mScanFilterData, mScanFilterDataMask);
              builder2.setDeviceAddress(str);
              BleScanFilter build2 = builder2.build();
              arrayList.add(build2);
              arrayList2.add(build2);
            } else {
              Log.m8e(TAG, "tryBleScannerStartScan invalid splitBleMac=" + str);
            }
          }
        }
        if (!arrayList.isEmpty()) {
          boolean startScan =
              this.mBleScanner.startScan(arrayList, arrayList2, build, this.mBleScanCallback);
          if (DesktopModeFeature.DEBUG) {
            Log.m10i(
                TAG, "tryBleScannerStartScan BLE_SCANNER_STATE_START_SCAN_REQUEST " + startScan);
          }
          this.mBleScannerState = "BLE_SCANNER_STATE_START_SCAN_REQUEST";
          return;
        }
        Log.m8e(TAG, "tryBleScannerStartScan BLE_SCANNER_STATE_NO_SCAN_TARGET");
        this.mBleScannerState = "BLE_SCANNER_STATE_NO_SCAN_TARGET";
      }
    }
  }

  public final void retryBleScannerStartScan(int i) {
    if (i < 3) {
      this.mBleScannerState = "BLE_SCANNER_STATE_BLE_NETWORK_NOT_READY_YET";
      this.mMcfHandler.removeMessages(301);
      McfHandler mcfHandler = this.mMcfHandler;
      mcfHandler.sendMessageDelayed(mcfHandler.obtainMessage(301, i + 1, 0, null), 3000L);
      return;
    }
    this.mBleScannerState = "BLE_SCANNER_STATE_BLE_NETWORK_NOT_READY";
  }

  public final void handleDualModeStopLoadingScreen(boolean z) {
    if (bleMacAddressListExists()) {
      if (z) {
        tryBleAdvertiserServiceUnbind(0L);
      } else if (!this.mBleAdvertiserServiceManager.isBound()
          && isNearbyScanningOn(this.mContext)
          && isBleAvailable()) {
        this.mBleAdvertiserServiceManager.bindService();
        tryBleAdvertiserServiceUnbind(45000L);
      }
    }
  }

  public final void handlePackageStateChanged() {
    initialize(1005);
  }

  public final void tryBleAdvertiserServiceUnbind(long j) {
    if (this.mBleAdvertiserServiceTimeout != j) {
      if (DesktopModeFeature.DEBUG) {
        Log.m10i(TAG, "tryBleAdvertiserServiceUnbind timeout changed " + j);
      }
      this.mBleAdvertiserServiceTimeout = j;
    }
    releaseWakeLock();
    acquireWakeLock(j);
    this.mMcfHandler.removeMessages(101);
    McfHandler mcfHandler = this.mMcfHandler;
    mcfHandler.sendMessageDelayed(mcfHandler.obtainMessage(101, Long.valueOf(j)), j);
  }

  public final void handleBleAdvertiserServiceUnbind(long j) {
    if (DesktopModeFeature.DEBUG) {
      Log.m12w(
          TAG,
          "handleBleAdvertiserServiceUnbind timeout "
              + j
              + ", mBleScannerState="
              + this.mBleScannerState);
    }
    if (!this.mBleScannerState.equals("BLE_SCANNER_STATE_MCF_ADAPTER_UNBIND")) {
      this.mBleScannerState = "BLE_SCANNER_STATE_SCAN_TIMEOUT";
    }
    this.mBleAdvertiserServiceTimeout = -1L;
    this.mBleAdvertiserServiceManager.unbindService();
    releaseWakeLock();
  }

  public final void handleWirelessDexBleMacAddressChanged(String str) {
    if (str == null) {
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(TAG, "handleWirelessDexBleMacAddressChanged deleted.");
      }
      deinitialize();
      return;
    }
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "handleWirelessDexBleMacAddressChanged changed.");
    }
    if (this.mMcfAdapter == null) {
      initialize(1002);
      return;
    }
    bleScannerStopScan();
    this.mBleScannerState = "BLE_SCANNER_STATE_SCAN_FILTER_CHANGE";
    this.mMcfHandler.removeMessages(301);
    McfHandler mcfHandler = this.mMcfHandler;
    mcfHandler.sendMessageDelayed(mcfHandler.obtainMessage(301, 0, 0, null), 100L);
  }

  public final void handleMcfServiceStateChanged(int i) {
    McfBleAdapter mcfBleAdapter = this.mMcfBleAdapter;
    if (mcfBleAdapter == null || 2 != i) {
      return;
    }
    if (mcfBleAdapter.isNetworkEnabled(1)) {
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(TAG, "handleMcfServiceStateChanged - BLE NETWORK is enabled");
      }
    } else if (DesktopModeFeature.DEBUG) {
      Log.m10i(
          TAG, "handleMcfServiceStateChanged - BLE NETWORK is not enabled. Need to check unbind");
    }
  }

  public final void handleMcfAdapterServiceConnected(int i, McfAdapter mcfAdapter) {
    if (mcfAdapter != null) {
      this.mBleScannerState = "BLE_SCANNER_STATE_MCF_ADAPTER_BIND";
      this.mMcfAdapter = mcfAdapter;
    }
    try {
      if (this.mMcfAdapter != null) {
        if (DesktopModeFeature.DEBUG) {
          Log.m7d(TAG, "handleMcfAdapterServiceConnected mMcfAdapter");
        }
        if (this.mMcfBleAdapter == null) {
          if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "handleMcfAdapterServiceConnected getBleAdapter");
          }
          this.mMcfBleAdapter = this.mMcfAdapter.getBleAdapter(35, this.mBleAdapterCallback);
        }
        McfBleAdapter mcfBleAdapter = this.mMcfBleAdapter;
        if (mcfBleAdapter != null) {
          if (mcfBleAdapter.isNetworkEnabled(1)) {
            tryBleScannerStartScan(getBleMacAddressList());
            return;
          } else {
            retryBleScannerStartScan(i);
            return;
          }
        }
        if (DesktopModeFeature.DEBUG) {
          Log.m7d(TAG, "handleMcfAdapterServiceConnected mMcfBleAdapter null");
          return;
        }
        return;
      }
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(TAG, "handleMcfAdapterServiceConnected mMcfAdapter null");
      }
    } catch (Exception e) {
      Log.m8e(TAG, "handleMcfAdapterServiceConnected Exception:" + e);
    }
  }

  public final void handleMcfAdapterServiceDisconnected() {
    unbindMcfAdapter();
    this.mBleScannerState = "BLE_SCANNER_STATE_MCF_ADAPTER_UNBIND";
    tryBleAdvertiserServiceUnbind(0L);
  }

  public final void handleMcfAdapterScannerScanResult() {
    this.mBleScannerState = "BLE_SCANNER_STATE_SCAN_RESULT";
    State state = this.mStateManager.getState();
    if (!this.mBleAdvertiserServiceManager.isBound()
        && state.getDesktopModeState().enabled != 3
        && state.getDesktopModeState().enabled != 4) {
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(TAG, "handleMcfAdapterScannerScanResult bindService");
      }
      this.mBleAdvertiserServiceManager.bindService();
    }
    tryBleAdvertiserServiceUnbind(60000L);
  }

  public final void handleAirplaneModeChanged() {
    if (isAirplaneModeOff(this.mContext)) {
      initialize(1003);
    }
  }

  public final boolean isAirplaneModeOff(Context context) {
    return context == null
        || Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) == 0;
  }

  public final void handleSemBleStateChanged(Intent intent) {
    int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "handleSemBleStateChanged STATE:" + intExtra);
    }
    if (intExtra == 15 || intExtra == 12) {
      initialize(1004);
    }
  }

  public final void handleNearbyScanningChanged() {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "handleNearbyScanningChanged");
    }
    if (isNearbyScanningOn(this.mContext)) {
      initialize(1006);
    } else {
      handleMcfAdapterServiceDisconnected();
    }
  }

  public final boolean isNearbyScanningOn(Context context) {
    if (context != null) {
      try {
        return Settings.System.getInt(context.getContentResolver(), "nearby_scanning_enabled") == 1;
      } catch (Settings.SettingNotFoundException e) {
        if (DesktopModeFeature.DEBUG) {
          Log.m12w(TAG, "SettingNotFoundException " + e);
        }
      }
    }
    return true;
  }

  public final String bleStartScanReasonToString(int i) {
    switch (i) {
      case 1001:
        return "BLE_START_SCAN_REASON_BOOT_COMPLETE";
      case 1002:
        return "BLE_START_SCAN_REASON_DEX_BLE_ADDRESS_CHANGED";
      case 1003:
        return "BLE_START_SCAN_REASON_AIRPLANE_MODE_CHANGED";
      case 1004:
        return "BLE_START_SCAN_REASON_BLUETOOTH_STATUS_CHANGED";
      case 1005:
        return "BLE_START_SCAN_REASON_PACKAGE_STATE_CHANGED";
      case 1006:
        return "BLE_START_SCAN_REASON_NEARBY_SCANNING_CHANGED";
      default:
        return "Unknown=" + i;
    }
  }

  public final void acquireWakeLock(long j) {
    PowerManager.WakeLock wakeLock = this.mWakeLock;
    if (wakeLock == null || wakeLock.isHeld()) {
      return;
    }
    long j2 = j + 10000;
    this.mWakeLockTimeout = j2;
    this.mWakeLock.acquire(j2);
  }

  public final void releaseWakeLock() {
    PowerManager.WakeLock wakeLock = this.mWakeLock;
    if (wakeLock == null || !wakeLock.isHeld()) {
      return;
    }
    this.mWakeLockTimeout = 0L;
    this.mWakeLock.release();
  }

  public final boolean isBleAvailable() {
    McfBleAdapter mcfBleAdapter = this.mMcfBleAdapter;
    if (mcfBleAdapter != null) {
      return mcfBleAdapter.isNetworkEnabled(1);
    }
    BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
    return defaultAdapter != null && defaultAdapter.semIsBleEnabled();
  }

  public final class McfHandler extends Handler {
    public McfHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 201) {
        McfManager.this.handleMcfServiceStateChanged(((Integer) message.obj).intValue());
        return;
      }
      if (i == 401) {
        McfManager.this.handleMcfAdapterScannerScanResult();
        return;
      }
      if (i == 301) {
        int i2 = message.arg1;
        McfAdapter mcfAdapter = (McfAdapter) message.obj;
        if (DesktopModeFeature.DEBUG) {
          Log.m7d(McfManager.TAG, "MSG_MCF_ADAPTER_SERVICE_CONNECTED retryCount=" + i2);
        }
        McfManager.this.handleMcfAdapterServiceConnected(i2, mcfAdapter);
        return;
      }
      if (i != 302) {
        switch (i) {
          case 101:
            McfManager.this.handleBleAdvertiserServiceUnbind(((Long) message.obj).longValue());
            break;
          case 102:
            McfManager.this.handleWirelessDexBleMacAddressChanged((String) message.obj);
            break;
          case 103:
            McfManager.this.handleDualModeStopLoadingScreen(((Boolean) message.obj).booleanValue());
            break;
          case 104:
            McfManager.this.handlePackageStateChanged();
            break;
          default:
            switch (i) {
              case 701:
                McfManager.this.handleAirplaneModeChanged();
                break;
              case 702:
                McfManager.this.handleSemBleStateChanged((Intent) message.obj);
                break;
              case 703:
                McfManager.this.handleNearbyScanningChanged();
                break;
            }
        }
        return;
      }
      McfManager.this.handleMcfAdapterServiceDisconnected();
    }
  }

  public void dump(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println("Current " + McfManager.class.getSimpleName() + " state:");
    indentingPrintWriter.increaseIndent();
    indentingPrintWriter.println("isBleAvailable=" + isBleAvailable());
    indentingPrintWriter.println("mBleScannerState=" + this.mBleScannerState);
    indentingPrintWriter.println(
        "mBleStartScanReason=" + bleStartScanReasonToString(this.mBleStartScanReason));
    indentingPrintWriter.println(
        "mBleAdvertiserServiceTimeout=" + this.mBleAdvertiserServiceTimeout);
    indentingPrintWriter.println("mWakeLockTimeout=" + this.mWakeLockTimeout);
    indentingPrintWriter.println("mLastBleScanFailedErrorCode=" + this.mLastBleScanFailedErrorCode);
    if (DesktopModeFeature.DEBUG) {
      indentingPrintWriter.println("mLastBleScanResult=" + this.mLastBleScanResult);
    }
    indentingPrintWriter.decreaseIndent();
  }
}
