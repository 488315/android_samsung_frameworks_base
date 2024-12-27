package com.android.server.alarm;

import android.app.ActivityManagerNative;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.EventLog;
import android.util.Slog;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.knoxguard.service.utils.Constants;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class GmsAlarmManager {
    public NetWorkStats avaStats;
    public final boolean isChinaMode;
    public final boolean isHongKongMode;
    public AlarmManager mAlarmManager;
    public AlarmManagerService mAlarmService;
    public AnonymousClass1 mBatteryChargingReceiver;
    public final ArrayList mCheckinServerUrl;
    public final int mConfigupdaterUid;
    public final Context mContext;
    public final ArrayList mCurrentIpList;
    public final int mGmsPkgAppid;
    public final int mGmsPkgUid;
    public GmsHandler mHandler;
    public PowerManagerInternal mLocalPowerManager;
    public NetworkInfo mNetworkInfo;
    public AnonymousClass1 mNetworkReceiver;
    public INetworkManagementService mNetworkService;
    public SmartManagerSettingsObserver mObserver;
    public AnonymousClass1 mSCPMReceiver;
    public AnonymousClass1 mScreenReceiver;
    public AnonymousClass1 mSetupWizardCompleteOrBootCompleteReceiver;
    public AnonymousClass1 mUserAddRemoveReceiver;
    public final int mVendingUid;
    public NetWorkStats noAvaStats;
    public NetWorkStats vpnStats;
    public static final Uri SMART_MGR_SETTINGS_URI =
            Uri.parse("content://com.samsung.android.sm/settings");
    public static final HandlerThread sHandlerThread = new HandlerThread("GmsAlarmManager", 1);
    public static final boolean DEBUG_SCPM = true;
    public static final StringBuilder sb = new StringBuilder();
    public boolean mWaitCheckNetWork = true;
    public boolean mGoogleNetWork = true;
    public boolean isGmsNetWorkLimt = false;
    public boolean isCharging = false;
    public boolean isSetupWizardCompleteOrBootComplete = false;
    public boolean mScreenOffChange = false;
    public boolean mScreenOn = true;
    public ConnectivityManager cm = null;
    public boolean mUserEnable = true;
    public int mTimeoutcount = 0;
    public int mTimeoutcountDef = 0;
    public PendingIntent mPendingIntent = null;
    public PendingIntent mInsertLogPendingIntent = null;
    public final boolean mBigdataEnable =
            SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE");
    public int mPolicyControlSwitch = 7;
    public final Uri SCPM_URI_GNET =
            Uri.parse("content://com.samsung.android.sm.policy/policy_item/gmsnet");
    public final Uri SCPM_URI_POLICY =
            Uri.parse("content://com.samsung.android.sm.policy/policy_item/policy_list");
    public final AnonymousClass1 mIntentReceiver = new AnonymousClass1(this, 0);
    public final ArrayList mGmsUidOfMultiUser = new ArrayList();

    /* renamed from: com.android.server.alarm.GmsAlarmManager$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ GmsAlarmManager this$0;

        public /* synthetic */ AnonymousClass1(GmsAlarmManager gmsAlarmManager, int i) {
            this.$r8$classId = i;
            this.this$0 = gmsAlarmManager;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            PendingIntent pendingIntent;
            PendingIntent pendingIntent2;
            switch (this.$r8$classId) {
                case 0:
                    if (intent.getAction() != null) {
                        if (!"com.samsung.android.server.action_check_gms_network"
                                .equals(intent.getAction())) {
                            if (!"com.samsung.android.server.action_insert_log"
                                    .equals(intent.getAction())) {
                                if ("android.intent.action.BOOT_COMPLETED"
                                        .equals(intent.getAction())) {
                                    GmsAlarmManager.m161$$Nest$msendInsertLogDelay(this.this$0);
                                    break;
                                }
                            } else {
                                this.this$0.mHandler.sendEmptyMessage(6);
                                break;
                            }
                        } else {
                            this.this$0.mHandler.sendEmptyMessage(1);
                            break;
                        }
                    }
                    break;
                case 1:
                    if (intent.getAction() != null) {
                        if (!"android.os.action.CHARGING".equals(intent.getAction())) {
                            this.this$0.mHandler.removeMessages(5);
                            this.this$0.mHandler.sendEmptyMessageDelayed(5, 10000L);
                            break;
                        } else {
                            this.this$0.mHandler.removeMessages(4);
                            this.this$0.mHandler.sendEmptyMessageDelayed(4, 10000L);
                            break;
                        }
                    }
                    break;
                case 2:
                    if (intent.getAction() != null
                            && "android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                        Slog.v("GmsAlarmManager", "CONNECTIVITY RECEIVER");
                        this.this$0.mNetworkInfo =
                                (NetworkInfo) intent.getParcelableExtra("networkInfo");
                        NetworkInfo networkInfo = this.this$0.mNetworkInfo;
                        if (networkInfo != null) {
                            int type = networkInfo.getType();
                            EventLog.writeEvent(40200, type);
                            Slog.v(
                                    "GmsAlarmManager",
                                    "NetworkInfo type = "
                                            + type
                                            + "  -- isConnected = "
                                            + this.this$0.mNetworkInfo.isConnected());
                            if (type == -1 || type == 1 || type == 0 || type == 17 || type == 16) {
                                if (type == 17) {
                                    if (this.this$0.mNetworkInfo.isConnected()) {
                                        this.this$0.vpnStats.addStartTime(
                                                SystemClock.elapsedRealtime());
                                    } else {
                                        this.this$0.vpnStats.setEndTime(
                                                SystemClock.elapsedRealtime());
                                    }
                                }
                                GmsAlarmManager gmsAlarmManager = this.this$0;
                                if (!gmsAlarmManager.mScreenOn
                                        && !gmsAlarmManager.mWaitCheckNetWork
                                        && !gmsAlarmManager.mGoogleNetWork) {
                                    gmsAlarmManager.mScreenOffChange = true;
                                    break;
                                } else {
                                    AlarmManager alarmManager = gmsAlarmManager.mAlarmManager;
                                    if (alarmManager != null
                                            && (pendingIntent = gmsAlarmManager.mPendingIntent)
                                                    != null) {
                                        alarmManager.cancel(pendingIntent);
                                    }
                                    GmsAlarmManager.m160$$Nest$msendCheckNetWorkDelay(this.this$0);
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case 3:
                    if (intent.getAction() != null
                            && "sec.app.policy.UPDATE.gmsnet".equals(intent.getAction())) {
                        Slog.v("GmsAlarmManager", "ACTION***" + intent.getAction());
                        this.this$0.mHandler.sendEmptyMessage(7);
                        break;
                    }
                    break;
                case 4:
                    if (intent.getAction() != null) {
                        if (!"android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                            GmsAlarmManager gmsAlarmManager2 = this.this$0;
                            gmsAlarmManager2.mScreenOn = false;
                            gmsAlarmManager2.mScreenOffChange = false;
                            break;
                        } else {
                            GmsAlarmManager gmsAlarmManager3 = this.this$0;
                            gmsAlarmManager3.mScreenOn = true;
                            if (!gmsAlarmManager3.mScreenOffChange) {
                                if (!gmsAlarmManager3.mHandler.hasMessages(1)) {
                                    this.this$0.mHandler.sendEmptyMessageDelayed(
                                            1, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
                                    break;
                                }
                            } else {
                                AlarmManager alarmManager2 = gmsAlarmManager3.mAlarmManager;
                                if (alarmManager2 != null
                                        && (pendingIntent2 = gmsAlarmManager3.mPendingIntent)
                                                != null) {
                                    alarmManager2.cancel(pendingIntent2);
                                }
                                GmsAlarmManager.m160$$Nest$msendCheckNetWorkDelay(this.this$0);
                                this.this$0.mScreenOffChange = false;
                                break;
                            }
                        }
                    }
                    break;
                case 5:
                    if (intent.getAction() != null) {
                        if (!Constants.INTENT_SECSETUPWIZARD_COMPLETE.equals(intent.getAction())) {
                            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                                this.this$0.mHandler.sendEmptyMessage(7);
                                if (this.this$0.isChinaMode) {
                                    Slog.v("GmsAlarmManager", "ACTION***" + intent.getAction());
                                    GmsAlarmManager gmsAlarmManager4 = this.this$0;
                                    gmsAlarmManager4.isSetupWizardCompleteOrBootComplete = true;
                                    gmsAlarmManager4.mHandler.removeMessages(1);
                                    this.this$0.mHandler.sendEmptyMessage(1);
                                }
                                this.this$0.mHandler.sendEmptyMessage(10);
                                break;
                            }
                        } else if (this.this$0.isChinaMode) {
                            Slog.v("GmsAlarmManager", "ACTION***" + intent.getAction());
                            GmsAlarmManager gmsAlarmManager5 = this.this$0;
                            gmsAlarmManager5.isSetupWizardCompleteOrBootComplete = true;
                            gmsAlarmManager5.mHandler.removeMessages(1);
                            this.this$0.mHandler.sendEmptyMessage(1);
                            break;
                        }
                    }
                    break;
                default:
                    String action = intent.getAction();
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    if (intExtra != -1 && action != null) {
                        Message message = new Message();
                        message.arg1 = intExtra;
                        if (!action.equals("android.intent.action.USER_REMOVED")) {
                            if (action.equals("android.intent.action.USER_ADDED")) {
                                message.what = 9;
                                this.this$0.mHandler.sendMessage(message);
                                break;
                            }
                        } else {
                            message.what = 8;
                            this.this$0.mHandler.sendMessage(message);
                            break;
                        }
                    }
                    break;
            }
        }
    }

    public final class GmsHandler extends Handler {
        public GmsHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Code restructure failed: missing block: B:329:0x0657, code lost:

           if (r0 == false) goto L271;
        */
        /* JADX WARN: Code restructure failed: missing block: B:331:0x0682, code lost:

           if (com.android.server.alarm.GmsAlarmManager.m157$$Nest$mcheckActiveNet(r13) == false) goto L295;
        */
        /* JADX WARN: Code restructure failed: missing block: B:332:0x0684, code lost:

           r0 = com.android.server.alarm.GmsAlarmManager.m158$$Nest$mcheckGoogleNetwork(r13);
           com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0.m(r0, "[GMS-CORE] China or hongkong mode and google network return: ", "GmsAlarmManager");
        */
        /* JADX WARN: Code restructure failed: missing block: B:333:0x068f, code lost:

           if (r0 == 200) goto L294;
        */
        /* JADX WARN: Code restructure failed: missing block: B:335:0x0693, code lost:

           if (r0 == 204) goto L294;
        */
        /* JADX WARN: Code restructure failed: missing block: B:337:0x0697, code lost:

           if (r0 != 302) goto L293;
        */
        /* JADX WARN: Code restructure failed: missing block: B:338:0x069a, code lost:

           r13.mGoogleNetWork = false;
        */
        /* JADX WARN: Code restructure failed: missing block: B:381:0x069d, code lost:

           r13.mGoogleNetWork = true;
        */
        /* JADX WARN: Code restructure failed: missing block: B:382:0x06a0, code lost:

           r13.mGoogleNetWork = false;
        */
        /* JADX WARN: Code restructure failed: missing block: B:399:0x067c, code lost:

           if (r0 != false) goto L284;
        */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r24) {
            /*
                Method dump skipped, instructions count: 1980
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.alarm.GmsAlarmManager.GmsHandler.handleMessage(android.os.Message):void");
        }
    }

    public final class NetWorkStats {
        public long mCount = 0;
        public long mTime = 0;
        public final ArrayList data = new ArrayList();
        public final Object mLock = new Object();

        public final class NetWorkData {
            public long endTime;
            public long startTime;
            public long totalTime;
        }

        public final void addStartTime(long j) {
            NetWorkData netWorkData = new NetWorkData();
            netWorkData.startTime = j;
            synchronized (this.mLock) {
                removeLastData(SystemClock.elapsedRealtime() - 172800000);
                this.data.add(netWorkData);
            }
        }

        public final String dump(long j) {
            long j2 = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
            long j3 = j - BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
            this.mCount = 0L;
            synchronized (this.mLock) {
                try {
                    if (j3 <= 0) {
                        this.mCount = this.data.size();
                        j2 = 0;
                        for (int i = 0; i < this.data.size(); i++) {
                            if (((NetWorkData) this.data.get(i)).totalTime != 0) {
                                j2 += ((NetWorkData) this.data.get(i)).totalTime;
                            }
                        }
                        if (this.data.size() > 0) {
                            if (((NetWorkData) this.data.get(r3.size() - 1)).totalTime == 0) {
                                j2 += j - ((NetWorkData) this.data.get(r3.size() - 1)).startTime;
                            }
                        }
                    } else {
                        long j4 = 0;
                        for (int i2 = 0; i2 < this.data.size(); i2++) {
                            if (((NetWorkData) this.data.get(i2)).totalTime != 0
                                    && ((NetWorkData) this.data.get(i2)).endTime - j3 > 0) {
                                this.mCount++;
                                j4 =
                                        j4 == 0
                                                ? j4
                                                        + (((NetWorkData) this.data.get(i2))
                                                                                .startTime
                                                                        < j3
                                                                ? ((NetWorkData) this.data.get(i2))
                                                                                .endTime
                                                                        - j3
                                                                : ((NetWorkData) this.data.get(i2))
                                                                                .endTime
                                                                        - ((NetWorkData)
                                                                                        this.data
                                                                                                .get(
                                                                                                        i2))
                                                                                .startTime)
                                                : j4 + ((NetWorkData) this.data.get(i2)).totalTime;
                            }
                        }
                        if (this.data.size() > 0) {
                            if (((NetWorkData) this.data.get(r10.size() - 1)).totalTime == 0) {
                                if (((NetWorkData) this.data.get(r5.size() - 1)).startTime >= j3) {
                                    j2 =
                                            (j
                                                            - ((NetWorkData)
                                                                            this.data.get(
                                                                                    r1.size() - 1))
                                                                    .startTime)
                                                    + j4;
                                }
                                this.mCount++;
                            }
                        }
                        j2 = j4;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            long j5 = j2 / ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
            long j6 = (j2 - (ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS * j5)) / 60000;
            this.mTime = j2 / 60000;
            StringBuilder sb = new StringBuilder();
            sb.append(j5);
            sb.append("h ");
            sb.append(j6);
            sb.append("m(");
            return AudioConfig$$ExternalSyntheticOutline0.m(sb, this.mCount, "X)");
        }

        public final void removeLastData(long j) {
            if (j < 0) {
                return;
            }
            synchronized (this.mLock) {
                int i = 0;
                while (true) {
                    try {
                        if (i >= this.data.size()) {
                            i = -1;
                            break;
                        } else if (((NetWorkData) this.data.get(i)).endTime == 0
                                || ((NetWorkData) this.data.get(i)).endTime <= j) {
                            i++;
                        }
                    } finally {
                    }
                }
                for (int i2 = 0; i2 < i; i2++) {
                    this.data.remove(0);
                }
            }
        }

        public final void setEndTime(long j) {
            synchronized (this.mLock) {
                try {
                    if (this.data.size() == 0) {
                        return;
                    }
                    NetWorkData netWorkData = (NetWorkData) this.data.get(r3.size() - 1);
                    netWorkData.endTime = j;
                    netWorkData.totalTime = j - netWorkData.startTime;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class SmartManagerSettingsObserver extends ContentObserver {
        public SmartManagerSettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            Slog.d(
                    "GmsAlarmManager",
                    "onChange - mSmartManagerSettingsObserver for GmsAlarmManger!");
            GmsAlarmManager.this.getSettingsValueFromDB();
        }
    }

    /* renamed from: -$$Nest$mcheckActiveNet, reason: not valid java name */
    public static boolean m157$$Nest$mcheckActiveNet(GmsAlarmManager gmsAlarmManager) {
        NetworkInfo activeNetworkInfo;
        if (gmsAlarmManager.cm == null) {
            gmsAlarmManager.cm =
                    (ConnectivityManager) gmsAlarmManager.mContext.getSystemService("connectivity");
        }
        ConnectivityManager connectivityManager = gmsAlarmManager.cm;
        if (connectivityManager != null
                && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null) {
            Slog.v("GmsAlarmManager", "networkInfo:" + activeNetworkInfo);
            if (activeNetworkInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:105:0x01a1, code lost:

       if (r11 == null) goto L99;
    */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x01db, code lost:

       if (r11 != null) goto L87;
    */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x01a3, code lost:

       r11.disconnect();
    */
    /* renamed from: -$$Nest$mcheckGoogleNetwork, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int m158$$Nest$mcheckGoogleNetwork(com.android.server.alarm.GmsAlarmManager r17) {
        /*
            Method dump skipped, instructions count: 486
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.alarm.GmsAlarmManager.m158$$Nest$mcheckGoogleNetwork(com.android.server.alarm.GmsAlarmManager):int");
    }

    /* renamed from: -$$Nest$mrestoreGcmAlarm, reason: not valid java name */
    public static void m159$$Nest$mrestoreGcmAlarm(GmsAlarmManager gmsAlarmManager) {
        Alarm alarm;
        PendingIntent pendingIntent;
        AlarmManagerService alarmManagerService = gmsAlarmManager.mAlarmService;
        synchronized (alarmManagerService.mLock) {
            try {
                LazyAlarmStore lazyAlarmStore = alarmManagerService.mAlarmStore;
                lazyAlarmStore.getClass();
                ArrayList arrayList = new ArrayList(lazyAlarmStore.mAlarms);
                Collections.reverse(arrayList);
                Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        alarm = null;
                        break;
                    }
                    alarm = (Alarm) it.next();
                    if (alarm != null
                            && (pendingIntent = alarm.operation) != null
                            && "com.google.android.gms".equals(pendingIntent.getCreatorPackage())
                            && alarm.operation.getIntent() != null
                            && "com.google.android.intent.action.GCM_RECONNECT"
                                    .equals(alarm.operation.getIntent().getAction())) {
                        alarmManagerService.mAlarmStore.remove(
                                new AlarmManagerService$$ExternalSyntheticLambda8());
                    }
                }
            } finally {
            }
        }
        if (alarm == null) {
            return;
        }
        alarm.setPolicyElapsed(4, SystemClock.elapsedRealtime() + 10000);
        AlarmManagerService alarmManagerService2 = gmsAlarmManager.mAlarmService;
        synchronized (alarmManagerService2.mLock) {
            alarmManagerService2.setImplLocked(alarm);
        }
    }

    /* renamed from: -$$Nest$msendCheckNetWorkDelay, reason: not valid java name */
    public static void m160$$Nest$msendCheckNetWorkDelay(GmsAlarmManager gmsAlarmManager) {
        if (gmsAlarmManager.mAlarmManager == null) {
            gmsAlarmManager.mAlarmManager =
                    (AlarmManager) gmsAlarmManager.mContext.getSystemService("alarm");
        }
        AlarmManager alarmManager = gmsAlarmManager.mAlarmManager;
        if (alarmManager == null || gmsAlarmManager.mPendingIntent == null) {
            return;
        }
        alarmManager.set(2, SystemClock.elapsedRealtime() + 10000, gmsAlarmManager.mPendingIntent);
        gmsAlarmManager.mWaitCheckNetWork = true;
    }

    /* renamed from: -$$Nest$msendInsertLogDelay, reason: not valid java name */
    public static void m161$$Nest$msendInsertLogDelay(GmsAlarmManager gmsAlarmManager) {
        if (gmsAlarmManager.mAlarmManager == null) {
            gmsAlarmManager.mAlarmManager =
                    (AlarmManager) gmsAlarmManager.mContext.getSystemService("alarm");
        }
        AlarmManager alarmManager = gmsAlarmManager.mAlarmManager;
        if (alarmManager == null || gmsAlarmManager.mInsertLogPendingIntent == null) {
            return;
        }
        alarmManager.set(
                3,
                SystemClock.elapsedRealtime()
                        + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS,
                gmsAlarmManager.mInsertLogPendingIntent);
    }

    /* renamed from: -$$Nest$msetGMSLocationProviderChangeReceiverState, reason: not valid java name */
    public static void m162$$Nest$msetGMSLocationProviderChangeReceiverState(
            GmsAlarmManager gmsAlarmManager, int i) {
        if (gmsAlarmManager.isHongKongMode) {
            return;
        }
        try {
            gmsAlarmManager
                    .mContext
                    .getPackageManager()
                    .setComponentEnabledSetting(
                            new ComponentName(
                                    "com.google.android.gms",
                                    "com.google.android.location.network.LocationProviderChangeReceiver"),
                            i,
                            1);
            Slog.i("GmsAlarmManager", "setGMSLocationProviderChangeReceiverState:" + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: -$$Nest$msetGmsNetWorkAllow, reason: not valid java name */
    public static void m163$$Nest$msetGmsNetWorkAllow(GmsAlarmManager gmsAlarmManager, boolean z) {
        int i = gmsAlarmManager.mConfigupdaterUid;
        int i2 = gmsAlarmManager.mGmsPkgUid;
        if ((gmsAlarmManager.mPolicyControlSwitch & 2) == 0) {
            return;
        }
        if (gmsAlarmManager.mNetworkService == null
                && gmsAlarmManager.getNetworkManagementService() == null) {
            return;
        }
        try {
            gmsAlarmManager.mNetworkService.setFirewallRuleWifi(i2, z);
            gmsAlarmManager.mNetworkService.setFirewallRuleMobileData(i2, z);
            gmsAlarmManager.mNetworkService.setFirewallRuleWifi(i, z);
            gmsAlarmManager.mNetworkService.setFirewallRuleMobileData(i, z);
            gmsAlarmManager.setGmsMultiUserWorkAllow(z);
        } catch (RemoteException e) {
            Slog.e("GmsAlarmManager", "RemoteException:" + e);
        } catch (IllegalStateException e2) {
            Slog.e("GmsAlarmManager", "IllegalStateException:" + e2);
        }
    }

    public GmsAlarmManager(Context context) {
        this.mGmsPkgUid = -1;
        this.mVendingUid = -1;
        this.mConfigupdaterUid = -1;
        this.mGmsPkgAppid = -1;
        this.isChinaMode = false;
        this.isHongKongMode = false;
        this.mContext = context;
        this.isChinaMode =
                "CHINA"
                        .equalsIgnoreCase(
                                SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY));
        this.isHongKongMode =
                "HONG KONG"
                        .equalsIgnoreCase(
                                SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY));
        PackageManager packageManager = context.getPackageManager();
        this.mCurrentIpList = new ArrayList();
        this.mCheckinServerUrl = new ArrayList();
        try {
            int packageUid = packageManager.getPackageUid("com.google.android.gms", 0);
            this.mGmsPkgUid = packageUid;
            this.mVendingUid = packageManager.getPackageUid("com.android.vending", 0);
            this.mConfigupdaterUid =
                    packageManager.getPackageUid("com.google.android.configupdater", 0);
            this.mGmsPkgAppid = UserHandle.getAppId(packageUid);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("GmsAlarmManager", "NameNotFoundException" + e);
        }
    }

    public static Intent getInfoFromPendingIntent(PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ActivityManagerNative.getDefault()
                    .getIntentForIntentSender(pendingIntent.getTarget());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void doDump(PrintWriter printWriter) {
        StringBuilder m$1 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                        printWriter, "  <GmsAlarmManager>", "isChinaMode:");
        boolean z = this.isChinaMode;
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        m$1, z, printWriter, "isHongKongMode:");
        boolean z2 = this.isHongKongMode;
        StringBuilder m2 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        m, z2, printWriter, "mGmsPkgUid:");
        int i = this.mGmsPkgUid;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(m2, i, printWriter);
        if ((z || z2) && i != -1) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("mVendingUid:"),
                            this.mVendingUid,
                            printWriter,
                            "mConfigupdaterUid:"),
                    this.mConfigupdaterUid,
                    printWriter);
            Iterator it = this.mGmsUidOfMultiUser.iterator();
            while (it.hasNext()) {
                printWriter.println("MultiUidList: " + ((Integer) it.next()).intValue());
            }
            StringBuilder m3 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                            .m(
                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                            .m(
                                                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                                            .m(
                                                                                                    new StringBuilder(
                                                                                                            "mUserEnable:"),
                                                                                                    this
                                                                                                            .mUserEnable,
                                                                                                    printWriter,
                                                                                                    "mWaitCheckNetWork:"),
                                                                                    this
                                                                                            .mWaitCheckNetWork,
                                                                                    printWriter,
                                                                                    "mGoogleNetWork:"),
                                                                    this.mGoogleNetWork,
                                                                    printWriter,
                                                                    "isGmsNetWorkLimt:"),
                                                    this.isGmsNetWorkLimt,
                                                    printWriter,
                                                    "mScreenOn:"),
                                            this.mScreenOn,
                                            printWriter,
                                            "mScreenOffChange:"),
                                    this.mScreenOffChange,
                                    printWriter,
                                    "isCharging:"),
                            this.isCharging,
                            printWriter,
                            "mPolicyControlSwitch:");
            m3.append(Integer.toBinaryString(this.mPolicyControlSwitch));
            printWriter.println(m3.toString());
            long elapsedRealtime = SystemClock.elapsedRealtime();
            StringBuilder m4 =
                    Preconditions$$ExternalSyntheticOutline0.m(
                            "Since last 24 hours\n"
                                + "Total time and # of event Google access is available   : "
                                    + this.avaStats.dump(elapsedRealtime)
                                    + "\n",
                            "Total time and # of event Google access is not possible : ");
            m4.append(this.noAvaStats.dump(elapsedRealtime));
            m4.append("\n");
            StringBuilder m5 =
                    Preconditions$$ExternalSyntheticOutline0.m(
                            m4.toString(), "Total time and # of event VPN is connected :");
            m5.append(this.vpnStats.dump(elapsedRealtime));
            printWriter.println(m5.toString());
            Iterator it2 = this.mCheckinServerUrl.iterator();
            while (it2.hasNext()) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m50m(
                        printWriter, "mCheckinServerUrl:", (String) it2.next());
            }
        }
    }

    public final INetworkManagementService getNetworkManagementService() {
        IBinder service;
        if (this.mNetworkService == null
                && (service = ServiceManager.getService("network_management")) != null) {
            this.mNetworkService = INetworkManagementService.Stub.asInterface(service);
        }
        return this.mNetworkService;
    }

    public final void getSettingsValueFromDB() {
        Cursor cursor;
        try {
            cursor =
                    this.mContext
                            .getContentResolver()
                            .query(
                                    SMART_MGR_SETTINGS_URI,
                                    new String[] {"key", "value"},
                                    null,
                                    null,
                                    null);
        } catch (Exception e) {
            Slog.e("GmsAlarmManager", "Exception with contentResolver : " + e.getMessage());
            e.printStackTrace();
            cursor = null;
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    if ("spcm_switch".equals(cursor.getString(0))) {
                        boolean z = Integer.parseInt(cursor.getString(1)) == 3;
                        Slog.d("GmsAlarmManager", "get from smartmanager DB, spcm_switch : " + z);
                        if (this.mUserEnable != z) {
                            this.mUserEnable = z;
                            this.mHandler.sendEmptyMessage(11);
                        }
                    }
                } catch (Exception e2) {
                    NandswapManager$$ExternalSyntheticOutline0.m(
                            e2, new StringBuilder("Exception with parseInt : "), "GmsAlarmManager");
                }
            }
            cursor.close();
        }
    }

    public final void init(AlarmManagerService alarmManagerService) {
        boolean z = this.isChinaMode;
        boolean z2 = this.isHongKongMode;
        if ((z || z2) && this.mGmsPkgUid != -1) {
            HandlerThread handlerThread = sHandlerThread;
            handlerThread.start();
            this.mHandler = new GmsHandler(handlerThread.getLooper());
            if (z2) {
                this.mUserEnable = false;
            }
            this.mAlarmService = alarmManagerService;
            this.mCheckinServerUrl.add("checkin.gstatic.com");
            this.mNetworkReceiver = new AnonymousClass1(this, 2);
            this.mContext.registerReceiver(
                    this.mNetworkReceiver,
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "android.net.conn.CONNECTIVITY_CHANGE"),
                    2);
            this.mScreenReceiver = new AnonymousClass1(this, 4);
            this.mContext.registerReceiver(
                    this.mScreenReceiver,
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                            "android.intent.action.SCREEN_OFF", "android.intent.action.SCREEN_ON"),
                    2);
            this.mUserAddRemoveReceiver = new AnonymousClass1(this, 6);
            this.mContext.registerReceiver(
                    this.mUserAddRemoveReceiver,
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                            "android.intent.action.USER_ADDED",
                            "android.intent.action.USER_REMOVED"),
                    2);
            this.mSetupWizardCompleteOrBootCompleteReceiver = new AnonymousClass1(this, 5);
            this.mContext.registerReceiver(
                    this.mSetupWizardCompleteOrBootCompleteReceiver,
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                            Constants.INTENT_SECSETUPWIZARD_COMPLETE,
                            "android.intent.action.BOOT_COMPLETED"),
                    2);
            this.mSCPMReceiver = new AnonymousClass1(this, 3);
            this.mContext.registerReceiver(
                    this.mSCPMReceiver,
                    BatteryService$$ExternalSyntheticOutline0.m("sec.app.policy.UPDATE.gmsnet"),
                    2);
            this.mBatteryChargingReceiver = new AnonymousClass1(this, 1);
            this.mContext.registerReceiver(
                    this.mBatteryChargingReceiver,
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                            "android.os.action.CHARGING", "android.os.action.DISCHARGING"),
                    2);
            this.mLocalPowerManager =
                    (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
            this.mContext.registerReceiver(
                    this.mIntentReceiver,
                    GmsAlarmManager$$ExternalSyntheticOutline0.m(
                            "com.samsung.android.server.action_check_gms_network",
                            "com.samsung.android.server.action_insert_log",
                            "android.intent.action.BOOT_COMPLETED"),
                    2);
            this.mWaitCheckNetWork = false;
            this.mGoogleNetWork = false;
            this.isCharging = false;
            this.mPendingIntent =
                    PendingIntent.getBroadcast(
                            this.mContext,
                            0,
                            new Intent("com.samsung.android.server.action_check_gms_network"),
                            67108864);
            this.mInsertLogPendingIntent =
                    PendingIntent.getBroadcast(
                            this.mContext,
                            0,
                            new Intent("com.samsung.android.server.action_insert_log"),
                            67108864);
            this.avaStats = new NetWorkStats();
            this.noAvaStats = new NetWorkStats();
            this.vpnStats = new NetWorkStats();
        }
    }

    public final boolean isVPNConnected() {
        NetworkInfo networkInfo =
                ((ConnectivityManager) this.mContext.getSystemService("connectivity"))
                        .getNetworkInfo(17);
        return networkInfo != null && networkInfo.isConnected();
    }

    public final void setGmsMultiUserWorkAllow(boolean z) {
        if (this.mNetworkService == null && getNetworkManagementService() == null) {
            return;
        }
        try {
            Iterator it = this.mGmsUidOfMultiUser.iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                this.mNetworkService.setFirewallRuleWifi(num.intValue(), z);
                this.mNetworkService.setFirewallRuleMobileData(num.intValue(), z);
            }
        } catch (RemoteException e) {
            Slog.e("GmsAlarmManager", "RemoteException:" + e);
        } catch (IllegalStateException e2) {
            Slog.e("GmsAlarmManager", "IllegalStateException:" + e2);
        }
    }

    public final void setUrlFirewallRule(ArrayList arrayList, boolean z) {
        int i = this.mGmsPkgUid;
        try {
            if (this.mNetworkService == null && getNetworkManagementService() == null) {
                return;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                Slog.d(
                        "GmsAlarmManager",
                        "[GMS-CORE] setUrlFirewallRule, ip = "
                                + str
                                + " isDeleteRule = "
                                + z
                                + " mGmsPkgUid = "
                                + i);
                this.mNetworkService.setUrlFirewallRuleMobileData(i, str, z);
                this.mNetworkService.setUrlFirewallRuleWifi(i, str, z);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final ArrayList updateMultiUserGmsUid(int i) {
        PackageManager packageManager = this.mContext.getPackageManager();
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "get Multi userId: ", "GmsAlarmManager");
        if (i > -1) {
            try {
                int packageUidAsUser =
                        packageManager.getPackageUidAsUser("com.google.android.gms", 0, i);
                int packageUidAsUser2 =
                        packageManager.getPackageUidAsUser("com.android.vending", 0, i);
                int packageUidAsUser3 =
                        packageManager.getPackageUidAsUser(
                                "com.google.android.configupdater", 0, i);
                arrayList.add(Integer.valueOf(packageUidAsUser));
                arrayList.add(Integer.valueOf(packageUidAsUser3));
                Slog.v(
                        "GmsAlarmManager",
                        "gmsuid = "
                                + packageUidAsUser
                                + " vendinguid = "
                                + packageUidAsUser2
                                + " configupdate = "
                                + packageUidAsUser3);
            } catch (PackageManager.NameNotFoundException e) {
                Slog.v("GmsAlarmManager", "NameNotFoundException" + e);
            }
        }
        return arrayList;
    }
}
