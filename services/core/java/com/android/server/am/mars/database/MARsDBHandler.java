package com.android.server.am.mars.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.am.FreecessController;
import com.android.server.am.FreecessTrigger;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.MARsTrigger;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.database.FASDataManager;
import com.android.server.am.mars.database.MARsDBManager;
import com.android.server.am.mars.database.MARsVersionManager;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.com.android.server.am.mars.database.MARsListManager;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MARsDBHandler {
    public DBThread mDBThread;
    public MARsDBHandler$$ExternalSyntheticLambda0 mFASDBupdateRunnable;
    public MainHandler mMainHandler;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DBThread extends Thread {
        public final /* synthetic */ MARsDBHandler this$0 = MARsDBHandlerHolder.INSTANCE;

        public DBThread() {
            super("MARsDBManagerThread");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Process.setThreadPriority(0);
            Looper.prepare();
            this.this$0.mMainHandler = new MainHandler();
            Looper.loop();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class MARsDBHandlerHolder {
        public static final MARsDBHandler INSTANCE;

        static {
            MARsDBHandler mARsDBHandler = new MARsDBHandler();
            mARsDBHandler.mFASDBupdateRunnable = new MARsDBHandler$$ExternalSyntheticLambda0();
            INSTANCE = mARsDBHandler;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MainHandler extends Handler {
        public final /* synthetic */ MARsDBHandler this$0 = MARsDBHandlerHolder.INSTANCE;
        public final MARsDBManager m = MARsDBManager.MARsDBManagerHolder.INSTANCE;

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            ArrayList arrayList;
            FASEntity fASEntity;
            MARsPolicyManager.Policy policy;
            Cursor cursor;
            Cursor cursor2;
            Cursor cursor3;
            Cursor cursor4;
            Cursor cursor5;
            Cursor query;
            MainHandler mainHandler;
            MARsDBHandler mARsDBHandler;
            MainHandler mainHandler2;
            Context context;
            Cursor cursor6 = null;
            int i = 0;
            switch (message.what) {
                case 1:
                    MARsDBManager mARsDBManager = this.m;
                    mARsDBManager.getSettingsValueFromDB(true);
                    mARsDBManager.getDefaultAllowedListDBValues();
                    if (MARsUtils.isChinaPolicyEnabled()) {
                        mARsDBManager.getFreezeExcludeListFromDB();
                        return;
                    }
                    return;
                case 2:
                    Bundle data = message.getData();
                    if (data == null || (arrayList = (ArrayList) data.getSerializable("values")) == null) {
                        return;
                    }
                    MARsDBManager mARsDBManager2 = this.m;
                    mARsDBManager2.getClass();
                    try {
                        Slog.d("MARsDBManager", "updatePkgsToSMDB : begin --size " + arrayList.size());
                        while (i < arrayList.size()) {
                            mARsDBManager2.updatePkgToSMDB((FASEntity) arrayList.get(i));
                            i++;
                        }
                        Slog.d("MARsDBManager", "updatePkgsToSMDB : end");
                        return;
                    } catch (Exception e) {
                        Slog.e("MARsDBManager", "Exception on handling DB : " + e.getMessage());
                        e.printStackTrace();
                        return;
                    }
                case 3:
                    Bundle data2 = message.getData();
                    if (data2 == null || (fASEntity = (FASEntity) data2.getSerializable("value")) == null) {
                        return;
                    }
                    this.m.updatePkgToSMDB(fASEntity);
                    return;
                case 4:
                    Bundle data3 = message.getData();
                    if (data3 == null) {
                        return;
                    }
                    String string = data3.getString("callee", null);
                    String string2 = data3.getString("caller", null);
                    boolean z = data3.getBoolean("isblock", true);
                    long j = data3.getLong("requesttime", System.currentTimeMillis());
                    MARsDBManager mARsDBManager3 = this.m;
                    mARsDBManager3.getClass();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("calleepackage", string);
                    contentValues.put("callerpackage", string2);
                    contentValues.put("isblocked", Boolean.toString(z));
                    contentValues.put("requestTime", Long.toString(j));
                    synchronized (mARsDBManager3.mAppStartUpList) {
                        try {
                            mARsDBManager3.mAppStartUpList.add(contentValues);
                            if (mARsDBManager3.mAppStartUpList.size() > 99) {
                                mARsDBManager3.doUpdateCompHistory(false);
                            }
                        } finally {
                        }
                    }
                    return;
                case 5:
                    Bundle data4 = message.getData();
                    if (data4 == null) {
                        return;
                    }
                    boolean z2 = data4.getBoolean("boot", false);
                    MARsDBManager mARsDBManager4 = this.m;
                    if (mARsDBManager4.isCompareDBVersion()) {
                        String[] strArr = {"key", "value"};
                        ArrayList arrayList2 = new ArrayList();
                        try {
                            cursor = mARsDBManager4.mContext.getContentResolver().query(MARsTableContract.MARS_SETTINGS_URI, strArr, null, null, null);
                        } catch (Exception e2) {
                            BootReceiver$$ExternalSyntheticOutline0.m(e2, "initMARsSettingsDefinitionInternal : Exception : ", "MARsDBManager");
                            cursor = null;
                        }
                        if (cursor != null) {
                            while (cursor.moveToNext()) {
                                if (cursor.getString(0) != null) {
                                    cursor.getString(0);
                                    cursor.getString(1);
                                    String[][] strArr2 = MARsVersionManager.mMARsSettingsInfoDefault;
                                    Objects.requireNonNull(MARsVersionManager.MARsVersionManagerHolder.INSTANCE);
                                    arrayList2.add(new MARsVersionManager.MARsSettingsInfo());
                                }
                            }
                            cursor.close();
                        } else {
                            Slog.e("MARsDBManager", "initMARsSettingsDefinitionInternal error, no database!!");
                        }
                        if (arrayList2.isEmpty()) {
                            MARsVersionManager.MARsVersionManagerHolder.INSTANCE.getClass();
                            MARsVersionManager.getMARsSettingsInfoFromDefaultValue();
                        } else {
                            MARsVersionManager.MARsVersionManagerHolder.INSTANCE.getClass();
                        }
                        try {
                            query = mARsDBManager4.mContext.getContentResolver().query(MARsTableContract.MARS_POLICY_DEFINITION, new String[]{"bigdataRestriction"}, null, null, null);
                        } catch (SQLiteException unused) {
                            Slog.v("MARsDBManager", "checkBigDataRestrictionColumnExist-sql, catch no column exception!");
                            mARsDBManager4.mIsBigDataRestrictionColumnExist = false;
                        } catch (IllegalArgumentException unused2) {
                            Slog.v("MARsDBManager", "checkBigDataRestrictionColumnExist, catch no column exception!");
                            mARsDBManager4.mIsBigDataRestrictionColumnExist = false;
                        }
                        try {
                            mARsDBManager4.mIsBigDataRestrictionColumnExist = true;
                            if (query != null) {
                                query.close();
                            }
                            String[] strArr3 = mARsDBManager4.mIsBigDataRestrictionColumnExist ? new String[]{"policyNum", "isPolicyEnabled", "targetCategory", "restriction", "killType", "firstTriggerTime", "repeatTriggerTime", "bigdataRestriction"} : new String[]{"policyNum", "isPolicyEnabled", "targetCategory", "restriction", "killType", "firstTriggerTime", "repeatTriggerTime"};
                            ArrayList arrayList3 = new ArrayList();
                            try {
                                cursor2 = mARsDBManager4.mContext.getContentResolver().query(MARsTableContract.MARS_POLICY_DEFINITION, strArr3, null, null, null);
                            } catch (Exception e3) {
                                BootReceiver$$ExternalSyntheticOutline0.m(e3, "initPolicyDefinitionInternal: Exception : ", "MARsDBManager");
                                cursor2 = null;
                            }
                            if (cursor2 != null) {
                                while (cursor2.moveToNext()) {
                                    if (cursor2.getString(0) != null) {
                                        int parseInt = Integer.parseInt(cursor2.getString(0));
                                        int parseInt2 = Integer.parseInt(cursor2.getString(1));
                                        int parseInt3 = Integer.parseInt(cursor2.getString(2));
                                        int parseInt4 = Integer.parseInt(cursor2.getString(3));
                                        int parseInt5 = Integer.parseInt(cursor2.getString(4));
                                        long parseLong = Long.parseLong(cursor2.getString(5));
                                        long parseLong2 = Long.parseLong(cursor2.getString(6));
                                        String[][] strArr4 = MARsVersionManager.mMARsSettingsInfoDefault;
                                        MARsVersionManager.MARsVersionManagerHolder.INSTANCE.getClass();
                                        arrayList3.add(new MARsVersionManager.PolicyInfo(MARsVersionManager.convertPolicyNumToName(parseInt), parseInt, parseInt2, parseInt3, parseInt4, parseInt5, parseLong, parseLong2));
                                    }
                                }
                                cursor2.close();
                            } else {
                                Slog.e("MARsDBManager", "initPolicyDefinitionInternal error, no database!!");
                            }
                            if (arrayList3.isEmpty()) {
                                MARsVersionManager.MARsVersionManagerHolder.INSTANCE.getClass();
                                MARsVersionManager.getPolicyFromDefaultValue();
                            } else {
                                MARsVersionManager.MARsVersionManagerHolder.INSTANCE.getClass();
                                MARsVersionManager.mPolicyInfoList = arrayList3;
                            }
                            String[] strArr5 = {"policyNum", "condition", "matchType", "packageName"};
                            ArrayList arrayList4 = new ArrayList();
                            try {
                                cursor3 = mARsDBManager4.mContext.getContentResolver().query(MARsTableContract.MARS_ADJUST_TARGET_EXCLUDE_PACKAGE, strArr5, null, null, null);
                            } catch (Exception e4) {
                                BootReceiver$$ExternalSyntheticOutline0.m(e4, "initAdjustTargetExcludePackageInternal: Exception : ", "MARsDBManager");
                                cursor3 = null;
                            }
                            if (cursor3 != null) {
                                while (cursor3.moveToNext()) {
                                    if (cursor3.getString(0) != null) {
                                        int parseInt6 = Integer.parseInt(cursor3.getString(0));
                                        int parseInt7 = Integer.parseInt(cursor3.getString(1));
                                        String string3 = cursor3.getString(2);
                                        String string4 = cursor3.getString(3);
                                        String[][] strArr6 = MARsVersionManager.mMARsSettingsInfoDefault;
                                        Objects.requireNonNull(MARsVersionManager.MARsVersionManagerHolder.INSTANCE);
                                        arrayList4.add(new MARsVersionManager.AdjustTargetExcludePackage(parseInt6, parseInt7, string3, string4));
                                    }
                                }
                                cursor3.close();
                            } else {
                                Slog.e("MARsDBManager", "initAdjustTargetExcludePackageInternal error, no database!!");
                            }
                            if (arrayList4.isEmpty()) {
                                MARsVersionManager mARsVersionManager = MARsVersionManager.MARsVersionManagerHolder.INSTANCE;
                                mARsVersionManager.getClass();
                                mARsVersionManager.mExcludeTargetList = MARsListManager.ListManagerHolder.INSTANCE.mExcludePackageDefault;
                            } else {
                                MARsVersionManager.MARsVersionManagerHolder.INSTANCE.mExcludeTargetList = arrayList4;
                            }
                            String[] strArr7 = {"policyNum", "currentImportantValue"};
                            ArrayList arrayList5 = new ArrayList();
                            try {
                                cursor4 = mARsDBManager4.mContext.getContentResolver().query(MARsTableContract.MARS_ADJUST_TARGET_CURRENT_IMPORTANT, strArr7, null, null, null);
                            } catch (Exception e5) {
                                BootReceiver$$ExternalSyntheticOutline0.m(e5, "initAdjustTargetIsCurrentImportantInternal: Exception : ", "MARsDBManager");
                                cursor4 = null;
                            }
                            if (cursor4 != null) {
                                while (cursor4.moveToNext()) {
                                    if (cursor4.getString(0) != null) {
                                        int parseInt8 = Integer.parseInt(cursor4.getString(0));
                                        int parseInt9 = Integer.parseInt(cursor4.getString(1));
                                        String[][] strArr8 = MARsVersionManager.mMARsSettingsInfoDefault;
                                        Objects.requireNonNull(MARsVersionManager.MARsVersionManagerHolder.INSTANCE);
                                        arrayList5.add(new MARsVersionManager.AdjustTargetCurrentImportant(parseInt8, parseInt9));
                                    }
                                }
                                cursor4.close();
                            } else {
                                Slog.e("MARsDBManager", "initAdjustTargetIsCurrentImportantInternal error, no database!!");
                            }
                            if (arrayList5.isEmpty()) {
                                MARsVersionManager.MARsVersionManagerHolder.INSTANCE.getIsCurrentImportantFromDefaultValue();
                            } else {
                                MARsVersionManager.MARsVersionManagerHolder.INSTANCE.setAdjustTargetCurrentImportant(arrayList5);
                            }
                            String[] strArr9 = {"restrictionType", "isAllowed", "callee", "caller", "matchType", "action"};
                            ArrayList arrayList6 = new ArrayList();
                            try {
                                cursor5 = mARsDBManager4.mContext.getContentResolver().query(MARsTableContract.MARS_ADJUST_RESTRICTION, strArr9, null, null, null);
                            } catch (Exception e6) {
                                BootReceiver$$ExternalSyntheticOutline0.m(e6, "initAdjustRestrictionInternal: Exception : ", "MARsDBManager");
                                cursor5 = null;
                            }
                            if (cursor5 != null) {
                                while (cursor5.moveToNext()) {
                                    if (cursor5.getString(0) != null) {
                                        int parseInt10 = Integer.parseInt(cursor5.getString(0));
                                        String string5 = cursor5.getString(1);
                                        String normalText = MARsVersionManager.toNormalText(cursor5.getString(2));
                                        String normalText2 = MARsVersionManager.toNormalText(cursor5.getString(3));
                                        String string6 = cursor5.getString(4);
                                        String normalText3 = MARsVersionManager.toNormalText(cursor5.getString(5));
                                        if (parseInt10 == 12) {
                                            boolean z3 = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
                                            boolean z4 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
                                            ArrayList arrayList7 = freecessController.mPendingIntents;
                                            if (arrayList7 != null) {
                                                if (!arrayList7.contains(normalText3)) {
                                                    if ("block".equals(string5) || FreecessController.productModel.startsWith(string5)) {
                                                        freecessController.mPendingIntents.add(normalText3);
                                                    }
                                                    boolean z5 = MARsPolicyManager.MARs_ENABLE;
                                                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
                                                    if (MARsPolicyManager.isChinaPolicyEnabled() && "blockchn".equals(string5)) {
                                                        freecessController.mPendingIntents.add(normalText3);
                                                    }
                                                }
                                            }
                                            if (freecessController.mPendingIntentsIdle != null && "idle".equals(string5)) {
                                                freecessController.mPendingIntentsIdle.add(normalText3);
                                            }
                                        } else if (parseInt10 == 13) {
                                            boolean z6 = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
                                            boolean z7 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            FreecessController freecessController2 = FreecessController.FreecessControllerHolder.INSTANCE;
                                            ArrayList arrayList8 = freecessController2.mPendingBlocklistForGPS;
                                            if (arrayList8 != null && !arrayList8.contains(normalText)) {
                                                freecessController2.mPendingBlocklistForGPS.add(normalText);
                                            }
                                        } else if (parseInt10 != 16) {
                                            Objects.requireNonNull(MARsVersionManager.MARsVersionManagerHolder.INSTANCE);
                                            arrayList6.add(new MARsVersionManager.AdjustRestriction(string5, normalText, normalText2, string6, normalText3, parseInt10));
                                        } else {
                                            boolean z8 = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
                                            boolean z9 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                            FreecessController freecessController3 = FreecessController.FreecessControllerHolder.INSTANCE;
                                            ArrayList arrayList9 = freecessController3.mOLAFBlockList;
                                            if (arrayList9 != null && !arrayList9.contains(normalText)) {
                                                freecessController3.mOLAFBlockList.add(normalText);
                                            }
                                        }
                                    }
                                }
                                cursor5.close();
                            } else {
                                Slog.e("MARsDBManager", "initAdjustRestrictionInternal error, no database!!");
                            }
                            if (arrayList6.isEmpty()) {
                                MARsVersionManager mARsVersionManager2 = MARsVersionManager.MARsVersionManagerHolder.INSTANCE;
                                mARsVersionManager2.getClass();
                                mARsVersionManager2.mRestrictionList = MARsListManager.ListManagerHolder.INSTANCE.mAdjustRestrictionDefault;
                            } else {
                                MARsVersionManager.MARsVersionManagerHolder.INSTANCE.mRestrictionList = arrayList6;
                            }
                        } finally {
                        }
                    } else {
                        MARsVersionManager mARsVersionManager3 = MARsVersionManager.MARsVersionManagerHolder.INSTANCE;
                        mARsVersionManager3.getClass();
                        MARsVersionManager.getMARsSettingsInfoFromDefaultValue();
                        MARsVersionManager.getPolicyFromDefaultValue();
                        MARsListManager mARsListManager = MARsListManager.ListManagerHolder.INSTANCE;
                        mARsVersionManager3.mExcludeTargetList = mARsListManager.mExcludePackageDefault;
                        mARsVersionManager3.getIsCurrentImportantFromDefaultValue();
                        mARsVersionManager3.mRestrictionList = mARsListManager.mAdjustRestrictionDefault;
                    }
                    ArrayList arrayList10 = MARsVersionManager.MARsVersionManagerHolder.INSTANCE.mRestrictionList;
                    boolean z10 = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
                    boolean z11 = MARsPolicyManager.MARs_ENABLE;
                    MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                    mARsPolicyManager.getClass();
                    for (int i2 = 0; i2 < MARsVersionManager.mPolicyInfoList.size(); i2++) {
                        String str = ((MARsVersionManager.PolicyInfo) MARsVersionManager.mPolicyInfoList.get(i2)).name;
                        int i3 = ((MARsVersionManager.PolicyInfo) MARsVersionManager.mPolicyInfoList.get(i2)).num;
                        int i4 = ((MARsVersionManager.PolicyInfo) MARsVersionManager.mPolicyInfoList.get(i2)).enabled;
                        int i5 = ((MARsVersionManager.PolicyInfo) MARsVersionManager.mPolicyInfoList.get(i2)).restriction;
                        MARsPolicyManager.Policy policy2 = new MARsPolicyManager.Policy(i3, ((MARsVersionManager.PolicyInfo) MARsVersionManager.mPolicyInfoList.get(i2)).action, str, i4 == 1);
                        if (i3 == 1) {
                            mARsPolicyManager.appLockerPolicy = policy2;
                        } else if (i3 == 2) {
                            mARsPolicyManager.autoRunPolicy = policy2;
                        } else if (i3 == 4) {
                            mARsPolicyManager.freecessPolicy = policy2;
                        } else {
                            if (i3 == 8) {
                                mARsPolicyManager.disablePolicy = policy2;
                            }
                        }
                    }
                    boolean z12 = MARsDebugConfig.DEBUG_ENG;
                    if (z12) {
                        Slog.d("MARsPolicyManager", "createPolicies---AL = " + mARsPolicyManager.appLockerPolicy + " , AR = " + mARsPolicyManager.autoRunPolicy + " , FZ = " + mARsPolicyManager.freecessPolicy);
                    }
                    mARsPolicyManager.sbikePolicy = new MARsPolicyManager.Policy(6, 9, "sbkiepolicy", SemCscFeature.getInstance().getString("CscFeature_Common_ConfigBikeMode").contains("bikemode"));
                    mARsPolicyManager.gamePolicy = new MARsPolicyManager.Policy(9, 10, "gamePolicy", SystemProperties.getBoolean("sys.config.mars.game_policy", true));
                    boolean z13 = MARsPolicyManager.MARs_ENABLE;
                    MARsPolicyManager mARsPolicyManager2 = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                    if (MARsPolicyManager.isChinaModel && (policy = mARsPolicyManager2.autoRunPolicy) != null) {
                        policy.enabled = true;
                    }
                    MARsPolicyManager.Policy policy3 = mARsPolicyManager2.freecessPolicy;
                    if (policy3 != null) {
                        boolean z14 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                        FreecessController freecessController4 = FreecessController.FreecessControllerHolder.INSTANCE;
                        AnyMotionDetector$$ExternalSyntheticOutline0.m("FreecessController", new StringBuilder("getFreecessEnabledConfig mIsKernelSupportFreecess = "), freecessController4.mIsKernelSupportFreecess);
                        policy3.enabled = freecessController4.mIsKernelSupportFreecess;
                    }
                    PackageManager packageManager = mARsPolicyManager2.mContext.getPackageManager();
                    Intent intent = new Intent();
                    intent.setAction("com.samsung.android.sm.ACTION_AUTO_RUN");
                    intent.setPackage(MARsPolicyManager.SMART_MANAGER_PKG_NAME);
                    ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 0);
                    if (resolveActivity != null && resolveActivity.activityInfo.isEnabled()) {
                        if (z12) {
                            Slog.d("MARsPolicyManager", "App StartUp History is enabled");
                        }
                        MARsPolicyManager.App_StartUp_History = true;
                    }
                    MARsPolicyManager.Policy policy4 = mARsPolicyManager2.sbikePolicy;
                    if (policy4 != null && policy4.enabled) {
                        MARsTrigger mARsTrigger = MARsTrigger.MARsTriggerHolder.INSTANCE;
                        mARsTrigger.getClass();
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction("com.android.server.am.MARS_TRIGGER_SBIKE_MODE_POLICY");
                        intentFilter.addAction("com.android.server.am.MARS_CANCEL_SBIKE_MODE_POLICY");
                        mARsTrigger.mContext.registerReceiver(mARsTrigger.mPolicySBikeIntentReceiver, intentFilter, 2);
                    }
                    MARsPolicyManager.Policy policy5 = mARsPolicyManager2.gamePolicy;
                    if (policy5 != null && policy5.enabled) {
                        MARsTrigger mARsTrigger2 = MARsTrigger.MARsTriggerHolder.INSTANCE;
                        mARsTrigger2.getClass();
                        IntentFilter intentFilter2 = new IntentFilter();
                        intentFilter2.addAction("com.android.server.am.MARS_TRIGGER_GAME_MODE_POLICY");
                        intentFilter2.addAction("com.android.server.am.MARS_CANCEL_GAME_MODE_POLICY");
                        mARsTrigger2.mContext.registerReceiver(mARsTrigger2.mPolicyGameIntentReceiver, intentFilter2, 2);
                    }
                    MARsTrigger mARsTrigger3 = MARsTrigger.MARsTriggerHolder.INSTANCE;
                    mARsTrigger3.getClass();
                    IntentFilter intentFilter3 = new IntentFilter();
                    intentFilter3.addAction("com.sec.android.sdhms.action.HIGH_CPU_USAGE");
                    intentFilter3.addAction("com.sec.android.sdhms.action.HIGH_CPU_USAGE_APP");
                    mARsTrigger3.mContext.registerReceiver(mARsTrigger3.mTCPUReceiver, intentFilter3, 2);
                    if (MARsDebugConfig.DEBUG_MARs && mARsPolicyManager2.autoRunPolicy != null && mARsPolicyManager2.freecessPolicy != null && mARsPolicyManager2.sbikePolicy != null && mARsPolicyManager2.disablePolicy != null && mARsPolicyManager2.gamePolicy != null) {
                        StringBuilder sb = new StringBuilder("FC = ");
                        sb.append(mARsPolicyManager2.freecessPolicy.enabled);
                        sb.append(", AR = ");
                        sb.append(mARsPolicyManager2.autoRunPolicy.enabled);
                        sb.append(", PD = ");
                        sb.append(mARsPolicyManager2.disablePolicy.enabled);
                        sb.append(", SB = ");
                        sb.append(mARsPolicyManager2.sbikePolicy.enabled);
                        sb.append(", GA = ");
                        ProxyManager$$ExternalSyntheticOutline0.m("MARsPolicyManager", sb, mARsPolicyManager2.gamePolicy.enabled);
                    }
                    if (z2) {
                        return;
                    }
                    MARsUtils.cancelAllPolicy();
                    return;
                case 6:
                    Bundle data5 = message.getData();
                    boolean z15 = data5.getBoolean("onCreate", false);
                    boolean z16 = data5.getBoolean("onUpgrade", false);
                    boolean z17 = data5.getBoolean("onInit", false);
                    if (MARsDebugConfig.DEBUG_MARs) {
                        AnyMotionDetector$$ExternalSyntheticOutline0.m("MARsDBHandler", FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m("Received MARS_DB_SM_CHANGED_MSG, --mDBCreate = ", z15, " --onUpgrade = ", z16, " --onInit = "), z17);
                    }
                    if (z17) {
                        MARsUtils.cancelAllPolicy();
                        this.m.getSettingsValueFromDB(false);
                    }
                    if (z16 || z17) {
                        boolean z18 = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
                        boolean z19 = MARsPolicyManager.MARs_ENABLE;
                        MARsPolicyManager mARsPolicyManager3 = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                        mARsPolicyManager3.getClass();
                        synchronized (MARsPolicyManager.MARsLock) {
                            try {
                                if (mARsPolicyManager3.mMARsTargetPackages.mMap.size() > 0) {
                                    mARsPolicyManager3.mMARsTargetPackages.mMap.clear();
                                }
                                if (mARsPolicyManager3.mMARsRestrictedPackages.mMap.size() > 0) {
                                    mARsPolicyManager3.mMARsRestrictedPackages.mMap.clear();
                                }
                            } finally {
                            }
                        }
                    }
                    if (z15 || z16 || z17) {
                        if (MARsUtils.isChinaPolicyEnabled()) {
                            boolean z20 = MARsPolicyManager.MARs_ENABLE;
                            MARsPolicyManager mARsPolicyManager4 = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                            synchronized (mARsPolicyManager4) {
                                mARsPolicyManager4.mFirstTimeUpdatePackages = true;
                            }
                        }
                        this.m.getDefaultAllowedListDBValues();
                        MARsDBManager mARsDBManager5 = this.m;
                        mARsDBManager5.getClass();
                        ArrayList fASDataFromDB = FASDataManager.FASDataManagerHolder.INSTANCE.getFASDataFromDB();
                        if (fASDataFromDB == null || fASDataFromDB.isEmpty()) {
                            Slog.e("MARsDBManager", "Packages database not exist, and not created!!");
                        } else {
                            MARsUtils.updateMARsTargetPackages(fASDataFromDB);
                        }
                        if (!mARsDBManager5.mRegisteredSmartManagerForcedAppStandbyObserver) {
                            MARsDBHandler mARsDBHandler2 = mARsDBManager5.mMARsDBHandler;
                            synchronized (mARsDBHandler2) {
                                mainHandler = mARsDBHandler2.mMainHandler;
                            }
                            mARsDBManager5.mSmartManagerFASObserver = new MARsDBManager.SmartManagerFASObserver(mARsDBManager5, mainHandler, i);
                            mARsDBManager5.mContext.getContentResolver().registerContentObserver(FASTableContract.SMART_MGR_FORCED_APP_STANDBY_URI, true, mARsDBManager5.mSmartManagerFASObserver, mARsDBManager5.mContext.getUserId());
                            mARsDBManager5.mRegisteredSmartManagerForcedAppStandbyObserver = true;
                        }
                    }
                    if (!z17 || (mainHandler2 = (mARsDBHandler = this.this$0).mMainHandler) == null) {
                        return;
                    }
                    if (mainHandler2.hasMessages(8)) {
                        mARsDBHandler.mMainHandler.removeMessages(8);
                    }
                    mARsDBHandler.mMainHandler.sendMessageDelayed(mARsDBHandler.mMainHandler.obtainMessage(8), 1000L);
                    return;
                case 7:
                    Bundle data6 = message.getData();
                    if (data6 == null) {
                        return;
                    }
                    UserHandle userHandle = new UserHandle(data6.getInt("userId", -1));
                    MARsDBManager mARsDBManager6 = this.m;
                    mARsDBManager6.getClass();
                    try {
                        Context context2 = mARsDBManager6.mContext;
                        context = context2.createPackageContextAsUser(context2.getPackageName(), 0, userHandle);
                    } catch (PackageManager.NameNotFoundException unused3) {
                        context = mARsDBManager6.mContext;
                    }
                    Slog.d("MARsDBHandler", "mContext.id = " + context.getUserId());
                    MARsDBManager.getMARsSettingsInfoForNotificationTime(context);
                    return;
                case 8:
                    MARsDBManager mARsDBManager7 = this.m;
                    mARsDBManager7.getClass();
                    Slog.i("MARsDBManager", "doMigration send MARS_REQUEST_MIGRATE");
                    try {
                        if (mARsDBManager7.mContext.getContentResolver() != null) {
                            mARsDBManager7.mContext.getContentResolver().call(FASTableContract.DC_AUTHORITY_FAS_URI, "FasDataMigration", (String) null, (Bundle) null);
                            return;
                        }
                        return;
                    } catch (Exception e7) {
                        BootReceiver$$ExternalSyntheticOutline0.m(e7, "Error on migrateFasData e = ", "MARsDBManager");
                        return;
                    }
                case 9:
                    MARsDBManager mARsDBManager8 = this.m;
                    boolean isCompareDBVersion = mARsDBManager8.isCompareDBVersion();
                    if (mARsDBManager8.MARsDBVersion == null) {
                        return;
                    }
                    if (isCompareDBVersion) {
                        try {
                            cursor6 = mARsDBManager8.mContext.getContentResolver().query(MARsTableContract.MARS_SETTINGS_URI, new String[]{"value"}, "key=?", new String[]{"restriction_flag"}, null);
                        } catch (Exception e8) {
                            Slog.e("MARsDBManager", "Exception with contentResolver : " + e8.getMessage());
                            e8.printStackTrace();
                        }
                        if (cursor6 != null) {
                            while (cursor6.moveToNext()) {
                                if (cursor6.getString(0) != null) {
                                    Integer.parseInt(cursor6.getString(0));
                                }
                            }
                            cursor6.close();
                        } else {
                            String[][] strArr10 = MARsVersionManager.mMARsSettingsInfoDefault;
                            MARsVersionManager.MARsVersionManagerHolder.INSTANCE.getClass();
                            MARsVersionManager.getRestrictionFlag();
                            Slog.e("MARsDBManager", "getRestrictionFlagFromDB error, no database!!");
                        }
                    } else {
                        MARsVersionManager.MARsVersionManagerHolder.INSTANCE.getClass();
                        MARsVersionManager.getRestrictionFlag();
                    }
                    boolean z21 = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
                    boolean z22 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                    FreecessController.FreecessControllerHolder.INSTANCE.getClass();
                    FreecessTrigger freecessTrigger = FreecessTrigger.FreecessTriggerHolder.INSTANCE;
                    if (freecessTrigger.mIsRegisteredReceiverForEnhancedFreecess) {
                        return;
                    }
                    try {
                        IntentFilter intentFilter4 = new IntentFilter();
                        intentFilter4.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
                        intentFilter4.addAction("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED");
                        intentFilter4.setPriority(1000);
                        freecessTrigger.mContext.registerReceiver(freecessTrigger.mIntentReceiverForBird, intentFilter4, 4);
                        freecessTrigger.mIsRegisteredReceiverForEnhancedFreecess = true;
                        return;
                    } catch (Exception e9) {
                        BootReceiver$$ExternalSyntheticOutline0.m(e9, "exception registerReceiverForBird ", "FreecessTrigger");
                        return;
                    }
                case 10:
                    this.m.getSCPMList();
                    return;
                case 11:
                    MARsDBManager mARsDBManager9 = this.m;
                    mARsDBManager9.getClass();
                    try {
                        Bundle call = mARsDBManager9.mContext.getContentResolver().call(MARsTableContract.SCPM_AUTHORITY_URI, "dc_scpm_mars_app_freeze_settings", (String) null, new Bundle());
                        if (call.getBoolean(KnoxCustomManagerService.SPCM_KEY_RESULT)) {
                            String string7 = call.getString("app_freeze_settings_json");
                            boolean z23 = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
                            boolean z24 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                            FreecessController.FreecessControllerHolder.INSTANCE.setFreecessPolicyFromSCPM(string7);
                            return;
                        }
                        return;
                    } catch (Exception e10) {
                        BootReceiver$$ExternalSyntheticOutline0.m(e10, "getSCPMPolicyForFreecess not available : ", "MARsDBManager");
                        return;
                    }
                case 12:
                    boolean z25 = MARsUtils.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;
                    boolean z26 = MARsPolicyManager.MARs_ENABLE;
                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.updateResetTime();
                    MARsDBHandler mARsDBHandler3 = this.this$0;
                    MainHandler mainHandler3 = mARsDBHandler3.mMainHandler;
                    if (mainHandler3 == null) {
                        return;
                    }
                    mARsDBHandler3.mMainHandler.sendMessageDelayed(mainHandler3.obtainMessage(12), 43200000L);
                    return;
                default:
                    return;
            }
        }
    }

    public static synchronized void getInstance() {
        synchronized (MARsDBHandler.class) {
            MARsDBHandler mARsDBHandler = MARsDBHandlerHolder.INSTANCE;
        }
    }

    public final void sendGetSCPMListMsgToMainHandler(long j) {
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler == null) {
            return;
        }
        mainHandler.removeMessages(10);
        this.mMainHandler.sendMessageDelayed(this.mMainHandler.obtainMessage(10), j);
        Slog.d("MARsDBHandler", "sendGetSCPMListMsgToMainHandler");
    }

    public final void sendGetSCPMPolicyMsgToDBHandler() {
        if (this.mMainHandler == null) {
            return;
        }
        Slog.d("MARsDBHandler", "sendGetSCPMPolicyMsgToDBHandler");
        this.mMainHandler.removeMessages(11);
        this.mMainHandler.sendMessageDelayed(this.mMainHandler.obtainMessage(11), 1000L);
    }

    public final void sendInitSettingMsgToDBHandler() {
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler == null) {
            return;
        }
        this.mMainHandler.sendMessage(mainHandler.obtainMessage(1));
        this.mMainHandler.post(this.mFASDBupdateRunnable);
        MARsDBManager.MARsDBManagerHolder.INSTANCE.getSCPMList();
        sendGetSCPMPolicyMsgToDBHandler();
    }

    public final void sendUpdateAppStartUpInfoMsgToDBHandler(String str, boolean z, String str2, long j) {
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler == null) {
            return;
        }
        Message obtainMessage = mainHandler.obtainMessage(4);
        Bundle bundle = new Bundle();
        bundle.putString("callee", str);
        bundle.putString("caller", str2);
        bundle.putBoolean("isblock", z);
        bundle.putLong("requesttime", j);
        obtainMessage.setData(bundle);
        this.mMainHandler.sendMessage(obtainMessage);
    }

    public final void sendUpdateResetTimeMsgToDBHandler(ArrayList arrayList) {
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler == null) {
            return;
        }
        Message obtainMessage = mainHandler.obtainMessage(2);
        Bundle bundle = new Bundle();
        bundle.putSerializable("values", arrayList);
        obtainMessage.setData(bundle);
        this.mMainHandler.sendMessage(obtainMessage);
    }

    public final void sendUpdateResetTimeSpecificMsgToDBHandler(FASEntity fASEntity) {
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler == null) {
            return;
        }
        Message obtainMessage = mainHandler.obtainMessage(3);
        Bundle bundle = new Bundle();
        bundle.putSerializable("value", fASEntity);
        obtainMessage.setData(bundle);
        this.mMainHandler.sendMessage(obtainMessage);
    }
}
