package com.android.server.adb;

import android.R;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.debug.AdbNotifications;
import android.debug.PairDevice;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.InputConstants;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.SystemService;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Base64;
import android.util.Xml;
import android.util.sysfwutil.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.internal.util.dump.DumpUtils;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.knoxguard.KnoxGuardManager;
import com.samsung.android.knoxguard.service.utils.Constants;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AdbDebuggingManager {
    public AdbConnectionInfo mAdbConnectionInfo;
    public final String mConfirmComponent;
    public AdbConnectionPortPoller mConnectionPortPoller;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public String mFingerprints;
    final AdbDebuggingHandler mHandler;
    public final PortListenerImpl mPortListener;
    public final File mTempKeysFile;
    public AdbDebuggingThread mThread;
    public final Ticker mTicker;
    public final File mUserKeyFile;
    public static final AdbDebuggingManager$$ExternalSyntheticLambda0 SYSTEM_TICKER =
            new AdbDebuggingManager$$ExternalSyntheticLambda0();
    public static final long ADBD_STATE_CHANGE_TIMEOUT =
            InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
    public boolean mAdbUsbEnabled = false;
    public boolean mAdbWifiEnabled = false;
    public final Map mConnectedKeys = new HashMap();
    public PairingThread mPairingThread = null;
    public final Set mWifiConnectedKeys = new HashSet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdbConnectionInfo {
        public String mBssid;
        public int mPort;
        public String mSsid;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface AdbConnectionPortListener {
        void onPortReceived(int i);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdbConnectionPortPoller extends Thread {
        public final AtomicBoolean mCanceled = new AtomicBoolean(false);
        public final AdbConnectionPortListener mListener;

        public AdbConnectionPortPoller(AdbConnectionPortListener adbConnectionPortListener) {
            this.mListener = adbConnectionPortListener;
        }

        public final void cancelAndWait() {
            this.mCanceled.set(true);
            if (isAlive()) {
                try {
                    join();
                } catch (InterruptedException unused) {
                }
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            AdbDebuggingManager$$ExternalSyntheticLambda0
                    adbDebuggingManager$$ExternalSyntheticLambda0 =
                            AdbDebuggingManager.SYSTEM_TICKER;
            Slog.d("AdbDebuggingManager", "Starting adb port property poller");
            for (int i = 0; i < 10; i++) {
                if (this.mCanceled.get()) {
                    return;
                }
                int i2 = SystemProperties.getInt("service.adb.tls.port", Integer.MAX_VALUE);
                if (i2 == -1 || (i2 > 0 && i2 <= 65535)) {
                    this.mListener.onPortReceived(i2);
                    return;
                }
                SystemClock.sleep(1000L);
            }
            AdbDebuggingManager$$ExternalSyntheticLambda0
                    adbDebuggingManager$$ExternalSyntheticLambda02 =
                            AdbDebuggingManager.SYSTEM_TICKER;
            Slog.w("AdbDebuggingManager", "Failed to receive adb connection port");
            this.mListener.onPortReceived(-1);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdbDebuggingHandler extends Handler {
        public int mAdbEnabledRefCount;
        AdbKeyStore mAdbKeyStore;
        public boolean mAdbNotificationShown;
        public final AnonymousClass2 mAuthTimeObserver;
        public final AnonymousClass1 mBroadcastReceiver;
        public NotificationManager mNotificationManager;

        /* JADX WARN: Type inference failed for: r2v1, types: [com.android.server.adb.AdbDebuggingManager$AdbDebuggingHandler$1] */
        /* JADX WARN: Type inference failed for: r2v3, types: [com.android.server.adb.AdbDebuggingManager$AdbDebuggingHandler$2] */
        public AdbDebuggingHandler(Looper looper, AdbDebuggingThread adbDebuggingThread) {
            super(looper);
            this.mBroadcastReceiver =
                    new BroadcastReceiver() { // from class:
                        // com.android.server.adb.AdbDebuggingManager.AdbDebuggingHandler.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            String action = intent.getAction();
                            if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                                if (intent.getIntExtra("wifi_state", 1) == 1) {
                                    AdbDebuggingManager$$ExternalSyntheticLambda0
                                            adbDebuggingManager$$ExternalSyntheticLambda0 =
                                                    AdbDebuggingManager.SYSTEM_TICKER;
                                    Slog.i(
                                            "AdbDebuggingManager",
                                            "Wifi disabled. Disabling adbwifi.");
                                    Settings.Global.putInt(
                                            AdbDebuggingManager.this.mContentResolver,
                                            "adb_wifi_enabled",
                                            0);
                                    return;
                                }
                                return;
                            }
                            if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                                NetworkInfo networkInfo =
                                        (NetworkInfo)
                                                intent.getParcelableExtra(
                                                        "networkInfo", NetworkInfo.class);
                                if (networkInfo.getType() == 1) {
                                    if (!networkInfo.isConnected()) {
                                        AdbDebuggingManager$$ExternalSyntheticLambda0
                                                adbDebuggingManager$$ExternalSyntheticLambda02 =
                                                        AdbDebuggingManager.SYSTEM_TICKER;
                                        Slog.i(
                                                "AdbDebuggingManager",
                                                "Network disconnected. Disabling adbwifi.");
                                        Settings.Global.putInt(
                                                AdbDebuggingManager.this.mContentResolver,
                                                "adb_wifi_enabled",
                                                0);
                                        return;
                                    }
                                    WifiInfo connectionInfo =
                                            ((WifiManager)
                                                            AdbDebuggingManager.this.mContext
                                                                    .getSystemService("wifi"))
                                                    .getConnectionInfo();
                                    if (connectionInfo == null
                                            || connectionInfo.getNetworkId() == -1) {
                                        AdbDebuggingManager$$ExternalSyntheticLambda0
                                                adbDebuggingManager$$ExternalSyntheticLambda03 =
                                                        AdbDebuggingManager.SYSTEM_TICKER;
                                        Slog.i(
                                                "AdbDebuggingManager",
                                                "Not connected to any wireless network. Not"
                                                    + " enabling adbwifi.");
                                        Settings.Global.putInt(
                                                AdbDebuggingManager.this.mContentResolver,
                                                "adb_wifi_enabled",
                                                0);
                                        return;
                                    }
                                    synchronized (AdbDebuggingManager.this.mAdbConnectionInfo) {
                                        try {
                                            String bssid = connectionInfo.getBSSID();
                                            if (TextUtils.isEmpty(bssid)) {
                                                AdbDebuggingManager$$ExternalSyntheticLambda0
                                                        adbDebuggingManager$$ExternalSyntheticLambda04 =
                                                                AdbDebuggingManager.SYSTEM_TICKER;
                                                Slog.e(
                                                        "AdbDebuggingManager",
                                                        "Unable to get the wifi ap's BSSID."
                                                            + " Disabling adbwifi.");
                                                Settings.Global.putInt(
                                                        AdbDebuggingManager.this.mContentResolver,
                                                        "adb_wifi_enabled",
                                                        0);
                                            } else {
                                                if (!TextUtils.equals(
                                                        bssid,
                                                        AdbDebuggingManager.this
                                                                .mAdbConnectionInfo
                                                                .mBssid)) {
                                                    AdbDebuggingManager$$ExternalSyntheticLambda0
                                                            adbDebuggingManager$$ExternalSyntheticLambda05 =
                                                                    AdbDebuggingManager
                                                                            .SYSTEM_TICKER;
                                                    Slog.i(
                                                            "AdbDebuggingManager",
                                                            "Detected wifi network change."
                                                                + " Disabling adbwifi.");
                                                    Settings.Global.putInt(
                                                            AdbDebuggingManager.this
                                                                    .mContentResolver,
                                                            "adb_wifi_enabled",
                                                            0);
                                                }
                                            }
                                        } finally {
                                        }
                                    }
                                }
                            }
                        }
                    };
            this.mAdbEnabledRefCount = 0;
            this.mAuthTimeObserver =
                    new ContentObserver(
                            this) { // from class:
                                    // com.android.server.adb.AdbDebuggingManager.AdbDebuggingHandler.2
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z, Uri uri) {
                            AdbDebuggingManager$$ExternalSyntheticLambda0
                                    adbDebuggingManager$$ExternalSyntheticLambda0 =
                                            AdbDebuggingManager.SYSTEM_TICKER;
                            Slog.d(
                                    "AdbDebuggingManager",
                                    "Received notification that uri "
                                            + uri
                                            + " was modified; rescheduling keystore job");
                            AdbDebuggingHandler.this.scheduleJobToUpdateAdbKeyStore();
                        }
                    };
            AdbDebuggingManager.this.mThread = adbDebuggingThread;
        }

        public final AdbConnectionInfo getCurrentWifiApInfo() {
            String passpointProviderFriendlyName;
            WifiManager wifiManager =
                    (WifiManager) AdbDebuggingManager.this.mContext.getSystemService("wifi");
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo == null || connectionInfo.getNetworkId() == -1) {
                AdbDebuggingManager$$ExternalSyntheticLambda0
                        adbDebuggingManager$$ExternalSyntheticLambda0 =
                                AdbDebuggingManager.SYSTEM_TICKER;
                Slog.i(
                        "AdbDebuggingManager",
                        "Not connected to any wireless network. Not enabling adbwifi.");
                return null;
            }
            if (connectionInfo.isPasspointAp() || connectionInfo.isOsuAp()) {
                passpointProviderFriendlyName = connectionInfo.getPasspointProviderFriendlyName();
            } else {
                passpointProviderFriendlyName = connectionInfo.getSSID();
                if (passpointProviderFriendlyName == null
                        || "<unknown ssid>".equals(passpointProviderFriendlyName)) {
                    List<WifiConfiguration> configuredNetworks =
                            wifiManager.getConfiguredNetworks();
                    int size = configuredNetworks.size();
                    for (int i = 0; i < size; i++) {
                        if (configuredNetworks.get(i).networkId == connectionInfo.getNetworkId()) {
                            passpointProviderFriendlyName = configuredNetworks.get(i).SSID;
                        }
                    }
                    if (passpointProviderFriendlyName == null) {
                        AdbDebuggingManager$$ExternalSyntheticLambda0
                                adbDebuggingManager$$ExternalSyntheticLambda02 =
                                        AdbDebuggingManager.SYSTEM_TICKER;
                        Slog.e("AdbDebuggingManager", "Unable to get ssid of the wifi AP.");
                        return null;
                    }
                }
            }
            String bssid = connectionInfo.getBSSID();
            if (TextUtils.isEmpty(bssid)) {
                AdbDebuggingManager$$ExternalSyntheticLambda0
                        adbDebuggingManager$$ExternalSyntheticLambda03 =
                                AdbDebuggingManager.SYSTEM_TICKER;
                Slog.e("AdbDebuggingManager", "Unable to get the wifi ap's BSSID.");
                return null;
            }
            AdbConnectionInfo adbConnectionInfo = new AdbConnectionInfo();
            adbConnectionInfo.mBssid = bssid;
            adbConnectionInfo.mSsid = passpointProviderFriendlyName;
            return adbConnectionInfo;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String string;
            FileOutputStream startWrite;
            AdbDebuggingManager adbDebuggingManager;
            initKeyStore();
            FileOutputStream fileOutputStream = null;
            String str = null;
            switch (message.what) {
                case 1:
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda0 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.d("AdbDebuggingManager", "handleMessage -> MESSAGE_ADB_ENABLED");
                    if (AdbDebuggingManager.this.mAdbUsbEnabled) {
                        return;
                    }
                    startAdbDebuggingThread();
                    AdbDebuggingManager.this.mAdbUsbEnabled = true;
                    return;
                case 2:
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda02 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.d("AdbDebuggingManager", "handleMessage -> MESSAGE_ADB_DISABLED");
                    if (AdbDebuggingManager.this.mAdbUsbEnabled) {
                        stopAdbDebuggingThread();
                        AdbDebuggingManager.this.mAdbUsbEnabled = false;
                        return;
                    }
                    return;
                case 3:
                    String str2 = (String) message.obj;
                    String m144$$Nest$mgetFingerprints =
                            AdbDebuggingManager.m144$$Nest$mgetFingerprints(
                                    AdbDebuggingManager.this, str2);
                    if (!m144$$Nest$mgetFingerprints.equals(
                            AdbDebuggingManager.this.mFingerprints)) {
                        StringBuilder m =
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        "Fingerprints do not match. Got ",
                                        m144$$Nest$mgetFingerprints,
                                        ", expected ");
                        m.append(AdbDebuggingManager.this.mFingerprints);
                        Slog.e("AdbDebuggingManager", m.toString());
                        return;
                    }
                    r1 = message.arg1 == 1;
                    AdbDebuggingThread adbDebuggingThread = AdbDebuggingManager.this.mThread;
                    if (adbDebuggingThread != null) {
                        adbDebuggingThread.sendResponse("OK");
                        if (r1) {
                            if (!((HashMap) AdbDebuggingManager.this.mConnectedKeys)
                                    .containsKey(str2)) {
                                ((HashMap) AdbDebuggingManager.this.mConnectedKeys).put(str2, 1);
                            }
                            this.mAdbKeyStore.setLastConnectionTime(
                                    str2,
                                    AdbDebuggingManager.this.mTicker.currentTimeMillis(),
                                    false);
                            AdbDebuggingManager.m145$$Nest$msendPersistKeyStoreMessage(
                                    AdbDebuggingManager.this);
                            scheduleJobToUpdateAdbKeyStore();
                        }
                        logAdbConnectionChanged(2, str2, r1);
                    }
                    Slog.d("AdbDebuggingManager", "handleMessage -> MESSAGE_ADB_ALLOW");
                    return;
                case 4:
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda03 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.d("AdbDebuggingManager", "handleMessage -> MESSAGE_ADB_DENY");
                    if (AdbDebuggingManager.this.mThread != null) {
                        Slog.w("AdbDebuggingManager", "Denying adb confirmation");
                        AdbDebuggingManager.this.mThread.sendResponse("NO");
                        logAdbConnectionChanged(3, null, false);
                        return;
                    }
                    return;
                case 5:
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda04 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.d("AdbDebuggingManager", "handleMessage -> MESSAGE_ADB_CONFIRM");
                    String str3 = (String) message.obj;
                    if (""
                            .equals(
                                    AdbDebuggingManager.m144$$Nest$mgetFingerprints(
                                            AdbDebuggingManager.this, str3))) {
                        if (AdbDebuggingManager.this.mThread != null) {
                            Slog.d(
                                    "AdbDebuggingManager",
                                    "handleMessage -> MESSAGE_ADB_CONFIRM_DENY_2");
                            AdbDebuggingManager.this.mThread.sendResponse("NO");
                            logAdbConnectionChanged(5, str3, false);
                            return;
                        }
                        return;
                    }
                    logAdbConnectionChanged(1, str3, false);
                    AdbDebuggingManager adbDebuggingManager2 = AdbDebuggingManager.this;
                    adbDebuggingManager2.mFingerprints =
                            AdbDebuggingManager.m144$$Nest$mgetFingerprints(
                                    adbDebuggingManager2, str3);
                    if (SystemProperties.get("persist.sys.auto_confirm", "0").equals("1")) {
                        AdbDebuggingManager adbDebuggingManager3 = AdbDebuggingManager.this;
                        Message obtainMessage = adbDebuggingManager3.mHandler.obtainMessage(3);
                        obtainMessage.arg1 = 1;
                        obtainMessage.obj = str3;
                        adbDebuggingManager3.mHandler.sendMessage(obtainMessage);
                        return;
                    }
                    AdbDebuggingManager adbDebuggingManager4 = AdbDebuggingManager.this;
                    String str4 = adbDebuggingManager4.mFingerprints;
                    Slog.d("AdbDebuggingManager", "startConfirmation");
                    if (((KeyguardManager)
                                    adbDebuggingManager4.mContext.getSystemService("keyguard"))
                            .inKeyguardRestrictedInputMode()) {
                        Slog.d("AdbDebuggingManager", "startConfirmation: isLockScreenMode");
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new AbstractMap.SimpleEntry("key", str3));
                    arrayList.add(new AbstractMap.SimpleEntry("fingerprints", str4));
                    UserInfo userInfo =
                            UserManager.get(adbDebuggingManager4.mContext)
                                    .getUserInfo(ActivityManager.getCurrentUser());
                    if (userInfo.isAdmin()) {
                        string = adbDebuggingManager4.mConfirmComponent;
                        if (string == null) {
                            string =
                                    Resources.getSystem()
                                            .getString(R.string.data_saver_enable_title);
                        }
                    } else {
                        string = Resources.getSystem().getString(R.string.data_usage_limit_body);
                    }
                    ComponentName unflattenFromString = ComponentName.unflattenFromString(string);
                    if (adbDebuggingManager4.startConfirmationActivity(
                                    unflattenFromString, userInfo.getUserHandle(), arrayList)
                            || adbDebuggingManager4.startConfirmationService(
                                    unflattenFromString, userInfo.getUserHandle(), arrayList)) {
                        return;
                    }
                    Slog.e(
                            "AdbDebuggingManager",
                            "unable to start customAdbPublicKeyConfirmation[SecondaryUser]Component"
                                + " "
                                    + string
                                    + " as an Activity or a Service");
                    return;
                case 6:
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda05 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.d("AdbDebuggingManager", "handleMessage -> MESSAGE_ADB_CLEAR");
                    Slog.d(
                            "AdbDebuggingManager",
                            "Received a request to clear the adb authorizations");
                    ((HashMap) AdbDebuggingManager.this.mConnectedKeys).clear();
                    initKeyStore();
                    ((HashSet) AdbDebuggingManager.this.mWifiConnectedKeys).clear();
                    this.mAdbKeyStore.deleteKeyStore();
                    removeMessages(9);
                    if (Settings.Global.getInt(
                                            AdbDebuggingManager.this.mContentResolver,
                                            "adb_disconnect_sessions_on_revoke",
                                            1)
                                    == 1
                            && AdbDebuggingManager.this.mAdbUsbEnabled) {
                        try {
                            SystemService.stop("adbd");
                            SystemService.State state = SystemService.State.STOPPED;
                            long j = AdbDebuggingManager.ADBD_STATE_CHANGE_TIMEOUT;
                            SystemService.waitForState("adbd", state, j);
                            SystemService.start("adbd");
                            SystemService.waitForState("adbd", SystemService.State.RUNNING, j);
                            return;
                        } catch (TimeoutException e) {
                            AdbDebuggingManager$$ExternalSyntheticLambda0
                                    adbDebuggingManager$$ExternalSyntheticLambda06 =
                                            AdbDebuggingManager.SYSTEM_TICKER;
                            Slog.e(
                                    "AdbDebuggingManager",
                                    "Timeout occurred waiting for adbd to cycle: ",
                                    e);
                            Settings.Global.putInt(
                                    AdbDebuggingManager.this.mContentResolver, "adb_enabled", 0);
                            return;
                        }
                    }
                    return;
                case 7:
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda07 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.d("AdbDebuggingManager", "handleMessage -> MESSAGE_ADB_DISCONNECT");
                    String str5 = (String) message.obj;
                    if (str5 == null || str5.length() <= 0) {
                        Slog.w(
                                "AdbDebuggingManager",
                                "Received a disconnected key message with an empty key");
                    } else if (((HashMap) AdbDebuggingManager.this.mConnectedKeys)
                            .containsKey(str5)) {
                        int intValue =
                                ((Integer)
                                                        ((HashMap)
                                                                        AdbDebuggingManager.this
                                                                                .mConnectedKeys)
                                                                .get(str5))
                                                .intValue()
                                        - 1;
                        if (intValue == 0) {
                            this.mAdbKeyStore.setLastConnectionTime(
                                    str5,
                                    AdbDebuggingManager.this.mTicker.currentTimeMillis(),
                                    false);
                            AdbDebuggingManager.m145$$Nest$msendPersistKeyStoreMessage(
                                    AdbDebuggingManager.this);
                            scheduleJobToUpdateAdbKeyStore();
                            ((HashMap) AdbDebuggingManager.this.mConnectedKeys).remove(str5);
                        } else {
                            ((HashMap) AdbDebuggingManager.this.mConnectedKeys)
                                    .put(str5, Integer.valueOf(intValue));
                        }
                        logAdbConnectionChanged(7, str5, r1);
                        return;
                    }
                    r1 = false;
                    logAdbConnectionChanged(7, str5, r1);
                    return;
                case 8:
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda08 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.d("AdbDebuggingManager", "handleMessage -> MESSAGE_ADB_PERSIST_KEYSTORE");
                    AdbKeyStore adbKeyStore = this.mAdbKeyStore;
                    if (adbKeyStore != null) {
                        adbKeyStore.filterOutOldKeys();
                        if (((HashMap) adbKeyStore.mKeyMap).isEmpty()
                                && ((ArrayList) adbKeyStore.mTrustedNetworks).isEmpty()) {
                            adbKeyStore.deleteKeyStore();
                            return;
                        }
                        AtomicFile atomicFile = adbKeyStore.mAtomicKeyFile;
                        AdbDebuggingManager adbDebuggingManager5 = AdbDebuggingManager.this;
                        if (atomicFile == null) {
                            if (adbDebuggingManager5.mTempKeysFile != null) {
                                adbKeyStore.mAtomicKeyFile =
                                        new AtomicFile(adbDebuggingManager5.mTempKeysFile);
                            }
                            if (adbKeyStore.mAtomicKeyFile == null) {
                                Slog.e(
                                        "AdbDebuggingManager",
                                        "Unable to obtain the key file, "
                                                + adbDebuggingManager5.mTempKeysFile
                                                + ", for writing");
                                return;
                            }
                        }
                        try {
                            startWrite = adbKeyStore.mAtomicKeyFile.startWrite();
                        } catch (IOException e2) {
                            e = e2;
                        }
                        try {
                            TypedXmlSerializer resolveSerializer =
                                    Xml.resolveSerializer(startWrite);
                            resolveSerializer.startDocument((String) null, Boolean.TRUE);
                            resolveSerializer.startTag((String) null, "keyStore");
                            resolveSerializer.attributeInt((String) null, "version", 1);
                            for (Map.Entry entry : ((HashMap) adbKeyStore.mKeyMap).entrySet()) {
                                resolveSerializer.startTag((String) null, "adbKey");
                                resolveSerializer.attribute(
                                        (String) null, "key", (String) entry.getKey());
                                resolveSerializer.attributeLong(
                                        (String) null,
                                        "lastConnection",
                                        ((Long) entry.getValue()).longValue());
                                resolveSerializer.endTag((String) null, "adbKey");
                            }
                            Iterator it = ((ArrayList) adbKeyStore.mTrustedNetworks).iterator();
                            while (it.hasNext()) {
                                String str6 = (String) it.next();
                                resolveSerializer.startTag((String) null, "wifiAP");
                                resolveSerializer.attribute((String) null, "bssid", str6);
                                resolveSerializer.endTag((String) null, "wifiAP");
                            }
                            resolveSerializer.endTag((String) null, "keyStore");
                            resolveSerializer.endDocument();
                            adbKeyStore.mAtomicKeyFile.finishWrite(startWrite);
                        } catch (IOException e3) {
                            e = e3;
                            fileOutputStream = startWrite;
                            AdbDebuggingManager$$ExternalSyntheticLambda0
                                    adbDebuggingManager$$ExternalSyntheticLambda09 =
                                            AdbDebuggingManager.SYSTEM_TICKER;
                            Slog.e(
                                    "AdbDebuggingManager",
                                    "Caught an exception writing the key map: ",
                                    e);
                            adbKeyStore.mAtomicKeyFile.failWrite(fileOutputStream);
                            AdbDebuggingManager.m147$$Nest$mwriteKeys(
                                    adbDebuggingManager5, ((HashMap) adbKeyStore.mKeyMap).keySet());
                            return;
                        }
                        AdbDebuggingManager.m147$$Nest$mwriteKeys(
                                adbDebuggingManager5, ((HashMap) adbKeyStore.mKeyMap).keySet());
                        return;
                    }
                    return;
                case 9:
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda010 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.d("AdbDebuggingManager", "handleMessage -> MESSAGE_ADB_UPDATE_KEYSTORE");
                    if (!((HashMap) AdbDebuggingManager.this.mConnectedKeys).isEmpty()) {
                        Iterator it2 =
                                ((HashMap) AdbDebuggingManager.this.mConnectedKeys)
                                        .entrySet()
                                        .iterator();
                        while (it2.hasNext()) {
                            this.mAdbKeyStore.setLastConnectionTime(
                                    (String) ((Map.Entry) it2.next()).getKey(),
                                    AdbDebuggingManager.this.mTicker.currentTimeMillis(),
                                    false);
                        }
                        AdbDebuggingManager.m145$$Nest$msendPersistKeyStoreMessage(
                                AdbDebuggingManager.this);
                        scheduleJobToUpdateAdbKeyStore();
                        return;
                    }
                    if (((HashMap) this.mAdbKeyStore.mKeyMap).isEmpty()) {
                        return;
                    }
                    AdbKeyStore adbKeyStore2 = this.mAdbKeyStore;
                    if (adbKeyStore2.filterOutOldKeys()) {
                        AdbDebuggingManager.m145$$Nest$msendPersistKeyStoreMessage(
                                AdbDebuggingManager.this);
                    }
                    scheduleJobToUpdateAdbKeyStore();
                    return;
                case 10:
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda011 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.d("AdbDebuggingManager", "handleMessage -> MESSAGE_ADB_CONNECTED_KEY");
                    String str7 = (String) message.obj;
                    if (str7 == null || str7.length() == 0) {
                        Slog.w(
                                "AdbDebuggingManager",
                                "Received a connected key message with an empty key");
                        return;
                    }
                    if (((HashMap) AdbDebuggingManager.this.mConnectedKeys).containsKey(str7)) {
                        HashMap hashMap = (HashMap) AdbDebuggingManager.this.mConnectedKeys;
                        hashMap.put(
                                str7,
                                Integer.valueOf(((Integer) hashMap.get(str7)).intValue() + 1));
                    } else {
                        ((HashMap) AdbDebuggingManager.this.mConnectedKeys).put(str7, 1);
                    }
                    this.mAdbKeyStore.setLastConnectionTime(
                            str7, AdbDebuggingManager.this.mTicker.currentTimeMillis(), false);
                    AdbDebuggingManager.m145$$Nest$msendPersistKeyStoreMessage(
                            AdbDebuggingManager.this);
                    scheduleJobToUpdateAdbKeyStore();
                    logAdbConnectionChanged(4, str7, true);
                    return;
                case 11:
                    if (AdbDebuggingManager.this.mAdbWifiEnabled) {
                        return;
                    }
                    if (!KnoxGuardManager.getInstance().isKGAllowADB()) {
                        Settings.Global.putInt(
                                AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 0);
                        return;
                    }
                    AdbConnectionInfo currentWifiApInfo = getCurrentWifiApInfo();
                    if (currentWifiApInfo == null) {
                        Settings.Global.putInt(
                                AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 0);
                        return;
                    }
                    String str8 = currentWifiApInfo.mBssid;
                    String str9 = currentWifiApInfo.mSsid;
                    if (((ArrayList) this.mAdbKeyStore.mTrustedNetworks).contains(str8)) {
                        AdbDebuggingManager.m146$$Nest$msetAdbConnectionInfo(
                                AdbDebuggingManager.this, currentWifiApInfo);
                        IntentFilter intentFilter =
                                new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
                        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
                        AdbDebuggingManager.this.mContext.registerReceiver(
                                this.mBroadcastReceiver, intentFilter);
                        SystemProperties.set("persist.adb.tls_server.enable", "1");
                        AdbDebuggingManager.this.mConnectionPortPoller =
                                new AdbConnectionPortPoller(AdbDebuggingManager.this.mPortListener);
                        AdbDebuggingManager.this.mConnectionPortPoller.start();
                        startAdbDebuggingThread();
                        AdbDebuggingManager.this.mAdbWifiEnabled = true;
                        Slog.i("AdbDebuggingManager", "adb start wireless adb");
                        return;
                    }
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda012 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    AdbDebuggingManager adbDebuggingManager6 = AdbDebuggingManager.this;
                    adbDebuggingManager6.getClass();
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(new AbstractMap.SimpleEntry("ssid", str9));
                    arrayList2.add(new AbstractMap.SimpleEntry("bssid", str8));
                    Slog.d("AdbDebuggingManager", "startConfirmationForNetwork");
                    if (((KeyguardManager)
                                    adbDebuggingManager6.mContext.getSystemService("keyguard"))
                            .inKeyguardRestrictedInputMode()) {
                        Slog.d(
                                "AdbDebuggingManager",
                                "startConfirmationForNetwork: isLockScreenMode");
                    } else {
                        int currentUser = ActivityManager.getCurrentUser();
                        String string2 =
                                Resources.getSystem()
                                        .getString(R.string.data_usage_limit_snoozed_body);
                        ComponentName unflattenFromString2 =
                                ComponentName.unflattenFromString(string2);
                        UserInfo userInfo2 =
                                UserManager.get(adbDebuggingManager6.mContext)
                                        .getUserInfo(currentUser);
                        if (!adbDebuggingManager6.startConfirmationActivity(
                                        unflattenFromString2, userInfo2.getUserHandle(), arrayList2)
                                && !adbDebuggingManager6.startConfirmationService(
                                        unflattenFromString2,
                                        userInfo2.getUserHandle(),
                                        arrayList2)) {
                            Slog.e(
                                    "AdbDebuggingManager",
                                    "Unable to start"
                                        + " customAdbWifiNetworkConfirmation[SecondaryUser]Component"
                                        + " "
                                            + string2
                                            + " as an Activity or a Service");
                        }
                    }
                    Settings.Global.putInt(
                            AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 0);
                    return;
                case 12:
                    AdbDebuggingManager adbDebuggingManager7 = AdbDebuggingManager.this;
                    if (adbDebuggingManager7.mAdbWifiEnabled) {
                        adbDebuggingManager7.mAdbWifiEnabled = false;
                        AdbDebuggingManager.m146$$Nest$msetAdbConnectionInfo(
                                adbDebuggingManager7, null);
                        AdbDebuggingManager.this.mContext.unregisterReceiver(
                                this.mBroadcastReceiver);
                        AdbDebuggingThread adbDebuggingThread2 = AdbDebuggingManager.this.mThread;
                        if (adbDebuggingThread2 != null) {
                            adbDebuggingThread2.sendResponse("DA");
                        }
                        onAdbdWifiServerDisconnected(-1);
                        stopAdbDebuggingThread();
                        return;
                    }
                    return;
                case 13:
                default:
                    return;
                case 14:
                    PairingThread pairingThread = AdbDebuggingManager.this.mPairingThread;
                    if (pairingThread != null) {
                        pairingThread.cancelPairing();
                        try {
                            AdbDebuggingManager.this.mPairingThread.join();
                        } catch (InterruptedException e4) {
                            AdbDebuggingManager$$ExternalSyntheticLambda0
                                    adbDebuggingManager$$ExternalSyntheticLambda013 =
                                            AdbDebuggingManager.SYSTEM_TICKER;
                            Slog.w(
                                    "AdbDebuggingManager",
                                    "Error while waiting for pairing thread to quit.");
                            e4.printStackTrace();
                        }
                        AdbDebuggingManager.this.mPairingThread = null;
                        return;
                    }
                    return;
                case 15:
                    SecureRandom secureRandom = new SecureRandom();
                    String str10 = "";
                    for (int i = 0; i < 6; i++) {
                        StringBuilder m2 = BootReceiver$$ExternalSyntheticOutline0.m(str10);
                        m2.append(secureRandom.nextInt(10));
                        str10 = m2.toString();
                    }
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda014 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.i("AdbDebuggingManager", "updateUIPairCode: " + str10);
                    Intent intent =
                            new Intent("com.android.server.adb.WIRELESS_DEBUG_PAIRING_RESULT");
                    intent.putExtra("pairing_code", str10);
                    intent.putExtra(Constants.JSON_CLIENT_DATA_STATUS, 3);
                    AdbDebuggingManager.sendBroadcastWithDebugPermission(
                            AdbDebuggingManager.this.mContext, intent, UserHandle.ALL);
                    AdbDebuggingManager.this.mPairingThread =
                            AdbDebuggingManager.this.new PairingThread(str10, null);
                    AdbDebuggingManager.this.mPairingThread.start();
                    return;
                case 16:
                    Bundle bundle = (Bundle) message.obj;
                    AdbDebuggingManager.this.mPairingThread =
                            AdbDebuggingManager.this
                            .new PairingThread(
                                    bundle.getString("password"), bundle.getString("serviceName"));
                    AdbDebuggingManager.this.mPairingThread.start();
                    return;
                case 17:
                    String str11 = (String) message.obj;
                    AdbKeyStore adbKeyStore3 = this.mAdbKeyStore;
                    Iterator it3 = ((HashMap) adbKeyStore3.mKeyMap).entrySet().iterator();
                    while (true) {
                        if (it3.hasNext()) {
                            Map.Entry entry2 = (Map.Entry) it3.next();
                            if (str11.equals(
                                    AdbDebuggingManager.m144$$Nest$mgetFingerprints(
                                            AdbDebuggingManager.this, (String) entry2.getKey()))) {
                                str = (String) entry2.getKey();
                            }
                        }
                    }
                    if (str == null || str.isEmpty()) {
                        AdbDebuggingManager$$ExternalSyntheticLambda0
                                adbDebuggingManager$$ExternalSyntheticLambda015 =
                                        AdbDebuggingManager.SYSTEM_TICKER;
                        Slog.e("AdbDebuggingManager", "Not a known fingerprint [" + str11 + "]");
                        return;
                    }
                    String concat = "DD".concat(str);
                    AdbDebuggingThread adbDebuggingThread3 = AdbDebuggingManager.this.mThread;
                    if (adbDebuggingThread3 != null) {
                        adbDebuggingThread3.sendResponse(concat);
                    }
                    AdbKeyStore adbKeyStore4 = this.mAdbKeyStore;
                    if (((HashMap) adbKeyStore4.mKeyMap).containsKey(str)) {
                        ((HashMap) adbKeyStore4.mKeyMap).remove(str);
                        AdbDebuggingManager.m145$$Nest$msendPersistKeyStoreMessage(
                                AdbDebuggingManager.this);
                    }
                    sendPairedDevicesToUI(this.mAdbKeyStore.getPairedDevices());
                    return;
                case 18:
                    if (AdbDebuggingManager.this.mAdbWifiEnabled) {
                        return;
                    }
                    String str12 = (String) message.obj;
                    if (message.arg1 == 1) {
                        AdbKeyStore adbKeyStore5 = this.mAdbKeyStore;
                        ((ArrayList) adbKeyStore5.mTrustedNetworks).add(str12);
                        AdbDebuggingManager.m145$$Nest$msendPersistKeyStoreMessage(
                                AdbDebuggingManager.this);
                    }
                    AdbConnectionInfo currentWifiApInfo2 = getCurrentWifiApInfo();
                    if (currentWifiApInfo2 == null || !str12.equals(currentWifiApInfo2.mBssid)) {
                        return;
                    }
                    AdbDebuggingManager.m146$$Nest$msetAdbConnectionInfo(
                            AdbDebuggingManager.this, currentWifiApInfo2);
                    Settings.Global.putInt(
                            AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 1);
                    IntentFilter intentFilter2 =
                            new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
                    intentFilter2.addAction("android.net.wifi.STATE_CHANGE");
                    AdbDebuggingManager.this.mContext.registerReceiver(
                            this.mBroadcastReceiver, intentFilter2);
                    SystemProperties.set("persist.adb.tls_server.enable", "1");
                    AdbDebuggingManager.this.mConnectionPortPoller =
                            new AdbConnectionPortPoller(AdbDebuggingManager.this.mPortListener);
                    AdbDebuggingManager.this.mConnectionPortPoller.start();
                    startAdbDebuggingThread();
                    AdbDebuggingManager.this.mAdbWifiEnabled = true;
                    Slog.i("AdbDebuggingManager", "adb start wireless adb");
                    return;
                case 19:
                    Settings.Global.putInt(
                            AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 0);
                    sendServerConnectionState(-1, false);
                    return;
                case 20:
                    String string3 = ((Bundle) message.obj).getString("publicKey");
                    AdbDebuggingManager adbDebuggingManager8 = AdbDebuggingManager.this;
                    if (string3 == null) {
                        Intent intent2 =
                                new Intent("com.android.server.adb.WIRELESS_DEBUG_PAIRING_RESULT");
                        intent2.putExtra(Constants.JSON_CLIENT_DATA_STATUS, 0);
                        AdbDebuggingManager.sendBroadcastWithDebugPermission(
                                adbDebuggingManager8.mContext, intent2, UserHandle.ALL);
                    } else {
                        Intent intent3 =
                                new Intent("com.android.server.adb.WIRELESS_DEBUG_PAIRING_RESULT");
                        intent3.putExtra(Constants.JSON_CLIENT_DATA_STATUS, 1);
                        String m144$$Nest$mgetFingerprints2 =
                                AdbDebuggingManager.m144$$Nest$mgetFingerprints(
                                        adbDebuggingManager8, string3);
                        String[] split = string3.split("\\s+");
                        String str13 = split.length > 1 ? split[1] : "nouser@nohostname";
                        PairDevice pairDevice = new PairDevice();
                        pairDevice.name = m144$$Nest$mgetFingerprints2;
                        pairDevice.guid = str13;
                        pairDevice.connected = false;
                        intent3.putExtra("pair_device", (Parcelable) pairDevice);
                        AdbDebuggingManager.sendBroadcastWithDebugPermission(
                                adbDebuggingManager8.mContext, intent3, UserHandle.ALL);
                        this.mAdbKeyStore.setLastConnectionTime(
                                string3, adbDebuggingManager8.mTicker.currentTimeMillis(), false);
                        AdbDebuggingManager.m145$$Nest$msendPersistKeyStoreMessage(
                                adbDebuggingManager8);
                        scheduleJobToUpdateAdbKeyStore();
                    }
                    sendPairedDevicesToUI(this.mAdbKeyStore.getPairedDevices());
                    return;
                case 21:
                    int intValue2 = ((Integer) message.obj).intValue();
                    Intent intent4 =
                            new Intent("com.android.server.adb.WIRELESS_DEBUG_PAIRING_RESULT");
                    intent4.putExtra(Constants.JSON_CLIENT_DATA_STATUS, 4);
                    intent4.putExtra("adb_port", intValue2);
                    AdbDebuggingManager.sendBroadcastWithDebugPermission(
                            AdbDebuggingManager.this.mContext, intent4, UserHandle.ALL);
                    return;
                case 22:
                    if (((HashSet) AdbDebuggingManager.this.mWifiConnectedKeys)
                            .add((String) message.obj)) {
                        sendPairedDevicesToUI(this.mAdbKeyStore.getPairedDevices());
                        showAdbConnectedNotification(true);
                        return;
                    }
                    return;
                case 23:
                    if (((HashSet) AdbDebuggingManager.this.mWifiConnectedKeys)
                            .remove((String) message.obj)) {
                        sendPairedDevicesToUI(this.mAdbKeyStore.getPairedDevices());
                        if (((HashSet) AdbDebuggingManager.this.mWifiConnectedKeys).isEmpty()) {
                            showAdbConnectedNotification(false);
                            return;
                        }
                        return;
                    }
                    return;
                case 24:
                    int intValue3 = ((Integer) message.obj).intValue();
                    sendPairedDevicesToUI(this.mAdbKeyStore.getPairedDevices());
                    sendServerConnectionState(intValue3, true);
                    synchronized (AdbDebuggingManager.this.mAdbConnectionInfo) {
                        adbDebuggingManager = AdbDebuggingManager.this;
                        adbDebuggingManager.mAdbConnectionInfo.mPort = intValue3;
                    }
                    Settings.Global.putInt(
                            adbDebuggingManager.mContentResolver, "adb_wifi_enabled", 1);
                    return;
                case 25:
                    if (AdbDebuggingManager.this.mAdbWifiEnabled) {
                        onAdbdWifiServerDisconnected(((Integer) message.obj).intValue());
                        Settings.Global.putInt(
                                AdbDebuggingManager.this.mContentResolver, "adb_wifi_enabled", 0);
                        stopAdbDebuggingThread();
                        AdbConnectionPortPoller adbConnectionPortPoller =
                                AdbDebuggingManager.this.mConnectionPortPoller;
                        if (adbConnectionPortPoller != null) {
                            adbConnectionPortPoller.cancelAndWait();
                            AdbDebuggingManager.this.mConnectionPortPoller = null;
                            return;
                        }
                        return;
                    }
                    return;
                case 26:
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda016 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.d("AdbDebuggingManager", "adbd socket connected");
                    AdbDebuggingManager adbDebuggingManager9 = AdbDebuggingManager.this;
                    if (adbDebuggingManager9.mAdbWifiEnabled) {
                        adbDebuggingManager9.mConnectionPortPoller =
                                new AdbConnectionPortPoller(AdbDebuggingManager.this.mPortListener);
                        AdbDebuggingManager.this.mConnectionPortPoller.start();
                        return;
                    }
                    return;
                case 27:
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda017 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.d("AdbDebuggingManager", "adbd socket disconnected");
                    AdbConnectionPortPoller adbConnectionPortPoller2 =
                            AdbDebuggingManager.this.mConnectionPortPoller;
                    if (adbConnectionPortPoller2 != null) {
                        adbConnectionPortPoller2.cancelAndWait();
                        AdbDebuggingManager.this.mConnectionPortPoller = null;
                    }
                    if (AdbDebuggingManager.this.mAdbWifiEnabled) {
                        onAdbdWifiServerDisconnected(-1);
                        return;
                    }
                    return;
                case 28:
                    this.mAdbKeyStore.readTempKeysFile();
                    return;
            }
        }

        public void initKeyStore() {
            if (this.mAdbKeyStore == null) {
                this.mAdbKeyStore = AdbDebuggingManager.this.new AdbKeyStore();
            }
        }

        public final void logAdbConnectionChanged(int i, String str, boolean z) {
            long longValue =
                    ((Long) ((HashMap) this.mAdbKeyStore.mKeyMap).getOrDefault(str, 0L))
                            .longValue();
            long j =
                    Settings.Global.getLong(
                            AdbDebuggingManager.this.mContext.getContentResolver(),
                            "adb_allowed_connection_time",
                            604800000L);
            AdbDebuggingManager$$ExternalSyntheticLambda0
                    adbDebuggingManager$$ExternalSyntheticLambda0 =
                            AdbDebuggingManager.SYSTEM_TICKER;
            StringBuilder m =
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i, "Logging key ", str, ", state = ", ", alwaysAllow = ");
            m.append(z);
            m.append(", lastConnectionTime = ");
            m.append(longValue);
            m.append(", authWindow = ");
            m.append(j);
            Slog.d("AdbDebuggingManager", m.toString());
            FrameworkStatsLog.write(144, longValue, j, i, z);
        }

        public final void onAdbdWifiServerDisconnected(int i) {
            ((HashSet) AdbDebuggingManager.this.mWifiConnectedKeys).clear();
            showAdbConnectedNotification(false);
            sendServerConnectionState(i, false);
        }

        public long scheduleJobToUpdateAdbKeyStore() {
            long j;
            removeMessages(9);
            AdbKeyStore adbKeyStore = this.mAdbKeyStore;
            long j2 =
                    Settings.Global.getLong(
                            AdbDebuggingManager.this.mContext.getContentResolver(),
                            "adb_allowed_connection_time",
                            604800000L);
            if (j2 == 0) {
                j = -1;
            } else {
                long currentTimeMillis = AdbDebuggingManager.this.mTicker.currentTimeMillis();
                Iterator it = ((HashMap) adbKeyStore.mKeyMap).entrySet().iterator();
                j = -1;
                while (it.hasNext()) {
                    long max =
                            Math.max(
                                    0L,
                                    (((Long) ((Map.Entry) it.next()).getValue()).longValue() + j2)
                                            - currentTimeMillis);
                    if (j == -1 || max < j) {
                        j = max;
                    }
                }
            }
            if (j == -1) {
                return -1L;
            }
            long max2 =
                    j != 0
                            ? Math.max(
                                    Math.min(
                                            BackupManagerConstants
                                                    .DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS,
                                            j),
                                    60000L)
                            : 0L;
            sendMessageDelayed(obtainMessage(9), max2);
            return max2;
        }

        public final void sendPairedDevicesToUI(Map map) {
            Intent intent = new Intent("com.android.server.adb.WIRELESS_DEBUG_PAIRED_DEVICES");
            intent.putExtra("devices_map", (HashMap) map);
            AdbDebuggingManager.sendBroadcastWithDebugPermission(
                    AdbDebuggingManager.this.mContext, intent, UserHandle.ALL);
        }

        public final void sendServerConnectionState(int i, boolean z) {
            Intent intent = new Intent("com.android.server.adb.WIRELESS_DEBUG_STATUS");
            intent.putExtra(Constants.JSON_CLIENT_DATA_STATUS, z ? 4 : 5);
            intent.putExtra("adb_port", i);
            AdbDebuggingManager.sendBroadcastWithDebugPermission(
                    AdbDebuggingManager.this.mContext, intent, UserHandle.ALL);
        }

        public final void showAdbConnectedNotification(boolean z) {
            if (z == this.mAdbNotificationShown) {
                return;
            }
            NotificationManager notificationManager = this.mNotificationManager;
            AdbDebuggingManager adbDebuggingManager = AdbDebuggingManager.this;
            if (notificationManager == null) {
                NotificationManager notificationManager2 =
                        (NotificationManager)
                                adbDebuggingManager.mContext.getSystemService("notification");
                this.mNotificationManager = notificationManager2;
                if (notificationManager2 == null) {
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda0 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.e(
                            "AdbDebuggingManager",
                            "Unable to setup notifications for wireless debugging");
                } else if (adbDebuggingManager
                        .mContext
                        .getPackageManager()
                        .hasSystemFeature("android.software.leanback")) {
                    this.mNotificationManager.createNotificationChannel(
                            new NotificationChannel(
                                    "usbdevicemanager.adb.tv",
                                    adbDebuggingManager.mContext.getString(
                                            R.string.biometric_error_user_canceled),
                                    4));
                }
            }
            if (this.mAdbNotificationShown) {
                this.mAdbNotificationShown = false;
                this.mNotificationManager.cancelAsUser(null, 62, UserHandle.ALL);
            } else {
                Notification createNotification =
                        AdbNotifications.createNotification(adbDebuggingManager.mContext, (byte) 1);
                this.mAdbNotificationShown = true;
                this.mNotificationManager.notifyAsUser(
                        null, 62, createNotification, UserHandle.ALL);
            }
        }

        public final void startAdbDebuggingThread() {
            this.mAdbEnabledRefCount++;
            AdbDebuggingManager$$ExternalSyntheticLambda0
                    adbDebuggingManager$$ExternalSyntheticLambda0 =
                            AdbDebuggingManager.SYSTEM_TICKER;
            Slog.i(
                    "AdbDebuggingManager",
                    "startAdbDebuggingThread ref=" + this.mAdbEnabledRefCount);
            if (this.mAdbEnabledRefCount > 1) {
                return;
            }
            Uri uriFor = Settings.Global.getUriFor("adb_allowed_connection_time");
            AdbDebuggingManager adbDebuggingManager = AdbDebuggingManager.this;
            adbDebuggingManager
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(uriFor, false, this.mAuthTimeObserver);
            AdbDebuggingThread adbDebuggingThread = new AdbDebuggingThread();
            adbDebuggingManager.mThread = adbDebuggingThread;
            adbDebuggingThread.setHandler(adbDebuggingManager.mHandler);
            adbDebuggingManager.mThread.start();
            AdbKeyStore adbKeyStore = this.mAdbKeyStore;
            if (adbKeyStore.filterOutOldKeys()) {
                AdbDebuggingManager.m145$$Nest$msendPersistKeyStoreMessage(
                        AdbDebuggingManager.this);
            }
            scheduleJobToUpdateAdbKeyStore();
        }

        public final void stopAdbDebuggingThread() {
            this.mAdbEnabledRefCount--;
            AdbDebuggingManager$$ExternalSyntheticLambda0
                    adbDebuggingManager$$ExternalSyntheticLambda0 =
                            AdbDebuggingManager.SYSTEM_TICKER;
            Slog.i("AdbDebuggingManager", "stopAdbDebuggingThread ref=" + this.mAdbEnabledRefCount);
            if (this.mAdbEnabledRefCount > 0) {
                return;
            }
            AdbDebuggingThread adbDebuggingThread = AdbDebuggingManager.this.mThread;
            if (adbDebuggingThread != null) {
                synchronized (adbDebuggingThread) {
                    adbDebuggingThread.mStopped = true;
                    adbDebuggingThread.closeSocketLocked();
                }
                AdbDebuggingManager.this.mThread = null;
            }
            if (!((HashMap) AdbDebuggingManager.this.mConnectedKeys).isEmpty()) {
                Iterator it =
                        ((HashMap) AdbDebuggingManager.this.mConnectedKeys).entrySet().iterator();
                while (it.hasNext()) {
                    this.mAdbKeyStore.setLastConnectionTime(
                            (String) ((Map.Entry) it.next()).getKey(),
                            AdbDebuggingManager.this.mTicker.currentTimeMillis(),
                            false);
                }
                AdbDebuggingManager.m145$$Nest$msendPersistKeyStoreMessage(
                        AdbDebuggingManager.this);
                ((HashMap) AdbDebuggingManager.this.mConnectedKeys).clear();
                ((HashSet) AdbDebuggingManager.this.mWifiConnectedKeys).clear();
            }
            scheduleJobToUpdateAdbKeyStore();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class AdbDebuggingThread extends Thread {
        public Handler mHandler;
        public InputStream mInputStream;
        public OutputStream mOutputStream;
        public LocalSocket mSocket;
        public boolean mStopped;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AdbDebuggingThread() {
            super("AdbDebuggingManager");
            AdbDebuggingManager$$ExternalSyntheticLambda0
                    adbDebuggingManager$$ExternalSyntheticLambda0 =
                            AdbDebuggingManager.SYSTEM_TICKER;
        }

        public final void closeSocketLocked() {
            AdbDebuggingManager$$ExternalSyntheticLambda0
                    adbDebuggingManager$$ExternalSyntheticLambda0 =
                            AdbDebuggingManager.SYSTEM_TICKER;
            Slog.d("AdbDebuggingManager", "Closing socket");
            try {
                OutputStream outputStream = this.mOutputStream;
                if (outputStream != null) {
                    outputStream.close();
                    this.mOutputStream = null;
                }
            } catch (IOException e) {
                AdbDebuggingManager$$ExternalSyntheticLambda0
                        adbDebuggingManager$$ExternalSyntheticLambda02 =
                                AdbDebuggingManager.SYSTEM_TICKER;
                Slog.e("AdbDebuggingManager", "Failed closing output stream: " + e);
            }
            try {
                LocalSocket localSocket = this.mSocket;
                if (localSocket != null) {
                    localSocket.close();
                    this.mSocket = null;
                }
            } catch (IOException e2) {
                AdbDebuggingManager$$ExternalSyntheticLambda0
                        adbDebuggingManager$$ExternalSyntheticLambda03 =
                                AdbDebuggingManager.SYSTEM_TICKER;
                Slog.e("AdbDebuggingManager", "Failed closing socket: " + e2);
            }
            this.mHandler.sendEmptyMessage(27);
        }

        public final void listenToSocket() {
            try {
                try {
                    byte[] bArr =
                            new byte[EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT];
                    while (true) {
                        int read = this.mInputStream.read(bArr);
                        if (read < 2) {
                            AdbDebuggingManager$$ExternalSyntheticLambda0
                                    adbDebuggingManager$$ExternalSyntheticLambda0 =
                                            AdbDebuggingManager.SYSTEM_TICKER;
                            Slog.w("AdbDebuggingManager", "Read failed with count " + read);
                            break;
                        }
                        byte b = bArr[0];
                        if (b == 80 && bArr[1] == 75) {
                            String str = new String(Arrays.copyOfRange(bArr, 2, read));
                            AdbDebuggingManager$$ExternalSyntheticLambda0
                                    adbDebuggingManager$$ExternalSyntheticLambda02 =
                                            AdbDebuggingManager.SYSTEM_TICKER;
                            Slog.d("AdbDebuggingManager", "Received public key: " + str);
                            Message obtainMessage = this.mHandler.obtainMessage(5);
                            obtainMessage.obj = str;
                            this.mHandler.sendMessage(obtainMessage);
                        } else if (b == 68 && bArr[1] == 67) {
                            String str2 = new String(Arrays.copyOfRange(bArr, 2, read));
                            AdbDebuggingManager$$ExternalSyntheticLambda0
                                    adbDebuggingManager$$ExternalSyntheticLambda03 =
                                            AdbDebuggingManager.SYSTEM_TICKER;
                            Slog.d("AdbDebuggingManager", "Received disconnected message: " + str2);
                            Message obtainMessage2 = this.mHandler.obtainMessage(7);
                            obtainMessage2.obj = str2;
                            this.mHandler.sendMessage(obtainMessage2);
                        } else if (b == 67 && bArr[1] == 75) {
                            String str3 = new String(Arrays.copyOfRange(bArr, 2, read));
                            AdbDebuggingManager$$ExternalSyntheticLambda0
                                    adbDebuggingManager$$ExternalSyntheticLambda04 =
                                            AdbDebuggingManager.SYSTEM_TICKER;
                            Slog.d(
                                    "AdbDebuggingManager",
                                    "Received connected key message: " + str3);
                            Message obtainMessage3 = this.mHandler.obtainMessage(10);
                            obtainMessage3.obj = str3;
                            this.mHandler.sendMessage(obtainMessage3);
                        } else if (b == 87 && bArr[1] == 69) {
                            byte b2 = bArr[2];
                            String str4 = new String(Arrays.copyOfRange(bArr, 3, read));
                            if (b2 == 0) {
                                AdbDebuggingManager$$ExternalSyntheticLambda0
                                        adbDebuggingManager$$ExternalSyntheticLambda05 =
                                                AdbDebuggingManager.SYSTEM_TICKER;
                                Slog.d(
                                        "AdbDebuggingManager",
                                        "Received USB TLS connected key message: " + str4);
                                Message obtainMessage4 = this.mHandler.obtainMessage(10);
                                obtainMessage4.obj = str4;
                                this.mHandler.sendMessage(obtainMessage4);
                            } else if (b2 == 1) {
                                AdbDebuggingManager$$ExternalSyntheticLambda0
                                        adbDebuggingManager$$ExternalSyntheticLambda06 =
                                                AdbDebuggingManager.SYSTEM_TICKER;
                                Slog.d(
                                        "AdbDebuggingManager",
                                        "Received WIFI TLS connected key message: " + str4);
                                Message obtainMessage5 = this.mHandler.obtainMessage(22);
                                obtainMessage5.obj = str4;
                                this.mHandler.sendMessage(obtainMessage5);
                            } else {
                                AdbDebuggingManager$$ExternalSyntheticLambda0
                                        adbDebuggingManager$$ExternalSyntheticLambda07 =
                                                AdbDebuggingManager.SYSTEM_TICKER;
                                Slog.e(
                                        "AdbDebuggingManager",
                                        "Got unknown transport type from adbd ("
                                                + ((int) b2)
                                                + ")");
                            }
                        } else {
                            if (b != 87 || bArr[1] != 70) {
                                break;
                            }
                            byte b3 = bArr[2];
                            String str5 = new String(Arrays.copyOfRange(bArr, 3, read));
                            if (b3 == 0) {
                                AdbDebuggingManager$$ExternalSyntheticLambda0
                                        adbDebuggingManager$$ExternalSyntheticLambda08 =
                                                AdbDebuggingManager.SYSTEM_TICKER;
                                Slog.d(
                                        "AdbDebuggingManager",
                                        "Received USB TLS disconnect message: " + str5);
                                Message obtainMessage6 = this.mHandler.obtainMessage(7);
                                obtainMessage6.obj = str5;
                                this.mHandler.sendMessage(obtainMessage6);
                            } else if (b3 == 1) {
                                AdbDebuggingManager$$ExternalSyntheticLambda0
                                        adbDebuggingManager$$ExternalSyntheticLambda09 =
                                                AdbDebuggingManager.SYSTEM_TICKER;
                                Slog.d(
                                        "AdbDebuggingManager",
                                        "Received WIFI TLS disconnect key message: " + str5);
                                Message obtainMessage7 = this.mHandler.obtainMessage(23);
                                obtainMessage7.obj = str5;
                                this.mHandler.sendMessage(obtainMessage7);
                            } else {
                                AdbDebuggingManager$$ExternalSyntheticLambda0
                                        adbDebuggingManager$$ExternalSyntheticLambda010 =
                                                AdbDebuggingManager.SYSTEM_TICKER;
                                Slog.e(
                                        "AdbDebuggingManager",
                                        "Got unknown transport type from adbd ("
                                                + ((int) b3)
                                                + ")");
                            }
                        }
                    }
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda011 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.e(
                            "AdbDebuggingManager",
                            "Wrong message: " + new String(Arrays.copyOfRange(bArr, 0, 2)));
                    synchronized (this) {
                        closeSocketLocked();
                    }
                } catch (IOException e) {
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda012 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.e("AdbDebuggingManager", "Communication error: ", e);
                    throw e;
                }
            } catch (Throwable th) {
                synchronized (this) {
                    closeSocketLocked();
                    throw th;
                }
            }
        }

        public final void openSocketLocked() {
            try {
                LocalSocketAddress localSocketAddress =
                        new LocalSocketAddress("adbd", LocalSocketAddress.Namespace.RESERVED);
                this.mInputStream = null;
                AdbDebuggingManager$$ExternalSyntheticLambda0
                        adbDebuggingManager$$ExternalSyntheticLambda0 =
                                AdbDebuggingManager.SYSTEM_TICKER;
                Slog.d("AdbDebuggingManager", "Creating socket");
                LocalSocket localSocket = new LocalSocket(3);
                this.mSocket = localSocket;
                localSocket.connect(localSocketAddress);
                this.mOutputStream = this.mSocket.getOutputStream();
                this.mInputStream = this.mSocket.getInputStream();
                this.mHandler.sendEmptyMessage(26);
            } catch (IOException e) {
                AdbDebuggingManager$$ExternalSyntheticLambda0
                        adbDebuggingManager$$ExternalSyntheticLambda02 =
                                AdbDebuggingManager.SYSTEM_TICKER;
                Slog.e("AdbDebuggingManager", "Caught an exception opening the socket: " + e);
                closeSocketLocked();
                throw e;
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            AdbDebuggingManager$$ExternalSyntheticLambda0
                    adbDebuggingManager$$ExternalSyntheticLambda0 =
                            AdbDebuggingManager.SYSTEM_TICKER;
            Slog.d("AdbDebuggingManager", "Entering thread");
            while (true) {
                synchronized (this) {
                    if (this.mStopped) {
                        AdbDebuggingManager$$ExternalSyntheticLambda0
                                adbDebuggingManager$$ExternalSyntheticLambda02 =
                                        AdbDebuggingManager.SYSTEM_TICKER;
                        Slog.d("AdbDebuggingManager", "Exiting thread");
                        return;
                    } else {
                        try {
                            openSocketLocked();
                        } catch (Exception unused) {
                            SystemClock.sleep(1000L);
                        }
                    }
                }
                try {
                    listenToSocket();
                } catch (Exception unused2) {
                    SystemClock.sleep(1000L);
                }
            }
        }

        public final void sendResponse(String str) {
            OutputStream outputStream;
            synchronized (this) {
                if (!this.mStopped && (outputStream = this.mOutputStream) != null) {
                    try {
                        outputStream.write(str.getBytes());
                    } catch (IOException e) {
                        AdbDebuggingManager$$ExternalSyntheticLambda0
                                adbDebuggingManager$$ExternalSyntheticLambda0 =
                                        AdbDebuggingManager.SYSTEM_TICKER;
                        Slog.e("AdbDebuggingManager", "Failed to write response:", e);
                    }
                }
            }
        }

        public void setHandler(Handler handler) {
            this.mHandler = handler;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdbKeyStore {
        public AtomicFile mAtomicKeyFile;
        public final Set mSystemKeys;
        public final Map mKeyMap = new HashMap();
        public final List mTrustedNetworks = new ArrayList();

        public AdbKeyStore() {
            BufferedReader bufferedReader;
            if (AdbDebuggingManager.this.mTempKeysFile != null) {
                this.mAtomicKeyFile = new AtomicFile(AdbDebuggingManager.this.mTempKeysFile);
            }
            readTempKeysFile();
            HashSet hashSet = new HashSet();
            File file = new File("/adb_keys");
            if (file.exists()) {
                try {
                    bufferedReader = new BufferedReader(new FileReader(file));
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            String trim = readLine.trim();
                            if (trim.length() > 0) {
                                hashSet.add(trim);
                            }
                        } finally {
                            try {
                                bufferedReader.close();
                            } catch (Throwable th) {
                                th.addSuppressed(th);
                            }
                        }
                    }
                    bufferedReader.close();
                } catch (IOException e) {
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda0 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.e("AdbDebuggingManager", "Caught an exception reading /adb_keys: " + e);
                }
            }
            this.mSystemKeys = hashSet;
            AdbDebuggingManager adbDebuggingManager = AdbDebuggingManager.this;
            File file2 = adbDebuggingManager.mUserKeyFile;
            if (file2 == null || !file2.exists()) {
                return;
            }
            boolean z = false;
            try {
                bufferedReader =
                        new BufferedReader(new FileReader(adbDebuggingManager.mUserKeyFile));
                while (true) {
                    try {
                        String readLine2 = bufferedReader.readLine();
                        if (readLine2 == null) {
                            break;
                        }
                        if (!((HashMap) this.mKeyMap).containsKey(readLine2)) {
                            ((HashMap) this.mKeyMap)
                                    .put(
                                            readLine2,
                                            Long.valueOf(
                                                    adbDebuggingManager.mTicker
                                                            .currentTimeMillis()));
                            z = true;
                        }
                    } finally {
                    }
                }
                bufferedReader.close();
            } catch (IOException e2) {
                AdbDebuggingManager$$ExternalSyntheticLambda0
                        adbDebuggingManager$$ExternalSyntheticLambda02 =
                                AdbDebuggingManager.SYSTEM_TICKER;
                Slog.e(
                        "AdbDebuggingManager",
                        "Caught an exception reading "
                                + adbDebuggingManager.mUserKeyFile
                                + ": "
                                + e2);
            }
            if (z) {
                AdbDebuggingManager.m145$$Nest$msendPersistKeyStoreMessage(adbDebuggingManager);
            }
        }

        public final void deleteKeyStore() {
            ((HashMap) this.mKeyMap).clear();
            ((ArrayList) this.mTrustedNetworks).clear();
            File file = AdbDebuggingManager.this.mUserKeyFile;
            if (file != null) {
                file.delete();
            }
            AtomicFile atomicFile = this.mAtomicKeyFile;
            if (atomicFile == null) {
                return;
            }
            atomicFile.delete();
        }

        public final boolean filterOutOldKeys() {
            AdbDebuggingManager adbDebuggingManager = AdbDebuggingManager.this;
            long j =
                    Settings.Global.getLong(
                            adbDebuggingManager.mContext.getContentResolver(),
                            "adb_allowed_connection_time",
                            604800000L);
            boolean z = false;
            if (j == 0) {
                return false;
            }
            long currentTimeMillis = adbDebuggingManager.mTicker.currentTimeMillis();
            Iterator it = ((HashMap) this.mKeyMap).entrySet().iterator();
            while (it.hasNext()) {
                if (currentTimeMillis
                        > ((Long) ((Map.Entry) it.next()).getValue()).longValue() + j) {
                    it.remove();
                    z = true;
                }
            }
            if (z) {
                AdbDebuggingManager.m147$$Nest$mwriteKeys(
                        adbDebuggingManager, ((HashMap) this.mKeyMap).keySet());
            }
            return z;
        }

        public final Map getPairedDevices() {
            HashMap hashMap = new HashMap();
            for (Map.Entry entry : ((HashMap) this.mKeyMap).entrySet()) {
                String str = (String) entry.getKey();
                AdbDebuggingManager adbDebuggingManager = AdbDebuggingManager.this;
                String m144$$Nest$mgetFingerprints =
                        AdbDebuggingManager.m144$$Nest$mgetFingerprints(adbDebuggingManager, str);
                String[] split = ((String) entry.getKey()).split("\\s+");
                String str2 = split.length > 1 ? split[1] : "nouser@nohostname";
                PairDevice pairDevice = new PairDevice();
                pairDevice.name = str2;
                pairDevice.guid = m144$$Nest$mgetFingerprints;
                pairDevice.connected =
                        ((HashSet) adbDebuggingManager.mWifiConnectedKeys).contains(entry.getKey());
                hashMap.put((String) entry.getKey(), pairDevice);
            }
            return hashMap;
        }

        public final void readKeyStoreContents(TypedXmlPullParser typedXmlPullParser) {
            while (typedXmlPullParser.next() != 1) {
                String name = typedXmlPullParser.getName();
                if ("adbKey".equals(name)) {
                    try {
                        this.mKeyMap.put(
                                typedXmlPullParser.getAttributeValue((String) null, "key"),
                                Long.valueOf(
                                        typedXmlPullParser.getAttributeLong(
                                                (String) null, "lastConnection")));
                    } catch (XmlPullParserException e) {
                        AdbDebuggingManager$$ExternalSyntheticLambda0
                                adbDebuggingManager$$ExternalSyntheticLambda0 =
                                        AdbDebuggingManager.SYSTEM_TICKER;
                        Slog.e("AdbDebuggingManager", "Error reading adbKey attributes", e);
                    }
                } else if ("wifiAP".equals(name)) {
                    this.mTrustedNetworks.add(
                            typedXmlPullParser.getAttributeValue((String) null, "bssid"));
                } else {
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda02 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.w("AdbDebuggingManager", "Ignoring tag '" + name + "'. Not recognized.");
                }
                XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }

        public final void readTempKeysFile() {
            TypedXmlPullParser resolvePullParser;
            ((HashMap) this.mKeyMap).clear();
            ((ArrayList) this.mTrustedNetworks).clear();
            if (this.mAtomicKeyFile == null) {
                AdbDebuggingManager adbDebuggingManager = AdbDebuggingManager.this;
                if (adbDebuggingManager.mTempKeysFile != null) {
                    this.mAtomicKeyFile = new AtomicFile(adbDebuggingManager.mTempKeysFile);
                }
                if (this.mAtomicKeyFile == null) {
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda0 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.e(
                            "AdbDebuggingManager",
                            "Unable to obtain the key file, "
                                    + adbDebuggingManager.mTempKeysFile
                                    + ", for reading");
                    return;
                }
            }
            if (this.mAtomicKeyFile.exists()) {
                try {
                    FileInputStream openRead = this.mAtomicKeyFile.openRead();
                    try {
                        try {
                            resolvePullParser = Xml.resolvePullParser(openRead);
                            XmlUtils.beginDocument(resolvePullParser, "keyStore");
                            int attributeInt =
                                    resolvePullParser.getAttributeInt((String) null, "version");
                            if (attributeInt > 1) {
                                AdbDebuggingManager$$ExternalSyntheticLambda0
                                        adbDebuggingManager$$ExternalSyntheticLambda02 =
                                                AdbDebuggingManager.SYSTEM_TICKER;
                                Slog.e(
                                        "AdbDebuggingManager",
                                        "Keystore version="
                                                + attributeInt
                                                + " not supported (max_supported=1)");
                                if (openRead != null) {
                                    openRead.close();
                                    return;
                                }
                                return;
                            }
                        } catch (XmlPullParserException unused) {
                            resolvePullParser = Xml.resolvePullParser(openRead);
                        }
                        readKeyStoreContents(resolvePullParser);
                        if (openRead != null) {
                            openRead.close();
                        }
                    } catch (Throwable th) {
                        if (openRead != null) {
                            try {
                                openRead.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (IOException e) {
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda03 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.e(
                            "AdbDebuggingManager",
                            "Caught an IOException parsing the XML key file: ",
                            e);
                } catch (XmlPullParserException e2) {
                    AdbDebuggingManager$$ExternalSyntheticLambda0
                            adbDebuggingManager$$ExternalSyntheticLambda04 =
                                    AdbDebuggingManager.SYSTEM_TICKER;
                    Slog.e(
                            "AdbDebuggingManager",
                            "Caught XmlPullParserException parsing the XML key file: ",
                            e2);
                }
            }
        }

        public void setLastConnectionTime(String str, long j, boolean z) {
            if ((!((HashMap) this.mKeyMap).containsKey(str)
                            || ((Long) ((HashMap) this.mKeyMap).get(str)).longValue() < j
                            || z)
                    && !((HashSet) this.mSystemKeys).contains(str)) {
                ((HashMap) this.mKeyMap).put(str, Long.valueOf(j));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class PairingThread extends Thread implements NsdManager.RegistrationListener {
        static final String SERVICE_PROTOCOL = "adb-tls-pairing";
        public final String mGuid;
        public final NsdManager mNsdManager;
        public final String mPairingCode;
        public int mPort;
        public final String mPublicKey;
        public final String mServiceName;
        public final String mServiceType;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PairingThread(String str, String str2) {
            super("AdbDebuggingManager");
            AdbDebuggingManager$$ExternalSyntheticLambda0
                    adbDebuggingManager$$ExternalSyntheticLambda0 =
                            AdbDebuggingManager.SYSTEM_TICKER;
            this.mServiceType = "_adb-tls-pairing._tcp.";
            this.mPublicKey = "";
            this.mPairingCode = str;
            String str3 = SystemProperties.get("persist.adb.wifi.guid");
            this.mGuid = str3;
            this.mServiceName = str2;
            if (str2 == null || str2.isEmpty()) {
                this.mServiceName = str3;
            }
            this.mPort = -1;
            this.mNsdManager =
                    (NsdManager)
                            AdbDebuggingManager.this.mContext.getSystemService("servicediscovery");
        }

        private native void native_pairing_cancel();

        private native int native_pairing_start(String str, String str2);

        private native boolean native_pairing_wait();

        public final void cancelPairing() {
            native_pairing_cancel();
        }

        @Override // android.net.nsd.NsdManager.RegistrationListener
        public final void onRegistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
            AdbDebuggingManager$$ExternalSyntheticLambda0
                    adbDebuggingManager$$ExternalSyntheticLambda0 =
                            AdbDebuggingManager.SYSTEM_TICKER;
            Slog.e(
                    "AdbDebuggingManager",
                    "Failed to register pairing service(err=" + i + "): " + nsdServiceInfo);
            native_pairing_cancel();
        }

        @Override // android.net.nsd.NsdManager.RegistrationListener
        public final void onServiceRegistered(NsdServiceInfo nsdServiceInfo) {}

        @Override // android.net.nsd.NsdManager.RegistrationListener
        public final void onServiceUnregistered(NsdServiceInfo nsdServiceInfo) {}

        @Override // android.net.nsd.NsdManager.RegistrationListener
        public final void onUnregistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
            AdbDebuggingManager$$ExternalSyntheticLambda0
                    adbDebuggingManager$$ExternalSyntheticLambda0 =
                            AdbDebuggingManager.SYSTEM_TICKER;
            Slog.w(
                    "AdbDebuggingManager",
                    "Failed to unregister pairing service(err=" + i + "): " + nsdServiceInfo);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            NsdServiceInfo nsdServiceInfo = new NsdServiceInfo();
            nsdServiceInfo.setServiceName(this.mServiceName);
            nsdServiceInfo.setServiceType(this.mServiceType);
            nsdServiceInfo.setPort(this.mPort);
            this.mNsdManager.registerService(nsdServiceInfo, 1, this);
            Message obtainMessage = AdbDebuggingManager.this.mHandler.obtainMessage(21);
            obtainMessage.obj = Integer.valueOf(this.mPort);
            AdbDebuggingManager.this.mHandler.sendMessage(obtainMessage);
            boolean native_pairing_wait = native_pairing_wait();
            if (this.mPublicKey != null) {
                AdbDebuggingManager$$ExternalSyntheticLambda0
                        adbDebuggingManager$$ExternalSyntheticLambda0 =
                                AdbDebuggingManager.SYSTEM_TICKER;
                Slog.i("AdbDebuggingManager", "Pairing succeeded key=" + this.mPublicKey);
            } else {
                AdbDebuggingManager$$ExternalSyntheticLambda0
                        adbDebuggingManager$$ExternalSyntheticLambda02 =
                                AdbDebuggingManager.SYSTEM_TICKER;
                Slog.i("AdbDebuggingManager", "Pairing failed");
            }
            this.mNsdManager.unregisterService(this);
            Bundle bundle = new Bundle();
            bundle.putString("publicKey", native_pairing_wait ? this.mPublicKey : null);
            AdbDebuggingManager.this.mHandler.sendMessage(
                    Message.obtain(AdbDebuggingManager.this.mHandler, 20, bundle));
        }

        @Override // java.lang.Thread
        public final void start() {
            if (this.mGuid.isEmpty()) {
                AdbDebuggingManager$$ExternalSyntheticLambda0
                        adbDebuggingManager$$ExternalSyntheticLambda0 =
                                AdbDebuggingManager.SYSTEM_TICKER;
                Slog.e("AdbDebuggingManager", "adbwifi guid was not set");
                return;
            }
            int native_pairing_start = native_pairing_start(this.mGuid, this.mPairingCode);
            this.mPort = native_pairing_start;
            if (native_pairing_start > 0) {
                super.start();
            } else {
                AdbDebuggingManager$$ExternalSyntheticLambda0
                        adbDebuggingManager$$ExternalSyntheticLambda02 =
                                AdbDebuggingManager.SYSTEM_TICKER;
                Slog.e("AdbDebuggingManager", "Unable to start pairing server");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PortListenerImpl implements AdbConnectionPortListener {
        public PortListenerImpl() {}

        @Override // com.android.server.adb.AdbDebuggingManager.AdbConnectionPortListener
        public final void onPortReceived(int i) {
            AdbDebuggingManager$$ExternalSyntheticLambda0
                    adbDebuggingManager$$ExternalSyntheticLambda0 =
                            AdbDebuggingManager.SYSTEM_TICKER;
            Slog.d("AdbDebuggingManager", "Received tls port=" + i);
            AdbDebuggingManager adbDebuggingManager = AdbDebuggingManager.this;
            Message obtainMessage = adbDebuggingManager.mHandler.obtainMessage(i > 0 ? 24 : 25);
            obtainMessage.obj = Integer.valueOf(i);
            adbDebuggingManager.mHandler.sendMessage(obtainMessage);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    interface Ticker {
        long currentTimeMillis();
    }

    /* renamed from: -$$Nest$mgetFingerprints, reason: not valid java name */
    public static String m144$$Nest$mgetFingerprints(
            AdbDebuggingManager adbDebuggingManager, String str) {
        adbDebuggingManager.getClass();
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            return "";
        }
        try {
            try {
                byte[] digest =
                        MessageDigest.getInstance("MD5")
                                .digest(Base64.decode(str.split("\\s+")[0].getBytes(), 0));
                for (int i = 0; i < digest.length; i++) {
                    sb.append("0123456789ABCDEF".charAt((digest[i] >> 4) & 15));
                    sb.append("0123456789ABCDEF".charAt(digest[i] & 15));
                    if (i < digest.length - 1) {
                        sb.append(":");
                    }
                }
                return sb.toString();
            } catch (IllegalArgumentException e) {
                Slog.e("AdbDebuggingManager", "error doing base64 decoding", e);
                return "";
            }
        } catch (Exception e2) {
            Slog.e("AdbDebuggingManager", "Error getting digester", e2);
            return "";
        }
    }

    /* renamed from: -$$Nest$msendPersistKeyStoreMessage, reason: not valid java name */
    public static void m145$$Nest$msendPersistKeyStoreMessage(
            AdbDebuggingManager adbDebuggingManager) {
        adbDebuggingManager.mHandler.sendMessage(adbDebuggingManager.mHandler.obtainMessage(8));
    }

    /* renamed from: -$$Nest$msetAdbConnectionInfo, reason: not valid java name */
    public static void m146$$Nest$msetAdbConnectionInfo(
            AdbDebuggingManager adbDebuggingManager, AdbConnectionInfo adbConnectionInfo) {
        synchronized (adbDebuggingManager.mAdbConnectionInfo) {
            try {
                if (adbConnectionInfo == null) {
                    AdbConnectionInfo adbConnectionInfo2 = adbDebuggingManager.mAdbConnectionInfo;
                    adbConnectionInfo2.mBssid = "";
                    adbConnectionInfo2.mSsid = "";
                    adbConnectionInfo2.mPort = -1;
                } else {
                    adbDebuggingManager.mAdbConnectionInfo = adbConnectionInfo;
                }
            } finally {
            }
        }
    }

    /* renamed from: -$$Nest$mwriteKeys, reason: not valid java name */
    public static void m147$$Nest$mwriteKeys(
            AdbDebuggingManager adbDebuggingManager, Iterable iterable) {
        FileOutputStream fileOutputStream;
        if (adbDebuggingManager.mUserKeyFile == null) {
            return;
        }
        AtomicFile atomicFile = new AtomicFile(adbDebuggingManager.mUserKeyFile);
        try {
            fileOutputStream = atomicFile.startWrite();
            try {
                Iterator it = iterable.iterator();
                while (it.hasNext()) {
                    fileOutputStream.write(((String) it.next()).getBytes());
                    fileOutputStream.write(10);
                }
                atomicFile.finishWrite(fileOutputStream);
                FileUtils.setPermissions(
                        adbDebuggingManager.mUserKeyFile.toString(),
                        FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED,
                        -1,
                        -1);
            } catch (IOException e) {
                e = e;
                Slog.e("AdbDebuggingManager", "Error writing keys: " + e);
                atomicFile.failWrite(fileOutputStream);
            }
        } catch (IOException e2) {
            e = e2;
            fileOutputStream = null;
        }
    }

    public AdbDebuggingManager(
            Context context,
            String str,
            File file,
            File file2,
            AdbDebuggingThread adbDebuggingThread,
            Ticker ticker) {
        AdbConnectionInfo adbConnectionInfo = new AdbConnectionInfo();
        adbConnectionInfo.mBssid = "";
        adbConnectionInfo.mSsid = "";
        adbConnectionInfo.mPort = -1;
        this.mAdbConnectionInfo = adbConnectionInfo;
        this.mPortListener = new PortListenerImpl();
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mConfirmComponent = str;
        this.mUserKeyFile = file;
        this.mTempKeysFile = file2;
        this.mThread = adbDebuggingThread;
        this.mTicker = ticker;
        this.mHandler = new AdbDebuggingHandler(FgThread.get().getLooper(), this.mThread);
    }

    public static Intent createConfirmationIntent(ComponentName componentName, List list) {
        Intent intent = new Intent();
        intent.setClassName(componentName.getPackageName(), componentName.getClassName());
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            intent.putExtra((String) entry.getKey(), (String) entry.getValue());
        }
        return intent;
    }

    public static File getAdbFile(String str) {
        File file = new File(Environment.getDataDirectory(), "misc/adb");
        if (file.exists()) {
            return new File(file, str);
        }
        Slog.e("AdbDebuggingManager", "ADB data directory does not exist");
        return null;
    }

    public static void sendBroadcastWithDebugPermission(
            Context context, Intent intent, UserHandle userHandle) {
        context.sendBroadcastAsUser(intent, userHandle, "android.permission.MANAGE_DEBUGGING");
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x0054 -> B:9:0x0059). Please report as a decompilation issue!!! */
    public final void dump(DualDumpOutputStream dualDumpOutputStream) {
        long start = dualDumpOutputStream.start("debugging_manager", 1146756268033L);
        dualDumpOutputStream.write("connected_to_adb", 1133871366145L, this.mThread != null);
        DumpUtils.writeStringIfNotNull(
                dualDumpOutputStream, "last_key_received", 1138166333442L, this.mFingerprints);
        try {
            File file = new File("/data/misc/adb/adb_keys");
            if (file.exists()) {
                dualDumpOutputStream.write(
                        "user_keys", 1138166333443L, FileUtils.readTextFile(file, 0, null));
            } else {
                Slog.i("AdbDebuggingManager", "No user keys on this device");
            }
        } catch (IOException e) {
            Slog.i("AdbDebuggingManager", "Cannot read user keys", e);
        }
        try {
            dualDumpOutputStream.write(
                    "system_keys",
                    1138166333444L,
                    FileUtils.readTextFile(new File("/adb_keys"), 0, null));
        } catch (IOException e2) {
            Slog.i("AdbDebuggingManager", "Cannot read system keys", e2);
        }
        try {
            dualDumpOutputStream.write(
                    "keystore",
                    1138166333445L,
                    FileUtils.readTextFile(this.mTempKeysFile, 0, null));
        } catch (IOException e3) {
            Slog.i("AdbDebuggingManager", "Cannot read keystore: ", e3);
        }
        dualDumpOutputStream.end(start);
    }

    public final void setAdbEnabled(boolean z, byte b) {
        if (b == 0) {
            this.mHandler.sendEmptyMessage(z ? 1 : 2);
        } else {
            if (b != 1) {
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                b, "setAdbEnabled called with unimplemented transport type="));
            }
            this.mHandler.sendEmptyMessage(z ? 11 : 12);
        }
    }

    public final boolean startConfirmationActivity(
            ComponentName componentName, UserHandle userHandle, List list) {
        Slog.d("AdbDebuggingManager", "startConfirmationActivity");
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent createConfirmationIntent = createConfirmationIntent(componentName, list);
        createConfirmationIntent.addFlags(268435456);
        if (packageManager.resolveActivity(
                        createConfirmationIntent,
                        EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT)
                == null) {
            return false;
        }
        try {
            this.mContext.startActivityAsUser(createConfirmationIntent, userHandle);
            return true;
        } catch (ActivityNotFoundException e) {
            Slog.e(
                    "AdbDebuggingManager",
                    "unable to start adb whitelist activity: " + componentName,
                    e);
            return false;
        }
    }

    public final boolean startConfirmationService(
            ComponentName componentName, UserHandle userHandle, List list) {
        Slog.d("AdbDebuggingManager", "startConfirmationService");
        try {
            return this.mContext.startServiceAsUser(
                            createConfirmationIntent(componentName, list), userHandle)
                    != null;
        } catch (SecurityException e) {
            Slog.e(
                    "AdbDebuggingManager",
                    "unable to start adb whitelist service: " + componentName,
                    e);
            return false;
        }
    }
}
