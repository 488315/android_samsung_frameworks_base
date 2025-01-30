package com.android.server.usb;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.FactoryTest;
import android.os.FileUtils;
import android.os.SystemProperties;
import android.os.UEventObserver;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.util.Pair;
import android.util.sysfwutil.Slog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.clipboard.ClipboardService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class UsbHostRestrictor implements ActivityTaskManagerInternal.ScreenObserver {
    public static boolean bRestrictHostAPI = false;
    public static boolean isLckScrBlock = false;
    public static boolean isMDMBlock = false;
    public static boolean isSIMBlock = false;
    public static final File mConHistFile = new File("/efs/usb_con_hist");
    public static String mCurrentScrLckNodeValue = "0";
    public static String mCurrentSysNodeValue = "OFF";
    public static boolean mIsDeviceConnected = false;
    public static boolean mIsHostConnected = false;
    public static boolean mIsSupportScrlckblk = false;
    public static int mLockStatus = 0;
    public static boolean mSecureKeyguardShowing = true;
    public static int mSettingBlockUsbLock = 1;
    public static String mStrAllowList = "";
    public static Thread mThreadLockTimer = null;
    public static boolean misRunScreenLockTimer = false;
    public static long startTime;
    public final EngineeringModeManager emm;
    public final Context mContext;
    public final UsbDeviceManager mDeviceManager;
    public final UEventObserver mHostInterfaceObserver;
    public final BroadcastReceiver mScreenBroadcastReceiver;
    public final BroadcastReceiver mSubscriptionIntentReceiver;
    public final UEventObserver mUEventDeviceObserver;
    public final UEventObserver mUEventHostObserver;
    public final BroadcastReceiver mUsbHostRestrictionReceiver;

    public final String checkBuildType() {
        return "USER";
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public void onAwakeStateChanged(boolean z) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public UsbHostRestrictor(Context context, UsbDeviceManager usbDeviceManager) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.usb.UsbHostRestrictor.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                RestrictionPolicy restrictionPolicy;
                Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver Receiver onReceive");
                if (intent.getAction().equals("com.samsung.android.knox.intent.action.UPDATE_ALLOW_USB_HOST_STORAGE_STATE_INTERNAL")) {
                    int intExtra = intent.getIntExtra("reason", 0);
                    if (intExtra == 0) {
                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver : reason - ALLOW");
                        UsbHostRestrictor.isMDMBlock = false;
                        String checkWriteValue = UsbHostRestrictor.this.checkWriteValue();
                        if (UsbHostRestrictor.this.getUsbHostDisableSysNodeWritable()) {
                            if (UsbHostRestrictor.this.readDisableSysNodefromFile().equals("OFF")) {
                                Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver USB is already UNBLOCKED");
                                return;
                            }
                            if (UsbHostRestrictor.this.readDisableSysNodefromFile().contains("ON_ALL")) {
                                Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver USB ALL is already BLOCKED");
                                return;
                            } else {
                                if (checkWriteValue.equals("OFF")) {
                                    Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver UNBLOCK USB HOST");
                                    UsbHostRestrictor.this.writeDisableSysNodetoFile(checkWriteValue);
                                    return;
                                }
                                return;
                            }
                        }
                        if (UsbHostRestrictor.this.getUsbHostDisableSysNodeWritable()) {
                            return;
                        }
                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver Cannot write for USB HOST ENABLE");
                        return;
                    }
                    if (intExtra == 1) {
                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver : reason - DISALLOW");
                        UsbHostRestrictor.isMDMBlock = true;
                        String checkWriteValue2 = UsbHostRestrictor.this.checkWriteValue();
                        if (UsbHostRestrictor.this.getUsbHostDisableSysNodeWritable()) {
                            if (UsbHostRestrictor.this.readDisableSysNodefromFile().contains("ON_ALL")) {
                                Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver USB ALL is already BLOCKED");
                                return;
                            } else {
                                if (checkWriteValue2.equals("ON_HOST_MDM")) {
                                    Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver BLOCK USB HOST");
                                    UsbHostRestrictor.this.writeDisableSysNodetoFile(checkWriteValue2);
                                    return;
                                }
                                return;
                            }
                        }
                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver Cannot write for USB HOST DISABLE");
                        return;
                    }
                    if (intExtra == 2) {
                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver : reason - INIT");
                        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(UsbHostRestrictor.this.mContext);
                        if (enterpriseDeviceManager == null || (restrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy()) == null) {
                            return;
                        }
                        if (restrictionPolicy.isUsbHostStorageAllowed(false) && UsbHostRestrictor.isMDMBlock) {
                            Slog.d("UsbHostRestrictor", "mUsbHostRestrictionReceiver : reason - INIT - UNBLOCK USB HOST");
                            UsbHostRestrictor.isMDMBlock = false;
                            String checkWriteValue3 = UsbHostRestrictor.this.checkWriteValue();
                            if (UsbHostRestrictor.this.getUsbHostDisableSysNodeWritable()) {
                                if (UsbHostRestrictor.this.readDisableSysNodefromFile().contains("ON_ALL")) {
                                    Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver USB ALL is already BLOCKED");
                                    return;
                                } else {
                                    if (checkWriteValue3.equals("OFF")) {
                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver UNBLOCK USB HOST");
                                        UsbHostRestrictor.this.writeDisableSysNodetoFile(checkWriteValue3);
                                        return;
                                    }
                                    return;
                                }
                            }
                            Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver Cannot write for USB HOST ENABLE");
                            return;
                        }
                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver : reason - INIT - Ignore bacuese of Multi admin policy or same value as previos");
                        return;
                    }
                    Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver reason is unknown.");
                }
            }
        };
        this.mUsbHostRestrictionReceiver = broadcastReceiver;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.server.usb.UsbHostRestrictor.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Slog.d("UsbHostRestrictor", "Subscription Receiver onReceive");
                if (intent.getAction().equals("android.intent.action.SIM_STATE_CHANGED")) {
                    String stringExtra = intent.getStringExtra("ss");
                    if (stringExtra.equals("LOADED")) {
                        int activeSubscriptionInfoCount = SubscriptionManager.from(UsbHostRestrictor.this.mContext).getActiveSubscriptionInfoCount();
                        Slog.d("UsbHostRestrictor", "Subscription Receiver Card Count is [" + activeSubscriptionInfoCount + "]");
                        if (activeSubscriptionInfoCount > 0) {
                            UsbHostRestrictor.isSIMBlock = false;
                            String checkWriteValue = UsbHostRestrictor.this.checkWriteValue();
                            if (UsbHostRestrictor.this.getUsbHostDisableSysNodeWritable()) {
                                if (UsbHostRestrictor.this.readDisableSysNodefromFile().equals("OFF")) {
                                    Slog.d("UsbHostRestrictor", "Subscription Receiver USB is already UNBLOCKED");
                                    return;
                                } else {
                                    if (checkWriteValue.equals("OFF") || checkWriteValue.equals("ON_HOST_MDM")) {
                                        UsbHostRestrictor.this.writeDisableSysNodetoFile(checkWriteValue);
                                        UsbHostRestrictor.this.mDeviceManager.updateUsbNotificationRefresh();
                                        return;
                                    }
                                    return;
                                }
                            }
                            if (UsbHostRestrictor.this.getUsbHostDisableSysNodeWritable()) {
                                return;
                            }
                            Slog.d("UsbHostRestrictor", "Subscription Receiver Cannot write for USB DISABLE");
                            return;
                        }
                        Slog.d("UsbHostRestrictor", "Subscription Receiver SIM is not recognized properly : card count is 0");
                        return;
                    }
                    Slog.d("UsbHostRestrictor", "Subscription Receiver SIM is not recognized properly : SIM_STATE_LOADED [" + stringExtra + "]");
                }
            }
        };
        this.mSubscriptionIntentReceiver = broadcastReceiver2;
        BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() { // from class: com.android.server.usb.UsbHostRestrictor.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                    Slog.d("UsbHostRestrictor", "ScreenBroadcastReceiver Screen On");
                    UsbHostRestrictor.this.isFinishLockTimer();
                }
            }
        };
        this.mScreenBroadcastReceiver = broadcastReceiver3;
        UEventObserver uEventObserver = new UEventObserver() { // from class: com.android.server.usb.UsbHostRestrictor.5
            public void onUEvent(UEventObserver.UEvent uEvent) {
                char c;
                Slog.v("UsbHostRestrictor", "USB HOST UEVENT : event=" + uEvent.toString());
                String str = uEvent.get("ACTION");
                String str2 = uEvent.get("DEVPATH");
                String str3 = uEvent.get("STATE");
                if ("change".equals(str) && str3 != null && str2.startsWith("/devices/virtual/host_notify")) {
                    try {
                        if ("BLOCK".equals(str3)) {
                            if (Settings.Secure.getInt(UsbHostRestrictor.this.mContext.getContentResolver(), "user_setup_complete", 1) != 1) {
                                Slog.d("UsbHostRestrictor", "UEventObserver SETUP WIZARD SCREEN. So skip about showing USB BLOCK Popup");
                                return;
                            }
                            String str4 = UsbHostRestrictor.mCurrentSysNodeValue;
                            switch (str4.hashCode()) {
                                case -2094529313:
                                    if (str4.equals("ON_HOST_MDM")) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 32049631:
                                    if (str4.equals("ON_ALL_BOTH")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 278144665:
                                    if (str4.equals("ON_ALL_SIM")) {
                                        c = 2;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1210477386:
                                    if (str4.equals("ON_ALL_SCREEN")) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    c = 65535;
                                    break;
                            }
                            if (c == 0) {
                                UsbHostRestrictor.this.showAlertDialog();
                                return;
                            }
                            if (c != 1) {
                                if (c == 2) {
                                    UsbHostRestrictor.this.showAlertDialog();
                                } else if (c == 3) {
                                    UsbHostRestrictor.this.showMDMToast();
                                } else {
                                    Slog.d("UsbHostRestrictor", "USB HOST is BLOCKED by UNKNOWN. Do Nothing!");
                                }
                            }
                        }
                    } catch (NumberFormatException unused) {
                        Slog.e("UsbHostRestrictor", "Could not parse state or devPath from event " + uEvent);
                    }
                }
            }
        };
        this.mUEventHostObserver = uEventObserver;
        UEventObserver uEventObserver2 = new UEventObserver() { // from class: com.android.server.usb.UsbHostRestrictor.6
            public void onUEvent(UEventObserver.UEvent uEvent) {
                String str = uEvent.get("USB_STATE");
                if (UsbHostRestrictor.mIsSupportScrlckblk) {
                    if ("DISCONNECTED".equals(str)) {
                        UsbHostRestrictor.mIsDeviceConnected = false;
                        if (UsbHostRestrictor.mLockStatus == 1) {
                            UsbHostRestrictor.this.startLockTimer();
                            return;
                        }
                        return;
                    }
                    if ("CONNECTED".equals(str)) {
                        UsbHostRestrictor.mIsDeviceConnected = true;
                        if (UsbHostRestrictor.mLockStatus != 1 || UsbHostRestrictor.this.isFinishLockTimer()) {
                            return;
                        }
                        UsbHostRestrictor.this.stopLockTimer();
                        return;
                    }
                    if ("CONFIGURED".equals(str)) {
                        int unused = UsbHostRestrictor.mLockStatus;
                        return;
                    }
                    Slog.e("UsbHostRestrictor", "unknown state " + str);
                }
            }
        };
        this.mUEventDeviceObserver = uEventObserver2;
        UEventObserver uEventObserver3 = new UEventObserver() { // from class: com.android.server.usb.UsbHostRestrictor.7
            public void onUEvent(UEventObserver.UEvent uEvent) {
                String str = uEvent.get("ACTION");
                String str2 = uEvent.get("PRODUCT");
                if (UsbHostRestrictor.mIsSupportScrlckblk) {
                    if ("add".equals(str)) {
                        UsbHostRestrictor.mIsHostConnected = true;
                        if (UsbHostRestrictor.mLockStatus == 1) {
                            if (UsbHostRestrictor.this.isFinishLockTimer()) {
                                return;
                            }
                            UsbHostRestrictor.this.stopLockTimer();
                            return;
                        } else {
                            if (UsbHostRestrictor.mLockStatus == 0 || UsbHostRestrictor.mLockStatus == 3) {
                                if (str2 == null || !str2.contains("/")) {
                                    return;
                                }
                                String[] split = str2.split("/");
                                UsbHostRestrictor.this.updateVidPidList(split[0], split[1]);
                                UsbHostRestrictor.this.writeVpidHistorytoFile(UsbHostRestrictor.this.readFileToString("/efs/usb_con_hist"));
                                return;
                            }
                            Slog.d("UsbHostRestrictor", "skip update vid:pid reason - SCREEN LOCK");
                            return;
                        }
                    }
                    if ("remove".equals(str)) {
                        UsbHostRestrictor.mIsHostConnected = false;
                        if (UsbHostRestrictor.mLockStatus == 1) {
                            UsbHostRestrictor.this.startLockTimer();
                        }
                    }
                }
            }
        };
        this.mHostInterfaceObserver = uEventObserver3;
        this.mContext = context;
        this.mDeviceManager = usbDeviceManager;
        uEventObserver.startObserving("DEVPATH=/devices/virtual/host_notify");
        context.registerReceiver(broadcastReceiver, new IntentFilter("com.samsung.android.knox.intent.action.UPDATE_ALLOW_USB_HOST_STORAGE_STATE_INTERNAL"));
        context.registerReceiver(broadcastReceiver2, new IntentFilter("android.intent.action.SIM_STATE_CHANGED"));
        context.registerReceiver(broadcastReceiver3, new IntentFilter("android.intent.action.SCREEN_ON"));
        this.emm = new EngineeringModeManager(context);
        uEventObserver2.startObserving("DEVPATH=/devices/virtual/android_usb/android0");
        uEventObserver3.startObserving("DEVTYPE=usb_interface");
        misRunScreenLockTimer = false;
        Thread thread = new Thread(new LockTimer());
        mThreadLockTimer = thread;
        thread.start();
        mIsSupportScrlckblk = checkSupportScrLckBlk();
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("block_usb_lock"), false, new ContentObserver(0 == true ? 1 : 0) { // from class: com.android.server.usb.UsbHostRestrictor.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                UsbHostRestrictor.mSettingBlockUsbLock = Settings.Secure.getInt(UsbHostRestrictor.this.mContext.getContentResolver(), "block_usb_lock", 1);
                if (UsbHostRestrictor.mIsSupportScrlckblk && UsbHostRestrictor.mSecureKeyguardShowing) {
                    if (UsbHostRestrictor.mSettingBlockUsbLock == 1 && !UsbHostRestrictor.this.isAdbOnlyMode()) {
                        Slog.d("UsbHostRestrictor", "changed setting LOCK_SCREEN_BLOCK : OFF -> ON");
                        UsbHostRestrictor.mLockStatus = 0;
                        return;
                    } else {
                        if (UsbHostRestrictor.mSecureKeyguardShowing && UsbHostRestrictor.mSettingBlockUsbLock == 0) {
                            Slog.d("UsbHostRestrictor", "changed setting LOCK_SCREEN_BLOCK : ON -> OFF");
                            UsbHostRestrictor.this.writeScrLckBlkSysNodetoFile("1");
                            UsbHostRestrictor.mLockStatus = 3;
                            UsbHostRestrictor.this.stopLockTimer();
                            return;
                        }
                        Slog.d("UsbHostRestrictor", "can't change block status (adb-only or unknown setting value)");
                        return;
                    }
                }
                Slog.d("UsbHostRestrictor", "can't change block status (unsupported block or none-lock)");
            }
        });
    }

    public void startLockTimer() {
        if (mIsSupportScrlckblk) {
            Slog.d("UsbHostRestrictor", "startLockTimer++");
            misRunScreenLockTimer = false;
            startTime = System.currentTimeMillis();
            misRunScreenLockTimer = true;
        }
    }

    public void stopLockTimer() {
        if (mIsSupportScrlckblk) {
            Slog.d("UsbHostRestrictor", "stopLockTimer--");
            misRunScreenLockTimer = false;
        }
    }

    public boolean isFinishLockTimer() {
        boolean z;
        if (mIsSupportScrlckblk && misRunScreenLockTimer) {
            Slog.d("UsbHostRestrictor", "isFinishLockTimer");
            long currentTimeMillis = System.currentTimeMillis() - startTime;
            if (this.emm.isConnected() && this.emm.getStatus(4) == 1) {
                Slog.d("UsbHostRestrictor", "TOKEN ENABLED");
                z = true;
            } else {
                z = false;
            }
            if (currentTimeMillis >= ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS && mLockStatus == 1 && !SystemProperties.get("persist.sys.auto_confirm", "0").equals("1") && !z) {
                Slog.d("UsbHostRestrictor", "finishLockTimer--");
                isLckScrBlock = true;
                mLockStatus = 2;
                writeScrLckBlkSysNodetoFile("2");
                String checkWriteValue = checkWriteValue();
                if (getUsbHostDisableSysNodeWritable()) {
                    String readDisableSysNodefromFile = readDisableSysNodefromFile();
                    if (readDisableSysNodefromFile.contains("ON_ALL")) {
                        if (readDisableSysNodefromFile.contains("ON_ALL_BOTH") || readDisableSysNodefromFile.contains("ON_ALL_SCREEN")) {
                            Slog.d("UsbHostRestrictor", "UsbHostRestrictor USB ALL is already BLOCKED by SCR LCK BLCK");
                        } else if (readDisableSysNodefromFile.contains("ON_ALL_SIM")) {
                            if (checkWriteValue.equals("ON_ALL_BOTH")) {
                                Slog.d("UsbHostRestrictor", "UsbHostRestrictor set SCR LCK BLCK");
                                writeDisableSysNodetoFile(checkWriteValue);
                            }
                        } else {
                            Slog.d("UsbHostRestrictor", "UsbHostRestrictor USB ALL is already BLOCKED by " + readDisableSysNodefromFile);
                        }
                    } else if (checkWriteValue.equals("ON_ALL_SCREEN")) {
                        Slog.d("UsbHostRestrictor", "UsbHostRestrictor set SCR LCK BLCK");
                        writeDisableSysNodetoFile(checkWriteValue);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public class LockTimer implements Runnable {
        public LockTimer() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!UsbHostRestrictor.mIsSupportScrlckblk) {
                return;
            }
            while (true) {
                UsbHostRestrictor.this.isFinishLockTimer();
                try {
                    Thread.sleep(60000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void bootCompleted() {
        initSetUsbBlock();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0153 A[Catch: IOException -> 0x014f, TRY_LEAVE, TryCatch #15 {IOException -> 0x014f, blocks: (B:34:0x014b, B:28:0x0153), top: B:33:0x014b }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x014b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0123 A[Catch: IOException -> 0x011f, TRY_LEAVE, TryCatch #21 {IOException -> 0x011f, blocks: (B:45:0x011b, B:39:0x0123), top: B:44:0x011b }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x011b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00f2 A[Catch: IOException -> 0x00ee, TRY_LEAVE, TryCatch #3 {IOException -> 0x00ee, blocks: (B:56:0x00ea, B:50:0x00f2), top: B:55:0x00ea }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ea A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x016b A[Catch: IOException -> 0x0167, TRY_LEAVE, TryCatch #17 {IOException -> 0x0167, blocks: (B:79:0x0163, B:72:0x016b), top: B:78:0x0163 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0163 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v32 */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r8v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12, types: [java.io.FileReader] */
    /* JADX WARN: Type inference failed for: r8v14, types: [java.io.FileReader] */
    /* JADX WARN: Type inference failed for: r8v16, types: [java.io.FileReader] */
    /* JADX WARN: Type inference failed for: r8v24 */
    /* JADX WARN: Type inference failed for: r8v26 */
    /* JADX WARN: Type inference failed for: r8v28 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v30 */
    /* JADX WARN: Type inference failed for: r8v31 */
    /* JADX WARN: Type inference failed for: r8v32 */
    /* JADX WARN: Type inference failed for: r8v33 */
    /* JADX WARN: Type inference failed for: r8v34 */
    /* JADX WARN: Type inference failed for: r8v35 */
    /* JADX WARN: Type inference failed for: r8v39, types: [java.io.FileReader, java.io.Reader] */
    /* JADX WARN: Type inference failed for: r8v41 */
    /* JADX WARN: Type inference failed for: r8v42 */
    /* JADX WARN: Type inference failed for: r8v43 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getSalesCode() {
        ?? r0;
        BufferedReader bufferedReader;
        IllegalArgumentException e;
        IOException e2;
        FileNotFoundException e3;
        StringBuilder sb;
        BufferedReader bufferedReader2;
        BufferedReader bufferedReader3;
        BufferedReader bufferedReader4;
        BufferedReader bufferedReader5;
        ?? r8 = "/system/csc/sales_code.dat";
        String str = "/efs/imei/mps_code.dat";
        String str2 = "null";
        FileReader fileReader = null;
        try {
            try {
                File file = new File("/efs/imei/mps_code.dat");
                File file2 = new File("/system/csc/sales_code.dat");
                if (file.exists()) {
                    Slog.d("UsbHostRestrictor", "mps exists");
                    r8 = new FileReader("/efs/imei/mps_code.dat");
                    try {
                        bufferedReader = new BufferedReader(r8);
                        try {
                            str2 = bufferedReader.readLine();
                            fileReader = r8;
                            bufferedReader2 = bufferedReader;
                        } catch (FileNotFoundException e4) {
                            e3 = e4;
                            Slog.e("UsbHostRestrictor", "File not Found exception: " + e3.getMessage());
                            if (r8 != 0) {
                                try {
                                    r8.close();
                                } catch (IOException e5) {
                                    e = e5;
                                    sb = new StringBuilder();
                                    sb.append("IOException(iex): ");
                                    sb.append(e.getMessage());
                                    Slog.e("UsbHostRestrictor", sb.toString());
                                    return str2;
                                }
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return str2;
                        } catch (IOException e6) {
                            e2 = e6;
                            Slog.e("UsbHostRestrictor", "IOException: " + e2.getMessage());
                            if (r8 != 0) {
                                try {
                                    r8.close();
                                } catch (IOException e7) {
                                    e = e7;
                                    sb = new StringBuilder();
                                    sb.append("IOException(iex): ");
                                    sb.append(e.getMessage());
                                    Slog.e("UsbHostRestrictor", sb.toString());
                                    return str2;
                                }
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return str2;
                        } catch (IllegalArgumentException e8) {
                            e = e8;
                            Slog.e("UsbHostRestrictor", "IllegalArgumentException: " + e.getMessage());
                            if (r8 != 0) {
                                try {
                                    r8.close();
                                } catch (IOException e9) {
                                    e = e9;
                                    sb = new StringBuilder();
                                    sb.append("IOException(iex): ");
                                    sb.append(e.getMessage());
                                    Slog.e("UsbHostRestrictor", sb.toString());
                                    return str2;
                                }
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return str2;
                        }
                    } catch (FileNotFoundException e10) {
                        bufferedReader5 = null;
                        e3 = e10;
                        r8 = r8;
                        bufferedReader = bufferedReader5;
                        Slog.e("UsbHostRestrictor", "File not Found exception: " + e3.getMessage());
                        if (r8 != 0) {
                        }
                        if (bufferedReader != null) {
                        }
                        return str2;
                    } catch (IOException e11) {
                        bufferedReader4 = null;
                        e2 = e11;
                        r8 = r8;
                        bufferedReader = bufferedReader4;
                        Slog.e("UsbHostRestrictor", "IOException: " + e2.getMessage());
                        if (r8 != 0) {
                        }
                        if (bufferedReader != null) {
                        }
                        return str2;
                    } catch (IllegalArgumentException e12) {
                        bufferedReader3 = null;
                        e = e12;
                        r8 = r8;
                        bufferedReader = bufferedReader3;
                        Slog.e("UsbHostRestrictor", "IllegalArgumentException: " + e.getMessage());
                        if (r8 != 0) {
                        }
                        if (bufferedReader != null) {
                        }
                        return str2;
                    } catch (Throwable th) {
                        th = th;
                        str = null;
                        fileReader = r8;
                        r0 = str;
                        if (fileReader != null) {
                        }
                        if (r0 != 0) {
                        }
                        throw th;
                    }
                } else if (file2.exists()) {
                    Slog.d("UsbHostRestrictor", "sales_code exists");
                    FileReader fileReader2 = new FileReader("/system/csc/sales_code.dat");
                    try {
                        bufferedReader2 = new BufferedReader(fileReader2);
                        try {
                            str2 = bufferedReader2.readLine();
                            fileReader = fileReader2;
                        } catch (FileNotFoundException e13) {
                            e3 = e13;
                            bufferedReader = bufferedReader2;
                            r8 = fileReader2;
                            Slog.e("UsbHostRestrictor", "File not Found exception: " + e3.getMessage());
                            if (r8 != 0) {
                            }
                            if (bufferedReader != null) {
                            }
                            return str2;
                        } catch (IOException e14) {
                            e2 = e14;
                            bufferedReader = bufferedReader2;
                            r8 = fileReader2;
                            Slog.e("UsbHostRestrictor", "IOException: " + e2.getMessage());
                            if (r8 != 0) {
                            }
                            if (bufferedReader != null) {
                            }
                            return str2;
                        } catch (IllegalArgumentException e15) {
                            e = e15;
                            bufferedReader = bufferedReader2;
                            r8 = fileReader2;
                            Slog.e("UsbHostRestrictor", "IllegalArgumentException: " + e.getMessage());
                            if (r8 != 0) {
                            }
                            if (bufferedReader != null) {
                            }
                            return str2;
                        } catch (Throwable th2) {
                            th = th2;
                            fileReader = fileReader2;
                            r0 = bufferedReader2;
                            if (fileReader != null) {
                                try {
                                    fileReader.close();
                                } catch (IOException e16) {
                                    Slog.e("UsbHostRestrictor", "IOException(iex): " + e16.getMessage());
                                    throw th;
                                }
                            }
                            if (r0 != 0) {
                                r0.close();
                            }
                            throw th;
                        }
                    } catch (FileNotFoundException e17) {
                        bufferedReader5 = null;
                        e3 = e17;
                        r8 = fileReader2;
                        bufferedReader = bufferedReader5;
                        Slog.e("UsbHostRestrictor", "File not Found exception: " + e3.getMessage());
                        if (r8 != 0) {
                        }
                        if (bufferedReader != null) {
                        }
                        return str2;
                    } catch (IOException e18) {
                        bufferedReader4 = null;
                        e2 = e18;
                        r8 = fileReader2;
                        bufferedReader = bufferedReader4;
                        Slog.e("UsbHostRestrictor", "IOException: " + e2.getMessage());
                        if (r8 != 0) {
                        }
                        if (bufferedReader != null) {
                        }
                        return str2;
                    } catch (IllegalArgumentException e19) {
                        bufferedReader3 = null;
                        e = e19;
                        r8 = fileReader2;
                        bufferedReader = bufferedReader3;
                        Slog.e("UsbHostRestrictor", "IllegalArgumentException: " + e.getMessage());
                        if (r8 != 0) {
                        }
                        if (bufferedReader != null) {
                        }
                        return str2;
                    } catch (Throwable th3) {
                        th = th3;
                        fileReader = fileReader2;
                        r0 = 0;
                    }
                } else {
                    Slog.e("UsbHostRestrictor", "Both dat does not exist");
                    bufferedReader2 = null;
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e20) {
                        e = e20;
                        sb = new StringBuilder();
                        sb.append("IOException(iex): ");
                        sb.append(e.getMessage());
                        Slog.e("UsbHostRestrictor", sb.toString());
                        return str2;
                    }
                }
                if (bufferedReader2 != null) {
                    bufferedReader2.close();
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (FileNotFoundException e21) {
            bufferedReader = null;
            e3 = e21;
            r8 = 0;
        } catch (IOException e22) {
            bufferedReader = null;
            e2 = e22;
            r8 = 0;
        } catch (IllegalArgumentException e23) {
            bufferedReader = null;
            e = e23;
            r8 = 0;
        } catch (Throwable th5) {
            th = th5;
            r0 = 0;
        }
        return str2;
    }

    public final void initSetUsbBlock() {
        if (mIsSupportScrlckblk) {
            Slog.d("UsbHostRestrictor", "initSetUsbBlock STARTED");
            if (mConHistFile.exists()) {
                writeVpidHistorytoFile(readFileToString("/efs/usb_con_hist"));
            } else {
                Slog.d("UsbHostRestrictor", "There is no connection record");
            }
            if (mSecureKeyguardShowing) {
                if (isAdbOnlyMode() || mSettingBlockUsbLock == 0) {
                    Slog.d("UsbHostRestrictor", "initSetUsbBlock LOCK_SCREEN_BLOCK : OFF");
                    writeScrLckBlkSysNodetoFile("1");
                    mLockStatus = 3;
                    stopLockTimer();
                } else {
                    Slog.d("UsbHostRestrictor", "initSetUsbBlock LOCK_SCREEN_BLOCK : ON");
                    writeScrLckBlkSysNodetoFile("2");
                    mLockStatus = 1;
                    if (!mIsDeviceConnected && !mIsHostConnected) {
                        startLockTimer();
                    }
                }
            }
        }
        boolean equals = SystemProperties.get("sys.config.usbSIMblock", "true").equals("true");
        String salesCode = getSalesCode();
        if ("null".equals(salesCode) || salesCode == null) {
            Slog.d("UsbHostRestrictor", "checkUsbBlockingCondition : salesCode is null");
            salesCode = "OXA";
        }
        int size = SubscriptionManager.from(this.mContext).getAllSubscriptionInfoList().size();
        if (size != 0) {
            if (size <= 0) {
                if (size < 0) {
                    Slog.d("UsbHostRestrictor", "SIM COUNT value is abnormal");
                    return;
                }
                return;
            } else {
                Slog.d("UsbHostRestrictor", "SIM has been already inserted");
                String checkWriteValue = checkWriteValue();
                if (getUsbHostDisableSysNodeWritable()) {
                    writeDisableSysNodetoFile(checkWriteValue);
                    return;
                } else {
                    Slog.d("UsbHostRestrictor", "Can NOT Write Disable Sys Node 2");
                    return;
                }
            }
        }
        Slog.d("UsbHostRestrictor", "SIM was never inserted");
        if (equals && !FactoryTest.isFactoryBinary() && ("CHM".equals(salesCode) || "CBK".equals(salesCode))) {
            Slog.d("UsbHostRestrictor", "NEED to BLOCK by NO SIM");
            isSIMBlock = true;
        }
        String checkWriteValue2 = checkWriteValue();
        if (getUsbHostDisableSysNodeWritable()) {
            writeDisableSysNodetoFile(checkWriteValue2);
        } else {
            Slog.d("UsbHostRestrictor", "Can NOT Write Disable Sys Node 1");
        }
    }

    public final String checkWriteValue() {
        Slog.d("UsbHostRestrictor", "checkWriteValue : isLckScrBlock= " + isLckScrBlock + ", isSIMBlock=" + isSIMBlock + ", isMDMBlock=" + isMDMBlock);
        boolean z = isLckScrBlock;
        if (z && isSIMBlock) {
            return "ON_ALL_BOTH";
        }
        if (z && !isSIMBlock) {
            return "ON_ALL_SCREEN";
        }
        if (!z && isSIMBlock) {
            return "ON_ALL_SIM";
        }
        if (!z && !isSIMBlock && isMDMBlock) {
            return "ON_HOST_MDM";
        }
        if (!z && !isSIMBlock) {
            boolean z2 = isMDMBlock;
        }
        return "OFF";
    }

    public final void writeDisableSysNodetoFile(String str) {
        Slog.d("UsbHostRestrictor", "Write Disable Sys Node with [" + str + "]");
        try {
            if (checkUsbBlockingCondition(str)) {
                FileUtils.stringToFile("/sys/class/usb_notify/usb_control/disable", str);
                mCurrentSysNodeValue = str;
                this.mDeviceManager.setUsbDisableVariable(str);
                this.mDeviceManager.updateUsbNotificationRefresh();
            } else {
                FileUtils.stringToFile("/sys/class/usb_notify/usb_control/disable", "OFF");
                mCurrentSysNodeValue = "OFF";
                this.mDeviceManager.setUsbDisableVariable("OFF");
            }
        } catch (IOException e) {
            Slog.e("UsbHostRestrictor", "Failed to write to DISABLE FILE");
            Slog.d("UsbHostRestrictor", "IOException : " + e);
            mCurrentSysNodeValue = "OFF";
            this.mDeviceManager.setUsbDisableVariable("OFF");
        }
    }

    public final boolean checkUsbBlockingCondition(String str) {
        String salesCode = getSalesCode();
        if ("null".equals(salesCode) || salesCode == null) {
            Slog.d("UsbHostRestrictor", "checkUsbBlockingCondition : salesCode is null");
            salesCode = "OXA";
        }
        if ("ENG".equals(checkBuildType())) {
            Slog.d("UsbHostRestrictor", "Cannot DISABLE USB at ENG BINARY");
            return false;
        }
        if ("USER".equals(checkBuildType()) && !FactoryTest.isFactoryBinary()) {
            if ("CHM".equals(salesCode) || "CBK".equals(salesCode)) {
                if (str.equals("ON_ALL_SIM") || str.equals("ON_HOST_MDM") || str.equals("ON_ALL_SCREEN") || str.equals("ON_ALL_BOTH")) {
                    Slog.d("UsbHostRestrictor", "DISABLE USB for USER BINARY and CMCC MODEL or MDM block or ON_ALL_SCREEN");
                    return true;
                }
                if ("OFF".equals(str)) {
                    Slog.d("UsbHostRestrictor", "NOT DISABLE USB 1");
                    return false;
                }
                Slog.d("UsbHostRestrictor", "NOT DISABLE USB 2");
                return false;
            }
            if (str.equals("ON_HOST_MDM") || str.equals("ON_ALL_SCREEN")) {
                Slog.d("UsbHostRestrictor", "DISABLE USB for MDM block or ON_ALL_SCREEN");
                return true;
            }
            Slog.d("UsbHostRestrictor", "NOT DISABLE USB 3");
            return false;
        }
        Slog.d("UsbHostRestrictor", "NOT DISABLE USB 4");
        return false;
    }

    public final String readDisableSysNodefromFile() {
        String str;
        try {
            str = FileUtils.readTextFile(new File("/sys/class/usb_notify/usb_control/disable"), 0, null).trim();
        } catch (IOException unused) {
            Slog.e("UsbHostRestrictor", "Failed to read from DISABLE FILE");
            str = "";
        }
        if (str.contains("ON")) {
            mCurrentSysNodeValue = str;
            this.mDeviceManager.setUsbDisableVariable(str);
        } else {
            mCurrentSysNodeValue = "OFF";
            this.mDeviceManager.setUsbDisableVariable("OFF");
        }
        return str;
    }

    public final boolean getUsbHostDisableSysNodeWritable() {
        return new File("/sys/class/usb_notify/usb_control/disable").exists();
    }

    public final void showAlertDialog() {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.samsung.android.settings.SettingsReceiverActivity");
        intent.putExtra("cmcc_block_usb", true);
        intent.addFlags(268435456);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Slog.d("UsbHostRestrictor", "Unable to start activity to show the USB BLOCK Dialog : " + e);
        }
    }

    public final void showMDMToast() {
        RestrictionPolicy restrictionPolicy;
        Slog.d("UsbHostRestrictor", "showMDMToast");
        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(this.mContext);
        if (enterpriseDeviceManager == null || (restrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy()) == null) {
            return;
        }
        restrictionPolicy.isUsbHostStorageAllowed(true);
    }

    public static boolean isUsbBlocked() {
        if (mCurrentSysNodeValue.contains("ON")) {
            return true;
        }
        if (!mCurrentSysNodeValue.equals("OFF")) {
            Slog.d("UsbHostRestrictor", "Current USB BLOCK STATE is UNKNOWN!! So USB is UNBLOCKED as a default value");
        }
        return false;
    }

    public boolean isSupportScrLckBlk() {
        return mIsSupportScrlckblk;
    }

    public boolean checkSupportScrLckBlk() {
        return readScrLckBlkSysNodefromFile().equals("3");
    }

    public static boolean isSupportDexRestrict() {
        File file = new File("/sys/class/usb_notify/usb_control/whitelist_for_mdm");
        Slog.d("UsbHostRestrictor", "isSupportDexRestrict [" + file.exists() + ", " + file.isFile() + ", " + file.canWrite() + "]");
        return file.exists() && file.isFile() && file.canWrite();
    }

    public static int restrictUsbHostInterface(boolean z, String str) {
        Slog.d("UsbHostRestrictor", "restrictUsbHostInterface(" + z + ", " + str + ")");
        if (isSupportDexRestrict()) {
            try {
                bRestrictHostAPI = z;
                mStrAllowList = str;
                FileUtils.stringToFile("/sys/class/usb_notify/usb_control/whitelist_for_mdm", str);
                return 0;
            } catch (Exception e) {
                Slog.e("UsbHostRestrictor", "restrictUsbHostInterface() fail - " + e);
                return -1;
            }
        }
        Slog.e("UsbHostRestrictor", "restrictUsbHostInterface() node error");
        return -1;
    }

    public final boolean updateVidPidList(String str, String str2) {
        boolean z;
        String str3 = str;
        String str4 = str2;
        if (str3.matches("-?[0-9a-fA-F]+") && str4.matches("-?[0-9a-fA-F]+")) {
            str3 = String.format("%04x", Long.valueOf(Long.parseLong(str3, 16)));
            str4 = String.format("%04x", Long.valueOf(Long.parseLong(str4, 16)));
        } else {
            Slog.d("UsbHostRestrictor", "updateVidPidList wrong vid pid");
        }
        try {
            File file = mConHistFile;
            if (file.exists()) {
                ArrayList arrayList = new ArrayList();
                BufferedReader bufferedReader = new BufferedReader(new FileReader("/efs/usb_con_hist"));
                StringBuilder sb = new StringBuilder();
                boolean z2 = false;
                boolean z3 = false;
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                    if (readLine.contains(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                        String[] split = readLine.split(XmlUtils.STRING_ARRAY_SEPARATOR);
                        for (int i = 1; i < split.length; i += 2) {
                            Iterator it = arrayList.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                Pair pair = (Pair) it.next();
                                boolean z4 = z3;
                                if (((String) pair.first).equals(split[i]) && ((String) pair.second).equals(split[i + 1])) {
                                    z3 = true;
                                    break;
                                }
                                z3 = z4;
                            }
                            if (!z3) {
                                arrayList.add(Pair.create(split[i], split[i + 1]));
                            }
                        }
                        boolean z5 = z3;
                        if (split.length >= 200) {
                            Slog.d("UsbHostRestrictor", "Lock screen block allow list full");
                            z2 = true;
                        }
                        z3 = z5;
                    }
                }
                bufferedReader.close();
                Iterator it2 = arrayList.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z = true;
                        break;
                    }
                    Pair pair2 = (Pair) it2.next();
                    String str5 = (String) pair2.first;
                    String str6 = (String) pair2.second;
                    if (str5.equals(str3) && str6.equals(str4)) {
                        z = false;
                        break;
                    }
                }
                if (!z) {
                    Slog.d("UsbHostRestrictor", "Skip update vid:pid - duplicate");
                    return false;
                }
                if (z2) {
                    try {
                        StringBuilder sb2 = new StringBuilder();
                        String sb3 = sb.toString();
                        if (sb3 != null && sb3.contains(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                            String[] split2 = sb3.split(XmlUtils.STRING_ARRAY_SEPARATOR);
                            for (int i2 = 3; i2 < split2.length; i2++) {
                                sb2.append(split2[i2]);
                                sb2.append(':');
                            }
                        }
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/efs/usb_con_hist", false));
                        bufferedWriter.write("VPID:" + sb2.toString() + str3 + XmlUtils.STRING_ARRAY_SEPARATOR + str4);
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter("/efs/usb_con_hist", true));
                    bufferedWriter2.write(XmlUtils.STRING_ARRAY_SEPARATOR + str3 + XmlUtils.STRING_ARRAY_SEPARATOR + str4);
                    bufferedWriter2.close();
                }
                return true;
            }
            Slog.d("UsbHostRestrictor", "Make new file");
            file.createNewFile();
            BufferedWriter bufferedWriter3 = new BufferedWriter(new FileWriter("/efs/usb_con_hist", true));
            bufferedWriter3.write("VPID:" + str3 + XmlUtils.STRING_ARRAY_SEPARATOR + str4);
            bufferedWriter3.close();
            return true;
        } catch (IOException e2) {
            Slog.e("UsbHostRestrictor", "Failed to write vid:pid history", e2);
            return false;
        }
    }

    public final String readFileToString(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                    sb.append('\n');
                } finally {
                }
            }
            bufferedReader.close();
        } catch (IOException unused) {
            Slog.e("UsbHostRestrictor", "Failed to read from ScrLck Block FILE");
        }
        return sb.toString();
    }

    public void systemReady() {
        Slog.d("UsbHostRestrictor", "systemReady");
        ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).registerScreenObserver(this);
        mSettingBlockUsbLock = Settings.Secure.getInt(this.mContext.getContentResolver(), "block_usb_lock", 1);
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public void onKeyguardStateChanged(boolean z) {
        int currentUser = ActivityManager.getCurrentUser();
        int i = 0;
        boolean z2 = z && ((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceSecure(currentUser);
        if (mSecureKeyguardShowing != z2) {
            mSecureKeyguardShowing = z2;
        }
        readScrLckBlkSysNodefromFile();
        if (!mIsSupportScrlckblk) {
            if (z2) {
                Slog.d("UsbHostRestrictor", "onKeyguardStateChanged: Screen Lock On");
                writeScrLckBlkSysNodetoFile("1");
                return;
            } else {
                if (z2) {
                    return;
                }
                Slog.d("UsbHostRestrictor", "onKeyguardStateChanged: Screen Lock Off");
                writeScrLckBlkSysNodetoFile("0");
                return;
            }
        }
        if (mSecureKeyguardShowing) {
            if ((isAdbOnlyMode() || mSettingBlockUsbLock == 0) && mLockStatus != 3) {
                writeScrLckBlkSysNodetoFile("1");
                mLockStatus = 3;
                stopLockTimer();
                Slog.d("UsbHostRestrictor", "onKeyguardStateChanged: LOCK_SCREEN_BLOCK : ON -> OFF");
                return;
            }
            if (!isAdbOnlyMode() && mSettingBlockUsbLock == 1 && mLockStatus == 3) {
                mLockStatus = 0;
                Slog.d("UsbHostRestrictor", "onKeyguardStateChanged: LOCK_SCREEN_BLOCK : OFF -> ON");
            } else if (mLockStatus == 3) {
                Slog.d("UsbHostRestrictor", "onKeyguardStateChanged: LOCK_SCREEN_BLOCK : OFF");
                return;
            }
        }
        if (!z2 || mLockStatus != 0) {
            if (z2) {
                return;
            }
            Slog.d("UsbHostRestrictor", "onKeyguardStateChanged: Screen Lock Off");
            if (mLockStatus == 2) {
                isLckScrBlock = false;
                String checkWriteValue = checkWriteValue();
                if (getUsbHostDisableSysNodeWritable() && (readDisableSysNodefromFile().contains("ON_ALL_SCREEN") || readDisableSysNodefromFile().contains("ON_ALL_BOTH"))) {
                    writeDisableSysNodetoFile(checkWriteValue);
                }
            }
            writeScrLckBlkSysNodetoFile("0");
            mLockStatus = 0;
            stopLockTimer();
            return;
        }
        while (!((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceLocked(currentUser)) {
            i++;
            if (i > 4) {
                return;
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Slog.d("UsbHostRestrictor", "onKeyguardStateChanged: Screen Lock On");
        writeScrLckBlkSysNodetoFile("2");
        mLockStatus = 1;
        if (mIsDeviceConnected || mIsHostConnected) {
            return;
        }
        startLockTimer();
    }

    public final String readScrLckBlkSysNodefromFile() {
        try {
            return FileUtils.readTextFile(new File("/sys/class/usb_notify/usb_control/usb_sl"), 0, null).trim();
        } catch (IOException unused) {
            Slog.e("UsbHostRestrictor", "Failed to read from ScrLck Block FILE");
            return "";
        }
    }

    public final void writeScrLckBlkSysNodetoFile(String str) {
        boolean z;
        String checkWriteValue;
        Slog.d("UsbHostRestrictor", "Write ScrLck Block FILE");
        try {
            if (this.emm.isConnected()) {
                z = true;
                if (this.emm.getStatus(4) == 1) {
                    Slog.d("UsbHostRestrictor", "TOKEN ENABLED 2");
                    if ((!SystemProperties.get("persist.sys.auto_confirm", "0").equals("1") || z) && "2".equals(str)) {
                        Slog.d("UsbHostRestrictor", "usb debug mode on");
                        isLckScrBlock = false;
                        checkWriteValue = checkWriteValue();
                        if (getUsbHostDisableSysNodeWritable() && (readDisableSysNodefromFile().contains("ON_ALL_SCREEN") || readDisableSysNodefromFile().contains("ON_ALL_BOTH"))) {
                            writeDisableSysNodetoFile(checkWriteValue);
                        }
                        str = "1";
                    }
                    FileUtils.stringToFile("/sys/class/usb_notify/usb_control/usb_sl", str);
                    mCurrentScrLckNodeValue = str;
                    return;
                }
            }
            FileUtils.stringToFile("/sys/class/usb_notify/usb_control/usb_sl", str);
            mCurrentScrLckNodeValue = str;
            return;
        } catch (IOException e) {
            Slog.e("UsbHostRestrictor", "Failed to write to ScrLck Block FILE");
            Slog.d("UsbHostRestrictor", "IOException : " + e);
            mCurrentScrLckNodeValue = "0";
            return;
        }
        z = false;
        if (!SystemProperties.get("persist.sys.auto_confirm", "0").equals("1")) {
        }
        Slog.d("UsbHostRestrictor", "usb debug mode on");
        isLckScrBlock = false;
        checkWriteValue = checkWriteValue();
        if (getUsbHostDisableSysNodeWritable()) {
            writeDisableSysNodetoFile(checkWriteValue);
        }
        str = "1";
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:16:0x0031
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public final void writeVpidHistorytoFile(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r5 = "Failed to close the file writer"
            java.lang.String r0 = "writeVpidHistorytoFile"
            java.lang.String r1 = "UsbHostRestrictor"
            android.util.sysfwutil.Slog.d(r1, r0)
            r0 = 0
            java.io.File r2 = new java.io.File     // Catch: java.lang.Throwable -> L24 java.io.IOException -> L26
            java.lang.String r3 = "/sys/class/usb_notify/usb_control/whitelist_for_mdm"
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L24 java.io.IOException -> L26
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch: java.lang.Throwable -> L24 java.io.IOException -> L26
            r4 = 1
            r3.<init>(r2, r4)     // Catch: java.lang.Throwable -> L24 java.io.IOException -> L26
            r3.write(r6)     // Catch: java.lang.Throwable -> L1f java.io.IOException -> L22
            r3.close()     // Catch: java.io.IOException -> L31
            goto L34
        L1f:
            r6 = move-exception
            r0 = r3
            goto L35
        L22:
            r0 = r3
            goto L26
        L24:
            r6 = move-exception
            goto L35
        L26:
            java.lang.String r6 = "Failed to write to USB RESTRICT File Path"
            android.util.sysfwutil.Slog.e(r1, r6)     // Catch: java.lang.Throwable -> L24
            if (r0 == 0) goto L34
            r0.close()     // Catch: java.io.IOException -> L31
            goto L34
        L31:
            android.util.sysfwutil.Slog.e(r1, r5)
        L34:
            return
        L35:
            if (r0 == 0) goto L3e
            r0.close()     // Catch: java.io.IOException -> L3b
            goto L3e
        L3b:
            android.util.sysfwutil.Slog.e(r1, r5)
        L3e:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.usb.UsbHostRestrictor.writeVpidHistorytoFile(java.lang.String):void");
    }

    public final boolean isAdbOnlyMode() {
        return SystemProperties.get("persist.sys.usb.config", "none").equals("adb");
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter) {
        printWriter.println("USB Host Restrictor State:");
        printWriter.println("  All SIM Count:" + SubscriptionManager.from(this.mContext).getAllSubscriptionInfoList().size());
        printWriter.println("  Disable Sys Node Value :" + readDisableSysNodefromFile());
        printWriter.println("  Disable Sys Node Writable :" + String.valueOf(getUsbHostDisableSysNodeWritable()));
        printWriter.println("  mCurrentSysNodeValue :" + mCurrentSysNodeValue);
        printWriter.println("  SIM BLOCK ON/OFF :" + isSIMBlock);
        printWriter.println("  MDM BLOCK ON/OFF :" + isMDMBlock);
        printWriter.println("  LckScr BLOCK ON/OFF :" + isLckScrBlock);
        printWriter.println("  CurrentScrLckStateValue :" + mCurrentScrLckNodeValue);
        printWriter.println("  ScreenLockStateValue :" + mLockStatus);
        printWriter.println("  IsHostConnected :" + mIsHostConnected);
        printWriter.println("  IsDeviceConnected :" + mIsDeviceConnected);
        printWriter.println("  SettingBlockUsbLock :" + mSettingBlockUsbLock);
        printWriter.println("  IsSupportScrlckblk :" + mIsSupportScrlckblk);
        printWriter.println("  MDM bRestrictHostAPI :" + bRestrictHostAPI);
        printWriter.println("  MDM mStrAllowList :" + mStrAllowList);
    }
}
