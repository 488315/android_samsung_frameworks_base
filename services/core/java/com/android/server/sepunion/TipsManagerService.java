package com.android.server.sepunion;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.sepunion.ITipsManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TipsManagerService extends ITipsManager.Stub implements AbsSemSystemService {
    public static final Long mLastDeviceConnectMsgTime = new Long(0);
    public boolean mBootupCompleted;
    public final Context mContext;
    public int mDialCount;
    public int mHUNDisplayCount;
    public final TipsPackageReceiver mPackageReceiver;
    public final TipsReceiver mTipReceiver;
    public boolean mTipsHUNAlreadyShown;
    public TipsHandler mTipsHandler;
    public boolean mTipsNetworkGranted;
    public boolean mTipsOnBoot;
    public boolean mTipsPackageExist;
    public boolean mWaitingCallEnd;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TipsHandler extends Handler {
        public TipsHandler() {
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            TipsManagerService tipsManagerService = TipsManagerService.this;
            if (SemEmergencyManager.getInstance(tipsManagerService.mContext) != null ? SemEmergencyManager.isEmergencyMode(tipsManagerService.mContext) : false) {
                Long l = TipsManagerService.mLastDeviceConnectMsgTime;
                Log.d("TipsManagerService", "[GalaxyTips] Fail to send intent to Tips at emergency mode.");
                return;
            }
            int i = message.what;
            if (i != 10001) {
                if (i != 10004) {
                    return;
                }
                Long l2 = TipsManagerService.mLastDeviceConnectMsgTime;
                Log.e("TipsManagerService", "[GalaxyTips] Send FOTA DONE.");
                Intent intent = new Intent();
                intent.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
                intent.putExtra("tips_extras", 5);
                try {
                    tipsManagerService.mContext.startForegroundServiceAsUser(intent, UserHandle.CURRENT);
                    return;
                } catch (Exception unused) {
                    Long l3 = TipsManagerService.mLastDeviceConnectMsgTime;
                    Log.d("TipsManagerService", "[GalaxyTips] Fail to send FOTA DONE intent to Tips.");
                    return;
                }
            }
            if (tipsManagerService.mTipsNetworkGranted || tipsManagerService.mHUNDisplayCount >= 3) {
                return;
            }
            Intent intent2 = new Intent();
            intent2.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
            intent2.putExtra("tips_extras", 2);
            try {
                tipsManagerService.mContext.startForegroundServiceAsUser(intent2, UserHandle.CURRENT);
            } catch (Exception unused2) {
                Long l4 = TipsManagerService.mLastDeviceConnectMsgTime;
                Log.d("TipsManagerService", "[GalaxyTips] Fail to send MSG_START_TIPS_HUN intent to Tips.");
            }
            tipsManagerService.mHUNDisplayCount++;
            TipsManagerService.m873$$Nest$smsendMsg(tipsManagerService.mTipsHandler, 10001, 604800000);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TipsManagerServiceThread extends Thread {
        public TipsManagerServiceThread() {
            super("TipsManagerService");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Looper.prepare();
            synchronized (TipsManagerService.this) {
                TipsManagerService.this.mTipsHandler = TipsManagerService.this.new TipsHandler();
                TipsManagerService.this.notify();
            }
            Looper.loop();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TipsPackageReceiver extends BroadcastReceiver {
        public TipsPackageReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals("android.intent.action.PACKAGE_INSTALL") && !intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
                if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED") && "com.samsung.android.app.tips".equals(intent.getData().getEncodedSchemeSpecificPart())) {
                    Long l = TipsManagerService.mLastDeviceConnectMsgTime;
                    Log.e("TipsManagerService", "[GalaxyTips] Tips was uninstalled.");
                    TipsManagerService.this.mTipsPackageExist = false;
                    return;
                }
                return;
            }
            if ("com.samsung.android.app.tips".equals(intent.getData().getEncodedSchemeSpecificPart())) {
                Long l2 = TipsManagerService.mLastDeviceConnectMsgTime;
                Log.e("TipsManagerService", "[GalaxyTips] Tips was installed. Start to register all filters");
                TipsManagerService tipsManagerService = TipsManagerService.this;
                tipsManagerService.mTipsPackageExist = true;
                if (tipsManagerService.mTipsOnBoot) {
                    context.unregisterReceiver(tipsManagerService.mTipReceiver);
                }
                IntentFilter m = GmsAlarmManager$$ExternalSyntheticOutline0.m("android.intent.action.LOCALE_CHANGED", "samsung.galaxy.tips.application.terminated", "android.intent.action.USER_SWITCHED");
                if (!TipsManagerService.this.mTipsNetworkGranted) {
                    m.addAction("samsung.galaxy.tips.network_granted");
                }
                if (!TipsManagerService.this.mTipsHUNAlreadyShown) {
                    m.addAction("android.intent.action.NEW_OUTGOING_CALL");
                    m.addAction("android.intent.action.PHONE_STATE");
                }
                context.registerReceiverAsUser(TipsManagerService.this.mTipReceiver, UserHandle.ALL, m, null, null, 2);
                TipsManagerService.this.mTipsOnBoot = true;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TipsReceiver extends BroadcastReceiver {
        public TipsReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int intForUser = Settings.Secure.getIntForUser(TipsManagerService.this.mContext.getContentResolver(), "user_setup_complete", 0, -2);
            boolean isEmergencyMode = SemEmergencyManager.getInstance(context) != null ? SemEmergencyManager.isEmergencyMode(context) : false;
            if (intForUser != 1 || isEmergencyMode || !TipsManagerService.this.mTipsPackageExist) {
                Long l = TipsManagerService.mLastDeviceConnectMsgTime;
                Log.e("TipsManagerService", "[GalaxyTips] Got a " + intent.getAction() + ". But can't perform.(completeSetupWizard= " + intForUser + " EmergencyMode= " + isEmergencyMode + " TipsExist= " + TipsManagerService.this.mTipsPackageExist + ")");
                return;
            }
            try {
                String action = intent.getAction();
                TipsManagerService tipsManagerService = TipsManagerService.this;
                if (!tipsManagerService.mTipsHUNAlreadyShown && !tipsManagerService.mWaitingCallEnd && action.equals("android.intent.action.NEW_OUTGOING_CALL")) {
                    TipsManagerService.this.mWaitingCallEnd = true;
                    return;
                }
                if (TipsManagerService.this.mWaitingCallEnd && action.equals("android.intent.action.PHONE_STATE")) {
                    Bundle extras = intent.getExtras();
                    if (extras == null || !extras.getString(LauncherConfigurationInternal.KEY_STATE_BOOLEAN).equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                        return;
                    }
                    TipsManagerService tipsManagerService2 = TipsManagerService.this;
                    tipsManagerService2.mWaitingCallEnd = false;
                    if (tipsManagerService2.mDialCount != 5) {
                        ContentResolver contentResolver = tipsManagerService2.mContext.getContentResolver();
                        TipsManagerService tipsManagerService3 = TipsManagerService.this;
                        int i = tipsManagerService3.mDialCount + 1;
                        tipsManagerService3.mDialCount = i;
                        Settings.System.putIntForUser(contentResolver, "tips_trigger_count", i, -2);
                        return;
                    }
                    Long l2 = TipsManagerService.mLastDeviceConnectMsgTime;
                    Log.d("TipsManagerService", "[GalaxyTips]TIPS Activitation!! WAKE UP");
                    Settings.System.putIntForUser(TipsManagerService.this.mContext.getContentResolver(), "tips_trigger_count", 1000, -2);
                    TipsManagerService tipsManagerService4 = TipsManagerService.this;
                    if (!tipsManagerService4.mTipsNetworkGranted) {
                        TipsManagerService.m873$$Nest$smsendMsg(tipsManagerService4.mTipsHandler, 10001, 60000);
                    }
                    TipsManagerService.this.mTipsHUNAlreadyShown = true;
                    return;
                }
                if (!TipsManagerService.this.mTipsNetworkGranted && action.equals("samsung.galaxy.tips.network_granted")) {
                    TipsManagerService tipsManagerService5 = TipsManagerService.this;
                    tipsManagerService5.mTipsNetworkGranted = true;
                    Settings.System.putIntForUser(tipsManagerService5.mContext.getContentResolver(), "gtips_network_granted", 1, -2);
                    return;
                }
                if (TipsManagerService.this.mBootupCompleted && action.equals("android.intent.action.LOCALE_CHANGED")) {
                    Intent intent2 = new Intent();
                    intent2.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
                    intent2.putExtra("tips_extras", 1);
                    try {
                        TipsManagerService.this.mContext.startForegroundServiceAsUser(intent2, UserHandle.CURRENT);
                        return;
                    } catch (Exception unused) {
                        Long l3 = TipsManagerService.mLastDeviceConnectMsgTime;
                        Log.d("TipsManagerService", "[GalaxyTips] Fail to send MSG_NEW_DATA_UPDATED intent to Tips.");
                        return;
                    }
                }
                if (TipsManagerService.this.mBootupCompleted && action.equals("android.intent.action.USER_SWITCHED")) {
                    if (Settings.System.getIntForUser(TipsManagerService.this.mContext.getContentResolver(), "gtips_network_granted", 0, -2) == 1) {
                        TipsManagerService.this.mTipsNetworkGranted = true;
                    } else {
                        TipsManagerService.this.mTipsNetworkGranted = false;
                    }
                    Long l4 = TipsManagerService.mLastDeviceConnectMsgTime;
                    Log.d("TipsManagerService", "USER_SWITCHED isNetworkGranted " + TipsManagerService.this.mTipsNetworkGranted);
                    return;
                }
                if (TipsManagerService.this.mBootupCompleted && action.equals("com.sec.android.app.secsetupwizard.FOTA_SUW_COMPLETE")) {
                    TipsManagerService tipsManagerService6 = TipsManagerService.this;
                    if (tipsManagerService6.mTipsPackageExist) {
                        TipsManagerService.m873$$Nest$smsendMsg(tipsManagerService6.mTipsHandler, 10004, 120000);
                    }
                }
            } catch (Exception unused2) {
                Long l5 = TipsManagerService.mLastDeviceConnectMsgTime;
                Log.d("TipsManagerService", "Fail to send intent to Tips.");
            }
        }
    }

    /* renamed from: -$$Nest$smsendMsg, reason: not valid java name */
    public static void m873$$Nest$smsendMsg(Handler handler, int i, int i2) {
        handler.removeMessages(i);
        synchronized (mLastDeviceConnectMsgTime) {
            handler.sendMessageAtTime(handler.obtainMessage(i, 0, 0, null), SystemClock.uptimeMillis() + i2);
        }
    }

    public TipsManagerService(Context context) {
        TipsReceiver tipsReceiver = new TipsReceiver();
        this.mTipReceiver = tipsReceiver;
        this.mPackageReceiver = new TipsPackageReceiver();
        this.mDialCount = 1;
        this.mHUNDisplayCount = 0;
        this.mWaitingCallEnd = false;
        this.mTipsHUNAlreadyShown = false;
        this.mTipsNetworkGranted = false;
        this.mTipsPackageExist = true;
        this.mBootupCompleted = false;
        this.mTipsOnBoot = true;
        this.mContext = context;
        Log.d("TipsManagerService", "[GalaxyTips] TipsManagerService");
        try {
            context.getPackageManager().getPackageInfo("com.samsung.android.app.tips", 1);
            IntentFilter intentFilter = new IntentFilter();
            int intForUser = Settings.System.getIntForUser(context.getContentResolver(), "tips_trigger_count", 0, -2);
            this.mDialCount = intForUser;
            if (intForUser == 1000) {
                this.mTipsHUNAlreadyShown = true;
            }
            if (Settings.System.getIntForUser(context.getContentResolver(), "gtips_network_granted", 0, -2) == 1) {
                this.mTipsNetworkGranted = true;
            }
            intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
            intentFilter.addAction("samsung.galaxy.tips.application.terminated");
            intentFilter.addAction("android.intent.action.USER_SWITCHED");
            intentFilter.addAction("com.sec.android.app.secsetupwizard.FOTA_SUW_COMPLETE");
            if (!this.mTipsNetworkGranted) {
                intentFilter.addAction("samsung.galaxy.tips.network_granted");
            }
            if (!this.mTipsHUNAlreadyShown) {
                intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
                intentFilter.addAction("android.intent.action.PHONE_STATE");
            }
            context.registerReceiverAsUser(tipsReceiver, UserHandle.ALL, intentFilter, null, null, 2);
        } catch (PackageManager.NameNotFoundException unused) {
            this.mTipsPackageExist = false;
            this.mTipsOnBoot = false;
        }
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.PACKAGE_INSTALL");
        intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter2.addDataScheme("package");
        this.mContext.registerReceiverAsUser(this.mPackageReceiver, UserHandle.ALL, intentFilter2, null, null, 2);
        new TipsManagerServiceThread().start();
    }

    public static int getDeviceVersion(Context context) {
        int i = Build.VERSION.SEM_PLATFORM_INT;
        if (i < 80100) {
            return 0;
        }
        return Integer.parseInt(String.valueOf(i / 10000) + String.valueOf((i % 10000) / 100));
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (strArr != null && strArr.length > 0) {
            String str = strArr[0];
            if ("welcome".equals(str)) {
                Intent intent = new Intent();
                intent.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
                intent.putExtra("tips_extras", 2);
                this.mContext.startForegroundServiceAsUser(intent, UserHandle.CURRENT);
                return;
            }
            if ("clearall".equals(str)) {
                Intent intent2 = new Intent();
                intent2.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
                intent2.putExtra("tips_extras", 1);
                this.mContext.startForegroundServiceAsUser(intent2, UserHandle.CURRENT);
                return;
            }
            if ("showbasic".equals(str)) {
                Intent intent3 = new Intent();
                intent3.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
                intent3.putExtra("tips_extras", 3);
                this.mContext.startForegroundServiceAsUser(intent3, UserHandle.CURRENT);
                return;
            }
            if ("getcustom".equals(str)) {
                Intent intent4 = new Intent();
                intent4.setAction("android.intent.action.MAIN");
                intent4.setFlags(402653184);
                intent4.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsSmartItemDetailActivity");
                intent4.putExtra("custom_inventory", strArr[1]);
                this.mContext.startActivity(intent4);
                return;
            }
            if ("fotadone".equals(str)) {
                Intent intent5 = new Intent();
                intent5.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
                intent5.putExtra("tips_extras", 5);
                this.mContext.startForegroundServiceAsUser(intent5, UserHandle.CURRENT);
                return;
            }
            if ("fotaready".equals(str)) {
                Intent intent6 = new Intent();
                intent6.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
                intent6.putExtra("tips_extras", 4);
                intent6.putExtra("tips_extras2", strArr[1]);
                this.mContext.startForegroundServiceAsUser(intent6, UserHandle.CURRENT);
                return;
            }
            if ("showrecent".equals(str)) {
                Intent intent7 = new Intent();
                intent7.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
                intent7.putExtra("tips_extras", 6);
                this.mContext.startForegroundServiceAsUser(intent7, UserHandle.CURRENT);
                return;
            }
            if ("showcontent".equals(str)) {
                Intent intent8 = new Intent();
                intent8.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
                intent8.putExtra("tips_extras", 7);
                intent8.putExtra("tips_extras2", strArr[1]);
                this.mContext.startForegroundServiceAsUser(intent8, UserHandle.CURRENT);
                return;
            }
            if ("showjit".equals(str)) {
                Intent intent9 = new Intent();
                intent9.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
                intent9.putExtra("tips_extras", 8);
                intent9.putExtra("tips_extras2", strArr[1]);
                if (strArr.length == 2) {
                    intent9.putExtra("tips_extras4", "");
                    intent9.putExtra("tips_extras3", "");
                    intent9.putExtra("tips_extras5", "");
                    intent9.putExtra("tips_extras6", "");
                } else if (strArr.length == 3) {
                    intent9.putExtra("tips_extras4", strArr[2]);
                    intent9.putExtra("tips_extras3", "");
                    intent9.putExtra("tips_extras5", "");
                    intent9.putExtra("tips_extras6", "");
                } else if (strArr.length == 4) {
                    intent9.putExtra("tips_extras4", strArr[2]);
                    intent9.putExtra("tips_extras3", strArr[3]);
                    intent9.putExtra("tips_extras5", "");
                    intent9.putExtra("tips_extras6", "");
                } else if (strArr.length == 5) {
                    intent9.putExtra("tips_extras4", strArr[2]);
                    intent9.putExtra("tips_extras3", strArr[3]);
                    intent9.putExtra("tips_extras5", strArr[4]);
                    intent9.putExtra("tips_extras6", "");
                } else if (strArr.length == 6) {
                    intent9.putExtra("tips_extras4", strArr[2]);
                    intent9.putExtra("tips_extras3", strArr[3]);
                    intent9.putExtra("tips_extras5", strArr[4]);
                    intent9.putExtra("tips_extras6", strArr[5]);
                }
                this.mContext.startForegroundServiceAsUser(intent9, UserHandle.CURRENT);
                return;
            }
            if ("showtip".equals(str)) {
                Intent intent10 = new Intent();
                intent10.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
                intent10.putExtra("tips_extras", 9);
                intent10.putExtra("tips_id", strArr[1]);
                intent10.putExtra("tips_title", strArr[2]);
                intent10.putExtra("tips_text", strArr[3]);
                intent10.putExtra("tips_condition", Integer.parseInt(strArr[4]));
                this.mContext.startForegroundServiceAsUser(intent10, UserHandle.CURRENT);
                return;
            }
        }
        printWriter.print("\n\n");
        printWriter.println("[Galaxy Tips]");
        if (!this.mTipsPackageExist) {
            printWriter.println("Galaxy Tips is not exist this device");
            return;
        }
        printWriter.println("\n\n");
        printWriter.println("ETC Values");
        printWriter.println("==========================================================================");
        printWriter.print("mTipsNetworkGranted = ");
        printWriter.println(this.mTipsNetworkGranted);
        printWriter.print("mTipsHUNAlreadyShown = ");
        printWriter.println(this.mTipsHUNAlreadyShown);
        printWriter.print("mDialCount = ");
        printWriter.println(this.mDialCount);
    }

    public final AbsSemSystemService getSemSystemService(String str) {
        return null;
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            this.mBootupCompleted = true;
        }
    }

    public final void onCleanupUser(int i) {
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onCreate(Bundle bundle) {
    }

    public final void onDestroy() {
    }

    public final void onStart() {
    }

    public final void onStartUser(int i) {
    }

    public final void onStopUser(int i) {
    }

    public final void onSwitchUser(int i) {
    }

    public final void onUnlockUser(int i) {
    }
}
