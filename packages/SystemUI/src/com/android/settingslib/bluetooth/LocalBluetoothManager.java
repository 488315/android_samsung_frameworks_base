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
import java.lang.ref.WeakReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LocalBluetoothManager {
    public static int mForegroundCount = 0;
    public static boolean mSystemUiInstance = false;
    public static LocalBluetoothManager sInstance;
    public final CachedBluetoothCastDeviceManager mCachedCastDeviceManager;
    public final CachedBluetoothDeviceManager mCachedDeviceManager;
    public final BluetoothCastEventManager mCastEventManager;
    public final Context mContext;
    public final BluetoothEventManager mEventManager;
    public WeakReference mForegroundActivity;
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
        BluetoothEventManager bluetoothEventManager = new BluetoothEventManager(localBluetoothAdapter, this, cachedBluetoothDeviceManager, applicationContext, handler, userHandle);
        this.mEventManager = bluetoothEventManager;
        LocalBluetoothProfileManager localBluetoothProfileManager = new LocalBluetoothProfileManager(applicationContext, localBluetoothAdapter, cachedBluetoothDeviceManager, bluetoothEventManager);
        this.mProfileManager = localBluetoothProfileManager;
        if ("com.android.systemui".equals(context.getPackageName().toLowerCase())) {
            mSystemUiInstance = true;
        }
        mForegroundCount = 0;
        this.mRestoredRetryDetector = new BluetoothRetryDetector(true);
        localBluetoothProfileManager.updateLocalProfiles();
        if (localBluetoothAdapter.mAdapter.isEnabled()) {
            bluetoothEventManager.readRestoredDevices();
        }
        bluetoothEventManager.readPairedDevices();
        if (SemBluetoothCastAdapter.isBluetoothCastSupported()) {
            synchronized (LocalBluetoothCastAdapter.class) {
                try {
                    if (LocalBluetoothCastAdapter.sInstance == null) {
                        LocalBluetoothCastAdapter.sInstance = new LocalBluetoothCastAdapter(applicationContext);
                    }
                    localBluetoothCastAdapter = LocalBluetoothCastAdapter.sInstance;
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mLocalCastAdapter = localBluetoothCastAdapter;
            CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager = new CachedBluetoothCastDeviceManager(applicationContext, this);
            this.mCachedCastDeviceManager = cachedBluetoothCastDeviceManager;
            BluetoothCastEventManager bluetoothCastEventManager = new BluetoothCastEventManager(localBluetoothCastAdapter, cachedBluetoothCastDeviceManager, applicationContext);
            this.mCastEventManager = bluetoothCastEventManager;
            this.mLocalCastProfileManager = new LocalBluetoothCastProfileManager(applicationContext, localBluetoothCastAdapter, cachedBluetoothCastDeviceManager, bluetoothCastEventManager);
        }
    }

    public static LocalBluetoothManager create(Context context, Handler handler) {
        UserHandle userHandle = UserHandle.ALL;
        if (sInstance == null) {
            LocalBluetoothAdapter localBluetoothAdapter = LocalBluetoothAdapter.getInstance();
            if (localBluetoothAdapter == null) {
                return null;
            }
            sInstance = new LocalBluetoothManager(localBluetoothAdapter, context, handler, userHandle);
        }
        return sInstance;
    }

    public static synchronized LocalBluetoothManager getInstance(Context context, BluetoothUtils.AnonymousClass2 anonymousClass2) {
        synchronized (LocalBluetoothManager.class) {
            if (sInstance == null) {
                Log.d("LocalBluetoothManager", "LocalBluetoothManager :: sInstance == null");
                LocalBluetoothAdapter localBluetoothAdapter = LocalBluetoothAdapter.getInstance();
                if (localBluetoothAdapter == null) {
                    Log.d("LocalBluetoothManager", "LocalBluetoothManager :: adapter == null");
                    return null;
                }
                sInstance = new LocalBluetoothManager(localBluetoothAdapter, context, null, null);
                if (anonymousClass2 != null) {
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
