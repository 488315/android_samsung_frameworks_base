package com.android.server.display;

import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.att.iqi.lib.metrics.hw.HwConstants;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.mcf.McfAdapter;
import com.samsung.android.mcf.McfBleAdapter;
import com.samsung.android.mcf.ble.BleAdapterCallback;
import com.samsung.android.mcf.ble.BleScanCallback;
import com.samsung.android.mcf.ble.BleScanFilter;
import com.samsung.android.mcf.ble.BleScanSettings;
import com.samsung.android.mcf.ble.BleScanner;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.UUID;

/* loaded from: classes2.dex */
public class WifiDisplayMcfManager {
  public static final String TAG = "WifiDisplayMcfManager";
  public static final byte[] mScanFilterData = {
    66,
    4,
    0,
    HwConstants.IQ_CONFIG_POS_WIFI_ENABLED,
    0,
    0,
    0,
    0,
    HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED
  };
  public static final byte[] mScanFilterDataMask = {
    -1, -1, 0, -1, 0, 0, 0, 0, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED
  };
  public BleScanner mBleScanner;
  public final Context mContext;
  public boolean mIsBounded;
  public boolean mIsIntentRegistered;
  public boolean mIsScanning;
  public McfAdapter mMcfAdapter;
  public McfBleAdapter mMcfBleAdapter;
  public final McfHandler mMcfHandler;
  public final PersistentDataStore mPersistentDataStore;
  public Messenger mService;
  public final ServiceConnection mConnection =
      new ServiceConnection() { // from class: com.android.server.display.WifiDisplayMcfManager.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
          Log.d(WifiDisplayMcfManager.TAG, "BleAdvertiserService onServiceConnected");
          WifiDisplayMcfManager.this.mService = new Messenger(iBinder);
          WifiDisplayMcfManager.this.mIsBounded = true;
          try {
            WifiDisplayMcfManager.this.mService.send(Message.obtain(null, 1, 0, 0));
          } catch (RemoteException unused) {
          }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
          Log.d(WifiDisplayMcfManager.TAG, "BleAdvertiserService onServiceDisconnected");
          WifiDisplayMcfManager.this.mService = null;
          WifiDisplayMcfManager.this.mIsBounded = false;
        }
      };
  public final BleAdapterCallback mBleAdapterCallback =
      new BleAdapterCallback() { // from class: com.android.server.display.WifiDisplayMcfManager.2
        public void onMcfServiceStateChanged(int i, int i2) {
          Log.i(WifiDisplayMcfManager.TAG, "onMcfServiceStateChanged state " + i);
          WifiDisplayMcfManager.this.mMcfHandler.removeMessages(1);
          WifiDisplayMcfManager.this.mMcfHandler.sendMessage(
              WifiDisplayMcfManager.this.mMcfHandler.obtainMessage(1, Integer.valueOf(i)));
        }
      };
  public final McfAdapter.McfAdapterListener mMcfAdapterListener =
      new McfAdapter
          .McfAdapterListener() { // from class: com.android.server.display.WifiDisplayMcfManager.3
        public void onServiceRemoteException() {}

        public void onServiceConnected(McfAdapter mcfAdapter) {
          Log.i(WifiDisplayMcfManager.TAG, "McfAdapterListener onServiceConnected");
          WifiDisplayMcfManager.this.mMcfHandler.removeMessages(2);
          WifiDisplayMcfManager.this.mMcfHandler.sendMessageDelayed(
              WifiDisplayMcfManager.this.mMcfHandler.obtainMessage(2, mcfAdapter), 100L);
        }

        public void onServiceDisconnected() {
          Log.i(WifiDisplayMcfManager.TAG, "McfAdapterListener onServiceDisconnected");
          WifiDisplayMcfManager.this.mMcfHandler.removeMessages(3);
          WifiDisplayMcfManager.this.mMcfHandler.sendMessage(
              WifiDisplayMcfManager.this.mMcfHandler.obtainMessage(3));
        }
      };
  public final BleScanCallback mBleScanCallback =
      new BleScanCallback() { // from class: com.android.server.display.WifiDisplayMcfManager.4
        public void onScanResult(ScanResult scanResult) {
          if (!WifiDisplayMcfManager.this.mIsBounded) {
            Log.i(WifiDisplayMcfManager.TAG, "onScanResult result " + scanResult.toString());
          }
          WifiDisplayMcfManager.this.mMcfHandler.removeMessages(4);
          WifiDisplayMcfManager.this.mMcfHandler.sendMessage(
              WifiDisplayMcfManager.this.mMcfHandler.obtainMessage(4));
        }

        public void onScanFailed(int i) {
          Log.e(WifiDisplayMcfManager.TAG, "onScanFailed errorCode " + i);
        }
      };
  public final BroadcastReceiver mReceiver =
      new BroadcastReceiver() { // from class: com.android.server.display.WifiDisplayMcfManager.5
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          Log.d(WifiDisplayMcfManager.TAG, "onReceive :" + action);
          if ("com.samsung.bluetooth.adapter.action.BLE_STATE_CHANGED".equals(action)) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
            if (intExtra == 15 || intExtra == 16) {
              boolean z = intExtra == 15;
              WifiDisplayMcfManager.this.mMcfHandler.removeMessages(8);
              WifiDisplayMcfManager.this.mMcfHandler.sendMessage(
                  WifiDisplayMcfManager.this.mMcfHandler.obtainMessage(8, Boolean.valueOf(z)));
              return;
            }
            return;
          }
          if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
            boolean booleanExtra =
                intent.getBooleanExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, false);
            WifiDisplayMcfManager.this.mMcfHandler.removeMessages(8);
            WifiDisplayMcfManager.this.mMcfHandler.sendMessage(
                WifiDisplayMcfManager.this.mMcfHandler.obtainMessage(
                    8, Boolean.valueOf(booleanExtra)));
          } else if ("com.samsung.android.nearbyscanning".equals(action)) {
            boolean isNearbyScanningOn = WifiDisplayMcfManager.this.isNearbyScanningOn();
            WifiDisplayMcfManager.this.mMcfHandler.removeMessages(8);
            WifiDisplayMcfManager.this.mMcfHandler.sendMessage(
                WifiDisplayMcfManager.this.mMcfHandler.obtainMessage(
                    8, Boolean.valueOf(isNearbyScanningOn)));
          }
        }
      };

  public WifiDisplayMcfManager(
      Context context, Handler handler, PersistentDataStore persistentDataStore) {
    this.mContext = context;
    this.mMcfHandler = new McfHandler(handler.getLooper());
    this.mPersistentDataStore = persistentDataStore;
  }

  public void initialize() {
    registerIntent();
    bindMcfAdapter();
  }

  public String getInitiatedMirroringUuid() {
    String initiatedMirroringUuid = this.mPersistentDataStore.getInitiatedMirroringUuid();
    if (!TextUtils.isEmpty(initiatedMirroringUuid)) {
      return initiatedMirroringUuid;
    }
    UUID randomUUID = UUID.randomUUID();
    String substring =
        Base64.getUrlEncoder()
            .encodeToString(
                ByteBuffer.wrap(new byte[16])
                    .putLong(randomUUID.getMostSignificantBits())
                    .putLong(randomUUID.getLeastSignificantBits())
                    .array())
            .substring(0, 22);
    this.mPersistentDataStore.setInitiatedMirroringUuid(substring);
    return substring;
  }

  public final void registerIntent() {
    if (this.mIsIntentRegistered) {
      return;
    }
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("com.samsung.bluetooth.adapter.action.BLE_STATE_CHANGED");
    intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
    intentFilter.addAction("com.samsung.android.nearbyscanning");
    this.mContext.registerReceiver(this.mReceiver, intentFilter);
    this.mIsIntentRegistered = true;
  }

  public final void bindMcfAdapter() {
    if (!isNearbyScanningOn() || isAirPlaneModeOn()) {
      return;
    }
    Log.i(TAG, "bindMcfAdapter");
    this.mMcfHandler.removeMessages(5);
    if (this.mMcfAdapter == null) {
      if (McfAdapter.bindService(this.mContext, this.mMcfAdapterListener)) {
        return;
      }
      McfHandler mcfHandler = this.mMcfHandler;
      mcfHandler.sendMessageDelayed(mcfHandler.obtainMessage(5), 5000L);
      return;
    }
    if (this.mIsScanning) {
      stopScanBleScanner();
    }
    retryStartScanBleScanner();
  }

  public final void unbindMcfAdapter() {
    Log.i(TAG, "unbindMcfAdapter");
    this.mMcfHandler.removeCallbacksAndMessages(null);
    stopScanBleScanner();
    McfBleAdapter mcfBleAdapter = this.mMcfBleAdapter;
    if (mcfBleAdapter != null) {
      mcfBleAdapter.close();
      this.mMcfBleAdapter = null;
    }
    McfAdapter mcfAdapter = this.mMcfAdapter;
    if (mcfAdapter != null) {
      mcfAdapter.unbindService();
      this.mMcfAdapter = null;
    }
  }

  public final void bindBleAdvertiserService() {
    if (this.mIsBounded) {
      return;
    }
    Log.d(TAG, "bindBleAdvertiserService, mIsBounded : " + this.mIsBounded);
    Intent className =
        new Intent()
            .setClassName(
                KnoxCustomManagerService.SMARTMIRRORING_PACKAGE_NAME,
                "com.samsung.android.smartmirroring.ble.BleAdvertiserService");
    className.putExtra("wfd_sec_mirroring_uuid", getInitiatedMirroringUuid());
    this.mContext.bindService(className, this.mConnection, 1);
  }

  public final void unbindBleAdvertiserService() {
    if (this.mIsBounded) {
      try {
        this.mService.send(Message.obtain(null, 2, 0, 0));
      } catch (RemoteException unused) {
      }
      this.mContext.unbindService(this.mConnection);
      this.mIsBounded = false;
    }
  }

  public final void stopScanBleScanner() {
    BleScanner bleScanner = this.mBleScanner;
    if (bleScanner != null) {
      bleScanner.stopScan(this.mBleScanCallback);
      this.mBleScanner = null;
      this.mIsScanning = false;
    }
  }

  public final void tryStartScanBleScanner() {
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
        ArrayList rememberedInitiatedDevices =
            this.mPersistentDataStore.getRememberedInitiatedDevices();
        if (rememberedInitiatedDevices.isEmpty()) {
          return;
        }
        Iterator it = rememberedInitiatedDevices.iterator();
        while (it.hasNext()) {
          String str = (String) it.next();
          Log.d(TAG, "tryStartScanBleScanner : " + str);
          BleScanFilter.Builder builder2 = new BleScanFilter.Builder();
          try {
            builder2.setManufacturerData(117, mScanFilterData, mScanFilterDataMask);
            builder2.setDeviceAddress(str);
            BleScanFilter build2 = builder2.build();
            arrayList.add(build2);
            arrayList2.add(build2);
          } catch (Exception e) {
            Log.w(TAG, "tryStartScanBleScanner failed : " + e.toString());
          }
        }
        if (arrayList.size() <= 0 || arrayList2.size() <= 0) {
          return;
        }
        this.mIsScanning =
            this.mBleScanner.startScan(arrayList, arrayList2, build, this.mBleScanCallback);
        Log.i(TAG, "tryStartScanBleScanner startScan, ret=" + this.mIsScanning);
        if (this.mIsScanning) {
          return;
        }
        stopScanBleScanner();
        retryStartScanBleScanner();
        return;
      }
      Log.e(TAG, "BleScanner is Null");
    }
  }

  public final void retryStartScanBleScanner() {
    if (this.mIsScanning) {
      return;
    }
    this.mMcfHandler.removeMessages(6);
    McfHandler mcfHandler = this.mMcfHandler;
    mcfHandler.sendMessageDelayed(mcfHandler.obtainMessage(6), 1000L);
  }

  public final void handleRetryStartScanBleScanner() {
    this.mMcfHandler.removeMessages(6);
    McfBleAdapter mcfBleAdapter = this.mMcfBleAdapter;
    if (mcfBleAdapter == null) {
      return;
    }
    if (mcfBleAdapter.isNetworkEnabled(1)) {
      tryStartScanBleScanner();
    } else {
      McfHandler mcfHandler = this.mMcfHandler;
      mcfHandler.sendMessageDelayed(mcfHandler.obtainMessage(6), 1000L);
    }
  }

  public final void handleMcfServiceStateChanged(int i) {
    McfBleAdapter mcfBleAdapter = this.mMcfBleAdapter;
    if (mcfBleAdapter == null || 2 != i) {
      return;
    }
    if (mcfBleAdapter.isNetworkEnabled(1)) {
      Log.d(TAG, "handleMcfServiceStateChanged - BLE NETWORK is enabled");
      initialize();
    } else {
      Log.i(TAG, "handleMcfServiceStateChanged - BLE NETWORK is not enabled. Need to unbind");
      unbindMcfAdapter();
      unbindBleAdvertiserService();
    }
  }

  public final void handleMcfAdapterServiceConnected(McfAdapter mcfAdapter) {
    if (mcfAdapter != null) {
      this.mMcfAdapter = mcfAdapter;
    }
    try {
      String str = "";
      McfAdapter mcfAdapter2 = this.mMcfAdapter;
      if (mcfAdapter2 != null) {
        if (this.mMcfBleAdapter == null) {
          this.mMcfBleAdapter = mcfAdapter2.getBleAdapter(38, this.mBleAdapterCallback);
        }
        McfBleAdapter mcfBleAdapter = this.mMcfBleAdapter;
        if (mcfBleAdapter != null) {
          if (mcfBleAdapter.isNetworkEnabled(1)) {
            this.mMcfHandler.removeMessages(2);
            tryStartScanBleScanner();
          } else {
            retryStartScanBleScanner();
          }
        } else {
          str = "handleMcfAdapterServiceConnected mMcfBleAdapter null";
        }
      } else {
        str = "handleMcfAdapterServiceConnected mMcfAdapter null";
      }
      Log.d(TAG, str);
    } catch (Exception e) {
      Log.e(TAG, "handleMcfAdapterServiceConnected Exception:" + e);
    }
  }

  public final void handleMcfAdapterServiceDisconnected() {
    handleMcfManagerStatus(false);
  }

  public final void handleMcfAdapterScannerScanResult() {
    if (!this.mIsBounded) {
      bindBleAdvertiserService();
    }
    this.mMcfHandler.removeMessages(7);
    McfHandler mcfHandler = this.mMcfHandler;
    mcfHandler.sendMessageDelayed(mcfHandler.obtainMessage(7), 30000L);
  }

  public final void handleMcfManagerStatus(boolean z) {
    if (z) {
      initialize();
    } else {
      unbindMcfAdapter();
      unbindBleAdvertiserService();
    }
  }

  public final boolean isAirPlaneModeOn() {
    return Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0) != 0;
  }

  public final boolean isNearbyScanningOn() {
    try {
      return Settings.System.getInt(this.mContext.getContentResolver(), "nearby_scanning_enabled")
          != 0;
    } catch (Settings.SettingNotFoundException unused) {
      return true;
    }
  }

  public final class McfHandler extends Handler {
    public McfHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      switch (message.what) {
        case 1:
          WifiDisplayMcfManager.this.handleMcfServiceStateChanged(
              Integer.parseInt(message.obj.toString()));
          break;
        case 2:
          WifiDisplayMcfManager.this.handleMcfAdapterServiceConnected(
              (McfAdapter) McfAdapter.class.cast(message.obj));
          break;
        case 3:
          WifiDisplayMcfManager.this.handleMcfAdapterServiceDisconnected();
          break;
        case 4:
          WifiDisplayMcfManager.this.handleMcfAdapterScannerScanResult();
          break;
        case 5:
          WifiDisplayMcfManager.this.bindMcfAdapter();
          break;
        case 6:
          WifiDisplayMcfManager.this.handleRetryStartScanBleScanner();
          break;
        case 7:
          WifiDisplayMcfManager.this.unbindBleAdvertiserService();
          break;
        case 8:
          WifiDisplayMcfManager.this.handleMcfManagerStatus(
              Boolean.parseBoolean(message.obj.toString()));
          break;
      }
    }
  }
}
