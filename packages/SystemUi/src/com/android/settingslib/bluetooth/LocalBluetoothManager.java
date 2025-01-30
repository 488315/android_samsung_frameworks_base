package com.android.settingslib.bluetooth;

import android.content.Context;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDeviceManager;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastProfileManager;
import com.samsung.android.settingslib.bluetooth.detector.BluetoothRetryDetector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LocalBluetoothManager {
    public static boolean mSystemUiInstance = false;
    public static LocalBluetoothManager sInstance;
    public final CachedBluetoothCastDeviceManager mCachedCastDeviceManager;
    public final CachedBluetoothDeviceManager mCachedDeviceManager;
    public final BluetoothCastEventManager mCastEventManager;
    public final Context mContext;
    public final BluetoothEventManager mEventManager;
    public final LocalBluetoothAdapter mLocalAdapter;
    public final LocalBluetoothCastAdapter mLocalCastAdapter;
    public final LocalBluetoothCastProfileManager mLocalCastProfileManager;
    public final LocalBluetoothProfileManager mProfileManager;
    public final BluetoothRetryDetector mRestoredRetryDetector;

    private LocalBluetoothManager(LocalBluetoothAdapter localBluetoothAdapter, Context context, Handler handler, UserHandle userHandle) {
        LocalBluetoothCastAdapter localBluetoothCastAdapter;
        Log.d("LocalBluetoothManager", "LocalBluetoothManager :: constructor");
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mLocalAdapter = localBluetoothAdapter;
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager = new CachedBluetoothDeviceManager(applicationContext, this);
        this.mCachedDeviceManager = cachedBluetoothDeviceManager;
        BluetoothEventManager bluetoothEventManager = new BluetoothEventManager(localBluetoothAdapter, cachedBluetoothDeviceManager, applicationContext, handler, userHandle);
        this.mEventManager = bluetoothEventManager;
        LocalBluetoothProfileManager localBluetoothProfileManager = new LocalBluetoothProfileManager(applicationContext, localBluetoothAdapter, cachedBluetoothDeviceManager, bluetoothEventManager);
        this.mProfileManager = localBluetoothProfileManager;
        if ("com.android.systemui".equals(context.getPackageName().toLowerCase())) {
            mSystemUiInstance = true;
        }
        this.mRestoredRetryDetector = new BluetoothRetryDetector(true);
        localBluetoothProfileManager.updateLocalProfiles();
        bluetoothEventManager.readPairedDevices();
        if (SemBluetoothCastAdapter.isBluetoothCastSupported()) {
            synchronized (LocalBluetoothCastAdapter.class) {
                if (LocalBluetoothCastAdapter.sInstance == null) {
                    LocalBluetoothCastAdapter.sInstance = new LocalBluetoothCastAdapter(applicationContext);
                }
                localBluetoothCastAdapter = LocalBluetoothCastAdapter.sInstance;
            }
            this.mLocalCastAdapter = localBluetoothCastAdapter;
            CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager = new CachedBluetoothCastDeviceManager(applicationContext, this);
            this.mCachedCastDeviceManager = cachedBluetoothCastDeviceManager;
            BluetoothCastEventManager bluetoothCastEventManager = new BluetoothCastEventManager(localBluetoothCastAdapter, cachedBluetoothCastDeviceManager, applicationContext);
            this.mCastEventManager = bluetoothCastEventManager;
            this.mLocalCastProfileManager = new LocalBluetoothCastProfileManager(applicationContext, localBluetoothCastAdapter, cachedBluetoothCastDeviceManager, bluetoothCastEventManager);
        }
    }

    public static LocalBluetoothManager create(Context context, Handler handler, UserHandle userHandle) {
        if (sInstance == null) {
            LocalBluetoothAdapter localBluetoothAdapter = LocalBluetoothAdapter.getInstance();
            if (localBluetoothAdapter == null) {
                return null;
            }
            sInstance = new LocalBluetoothManager(localBluetoothAdapter, context, handler, userHandle);
        }
        return sInstance;
    }

    public static synchronized LocalBluetoothManager getInstance(Context context, BluetoothUtils.C09152 c09152) {
        synchronized (LocalBluetoothManager.class) {
            if (sInstance == null) {
                Log.d("LocalBluetoothManager", "LocalBluetoothManager :: sInstance == null");
                LocalBluetoothAdapter localBluetoothAdapter = LocalBluetoothAdapter.getInstance();
                if (localBluetoothAdapter == null) {
                    Log.d("LocalBluetoothManager", "LocalBluetoothManager :: adapter == null");
                    return null;
                }
                sInstance = new LocalBluetoothManager(localBluetoothAdapter, context, null, null);
                if (c09152 != null) {
                    Log.d("LocalBluetoothManager", "LocalBluetoothManager :: onInitCallback != null");
                    context.getApplicationContext();
                }
            }
            return sInstance;
        }
    }

    public final boolean isTetheredSettings() {
        String string = Settings.Secure.getString(this.mContext.getContentResolver(), "bluetooth_tethering_settings_foreground");
        Log.d("LocalBluetoothManager", "isTetheredSettings : " + string);
        return "true".equals(string);
    }

    public final boolean semIsForegroundActivity() {
        return Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "bluetooth_settings_foreground", 0, -2) != 0;
    }
}
