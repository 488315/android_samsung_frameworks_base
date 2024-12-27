package com.android.server.am;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.UiModeManager;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.mars.EventRecorder;
import com.android.server.am.mars.MARsBigData;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.MARsHistoryLog;
import com.android.server.am.mars.database.MARsDBHandler;
import com.android.server.am.mars.database.MARsDBManager;
import com.android.server.am.mars.filter.FilterFactory;
import com.android.server.am.mars.filter.FilterManager;
import com.android.server.am.mars.filter.IFilter;
import com.android.server.am.mars.filter.filter.ActiveTrafficFilter;
import com.android.server.am.mars.filter.filter.AllowListFilter;
import com.android.server.am.mars.filter.filter.LatestProtectedPackageFilter;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MARsTrigger {
    public AlarmManager mAlarm;
    public AnonymousClass5 mAppStartUpIntentReceiver;
    public Context mContext;
    public boolean mEmStateReceiverRegistered;
    public AnonymousClass1 mEmergencyStateChangedReceiver;
    public AnonymousClass1 mIntentReceiver;
    public long mLastTimeChangeClockTime;
    public long mLastTimeChangeRealtime;
    public PendingIntent mMARsFirstTriggerPolicyAlarmIntent;
    public AnonymousClass1 mPolicyGameIntentReceiver;
    public AnonymousClass1 mPolicyIntentReceiver;
    public AnonymousClass1 mPolicySBikeIntentReceiver;
    public boolean mReceiverRegistered;
    public AnonymousClass5 mSMDBChangedReceiver;
    public boolean mSMDBChangedReceiverRegistered;
    public AnonymousClass5 mTCPUReceiver;
    public AnonymousClass1 mTimeIntentReceiver;
    public AnonymousClass1 mTriggerIntentReceiver;
    public AnonymousClass5 mUserActionReceiver;
    public AnonymousClass1 mUserIntentReceiver;
    public UserHandle user;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class MARsTriggerHolder {
        public static final MARsTrigger INSTANCE;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.am.MARsTrigger$1] */
        /* JADX WARN: Type inference failed for: r1v10, types: [com.android.server.am.MARsTrigger$5] */
        /* JADX WARN: Type inference failed for: r1v11, types: [com.android.server.am.MARsTrigger$1] */
        /* JADX WARN: Type inference failed for: r1v12, types: [com.android.server.am.MARsTrigger$5] */
        /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.am.MARsTrigger$1] */
        /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.am.MARsTrigger$1] */
        /* JADX WARN: Type inference failed for: r1v4, types: [com.android.server.am.MARsTrigger$1] */
        /* JADX WARN: Type inference failed for: r1v5, types: [com.android.server.am.MARsTrigger$5] */
        /* JADX WARN: Type inference failed for: r1v6, types: [com.android.server.am.MARsTrigger$1] */
        /* JADX WARN: Type inference failed for: r1v7, types: [com.android.server.am.MARsTrigger$1] */
        /* JADX WARN: Type inference failed for: r1v8, types: [com.android.server.am.MARsTrigger$5] */
        /* JADX WARN: Type inference failed for: r1v9, types: [com.android.server.am.MARsTrigger$1] */
        static {
            final MARsTrigger mARsTrigger = new MARsTrigger();
            mARsTrigger.mReceiverRegistered = false;
            mARsTrigger.mEmStateReceiverRegistered = false;
            mARsTrigger.mSMDBChangedReceiverRegistered = false;
            final int i = 0;
            mARsTrigger.mIntentReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            boolean z;
                            Bundle extras;
                            ArrayList<String> stringArrayList;
                            Context context2;
                            int i2 = 2;
                            switch (i) {
                                case 0:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    if (action.equals("android.intent.action.SCREEN_ON")) {
                                        boolean z2 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setScreenOnState(true);
                                        MARsTrigger mARsTrigger2 = mARsTrigger;
                                        PendingIntent pendingIntent =
                                                mARsTrigger2.mMARsFirstTriggerPolicyAlarmIntent;
                                        if (pendingIntent != null) {
                                            mARsTrigger2.mAlarm.cancel(pendingIntent);
                                        }
                                        MARsHandler mARsHandler =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        MARsHandler.MainHandler mainHandler =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler != null) {
                                            mainHandler.removeMessages(1);
                                        }
                                        MARsHandler.MainHandler mainHandler2 =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler2 == null) {
                                            return;
                                        }
                                        mainHandler2.removeMessages(2);
                                        return;
                                    }
                                    if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                                        ActiveTrafficFilter activeTrafficFilter =
                                                ActiveTrafficFilter.ActiveTrafficFilterHolder
                                                        .INSTANCE;
                                        NetworkInfo activeNetworkInfo =
                                                ((ConnectivityManager)
                                                                activeTrafficFilter.mContext
                                                                        .getSystemService(
                                                                                "connectivity"))
                                                        .getActiveNetworkInfo();
                                        activeTrafficFilter.mIsDataConnectionConnected =
                                                activeNetworkInfo != null
                                                        && activeNetworkInfo.isConnected();
                                        if (MARsDebugConfig.DEBUG_FILTER) {
                                            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                                    "MARs:ActiveTrafficFilter",
                                                    new StringBuilder("DataConnection: "),
                                                    activeTrafficFilter.mIsDataConnectionConnected);
                                            return;
                                        }
                                        return;
                                    }
                                    if (action.equals(UiModeManager.ACTION_ENTER_CAR_MODE)) {
                                        boolean z3 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager mARsPolicyManager =
                                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                        synchronized (mARsPolicyManager) {
                                            mARsPolicyManager.mCarModeOn = true;
                                        }
                                        return;
                                    }
                                    if (action.equals(Constants.SIM_STATE_CHANGED)) {
                                        AllowListFilter.AllowListFilterHolder.INSTANCE
                                                .setCarrierAllowList();
                                        return;
                                    }
                                    if (!action.equals("MARS_REQUEST_DB_COMPLETE")) {
                                        if (action.equals(
                                                "com.samsung.android.sm.ACTION_SCPM_MARS_SETTINGS_UPDATED")) {
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE
                                                    .sendGetSCPMPolicyMsgToDBHandler();
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager2 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager2.getClass();
                                    synchronized (MARsPolicyManager.MARsLock) {
                                        if (mARsPolicyManager2.mMARsTargetPackages.mMap.size()
                                                <= 0) {
                                            r3 = false;
                                        }
                                    }
                                    if (r3) {
                                        return;
                                    }
                                    MARsDBHandler.getInstance();
                                    MARsDBHandler mARsDBHandler =
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                    if (mARsDBHandler.mMainHandler == null) {
                                        return;
                                    }
                                    Slog.d("MARsDBHandler", "sendSdhmsDBCompleteMsgToDBHandler");
                                    mARsDBHandler.mMainHandler.post(
                                            mARsDBHandler.mFASDBupdateRunnable);
                                    return;
                                case 1:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals(
                                                            "com.samsung.intent.action.EMERGENCY_STATE_CHANGED")
                                            || intent.getIntExtra("reason", 0) != 5) {
                                        return;
                                    }
                                    Slog.d("MARsTrigger", "disable ultra power saving mode");
                                    if (MARsPolicyManager.MARs_ENABLE) {
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setSubUserIds();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger3 = mARsTrigger;
                                    if (mARsTrigger3.mEmStateReceiverRegistered) {
                                        try {
                                            mARsTrigger3.mContext.unregisterReceiver(
                                                    mARsTrigger3.mEmergencyStateChangedReceiver);
                                            mARsTrigger3.mEmStateReceiverRegistered = false;
                                        } catch (IllegalArgumentException unused) {
                                            Slog.e(
                                                    "MARsTrigger",
                                                    "IllegalArgumentException occurred in"
                                                        + " unregisterEmStateReceiver()");
                                        }
                                    }
                                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.postInit();
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action2 = intent.getAction();
                                    if (!action2.equals("android.intent.action.SCREEN_OFF")) {
                                        if (action2.equals(UiModeManager.ACTION_EXIT_CAR_MODE)) {
                                            boolean z5 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager3 =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager3) {
                                                mARsPolicyManager3.mCarModeOn = false;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z6 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager4 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager4.setScreenOnState(false);
                                    boolean z7 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    if (FreecessController.FreecessControllerHolder.INSTANCE
                                            .mIsDumpstateWorking) {
                                        return;
                                    }
                                    synchronized (mARsPolicyManager4) {
                                        z = mARsPolicyManager4.mCarModeOn;
                                    }
                                    if (z) {
                                        return;
                                    }
                                    if (MARsPolicyManager.isChinaModel) {
                                        MARsHandler.MARsHandlerHolder.INSTANCE
                                                .sendFirstTriggerMsgToMainHandler();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger4 = mARsTrigger;
                                    mARsTrigger4.getClass();
                                    if (mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent == null) {
                                        mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent =
                                                PendingIntent.getBroadcastAsUser(
                                                        mARsTrigger4.mContext,
                                                        0,
                                                        new Intent("FIRST_ALARM_TRIGGER_ACTION")
                                                                .setPackage("android")
                                                                .setFlags(1073741824),
                                                        67108864,
                                                        mARsTrigger4.user);
                                    }
                                    mARsTrigger4.mAlarm.setExact(
                                            1,
                                            System.currentTimeMillis() + 5000,
                                            mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent);
                                    return;
                                case 3:
                                    String action3 = intent.getAction();
                                    if (action3 == null) {
                                        return;
                                    }
                                    boolean equals = action3.equals("FIRST_ALARM_TRIGGER_ACTION");
                                    MARsHandler mARsHandler2 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals) {
                                        Slog.d(
                                                "MARsTrigger",
                                                "mPolicyIntentReceiver broadcast received action : "
                                                        .concat(action3));
                                        boolean z8 = MARsPolicyManager.MARs_ENABLE;
                                        if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .getScreenOnState()) {
                                            boolean z9 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            if (!FreecessController.FreecessControllerHolder
                                                    .INSTANCE
                                                    .mIsDumpstateWorking) {
                                                mARsHandler2.sendFirstTriggerMsgToMainHandler();
                                                mARsHandler2.sendRepeatTriggerMsgToMainHandler();
                                            }
                                        }
                                    }
                                    if (!action3.equals(
                                                    "com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")
                                            || (extras = intent.getExtras()) == null) {
                                        return;
                                    }
                                    String string = extras.getString("POLICY_NAME", "");
                                    mARsTrigger.getClass();
                                    if ("applocker".equalsIgnoreCase(string)) {
                                        i2 = 1;
                                    } else if (!"autorun".equalsIgnoreCase(string)) {
                                        i2 =
                                                "freecess".equalsIgnoreCase(string)
                                                        ? 4
                                                        : "sbikepolicy".equalsIgnoreCase(string)
                                                                ? 6
                                                                : "gamepolicy"
                                                                                .equalsIgnoreCase(
                                                                                        string)
                                                                        ? 9
                                                                        : 0;
                                    }
                                    ArrayList<String> stringArrayList2 =
                                            extras.getStringArrayList("PACKAGE_NAME");
                                    if (i2 == 0 || stringArrayList2 == null) {
                                        return;
                                    }
                                    mARsHandler2.sendRunPolicySpecificPkgMsgToMainHandler(
                                            i2,
                                            mARsTrigger.mContext.getUserId(),
                                            null,
                                            stringArrayList2);
                                    return;
                                case 4:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action4 = intent.getAction();
                                    boolean equals2 =
                                            action4.equals(
                                                    "com.android.server.am.MARS_TRIGGER_SBIKE_MODE_POLICY");
                                    MARsHandler mARsHandler3 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals2) {
                                        Bundle extras2 = intent.getExtras();
                                        if (extras2 == null
                                                || (stringArrayList =
                                                                extras2.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        mARsHandler3.sendRunPolicySpecificPkgMsgToMainHandler(
                                                6,
                                                mARsTrigger.mContext.getUserId(),
                                                MARsTrigger.m199$$Nest$mactionToString(
                                                        mARsTrigger, action4),
                                                stringArrayList);
                                        return;
                                    }
                                    if (action4.equals(
                                            "com.android.server.am.MARS_CANCEL_SBIKE_MODE_POLICY")) {
                                        Bundle extras3 = intent.getExtras();
                                        if (extras3 == null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6, mARsTrigger.mContext.getUserId(), null);
                                            return;
                                        }
                                        ArrayList<String> stringArrayList3 =
                                                extras3.getStringArrayList("PACKAGE_NAME");
                                        if (stringArrayList3 != null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6,
                                                    mARsTrigger.mContext.getUserId(),
                                                    stringArrayList3);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 5:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String stringExtra = intent.getStringExtra("package");
                                    if (stringExtra == null
                                            || "com.samsung.android.game.gos".equals(stringExtra)) {
                                        String action5 = intent.getAction();
                                        boolean equals3 =
                                                action5.equals(
                                                        "com.android.server.am.MARS_TRIGGER_GAME_MODE_POLICY");
                                        MARsHandler mARsHandler4 =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        if (!equals3) {
                                            if (action5.equals(
                                                    "com.android.server.am.MARS_CANCEL_GAME_MODE_POLICY")) {
                                                boolean z10 =
                                                        FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                                FreecessController freecessController =
                                                        FreecessController.FreecessControllerHolder
                                                                .INSTANCE;
                                                if (freecessController.mCalmModeEnabled) {
                                                    FreecessHandler freecessHandler =
                                                            FreecessHandler.FreecessHandlerHolder
                                                                    .INSTANCE;
                                                    FreecessHandler.MainHandler mainHandler3 =
                                                            freecessHandler.mMainHandler;
                                                    if (mainHandler3 != null) {
                                                        Message obtainMessage =
                                                                mainHandler3.obtainMessage(23);
                                                        freecessHandler.mMainHandler.removeMessages(
                                                                23);
                                                        freecessHandler.mMainHandler.sendMessage(
                                                                obtainMessage);
                                                    }
                                                } else {
                                                    boolean z11 = MARsPolicyManager.MARs_ENABLE;
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE
                                                            .getClass();
                                                    if (!MARsPolicyManager.isChinaPolicyEnabled()) {
                                                        mARsHandler4
                                                                .sendCancelPolicyMsgToMainHandler(
                                                                        9, -1, null);
                                                        if (freecessController.mGameModeEnabled) {
                                                            freecessController.mGameModeEnabled =
                                                                    false;
                                                        }
                                                    }
                                                }
                                                Slog.d(
                                                        "MARsTrigger",
                                                        "broadcast received action :"
                                                            + " MARS_CANCEL_GAME_MODE_POLICY");
                                                return;
                                            }
                                            return;
                                        }
                                        Bundle extras4 = intent.getExtras();
                                        if (extras4 != null) {
                                            ArrayList<String> stringArrayList4 =
                                                    extras4.getStringArrayList("survive_app");
                                            LatestProtectedPackageFilter
                                                            .LatestProtectedPackageFilterHolder
                                                            .INSTANCE
                                                            .mProtectedAppSizeForGame =
                                                    extras4.getInt("lru_num");
                                            boolean z12 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            FreecessController freecessController2 =
                                                    FreecessController.FreecessControllerHolder
                                                            .INSTANCE;
                                            if (!freecessController2.mIsFreecessEnable
                                                    || !FreecessController.IS_SUPPORT_CALM_MODE) {
                                                mARsHandler4
                                                        .sendRunPolicySpecificPkgMsgToMainHandler(
                                                                9,
                                                                mARsTrigger.mContext.getUserId(),
                                                                MARsTrigger
                                                                        .m199$$Nest$mactionToString(
                                                                                mARsTrigger,
                                                                                action5),
                                                                stringArrayList4);
                                                if (freecessController2.mGameModeEnabled) {
                                                    return;
                                                }
                                                freecessController2.mGameModeEnabled = true;
                                                return;
                                            }
                                            FreecessHandler freecessHandler2 =
                                                    FreecessHandler.FreecessHandlerHolder.INSTANCE;
                                            FreecessHandler.MainHandler mainHandler4 =
                                                    freecessHandler2.mMainHandler;
                                            if (mainHandler4 == null) {
                                                return;
                                            }
                                            mainHandler4.removeMessages(21);
                                            Bundle bundle = new Bundle();
                                            bundle.putStringArrayList("list", stringArrayList4);
                                            Message obtainMessage2 =
                                                    freecessHandler2.mMainHandler.obtainMessage(21);
                                            obtainMessage2.setData(bundle);
                                            freecessHandler2.mMainHandler.sendMessage(
                                                    obtainMessage2);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 6:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals("android.intent.action.TIME_SET")) {
                                        return;
                                    }
                                    long currentTimeMillis = System.currentTimeMillis();
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    MARsTrigger mARsTrigger5 = mARsTrigger;
                                    long j =
                                            currentTimeMillis
                                                    - ((elapsedRealtime
                                                                    - mARsTrigger5
                                                                            .mLastTimeChangeRealtime)
                                                            + mARsTrigger5
                                                                    .mLastTimeChangeClockTime);
                                    MARsHandler mARsHandler5 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putLong("changedTime", j);
                                    Message obtainMessage3 =
                                            mARsHandler5.mMainHandler.obtainMessage(6);
                                    obtainMessage3.setData(bundle2);
                                    mARsHandler5.mMainHandler.sendMessage(obtainMessage3);
                                    MARsTrigger mARsTrigger6 = mARsTrigger;
                                    mARsTrigger6.mLastTimeChangeClockTime = currentTimeMillis;
                                    mARsTrigger6.mLastTimeChangeRealtime = elapsedRealtime;
                                    boolean z13 = EventRecorder.FEATURE_ENABLE;
                                    EventRecorder eventRecorder =
                                            EventRecorder.EventRecorderHolder.INSTANCE;
                                    eventRecorder.getClass();
                                    if (Math.abs(j) < 30000) {
                                        Slog.d(
                                                "EventRecorder",
                                                "changed time is " + j + ". ignore.");
                                        return;
                                    }
                                    Slog.d("EventRecorder", "disable EventRecorder");
                                    eventRecorder.performWrite(System.currentTimeMillis());
                                    EventRecorder.FEATURE_ENABLE = false;
                                    JobScheduler jobScheduler =
                                            (JobScheduler)
                                                    eventRecorder.mContext.getSystemService(
                                                            "jobscheduler");
                                    if (jobScheduler != null) {
                                        jobScheduler
                                                .forNamespace("MARsEventRecorderNamespace")
                                                .cancel(1);
                                    }
                                    ReentrantLock reentrantLock = EventRecorder.mFileLock;
                                    reentrantLock.lock();
                                    try {
                                        Slog.d(
                                                "EventRecorder",
                                                "file delete result : "
                                                        + EventRecorder.file.delete());
                                        reentrantLock.unlock();
                                        return;
                                    } catch (Throwable th) {
                                        EventRecorder.mFileLock.unlock();
                                        throw th;
                                    }
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action6 = intent.getAction();
                                    Slog.d("MARsTrigger", "broadcast received action : " + action6);
                                    if (!"android.intent.action.USER_SWITCHED".equals(action6)) {
                                        return;
                                    }
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", 0);
                                    Slog.d(
                                            "MARsTrigger",
                                            "mContext.id = " + mARsTrigger.mContext.getUserId());
                                    if (MARsPolicyManager.App_StartUp_History) {
                                        MARsDBManager.MARsDBManagerHolder.INSTANCE
                                                .doUpdateCompHistory(false);
                                    }
                                    MARsPolicyManager mARsPolicyManager5 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager5.addDebugInfoToHistory("DEV", "switchUser");
                                    boolean z14 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController3 =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    boolean z15 = freecessController3.mIsScreenOnFreecessEnabled;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(false);
                                        if (MARsDebugConfig.DEBUG_ENG) {
                                            Slog.d("FreecessController", "removeBgTriggerMsg....");
                                        }
                                        FreecessHandler.FreecessHandlerHolder.INSTANCE
                                                .removeBgTriggerMsg();
                                    }
                                    if (freecessController3.mIsFreecessEnable) {
                                        freecessController3.unFreezePackage("MUM");
                                    }
                                    mARsPolicyManager5.mFirstTimeUpdatePackages = true;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(true);
                                    }
                                    FilterManager filterManager =
                                            FilterManager.FilterManagerHolder.INSTANCE;
                                    int i3 = 1;
                                    while (true) {
                                        FilterFactory filterFactory = filterManager.mFF;
                                        if (i3 >= 34) {
                                            filterFactory.filterHashMap.clear();
                                            filterManager.mFCF.filterHashMap.clear();
                                            mARsPolicyManager5.mCurrentUserId = intExtra;
                                            mARsPolicyManager5.mLastNotiSentTimeForDisabled = 0L;
                                            UserHandle userHandle = new UserHandle(intExtra);
                                            try {
                                                Context context3 = mARsPolicyManager5.mContext;
                                                context2 =
                                                        context3.createPackageContextAsUser(
                                                                context3.getPackageName(),
                                                                0,
                                                                userHandle);
                                            } catch (PackageManager.NameNotFoundException unused2) {
                                                context2 = mARsPolicyManager5.mContext;
                                            }
                                            MARsDBManager mARsDBManager =
                                                    MARsDBManager.MARsDBManagerHolder.INSTANCE;
                                            Context context4 = mARsDBManager.mCurrentContext;
                                            try {
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerSettingsObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerSettingsObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerSettingsObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerFreezeExcludeListObserver) {
                                                    mARsDBManager
                                                            .mContext
                                                            .getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerFreezeExcludeListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerFreezeExcludeListObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerDefaultAllowedListObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerDefaultAllowedListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerDefaultAllowedListObserver =
                                                            false;
                                                }
                                            } catch (IllegalArgumentException unused3) {
                                                Slog.e(
                                                        "MARsDBManager",
                                                        "IllegalArgumentException occurred in"
                                                            + " unregisterContentObserver()");
                                            }
                                            mARsDBManager.mCurrentContext = context2;
                                            mARsDBManager.registerContentObservers(context2);
                                            FilterManager.FilterManagerHolder.INSTANCE.init(
                                                    context2);
                                            MARsHandler.MARsHandlerHolder.INSTANCE
                                                    .sendInitDisabledMsgToMainHandler(intExtra);
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler mARsDBHandler2 =
                                                    MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                            if (mARsDBHandler2.mMainHandler == null) {
                                                return;
                                            }
                                            Bundle m =
                                                    SystemUpdateManagerService$$ExternalSyntheticOutline0
                                                            .m(intExtra, "userId");
                                            Message obtainMessage4 =
                                                    mARsDBHandler2.mMainHandler.obtainMessage(7);
                                            obtainMessage4.setData(m);
                                            mARsDBHandler2.mMainHandler.sendMessage(obtainMessage4);
                                            return;
                                        }
                                        ((IFilter)
                                                        filterFactory.filterHashMap.get(
                                                                Integer.valueOf(i3)))
                                                .deInit();
                                        i3++;
                                    }
                                    break;
                            }
                        }
                    };
            final int i2 = 2;
            mARsTrigger.mTriggerIntentReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            boolean z;
                            Bundle extras;
                            ArrayList<String> stringArrayList;
                            Context context2;
                            int i22 = 2;
                            switch (i2) {
                                case 0:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    if (action.equals("android.intent.action.SCREEN_ON")) {
                                        boolean z2 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setScreenOnState(true);
                                        MARsTrigger mARsTrigger2 = mARsTrigger;
                                        PendingIntent pendingIntent =
                                                mARsTrigger2.mMARsFirstTriggerPolicyAlarmIntent;
                                        if (pendingIntent != null) {
                                            mARsTrigger2.mAlarm.cancel(pendingIntent);
                                        }
                                        MARsHandler mARsHandler =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        MARsHandler.MainHandler mainHandler =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler != null) {
                                            mainHandler.removeMessages(1);
                                        }
                                        MARsHandler.MainHandler mainHandler2 =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler2 == null) {
                                            return;
                                        }
                                        mainHandler2.removeMessages(2);
                                        return;
                                    }
                                    if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                                        ActiveTrafficFilter activeTrafficFilter =
                                                ActiveTrafficFilter.ActiveTrafficFilterHolder
                                                        .INSTANCE;
                                        NetworkInfo activeNetworkInfo =
                                                ((ConnectivityManager)
                                                                activeTrafficFilter.mContext
                                                                        .getSystemService(
                                                                                "connectivity"))
                                                        .getActiveNetworkInfo();
                                        activeTrafficFilter.mIsDataConnectionConnected =
                                                activeNetworkInfo != null
                                                        && activeNetworkInfo.isConnected();
                                        if (MARsDebugConfig.DEBUG_FILTER) {
                                            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                                    "MARs:ActiveTrafficFilter",
                                                    new StringBuilder("DataConnection: "),
                                                    activeTrafficFilter.mIsDataConnectionConnected);
                                            return;
                                        }
                                        return;
                                    }
                                    if (action.equals(UiModeManager.ACTION_ENTER_CAR_MODE)) {
                                        boolean z3 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager mARsPolicyManager =
                                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                        synchronized (mARsPolicyManager) {
                                            mARsPolicyManager.mCarModeOn = true;
                                        }
                                        return;
                                    }
                                    if (action.equals(Constants.SIM_STATE_CHANGED)) {
                                        AllowListFilter.AllowListFilterHolder.INSTANCE
                                                .setCarrierAllowList();
                                        return;
                                    }
                                    if (!action.equals("MARS_REQUEST_DB_COMPLETE")) {
                                        if (action.equals(
                                                "com.samsung.android.sm.ACTION_SCPM_MARS_SETTINGS_UPDATED")) {
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE
                                                    .sendGetSCPMPolicyMsgToDBHandler();
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager2 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager2.getClass();
                                    synchronized (MARsPolicyManager.MARsLock) {
                                        if (mARsPolicyManager2.mMARsTargetPackages.mMap.size()
                                                <= 0) {
                                            r3 = false;
                                        }
                                    }
                                    if (r3) {
                                        return;
                                    }
                                    MARsDBHandler.getInstance();
                                    MARsDBHandler mARsDBHandler =
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                    if (mARsDBHandler.mMainHandler == null) {
                                        return;
                                    }
                                    Slog.d("MARsDBHandler", "sendSdhmsDBCompleteMsgToDBHandler");
                                    mARsDBHandler.mMainHandler.post(
                                            mARsDBHandler.mFASDBupdateRunnable);
                                    return;
                                case 1:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals(
                                                            "com.samsung.intent.action.EMERGENCY_STATE_CHANGED")
                                            || intent.getIntExtra("reason", 0) != 5) {
                                        return;
                                    }
                                    Slog.d("MARsTrigger", "disable ultra power saving mode");
                                    if (MARsPolicyManager.MARs_ENABLE) {
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setSubUserIds();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger3 = mARsTrigger;
                                    if (mARsTrigger3.mEmStateReceiverRegistered) {
                                        try {
                                            mARsTrigger3.mContext.unregisterReceiver(
                                                    mARsTrigger3.mEmergencyStateChangedReceiver);
                                            mARsTrigger3.mEmStateReceiverRegistered = false;
                                        } catch (IllegalArgumentException unused) {
                                            Slog.e(
                                                    "MARsTrigger",
                                                    "IllegalArgumentException occurred in"
                                                        + " unregisterEmStateReceiver()");
                                        }
                                    }
                                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.postInit();
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action2 = intent.getAction();
                                    if (!action2.equals("android.intent.action.SCREEN_OFF")) {
                                        if (action2.equals(UiModeManager.ACTION_EXIT_CAR_MODE)) {
                                            boolean z5 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager3 =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager3) {
                                                mARsPolicyManager3.mCarModeOn = false;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z6 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager4 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager4.setScreenOnState(false);
                                    boolean z7 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    if (FreecessController.FreecessControllerHolder.INSTANCE
                                            .mIsDumpstateWorking) {
                                        return;
                                    }
                                    synchronized (mARsPolicyManager4) {
                                        z = mARsPolicyManager4.mCarModeOn;
                                    }
                                    if (z) {
                                        return;
                                    }
                                    if (MARsPolicyManager.isChinaModel) {
                                        MARsHandler.MARsHandlerHolder.INSTANCE
                                                .sendFirstTriggerMsgToMainHandler();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger4 = mARsTrigger;
                                    mARsTrigger4.getClass();
                                    if (mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent == null) {
                                        mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent =
                                                PendingIntent.getBroadcastAsUser(
                                                        mARsTrigger4.mContext,
                                                        0,
                                                        new Intent("FIRST_ALARM_TRIGGER_ACTION")
                                                                .setPackage("android")
                                                                .setFlags(1073741824),
                                                        67108864,
                                                        mARsTrigger4.user);
                                    }
                                    mARsTrigger4.mAlarm.setExact(
                                            1,
                                            System.currentTimeMillis() + 5000,
                                            mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent);
                                    return;
                                case 3:
                                    String action3 = intent.getAction();
                                    if (action3 == null) {
                                        return;
                                    }
                                    boolean equals = action3.equals("FIRST_ALARM_TRIGGER_ACTION");
                                    MARsHandler mARsHandler2 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals) {
                                        Slog.d(
                                                "MARsTrigger",
                                                "mPolicyIntentReceiver broadcast received action : "
                                                        .concat(action3));
                                        boolean z8 = MARsPolicyManager.MARs_ENABLE;
                                        if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .getScreenOnState()) {
                                            boolean z9 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            if (!FreecessController.FreecessControllerHolder
                                                    .INSTANCE
                                                    .mIsDumpstateWorking) {
                                                mARsHandler2.sendFirstTriggerMsgToMainHandler();
                                                mARsHandler2.sendRepeatTriggerMsgToMainHandler();
                                            }
                                        }
                                    }
                                    if (!action3.equals(
                                                    "com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")
                                            || (extras = intent.getExtras()) == null) {
                                        return;
                                    }
                                    String string = extras.getString("POLICY_NAME", "");
                                    mARsTrigger.getClass();
                                    if ("applocker".equalsIgnoreCase(string)) {
                                        i22 = 1;
                                    } else if (!"autorun".equalsIgnoreCase(string)) {
                                        i22 =
                                                "freecess".equalsIgnoreCase(string)
                                                        ? 4
                                                        : "sbikepolicy".equalsIgnoreCase(string)
                                                                ? 6
                                                                : "gamepolicy"
                                                                                .equalsIgnoreCase(
                                                                                        string)
                                                                        ? 9
                                                                        : 0;
                                    }
                                    ArrayList<String> stringArrayList2 =
                                            extras.getStringArrayList("PACKAGE_NAME");
                                    if (i22 == 0 || stringArrayList2 == null) {
                                        return;
                                    }
                                    mARsHandler2.sendRunPolicySpecificPkgMsgToMainHandler(
                                            i22,
                                            mARsTrigger.mContext.getUserId(),
                                            null,
                                            stringArrayList2);
                                    return;
                                case 4:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action4 = intent.getAction();
                                    boolean equals2 =
                                            action4.equals(
                                                    "com.android.server.am.MARS_TRIGGER_SBIKE_MODE_POLICY");
                                    MARsHandler mARsHandler3 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals2) {
                                        Bundle extras2 = intent.getExtras();
                                        if (extras2 == null
                                                || (stringArrayList =
                                                                extras2.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        mARsHandler3.sendRunPolicySpecificPkgMsgToMainHandler(
                                                6,
                                                mARsTrigger.mContext.getUserId(),
                                                MARsTrigger.m199$$Nest$mactionToString(
                                                        mARsTrigger, action4),
                                                stringArrayList);
                                        return;
                                    }
                                    if (action4.equals(
                                            "com.android.server.am.MARS_CANCEL_SBIKE_MODE_POLICY")) {
                                        Bundle extras3 = intent.getExtras();
                                        if (extras3 == null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6, mARsTrigger.mContext.getUserId(), null);
                                            return;
                                        }
                                        ArrayList<String> stringArrayList3 =
                                                extras3.getStringArrayList("PACKAGE_NAME");
                                        if (stringArrayList3 != null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6,
                                                    mARsTrigger.mContext.getUserId(),
                                                    stringArrayList3);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 5:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String stringExtra = intent.getStringExtra("package");
                                    if (stringExtra == null
                                            || "com.samsung.android.game.gos".equals(stringExtra)) {
                                        String action5 = intent.getAction();
                                        boolean equals3 =
                                                action5.equals(
                                                        "com.android.server.am.MARS_TRIGGER_GAME_MODE_POLICY");
                                        MARsHandler mARsHandler4 =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        if (!equals3) {
                                            if (action5.equals(
                                                    "com.android.server.am.MARS_CANCEL_GAME_MODE_POLICY")) {
                                                boolean z10 =
                                                        FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                                FreecessController freecessController =
                                                        FreecessController.FreecessControllerHolder
                                                                .INSTANCE;
                                                if (freecessController.mCalmModeEnabled) {
                                                    FreecessHandler freecessHandler =
                                                            FreecessHandler.FreecessHandlerHolder
                                                                    .INSTANCE;
                                                    FreecessHandler.MainHandler mainHandler3 =
                                                            freecessHandler.mMainHandler;
                                                    if (mainHandler3 != null) {
                                                        Message obtainMessage =
                                                                mainHandler3.obtainMessage(23);
                                                        freecessHandler.mMainHandler.removeMessages(
                                                                23);
                                                        freecessHandler.mMainHandler.sendMessage(
                                                                obtainMessage);
                                                    }
                                                } else {
                                                    boolean z11 = MARsPolicyManager.MARs_ENABLE;
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE
                                                            .getClass();
                                                    if (!MARsPolicyManager.isChinaPolicyEnabled()) {
                                                        mARsHandler4
                                                                .sendCancelPolicyMsgToMainHandler(
                                                                        9, -1, null);
                                                        if (freecessController.mGameModeEnabled) {
                                                            freecessController.mGameModeEnabled =
                                                                    false;
                                                        }
                                                    }
                                                }
                                                Slog.d(
                                                        "MARsTrigger",
                                                        "broadcast received action :"
                                                            + " MARS_CANCEL_GAME_MODE_POLICY");
                                                return;
                                            }
                                            return;
                                        }
                                        Bundle extras4 = intent.getExtras();
                                        if (extras4 != null) {
                                            ArrayList<String> stringArrayList4 =
                                                    extras4.getStringArrayList("survive_app");
                                            LatestProtectedPackageFilter
                                                            .LatestProtectedPackageFilterHolder
                                                            .INSTANCE
                                                            .mProtectedAppSizeForGame =
                                                    extras4.getInt("lru_num");
                                            boolean z12 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            FreecessController freecessController2 =
                                                    FreecessController.FreecessControllerHolder
                                                            .INSTANCE;
                                            if (!freecessController2.mIsFreecessEnable
                                                    || !FreecessController.IS_SUPPORT_CALM_MODE) {
                                                mARsHandler4
                                                        .sendRunPolicySpecificPkgMsgToMainHandler(
                                                                9,
                                                                mARsTrigger.mContext.getUserId(),
                                                                MARsTrigger
                                                                        .m199$$Nest$mactionToString(
                                                                                mARsTrigger,
                                                                                action5),
                                                                stringArrayList4);
                                                if (freecessController2.mGameModeEnabled) {
                                                    return;
                                                }
                                                freecessController2.mGameModeEnabled = true;
                                                return;
                                            }
                                            FreecessHandler freecessHandler2 =
                                                    FreecessHandler.FreecessHandlerHolder.INSTANCE;
                                            FreecessHandler.MainHandler mainHandler4 =
                                                    freecessHandler2.mMainHandler;
                                            if (mainHandler4 == null) {
                                                return;
                                            }
                                            mainHandler4.removeMessages(21);
                                            Bundle bundle = new Bundle();
                                            bundle.putStringArrayList("list", stringArrayList4);
                                            Message obtainMessage2 =
                                                    freecessHandler2.mMainHandler.obtainMessage(21);
                                            obtainMessage2.setData(bundle);
                                            freecessHandler2.mMainHandler.sendMessage(
                                                    obtainMessage2);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 6:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals("android.intent.action.TIME_SET")) {
                                        return;
                                    }
                                    long currentTimeMillis = System.currentTimeMillis();
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    MARsTrigger mARsTrigger5 = mARsTrigger;
                                    long j =
                                            currentTimeMillis
                                                    - ((elapsedRealtime
                                                                    - mARsTrigger5
                                                                            .mLastTimeChangeRealtime)
                                                            + mARsTrigger5
                                                                    .mLastTimeChangeClockTime);
                                    MARsHandler mARsHandler5 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putLong("changedTime", j);
                                    Message obtainMessage3 =
                                            mARsHandler5.mMainHandler.obtainMessage(6);
                                    obtainMessage3.setData(bundle2);
                                    mARsHandler5.mMainHandler.sendMessage(obtainMessage3);
                                    MARsTrigger mARsTrigger6 = mARsTrigger;
                                    mARsTrigger6.mLastTimeChangeClockTime = currentTimeMillis;
                                    mARsTrigger6.mLastTimeChangeRealtime = elapsedRealtime;
                                    boolean z13 = EventRecorder.FEATURE_ENABLE;
                                    EventRecorder eventRecorder =
                                            EventRecorder.EventRecorderHolder.INSTANCE;
                                    eventRecorder.getClass();
                                    if (Math.abs(j) < 30000) {
                                        Slog.d(
                                                "EventRecorder",
                                                "changed time is " + j + ". ignore.");
                                        return;
                                    }
                                    Slog.d("EventRecorder", "disable EventRecorder");
                                    eventRecorder.performWrite(System.currentTimeMillis());
                                    EventRecorder.FEATURE_ENABLE = false;
                                    JobScheduler jobScheduler =
                                            (JobScheduler)
                                                    eventRecorder.mContext.getSystemService(
                                                            "jobscheduler");
                                    if (jobScheduler != null) {
                                        jobScheduler
                                                .forNamespace("MARsEventRecorderNamespace")
                                                .cancel(1);
                                    }
                                    ReentrantLock reentrantLock = EventRecorder.mFileLock;
                                    reentrantLock.lock();
                                    try {
                                        Slog.d(
                                                "EventRecorder",
                                                "file delete result : "
                                                        + EventRecorder.file.delete());
                                        reentrantLock.unlock();
                                        return;
                                    } catch (Throwable th) {
                                        EventRecorder.mFileLock.unlock();
                                        throw th;
                                    }
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action6 = intent.getAction();
                                    Slog.d("MARsTrigger", "broadcast received action : " + action6);
                                    if (!"android.intent.action.USER_SWITCHED".equals(action6)) {
                                        return;
                                    }
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", 0);
                                    Slog.d(
                                            "MARsTrigger",
                                            "mContext.id = " + mARsTrigger.mContext.getUserId());
                                    if (MARsPolicyManager.App_StartUp_History) {
                                        MARsDBManager.MARsDBManagerHolder.INSTANCE
                                                .doUpdateCompHistory(false);
                                    }
                                    MARsPolicyManager mARsPolicyManager5 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager5.addDebugInfoToHistory("DEV", "switchUser");
                                    boolean z14 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController3 =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    boolean z15 = freecessController3.mIsScreenOnFreecessEnabled;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(false);
                                        if (MARsDebugConfig.DEBUG_ENG) {
                                            Slog.d("FreecessController", "removeBgTriggerMsg....");
                                        }
                                        FreecessHandler.FreecessHandlerHolder.INSTANCE
                                                .removeBgTriggerMsg();
                                    }
                                    if (freecessController3.mIsFreecessEnable) {
                                        freecessController3.unFreezePackage("MUM");
                                    }
                                    mARsPolicyManager5.mFirstTimeUpdatePackages = true;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(true);
                                    }
                                    FilterManager filterManager =
                                            FilterManager.FilterManagerHolder.INSTANCE;
                                    int i3 = 1;
                                    while (true) {
                                        FilterFactory filterFactory = filterManager.mFF;
                                        if (i3 >= 34) {
                                            filterFactory.filterHashMap.clear();
                                            filterManager.mFCF.filterHashMap.clear();
                                            mARsPolicyManager5.mCurrentUserId = intExtra;
                                            mARsPolicyManager5.mLastNotiSentTimeForDisabled = 0L;
                                            UserHandle userHandle = new UserHandle(intExtra);
                                            try {
                                                Context context3 = mARsPolicyManager5.mContext;
                                                context2 =
                                                        context3.createPackageContextAsUser(
                                                                context3.getPackageName(),
                                                                0,
                                                                userHandle);
                                            } catch (PackageManager.NameNotFoundException unused2) {
                                                context2 = mARsPolicyManager5.mContext;
                                            }
                                            MARsDBManager mARsDBManager =
                                                    MARsDBManager.MARsDBManagerHolder.INSTANCE;
                                            Context context4 = mARsDBManager.mCurrentContext;
                                            try {
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerSettingsObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerSettingsObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerSettingsObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerFreezeExcludeListObserver) {
                                                    mARsDBManager
                                                            .mContext
                                                            .getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerFreezeExcludeListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerFreezeExcludeListObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerDefaultAllowedListObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerDefaultAllowedListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerDefaultAllowedListObserver =
                                                            false;
                                                }
                                            } catch (IllegalArgumentException unused3) {
                                                Slog.e(
                                                        "MARsDBManager",
                                                        "IllegalArgumentException occurred in"
                                                            + " unregisterContentObserver()");
                                            }
                                            mARsDBManager.mCurrentContext = context2;
                                            mARsDBManager.registerContentObservers(context2);
                                            FilterManager.FilterManagerHolder.INSTANCE.init(
                                                    context2);
                                            MARsHandler.MARsHandlerHolder.INSTANCE
                                                    .sendInitDisabledMsgToMainHandler(intExtra);
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler mARsDBHandler2 =
                                                    MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                            if (mARsDBHandler2.mMainHandler == null) {
                                                return;
                                            }
                                            Bundle m =
                                                    SystemUpdateManagerService$$ExternalSyntheticOutline0
                                                            .m(intExtra, "userId");
                                            Message obtainMessage4 =
                                                    mARsDBHandler2.mMainHandler.obtainMessage(7);
                                            obtainMessage4.setData(m);
                                            mARsDBHandler2.mMainHandler.sendMessage(obtainMessage4);
                                            return;
                                        }
                                        ((IFilter)
                                                        filterFactory.filterHashMap.get(
                                                                Integer.valueOf(i3)))
                                                .deInit();
                                        i3++;
                                    }
                                    break;
                            }
                        }
                    };
            final int i3 = 3;
            mARsTrigger.mPolicyIntentReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            boolean z;
                            Bundle extras;
                            ArrayList<String> stringArrayList;
                            Context context2;
                            int i22 = 2;
                            switch (i3) {
                                case 0:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    if (action.equals("android.intent.action.SCREEN_ON")) {
                                        boolean z2 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setScreenOnState(true);
                                        MARsTrigger mARsTrigger2 = mARsTrigger;
                                        PendingIntent pendingIntent =
                                                mARsTrigger2.mMARsFirstTriggerPolicyAlarmIntent;
                                        if (pendingIntent != null) {
                                            mARsTrigger2.mAlarm.cancel(pendingIntent);
                                        }
                                        MARsHandler mARsHandler =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        MARsHandler.MainHandler mainHandler =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler != null) {
                                            mainHandler.removeMessages(1);
                                        }
                                        MARsHandler.MainHandler mainHandler2 =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler2 == null) {
                                            return;
                                        }
                                        mainHandler2.removeMessages(2);
                                        return;
                                    }
                                    if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                                        ActiveTrafficFilter activeTrafficFilter =
                                                ActiveTrafficFilter.ActiveTrafficFilterHolder
                                                        .INSTANCE;
                                        NetworkInfo activeNetworkInfo =
                                                ((ConnectivityManager)
                                                                activeTrafficFilter.mContext
                                                                        .getSystemService(
                                                                                "connectivity"))
                                                        .getActiveNetworkInfo();
                                        activeTrafficFilter.mIsDataConnectionConnected =
                                                activeNetworkInfo != null
                                                        && activeNetworkInfo.isConnected();
                                        if (MARsDebugConfig.DEBUG_FILTER) {
                                            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                                    "MARs:ActiveTrafficFilter",
                                                    new StringBuilder("DataConnection: "),
                                                    activeTrafficFilter.mIsDataConnectionConnected);
                                            return;
                                        }
                                        return;
                                    }
                                    if (action.equals(UiModeManager.ACTION_ENTER_CAR_MODE)) {
                                        boolean z3 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager mARsPolicyManager =
                                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                        synchronized (mARsPolicyManager) {
                                            mARsPolicyManager.mCarModeOn = true;
                                        }
                                        return;
                                    }
                                    if (action.equals(Constants.SIM_STATE_CHANGED)) {
                                        AllowListFilter.AllowListFilterHolder.INSTANCE
                                                .setCarrierAllowList();
                                        return;
                                    }
                                    if (!action.equals("MARS_REQUEST_DB_COMPLETE")) {
                                        if (action.equals(
                                                "com.samsung.android.sm.ACTION_SCPM_MARS_SETTINGS_UPDATED")) {
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE
                                                    .sendGetSCPMPolicyMsgToDBHandler();
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager2 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager2.getClass();
                                    synchronized (MARsPolicyManager.MARsLock) {
                                        if (mARsPolicyManager2.mMARsTargetPackages.mMap.size()
                                                <= 0) {
                                            r3 = false;
                                        }
                                    }
                                    if (r3) {
                                        return;
                                    }
                                    MARsDBHandler.getInstance();
                                    MARsDBHandler mARsDBHandler =
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                    if (mARsDBHandler.mMainHandler == null) {
                                        return;
                                    }
                                    Slog.d("MARsDBHandler", "sendSdhmsDBCompleteMsgToDBHandler");
                                    mARsDBHandler.mMainHandler.post(
                                            mARsDBHandler.mFASDBupdateRunnable);
                                    return;
                                case 1:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals(
                                                            "com.samsung.intent.action.EMERGENCY_STATE_CHANGED")
                                            || intent.getIntExtra("reason", 0) != 5) {
                                        return;
                                    }
                                    Slog.d("MARsTrigger", "disable ultra power saving mode");
                                    if (MARsPolicyManager.MARs_ENABLE) {
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setSubUserIds();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger3 = mARsTrigger;
                                    if (mARsTrigger3.mEmStateReceiverRegistered) {
                                        try {
                                            mARsTrigger3.mContext.unregisterReceiver(
                                                    mARsTrigger3.mEmergencyStateChangedReceiver);
                                            mARsTrigger3.mEmStateReceiverRegistered = false;
                                        } catch (IllegalArgumentException unused) {
                                            Slog.e(
                                                    "MARsTrigger",
                                                    "IllegalArgumentException occurred in"
                                                        + " unregisterEmStateReceiver()");
                                        }
                                    }
                                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.postInit();
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action2 = intent.getAction();
                                    if (!action2.equals("android.intent.action.SCREEN_OFF")) {
                                        if (action2.equals(UiModeManager.ACTION_EXIT_CAR_MODE)) {
                                            boolean z5 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager3 =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager3) {
                                                mARsPolicyManager3.mCarModeOn = false;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z6 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager4 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager4.setScreenOnState(false);
                                    boolean z7 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    if (FreecessController.FreecessControllerHolder.INSTANCE
                                            .mIsDumpstateWorking) {
                                        return;
                                    }
                                    synchronized (mARsPolicyManager4) {
                                        z = mARsPolicyManager4.mCarModeOn;
                                    }
                                    if (z) {
                                        return;
                                    }
                                    if (MARsPolicyManager.isChinaModel) {
                                        MARsHandler.MARsHandlerHolder.INSTANCE
                                                .sendFirstTriggerMsgToMainHandler();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger4 = mARsTrigger;
                                    mARsTrigger4.getClass();
                                    if (mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent == null) {
                                        mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent =
                                                PendingIntent.getBroadcastAsUser(
                                                        mARsTrigger4.mContext,
                                                        0,
                                                        new Intent("FIRST_ALARM_TRIGGER_ACTION")
                                                                .setPackage("android")
                                                                .setFlags(1073741824),
                                                        67108864,
                                                        mARsTrigger4.user);
                                    }
                                    mARsTrigger4.mAlarm.setExact(
                                            1,
                                            System.currentTimeMillis() + 5000,
                                            mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent);
                                    return;
                                case 3:
                                    String action3 = intent.getAction();
                                    if (action3 == null) {
                                        return;
                                    }
                                    boolean equals = action3.equals("FIRST_ALARM_TRIGGER_ACTION");
                                    MARsHandler mARsHandler2 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals) {
                                        Slog.d(
                                                "MARsTrigger",
                                                "mPolicyIntentReceiver broadcast received action : "
                                                        .concat(action3));
                                        boolean z8 = MARsPolicyManager.MARs_ENABLE;
                                        if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .getScreenOnState()) {
                                            boolean z9 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            if (!FreecessController.FreecessControllerHolder
                                                    .INSTANCE
                                                    .mIsDumpstateWorking) {
                                                mARsHandler2.sendFirstTriggerMsgToMainHandler();
                                                mARsHandler2.sendRepeatTriggerMsgToMainHandler();
                                            }
                                        }
                                    }
                                    if (!action3.equals(
                                                    "com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")
                                            || (extras = intent.getExtras()) == null) {
                                        return;
                                    }
                                    String string = extras.getString("POLICY_NAME", "");
                                    mARsTrigger.getClass();
                                    if ("applocker".equalsIgnoreCase(string)) {
                                        i22 = 1;
                                    } else if (!"autorun".equalsIgnoreCase(string)) {
                                        i22 =
                                                "freecess".equalsIgnoreCase(string)
                                                        ? 4
                                                        : "sbikepolicy".equalsIgnoreCase(string)
                                                                ? 6
                                                                : "gamepolicy"
                                                                                .equalsIgnoreCase(
                                                                                        string)
                                                                        ? 9
                                                                        : 0;
                                    }
                                    ArrayList<String> stringArrayList2 =
                                            extras.getStringArrayList("PACKAGE_NAME");
                                    if (i22 == 0 || stringArrayList2 == null) {
                                        return;
                                    }
                                    mARsHandler2.sendRunPolicySpecificPkgMsgToMainHandler(
                                            i22,
                                            mARsTrigger.mContext.getUserId(),
                                            null,
                                            stringArrayList2);
                                    return;
                                case 4:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action4 = intent.getAction();
                                    boolean equals2 =
                                            action4.equals(
                                                    "com.android.server.am.MARS_TRIGGER_SBIKE_MODE_POLICY");
                                    MARsHandler mARsHandler3 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals2) {
                                        Bundle extras2 = intent.getExtras();
                                        if (extras2 == null
                                                || (stringArrayList =
                                                                extras2.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        mARsHandler3.sendRunPolicySpecificPkgMsgToMainHandler(
                                                6,
                                                mARsTrigger.mContext.getUserId(),
                                                MARsTrigger.m199$$Nest$mactionToString(
                                                        mARsTrigger, action4),
                                                stringArrayList);
                                        return;
                                    }
                                    if (action4.equals(
                                            "com.android.server.am.MARS_CANCEL_SBIKE_MODE_POLICY")) {
                                        Bundle extras3 = intent.getExtras();
                                        if (extras3 == null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6, mARsTrigger.mContext.getUserId(), null);
                                            return;
                                        }
                                        ArrayList<String> stringArrayList3 =
                                                extras3.getStringArrayList("PACKAGE_NAME");
                                        if (stringArrayList3 != null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6,
                                                    mARsTrigger.mContext.getUserId(),
                                                    stringArrayList3);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 5:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String stringExtra = intent.getStringExtra("package");
                                    if (stringExtra == null
                                            || "com.samsung.android.game.gos".equals(stringExtra)) {
                                        String action5 = intent.getAction();
                                        boolean equals3 =
                                                action5.equals(
                                                        "com.android.server.am.MARS_TRIGGER_GAME_MODE_POLICY");
                                        MARsHandler mARsHandler4 =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        if (!equals3) {
                                            if (action5.equals(
                                                    "com.android.server.am.MARS_CANCEL_GAME_MODE_POLICY")) {
                                                boolean z10 =
                                                        FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                                FreecessController freecessController =
                                                        FreecessController.FreecessControllerHolder
                                                                .INSTANCE;
                                                if (freecessController.mCalmModeEnabled) {
                                                    FreecessHandler freecessHandler =
                                                            FreecessHandler.FreecessHandlerHolder
                                                                    .INSTANCE;
                                                    FreecessHandler.MainHandler mainHandler3 =
                                                            freecessHandler.mMainHandler;
                                                    if (mainHandler3 != null) {
                                                        Message obtainMessage =
                                                                mainHandler3.obtainMessage(23);
                                                        freecessHandler.mMainHandler.removeMessages(
                                                                23);
                                                        freecessHandler.mMainHandler.sendMessage(
                                                                obtainMessage);
                                                    }
                                                } else {
                                                    boolean z11 = MARsPolicyManager.MARs_ENABLE;
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE
                                                            .getClass();
                                                    if (!MARsPolicyManager.isChinaPolicyEnabled()) {
                                                        mARsHandler4
                                                                .sendCancelPolicyMsgToMainHandler(
                                                                        9, -1, null);
                                                        if (freecessController.mGameModeEnabled) {
                                                            freecessController.mGameModeEnabled =
                                                                    false;
                                                        }
                                                    }
                                                }
                                                Slog.d(
                                                        "MARsTrigger",
                                                        "broadcast received action :"
                                                            + " MARS_CANCEL_GAME_MODE_POLICY");
                                                return;
                                            }
                                            return;
                                        }
                                        Bundle extras4 = intent.getExtras();
                                        if (extras4 != null) {
                                            ArrayList<String> stringArrayList4 =
                                                    extras4.getStringArrayList("survive_app");
                                            LatestProtectedPackageFilter
                                                            .LatestProtectedPackageFilterHolder
                                                            .INSTANCE
                                                            .mProtectedAppSizeForGame =
                                                    extras4.getInt("lru_num");
                                            boolean z12 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            FreecessController freecessController2 =
                                                    FreecessController.FreecessControllerHolder
                                                            .INSTANCE;
                                            if (!freecessController2.mIsFreecessEnable
                                                    || !FreecessController.IS_SUPPORT_CALM_MODE) {
                                                mARsHandler4
                                                        .sendRunPolicySpecificPkgMsgToMainHandler(
                                                                9,
                                                                mARsTrigger.mContext.getUserId(),
                                                                MARsTrigger
                                                                        .m199$$Nest$mactionToString(
                                                                                mARsTrigger,
                                                                                action5),
                                                                stringArrayList4);
                                                if (freecessController2.mGameModeEnabled) {
                                                    return;
                                                }
                                                freecessController2.mGameModeEnabled = true;
                                                return;
                                            }
                                            FreecessHandler freecessHandler2 =
                                                    FreecessHandler.FreecessHandlerHolder.INSTANCE;
                                            FreecessHandler.MainHandler mainHandler4 =
                                                    freecessHandler2.mMainHandler;
                                            if (mainHandler4 == null) {
                                                return;
                                            }
                                            mainHandler4.removeMessages(21);
                                            Bundle bundle = new Bundle();
                                            bundle.putStringArrayList("list", stringArrayList4);
                                            Message obtainMessage2 =
                                                    freecessHandler2.mMainHandler.obtainMessage(21);
                                            obtainMessage2.setData(bundle);
                                            freecessHandler2.mMainHandler.sendMessage(
                                                    obtainMessage2);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 6:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals("android.intent.action.TIME_SET")) {
                                        return;
                                    }
                                    long currentTimeMillis = System.currentTimeMillis();
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    MARsTrigger mARsTrigger5 = mARsTrigger;
                                    long j =
                                            currentTimeMillis
                                                    - ((elapsedRealtime
                                                                    - mARsTrigger5
                                                                            .mLastTimeChangeRealtime)
                                                            + mARsTrigger5
                                                                    .mLastTimeChangeClockTime);
                                    MARsHandler mARsHandler5 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putLong("changedTime", j);
                                    Message obtainMessage3 =
                                            mARsHandler5.mMainHandler.obtainMessage(6);
                                    obtainMessage3.setData(bundle2);
                                    mARsHandler5.mMainHandler.sendMessage(obtainMessage3);
                                    MARsTrigger mARsTrigger6 = mARsTrigger;
                                    mARsTrigger6.mLastTimeChangeClockTime = currentTimeMillis;
                                    mARsTrigger6.mLastTimeChangeRealtime = elapsedRealtime;
                                    boolean z13 = EventRecorder.FEATURE_ENABLE;
                                    EventRecorder eventRecorder =
                                            EventRecorder.EventRecorderHolder.INSTANCE;
                                    eventRecorder.getClass();
                                    if (Math.abs(j) < 30000) {
                                        Slog.d(
                                                "EventRecorder",
                                                "changed time is " + j + ". ignore.");
                                        return;
                                    }
                                    Slog.d("EventRecorder", "disable EventRecorder");
                                    eventRecorder.performWrite(System.currentTimeMillis());
                                    EventRecorder.FEATURE_ENABLE = false;
                                    JobScheduler jobScheduler =
                                            (JobScheduler)
                                                    eventRecorder.mContext.getSystemService(
                                                            "jobscheduler");
                                    if (jobScheduler != null) {
                                        jobScheduler
                                                .forNamespace("MARsEventRecorderNamespace")
                                                .cancel(1);
                                    }
                                    ReentrantLock reentrantLock = EventRecorder.mFileLock;
                                    reentrantLock.lock();
                                    try {
                                        Slog.d(
                                                "EventRecorder",
                                                "file delete result : "
                                                        + EventRecorder.file.delete());
                                        reentrantLock.unlock();
                                        return;
                                    } catch (Throwable th) {
                                        EventRecorder.mFileLock.unlock();
                                        throw th;
                                    }
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action6 = intent.getAction();
                                    Slog.d("MARsTrigger", "broadcast received action : " + action6);
                                    if (!"android.intent.action.USER_SWITCHED".equals(action6)) {
                                        return;
                                    }
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", 0);
                                    Slog.d(
                                            "MARsTrigger",
                                            "mContext.id = " + mARsTrigger.mContext.getUserId());
                                    if (MARsPolicyManager.App_StartUp_History) {
                                        MARsDBManager.MARsDBManagerHolder.INSTANCE
                                                .doUpdateCompHistory(false);
                                    }
                                    MARsPolicyManager mARsPolicyManager5 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager5.addDebugInfoToHistory("DEV", "switchUser");
                                    boolean z14 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController3 =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    boolean z15 = freecessController3.mIsScreenOnFreecessEnabled;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(false);
                                        if (MARsDebugConfig.DEBUG_ENG) {
                                            Slog.d("FreecessController", "removeBgTriggerMsg....");
                                        }
                                        FreecessHandler.FreecessHandlerHolder.INSTANCE
                                                .removeBgTriggerMsg();
                                    }
                                    if (freecessController3.mIsFreecessEnable) {
                                        freecessController3.unFreezePackage("MUM");
                                    }
                                    mARsPolicyManager5.mFirstTimeUpdatePackages = true;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(true);
                                    }
                                    FilterManager filterManager =
                                            FilterManager.FilterManagerHolder.INSTANCE;
                                    int i32 = 1;
                                    while (true) {
                                        FilterFactory filterFactory = filterManager.mFF;
                                        if (i32 >= 34) {
                                            filterFactory.filterHashMap.clear();
                                            filterManager.mFCF.filterHashMap.clear();
                                            mARsPolicyManager5.mCurrentUserId = intExtra;
                                            mARsPolicyManager5.mLastNotiSentTimeForDisabled = 0L;
                                            UserHandle userHandle = new UserHandle(intExtra);
                                            try {
                                                Context context3 = mARsPolicyManager5.mContext;
                                                context2 =
                                                        context3.createPackageContextAsUser(
                                                                context3.getPackageName(),
                                                                0,
                                                                userHandle);
                                            } catch (PackageManager.NameNotFoundException unused2) {
                                                context2 = mARsPolicyManager5.mContext;
                                            }
                                            MARsDBManager mARsDBManager =
                                                    MARsDBManager.MARsDBManagerHolder.INSTANCE;
                                            Context context4 = mARsDBManager.mCurrentContext;
                                            try {
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerSettingsObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerSettingsObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerSettingsObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerFreezeExcludeListObserver) {
                                                    mARsDBManager
                                                            .mContext
                                                            .getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerFreezeExcludeListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerFreezeExcludeListObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerDefaultAllowedListObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerDefaultAllowedListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerDefaultAllowedListObserver =
                                                            false;
                                                }
                                            } catch (IllegalArgumentException unused3) {
                                                Slog.e(
                                                        "MARsDBManager",
                                                        "IllegalArgumentException occurred in"
                                                            + " unregisterContentObserver()");
                                            }
                                            mARsDBManager.mCurrentContext = context2;
                                            mARsDBManager.registerContentObservers(context2);
                                            FilterManager.FilterManagerHolder.INSTANCE.init(
                                                    context2);
                                            MARsHandler.MARsHandlerHolder.INSTANCE
                                                    .sendInitDisabledMsgToMainHandler(intExtra);
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler mARsDBHandler2 =
                                                    MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                            if (mARsDBHandler2.mMainHandler == null) {
                                                return;
                                            }
                                            Bundle m =
                                                    SystemUpdateManagerService$$ExternalSyntheticOutline0
                                                            .m(intExtra, "userId");
                                            Message obtainMessage4 =
                                                    mARsDBHandler2.mMainHandler.obtainMessage(7);
                                            obtainMessage4.setData(m);
                                            mARsDBHandler2.mMainHandler.sendMessage(obtainMessage4);
                                            return;
                                        }
                                        ((IFilter)
                                                        filterFactory.filterHashMap.get(
                                                                Integer.valueOf(i32)))
                                                .deInit();
                                        i32++;
                                    }
                                    break;
                            }
                        }
                    };
            final int i4 = 4;
            mARsTrigger.mPolicySBikeIntentReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            boolean z;
                            Bundle extras;
                            ArrayList<String> stringArrayList;
                            Context context2;
                            int i22 = 2;
                            switch (i4) {
                                case 0:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    if (action.equals("android.intent.action.SCREEN_ON")) {
                                        boolean z2 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setScreenOnState(true);
                                        MARsTrigger mARsTrigger2 = mARsTrigger;
                                        PendingIntent pendingIntent =
                                                mARsTrigger2.mMARsFirstTriggerPolicyAlarmIntent;
                                        if (pendingIntent != null) {
                                            mARsTrigger2.mAlarm.cancel(pendingIntent);
                                        }
                                        MARsHandler mARsHandler =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        MARsHandler.MainHandler mainHandler =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler != null) {
                                            mainHandler.removeMessages(1);
                                        }
                                        MARsHandler.MainHandler mainHandler2 =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler2 == null) {
                                            return;
                                        }
                                        mainHandler2.removeMessages(2);
                                        return;
                                    }
                                    if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                                        ActiveTrafficFilter activeTrafficFilter =
                                                ActiveTrafficFilter.ActiveTrafficFilterHolder
                                                        .INSTANCE;
                                        NetworkInfo activeNetworkInfo =
                                                ((ConnectivityManager)
                                                                activeTrafficFilter.mContext
                                                                        .getSystemService(
                                                                                "connectivity"))
                                                        .getActiveNetworkInfo();
                                        activeTrafficFilter.mIsDataConnectionConnected =
                                                activeNetworkInfo != null
                                                        && activeNetworkInfo.isConnected();
                                        if (MARsDebugConfig.DEBUG_FILTER) {
                                            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                                    "MARs:ActiveTrafficFilter",
                                                    new StringBuilder("DataConnection: "),
                                                    activeTrafficFilter.mIsDataConnectionConnected);
                                            return;
                                        }
                                        return;
                                    }
                                    if (action.equals(UiModeManager.ACTION_ENTER_CAR_MODE)) {
                                        boolean z3 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager mARsPolicyManager =
                                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                        synchronized (mARsPolicyManager) {
                                            mARsPolicyManager.mCarModeOn = true;
                                        }
                                        return;
                                    }
                                    if (action.equals(Constants.SIM_STATE_CHANGED)) {
                                        AllowListFilter.AllowListFilterHolder.INSTANCE
                                                .setCarrierAllowList();
                                        return;
                                    }
                                    if (!action.equals("MARS_REQUEST_DB_COMPLETE")) {
                                        if (action.equals(
                                                "com.samsung.android.sm.ACTION_SCPM_MARS_SETTINGS_UPDATED")) {
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE
                                                    .sendGetSCPMPolicyMsgToDBHandler();
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager2 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager2.getClass();
                                    synchronized (MARsPolicyManager.MARsLock) {
                                        if (mARsPolicyManager2.mMARsTargetPackages.mMap.size()
                                                <= 0) {
                                            r3 = false;
                                        }
                                    }
                                    if (r3) {
                                        return;
                                    }
                                    MARsDBHandler.getInstance();
                                    MARsDBHandler mARsDBHandler =
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                    if (mARsDBHandler.mMainHandler == null) {
                                        return;
                                    }
                                    Slog.d("MARsDBHandler", "sendSdhmsDBCompleteMsgToDBHandler");
                                    mARsDBHandler.mMainHandler.post(
                                            mARsDBHandler.mFASDBupdateRunnable);
                                    return;
                                case 1:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals(
                                                            "com.samsung.intent.action.EMERGENCY_STATE_CHANGED")
                                            || intent.getIntExtra("reason", 0) != 5) {
                                        return;
                                    }
                                    Slog.d("MARsTrigger", "disable ultra power saving mode");
                                    if (MARsPolicyManager.MARs_ENABLE) {
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setSubUserIds();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger3 = mARsTrigger;
                                    if (mARsTrigger3.mEmStateReceiverRegistered) {
                                        try {
                                            mARsTrigger3.mContext.unregisterReceiver(
                                                    mARsTrigger3.mEmergencyStateChangedReceiver);
                                            mARsTrigger3.mEmStateReceiverRegistered = false;
                                        } catch (IllegalArgumentException unused) {
                                            Slog.e(
                                                    "MARsTrigger",
                                                    "IllegalArgumentException occurred in"
                                                        + " unregisterEmStateReceiver()");
                                        }
                                    }
                                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.postInit();
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action2 = intent.getAction();
                                    if (!action2.equals("android.intent.action.SCREEN_OFF")) {
                                        if (action2.equals(UiModeManager.ACTION_EXIT_CAR_MODE)) {
                                            boolean z5 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager3 =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager3) {
                                                mARsPolicyManager3.mCarModeOn = false;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z6 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager4 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager4.setScreenOnState(false);
                                    boolean z7 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    if (FreecessController.FreecessControllerHolder.INSTANCE
                                            .mIsDumpstateWorking) {
                                        return;
                                    }
                                    synchronized (mARsPolicyManager4) {
                                        z = mARsPolicyManager4.mCarModeOn;
                                    }
                                    if (z) {
                                        return;
                                    }
                                    if (MARsPolicyManager.isChinaModel) {
                                        MARsHandler.MARsHandlerHolder.INSTANCE
                                                .sendFirstTriggerMsgToMainHandler();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger4 = mARsTrigger;
                                    mARsTrigger4.getClass();
                                    if (mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent == null) {
                                        mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent =
                                                PendingIntent.getBroadcastAsUser(
                                                        mARsTrigger4.mContext,
                                                        0,
                                                        new Intent("FIRST_ALARM_TRIGGER_ACTION")
                                                                .setPackage("android")
                                                                .setFlags(1073741824),
                                                        67108864,
                                                        mARsTrigger4.user);
                                    }
                                    mARsTrigger4.mAlarm.setExact(
                                            1,
                                            System.currentTimeMillis() + 5000,
                                            mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent);
                                    return;
                                case 3:
                                    String action3 = intent.getAction();
                                    if (action3 == null) {
                                        return;
                                    }
                                    boolean equals = action3.equals("FIRST_ALARM_TRIGGER_ACTION");
                                    MARsHandler mARsHandler2 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals) {
                                        Slog.d(
                                                "MARsTrigger",
                                                "mPolicyIntentReceiver broadcast received action : "
                                                        .concat(action3));
                                        boolean z8 = MARsPolicyManager.MARs_ENABLE;
                                        if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .getScreenOnState()) {
                                            boolean z9 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            if (!FreecessController.FreecessControllerHolder
                                                    .INSTANCE
                                                    .mIsDumpstateWorking) {
                                                mARsHandler2.sendFirstTriggerMsgToMainHandler();
                                                mARsHandler2.sendRepeatTriggerMsgToMainHandler();
                                            }
                                        }
                                    }
                                    if (!action3.equals(
                                                    "com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")
                                            || (extras = intent.getExtras()) == null) {
                                        return;
                                    }
                                    String string = extras.getString("POLICY_NAME", "");
                                    mARsTrigger.getClass();
                                    if ("applocker".equalsIgnoreCase(string)) {
                                        i22 = 1;
                                    } else if (!"autorun".equalsIgnoreCase(string)) {
                                        i22 =
                                                "freecess".equalsIgnoreCase(string)
                                                        ? 4
                                                        : "sbikepolicy".equalsIgnoreCase(string)
                                                                ? 6
                                                                : "gamepolicy"
                                                                                .equalsIgnoreCase(
                                                                                        string)
                                                                        ? 9
                                                                        : 0;
                                    }
                                    ArrayList<String> stringArrayList2 =
                                            extras.getStringArrayList("PACKAGE_NAME");
                                    if (i22 == 0 || stringArrayList2 == null) {
                                        return;
                                    }
                                    mARsHandler2.sendRunPolicySpecificPkgMsgToMainHandler(
                                            i22,
                                            mARsTrigger.mContext.getUserId(),
                                            null,
                                            stringArrayList2);
                                    return;
                                case 4:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action4 = intent.getAction();
                                    boolean equals2 =
                                            action4.equals(
                                                    "com.android.server.am.MARS_TRIGGER_SBIKE_MODE_POLICY");
                                    MARsHandler mARsHandler3 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals2) {
                                        Bundle extras2 = intent.getExtras();
                                        if (extras2 == null
                                                || (stringArrayList =
                                                                extras2.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        mARsHandler3.sendRunPolicySpecificPkgMsgToMainHandler(
                                                6,
                                                mARsTrigger.mContext.getUserId(),
                                                MARsTrigger.m199$$Nest$mactionToString(
                                                        mARsTrigger, action4),
                                                stringArrayList);
                                        return;
                                    }
                                    if (action4.equals(
                                            "com.android.server.am.MARS_CANCEL_SBIKE_MODE_POLICY")) {
                                        Bundle extras3 = intent.getExtras();
                                        if (extras3 == null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6, mARsTrigger.mContext.getUserId(), null);
                                            return;
                                        }
                                        ArrayList<String> stringArrayList3 =
                                                extras3.getStringArrayList("PACKAGE_NAME");
                                        if (stringArrayList3 != null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6,
                                                    mARsTrigger.mContext.getUserId(),
                                                    stringArrayList3);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 5:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String stringExtra = intent.getStringExtra("package");
                                    if (stringExtra == null
                                            || "com.samsung.android.game.gos".equals(stringExtra)) {
                                        String action5 = intent.getAction();
                                        boolean equals3 =
                                                action5.equals(
                                                        "com.android.server.am.MARS_TRIGGER_GAME_MODE_POLICY");
                                        MARsHandler mARsHandler4 =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        if (!equals3) {
                                            if (action5.equals(
                                                    "com.android.server.am.MARS_CANCEL_GAME_MODE_POLICY")) {
                                                boolean z10 =
                                                        FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                                FreecessController freecessController =
                                                        FreecessController.FreecessControllerHolder
                                                                .INSTANCE;
                                                if (freecessController.mCalmModeEnabled) {
                                                    FreecessHandler freecessHandler =
                                                            FreecessHandler.FreecessHandlerHolder
                                                                    .INSTANCE;
                                                    FreecessHandler.MainHandler mainHandler3 =
                                                            freecessHandler.mMainHandler;
                                                    if (mainHandler3 != null) {
                                                        Message obtainMessage =
                                                                mainHandler3.obtainMessage(23);
                                                        freecessHandler.mMainHandler.removeMessages(
                                                                23);
                                                        freecessHandler.mMainHandler.sendMessage(
                                                                obtainMessage);
                                                    }
                                                } else {
                                                    boolean z11 = MARsPolicyManager.MARs_ENABLE;
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE
                                                            .getClass();
                                                    if (!MARsPolicyManager.isChinaPolicyEnabled()) {
                                                        mARsHandler4
                                                                .sendCancelPolicyMsgToMainHandler(
                                                                        9, -1, null);
                                                        if (freecessController.mGameModeEnabled) {
                                                            freecessController.mGameModeEnabled =
                                                                    false;
                                                        }
                                                    }
                                                }
                                                Slog.d(
                                                        "MARsTrigger",
                                                        "broadcast received action :"
                                                            + " MARS_CANCEL_GAME_MODE_POLICY");
                                                return;
                                            }
                                            return;
                                        }
                                        Bundle extras4 = intent.getExtras();
                                        if (extras4 != null) {
                                            ArrayList<String> stringArrayList4 =
                                                    extras4.getStringArrayList("survive_app");
                                            LatestProtectedPackageFilter
                                                            .LatestProtectedPackageFilterHolder
                                                            .INSTANCE
                                                            .mProtectedAppSizeForGame =
                                                    extras4.getInt("lru_num");
                                            boolean z12 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            FreecessController freecessController2 =
                                                    FreecessController.FreecessControllerHolder
                                                            .INSTANCE;
                                            if (!freecessController2.mIsFreecessEnable
                                                    || !FreecessController.IS_SUPPORT_CALM_MODE) {
                                                mARsHandler4
                                                        .sendRunPolicySpecificPkgMsgToMainHandler(
                                                                9,
                                                                mARsTrigger.mContext.getUserId(),
                                                                MARsTrigger
                                                                        .m199$$Nest$mactionToString(
                                                                                mARsTrigger,
                                                                                action5),
                                                                stringArrayList4);
                                                if (freecessController2.mGameModeEnabled) {
                                                    return;
                                                }
                                                freecessController2.mGameModeEnabled = true;
                                                return;
                                            }
                                            FreecessHandler freecessHandler2 =
                                                    FreecessHandler.FreecessHandlerHolder.INSTANCE;
                                            FreecessHandler.MainHandler mainHandler4 =
                                                    freecessHandler2.mMainHandler;
                                            if (mainHandler4 == null) {
                                                return;
                                            }
                                            mainHandler4.removeMessages(21);
                                            Bundle bundle = new Bundle();
                                            bundle.putStringArrayList("list", stringArrayList4);
                                            Message obtainMessage2 =
                                                    freecessHandler2.mMainHandler.obtainMessage(21);
                                            obtainMessage2.setData(bundle);
                                            freecessHandler2.mMainHandler.sendMessage(
                                                    obtainMessage2);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 6:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals("android.intent.action.TIME_SET")) {
                                        return;
                                    }
                                    long currentTimeMillis = System.currentTimeMillis();
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    MARsTrigger mARsTrigger5 = mARsTrigger;
                                    long j =
                                            currentTimeMillis
                                                    - ((elapsedRealtime
                                                                    - mARsTrigger5
                                                                            .mLastTimeChangeRealtime)
                                                            + mARsTrigger5
                                                                    .mLastTimeChangeClockTime);
                                    MARsHandler mARsHandler5 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putLong("changedTime", j);
                                    Message obtainMessage3 =
                                            mARsHandler5.mMainHandler.obtainMessage(6);
                                    obtainMessage3.setData(bundle2);
                                    mARsHandler5.mMainHandler.sendMessage(obtainMessage3);
                                    MARsTrigger mARsTrigger6 = mARsTrigger;
                                    mARsTrigger6.mLastTimeChangeClockTime = currentTimeMillis;
                                    mARsTrigger6.mLastTimeChangeRealtime = elapsedRealtime;
                                    boolean z13 = EventRecorder.FEATURE_ENABLE;
                                    EventRecorder eventRecorder =
                                            EventRecorder.EventRecorderHolder.INSTANCE;
                                    eventRecorder.getClass();
                                    if (Math.abs(j) < 30000) {
                                        Slog.d(
                                                "EventRecorder",
                                                "changed time is " + j + ". ignore.");
                                        return;
                                    }
                                    Slog.d("EventRecorder", "disable EventRecorder");
                                    eventRecorder.performWrite(System.currentTimeMillis());
                                    EventRecorder.FEATURE_ENABLE = false;
                                    JobScheduler jobScheduler =
                                            (JobScheduler)
                                                    eventRecorder.mContext.getSystemService(
                                                            "jobscheduler");
                                    if (jobScheduler != null) {
                                        jobScheduler
                                                .forNamespace("MARsEventRecorderNamespace")
                                                .cancel(1);
                                    }
                                    ReentrantLock reentrantLock = EventRecorder.mFileLock;
                                    reentrantLock.lock();
                                    try {
                                        Slog.d(
                                                "EventRecorder",
                                                "file delete result : "
                                                        + EventRecorder.file.delete());
                                        reentrantLock.unlock();
                                        return;
                                    } catch (Throwable th) {
                                        EventRecorder.mFileLock.unlock();
                                        throw th;
                                    }
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action6 = intent.getAction();
                                    Slog.d("MARsTrigger", "broadcast received action : " + action6);
                                    if (!"android.intent.action.USER_SWITCHED".equals(action6)) {
                                        return;
                                    }
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", 0);
                                    Slog.d(
                                            "MARsTrigger",
                                            "mContext.id = " + mARsTrigger.mContext.getUserId());
                                    if (MARsPolicyManager.App_StartUp_History) {
                                        MARsDBManager.MARsDBManagerHolder.INSTANCE
                                                .doUpdateCompHistory(false);
                                    }
                                    MARsPolicyManager mARsPolicyManager5 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager5.addDebugInfoToHistory("DEV", "switchUser");
                                    boolean z14 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController3 =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    boolean z15 = freecessController3.mIsScreenOnFreecessEnabled;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(false);
                                        if (MARsDebugConfig.DEBUG_ENG) {
                                            Slog.d("FreecessController", "removeBgTriggerMsg....");
                                        }
                                        FreecessHandler.FreecessHandlerHolder.INSTANCE
                                                .removeBgTriggerMsg();
                                    }
                                    if (freecessController3.mIsFreecessEnable) {
                                        freecessController3.unFreezePackage("MUM");
                                    }
                                    mARsPolicyManager5.mFirstTimeUpdatePackages = true;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(true);
                                    }
                                    FilterManager filterManager =
                                            FilterManager.FilterManagerHolder.INSTANCE;
                                    int i32 = 1;
                                    while (true) {
                                        FilterFactory filterFactory = filterManager.mFF;
                                        if (i32 >= 34) {
                                            filterFactory.filterHashMap.clear();
                                            filterManager.mFCF.filterHashMap.clear();
                                            mARsPolicyManager5.mCurrentUserId = intExtra;
                                            mARsPolicyManager5.mLastNotiSentTimeForDisabled = 0L;
                                            UserHandle userHandle = new UserHandle(intExtra);
                                            try {
                                                Context context3 = mARsPolicyManager5.mContext;
                                                context2 =
                                                        context3.createPackageContextAsUser(
                                                                context3.getPackageName(),
                                                                0,
                                                                userHandle);
                                            } catch (PackageManager.NameNotFoundException unused2) {
                                                context2 = mARsPolicyManager5.mContext;
                                            }
                                            MARsDBManager mARsDBManager =
                                                    MARsDBManager.MARsDBManagerHolder.INSTANCE;
                                            Context context4 = mARsDBManager.mCurrentContext;
                                            try {
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerSettingsObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerSettingsObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerSettingsObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerFreezeExcludeListObserver) {
                                                    mARsDBManager
                                                            .mContext
                                                            .getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerFreezeExcludeListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerFreezeExcludeListObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerDefaultAllowedListObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerDefaultAllowedListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerDefaultAllowedListObserver =
                                                            false;
                                                }
                                            } catch (IllegalArgumentException unused3) {
                                                Slog.e(
                                                        "MARsDBManager",
                                                        "IllegalArgumentException occurred in"
                                                            + " unregisterContentObserver()");
                                            }
                                            mARsDBManager.mCurrentContext = context2;
                                            mARsDBManager.registerContentObservers(context2);
                                            FilterManager.FilterManagerHolder.INSTANCE.init(
                                                    context2);
                                            MARsHandler.MARsHandlerHolder.INSTANCE
                                                    .sendInitDisabledMsgToMainHandler(intExtra);
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler mARsDBHandler2 =
                                                    MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                            if (mARsDBHandler2.mMainHandler == null) {
                                                return;
                                            }
                                            Bundle m =
                                                    SystemUpdateManagerService$$ExternalSyntheticOutline0
                                                            .m(intExtra, "userId");
                                            Message obtainMessage4 =
                                                    mARsDBHandler2.mMainHandler.obtainMessage(7);
                                            obtainMessage4.setData(m);
                                            mARsDBHandler2.mMainHandler.sendMessage(obtainMessage4);
                                            return;
                                        }
                                        ((IFilter)
                                                        filterFactory.filterHashMap.get(
                                                                Integer.valueOf(i32)))
                                                .deInit();
                                        i32++;
                                    }
                                    break;
                            }
                        }
                    };
            final int i5 = 0;
            mARsTrigger.mTCPUReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.5
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            boolean z;
                            boolean z2;
                            UserInfo userInfo;
                            int i6;
                            UserInfo userInfo2;
                            UserInfo userInfo3;
                            boolean z3 = false;
                            switch (i5) {
                                case 0:
                                    ArrayList<Integer> integerArrayListExtra =
                                            intent.getIntegerArrayListExtra("pid");
                                    MARsHandler mARsHandler =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    MARsHandler.MainHandler mainHandler = mARsHandler.mMainHandler;
                                    if (mainHandler != null && integerArrayListExtra != null) {
                                        Message obtainMessage = mainHandler.obtainMessage(17);
                                        Bundle bundle = new Bundle();
                                        bundle.putIntegerArrayList(
                                                "pidList", integerArrayListExtra);
                                        obtainMessage.setData(bundle);
                                        mARsHandler.mMainHandler.sendMessage(obtainMessage);
                                    }
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            "broadcast received action TCPU: ",
                                            intent.getAction(),
                                            "MARsTrigger");
                                    return;
                                case 1:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    Slog.d(
                                            "MARsTrigger",
                                            "mAppStartUpIntentReceiver onReceive : " + action);
                                    if (action.equals("android.intent.action.ACTION_SHUTDOWN")
                                            || action.equals("android.intent.action.REBOOT")) {
                                        MARsBigData mARsBigData =
                                                MARsBigData.MARsBigDataHolder.INSTANCE;
                                        mARsBigData.getClass();
                                        try {
                                            mARsBigData.updateBigdataInfo();
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                            mARsBigData.PLEVdata =
                                                    new MARsBigData.UsageInfo().toString();
                                        }
                                        String str = mARsBigData.PLEVdata;
                                        if (str != null) {
                                            mARsBigData.sendBigData("PLEV", str);
                                        }
                                        boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .updateResetTime();
                                        if (MARsDebugConfig.DEBUG_MID
                                                || MARsDebugConfig.DEBUG_HIGH) {
                                            final MARsHistoryLog.SaveLogThread saveLogThread =
                                                    MARsHistoryLog.MARsHistoryLogHolder.INSTANCE
                                                            .mSaveLogThread;
                                            saveLogThread.getClass();
                                            Thread thread =
                                                    new Thread(
                                                            new Runnable() { // from class:
                                                                             // com.android.server.am.mars.MARsHistoryLog$SaveLogThread$$ExternalSyntheticLambda0
                                                                @Override // java.lang.Runnable
                                                                public final void run() {
                                                                    MARsHistoryLog.SaveLogThread
                                                                            .this
                                                                            .this$0.saveLogToFile(
                                                                            true, true);
                                                                }
                                                            });
                                            thread.setPriority(1);
                                            thread.start();
                                        }
                                        if (MARsPolicyManager.App_StartUp_History) {
                                            MARsDBManager.MARsDBManagerHolder.INSTANCE
                                                    .doUpdateCompHistory(true);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action2 = intent.getAction();
                                    Bundle extras = intent.getExtras();
                                    if (action2.equals("MARS_REQUEST_PKG_INFO")) {
                                        if (!MARsPolicyManager.MARs_ENABLE) {
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                    .postInit();
                                            return;
                                        }
                                        if (extras != null) {
                                            String string = extras.getString("MARS_EXTRA", "");
                                            boolean equals = string.equals("create");
                                            z = string.equals("update");
                                            z2 = string.equals("init");
                                            z3 = equals;
                                        } else {
                                            z = false;
                                            z2 = false;
                                        }
                                        MARsDBHandler.getInstance();
                                        MARsDBHandler mARsDBHandler =
                                                MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                        MARsDBHandler.MainHandler mainHandler2 =
                                                mARsDBHandler.mMainHandler;
                                        if (mainHandler2 == null) {
                                            return;
                                        }
                                        mainHandler2.removeMessages(6);
                                        Message obtainMessage2 =
                                                mARsDBHandler.mMainHandler.obtainMessage(6);
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putBoolean("onCreate", z3);
                                        bundle2.putBoolean("onUpgrade", z);
                                        bundle2.putBoolean("onInit", z2);
                                        obtainMessage2.setData(bundle2);
                                        mARsDBHandler.mMainHandler.sendMessage(obtainMessage2);
                                        return;
                                    }
                                    return;
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action3 = intent.getAction();
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", -1);
                                    Slog.d(
                                            "MARsTrigger",
                                            "mUserActionReceiver : "
                                                    + action3
                                                    + " userId = "
                                                    + intExtra);
                                    if ("android.intent.action.USER_STOPPED".equals(action3)) {
                                        if (intExtra >= 95 && intExtra <= 99) {
                                            boolean z5 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager) {
                                                mARsPolicyManager.mDualAppEnabled = false;
                                            }
                                            return;
                                        }
                                        UserManager userManager =
                                                (UserManager) context.getSystemService("user");
                                        if (userManager == null
                                                || (userInfo3 = userManager.getUserInfo(intExtra))
                                                        == null
                                                || !userInfo3.isManagedProfile()) {
                                            return;
                                        }
                                        boolean z6 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setManagedProfileEnabled(intExtra, false);
                                        return;
                                    }
                                    if ("android.intent.action.USER_STARTED".equals(action3)) {
                                        if (intExtra >= 95 && intExtra <= 99) {
                                            boolean z7 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager2 =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager2) {
                                                mARsPolicyManager2.mDualAppEnabled = true;
                                            }
                                            return;
                                        }
                                        UserManager userManager2 =
                                                (UserManager) context.getSystemService("user");
                                        if (userManager2 == null
                                                || (userInfo2 = userManager2.getUserInfo(intExtra))
                                                        == null
                                                || !userInfo2.isManagedProfile()) {
                                            return;
                                        }
                                        boolean z8 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setManagedProfileEnabled(intExtra, true);
                                        return;
                                    }
                                    if ("android.intent.action.USER_ADDED".equals(action3)) {
                                        if (intExtra < 95 || intExtra > 99) {
                                            UserManager userManager3 =
                                                    (UserManager) context.getSystemService("user");
                                            if (userManager3 == null
                                                    || (userInfo =
                                                                    userManager3.getUserInfo(
                                                                            intExtra))
                                                            == null
                                                    || !userInfo.isManagedProfile()) {
                                                return;
                                            }
                                            boolean z9 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                    .setManagedProfileEnabled(intExtra, true);
                                            return;
                                        }
                                        boolean z10 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager mARsPolicyManager3 =
                                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                        synchronized (mARsPolicyManager3) {
                                            mARsPolicyManager3.mDualAppEnabled = true;
                                        }
                                        synchronized (mARsPolicyManager3) {
                                            i6 = mARsPolicyManager3.mDualAppUserId;
                                        }
                                        if (intExtra != i6) {
                                            synchronized (mARsPolicyManager3) {
                                                mARsPolicyManager3.mDualAppUserId = intExtra;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                            }
                        }
                    };
            final int i6 = 5;
            mARsTrigger.mPolicyGameIntentReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            boolean z;
                            Bundle extras;
                            ArrayList<String> stringArrayList;
                            Context context2;
                            int i22 = 2;
                            switch (i6) {
                                case 0:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    if (action.equals("android.intent.action.SCREEN_ON")) {
                                        boolean z2 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setScreenOnState(true);
                                        MARsTrigger mARsTrigger2 = mARsTrigger;
                                        PendingIntent pendingIntent =
                                                mARsTrigger2.mMARsFirstTriggerPolicyAlarmIntent;
                                        if (pendingIntent != null) {
                                            mARsTrigger2.mAlarm.cancel(pendingIntent);
                                        }
                                        MARsHandler mARsHandler =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        MARsHandler.MainHandler mainHandler =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler != null) {
                                            mainHandler.removeMessages(1);
                                        }
                                        MARsHandler.MainHandler mainHandler2 =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler2 == null) {
                                            return;
                                        }
                                        mainHandler2.removeMessages(2);
                                        return;
                                    }
                                    if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                                        ActiveTrafficFilter activeTrafficFilter =
                                                ActiveTrafficFilter.ActiveTrafficFilterHolder
                                                        .INSTANCE;
                                        NetworkInfo activeNetworkInfo =
                                                ((ConnectivityManager)
                                                                activeTrafficFilter.mContext
                                                                        .getSystemService(
                                                                                "connectivity"))
                                                        .getActiveNetworkInfo();
                                        activeTrafficFilter.mIsDataConnectionConnected =
                                                activeNetworkInfo != null
                                                        && activeNetworkInfo.isConnected();
                                        if (MARsDebugConfig.DEBUG_FILTER) {
                                            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                                    "MARs:ActiveTrafficFilter",
                                                    new StringBuilder("DataConnection: "),
                                                    activeTrafficFilter.mIsDataConnectionConnected);
                                            return;
                                        }
                                        return;
                                    }
                                    if (action.equals(UiModeManager.ACTION_ENTER_CAR_MODE)) {
                                        boolean z3 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager mARsPolicyManager =
                                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                        synchronized (mARsPolicyManager) {
                                            mARsPolicyManager.mCarModeOn = true;
                                        }
                                        return;
                                    }
                                    if (action.equals(Constants.SIM_STATE_CHANGED)) {
                                        AllowListFilter.AllowListFilterHolder.INSTANCE
                                                .setCarrierAllowList();
                                        return;
                                    }
                                    if (!action.equals("MARS_REQUEST_DB_COMPLETE")) {
                                        if (action.equals(
                                                "com.samsung.android.sm.ACTION_SCPM_MARS_SETTINGS_UPDATED")) {
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE
                                                    .sendGetSCPMPolicyMsgToDBHandler();
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager2 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager2.getClass();
                                    synchronized (MARsPolicyManager.MARsLock) {
                                        if (mARsPolicyManager2.mMARsTargetPackages.mMap.size()
                                                <= 0) {
                                            r3 = false;
                                        }
                                    }
                                    if (r3) {
                                        return;
                                    }
                                    MARsDBHandler.getInstance();
                                    MARsDBHandler mARsDBHandler =
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                    if (mARsDBHandler.mMainHandler == null) {
                                        return;
                                    }
                                    Slog.d("MARsDBHandler", "sendSdhmsDBCompleteMsgToDBHandler");
                                    mARsDBHandler.mMainHandler.post(
                                            mARsDBHandler.mFASDBupdateRunnable);
                                    return;
                                case 1:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals(
                                                            "com.samsung.intent.action.EMERGENCY_STATE_CHANGED")
                                            || intent.getIntExtra("reason", 0) != 5) {
                                        return;
                                    }
                                    Slog.d("MARsTrigger", "disable ultra power saving mode");
                                    if (MARsPolicyManager.MARs_ENABLE) {
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setSubUserIds();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger3 = mARsTrigger;
                                    if (mARsTrigger3.mEmStateReceiverRegistered) {
                                        try {
                                            mARsTrigger3.mContext.unregisterReceiver(
                                                    mARsTrigger3.mEmergencyStateChangedReceiver);
                                            mARsTrigger3.mEmStateReceiverRegistered = false;
                                        } catch (IllegalArgumentException unused) {
                                            Slog.e(
                                                    "MARsTrigger",
                                                    "IllegalArgumentException occurred in"
                                                        + " unregisterEmStateReceiver()");
                                        }
                                    }
                                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.postInit();
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action2 = intent.getAction();
                                    if (!action2.equals("android.intent.action.SCREEN_OFF")) {
                                        if (action2.equals(UiModeManager.ACTION_EXIT_CAR_MODE)) {
                                            boolean z5 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager3 =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager3) {
                                                mARsPolicyManager3.mCarModeOn = false;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z6 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager4 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager4.setScreenOnState(false);
                                    boolean z7 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    if (FreecessController.FreecessControllerHolder.INSTANCE
                                            .mIsDumpstateWorking) {
                                        return;
                                    }
                                    synchronized (mARsPolicyManager4) {
                                        z = mARsPolicyManager4.mCarModeOn;
                                    }
                                    if (z) {
                                        return;
                                    }
                                    if (MARsPolicyManager.isChinaModel) {
                                        MARsHandler.MARsHandlerHolder.INSTANCE
                                                .sendFirstTriggerMsgToMainHandler();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger4 = mARsTrigger;
                                    mARsTrigger4.getClass();
                                    if (mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent == null) {
                                        mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent =
                                                PendingIntent.getBroadcastAsUser(
                                                        mARsTrigger4.mContext,
                                                        0,
                                                        new Intent("FIRST_ALARM_TRIGGER_ACTION")
                                                                .setPackage("android")
                                                                .setFlags(1073741824),
                                                        67108864,
                                                        mARsTrigger4.user);
                                    }
                                    mARsTrigger4.mAlarm.setExact(
                                            1,
                                            System.currentTimeMillis() + 5000,
                                            mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent);
                                    return;
                                case 3:
                                    String action3 = intent.getAction();
                                    if (action3 == null) {
                                        return;
                                    }
                                    boolean equals = action3.equals("FIRST_ALARM_TRIGGER_ACTION");
                                    MARsHandler mARsHandler2 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals) {
                                        Slog.d(
                                                "MARsTrigger",
                                                "mPolicyIntentReceiver broadcast received action : "
                                                        .concat(action3));
                                        boolean z8 = MARsPolicyManager.MARs_ENABLE;
                                        if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .getScreenOnState()) {
                                            boolean z9 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            if (!FreecessController.FreecessControllerHolder
                                                    .INSTANCE
                                                    .mIsDumpstateWorking) {
                                                mARsHandler2.sendFirstTriggerMsgToMainHandler();
                                                mARsHandler2.sendRepeatTriggerMsgToMainHandler();
                                            }
                                        }
                                    }
                                    if (!action3.equals(
                                                    "com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")
                                            || (extras = intent.getExtras()) == null) {
                                        return;
                                    }
                                    String string = extras.getString("POLICY_NAME", "");
                                    mARsTrigger.getClass();
                                    if ("applocker".equalsIgnoreCase(string)) {
                                        i22 = 1;
                                    } else if (!"autorun".equalsIgnoreCase(string)) {
                                        i22 =
                                                "freecess".equalsIgnoreCase(string)
                                                        ? 4
                                                        : "sbikepolicy".equalsIgnoreCase(string)
                                                                ? 6
                                                                : "gamepolicy"
                                                                                .equalsIgnoreCase(
                                                                                        string)
                                                                        ? 9
                                                                        : 0;
                                    }
                                    ArrayList<String> stringArrayList2 =
                                            extras.getStringArrayList("PACKAGE_NAME");
                                    if (i22 == 0 || stringArrayList2 == null) {
                                        return;
                                    }
                                    mARsHandler2.sendRunPolicySpecificPkgMsgToMainHandler(
                                            i22,
                                            mARsTrigger.mContext.getUserId(),
                                            null,
                                            stringArrayList2);
                                    return;
                                case 4:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action4 = intent.getAction();
                                    boolean equals2 =
                                            action4.equals(
                                                    "com.android.server.am.MARS_TRIGGER_SBIKE_MODE_POLICY");
                                    MARsHandler mARsHandler3 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals2) {
                                        Bundle extras2 = intent.getExtras();
                                        if (extras2 == null
                                                || (stringArrayList =
                                                                extras2.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        mARsHandler3.sendRunPolicySpecificPkgMsgToMainHandler(
                                                6,
                                                mARsTrigger.mContext.getUserId(),
                                                MARsTrigger.m199$$Nest$mactionToString(
                                                        mARsTrigger, action4),
                                                stringArrayList);
                                        return;
                                    }
                                    if (action4.equals(
                                            "com.android.server.am.MARS_CANCEL_SBIKE_MODE_POLICY")) {
                                        Bundle extras3 = intent.getExtras();
                                        if (extras3 == null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6, mARsTrigger.mContext.getUserId(), null);
                                            return;
                                        }
                                        ArrayList<String> stringArrayList3 =
                                                extras3.getStringArrayList("PACKAGE_NAME");
                                        if (stringArrayList3 != null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6,
                                                    mARsTrigger.mContext.getUserId(),
                                                    stringArrayList3);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 5:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String stringExtra = intent.getStringExtra("package");
                                    if (stringExtra == null
                                            || "com.samsung.android.game.gos".equals(stringExtra)) {
                                        String action5 = intent.getAction();
                                        boolean equals3 =
                                                action5.equals(
                                                        "com.android.server.am.MARS_TRIGGER_GAME_MODE_POLICY");
                                        MARsHandler mARsHandler4 =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        if (!equals3) {
                                            if (action5.equals(
                                                    "com.android.server.am.MARS_CANCEL_GAME_MODE_POLICY")) {
                                                boolean z10 =
                                                        FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                                FreecessController freecessController =
                                                        FreecessController.FreecessControllerHolder
                                                                .INSTANCE;
                                                if (freecessController.mCalmModeEnabled) {
                                                    FreecessHandler freecessHandler =
                                                            FreecessHandler.FreecessHandlerHolder
                                                                    .INSTANCE;
                                                    FreecessHandler.MainHandler mainHandler3 =
                                                            freecessHandler.mMainHandler;
                                                    if (mainHandler3 != null) {
                                                        Message obtainMessage =
                                                                mainHandler3.obtainMessage(23);
                                                        freecessHandler.mMainHandler.removeMessages(
                                                                23);
                                                        freecessHandler.mMainHandler.sendMessage(
                                                                obtainMessage);
                                                    }
                                                } else {
                                                    boolean z11 = MARsPolicyManager.MARs_ENABLE;
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE
                                                            .getClass();
                                                    if (!MARsPolicyManager.isChinaPolicyEnabled()) {
                                                        mARsHandler4
                                                                .sendCancelPolicyMsgToMainHandler(
                                                                        9, -1, null);
                                                        if (freecessController.mGameModeEnabled) {
                                                            freecessController.mGameModeEnabled =
                                                                    false;
                                                        }
                                                    }
                                                }
                                                Slog.d(
                                                        "MARsTrigger",
                                                        "broadcast received action :"
                                                            + " MARS_CANCEL_GAME_MODE_POLICY");
                                                return;
                                            }
                                            return;
                                        }
                                        Bundle extras4 = intent.getExtras();
                                        if (extras4 != null) {
                                            ArrayList<String> stringArrayList4 =
                                                    extras4.getStringArrayList("survive_app");
                                            LatestProtectedPackageFilter
                                                            .LatestProtectedPackageFilterHolder
                                                            .INSTANCE
                                                            .mProtectedAppSizeForGame =
                                                    extras4.getInt("lru_num");
                                            boolean z12 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            FreecessController freecessController2 =
                                                    FreecessController.FreecessControllerHolder
                                                            .INSTANCE;
                                            if (!freecessController2.mIsFreecessEnable
                                                    || !FreecessController.IS_SUPPORT_CALM_MODE) {
                                                mARsHandler4
                                                        .sendRunPolicySpecificPkgMsgToMainHandler(
                                                                9,
                                                                mARsTrigger.mContext.getUserId(),
                                                                MARsTrigger
                                                                        .m199$$Nest$mactionToString(
                                                                                mARsTrigger,
                                                                                action5),
                                                                stringArrayList4);
                                                if (freecessController2.mGameModeEnabled) {
                                                    return;
                                                }
                                                freecessController2.mGameModeEnabled = true;
                                                return;
                                            }
                                            FreecessHandler freecessHandler2 =
                                                    FreecessHandler.FreecessHandlerHolder.INSTANCE;
                                            FreecessHandler.MainHandler mainHandler4 =
                                                    freecessHandler2.mMainHandler;
                                            if (mainHandler4 == null) {
                                                return;
                                            }
                                            mainHandler4.removeMessages(21);
                                            Bundle bundle = new Bundle();
                                            bundle.putStringArrayList("list", stringArrayList4);
                                            Message obtainMessage2 =
                                                    freecessHandler2.mMainHandler.obtainMessage(21);
                                            obtainMessage2.setData(bundle);
                                            freecessHandler2.mMainHandler.sendMessage(
                                                    obtainMessage2);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 6:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals("android.intent.action.TIME_SET")) {
                                        return;
                                    }
                                    long currentTimeMillis = System.currentTimeMillis();
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    MARsTrigger mARsTrigger5 = mARsTrigger;
                                    long j =
                                            currentTimeMillis
                                                    - ((elapsedRealtime
                                                                    - mARsTrigger5
                                                                            .mLastTimeChangeRealtime)
                                                            + mARsTrigger5
                                                                    .mLastTimeChangeClockTime);
                                    MARsHandler mARsHandler5 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putLong("changedTime", j);
                                    Message obtainMessage3 =
                                            mARsHandler5.mMainHandler.obtainMessage(6);
                                    obtainMessage3.setData(bundle2);
                                    mARsHandler5.mMainHandler.sendMessage(obtainMessage3);
                                    MARsTrigger mARsTrigger6 = mARsTrigger;
                                    mARsTrigger6.mLastTimeChangeClockTime = currentTimeMillis;
                                    mARsTrigger6.mLastTimeChangeRealtime = elapsedRealtime;
                                    boolean z13 = EventRecorder.FEATURE_ENABLE;
                                    EventRecorder eventRecorder =
                                            EventRecorder.EventRecorderHolder.INSTANCE;
                                    eventRecorder.getClass();
                                    if (Math.abs(j) < 30000) {
                                        Slog.d(
                                                "EventRecorder",
                                                "changed time is " + j + ". ignore.");
                                        return;
                                    }
                                    Slog.d("EventRecorder", "disable EventRecorder");
                                    eventRecorder.performWrite(System.currentTimeMillis());
                                    EventRecorder.FEATURE_ENABLE = false;
                                    JobScheduler jobScheduler =
                                            (JobScheduler)
                                                    eventRecorder.mContext.getSystemService(
                                                            "jobscheduler");
                                    if (jobScheduler != null) {
                                        jobScheduler
                                                .forNamespace("MARsEventRecorderNamespace")
                                                .cancel(1);
                                    }
                                    ReentrantLock reentrantLock = EventRecorder.mFileLock;
                                    reentrantLock.lock();
                                    try {
                                        Slog.d(
                                                "EventRecorder",
                                                "file delete result : "
                                                        + EventRecorder.file.delete());
                                        reentrantLock.unlock();
                                        return;
                                    } catch (Throwable th) {
                                        EventRecorder.mFileLock.unlock();
                                        throw th;
                                    }
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action6 = intent.getAction();
                                    Slog.d("MARsTrigger", "broadcast received action : " + action6);
                                    if (!"android.intent.action.USER_SWITCHED".equals(action6)) {
                                        return;
                                    }
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", 0);
                                    Slog.d(
                                            "MARsTrigger",
                                            "mContext.id = " + mARsTrigger.mContext.getUserId());
                                    if (MARsPolicyManager.App_StartUp_History) {
                                        MARsDBManager.MARsDBManagerHolder.INSTANCE
                                                .doUpdateCompHistory(false);
                                    }
                                    MARsPolicyManager mARsPolicyManager5 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager5.addDebugInfoToHistory("DEV", "switchUser");
                                    boolean z14 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController3 =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    boolean z15 = freecessController3.mIsScreenOnFreecessEnabled;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(false);
                                        if (MARsDebugConfig.DEBUG_ENG) {
                                            Slog.d("FreecessController", "removeBgTriggerMsg....");
                                        }
                                        FreecessHandler.FreecessHandlerHolder.INSTANCE
                                                .removeBgTriggerMsg();
                                    }
                                    if (freecessController3.mIsFreecessEnable) {
                                        freecessController3.unFreezePackage("MUM");
                                    }
                                    mARsPolicyManager5.mFirstTimeUpdatePackages = true;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(true);
                                    }
                                    FilterManager filterManager =
                                            FilterManager.FilterManagerHolder.INSTANCE;
                                    int i32 = 1;
                                    while (true) {
                                        FilterFactory filterFactory = filterManager.mFF;
                                        if (i32 >= 34) {
                                            filterFactory.filterHashMap.clear();
                                            filterManager.mFCF.filterHashMap.clear();
                                            mARsPolicyManager5.mCurrentUserId = intExtra;
                                            mARsPolicyManager5.mLastNotiSentTimeForDisabled = 0L;
                                            UserHandle userHandle = new UserHandle(intExtra);
                                            try {
                                                Context context3 = mARsPolicyManager5.mContext;
                                                context2 =
                                                        context3.createPackageContextAsUser(
                                                                context3.getPackageName(),
                                                                0,
                                                                userHandle);
                                            } catch (PackageManager.NameNotFoundException unused2) {
                                                context2 = mARsPolicyManager5.mContext;
                                            }
                                            MARsDBManager mARsDBManager =
                                                    MARsDBManager.MARsDBManagerHolder.INSTANCE;
                                            Context context4 = mARsDBManager.mCurrentContext;
                                            try {
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerSettingsObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerSettingsObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerSettingsObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerFreezeExcludeListObserver) {
                                                    mARsDBManager
                                                            .mContext
                                                            .getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerFreezeExcludeListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerFreezeExcludeListObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerDefaultAllowedListObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerDefaultAllowedListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerDefaultAllowedListObserver =
                                                            false;
                                                }
                                            } catch (IllegalArgumentException unused3) {
                                                Slog.e(
                                                        "MARsDBManager",
                                                        "IllegalArgumentException occurred in"
                                                            + " unregisterContentObserver()");
                                            }
                                            mARsDBManager.mCurrentContext = context2;
                                            mARsDBManager.registerContentObservers(context2);
                                            FilterManager.FilterManagerHolder.INSTANCE.init(
                                                    context2);
                                            MARsHandler.MARsHandlerHolder.INSTANCE
                                                    .sendInitDisabledMsgToMainHandler(intExtra);
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler mARsDBHandler2 =
                                                    MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                            if (mARsDBHandler2.mMainHandler == null) {
                                                return;
                                            }
                                            Bundle m =
                                                    SystemUpdateManagerService$$ExternalSyntheticOutline0
                                                            .m(intExtra, "userId");
                                            Message obtainMessage4 =
                                                    mARsDBHandler2.mMainHandler.obtainMessage(7);
                                            obtainMessage4.setData(m);
                                            mARsDBHandler2.mMainHandler.sendMessage(obtainMessage4);
                                            return;
                                        }
                                        ((IFilter)
                                                        filterFactory.filterHashMap.get(
                                                                Integer.valueOf(i32)))
                                                .deInit();
                                        i32++;
                                    }
                                    break;
                            }
                        }
                    };
            final int i7 = 6;
            mARsTrigger.mTimeIntentReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            boolean z;
                            Bundle extras;
                            ArrayList<String> stringArrayList;
                            Context context2;
                            int i22 = 2;
                            switch (i7) {
                                case 0:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    if (action.equals("android.intent.action.SCREEN_ON")) {
                                        boolean z2 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setScreenOnState(true);
                                        MARsTrigger mARsTrigger2 = mARsTrigger;
                                        PendingIntent pendingIntent =
                                                mARsTrigger2.mMARsFirstTriggerPolicyAlarmIntent;
                                        if (pendingIntent != null) {
                                            mARsTrigger2.mAlarm.cancel(pendingIntent);
                                        }
                                        MARsHandler mARsHandler =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        MARsHandler.MainHandler mainHandler =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler != null) {
                                            mainHandler.removeMessages(1);
                                        }
                                        MARsHandler.MainHandler mainHandler2 =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler2 == null) {
                                            return;
                                        }
                                        mainHandler2.removeMessages(2);
                                        return;
                                    }
                                    if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                                        ActiveTrafficFilter activeTrafficFilter =
                                                ActiveTrafficFilter.ActiveTrafficFilterHolder
                                                        .INSTANCE;
                                        NetworkInfo activeNetworkInfo =
                                                ((ConnectivityManager)
                                                                activeTrafficFilter.mContext
                                                                        .getSystemService(
                                                                                "connectivity"))
                                                        .getActiveNetworkInfo();
                                        activeTrafficFilter.mIsDataConnectionConnected =
                                                activeNetworkInfo != null
                                                        && activeNetworkInfo.isConnected();
                                        if (MARsDebugConfig.DEBUG_FILTER) {
                                            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                                    "MARs:ActiveTrafficFilter",
                                                    new StringBuilder("DataConnection: "),
                                                    activeTrafficFilter.mIsDataConnectionConnected);
                                            return;
                                        }
                                        return;
                                    }
                                    if (action.equals(UiModeManager.ACTION_ENTER_CAR_MODE)) {
                                        boolean z3 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager mARsPolicyManager =
                                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                        synchronized (mARsPolicyManager) {
                                            mARsPolicyManager.mCarModeOn = true;
                                        }
                                        return;
                                    }
                                    if (action.equals(Constants.SIM_STATE_CHANGED)) {
                                        AllowListFilter.AllowListFilterHolder.INSTANCE
                                                .setCarrierAllowList();
                                        return;
                                    }
                                    if (!action.equals("MARS_REQUEST_DB_COMPLETE")) {
                                        if (action.equals(
                                                "com.samsung.android.sm.ACTION_SCPM_MARS_SETTINGS_UPDATED")) {
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE
                                                    .sendGetSCPMPolicyMsgToDBHandler();
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager2 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager2.getClass();
                                    synchronized (MARsPolicyManager.MARsLock) {
                                        if (mARsPolicyManager2.mMARsTargetPackages.mMap.size()
                                                <= 0) {
                                            r3 = false;
                                        }
                                    }
                                    if (r3) {
                                        return;
                                    }
                                    MARsDBHandler.getInstance();
                                    MARsDBHandler mARsDBHandler =
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                    if (mARsDBHandler.mMainHandler == null) {
                                        return;
                                    }
                                    Slog.d("MARsDBHandler", "sendSdhmsDBCompleteMsgToDBHandler");
                                    mARsDBHandler.mMainHandler.post(
                                            mARsDBHandler.mFASDBupdateRunnable);
                                    return;
                                case 1:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals(
                                                            "com.samsung.intent.action.EMERGENCY_STATE_CHANGED")
                                            || intent.getIntExtra("reason", 0) != 5) {
                                        return;
                                    }
                                    Slog.d("MARsTrigger", "disable ultra power saving mode");
                                    if (MARsPolicyManager.MARs_ENABLE) {
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setSubUserIds();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger3 = mARsTrigger;
                                    if (mARsTrigger3.mEmStateReceiverRegistered) {
                                        try {
                                            mARsTrigger3.mContext.unregisterReceiver(
                                                    mARsTrigger3.mEmergencyStateChangedReceiver);
                                            mARsTrigger3.mEmStateReceiverRegistered = false;
                                        } catch (IllegalArgumentException unused) {
                                            Slog.e(
                                                    "MARsTrigger",
                                                    "IllegalArgumentException occurred in"
                                                        + " unregisterEmStateReceiver()");
                                        }
                                    }
                                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.postInit();
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action2 = intent.getAction();
                                    if (!action2.equals("android.intent.action.SCREEN_OFF")) {
                                        if (action2.equals(UiModeManager.ACTION_EXIT_CAR_MODE)) {
                                            boolean z5 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager3 =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager3) {
                                                mARsPolicyManager3.mCarModeOn = false;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z6 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager4 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager4.setScreenOnState(false);
                                    boolean z7 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    if (FreecessController.FreecessControllerHolder.INSTANCE
                                            .mIsDumpstateWorking) {
                                        return;
                                    }
                                    synchronized (mARsPolicyManager4) {
                                        z = mARsPolicyManager4.mCarModeOn;
                                    }
                                    if (z) {
                                        return;
                                    }
                                    if (MARsPolicyManager.isChinaModel) {
                                        MARsHandler.MARsHandlerHolder.INSTANCE
                                                .sendFirstTriggerMsgToMainHandler();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger4 = mARsTrigger;
                                    mARsTrigger4.getClass();
                                    if (mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent == null) {
                                        mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent =
                                                PendingIntent.getBroadcastAsUser(
                                                        mARsTrigger4.mContext,
                                                        0,
                                                        new Intent("FIRST_ALARM_TRIGGER_ACTION")
                                                                .setPackage("android")
                                                                .setFlags(1073741824),
                                                        67108864,
                                                        mARsTrigger4.user);
                                    }
                                    mARsTrigger4.mAlarm.setExact(
                                            1,
                                            System.currentTimeMillis() + 5000,
                                            mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent);
                                    return;
                                case 3:
                                    String action3 = intent.getAction();
                                    if (action3 == null) {
                                        return;
                                    }
                                    boolean equals = action3.equals("FIRST_ALARM_TRIGGER_ACTION");
                                    MARsHandler mARsHandler2 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals) {
                                        Slog.d(
                                                "MARsTrigger",
                                                "mPolicyIntentReceiver broadcast received action : "
                                                        .concat(action3));
                                        boolean z8 = MARsPolicyManager.MARs_ENABLE;
                                        if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .getScreenOnState()) {
                                            boolean z9 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            if (!FreecessController.FreecessControllerHolder
                                                    .INSTANCE
                                                    .mIsDumpstateWorking) {
                                                mARsHandler2.sendFirstTriggerMsgToMainHandler();
                                                mARsHandler2.sendRepeatTriggerMsgToMainHandler();
                                            }
                                        }
                                    }
                                    if (!action3.equals(
                                                    "com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")
                                            || (extras = intent.getExtras()) == null) {
                                        return;
                                    }
                                    String string = extras.getString("POLICY_NAME", "");
                                    mARsTrigger.getClass();
                                    if ("applocker".equalsIgnoreCase(string)) {
                                        i22 = 1;
                                    } else if (!"autorun".equalsIgnoreCase(string)) {
                                        i22 =
                                                "freecess".equalsIgnoreCase(string)
                                                        ? 4
                                                        : "sbikepolicy".equalsIgnoreCase(string)
                                                                ? 6
                                                                : "gamepolicy"
                                                                                .equalsIgnoreCase(
                                                                                        string)
                                                                        ? 9
                                                                        : 0;
                                    }
                                    ArrayList<String> stringArrayList2 =
                                            extras.getStringArrayList("PACKAGE_NAME");
                                    if (i22 == 0 || stringArrayList2 == null) {
                                        return;
                                    }
                                    mARsHandler2.sendRunPolicySpecificPkgMsgToMainHandler(
                                            i22,
                                            mARsTrigger.mContext.getUserId(),
                                            null,
                                            stringArrayList2);
                                    return;
                                case 4:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action4 = intent.getAction();
                                    boolean equals2 =
                                            action4.equals(
                                                    "com.android.server.am.MARS_TRIGGER_SBIKE_MODE_POLICY");
                                    MARsHandler mARsHandler3 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals2) {
                                        Bundle extras2 = intent.getExtras();
                                        if (extras2 == null
                                                || (stringArrayList =
                                                                extras2.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        mARsHandler3.sendRunPolicySpecificPkgMsgToMainHandler(
                                                6,
                                                mARsTrigger.mContext.getUserId(),
                                                MARsTrigger.m199$$Nest$mactionToString(
                                                        mARsTrigger, action4),
                                                stringArrayList);
                                        return;
                                    }
                                    if (action4.equals(
                                            "com.android.server.am.MARS_CANCEL_SBIKE_MODE_POLICY")) {
                                        Bundle extras3 = intent.getExtras();
                                        if (extras3 == null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6, mARsTrigger.mContext.getUserId(), null);
                                            return;
                                        }
                                        ArrayList<String> stringArrayList3 =
                                                extras3.getStringArrayList("PACKAGE_NAME");
                                        if (stringArrayList3 != null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6,
                                                    mARsTrigger.mContext.getUserId(),
                                                    stringArrayList3);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 5:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String stringExtra = intent.getStringExtra("package");
                                    if (stringExtra == null
                                            || "com.samsung.android.game.gos".equals(stringExtra)) {
                                        String action5 = intent.getAction();
                                        boolean equals3 =
                                                action5.equals(
                                                        "com.android.server.am.MARS_TRIGGER_GAME_MODE_POLICY");
                                        MARsHandler mARsHandler4 =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        if (!equals3) {
                                            if (action5.equals(
                                                    "com.android.server.am.MARS_CANCEL_GAME_MODE_POLICY")) {
                                                boolean z10 =
                                                        FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                                FreecessController freecessController =
                                                        FreecessController.FreecessControllerHolder
                                                                .INSTANCE;
                                                if (freecessController.mCalmModeEnabled) {
                                                    FreecessHandler freecessHandler =
                                                            FreecessHandler.FreecessHandlerHolder
                                                                    .INSTANCE;
                                                    FreecessHandler.MainHandler mainHandler3 =
                                                            freecessHandler.mMainHandler;
                                                    if (mainHandler3 != null) {
                                                        Message obtainMessage =
                                                                mainHandler3.obtainMessage(23);
                                                        freecessHandler.mMainHandler.removeMessages(
                                                                23);
                                                        freecessHandler.mMainHandler.sendMessage(
                                                                obtainMessage);
                                                    }
                                                } else {
                                                    boolean z11 = MARsPolicyManager.MARs_ENABLE;
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE
                                                            .getClass();
                                                    if (!MARsPolicyManager.isChinaPolicyEnabled()) {
                                                        mARsHandler4
                                                                .sendCancelPolicyMsgToMainHandler(
                                                                        9, -1, null);
                                                        if (freecessController.mGameModeEnabled) {
                                                            freecessController.mGameModeEnabled =
                                                                    false;
                                                        }
                                                    }
                                                }
                                                Slog.d(
                                                        "MARsTrigger",
                                                        "broadcast received action :"
                                                            + " MARS_CANCEL_GAME_MODE_POLICY");
                                                return;
                                            }
                                            return;
                                        }
                                        Bundle extras4 = intent.getExtras();
                                        if (extras4 != null) {
                                            ArrayList<String> stringArrayList4 =
                                                    extras4.getStringArrayList("survive_app");
                                            LatestProtectedPackageFilter
                                                            .LatestProtectedPackageFilterHolder
                                                            .INSTANCE
                                                            .mProtectedAppSizeForGame =
                                                    extras4.getInt("lru_num");
                                            boolean z12 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            FreecessController freecessController2 =
                                                    FreecessController.FreecessControllerHolder
                                                            .INSTANCE;
                                            if (!freecessController2.mIsFreecessEnable
                                                    || !FreecessController.IS_SUPPORT_CALM_MODE) {
                                                mARsHandler4
                                                        .sendRunPolicySpecificPkgMsgToMainHandler(
                                                                9,
                                                                mARsTrigger.mContext.getUserId(),
                                                                MARsTrigger
                                                                        .m199$$Nest$mactionToString(
                                                                                mARsTrigger,
                                                                                action5),
                                                                stringArrayList4);
                                                if (freecessController2.mGameModeEnabled) {
                                                    return;
                                                }
                                                freecessController2.mGameModeEnabled = true;
                                                return;
                                            }
                                            FreecessHandler freecessHandler2 =
                                                    FreecessHandler.FreecessHandlerHolder.INSTANCE;
                                            FreecessHandler.MainHandler mainHandler4 =
                                                    freecessHandler2.mMainHandler;
                                            if (mainHandler4 == null) {
                                                return;
                                            }
                                            mainHandler4.removeMessages(21);
                                            Bundle bundle = new Bundle();
                                            bundle.putStringArrayList("list", stringArrayList4);
                                            Message obtainMessage2 =
                                                    freecessHandler2.mMainHandler.obtainMessage(21);
                                            obtainMessage2.setData(bundle);
                                            freecessHandler2.mMainHandler.sendMessage(
                                                    obtainMessage2);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 6:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals("android.intent.action.TIME_SET")) {
                                        return;
                                    }
                                    long currentTimeMillis = System.currentTimeMillis();
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    MARsTrigger mARsTrigger5 = mARsTrigger;
                                    long j =
                                            currentTimeMillis
                                                    - ((elapsedRealtime
                                                                    - mARsTrigger5
                                                                            .mLastTimeChangeRealtime)
                                                            + mARsTrigger5
                                                                    .mLastTimeChangeClockTime);
                                    MARsHandler mARsHandler5 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putLong("changedTime", j);
                                    Message obtainMessage3 =
                                            mARsHandler5.mMainHandler.obtainMessage(6);
                                    obtainMessage3.setData(bundle2);
                                    mARsHandler5.mMainHandler.sendMessage(obtainMessage3);
                                    MARsTrigger mARsTrigger6 = mARsTrigger;
                                    mARsTrigger6.mLastTimeChangeClockTime = currentTimeMillis;
                                    mARsTrigger6.mLastTimeChangeRealtime = elapsedRealtime;
                                    boolean z13 = EventRecorder.FEATURE_ENABLE;
                                    EventRecorder eventRecorder =
                                            EventRecorder.EventRecorderHolder.INSTANCE;
                                    eventRecorder.getClass();
                                    if (Math.abs(j) < 30000) {
                                        Slog.d(
                                                "EventRecorder",
                                                "changed time is " + j + ". ignore.");
                                        return;
                                    }
                                    Slog.d("EventRecorder", "disable EventRecorder");
                                    eventRecorder.performWrite(System.currentTimeMillis());
                                    EventRecorder.FEATURE_ENABLE = false;
                                    JobScheduler jobScheduler =
                                            (JobScheduler)
                                                    eventRecorder.mContext.getSystemService(
                                                            "jobscheduler");
                                    if (jobScheduler != null) {
                                        jobScheduler
                                                .forNamespace("MARsEventRecorderNamespace")
                                                .cancel(1);
                                    }
                                    ReentrantLock reentrantLock = EventRecorder.mFileLock;
                                    reentrantLock.lock();
                                    try {
                                        Slog.d(
                                                "EventRecorder",
                                                "file delete result : "
                                                        + EventRecorder.file.delete());
                                        reentrantLock.unlock();
                                        return;
                                    } catch (Throwable th) {
                                        EventRecorder.mFileLock.unlock();
                                        throw th;
                                    }
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action6 = intent.getAction();
                                    Slog.d("MARsTrigger", "broadcast received action : " + action6);
                                    if (!"android.intent.action.USER_SWITCHED".equals(action6)) {
                                        return;
                                    }
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", 0);
                                    Slog.d(
                                            "MARsTrigger",
                                            "mContext.id = " + mARsTrigger.mContext.getUserId());
                                    if (MARsPolicyManager.App_StartUp_History) {
                                        MARsDBManager.MARsDBManagerHolder.INSTANCE
                                                .doUpdateCompHistory(false);
                                    }
                                    MARsPolicyManager mARsPolicyManager5 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager5.addDebugInfoToHistory("DEV", "switchUser");
                                    boolean z14 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController3 =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    boolean z15 = freecessController3.mIsScreenOnFreecessEnabled;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(false);
                                        if (MARsDebugConfig.DEBUG_ENG) {
                                            Slog.d("FreecessController", "removeBgTriggerMsg....");
                                        }
                                        FreecessHandler.FreecessHandlerHolder.INSTANCE
                                                .removeBgTriggerMsg();
                                    }
                                    if (freecessController3.mIsFreecessEnable) {
                                        freecessController3.unFreezePackage("MUM");
                                    }
                                    mARsPolicyManager5.mFirstTimeUpdatePackages = true;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(true);
                                    }
                                    FilterManager filterManager =
                                            FilterManager.FilterManagerHolder.INSTANCE;
                                    int i32 = 1;
                                    while (true) {
                                        FilterFactory filterFactory = filterManager.mFF;
                                        if (i32 >= 34) {
                                            filterFactory.filterHashMap.clear();
                                            filterManager.mFCF.filterHashMap.clear();
                                            mARsPolicyManager5.mCurrentUserId = intExtra;
                                            mARsPolicyManager5.mLastNotiSentTimeForDisabled = 0L;
                                            UserHandle userHandle = new UserHandle(intExtra);
                                            try {
                                                Context context3 = mARsPolicyManager5.mContext;
                                                context2 =
                                                        context3.createPackageContextAsUser(
                                                                context3.getPackageName(),
                                                                0,
                                                                userHandle);
                                            } catch (PackageManager.NameNotFoundException unused2) {
                                                context2 = mARsPolicyManager5.mContext;
                                            }
                                            MARsDBManager mARsDBManager =
                                                    MARsDBManager.MARsDBManagerHolder.INSTANCE;
                                            Context context4 = mARsDBManager.mCurrentContext;
                                            try {
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerSettingsObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerSettingsObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerSettingsObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerFreezeExcludeListObserver) {
                                                    mARsDBManager
                                                            .mContext
                                                            .getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerFreezeExcludeListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerFreezeExcludeListObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerDefaultAllowedListObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerDefaultAllowedListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerDefaultAllowedListObserver =
                                                            false;
                                                }
                                            } catch (IllegalArgumentException unused3) {
                                                Slog.e(
                                                        "MARsDBManager",
                                                        "IllegalArgumentException occurred in"
                                                            + " unregisterContentObserver()");
                                            }
                                            mARsDBManager.mCurrentContext = context2;
                                            mARsDBManager.registerContentObservers(context2);
                                            FilterManager.FilterManagerHolder.INSTANCE.init(
                                                    context2);
                                            MARsHandler.MARsHandlerHolder.INSTANCE
                                                    .sendInitDisabledMsgToMainHandler(intExtra);
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler mARsDBHandler2 =
                                                    MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                            if (mARsDBHandler2.mMainHandler == null) {
                                                return;
                                            }
                                            Bundle m =
                                                    SystemUpdateManagerService$$ExternalSyntheticOutline0
                                                            .m(intExtra, "userId");
                                            Message obtainMessage4 =
                                                    mARsDBHandler2.mMainHandler.obtainMessage(7);
                                            obtainMessage4.setData(m);
                                            mARsDBHandler2.mMainHandler.sendMessage(obtainMessage4);
                                            return;
                                        }
                                        ((IFilter)
                                                        filterFactory.filterHashMap.get(
                                                                Integer.valueOf(i32)))
                                                .deInit();
                                        i32++;
                                    }
                                    break;
                            }
                        }
                    };
            final int i8 = 3;
            mARsTrigger.mUserActionReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.5
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            boolean z;
                            boolean z2;
                            UserInfo userInfo;
                            int i62;
                            UserInfo userInfo2;
                            UserInfo userInfo3;
                            boolean z3 = false;
                            switch (i8) {
                                case 0:
                                    ArrayList<Integer> integerArrayListExtra =
                                            intent.getIntegerArrayListExtra("pid");
                                    MARsHandler mARsHandler =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    MARsHandler.MainHandler mainHandler = mARsHandler.mMainHandler;
                                    if (mainHandler != null && integerArrayListExtra != null) {
                                        Message obtainMessage = mainHandler.obtainMessage(17);
                                        Bundle bundle = new Bundle();
                                        bundle.putIntegerArrayList(
                                                "pidList", integerArrayListExtra);
                                        obtainMessage.setData(bundle);
                                        mARsHandler.mMainHandler.sendMessage(obtainMessage);
                                    }
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            "broadcast received action TCPU: ",
                                            intent.getAction(),
                                            "MARsTrigger");
                                    return;
                                case 1:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    Slog.d(
                                            "MARsTrigger",
                                            "mAppStartUpIntentReceiver onReceive : " + action);
                                    if (action.equals("android.intent.action.ACTION_SHUTDOWN")
                                            || action.equals("android.intent.action.REBOOT")) {
                                        MARsBigData mARsBigData =
                                                MARsBigData.MARsBigDataHolder.INSTANCE;
                                        mARsBigData.getClass();
                                        try {
                                            mARsBigData.updateBigdataInfo();
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                            mARsBigData.PLEVdata =
                                                    new MARsBigData.UsageInfo().toString();
                                        }
                                        String str = mARsBigData.PLEVdata;
                                        if (str != null) {
                                            mARsBigData.sendBigData("PLEV", str);
                                        }
                                        boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .updateResetTime();
                                        if (MARsDebugConfig.DEBUG_MID
                                                || MARsDebugConfig.DEBUG_HIGH) {
                                            final MARsHistoryLog.SaveLogThread saveLogThread =
                                                    MARsHistoryLog.MARsHistoryLogHolder.INSTANCE
                                                            .mSaveLogThread;
                                            saveLogThread.getClass();
                                            Thread thread =
                                                    new Thread(
                                                            new Runnable() { // from class:
                                                                             // com.android.server.am.mars.MARsHistoryLog$SaveLogThread$$ExternalSyntheticLambda0
                                                                @Override // java.lang.Runnable
                                                                public final void run() {
                                                                    MARsHistoryLog.SaveLogThread
                                                                            .this
                                                                            .this$0.saveLogToFile(
                                                                            true, true);
                                                                }
                                                            });
                                            thread.setPriority(1);
                                            thread.start();
                                        }
                                        if (MARsPolicyManager.App_StartUp_History) {
                                            MARsDBManager.MARsDBManagerHolder.INSTANCE
                                                    .doUpdateCompHistory(true);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action2 = intent.getAction();
                                    Bundle extras = intent.getExtras();
                                    if (action2.equals("MARS_REQUEST_PKG_INFO")) {
                                        if (!MARsPolicyManager.MARs_ENABLE) {
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                    .postInit();
                                            return;
                                        }
                                        if (extras != null) {
                                            String string = extras.getString("MARS_EXTRA", "");
                                            boolean equals = string.equals("create");
                                            z = string.equals("update");
                                            z2 = string.equals("init");
                                            z3 = equals;
                                        } else {
                                            z = false;
                                            z2 = false;
                                        }
                                        MARsDBHandler.getInstance();
                                        MARsDBHandler mARsDBHandler =
                                                MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                        MARsDBHandler.MainHandler mainHandler2 =
                                                mARsDBHandler.mMainHandler;
                                        if (mainHandler2 == null) {
                                            return;
                                        }
                                        mainHandler2.removeMessages(6);
                                        Message obtainMessage2 =
                                                mARsDBHandler.mMainHandler.obtainMessage(6);
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putBoolean("onCreate", z3);
                                        bundle2.putBoolean("onUpgrade", z);
                                        bundle2.putBoolean("onInit", z2);
                                        obtainMessage2.setData(bundle2);
                                        mARsDBHandler.mMainHandler.sendMessage(obtainMessage2);
                                        return;
                                    }
                                    return;
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action3 = intent.getAction();
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", -1);
                                    Slog.d(
                                            "MARsTrigger",
                                            "mUserActionReceiver : "
                                                    + action3
                                                    + " userId = "
                                                    + intExtra);
                                    if ("android.intent.action.USER_STOPPED".equals(action3)) {
                                        if (intExtra >= 95 && intExtra <= 99) {
                                            boolean z5 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager) {
                                                mARsPolicyManager.mDualAppEnabled = false;
                                            }
                                            return;
                                        }
                                        UserManager userManager =
                                                (UserManager) context.getSystemService("user");
                                        if (userManager == null
                                                || (userInfo3 = userManager.getUserInfo(intExtra))
                                                        == null
                                                || !userInfo3.isManagedProfile()) {
                                            return;
                                        }
                                        boolean z6 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setManagedProfileEnabled(intExtra, false);
                                        return;
                                    }
                                    if ("android.intent.action.USER_STARTED".equals(action3)) {
                                        if (intExtra >= 95 && intExtra <= 99) {
                                            boolean z7 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager2 =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager2) {
                                                mARsPolicyManager2.mDualAppEnabled = true;
                                            }
                                            return;
                                        }
                                        UserManager userManager2 =
                                                (UserManager) context.getSystemService("user");
                                        if (userManager2 == null
                                                || (userInfo2 = userManager2.getUserInfo(intExtra))
                                                        == null
                                                || !userInfo2.isManagedProfile()) {
                                            return;
                                        }
                                        boolean z8 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setManagedProfileEnabled(intExtra, true);
                                        return;
                                    }
                                    if ("android.intent.action.USER_ADDED".equals(action3)) {
                                        if (intExtra < 95 || intExtra > 99) {
                                            UserManager userManager3 =
                                                    (UserManager) context.getSystemService("user");
                                            if (userManager3 == null
                                                    || (userInfo =
                                                                    userManager3.getUserInfo(
                                                                            intExtra))
                                                            == null
                                                    || !userInfo.isManagedProfile()) {
                                                return;
                                            }
                                            boolean z9 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                    .setManagedProfileEnabled(intExtra, true);
                                            return;
                                        }
                                        boolean z10 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager mARsPolicyManager3 =
                                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                        synchronized (mARsPolicyManager3) {
                                            mARsPolicyManager3.mDualAppEnabled = true;
                                        }
                                        synchronized (mARsPolicyManager3) {
                                            i62 = mARsPolicyManager3.mDualAppUserId;
                                        }
                                        if (intExtra != i62) {
                                            synchronized (mARsPolicyManager3) {
                                                mARsPolicyManager3.mDualAppUserId = intExtra;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                            }
                        }
                    };
            final int i9 = 7;
            mARsTrigger.mUserIntentReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            boolean z;
                            Bundle extras;
                            ArrayList<String> stringArrayList;
                            Context context2;
                            int i22 = 2;
                            switch (i9) {
                                case 0:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    if (action.equals("android.intent.action.SCREEN_ON")) {
                                        boolean z2 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setScreenOnState(true);
                                        MARsTrigger mARsTrigger2 = mARsTrigger;
                                        PendingIntent pendingIntent =
                                                mARsTrigger2.mMARsFirstTriggerPolicyAlarmIntent;
                                        if (pendingIntent != null) {
                                            mARsTrigger2.mAlarm.cancel(pendingIntent);
                                        }
                                        MARsHandler mARsHandler =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        MARsHandler.MainHandler mainHandler =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler != null) {
                                            mainHandler.removeMessages(1);
                                        }
                                        MARsHandler.MainHandler mainHandler2 =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler2 == null) {
                                            return;
                                        }
                                        mainHandler2.removeMessages(2);
                                        return;
                                    }
                                    if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                                        ActiveTrafficFilter activeTrafficFilter =
                                                ActiveTrafficFilter.ActiveTrafficFilterHolder
                                                        .INSTANCE;
                                        NetworkInfo activeNetworkInfo =
                                                ((ConnectivityManager)
                                                                activeTrafficFilter.mContext
                                                                        .getSystemService(
                                                                                "connectivity"))
                                                        .getActiveNetworkInfo();
                                        activeTrafficFilter.mIsDataConnectionConnected =
                                                activeNetworkInfo != null
                                                        && activeNetworkInfo.isConnected();
                                        if (MARsDebugConfig.DEBUG_FILTER) {
                                            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                                    "MARs:ActiveTrafficFilter",
                                                    new StringBuilder("DataConnection: "),
                                                    activeTrafficFilter.mIsDataConnectionConnected);
                                            return;
                                        }
                                        return;
                                    }
                                    if (action.equals(UiModeManager.ACTION_ENTER_CAR_MODE)) {
                                        boolean z3 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager mARsPolicyManager =
                                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                        synchronized (mARsPolicyManager) {
                                            mARsPolicyManager.mCarModeOn = true;
                                        }
                                        return;
                                    }
                                    if (action.equals(Constants.SIM_STATE_CHANGED)) {
                                        AllowListFilter.AllowListFilterHolder.INSTANCE
                                                .setCarrierAllowList();
                                        return;
                                    }
                                    if (!action.equals("MARS_REQUEST_DB_COMPLETE")) {
                                        if (action.equals(
                                                "com.samsung.android.sm.ACTION_SCPM_MARS_SETTINGS_UPDATED")) {
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE
                                                    .sendGetSCPMPolicyMsgToDBHandler();
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager2 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager2.getClass();
                                    synchronized (MARsPolicyManager.MARsLock) {
                                        if (mARsPolicyManager2.mMARsTargetPackages.mMap.size()
                                                <= 0) {
                                            r3 = false;
                                        }
                                    }
                                    if (r3) {
                                        return;
                                    }
                                    MARsDBHandler.getInstance();
                                    MARsDBHandler mARsDBHandler =
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                    if (mARsDBHandler.mMainHandler == null) {
                                        return;
                                    }
                                    Slog.d("MARsDBHandler", "sendSdhmsDBCompleteMsgToDBHandler");
                                    mARsDBHandler.mMainHandler.post(
                                            mARsDBHandler.mFASDBupdateRunnable);
                                    return;
                                case 1:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals(
                                                            "com.samsung.intent.action.EMERGENCY_STATE_CHANGED")
                                            || intent.getIntExtra("reason", 0) != 5) {
                                        return;
                                    }
                                    Slog.d("MARsTrigger", "disable ultra power saving mode");
                                    if (MARsPolicyManager.MARs_ENABLE) {
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setSubUserIds();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger3 = mARsTrigger;
                                    if (mARsTrigger3.mEmStateReceiverRegistered) {
                                        try {
                                            mARsTrigger3.mContext.unregisterReceiver(
                                                    mARsTrigger3.mEmergencyStateChangedReceiver);
                                            mARsTrigger3.mEmStateReceiverRegistered = false;
                                        } catch (IllegalArgumentException unused) {
                                            Slog.e(
                                                    "MARsTrigger",
                                                    "IllegalArgumentException occurred in"
                                                        + " unregisterEmStateReceiver()");
                                        }
                                    }
                                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.postInit();
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action2 = intent.getAction();
                                    if (!action2.equals("android.intent.action.SCREEN_OFF")) {
                                        if (action2.equals(UiModeManager.ACTION_EXIT_CAR_MODE)) {
                                            boolean z5 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager3 =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager3) {
                                                mARsPolicyManager3.mCarModeOn = false;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z6 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager4 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager4.setScreenOnState(false);
                                    boolean z7 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    if (FreecessController.FreecessControllerHolder.INSTANCE
                                            .mIsDumpstateWorking) {
                                        return;
                                    }
                                    synchronized (mARsPolicyManager4) {
                                        z = mARsPolicyManager4.mCarModeOn;
                                    }
                                    if (z) {
                                        return;
                                    }
                                    if (MARsPolicyManager.isChinaModel) {
                                        MARsHandler.MARsHandlerHolder.INSTANCE
                                                .sendFirstTriggerMsgToMainHandler();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger4 = mARsTrigger;
                                    mARsTrigger4.getClass();
                                    if (mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent == null) {
                                        mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent =
                                                PendingIntent.getBroadcastAsUser(
                                                        mARsTrigger4.mContext,
                                                        0,
                                                        new Intent("FIRST_ALARM_TRIGGER_ACTION")
                                                                .setPackage("android")
                                                                .setFlags(1073741824),
                                                        67108864,
                                                        mARsTrigger4.user);
                                    }
                                    mARsTrigger4.mAlarm.setExact(
                                            1,
                                            System.currentTimeMillis() + 5000,
                                            mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent);
                                    return;
                                case 3:
                                    String action3 = intent.getAction();
                                    if (action3 == null) {
                                        return;
                                    }
                                    boolean equals = action3.equals("FIRST_ALARM_TRIGGER_ACTION");
                                    MARsHandler mARsHandler2 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals) {
                                        Slog.d(
                                                "MARsTrigger",
                                                "mPolicyIntentReceiver broadcast received action : "
                                                        .concat(action3));
                                        boolean z8 = MARsPolicyManager.MARs_ENABLE;
                                        if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .getScreenOnState()) {
                                            boolean z9 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            if (!FreecessController.FreecessControllerHolder
                                                    .INSTANCE
                                                    .mIsDumpstateWorking) {
                                                mARsHandler2.sendFirstTriggerMsgToMainHandler();
                                                mARsHandler2.sendRepeatTriggerMsgToMainHandler();
                                            }
                                        }
                                    }
                                    if (!action3.equals(
                                                    "com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")
                                            || (extras = intent.getExtras()) == null) {
                                        return;
                                    }
                                    String string = extras.getString("POLICY_NAME", "");
                                    mARsTrigger.getClass();
                                    if ("applocker".equalsIgnoreCase(string)) {
                                        i22 = 1;
                                    } else if (!"autorun".equalsIgnoreCase(string)) {
                                        i22 =
                                                "freecess".equalsIgnoreCase(string)
                                                        ? 4
                                                        : "sbikepolicy".equalsIgnoreCase(string)
                                                                ? 6
                                                                : "gamepolicy"
                                                                                .equalsIgnoreCase(
                                                                                        string)
                                                                        ? 9
                                                                        : 0;
                                    }
                                    ArrayList<String> stringArrayList2 =
                                            extras.getStringArrayList("PACKAGE_NAME");
                                    if (i22 == 0 || stringArrayList2 == null) {
                                        return;
                                    }
                                    mARsHandler2.sendRunPolicySpecificPkgMsgToMainHandler(
                                            i22,
                                            mARsTrigger.mContext.getUserId(),
                                            null,
                                            stringArrayList2);
                                    return;
                                case 4:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action4 = intent.getAction();
                                    boolean equals2 =
                                            action4.equals(
                                                    "com.android.server.am.MARS_TRIGGER_SBIKE_MODE_POLICY");
                                    MARsHandler mARsHandler3 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals2) {
                                        Bundle extras2 = intent.getExtras();
                                        if (extras2 == null
                                                || (stringArrayList =
                                                                extras2.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        mARsHandler3.sendRunPolicySpecificPkgMsgToMainHandler(
                                                6,
                                                mARsTrigger.mContext.getUserId(),
                                                MARsTrigger.m199$$Nest$mactionToString(
                                                        mARsTrigger, action4),
                                                stringArrayList);
                                        return;
                                    }
                                    if (action4.equals(
                                            "com.android.server.am.MARS_CANCEL_SBIKE_MODE_POLICY")) {
                                        Bundle extras3 = intent.getExtras();
                                        if (extras3 == null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6, mARsTrigger.mContext.getUserId(), null);
                                            return;
                                        }
                                        ArrayList<String> stringArrayList3 =
                                                extras3.getStringArrayList("PACKAGE_NAME");
                                        if (stringArrayList3 != null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6,
                                                    mARsTrigger.mContext.getUserId(),
                                                    stringArrayList3);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 5:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String stringExtra = intent.getStringExtra("package");
                                    if (stringExtra == null
                                            || "com.samsung.android.game.gos".equals(stringExtra)) {
                                        String action5 = intent.getAction();
                                        boolean equals3 =
                                                action5.equals(
                                                        "com.android.server.am.MARS_TRIGGER_GAME_MODE_POLICY");
                                        MARsHandler mARsHandler4 =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        if (!equals3) {
                                            if (action5.equals(
                                                    "com.android.server.am.MARS_CANCEL_GAME_MODE_POLICY")) {
                                                boolean z10 =
                                                        FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                                FreecessController freecessController =
                                                        FreecessController.FreecessControllerHolder
                                                                .INSTANCE;
                                                if (freecessController.mCalmModeEnabled) {
                                                    FreecessHandler freecessHandler =
                                                            FreecessHandler.FreecessHandlerHolder
                                                                    .INSTANCE;
                                                    FreecessHandler.MainHandler mainHandler3 =
                                                            freecessHandler.mMainHandler;
                                                    if (mainHandler3 != null) {
                                                        Message obtainMessage =
                                                                mainHandler3.obtainMessage(23);
                                                        freecessHandler.mMainHandler.removeMessages(
                                                                23);
                                                        freecessHandler.mMainHandler.sendMessage(
                                                                obtainMessage);
                                                    }
                                                } else {
                                                    boolean z11 = MARsPolicyManager.MARs_ENABLE;
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE
                                                            .getClass();
                                                    if (!MARsPolicyManager.isChinaPolicyEnabled()) {
                                                        mARsHandler4
                                                                .sendCancelPolicyMsgToMainHandler(
                                                                        9, -1, null);
                                                        if (freecessController.mGameModeEnabled) {
                                                            freecessController.mGameModeEnabled =
                                                                    false;
                                                        }
                                                    }
                                                }
                                                Slog.d(
                                                        "MARsTrigger",
                                                        "broadcast received action :"
                                                            + " MARS_CANCEL_GAME_MODE_POLICY");
                                                return;
                                            }
                                            return;
                                        }
                                        Bundle extras4 = intent.getExtras();
                                        if (extras4 != null) {
                                            ArrayList<String> stringArrayList4 =
                                                    extras4.getStringArrayList("survive_app");
                                            LatestProtectedPackageFilter
                                                            .LatestProtectedPackageFilterHolder
                                                            .INSTANCE
                                                            .mProtectedAppSizeForGame =
                                                    extras4.getInt("lru_num");
                                            boolean z12 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            FreecessController freecessController2 =
                                                    FreecessController.FreecessControllerHolder
                                                            .INSTANCE;
                                            if (!freecessController2.mIsFreecessEnable
                                                    || !FreecessController.IS_SUPPORT_CALM_MODE) {
                                                mARsHandler4
                                                        .sendRunPolicySpecificPkgMsgToMainHandler(
                                                                9,
                                                                mARsTrigger.mContext.getUserId(),
                                                                MARsTrigger
                                                                        .m199$$Nest$mactionToString(
                                                                                mARsTrigger,
                                                                                action5),
                                                                stringArrayList4);
                                                if (freecessController2.mGameModeEnabled) {
                                                    return;
                                                }
                                                freecessController2.mGameModeEnabled = true;
                                                return;
                                            }
                                            FreecessHandler freecessHandler2 =
                                                    FreecessHandler.FreecessHandlerHolder.INSTANCE;
                                            FreecessHandler.MainHandler mainHandler4 =
                                                    freecessHandler2.mMainHandler;
                                            if (mainHandler4 == null) {
                                                return;
                                            }
                                            mainHandler4.removeMessages(21);
                                            Bundle bundle = new Bundle();
                                            bundle.putStringArrayList("list", stringArrayList4);
                                            Message obtainMessage2 =
                                                    freecessHandler2.mMainHandler.obtainMessage(21);
                                            obtainMessage2.setData(bundle);
                                            freecessHandler2.mMainHandler.sendMessage(
                                                    obtainMessage2);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 6:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals("android.intent.action.TIME_SET")) {
                                        return;
                                    }
                                    long currentTimeMillis = System.currentTimeMillis();
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    MARsTrigger mARsTrigger5 = mARsTrigger;
                                    long j =
                                            currentTimeMillis
                                                    - ((elapsedRealtime
                                                                    - mARsTrigger5
                                                                            .mLastTimeChangeRealtime)
                                                            + mARsTrigger5
                                                                    .mLastTimeChangeClockTime);
                                    MARsHandler mARsHandler5 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putLong("changedTime", j);
                                    Message obtainMessage3 =
                                            mARsHandler5.mMainHandler.obtainMessage(6);
                                    obtainMessage3.setData(bundle2);
                                    mARsHandler5.mMainHandler.sendMessage(obtainMessage3);
                                    MARsTrigger mARsTrigger6 = mARsTrigger;
                                    mARsTrigger6.mLastTimeChangeClockTime = currentTimeMillis;
                                    mARsTrigger6.mLastTimeChangeRealtime = elapsedRealtime;
                                    boolean z13 = EventRecorder.FEATURE_ENABLE;
                                    EventRecorder eventRecorder =
                                            EventRecorder.EventRecorderHolder.INSTANCE;
                                    eventRecorder.getClass();
                                    if (Math.abs(j) < 30000) {
                                        Slog.d(
                                                "EventRecorder",
                                                "changed time is " + j + ". ignore.");
                                        return;
                                    }
                                    Slog.d("EventRecorder", "disable EventRecorder");
                                    eventRecorder.performWrite(System.currentTimeMillis());
                                    EventRecorder.FEATURE_ENABLE = false;
                                    JobScheduler jobScheduler =
                                            (JobScheduler)
                                                    eventRecorder.mContext.getSystemService(
                                                            "jobscheduler");
                                    if (jobScheduler != null) {
                                        jobScheduler
                                                .forNamespace("MARsEventRecorderNamespace")
                                                .cancel(1);
                                    }
                                    ReentrantLock reentrantLock = EventRecorder.mFileLock;
                                    reentrantLock.lock();
                                    try {
                                        Slog.d(
                                                "EventRecorder",
                                                "file delete result : "
                                                        + EventRecorder.file.delete());
                                        reentrantLock.unlock();
                                        return;
                                    } catch (Throwable th) {
                                        EventRecorder.mFileLock.unlock();
                                        throw th;
                                    }
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action6 = intent.getAction();
                                    Slog.d("MARsTrigger", "broadcast received action : " + action6);
                                    if (!"android.intent.action.USER_SWITCHED".equals(action6)) {
                                        return;
                                    }
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", 0);
                                    Slog.d(
                                            "MARsTrigger",
                                            "mContext.id = " + mARsTrigger.mContext.getUserId());
                                    if (MARsPolicyManager.App_StartUp_History) {
                                        MARsDBManager.MARsDBManagerHolder.INSTANCE
                                                .doUpdateCompHistory(false);
                                    }
                                    MARsPolicyManager mARsPolicyManager5 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager5.addDebugInfoToHistory("DEV", "switchUser");
                                    boolean z14 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController3 =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    boolean z15 = freecessController3.mIsScreenOnFreecessEnabled;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(false);
                                        if (MARsDebugConfig.DEBUG_ENG) {
                                            Slog.d("FreecessController", "removeBgTriggerMsg....");
                                        }
                                        FreecessHandler.FreecessHandlerHolder.INSTANCE
                                                .removeBgTriggerMsg();
                                    }
                                    if (freecessController3.mIsFreecessEnable) {
                                        freecessController3.unFreezePackage("MUM");
                                    }
                                    mARsPolicyManager5.mFirstTimeUpdatePackages = true;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(true);
                                    }
                                    FilterManager filterManager =
                                            FilterManager.FilterManagerHolder.INSTANCE;
                                    int i32 = 1;
                                    while (true) {
                                        FilterFactory filterFactory = filterManager.mFF;
                                        if (i32 >= 34) {
                                            filterFactory.filterHashMap.clear();
                                            filterManager.mFCF.filterHashMap.clear();
                                            mARsPolicyManager5.mCurrentUserId = intExtra;
                                            mARsPolicyManager5.mLastNotiSentTimeForDisabled = 0L;
                                            UserHandle userHandle = new UserHandle(intExtra);
                                            try {
                                                Context context3 = mARsPolicyManager5.mContext;
                                                context2 =
                                                        context3.createPackageContextAsUser(
                                                                context3.getPackageName(),
                                                                0,
                                                                userHandle);
                                            } catch (PackageManager.NameNotFoundException unused2) {
                                                context2 = mARsPolicyManager5.mContext;
                                            }
                                            MARsDBManager mARsDBManager =
                                                    MARsDBManager.MARsDBManagerHolder.INSTANCE;
                                            Context context4 = mARsDBManager.mCurrentContext;
                                            try {
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerSettingsObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerSettingsObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerSettingsObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerFreezeExcludeListObserver) {
                                                    mARsDBManager
                                                            .mContext
                                                            .getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerFreezeExcludeListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerFreezeExcludeListObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerDefaultAllowedListObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerDefaultAllowedListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerDefaultAllowedListObserver =
                                                            false;
                                                }
                                            } catch (IllegalArgumentException unused3) {
                                                Slog.e(
                                                        "MARsDBManager",
                                                        "IllegalArgumentException occurred in"
                                                            + " unregisterContentObserver()");
                                            }
                                            mARsDBManager.mCurrentContext = context2;
                                            mARsDBManager.registerContentObservers(context2);
                                            FilterManager.FilterManagerHolder.INSTANCE.init(
                                                    context2);
                                            MARsHandler.MARsHandlerHolder.INSTANCE
                                                    .sendInitDisabledMsgToMainHandler(intExtra);
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler mARsDBHandler2 =
                                                    MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                            if (mARsDBHandler2.mMainHandler == null) {
                                                return;
                                            }
                                            Bundle m =
                                                    SystemUpdateManagerService$$ExternalSyntheticOutline0
                                                            .m(intExtra, "userId");
                                            Message obtainMessage4 =
                                                    mARsDBHandler2.mMainHandler.obtainMessage(7);
                                            obtainMessage4.setData(m);
                                            mARsDBHandler2.mMainHandler.sendMessage(obtainMessage4);
                                            return;
                                        }
                                        ((IFilter)
                                                        filterFactory.filterHashMap.get(
                                                                Integer.valueOf(i32)))
                                                .deInit();
                                        i32++;
                                    }
                                    break;
                            }
                        }
                    };
            final int i10 = 1;
            mARsTrigger.mAppStartUpIntentReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.5
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            boolean z;
                            boolean z2;
                            UserInfo userInfo;
                            int i62;
                            UserInfo userInfo2;
                            UserInfo userInfo3;
                            boolean z3 = false;
                            switch (i10) {
                                case 0:
                                    ArrayList<Integer> integerArrayListExtra =
                                            intent.getIntegerArrayListExtra("pid");
                                    MARsHandler mARsHandler =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    MARsHandler.MainHandler mainHandler = mARsHandler.mMainHandler;
                                    if (mainHandler != null && integerArrayListExtra != null) {
                                        Message obtainMessage = mainHandler.obtainMessage(17);
                                        Bundle bundle = new Bundle();
                                        bundle.putIntegerArrayList(
                                                "pidList", integerArrayListExtra);
                                        obtainMessage.setData(bundle);
                                        mARsHandler.mMainHandler.sendMessage(obtainMessage);
                                    }
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            "broadcast received action TCPU: ",
                                            intent.getAction(),
                                            "MARsTrigger");
                                    return;
                                case 1:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    Slog.d(
                                            "MARsTrigger",
                                            "mAppStartUpIntentReceiver onReceive : " + action);
                                    if (action.equals("android.intent.action.ACTION_SHUTDOWN")
                                            || action.equals("android.intent.action.REBOOT")) {
                                        MARsBigData mARsBigData =
                                                MARsBigData.MARsBigDataHolder.INSTANCE;
                                        mARsBigData.getClass();
                                        try {
                                            mARsBigData.updateBigdataInfo();
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                            mARsBigData.PLEVdata =
                                                    new MARsBigData.UsageInfo().toString();
                                        }
                                        String str = mARsBigData.PLEVdata;
                                        if (str != null) {
                                            mARsBigData.sendBigData("PLEV", str);
                                        }
                                        boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .updateResetTime();
                                        if (MARsDebugConfig.DEBUG_MID
                                                || MARsDebugConfig.DEBUG_HIGH) {
                                            final MARsHistoryLog.SaveLogThread saveLogThread =
                                                    MARsHistoryLog.MARsHistoryLogHolder.INSTANCE
                                                            .mSaveLogThread;
                                            saveLogThread.getClass();
                                            Thread thread =
                                                    new Thread(
                                                            new Runnable() { // from class:
                                                                             // com.android.server.am.mars.MARsHistoryLog$SaveLogThread$$ExternalSyntheticLambda0
                                                                @Override // java.lang.Runnable
                                                                public final void run() {
                                                                    MARsHistoryLog.SaveLogThread
                                                                            .this
                                                                            .this$0.saveLogToFile(
                                                                            true, true);
                                                                }
                                                            });
                                            thread.setPriority(1);
                                            thread.start();
                                        }
                                        if (MARsPolicyManager.App_StartUp_History) {
                                            MARsDBManager.MARsDBManagerHolder.INSTANCE
                                                    .doUpdateCompHistory(true);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action2 = intent.getAction();
                                    Bundle extras = intent.getExtras();
                                    if (action2.equals("MARS_REQUEST_PKG_INFO")) {
                                        if (!MARsPolicyManager.MARs_ENABLE) {
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                    .postInit();
                                            return;
                                        }
                                        if (extras != null) {
                                            String string = extras.getString("MARS_EXTRA", "");
                                            boolean equals = string.equals("create");
                                            z = string.equals("update");
                                            z2 = string.equals("init");
                                            z3 = equals;
                                        } else {
                                            z = false;
                                            z2 = false;
                                        }
                                        MARsDBHandler.getInstance();
                                        MARsDBHandler mARsDBHandler =
                                                MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                        MARsDBHandler.MainHandler mainHandler2 =
                                                mARsDBHandler.mMainHandler;
                                        if (mainHandler2 == null) {
                                            return;
                                        }
                                        mainHandler2.removeMessages(6);
                                        Message obtainMessage2 =
                                                mARsDBHandler.mMainHandler.obtainMessage(6);
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putBoolean("onCreate", z3);
                                        bundle2.putBoolean("onUpgrade", z);
                                        bundle2.putBoolean("onInit", z2);
                                        obtainMessage2.setData(bundle2);
                                        mARsDBHandler.mMainHandler.sendMessage(obtainMessage2);
                                        return;
                                    }
                                    return;
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action3 = intent.getAction();
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", -1);
                                    Slog.d(
                                            "MARsTrigger",
                                            "mUserActionReceiver : "
                                                    + action3
                                                    + " userId = "
                                                    + intExtra);
                                    if ("android.intent.action.USER_STOPPED".equals(action3)) {
                                        if (intExtra >= 95 && intExtra <= 99) {
                                            boolean z5 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager) {
                                                mARsPolicyManager.mDualAppEnabled = false;
                                            }
                                            return;
                                        }
                                        UserManager userManager =
                                                (UserManager) context.getSystemService("user");
                                        if (userManager == null
                                                || (userInfo3 = userManager.getUserInfo(intExtra))
                                                        == null
                                                || !userInfo3.isManagedProfile()) {
                                            return;
                                        }
                                        boolean z6 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setManagedProfileEnabled(intExtra, false);
                                        return;
                                    }
                                    if ("android.intent.action.USER_STARTED".equals(action3)) {
                                        if (intExtra >= 95 && intExtra <= 99) {
                                            boolean z7 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager2 =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager2) {
                                                mARsPolicyManager2.mDualAppEnabled = true;
                                            }
                                            return;
                                        }
                                        UserManager userManager2 =
                                                (UserManager) context.getSystemService("user");
                                        if (userManager2 == null
                                                || (userInfo2 = userManager2.getUserInfo(intExtra))
                                                        == null
                                                || !userInfo2.isManagedProfile()) {
                                            return;
                                        }
                                        boolean z8 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setManagedProfileEnabled(intExtra, true);
                                        return;
                                    }
                                    if ("android.intent.action.USER_ADDED".equals(action3)) {
                                        if (intExtra < 95 || intExtra > 99) {
                                            UserManager userManager3 =
                                                    (UserManager) context.getSystemService("user");
                                            if (userManager3 == null
                                                    || (userInfo =
                                                                    userManager3.getUserInfo(
                                                                            intExtra))
                                                            == null
                                                    || !userInfo.isManagedProfile()) {
                                                return;
                                            }
                                            boolean z9 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                    .setManagedProfileEnabled(intExtra, true);
                                            return;
                                        }
                                        boolean z10 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager mARsPolicyManager3 =
                                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                        synchronized (mARsPolicyManager3) {
                                            mARsPolicyManager3.mDualAppEnabled = true;
                                        }
                                        synchronized (mARsPolicyManager3) {
                                            i62 = mARsPolicyManager3.mDualAppUserId;
                                        }
                                        if (intExtra != i62) {
                                            synchronized (mARsPolicyManager3) {
                                                mARsPolicyManager3.mDualAppUserId = intExtra;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                            }
                        }
                    };
            final int i11 = 1;
            mARsTrigger.mEmergencyStateChangedReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            boolean z;
                            Bundle extras;
                            ArrayList<String> stringArrayList;
                            Context context2;
                            int i22 = 2;
                            switch (i11) {
                                case 0:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    if (action.equals("android.intent.action.SCREEN_ON")) {
                                        boolean z2 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setScreenOnState(true);
                                        MARsTrigger mARsTrigger2 = mARsTrigger;
                                        PendingIntent pendingIntent =
                                                mARsTrigger2.mMARsFirstTriggerPolicyAlarmIntent;
                                        if (pendingIntent != null) {
                                            mARsTrigger2.mAlarm.cancel(pendingIntent);
                                        }
                                        MARsHandler mARsHandler =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        MARsHandler.MainHandler mainHandler =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler != null) {
                                            mainHandler.removeMessages(1);
                                        }
                                        MARsHandler.MainHandler mainHandler2 =
                                                mARsHandler.mMainHandler;
                                        if (mainHandler2 == null) {
                                            return;
                                        }
                                        mainHandler2.removeMessages(2);
                                        return;
                                    }
                                    if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                                        ActiveTrafficFilter activeTrafficFilter =
                                                ActiveTrafficFilter.ActiveTrafficFilterHolder
                                                        .INSTANCE;
                                        NetworkInfo activeNetworkInfo =
                                                ((ConnectivityManager)
                                                                activeTrafficFilter.mContext
                                                                        .getSystemService(
                                                                                "connectivity"))
                                                        .getActiveNetworkInfo();
                                        activeTrafficFilter.mIsDataConnectionConnected =
                                                activeNetworkInfo != null
                                                        && activeNetworkInfo.isConnected();
                                        if (MARsDebugConfig.DEBUG_FILTER) {
                                            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                                    "MARs:ActiveTrafficFilter",
                                                    new StringBuilder("DataConnection: "),
                                                    activeTrafficFilter.mIsDataConnectionConnected);
                                            return;
                                        }
                                        return;
                                    }
                                    if (action.equals(UiModeManager.ACTION_ENTER_CAR_MODE)) {
                                        boolean z3 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager mARsPolicyManager =
                                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                        synchronized (mARsPolicyManager) {
                                            mARsPolicyManager.mCarModeOn = true;
                                        }
                                        return;
                                    }
                                    if (action.equals(Constants.SIM_STATE_CHANGED)) {
                                        AllowListFilter.AllowListFilterHolder.INSTANCE
                                                .setCarrierAllowList();
                                        return;
                                    }
                                    if (!action.equals("MARS_REQUEST_DB_COMPLETE")) {
                                        if (action.equals(
                                                "com.samsung.android.sm.ACTION_SCPM_MARS_SETTINGS_UPDATED")) {
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE
                                                    .sendGetSCPMPolicyMsgToDBHandler();
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager2 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager2.getClass();
                                    synchronized (MARsPolicyManager.MARsLock) {
                                        if (mARsPolicyManager2.mMARsTargetPackages.mMap.size()
                                                <= 0) {
                                            r3 = false;
                                        }
                                    }
                                    if (r3) {
                                        return;
                                    }
                                    MARsDBHandler.getInstance();
                                    MARsDBHandler mARsDBHandler =
                                            MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                    if (mARsDBHandler.mMainHandler == null) {
                                        return;
                                    }
                                    Slog.d("MARsDBHandler", "sendSdhmsDBCompleteMsgToDBHandler");
                                    mARsDBHandler.mMainHandler.post(
                                            mARsDBHandler.mFASDBupdateRunnable);
                                    return;
                                case 1:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals(
                                                            "com.samsung.intent.action.EMERGENCY_STATE_CHANGED")
                                            || intent.getIntExtra("reason", 0) != 5) {
                                        return;
                                    }
                                    Slog.d("MARsTrigger", "disable ultra power saving mode");
                                    if (MARsPolicyManager.MARs_ENABLE) {
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setSubUserIds();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger3 = mARsTrigger;
                                    if (mARsTrigger3.mEmStateReceiverRegistered) {
                                        try {
                                            mARsTrigger3.mContext.unregisterReceiver(
                                                    mARsTrigger3.mEmergencyStateChangedReceiver);
                                            mARsTrigger3.mEmStateReceiverRegistered = false;
                                        } catch (IllegalArgumentException unused) {
                                            Slog.e(
                                                    "MARsTrigger",
                                                    "IllegalArgumentException occurred in"
                                                        + " unregisterEmStateReceiver()");
                                        }
                                    }
                                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.postInit();
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action2 = intent.getAction();
                                    if (!action2.equals("android.intent.action.SCREEN_OFF")) {
                                        if (action2.equals(UiModeManager.ACTION_EXIT_CAR_MODE)) {
                                            boolean z5 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager3 =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager3) {
                                                mARsPolicyManager3.mCarModeOn = false;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    boolean z6 = MARsPolicyManager.MARs_ENABLE;
                                    MARsPolicyManager mARsPolicyManager4 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager4.setScreenOnState(false);
                                    boolean z7 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    if (FreecessController.FreecessControllerHolder.INSTANCE
                                            .mIsDumpstateWorking) {
                                        return;
                                    }
                                    synchronized (mARsPolicyManager4) {
                                        z = mARsPolicyManager4.mCarModeOn;
                                    }
                                    if (z) {
                                        return;
                                    }
                                    if (MARsPolicyManager.isChinaModel) {
                                        MARsHandler.MARsHandlerHolder.INSTANCE
                                                .sendFirstTriggerMsgToMainHandler();
                                        return;
                                    }
                                    MARsTrigger mARsTrigger4 = mARsTrigger;
                                    mARsTrigger4.getClass();
                                    if (mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent == null) {
                                        mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent =
                                                PendingIntent.getBroadcastAsUser(
                                                        mARsTrigger4.mContext,
                                                        0,
                                                        new Intent("FIRST_ALARM_TRIGGER_ACTION")
                                                                .setPackage("android")
                                                                .setFlags(1073741824),
                                                        67108864,
                                                        mARsTrigger4.user);
                                    }
                                    mARsTrigger4.mAlarm.setExact(
                                            1,
                                            System.currentTimeMillis() + 5000,
                                            mARsTrigger4.mMARsFirstTriggerPolicyAlarmIntent);
                                    return;
                                case 3:
                                    String action3 = intent.getAction();
                                    if (action3 == null) {
                                        return;
                                    }
                                    boolean equals = action3.equals("FIRST_ALARM_TRIGGER_ACTION");
                                    MARsHandler mARsHandler2 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals) {
                                        Slog.d(
                                                "MARsTrigger",
                                                "mPolicyIntentReceiver broadcast received action : "
                                                        .concat(action3));
                                        boolean z8 = MARsPolicyManager.MARs_ENABLE;
                                        if (!MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .getScreenOnState()) {
                                            boolean z9 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            if (!FreecessController.FreecessControllerHolder
                                                    .INSTANCE
                                                    .mIsDumpstateWorking) {
                                                mARsHandler2.sendFirstTriggerMsgToMainHandler();
                                                mARsHandler2.sendRepeatTriggerMsgToMainHandler();
                                            }
                                        }
                                    }
                                    if (!action3.equals(
                                                    "com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")
                                            || (extras = intent.getExtras()) == null) {
                                        return;
                                    }
                                    String string = extras.getString("POLICY_NAME", "");
                                    mARsTrigger.getClass();
                                    if ("applocker".equalsIgnoreCase(string)) {
                                        i22 = 1;
                                    } else if (!"autorun".equalsIgnoreCase(string)) {
                                        i22 =
                                                "freecess".equalsIgnoreCase(string)
                                                        ? 4
                                                        : "sbikepolicy".equalsIgnoreCase(string)
                                                                ? 6
                                                                : "gamepolicy"
                                                                                .equalsIgnoreCase(
                                                                                        string)
                                                                        ? 9
                                                                        : 0;
                                    }
                                    ArrayList<String> stringArrayList2 =
                                            extras.getStringArrayList("PACKAGE_NAME");
                                    if (i22 == 0 || stringArrayList2 == null) {
                                        return;
                                    }
                                    mARsHandler2.sendRunPolicySpecificPkgMsgToMainHandler(
                                            i22,
                                            mARsTrigger.mContext.getUserId(),
                                            null,
                                            stringArrayList2);
                                    return;
                                case 4:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action4 = intent.getAction();
                                    boolean equals2 =
                                            action4.equals(
                                                    "com.android.server.am.MARS_TRIGGER_SBIKE_MODE_POLICY");
                                    MARsHandler mARsHandler3 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    if (equals2) {
                                        Bundle extras2 = intent.getExtras();
                                        if (extras2 == null
                                                || (stringArrayList =
                                                                extras2.getStringArrayList(
                                                                        "PACKAGE_NAME"))
                                                        == null) {
                                            return;
                                        }
                                        mARsHandler3.sendRunPolicySpecificPkgMsgToMainHandler(
                                                6,
                                                mARsTrigger.mContext.getUserId(),
                                                MARsTrigger.m199$$Nest$mactionToString(
                                                        mARsTrigger, action4),
                                                stringArrayList);
                                        return;
                                    }
                                    if (action4.equals(
                                            "com.android.server.am.MARS_CANCEL_SBIKE_MODE_POLICY")) {
                                        Bundle extras3 = intent.getExtras();
                                        if (extras3 == null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6, mARsTrigger.mContext.getUserId(), null);
                                            return;
                                        }
                                        ArrayList<String> stringArrayList3 =
                                                extras3.getStringArrayList("PACKAGE_NAME");
                                        if (stringArrayList3 != null) {
                                            mARsHandler3.sendCancelPolicyMsgToMainHandler(
                                                    6,
                                                    mARsTrigger.mContext.getUserId(),
                                                    stringArrayList3);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 5:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String stringExtra = intent.getStringExtra("package");
                                    if (stringExtra == null
                                            || "com.samsung.android.game.gos".equals(stringExtra)) {
                                        String action5 = intent.getAction();
                                        boolean equals3 =
                                                action5.equals(
                                                        "com.android.server.am.MARS_TRIGGER_GAME_MODE_POLICY");
                                        MARsHandler mARsHandler4 =
                                                MARsHandler.MARsHandlerHolder.INSTANCE;
                                        if (!equals3) {
                                            if (action5.equals(
                                                    "com.android.server.am.MARS_CANCEL_GAME_MODE_POLICY")) {
                                                boolean z10 =
                                                        FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                                FreecessController freecessController =
                                                        FreecessController.FreecessControllerHolder
                                                                .INSTANCE;
                                                if (freecessController.mCalmModeEnabled) {
                                                    FreecessHandler freecessHandler =
                                                            FreecessHandler.FreecessHandlerHolder
                                                                    .INSTANCE;
                                                    FreecessHandler.MainHandler mainHandler3 =
                                                            freecessHandler.mMainHandler;
                                                    if (mainHandler3 != null) {
                                                        Message obtainMessage =
                                                                mainHandler3.obtainMessage(23);
                                                        freecessHandler.mMainHandler.removeMessages(
                                                                23);
                                                        freecessHandler.mMainHandler.sendMessage(
                                                                obtainMessage);
                                                    }
                                                } else {
                                                    boolean z11 = MARsPolicyManager.MARs_ENABLE;
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE
                                                            .getClass();
                                                    if (!MARsPolicyManager.isChinaPolicyEnabled()) {
                                                        mARsHandler4
                                                                .sendCancelPolicyMsgToMainHandler(
                                                                        9, -1, null);
                                                        if (freecessController.mGameModeEnabled) {
                                                            freecessController.mGameModeEnabled =
                                                                    false;
                                                        }
                                                    }
                                                }
                                                Slog.d(
                                                        "MARsTrigger",
                                                        "broadcast received action :"
                                                            + " MARS_CANCEL_GAME_MODE_POLICY");
                                                return;
                                            }
                                            return;
                                        }
                                        Bundle extras4 = intent.getExtras();
                                        if (extras4 != null) {
                                            ArrayList<String> stringArrayList4 =
                                                    extras4.getStringArrayList("survive_app");
                                            LatestProtectedPackageFilter
                                                            .LatestProtectedPackageFilterHolder
                                                            .INSTANCE
                                                            .mProtectedAppSizeForGame =
                                                    extras4.getInt("lru_num");
                                            boolean z12 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            FreecessController freecessController2 =
                                                    FreecessController.FreecessControllerHolder
                                                            .INSTANCE;
                                            if (!freecessController2.mIsFreecessEnable
                                                    || !FreecessController.IS_SUPPORT_CALM_MODE) {
                                                mARsHandler4
                                                        .sendRunPolicySpecificPkgMsgToMainHandler(
                                                                9,
                                                                mARsTrigger.mContext.getUserId(),
                                                                MARsTrigger
                                                                        .m199$$Nest$mactionToString(
                                                                                mARsTrigger,
                                                                                action5),
                                                                stringArrayList4);
                                                if (freecessController2.mGameModeEnabled) {
                                                    return;
                                                }
                                                freecessController2.mGameModeEnabled = true;
                                                return;
                                            }
                                            FreecessHandler freecessHandler2 =
                                                    FreecessHandler.FreecessHandlerHolder.INSTANCE;
                                            FreecessHandler.MainHandler mainHandler4 =
                                                    freecessHandler2.mMainHandler;
                                            if (mainHandler4 == null) {
                                                return;
                                            }
                                            mainHandler4.removeMessages(21);
                                            Bundle bundle = new Bundle();
                                            bundle.putStringArrayList("list", stringArrayList4);
                                            Message obtainMessage2 =
                                                    freecessHandler2.mMainHandler.obtainMessage(21);
                                            obtainMessage2.setData(bundle);
                                            freecessHandler2.mMainHandler.sendMessage(
                                                    obtainMessage2);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 6:
                                    if (intent == null
                                            || intent.getAction() == null
                                            || !intent.getAction()
                                                    .equals("android.intent.action.TIME_SET")) {
                                        return;
                                    }
                                    long currentTimeMillis = System.currentTimeMillis();
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    MARsTrigger mARsTrigger5 = mARsTrigger;
                                    long j =
                                            currentTimeMillis
                                                    - ((elapsedRealtime
                                                                    - mARsTrigger5
                                                                            .mLastTimeChangeRealtime)
                                                            + mARsTrigger5
                                                                    .mLastTimeChangeClockTime);
                                    MARsHandler mARsHandler5 =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putLong("changedTime", j);
                                    Message obtainMessage3 =
                                            mARsHandler5.mMainHandler.obtainMessage(6);
                                    obtainMessage3.setData(bundle2);
                                    mARsHandler5.mMainHandler.sendMessage(obtainMessage3);
                                    MARsTrigger mARsTrigger6 = mARsTrigger;
                                    mARsTrigger6.mLastTimeChangeClockTime = currentTimeMillis;
                                    mARsTrigger6.mLastTimeChangeRealtime = elapsedRealtime;
                                    boolean z13 = EventRecorder.FEATURE_ENABLE;
                                    EventRecorder eventRecorder =
                                            EventRecorder.EventRecorderHolder.INSTANCE;
                                    eventRecorder.getClass();
                                    if (Math.abs(j) < 30000) {
                                        Slog.d(
                                                "EventRecorder",
                                                "changed time is " + j + ". ignore.");
                                        return;
                                    }
                                    Slog.d("EventRecorder", "disable EventRecorder");
                                    eventRecorder.performWrite(System.currentTimeMillis());
                                    EventRecorder.FEATURE_ENABLE = false;
                                    JobScheduler jobScheduler =
                                            (JobScheduler)
                                                    eventRecorder.mContext.getSystemService(
                                                            "jobscheduler");
                                    if (jobScheduler != null) {
                                        jobScheduler
                                                .forNamespace("MARsEventRecorderNamespace")
                                                .cancel(1);
                                    }
                                    ReentrantLock reentrantLock = EventRecorder.mFileLock;
                                    reentrantLock.lock();
                                    try {
                                        Slog.d(
                                                "EventRecorder",
                                                "file delete result : "
                                                        + EventRecorder.file.delete());
                                        reentrantLock.unlock();
                                        return;
                                    } catch (Throwable th) {
                                        EventRecorder.mFileLock.unlock();
                                        throw th;
                                    }
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action6 = intent.getAction();
                                    Slog.d("MARsTrigger", "broadcast received action : " + action6);
                                    if (!"android.intent.action.USER_SWITCHED".equals(action6)) {
                                        return;
                                    }
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", 0);
                                    Slog.d(
                                            "MARsTrigger",
                                            "mContext.id = " + mARsTrigger.mContext.getUserId());
                                    if (MARsPolicyManager.App_StartUp_History) {
                                        MARsDBManager.MARsDBManagerHolder.INSTANCE
                                                .doUpdateCompHistory(false);
                                    }
                                    MARsPolicyManager mARsPolicyManager5 =
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                    mARsPolicyManager5.addDebugInfoToHistory("DEV", "switchUser");
                                    boolean z14 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController3 =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    boolean z15 = freecessController3.mIsScreenOnFreecessEnabled;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(false);
                                        if (MARsDebugConfig.DEBUG_ENG) {
                                            Slog.d("FreecessController", "removeBgTriggerMsg....");
                                        }
                                        FreecessHandler.FreecessHandlerHolder.INSTANCE
                                                .removeBgTriggerMsg();
                                    }
                                    if (freecessController3.mIsFreecessEnable) {
                                        freecessController3.unFreezePackage("MUM");
                                    }
                                    mARsPolicyManager5.mFirstTimeUpdatePackages = true;
                                    if (z15) {
                                        freecessController3.setScreenOnFreecessEnabled(true);
                                    }
                                    FilterManager filterManager =
                                            FilterManager.FilterManagerHolder.INSTANCE;
                                    int i32 = 1;
                                    while (true) {
                                        FilterFactory filterFactory = filterManager.mFF;
                                        if (i32 >= 34) {
                                            filterFactory.filterHashMap.clear();
                                            filterManager.mFCF.filterHashMap.clear();
                                            mARsPolicyManager5.mCurrentUserId = intExtra;
                                            mARsPolicyManager5.mLastNotiSentTimeForDisabled = 0L;
                                            UserHandle userHandle = new UserHandle(intExtra);
                                            try {
                                                Context context3 = mARsPolicyManager5.mContext;
                                                context2 =
                                                        context3.createPackageContextAsUser(
                                                                context3.getPackageName(),
                                                                0,
                                                                userHandle);
                                            } catch (PackageManager.NameNotFoundException unused2) {
                                                context2 = mARsPolicyManager5.mContext;
                                            }
                                            MARsDBManager mARsDBManager =
                                                    MARsDBManager.MARsDBManagerHolder.INSTANCE;
                                            Context context4 = mARsDBManager.mCurrentContext;
                                            try {
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerSettingsObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerSettingsObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerSettingsObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerFreezeExcludeListObserver) {
                                                    mARsDBManager
                                                            .mContext
                                                            .getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerFreezeExcludeListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerFreezeExcludeListObserver =
                                                            false;
                                                }
                                                if (mARsDBManager
                                                        .mRegisteredSmartManagerDefaultAllowedListObserver) {
                                                    context4.getContentResolver()
                                                            .unregisterContentObserver(
                                                                    mARsDBManager
                                                                            .mSmartManagerDefaultAllowedListObserver);
                                                    mARsDBManager
                                                                    .mRegisteredSmartManagerDefaultAllowedListObserver =
                                                            false;
                                                }
                                            } catch (IllegalArgumentException unused3) {
                                                Slog.e(
                                                        "MARsDBManager",
                                                        "IllegalArgumentException occurred in"
                                                            + " unregisterContentObserver()");
                                            }
                                            mARsDBManager.mCurrentContext = context2;
                                            mARsDBManager.registerContentObservers(context2);
                                            FilterManager.FilterManagerHolder.INSTANCE.init(
                                                    context2);
                                            MARsHandler.MARsHandlerHolder.INSTANCE
                                                    .sendInitDisabledMsgToMainHandler(intExtra);
                                            MARsDBHandler.getInstance();
                                            MARsDBHandler mARsDBHandler2 =
                                                    MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                            if (mARsDBHandler2.mMainHandler == null) {
                                                return;
                                            }
                                            Bundle m =
                                                    SystemUpdateManagerService$$ExternalSyntheticOutline0
                                                            .m(intExtra, "userId");
                                            Message obtainMessage4 =
                                                    mARsDBHandler2.mMainHandler.obtainMessage(7);
                                            obtainMessage4.setData(m);
                                            mARsDBHandler2.mMainHandler.sendMessage(obtainMessage4);
                                            return;
                                        }
                                        ((IFilter)
                                                        filterFactory.filterHashMap.get(
                                                                Integer.valueOf(i32)))
                                                .deInit();
                                        i32++;
                                    }
                                    break;
                            }
                        }
                    };
            final int i12 = 2;
            mARsTrigger.mSMDBChangedReceiver =
                    new BroadcastReceiver() { // from class: com.android.server.am.MARsTrigger.5
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            boolean z;
                            boolean z2;
                            UserInfo userInfo;
                            int i62;
                            UserInfo userInfo2;
                            UserInfo userInfo3;
                            boolean z3 = false;
                            switch (i12) {
                                case 0:
                                    ArrayList<Integer> integerArrayListExtra =
                                            intent.getIntegerArrayListExtra("pid");
                                    MARsHandler mARsHandler =
                                            MARsHandler.MARsHandlerHolder.INSTANCE;
                                    MARsHandler.MainHandler mainHandler = mARsHandler.mMainHandler;
                                    if (mainHandler != null && integerArrayListExtra != null) {
                                        Message obtainMessage = mainHandler.obtainMessage(17);
                                        Bundle bundle = new Bundle();
                                        bundle.putIntegerArrayList(
                                                "pidList", integerArrayListExtra);
                                        obtainMessage.setData(bundle);
                                        mARsHandler.mMainHandler.sendMessage(obtainMessage);
                                    }
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            "broadcast received action TCPU: ",
                                            intent.getAction(),
                                            "MARsTrigger");
                                    return;
                                case 1:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action = intent.getAction();
                                    Slog.d(
                                            "MARsTrigger",
                                            "mAppStartUpIntentReceiver onReceive : " + action);
                                    if (action.equals("android.intent.action.ACTION_SHUTDOWN")
                                            || action.equals("android.intent.action.REBOOT")) {
                                        MARsBigData mARsBigData =
                                                MARsBigData.MARsBigDataHolder.INSTANCE;
                                        mARsBigData.getClass();
                                        try {
                                            mARsBigData.updateBigdataInfo();
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                            mARsBigData.PLEVdata =
                                                    new MARsBigData.UsageInfo().toString();
                                        }
                                        String str = mARsBigData.PLEVdata;
                                        if (str != null) {
                                            mARsBigData.sendBigData("PLEV", str);
                                        }
                                        boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .updateResetTime();
                                        if (MARsDebugConfig.DEBUG_MID
                                                || MARsDebugConfig.DEBUG_HIGH) {
                                            final MARsHistoryLog.SaveLogThread saveLogThread =
                                                    MARsHistoryLog.MARsHistoryLogHolder.INSTANCE
                                                            .mSaveLogThread;
                                            saveLogThread.getClass();
                                            Thread thread =
                                                    new Thread(
                                                            new Runnable() { // from class:
                                                                             // com.android.server.am.mars.MARsHistoryLog$SaveLogThread$$ExternalSyntheticLambda0
                                                                @Override // java.lang.Runnable
                                                                public final void run() {
                                                                    MARsHistoryLog.SaveLogThread
                                                                            .this
                                                                            .this$0.saveLogToFile(
                                                                            true, true);
                                                                }
                                                            });
                                            thread.setPriority(1);
                                            thread.start();
                                        }
                                        if (MARsPolicyManager.App_StartUp_History) {
                                            MARsDBManager.MARsDBManagerHolder.INSTANCE
                                                    .doUpdateCompHistory(true);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                case 2:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action2 = intent.getAction();
                                    Bundle extras = intent.getExtras();
                                    if (action2.equals("MARS_REQUEST_PKG_INFO")) {
                                        if (!MARsPolicyManager.MARs_ENABLE) {
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                    .postInit();
                                            return;
                                        }
                                        if (extras != null) {
                                            String string = extras.getString("MARS_EXTRA", "");
                                            boolean equals = string.equals("create");
                                            z = string.equals("update");
                                            z2 = string.equals("init");
                                            z3 = equals;
                                        } else {
                                            z = false;
                                            z2 = false;
                                        }
                                        MARsDBHandler.getInstance();
                                        MARsDBHandler mARsDBHandler =
                                                MARsDBHandler.MARsDBHandlerHolder.INSTANCE;
                                        MARsDBHandler.MainHandler mainHandler2 =
                                                mARsDBHandler.mMainHandler;
                                        if (mainHandler2 == null) {
                                            return;
                                        }
                                        mainHandler2.removeMessages(6);
                                        Message obtainMessage2 =
                                                mARsDBHandler.mMainHandler.obtainMessage(6);
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putBoolean("onCreate", z3);
                                        bundle2.putBoolean("onUpgrade", z);
                                        bundle2.putBoolean("onInit", z2);
                                        obtainMessage2.setData(bundle2);
                                        mARsDBHandler.mMainHandler.sendMessage(obtainMessage2);
                                        return;
                                    }
                                    return;
                                default:
                                    if (intent == null || intent.getAction() == null) {
                                        return;
                                    }
                                    String action3 = intent.getAction();
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", -1);
                                    Slog.d(
                                            "MARsTrigger",
                                            "mUserActionReceiver : "
                                                    + action3
                                                    + " userId = "
                                                    + intExtra);
                                    if ("android.intent.action.USER_STOPPED".equals(action3)) {
                                        if (intExtra >= 95 && intExtra <= 99) {
                                            boolean z5 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager) {
                                                mARsPolicyManager.mDualAppEnabled = false;
                                            }
                                            return;
                                        }
                                        UserManager userManager =
                                                (UserManager) context.getSystemService("user");
                                        if (userManager == null
                                                || (userInfo3 = userManager.getUserInfo(intExtra))
                                                        == null
                                                || !userInfo3.isManagedProfile()) {
                                            return;
                                        }
                                        boolean z6 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setManagedProfileEnabled(intExtra, false);
                                        return;
                                    }
                                    if ("android.intent.action.USER_STARTED".equals(action3)) {
                                        if (intExtra >= 95 && intExtra <= 99) {
                                            boolean z7 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager mARsPolicyManager2 =
                                                    MARsPolicyManager.MARsPolicyManagerHolder
                                                            .INSTANCE;
                                            synchronized (mARsPolicyManager2) {
                                                mARsPolicyManager2.mDualAppEnabled = true;
                                            }
                                            return;
                                        }
                                        UserManager userManager2 =
                                                (UserManager) context.getSystemService("user");
                                        if (userManager2 == null
                                                || (userInfo2 = userManager2.getUserInfo(intExtra))
                                                        == null
                                                || !userInfo2.isManagedProfile()) {
                                            return;
                                        }
                                        boolean z8 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                .setManagedProfileEnabled(intExtra, true);
                                        return;
                                    }
                                    if ("android.intent.action.USER_ADDED".equals(action3)) {
                                        if (intExtra < 95 || intExtra > 99) {
                                            UserManager userManager3 =
                                                    (UserManager) context.getSystemService("user");
                                            if (userManager3 == null
                                                    || (userInfo =
                                                                    userManager3.getUserInfo(
                                                                            intExtra))
                                                            == null
                                                    || !userInfo.isManagedProfile()) {
                                                return;
                                            }
                                            boolean z9 = MARsPolicyManager.MARs_ENABLE;
                                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE
                                                    .setManagedProfileEnabled(intExtra, true);
                                            return;
                                        }
                                        boolean z10 = MARsPolicyManager.MARs_ENABLE;
                                        MARsPolicyManager mARsPolicyManager3 =
                                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                                        synchronized (mARsPolicyManager3) {
                                            mARsPolicyManager3.mDualAppEnabled = true;
                                        }
                                        synchronized (mARsPolicyManager3) {
                                            i62 = mARsPolicyManager3.mDualAppUserId;
                                        }
                                        if (intExtra != i62) {
                                            synchronized (mARsPolicyManager3) {
                                                mARsPolicyManager3.mDualAppUserId = intExtra;
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                            }
                        }
                    };
            INSTANCE = mARsTrigger;
        }
    }

    /* renamed from: -$$Nest$mactionToString, reason: not valid java name */
    public static String m199$$Nest$mactionToString(MARsTrigger mARsTrigger, String str) {
        if (str.equals("com.android.server.am.ACTION_UI_SET_ALWAYS_OPTIMIZING")) {
            return "User Set Always Optimizing";
        }
        if (str.equals("com.android.server.am.ACTION_PACKAGE_NOTUSED_RECENTLY")) {
            return "Package Not Used Recently";
        }
        if (str.equals("com.samsung.android.server.am.ACTION_UI_TRIGGER_POLICY")) {
            return "User Trigger Policy";
        }
        if (str.equals("com.android.server.am.MARS_TRIGGER_SBIKE_MODE_POLICY")) {
            return "Trigger S Bike Policy";
        }
        if (str.equals("com.android.server.am.MARS_TRIGGER_GAME_MODE_POLICY")) {
            return "Trigger GAME MODE Policy";
        }
        return null;
    }
}
