package com.android.server.display;

import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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

import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;

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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class WifiDisplayMcfManager {
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
    public final AnonymousClass1 mConnection =
            new ServiceConnection() { // from class:
                                      // com.android.server.display.WifiDisplayMcfManager.1
                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    Log.d("WifiDisplayMcfManager", "BleAdvertiserService onServiceConnected");
                    WifiDisplayMcfManager.this.mService = new Messenger(iBinder);
                    WifiDisplayMcfManager wifiDisplayMcfManager = WifiDisplayMcfManager.this;
                    wifiDisplayMcfManager.mIsBounded = true;
                    try {
                        wifiDisplayMcfManager.mService.send(Message.obtain(null, 1, 0, 0));
                    } catch (RemoteException unused) {
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    Log.d("WifiDisplayMcfManager", "BleAdvertiserService onServiceDisconnected");
                    WifiDisplayMcfManager wifiDisplayMcfManager = WifiDisplayMcfManager.this;
                    wifiDisplayMcfManager.mService = null;
                    wifiDisplayMcfManager.mIsBounded = false;
                }
            };
    public final AnonymousClass2 mBleAdapterCallback =
            new BleAdapterCallback() { // from class:
                                       // com.android.server.display.WifiDisplayMcfManager.2
                public final void onMcfServiceStateChanged(int i, int i2) {
                    DirEncryptService$$ExternalSyntheticOutline0.m(
                            i, "onMcfServiceStateChanged state ", "WifiDisplayMcfManager");
                    WifiDisplayMcfManager.this.mMcfHandler.removeMessages(1);
                    McfHandler mcfHandler = WifiDisplayMcfManager.this.mMcfHandler;
                    mcfHandler.sendMessage(mcfHandler.obtainMessage(1, Integer.valueOf(i)));
                }
            };
    public final AnonymousClass3 mMcfAdapterListener =
            new McfAdapter
                    .McfAdapterListener() { // from class:
                                            // com.android.server.display.WifiDisplayMcfManager.3
                public final void onServiceConnected(McfAdapter mcfAdapter) {
                    Log.i("WifiDisplayMcfManager", "McfAdapterListener onServiceConnected");
                    WifiDisplayMcfManager.this.mMcfHandler.removeMessages(2);
                    McfHandler mcfHandler = WifiDisplayMcfManager.this.mMcfHandler;
                    mcfHandler.sendMessageDelayed(mcfHandler.obtainMessage(2, mcfAdapter), 100L);
                }

                public final void onServiceDisconnected() {
                    Log.i("WifiDisplayMcfManager", "McfAdapterListener onServiceDisconnected");
                    WifiDisplayMcfManager.this.mMcfHandler.removeMessages(3);
                    McfHandler mcfHandler = WifiDisplayMcfManager.this.mMcfHandler;
                    mcfHandler.sendMessage(mcfHandler.obtainMessage(3));
                }

                public final void onServiceRemoteException() {}
            };
    public final AnonymousClass4 mBleScanCallback =
            new BleScanCallback() { // from class:
                                    // com.android.server.display.WifiDisplayMcfManager.4
                public final void onScanFailed(int i) {
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                            i, "onScanFailed errorCode ", "WifiDisplayMcfManager");
                }

                public final void onScanResult(ScanResult scanResult) {
                    if (!WifiDisplayMcfManager.this.mIsBounded) {
                        Log.i(
                                "WifiDisplayMcfManager",
                                "onScanResult result " + scanResult.toString());
                    }
                    WifiDisplayMcfManager.this.mMcfHandler.removeMessages(4);
                    McfHandler mcfHandler = WifiDisplayMcfManager.this.mMcfHandler;
                    mcfHandler.sendMessage(mcfHandler.obtainMessage(4));
                }
            };
    public final AnonymousClass5 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.display.WifiDisplayMcfManager.5
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    Log.d("WifiDisplayMcfManager", "onReceive :" + action);
                    if ("com.samsung.bluetooth.adapter.action.BLE_STATE_CHANGED".equals(action)) {
                        int intExtra =
                                intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                        if (intExtra == 15 || intExtra == 16) {
                            r1 = intExtra == 15;
                            WifiDisplayMcfManager.this.mMcfHandler.removeMessages(8);
                            McfHandler mcfHandler = WifiDisplayMcfManager.this.mMcfHandler;
                            mcfHandler.sendMessage(
                                    mcfHandler.obtainMessage(8, Boolean.valueOf(r1)));
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                        boolean booleanExtra =
                                intent.getBooleanExtra(
                                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN, false);
                        WifiDisplayMcfManager.this.mMcfHandler.removeMessages(8);
                        McfHandler mcfHandler2 = WifiDisplayMcfManager.this.mMcfHandler;
                        mcfHandler2.sendMessage(
                                mcfHandler2.obtainMessage(8, Boolean.valueOf(booleanExtra)));
                        return;
                    }
                    if ("com.samsung.android.nearbyscanning".equals(action)) {
                        WifiDisplayMcfManager wifiDisplayMcfManager = WifiDisplayMcfManager.this;
                        wifiDisplayMcfManager.getClass();
                        try {
                            if (Settings.System.getInt(
                                            wifiDisplayMcfManager.mContext.getContentResolver(),
                                            "nearby_scanning_enabled")
                                    == 0) {
                                r1 = false;
                            }
                        } catch (Settings.SettingNotFoundException unused) {
                        }
                        WifiDisplayMcfManager.this.mMcfHandler.removeMessages(8);
                        McfHandler mcfHandler3 = WifiDisplayMcfManager.this.mMcfHandler;
                        mcfHandler3.sendMessage(mcfHandler3.obtainMessage(8, Boolean.valueOf(r1)));
                    }
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class McfHandler extends Handler {
        public McfHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            WifiDisplayMcfManager wifiDisplayMcfManager = WifiDisplayMcfManager.this;
            switch (i) {
                case 1:
                    int parseInt = Integer.parseInt(message.obj.toString());
                    McfBleAdapter mcfBleAdapter = wifiDisplayMcfManager.mMcfBleAdapter;
                    if (mcfBleAdapter != null && 2 == parseInt) {
                        if (!mcfBleAdapter.isNetworkEnabled(1)) {
                            Log.i(
                                    "WifiDisplayMcfManager",
                                    "handleMcfServiceStateChanged - BLE NETWORK is not enabled."
                                        + " Need to unbind");
                            wifiDisplayMcfManager.unbindMcfAdapter();
                            wifiDisplayMcfManager.unbindBleAdvertiserService();
                            break;
                        } else {
                            Log.d(
                                    "WifiDisplayMcfManager",
                                    "handleMcfServiceStateChanged - BLE NETWORK is enabled");
                            wifiDisplayMcfManager.initialize();
                            break;
                        }
                    }
                    break;
                case 2:
                    McfAdapter mcfAdapter = (McfAdapter) McfAdapter.class.cast(message.obj);
                    if (mcfAdapter != null) {
                        wifiDisplayMcfManager.mMcfAdapter = mcfAdapter;
                    } else {
                        wifiDisplayMcfManager.getClass();
                    }
                    try {
                        String str = "";
                        McfAdapter mcfAdapter2 = wifiDisplayMcfManager.mMcfAdapter;
                        if (mcfAdapter2 != null) {
                            if (wifiDisplayMcfManager.mMcfBleAdapter == null) {
                                wifiDisplayMcfManager.mMcfBleAdapter =
                                        mcfAdapter2.getBleAdapter(
                                                38, wifiDisplayMcfManager.mBleAdapterCallback);
                            }
                            McfBleAdapter mcfBleAdapter2 = wifiDisplayMcfManager.mMcfBleAdapter;
                            if (mcfBleAdapter2 == null) {
                                str = "handleMcfAdapterServiceConnected mMcfBleAdapter null";
                            } else if (mcfBleAdapter2.isNetworkEnabled(1)) {
                                wifiDisplayMcfManager.mMcfHandler.removeMessages(2);
                                wifiDisplayMcfManager.tryStartScanBleScanner();
                            } else {
                                wifiDisplayMcfManager.retryStartScanBleScanner();
                            }
                        } else {
                            str = "handleMcfAdapterServiceConnected mMcfAdapter null";
                        }
                        Log.d("WifiDisplayMcfManager", str);
                        break;
                    } catch (Exception e) {
                        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                e,
                                "handleMcfAdapterServiceConnected Exception:",
                                "WifiDisplayMcfManager");
                        return;
                    }
                case 3:
                    wifiDisplayMcfManager.unbindMcfAdapter();
                    wifiDisplayMcfManager.unbindBleAdvertiserService();
                    break;
                case 4:
                    boolean z = wifiDisplayMcfManager.mIsBounded;
                    if (!z && !z) {
                        Log.d(
                                "WifiDisplayMcfManager",
                                "bindBleAdvertiserService, mIsBounded : "
                                        + wifiDisplayMcfManager.mIsBounded);
                        Intent className =
                                new Intent()
                                        .setClassName(
                                                KnoxCustomManagerService
                                                        .SMARTMIRRORING_PACKAGE_NAME,
                                                "com.samsung.android.smartmirroring.ble.BleAdvertiserService");
                        className.putExtra(
                                "wfd_sec_mirroring_uuid",
                                wifiDisplayMcfManager.getInitiatedMirroringUuid());
                        wifiDisplayMcfManager.mContext.bindService(
                                className, wifiDisplayMcfManager.mConnection, 1);
                    }
                    McfHandler mcfHandler = wifiDisplayMcfManager.mMcfHandler;
                    mcfHandler.removeMessages(7);
                    mcfHandler.sendMessageDelayed(mcfHandler.obtainMessage(7), 30000L);
                    break;
                case 5:
                    wifiDisplayMcfManager.bindMcfAdapter();
                    break;
                case 6:
                    McfHandler mcfHandler2 = wifiDisplayMcfManager.mMcfHandler;
                    mcfHandler2.removeMessages(6);
                    McfBleAdapter mcfBleAdapter3 = wifiDisplayMcfManager.mMcfBleAdapter;
                    if (mcfBleAdapter3 != null) {
                        if (!mcfBleAdapter3.isNetworkEnabled(1)) {
                            mcfHandler2.sendMessageDelayed(mcfHandler2.obtainMessage(6), 1000L);
                            break;
                        } else {
                            wifiDisplayMcfManager.tryStartScanBleScanner();
                            break;
                        }
                    }
                    break;
                case 7:
                    wifiDisplayMcfManager.unbindBleAdvertiserService();
                    break;
                case 8:
                    if (!Boolean.parseBoolean(message.obj.toString())) {
                        wifiDisplayMcfManager.unbindMcfAdapter();
                        wifiDisplayMcfManager.unbindBleAdvertiserService();
                        break;
                    } else {
                        wifiDisplayMcfManager.initialize();
                        break;
                    }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.display.WifiDisplayMcfManager$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.display.WifiDisplayMcfManager$2] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.display.WifiDisplayMcfManager$3] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.display.WifiDisplayMcfManager$4] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.display.WifiDisplayMcfManager$5] */
    public WifiDisplayMcfManager(
            Context context, Handler handler, PersistentDataStore persistentDataStore) {
        this.mContext = context;
        this.mMcfHandler = new McfHandler(handler.getLooper());
        this.mPersistentDataStore = persistentDataStore;
    }

    public final void bindMcfAdapter() {
        BleScanner bleScanner;
        try {
            if (Settings.System.getInt(
                            this.mContext.getContentResolver(), "nearby_scanning_enabled")
                    == 0) {
                return;
            }
        } catch (Settings.SettingNotFoundException unused) {
        }
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0)
                != 0) {
            return;
        }
        Log.i("WifiDisplayMcfManager", "bindMcfAdapter");
        McfHandler mcfHandler = this.mMcfHandler;
        mcfHandler.removeMessages(5);
        if (this.mMcfAdapter == null) {
            if (McfAdapter.bindService(this.mContext, this.mMcfAdapterListener)) {
                return;
            }
            mcfHandler.sendMessageDelayed(mcfHandler.obtainMessage(5), 5000L);
        } else {
            if (this.mIsScanning && (bleScanner = this.mBleScanner) != null) {
                bleScanner.stopScan(this.mBleScanCallback);
                this.mBleScanner = null;
                this.mIsScanning = false;
            }
            retryStartScanBleScanner();
        }
    }

    public final String getInitiatedMirroringUuid() {
        PersistentDataStore persistentDataStore = this.mPersistentDataStore;
        String str = persistentDataStore.mInitiatedMirroringUuid;
        if (!TextUtils.isEmpty(str)) {
            return str;
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
        persistentDataStore.loadIfNeeded();
        persistentDataStore.mInitiatedMirroringUuid = substring;
        persistentDataStore.mDirty = true;
        return substring;
    }

    public final void initialize() {
        if (!this.mIsIntentRegistered) {
            this.mContext.registerReceiver(
                    this.mReceiver,
                    GmsAlarmManager$$ExternalSyntheticOutline0.m(
                            "com.samsung.bluetooth.adapter.action.BLE_STATE_CHANGED",
                            "android.intent.action.AIRPLANE_MODE",
                            "com.samsung.android.nearbyscanning"),
                    2);
            this.mIsIntentRegistered = true;
        }
        bindMcfAdapter();
    }

    public final void retryStartScanBleScanner() {
        if (this.mIsScanning) {
            return;
        }
        McfHandler mcfHandler = this.mMcfHandler;
        mcfHandler.removeMessages(6);
        mcfHandler.sendMessageDelayed(mcfHandler.obtainMessage(6), 1000L);
    }

    public final void tryStartScanBleScanner() {
        McfBleAdapter mcfBleAdapter = this.mMcfBleAdapter;
        if (mcfBleAdapter != null) {
            BleScanner bleScanner = mcfBleAdapter.getBleScanner();
            this.mBleScanner = bleScanner;
            if (bleScanner == null) {
                Log.e("WifiDisplayMcfManager", "BleScanner is Null");
                return;
            }
            BleScanSettings.Builder builder = new BleScanSettings.Builder();
            builder.setTimeout(0);
            BleScanSettings build = builder.build();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            PersistentDataStore persistentDataStore = this.mPersistentDataStore;
            persistentDataStore.loadIfNeeded();
            ArrayList arrayList3 = persistentDataStore.mRememberedInitiatedDevices;
            if (arrayList3.isEmpty()) {
                return;
            }
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                Log.d("WifiDisplayMcfManager", "tryStartScanBleScanner : " + str);
                BleScanFilter.Builder builder2 = new BleScanFilter.Builder();
                try {
                    builder2.setManufacturerData(117, mScanFilterData, mScanFilterDataMask);
                    builder2.setDeviceAddress(str);
                    BleScanFilter build2 = builder2.build();
                    arrayList.add(build2);
                    arrayList2.add(build2);
                } catch (Exception e) {
                    Log.w(
                            "WifiDisplayMcfManager",
                            "tryStartScanBleScanner failed : " + e.toString());
                }
            }
            if (arrayList.size() <= 0 || arrayList2.size() <= 0) {
                return;
            }
            BleScanner bleScanner2 = this.mBleScanner;
            AnonymousClass4 anonymousClass4 = this.mBleScanCallback;
            this.mIsScanning = bleScanner2.startScan(arrayList, arrayList2, build, anonymousClass4);
            FlashNotificationsController$$ExternalSyntheticOutline0.m(
                    "WifiDisplayMcfManager",
                    new StringBuilder("tryStartScanBleScanner startScan, ret="),
                    this.mIsScanning);
            if (this.mIsScanning) {
                return;
            }
            BleScanner bleScanner3 = this.mBleScanner;
            if (bleScanner3 != null) {
                bleScanner3.stopScan(anonymousClass4);
                this.mBleScanner = null;
                this.mIsScanning = false;
            }
            retryStartScanBleScanner();
        }
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

    public final void unbindMcfAdapter() {
        Log.i("WifiDisplayMcfManager", "unbindMcfAdapter");
        this.mMcfHandler.removeCallbacksAndMessages(null);
        BleScanner bleScanner = this.mBleScanner;
        if (bleScanner != null) {
            bleScanner.stopScan(this.mBleScanCallback);
            this.mBleScanner = null;
            this.mIsScanning = false;
        }
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
}
