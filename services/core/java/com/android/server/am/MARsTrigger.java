package com.android.server.am;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Slog;
import com.android.server.am.mars.MARsBigData;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.MARsHistoryLog;
import com.android.server.am.mars.database.MARsDBManager;
import com.android.server.am.mars.filter.filter.ActiveTrafficFilter;
import com.android.server.am.mars.filter.filter.AllowListFilter;
import com.android.server.am.mars.filter.filter.LatestProtectedPackageFilter;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class MARsTrigger {
    public AlarmManager mAlarm;
    public BroadcastReceiver mAppStartUpIntentReceiver;
    public Context mContext;
    public boolean mEmStateReceiverRegistered;
    public BroadcastReceiver mEmergencyStateChangedReceiver;
    public BroadcastReceiver mIntentReceiver;
    public long mLastTimeChangeClockTime;
    public long mLastTimeChangeRealtime;
    public PendingIntent mMARsFirstTriggerPolicyAlarmIntent;
    public BroadcastReceiver mPolicyGameIntentReceiver;
    public BroadcastReceiver mPolicyIntentReceiver;
    public BroadcastReceiver mPolicyMPSMIntentReceiver;
    public BroadcastReceiver mPolicySBikeIntentReceiver;
    public BroadcastReceiver mPolicyUDSIntentReceiver;
    public boolean mReceiverRegistered;
    public BroadcastReceiver mSMDBChangedReceiver;
    public boolean mSMDBChangedReceiverRegistered;
    public BroadcastReceiver mTCPUReceiver;
    public BroadcastReceiver mTimeIntentReceiver;
    public BroadcastReceiver mTriggerIntentReceiver;
    public BroadcastReceiver mUserActionReceiver;
    public BroadcastReceiver mUserIntentReceiver;
    public UserHandle user;

    public abstract class MARsTriggerHolder {
        public static final MARsTrigger INSTANCE = new MARsTrigger();
    }

    public MARsTrigger() {
        this.mReceiverRegistered = false;
        this.mEmStateReceiverRegistered = false;
        this.mSMDBChangedReceiverRegistered = false;
        this.mIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent == null || intent.getAction() == null) {
                    return;
                }
                String action = intent.getAction();
                intent.getExtras();
                if (action.equals("android.intent.action.SCREEN_ON")) {
                    MARsPolicyManager.getInstance().setScreenOnState(true);
                    if (MARsTrigger.this.mMARsFirstTriggerPolicyAlarmIntent != null) {
                        MARsTrigger.this.mAlarm.cancel(MARsTrigger.this.mMARsFirstTriggerPolicyAlarmIntent);
                    }
                    MARsHandler.getInstance().removeMessages(1);
                    MARsHandler.getInstance().removeMessages(2);
                    return;
                }
                if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    ActiveTrafficFilter.getInstance().updateDataConnectionInfo();
                    return;
                }
                if (action.equals(UiModeManager.ACTION_ENTER_CAR_MODE)) {
                    MARsPolicyManager.getInstance().setCarModeOnState(true);
                    return;
                }
                if (action.equals("android.intent.action.SIM_STATE_CHANGED")) {
                    AllowListFilter.getInstance().setCarrierAllowList();
                } else if (action.equals("MARS_REQUEST_DB_COMPLETE")) {
                    MARsPolicyManager.getInstance().checkUpdatedDBFromSDHMS();
                } else if (action.equals("com.samsung.android.sm.ACTION_SCPM_MARS_SETTINGS_UPDATED")) {
                    MARsDBManager.getInstance().sendGetSCPMPolicyMsgToDBHandler();
                }
            }
        };
        this.mTriggerIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent == null || intent.getAction() == null) {
                    return;
                }
                String action = intent.getAction();
                intent.getExtras();
                if (action.equals("android.intent.action.SCREEN_OFF")) {
                    MARsPolicyManager.getInstance().setScreenOnState(false);
                    if (FreecessController.getInstance().getIsDumpstateWorking() || MARsPolicyManager.getInstance().getCarModeOnState()) {
                        return;
                    }
                    if (MARsPolicyManager.getInstance().checkIsChinaModel()) {
                        MARsHandler.getInstance().sendFirstTriggerMsgToMainHandler();
                        return;
                    } else {
                        MARsTrigger.this.setAlarm("FIRST_ALARM_TRIGGER_ACTION", 5000L);
                        return;
                    }
                }
                if (action.equals(UiModeManager.ACTION_EXIT_CAR_MODE)) {
                    MARsPolicyManager.getInstance().setCarModeOnState(false);
                }
            }
        };
        this.mPolicyIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Bundle extras;
                String action = intent.getAction();
                if (action == null) {
                    return;
                }
                if (action.equals("FIRST_ALARM_TRIGGER_ACTION")) {
                    Slog.d("MARsTrigger", "mPolicyIntentReceiver broadcast received action : " + action);
                    if (!MARsPolicyManager.getInstance().getScreenOnState() && !FreecessController.getInstance().getIsDumpstateWorking()) {
                        MARsHandler.getInstance().sendFirstTriggerMsgToMainHandler();
                        MARsHandler.getInstance().sendRepeatTriggerMsgToMainHandler();
                    }
                }
                if (!action.equals("com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY") || (extras = intent.getExtras()) == null) {
                    return;
                }
                int policyNameToInt = MARsTrigger.this.policyNameToInt(extras.getString("POLICY_NAME", ""));
                ArrayList<String> stringArrayList = extras.getStringArrayList("PACKAGE_NAME");
                if (policyNameToInt == 0 || stringArrayList == null) {
                    return;
                }
                MARsHandler.getInstance().sendRunPolicySpecificPkgMsgToMainHandler(stringArrayList, policyNameToInt, null, MARsTrigger.this.mContext.getUserId());
            }
        };
        this.mPolicyUDSIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                ArrayList<String> stringArrayList;
                if (intent == null || intent.getAction() == null) {
                    return;
                }
                String action = intent.getAction();
                if (action.equals("com.android.server.am.MARS_TRIGGER_UDS_POLICY")) {
                    Bundle extras = intent.getExtras();
                    if (extras == null || (stringArrayList = extras.getStringArrayList("PACKAGE_NAME")) == null) {
                        return;
                    }
                    MARsHandler.getInstance().sendRunPolicySpecificPkgMsgToMainHandler(stringArrayList, 5, MARsTrigger.this.actionToString(action), MARsTrigger.this.mContext.getUserId());
                    return;
                }
                if (action.equals("com.android.server.am.MARS_CANCEL_UDS_POLICY")) {
                    Bundle extras2 = intent.getExtras();
                    if (extras2 != null) {
                        ArrayList<String> stringArrayList2 = extras2.getStringArrayList("PACKAGE_NAME");
                        if (stringArrayList2 != null) {
                            MARsHandler.getInstance().sendCancelPolicyMsgToMainHandler(stringArrayList2, 5, MARsTrigger.this.mContext.getUserId());
                            return;
                        }
                        return;
                    }
                    MARsHandler.getInstance().sendCancelPolicyMsgToMainHandler(null, 5, MARsTrigger.this.mContext.getUserId());
                }
            }
        };
        this.mPolicySBikeIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                ArrayList<String> stringArrayList;
                if (intent == null || intent.getAction() == null) {
                    return;
                }
                String action = intent.getAction();
                if (action.equals("com.android.server.am.MARS_TRIGGER_SBIKE_MODE_POLICY")) {
                    Bundle extras = intent.getExtras();
                    if (extras == null || (stringArrayList = extras.getStringArrayList("PACKAGE_NAME")) == null) {
                        return;
                    }
                    MARsHandler.getInstance().sendRunPolicySpecificPkgMsgToMainHandler(stringArrayList, 6, MARsTrigger.this.actionToString(action), MARsTrigger.this.mContext.getUserId());
                    return;
                }
                if (action.equals("com.android.server.am.MARS_CANCEL_SBIKE_MODE_POLICY")) {
                    Bundle extras2 = intent.getExtras();
                    if (extras2 != null) {
                        ArrayList<String> stringArrayList2 = extras2.getStringArrayList("PACKAGE_NAME");
                        if (stringArrayList2 != null) {
                            MARsHandler.getInstance().sendCancelPolicyMsgToMainHandler(stringArrayList2, 6, MARsTrigger.this.mContext.getUserId());
                            return;
                        }
                        return;
                    }
                    MARsHandler.getInstance().sendCancelPolicyMsgToMainHandler(null, 6, MARsTrigger.this.mContext.getUserId());
                }
            }
        };
        this.mTCPUReceiver = new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.6
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                MARsHandler.getInstance().sendTCPUMsgToMainHandler(intent.getIntegerArrayListExtra("pid"));
                Slog.d("MARsTrigger", "broadcast received action TCPU: " + intent.getAction());
            }
        };
        this.mPolicyGameIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent == null || intent.getAction() == null) {
                    return;
                }
                String stringExtra = intent.getStringExtra("package");
                if (stringExtra == null || "com.samsung.android.game.gos".equals(stringExtra)) {
                    String action = intent.getAction();
                    if (action.equals("com.android.server.am.MARS_TRIGGER_GAME_MODE_POLICY")) {
                        Bundle extras = intent.getExtras();
                        if (extras != null) {
                            ArrayList<String> stringArrayList = extras.getStringArrayList("survive_app");
                            LatestProtectedPackageFilter.getInstance().setProtectAppCntForGame(extras.getInt("lru_num"));
                            if (!FreecessController.getInstance().getFreecessEnabled() || !FreecessController.IS_SUPPORT_CALM_MODE) {
                                MARsHandler.getInstance().sendRunPolicySpecificPkgMsgToMainHandler(stringArrayList, 9, MARsTrigger.this.actionToString(action), MARsTrigger.this.mContext.getUserId());
                                return;
                            } else {
                                FreecessHandler.getInstance().sendCalmModeTriggerMsg(stringArrayList);
                                return;
                            }
                        }
                        return;
                    }
                    if (action.equals("com.android.server.am.MARS_CANCEL_GAME_MODE_POLICY")) {
                        if (!FreecessController.getInstance().isCalmModeOnoff()) {
                            if (!MARsPolicyManager.getInstance().isChinaPolicyEnabled()) {
                                MARsHandler.getInstance().sendCancelPolicyMsgToMainHandler(null, 9, -1);
                            }
                        } else {
                            FreecessHandler.getInstance().sendCalmModeCancelMsg();
                        }
                        Slog.d("MARsTrigger", "broadcast received action : MARS_CANCEL_GAME_MODE_POLICY");
                    }
                }
            }
        };
        this.mPolicyMPSMIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.8
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                ArrayList<String> stringArrayList;
                if (intent == null || intent.getAction() == null) {
                    return;
                }
                String action = intent.getAction();
                if (action.equals("com.android.server.am.MARS_TRIGGER_MPSM_POLICY")) {
                    Slog.d("MARsTrigger", "broadcast received action : MARS_TRIGGER_MPSM_POLICY");
                    Bundle extras = intent.getExtras();
                    if (extras == null || (stringArrayList = extras.getStringArrayList("PACKAGE_NAME")) == null) {
                        return;
                    }
                    MARsHandler.getInstance().sendRunPolicySpecificPkgMsgToMainHandler(stringArrayList, 10, MARsTrigger.this.actionToString(action), MARsTrigger.this.mContext.getUserId());
                    return;
                }
                if (action.equals("com.android.server.am.MARS_CANCEL_MPSM_POLICY")) {
                    Bundle extras2 = intent.getExtras();
                    if (extras2 != null) {
                        ArrayList<String> stringArrayList2 = extras2.getStringArrayList("PACKAGE_NAME");
                        if (stringArrayList2 != null) {
                            MARsHandler.getInstance().sendCancelPolicyMsgToMainHandler(stringArrayList2, 10, MARsTrigger.this.mContext.getUserId());
                            return;
                        }
                        return;
                    }
                    MARsHandler.getInstance().sendCancelPolicyMsgToMainHandler(null, 10, MARsTrigger.this.mContext.getUserId());
                }
            }
        };
        this.mTimeIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.9
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent == null || intent.getAction() == null || !intent.getAction().equals("android.intent.action.TIME_SET")) {
                    return;
                }
                long currentTimeMillis = System.currentTimeMillis();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                MARsHandler.getInstance().sendTimeChangedMsgToMainHandler(currentTimeMillis - (MARsTrigger.this.mLastTimeChangeClockTime + (elapsedRealtime - MARsTrigger.this.mLastTimeChangeRealtime)));
                MARsTrigger.this.mLastTimeChangeClockTime = currentTimeMillis;
                MARsTrigger.this.mLastTimeChangeRealtime = elapsedRealtime;
            }
        };
        this.mUserActionReceiver = new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.10
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                UserInfo userInfo;
                UserInfo userInfo2;
                UserInfo userInfo3;
                if (intent == null || intent.getAction() == null) {
                    return;
                }
                String action = intent.getAction();
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                Slog.d("MARsTrigger", "mUserActionReceiver : " + action + " userId = " + intExtra);
                if ("android.intent.action.USER_STOPPED".equals(action)) {
                    if (intExtra >= 95 && intExtra <= 99) {
                        MARsPolicyManager.getInstance().setDualAppEnabled(false);
                        return;
                    }
                    UserManager userManager = (UserManager) context.getSystemService("user");
                    if (userManager == null || (userInfo3 = userManager.getUserInfo(intExtra)) == null || !userInfo3.isManagedProfile()) {
                        return;
                    }
                    MARsPolicyManager.getInstance().setManagedProfileEnabled(false, intExtra);
                    return;
                }
                if ("android.intent.action.USER_STARTED".equals(action)) {
                    if (intExtra >= 95 && intExtra <= 99) {
                        MARsPolicyManager.getInstance().setDualAppEnabled(true);
                        return;
                    }
                    UserManager userManager2 = (UserManager) context.getSystemService("user");
                    if (userManager2 == null || (userInfo2 = userManager2.getUserInfo(intExtra)) == null || !userInfo2.isManagedProfile()) {
                        return;
                    }
                    MARsPolicyManager.getInstance().setManagedProfileEnabled(true, intExtra);
                    return;
                }
                if ("android.intent.action.USER_ADDED".equals(action)) {
                    if (intExtra >= 95 && intExtra <= 99) {
                        MARsPolicyManager.getInstance().setDualAppEnabled(true);
                        if (intExtra != MARsPolicyManager.getInstance().getDualAppUserId()) {
                            MARsPolicyManager.getInstance().setDualAppUserId(intExtra);
                            return;
                        }
                        return;
                    }
                    UserManager userManager3 = (UserManager) context.getSystemService("user");
                    if (userManager3 == null || (userInfo = userManager3.getUserInfo(intExtra)) == null || !userInfo.isManagedProfile()) {
                        return;
                    }
                    MARsPolicyManager.getInstance().setManagedProfileEnabled(true, intExtra);
                }
            }
        };
        this.mUserIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.11
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent == null || intent.getAction() == null) {
                    return;
                }
                String action = intent.getAction();
                Slog.d("MARsTrigger", "broadcast received action : " + action);
                if ("android.intent.action.USER_SWITCHED".equals(action)) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
                    Slog.d("MARsTrigger", "mContext.id = " + MARsTrigger.this.mContext.getUserId());
                    if (MARsPolicyManager.App_StartUp_History) {
                        MARsDBManager.getInstance().doUpdateCompHistory(false);
                    }
                    MARsPolicyManager.getInstance().switchUser(intExtra);
                    MARsDBManager.getInstance().sendUpdateNotiTimeMsgToMainHandler(intExtra);
                }
            }
        };
        this.mAppStartUpIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.12
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent == null || intent.getAction() == null) {
                    return;
                }
                String action = intent.getAction();
                Slog.d("MARsTrigger", "mAppStartUpIntentReceiver onReceive : " + action);
                if (action.equals("android.intent.action.ACTION_SHUTDOWN") || action.equals("android.intent.action.REBOOT")) {
                    MARsBigData.getInstance(MARsTrigger.this.mContext).sendBigDataInfoToHQM();
                    MARsPolicyManager.getInstance().updateResetTime();
                    if (MARsDebugConfig.DEBUG_MID || MARsDebugConfig.DEBUG_HIGH) {
                        MARsHistoryLog.getInstance().mSaveLogThread.start();
                    }
                    if (MARsPolicyManager.App_StartUp_History) {
                        MARsDBManager.getInstance().doUpdateCompHistory(true);
                    }
                }
            }
        };
        this.mEmergencyStateChangedReceiver = new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.13
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent == null || intent.getAction() == null || !intent.getAction().equals("com.samsung.intent.action.EMERGENCY_STATE_CHANGED") || intent.getIntExtra("reason", 0) != 5) {
                    return;
                }
                Slog.d("MARsTrigger", "disable ultra power saving mode");
                MARsPolicyManager.getInstance();
                if (!MARsPolicyManager.MARs_ENABLE) {
                    MARsTrigger.this.unregisterEmStateReceiver();
                    MARsPolicyManager.getInstance().postInit(true);
                } else {
                    MARsPolicyManager.getInstance().setSubUserIds();
                }
            }
        };
        this.mSMDBChangedReceiver = new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.14
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                boolean z;
                boolean z2;
                if (intent == null || intent.getAction() == null) {
                    return;
                }
                String action = intent.getAction();
                Bundle extras = intent.getExtras();
                if (action.equals("MARS_REQUEST_PKG_INFO")) {
                    if (!MARsPolicyManager.MARs_ENABLE) {
                        MARsPolicyManager.getInstance().postInit(true);
                        return;
                    }
                    boolean z3 = false;
                    if (extras != null) {
                        String string = extras.getString("MARS_EXTRA", "");
                        boolean equals = string.equals("create");
                        z2 = string.equals("update");
                        z = string.equals("init");
                        z3 = equals;
                    } else {
                        z = false;
                        z2 = false;
                    }
                    MARsDBManager.getInstance().sendSMDBChangedMsgToDBHandler(z3, z2, z);
                }
            }
        };
    }

    public static MARsTrigger getInstance() {
        return MARsTriggerHolder.INSTANCE;
    }

    public void setContext(Context context) {
        this.mContext = context;
        this.user = new UserHandle(this.mContext.getUserId());
    }

    public void init(Context context) {
        setContext(context);
        this.user = new UserHandle(this.mContext.getUserId());
        this.mLastTimeChangeClockTime = System.currentTimeMillis();
        this.mLastTimeChangeRealtime = SystemClock.elapsedRealtime();
    }

    public void registerReceiver(boolean z) {
        if (this.mReceiverRegistered) {
            return;
        }
        if (this.mAlarm == null) {
            this.mAlarm = (AlarmManager) this.mContext.getSystemService("alarm");
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction(UiModeManager.ACTION_ENTER_CAR_MODE);
        intentFilter.addAction("MARS_REQUEST_POLICY_INFO");
        intentFilter.addAction("MARS_REQUEST_DB_COMPLETE");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter.addAction("com.samsung.android.sm.ACTION_SCPM_MARS_SETTINGS_UPDATED");
        intentFilter.setPriority(999);
        this.mContext.registerReceiver(this.mIntentReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.SCREEN_OFF");
        intentFilter2.addAction(UiModeManager.ACTION_EXIT_CAR_MODE);
        intentFilter2.setPriority(1000);
        this.mContext.registerReceiver(this.mTriggerIntentReceiver, intentFilter2);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("FIRST_ALARM_TRIGGER_ACTION");
        intentFilter3.addAction("com.android.server.am.ACTION_UI_SET_ALWAYS_OPTIMIZING");
        intentFilter3.addAction("com.android.server.am.ACTION_PACKAGE_NOTUSED_RECENTLY");
        this.mContext.registerReceiver(this.mPolicyIntentReceiver, intentFilter3);
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("android.intent.action.USER_ADDED");
        intentFilter4.addAction("android.intent.action.USER_STOPPED");
        intentFilter4.addAction("android.intent.action.USER_STARTED");
        this.mContext.registerReceiverAsUser(this.mUserActionReceiver, UserHandle.ALL, intentFilter4, null, null);
        IntentFilter intentFilter5 = new IntentFilter();
        intentFilter5.addAction("com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY");
        this.mContext.registerReceiver(this.mPolicyIntentReceiver, intentFilter5, "android.permission.WRITE_SECURE_SETTINGS", null);
        IntentFilter intentFilter6 = new IntentFilter();
        intentFilter6.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter6.addAction("android.intent.action.REBOOT");
        this.mContext.registerReceiver(this.mAppStartUpIntentReceiver, intentFilter6);
        if (z) {
            IntentFilter intentFilter7 = new IntentFilter();
            intentFilter7.addAction("android.intent.action.USER_SWITCHED");
            this.mContext.registerReceiver(this.mUserIntentReceiver, intentFilter7);
            IntentFilter intentFilter8 = new IntentFilter();
            intentFilter8.addAction("android.intent.action.TIME_SET");
            this.mContext.registerReceiver(this.mTimeIntentReceiver, intentFilter8);
        }
        this.mReceiverRegistered = true;
    }

    public void registerUDSReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.server.am.MARS_TRIGGER_UDS_POLICY");
        intentFilter.addAction("com.android.server.am.MARS_CANCEL_UDS_POLICY");
        this.mContext.registerReceiver(this.mPolicyUDSIntentReceiver, intentFilter);
    }

    public void registerSBikeReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.server.am.MARS_TRIGGER_SBIKE_MODE_POLICY");
        intentFilter.addAction("com.android.server.am.MARS_CANCEL_SBIKE_MODE_POLICY");
        this.mContext.registerReceiver(this.mPolicySBikeIntentReceiver, intentFilter);
    }

    public void registerTCPUReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sec.android.sdhms.action.HIGH_CPU_USAGE");
        intentFilter.addAction("com.sec.android.sdhms.action.HIGH_CPU_USAGE_APP");
        this.mContext.registerReceiver(this.mTCPUReceiver, intentFilter);
    }

    public void registerGameReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.server.am.MARS_TRIGGER_GAME_MODE_POLICY");
        intentFilter.addAction("com.android.server.am.MARS_CANCEL_GAME_MODE_POLICY");
        this.mContext.registerReceiver(this.mPolicyGameIntentReceiver, intentFilter);
    }

    public void registerMPSMReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.server.am.MARS_TRIGGER_MPSM_POLICY");
        intentFilter.addAction("com.android.server.am.MARS_CANCEL_MPSM_POLICY");
        this.mContext.registerReceiver(this.mPolicyMPSMIntentReceiver, intentFilter);
    }

    public void registerEmStateReceiver() {
        if (this.mEmStateReceiverRegistered) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
        this.mContext.registerReceiver(this.mEmergencyStateChangedReceiver, intentFilter);
        this.mEmStateReceiverRegistered = true;
    }

    public void unregisterEmStateReceiver() {
        if (this.mEmStateReceiverRegistered) {
            try {
                this.mContext.unregisterReceiver(this.mEmergencyStateChangedReceiver);
                this.mEmStateReceiverRegistered = false;
            } catch (IllegalArgumentException unused) {
                Slog.e("MARsTrigger", "IllegalArgumentException occurred in unregisterEmStateReceiver()");
            }
        }
    }

    public void registerSMDBChangedReceiver() {
        if (this.mSMDBChangedReceiverRegistered) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("MARS_REQUEST_PKG_INFO");
        this.mContext.registerReceiver(this.mSMDBChangedReceiver, intentFilter);
        this.mSMDBChangedReceiverRegistered = true;
    }

    public final String actionToString(String str) {
        if (str.equals("com.android.server.am.ACTION_UI_SET_ALWAYS_OPTIMIZING")) {
            return "User Set Always Optimizing";
        }
        if (str.equals("com.android.server.am.ACTION_PACKAGE_NOTUSED_RECENTLY")) {
            return "Package Not Used Recently";
        }
        if (str.equals("com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")) {
            return "User Trigger Policy";
        }
        if (str.equals("com.android.server.am.MARS_TRIGGER_UDS_POLICY")) {
            return "Trigger UDS(Ultra Data Saving) Policy";
        }
        if (str.equals("com.android.server.am.MARS_TRIGGER_SBIKE_MODE_POLICY")) {
            return "Trigger S Bike Policy";
        }
        if (str.equals("com.android.server.am.MARS_TRIGGER_GAME_MODE_POLICY")) {
            return "Trigger GAME MODE Policy";
        }
        if (str.equals("com.android.server.am.MARS_TRIGGER_MPSM_POLICY")) {
            return "Trigger MPSM Policy";
        }
        return null;
    }

    public final int policyNameToInt(String str) {
        if ("applocker".equalsIgnoreCase(str)) {
            return 1;
        }
        if ("autorun".equalsIgnoreCase(str)) {
            return 2;
        }
        if ("freecess".equalsIgnoreCase(str)) {
            return 4;
        }
        if ("udspolicy".equalsIgnoreCase(str)) {
            return 5;
        }
        if ("sbikepolicy".equalsIgnoreCase(str)) {
            return 6;
        }
        if ("gamepolicy".equalsIgnoreCase(str)) {
            return 9;
        }
        return "mpsmpolicy".equalsIgnoreCase(str) ? 10 : 0;
    }

    public final void setAlarm(String str, long j) {
        if (str.equals("FIRST_ALARM_TRIGGER_ACTION")) {
            if (this.mMARsFirstTriggerPolicyAlarmIntent == null) {
                this.mMARsFirstTriggerPolicyAlarmIntent = PendingIntent.getBroadcastAsUser(this.mContext, 0, new Intent(str).setPackage("android").setFlags(1073741824), 67108864, this.user);
            }
            this.mAlarm.setExact(1, System.currentTimeMillis() + j, this.mMARsFirstTriggerPolicyAlarmIntent);
        }
    }
}
