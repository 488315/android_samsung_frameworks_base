package com.android.server.usb;

import android.R;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbPortStatus;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.sysfwutil.Slog;
import android.widget.Toast;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.Preconditions;
import com.android.server.LocalServices;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.feature.SemFloatingFeature;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/* loaded from: classes3.dex */
public class UsbUI implements ActivityTaskManagerInternal.ScreenObserver {
    public final BroadcastReceiver mBatteryEventReceiver;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final BroadcastReceiver mEmergencyModeReceiver;
    public final UsbUIHandler mHandler;
    public final UEventObserver mHostInterfaceObserver;
    public final UEventObserver mHostPathObserver;
    public final BroadcastReceiver mLocaleChangedReceiver;
    public final Object mLoggingLock;
    public NotificationManager mNotificationManager;
    public final BroadcastReceiver mPortReceiver;
    public final PowerManager mPowerManager;
    public boolean mSupportDualRole;
    public final UEventObserver mUsbCcicObserver;
    public final UEventObserver mUsbControlObserver;
    public final BroadcastReceiver mUsbWetStateReceiver;
    public final PowerManager.WakeLock mWakeLock;
    public boolean mSystemReady = false;
    public boolean mBootCompleted = false;
    public boolean mIsEmergencyMode = false;
    public boolean mIsHostConnected = false;
    public boolean mSourcePower = false;
    public boolean mIsHiccupState = false;
    public boolean mIsUsbPortWet = false;
    public boolean isSupportWirelessCharging = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_WIRELESS_HV");
    public boolean mIsUsbBlkNotiShown = false;
    public String mUsbBlkPendingIntent = "";

    public static boolean areSettableOptions(long j) {
        return j == 0 || (j & (-512)) == 0;
    }

    public static boolean isIncludeOption(long j, long j2) {
        return (j & j2) != 0;
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public void onAwakeStateChanged(boolean z) {
    }

    public UsbUI(Context context, Looper looper) {
        this.mSupportDualRole = false;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.usb.UsbUI.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Slog.d("UsbUI", "mLocalechangedReceiver (" + intent + ")");
                UsbUI.this.updateChangedLocale();
            }
        };
        this.mLocaleChangedReceiver = broadcastReceiver;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.server.usb.UsbUI.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String string;
                Slog.d("UsbUI", "mUsbWetStateReceiver (" + intent + ")");
                UsbUI.this.mContext.sendBroadcastAsUser(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"), UserHandle.ALL);
                String string2 = UsbUI.this.mContext.getString(17043188);
                String string3 = UsbUI.this.mContext.getString(17043196);
                if (UsbUI.this.isSupportWirelessCharging) {
                    string = UsbUI.this.mContext.getString(17043197);
                } else {
                    string = UsbUI.this.mContext.getString(17043195);
                }
                String replace = string.replace("\\n", KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                AlertDialog.Builder builder = new AlertDialog.Builder(UsbUI.this.mContext, (UsbUI.this.mContext.getResources().getConfiguration().uiMode & 48) == 32 ? 4 : 5);
                builder.setCancelable(false);
                builder.setTitle(string2);
                builder.setMessage(replace);
                builder.setPositiveButton(string3, new DialogInterface.OnClickListener() { // from class: com.android.server.usb.UsbUI.2.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (dialogInterface != null) {
                            dialogInterface.dismiss();
                        }
                    }
                });
                AlertDialog create = builder.create();
                create.getWindow().setType(2008);
                create.show();
            }
        };
        this.mUsbWetStateReceiver = broadcastReceiver2;
        BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() { // from class: com.android.server.usb.UsbUI.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Slog.d("UsbUI", "mPortReceiver (" + intent + ")");
                boolean z = UsbUI.this.mSourcePower;
                UsbPortStatus parcelableExtra = intent.getParcelableExtra("portStatus");
                if (parcelableExtra == null) {
                    Slog.d("UsbUI", "UsbPortStatus is null");
                    UsbUI.this.mSourcePower = false;
                } else {
                    UsbUI.this.mSourcePower = parcelableExtra.getCurrentPowerRole() == 1;
                }
                Slog.d("UsbUI", "oldSourcePower=" + z + " mSourcePower=" + UsbUI.this.mSourcePower);
                if (z == UsbUI.this.mSourcePower || !UsbUI.this.mIsEmergencyMode) {
                    return;
                }
                UsbUI usbUI = UsbUI.this;
                usbUI.notifyIncreaseBatteryUsage(usbUI.mSourcePower);
            }
        };
        this.mPortReceiver = broadcastReceiver3;
        BroadcastReceiver broadcastReceiver4 = new BroadcastReceiver() { // from class: com.android.server.usb.UsbUI.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                boolean z = UsbUI.this.mIsHiccupState;
                UsbUI.this.mIsHiccupState = (intent.getIntExtra("misc_event", 0) & 32) != 0;
                if (z != UsbUI.this.mIsHiccupState) {
                    Slog.d("UsbUI", "oldHiccupState=" + z + " mIsHiccupState=" + UsbUI.this.mIsHiccupState);
                    UsbUI usbUI = UsbUI.this;
                    usbUI.loggingUsbWetDetection(usbUI.mIsHiccupState ? "CD" : "CR");
                }
            }
        };
        this.mBatteryEventReceiver = broadcastReceiver4;
        BroadcastReceiver broadcastReceiver5 = new BroadcastReceiver() { // from class: com.android.server.usb.UsbUI.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                Slog.d("UsbUI", "mEmergencyModeReceiver (" + intent + ")");
                int intExtra = intent.getIntExtra("reason", 0);
                if (intExtra == 3) {
                    Slog.d("UsbUI", "EmergencyMode enabled");
                    UsbUI.this.mIsEmergencyMode = true;
                    if (UsbUI.this.mSourcePower || (!UsbUI.this.mSupportDualRole && UsbUI.this.mIsHostConnected)) {
                        UsbUI.this.notifyIncreaseBatteryUsage(true);
                        return;
                    }
                    return;
                }
                if (intExtra == 5) {
                    Slog.d("UsbUI", "EmergencyMode disabled");
                    UsbUI.this.mIsEmergencyMode = false;
                    if (UsbUI.this.mSourcePower || (!UsbUI.this.mSupportDualRole && UsbUI.this.mIsHostConnected)) {
                        UsbUI.this.notifyIncreaseBatteryUsage(false);
                    }
                }
            }
        };
        this.mEmergencyModeReceiver = broadcastReceiver5;
        UEventObserver uEventObserver = new UEventObserver() { // from class: com.android.server.usb.UsbUI.6
            public void onUEvent(UEventObserver.UEvent uEvent) {
                Slog.v("UsbUI", "onUEvent(Host Path): " + uEvent);
                String str = uEvent.get("ACTION");
                String str2 = uEvent.get("STATE");
                if ("remove".equals(str)) {
                    UsbUI.this.cancelAllHostNotification();
                    return;
                }
                if (!"change".equals(str) || str2 == null) {
                    return;
                }
                if (str2.equals("OVERCURRENT")) {
                    UsbUI.this.postNotification(112, 17043126, 17043125, 17304254, 0L);
                } else if (str2.equals("UNKNOWN")) {
                    UsbUI.this.postNotification(113, 17043122, 0, 17304255, 0L);
                }
                if ("ADD".equals(str2) || "REMOVE".equals(str2)) {
                    UsbUI.this.mIsHostConnected = "ADD".equals(str2);
                    if (UsbUI.this.mSupportDualRole || !UsbUI.this.mIsEmergencyMode) {
                        return;
                    }
                    UsbUI usbUI = UsbUI.this;
                    usbUI.notifyIncreaseBatteryUsage(usbUI.mIsHostConnected);
                }
            }
        };
        this.mHostPathObserver = uEventObserver;
        UEventObserver uEventObserver2 = new UEventObserver() { // from class: com.android.server.usb.UsbUI.7
            public void onUEvent(UEventObserver.UEvent uEvent) {
                Slog.d("UsbUI", "onUEvent(Host Interface): " + uEvent);
                String str = uEvent.get("ACTION");
                String str2 = uEvent.get("INTERFACE");
                String str3 = uEvent.get("WARNING");
                if ("unsupport_accessory".equals(str3)) {
                    UsbUI.this.postNotification(114, 17043177, 17043176, 1L);
                    return;
                }
                if ("no_response".equals(str3)) {
                    UsbUI.this.postNotification(115, 17043147, 17043146, 1L);
                    return;
                }
                if ("hub_depth_exceed".equals(str3)) {
                    UsbUI.this.postNotification(116, 17043130, 17043129, 1L);
                    return;
                }
                if ("hub_power_exceed".equals(str3)) {
                    UsbUI.this.postNotification(117, 17043147, 17043131, 1L);
                    return;
                }
                if ("host_resource_exceed".equals(str3)) {
                    UsbUI.this.postNotification(118, 17043128, 17043127, 1L);
                    return;
                }
                try {
                    String[] split = str2.split("/");
                    String str4 = split[0];
                    String str5 = split[1];
                    String str6 = split[2];
                    if (str4 != null && str5 != null && str6 != null) {
                        UsbUI.this.notifyUsbInterface(str, Integer.parseInt(str4), Integer.parseInt(str5), Integer.parseInt(str6));
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    Slog.e("UsbUI", "ArrayIndexOutOfBoundsException " + e);
                } catch (NumberFormatException unused) {
                    Slog.e("UsbUI", "Could not parse state or devPath from event " + uEvent);
                }
            }
        };
        this.mHostInterfaceObserver = uEventObserver2;
        UEventObserver uEventObserver3 = new UEventObserver() { // from class: com.android.server.usb.UsbUI.8
            public void onUEvent(UEventObserver.UEvent uEvent) {
                Slog.d("UsbUI", "onUEvent(USB Control): " + uEvent);
                String str = uEvent.get("TYPE");
                String str2 = uEvent.get("STATE");
                if ("usberr".equals(str)) {
                    String str3 = uEvent.get("WORDS");
                    str3.hashCode();
                    if (str3.equals("abnormal_reset")) {
                        if ("ADD".equals(str2)) {
                            UsbUI.this.makeLongToast(17043101);
                            UsbUI.this.postNotification(109, 17043100, 17043099, 12L);
                            return;
                        } else {
                            if ("REMOVE".equals(str2)) {
                                UsbUI.this.cancelNotification(109);
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                if ("usbrestrict".equals(str)) {
                    String str4 = uEvent.get("WORDS");
                    str4.hashCode();
                    switch (str4) {
                        case "securerelease":
                            if ("ADD".equals(str2)) {
                                Slog.d("UsbUI", "onUEvent(USB Control): Cancel USB BLK Notification");
                                UsbUI.this.notifyUsbRestrict(false);
                                UsbUI.this.sendingUsbRestrictionStateIntent("OFF");
                                break;
                            }
                            break;
                        case "securerestrict":
                            if ("ADD".equals(str2)) {
                                Slog.d("UsbUI", "onUEvent(USB Control): Show USB BLK Toast");
                                UsbUI.this.makeLongToast(17043174);
                                break;
                            }
                            break;
                        case "timesecurerestrict":
                            if ("ADD".equals(str2)) {
                                Slog.d("UsbUI", "onUEvent(USB Control): Show USB BLK Notification");
                                UsbUI.this.notifyUsbRestrict(true);
                                UsbUI.this.sendingUsbRestrictionStateIntent("ON");
                                break;
                            }
                            break;
                    }
                }
            }
        };
        this.mUsbControlObserver = uEventObserver3;
        UEventObserver uEventObserver4 = new UEventObserver() { // from class: com.android.server.usb.UsbUI.9
            public void onUEvent(UEventObserver.UEvent uEvent) {
                String str = uEvent.get("SWITCH_STATE");
                String str2 = uEvent.get("CCIC");
                if (str == null && str2 == null) {
                    return;
                }
                Slog.d("UsbUI", "onUEvent(USB CCIC): " + uEvent);
                if ("115".equals(str)) {
                    UsbUI.this.makeLongToast(17043180);
                    UsbUI.this.postNotification(108, 17043181, 17043180, 14L);
                } else if ("0".equals(str)) {
                    UsbUI.this.cancelNotification(108);
                }
                boolean z = UsbUI.this.mIsUsbPortWet;
                if ("WATER".equals(str2)) {
                    UsbUI.this.mIsUsbPortWet = true;
                } else if ("DRY".equals(str2)) {
                    UsbUI.this.mIsUsbPortWet = false;
                }
                if (UsbUI.this.mIsUsbPortWet != z) {
                    UsbUI usbUI = UsbUI.this;
                    usbUI.notifyUsbWetDetection(usbUI.mIsUsbPortWet);
                }
            }
        };
        this.mUsbCcicObserver = uEventObserver4;
        this.mLoggingLock = new Object();
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mHandler = new UsbUIHandler(looper);
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.mPowerManager = powerManager;
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "UsbUI");
        this.mWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        this.mSupportDualRole = new File("/sys/class/typec").exists();
        uEventObserver.startObserving("DEVPATH=/devices/virtual/host_notify");
        uEventObserver2.startObserving("DEVTYPE=usb_interface");
        uEventObserver4.startObserving("DEVPATH=/devices/virtual/sec/ccic");
        uEventObserver3.startObserving("DEVPATH=/devices/virtual/usb_notify/usb_control");
        context.registerReceiver(broadcastReceiver, new IntentFilter("android.intent.action.LOCALE_CHANGED"));
        context.registerReceiver(broadcastReceiver2, new IntentFilter("com.samsung.intent.action.USB_WET_STATE"));
        context.registerReceiver(broadcastReceiver3, new IntentFilter("android.hardware.usb.action.USB_PORT_CHANGED"));
        context.registerReceiver(broadcastReceiver4, new IntentFilter("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT"));
        context.registerReceiver(broadcastReceiver5, new IntentFilter("com.samsung.intent.action.EMERGENCY_STATE_CHANGED"));
    }

    public void systemReady() {
        Slog.d("UsbUI", "system ready");
        this.mNotificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).registerScreenObserver(this);
        this.mSystemReady = true;
        if (isUsbWet()) {
            this.mIsUsbPortWet = true;
            notifyUsbWetDetection(true);
        }
        this.mIsEmergencyMode = Settings.System.getIntForUser(this.mContentResolver, "emergency_mode", 0, -2) != 0;
    }

    public void bootCompleted() {
        Slog.d("UsbUI", "boot completed");
        this.mBootCompleted = true;
        if (!"".equals(this.mUsbBlkPendingIntent)) {
            sendingUsbRestrictionStateIntent(this.mUsbBlkPendingIntent);
        }
        for (UsbDevice usbDevice : ((UsbManager) this.mContext.getSystemService("usb")).getDeviceList().values()) {
            int interfaceCount = usbDevice.getInterfaceCount();
            for (int i = 0; i < interfaceCount; i++) {
                UsbInterface usbInterface = usbDevice.getInterface(i);
                notifyUsbInterface("add", usbInterface.getInterfaceClass(), usbInterface.getInterfaceSubclass(), usbInterface.getInterfaceProtocol());
            }
        }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public void onKeyguardStateChanged(boolean z) {
        int currentUser = ActivityManager.getCurrentUser();
        if ((z && ((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceSecure(currentUser) && ((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceLocked(currentUser)) || !this.mIsUsbBlkNotiShown) {
            return;
        }
        Slog.d("UsbUI", "Clear USB Restriction Noti by Screen Unlock");
        notifyUsbRestrict(false);
        sendingUsbRestrictionStateIntent("OFF");
    }

    public final void sendingUsbRestrictionStateIntent(String str) {
        if (this.mBootCompleted) {
            try {
                Intent intent = new Intent("com.samsung.intent.action.USB_RESTRICTION_STATE");
                intent.putExtra("RestrictionState", str);
                Slog.d("UsbUI", "broadcasting intent to Lock Screen");
                this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                return;
            } catch (Exception unused) {
                Slog.e("UsbUI", "Failed to broadcast intent to Lock Screen");
                return;
            }
        }
        this.mUsbBlkPendingIntent = str;
        Slog.d("UsbUI", "Pending intent to Lock Screen");
    }

    public final void cancelAllHostNotification() {
        cancelNotification(112);
        cancelNotification(113);
    }

    public final void notifyUsbInterface(String str, int i, int i2, int i3) {
        if (str.equals("add") || str.equals("remove")) {
            Slog.d("UsbUI", String.format("notifyUsbInterface: [%d, %d, %d]", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
            if (str.equals("add")) {
                if (i == 6) {
                    postNotification(124, 17043109, 0, 2L);
                }
            } else if (str.equals("remove") && i == 6) {
                cancelNotification(124);
                postNotification(111, 17043110, 0, 3L);
            }
        }
    }

    public final void turnOnDisplay() {
        Slog.d("UsbUI", "turnOnDisplay()");
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            wakeLock.acquire(6000L);
        }
        PowerManager powerManager = this.mPowerManager;
        if (powerManager != null) {
            powerManager.wakeUp(SystemClock.uptimeMillis(), 3, "UsbUI");
            this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 0, 0);
        }
    }

    public final void notifyUsbWetDetection(boolean z) {
        if (z) {
            String string = this.mContext.getString(17043200);
            PendingIntent broadcastAsUser = PendingIntent.getBroadcastAsUser(this.mContext, 0, new Intent("com.samsung.intent.action.USB_WET_STATE"), 67108864, UserHandle.ALL);
            turnOnDisplay();
            postNotification(102, 17043201, 17043198, 17304211, new Notification.Action(0, string, broadcastAsUser), 14L);
            loggingUsbWetDetection("WD");
            return;
        }
        cancelNotification(102);
        loggingUsbWetDetection("DD");
    }

    public final boolean isUsbWet() {
        try {
            File file = new File("/sys/class/sec/ccic/water");
            if (file.exists()) {
                String trim = FileUtils.readTextFile(file, 0, null).trim();
                Slog.d("UsbUI", "isUsbWet: state=" + trim);
                if ("1".equals(trim)) {
                    return true;
                }
            }
        } catch (Exception e) {
            Slog.e("UsbUI", "Can't read /sys/class/sec/ccic/water", e);
        }
        return false;
    }

    public final void loggingUsbWetDetection(String str) {
        String str2;
        String str3;
        String format;
        FileWriter fileWriter;
        synchronized (this.mLoggingLock) {
            FileWriter fileWriter2 = null;
            try {
                try {
                    format = new SimpleDateFormat("yyyy/MM/dd/HH:mm").format(Calendar.getInstance().getTime());
                    File file = new File("/data/log/wet_detect.log");
                    file.setReadable(true, false);
                    fileWriter = new FileWriter(file, true);
                } catch (IOException unused) {
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                fileWriter.write(String.format("%s %s%n", format, str));
                try {
                    fileWriter.close();
                } catch (IOException unused2) {
                    str2 = "UsbUI";
                    str3 = "Can't close stream";
                    Slog.e(str2, str3);
                }
            } catch (IOException unused3) {
                fileWriter2 = fileWriter;
                Slog.e("UsbUI", "Can't write to /data/log/wet_detect.log");
                if (fileWriter2 != null) {
                    try {
                        fileWriter2.close();
                    } catch (IOException unused4) {
                        str2 = "UsbUI";
                        str3 = "Can't close stream";
                        Slog.e(str2, str3);
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                fileWriter2 = fileWriter;
                if (fileWriter2 != null) {
                    try {
                        fileWriter2.close();
                    } catch (IOException unused5) {
                        Slog.e("UsbUI", "Can't close stream");
                    }
                }
                throw th;
            }
        }
    }

    public final void notifyIncreaseBatteryUsage(boolean z) {
        if (z) {
            makeShortToast(17043132);
            postNotification(107, 0, 17043132, 138L);
        } else {
            cancelNotification(107);
        }
    }

    public final void notifyUsbRestrict(boolean z) {
        if (z) {
            this.mIsUsbBlkNotiShown = true;
            postNotification(125, 17043175, 0, 17304252, 138L);
        } else {
            this.mIsUsbBlkNotiShown = false;
            cancelNotification(125);
        }
    }

    public final void makeShortToast(int i) {
        if (this.mSystemReady) {
            this.mHandler.sendMessage(0, i);
        }
    }

    public final void makeLongToast(int i) {
        if (this.mSystemReady) {
            this.mHandler.sendMessage(1, i);
        }
    }

    public final void postNotification(int i, int i2, int i3, long j) {
        postNotificationInternal(i, i2, i3, 17304253, null, null, j);
    }

    public final void postNotification(int i, int i2, int i3, int i4, long j) {
        postNotificationInternal(i, i2, i3, i4, null, null, j);
    }

    public final void postNotification(int i, int i2, int i3, int i4, Notification.Action action, long j) {
        postNotificationInternal(i, i2, i3, i4, null, action, j);
    }

    public final void cancelNotification(int i) {
        if (!this.mSystemReady || this.mNotificationManager == null) {
            return;
        }
        this.mHandler.sendMessage(4, i);
    }

    public final void postNotificationInternal(int i, int i2, int i3, int i4, PendingIntent pendingIntent, Notification.Action action, long j) {
        Preconditions.checkArgument(areSettableOptions(j));
        NotificationWrapper notificationWrapper = new NotificationWrapper(i2, i3, i4, pendingIntent, action, j);
        if (this.mSystemReady && this.mNotificationManager != null) {
            if (notificationWrapper.instant || notificationWrapper.ongoing) {
                this.mHandler.sendMessage(2, i, notificationWrapper);
                return;
            } else {
                this.mNotificationManager.notifyAsUser(null, i, notificationWrapper.build(), UserHandle.ALL);
                return;
            }
        }
        Slog.d("UsbUI", "Before system ready: title=" + this.mContext.getString(i2));
    }

    public final void updateChangedLocale() {
        this.mHandler.sendEmptyMessage(5);
    }

    public final class UsbUIHandler extends Handler {
        public final HashMap currentOngoings;
        public final HashMap pendingJobs;

        public UsbUIHandler(Looper looper) {
            super(looper);
            this.pendingJobs = new HashMap();
            this.currentOngoings = new HashMap();
        }

        public void sendMessage(int i, int i2) {
            Message obtain = Message.obtain(this, i);
            obtain.arg1 = i2;
            sendMessage(obtain);
        }

        public void sendMessage(int i, int i2, Object obj) {
            Message obtain = Message.obtain(this, i);
            obtain.arg1 = i2;
            obtain.obj = obj;
            sendMessage(obtain);
        }

        public void sendMessageDelayed(int i, int i2, int i3) {
            Message obtain = Message.obtain(this, i);
            obtain.arg1 = i2;
            sendMessageDelayed(obtain, i3);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                Toast.makeText(UsbUI.this.mContext, UsbUI.this.mContext.getResources().getString(message.arg1), 0).show();
                return;
            }
            if (i == 1) {
                Toast.makeText(UsbUI.this.mContext, UsbUI.this.mContext.getResources().getString(message.arg1), 1).show();
                return;
            }
            if (i == 2) {
                int i2 = message.arg1;
                NotificationWrapper notificationWrapper = (NotificationWrapper) message.obj;
                offer(i2, notificationWrapper);
                if (!notificationWrapper.instant && size(i2) > 1) {
                    Slog.d("UsbUI", "Remove last insertion: " + ((Object) notificationWrapper.title));
                    pollLast(i2);
                    return;
                }
                if (size(i2) == 1) {
                    sendMessage(3, i2);
                    return;
                }
                return;
            }
            if (i == 3) {
                int i3 = message.arg1;
                NotificationWrapper peek = peek(i3);
                if (peek != null) {
                    Slog.d("UsbUI", "notifyAsUser: " + ((Object) peek.title));
                    UsbUI.this.mNotificationManager.notifyAsUser(null, i3, peek.build(), UserHandle.ALL);
                    if (peek.instant) {
                        sendMessageDelayed(4, i3, 3000);
                    }
                    if (peek.instant || !peek.ongoing) {
                        return;
                    }
                    this.currentOngoings.put(Integer.valueOf(i3), peek);
                    return;
                }
                return;
            }
            if (i != 4) {
                if (i != 5) {
                    Slog.e("UsbUI", "Unexpected message " + message.what);
                    return;
                }
                return;
            }
            int i4 = message.arg1;
            NotificationWrapper pollFirst = pollFirst(i4);
            if (pollFirst != null) {
                Slog.d("UsbUI", "cancelAsUser: " + ((Object) pollFirst.title));
            }
            UsbUI.this.mNotificationManager.cancelAsUser(null, i4, UserHandle.ALL);
            if (size(i4) >= 1) {
                sendMessage(3, i4);
            }
            if (this.currentOngoings.containsKey(Integer.valueOf(i4))) {
                this.currentOngoings.remove(Integer.valueOf(i4));
            }
        }

        public final boolean offer(int i, NotificationWrapper notificationWrapper) {
            Deque deque;
            if (this.pendingJobs.containsKey(Integer.valueOf(i))) {
                deque = (Deque) this.pendingJobs.get(Integer.valueOf(i));
            } else {
                LinkedList linkedList = new LinkedList();
                this.pendingJobs.put(Integer.valueOf(i), linkedList);
                deque = linkedList;
            }
            return deque.offer(notificationWrapper);
        }

        public final NotificationWrapper pollFirst(int i) {
            if (this.pendingJobs.containsKey(Integer.valueOf(i))) {
                return (NotificationWrapper) ((Deque) this.pendingJobs.get(Integer.valueOf(i))).pollFirst();
            }
            return null;
        }

        public final NotificationWrapper pollLast(int i) {
            if (this.pendingJobs.containsKey(Integer.valueOf(i))) {
                return (NotificationWrapper) ((Deque) this.pendingJobs.get(Integer.valueOf(i))).pollLast();
            }
            return null;
        }

        public final NotificationWrapper peek(int i) {
            if (this.pendingJobs.containsKey(Integer.valueOf(i))) {
                return (NotificationWrapper) ((Deque) this.pendingJobs.get(Integer.valueOf(i))).peek();
            }
            return null;
        }

        public final int size(int i) {
            if (this.pendingJobs.containsKey(Integer.valueOf(i))) {
                return ((Deque) this.pendingJobs.get(Integer.valueOf(i))).size();
            }
            return -1;
        }
    }

    public final class NotificationWrapper {
        public final Notification.Action action;
        public final PendingIntent contentInent;
        public final int icon;
        public final CharSequence message;
        public final CharSequence title;
        public boolean instant = false;
        public boolean ongoing = false;
        public boolean alertOnce = false;
        public String channel = SystemNotificationChannels.USB;
        public int priority = 0;

        public NotificationWrapper(int i, int i2, int i3, PendingIntent pendingIntent, Notification.Action action, long j) {
            this.title = i != 0 ? UsbUI.this.mContext.getString(i) : null;
            this.message = i2 != 0 ? UsbUI.this.mContext.getString(i2) : null;
            this.icon = i3;
            this.action = action;
            this.contentInent = pendingIntent;
            parseOptions(j);
        }

        public final void parseOptions(long j) {
            if (UsbUI.isIncludeOption(j, 1L)) {
                this.instant = true;
            }
            if (UsbUI.isIncludeOption(j, 2L)) {
                this.ongoing = true;
            }
            if (UsbUI.isIncludeOption(j, 4L)) {
                this.alertOnce = true;
            }
            if (UsbUI.isIncludeOption(j, 8L)) {
                this.channel = SystemNotificationChannels.ALERTS;
            }
            if (UsbUI.isIncludeOption(j, 16L)) {
                this.priority = -1;
            }
            if (UsbUI.isIncludeOption(j, 32L)) {
                this.priority = -2;
            }
            if (UsbUI.isIncludeOption(j, 64L)) {
                this.priority = 1;
            }
            if (UsbUI.isIncludeOption(j, 128L)) {
                this.priority = 2;
            }
            if (UsbUI.isIncludeOption(j, 256L)) {
                this.channel = "USBImpLow";
                NotificationChannel notificationChannel = new NotificationChannel(this.channel, "USBImportanceLow", 2);
                notificationChannel.setDescription("USB connector connection");
                ((NotificationManager) UsbUI.this.mContext.getSystemService(NotificationManager.class)).createNotificationChannel(notificationChannel);
            }
        }

        public final Notification.Builder buildNotificationBuilder() {
            Notification.Builder visibility = new Notification.Builder(UsbUI.this.mContext, this.channel).setSmallIcon(this.icon).setPriority(this.priority).setColor(UsbUI.this.mContext.getColor(R.color.system_notification_accent_color)).setStyle(new Notification.BigTextStyle().bigText(this.message)).setShowWhen(false).setDefaults(0).setVisibility(1);
            CharSequence charSequence = this.title;
            if (charSequence != null) {
                visibility.setContentTitle(charSequence);
                if (!"USBImpLow".equals(this.channel)) {
                    visibility.setTicker(this.title);
                }
            }
            CharSequence charSequence2 = this.message;
            if (charSequence2 != null) {
                visibility.setContentText(charSequence2);
            }
            Notification.Action action = this.action;
            if (action != null) {
                visibility.addAction(action);
            }
            if (this.ongoing) {
                visibility.setOngoing(true);
            } else {
                visibility.setOngoing(false);
            }
            PendingIntent pendingIntent = this.contentInent;
            if (pendingIntent != null) {
                visibility.setContentIntent(pendingIntent);
            }
            if (this.alertOnce) {
                visibility.setOnlyAlertOnce(true);
            }
            return visibility;
        }

        public Notification build() {
            return buildNotificationBuilder().build();
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter) {
        printWriter.println("UsbUI:");
        printWriter.println("  mSystemReady=" + this.mSystemReady);
        printWriter.println("  mBootCompleted=" + this.mBootCompleted);
        printWriter.println("  mSupportDualRole=" + this.mSupportDualRole);
        printWriter.println("  mIsEmergencyMode=" + this.mIsEmergencyMode);
        printWriter.println("  mIsHostConnected=" + this.mIsHostConnected);
        printWriter.println("  mSourcePower=" + this.mSourcePower);
        printWriter.println("  mIsHiccupState=" + this.mIsHiccupState);
        printWriter.println("  mIsUsbPortWet=" + this.mIsUsbPortWet);
    }
}
