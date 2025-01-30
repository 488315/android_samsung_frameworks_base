package com.android.server.usb;

import android.R;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.debug.AdbManagerInternal;
import android.debug.IAdbTransport;
import android.hardware.usb.ParcelableUsbPort;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbConfiguration;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import android.hardware.usb.gadget.V1_0.GadgetFunction;
import android.hidl.manager.V1_0.IServiceNotification;
import android.net.Uri;
import android.os.Environment;
import android.os.FactoryTest;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.util.Pair;
import android.util.sysfwutil.DexConnectionListener;
import android.util.sysfwutil.DexObserver;
import android.util.sysfwutil.Slog;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.os.SomeArgs;
import com.android.internal.usb.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.usb.hal.gadget.UsbGadgetHal;
import com.android.server.usb.hal.gadget.UsbGadgetHalInstance;
import com.android.server.utils.EventLogger;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public class UsbDeviceManager implements ActivityTaskManagerInternal.ScreenObserver {
    public static final String TAG = "UsbDeviceManager";
    public static DexObserver mDexObserver;
    public static boolean mEnableUsbHiddenMenu;
    public static boolean mSetNextUsbModeToDefault;
    public static boolean mSupportDualRole;
    public static UsbGadgetHal mUsbGadgetHal;
    public static Set sDenyInterfaces;
    public static EventLogger sEventLogger;
    public static final AtomicInteger sUsbOperationCount = new AtomicInteger();
    public static String usbConfiguredState;
    public static String usbDisableSettingVal;
    public String[] mAccessoryStrings;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public HashMap mControlFds;
    public UsbProfileGroupSettingsManager mCurrentSettings;
    public final DexConnectionListener mDexListener;
    public UsbHandler mHandler;
    public final boolean mHasUsbAccessory;
    public final BroadcastReceiver mHostConnectionReceiver;
    public final Object mLock = new Object();
    public final UEventObserver mUEventObserver;

    private native String[] nativeGetAccessoryStrings();

    private native int nativeGetAudioMode();

    private native boolean nativeIsStartRequested();

    private native ParcelFileDescriptor nativeOpenAccessory();

    private native FileDescriptor nativeOpenControl(String str);

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public void onAwakeStateChanged(boolean z) {
    }

    static {
        HashSet hashSet = new HashSet();
        sDenyInterfaces = hashSet;
        hashSet.add(1);
        sDenyInterfaces.add(2);
        sDenyInterfaces.add(3);
        sDenyInterfaces.add(7);
        sDenyInterfaces.add(8);
        sDenyInterfaces.add(9);
        sDenyInterfaces.add(10);
        sDenyInterfaces.add(11);
        sDenyInterfaces.add(13);
        sDenyInterfaces.add(14);
        sDenyInterfaces.add(224);
        mSupportDualRole = false;
        mSetNextUsbModeToDefault = false;
        usbDisableSettingVal = "OFF";
        usbConfiguredState = "none";
        mEnableUsbHiddenMenu = false;
    }

    public final class UsbUEventObserver extends UEventObserver {
        public UsbUEventObserver() {
        }

        public void onUEvent(UEventObserver.UEvent uEvent) {
            Slog.v(UsbDeviceManager.TAG, "USB UEVENT: " + uEvent.toString());
            if (UsbDeviceManager.sEventLogger != null) {
                UsbDeviceManager.sEventLogger.enqueue(new EventLogger.StringEvent("USB UEVENT: " + uEvent.toString()));
            } else {
                Slog.d(UsbDeviceManager.TAG, "sEventLogger == null");
            }
            String str = uEvent.get("USB_STATE");
            String str2 = uEvent.get("ACCESSORY");
            if (str != null) {
                UsbDeviceManager.this.mHandler.updateState(str);
                return;
            }
            if ("GETPROTOCOL".equals(str2)) {
                Slog.d(UsbDeviceManager.TAG, "got accessory get protocol");
                UsbDeviceManager.this.mHandler.setAccessoryUEventTime(SystemClock.elapsedRealtime());
                UsbDeviceManager.this.resetAccessoryHandshakeTimeoutHandler();
            } else if ("SENDSTRING".equals(str2)) {
                Slog.d(UsbDeviceManager.TAG, "got accessory send string");
                UsbDeviceManager.this.mHandler.sendEmptyMessage(21);
                UsbDeviceManager.this.resetAccessoryHandshakeTimeoutHandler();
            } else if ("START".equals(str2)) {
                Slog.d(UsbDeviceManager.TAG, "got accessory start");
                UsbDeviceManager.this.mHandler.removeMessages(20);
                UsbDeviceManager.this.mHandler.setStartAccessoryTrue();
                UsbDeviceManager.this.startAccessoryMode();
            }
        }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public void onKeyguardStateChanged(boolean z) {
        int currentUser = ActivityManager.getCurrentUser();
        boolean isDeviceSecure = ((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceSecure(currentUser);
        Slog.v(TAG, "onKeyguardStateChanged: isShowing:" + z + " secure:" + isDeviceSecure + " user:" + currentUser);
        this.mHandler.sendMessage(13, z && isDeviceSecure);
    }

    public void onUnlockUser(int i) {
        onKeyguardStateChanged(false);
    }

    public UsbDeviceManager(Context context, UsbAlsaManager usbAlsaManager, UsbSettingsManager usbSettingsManager, UsbPermissionManager usbPermissionManager, DexObserver dexObserver) {
        DexConnectionListener dexConnectionListener = new DexConnectionListener() { // from class: com.android.server.usb.UsbDeviceManager.1
            public void onConnect() {
                Slog.d(UsbDeviceManager.TAG, " DexConnectionListener:onConnect()");
                UsbDeviceManager.this.updateUsbNotificationRefresh();
            }
        };
        this.mDexListener = dexConnectionListener;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.usb.UsbDeviceManager.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Slog.d(UsbDeviceManager.TAG, "received ACTION_USB_OTG_CONNECTION");
                if (intent.getStringExtra("Connect").equals("On")) {
                    UsbDeviceManager.mSetNextUsbModeToDefault = true;
                    Slog.d(UsbDeviceManager.TAG, "Set mSetNextUsbModeToDefault=" + UsbDeviceManager.mSetNextUsbModeToDefault);
                }
            }
        };
        this.mHostConnectionReceiver = broadcastReceiver;
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mHasUsbAccessory = context.getPackageManager().hasSystemFeature("android.hardware.usb.accessory");
        initRndisAddress();
        int incrementAndGet = sUsbOperationCount.incrementAndGet();
        mUsbGadgetHal = UsbGadgetHalInstance.getInstance(this, null);
        String str = TAG;
        Slog.d(str, "getInstance done");
        this.mControlFds = new HashMap();
        FileDescriptor nativeOpenControl = nativeOpenControl("mtp");
        if (nativeOpenControl == null) {
            Slog.e(str, "Failed to open control for mtp");
        }
        this.mControlFds.put(4L, nativeOpenControl);
        FileDescriptor nativeOpenControl2 = nativeOpenControl("ptp");
        if (nativeOpenControl2 == null) {
            Slog.e(str, "Failed to open control for ptp");
        }
        this.mControlFds.put(16L, nativeOpenControl2);
        if (mUsbGadgetHal == null) {
            this.mHandler = new UsbHandlerLegacy(FgThread.get().getLooper(), context, this, usbAlsaManager, usbPermissionManager);
        } else {
            this.mHandler = new UsbHandlerHal(FgThread.get().getLooper(), context, this, usbAlsaManager, usbPermissionManager);
        }
        this.mHandler.handlerInitDone(incrementAndGet);
        if (nativeIsStartRequested()) {
            Slog.d(str, "accessory attached at boot");
            startAccessoryMode();
        }
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.server.usb.UsbDeviceManager.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Slog.d(UsbDeviceManager.TAG, "received ACTION_USB_PORT_CHANGED");
                ParcelableUsbPort parcelableUsbPort = (ParcelableUsbPort) intent.getParcelableExtra("port", ParcelableUsbPort.class);
                UsbDeviceManager.this.mHandler.updateHostState(parcelableUsbPort.getUsbPort((UsbManager) context2.getSystemService(UsbManager.class)), (UsbPortStatus) intent.getParcelableExtra("portStatus", UsbPortStatus.class));
            }
        };
        BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() { // from class: com.android.server.usb.UsbDeviceManager.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                UsbDeviceManager.this.mHandler.sendMessage(9, intent.getIntExtra("plugged", -1) == 2);
            }
        };
        BroadcastReceiver broadcastReceiver4 = new BroadcastReceiver() { // from class: com.android.server.usb.UsbDeviceManager.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Slog.d(UsbDeviceManager.TAG, "received ACTION_USB_DEVICE_ATTACHED / ACTION_USB_DEVICE_DETACHED");
                Iterator<Map.Entry<String, UsbDevice>> it = ((UsbManager) context2.getSystemService("usb")).getDeviceList().entrySet().iterator();
                if (intent.getAction().equals("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                    UsbDeviceManager.this.mHandler.sendMessage(10, (Object) it, true);
                } else {
                    UsbDeviceManager.this.mHandler.sendMessage(10, (Object) it, false);
                }
            }
        };
        BroadcastReceiver broadcastReceiver5 = new BroadcastReceiver() { // from class: com.android.server.usb.UsbDeviceManager.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                UsbDeviceManager.this.mHandler.sendEmptyMessage(11);
            }
        };
        context.registerReceiver(broadcastReceiver2, new IntentFilter("android.hardware.usb.action.USB_PORT_CHANGED"));
        context.registerReceiver(broadcastReceiver3, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        IntentFilter intentFilter = new IntentFilter("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        context.registerReceiver(broadcastReceiver4, intentFilter);
        context.registerReceiver(broadcastReceiver5, new IntentFilter("android.intent.action.LOCALE_CHANGED"));
        UsbUEventObserver usbUEventObserver = new UsbUEventObserver();
        this.mUEventObserver = usbUEventObserver;
        usbUEventObserver.startObserving("DEVPATH=/devices/virtual/android_usb/android0");
        usbUEventObserver.startObserving("DEVPATH=/devices/virtual/misc/usb_accessory");
        sEventLogger = new EventLogger(200, "UsbDeviceManager activity");
        if (dexObserver != null) {
            mDexObserver = dexObserver;
            dexObserver.addListener(dexConnectionListener);
        }
        context.registerReceiver(broadcastReceiver, new IntentFilter("com.samsung.UsbOtgCableConnection"));
        mSupportDualRole = new File("/sys/class/typec").exists();
    }

    public UsbProfileGroupSettingsManager getCurrentSettings() {
        UsbProfileGroupSettingsManager usbProfileGroupSettingsManager;
        synchronized (this.mLock) {
            usbProfileGroupSettingsManager = this.mCurrentSettings;
        }
        return usbProfileGroupSettingsManager;
    }

    public String[] getAccessoryStrings() {
        String[] strArr;
        synchronized (this.mLock) {
            strArr = this.mAccessoryStrings;
        }
        return strArr;
    }

    public void systemReady() {
        Slog.d(TAG, "systemReady");
        ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).registerScreenObserver(this);
        this.mHandler.sendEmptyMessage(3);
    }

    public void bootCompleted() {
        Slog.d(TAG, "boot completed");
        this.mHandler.sendEmptyMessage(4);
    }

    public void setCurrentUser(int i, UsbProfileGroupSettingsManager usbProfileGroupSettingsManager) {
        synchronized (this.mLock) {
            this.mCurrentSettings = usbProfileGroupSettingsManager;
            this.mHandler.obtainMessage(5, i, 0).sendToTarget();
        }
    }

    public void updateUserRestrictions() {
        this.mHandler.sendEmptyMessage(6);
    }

    public final void resetAccessoryHandshakeTimeoutHandler() {
        if ((getCurrentFunctions() & 2) == 0) {
            this.mHandler.removeMessages(20);
            UsbHandler usbHandler = this.mHandler;
            usbHandler.sendMessageDelayed(usbHandler.obtainMessage(20), 10000L);
        }
    }

    public final void startAccessoryMode() {
        String str = TAG;
        Slog.d(str, "startAccessoryMode()");
        if (this.mHasUsbAccessory) {
            int incrementAndGet = sUsbOperationCount.incrementAndGet();
            this.mAccessoryStrings = nativeGetAccessoryStrings();
            boolean z = nativeGetAudioMode() == 1;
            String[] strArr = this.mAccessoryStrings;
            boolean z2 = (strArr == null || strArr[0] == null || strArr[1] == null) ? false : true;
            if (strArr != null) {
                for (int i = 0; i < this.mAccessoryStrings.length; i++) {
                    Slog.d(TAG, "mAccessoryStrings[" + i + "]=" + this.mAccessoryStrings[i]);
                }
                Slog.d(TAG, "enableAudio=" + z + " enableAccessory=" + z2);
            } else {
                Slog.d(str, "mAccessoryStrings is null");
            }
            long j = z2 ? 2L : 0L;
            if (z) {
                j |= 64;
            }
            if (j != 0) {
                UsbHandler usbHandler = this.mHandler;
                usbHandler.sendMessageDelayed(usbHandler.obtainMessage(8), 10000L);
                UsbHandler usbHandler2 = this.mHandler;
                usbHandler2.sendMessageDelayed(usbHandler2.obtainMessage(20), 10000L);
                setCurrentFunctions(j, incrementAndGet);
            }
        }
    }

    public static void initRndisAddress() {
        int[] iArr = new int[6];
        iArr[0] = 2;
        String str = SystemProperties.get("ro.serialno", "1234567890ABCDEF");
        int length = str.length();
        for (int i = 0; i < length; i++) {
            int i2 = (i % 5) + 1;
            iArr[i2] = iArr[i2] ^ str.charAt(i);
        }
        try {
            FileUtils.stringToFile("/sys/class/android_usb/android0/f_rndis/ethaddr", String.format(Locale.US, "%02X:%02X:%02X:%02X:%02X:%02X", Integer.valueOf(iArr[0]), Integer.valueOf(iArr[1]), Integer.valueOf(iArr[2]), Integer.valueOf(iArr[3]), Integer.valueOf(iArr[4]), Integer.valueOf(iArr[5])));
        } catch (IOException unused) {
            Slog.i(TAG, "failed to write to /sys/class/android_usb/android0/f_rndis/ethaddr");
        }
    }

    public static void logAndPrint(int i, IndentingPrintWriter indentingPrintWriter, String str) {
        Slog.println(i, TAG, str);
        if (indentingPrintWriter != null) {
            indentingPrintWriter.println(str);
        }
    }

    public static void logAndPrintException(IndentingPrintWriter indentingPrintWriter, String str, Exception exc) {
        Slog.e(TAG, str, exc);
        if (indentingPrintWriter != null) {
            indentingPrintWriter.println(str + exc);
        }
    }

    public abstract class UsbHandler extends Handler {
        public long mAccessoryConnectionStartTime;
        public boolean mAudioAccessoryConnected;
        public boolean mAudioAccessorySupported;
        public boolean mBootCompleted;
        public Intent mBroadcastedIntent;
        public boolean mConfigured;
        public boolean mConnected;
        public boolean mConnectedToDataDisabledPort;
        public final ContentResolver mContentResolver;
        public final Context mContext;
        public UsbAccessory mCurrentAccessory;
        public long mCurrentFunctions;
        public boolean mCurrentFunctionsApplied;
        public int mCurrentGadgetHalVersion;
        public boolean mCurrentUsbFunctionsReceived;
        public int mCurrentUser;
        public boolean mHideUsbNotiForSecUsbSmartEP;
        public boolean mHideUsbNotification;
        public boolean mHostConnected;
        public boolean mInHostModeWithNoAccessoryConnected;
        public int mMidiCard;
        public int mMidiDevice;
        public boolean mMidiEnabled;
        public NotificationManager mNotificationManager;
        public boolean mPendingBootAccessoryHandshakeBroadcast;
        public boolean mPendingBootBroadcast;
        public final UsbPermissionManager mPermissionManager;
        public int mPowerBrickConnectionStatus;
        public boolean mResetUsbGadgetDisableDebounce;
        public boolean mScreenLocked;
        public long mScreenUnlockedFunctions;
        public long mSecCurrentFunctions;
        public int mSendStringCount;
        public SharedPreferences mSettings;
        public boolean mSinkPower;
        public boolean mSourcePower;
        public boolean mStartAccessory;
        public boolean mSupportsAllCombinations;
        public boolean mSystemReady;
        public boolean mUsbAccessoryConnected;
        public final UsbAlsaManager mUsbAlsaManager;
        public boolean mUsbCharging;
        public final UsbDeviceManager mUsbDeviceManager;
        public int mUsbNotificationId;
        public int mUsbSpeed;
        public boolean mUseUsbNotification;

        public abstract void getUsbSpeedCb(int i);

        public abstract void handlerInitDone(int i);

        public boolean isUsbDataTransferActive(long j) {
            return ((4 & j) == 0 && (j & 16) == 0) ? false : true;
        }

        public abstract void resetCb(int i);

        public abstract void setCurrentUsbFunctionsCb(long j, int i, int i2, long j2, boolean z);

        public abstract void setEnabledFunctions(long j, boolean z, int i);

        public UsbHandler(Looper looper, Context context, UsbDeviceManager usbDeviceManager, UsbAlsaManager usbAlsaManager, UsbPermissionManager usbPermissionManager) {
            super(looper);
            this.mAccessoryConnectionStartTime = 0L;
            boolean z = false;
            this.mSendStringCount = 0;
            this.mStartAccessory = false;
            this.mContext = context;
            this.mUsbDeviceManager = usbDeviceManager;
            this.mUsbAlsaManager = usbAlsaManager;
            this.mPermissionManager = usbPermissionManager;
            this.mContentResolver = context.getContentResolver();
            this.mCurrentUser = ActivityManager.getCurrentUser();
            this.mScreenLocked = true;
            SharedPreferences pinnedSharedPrefs = getPinnedSharedPrefs(context);
            this.mSettings = pinnedSharedPrefs;
            if (pinnedSharedPrefs == null) {
                Slog.e(UsbDeviceManager.TAG, "Couldn't load shared preferences");
            } else {
                this.mScreenUnlockedFunctions = UsbManager.usbFunctionsFromString(pinnedSharedPrefs.getString(String.format(Locale.ENGLISH, "usb-screen-unlocked-config-%d", Integer.valueOf(this.mCurrentUser)), ""));
            }
            StorageManager from = StorageManager.from(context);
            StorageVolume primaryVolume = from != null ? from.getPrimaryVolume() : null;
            if (!(primaryVolume != null && primaryVolume.allowMassStorage()) && context.getResources().getBoolean(R.bool.config_subscription_database_async_update)) {
                z = true;
            }
            this.mUseUsbNotification = z;
        }

        public void sendMessage(int i, boolean z) {
            removeMessages(i);
            Message obtain = Message.obtain(this, i);
            obtain.arg1 = z ? 1 : 0;
            sendMessage(obtain);
        }

        public void sendMessage(int i, Object obj) {
            removeMessages(i);
            Message obtain = Message.obtain(this, i);
            obtain.obj = obj;
            sendMessage(obtain);
        }

        public void sendMessage(int i, Object obj, int i2) {
            removeMessages(i);
            Message obtain = Message.obtain(this, i);
            obtain.obj = obj;
            obtain.arg1 = i2;
            sendMessage(obtain);
        }

        public void sendMessage(int i, boolean z, int i2) {
            removeMessages(i);
            Message obtain = Message.obtain(this, i);
            obtain.arg1 = z ? 1 : 0;
            obtain.arg2 = i2;
            sendMessage(obtain);
        }

        public void sendMessage(int i, Object obj, boolean z) {
            removeMessages(i);
            Message obtain = Message.obtain(this, i);
            obtain.obj = obj;
            obtain.arg1 = z ? 1 : 0;
            sendMessage(obtain);
        }

        public void sendMessageDelayed(int i, boolean z, long j) {
            removeMessages(i);
            Message obtain = Message.obtain(this, i);
            obtain.arg1 = z ? 1 : 0;
            sendMessageDelayed(obtain, j);
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x006e  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0076  */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0034  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void updateState(String str) {
            int i;
            int i2;
            long j;
            if ("DISCONNECTED".equals(str)) {
                setSystemProperty("sys.usb.configured", "none");
                i = 0;
            } else if (!"CONNECTED".equals(str)) {
                if ("CONFIGURED".equals(str)) {
                    setSystemProperty("sys.usb.configured", "configured");
                    i = 1;
                } else {
                    Slog.e(UsbDeviceManager.TAG, "unknown state " + str);
                    return;
                }
            } else {
                i = 1;
                i2 = 0;
                if (i == 1) {
                    removeMessages(17);
                }
                Message obtain = Message.obtain(this, 0);
                obtain.arg1 = i;
                obtain.arg2 = i2;
                Slog.i(UsbDeviceManager.TAG, "mResetUsbGadgetDisableDebounce:" + this.mResetUsbGadgetDisableDebounce + " connected:" + i + "configured:" + i2);
                if (!this.mResetUsbGadgetDisableDebounce) {
                    sendMessage(obtain);
                    if (i == 1) {
                        this.mResetUsbGadgetDisableDebounce = false;
                        return;
                    }
                    return;
                }
                if (i2 == 0) {
                    removeMessages(0);
                    Slog.i(UsbDeviceManager.TAG, "removeMessages MSG_UPDATE_STATE");
                }
                if (i == 1) {
                    removeMessages(17);
                }
                if (i == 0) {
                    j = this.mScreenLocked ? 1000 : 3000;
                } else {
                    j = 0;
                }
                sendMessageDelayed(obtain, j);
                return;
            }
            i2 = i;
            if (i == 1) {
            }
            Message obtain2 = Message.obtain(this, 0);
            obtain2.arg1 = i;
            obtain2.arg2 = i2;
            Slog.i(UsbDeviceManager.TAG, "mResetUsbGadgetDisableDebounce:" + this.mResetUsbGadgetDisableDebounce + " connected:" + i + "configured:" + i2);
            if (!this.mResetUsbGadgetDisableDebounce) {
            }
        }

        public void updateHostState(UsbPort usbPort, UsbPortStatus usbPortStatus) {
            Slog.i(UsbDeviceManager.TAG, "updateHostState " + usbPort + " status=" + usbPortStatus);
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = usbPort;
            obtain.arg2 = usbPortStatus;
            removeMessages(7);
            sendMessageDelayed(obtainMessage(7, obtain), 1000L);
        }

        public final void setAdbEnabled(boolean z, int i) {
            String removeFunction;
            Slog.d(UsbDeviceManager.TAG, "setAdbEnabled: enable=" + z);
            String systemProperty = getSystemProperty("persist.sys.usb.config", "none");
            if (z) {
                if (this.mScreenUnlockedFunctions == 262144) {
                    sendMessage(12, (Object) 262144L);
                    return;
                }
            } else if (systemProperty.equals("adb")) {
                sendMessage(12, (Object) 262144L);
                return;
            }
            if (z) {
                removeFunction = UsbHandlerLegacy.addFunction(systemProperty, "adb");
            } else {
                removeFunction = UsbHandlerLegacy.removeFunction(systemProperty, "adb");
            }
            setSystemProperty("persist.sys.usb.config", removeFunction);
            Slog.d(UsbDeviceManager.TAG, "Set persist.sys.usb.config to " + removeFunction);
            setEnabledFunctions(this.mCurrentFunctions, true, i);
        }

        public boolean isUsbTransferAllowed() {
            return !((UserManager) this.mContext.getSystemService("user")).hasUserRestriction("no_usb_file_transfer");
        }

        public final void updateCurrentAccessory() {
            int incrementAndGet = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
            boolean hasMessages = hasMessages(8);
            if (!this.mConfigured || !hasMessages) {
                if (!hasMessages) {
                    notifyAccessoryModeExit(incrementAndGet);
                    return;
                } else {
                    Slog.v(UsbDeviceManager.TAG, "Debouncing accessory mode exit");
                    return;
                }
            }
            String[] accessoryStrings = this.mUsbDeviceManager.getAccessoryStrings();
            if (accessoryStrings != null) {
                UsbSerialReader usbSerialReader = new UsbSerialReader(this.mContext, this.mPermissionManager, accessoryStrings[5]);
                UsbAccessory usbAccessory = new UsbAccessory(accessoryStrings[0], accessoryStrings[1], accessoryStrings[2], accessoryStrings[3], accessoryStrings[4], usbSerialReader);
                this.mCurrentAccessory = usbAccessory;
                usbSerialReader.setDevice(usbAccessory);
                Slog.d(UsbDeviceManager.TAG, "entering USB accessory mode: " + this.mCurrentAccessory);
                if (this.mBootCompleted) {
                    this.mUsbDeviceManager.getCurrentSettings().accessoryAttached(this.mCurrentAccessory);
                    removeMessages(20);
                    broadcastUsbAccessoryHandshake();
                    return;
                }
                return;
            }
            Slog.e(UsbDeviceManager.TAG, "nativeGetAccessoryStrings failed");
        }

        public void notifyAccessoryModeExit(int i) {
            Slog.d(UsbDeviceManager.TAG, "exited USB accessory mode");
            setEnabledFunctions(UsbDeviceManager.getDefaultUsbValue(), false, i);
            UsbAccessory usbAccessory = this.mCurrentAccessory;
            if (usbAccessory != null) {
                if (this.mBootCompleted) {
                    this.mPermissionManager.usbAccessoryRemoved(usbAccessory);
                }
                this.mCurrentAccessory = null;
            }
        }

        public SharedPreferences getPinnedSharedPrefs(Context context) {
            return context.createDeviceProtectedStorageContext().getSharedPreferences(new File(Environment.getDataSystemDeDirectory(0), "UsbDeviceManagerPrefs.xml"), 0);
        }

        public final boolean isUsbStateChanged(Intent intent) {
            Set<String> keySet = intent.getExtras().keySet();
            Intent intent2 = this.mBroadcastedIntent;
            if (intent2 == null) {
                Iterator<String> it = keySet.iterator();
                while (it.hasNext()) {
                    if (intent.getBooleanExtra(it.next(), false)) {
                        return true;
                    }
                }
            } else {
                if (!keySet.equals(intent2.getExtras().keySet())) {
                    return true;
                }
                for (String str : keySet) {
                    if (intent.getBooleanExtra(str, false) != this.mBroadcastedIntent.getBooleanExtra(str, false)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public final void broadcastUsbAccessoryHandshake() {
            Intent putExtra = new Intent("android.hardware.usb.action.USB_ACCESSORY_HANDSHAKE").addFlags(285212672).putExtra("android.hardware.usb.extra.ACCESSORY_UEVENT_TIME", this.mAccessoryConnectionStartTime).putExtra("android.hardware.usb.extra.ACCESSORY_STRING_COUNT", this.mSendStringCount).putExtra("android.hardware.usb.extra.ACCESSORY_START", this.mStartAccessory).putExtra("android.hardware.usb.extra.ACCESSORY_HANDSHAKE_END", SystemClock.elapsedRealtime());
            Slog.d(UsbDeviceManager.TAG, "broadcasting " + putExtra + " extras: " + putExtra.getExtras());
            sendStickyBroadcast(putExtra);
            resetUsbAccessoryHandshakeDebuggingInfo();
        }

        public void updateUsbStateBroadcastIfNeeded(long j, boolean z) {
            Slog.d(UsbDeviceManager.TAG, "updateUsbStateBroadcastIfNeeded: functions=" + UsbManager.usbFunctionsToString(j));
            Intent intent = new Intent("android.hardware.usb.action.USB_STATE");
            intent.addFlags(822083584);
            intent.putExtra("connected", this.mConnected);
            intent.putExtra("host_connected", this.mHostConnected);
            intent.putExtra("configured", this.mConfigured);
            intent.putExtra("unlocked", isUsbTransferAllowed() && isUsbDataTransferActive(this.mSecCurrentFunctions));
            intent.putExtra("config_changed", z);
            while (j != 0) {
                intent.putExtra(UsbManager.usbFunctionsToString(Long.highestOneBit(j)), true);
                j -= Long.highestOneBit(j);
            }
            if (!isUsbStateChanged(intent)) {
                Slog.d(UsbDeviceManager.TAG, "skip broadcasting " + intent + " extras: " + intent.getExtras());
                return;
            }
            Slog.d(UsbDeviceManager.TAG, "broadcasting " + intent + " extras: " + intent.getExtras());
            sendStickyBroadcast(intent);
            this.mBroadcastedIntent = intent;
        }

        public void sendStickyBroadcast(Intent intent) {
            this.mContext.sendStickyBroadcastAsUser(intent, UserHandle.ALL);
            UsbDeviceManager.sEventLogger.enqueue(new EventLogger.StringEvent("USB intent: " + intent));
        }

        public final void updateUsbFunctions() {
            updateMidiFunction();
        }

        public final void updateMidiFunction() {
            Scanner scanner;
            boolean z = (this.mCurrentFunctions & 8) != 0;
            if (z != this.mMidiEnabled) {
                if (z) {
                    Scanner scanner2 = null;
                    Scanner scanner3 = null;
                    try {
                        try {
                            scanner = new Scanner(new File("/sys/class/android_usb/android0/f_midi/alsa"));
                        } catch (Throwable th) {
                            th = th;
                        }
                    } catch (FileNotFoundException e) {
                        e = e;
                    }
                    try {
                        this.mMidiCard = scanner.nextInt();
                        int nextInt = scanner.nextInt();
                        this.mMidiDevice = nextInt;
                        scanner.close();
                        scanner2 = nextInt;
                    } catch (FileNotFoundException e2) {
                        e = e2;
                        scanner3 = scanner;
                        Slog.e(UsbDeviceManager.TAG, "could not open MIDI file", e);
                        if (scanner3 != null) {
                            scanner3.close();
                        }
                        z = false;
                        scanner2 = scanner3;
                        this.mMidiEnabled = z;
                        this.mUsbAlsaManager.setPeripheralMidiState(!this.mMidiEnabled && this.mConfigured, this.mMidiCard, this.mMidiDevice);
                    } catch (Throwable th2) {
                        th = th2;
                        scanner2 = scanner;
                        if (scanner2 != null) {
                            scanner2.close();
                        }
                        throw th;
                    }
                }
                this.mMidiEnabled = z;
            }
            this.mUsbAlsaManager.setPeripheralMidiState(!this.mMidiEnabled && this.mConfigured, this.mMidiCard, this.mMidiDevice);
        }

        public final void setScreenUnlockedFunctions(int i) {
            Slog.d(UsbDeviceManager.TAG, "setScreenUnlockedFunctions: mScreenUnlockedFunctions=" + UsbManager.usbFunctionsToString(this.mScreenUnlockedFunctions));
            setEnabledFunctions(this.mScreenUnlockedFunctions, false, i);
        }

        public class AdbTransport extends IAdbTransport.Stub {
            public final UsbHandler mHandler;

            public AdbTransport(UsbHandler usbHandler) {
                this.mHandler = usbHandler;
            }

            public void onAdbEnabled(boolean z, byte b) {
                if (b == 0) {
                    this.mHandler.sendMessage(1, z, UsbDeviceManager.sUsbOperationCount.incrementAndGet());
                }
            }
        }

        public long getAppliedFunctions(long j) {
            if (j == 0) {
                return getChargingFunctions();
            }
            return isAdbEnabled() ? j | 1 : j;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z;
            int i = message.what;
            if (i == 20) {
                Slog.v(UsbDeviceManager.TAG, "Accessory handshake timeout");
                if (this.mBootCompleted) {
                    broadcastUsbAccessoryHandshake();
                    return;
                } else {
                    Slog.v(UsbDeviceManager.TAG, "Pending broadcasting intent as not boot completed yet.");
                    this.mPendingBootAccessoryHandshakeBroadcast = true;
                    return;
                }
            }
            if (i == 21) {
                this.mSendStringCount++;
                return;
            }
            if (i != 101) {
                switch (i) {
                    case 0:
                        Slog.d(UsbDeviceManager.TAG, "handleMessage -> MSG_UPDATE_STATE: connected=" + message.arg1 + " configured=" + message.arg2 + " mCurrentFunctions=" + UsbManager.usbFunctionsToString(this.mCurrentFunctions));
                        int incrementAndGet = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                        this.mConnected = message.arg1 == 1;
                        this.mConfigured = message.arg2 == 1;
                        Slog.i(UsbDeviceManager.TAG, "handleMessage MSG_UPDATE_STATE mConnected:" + this.mConnected + " mConfigured:" + this.mConfigured);
                        updateUsbNotification(false);
                        if (this.mBootCompleted) {
                            updateUsbStateBroadcastIfNeeded(getAppliedFunctions(this.mSecCurrentFunctions), false);
                        }
                        if ((this.mCurrentFunctions & 2) != 0) {
                            updateCurrentAccessory();
                        }
                        if (this.mBootCompleted) {
                            if (!this.mConnected && !hasMessages(8) && !hasMessages(17)) {
                                if (!this.mScreenLocked && this.mScreenUnlockedFunctions != 0) {
                                    setScreenUnlockedFunctions(incrementAndGet);
                                } else {
                                    setEnabledFunctions(UsbDeviceManager.getDefaultUsbValue(), false, incrementAndGet);
                                }
                            } else if (this.mConnected && UsbDeviceManager.mSupportDualRole) {
                                if (UsbDeviceManager.mSetNextUsbModeToDefault && this.mScreenUnlockedFunctions == 0) {
                                    Slog.d(UsbDeviceManager.TAG, "mSetNextUsbModeToDefault: Set default functions!");
                                    setEnabledFunctions(UsbDeviceManager.getDefaultUsbValue(), false, incrementAndGet);
                                    UsbDeviceManager.mSetNextUsbModeToDefault = false;
                                }
                            } else if (!this.mConnected && UsbDeviceManager.mEnableUsbHiddenMenu) {
                                UsbDeviceManager.mEnableUsbHiddenMenu = false;
                                Slog.d(UsbDeviceManager.TAG, "Disable USB Hidden Menu");
                            }
                            updateUsbFunctions();
                        } else {
                            this.mPendingBootBroadcast = true;
                        }
                        updateUsbSpeed();
                        break;
                    case 1:
                        setAdbEnabled(message.arg1 == 1, message.arg2);
                        break;
                    case 2:
                        Slog.d(UsbDeviceManager.TAG, "handleMessage -> MSG_SET_CURRENT_FUNCTION");
                        setEnabledFunctions(((Long) message.obj).longValue(), false, message.arg1);
                        break;
                    case 3:
                        Slog.d(UsbDeviceManager.TAG, "handleMessage -> MSG_SYSTEM_READY");
                        int incrementAndGet2 = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                        this.mNotificationManager = (NotificationManager) this.mContext.getSystemService("notification");
                        ((AdbManagerInternal) LocalServices.getService(AdbManagerInternal.class)).registerTransport(new AdbTransport(this));
                        if (isTv()) {
                            this.mNotificationManager.createNotificationChannel(new NotificationChannel("usbdevicemanager.adb.tv", this.mContext.getString(R.string.beforeOneMonthDurationPast), 4));
                        }
                        this.mSystemReady = true;
                        finishBoot(incrementAndGet2);
                        break;
                    case 4:
                        Slog.d(UsbDeviceManager.TAG, "handleMessage -> MSG_BOOT_COMPLETED");
                        int incrementAndGet3 = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                        this.mBootCompleted = true;
                        finishBoot(incrementAndGet3);
                        break;
                    case 5:
                        Slog.d(UsbDeviceManager.TAG, "handleMessage -> MSG_USER_SWITCHED");
                        int incrementAndGet4 = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                        if (this.mCurrentUser != message.arg1) {
                            Slog.v(UsbDeviceManager.TAG, "Current user switched to " + message.arg1);
                            int i2 = message.arg1;
                            this.mCurrentUser = i2;
                            this.mScreenLocked = true;
                            this.mScreenUnlockedFunctions = 0L;
                            SharedPreferences sharedPreferences = this.mSettings;
                            if (sharedPreferences != null) {
                                this.mScreenUnlockedFunctions = UsbManager.usbFunctionsFromString(sharedPreferences.getString(String.format(Locale.ENGLISH, "usb-screen-unlocked-config-%d", Integer.valueOf(i2)), ""));
                            }
                            setEnabledFunctions(this.mCurrentFunctions, false, incrementAndGet4);
                            break;
                        }
                        break;
                    case 6:
                        Slog.d(UsbDeviceManager.TAG, "handleMessage -> MSG_UPDATE_USER_RESTRICTIONS mCurrentFunctions=" + UsbManager.usbFunctionsToString(this.mCurrentFunctions) + " isUsbTransferAllowed()=" + isUsbTransferAllowed());
                        int incrementAndGet5 = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                        if ((isUsbDataTransferActive(this.mCurrentFunctions) || isUsbDataTransferActive(this.mSecCurrentFunctions)) && !isUsbTransferAllowed()) {
                            setEnabledFunctions(0L, true, incrementAndGet5);
                            break;
                        }
                        break;
                    case 7:
                        SomeArgs someArgs = (SomeArgs) message.obj;
                        boolean z2 = this.mHostConnected;
                        UsbPort usbPort = (UsbPort) someArgs.arg1;
                        UsbPortStatus usbPortStatus = (UsbPortStatus) someArgs.arg2;
                        if (usbPortStatus != null) {
                            this.mHostConnected = usbPortStatus.getCurrentDataRole() == 1;
                            this.mSourcePower = usbPortStatus.getCurrentPowerRole() == 1;
                            this.mSinkPower = usbPortStatus.getCurrentPowerRole() == 2;
                            this.mAudioAccessoryConnected = usbPortStatus.getCurrentMode() == 4;
                            this.mSupportsAllCombinations = usbPortStatus.isRoleCombinationSupported(1, 1) && usbPortStatus.isRoleCombinationSupported(2, 1) && usbPortStatus.isRoleCombinationSupported(1, 2) && usbPortStatus.isRoleCombinationSupported(2, 2);
                            this.mConnectedToDataDisabledPort = usbPortStatus.isConnected() && (usbPortStatus.getUsbDataStatus() != 1);
                            this.mPowerBrickConnectionStatus = usbPortStatus.getPowerBrickConnectionStatus();
                        } else {
                            this.mHostConnected = false;
                            this.mSourcePower = false;
                            this.mSinkPower = false;
                            this.mAudioAccessoryConnected = false;
                            this.mSupportsAllCombinations = false;
                            this.mConnectedToDataDisabledPort = false;
                            this.mPowerBrickConnectionStatus = 0;
                        }
                        if (this.mHostConnected) {
                            if (!this.mUsbAccessoryConnected) {
                                this.mInHostModeWithNoAccessoryConnected = true;
                            } else {
                                this.mInHostModeWithNoAccessoryConnected = false;
                            }
                        } else {
                            this.mInHostModeWithNoAccessoryConnected = false;
                        }
                        this.mAudioAccessorySupported = usbPort.isModeSupported(4);
                        Slog.d(UsbDeviceManager.TAG, "handleMessage -> MSG_UPDATE_PORT_STATE: mSupportsAllCombinations=" + this.mSupportsAllCombinations + " mAudioAccessorySupported=" + this.mAudioAccessorySupported + " mAudioAccessoryConnected=" + this.mAudioAccessoryConnected);
                        someArgs.recycle();
                        String usbSecurityCheckNodeValue = UsbDeviceManager.getUsbSecurityCheckNodeValue();
                        this.mHideUsbNotiForSecUsbSmartEP = false;
                        if ("04E8:B007".equalsIgnoreCase(usbSecurityCheckNodeValue)) {
                            Slog.d(UsbDeviceManager.TAG, "Clear notification: SAMSUNG USB Smart Earphone connected");
                            this.mHideUsbNotiForSecUsbSmartEP = true;
                        }
                        updateUsbNotification(false);
                        if (this.mBootCompleted) {
                            if (this.mHostConnected || z2) {
                                updateUsbStateBroadcastIfNeeded(getAppliedFunctions(this.mSecCurrentFunctions), false);
                                break;
                            }
                        } else {
                            this.mPendingBootBroadcast = true;
                            break;
                        }
                        break;
                    case 8:
                        Slog.d(UsbDeviceManager.TAG, "handleMessage -> MSG_ACCESSORY_MODE_ENTER_TIMEOUT");
                        int incrementAndGet6 = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                        Slog.v(UsbDeviceManager.TAG, "Accessory mode enter timeout: " + this.mConnected + " ,operationId: " + incrementAndGet6);
                        if (!this.mConnected || (this.mCurrentFunctions & 2) == 0) {
                            notifyAccessoryModeExit(incrementAndGet6);
                            break;
                        }
                        break;
                    case 9:
                        z = message.arg1 == 1;
                        if (this.mUsbCharging != z) {
                            Slog.d(UsbDeviceManager.TAG, "handleMessage -> MSG_UPDATE_CHARGING_STATE");
                            this.mUsbCharging = z;
                            updateUsbNotification(false);
                            break;
                        }
                        break;
                    case 10:
                        String str = UsbDeviceManager.TAG;
                        StringBuilder sb = new StringBuilder();
                        sb.append("handleMessage -> MSG_UPDATE_HOST_STATE: connected=");
                        sb.append(message.arg1 == 1);
                        Slog.d(str, sb.toString());
                        Iterator it = (Iterator) message.obj;
                        this.mUsbAccessoryConnected = message.arg1 == 1;
                        Slog.i(UsbDeviceManager.TAG, "HOST_STATE connected:" + this.mUsbAccessoryConnected);
                        if (!it.hasNext()) {
                            this.mInHostModeWithNoAccessoryConnected = true;
                        } else {
                            this.mInHostModeWithNoAccessoryConnected = false;
                        }
                        this.mHideUsbNotification = false;
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            Slog.i(UsbDeviceManager.TAG, entry.getKey() + " = " + entry.getValue());
                            UsbDevice usbDevice = (UsbDevice) entry.getValue();
                            int configurationCount = usbDevice.getConfigurationCount() - 1;
                            while (configurationCount >= 0) {
                                UsbConfiguration configuration = usbDevice.getConfiguration(configurationCount);
                                configurationCount--;
                                int interfaceCount = configuration.getInterfaceCount() - 1;
                                while (true) {
                                    if (interfaceCount >= 0) {
                                        UsbInterface usbInterface = configuration.getInterface(interfaceCount);
                                        interfaceCount--;
                                        if (UsbDeviceManager.sDenyInterfaces.contains(Integer.valueOf(usbInterface.getInterfaceClass())) && usbInterface.getInterfaceClass() == 1) {
                                            Slog.d(UsbDeviceManager.TAG, "Clear notification: USB Audio connected");
                                            this.mHideUsbNotification = true;
                                        }
                                    }
                                }
                            }
                        }
                        if (this.mUsbAccessoryConnected) {
                            updateUsbNotification(false);
                            break;
                        }
                        break;
                    case 11:
                        Slog.d(UsbDeviceManager.TAG, "handleMessage -> MSG_LOCALE_CHANGED");
                        updateUsbNotification(true);
                        break;
                    case 12:
                        int incrementAndGet7 = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                        this.mScreenUnlockedFunctions = ((Long) message.obj).longValue();
                        Slog.d(UsbDeviceManager.TAG, "handleMessage -> MSG_SET_SCREEN_UNLOCKED_FUNCTIONS: mScreenUnlockedFunctions=" + UsbManager.usbFunctionsToString(this.mScreenUnlockedFunctions));
                        if ((this.mScreenUnlockedFunctions & 262144) != 0 && isAdbEnabled()) {
                            this.mScreenUnlockedFunctions = 0L;
                            setSystemProperty("persist.sys.usb.config", "adb");
                            Slog.d(UsbDeviceManager.TAG, "Set persist.sys.usb.config to adb");
                        } else {
                            String usbFunctionsToString = UsbManager.usbFunctionsToString(isAdbEnabled() ? 5L : 4L);
                            setSystemProperty("persist.sys.usb.config", usbFunctionsToString);
                            Slog.d(UsbDeviceManager.TAG, "Set persist.sys.usb.config to " + usbFunctionsToString);
                        }
                        SharedPreferences sharedPreferences2 = this.mSettings;
                        if (sharedPreferences2 != null) {
                            SharedPreferences.Editor edit = sharedPreferences2.edit();
                            edit.putString(String.format(Locale.ENGLISH, "usb-screen-unlocked-config-%d", Integer.valueOf(this.mCurrentUser)), UsbManager.usbFunctionsToString(this.mScreenUnlockedFunctions));
                            edit.commit();
                        }
                        if (!this.mScreenLocked && this.mScreenUnlockedFunctions != 0) {
                            setScreenUnlockedFunctions(incrementAndGet7);
                            break;
                        } else {
                            setEnabledFunctions(0L, false, incrementAndGet7);
                            break;
                        }
                        break;
                    case 13:
                        int incrementAndGet8 = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                        if ((message.arg1 == 1) != this.mScreenLocked) {
                            String str2 = UsbDeviceManager.TAG;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("handleMessage -> MSG_UPDATE_SCREEN_LOCK: mScreenLocked=");
                            sb2.append(message.arg1 == 1);
                            Slog.d(str2, sb2.toString());
                            z = message.arg1 == 1;
                            this.mScreenLocked = z;
                            if (this.mBootCompleted) {
                                if (!z) {
                                    if (this.mScreenUnlockedFunctions != 0 && (this.mCurrentFunctions & 4) != 0) {
                                        setScreenUnlockedFunctions(incrementAndGet8);
                                        break;
                                    }
                                } else if (!this.mConnected) {
                                    setEnabledFunctions(UsbDeviceManager.getDefaultUsbValue(), false, incrementAndGet8);
                                    break;
                                }
                            }
                        }
                        break;
                }
                return;
            }
            Slog.d(UsbDeviceManager.TAG, "handleMessage -> MSG_USB_NOTI_UPDATE: isForce=" + message.arg1);
            updateUsbNotification(message.arg1 == 1);
        }

        public void finishBoot(int i) {
            Slog.d(UsbDeviceManager.TAG, "finishBoot");
            if (this.mBootCompleted && this.mCurrentUsbFunctionsReceived && this.mSystemReady) {
                if (this.mPendingBootBroadcast) {
                    updateUsbStateBroadcastIfNeeded(getAppliedFunctions(this.mSecCurrentFunctions), false);
                    this.mPendingBootBroadcast = false;
                }
                if (!this.mScreenLocked && this.mScreenUnlockedFunctions != 0) {
                    Slog.d(UsbDeviceManager.TAG, "finishBoot mScreenUnlockedFunctions=" + UsbManager.usbFunctionsToString(this.mScreenUnlockedFunctions));
                    setScreenUnlockedFunctions(i);
                } else {
                    setEnabledFunctions(UsbDeviceManager.getDefaultUsbValue(), false, i);
                }
                if (this.mCurrentAccessory != null) {
                    this.mUsbDeviceManager.getCurrentSettings().accessoryAttached(this.mCurrentAccessory);
                    broadcastUsbAccessoryHandshake();
                } else if (this.mPendingBootAccessoryHandshakeBroadcast) {
                    broadcastUsbAccessoryHandshake();
                }
                this.mPendingBootAccessoryHandshakeBroadcast = false;
                updateUsbNotification(false);
                updateUsbFunctions();
            }
        }

        public UsbAccessory getCurrentAccessory() {
            return this.mCurrentAccessory;
        }

        public void updateUsbGadgetHalVersion() {
            sendMessage(23, (Object) null);
        }

        public void updateUsbSpeed() {
            if (this.mCurrentGadgetHalVersion < 10) {
                this.mUsbSpeed = -1;
            } else if (this.mConnected && this.mConfigured) {
                sendMessage(22, (Object) null);
            } else {
                this.mUsbSpeed = -1;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:85:0x015a, code lost:
        
            if (r6 == 0) goto L81;
         */
        /* JADX WARN: Removed duplicated region for block: B:40:0x01ee  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void updateUsbNotification(boolean z) {
            int i;
            int i2;
            int i3;
            int i4;
            int i5;
            String str;
            PendingIntent pendingIntent;
            Slog.d(UsbDeviceManager.TAG, "updateUsbNotification(" + z + "): mConnected=" + this.mConnected + " mHostConnected=" + this.mHostConnected + " mSourcePower=" + this.mSourcePower + " mSinkPower=" + this.mSinkPower + " mUsbCharging=" + this.mUsbCharging);
            if (this.mNotificationManager == null || !this.mUseUsbNotification || "0".equals(getSystemProperty("persist.charging.notify", ""))) {
                return;
            }
            if ((this.mHideUsbNotification || this.mInHostModeWithNoAccessoryConnected) && !this.mSupportsAllCombinations) {
                int i6 = this.mUsbNotificationId;
                if (i6 != 0) {
                    this.mNotificationManager.cancelAsUser(null, i6, UserHandle.ALL);
                    this.mUsbNotificationId = 0;
                    Slog.d(UsbDeviceManager.TAG, "Clear notification");
                    return;
                }
                return;
            }
            if (this.mHideUsbNotiForSecUsbSmartEP) {
                int i7 = this.mUsbNotificationId;
                if (i7 != 0) {
                    this.mNotificationManager.cancelAsUser(null, i7, UserHandle.ALL);
                    this.mUsbNotificationId = 0;
                    Slog.d(UsbDeviceManager.TAG, "Clear notification by SEC USB SmartEP");
                    return;
                }
                return;
            }
            Resources resources = this.mContext.getResources();
            CharSequence text = resources.getText(17043149);
            long j = this.mSecCurrentFunctions;
            if (j != 1) {
                j = j & (-2) & (-4194305);
            }
            if (!this.mAudioAccessoryConnected || this.mAudioAccessorySupported) {
                i = 17043150;
                if (this.mConnected) {
                    if (j == 4) {
                        i3 = 17043145;
                        i4 = 27;
                    } else if (j == 16) {
                        i3 = 17043152;
                        i4 = 28;
                    } else if (j == 8) {
                        i3 = 17043136;
                        i4 = 29;
                    } else if (j == 32 || j == 1024) {
                        i3 = 17043167;
                        i4 = 47;
                    } else if (j == 128) {
                        i3 = 17043182;
                        i4 = 75;
                    } else if (j == 2) {
                        i3 = 17043102;
                        i4 = 30;
                    } else if (j == 524288) {
                        i3 = 17043111;
                        i4 = 100;
                    } else if (j == 1) {
                        i3 = 17043120;
                        i4 = 110;
                    } else {
                        i3 = 0;
                        i4 = 0;
                    }
                    if (this.mSourcePower) {
                        if (i3 != 0) {
                            text = resources.getText(17043151);
                        }
                        i2 = 31;
                        i = 17043166;
                    }
                    i = i3;
                    i2 = i4;
                } else {
                    if (!this.mSourcePower) {
                        if (!this.mHostConnected || !this.mSinkPower || (!this.mUsbCharging && !this.mUsbAccessoryConnected)) {
                            i2 = 0;
                            i = 0;
                        }
                        i2 = 32;
                    }
                    i2 = 31;
                    i = 17043166;
                }
            } else {
                i2 = 41;
                i = 17043179;
            }
            if (UsbDeviceManager.mDexObserver != null && (UsbDeviceManager.mDexObserver.isDexModeOn() || UsbDeviceManager.mDexObserver.isSemiDexModeOn())) {
                Slog.d(UsbDeviceManager.TAG, "Clear notification: Dex is on");
            } else if (UsbDeviceManager.usbDisableSettingVal.contains("ON_ALL")) {
                Slog.d(UsbDeviceManager.TAG, "Clear notification: ON_ALL");
            } else if (UsbDeviceManager.usbDisableSettingVal.contains("ON_HOST") && i2 == 31) {
                Slog.d(UsbDeviceManager.TAG, "Clear notification: ON_HOST");
            } else {
                if (UsbDeviceManager.usbDisableSettingVal.contains("ON_CLIENT") && i2 != 31) {
                    Slog.d(UsbDeviceManager.TAG, "Clear notification: ON_CLIENT");
                }
                i5 = this.mUsbNotificationId;
                if (i2 == i5 || z) {
                    if (i5 != 0) {
                        this.mNotificationManager.cancelAsUser(null, i5, UserHandle.ALL);
                        Slog.d(UsbDeviceManager.TAG, "cancelAsUser: id=" + this.mUsbNotificationId);
                        this.mUsbNotificationId = 0;
                    }
                    if ((!this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive") || this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.watch")) && i2 == 32) {
                        this.mUsbNotificationId = 0;
                    }
                    if (i2 != 0) {
                        CharSequence text2 = resources.getText(i);
                        if (i != 17043179) {
                            pendingIntent = PendingIntent.getActivityAsUser(this.mContext, 0, Intent.makeRestartActivityTask(new ComponentName("com.android.settings", "com.android.settings.Settings$UsbDetailsActivity")), 67108864, null, UserHandle.CURRENT);
                            str = SystemNotificationChannels.USB;
                        } else {
                            Intent intent = new Intent();
                            intent.setClassName("com.android.settings", "com.android.settings.HelpTrampoline");
                            intent.putExtra("android.intent.extra.TEXT", "help_url_audio_accessory_not_supported");
                            PendingIntent activity = this.mContext.getPackageManager().resolveActivity(intent, 0) != null ? PendingIntent.getActivity(this.mContext, 0, intent, 67108864) : null;
                            str = SystemNotificationChannels.ALERTS;
                            PendingIntent pendingIntent2 = activity;
                            text = resources.getText(17043178);
                            pendingIntent = pendingIntent2;
                        }
                        Notification.Builder visibility = new Notification.Builder(this.mContext, str).setSmallIcon(17304219).setWhen(0L).setOngoing(true).setTicker(text2).setDefaults(0).setContentTitle(text2).setContentText(text).setContentIntent(pendingIntent).setVisibility(1);
                        if (i == 17043179) {
                            visibility.setStyle(new Notification.BigTextStyle().bigText(text));
                        }
                        this.mNotificationManager.notifyAsUser(null, i2, visibility.build(), UserHandle.ALL);
                        Slog.d(UsbDeviceManager.TAG, "notifyAsUser: id=" + this.mUsbNotificationId + " title=" + ((Object) text2));
                        this.mUsbNotificationId = i2;
                        return;
                    }
                    return;
                }
                return;
            }
            i2 = 0;
            i5 = this.mUsbNotificationId;
            if (i2 == i5) {
            }
            if (i5 != 0) {
            }
            if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
            }
            this.mUsbNotificationId = 0;
        }

        public boolean isAdbEnabled() {
            return ((AdbManagerInternal) LocalServices.getService(AdbManagerInternal.class)).isAdbEnabled((byte) 0);
        }

        public final boolean isTv() {
            return this.mContext.getPackageManager().hasSystemFeature("android.software.leanback");
        }

        public long getChargingFunctions() {
            return isAdbEnabled() ? 1L : 4L;
        }

        public void setSystemProperty(String str, String str2) {
            SystemProperties.set(str, str2);
        }

        public String getSystemProperty(String str, String str2) {
            return SystemProperties.get(str, str2);
        }

        public long getEnabledFunctions() {
            return this.mCurrentFunctions;
        }

        public long getScreenUnlockedFunctions() {
            if (UsbManager.usbFunctionsToString(UsbDeviceManager.getDefaultUsbValue()).equals("adb")) {
                return 0L;
            }
            return this.mScreenUnlockedFunctions;
        }

        public int getUsbSpeed() {
            return this.mUsbSpeed;
        }

        public int getGadgetHalVersion() {
            return this.mCurrentGadgetHalVersion;
        }

        public final void dumpFunctions(DualDumpOutputStream dualDumpOutputStream, String str, long j, long j2) {
            for (int i = 0; i < 63; i++) {
                long j3 = 1 << i;
                if ((j2 & j3) != 0) {
                    if (dualDumpOutputStream.isProto()) {
                        dualDumpOutputStream.write(str, j, j3);
                    } else {
                        dualDumpOutputStream.write(str, j, GadgetFunction.toString(j3));
                    }
                }
            }
        }

        public void dump(DualDumpOutputStream dualDumpOutputStream, String str, long j) {
            long start = dualDumpOutputStream.start(str, j);
            dumpFunctions(dualDumpOutputStream, "current_functions", 2259152797697L, this.mCurrentFunctions);
            dualDumpOutputStream.write("current_functions_applied", 1133871366146L, this.mCurrentFunctionsApplied);
            dumpFunctions(dualDumpOutputStream, "screen_unlocked_functions", 2259152797699L, this.mScreenUnlockedFunctions);
            dualDumpOutputStream.write("screen_locked", 1133871366148L, this.mScreenLocked);
            dualDumpOutputStream.write("connected", 1133871366149L, this.mConnected);
            dualDumpOutputStream.write("configured", 1133871366150L, this.mConfigured);
            UsbAccessory usbAccessory = this.mCurrentAccessory;
            if (usbAccessory != null) {
                DumpUtils.writeAccessory(dualDumpOutputStream, "current_accessory", 1146756268039L, usbAccessory);
            }
            dualDumpOutputStream.write("host_connected", 1133871366152L, this.mHostConnected);
            dualDumpOutputStream.write("source_power", 1133871366153L, this.mSourcePower);
            dualDumpOutputStream.write("sink_power", 1133871366154L, this.mSinkPower);
            dualDumpOutputStream.write("usb_charging", 1133871366155L, this.mUsbCharging);
            dualDumpOutputStream.write("hide_usb_notification", 1133871366156L, this.mHideUsbNotification);
            dualDumpOutputStream.write("hide_usb_notification", 1133871366156L, this.mHideUsbNotiForSecUsbSmartEP);
            dualDumpOutputStream.write("audio_accessory_connected", 1133871366157L, this.mAudioAccessoryConnected);
            try {
                com.android.internal.util.dump.DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "kernel_state", 1138166333455L, FileUtils.readTextFile(new File("/sys/class/android_usb/android0/state"), 0, null).trim());
            } catch (FileNotFoundException unused) {
                Slog.w(UsbDeviceManager.TAG, "Ignore missing legacy kernel path in bugreport dump: kernel state:/sys/class/android_usb/android0/state");
            } catch (Exception e) {
                Slog.e(UsbDeviceManager.TAG, "Could not read kernel state", e);
            }
            try {
                com.android.internal.util.dump.DumpUtils.writeStringIfNotNull(dualDumpOutputStream, "kernel_function_list", 1138166333456L, FileUtils.readTextFile(new File("/sys/class/android_usb/android0/functions"), 0, null).trim());
            } catch (FileNotFoundException unused2) {
                Slog.w(UsbDeviceManager.TAG, "Ignore missing legacy kernel path in bugreport dump: kernel function list:/sys/class/android_usb/android0/functions");
            } catch (Exception e2) {
                Slog.e(UsbDeviceManager.TAG, "Could not read kernel function list", e2);
            }
            dualDumpOutputStream.end(start);
        }

        public void setAccessoryUEventTime(long j) {
            this.mAccessoryConnectionStartTime = j;
        }

        public void setStartAccessoryTrue() {
            this.mStartAccessory = true;
        }

        public void resetUsbAccessoryHandshakeDebuggingInfo() {
            this.mAccessoryConnectionStartTime = 0L;
            this.mSendStringCount = 0;
            this.mStartAccessory = false;
        }
    }

    public final class UsbHandlerLegacy extends UsbHandler {
        public String mCurrentFunctionsStr;
        public String mCurrentOemFunctions;
        public int mCurrentRequest;
        public HashMap mOemModeMap;
        public boolean mUsbDataUnlocked;

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void getUsbSpeedCb(int i) {
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void handlerInitDone(int i) {
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void resetCb(int i) {
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void setCurrentUsbFunctionsCb(long j, int i, int i2, long j2, boolean z) {
        }

        public UsbHandlerLegacy(Looper looper, Context context, UsbDeviceManager usbDeviceManager, UsbAlsaManager usbAlsaManager, UsbPermissionManager usbPermissionManager) {
            super(looper, context, usbDeviceManager, usbAlsaManager, usbPermissionManager);
            this.mCurrentRequest = 0;
            try {
                readOemUsbOverrideConfig(context);
                this.mCurrentOemFunctions = getSystemProperty(UsbDeviceManager.getPersistProp(false), "none");
                if (isNormalBoot()) {
                    long usbFunctionsFromString = UsbManager.usbFunctionsFromString(SystemProperties.get("sys.usb.config", "none"));
                    this.mCurrentFunctions = usbFunctionsFromString;
                    this.mSecCurrentFunctions = usbFunctionsFromString;
                    if (!FactoryTest.isFactoryBinary()) {
                        this.mCurrentFunctions = (-2) & this.mCurrentFunctions;
                    }
                    String systemProperty = getSystemProperty("sys.usb.config", "none");
                    this.mCurrentFunctionsStr = systemProperty;
                    this.mCurrentFunctionsApplied = systemProperty.equals(getSystemProperty("sys.usb.state", "none"));
                } else {
                    long usbFunctionsFromString2 = UsbManager.usbFunctionsFromString(SystemProperties.get(UsbDeviceManager.getPersistProp(true), "none"));
                    this.mCurrentFunctions = usbFunctionsFromString2;
                    this.mSecCurrentFunctions = usbFunctionsFromString2;
                    if (!FactoryTest.isFactoryBinary()) {
                        this.mCurrentFunctions = (-2) & this.mCurrentFunctions;
                    }
                    this.mCurrentFunctionsStr = getSystemProperty(UsbDeviceManager.getPersistProp(true), "none");
                    this.mCurrentFunctionsApplied = getSystemProperty("sys.usb.config", "none").equals(getSystemProperty("sys.usb.state", "none"));
                }
                this.mCurrentUsbFunctionsReceived = true;
                this.mUsbSpeed = -1;
                this.mCurrentGadgetHalVersion = -1;
                UsbDeviceManager.usbConfiguredState = getSystemProperty("sys.usb.configured", "none");
                String trim = FileUtils.readTextFile(new File("/sys/class/android_usb/android0/state"), 0, null).trim();
                Slog.d(UsbDeviceManager.TAG, "Init mCurrentFunctions=" + UsbManager.usbFunctionsToString(this.mCurrentFunctions) + " state=" + trim);
                updateState(trim);
            } catch (Exception e) {
                Slog.e(UsbDeviceManager.TAG, "Error initializing UsbHandler", e);
            }
        }

        public final void readOemUsbOverrideConfig(Context context) {
            String[] stringArray = context.getResources().getStringArray(17236270);
            if (stringArray != null) {
                for (String str : stringArray) {
                    String[] split = str.split(XmlUtils.STRING_ARRAY_SEPARATOR);
                    if (split.length == 3 || split.length == 4) {
                        if (this.mOemModeMap == null) {
                            this.mOemModeMap = new HashMap();
                        }
                        HashMap hashMap = (HashMap) this.mOemModeMap.get(split[0]);
                        if (hashMap == null) {
                            hashMap = new HashMap();
                            this.mOemModeMap.put(split[0], hashMap);
                        }
                        if (!hashMap.containsKey(split[1])) {
                            if (split.length == 3) {
                                hashMap.put(split[1], new Pair(split[2], ""));
                            } else {
                                hashMap.put(split[1], new Pair(split[2], split[3]));
                            }
                        }
                    }
                }
            }
        }

        public final String applyOemOverrideFunction(String str) {
            String str2;
            if (str != null && this.mOemModeMap != null) {
                String systemProperty = getSystemProperty("ro.bootmode", "unknown");
                Slog.d(UsbDeviceManager.TAG, "applyOemOverride usbfunctions=" + str + " bootmode=" + systemProperty);
                Map map = (Map) this.mOemModeMap.get(systemProperty);
                if (map != null && !systemProperty.equals("normal") && !systemProperty.equals("unknown")) {
                    Pair pair = (Pair) map.get(str);
                    if (pair != null) {
                        Slog.d(UsbDeviceManager.TAG, "OEM USB override: " + str + " ==> " + ((String) pair.first) + " persist across reboot " + ((String) pair.second));
                        if (!((String) pair.second).equals("")) {
                            if (isAdbEnabled()) {
                                str2 = addFunction((String) pair.second, "adb");
                            } else {
                                str2 = (String) pair.second;
                            }
                            Slog.d(UsbDeviceManager.TAG, "OEM USB override persisting: " + str2 + "in prop: " + UsbDeviceManager.getPersistProp(false));
                            setSystemProperty(UsbDeviceManager.getPersistProp(false), str2);
                        }
                        return (String) pair.first;
                    }
                    if (isAdbEnabled()) {
                        setSystemProperty(UsbDeviceManager.getPersistProp(false), addFunction("none", "adb"));
                    } else {
                        setSystemProperty(UsbDeviceManager.getPersistProp(false), "none");
                    }
                }
            }
            return str;
        }

        public final boolean waitForState(String str) {
            int i = (containsFunction(str, "acm") || containsFunction(str, "dm") || containsFunction(str, "adb")) ? 40 : 30;
            if (SystemProperties.get("ro.build.type").equals("eng")) {
                i = 200;
            }
            String str2 = null;
            for (int i2 = 0; i2 < i; i2++) {
                str2 = getSystemProperty("sys.usb.state", "");
                if (str.equals(str2)) {
                    return true;
                }
                SystemClock.sleep(50L);
            }
            Slog.e(UsbDeviceManager.TAG, "waitForState(" + str + ") FAILED: got " + str2 + ", waitLoops " + i);
            return false;
        }

        public final void setUsbConfig(String str) {
            Slog.d(UsbDeviceManager.TAG, "setUsbConfig(" + str + ")");
            setSystemProperty("sys.usb.config", str);
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void setEnabledFunctions(long j, boolean z, int i) {
            boolean isUsbDataTransferActive = isUsbDataTransferActive(j);
            Slog.d(UsbDeviceManager.TAG, "setEnabledFunctions functions=" + j + " ,forceRestart=" + z + " ,usbDataUnlocked=" + isUsbDataTransferActive + " ,operationId=" + i);
            if (isUsbDataTransferActive != this.mUsbDataUnlocked && !FactoryTest.isFactoryBinary() && !UsbDeviceManager.isMassTestEnabled()) {
                this.mUsbDataUnlocked = isUsbDataTransferActive;
                updateUsbNotification(false);
                z = true;
            }
            long j2 = this.mCurrentFunctions;
            boolean z2 = this.mCurrentFunctionsApplied;
            if (trySetEnabledFunctions(j, z)) {
                return;
            }
            if (z2 && j2 != j) {
                Slog.e(UsbDeviceManager.TAG, "Failsafe 1: Restoring previous USB functions.");
                if (trySetEnabledFunctions(j2, false)) {
                    return;
                }
            }
            Slog.e(UsbDeviceManager.TAG, "Failsafe 2: Restoring default USB functions.");
            if (trySetEnabledFunctions(UsbDeviceManager.getDefaultUsbValue(), false)) {
                return;
            }
            Slog.e(UsbDeviceManager.TAG, "Failsafe 3: Restoring empty function list (with ADB if enabled).");
            if (trySetEnabledFunctions(0L, false)) {
                return;
            }
            Slog.e(UsbDeviceManager.TAG, "Unable to set any USB functions!");
        }

        public final boolean isNormalBoot() {
            String systemProperty = getSystemProperty("ro.bootmode", "unknown");
            return systemProperty.equals("normal") || systemProperty.equals("unknown");
        }

        public String applyAdbFunction(String str) {
            if (str == null) {
                str = "";
            }
            if (isAdbEnabled()) {
                return addFunction(str, "adb");
            }
            return removeFunction(str, "adb");
        }

        public final boolean trySetEnabledFunctions(long j, boolean z) {
            String usbFunctionsToString;
            Slog.d(UsbDeviceManager.TAG, "trySetEnabledFunctions: usbFunctions=" + UsbManager.usbFunctionsToString(j) + " forceRestart=" + z);
            if (j != 0) {
                usbFunctionsToString = UsbManager.usbFunctionsToString(j);
            } else {
                long defaultUsbValue = UsbDeviceManager.getDefaultUsbValue();
                usbFunctionsToString = UsbManager.usbFunctionsToString(defaultUsbValue);
                if (isUsbDataTransferActive(defaultUsbValue)) {
                    this.mUsbDataUnlocked = true;
                }
            }
            this.mCurrentFunctions = j;
            if (!FactoryTest.isFactoryBinary()) {
                this.mCurrentFunctions &= -2;
            }
            if (usbFunctionsToString == null || applyAdbFunction(usbFunctionsToString).equals("none")) {
                usbFunctionsToString = UsbManager.usbFunctionsToString(getChargingFunctions());
            }
            String validateRestrictionPolicy = this.mUsbDeviceManager.validateRestrictionPolicy(applyAdbFunction(usbFunctionsToString));
            this.mSecCurrentFunctions = UsbManager.usbFunctionsFromString(validateRestrictionPolicy);
            Slog.d(UsbDeviceManager.TAG, "Update mCurrentFunctions=" + UsbManager.usbFunctionsToString(this.mCurrentFunctions) + " mSecCurrentFunctions=" + UsbManager.usbFunctionsToString(this.mSecCurrentFunctions));
            String applyOemOverrideFunction = applyOemOverrideFunction(validateRestrictionPolicy);
            if (!isNormalBoot() && !this.mCurrentFunctionsStr.equals(validateRestrictionPolicy)) {
                setSystemProperty(UsbDeviceManager.getPersistProp(true), validateRestrictionPolicy);
            }
            if ((!validateRestrictionPolicy.equals(applyOemOverrideFunction) && !this.mCurrentOemFunctions.equals(applyOemOverrideFunction)) || !this.mCurrentFunctionsStr.equals(validateRestrictionPolicy) || !this.mCurrentFunctionsApplied || z) {
                this.mCurrentFunctionsStr = validateRestrictionPolicy;
                this.mCurrentOemFunctions = applyOemOverrideFunction;
                this.mCurrentFunctionsApplied = false;
                setUsbConfig("none");
                if (!waitForState("none")) {
                    Slog.e(UsbDeviceManager.TAG, "Failed to kick USB config");
                    return false;
                }
                if (containsFunction(applyOemOverrideFunction, "shutdown")) {
                    Slog.d(UsbDeviceManager.TAG, "Set SHUTDOWN USB MODE, so set values to none");
                    this.mCurrentFunctions = 0L;
                    this.mSecCurrentFunctions = 0L;
                    this.mCurrentFunctionsStr = "none";
                    this.mCurrentOemFunctions = "none";
                    this.mCurrentFunctionsApplied = true;
                    return true;
                }
                setUsbConfig(applyOemOverrideFunction);
                if (this.mBootCompleted && (containsFunction(validateRestrictionPolicy, "mtp") || containsFunction(validateRestrictionPolicy, "ptp"))) {
                    updateUsbStateBroadcastIfNeeded(getAppliedFunctions(this.mSecCurrentFunctions), true);
                }
                if (!waitForState(applyOemOverrideFunction)) {
                    Slog.e(UsbDeviceManager.TAG, "Failed to switch USB config to " + validateRestrictionPolicy);
                    return false;
                }
                this.mCurrentFunctionsApplied = true;
            }
            return true;
        }

        public static String addFunction(String str, String str2) {
            if ("none".equals(str)) {
                return str2;
            }
            if (containsFunction(str, str2)) {
                return str;
            }
            if (str.length() > 0) {
                str = str + ",";
            }
            return str + str2;
        }

        public static String removeFunction(String str, String str2) {
            String[] split = str.split(",");
            for (int i = 0; i < split.length; i++) {
                if (str2.equals(split[i])) {
                    split[i] = null;
                }
            }
            if (split.length == 1 && split[0] == null) {
                return "none";
            }
            StringBuilder sb = new StringBuilder();
            for (String str3 : split) {
                if (str3 != null) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(str3);
                }
            }
            return sb.toString();
        }

        public static boolean containsFunction(String str, String str2) {
            int indexOf = str.indexOf(str2);
            if (indexOf < 0) {
                return false;
            }
            if (indexOf > 0 && str.charAt(indexOf - 1) != ',') {
                return false;
            }
            int length = indexOf + str2.length();
            return length >= str.length() || str.charAt(length) == ',';
        }
    }

    public final class UsbHandlerHal extends UsbHandler {
        public int mCurrentRequest;
        public boolean mCurrentUsbFunctionsRequested;
        public final Object mGadgetProxyLock;

        public UsbHandlerHal(Looper looper, Context context, UsbDeviceManager usbDeviceManager, UsbAlsaManager usbAlsaManager, UsbPermissionManager usbPermissionManager) {
            super(looper, context, usbDeviceManager, usbAlsaManager, usbPermissionManager);
            Object obj = new Object();
            this.mGadgetProxyLock = obj;
            this.mCurrentRequest = 0;
            UsbDeviceManager.sUsbOperationCount.incrementAndGet();
            try {
                synchronized (obj) {
                    this.mCurrentFunctions = 0L;
                    this.mCurrentUsbFunctionsRequested = true;
                    this.mUsbSpeed = -1;
                    this.mCurrentGadgetHalVersion = 10;
                    updateUsbGadgetHalVersion();
                }
                updateState(FileUtils.readTextFile(new File("/sys/class/android_usb/android0/state"), 0, null).trim());
            } catch (NoSuchElementException e) {
                Slog.e(UsbDeviceManager.TAG, "Usb gadget hal not found", e);
            } catch (Exception e2) {
                Slog.e(UsbDeviceManager.TAG, "Error initializing UsbHandler", e2);
            }
        }

        public final class ServiceNotification extends IServiceNotification.Stub {
            public final /* synthetic */ UsbHandlerHal this$0;

            @Override // android.hidl.manager.V1_0.IServiceNotification
            public void onRegistration(String str, String str2, boolean z) {
                Slog.i(UsbDeviceManager.TAG, "Usb gadget hal service started " + str + " " + str2);
                if (!str.equals("android.hardware.usb.gadget@1.0::IUsbGadget")) {
                    Slog.e(UsbDeviceManager.TAG, "fqName does not match");
                } else {
                    this.this$0.sendMessage(18, z);
                }
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler, android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 14:
                    setEnabledFunctions(0L, false, UsbDeviceManager.sUsbOperationCount.incrementAndGet());
                    return;
                case 15:
                    int incrementAndGet = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    Slog.e(UsbDeviceManager.TAG, "Set functions timed out! no reply from usb hal ,operationId:" + incrementAndGet);
                    if (message.arg1 != 1) {
                        setEnabledFunctions(this.mScreenUnlockedFunctions, false, incrementAndGet);
                        return;
                    }
                    return;
                case 16:
                    Slog.i(UsbDeviceManager.TAG, "processing MSG_GET_CURRENT_USB_FUNCTIONS");
                    this.mCurrentUsbFunctionsReceived = true;
                    int i = message.arg2;
                    if (this.mCurrentUsbFunctionsRequested) {
                        Slog.i(UsbDeviceManager.TAG, "updating mCurrentFunctions");
                        this.mCurrentFunctions = ((Long) message.obj).longValue() & (-2);
                        Slog.i(UsbDeviceManager.TAG, "mCurrentFunctions:" + this.mCurrentFunctions + "applied:" + message.arg1);
                        this.mCurrentFunctionsApplied = message.arg1 == 1;
                    }
                    finishBoot(i);
                    return;
                case 17:
                    int incrementAndGet2 = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    if (message.arg1 != 1) {
                        if (this.mCurrentFunctions == 2) {
                            notifyAccessoryModeExit(incrementAndGet2);
                            return;
                        } else {
                            setEnabledFunctions(this.mScreenUnlockedFunctions, false, incrementAndGet2);
                            return;
                        }
                    }
                    return;
                case 18:
                    boolean z = message.arg1 == 1;
                    int incrementAndGet3 = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    synchronized (this.mGadgetProxyLock) {
                        try {
                            UsbDeviceManager.mUsbGadgetHal = UsbGadgetHalInstance.getInstance(this.mUsbDeviceManager, null);
                            if (!this.mCurrentFunctionsApplied && !z) {
                                setEnabledFunctions(this.mCurrentFunctions, false, incrementAndGet3);
                            }
                        } catch (NoSuchElementException e) {
                            Slog.e(UsbDeviceManager.TAG, "Usb gadget hal not found", e);
                        }
                    }
                    return;
                case 19:
                    int incrementAndGet4 = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    synchronized (this.mGadgetProxyLock) {
                        if (UsbDeviceManager.mUsbGadgetHal == null) {
                            Slog.e(UsbDeviceManager.TAG, "reset Usb Gadget mUsbGadgetHal is null");
                            return;
                        }
                        try {
                            removeMessages(8);
                            if (this.mConfigured) {
                                this.mResetUsbGadgetDisableDebounce = true;
                            }
                            UsbDeviceManager.mUsbGadgetHal.reset(incrementAndGet4);
                        } catch (Exception e2) {
                            Slog.e(UsbDeviceManager.TAG, "reset Usb Gadget failed", e2);
                            this.mResetUsbGadgetDisableDebounce = false;
                        }
                        return;
                    }
                case 20:
                case 21:
                default:
                    super.handleMessage(message);
                    return;
                case 22:
                    int incrementAndGet5 = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    if (UsbDeviceManager.mUsbGadgetHal == null) {
                        Slog.e(UsbDeviceManager.TAG, "mGadgetHal is null, operationId:" + incrementAndGet5);
                        return;
                    }
                    try {
                        UsbDeviceManager.mUsbGadgetHal.getUsbSpeed(incrementAndGet5);
                        return;
                    } catch (Exception e3) {
                        Slog.e(UsbDeviceManager.TAG, "get UsbSpeed failed", e3);
                        return;
                    }
                case 23:
                    if (UsbDeviceManager.mUsbGadgetHal == null) {
                        Slog.e(UsbDeviceManager.TAG, "mUsbGadgetHal is null");
                        return;
                    }
                    try {
                        this.mCurrentGadgetHalVersion = UsbDeviceManager.mUsbGadgetHal.getGadgetHalVersion();
                        return;
                    } catch (RemoteException e4) {
                        Slog.e(UsbDeviceManager.TAG, "update Usb gadget version failed", e4);
                        return;
                    }
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void setCurrentUsbFunctionsCb(long j, int i, int i2, long j2, boolean z) {
            if (this.mCurrentRequest == i2 && hasMessages(15) && j2 == j) {
                removeMessages(15);
                Slog.i(UsbDeviceManager.TAG, "notifyCurrentFunction request:" + i2 + " status:" + i);
                if (i == 0) {
                    this.mCurrentFunctionsApplied = true;
                } else {
                    if (z) {
                        return;
                    }
                    Slog.e(UsbDeviceManager.TAG, "Setting default fuctions");
                    sendEmptyMessage(14);
                }
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void getUsbSpeedCb(int i) {
            this.mUsbSpeed = i;
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void resetCb(int i) {
            if (i != 0) {
                Slog.e(UsbDeviceManager.TAG, "resetCb fail");
            }
        }

        public final void setUsbConfig(long j, boolean z, int i) {
            String str = UsbDeviceManager.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("setUsbConfig(");
            sb.append(j);
            sb.append(") request:");
            int i2 = this.mCurrentRequest + 1;
            this.mCurrentRequest = i2;
            sb.append(i2);
            Slog.d(str, sb.toString());
            removeMessages(17);
            removeMessages(15);
            removeMessages(14);
            synchronized (this.mGadgetProxyLock) {
                if (UsbDeviceManager.mUsbGadgetHal == null) {
                    Slog.e(UsbDeviceManager.TAG, "setUsbConfig mUsbGadgetHal is null");
                    return;
                }
                try {
                    if ((1 & j) != 0) {
                        ((AdbManagerInternal) LocalServices.getService(AdbManagerInternal.class)).startAdbdForTransport((byte) 0);
                    } else {
                        ((AdbManagerInternal) LocalServices.getService(AdbManagerInternal.class)).stopAdbdForTransport((byte) 0);
                    }
                    UsbDeviceManager.mUsbGadgetHal.setCurrentUsbFunctions(this.mCurrentRequest, j, z, 2500, i);
                    sendMessageDelayed(15, z, 3000L);
                    if (this.mConnected) {
                        sendMessageDelayed(17, z, 5000L);
                    }
                    Slog.d(UsbDeviceManager.TAG, "timeout message queued");
                } catch (Exception e) {
                    Slog.e(UsbDeviceManager.TAG, "Remoteexception while calling setCurrentUsbFunctions", e);
                }
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void setEnabledFunctions(long j, boolean z, int i) {
            Slog.d(UsbDeviceManager.TAG, "setEnabledFunctionsi functions=" + j + ", forceRestart=" + z + ", operationId=" + i);
            if (this.mCurrentGadgetHalVersion < 12 && (1024 & j) != 0) {
                Slog.e(UsbDeviceManager.TAG, "Could not set unsupported function for the GadgetHal");
                return;
            }
            if (this.mCurrentFunctions == j && this.mCurrentFunctionsApplied && !z) {
                return;
            }
            Slog.i(UsbDeviceManager.TAG, "Setting USB config to " + UsbManager.usbFunctionsToString(j));
            this.mCurrentFunctions = j;
            this.mCurrentFunctionsApplied = false;
            this.mCurrentUsbFunctionsRequested = false;
            boolean z2 = j == 0;
            long appliedFunctions = getAppliedFunctions(j);
            setUsbConfig(appliedFunctions, z2, i);
            if (this.mBootCompleted && isUsbDataTransferActive(appliedFunctions)) {
                updateUsbStateBroadcastIfNeeded(appliedFunctions, true);
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public void handlerInitDone(int i) {
            UsbDeviceManager.mUsbGadgetHal.getCurrentUsbFunctions(i);
        }
    }

    public UsbAccessory getCurrentAccessory() {
        return this.mHandler.getCurrentAccessory();
    }

    public ParcelFileDescriptor openAccessory(UsbAccessory usbAccessory, UsbUserPermissionManager usbUserPermissionManager, int i, int i2) {
        UsbAccessory currentAccessory = this.mHandler.getCurrentAccessory();
        if (currentAccessory == null) {
            throw new IllegalArgumentException("no accessory attached");
        }
        if (!currentAccessory.equals(usbAccessory)) {
            throw new IllegalArgumentException(usbAccessory.toString() + " does not match current accessory " + currentAccessory);
        }
        usbUserPermissionManager.checkPermission(usbAccessory, i, i2);
        return nativeOpenAccessory();
    }

    public long getCurrentFunctions() {
        return this.mHandler.getEnabledFunctions();
    }

    public int getCurrentUsbSpeed() {
        return this.mHandler.getUsbSpeed();
    }

    public int getGadgetHalVersion() {
        return this.mHandler.getGadgetHalVersion();
    }

    public void setCurrentUsbFunctionsCb(long j, int i, int i2, long j2, boolean z) {
        this.mHandler.setCurrentUsbFunctionsCb(j, i, i2, j2, z);
    }

    public void getCurrentUsbFunctionsCb(long j, int i) {
        this.mHandler.sendMessage(16, Long.valueOf(j), i == 2);
    }

    public void getUsbSpeedCb(int i) {
        this.mHandler.getUsbSpeedCb(i);
    }

    public void resetCb(int i) {
        this.mHandler.resetCb(i);
    }

    public ParcelFileDescriptor getControlFd(long j) {
        FileDescriptor fileDescriptor = (FileDescriptor) this.mControlFds.get(Long.valueOf(j));
        if (fileDescriptor == null) {
            return null;
        }
        try {
            return ParcelFileDescriptor.dup(fileDescriptor);
        } catch (IOException unused) {
            Slog.e(TAG, "Could not dup fd for " + j);
            return null;
        }
    }

    public long getScreenUnlockedFunctions() {
        return this.mHandler.getScreenUnlockedFunctions();
    }

    public void setCurrentFunctions(long j, int i) {
        String str = TAG;
        Slog.d(str, "setCurrentFunctions(" + UsbManager.usbFunctionsToString(j) + ")");
        String findForegroundPackage = findForegroundPackage();
        if (mEnableUsbHiddenMenu || findForegroundPackage.equals("com.sec.usbsettings") || findForegroundPackage.equals("com.sec.hiddenmenu")) {
            if ((j & 4) != 0) {
                SystemProperties.set("persist.sys.usb.q_config", "none");
                Slog.d(str, "Disable USB Hidden Menu");
            } else {
                String usbFunctionsToString = UsbManager.usbFunctionsToString(j);
                SystemProperties.set("persist.sys.usb.q_config", usbFunctionsToString);
                Slog.d(str, "Enable USB Hidden Menu: functions=" + usbFunctionsToString);
            }
        }
        if (j == 0) {
            MetricsLogger.action(this.mContext, 1275);
        } else if (j == 4) {
            MetricsLogger.action(this.mContext, 1276);
        } else if (j == 16) {
            MetricsLogger.action(this.mContext, 1277);
        } else if (j == 8) {
            MetricsLogger.action(this.mContext, 1279);
        } else if (j == 32) {
            MetricsLogger.action(this.mContext, 1278);
        } else if (j == 2) {
            MetricsLogger.action(this.mContext, 1280);
        }
        this.mHandler.sendMessage(2, Long.valueOf(j), i);
    }

    public void setScreenUnlockedFunctions(long j) {
        String str = TAG;
        Slog.d(str, "setScreenUnlockedFunctions(" + UsbManager.usbFunctionsToString(j) + ")");
        if ("adb".equals(SystemProperties.get(getPersistProp(false), "none")) && j == 0) {
            Slog.d(str, "setScreenUnlockedFunctions keep the current state");
        } else {
            this.mHandler.sendMessage(12, Long.valueOf(j));
        }
    }

    public void resetUsbGadget() {
        Slog.d(TAG, "reset Usb Gadget");
        this.mHandler.sendMessage(19, (Object) null);
    }

    public void dump(DualDumpOutputStream dualDumpOutputStream, String str, long j) {
        long start = dualDumpOutputStream.start(str, j);
        UsbHandler usbHandler = this.mHandler;
        if (usbHandler != null) {
            usbHandler.dump(dualDumpOutputStream, "handler", 1146756268033L);
            sEventLogger.dump(new DualOutputStreamDumpSink(dualDumpOutputStream, 1138166333457L));
        }
        dualDumpOutputStream.end(start);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r9v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean hasSecRestriction(String str, String str2, String str3, String[] strArr) {
        Exception e;
        Cursor cursor;
        Uri parse = Uri.parse(str2);
        ?? r9 = 0;
        Cursor cursor2 = null;
        try {
            try {
                try {
                    cursor = this.mContext.getContentResolver().query(parse, null, str3, strArr, null);
                    if (cursor != null) {
                        try {
                            cursor.moveToFirst();
                            if ("false".equals(cursor.getString(cursor.getColumnIndex(str3)))) {
                                Slog.d(TAG, "Function " + str + " is restricted");
                                cursor.close();
                                return true;
                            }
                            cursor.close();
                        } catch (Exception e2) {
                            e = e2;
                            Slog.e(TAG, "policy : " + str + ", return exception", e);
                            if (cursor == null) {
                                return false;
                            }
                            cursor.close();
                            return false;
                        }
                    } else {
                        cursor2 = cursor;
                    }
                } catch (Throwable th) {
                    th = th;
                    r9 = this;
                    if (r9 != 0) {
                        try {
                            r9.close();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                cursor = null;
            } catch (Throwable th2) {
                th = th2;
                if (r9 != 0) {
                }
                throw th;
            }
            if (cursor2 == null) {
                return false;
            }
            cursor2.close();
            return false;
        } catch (Exception e5) {
            e5.printStackTrace();
            return false;
        }
    }

    public final boolean hasDeviceRestriction(String str) {
        if (str.equals("adb")) {
            return hasSecRestriction(str, "content://com.sec.knox.provider/RestrictionPolicy3", "isUsbDebuggingEnabled", null);
        }
        Slog.d(TAG, "Undefine restriction policy!");
        return false;
    }

    public final String validateRestrictionPolicy(String str) {
        return (UsbHandlerLegacy.containsFunction(str, "adb") && hasDeviceRestriction("adb")) ? UsbHandlerLegacy.removeFunction(str, "adb") : str;
    }

    public static String getPersistProp(boolean z) {
        String str = isHiddenMenuActivated() ? "persist.sys.usb.q_config" : "persist.sys.usb.config";
        Slog.d(TAG, "getPersistProp: return=" + str);
        return str;
    }

    public static long getDefaultUsbValue() {
        int knoxCustomUsbConnectionType = getKnoxCustomUsbConnectionType();
        if (knoxCustomUsbConnectionType == 1) {
            Slog.d(TAG, "getDefaultUsbValue :: knoxUsb returns MTP");
            return 4L;
        }
        if (knoxCustomUsbConnectionType == 2) {
            Slog.d(TAG, "getDefaultUsbValue :: knoxUsb returns PTP");
            return 16L;
        }
        if (knoxCustomUsbConnectionType == 3) {
            Slog.d(TAG, "getDefaultUsbValue :: knoxUsb returns MIDI");
            return 8L;
        }
        if (knoxCustomUsbConnectionType == 4) {
            Slog.d(TAG, "getDefaultUsbValue :: knoxUsb returns CHARGING");
            return 262144L;
        }
        if (knoxCustomUsbConnectionType == 5) {
            Slog.d(TAG, "getDefaultUsbValue :: knoxUsb returns TETHERING");
            return 32L;
        }
        String persistProp = getPersistProp(false);
        String str = SystemProperties.get(persistProp, "none");
        if (persistProp.equals("persist.sys.usb.q_config")) {
            return UsbManager.usbFunctionsFromString(str);
        }
        if (FactoryTest.isFactoryBinary() || isMassTestEnabled()) {
            Slog.d(TAG, "Prevent temporary usb disconnection for Factory Binary or JIG connection");
            str = "mtp,adb";
        }
        return UsbManager.usbFunctionsFromString(str);
    }

    public static boolean isHiddenMenuActivated() {
        return !SystemProperties.get("persist.sys.usb.q_config", "none").equals("none");
    }

    public final String findForegroundPackage() {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE);
        if (runningTasks == null || runningTasks.isEmpty()) {
            return "";
        }
        if (SystemProperties.get("ro.build.type").equals("eng")) {
            for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
                Slog.d(TAG, "task.topActivity=" + runningTaskInfo.topActivity.getPackageName());
            }
        }
        return runningTasks.get(0).topActivity.getPackageName();
    }

    public void setUsbHiddenMenuState(boolean z) {
        mEnableUsbHiddenMenu = z;
    }

    public static boolean isMassTestEnabled() {
        try {
            File file = new File("/sys/class/sec/rid_adc/rid_adc");
            if (file.exists()) {
                String trim = FileUtils.readTextFile(file, 0, null).trim();
                Slog.d(TAG, "isMassTestEnabled: state=" + trim);
                if (trim.equals("255K") || trim.equals("302K") || trim.equals("523K")) {
                    return true;
                }
                if (trim.equals("619K")) {
                    return true;
                }
            }
        } catch (Exception e) {
            Slog.e(TAG, "Could't read /sys/class/sec/rid_adc/rid_adc", e);
        }
        return false;
    }

    public void setUsbDisableVariable(String str) {
        usbDisableSettingVal = str;
    }

    public void updateUsbNotificationRefresh() {
        this.mHandler.sendMessage(101, true);
    }

    public void setNextUsbModeToDefault() {
        Slog.d(TAG, "setNextUsbModeToDefault");
        mSetNextUsbModeToDefault = true;
    }

    public void rmSetNextUsbModeToDefault() {
        Slog.d(TAG, "rmSetNextUsbModeToDefault");
        mSetNextUsbModeToDefault = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0121 A[Catch: IOException -> 0x011d, TRY_LEAVE, TryCatch #16 {IOException -> 0x011d, blocks: (B:76:0x0119, B:69:0x0121), top: B:75:0x0119 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0119 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v34 */
    /* JADX WARN: Type inference failed for: r0v8, types: [java.io.BufferedReader] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getUsbSecurityCheckNodeValue() {
        ?? r0;
        FileReader fileReader;
        IllegalArgumentException e;
        BufferedReader bufferedReader;
        IOException e2;
        FileNotFoundException e3;
        String str;
        StringBuilder sb;
        String str2 = "/sys/class/sec/ccic/usbpd_ids";
        String str3 = "null";
        FileReader fileReader2 = null;
        try {
            try {
                if (new File("/sys/class/sec/ccic/usbpd_ids").exists()) {
                    fileReader = new FileReader("/sys/class/sec/ccic/usbpd_ids");
                    try {
                        bufferedReader = new BufferedReader(fileReader);
                        try {
                            str3 = bufferedReader.readLine();
                            fileReader2 = fileReader;
                        } catch (FileNotFoundException e4) {
                            e3 = e4;
                            Slog.e(TAG, "File not Found exception: " + e3.getMessage());
                            if (fileReader != null) {
                                try {
                                    fileReader.close();
                                } catch (IOException e5) {
                                    e = e5;
                                    str = TAG;
                                    sb = new StringBuilder();
                                    sb.append("IOException(iex): ");
                                    sb.append(e.getMessage());
                                    Slog.e(str, sb.toString());
                                    return str3;
                                }
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return str3;
                        } catch (IOException e6) {
                            e2 = e6;
                            Slog.e(TAG, "IOException: " + e2.getMessage());
                            if (fileReader != null) {
                                try {
                                    fileReader.close();
                                } catch (IOException e7) {
                                    e = e7;
                                    str = TAG;
                                    sb = new StringBuilder();
                                    sb.append("IOException(iex): ");
                                    sb.append(e.getMessage());
                                    Slog.e(str, sb.toString());
                                    return str3;
                                }
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return str3;
                        } catch (IllegalArgumentException e8) {
                            e = e8;
                            Slog.e(TAG, "IllegalArgumentException: " + e.getMessage());
                            if (fileReader != null) {
                                try {
                                    fileReader.close();
                                } catch (IOException e9) {
                                    e = e9;
                                    str = TAG;
                                    sb = new StringBuilder();
                                    sb.append("IOException(iex): ");
                                    sb.append(e.getMessage());
                                    Slog.e(str, sb.toString());
                                    return str3;
                                }
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return str3;
                        }
                    } catch (FileNotFoundException e10) {
                        e3 = e10;
                        bufferedReader = null;
                    } catch (IOException e11) {
                        e2 = e11;
                        bufferedReader = null;
                    } catch (IllegalArgumentException e12) {
                        e = e12;
                        bufferedReader = null;
                    } catch (Throwable th) {
                        th = th;
                        str2 = null;
                        fileReader2 = fileReader;
                        r0 = str2;
                        if (fileReader2 != null) {
                        }
                        if (r0 != 0) {
                        }
                        throw th;
                    }
                } else {
                    Slog.e(TAG, "USB Check File does not exist");
                    bufferedReader = null;
                }
                if (fileReader2 != null) {
                    try {
                        fileReader2.close();
                    } catch (IOException e13) {
                        e = e13;
                        str = TAG;
                        sb = new StringBuilder();
                        sb.append("IOException(iex): ");
                        sb.append(e.getMessage());
                        Slog.e(str, sb.toString());
                        return str3;
                    }
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (FileNotFoundException e14) {
            fileReader = null;
            e3 = e14;
            bufferedReader = null;
        } catch (IOException e15) {
            fileReader = null;
            e2 = e15;
            bufferedReader = null;
        } catch (IllegalArgumentException e16) {
            fileReader = null;
            e = e16;
            bufferedReader = null;
        } catch (Throwable th3) {
            th = th3;
            r0 = 0;
            if (fileReader2 != null) {
                try {
                    fileReader2.close();
                } catch (IOException e17) {
                    Slog.e(TAG, "IOException(iex): " + e17.getMessage());
                    throw th;
                }
            }
            if (r0 != 0) {
                r0.close();
            }
            throw th;
        }
        return str3;
    }

    public static int getKnoxCustomUsbConnectionType() {
        try {
            IKnoxCustomManager asInterface = IKnoxCustomManager.Stub.asInterface(ServiceManager.getService(KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE));
            if (asInterface != null) {
                return asInterface.getUsbConnectionTypeInternal();
            }
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
