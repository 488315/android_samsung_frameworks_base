package com.android.server.usb;

import android.R;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.debug.AdbManagerInternal;
import android.debug.IAdbTransport;
import android.hardware.usb.ParcelableUsbPort;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import android.hardware.usb.gadget.V1_0.GadgetFunction;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Environment;
import android.os.FactoryTest;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
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
import android.text.TextUtils;
import android.util.Pair;
import android.util.sysfwutil.DexConnectionListener;
import android.util.sysfwutil.DexObserver;
import android.util.sysfwutil.Slog;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.os.SomeArgs;
import com.android.internal.usb.DumpUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.usb.hal.gadget.UsbGadgetHal;
import com.android.server.usb.hal.gadget.UsbGadgetHalInstance;
import com.android.server.utils.EventLogger;
import com.android.server.wm.ActivityTaskManagerInternal;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.restriction.IRestrictionPolicy;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsbDeviceManager implements ActivityTaskManagerInternal.ScreenObserver {
    public static DexObserver mDexObserver;
    public static boolean mEnableUsbHiddenMenu;
    public static boolean mSetNextUsbModeToDefault;
    public static boolean mSupportDualRole;
    public static UsbGadgetHal mUsbGadgetHal;
    public static final Set sDenyInterfaces;
    public static EventLogger sEventLogger;
    public static final AtomicInteger sUsbOperationCount = new AtomicInteger();
    public static String usbDisableSettingVal;
    public String[] mAccessoryStrings;
    public final Context mContext;
    public final HashMap mControlFds;
    public UsbProfileGroupSettingsManager mCurrentSettings;
    public final UsbHandler mHandler;
    public final boolean mHasUsbAccessory;
    public final UsbUEventObserver mUEventObserver;
    public final Object mLock = new Object();
    public final AnonymousClass1 mDexListener =
            new DexConnectionListener() { // from class: com.android.server.usb.UsbDeviceManager.1
                public final void onConnect() {
                    Set set = UsbDeviceManager.sDenyInterfaces;
                    Slog.d("UsbDeviceManager", " DexConnectionListener:onConnect()");
                    UsbHandler usbHandler = UsbDeviceManager.this.mHandler;
                    usbHandler.removeMessages(101);
                    Message obtain = Message.obtain(usbHandler, 101);
                    obtain.arg1 = 1;
                    usbHandler.sendMessage(obtain);
                }
            };
    public final AnonymousClass2 mHostConnectionReceiver = new AnonymousClass2();

    /* renamed from: com.android.server.usb.UsbDeviceManager$2, reason: invalid class name */
    public final class AnonymousClass2 extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Set set = UsbDeviceManager.sDenyInterfaces;
            Slog.d("UsbDeviceManager", "received ACTION_USB_OTG_CONNECTION");
            if (intent.getStringExtra("Connect").equals("On")) {
                UsbDeviceManager.mSetNextUsbModeToDefault = true;
                Slog.d(
                        "UsbDeviceManager",
                        "Set mSetNextUsbModeToDefault="
                                + UsbDeviceManager.mSetNextUsbModeToDefault);
            }
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
        public boolean mIsMtpServiceBound;
        public int mMidiCard;
        public int mMidiDevice;
        public boolean mMidiEnabled;
        public final AnonymousClass1 mMtpServiceConnection;
        public NotificationManager mNotificationManager;
        public boolean mPendingBootAccessoryHandshakeBroadcast;
        public boolean mPendingBootBroadcast;
        public final UsbPermissionManager mPermissionManager;
        public boolean mResetUsbGadgetDisableDebounce;
        public boolean mScreenLocked;
        public long mScreenUnlockedFunctions;
        public long mSecCurrentFunctions;
        public int mSendStringCount;
        public final SharedPreferences mSettings;
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
        public final boolean mUseUsbNotification;

        /* renamed from: com.android.server.usb.UsbDeviceManager$UsbHandler$1, reason: invalid class name */
        public final class AnonymousClass1 implements ServiceConnection {
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {}

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {}
        }

        public final class AdbTransport extends IAdbTransport.Stub {
            public final UsbHandler mHandler;

            public AdbTransport(UsbHandler usbHandler) {
                this.mHandler = usbHandler;
            }

            public final void onAdbEnabled(boolean z, byte b) {
                if (b == 0) {
                    int incrementAndGet = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    UsbHandler usbHandler = this.mHandler;
                    usbHandler.removeMessages(1);
                    Message obtain = Message.obtain(usbHandler, 1);
                    obtain.arg1 = z ? 1 : 0;
                    obtain.arg2 = incrementAndGet;
                    usbHandler.sendMessage(obtain);
                }
            }
        }

        public UsbHandler(
                Looper looper,
                Context context,
                UsbDeviceManager usbDeviceManager,
                UsbAlsaManager usbAlsaManager,
                UsbPermissionManager usbPermissionManager) {
            super(looper);
            this.mAccessoryConnectionStartTime = 0L;
            boolean z = false;
            this.mSendStringCount = 0;
            this.mStartAccessory = false;
            this.mIsMtpServiceBound = false;
            this.mMtpServiceConnection = new AnonymousClass1();
            this.mContext = context;
            this.mUsbDeviceManager = usbDeviceManager;
            this.mUsbAlsaManager = usbAlsaManager;
            this.mPermissionManager = usbPermissionManager;
            context.getContentResolver();
            this.mCurrentUser = ActivityManager.getCurrentUser();
            this.mScreenLocked = true;
            SharedPreferences sharedPreferences =
                    context.createDeviceProtectedStorageContext()
                            .getSharedPreferences(
                                    new File(
                                            Environment.getDataSystemDeDirectory(0),
                                            "UsbDeviceManagerPrefs.xml"),
                                    0);
            this.mSettings = sharedPreferences;
            if (sharedPreferences == null) {
                Set set = UsbDeviceManager.sDenyInterfaces;
                Slog.e("UsbDeviceManager", "Couldn't load shared preferences");
            } else {
                Locale locale = Locale.ENGLISH;
                this.mScreenUnlockedFunctions =
                        UsbManager.usbFunctionsFromString(
                                sharedPreferences.getString(
                                        "usb-screen-unlocked-config-" + this.mCurrentUser, ""));
            }
            StorageManager from = StorageManager.from(context);
            StorageVolume primaryVolume = from != null ? from.getPrimaryVolume() : null;
            if ((primaryVolume == null || !primaryVolume.allowMassStorage())
                    && context.getResources()
                            .getBoolean(R.bool.config_use_strict_phone_number_comparation)) {
                z = true;
            }
            this.mUseUsbNotification = z;
        }

        public static void dumpFunctions(
                DualDumpOutputStream dualDumpOutputStream, String str, long j, long j2) {
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

        public static long getAppliedFunctions(long j) {
            return j == 0 ? isAdbEnabled() ? 1L : 4L : isAdbEnabled() ? j | 1 : j;
        }

        public static boolean isAdbEnabled() {
            return ((AdbManagerInternal) LocalServices.getService(AdbManagerInternal.class))
                    .isAdbEnabled((byte) 0);
        }

        public static boolean isEDMRestrictionPolicy() {
            try {
                IRestrictionPolicy asInterface =
                        IRestrictionPolicy.Stub.asInterface(
                                ServiceManager.getService("restriction_policy"));
                if (asInterface == null
                        || asInterface.isUsbMediaPlayerAvailable(
                                new ContextInfo(Binder.getCallingUid()), false)) {
                    return true;
                }
                Set set = UsbDeviceManager.sDenyInterfaces;
                Slog.d("UsbDeviceManager", "USB_MEDIA_PLAYER_AVAILABLE is false");
                return false;
            } catch (RemoteException e) {
                Set set2 = UsbDeviceManager.sDenyInterfaces;
                Slog.e(
                        "UsbDeviceManager",
                        " Error while communicating with RestrictionPolicy : ",
                        e);
                return true;
            }
        }

        public static boolean isUsbDataTransferActive(long j) {
            return ((4 & j) == 0 && (j & 16) == 0) ? false : true;
        }

        public final void broadcastUsbAccessoryHandshake() {
            Intent putExtra =
                    new Intent("android.hardware.usb.action.USB_ACCESSORY_HANDSHAKE")
                            .addFlags(285212672)
                            .putExtra(
                                    "android.hardware.usb.extra.ACCESSORY_UEVENT_TIME",
                                    this.mAccessoryConnectionStartTime)
                            .putExtra(
                                    "android.hardware.usb.extra.ACCESSORY_STRING_COUNT",
                                    this.mSendStringCount)
                            .putExtra(
                                    "android.hardware.usb.extra.ACCESSORY_START",
                                    this.mStartAccessory)
                            .putExtra(
                                    "android.hardware.usb.extra.ACCESSORY_HANDSHAKE_END",
                                    SystemClock.elapsedRealtime());
            Set set = UsbDeviceManager.sDenyInterfaces;
            Slog.d(
                    "UsbDeviceManager",
                    "broadcasting " + putExtra + " extras: " + putExtra.getExtras());
            sendStickyBroadcast(putExtra);
            this.mAccessoryConnectionStartTime = 0L;
            this.mSendStringCount = 0;
            this.mStartAccessory = false;
        }

        public final void finishBoot(int i) {
            UsbProfileGroupSettingsManager usbProfileGroupSettingsManager;
            Set set = UsbDeviceManager.sDenyInterfaces;
            Slog.d("UsbDeviceManager", "finishBoot");
            if (this.mBootCompleted && this.mCurrentUsbFunctionsReceived && this.mSystemReady) {
                if (this.mPendingBootBroadcast) {
                    updateUsbStateBroadcastIfNeeded(
                            getAppliedFunctions(this.mSecCurrentFunctions), false);
                    this.mPendingBootBroadcast = false;
                }
                if (this.mScreenLocked || this.mScreenUnlockedFunctions == 0) {
                    setEnabledFunctions(
                            i, UsbDeviceManager.m1015$$Nest$smgetDefaultUsbValue(), false);
                } else {
                    Slog.d(
                            "UsbDeviceManager",
                            "finishBoot mScreenUnlockedFunctions="
                                    + UsbManager.usbFunctionsToString(
                                            this.mScreenUnlockedFunctions));
                    setScreenUnlockedFunctions(i);
                }
                if (this.mCurrentAccessory != null) {
                    UsbDeviceManager usbDeviceManager = this.mUsbDeviceManager;
                    synchronized (usbDeviceManager.mLock) {
                        usbProfileGroupSettingsManager = usbDeviceManager.mCurrentSettings;
                    }
                    usbProfileGroupSettingsManager.accessoryAttached(this.mCurrentAccessory);
                    broadcastUsbAccessoryHandshake();
                } else if (this.mPendingBootAccessoryHandshakeBroadcast) {
                    broadcastUsbAccessoryHandshake();
                }
                this.mPendingBootAccessoryHandshakeBroadcast = false;
                updateUsbNotification(false);
                updateUsbFunctions();
            }
        }

        public final void getMidiCardDevice() {
            final int i = 1;
            final int i2 = 0;
            String str = SystemProperties.get("sys.usb.controller", "");
            if (TextUtils.isEmpty(str)) {
                throw new FileNotFoundException("controller name not found");
            }
            File file =
                    new File(
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    "/sys/class/udc/", str, "/gadget/sound"));
            if (!file.exists()) {
                throw new FileNotFoundException("sound device not found");
            }
            File[] listFilesOrEmpty =
                    FileUtils.listFilesOrEmpty(
                            file,
                            new FilenameFilter() { // from class:
                                                   // com.android.server.usb.UsbDeviceManager$UsbHandler$$ExternalSyntheticLambda0
                                @Override // java.io.FilenameFilter
                                public final boolean accept(File file2, String str2) {
                                    switch (i2) {
                                        case 0:
                                            return str2.startsWith("card");
                                        default:
                                            return str2.startsWith("midi");
                                    }
                                }
                            });
            if (listFilesOrEmpty.length != 1) {
                throw new FileNotFoundException("sound card not match");
            }
            File[] listFilesOrEmpty2 =
                    FileUtils.listFilesOrEmpty(
                            listFilesOrEmpty[0],
                            new FilenameFilter() { // from class:
                                                   // com.android.server.usb.UsbDeviceManager$UsbHandler$$ExternalSyntheticLambda0
                                @Override // java.io.FilenameFilter
                                public final boolean accept(File file2, String str2) {
                                    switch (i) {
                                        case 0:
                                            return str2.startsWith("card");
                                        default:
                                            return str2.startsWith("midi");
                                    }
                                }
                            });
            if (listFilesOrEmpty2.length != 1) {
                throw new FileNotFoundException("MIDI device not match");
            }
            Matcher matcher =
                    Pattern.compile("midiC(\\d+)D(\\d+)").matcher(listFilesOrEmpty2[0].getName());
            if (!matcher.matches()) {
                throw new FileNotFoundException("MIDI name not match");
            }
            this.mMidiCard = Integer.parseInt(matcher.group(1));
            this.mMidiDevice = Integer.parseInt(matcher.group(2));
            Set set = UsbDeviceManager.sDenyInterfaces;
            Slog.i(
                    "UsbDeviceManager",
                    "Found MIDI card " + this.mMidiCard + " device " + this.mMidiDevice);
        }

        public abstract void getUsbSpeedCb(int i);

        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void handleMessage(android.os.Message r25) {
            /*
                Method dump skipped, instructions count: 2110
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.usb.UsbDeviceManager.UsbHandler.handleMessage(android.os.Message):void");
        }

        public abstract void handlerInitDone(int i);

        public final boolean isUsbTransferAllowed() {
            return !((UserManager) this.mContext.getSystemService("user"))
                    .hasUserRestriction("no_usb_file_transfer");
        }

        public final void notifyAccessoryModeExit(int i) {
            Set set = UsbDeviceManager.sDenyInterfaces;
            Slog.d("UsbDeviceManager", "exited USB accessory mode");
            setEnabledFunctions(i, UsbDeviceManager.m1015$$Nest$smgetDefaultUsbValue(), false);
            UsbAccessory usbAccessory = this.mCurrentAccessory;
            if (usbAccessory != null) {
                if (this.mBootCompleted) {
                    UsbPermissionManager usbPermissionManager = this.mPermissionManager;
                    synchronized (usbPermissionManager.mPermissionsByUser) {
                        for (int i2 = 0;
                                i2 < usbPermissionManager.mPermissionsByUser.size();
                                i2++) {
                            try {
                                UsbUserPermissionManager usbUserPermissionManager =
                                        (UsbUserPermissionManager)
                                                usbPermissionManager.mPermissionsByUser.valueAt(i2);
                                synchronized (usbUserPermissionManager.mLock) {
                                    usbUserPermissionManager.mAccessoryPermissionMap.remove(
                                            usbAccessory);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                    Intent intent =
                            new Intent("android.hardware.usb.action.USB_ACCESSORY_DETACHED");
                    intent.addFlags(16777216);
                    intent.putExtra("accessory", usbAccessory);
                    usbPermissionManager.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                }
                this.mCurrentAccessory = null;
            }
        }

        public abstract void resetCb(int i);

        public final void sendMessage(int i, Object obj) {
            removeMessages(i);
            Message obtain = Message.obtain(this, i);
            obtain.obj = obj;
            sendMessage(obtain);
        }

        public final void sendStickyBroadcast(Intent intent) {
            this.mContext.sendStickyBroadcastAsUser(intent, UserHandle.ALL);
            UsbDeviceManager.sEventLogger.enqueue(
                    new EventLogger.StringEvent("USB intent: " + intent));
        }

        public abstract void setCurrentUsbFunctionsCb(int i, long j, long j2, int i2, boolean z);

        public abstract void setEnabledFunctions(int i, long j, boolean z);

        public final void setScreenUnlockedFunctions(int i) {
            Set set = UsbDeviceManager.sDenyInterfaces;
            Slog.d(
                    "UsbDeviceManager",
                    "setScreenUnlockedFunctions: mScreenUnlockedFunctions="
                            + UsbManager.usbFunctionsToString(this.mScreenUnlockedFunctions));
            setEnabledFunctions(i, this.mScreenUnlockedFunctions, false);
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateState(java.lang.String r9) {
            /*
                r8 = this;
                java.lang.String r0 = "DISCONNECTED"
                boolean r0 = r0.equals(r9)
                java.lang.String r1 = "sys.usb.configured"
                java.lang.String r2 = "UsbDeviceManager"
                r3 = 1
                r4 = 0
                if (r0 == 0) goto L18
                java.lang.String r9 = "none"
                android.os.SystemProperties.set(r1, r9)
                r9 = r4
            L16:
                r0 = r9
                goto L32
            L18:
                java.lang.String r0 = "CONNECTED"
                boolean r0 = r0.equals(r9)
                if (r0 == 0) goto L23
                r9 = r3
                r0 = r4
                goto L32
            L23:
                java.lang.String r0 = "CONFIGURED"
                boolean r0 = r0.equals(r9)
                if (r0 == 0) goto L96
                java.lang.String r9 = "configured"
                android.os.SystemProperties.set(r1, r9)
                r9 = r3
                goto L16
            L32:
                r1 = 17
                if (r9 != r3) goto L39
                r8.removeMessages(r1)
            L39:
                android.os.Message r5 = android.os.Message.obtain(r8, r4)
                r5.arg1 = r9
                r5.arg2 = r0
                java.util.Set r6 = com.android.server.usb.UsbDeviceManager.sDenyInterfaces
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                java.lang.String r7 = "mResetUsbGadgetDisableDebounce:"
                r6.<init>(r7)
                boolean r7 = r8.mResetUsbGadgetDisableDebounce
                r6.append(r7)
                java.lang.String r7 = " connected:"
                r6.append(r7)
                r6.append(r9)
                java.lang.String r7 = "configured:"
                r6.append(r7)
                r6.append(r0)
                java.lang.String r6 = r6.toString()
                android.util.sysfwutil.Slog.i(r2, r6)
                boolean r6 = r8.mResetUsbGadgetDisableDebounce
                if (r6 == 0) goto L73
                r8.sendMessage(r5)
                if (r9 != r3) goto L95
                r8.mResetUsbGadgetDisableDebounce = r4
                goto L95
            L73:
                if (r0 != 0) goto L7e
                r8.removeMessages(r4)
                java.lang.String r0 = "removeMessages MSG_UPDATE_STATE"
                android.util.sysfwutil.Slog.i(r2, r0)
            L7e:
                if (r9 != r3) goto L83
                r8.removeMessages(r1)
            L83:
                if (r9 != 0) goto L90
                boolean r9 = r8.mScreenLocked
                if (r9 == 0) goto L8c
                r9 = 1000(0x3e8, float:1.401E-42)
                goto L8e
            L8c:
                r9 = 3000(0xbb8, float:4.204E-42)
            L8e:
                long r0 = (long) r9
                goto L92
            L90:
                r0 = 0
            L92:
                r8.sendMessageDelayed(r5, r0)
            L95:
                return
            L96:
                java.util.Set r8 = com.android.server.usb.UsbDeviceManager.sDenyInterfaces
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                java.lang.String r0 = "unknown state "
                r8.<init>(r0)
                r8.append(r9)
                java.lang.String r8 = r8.toString()
                android.util.sysfwutil.Slog.e(r2, r8)
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.usb.UsbDeviceManager.UsbHandler.updateState(java.lang.String):void");
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateUsbFunctions() {
            /*
                Method dump skipped, instructions count: 360
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.usb.UsbDeviceManager.UsbHandler.updateUsbFunctions():void");
        }

        /* JADX WARN: Code restructure failed: missing block: B:85:0x014b, code lost:

           if (r7 == 0) goto L70;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateUsbNotification(boolean r24) {
            /*
                Method dump skipped, instructions count: 703
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.usb.UsbDeviceManager.UsbHandler.updateUsbNotification(boolean):void");
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateUsbStateBroadcastIfNeeded(long r12, boolean r14) {
            /*
                Method dump skipped, instructions count: 338
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.usb.UsbDeviceManager.UsbHandler.updateUsbStateBroadcastIfNeeded(long,"
                        + " boolean):void");
        }
    }

    public final class UsbHandlerHal extends UsbHandler {
        public int mCurrentRequest;
        public boolean mCurrentUsbFunctionsRequested;
        public final Object mGadgetProxyLock;

        public UsbHandlerHal(
                Looper looper,
                Context context,
                UsbDeviceManager usbDeviceManager,
                UsbAlsaManager usbAlsaManager,
                UsbPermissionManager usbPermissionManager) {
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
                    sendMessage(23, null);
                }
                updateState(
                        FileUtils.readTextFile(
                                        new File("/sys/class/android_usb/android0/state"), 0, null)
                                .trim());
            } catch (NoSuchElementException e) {
                Set set = UsbDeviceManager.sDenyInterfaces;
                Slog.e("UsbDeviceManager", "Usb gadget hal not found", e);
            } catch (Exception e2) {
                Set set2 = UsbDeviceManager.sDenyInterfaces;
                Slog.e("UsbDeviceManager", "Error initializing UsbHandler", e2);
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public final void getUsbSpeedCb(int i) {
            this.mUsbSpeed = i;
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler, android.os.Handler
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 14:
                    setEnabledFunctions(
                            UsbDeviceManager.sUsbOperationCount.incrementAndGet(), 0L, false);
                    return;
                case 15:
                    int incrementAndGet = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    Slog.e(
                            "UsbDeviceManager",
                            "Set functions timed out! no reply from usb hal ,operationId:"
                                    + incrementAndGet);
                    if (message.arg1 != 1) {
                        setEnabledFunctions(incrementAndGet, this.mScreenUnlockedFunctions, false);
                        return;
                    }
                    return;
                case 16:
                    Set set = UsbDeviceManager.sDenyInterfaces;
                    Slog.i("UsbDeviceManager", "processing MSG_GET_CURRENT_USB_FUNCTIONS");
                    this.mCurrentUsbFunctionsReceived = true;
                    int i = message.arg2;
                    if (this.mCurrentUsbFunctionsRequested) {
                        Slog.i("UsbDeviceManager", "updating mCurrentFunctions");
                        this.mCurrentFunctions = ((Long) message.obj).longValue() & (-2);
                        Slog.i(
                                "UsbDeviceManager",
                                "mCurrentFunctions:"
                                        + this.mCurrentFunctions
                                        + "applied:"
                                        + message.arg1);
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
                            setEnabledFunctions(
                                    incrementAndGet2, this.mScreenUnlockedFunctions, false);
                            return;
                        }
                    }
                    return;
                case 18:
                    boolean z = message.arg1 == 1;
                    int incrementAndGet3 = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    synchronized (this.mGadgetProxyLock) {
                        try {
                            UsbDeviceManager.mUsbGadgetHal =
                                    UsbGadgetHalInstance.getInstance(this.mUsbDeviceManager);
                            if (!this.mCurrentFunctionsApplied && !z) {
                                setEnabledFunctions(
                                        incrementAndGet3, this.mCurrentFunctions, false);
                            }
                        } catch (NoSuchElementException e) {
                            Set set2 = UsbDeviceManager.sDenyInterfaces;
                            Slog.e("UsbDeviceManager", "Usb gadget hal not found", e);
                        }
                    }
                    return;
                case 19:
                    int incrementAndGet4 = UsbDeviceManager.sUsbOperationCount.incrementAndGet();
                    synchronized (this.mGadgetProxyLock) {
                        if (UsbDeviceManager.mUsbGadgetHal == null) {
                            Slog.e("UsbDeviceManager", "reset Usb Gadget mUsbGadgetHal is null");
                            return;
                        }
                        try {
                            removeMessages(8);
                            if (this.mConfigured) {
                                this.mResetUsbGadgetDisableDebounce = true;
                            }
                            UsbDeviceManager.mUsbGadgetHal.reset(incrementAndGet4);
                        } catch (Exception e2) {
                            Set set3 = UsbDeviceManager.sDenyInterfaces;
                            Slog.e("UsbDeviceManager", "reset Usb Gadget failed", e2);
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
                    UsbGadgetHal usbGadgetHal = UsbDeviceManager.mUsbGadgetHal;
                    if (usbGadgetHal == null) {
                        Slog.e(
                                "UsbDeviceManager",
                                "mGadgetHal is null, operationId:" + incrementAndGet5);
                        return;
                    } else {
                        try {
                            usbGadgetHal.getUsbSpeed(incrementAndGet5);
                            return;
                        } catch (Exception e3) {
                            Set set4 = UsbDeviceManager.sDenyInterfaces;
                            Slog.e("UsbDeviceManager", "get UsbSpeed failed", e3);
                            return;
                        }
                    }
                case 23:
                    UsbGadgetHal usbGadgetHal2 = UsbDeviceManager.mUsbGadgetHal;
                    if (usbGadgetHal2 == null) {
                        Slog.e("UsbDeviceManager", "mUsbGadgetHal is null");
                        return;
                    }
                    try {
                        this.mCurrentGadgetHalVersion = usbGadgetHal2.getGadgetHalVersion();
                        return;
                    } catch (RemoteException e4) {
                        Set set5 = UsbDeviceManager.sDenyInterfaces;
                        Slog.e("UsbDeviceManager", "update Usb gadget version failed", e4);
                        return;
                    }
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public final void handlerInitDone(int i) {
            UsbDeviceManager.mUsbGadgetHal.getCurrentUsbFunctions(i);
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public final void resetCb(int i) {
            if (i != 0) {
                Set set = UsbDeviceManager.sDenyInterfaces;
                Slog.e("UsbDeviceManager", "resetCb fail");
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public final void setCurrentUsbFunctionsCb(int i, long j, long j2, int i2, boolean z) {
            if (this.mCurrentRequest == i2 && hasMessages(15) && j2 == j) {
                removeMessages(15);
                Set set = UsbDeviceManager.sDenyInterfaces;
                Slog.i("UsbDeviceManager", "notifyCurrentFunction request:" + i2 + " status:" + i);
                if (i == 0) {
                    this.mCurrentFunctionsApplied = true;
                } else {
                    if (z) {
                        return;
                    }
                    Slog.e("UsbDeviceManager", "Setting default fuctions");
                    sendEmptyMessage(14);
                }
            }
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public final void setEnabledFunctions(int i, long j, boolean z) {
            Object obj;
            Set set = UsbDeviceManager.sDenyInterfaces;
            Slog.d(
                    "UsbDeviceManager",
                    "setEnabledFunctionsi functions="
                            + j
                            + ", forceRestart="
                            + z
                            + ", operationId="
                            + i);
            if (this.mCurrentGadgetHalVersion < 12 && (1024 & j) != 0) {
                Slog.e("UsbDeviceManager", "Could not set unsupported function for the GadgetHal");
                return;
            }
            if (this.mCurrentFunctions == j && this.mCurrentFunctionsApplied && !z) {
                return;
            }
            Slog.i(
                    "UsbDeviceManager",
                    "Setting USB config to " + UsbManager.usbFunctionsToString(j));
            this.mCurrentFunctions = j;
            Object obj2 = null;
            this.mCurrentFunctionsApplied = false;
            this.mCurrentUsbFunctionsRequested = false;
            int i2 = j == 0 ? 1 : 0;
            long appliedFunctions = UsbHandler.getAppliedFunctions(j);
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "setUsbConfig(", appliedFunctions, ") request:");
            int i3 = this.mCurrentRequest + 1;
            this.mCurrentRequest = i3;
            m.append(i3);
            Slog.d("UsbDeviceManager", m.toString());
            removeMessages(17);
            removeMessages(15);
            removeMessages(14);
            Object obj3 = this.mGadgetProxyLock;
            synchronized (obj3) {
                try {
                    try {
                        if (UsbDeviceManager.mUsbGadgetHal == null) {
                            Slog.e("UsbDeviceManager", "setUsbConfig mUsbGadgetHal is null");
                        } else {
                            try {
                                if ((1 & appliedFunctions) != 0) {
                                    ((AdbManagerInternal)
                                                    LocalServices.getService(
                                                            AdbManagerInternal.class))
                                            .startAdbdForTransport((byte) 0);
                                } else {
                                    ((AdbManagerInternal)
                                                    LocalServices.getService(
                                                            AdbManagerInternal.class))
                                            .stopAdbdForTransport((byte) 0);
                                }
                                obj = obj3;
                                try {
                                    UsbDeviceManager.mUsbGadgetHal.setCurrentUsbFunctions(
                                            this.mCurrentRequest, appliedFunctions, i, i2);
                                    removeMessages(15);
                                    Message obtain = Message.obtain(this, 15);
                                    obtain.arg1 = i2;
                                    sendMessageDelayed(obtain, 3000L);
                                    if (this.mConnected) {
                                        removeMessages(17);
                                        Message obtain2 = Message.obtain(this, 17);
                                        obtain2.arg1 = i2;
                                        sendMessageDelayed(obtain2, 5000L);
                                    }
                                    Slog.d("UsbDeviceManager", "timeout message queued");
                                } catch (Exception e) {
                                    e = e;
                                    Set set2 = UsbDeviceManager.sDenyInterfaces;
                                    Slog.e(
                                            "UsbDeviceManager",
                                            "Remoteexception while calling setCurrentUsbFunctions",
                                            e);
                                    if (this.mBootCompleted) {
                                        return;
                                    } else {
                                        return;
                                    }
                                }
                            } catch (Exception e2) {
                                e = e2;
                                obj = obj3;
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        obj2 = obj3;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
            if (this.mBootCompleted || !UsbHandler.isUsbDataTransferActive(appliedFunctions)) {
                return;
            }
            updateUsbStateBroadcastIfNeeded(appliedFunctions, true);
        }
    }

    public final class UsbHandlerLegacy extends UsbHandler {
        public String mCurrentFunctionsStr;
        public String mCurrentOemFunctions;
        public HashMap mOemModeMap;
        public boolean mUsbDataUnlocked;

        public static String addFunction(String str) {
            if ("none".equals(str)) {
                return "adb";
            }
            if (containsFunction(str, "adb")) {
                return str;
            }
            if (str.length() > 0) {
                str = str.concat(",");
            }
            return ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "adb");
        }

        public static boolean containsFunction(String str, String str2) {
            int indexOf = str.indexOf(str2);
            if (indexOf < 0) {
                return false;
            }
            if (indexOf > 0 && str.charAt(indexOf - 1) != ',') {
                return false;
            }
            int length = str2.length() + indexOf;
            return length >= str.length() || str.charAt(length) == ',';
        }

        public static String removeFunction(String str) {
            String[] split = str.split(",");
            for (int i = 0; i < split.length; i++) {
                if ("adb".equals(split[i])) {
                    split[i] = null;
                }
            }
            if (split.length == 1 && split[0] == null) {
                return "none";
            }
            StringBuilder sb = new StringBuilder();
            for (String str2 : split) {
                if (str2 != null) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(str2);
                }
            }
            return sb.toString();
        }

        public static void setUsbConfig(String str) {
            Set set = UsbDeviceManager.sDenyInterfaces;
            Slog.d("UsbDeviceManager", "setUsbConfig(" + str + ")");
            SystemProperties.set("sys.usb.config", str);
        }

        public static boolean waitForState(String str) {
            int i =
                    (containsFunction(str, "acm")
                                    || containsFunction(str, "dm")
                                    || containsFunction(str, "adb"))
                            ? 40
                            : 30;
            String str2 = null;
            for (int i2 = 0; i2 < i; i2++) {
                str2 = SystemProperties.get("sys.usb.state", "");
                if (str.equals(str2)) {
                    return true;
                }
                SystemClock.sleep(50L);
            }
            Set set = UsbDeviceManager.sDenyInterfaces;
            StringBuilder m =
                    InitialConfiguration$$ExternalSyntheticOutline0.m(
                            "waitForState(", str, ") FAILED: got ", str2, ", waitLoops ");
            m.append(i);
            Slog.e("UsbDeviceManager", m.toString());
            return false;
        }

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public final void getUsbSpeedCb(int i) {}

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public final void handlerInitDone(int i) {}

        public final void readOemUsbOverrideConfig(Context context) {
            String[] stringArray = context.getResources().getStringArray(17236278);
            if (stringArray != null) {
                for (String str : stringArray) {
                    String[] split = str.split(":");
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

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public final void resetCb(int i) {}

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public final void setCurrentUsbFunctionsCb(int i, long j, long j2, int i2, boolean z) {}

        @Override // com.android.server.usb.UsbDeviceManager.UsbHandler
        public final void setEnabledFunctions(int i, long j, boolean z) {
            boolean isUsbDataTransferActive = UsbHandler.isUsbDataTransferActive(j);
            Set set = UsbDeviceManager.sDenyInterfaces;
            Slog.d(
                    "UsbDeviceManager",
                    "setEnabledFunctions functions="
                            + j
                            + " ,forceRestart="
                            + z
                            + " ,usbDataUnlocked="
                            + isUsbDataTransferActive
                            + " ,operationId="
                            + i);
            if (isUsbDataTransferActive != this.mUsbDataUnlocked
                    && !FactoryTest.isFactoryBinary()
                    && !UsbDeviceManager.isMassTestEnabled()) {
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
                Slog.e("UsbDeviceManager", "Failsafe 1: Restoring previous USB functions.");
                if (trySetEnabledFunctions(j2, false)) {
                    return;
                }
            }
            Slog.e("UsbDeviceManager", "Failsafe 2: Restoring default USB functions.");
            if (trySetEnabledFunctions(
                    UsbDeviceManager.m1015$$Nest$smgetDefaultUsbValue(), false)) {
                return;
            }
            Slog.e(
                    "UsbDeviceManager",
                    "Failsafe 3: Restoring empty function list (with ADB if enabled).");
            if (trySetEnabledFunctions(0L, false)) {
                return;
            }
            Slog.e("UsbDeviceManager", "Unable to set any USB functions!");
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x006b, code lost:

           if ((com.android.server.usb.UsbDeviceManager.UsbHandler.isAdbEnabled() ? addFunction(r0) : removeFunction(r0)).equals("none") != false) goto L19;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean trySetEnabledFunctions(long r19, boolean r21) {
            /*
                Method dump skipped, instructions count: 688
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.usb.UsbDeviceManager.UsbHandlerLegacy.trySetEnabledFunctions(long,"
                        + " boolean):boolean");
        }
    }

    public final class UsbUEventObserver extends UEventObserver {
        public UsbUEventObserver() {}

        public final void onUEvent(UEventObserver.UEvent uEvent) {
            Set set = UsbDeviceManager.sDenyInterfaces;
            Slog.v("UsbDeviceManager", "USB UEVENT: " + uEvent.toString());
            EventLogger eventLogger = UsbDeviceManager.sEventLogger;
            if (eventLogger != null) {
                eventLogger.enqueue(
                        new EventLogger.StringEvent("USB UEVENT: " + uEvent.toString()));
            } else {
                Slog.d("UsbDeviceManager", "sEventLogger == null");
            }
            String str = uEvent.get("USB_STATE");
            String str2 = uEvent.get("ACCESSORY");
            if (str != null) {
                UsbDeviceManager.this.mHandler.updateState(str);
                return;
            }
            if ("GETPROTOCOL".equals(str2)) {
                Slog.d("UsbDeviceManager", "got accessory get protocol");
                UsbDeviceManager.this.mHandler.mAccessoryConnectionStartTime =
                        SystemClock.elapsedRealtime();
                UsbDeviceManager.m1014$$Nest$mresetAccessoryHandshakeTimeoutHandler(
                        UsbDeviceManager.this);
                return;
            }
            if ("SENDSTRING".equals(str2)) {
                Slog.d("UsbDeviceManager", "got accessory send string");
                UsbDeviceManager.this.mHandler.sendEmptyMessage(21);
                UsbDeviceManager.m1014$$Nest$mresetAccessoryHandshakeTimeoutHandler(
                        UsbDeviceManager.this);
            } else if ("START".equals(str2)) {
                Slog.d("UsbDeviceManager", "got accessory start");
                UsbDeviceManager.this.mHandler.removeMessages(20);
                UsbDeviceManager usbDeviceManager = UsbDeviceManager.this;
                usbDeviceManager.mHandler.mStartAccessory = true;
                usbDeviceManager.startAccessoryMode();
            }
        }
    }

    /* renamed from: -$$Nest$mresetAccessoryHandshakeTimeoutHandler, reason: not valid java name */
    public static void m1014$$Nest$mresetAccessoryHandshakeTimeoutHandler(
            UsbDeviceManager usbDeviceManager) {
        UsbHandler usbHandler = usbDeviceManager.mHandler;
        if ((usbHandler.mCurrentFunctions & 2) == 0) {
            usbHandler.removeMessages(20);
            usbHandler.sendMessageDelayed(usbHandler.obtainMessage(20), 10000L);
        }
    }

    /* renamed from: -$$Nest$smgetDefaultUsbValue, reason: not valid java name */
    public static long m1015$$Nest$smgetDefaultUsbValue() {
        int i = 0;
        try {
            IKnoxCustomManager asInterface =
                    IKnoxCustomManager.Stub.asInterface(
                            ServiceManager.getService(
                                    KnoxCustomManagerService.KNOX_CUSTOM_MANAGER_SERVICE));
            if (asInterface != null) {
                i = asInterface.getUsbConnectionTypeInternal();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (i == 1) {
            Slog.d("UsbDeviceManager", "getDefaultUsbValue :: knoxUsb returns MTP");
            return 4L;
        }
        if (i == 2) {
            Slog.d("UsbDeviceManager", "getDefaultUsbValue :: knoxUsb returns PTP");
            return 16L;
        }
        if (i == 3) {
            Slog.d("UsbDeviceManager", "getDefaultUsbValue :: knoxUsb returns MIDI");
            return 8L;
        }
        if (i == 4) {
            Slog.d("UsbDeviceManager", "getDefaultUsbValue :: knoxUsb returns CHARGING");
            return 262144L;
        }
        if (i == 5) {
            Slog.d("UsbDeviceManager", "getDefaultUsbValue :: knoxUsb returns TETHERING");
            return 32L;
        }
        String persistProp = getPersistProp();
        String str = SystemProperties.get(persistProp, "none");
        if (persistProp.equals("persist.sys.usb.q_config")) {
            return UsbManager.usbFunctionsFromString(str);
        }
        if (FactoryTest.isFactoryBinary() || isMassTestEnabled()) {
            Slog.d(
                    "UsbDeviceManager",
                    "Prevent temporary usb disconnection for Factory Binary or JIG connection");
            str = "mtp,adb";
        }
        return UsbManager.usbFunctionsFromString(str);
    }

    static {
        HashSet hashSet = new HashSet();
        sDenyInterfaces = hashSet;
        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(3);
        hashSet.add(7);
        hashSet.add(8);
        hashSet.add(9);
        hashSet.add(10);
        hashSet.add(11);
        hashSet.add(13);
        hashSet.add(14);
        hashSet.add(224);
        mSupportDualRole = false;
        mSetNextUsbModeToDefault = false;
        usbDisableSettingVal = "OFF";
        mEnableUsbHiddenMenu = false;
    }

    public UsbDeviceManager(
            Context context,
            UsbAlsaManager usbAlsaManager,
            UsbPermissionManager usbPermissionManager,
            DexObserver dexObserver) {
        this.mContext = context;
        context.getContentResolver();
        this.mHasUsbAccessory =
                context.getPackageManager().hasSystemFeature("android.hardware.usb.accessory");
        int[] iArr = new int[6];
        iArr[0] = 2;
        String str = SystemProperties.get("ro.serialno", "1234567890ABCDEF");
        int length = str.length();
        for (int i = 0; i < length; i++) {
            int i2 = (i % 5) + 1;
            iArr[i2] = iArr[i2] ^ str.charAt(i);
        }
        try {
            FileUtils.stringToFile(
                    "/sys/class/android_usb/android0/f_rndis/ethaddr",
                    String.format(
                            Locale.US,
                            "%02X:%02X:%02X:%02X:%02X:%02X",
                            Integer.valueOf(iArr[0]),
                            Integer.valueOf(iArr[1]),
                            Integer.valueOf(iArr[2]),
                            Integer.valueOf(iArr[3]),
                            Integer.valueOf(iArr[4]),
                            Integer.valueOf(iArr[5])));
        } catch (IOException unused) {
            Slog.i(
                    "UsbDeviceManager",
                    "failed to write to /sys/class/android_usb/android0/f_rndis/ethaddr");
        }
        int incrementAndGet = sUsbOperationCount.incrementAndGet();
        mUsbGadgetHal = UsbGadgetHalInstance.getInstance(this);
        Slog.d("UsbDeviceManager", "getInstance done");
        HashMap hashMap = new HashMap();
        this.mControlFds = hashMap;
        FileDescriptor nativeOpenControl = nativeOpenControl("mtp");
        if (nativeOpenControl == null) {
            Slog.e("UsbDeviceManager", "Failed to open control for mtp");
        }
        hashMap.put(4L, nativeOpenControl);
        FileDescriptor nativeOpenControl2 = nativeOpenControl("ptp");
        if (nativeOpenControl2 == null) {
            Slog.e("UsbDeviceManager", "Failed to open control for ptp");
        }
        hashMap.put(16L, nativeOpenControl2);
        if (mUsbGadgetHal == null) {
            Looper looper = FgThread.get().getLooper();
            Context context2 = this.mContext;
            UsbHandlerLegacy usbHandlerLegacy =
                    new UsbHandlerLegacy(
                            looper, context2, this, usbAlsaManager, usbPermissionManager);
            try {
                usbHandlerLegacy.readOemUsbOverrideConfig(context2);
                usbHandlerLegacy.mCurrentOemFunctions =
                        SystemProperties.get(getPersistProp(), "none");
                String str2 = SystemProperties.get("ro.bootmode", "unknown");
                if (str2.equals("normal") || str2.equals("unknown")) {
                    long usbFunctionsFromString =
                            UsbManager.usbFunctionsFromString(
                                    SystemProperties.get("sys.usb.config", "none"));
                    usbHandlerLegacy.mCurrentFunctions = usbFunctionsFromString;
                    usbHandlerLegacy.mSecCurrentFunctions = usbFunctionsFromString;
                    if (!FactoryTest.isFactoryBinary()) {
                        usbHandlerLegacy.mCurrentFunctions =
                                (-2) & usbHandlerLegacy.mCurrentFunctions;
                    }
                    String str3 = SystemProperties.get("sys.usb.config", "none");
                    usbHandlerLegacy.mCurrentFunctionsStr = str3;
                    usbHandlerLegacy.mCurrentFunctionsApplied =
                            str3.equals(SystemProperties.get("sys.usb.state", "none"));
                } else {
                    long usbFunctionsFromString2 =
                            UsbManager.usbFunctionsFromString(
                                    SystemProperties.get(getPersistProp(), "none"));
                    usbHandlerLegacy.mCurrentFunctions = usbFunctionsFromString2;
                    usbHandlerLegacy.mSecCurrentFunctions = usbFunctionsFromString2;
                    if (!FactoryTest.isFactoryBinary()) {
                        usbHandlerLegacy.mCurrentFunctions =
                                (-2) & usbHandlerLegacy.mCurrentFunctions;
                    }
                    usbHandlerLegacy.mCurrentFunctionsStr =
                            SystemProperties.get(getPersistProp(), "none");
                    usbHandlerLegacy.mCurrentFunctionsApplied =
                            SystemProperties.get("sys.usb.config", "none")
                                    .equals(SystemProperties.get("sys.usb.state", "none"));
                }
                usbHandlerLegacy.mCurrentUsbFunctionsReceived = true;
                usbHandlerLegacy.mUsbSpeed = -1;
                usbHandlerLegacy.mCurrentGadgetHalVersion = -1;
                SystemProperties.get("sys.usb.configured", "none");
                String trim =
                        FileUtils.readTextFile(
                                        new File("/sys/class/android_usb/android0/state"), 0, null)
                                .trim();
                Slog.d(
                        "UsbDeviceManager",
                        "Init mCurrentFunctions="
                                + UsbManager.usbFunctionsToString(
                                        usbHandlerLegacy.mCurrentFunctions)
                                + " state="
                                + trim);
                usbHandlerLegacy.updateState(trim);
            } catch (Exception e) {
                Slog.e("UsbDeviceManager", "Error initializing UsbHandler", e);
            }
            this.mHandler = usbHandlerLegacy;
        } else {
            this.mHandler =
                    new UsbHandlerHal(
                            FgThread.get().getLooper(),
                            this.mContext,
                            this,
                            usbAlsaManager,
                            usbPermissionManager);
        }
        this.mHandler.handlerInitDone(incrementAndGet);
        if (nativeIsStartRequested()) {
            Slog.d("UsbDeviceManager", "accessory attached at boot");
            startAccessoryMode();
        }
        final int i3 = 0;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.usb.UsbDeviceManager.3
                    public final /* synthetic */ UsbDeviceManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context3, Intent intent) {
                        switch (i3) {
                            case 0:
                                Set set = UsbDeviceManager.sDenyInterfaces;
                                Slog.d("UsbDeviceManager", "received ACTION_USB_PORT_CHANGED");
                                ParcelableUsbPort parcelableUsbPort =
                                        (ParcelableUsbPort)
                                                intent.getParcelableExtra(
                                                        "port", ParcelableUsbPort.class);
                                UsbPortStatus usbPortStatus =
                                        (UsbPortStatus)
                                                intent.getParcelableExtra(
                                                        "portStatus", UsbPortStatus.class);
                                UsbHandler usbHandler = this.this$0.mHandler;
                                UsbPort usbPort =
                                        parcelableUsbPort.getUsbPort(
                                                (UsbManager)
                                                        context3.getSystemService(
                                                                UsbManager.class));
                                usbHandler.getClass();
                                Slog.i(
                                        "UsbDeviceManager",
                                        "updateHostState " + usbPort + " status=" + usbPortStatus);
                                SomeArgs obtain = SomeArgs.obtain();
                                obtain.arg1 = usbPort;
                                obtain.arg2 = usbPortStatus;
                                usbHandler.removeMessages(7);
                                usbHandler.sendMessageDelayed(
                                        usbHandler.obtainMessage(7, obtain), 1000L);
                                break;
                            case 1:
                                int i4 = intent.getIntExtra("plugged", -1) == 2 ? 1 : 0;
                                UsbHandler usbHandler2 = this.this$0.mHandler;
                                usbHandler2.removeMessages(9);
                                Message obtain2 = Message.obtain(usbHandler2, 9);
                                obtain2.arg1 = i4;
                                usbHandler2.sendMessage(obtain2);
                                break;
                            case 2:
                                Set set2 = UsbDeviceManager.sDenyInterfaces;
                                Slog.d(
                                        "UsbDeviceManager",
                                        "received ACTION_USB_DEVICE_ATTACHED /"
                                            + " ACTION_USB_DEVICE_DETACHED");
                                Iterator<Map.Entry<String, UsbDevice>> it =
                                        ((UsbManager) context3.getSystemService("usb"))
                                                .getDeviceList()
                                                .entrySet()
                                                .iterator();
                                if (!intent.getAction()
                                        .equals(
                                                "android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                                    UsbHandler usbHandler3 = this.this$0.mHandler;
                                    usbHandler3.removeMessages(10);
                                    Message obtain3 = Message.obtain(usbHandler3, 10);
                                    obtain3.obj = it;
                                    obtain3.arg1 = 0;
                                    usbHandler3.sendMessage(obtain3);
                                    break;
                                } else {
                                    UsbHandler usbHandler4 = this.this$0.mHandler;
                                    usbHandler4.removeMessages(10);
                                    Message obtain4 = Message.obtain(usbHandler4, 10);
                                    obtain4.obj = it;
                                    obtain4.arg1 = 1;
                                    usbHandler4.sendMessage(obtain4);
                                    break;
                                }
                            default:
                                this.this$0.mHandler.sendEmptyMessage(11);
                                break;
                        }
                    }
                };
        final int i4 = 1;
        BroadcastReceiver broadcastReceiver2 =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.usb.UsbDeviceManager.3
                    public final /* synthetic */ UsbDeviceManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context3, Intent intent) {
                        switch (i4) {
                            case 0:
                                Set set = UsbDeviceManager.sDenyInterfaces;
                                Slog.d("UsbDeviceManager", "received ACTION_USB_PORT_CHANGED");
                                ParcelableUsbPort parcelableUsbPort =
                                        (ParcelableUsbPort)
                                                intent.getParcelableExtra(
                                                        "port", ParcelableUsbPort.class);
                                UsbPortStatus usbPortStatus =
                                        (UsbPortStatus)
                                                intent.getParcelableExtra(
                                                        "portStatus", UsbPortStatus.class);
                                UsbHandler usbHandler = this.this$0.mHandler;
                                UsbPort usbPort =
                                        parcelableUsbPort.getUsbPort(
                                                (UsbManager)
                                                        context3.getSystemService(
                                                                UsbManager.class));
                                usbHandler.getClass();
                                Slog.i(
                                        "UsbDeviceManager",
                                        "updateHostState " + usbPort + " status=" + usbPortStatus);
                                SomeArgs obtain = SomeArgs.obtain();
                                obtain.arg1 = usbPort;
                                obtain.arg2 = usbPortStatus;
                                usbHandler.removeMessages(7);
                                usbHandler.sendMessageDelayed(
                                        usbHandler.obtainMessage(7, obtain), 1000L);
                                break;
                            case 1:
                                int i42 = intent.getIntExtra("plugged", -1) == 2 ? 1 : 0;
                                UsbHandler usbHandler2 = this.this$0.mHandler;
                                usbHandler2.removeMessages(9);
                                Message obtain2 = Message.obtain(usbHandler2, 9);
                                obtain2.arg1 = i42;
                                usbHandler2.sendMessage(obtain2);
                                break;
                            case 2:
                                Set set2 = UsbDeviceManager.sDenyInterfaces;
                                Slog.d(
                                        "UsbDeviceManager",
                                        "received ACTION_USB_DEVICE_ATTACHED /"
                                            + " ACTION_USB_DEVICE_DETACHED");
                                Iterator<Map.Entry<String, UsbDevice>> it =
                                        ((UsbManager) context3.getSystemService("usb"))
                                                .getDeviceList()
                                                .entrySet()
                                                .iterator();
                                if (!intent.getAction()
                                        .equals(
                                                "android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                                    UsbHandler usbHandler3 = this.this$0.mHandler;
                                    usbHandler3.removeMessages(10);
                                    Message obtain3 = Message.obtain(usbHandler3, 10);
                                    obtain3.obj = it;
                                    obtain3.arg1 = 0;
                                    usbHandler3.sendMessage(obtain3);
                                    break;
                                } else {
                                    UsbHandler usbHandler4 = this.this$0.mHandler;
                                    usbHandler4.removeMessages(10);
                                    Message obtain4 = Message.obtain(usbHandler4, 10);
                                    obtain4.obj = it;
                                    obtain4.arg1 = 1;
                                    usbHandler4.sendMessage(obtain4);
                                    break;
                                }
                            default:
                                this.this$0.mHandler.sendEmptyMessage(11);
                                break;
                        }
                    }
                };
        final int i5 = 2;
        BroadcastReceiver broadcastReceiver3 =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.usb.UsbDeviceManager.3
                    public final /* synthetic */ UsbDeviceManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context3, Intent intent) {
                        switch (i5) {
                            case 0:
                                Set set = UsbDeviceManager.sDenyInterfaces;
                                Slog.d("UsbDeviceManager", "received ACTION_USB_PORT_CHANGED");
                                ParcelableUsbPort parcelableUsbPort =
                                        (ParcelableUsbPort)
                                                intent.getParcelableExtra(
                                                        "port", ParcelableUsbPort.class);
                                UsbPortStatus usbPortStatus =
                                        (UsbPortStatus)
                                                intent.getParcelableExtra(
                                                        "portStatus", UsbPortStatus.class);
                                UsbHandler usbHandler = this.this$0.mHandler;
                                UsbPort usbPort =
                                        parcelableUsbPort.getUsbPort(
                                                (UsbManager)
                                                        context3.getSystemService(
                                                                UsbManager.class));
                                usbHandler.getClass();
                                Slog.i(
                                        "UsbDeviceManager",
                                        "updateHostState " + usbPort + " status=" + usbPortStatus);
                                SomeArgs obtain = SomeArgs.obtain();
                                obtain.arg1 = usbPort;
                                obtain.arg2 = usbPortStatus;
                                usbHandler.removeMessages(7);
                                usbHandler.sendMessageDelayed(
                                        usbHandler.obtainMessage(7, obtain), 1000L);
                                break;
                            case 1:
                                int i42 = intent.getIntExtra("plugged", -1) == 2 ? 1 : 0;
                                UsbHandler usbHandler2 = this.this$0.mHandler;
                                usbHandler2.removeMessages(9);
                                Message obtain2 = Message.obtain(usbHandler2, 9);
                                obtain2.arg1 = i42;
                                usbHandler2.sendMessage(obtain2);
                                break;
                            case 2:
                                Set set2 = UsbDeviceManager.sDenyInterfaces;
                                Slog.d(
                                        "UsbDeviceManager",
                                        "received ACTION_USB_DEVICE_ATTACHED /"
                                            + " ACTION_USB_DEVICE_DETACHED");
                                Iterator<Map.Entry<String, UsbDevice>> it =
                                        ((UsbManager) context3.getSystemService("usb"))
                                                .getDeviceList()
                                                .entrySet()
                                                .iterator();
                                if (!intent.getAction()
                                        .equals(
                                                "android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                                    UsbHandler usbHandler3 = this.this$0.mHandler;
                                    usbHandler3.removeMessages(10);
                                    Message obtain3 = Message.obtain(usbHandler3, 10);
                                    obtain3.obj = it;
                                    obtain3.arg1 = 0;
                                    usbHandler3.sendMessage(obtain3);
                                    break;
                                } else {
                                    UsbHandler usbHandler4 = this.this$0.mHandler;
                                    usbHandler4.removeMessages(10);
                                    Message obtain4 = Message.obtain(usbHandler4, 10);
                                    obtain4.obj = it;
                                    obtain4.arg1 = 1;
                                    usbHandler4.sendMessage(obtain4);
                                    break;
                                }
                            default:
                                this.this$0.mHandler.sendEmptyMessage(11);
                                break;
                        }
                    }
                };
        final int i6 = 3;
        BroadcastReceiver broadcastReceiver4 =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.usb.UsbDeviceManager.3
                    public final /* synthetic */ UsbDeviceManager this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context3, Intent intent) {
                        switch (i6) {
                            case 0:
                                Set set = UsbDeviceManager.sDenyInterfaces;
                                Slog.d("UsbDeviceManager", "received ACTION_USB_PORT_CHANGED");
                                ParcelableUsbPort parcelableUsbPort =
                                        (ParcelableUsbPort)
                                                intent.getParcelableExtra(
                                                        "port", ParcelableUsbPort.class);
                                UsbPortStatus usbPortStatus =
                                        (UsbPortStatus)
                                                intent.getParcelableExtra(
                                                        "portStatus", UsbPortStatus.class);
                                UsbHandler usbHandler = this.this$0.mHandler;
                                UsbPort usbPort =
                                        parcelableUsbPort.getUsbPort(
                                                (UsbManager)
                                                        context3.getSystemService(
                                                                UsbManager.class));
                                usbHandler.getClass();
                                Slog.i(
                                        "UsbDeviceManager",
                                        "updateHostState " + usbPort + " status=" + usbPortStatus);
                                SomeArgs obtain = SomeArgs.obtain();
                                obtain.arg1 = usbPort;
                                obtain.arg2 = usbPortStatus;
                                usbHandler.removeMessages(7);
                                usbHandler.sendMessageDelayed(
                                        usbHandler.obtainMessage(7, obtain), 1000L);
                                break;
                            case 1:
                                int i42 = intent.getIntExtra("plugged", -1) == 2 ? 1 : 0;
                                UsbHandler usbHandler2 = this.this$0.mHandler;
                                usbHandler2.removeMessages(9);
                                Message obtain2 = Message.obtain(usbHandler2, 9);
                                obtain2.arg1 = i42;
                                usbHandler2.sendMessage(obtain2);
                                break;
                            case 2:
                                Set set2 = UsbDeviceManager.sDenyInterfaces;
                                Slog.d(
                                        "UsbDeviceManager",
                                        "received ACTION_USB_DEVICE_ATTACHED /"
                                            + " ACTION_USB_DEVICE_DETACHED");
                                Iterator<Map.Entry<String, UsbDevice>> it =
                                        ((UsbManager) context3.getSystemService("usb"))
                                                .getDeviceList()
                                                .entrySet()
                                                .iterator();
                                if (!intent.getAction()
                                        .equals(
                                                "android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
                                    UsbHandler usbHandler3 = this.this$0.mHandler;
                                    usbHandler3.removeMessages(10);
                                    Message obtain3 = Message.obtain(usbHandler3, 10);
                                    obtain3.obj = it;
                                    obtain3.arg1 = 0;
                                    usbHandler3.sendMessage(obtain3);
                                    break;
                                } else {
                                    UsbHandler usbHandler4 = this.this$0.mHandler;
                                    usbHandler4.removeMessages(10);
                                    Message obtain4 = Message.obtain(usbHandler4, 10);
                                    obtain4.obj = it;
                                    obtain4.arg1 = 1;
                                    usbHandler4.sendMessage(obtain4);
                                    break;
                                }
                            default:
                                this.this$0.mHandler.sendEmptyMessage(11);
                                break;
                        }
                    }
                };
        this.mContext.registerReceiver(
                broadcastReceiver,
                new IntentFilter("android.hardware.usb.action.USB_PORT_CHANGED"));
        this.mContext.registerReceiver(
                broadcastReceiver2, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        IntentFilter intentFilter =
                new IntentFilter("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        this.mContext.registerReceiver(broadcastReceiver3, intentFilter);
        this.mContext.registerReceiver(
                broadcastReceiver4, new IntentFilter("android.intent.action.LOCALE_CHANGED"));
        UsbUEventObserver usbUEventObserver = new UsbUEventObserver();
        usbUEventObserver.startObserving("DEVPATH=/devices/virtual/android_usb/android0");
        usbUEventObserver.startObserving("DEVPATH=/devices/virtual/misc/usb_accessory");
        sEventLogger = new EventLogger(200, "UsbDeviceManager activity");
        if (dexObserver != null) {
            mDexObserver = dexObserver;
            dexObserver.addListener(this.mDexListener);
        }
        this.mContext.registerReceiver(
                this.mHostConnectionReceiver,
                new IntentFilter("com.samsung.UsbOtgCableConnection"));
        mSupportDualRole = new File("/sys/class/typec").exists();
    }

    public static String getPersistProp() {
        String str =
                SystemProperties.get("persist.sys.usb.q_config", "none").equals("none") ^ true
                        ? "persist.sys.usb.q_config"
                        : "persist.sys.usb.config";
        Slog.d("UsbDeviceManager", "getPersistProp: return=".concat(str));
        return str;
    }

    public static boolean isMassTestEnabled() {
        try {
            File file = new File("/sys/class/sec/rid_adc/rid_adc");
            if (file.exists()) {
                String trim = FileUtils.readTextFile(file, 0, null).trim();
                Slog.d("UsbDeviceManager", "isMassTestEnabled: state=" + trim);
                if (trim.equals("255K") || trim.equals("302K") || trim.equals("523K")) {
                    return true;
                }
                if (trim.equals("619K")) {
                    return true;
                }
            }
        } catch (Exception e) {
            Slog.e("UsbDeviceManager", "Could't read /sys/class/sec/rid_adc/rid_adc", e);
        }
        return false;
    }

    public static void logAndPrint(int i, IndentingPrintWriter indentingPrintWriter, String str) {
        Slog.println(i, "UsbDeviceManager", str);
        if (indentingPrintWriter != null) {
            indentingPrintWriter.println(str);
        }
    }

    public static void logAndPrintException(
            IndentingPrintWriter indentingPrintWriter, String str, Exception exc) {
        Slog.e("UsbDeviceManager", str, exc);
        if (indentingPrintWriter != null) {
            indentingPrintWriter.println(str + exc);
        }
    }

    private native String[] nativeGetAccessoryStrings();

    private native boolean nativeIsStartRequested();

    private native ParcelFileDescriptor nativeOpenAccessory();

    private native FileDescriptor nativeOpenControl(String str);

    public final void dump(DualDumpOutputStream dualDumpOutputStream) {
        long start = dualDumpOutputStream.start("device_manager", 1146756268033L);
        UsbHandler usbHandler = this.mHandler;
        if (usbHandler != null) {
            long start2 = dualDumpOutputStream.start("handler", 1146756268033L);
            UsbHandler.dumpFunctions(
                    dualDumpOutputStream,
                    "current_functions",
                    2259152797697L,
                    usbHandler.mCurrentFunctions);
            dualDumpOutputStream.write(
                    "current_functions_applied",
                    1133871366146L,
                    usbHandler.mCurrentFunctionsApplied);
            UsbHandler.dumpFunctions(
                    dualDumpOutputStream,
                    "screen_unlocked_functions",
                    2259152797699L,
                    usbHandler.mScreenUnlockedFunctions);
            dualDumpOutputStream.write("screen_locked", 1133871366148L, usbHandler.mScreenLocked);
            dualDumpOutputStream.write("connected", 1133871366149L, usbHandler.mConnected);
            dualDumpOutputStream.write("configured", 1133871366150L, usbHandler.mConfigured);
            UsbAccessory usbAccessory = usbHandler.mCurrentAccessory;
            if (usbAccessory != null) {
                DumpUtils.writeAccessory(
                        dualDumpOutputStream, "current_accessory", 1146756268039L, usbAccessory);
            }
            dualDumpOutputStream.write("host_connected", 1133871366152L, usbHandler.mHostConnected);
            dualDumpOutputStream.write("source_power", 1133871366153L, usbHandler.mSourcePower);
            dualDumpOutputStream.write("sink_power", 1133871366154L, usbHandler.mSinkPower);
            dualDumpOutputStream.write("usb_charging", 1133871366155L, usbHandler.mUsbCharging);
            dualDumpOutputStream.write(
                    "hide_usb_notification", 1133871366156L, usbHandler.mHideUsbNotification);
            dualDumpOutputStream.write(
                    "hide_usb_notification",
                    1133871366156L,
                    usbHandler.mHideUsbNotiForSecUsbSmartEP);
            dualDumpOutputStream.write(
                    "audio_accessory_connected",
                    1133871366157L,
                    usbHandler.mAudioAccessoryConnected);
            try {
                com.android.internal.util.dump.DumpUtils.writeStringIfNotNull(
                        dualDumpOutputStream,
                        "kernel_state",
                        1138166333455L,
                        FileUtils.readTextFile(
                                        new File("/sys/class/android_usb/android0/state"), 0, null)
                                .trim());
            } catch (FileNotFoundException unused) {
                Slog.w(
                        "UsbDeviceManager",
                        "Ignore missing legacy kernel path in bugreport dump: kernel"
                            + " state:/sys/class/android_usb/android0/state");
            } catch (Exception e) {
                Slog.e("UsbDeviceManager", "Could not read kernel state", e);
            }
            try {
                com.android.internal.util.dump.DumpUtils.writeStringIfNotNull(
                        dualDumpOutputStream,
                        "kernel_function_list",
                        1138166333456L,
                        FileUtils.readTextFile(
                                        new File("/sys/class/android_usb/android0/functions"),
                                        0,
                                        null)
                                .trim());
            } catch (FileNotFoundException unused2) {
                Slog.w(
                        "UsbDeviceManager",
                        "Ignore missing legacy kernel path in bugreport dump: kernel function"
                            + " list:/sys/class/android_usb/android0/functions");
            } catch (Exception e2) {
                Slog.e("UsbDeviceManager", "Could not read kernel function list", e2);
            }
            dualDumpOutputStream.end(start2);
            sEventLogger.dump(new DualOutputStreamDumpSink(dualDumpOutputStream, 1138166333457L));
        }
        dualDumpOutputStream.end(start);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public final void onAwakeStateChanged(boolean z) {}

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public final void onKeyguardStateChanged(boolean z) {
        int currentUser = ActivityManager.getCurrentUser();
        boolean isDeviceSecure =
                ((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class))
                        .isDeviceSecure(currentUser);
        StringBuilder m =
                FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                        "onKeyguardStateChanged: isShowing:",
                        z,
                        " secure:",
                        isDeviceSecure,
                        " user:");
        m.append(currentUser);
        Slog.v("UsbDeviceManager", m.toString());
        int i = (z && isDeviceSecure) ? 1 : 0;
        UsbHandler usbHandler = this.mHandler;
        usbHandler.removeMessages(13);
        Message obtain = Message.obtain(usbHandler, 13);
        obtain.arg1 = i;
        usbHandler.sendMessage(obtain);
    }

    public final ParcelFileDescriptor openAccessory(
            UsbAccessory usbAccessory,
            UsbUserPermissionManager usbUserPermissionManager,
            int i,
            int i2) {
        UsbAccessory usbAccessory2 = this.mHandler.mCurrentAccessory;
        if (usbAccessory2 == null) {
            throw new IllegalArgumentException("no accessory attached");
        }
        if (usbAccessory2.equals(usbAccessory)) {
            usbUserPermissionManager.checkPermission(usbAccessory, i, i2);
            return nativeOpenAccessory();
        }
        throw new IllegalArgumentException(
                usbAccessory.toString() + " does not match current accessory " + usbAccessory2);
    }

    public final void setCurrentFunctions(long j, int i) {
        Slog.d(
                "UsbDeviceManager",
                "setCurrentFunctions(" + UsbManager.usbFunctionsToString(j) + ")");
        List<ActivityManager.RunningTaskInfo> runningTasks =
                ((ActivityManager) this.mContext.getSystemService("activity"))
                        .getRunningTasks(Integer.MAX_VALUE);
        String packageName =
                (runningTasks == null || runningTasks.isEmpty())
                        ? ""
                        : runningTasks.get(0).topActivity.getPackageName();
        if (mEnableUsbHiddenMenu
                || packageName.equals("com.sec.usbsettings")
                || packageName.equals("com.sec.hiddenmenu")) {
            if ((j & 4) != 0) {
                SystemProperties.set("persist.sys.usb.q_config", "none");
                Slog.d("UsbDeviceManager", "Disable USB Hidden Menu");
            } else {
                String usbFunctionsToString = UsbManager.usbFunctionsToString(j);
                SystemProperties.set("persist.sys.usb.q_config", usbFunctionsToString);
                Slog.d(
                        "UsbDeviceManager",
                        "Enable USB Hidden Menu: functions=" + usbFunctionsToString);
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
        Long valueOf = Long.valueOf(j);
        UsbHandler usbHandler = this.mHandler;
        usbHandler.removeMessages(2);
        Message obtain = Message.obtain(usbHandler, 2);
        obtain.obj = valueOf;
        obtain.arg1 = i;
        usbHandler.sendMessage(obtain);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0023, code lost:

       if (r2[1] != null) goto L13;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startAccessoryMode() {
        /*
            r7 = this;
            java.lang.String r0 = "startAccessoryMode()"
            java.lang.String r1 = "UsbDeviceManager"
            android.util.sysfwutil.Slog.d(r1, r0)
            boolean r0 = r7.mHasUsbAccessory
            if (r0 != 0) goto Ld
            return
        Ld:
            java.util.concurrent.atomic.AtomicInteger r0 = com.android.server.usb.UsbDeviceManager.sUsbOperationCount
            int r0 = r0.incrementAndGet()
            java.lang.String[] r2 = r7.nativeGetAccessoryStrings()
            r7.mAccessoryStrings = r2
            r3 = 0
            if (r2 == 0) goto L26
            r4 = r2[r3]
            if (r4 == 0) goto L26
            r4 = 1
            r5 = r2[r4]
            if (r5 == 0) goto L26
            goto L27
        L26:
            r4 = r3
        L27:
            if (r2 == 0) goto L59
        L29:
            java.lang.String[] r2 = r7.mAccessoryStrings
            int r2 = r2.length
            if (r3 >= r2) goto L47
            java.lang.String r2 = "mAccessoryStrings["
            java.lang.String r5 = "]="
            java.lang.StringBuilder r2 = com.android.server.BatteryService$$ExternalSyntheticOutline0.m(r3, r2, r5)
            java.lang.String[] r5 = r7.mAccessoryStrings
            r5 = r5[r3]
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            android.util.sysfwutil.Slog.d(r1, r2)
            int r3 = r3 + 1
            goto L29
        L47:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "enableAccessory="
            r2.<init>(r3)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            android.util.sysfwutil.Slog.d(r1, r2)
            goto L5e
        L59:
            java.lang.String r2 = "mAccessoryStrings is null"
            android.util.sysfwutil.Slog.d(r1, r2)
        L5e:
            r1 = 0
            if (r4 == 0) goto L65
            r3 = 2
            goto L66
        L65:
            r3 = r1
        L66:
            int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r1 == 0) goto L83
            r1 = 8
            com.android.server.usb.UsbDeviceManager$UsbHandler r2 = r7.mHandler
            android.os.Message r1 = r2.obtainMessage(r1)
            r5 = 10000(0x2710, double:4.9407E-320)
            r2.sendMessageDelayed(r1, r5)
            r1 = 20
            android.os.Message r1 = r2.obtainMessage(r1)
            r2.sendMessageDelayed(r1, r5)
            r7.setCurrentFunctions(r3, r0)
        L83:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.usb.UsbDeviceManager.startAccessoryMode():void");
    }
}
