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
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbPortStatus;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.util.sysfwutil.Slog;
import android.widget.Toast;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.Preconditions;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.feature.SemFloatingFeature;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public final class UsbUI implements ActivityTaskManagerInternal.ScreenObserver {
    public final AnonymousClass1 mBatteryEventReceiver;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final AnonymousClass1 mEmergencyModeReceiver;
    public final UsbUIHandler mHandler;
    public final AnonymousClass6 mHostInterfaceObserver;
    public final AnonymousClass6 mHostPathObserver;
    public final AnonymousClass1 mLocaleChangedReceiver;
    public final Object mLoggingLock;
    public NotificationManager mNotificationManager;
    public final AnonymousClass1 mPortReceiver;
    public final PowerManager mPowerManager;
    public final boolean mSupportDualRole;
    public final AnonymousClass6 mUsbCcicObserver;
    public final AnonymousClass6 mUsbControlObserver;
    public final AnonymousClass1 mUsbWetStateReceiver;
    public final PowerManager.WakeLock mWakeLock;
    public boolean mSystemReady = false;
    public boolean mBootCompleted = false;
    public boolean mIsEmergencyMode = false;
    public boolean mIsHostConnected = false;
    public boolean mSourcePower = false;
    public boolean mIsHiccupState = false;
    public boolean mIsUsbPortWet = false;
    public final boolean isSupportWirelessCharging = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_WIRELESS_HV");
    public boolean mIsUsbBlkNotiShown = false;
    public String mUsbBlkPendingIntent = "";

    public final class NotificationWrapper {
        public final Notification.Action action;
        public final boolean alertOnce;
        public final String channel;
        public final PendingIntent contentInent = null;
        public final int icon;
        public final boolean instant;
        public final int messageResId;
        public final boolean ongoing;
        public final int priority;
        public final int titleResId;

        public NotificationWrapper(int i, int i2, int i3, Notification.Action action, long j) {
            this.instant = false;
            this.ongoing = false;
            this.alertOnce = false;
            this.channel = SystemNotificationChannels.USB;
            this.priority = 0;
            this.titleResId = i;
            this.messageResId = i2;
            this.icon = i3;
            this.action = action;
            if (UsbUI.m1026$$Nest$smisIncludeOption(j, 1L)) {
                this.instant = true;
            }
            if (UsbUI.m1026$$Nest$smisIncludeOption(j, 2L)) {
                this.ongoing = true;
            }
            if (UsbUI.m1026$$Nest$smisIncludeOption(j, 4L)) {
                this.alertOnce = true;
            }
            if (UsbUI.m1026$$Nest$smisIncludeOption(j, 8L)) {
                this.channel = SystemNotificationChannels.ALERTS;
            }
            if (UsbUI.m1026$$Nest$smisIncludeOption(j, 16L)) {
                this.priority = -1;
            }
            if (UsbUI.m1026$$Nest$smisIncludeOption(j, 32L)) {
                this.priority = -2;
            }
            if (UsbUI.m1026$$Nest$smisIncludeOption(j, 64L)) {
                this.priority = 1;
            }
            if (UsbUI.m1026$$Nest$smisIncludeOption(j, 128L)) {
                this.priority = 2;
            }
            if (UsbUI.m1026$$Nest$smisIncludeOption(j, 256L)) {
                this.channel = "USBImpLow";
                NotificationChannel notificationChannel = new NotificationChannel(this.channel, "USBImportanceLow", 2);
                notificationChannel.setDescription("USB connector connection");
                ((NotificationManager) UsbUI.this.mContext.getSystemService(NotificationManager.class)).createNotificationChannel(notificationChannel);
            }
        }

        public final Notification build() {
            CharSequence title = getTitle();
            UsbUI usbUI = UsbUI.this;
            int i = this.messageResId;
            String string = i != 0 ? usbUI.mContext.getString(i) : null;
            Notification.Builder visibility = new Notification.Builder(usbUI.mContext, this.channel).setSmallIcon(this.icon).setPriority(this.priority).setColor(usbUI.mContext.getColor(R.color.system_notification_accent_color)).setStyle(new Notification.BigTextStyle().bigText(string)).setShowWhen(false).setDefaults(0).setVisibility(1);
            if (title != null) {
                visibility.setContentTitle(title);
                if (!"USBImpLow".equals(this.channel)) {
                    visibility.setTicker(title);
                }
            }
            if (string != null) {
                visibility.setContentText(string);
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
            if (this.alertOnce) {
                visibility.setOnlyAlertOnce(true);
            }
            PendingIntent pendingIntent = this.contentInent;
            if (pendingIntent != null) {
                visibility.setContentIntent(pendingIntent);
            }
            return visibility.build();
        }

        public final CharSequence getTitle() {
            int i = this.titleResId;
            if (i != 0) {
                return UsbUI.this.mContext.getString(i);
            }
            return null;
        }
    }

    public final class UsbUIHandler extends Handler {
        public final HashMap currentOngoings;
        public final HashMap pendingJobs;

        public UsbUIHandler(Looper looper) {
            super(looper);
            this.pendingJobs = new HashMap();
            this.currentOngoings = new HashMap();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Deque linkedList;
            int i = message.what;
            UsbUI usbUI = UsbUI.this;
            if (i == 0) {
                int i2 = message.arg1;
                Context context = usbUI.mContext;
                Toast.makeText(context, context.getResources().getString(i2), 0).show();
                return;
            }
            if (i == 1) {
                int i3 = message.arg1;
                Context context2 = usbUI.mContext;
                Toast.makeText(context2, context2.getResources().getString(i3), 1).show();
                return;
            }
            if (i == 2) {
                int i4 = message.arg1;
                NotificationWrapper notificationWrapper = (NotificationWrapper) message.obj;
                if (this.pendingJobs.containsKey(Integer.valueOf(i4))) {
                    linkedList = (Deque) this.pendingJobs.get(Integer.valueOf(i4));
                } else {
                    linkedList = new LinkedList();
                    this.pendingJobs.put(Integer.valueOf(i4), linkedList);
                }
                linkedList.offer(notificationWrapper);
                if (notificationWrapper.instant || size(i4) <= 1) {
                    if (size(i4) == 1) {
                        Message obtain = Message.obtain(this, 3);
                        obtain.arg1 = i4;
                        sendMessage(obtain);
                        return;
                    }
                    return;
                }
                Slog.d("UsbUI", "Remove last insertion: " + ((Object) notificationWrapper.getTitle()));
                if (this.pendingJobs.containsKey(Integer.valueOf(i4))) {
                    return;
                }
                return;
            }
            if (i == 3) {
                int i5 = message.arg1;
                NotificationWrapper notificationWrapper2 = this.pendingJobs.containsKey(Integer.valueOf(i5)) ? (NotificationWrapper) ((Deque) this.pendingJobs.get(Integer.valueOf(i5))).peek() : null;
                if (notificationWrapper2 != null) {
                    Slog.d("UsbUI", "notifyAsUser: " + ((Object) notificationWrapper2.getTitle()));
                    usbUI.mNotificationManager.notifyAsUser(null, i5, notificationWrapper2.build(), UserHandle.ALL);
                    if (notificationWrapper2.instant) {
                        Message obtain2 = Message.obtain(this, 4);
                        obtain2.arg1 = i5;
                        sendMessageDelayed(obtain2, 3000);
                    }
                    if (notificationWrapper2.instant || !notificationWrapper2.ongoing) {
                        return;
                    }
                    this.currentOngoings.put(Integer.valueOf(i5), notificationWrapper2);
                    return;
                }
                return;
            }
            if (i == 4) {
                int i6 = message.arg1;
                NotificationWrapper notificationWrapper3 = this.pendingJobs.containsKey(Integer.valueOf(i6)) ? (NotificationWrapper) ((Deque) this.pendingJobs.get(Integer.valueOf(i6))).pollFirst() : null;
                if (notificationWrapper3 != null) {
                    Slog.d("UsbUI", "cancelAsUser: " + ((Object) notificationWrapper3.getTitle()));
                }
                usbUI.mNotificationManager.cancelAsUser(null, i6, UserHandle.ALL);
                if (size(i6) >= 1) {
                    Message obtain3 = Message.obtain(this, 3);
                    obtain3.arg1 = i6;
                    sendMessage(obtain3);
                }
                if (this.currentOngoings.containsKey(Integer.valueOf(i6))) {
                    this.currentOngoings.remove(Integer.valueOf(i6));
                    return;
                }
                return;
            }
            if (i != 5) {
                Slog.e("UsbUI", "Unexpected message " + message.what);
                return;
            }
            for (Map.Entry entry : this.currentOngoings.entrySet()) {
                int intValue = ((Integer) entry.getKey()).intValue();
                NotificationWrapper notificationWrapper4 = (NotificationWrapper) entry.getValue();
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(intValue, "update id=", " title=");
                m.append((Object) notificationWrapper4.getTitle());
                Slog.d("UsbUI", m.toString());
                NotificationManager notificationManager = usbUI.mNotificationManager;
                UserHandle userHandle = UserHandle.ALL;
                notificationManager.cancelAsUser(null, intValue, userHandle);
                usbUI.mNotificationManager.notifyAsUser(null, intValue, notificationWrapper4.build(), userHandle);
            }
        }

        public final int size(int i) {
            if (this.pendingJobs.containsKey(Integer.valueOf(i))) {
                return ((Deque) this.pendingJobs.get(Integer.valueOf(i))).size();
            }
            return -1;
        }
    }

    /* renamed from: -$$Nest$mnotifyIncreaseBatteryUsage, reason: not valid java name */
    public static void m1025$$Nest$mnotifyIncreaseBatteryUsage(UsbUI usbUI, boolean z) {
        if (!z) {
            usbUI.cancelNotification(107);
            return;
        }
        if (usbUI.mSystemReady) {
            UsbUIHandler usbUIHandler = usbUI.mHandler;
            usbUIHandler.getClass();
            Message obtain = Message.obtain(usbUIHandler, 0);
            obtain.arg1 = 17043352;
            usbUIHandler.sendMessage(obtain);
        }
        usbUI.postNotification(107, 0, 17043352, 138L);
    }

    /* renamed from: -$$Nest$smisIncludeOption, reason: not valid java name */
    public static boolean m1026$$Nest$smisIncludeOption(long j, long j2) {
        return (j & j2) != 0;
    }

    public UsbUI(Context context, Looper looper) {
        this.mSupportDualRole = false;
        final int i = 0;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver(this) { // from class: com.android.server.usb.UsbUI.1
            public final /* synthetic */ UsbUI this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i) {
                    case 0:
                        Slog.d("UsbUI", "mLocalechangedReceiver (" + intent + ")");
                        this.this$0.mHandler.sendEmptyMessage(5);
                        break;
                    case 1:
                        Slog.d("UsbUI", "mUsbWetStateReceiver (" + intent + ")");
                        this.this$0.mContext.sendBroadcastAsUser(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"), UserHandle.ALL);
                        String string = this.this$0.mContext.getString(17043408);
                        String string2 = this.this$0.mContext.getString(17043416);
                        UsbUI usbUI = this.this$0;
                        if (usbUI.isSupportWirelessCharging) {
                            usbUI.mContext.getString(17043417);
                        } else {
                            usbUI.mContext.getString(17043415);
                        }
                        String replace = this.this$0.mContext.getString(17043417).replace("\\n", "\n");
                        AlertDialog.Builder builder = new AlertDialog.Builder(this.this$0.mContext, (this.this$0.mContext.getResources().getConfiguration().uiMode & 48) == 32 ? 4 : 5);
                        builder.setCancelable(false);
                        builder.setTitle(string);
                        builder.setMessage(replace);
                        builder.setPositiveButton(string2, new UsbUI$2$1());
                        AlertDialog create = builder.create();
                        create.getWindow().setType(2008);
                        create.show();
                        break;
                    case 2:
                        Slog.d("UsbUI", "mPortReceiver (" + intent + ")");
                        boolean z = this.this$0.mSourcePower;
                        UsbPortStatus parcelableExtra = intent.getParcelableExtra("portStatus");
                        if (parcelableExtra == null) {
                            Slog.d("UsbUI", "UsbPortStatus is null");
                            this.this$0.mSourcePower = false;
                        } else {
                            this.this$0.mSourcePower = parcelableExtra.getCurrentPowerRole() == 1;
                        }
                        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("oldSourcePower=", " mSourcePower=", z);
                        m.append(this.this$0.mSourcePower);
                        Slog.d("UsbUI", m.toString());
                        UsbUI usbUI2 = this.this$0;
                        boolean z2 = usbUI2.mSourcePower;
                        if (z != z2 && usbUI2.mIsEmergencyMode) {
                            UsbUI.m1025$$Nest$mnotifyIncreaseBatteryUsage(usbUI2, z2);
                            break;
                        }
                        break;
                    case 3:
                        UsbUI usbUI3 = this.this$0;
                        boolean z3 = usbUI3.mIsHiccupState;
                        usbUI3.mIsHiccupState = (intent.getIntExtra("misc_event", 0) & 32) != 0;
                        if (z3 != this.this$0.mIsHiccupState) {
                            StringBuilder m2 = BatteryService$$ExternalSyntheticOutline0.m("oldHiccupState=", " mIsHiccupState=", z3);
                            m2.append(this.this$0.mIsHiccupState);
                            Slog.d("UsbUI", m2.toString());
                            UsbUI usbUI4 = this.this$0;
                            usbUI4.loggingUsbWetDetection(usbUI4.mIsHiccupState ? "CD" : "CR");
                            break;
                        }
                        break;
                    default:
                        Slog.d("UsbUI", "mEmergencyModeReceiver (" + intent + ")");
                        int intExtra = intent.getIntExtra("reason", 0);
                        if (intExtra != 3) {
                            if (intExtra == 5) {
                                Slog.d("UsbUI", "EmergencyMode disabled");
                                UsbUI usbUI5 = this.this$0;
                                usbUI5.mIsEmergencyMode = false;
                                if (usbUI5.mSourcePower || (!usbUI5.mSupportDualRole && usbUI5.mIsHostConnected)) {
                                    usbUI5.cancelNotification(107);
                                    break;
                                }
                            }
                        } else {
                            Slog.d("UsbUI", "EmergencyMode enabled");
                            UsbUI usbUI6 = this.this$0;
                            usbUI6.mIsEmergencyMode = true;
                            if (usbUI6.mSourcePower || (!usbUI6.mSupportDualRole && usbUI6.mIsHostConnected)) {
                                UsbUI.m1025$$Nest$mnotifyIncreaseBatteryUsage(usbUI6, true);
                                break;
                            }
                        }
                        break;
                }
            }
        };
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver(this) { // from class: com.android.server.usb.UsbUI.1
            public final /* synthetic */ UsbUI this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i2) {
                    case 0:
                        Slog.d("UsbUI", "mLocalechangedReceiver (" + intent + ")");
                        this.this$0.mHandler.sendEmptyMessage(5);
                        break;
                    case 1:
                        Slog.d("UsbUI", "mUsbWetStateReceiver (" + intent + ")");
                        this.this$0.mContext.sendBroadcastAsUser(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"), UserHandle.ALL);
                        String string = this.this$0.mContext.getString(17043408);
                        String string2 = this.this$0.mContext.getString(17043416);
                        UsbUI usbUI = this.this$0;
                        if (usbUI.isSupportWirelessCharging) {
                            usbUI.mContext.getString(17043417);
                        } else {
                            usbUI.mContext.getString(17043415);
                        }
                        String replace = this.this$0.mContext.getString(17043417).replace("\\n", "\n");
                        AlertDialog.Builder builder = new AlertDialog.Builder(this.this$0.mContext, (this.this$0.mContext.getResources().getConfiguration().uiMode & 48) == 32 ? 4 : 5);
                        builder.setCancelable(false);
                        builder.setTitle(string);
                        builder.setMessage(replace);
                        builder.setPositiveButton(string2, new UsbUI$2$1());
                        AlertDialog create = builder.create();
                        create.getWindow().setType(2008);
                        create.show();
                        break;
                    case 2:
                        Slog.d("UsbUI", "mPortReceiver (" + intent + ")");
                        boolean z = this.this$0.mSourcePower;
                        UsbPortStatus parcelableExtra = intent.getParcelableExtra("portStatus");
                        if (parcelableExtra == null) {
                            Slog.d("UsbUI", "UsbPortStatus is null");
                            this.this$0.mSourcePower = false;
                        } else {
                            this.this$0.mSourcePower = parcelableExtra.getCurrentPowerRole() == 1;
                        }
                        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("oldSourcePower=", " mSourcePower=", z);
                        m.append(this.this$0.mSourcePower);
                        Slog.d("UsbUI", m.toString());
                        UsbUI usbUI2 = this.this$0;
                        boolean z2 = usbUI2.mSourcePower;
                        if (z != z2 && usbUI2.mIsEmergencyMode) {
                            UsbUI.m1025$$Nest$mnotifyIncreaseBatteryUsage(usbUI2, z2);
                            break;
                        }
                        break;
                    case 3:
                        UsbUI usbUI3 = this.this$0;
                        boolean z3 = usbUI3.mIsHiccupState;
                        usbUI3.mIsHiccupState = (intent.getIntExtra("misc_event", 0) & 32) != 0;
                        if (z3 != this.this$0.mIsHiccupState) {
                            StringBuilder m2 = BatteryService$$ExternalSyntheticOutline0.m("oldHiccupState=", " mIsHiccupState=", z3);
                            m2.append(this.this$0.mIsHiccupState);
                            Slog.d("UsbUI", m2.toString());
                            UsbUI usbUI4 = this.this$0;
                            usbUI4.loggingUsbWetDetection(usbUI4.mIsHiccupState ? "CD" : "CR");
                            break;
                        }
                        break;
                    default:
                        Slog.d("UsbUI", "mEmergencyModeReceiver (" + intent + ")");
                        int intExtra = intent.getIntExtra("reason", 0);
                        if (intExtra != 3) {
                            if (intExtra == 5) {
                                Slog.d("UsbUI", "EmergencyMode disabled");
                                UsbUI usbUI5 = this.this$0;
                                usbUI5.mIsEmergencyMode = false;
                                if (usbUI5.mSourcePower || (!usbUI5.mSupportDualRole && usbUI5.mIsHostConnected)) {
                                    usbUI5.cancelNotification(107);
                                    break;
                                }
                            }
                        } else {
                            Slog.d("UsbUI", "EmergencyMode enabled");
                            UsbUI usbUI6 = this.this$0;
                            usbUI6.mIsEmergencyMode = true;
                            if (usbUI6.mSourcePower || (!usbUI6.mSupportDualRole && usbUI6.mIsHostConnected)) {
                                UsbUI.m1025$$Nest$mnotifyIncreaseBatteryUsage(usbUI6, true);
                                break;
                            }
                        }
                        break;
                }
            }
        };
        final int i3 = 2;
        BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver(this) { // from class: com.android.server.usb.UsbUI.1
            public final /* synthetic */ UsbUI this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i3) {
                    case 0:
                        Slog.d("UsbUI", "mLocalechangedReceiver (" + intent + ")");
                        this.this$0.mHandler.sendEmptyMessage(5);
                        break;
                    case 1:
                        Slog.d("UsbUI", "mUsbWetStateReceiver (" + intent + ")");
                        this.this$0.mContext.sendBroadcastAsUser(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"), UserHandle.ALL);
                        String string = this.this$0.mContext.getString(17043408);
                        String string2 = this.this$0.mContext.getString(17043416);
                        UsbUI usbUI = this.this$0;
                        if (usbUI.isSupportWirelessCharging) {
                            usbUI.mContext.getString(17043417);
                        } else {
                            usbUI.mContext.getString(17043415);
                        }
                        String replace = this.this$0.mContext.getString(17043417).replace("\\n", "\n");
                        AlertDialog.Builder builder = new AlertDialog.Builder(this.this$0.mContext, (this.this$0.mContext.getResources().getConfiguration().uiMode & 48) == 32 ? 4 : 5);
                        builder.setCancelable(false);
                        builder.setTitle(string);
                        builder.setMessage(replace);
                        builder.setPositiveButton(string2, new UsbUI$2$1());
                        AlertDialog create = builder.create();
                        create.getWindow().setType(2008);
                        create.show();
                        break;
                    case 2:
                        Slog.d("UsbUI", "mPortReceiver (" + intent + ")");
                        boolean z = this.this$0.mSourcePower;
                        UsbPortStatus parcelableExtra = intent.getParcelableExtra("portStatus");
                        if (parcelableExtra == null) {
                            Slog.d("UsbUI", "UsbPortStatus is null");
                            this.this$0.mSourcePower = false;
                        } else {
                            this.this$0.mSourcePower = parcelableExtra.getCurrentPowerRole() == 1;
                        }
                        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("oldSourcePower=", " mSourcePower=", z);
                        m.append(this.this$0.mSourcePower);
                        Slog.d("UsbUI", m.toString());
                        UsbUI usbUI2 = this.this$0;
                        boolean z2 = usbUI2.mSourcePower;
                        if (z != z2 && usbUI2.mIsEmergencyMode) {
                            UsbUI.m1025$$Nest$mnotifyIncreaseBatteryUsage(usbUI2, z2);
                            break;
                        }
                        break;
                    case 3:
                        UsbUI usbUI3 = this.this$0;
                        boolean z3 = usbUI3.mIsHiccupState;
                        usbUI3.mIsHiccupState = (intent.getIntExtra("misc_event", 0) & 32) != 0;
                        if (z3 != this.this$0.mIsHiccupState) {
                            StringBuilder m2 = BatteryService$$ExternalSyntheticOutline0.m("oldHiccupState=", " mIsHiccupState=", z3);
                            m2.append(this.this$0.mIsHiccupState);
                            Slog.d("UsbUI", m2.toString());
                            UsbUI usbUI4 = this.this$0;
                            usbUI4.loggingUsbWetDetection(usbUI4.mIsHiccupState ? "CD" : "CR");
                            break;
                        }
                        break;
                    default:
                        Slog.d("UsbUI", "mEmergencyModeReceiver (" + intent + ")");
                        int intExtra = intent.getIntExtra("reason", 0);
                        if (intExtra != 3) {
                            if (intExtra == 5) {
                                Slog.d("UsbUI", "EmergencyMode disabled");
                                UsbUI usbUI5 = this.this$0;
                                usbUI5.mIsEmergencyMode = false;
                                if (usbUI5.mSourcePower || (!usbUI5.mSupportDualRole && usbUI5.mIsHostConnected)) {
                                    usbUI5.cancelNotification(107);
                                    break;
                                }
                            }
                        } else {
                            Slog.d("UsbUI", "EmergencyMode enabled");
                            UsbUI usbUI6 = this.this$0;
                            usbUI6.mIsEmergencyMode = true;
                            if (usbUI6.mSourcePower || (!usbUI6.mSupportDualRole && usbUI6.mIsHostConnected)) {
                                UsbUI.m1025$$Nest$mnotifyIncreaseBatteryUsage(usbUI6, true);
                                break;
                            }
                        }
                        break;
                }
            }
        };
        final int i4 = 3;
        BroadcastReceiver broadcastReceiver4 = new BroadcastReceiver(this) { // from class: com.android.server.usb.UsbUI.1
            public final /* synthetic */ UsbUI this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i4) {
                    case 0:
                        Slog.d("UsbUI", "mLocalechangedReceiver (" + intent + ")");
                        this.this$0.mHandler.sendEmptyMessage(5);
                        break;
                    case 1:
                        Slog.d("UsbUI", "mUsbWetStateReceiver (" + intent + ")");
                        this.this$0.mContext.sendBroadcastAsUser(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"), UserHandle.ALL);
                        String string = this.this$0.mContext.getString(17043408);
                        String string2 = this.this$0.mContext.getString(17043416);
                        UsbUI usbUI = this.this$0;
                        if (usbUI.isSupportWirelessCharging) {
                            usbUI.mContext.getString(17043417);
                        } else {
                            usbUI.mContext.getString(17043415);
                        }
                        String replace = this.this$0.mContext.getString(17043417).replace("\\n", "\n");
                        AlertDialog.Builder builder = new AlertDialog.Builder(this.this$0.mContext, (this.this$0.mContext.getResources().getConfiguration().uiMode & 48) == 32 ? 4 : 5);
                        builder.setCancelable(false);
                        builder.setTitle(string);
                        builder.setMessage(replace);
                        builder.setPositiveButton(string2, new UsbUI$2$1());
                        AlertDialog create = builder.create();
                        create.getWindow().setType(2008);
                        create.show();
                        break;
                    case 2:
                        Slog.d("UsbUI", "mPortReceiver (" + intent + ")");
                        boolean z = this.this$0.mSourcePower;
                        UsbPortStatus parcelableExtra = intent.getParcelableExtra("portStatus");
                        if (parcelableExtra == null) {
                            Slog.d("UsbUI", "UsbPortStatus is null");
                            this.this$0.mSourcePower = false;
                        } else {
                            this.this$0.mSourcePower = parcelableExtra.getCurrentPowerRole() == 1;
                        }
                        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("oldSourcePower=", " mSourcePower=", z);
                        m.append(this.this$0.mSourcePower);
                        Slog.d("UsbUI", m.toString());
                        UsbUI usbUI2 = this.this$0;
                        boolean z2 = usbUI2.mSourcePower;
                        if (z != z2 && usbUI2.mIsEmergencyMode) {
                            UsbUI.m1025$$Nest$mnotifyIncreaseBatteryUsage(usbUI2, z2);
                            break;
                        }
                        break;
                    case 3:
                        UsbUI usbUI3 = this.this$0;
                        boolean z3 = usbUI3.mIsHiccupState;
                        usbUI3.mIsHiccupState = (intent.getIntExtra("misc_event", 0) & 32) != 0;
                        if (z3 != this.this$0.mIsHiccupState) {
                            StringBuilder m2 = BatteryService$$ExternalSyntheticOutline0.m("oldHiccupState=", " mIsHiccupState=", z3);
                            m2.append(this.this$0.mIsHiccupState);
                            Slog.d("UsbUI", m2.toString());
                            UsbUI usbUI4 = this.this$0;
                            usbUI4.loggingUsbWetDetection(usbUI4.mIsHiccupState ? "CD" : "CR");
                            break;
                        }
                        break;
                    default:
                        Slog.d("UsbUI", "mEmergencyModeReceiver (" + intent + ")");
                        int intExtra = intent.getIntExtra("reason", 0);
                        if (intExtra != 3) {
                            if (intExtra == 5) {
                                Slog.d("UsbUI", "EmergencyMode disabled");
                                UsbUI usbUI5 = this.this$0;
                                usbUI5.mIsEmergencyMode = false;
                                if (usbUI5.mSourcePower || (!usbUI5.mSupportDualRole && usbUI5.mIsHostConnected)) {
                                    usbUI5.cancelNotification(107);
                                    break;
                                }
                            }
                        } else {
                            Slog.d("UsbUI", "EmergencyMode enabled");
                            UsbUI usbUI6 = this.this$0;
                            usbUI6.mIsEmergencyMode = true;
                            if (usbUI6.mSourcePower || (!usbUI6.mSupportDualRole && usbUI6.mIsHostConnected)) {
                                UsbUI.m1025$$Nest$mnotifyIncreaseBatteryUsage(usbUI6, true);
                                break;
                            }
                        }
                        break;
                }
            }
        };
        final int i5 = 4;
        BroadcastReceiver broadcastReceiver5 = new BroadcastReceiver(this) { // from class: com.android.server.usb.UsbUI.1
            public final /* synthetic */ UsbUI this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i5) {
                    case 0:
                        Slog.d("UsbUI", "mLocalechangedReceiver (" + intent + ")");
                        this.this$0.mHandler.sendEmptyMessage(5);
                        break;
                    case 1:
                        Slog.d("UsbUI", "mUsbWetStateReceiver (" + intent + ")");
                        this.this$0.mContext.sendBroadcastAsUser(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"), UserHandle.ALL);
                        String string = this.this$0.mContext.getString(17043408);
                        String string2 = this.this$0.mContext.getString(17043416);
                        UsbUI usbUI = this.this$0;
                        if (usbUI.isSupportWirelessCharging) {
                            usbUI.mContext.getString(17043417);
                        } else {
                            usbUI.mContext.getString(17043415);
                        }
                        String replace = this.this$0.mContext.getString(17043417).replace("\\n", "\n");
                        AlertDialog.Builder builder = new AlertDialog.Builder(this.this$0.mContext, (this.this$0.mContext.getResources().getConfiguration().uiMode & 48) == 32 ? 4 : 5);
                        builder.setCancelable(false);
                        builder.setTitle(string);
                        builder.setMessage(replace);
                        builder.setPositiveButton(string2, new UsbUI$2$1());
                        AlertDialog create = builder.create();
                        create.getWindow().setType(2008);
                        create.show();
                        break;
                    case 2:
                        Slog.d("UsbUI", "mPortReceiver (" + intent + ")");
                        boolean z = this.this$0.mSourcePower;
                        UsbPortStatus parcelableExtra = intent.getParcelableExtra("portStatus");
                        if (parcelableExtra == null) {
                            Slog.d("UsbUI", "UsbPortStatus is null");
                            this.this$0.mSourcePower = false;
                        } else {
                            this.this$0.mSourcePower = parcelableExtra.getCurrentPowerRole() == 1;
                        }
                        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("oldSourcePower=", " mSourcePower=", z);
                        m.append(this.this$0.mSourcePower);
                        Slog.d("UsbUI", m.toString());
                        UsbUI usbUI2 = this.this$0;
                        boolean z2 = usbUI2.mSourcePower;
                        if (z != z2 && usbUI2.mIsEmergencyMode) {
                            UsbUI.m1025$$Nest$mnotifyIncreaseBatteryUsage(usbUI2, z2);
                            break;
                        }
                        break;
                    case 3:
                        UsbUI usbUI3 = this.this$0;
                        boolean z3 = usbUI3.mIsHiccupState;
                        usbUI3.mIsHiccupState = (intent.getIntExtra("misc_event", 0) & 32) != 0;
                        if (z3 != this.this$0.mIsHiccupState) {
                            StringBuilder m2 = BatteryService$$ExternalSyntheticOutline0.m("oldHiccupState=", " mIsHiccupState=", z3);
                            m2.append(this.this$0.mIsHiccupState);
                            Slog.d("UsbUI", m2.toString());
                            UsbUI usbUI4 = this.this$0;
                            usbUI4.loggingUsbWetDetection(usbUI4.mIsHiccupState ? "CD" : "CR");
                            break;
                        }
                        break;
                    default:
                        Slog.d("UsbUI", "mEmergencyModeReceiver (" + intent + ")");
                        int intExtra = intent.getIntExtra("reason", 0);
                        if (intExtra != 3) {
                            if (intExtra == 5) {
                                Slog.d("UsbUI", "EmergencyMode disabled");
                                UsbUI usbUI5 = this.this$0;
                                usbUI5.mIsEmergencyMode = false;
                                if (usbUI5.mSourcePower || (!usbUI5.mSupportDualRole && usbUI5.mIsHostConnected)) {
                                    usbUI5.cancelNotification(107);
                                    break;
                                }
                            }
                        } else {
                            Slog.d("UsbUI", "EmergencyMode enabled");
                            UsbUI usbUI6 = this.this$0;
                            usbUI6.mIsEmergencyMode = true;
                            if (usbUI6.mSourcePower || (!usbUI6.mSupportDualRole && usbUI6.mIsHostConnected)) {
                                UsbUI.m1025$$Nest$mnotifyIncreaseBatteryUsage(usbUI6, true);
                                break;
                            }
                        }
                        break;
                }
            }
        };
        final int i6 = 0;
        UEventObserver uEventObserver = new UEventObserver(this) { // from class: com.android.server.usb.UsbUI.6
            public final /* synthetic */ UsbUI this$0;

            {
                this.this$0 = this;
            }

            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
            java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
            	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
            	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
             */
            public final void onUEvent(UEventObserver.UEvent uEvent) {
                switch (i6) {
                    case 0:
                        Slog.v("UsbUI", "onUEvent(Host Path): " + uEvent);
                        String str = uEvent.get("ACTION");
                        String str2 = uEvent.get("STATE");
                        if (!"remove".equals(str)) {
                            if ("change".equals(str) && str2 != null) {
                                switch (str2) {
                                    case "REMOVE":
                                        UsbUI usbUI = this.this$0;
                                        usbUI.cancelNotification(112);
                                        usbUI.cancelNotification(113);
                                        break;
                                    case "OVERCURRENT":
                                        this.this$0.postNotificationInternal(112, 17043346, 17043345, 17304480, null, 0L);
                                        break;
                                    case "UNKNOWN":
                                        this.this$0.postNotificationInternal(113, 17043342, 0, 17304481, null, 0L);
                                        break;
                                }
                                if ("ADD".equals(str2) || "REMOVE".equals(str2)) {
                                    this.this$0.mIsHostConnected = "ADD".equals(str2);
                                    UsbUI usbUI2 = this.this$0;
                                    if (!usbUI2.mSupportDualRole && usbUI2.mIsEmergencyMode) {
                                        UsbUI.m1025$$Nest$mnotifyIncreaseBatteryUsage(usbUI2, usbUI2.mIsHostConnected);
                                        break;
                                    }
                                }
                            }
                        } else {
                            UsbUI usbUI3 = this.this$0;
                            usbUI3.cancelNotification(112);
                            usbUI3.cancelNotification(113);
                            break;
                        }
                        break;
                    case 1:
                        Slog.d("UsbUI", "onUEvent(Host Interface): " + uEvent);
                        String str3 = uEvent.get("ACTION");
                        String str4 = uEvent.get("INTERFACE");
                        String str5 = uEvent.get("WARNING");
                        if ("unsupport_accessory".equals(str5)) {
                            this.this$0.postNotification(114, 17043397, 17043396, 1L);
                            break;
                        } else if ("no_response".equals(str5)) {
                            this.this$0.postNotification(115, 17043367, 17043366, 1L);
                            break;
                        } else if ("hub_depth_exceed".equals(str5)) {
                            this.this$0.postNotification(116, 17043350, 17043349, 1L);
                            break;
                        } else if ("hub_power_exceed".equals(str5)) {
                            this.this$0.postNotification(117, 17043367, 17043351, 1L);
                            break;
                        } else if ("host_resource_exceed".equals(str5)) {
                            this.this$0.postNotification(118, 17043348, 17043347, 1L);
                            break;
                        } else {
                            try {
                                String[] split = str4.split("/");
                                String str6 = split[0];
                                String str7 = split[1];
                                String str8 = split[2];
                                if (str6 != null && str7 != null && str8 != null) {
                                    this.this$0.notifyUsbInterface(Integer.parseInt(str6), Integer.parseInt(str7), Integer.parseInt(str8), str3);
                                    break;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                Slog.e("UsbUI", "ArrayIndexOutOfBoundsException " + e);
                                return;
                            } catch (NumberFormatException unused) {
                                Slog.e("UsbUI", "Could not parse state or devPath from event " + uEvent);
                                return;
                            }
                        }
                        break;
                    case 2:
                        Slog.d("UsbUI", "onUEvent(USB Control): " + uEvent);
                        String str9 = uEvent.get("TYPE");
                        String str10 = uEvent.get("STATE");
                        if (!"usberr".equals(str9)) {
                            if ("usbrestrict".equals(str9)) {
                                String str11 = uEvent.get("WORDS");
                                str11.getClass();
                                switch (str11.hashCode()) {
                                    case -1421522768:
                                        if (str11.equals("securerelease")) {
                                            break;
                                        }
                                        break;
                                    case -902779085:
                                        if (str11.equals("securerestrict")) {
                                            break;
                                        }
                                        break;
                                    case 1889769280:
                                        if (str11.equals("timesecurerestrict")) {
                                            break;
                                        }
                                        break;
                                }
                                /*  JADX ERROR: Method code generation error
                                    java.lang.NullPointerException: Switch insn not found in header
                                    	at java.base/java.util.Objects.requireNonNull(Objects.java:235)
                                    	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:246)
                                    	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                    	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                    	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:267)
                                    	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                    	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                                    	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                                    	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                                    	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                                    	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                                    	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                                    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                                    	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                    	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                                    */
                                /*
                                    Method dump skipped, instructions count: 904
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.server.usb.UsbUI.AnonymousClass6.onUEvent(android.os.UEventObserver$UEvent):void");
                            }
                        };
                        final int i7 = 1;
                        UEventObserver uEventObserver2 = new UEventObserver(this) { // from class: com.android.server.usb.UsbUI.6
                            public final /* synthetic */ UsbUI this$0;

                            {
                                this.this$0 = this;
                            }

                            /*  JADX ERROR: Method code generation error
                                java.lang.NullPointerException: Switch insn not found in header
                                	at java.base/java.util.Objects.requireNonNull(Objects.java:235)
                                	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:246)
                                	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:267)
                                	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                                */
                            public final void onUEvent(android.os.UEventObserver.UEvent r29) {
                                /*
                                    Method dump skipped, instructions count: 904
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.server.usb.UsbUI.AnonymousClass6.onUEvent(android.os.UEventObserver$UEvent):void");
                            }
                        };
                        final int i8 = 2;
                        UEventObserver uEventObserver3 = new UEventObserver(this) { // from class: com.android.server.usb.UsbUI.6
                            public final /* synthetic */ UsbUI this$0;

                            {
                                this.this$0 = this;
                            }

                            /*  JADX ERROR: Method code generation error
                                java.lang.NullPointerException: Switch insn not found in header
                                	at java.base/java.util.Objects.requireNonNull(Objects.java:235)
                                	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:246)
                                	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:267)
                                	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                                */
                            public final void onUEvent(android.os.UEventObserver.UEvent r29) {
                                /*
                                    Method dump skipped, instructions count: 904
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.server.usb.UsbUI.AnonymousClass6.onUEvent(android.os.UEventObserver$UEvent):void");
                            }
                        };
                        final int i9 = 3;
                        UEventObserver uEventObserver4 = new UEventObserver(this) { // from class: com.android.server.usb.UsbUI.6
                            public final /* synthetic */ UsbUI this$0;

                            {
                                this.this$0 = this;
                            }

                            /*  JADX ERROR: Method code generation error
                                java.lang.NullPointerException: Switch insn not found in header
                                	at java.base/java.util.Objects.requireNonNull(Objects.java:235)
                                	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:246)
                                	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:267)
                                	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                                */
                            public final void onUEvent(android.os.UEventObserver.UEvent r29) {
                                /*
                                    Method dump skipped, instructions count: 904
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.server.usb.UsbUI.AnonymousClass6.onUEvent(android.os.UEventObserver$UEvent):void");
                            }
                        };
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
                        context.registerReceiver(broadcastReceiver, new IntentFilter("android.intent.action.LOCALE_CHANGED"), 2);
                        context.registerReceiver(broadcastReceiver2, new IntentFilter("com.samsung.intent.action.USB_WET_STATE"), 2);
                        context.registerReceiver(broadcastReceiver3, new IntentFilter("android.hardware.usb.action.USB_PORT_CHANGED"), 2);
                        context.registerReceiver(broadcastReceiver4, new IntentFilter("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT"), 2);
                        context.registerReceiver(broadcastReceiver5, new IntentFilter("com.samsung.intent.action.EMERGENCY_STATE_CHANGED"), 2);
                    }

                    public final void cancelNotification(int i) {
                        if (!this.mSystemReady || this.mNotificationManager == null) {
                            return;
                        }
                        UsbUIHandler usbUIHandler = this.mHandler;
                        usbUIHandler.getClass();
                        Message obtain = Message.obtain(usbUIHandler, 4);
                        obtain.arg1 = i;
                        usbUIHandler.sendMessage(obtain);
                    }

                    public final void dump(PrintWriter printWriter) {
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "UsbUI:", "  mSystemReady="), this.mSystemReady, printWriter, "  mBootCompleted="), this.mBootCompleted, printWriter, "  mSupportDualRole="), this.mSupportDualRole, printWriter, "  mIsEmergencyMode="), this.mIsEmergencyMode, printWriter, "  mIsHostConnected="), this.mIsHostConnected, printWriter, "  mSourcePower="), this.mSourcePower, printWriter, "  mIsHiccupState="), this.mIsHiccupState, printWriter, "  mIsUsbPortWet="), this.mIsUsbPortWet, printWriter);
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r0v10, types: [java.lang.String] */
                    public final void loggingUsbWetDetection(String str) {
                        String str2;
                        String str3;
                        ?? r0;
                        synchronized (this.mLoggingLock) {
                            FileWriter fileWriter = null;
                            FileWriter fileWriter2 = null;
                            try {
                                try {
                                    String format = new SimpleDateFormat("yyyy/MM/dd/HH:mm").format(Calendar.getInstance().getTime());
                                    File file = new File("/data/log/wet_detect.log");
                                    file.setReadable(true, false);
                                    FileWriter fileWriter3 = new FileWriter(file, true);
                                    try {
                                        r0 = "%s %s%n";
                                        fileWriter3.write(String.format("%s %s%n", format, str));
                                    } catch (IOException unused) {
                                        fileWriter2 = fileWriter3;
                                        Slog.e("UsbUI", "Can't write to /data/log/wet_detect.log");
                                        fileWriter = fileWriter2;
                                        if (fileWriter2 != null) {
                                            try {
                                                fileWriter2.close();
                                                fileWriter = fileWriter2;
                                            } catch (IOException unused2) {
                                                str2 = "UsbUI";
                                                str3 = "Can't close stream";
                                                Slog.e(str2, str3);
                                            }
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        fileWriter = fileWriter3;
                                        if (fileWriter != null) {
                                            try {
                                                fileWriter.close();
                                            } catch (IOException unused3) {
                                                Slog.e("UsbUI", "Can't close stream");
                                            }
                                        }
                                        throw th;
                                    }
                                    try {
                                        fileWriter3.close();
                                        fileWriter = r0;
                                    } catch (IOException unused4) {
                                        str2 = "UsbUI";
                                        str3 = "Can't close stream";
                                        Slog.e(str2, str3);
                                    }
                                } catch (IOException unused5) {
                                }
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        }
                    }

                    public final void notifyUsbInterface(int i, int i2, int i3, String str) {
                        if (str.equals("add") || str.equals("remove")) {
                            Slog.d("UsbUI", String.format("notifyUsbInterface: [%d, %d, %d]", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
                            if (str.equals("add")) {
                                if (i == 6) {
                                    postNotification(124, 17043329, 0, 2L);
                                }
                            } else if (str.equals("remove") && i == 6) {
                                cancelNotification(124);
                                postNotification(111, 17043330, 0, 3L);
                            }
                        }
                    }

                    public final void notifyUsbRestrict(boolean z) {
                        if (z) {
                            this.mIsUsbBlkNotiShown = true;
                            postNotificationInternal(125, 0, 17043395, 17304478, null, 138L);
                        } else {
                            this.mIsUsbBlkNotiShown = false;
                            cancelNotification(125);
                        }
                    }

                    public final void notifyUsbWetDetection(boolean z) {
                        if (!z) {
                            cancelNotification(102);
                            loggingUsbWetDetection("DD");
                            return;
                        }
                        String string = this.mContext.getString(17043420);
                        PendingIntent broadcastAsUser = PendingIntent.getBroadcastAsUser(this.mContext, 0, new Intent("com.samsung.intent.action.USB_WET_STATE"), 67108864, UserHandle.ALL);
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
                        postNotificationInternal(102, 17043421, 17043418, 17304437, new Notification.Action(0, string, broadcastAsUser), 14L);
                        loggingUsbWetDetection("WD");
                    }

                    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
                    public final void onAwakeStateChanged(boolean z) {
                    }

                    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
                    public final void onKeyguardStateChanged(boolean z) {
                        int currentUser = ActivityManager.getCurrentUser();
                        boolean isDeviceSecure = ((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceSecure(currentUser);
                        boolean isDeviceLocked = ((KeyguardManager) this.mContext.getSystemService(KeyguardManager.class)).isDeviceLocked(currentUser);
                        if (!(z && isDeviceSecure && isDeviceLocked) && this.mIsUsbBlkNotiShown) {
                            Slog.d("UsbUI", "Clear USB Restriction Noti by Screen Unlock");
                            notifyUsbRestrict(false);
                            sendingUsbRestrictionStateIntent("OFF");
                        }
                    }

                    public final void postNotification(int i, int i2, int i3, long j) {
                        postNotificationInternal(i, i2, i3, 17304479, null, j);
                    }

                    public final void postNotificationInternal(int i, int i2, int i3, int i4, Notification.Action action, long j) {
                        NotificationManager notificationManager;
                        Preconditions.checkArgument(j == 0 || (j & (-512)) == 0);
                        NotificationWrapper notificationWrapper = new NotificationWrapper(i2, i3, i4, action, j);
                        if (!this.mSystemReady || (notificationManager = this.mNotificationManager) == null) {
                            Slog.d("UsbUI", "Before system ready: title=" + this.mContext.getString(i2));
                            return;
                        }
                        if (!notificationWrapper.instant && !notificationWrapper.ongoing) {
                            notificationManager.notifyAsUser(null, i, notificationWrapper.build(), UserHandle.ALL);
                            return;
                        }
                        UsbUIHandler usbUIHandler = this.mHandler;
                        usbUIHandler.getClass();
                        Message obtain = Message.obtain(usbUIHandler, 2);
                        obtain.arg1 = i;
                        obtain.obj = notificationWrapper;
                        usbUIHandler.sendMessage(obtain);
                    }

                    public final void sendingUsbRestrictionStateIntent(String str) {
                        if (!this.mBootCompleted) {
                            this.mUsbBlkPendingIntent = str;
                            Slog.d("UsbUI", "Pending intent to Lock Screen");
                            return;
                        }
                        try {
                            Intent intent = new Intent("com.samsung.intent.action.USB_RESTRICTION_STATE");
                            intent.putExtra("RestrictionState", str);
                            Slog.d("UsbUI", "broadcasting intent to Lock Screen");
                            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                        } catch (Exception unused) {
                            Slog.e("UsbUI", "Failed to broadcast intent to Lock Screen");
                        }
                    }
                }
