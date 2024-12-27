package com.android.server.am;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.system.Os;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsBigData;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.filter.FilterManager;
import com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter;
import com.android.server.appop.AppOpsService;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;
import com.android.server.usage.AppStandbyInternal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.IntPredicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MARsHandler {
    public Context mContext;
    public MainHandler mMainHandler;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class MARsHandlerHolder {
        public static final MARsHandler INSTANCE = new MARsHandler();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MainHandler extends Handler {
        public final MARsPolicyManager mMARsPolicyManager;
        public final /* synthetic */ MARsHandler this$0 = MARsHandlerHolder.INSTANCE;
        public Bundle extras = null;

        public MainHandler() {
            boolean z = MARsPolicyManager.MARs_ENABLE;
            this.mMARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            boolean z2;
            int i;
            String str;
            boolean z3;
            Bundle data = message.getData();
            this.extras = data;
            int i2 = message.what;
            int i3 = 0;
            MARsPackageInfo mARsPackageInfo = null;
            switch (i2) {
                case 1:
                case 2:
                    MARsPolicyManager mARsPolicyManager = this.mMARsPolicyManager;
                    if (mARsPolicyManager.getScreenOnState()) {
                        return;
                    }
                    synchronized (mARsPolicyManager) {
                        z = mARsPolicyManager.mCarModeOn;
                    }
                    if (z) {
                        return;
                    }
                    boolean z4 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                    if (FreecessController.FreecessControllerHolder.INSTANCE.mIsDumpstateWorking) {
                        return;
                    }
                    mARsPolicyManager.triggerAction();
                    MARsHandler mARsHandler = this.this$0;
                    if (i2 == 1) {
                        if (MARsPolicyManager.isChinaModel) {
                            mARsHandler.sendRepeatTriggerMsgToMainHandler();
                            return;
                        }
                        return;
                    } else {
                        if (i2 == 2) {
                            mARsHandler.sendRepeatTriggerMsgToMainHandler();
                            return;
                        }
                        return;
                    }
                case 3:
                    MARsPolicyManager mARsPolicyManager2 = this.mMARsPolicyManager;
                    synchronized (mARsPolicyManager2) {
                        z2 = mARsPolicyManager2.mIsManualMode;
                    }
                    if (z2) {
                        return;
                    }
                    if (MARsDebugConfig.DEBUG_ENG) {
                        Slog.d("MARsPolicyManager", "doUpdatePackages called!");
                    }
                    mARsPolicyManager2.updateFromMARsMainThread();
                    this.this$0.sendUpdatePkgMsgToMainHandler(false);
                    return;
                case 4:
                    this.mMARsPolicyManager.getBatteryStats();
                    MARsHandler mARsHandler2 = this.this$0;
                    MainHandler mainHandler = mARsHandler2.mMainHandler;
                    if (mainHandler == null) {
                        return;
                    }
                    mainHandler.removeMessages(4);
                    mARsHandler2.mMainHandler.sendMessageDelayed(
                            mARsHandler2.mMainHandler.obtainMessage(4), 43200000L);
                    return;
                case 5:
                    if (data == null) {
                        return;
                    }
                    String string = data.getString("pkgName", null);
                    int i4 = this.extras.getInt("uid", -1);
                    if (string == null || i4 == -1) {
                        return;
                    }
                    int i5 = this.extras.getInt("mode", 0);
                    MARsPolicyManager mARsPolicyManager3 = this.mMARsPolicyManager;
                    mARsPolicyManager3.getClass();
                    try {
                        if (mARsPolicyManager3.mAppOpsService == null) {
                            mARsPolicyManager3.mAppOpsService =
                                    mARsPolicyManager3.mAm.mAppOpsService;
                        }
                        AppOpsService appOpsService = mARsPolicyManager3.mAppOpsService;
                        if (appOpsService != null) {
                            appOpsService.setMode(70, i4, string, i5, null);
                            return;
                        }
                        return;
                    } catch (RemoteException e) {
                        Slog.e("MARsPolicyManager", "updateFasState exception:" + e);
                        return;
                    }
                case 6:
                    if (data != null) {
                        this.mMARsPolicyManager.onAppUsedForTimeChanged(
                                data.getLong("changedTime"));
                        return;
                    }
                    return;
                case 7:
                    handleSpecificPackage();
                    return;
                case 8:
                    if (data == null) {
                        return;
                    }
                    ArrayList<String> stringArrayList = data.getStringArrayList("packageList");
                    int i6 = this.extras.getInt("policy-num", 0);
                    if (stringArrayList != null) {
                        int i7 = this.extras.getInt("userId", this.this$0.mContext.getUserId());
                        for (int i8 = 0; i8 < stringArrayList.size(); i8++) {
                            this.mMARsPolicyManager.cancelPolicy(
                                    i6, i7, stringArrayList.get(i8), false);
                        }
                        return;
                    }
                    MARsPolicyManager mARsPolicyManager4 = this.mMARsPolicyManager;
                    mARsPolicyManager4.getClass();
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    synchronized (MARsPolicyManager.MARsLock) {
                        for (int i9 = 0;
                                i9 < mARsPolicyManager4.mMARsRestrictedPackages.mMap.size();
                                i9++) {
                            try {
                                SparseArray sparseArray =
                                        (SparseArray)
                                                mARsPolicyManager4.mMARsRestrictedPackages.mMap
                                                        .valueAt(i9);
                                for (int i10 = 0; i10 < sparseArray.size(); i10++) {
                                    MARsPackageInfo mARsPackageInfo2 =
                                            (MARsPackageInfo) sparseArray.valueAt(i10);
                                    mARsPackageInfo2.curLevel = 0;
                                    boolean z5 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    FreecessController freecessController =
                                            FreecessController.FreecessControllerHolder.INSTANCE;
                                    if (freecessController.mIsFreecessEnable) {
                                        if (freecessController.isFreezedPackage(
                                                mARsPackageInfo2.userId, mARsPackageInfo2.name)) {
                                            freecessController.unFreezePackage(
                                                    mARsPackageInfo2.userId,
                                                    mARsPackageInfo2.name,
                                                    "CancelPolicy");
                                        }
                                    }
                                    MARsPolicyManager.Policy policy =
                                            mARsPackageInfo2.appliedPolicy;
                                    if (policy != null && policy.num == i6) {
                                        if (i6 == 6) {
                                            mARsPackageInfo2.sbike = 0;
                                        }
                                        if (i6 == 8
                                                && (mARsPackageInfo2.isDisabled
                                                        || MARsPolicyManager.isDisabledByUser(
                                                                mARsPackageInfo2.disableReason))) {
                                            arrayList.add(mARsPackageInfo2);
                                        } else {
                                            mARsPackageInfo2.appliedPolicy = null;
                                        }
                                    }
                                    if (mARsPackageInfo2.appliedPolicy == null) {
                                        arrayList2.add(mARsPackageInfo2);
                                    }
                                }
                            } finally {
                            }
                        }
                        for (int i11 = 0; i11 < arrayList2.size(); i11++) {
                            MARsPackageInfo mARsPackageInfo3 =
                                    (MARsPackageInfo) arrayList2.get(i11);
                            mARsPolicyManager4.mMARsRestrictedPackages.remove(
                                    mARsPackageInfo3.userId, mARsPackageInfo3.name);
                        }
                    }
                    while (i3 < arrayList.size()) {
                        synchronized (MARsPolicyManager.MARsLock) {
                            MARsPackageInfo mARsPackageInfo4 = (MARsPackageInfo) arrayList.get(i3);
                            if (mARsPackageInfo4 != null) {
                                str = mARsPackageInfo4.name;
                                i = mARsPackageInfo4.userId;
                            } else {
                                i = -1;
                                str = null;
                            }
                        }
                        mARsPolicyManager4.cancelDisablePolicy(str, i, 1);
                        i3++;
                    }
                    return;
                case 9:
                    this.mMARsPolicyManager.triggerAction();
                    return;
                case 10:
                    MARsPolicyManager mARsPolicyManager5 = this.mMARsPolicyManager;
                    synchronized (mARsPolicyManager5) {
                        z3 = mARsPolicyManager5.mIsManualMode;
                    }
                    if (z3) {
                        return;
                    }
                    mARsPolicyManager5.checkUnusedTargetForDeepSleep();
                    Bundle bundle = this.extras;
                    if (bundle != null) {
                        this.this$0.sendUpdateDisableMsgToMainHandler(
                                bundle.getBoolean("debug", false));
                        return;
                    }
                    return;
                case 11:
                    if (data != null) {
                        String string2 = data.getString("extraType", "");
                        ArrayList<String> stringArrayList2 =
                                this.extras.getStringArrayList("packageList");
                        ArrayList<Integer> integerArrayList =
                                this.extras.getIntegerArrayList("uidList");
                        MARsPolicyManager mARsPolicyManager6 = this.mMARsPolicyManager;
                        if (mARsPolicyManager6.mContext != null) {
                            Intent intent = new Intent();
                            intent.addFlags(16777216);
                            intent.setAction("com.sec.android.mars.APP_SLEEP_NOTIFY");
                            intent.setPackage(MARsPolicyManager.SMART_MANAGER_PKG_NAME);
                            intent.putExtra("type", string2);
                            intent.putExtra("specificpackagelist", stringArrayList2);
                            intent.putExtra("specificpackageUidlist", integerArrayList);
                            mARsPolicyManager6.mContext.sendBroadcastAsUser(
                                    intent, new UserHandle(mARsPolicyManager6.mCurrentUserId));
                            mARsPolicyManager6.addDebugInfoToHistory("NOTI", string2);
                            return;
                        }
                        return;
                    }
                    return;
                case 12:
                    if (data != null) {
                        int i12 = data.getInt("userId", this.this$0.mContext.getUserId());
                        MARsPolicyManager mARsPolicyManager7 = this.mMARsPolicyManager;
                        PackageManager packageManager =
                                mARsPolicyManager7.mContext.getPackageManager();
                        List installedPackagesAsUser =
                                packageManager.getInstalledPackagesAsUser(32768, i12);
                        while (i3 < installedPackagesAsUser.size()) {
                            PackageInfo packageInfo = (PackageInfo) installedPackagesAsUser.get(i3);
                            if (packageInfo != null) {
                                String str2 = packageInfo.packageName;
                                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                                if (applicationInfo != null) {
                                    int i13 = applicationInfo.uid;
                                    if (applicationInfo.enabledSetting == 4
                                            && !mARsPolicyManager7.isMARsTarget(i12, str2)
                                            && packageManager.isPackageAutoDisabled(str2, i13)) {
                                        MARsPolicyManager.setEnabledSetting(i12, 1, 1, str2);
                                    }
                                }
                            }
                            i3++;
                        }
                        return;
                    }
                    return;
                case 13:
                    if (data != null) {
                        String string3 = data.getString("pkgName", null);
                        int i14 = this.extras.getInt("userId", -1);
                        if (string3 == null || i14 == -1) {
                            return;
                        }
                        int i15 = this.extras.getInt("bucket");
                        boolean z6 = this.extras.getBoolean("byUser", false);
                        MARsPolicyManager mARsPolicyManager8 = this.mMARsPolicyManager;
                        mARsPolicyManager8.getClass();
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            try {
                                if (mARsPolicyManager8.mAppStandby == null) {
                                    mARsPolicyManager8.mAppStandby =
                                            (AppStandbyInternal)
                                                    LocalServices.getService(
                                                            AppStandbyInternal.class);
                                }
                                mARsPolicyManager8.mAppStandby.setAppStandbyBucketForMARs(
                                        string3, i14, i15, 1792, false, z6);
                            } catch (Exception e2) {
                                Slog.e(
                                        "MARsPolicyManager",
                                        "callSetAppStandbyBucket exception:" + e2);
                            }
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return;
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                    return;
                case 14:
                    if (data != null) {
                        String string4 = data.getString("pkgName", null);
                        int i16 = this.extras.getInt("uid", -1);
                        int i17 = this.extras.getInt("userId", -1);
                        int i18 = this.extras.getInt("minAdj", -1);
                        boolean z7 = this.extras.getBoolean("allowRestart", true);
                        String string5 = this.extras.getString("reason", null);
                        StringBuilder m =
                                StorageManagerService$$ExternalSyntheticOutline0.m(
                                        i16,
                                        "kill package processes. pkgName=",
                                        string4,
                                        ", uid=",
                                        ", userId=");
                        ServiceKeeper$$ExternalSyntheticOutline0.m(
                                i17, i18, ", minAdj=", ", allowRestart=", m);
                        m.append(z7);
                        m.append(", reason=");
                        m.append(string5);
                        Slog.d("MARsHandler", m.toString());
                        if (string4 == null
                                || i16 == -1
                                || i17 == -1
                                || i18 == -1
                                || string5 == null) {
                            return;
                        }
                        this.mMARsPolicyManager.mAm.killProcessForMARs(
                                i16, i17, string4, string5, i18, z7);
                        return;
                    }
                    return;
                case 15:
                    if (data == null) {
                        return;
                    }
                    String string6 = data.getString("pkgName", null);
                    int i19 = this.extras.getInt("uid", -1);
                    if (string6 == null || i19 == -1) {
                        return;
                    }
                    MARsPolicyManager mARsPolicyManager9 = this.mMARsPolicyManager;
                    mARsPolicyManager9.getClass();
                    synchronized (MARsPolicyManager.MARsLock) {
                        try {
                            MARsPackageInfo mARsPackageInfo5 =
                                    MARsPolicyManager.getMARsPackageInfo(
                                            mARsPolicyManager9.mMARsTargetPackages,
                                            string6,
                                            UserHandle.getUserId(i19));
                            if (mARsPackageInfo5 != null
                                    && mARsPackageInfo5.disableResetTime > 0
                                    && System.currentTimeMillis()
                                                    - mARsPackageInfo5.disableResetTime
                                            >= 259200000
                                    && mARsPackageInfo5.BatteryUsage
                                            >= MARsPolicyManager.FGS_BATTERY_USAGE_THRESHOLD
                                    && MARsPolicyManager.isAnomalyFGSPackage(i19)) {
                                MARsHandlerHolder.INSTANCE.sendAnomalyMsgToMainHandler(
                                        mARsPackageInfo5.uid,
                                        mARsPackageInfo5.name,
                                        "excessive_fgs");
                                mARsPackageInfo = mARsPackageInfo5;
                            }
                        } finally {
                        }
                    }
                    if (mARsPackageInfo != null) {
                        MARsBigData mARsBigData = MARsBigData.MARsBigDataHolder.INSTANCE;
                        mARsBigData.getClass();
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject
                                    .put("PKGN", mARsPackageInfo.name)
                                    .put("UID", mARsPackageInfo.uid)
                                    .put(
                                            "NUSD",
                                            (System.currentTimeMillis()
                                                            - mARsPackageInfo.disableResetTime)
                                                    / ClipboardService
                                                            .DEFAULT_CLIPBOARD_TIMEOUT_MILLIS)
                                    .put("BUSE", mARsPackageInfo.BatteryUsage);
                        } catch (JSONException e3) {
                            e3.printStackTrace();
                        }
                        String jSONObject2 = jSONObject.toString();
                        mARsBigData.sendBigData(
                                "FGSN", jSONObject2.substring(1, jSONObject2.length() - 1));
                        return;
                    }
                    return;
                case 16:
                    if (data != null) {
                        String string7 = data.getString("pkgName", null);
                        int i20 = this.extras.getInt("uid", -1);
                        String string8 = this.extras.getString("type");
                        if (string7 == null || i20 == -1) {
                            return;
                        }
                        MARsPolicyManager mARsPolicyManager10 = this.mMARsPolicyManager;
                        if (mARsPolicyManager10.mContext != null) {
                            Intent intent2 = new Intent();
                            intent2.addFlags(16777216);
                            intent2.setAction("com.sec.android.sdhms.action.FGS_ANOMALY");
                            intent2.putExtra("pkgName", string7);
                            intent2.putExtra("userId", UserHandle.getUserId(i20));
                            intent2.putExtra("uid", i20);
                            intent2.putExtra("type", string8);
                            intent2.setPackage("com.sec.android.sdhms");
                            mARsPolicyManager10.mContext.sendBroadcastAsUser(
                                    intent2, new UserHandle(mARsPolicyManager10.mCurrentUserId));
                            Slog.d(
                                    "MARsPolicyManager",
                                    "notifyAnomalyApp pkgname:"
                                            + string7
                                            + " uid:"
                                            + i20
                                            + " type:"
                                            + string8);
                            StringBuilder sb = new StringBuilder("[");
                            sb.append(string8);
                            sb.append("]");
                            sb.append(i20);
                            mARsPolicyManager10.addDebugInfoToHistory("NOTI", sb.toString());
                            return;
                        }
                        return;
                    }
                    return;
                case 17:
                    if (data == null) {
                        return;
                    }
                    Slog.d("MARsHandler", "TCPU: case MARS_MH_NOTIFY_ANOMALY_MSG_TCPU");
                    ArrayList<Integer> integerArrayList2 =
                            this.extras.getIntegerArrayList("pidList");
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    final ArrayList arrayList5 = new ArrayList();
                    Iterator<Integer> it = integerArrayList2.iterator();
                    while (it.hasNext()) {
                        Integer next = it.next();
                        final int parentPid = Process.getParentPid(next.intValue());
                        if (parentPid != 2) {
                            if (Arrays.stream(
                                            Process.getPidsForCommands(
                                                    new String[] {"zygote", "zygote64"}))
                                    .anyMatch(
                                            new IntPredicate() { // from class:
                                                                 // com.android.server.am.MARsHandler$MainHandler$$ExternalSyntheticLambda0
                                                @Override // java.util.function.IntPredicate
                                                public final boolean test(int i21) {
                                                    return i21 == parentPid;
                                                }
                                            })) {
                                arrayList3.add(next);
                            } else {
                                arrayList4.add(next);
                                if (Process.getUidForPid(next.intValue()) >= 10000) {
                                    arrayList5.add(next);
                                }
                            }
                        }
                    }
                    try {
                        Slog.i(
                                "MARsHandler",
                                "TCPU: java pid=" + arrayList3 + " native pid=" + arrayList4);
                        File dumpStackTraces =
                                StackTracesDumpHelper.dumpStackTraces(
                                        arrayList3,
                                        null,
                                        null,
                                        CompletableFuture.completedFuture(arrayList4),
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null,
                                        null);
                        File file = new File("/data/log/TCPU.log");
                        if (dumpStackTraces.renameTo(file)) {
                            Os.chmod(file.getPath(), FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
                            Os.chown(file.getPath(), 1000, 1007);
                        } else {
                            Slog.w("MARsHandler", "Log file not generated.");
                        }
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                    final MARsPolicyManager mARsPolicyManager11 = this.mMARsPolicyManager;
                    if (mARsPolicyManager11.mContext != null) {
                        Intent intent3 = new Intent();
                        intent3.addFlags(16777216);
                        intent3.setAction("com.sec.android.sdhms.action.TCPU_LOG");
                        intent3.putExtra("type", "tcpu_log");
                        mARsPolicyManager11.mContext.sendBroadcastAsUser(
                                intent3, new UserHandle(mARsPolicyManager11.mCurrentUserId));
                    }
                    if (arrayList5.isEmpty()) {
                        return;
                    }
                    new Thread() { // from class: com.android.server.am.MARsPolicyManager.1
                        public final /* synthetic */ List val$nativePidList;

                        public AnonymousClass1(final List arrayList52) {
                            r2 = arrayList52;
                        }

                        @Override // java.lang.Thread, java.lang.Runnable
                        public final void run() {
                            try {
                                ArrayList arrayList6 = new ArrayList();
                                for (Integer num : r2) {
                                    synchronized (MARsPolicyManager.this.mAm.mPidsSelfLocked) {
                                        try {
                                            if (MARsPolicyManager.this.mAm.mPidsSelfLocked.get(
                                                            num.intValue())
                                                    == null) {
                                                arrayList6.add(num);
                                            }
                                        } finally {
                                        }
                                    }
                                }
                                Iterator it2 = arrayList6.iterator();
                                while (it2.hasNext()) {
                                    Integer num2 = (Integer) it2.next();
                                    Slog.d(
                                            "MARsPolicyManager",
                                            "TCPU : kill phantom uid "
                                                    + Process.getUidForPid(num2.intValue())
                                                    + ", pid "
                                                    + num2);
                                    Process.killProcess(num2.intValue());
                                }
                                MARsPolicyManager.this.addDebugInfoToHistory(
                                        "TCPU", "kill phantom processes " + arrayList6);
                            } catch (Exception e5) {
                                BootReceiver$$ExternalSyntheticOutline0.m(
                                        e5,
                                        "Error occurred in killPhantomProcessLocked() ",
                                        "MARsPolicyManager");
                            }
                        }
                    }.start();
                    return;
                case 18:
                    if (data != null) {
                        this.mMARsPolicyManager.disablePackageListForSpecific(
                                "added_from_mars_auto_specific",
                                data.getParcelableArrayList("AppRestrictionInfo"));
                        return;
                    }
                    return;
                case 19:
                    if (data != null) {
                        String string9 = data.getString("pkgName", null);
                        int i21 = this.extras.getInt("uid", -1);
                        if (string9 == null || i21 == -1) {
                            return;
                        }
                        MARsPolicyManager mARsPolicyManager12 = this.mMARsPolicyManager;
                        mARsPolicyManager12.getClass();
                        Slog.d(
                                "MARsPolicyManager",
                                "resetAbusiveEvents uid:" + i21 + " pkgname:" + string9);
                        synchronized (MARsPolicyManager.MARsLock) {
                            try {
                                MARsPackageInfo mARsPackageInfo6 =
                                        MARsPolicyManager.getMARsPackageInfo(
                                                mARsPolicyManager12.mMARsTargetPackages,
                                                string9,
                                                UserHandle.getUserId(i21));
                                if (mARsPackageInfo6 != null) {
                                    Slog.d(
                                            "MARsPolicyManager",
                                            "disable restriction uid:"
                                                    + i21
                                                    + " pkgname:"
                                                    + string9
                                                    + " type:excessive_unfreeze");
                                    MARsPolicyManager.resetAbusiveFlag(mARsPackageInfo6);
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    return;
                default:
                    throw new IllegalStateException("Unexpected value: " + message.what);
            }
        }

        public final void handleSpecificPackage() {
            boolean z;
            MARsPackageInfo mARsPackageInfo;
            Bundle bundle = this.extras;
            if (bundle == null) {
                return;
            }
            ArrayList<String> stringArrayList = bundle.getStringArrayList("packageList");
            char c = 0;
            int i = this.extras.getInt("policy-num", 0);
            int i2 = this.extras.getInt("userId", this.this$0.mContext.getUserId());
            if (i != 0) {
                if (i == 6) {
                    if (stringArrayList != null) {
                        MARsPolicyManager mARsPolicyManager = this.mMARsPolicyManager;
                        mARsPolicyManager.getClass();
                        for (int i3 = 0; i3 < stringArrayList.size(); i3++) {
                            String str = stringArrayList.get(i3);
                            if (str != null) {
                                synchronized (MARsPolicyManager.MARsLock) {
                                    MARsPackageInfo mARsPackageInfo2 =
                                            MARsPolicyManager.getMARsPackageInfo(
                                                    mARsPolicyManager.mMARsTargetPackages, str, i2);
                                    if (mARsPackageInfo2 != null
                                            && mARsPackageInfo2.maxLevel != 4
                                            && i == 6) {
                                        mARsPackageInfo2.sbike = 1;
                                    }
                                }
                            }
                        }
                    }
                    this.mMARsPolicyManager.forceRunPolicyForSpecificPolicy(null, i);
                    return;
                }
                if (i == 9) {
                    this.mMARsPolicyManager.forceRunPolicyForSpecificPolicy(stringArrayList, i);
                    return;
                }
                MARsPolicyManager mARsPolicyManager2 = this.mMARsPolicyManager;
                mARsPolicyManager2.getClass();
                ArrayMap arrayMap = new ArrayMap();
                ArrayList arrayList = new ArrayList();
                StringBuilder sb = new StringBuilder();
                MARsPolicyManager.Policy policy = mARsPolicyManager2.getPolicy(i);
                if (policy == null || !policy.enabled) {
                    Slog.v("MARsPolicyManager", "policy is not exist or not enabled!");
                    return;
                }
                ActiveMusicRecordFilter.ActiveMusicRecordFilterHolder.INSTANCE
                        .getUidListUsingAudio();
                synchronized (MARsPolicyManager.MARsLock) {
                    int i4 = 0;
                    while (i4 < stringArrayList.size()) {
                        String str2 = stringArrayList.get(i4);
                        int userId = mARsPolicyManager2.mContext.getUserId();
                        if (str2 == null || !str2.contains(",")) {
                            mARsPackageInfo =
                                    MARsPolicyManager.getMARsPackageInfo(
                                            mARsPolicyManager2.mMARsTargetPackages, str2, userId);
                        } else {
                            String[] split = str2.split(",");
                            if (split == null || split.length != 2) {
                                mARsPackageInfo = null;
                            } else {
                                String str3 = split[c];
                                if (str3 == null) {
                                    str3 = null;
                                }
                                try {
                                    String str4 = split[1];
                                    if (str4 != null) {
                                        userId = Integer.parseInt(str4);
                                    }
                                } catch (NumberFormatException unused) {
                                    Slog.e(
                                            "MARsPolicyManager",
                                            "forceRunPolicyForSpecificPackage parseInt error!");
                                }
                                mARsPackageInfo =
                                        MARsPolicyManager.getMARsPackageInfo(
                                                mARsPolicyManager2.mMARsTargetPackages,
                                                str3,
                                                userId);
                            }
                        }
                        if (mARsPackageInfo != null) {
                            String str5 = mARsPackageInfo.name;
                            int i5 = mARsPackageInfo.userId;
                            SparseArray sparseArray = (SparseArray) arrayMap.get(str5);
                            if (sparseArray == null) {
                                sparseArray = new SparseArray(2);
                                arrayMap.put(str5, sparseArray);
                            }
                            sparseArray.put(i5, mARsPackageInfo);
                        }
                        i4++;
                        c = 0;
                    }
                    for (int i6 = 0; i6 < arrayMap.size(); i6++) {
                        SparseArray sparseArray2 = (SparseArray) arrayMap.valueAt(i6);
                        for (int i7 = 0; i7 < sparseArray2.size(); i7++) {
                            MARsPackageInfo mARsPackageInfo3 =
                                    (MARsPackageInfo) sparseArray2.valueAt(i7);
                            if (!mARsPolicyManager2.isInPolicyExceptionList(
                                    mARsPackageInfo3.userId, policy.num, mARsPackageInfo3.name)) {
                                FilterManager filterManager =
                                        FilterManager.FilterManagerHolder.INSTANCE;
                                String str6 = mARsPackageInfo3.name;
                                int i8 = mARsPackageInfo3.userId;
                                int i9 = mARsPackageInfo3.uid;
                                filterManager.getClass();
                                if (FilterManager.filterForSpecificPolicy(1, i8, i9, str6) <= 0) {
                                    MARsPolicyManager.PkgStatusInfo pkgStatusInfo =
                                            new MARsPolicyManager.PkgStatusInfo(
                                                    mARsPackageInfo3.uid,
                                                    mARsPackageInfo3.userId,
                                                    mARsPackageInfo3.name);
                                    MARsPolicyManager.updateInfoToPkgStatus(
                                            mARsPackageInfo3, pkgStatusInfo);
                                    arrayList.add(pkgStatusInfo);
                                }
                            } else if (MARsDebugConfig.DEBUG_ENG) {
                                Slog.v(
                                        "MARsPolicyManager",
                                        "package "
                                                + mARsPackageInfo3.name
                                                + " inPolicyAllowList, don't execute this policy "
                                                + policy);
                            }
                        }
                    }
                }
                ActivityManagerService activityManagerService = mARsPolicyManager2.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        z = false;
                        for (int size = arrayList.size() - 1; size >= 0; size--) {
                            MARsPolicyManager.PkgStatusInfo pkgStatusInfo2 =
                                    (MARsPolicyManager.PkgStatusInfo) arrayList.get(size);
                            boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                            FreecessController freecessController =
                                    FreecessController.FreecessControllerHolder.INSTANCE;
                            if (freecessController.mIsFreecessEnable) {
                                if (freecessController.isFreezedPackage(
                                        pkgStatusInfo2.userId, pkgStatusInfo2.name)) {
                                    freecessController.unFreezePackage(
                                            pkgStatusInfo2.userId, pkgStatusInfo2.name, "SMKill");
                                }
                            }
                            if (mARsPolicyManager2.forceKillPackage(
                                    pkgStatusInfo2.name,
                                    policy,
                                    pkgStatusInfo2.userId,
                                    pkgStatusInfo2.uid)) {
                                pkgStatusInfo2.currentLevel = 3;
                                sb.append(" " + pkgStatusInfo2.uid);
                                z = true;
                            } else {
                                arrayList.remove(size);
                            }
                        }
                    } finally {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                synchronized (MARsPolicyManager.MARsLock) {
                    for (int i10 = 0; i10 < arrayList.size(); i10++) {
                        try {
                            MARsPolicyManager.PkgStatusInfo pkgStatusInfo3 =
                                    (MARsPolicyManager.PkgStatusInfo) arrayList.get(i10);
                            MARsPackageInfo mARsPackageInfo4 =
                                    MARsPolicyManager.getMARsPackageInfo(
                                            mARsPolicyManager2.mMARsTargetPackages,
                                            pkgStatusInfo3.name,
                                            pkgStatusInfo3.userId);
                            if (mARsPackageInfo4 != null) {
                                MARsPolicyManager.updateInfoToMARsPkgStatus(
                                        mARsPackageInfo4, pkgStatusInfo3);
                                if (mARsPackageInfo4.appliedPolicy
                                        != mARsPolicyManager2.disablePolicy) {
                                    mARsPackageInfo4.appliedPolicy = policy;
                                    if (i == 2) {
                                        mARsPolicyManager2.levelChange(2, mARsPackageInfo4);
                                    }
                                }
                                if (mARsPolicyManager2.mMARsRestrictedPackages.get(
                                                mARsPackageInfo4.userId, mARsPackageInfo4.name)
                                        == null) {
                                    mARsPolicyManager2.mMARsRestrictedPackages.put(
                                            mARsPackageInfo4.name,
                                            mARsPackageInfo4.userId,
                                            mARsPackageInfo4);
                                } else if (mARsPackageInfo4.appliedPolicy
                                                == mARsPolicyManager2.disablePolicy
                                        && MARsPolicyManager.isDisabledByUser(
                                                mARsPackageInfo4.disableReason)) {
                                    mARsPackageInfo4.curLevel = 4;
                                    mARsPolicyManager2.mMARsRestrictedPackages.put(
                                            mARsPackageInfo4.name,
                                            mARsPackageInfo4.userId,
                                            mARsPackageInfo4);
                                }
                                Slog.v(
                                        "MARsPolicyManager",
                                        "add mRestrictedPackages "
                                                + mARsPackageInfo4.name
                                                + " policy --"
                                                + mARsPackageInfo4.appliedPolicy);
                            }
                        } finally {
                        }
                    }
                }
                if (z) {
                    mARsPolicyManager2.addDebugInfoToHistory("SM", sb.toString());
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MainThread extends Thread {
        public final int mPriority;
        public final /* synthetic */ MARsHandler this$0 = MARsHandlerHolder.INSTANCE;

        public MainThread() {
            super("MARsMainThread");
            this.mPriority = -2;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Process.setThreadPriority(this.mPriority);
            Looper.prepare();
            this.this$0.mMainHandler = new MainHandler();
            Looper.loop();
        }
    }

    public final void sendAnomalyMsgToMainHandler(int i, String str, String str2) {
        if (this.mMainHandler == null) {
            return;
        }
        Bundle m = FreecessController$$ExternalSyntheticOutline0.m(i, "pkgName", str, "uid");
        m.putString("type", str2);
        Message obtainMessage = this.mMainHandler.obtainMessage(16);
        obtainMessage.setData(m);
        this.mMainHandler.sendMessage(obtainMessage);
    }

    public final void sendCallSetAppStandbyBucketMsgToMainHandler(
            int i, int i2, String str, boolean z) {
        if (this.mMainHandler == null) {
            return;
        }
        Bundle m = FreecessController$$ExternalSyntheticOutline0.m(i, "pkgName", str, "userId");
        m.putInt("bucket", i2);
        m.putBoolean("byUser", z);
        Message obtainMessage = this.mMainHandler.obtainMessage(13);
        obtainMessage.setData(m);
        this.mMainHandler.sendMessage(obtainMessage);
    }

    public final void sendCallSetModeMsgToMainHandler(int i, int i2, String str) {
        if (this.mMainHandler == null) {
            return;
        }
        Bundle m = FreecessController$$ExternalSyntheticOutline0.m(i, "pkgName", str, "uid");
        m.putInt("mode", i2);
        Message obtainMessage = this.mMainHandler.obtainMessage(5);
        obtainMessage.setData(m);
        if (i2 == 1) {
            this.mMainHandler.sendMessage(obtainMessage);
        } else {
            this.mMainHandler.sendMessageAtFrontOfQueue(obtainMessage);
        }
    }

    public final void sendCancelPolicyMsgToMainHandler(int i, int i2, ArrayList arrayList) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("packageList", arrayList);
        bundle.putInt("policy-num", i);
        bundle.putInt("userId", i2);
        Message obtainMessage = this.mMainHandler.obtainMessage(8);
        obtainMessage.setData(bundle);
        this.mMainHandler.sendMessage(obtainMessage);
    }

    public final void sendFirstTriggerMsgToMainHandler() {
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler == null) {
            return;
        }
        mainHandler.removeMessages(1);
        Message obtainMessage = this.mMainHandler.obtainMessage(1);
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
        if (MARsPolicyManager.isChinaModel) {
            this.mMainHandler.sendMessageDelayed(obtainMessage, 5000L);
        } else {
            this.mMainHandler.sendMessage(obtainMessage);
        }
    }

    public final void sendInitDisabledMsgToMainHandler(int i) {
        if (this.mMainHandler == null) {
            return;
        }
        Bundle m = SystemUpdateManagerService$$ExternalSyntheticOutline0.m(i, "userId");
        Message obtainMessage = this.mMainHandler.obtainMessage(12);
        obtainMessage.setData(m);
        this.mMainHandler.sendMessage(obtainMessage);
    }

    public final void sendKillPackageProcsMsgToMainHandler(int i, int i2, String str, String str2) {
        if (this.mMainHandler == null) {
            return;
        }
        Bundle m = FreecessController$$ExternalSyntheticOutline0.m(i, "pkgName", str, "uid");
        m.putInt("userId", i2);
        m.putInt("minAdj", 200);
        m.putBoolean("allowRestart", false);
        m.putString("reason", str2);
        Message obtainMessage = this.mMainHandler.obtainMessage(14);
        obtainMessage.setData(m);
        this.mMainHandler.sendMessage(obtainMessage);
    }

    public final void sendNotifyDeviceCareMsgToMainHandler(
            String str, ArrayList arrayList, ArrayList arrayList2) {
        if (this.mMainHandler == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("extraType", str);
        bundle.putStringArrayList("packageList", arrayList);
        bundle.putIntegerArrayList("uidList", arrayList2);
        Message obtainMessage = this.mMainHandler.obtainMessage(11);
        obtainMessage.setData(bundle);
        this.mMainHandler.sendMessage(obtainMessage);
    }

    public final void sendRepeatTriggerMsgToMainHandler() {
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler == null) {
            return;
        }
        mainHandler.removeMessages(2);
        this.mMainHandler.sendMessageDelayed(this.mMainHandler.obtainMessage(2), 5000L);
    }

    public final void sendRunPolicySpecificPkgMsgToMainHandler(
            int i, int i2, String str, ArrayList arrayList) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("packageList", arrayList);
        bundle.putInt("policy-num", i);
        bundle.putString("trigger-reason", str);
        bundle.putInt("userId", i2);
        Message obtainMessage = this.mMainHandler.obtainMessage(7);
        obtainMessage.setData(bundle);
        this.mMainHandler.sendMessage(obtainMessage);
    }

    public final void sendUpdateDisableMsgToMainHandler(boolean z) {
        if (this.mMainHandler == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("debug", z);
        this.mMainHandler.removeMessages(10);
        Message obtainMessage = this.mMainHandler.obtainMessage(10);
        obtainMessage.setData(bundle);
        this.mMainHandler.sendMessageDelayed(
                obtainMessage,
                z
                        ? ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS
                        : BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS);
    }

    public final void sendUpdatePkgMsgToMainHandler(boolean z) {
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler == null) {
            return;
        }
        mainHandler.removeMessages(3);
        this.mMainHandler.sendMessageDelayed(this.mMainHandler.obtainMessage(3), z ? 0L : 600000L);
    }
}
