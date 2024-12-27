package com.android.server.am.mars;

import android.app.usage.AppStandbyInfo;
import android.content.pm.IPackageManager;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.am.FreecessController;
import com.android.server.am.FreecessPkgStatus;
import com.android.server.am.MARsHandler;
import com.android.server.am.MARsPackageInfo;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.database.FASEntity;
import com.android.server.am.mars.database.FASTableContract;
import com.android.server.am.mars.database.MARsVersionManager;
import com.android.server.am.mars.filter.filter.WidgetPkgFilter;
import com.android.server.usage.AppStandbyInternal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class MARsUtils {
    public static final boolean IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE =
            FreecessController.IS_SUPPORT_FREEZE_FG_SERVICE_FEATURE;

    public static void addFilterDebugInfoToHistory(String str, String str2) {
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        if (mARsPolicyManager.mFilterHistoryBufferArray != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(
                    "["
                            + MARsPolicyManager.formatDateTimeWithoutYear(
                                    System.currentTimeMillis())
                            + "] ");
            sb.append("[" + str + "] ");
            sb.append("[" + str2 + "]\n");
            mARsPolicyManager.mFilterHistoryBufferArray.put(sb.toString());
        }
    }

    public static void cancelAllPolicy() {
        int i;
        String str;
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        mARsPolicyManager.getClass();
        ArrayList arrayList = new ArrayList();
        synchronized (MARsPolicyManager.MARsLock) {
            for (int i2 = 0; i2 < mARsPolicyManager.mMARsRestrictedPackages.mMap.size(); i2++) {
                try {
                    SparseArray sparseArray =
                            (SparseArray)
                                    mARsPolicyManager.mMARsRestrictedPackages.mMap.valueAt(i2);
                    for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                        MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i3);
                        boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                        FreecessController freecessController =
                                FreecessController.FreecessControllerHolder.INSTANCE;
                        if (freecessController.mIsFreecessEnable) {
                            if (freecessController.isFreezedPackage(
                                    mARsPackageInfo.userId, mARsPackageInfo.name)) {
                                freecessController.unFreezePackage(
                                        mARsPackageInfo.userId,
                                        mARsPackageInfo.name,
                                        "CancelPolicy");
                            }
                        }
                        if (mARsPackageInfo.appliedPolicy != null) {
                            if (!mARsPackageInfo.isDisabled
                                    && !MARsPolicyManager.isDisabledByUser(
                                            mARsPackageInfo.disableReason)) {
                                mARsPackageInfo.appliedPolicy = null;
                                mARsPackageInfo.curLevel = 0;
                            }
                            if (MARsPolicyManager.isDisabledByUser(mARsPackageInfo.disableReason)) {
                                mARsPackageInfo.disableReason = 0;
                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
                                if (!MARsPolicyManager.isChinaPolicyEnabled()) {
                                    mARsPackageInfo.fasType = 1;
                                }
                            }
                            arrayList.add(mARsPackageInfo);
                        }
                    }
                } finally {
                }
            }
        }
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            synchronized (MARsPolicyManager.MARsLock) {
                MARsPackageInfo mARsPackageInfo2 = (MARsPackageInfo) arrayList.get(i4);
                if (mARsPackageInfo2 != null) {
                    str = mARsPackageInfo2.name;
                    i = mARsPackageInfo2.userId;
                } else {
                    i = -1;
                    str = null;
                }
            }
            mARsPolicyManager.cancelDisablePolicy(str, i, 0);
        }
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                if (mARsPolicyManager.mMARsRestrictedPackages.mMap.size() > 0) {
                    mARsPolicyManager.mMARsRestrictedPackages.mMap.clear();
                }
            } finally {
            }
        }
    }

    public static boolean getScreenOnState() {
        boolean z = MARsPolicyManager.MARs_ENABLE;
        return MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getScreenOnState();
    }

    public static boolean isChinaPolicyEnabled() {
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
        return MARsPolicyManager.isChinaPolicyEnabled();
    }

    public static void setAllPoliciesOnOffState(int i, boolean z) {
        boolean z2 = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        mARsPolicyManager.getClass();
        if (MARsDebugConfig.DEBUG_MARs) {
            Slog.v("MARsPolicyManager", "setAllPoliciesOnOffState on = " + i);
            mARsPolicyManager.addDebugInfoToHistory("DEV", "spcm_switch : " + i);
        }
        if (mARsPolicyManager.mAllPoliciesOn != i) {
            if (i == 0) {
                mARsPolicyManager.setIsManualMode(true);
                if (mARsPolicyManager.getPackageDisablerEnabled()) {
                    mARsPolicyManager.setPackageDisablerEnabled(false);
                }
            } else if (i == 1) {
                if (!MARsPolicyManager.isChinaModel && MARsPolicyManager.GlobalModelWithChinaSIM) {
                    MARsPolicyManager.GlobalModelWithChinaSIM = false;
                    mARsPolicyManager.switchPolicies(1, z);
                }
                if (!mARsPolicyManager.getPackageDisablerEnabled()) {
                    mARsPolicyManager.setPackageDisablerEnabled(true);
                }
                mARsPolicyManager.setIsManualMode(false);
            } else if (i == 3) {
                MARsPolicyManager.GlobalModelWithChinaSIM = true;
                mARsPolicyManager.switchPolicies(2, z);
                if (!mARsPolicyManager.getPackageDisablerEnabled()) {
                    mARsPolicyManager.setPackageDisablerEnabled(true);
                }
            } else if (i == 9999999) {
                mARsPolicyManager.setIsManualMode(true);
                if (mARsPolicyManager.getPackageDisablerEnabled()) {
                    mARsPolicyManager.setPackageDisablerEnabled(false);
                }
            }
            mARsPolicyManager.mAllPoliciesOn = i;
        }
    }

    public static void setFreezeExcludeList(ArrayList arrayList) {
        boolean z = FreecessController.IS_MINIMIZE_OLAF_LOCK;
        FreecessController freecessController =
                FreecessController.FreecessControllerHolder.INSTANCE;
        synchronized (freecessController.mFreezeExcludePackages) {
            try {
                ((HashSet) freecessController.mFreezeExcludePackages).clear();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    if (!((HashSet) freecessController.mSsrmAllowPackages).contains(str)) {
                        ((HashSet) freecessController.mFreezeExcludePackages)
                                .add(MARsVersionManager.toNormalText(str));
                    }
                }
            } finally {
            }
        }
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                SparseArray sparseArray = freecessController.mFreecessManagedPackages.mUidMap;
                for (int i = 0; i < sparseArray.size(); i++) {
                    FreecessPkgStatus freecessPkgStatus =
                            (FreecessPkgStatus) sparseArray.valueAt(i);
                    if (freecessPkgStatus != null) {
                        if (MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.isMARsTarget(
                                freecessPkgStatus.userId, freecessPkgStatus.name)) {
                            freecessPkgStatus.isInAllowList = false;
                            freecessController.updateAllowListForFreecess(freecessPkgStatus);
                        }
                    }
                }
            } finally {
            }
        }
    }

    public static void setPackagesUnusedLockingTime(int i) {
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        mARsPolicyManager.getClass();
        if (MARsDebugConfig.DEBUG_MARs) {
            ProxyManager$$ExternalSyntheticOutline0.m(
                    i, "setPackagesUnusedLockingTime hours = ", "MARsPolicyManager");
        }
        if (i != 1) {
            mARsPolicyManager.THRESHOLD_POWER_USAGE =
                    mARsPolicyManager.THRESHOLD_POWER_USAGE_BACKUP;
            mARsPolicyManager.isTimeChangedForDebug = false;
            MARsHandler.MARsHandlerHolder.INSTANCE.sendUpdateDisableMsgToMainHandler(false);
            return;
        }
        if (mARsPolicyManager.mAppStandby == null) {
            mARsPolicyManager.mAppStandby =
                    (AppStandbyInternal) LocalServices.getService(AppStandbyInternal.class);
        }
        if (mARsPolicyManager.mAppStandby != null) {
            synchronized (mARsPolicyManager.mMARsTargetPackages) {
                for (int i2 = 0; i2 < mARsPolicyManager.mMARsTargetPackages.mMap.size(); i2++) {
                    try {
                        SparseArray sparseArray =
                                (SparseArray)
                                        mARsPolicyManager.mMARsTargetPackages.mMap.valueAt(i2);
                        for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                            MARsPackageInfo mARsPackageInfo =
                                    (MARsPackageInfo) sparseArray.valueAt(i3);
                            if (mARsPackageInfo != null) {
                                mARsPolicyManager.mAppStandby.restrictApp(
                                        mARsPackageInfo.name, mARsPackageInfo.userId, 4);
                            }
                        }
                    } finally {
                    }
                }
            }
        }
        if (mARsPolicyManager.ENABLE_RESTRICTED_BUCKET) {
            int userId = mARsPolicyManager.mContext.getUserId();
            if (mARsPolicyManager.mAppStandby == null) {
                mARsPolicyManager.mAppStandby =
                        (AppStandbyInternal) LocalServices.getService(AppStandbyInternal.class);
            }
            if (mARsPolicyManager.mAppStandby != null) {
                ArrayList arrayList = new ArrayList();
                synchronized (mARsPolicyManager.mMARsTargetPackages) {
                    for (int i4 = 0; i4 < mARsPolicyManager.mMARsTargetPackages.mMap.size(); i4++) {
                        try {
                            SparseArray sparseArray2 =
                                    (SparseArray)
                                            mARsPolicyManager.mMARsTargetPackages.mMap.valueAt(i4);
                            for (int i5 = 0; i5 < sparseArray2.size(); i5++) {
                                MARsPackageInfo mARsPackageInfo2 =
                                        (MARsPackageInfo) sparseArray2.valueAt(i5);
                                if (mARsPackageInfo2 != null) {
                                    arrayList.add(new AppStandbyInfo(mARsPackageInfo2.name, 45));
                                }
                            }
                        } finally {
                        }
                    }
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    mARsPolicyManager.mAppStandby.setAppStandbyBucketsForMARs(
                            arrayList, userId, 45, 1792, false, true);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        mARsPolicyManager.addDebugInfoToHistory(
                "DEV", "Auto restriction's battery condition changed !");
        mARsPolicyManager.THRESHOLD_POWER_USAGE_BACKUP = mARsPolicyManager.THRESHOLD_POWER_USAGE;
        mARsPolicyManager.THRESHOLD_POWER_USAGE = -2.0d;
        mARsPolicyManager.isTimeChangedForDebug = true;
        mARsPolicyManager.mAutoDeepSleepTimeForDebug = 7200000L;
        MARsHandler.MARsHandlerHolder.INSTANCE.sendUpdateDisableMsgToMainHandler(true);
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.v("MARsPolicyManager", "DEBUGGING mode turned on, skip to check battery usage !");
        }
    }

    public static void updateMARsTargetPackages(ArrayList arrayList) {
        int i;
        boolean contains;
        String str;
        int i2;
        int i3;
        int i4;
        ArrayList arrayList2;
        MARsPackageInfo mARsPackageInfo;
        int i5;
        boolean z;
        boolean z2;
        boolean z3 = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
        mARsPolicyManager.getClass();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        synchronized (MARsPolicyManager.MARsLock) {
            i = mARsPolicyManager.mMARsTargetPackages.totalSize();
        }
        mARsPolicyManager.mNeedtoDisablePackages.clear();
        IPackageManager asInterface =
                IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        if (MARsPolicyManager.isChinaPolicyEnabled()) {
            synchronized (mARsPolicyManager) {
                z2 = mARsPolicyManager.mFirstTimeUpdatePackages;
            }
            if (z2) {
                WidgetPkgFilter.WidgetPkgFilterHolder.INSTANCE.getBoundAppWidgetPackages();
            }
        }
        int i6 = 0;
        boolean z4 = false;
        while (i6 < arrayList.size()) {
            FASEntity fASEntity = (FASEntity) arrayList.get(i6);
            MARsPackageInfo mARsPackageInfo2 = new MARsPackageInfo(fASEntity);
            int convertFASReasonToValue =
                    FASTableContract.convertFASReasonToValue(fASEntity.getStrFasReason());
            try {
                i4 = Integer.parseInt(fASEntity.getStrMode());
                i3 = i;
            } catch (NumberFormatException e) {
                i3 = i;
                Slog.e("MARsPolicyManager", "NumberFormatException !" + e);
                i4 = 0;
            }
            synchronized (MARsPolicyManager.MARsLock) {
                try {
                    arrayList2 = arrayList4;
                    mARsPackageInfo =
                            MARsPolicyManager.getMARsPackageInfo(
                                    mARsPolicyManager.mMARsTargetPackages,
                                    mARsPackageInfo2.name,
                                    mARsPackageInfo2.userId);
                } catch (RemoteException e2) {
                    Slog.e("MARsPolicyManager", "getPkgInfoFromSMToMARs exception:" + e2);
                } finally {
                }
                if (mARsPackageInfo != null) {
                    if (mARsPackageInfo.isFASEnabled == i4
                            && mARsPackageInfo.fasType == convertFASReasonToValue) {
                        if (mARsPackageInfo.isDisabled && mARsPackageInfo.disableReason == 0) {
                            mARsPackageInfo.disableReason = 1;
                        }
                    }
                    mARsPackageInfo.fasType = convertFASReasonToValue;
                    mARsPackageInfo.fasReason = fASEntity.getStrFasReason();
                    if (i4 == 1) {
                        mARsPolicyManager.levelChange(32, mARsPackageInfo);
                    } else {
                        mARsPolicyManager.levelChange(256, mARsPackageInfo);
                        if (mARsPackageInfo.isDisabled
                                || MARsPolicyManager.isDisabledByUser(
                                        mARsPackageInfo.disableReason)) {
                            if (MARsPolicyManager.isDisabledByUser(mARsPackageInfo.disableReason)) {
                                mARsPackageInfo.disableReason = 0;
                            }
                            arrayList5.add(mARsPackageInfo);
                        }
                    }
                } else {
                    arrayList6.add(
                            new Pair(mARsPackageInfo2.name, Integer.valueOf(mARsPackageInfo2.uid)));
                    mARsPackageInfo2.sharedUidName =
                            mARsPolicyManager.getSharedUidName(
                                    mARsPackageInfo2.userId, mARsPackageInfo2.name);
                    if ((mARsPackageInfo2.packageType & 1) != 0) {
                        mARsPackageInfo2.hasAppIcon = true;
                    }
                    if (asInterface != null) {
                        if (asInterface.isPackageAutoDisabled(
                                mARsPackageInfo2.name, mARsPackageInfo2.uid)) {
                            mARsPackageInfo2.maxLevel = 4;
                            mARsPackageInfo2.curLevel = 4;
                            mARsPackageInfo2.isDisabled = true;
                            mARsPackageInfo2.appliedPolicy = mARsPolicyManager.disablePolicy;
                            if (!MARsPolicyManager.isDisabledByUser(mARsPackageInfo2.disableReason)
                                    && mARsPackageInfo2.disableReason != 16) {
                                mARsPackageInfo2.disableReason = 1;
                                mARsPackageInfo2.state = 16;
                            }
                            mARsPolicyManager.mMARsRestrictedPackages.put(
                                    mARsPackageInfo2.name,
                                    mARsPackageInfo2.userId,
                                    mARsPackageInfo2);
                            mARsPackageInfo2.initOptionFlag();
                            mARsPolicyManager.mMARsTargetPackages.put(
                                    mARsPackageInfo2.name,
                                    mARsPackageInfo2.userId,
                                    mARsPackageInfo2);
                        }
                    }
                    if (mARsPackageInfo2.maxLevel == 4
                            && MARsPolicyManager.isDisabledByUser(mARsPackageInfo2.disableReason)) {
                        mARsPackageInfo2.appliedPolicy = mARsPolicyManager.disablePolicy;
                        mARsPolicyManager.mMARsRestrictedPackages.put(
                                mARsPackageInfo2.name, mARsPackageInfo2.userId, mARsPackageInfo2);
                    }
                    mARsPackageInfo2.initOptionFlag();
                    mARsPolicyManager.mMARsTargetPackages.put(
                            mARsPackageInfo2.name, mARsPackageInfo2.userId, mARsPackageInfo2);
                }
            }
            if (MARsPolicyManager.isChinaPolicyEnabled()) {
                synchronized (mARsPolicyManager) {
                    z = mARsPolicyManager.mFirstTimeUpdatePackages;
                }
                if (z && mARsPackageInfo2.isFASEnabled) {
                    i5 = 1;
                    z4 = true;
                    i6 += i5;
                    i = i3;
                    arrayList4 = arrayList2;
                }
            }
            i5 = 1;
            i6 += i5;
            i = i3;
            arrayList4 = arrayList2;
        }
        ArrayList arrayList7 = arrayList4;
        int i7 = i;
        for (int i8 = 0; i8 < arrayList5.size(); i8++) {
            synchronized (MARsPolicyManager.MARsLock) {
                MARsPackageInfo mARsPackageInfo3 = (MARsPackageInfo) arrayList5.get(i8);
                if (mARsPackageInfo3 != null) {
                    str = mARsPackageInfo3.name;
                    i2 = mARsPackageInfo3.userId;
                } else {
                    str = null;
                    i2 = -1;
                }
            }
            mARsPolicyManager.cancelDisablePolicy(str, i2, 0);
        }
        for (int i9 = 0; i9 < arrayList6.size(); i9++) {
            String str2 = (String) ((Pair) arrayList6.get(i9)).first;
            Integer num = (Integer) ((Pair) arrayList6.get(i9)).second;
            synchronized (mARsPolicyManager.mScpmList) {
                contains = mARsPolicyManager.mScpmList.contains(str2);
            }
            if (contains) {
                mARsPolicyManager.mNeedtoDisablePackages.add(new Pair(str2, num));
            }
            Slog.d("MARsPolicyManager", "new Package : " + str2 + ", SCPMTarget : " + contains);
        }
        if (z4) {
            MARsHandler mARsHandler = MARsHandler.MARsHandlerHolder.INSTANCE;
            mARsHandler.mMainHandler.sendMessage(mARsHandler.mMainHandler.obtainMessage(9));
        }
        mARsPolicyManager.deletePkgInfoInMARs(arrayList);
        if (!mARsPolicyManager.mInitDisabledPackage) {
            mARsPolicyManager.mInitDisabledPackage = true;
            MARsHandler.MARsHandlerHolder.INSTANCE.sendInitDisabledMsgToMainHandler(
                    mARsPolicyManager.mContext.getUserId());
        }
        for (int i10 = 0; i10 < mARsPolicyManager.mNeedtoDisablePackages.size(); i10++) {
            String str3 = (String) ((Pair) mARsPolicyManager.mNeedtoDisablePackages.get(i10)).first;
            Integer num2 =
                    (Integer) ((Pair) mARsPolicyManager.mNeedtoDisablePackages.get(i10)).second;
            arrayList3.add(str3);
            arrayList7.add(num2);
            mARsPolicyManager.disablePackageForSpecific(
                    num2.intValue(), str3, "added_from_mars_auto_specific");
        }
        if (!arrayList3.isEmpty()) {
            MARsHandler.MARsHandlerHolder.INSTANCE.sendNotifyDeviceCareMsgToMainHandler(
                    "deepsleepspecific", arrayList3, arrayList7);
        }
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                i7, "updateMARsTargetPackages mMARsTargetPackages.size-", "MARsPolicyManager");
    }
}
