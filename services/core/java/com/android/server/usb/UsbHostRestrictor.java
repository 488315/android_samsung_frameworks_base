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
import android.os.Message;
import android.os.SystemProperties;
import android.os.UEventObserver;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.util.Pair;
import android.util.sysfwutil.Slog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.usb.UsbDeviceManager;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbHostRestrictor implements ActivityTaskManagerInternal.ScreenObserver {
    public static boolean bRestrictHostAPI = false;
    public static boolean isEMTokenEnabled = false;
    public static boolean isLckScrBlock = false;
    public static boolean isMDMBlock = false;
    public static boolean isSIMBlock = false;
    public static String mCurrentScrLckNodeValue = "0";
    public static String mCurrentSysNodeValue = "OFF";
    public static boolean mIsDeviceConnected = false;
    public static boolean mIsHostConnected = false;
    public static int mLockStatus = 0;
    public static boolean mSecureKeyguardShowing = true;
    public static int mSettingBlockUsbLock = 1;
    public static String mStrAllowList = "";
    public static String mUsbConHist = "";
    public static boolean misRunScreenLockTimer;
    public static long startTime;
    public final EngineeringModeManager emm;
    public final Context mContext;
    public final UsbDeviceManager mDeviceManager;
    public final AnonymousClass4 mHostInterfaceObserver;
    public final AnonymousClass2 mSubscriptionIntentReceiver;
    public final AnonymousClass4 mUEventDeviceObserver;
    public final AnonymousClass4 mUEventHostObserver;
    public final AnonymousClass2 mUsbHostRestrictionReceiver;
    public final Object mUsbRestrictLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LockTimer implements Runnable {
        public LockTimer() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            while (true) {
                synchronized (UsbHostRestrictor.this.mUsbRestrictLock) {
                    try {
                        if (UsbHostRestrictor.mLockStatus != 2 && UsbHostRestrictor.misRunScreenLockTimer) {
                            Slog.d("UsbHostRestrictor", "LockTimer run FinishLockTimer");
                            UsbHostRestrictor.this.isFinishLockTimer();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                try {
                    Thread.sleep(16000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* renamed from: -$$Nest$mshowAlertDialog, reason: not valid java name */
    public static void m1020$$Nest$mshowAlertDialog(UsbHostRestrictor usbHostRestrictor) {
        usbHostRestrictor.getClass();
        Intent intent = new Intent();
        intent.setClassName(KnoxCustomManagerService.SETTING_PKG_NAME, "com.samsung.android.settings.SettingsReceiverActivity");
        intent.putExtra("cmcc_block_usb", true);
        intent.addFlags(268435456);
        try {
            usbHostRestrictor.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Slog.d("UsbHostRestrictor", "Unable to start activity to show the USB BLOCK Dialog : " + e);
        }
    }

    /* renamed from: -$$Nest$mupdateVidPidList, reason: not valid java name */
    public static void m1021$$Nest$mupdateVidPidList(UsbHostRestrictor usbHostRestrictor, String str, String str2) {
        usbHostRestrictor.getClass();
        if (str.matches("-?[0-9a-fA-F]+") && str2.matches("-?[0-9a-fA-F]+")) {
            str = String.format("%04x", Long.valueOf(Long.parseLong(str, 16)));
            str2 = String.format("%04x", Long.valueOf(Long.parseLong(str2, 16)));
        } else {
            Slog.d("UsbHostRestrictor", "updateVidPidList wrong vid pid");
        }
        String str3 = mUsbConHist;
        if (str3 == null || str3 == "") {
            if (str3 == null || str3 != "") {
                Slog.d("UsbHostRestrictor", "mUsbConHist is null");
                return;
            } else {
                mUsbConHist = BootReceiver$$ExternalSyntheticOutline0.m("VPID:", str, ":", str2);
                return;
            }
        }
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        String str4 = mUsbConHist;
        sb.append(str4);
        boolean z = false;
        if (str4 != null && str4.contains(":")) {
            String[] split = str4.split(":");
            boolean z2 = false;
            for (int i = 1; i < split.length; i += 2) {
                Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Pair pair = (Pair) it.next();
                    if (((String) pair.first).equals(split[i]) && ((String) pair.second).equals(split[i + 1])) {
                        z2 = true;
                        break;
                    }
                }
                if (!z2) {
                    arrayList.add(Pair.create(split[i], split[i + 1]));
                }
            }
            if (split.length >= 40) {
                Slog.d("UsbHostRestrictor", "Lock screen block allow list full");
                z = true;
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Pair pair2 = (Pair) it2.next();
            String str5 = (String) pair2.first;
            String str6 = (String) pair2.second;
            if (str5.equals(str) && str6.equals(str2)) {
                Slog.d("UsbHostRestrictor", "Skip update vid:pid - duplicate");
                return;
            }
        }
        if (!z) {
            StringBuilder sb2 = new StringBuilder();
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb2, mUsbConHist, ":", str, ":");
            sb2.append(str2);
            mUsbConHist = sb2.toString();
            return;
        }
        StringBuilder sb3 = new StringBuilder();
        String sb4 = sb.toString();
        if (sb4 != null && sb4.contains(":")) {
            String[] split2 = sb4.split(":");
            for (int i2 = 3; i2 < split2.length; i2++) {
                sb3.append(split2[i2]);
                sb3.append(':');
            }
        }
        mUsbConHist = "VPID:" + sb3.toString() + str + ":" + str2;
    }

    /* renamed from: -$$Nest$mwriteVpidHistorytoFile, reason: not valid java name */
    public static void m1022$$Nest$mwriteVpidHistorytoFile(UsbHostRestrictor usbHostRestrictor, String str) {
        usbHostRestrictor.getClass();
        Slog.d("UsbHostRestrictor", "writeVpidHistorytoFile");
        FileWriter fileWriter = null;
        try {
            try {
                try {
                    FileWriter fileWriter2 = new FileWriter(new File("/sys/class/usb_notify/usb_control/whitelist_for_mdm"), true);
                    try {
                        fileWriter2.write(str);
                        fileWriter2.close();
                    } catch (IOException unused) {
                        fileWriter = fileWriter2;
                        Slog.e("UsbHostRestrictor", "Failed to write to USB RESTRICT File Path");
                        if (fileWriter != null) {
                            fileWriter.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileWriter = fileWriter2;
                        if (fileWriter != null) {
                            try {
                                fileWriter.close();
                            } catch (IOException unused2) {
                                Slog.e("UsbHostRestrictor", "Failed to close the file writer");
                            }
                        }
                        throw th;
                    }
                } catch (IOException unused3) {
                }
            } catch (IOException unused4) {
                Slog.e("UsbHostRestrictor", "Failed to close the file writer");
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public UsbHostRestrictor(Context context, UsbDeviceManager usbDeviceManager) {
        final int i = 0;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class: com.android.server.usb.UsbHostRestrictor.2
            public final /* synthetic */ UsbHostRestrictor this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                RestrictionPolicy restrictionPolicy;
                switch (i) {
                    case 0:
                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver Receiver onReceive");
                        if (intent.getAction().equals("com.samsung.android.knox.intent.action.UPDATE_ALLOW_USB_HOST_STORAGE_STATE_INTERNAL")) {
                            int intExtra = intent.getIntExtra("reason", 0);
                            if (intExtra != 0) {
                                if (intExtra != 1) {
                                    if (intExtra != 2) {
                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver reason is unknown.");
                                        break;
                                    } else {
                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver : reason - INIT");
                                        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(this.this$0.mContext);
                                        if (enterpriseDeviceManager != null && (restrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy()) != null) {
                                            if (!restrictionPolicy.isUsbHostStorageAllowed(false) || !UsbHostRestrictor.isMDMBlock) {
                                                Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver : reason - INIT - Ignore bacuese of Multi admin policy or same value as previos");
                                                break;
                                            } else {
                                                Slog.d("UsbHostRestrictor", "mUsbHostRestrictionReceiver : reason - INIT - UNBLOCK USB HOST");
                                                UsbHostRestrictor.isMDMBlock = false;
                                                this.this$0.getClass();
                                                String checkWriteValue = UsbHostRestrictor.checkWriteValue();
                                                this.this$0.getClass();
                                                if (!UsbHostRestrictor.getUsbHostDisableSysNodeWritable()) {
                                                    Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver Cannot write for USB HOST ENABLE");
                                                    break;
                                                } else if (!this.this$0.readDisableSysNodefromFile().contains("ON_ALL")) {
                                                    if (checkWriteValue.equals("OFF")) {
                                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver UNBLOCK USB HOST");
                                                        this.this$0.writeDisableSysNodetoFile(checkWriteValue);
                                                        break;
                                                    }
                                                } else {
                                                    Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver USB ALL is already BLOCKED");
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver : reason - DISALLOW");
                                    UsbHostRestrictor.isMDMBlock = true;
                                    this.this$0.getClass();
                                    String checkWriteValue2 = UsbHostRestrictor.checkWriteValue();
                                    this.this$0.getClass();
                                    if (!UsbHostRestrictor.getUsbHostDisableSysNodeWritable()) {
                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver Cannot write for USB HOST DISABLE");
                                        break;
                                    } else if (!this.this$0.readDisableSysNodefromFile().contains("ON_ALL")) {
                                        if (checkWriteValue2.equals("ON_HOST_MDM")) {
                                            Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver BLOCK USB HOST");
                                            this.this$0.writeDisableSysNodetoFile(checkWriteValue2);
                                            break;
                                        }
                                    } else {
                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver USB ALL is already BLOCKED");
                                        break;
                                    }
                                }
                            } else {
                                Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver : reason - ALLOW");
                                UsbHostRestrictor.isMDMBlock = false;
                                this.this$0.getClass();
                                String checkWriteValue3 = UsbHostRestrictor.checkWriteValue();
                                this.this$0.getClass();
                                if (!UsbHostRestrictor.getUsbHostDisableSysNodeWritable()) {
                                    this.this$0.getClass();
                                    if (!UsbHostRestrictor.getUsbHostDisableSysNodeWritable()) {
                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver Cannot write for USB HOST ENABLE");
                                        break;
                                    }
                                } else if (!this.this$0.readDisableSysNodefromFile().equals("OFF")) {
                                    if (!this.this$0.readDisableSysNodefromFile().contains("ON_ALL")) {
                                        if (checkWriteValue3.equals("OFF")) {
                                            Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver UNBLOCK USB HOST");
                                            this.this$0.writeDisableSysNodetoFile(checkWriteValue3);
                                            break;
                                        }
                                    } else {
                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver USB ALL is already BLOCKED");
                                        break;
                                    }
                                } else {
                                    Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver USB is already UNBLOCKED");
                                    break;
                                }
                            }
                        }
                        break;
                    default:
                        Slog.d("UsbHostRestrictor", "Subscription Receiver onReceive");
                        if (intent.getAction().equals(Constants.SIM_STATE_CHANGED)) {
                            String stringExtra = intent.getStringExtra("ss");
                            if (!stringExtra.equals("LOADED")) {
                                Slog.d("UsbHostRestrictor", "Subscription Receiver SIM is not recognized properly : SIM_STATE_LOADED [" + stringExtra + "]");
                                break;
                            } else {
                                int activeSubscriptionInfoCount = SubscriptionManager.from(this.this$0.mContext).getActiveSubscriptionInfoCount();
                                Slog.d("UsbHostRestrictor", "Subscription Receiver Card Count is [" + activeSubscriptionInfoCount + "]");
                                if (activeSubscriptionInfoCount <= 0) {
                                    Slog.d("UsbHostRestrictor", "Subscription Receiver SIM is not recognized properly : card count is 0");
                                    break;
                                } else {
                                    UsbHostRestrictor.isSIMBlock = false;
                                    this.this$0.getClass();
                                    String checkWriteValue4 = UsbHostRestrictor.checkWriteValue();
                                    this.this$0.getClass();
                                    if (!UsbHostRestrictor.getUsbHostDisableSysNodeWritable()) {
                                        this.this$0.getClass();
                                        if (!UsbHostRestrictor.getUsbHostDisableSysNodeWritable()) {
                                            Slog.d("UsbHostRestrictor", "Subscription Receiver Cannot write for USB DISABLE");
                                            break;
                                        }
                                    } else if (!this.this$0.readDisableSysNodefromFile().equals("OFF")) {
                                        if (checkWriteValue4.equals("OFF") || checkWriteValue4.equals("ON_HOST_MDM")) {
                                            this.this$0.writeDisableSysNodetoFile(checkWriteValue4);
                                            UsbDeviceManager.UsbHandler usbHandler = this.this$0.mDeviceManager.mHandler;
                                            usbHandler.removeMessages(101);
                                            Message obtain = Message.obtain(usbHandler, 101);
                                            obtain.arg1 = 1;
                                            usbHandler.sendMessage(obtain);
                                            break;
                                        }
                                    } else {
                                        Slog.d("UsbHostRestrictor", "Subscription Receiver USB is already UNBLOCKED");
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        };
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver(this) { // from class: com.android.server.usb.UsbHostRestrictor.2
            public final /* synthetic */ UsbHostRestrictor this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                RestrictionPolicy restrictionPolicy;
                switch (i2) {
                    case 0:
                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver Receiver onReceive");
                        if (intent.getAction().equals("com.samsung.android.knox.intent.action.UPDATE_ALLOW_USB_HOST_STORAGE_STATE_INTERNAL")) {
                            int intExtra = intent.getIntExtra("reason", 0);
                            if (intExtra != 0) {
                                if (intExtra != 1) {
                                    if (intExtra != 2) {
                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver reason is unknown.");
                                        break;
                                    } else {
                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver : reason - INIT");
                                        EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(this.this$0.mContext);
                                        if (enterpriseDeviceManager != null && (restrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy()) != null) {
                                            if (!restrictionPolicy.isUsbHostStorageAllowed(false) || !UsbHostRestrictor.isMDMBlock) {
                                                Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver : reason - INIT - Ignore bacuese of Multi admin policy or same value as previos");
                                                break;
                                            } else {
                                                Slog.d("UsbHostRestrictor", "mUsbHostRestrictionReceiver : reason - INIT - UNBLOCK USB HOST");
                                                UsbHostRestrictor.isMDMBlock = false;
                                                this.this$0.getClass();
                                                String checkWriteValue = UsbHostRestrictor.checkWriteValue();
                                                this.this$0.getClass();
                                                if (!UsbHostRestrictor.getUsbHostDisableSysNodeWritable()) {
                                                    Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver Cannot write for USB HOST ENABLE");
                                                    break;
                                                } else if (!this.this$0.readDisableSysNodefromFile().contains("ON_ALL")) {
                                                    if (checkWriteValue.equals("OFF")) {
                                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver UNBLOCK USB HOST");
                                                        this.this$0.writeDisableSysNodetoFile(checkWriteValue);
                                                        break;
                                                    }
                                                } else {
                                                    Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver USB ALL is already BLOCKED");
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver : reason - DISALLOW");
                                    UsbHostRestrictor.isMDMBlock = true;
                                    this.this$0.getClass();
                                    String checkWriteValue2 = UsbHostRestrictor.checkWriteValue();
                                    this.this$0.getClass();
                                    if (!UsbHostRestrictor.getUsbHostDisableSysNodeWritable()) {
                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver Cannot write for USB HOST DISABLE");
                                        break;
                                    } else if (!this.this$0.readDisableSysNodefromFile().contains("ON_ALL")) {
                                        if (checkWriteValue2.equals("ON_HOST_MDM")) {
                                            Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver BLOCK USB HOST");
                                            this.this$0.writeDisableSysNodetoFile(checkWriteValue2);
                                            break;
                                        }
                                    } else {
                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver USB ALL is already BLOCKED");
                                        break;
                                    }
                                }
                            } else {
                                Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver : reason - ALLOW");
                                UsbHostRestrictor.isMDMBlock = false;
                                this.this$0.getClass();
                                String checkWriteValue3 = UsbHostRestrictor.checkWriteValue();
                                this.this$0.getClass();
                                if (!UsbHostRestrictor.getUsbHostDisableSysNodeWritable()) {
                                    this.this$0.getClass();
                                    if (!UsbHostRestrictor.getUsbHostDisableSysNodeWritable()) {
                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver Cannot write for USB HOST ENABLE");
                                        break;
                                    }
                                } else if (!this.this$0.readDisableSysNodefromFile().equals("OFF")) {
                                    if (!this.this$0.readDisableSysNodefromFile().contains("ON_ALL")) {
                                        if (checkWriteValue3.equals("OFF")) {
                                            Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver UNBLOCK USB HOST");
                                            this.this$0.writeDisableSysNodetoFile(checkWriteValue3);
                                            break;
                                        }
                                    } else {
                                        Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver USB ALL is already BLOCKED");
                                        break;
                                    }
                                } else {
                                    Slog.d("UsbHostRestrictor", "UsbHostRestrictionReceiver USB is already UNBLOCKED");
                                    break;
                                }
                            }
                        }
                        break;
                    default:
                        Slog.d("UsbHostRestrictor", "Subscription Receiver onReceive");
                        if (intent.getAction().equals(Constants.SIM_STATE_CHANGED)) {
                            String stringExtra = intent.getStringExtra("ss");
                            if (!stringExtra.equals("LOADED")) {
                                Slog.d("UsbHostRestrictor", "Subscription Receiver SIM is not recognized properly : SIM_STATE_LOADED [" + stringExtra + "]");
                                break;
                            } else {
                                int activeSubscriptionInfoCount = SubscriptionManager.from(this.this$0.mContext).getActiveSubscriptionInfoCount();
                                Slog.d("UsbHostRestrictor", "Subscription Receiver Card Count is [" + activeSubscriptionInfoCount + "]");
                                if (activeSubscriptionInfoCount <= 0) {
                                    Slog.d("UsbHostRestrictor", "Subscription Receiver SIM is not recognized properly : card count is 0");
                                    break;
                                } else {
                                    UsbHostRestrictor.isSIMBlock = false;
                                    this.this$0.getClass();
                                    String checkWriteValue4 = UsbHostRestrictor.checkWriteValue();
                                    this.this$0.getClass();
                                    if (!UsbHostRestrictor.getUsbHostDisableSysNodeWritable()) {
                                        this.this$0.getClass();
                                        if (!UsbHostRestrictor.getUsbHostDisableSysNodeWritable()) {
                                            Slog.d("UsbHostRestrictor", "Subscription Receiver Cannot write for USB DISABLE");
                                            break;
                                        }
                                    } else if (!this.this$0.readDisableSysNodefromFile().equals("OFF")) {
                                        if (checkWriteValue4.equals("OFF") || checkWriteValue4.equals("ON_HOST_MDM")) {
                                            this.this$0.writeDisableSysNodetoFile(checkWriteValue4);
                                            UsbDeviceManager.UsbHandler usbHandler = this.this$0.mDeviceManager.mHandler;
                                            usbHandler.removeMessages(101);
                                            Message obtain = Message.obtain(usbHandler, 101);
                                            obtain.arg1 = 1;
                                            usbHandler.sendMessage(obtain);
                                            break;
                                        }
                                    } else {
                                        Slog.d("UsbHostRestrictor", "Subscription Receiver USB is already UNBLOCKED");
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        };
        final int i3 = 0;
        UEventObserver uEventObserver = new UEventObserver(this) { // from class: com.android.server.usb.UsbHostRestrictor.4
            public final /* synthetic */ UsbHostRestrictor this$0;

            {
                this.this$0 = this;
            }

            /* JADX WARN: Code restructure failed: missing block: B:24:0x0058, code lost:
            
                if (r7.contains("/") == false) goto L29;
             */
            /* JADX WARN: Code restructure failed: missing block: B:25:0x005a, code lost:
            
                r7 = r7.split("/");
                com.android.server.usb.UsbHostRestrictor.m1021$$Nest$mupdateVidPidList(r6.this$0, r7[0], r7[1]);
                com.android.server.usb.UsbHostRestrictor.m1022$$Nest$mwriteVpidHistorytoFile(r6.this$0, com.android.server.usb.UsbHostRestrictor.mUsbConHist);
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onUEvent(android.os.UEventObserver.UEvent r7) {
                /*
                    Method dump skipped, instructions count: 488
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.usb.UsbHostRestrictor.AnonymousClass4.onUEvent(android.os.UEventObserver$UEvent):void");
            }
        };
        final int i4 = 1;
        UEventObserver uEventObserver2 = new UEventObserver(this) { // from class: com.android.server.usb.UsbHostRestrictor.4
            public final /* synthetic */ UsbHostRestrictor this$0;

            {
                this.this$0 = this;
            }

            public final void onUEvent(UEventObserver.UEvent uEvent) {
                /*
                    Method dump skipped, instructions count: 488
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.usb.UsbHostRestrictor.AnonymousClass4.onUEvent(android.os.UEventObserver$UEvent):void");
            }
        };
        final int i5 = 2;
        UEventObserver uEventObserver3 = new UEventObserver(this) { // from class: com.android.server.usb.UsbHostRestrictor.4
            public final /* synthetic */ UsbHostRestrictor this$0;

            {
                this.this$0 = this;
            }

            public final void onUEvent(UEventObserver.UEvent uEvent) {
                /*
                    Method dump skipped, instructions count: 488
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.usb.UsbHostRestrictor.AnonymousClass4.onUEvent(android.os.UEventObserver$UEvent):void");
            }
        };
        this.mContext = context;
        this.mDeviceManager = usbDeviceManager;
        uEventObserver.startObserving("DEVPATH=/devices/virtual/host_notify");
        context.registerReceiver(broadcastReceiver, new IntentFilter("com.samsung.android.knox.intent.action.UPDATE_ALLOW_USB_HOST_STORAGE_STATE_INTERNAL"));
        context.registerReceiver(broadcastReceiver2, new IntentFilter(Constants.SIM_STATE_CHANGED));
        this.emm = new EngineeringModeManager(context);
        uEventObserver2.startObserving("DEVPATH=/devices/virtual/android_usb/android0");
        uEventObserver3.startObserving("DEVTYPE=usb_interface");
        misRunScreenLockTimer = false;
        new Thread(new LockTimer()).start();
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("block_usb_lock"), false, new ContentObserver() { // from class: com.android.server.usb.UsbHostRestrictor.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                synchronized (UsbHostRestrictor.this.mUsbRestrictLock) {
                    try {
                        int i6 = Settings.Secure.getInt(UsbHostRestrictor.this.mContext.getContentResolver(), "block_usb_lock", 1);
                        UsbHostRestrictor.mSettingBlockUsbLock = i6;
                        if (UsbHostRestrictor.mSecureKeyguardShowing && i6 == 1) {
                            UsbHostRestrictor.this.getClass();
                            if (!UsbHostRestrictor.isAdbOnlyMode()) {
                                Slog.d("UsbHostRestrictor", "changed setting LOCK_SCREEN_BLOCK : OFF -> ON");
                                UsbHostRestrictor.mLockStatus = 0;
                            }
                        }
                        if (UsbHostRestrictor.mSecureKeyguardShowing && UsbHostRestrictor.mSettingBlockUsbLock == 0) {
                            Slog.d("UsbHostRestrictor", "changed setting LOCK_SCREEN_BLOCK : ON -> OFF");
                            UsbHostRestrictor.this.writeScrLckBlkSysNodetoFile("1");
                            UsbHostRestrictor.mLockStatus = 3;
                            UsbHostRestrictor.this.getClass();
                            UsbHostRestrictor.stopLockTimer();
                        } else {
                            Slog.d("UsbHostRestrictor", "can't change block status (none-lock or adb-only or unknown setting value)");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        });
    }

    public static boolean checkUsbBlockingCondition(String str) {
        String salesCode = getSalesCode();
        if ("null".equals(salesCode) || salesCode == null) {
            Slog.d("UsbHostRestrictor", "checkUsbBlockingCondition : salesCode is null");
            salesCode = "OXA";
        }
        if (FactoryTest.isFactoryBinary()) {
            Slog.d("UsbHostRestrictor", "NOT DISABLE USB 4");
            return false;
        }
        if (!"CHM".equals(salesCode) && !"CBK".equals(salesCode)) {
            if (str.equals("ON_HOST_MDM") || str.equals("ON_ALL_SCREEN")) {
                Slog.d("UsbHostRestrictor", "DISABLE USB for MDM block or ON_ALL_SCREEN");
                return true;
            }
            Slog.d("UsbHostRestrictor", "NOT DISABLE USB 3");
            return false;
        }
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

    public static String checkWriteValue() {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0144 A[Catch: IOException -> 0x0140, TRY_LEAVE, TryCatch #14 {IOException -> 0x0140, blocks: (B:38:0x013c, B:30:0x0144), top: B:37:0x013c }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x013c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x011d A[Catch: IOException -> 0x0119, TRY_LEAVE, TryCatch #17 {IOException -> 0x0119, blocks: (B:50:0x0115, B:44:0x011d), top: B:49:0x0115 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0115 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00f4 A[Catch: IOException -> 0x00f0, TRY_LEAVE, TryCatch #5 {IOException -> 0x00f0, blocks: (B:62:0x00ec, B:56:0x00f4), top: B:61:0x00ec }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00ec A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x015a A[Catch: IOException -> 0x0156, TRY_LEAVE, TryCatch #19 {IOException -> 0x0156, blocks: (B:74:0x0152, B:67:0x015a), top: B:73:0x0152 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0152 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v12, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v26 */
    /* JADX WARN: Type inference failed for: r0v27 */
    /* JADX WARN: Type inference failed for: r0v28 */
    /* JADX WARN: Type inference failed for: r0v29 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v30, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v35 */
    /* JADX WARN: Type inference failed for: r0v36 */
    /* JADX WARN: Type inference failed for: r0v37 */
    /* JADX WARN: Type inference failed for: r0v38 */
    /* JADX WARN: Type inference failed for: r0v39 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v40 */
    /* JADX WARN: Type inference failed for: r0v41 */
    /* JADX WARN: Type inference failed for: r0v42 */
    /* JADX WARN: Type inference failed for: r0v45 */
    /* JADX WARN: Type inference failed for: r0v46 */
    /* JADX WARN: Type inference failed for: r0v47 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.io.BufferedReader] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getSalesCode() {
        /*
            Method dump skipped, instructions count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.usb.UsbHostRestrictor.getSalesCode():java.lang.String");
    }

    public static boolean getUsbHostDisableSysNodeWritable() {
        return BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/usb_notify/usb_control/disable");
    }

    public static boolean isAdbOnlyMode() {
        return SystemProperties.get("persist.sys.usb.config", "none").equals("adb");
    }

    public static boolean isSupportDexRestrict() {
        File file = new File("/sys/class/usb_notify/usb_control/whitelist_for_mdm");
        Slog.d("UsbHostRestrictor", "isSupportDexRestrict [" + file.exists() + ", " + file.isFile() + ", " + file.canWrite() + "]");
        return file.exists() && file.isFile() && file.canWrite();
    }

    public static void startLockTimer() {
        Slog.d("UsbHostRestrictor", "startLockTimer++");
        misRunScreenLockTimer = false;
        startTime = System.currentTimeMillis();
        misRunScreenLockTimer = true;
    }

    public static void stopLockTimer() {
        Slog.d("UsbHostRestrictor", "stopLockTimer--");
        misRunScreenLockTimer = false;
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m$1 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "USB Host Restrictor State:", "  All SIM Count:");
        m$1.append(SubscriptionManager.from(this.mContext).getAllSubscriptionInfoList().size());
        printWriter.println(m$1.toString());
        printWriter.println("  Disable Sys Node Value :" + readDisableSysNodefromFile());
        printWriter.println("  Disable Sys Node Writable :" + String.valueOf(getUsbHostDisableSysNodeWritable()));
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, mCurrentScrLckNodeValue, "  ScreenLockStateValue :", BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, mCurrentSysNodeValue, "  SIM BLOCK ON/OFF :", new StringBuilder("  mCurrentSysNodeValue :")), isSIMBlock, printWriter, "  MDM BLOCK ON/OFF :"), isMDMBlock, printWriter, "  LckScr BLOCK ON/OFF :"), isLckScrBlock, printWriter, "  CurrentScrLckStateValue :")), mLockStatus, printWriter, "  IsHostConnected :"), mIsHostConnected, printWriter, "  IsDeviceConnected :"), mIsDeviceConnected, printWriter, "  SettingBlockUsbLock :"), mSettingBlockUsbLock, printWriter, "  IsTKInstalled :"), isEMTokenEnabled, printWriter, "  MDM bRestrictHostAPI :"), bRestrictHostAPI, printWriter, "  MDM mStrAllowList :"), mStrAllowList, printWriter);
    }

    public final boolean isFinishLockTimer() {
        if (misRunScreenLockTimer) {
            Slog.d("UsbHostRestrictor", "isFinishLockTimer");
            long currentTimeMillis = System.currentTimeMillis() - startTime;
            if (this.emm.isConnected()) {
                if (this.emm.getStatus(4) == 1) {
                    Slog.d("UsbHostRestrictor", "TK ENABLED");
                    isEMTokenEnabled = true;
                } else {
                    Slog.d("UsbHostRestrictor", "TK NOT ENABLED");
                    isEMTokenEnabled = false;
                }
            }
            if (currentTimeMillis >= 15000 && mLockStatus == 1 && !"1".equals(SystemProperties.get("persist.sys.auto_confirm", "0")) && !isEMTokenEnabled) {
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
                        } else if (!readDisableSysNodefromFile.contains("ON_ALL_SIM")) {
                            Slog.d("UsbHostRestrictor", "UsbHostRestrictor USB ALL is already BLOCKED by ".concat(readDisableSysNodefromFile));
                        } else if (checkWriteValue.equals("ON_ALL_BOTH")) {
                            Slog.d("UsbHostRestrictor", "UsbHostRestrictor set SCR LCK BLCK");
                            writeDisableSysNodetoFile(checkWriteValue);
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

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public final void onAwakeStateChanged(boolean z) {
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public final void onKeyguardStateChanged(boolean z) {
        synchronized (this.mUsbRestrictLock) {
            int currentUser = ActivityManager.getCurrentUser();
            int i = 0;
            boolean z2 = z && ((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceSecure(currentUser);
            if (mSecureKeyguardShowing != z2) {
                mSecureKeyguardShowing = z2;
            }
            try {
                FileUtils.readTextFile(new File("/sys/class/usb_notify/usb_control/usb_sl"), 0, null).getClass();
            } catch (IOException unused) {
                Slog.e("UsbHostRestrictor", "Failed to read from ScrLck Block FILE");
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
            if (z2 && mLockStatus == 0) {
                while (true) {
                    if (((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceLocked(currentUser)) {
                        Slog.d("UsbHostRestrictor", "onKeyguardStateChanged: Screen Lock On");
                        writeScrLckBlkSysNodetoFile("2");
                        mLockStatus = 1;
                        if (!mIsDeviceConnected && !mIsHostConnected) {
                            startLockTimer();
                            Slog.d("UsbHostRestrictor", "onKeyguardStateChanged: Block immediately");
                            isLckScrBlock = true;
                            mLockStatus = 2;
                            writeScrLckBlkSysNodetoFile("2");
                            String checkWriteValue = checkWriteValue();
                            if (getUsbHostDisableSysNodeWritable()) {
                                String readDisableSysNodefromFile = readDisableSysNodefromFile();
                                if (readDisableSysNodefromFile.contains("ON_ALL")) {
                                    if (!readDisableSysNodefromFile.contains("ON_ALL_BOTH") && !readDisableSysNodefromFile.contains("ON_ALL_SCREEN")) {
                                        if (!readDisableSysNodefromFile.contains("ON_ALL_SIM")) {
                                            Slog.d("UsbHostRestrictor", "onKeyguardStateChanged USB ALL is already BLOCKED by " + readDisableSysNodefromFile);
                                        } else if (checkWriteValue.equals("ON_ALL_BOTH")) {
                                            Slog.d("UsbHostRestrictor", "onKeyguardStateChanged set SCR LCK BLCK");
                                            writeDisableSysNodetoFile(checkWriteValue);
                                        }
                                    }
                                    Slog.d("UsbHostRestrictor", "onKeyguardStateChanged USB ALL is already BLOCKED by SCR LCK BLCK");
                                } else if (checkWriteValue.equals("ON_ALL_SCREEN")) {
                                    Slog.d("UsbHostRestrictor", "onKeyguardStateChanged set SCR LCK BLCK");
                                    writeDisableSysNodetoFile(checkWriteValue);
                                }
                            }
                        }
                    } else {
                        i++;
                        if (i > 4) {
                            break;
                        }
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (!z2) {
                Slog.d("UsbHostRestrictor", "onKeyguardStateChanged: Screen Lock Off");
                if (mLockStatus == 2) {
                    isLckScrBlock = false;
                    String checkWriteValue2 = checkWriteValue();
                    if (getUsbHostDisableSysNodeWritable() && (readDisableSysNodefromFile().contains("ON_ALL_SCREEN") || readDisableSysNodefromFile().contains("ON_ALL_BOTH"))) {
                        writeDisableSysNodetoFile(checkWriteValue2);
                    }
                }
                writeScrLckBlkSysNodetoFile("0");
                mLockStatus = 0;
                stopLockTimer();
            }
        }
    }

    public final String readDisableSysNodefromFile() {
        String str;
        try {
            str = FileUtils.readTextFile(new File("/sys/class/usb_notify/usb_control/disable"), 0, null).trim();
        } catch (IOException unused) {
            Slog.e("UsbHostRestrictor", "Failed to read from DISABLE FILE");
            str = "";
        }
        UsbDeviceManager usbDeviceManager = this.mDeviceManager;
        if (str == null || !str.contains("ON")) {
            mCurrentSysNodeValue = "OFF";
            usbDeviceManager.getClass();
            UsbDeviceManager.usbDisableSettingVal = "OFF";
        } else {
            mCurrentSysNodeValue = str;
            usbDeviceManager.getClass();
            UsbDeviceManager.usbDisableSettingVal = str;
        }
        return str;
    }

    public final void writeDisableSysNodetoFile(String str) {
        UsbDeviceManager usbDeviceManager = this.mDeviceManager;
        Slog.d("UsbHostRestrictor", "Write Disable Sys Node with [" + str + "]");
        try {
            if (checkUsbBlockingCondition(str)) {
                FileUtils.stringToFile("/sys/class/usb_notify/usb_control/disable", str);
                mCurrentSysNodeValue = str;
                usbDeviceManager.getClass();
                UsbDeviceManager.usbDisableSettingVal = str;
                UsbDeviceManager.UsbHandler usbHandler = usbDeviceManager.mHandler;
                usbHandler.removeMessages(101);
                Message obtain = Message.obtain(usbHandler, 101);
                obtain.arg1 = 1;
                usbHandler.sendMessage(obtain);
            } else {
                FileUtils.stringToFile("/sys/class/usb_notify/usb_control/disable", "OFF");
                mCurrentSysNodeValue = "OFF";
                usbDeviceManager.getClass();
                UsbDeviceManager.usbDisableSettingVal = "OFF";
            }
        } catch (IOException e) {
            Slog.e("UsbHostRestrictor", "Failed to write to DISABLE FILE");
            Slog.d("UsbHostRestrictor", "IOException : " + e);
            mCurrentSysNodeValue = "OFF";
            usbDeviceManager.getClass();
            UsbDeviceManager.usbDisableSettingVal = "OFF";
        }
    }

    public final void writeScrLckBlkSysNodetoFile(String str) {
        Slog.d("UsbHostRestrictor", "Write ScrLck Block FILE");
        if (this.emm.isConnected() && "2".equals(str)) {
            if (this.emm.getStatus(4) == 1) {
                Slog.d("UsbHostRestrictor", "TK ENABLED 2");
                isEMTokenEnabled = true;
            } else {
                Slog.d("UsbHostRestrictor", "TK NOT ENABLED 2");
                isEMTokenEnabled = false;
            }
        }
        if (("1".equals(SystemProperties.get("persist.sys.auto_confirm", "0")) || isEMTokenEnabled) && "2".equals(str)) {
            Slog.d("UsbHostRestrictor", "usb debug mode on");
            isLckScrBlock = false;
            String checkWriteValue = checkWriteValue();
            if (getUsbHostDisableSysNodeWritable() && (readDisableSysNodefromFile().contains("ON_ALL_SCREEN") || readDisableSysNodefromFile().contains("ON_ALL_BOTH"))) {
                writeDisableSysNodetoFile(checkWriteValue);
            }
            str = "1";
        }
        try {
            FileUtils.stringToFile("/sys/class/usb_notify/usb_control/usb_sl", str);
            mCurrentScrLckNodeValue = str;
        } catch (IOException e) {
            Slog.e("UsbHostRestrictor", "Failed to write to ScrLck Block FILE");
            Slog.d("UsbHostRestrictor", "IOException : " + e);
            mCurrentScrLckNodeValue = "0";
        }
    }
}
