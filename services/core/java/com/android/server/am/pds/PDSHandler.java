package com.android.server.am.pds;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.IActivityManager;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.PDSController;
import com.android.server.am.mars.database.FASDataManager;
import com.android.server.am.mars.database.FASEntity;
import com.android.server.am.mars.database.FASTableContract;

import com.samsung.android.sdhms.SemAppRestrictionManager;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PDSHandler {
    public Context mContext;
    public MainHandler mMainHandler;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MainHandler extends Handler {
        public final PDSController mPDSController;
        public final /* synthetic */ PDSHandler this$0 = PDSHandlerHolder.INSTANCE;
        public Bundle extras = null;

        public MainHandler() {
            PDSController.Lock lock = PDSController.PDSLock;
            this.mPDSController = PDSController.PDSControllerHolder.INSTANCE;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            Bundle bundle;
            PDSPackageInfo pDSPackageInfo;
            int i;
            ArrayList arrayList;
            NumberFormatException numberFormatException;
            int i2;
            int i3;
            int i4;
            long j;
            PDSPackageInfo pDSPackageInfo2;
            Bundle data = message.getData();
            this.extras = data;
            int i5 = message.what;
            int i6 = 0;
            if (i5 == 1) {
                PDSController pDSController = this.mPDSController;
                synchronized (pDSController) {
                    z = pDSController.mScreenOn;
                }
                if (z || (bundle = this.extras) == null) {
                    return;
                }
                int i7 = bundle.getInt("policy-num", 0);
                if (i7 == 5 || i7 == 10) {
                    pDSController.forceRunPolicyForSpecificPolicy(i7);
                }
                this.this$0.sendFirstTriggerMsgToMainHandler(i7, 300000L);
                return;
            }
            if (i5 == 7) {
                if (data == null) {
                    return;
                }
                ArrayList<String> stringArrayList = data.getStringArrayList("packageList");
                int i8 = this.extras.getInt("policy-num", 0);
                int i9 = this.extras.getInt("userId", this.this$0.mContext.getUserId());
                if (i8 == 5 || i8 == 10) {
                    if (stringArrayList != null) {
                        PDSController pDSController2 = this.mPDSController;
                        pDSController2.getClass();
                        try {
                            IActivityManager service = ActivityManager.getService();
                            if (service != null) {
                                pDSController2.mPDSTargetlist = service.getAllRestrictedList();
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        PDSPkgMap pDSPkgMap = pDSController2.mPDSTargetPackages;
                        pDSPkgMap.mMap.clear();
                        ArrayList fASDataFromDB =
                                FASDataManager.FASDataManagerHolder.INSTANCE.getFASDataFromDB();
                        for (SemAppRestrictionManager.AppRestrictionInfo appRestrictionInfo :
                                pDSController2.mPDSTargetlist) {
                            int i10 = i6;
                            while (true) {
                                if (i10 >= fASDataFromDB.size()) {
                                    arrayList = fASDataFromDB;
                                    break;
                                }
                                FASEntity fASEntity = (FASEntity) fASDataFromDB.get(i10);
                                PDSPackageInfo pDSPackageInfo3 = new PDSPackageInfo();
                                String strPkgName = fASEntity.getStrPkgName();
                                int convertFASReasonToValue =
                                        FASTableContract.convertFASReasonToValue(
                                                fASEntity.getStrFasReason());
                                int i11 = -1;
                                try {
                                    int parseInt =
                                            fASEntity.getStrUid() != null
                                                    ? Integer.parseInt(fASEntity.getStrUid())
                                                    : -1;
                                    try {
                                        i11 = UserHandle.getUserId(parseInt);
                                        long parseLong =
                                                fASEntity.getStrDisableResetTime() != null
                                                        ? Long.parseLong(
                                                                fASEntity.getStrDisableResetTime())
                                                        : 0L;
                                        arrayList = fASDataFromDB;
                                        i3 = i11;
                                        j = parseLong;
                                        i4 = parseInt;
                                    } catch (NumberFormatException e2) {
                                        numberFormatException = e2;
                                        i2 = i11;
                                        i11 = parseInt;
                                        Log.e(
                                                "PDSPackageInfo",
                                                "NumberFormatException !" + numberFormatException);
                                        i3 = i2;
                                        arrayList = fASDataFromDB;
                                        i4 = i11;
                                        j = 0;
                                        pDSPackageInfo3.name = strPkgName;
                                        pDSPackageInfo3.uid = i4;
                                        pDSPackageInfo3.userId = i3;
                                        pDSPackageInfo3.fasType = convertFASReasonToValue;
                                        pDSPackageInfo3.disableResetTime = j;
                                        if (appRestrictionInfo
                                                .getPackageName()
                                                .equals(strPkgName)) {}
                                        i10++;
                                        fASDataFromDB = arrayList;
                                    }
                                } catch (NumberFormatException e3) {
                                    numberFormatException = e3;
                                    i2 = -1;
                                }
                                pDSPackageInfo3.name = strPkgName;
                                pDSPackageInfo3.uid = i4;
                                pDSPackageInfo3.userId = i3;
                                pDSPackageInfo3.fasType = convertFASReasonToValue;
                                pDSPackageInfo3.disableResetTime = j;
                                if (appRestrictionInfo.getPackageName().equals(strPkgName)
                                        || appRestrictionInfo.getUid() != i4) {
                                    i10++;
                                    fASDataFromDB = arrayList;
                                } else {
                                    String packageName = appRestrictionInfo.getPackageName();
                                    SparseArray sparseArray =
                                            (SparseArray) pDSPkgMap.mMap.get(packageName);
                                    if (sparseArray == null) {
                                        sparseArray = new SparseArray(2);
                                        pDSPkgMap.mMap.put(packageName, sparseArray);
                                    }
                                    sparseArray.put(i3, pDSPackageInfo3);
                                }
                            }
                            fASDataFromDB = arrayList;
                            i6 = 0;
                        }
                        for (int i12 = 0; i12 < stringArrayList.size(); i12++) {
                            String str = stringArrayList.get(i12);
                            if (str != null) {
                                synchronized (PDSController.PDSLock) {
                                    PDSPkgMap pDSPkgMap2 = pDSController2.mPDSTargetPackages;
                                    if (pDSPkgMap2 == null || pDSPkgMap2.totalSize() == 0) {
                                        pDSPackageInfo = null;
                                    } else {
                                        SparseArray sparseArray2 =
                                                (SparseArray) pDSPkgMap2.mMap.get(str);
                                        pDSPackageInfo =
                                                (PDSPackageInfo)
                                                        (sparseArray2 == null
                                                                ? null
                                                                : sparseArray2.get(i9));
                                    }
                                    if (pDSPackageInfo != null) {
                                        if (i8 == 5) {
                                            i = 1;
                                            pDSPackageInfo.uds = 1;
                                        } else {
                                            i = 1;
                                        }
                                        if (i8 == 10) {
                                            pDSPackageInfo.mpsm = i;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.mPDSController.forceRunPolicyForSpecificPolicy(i8);
                    return;
                }
                return;
            }
            if (i5 != 8) {
                throw new IllegalStateException("Unexpected value: " + message.what);
            }
            if (data == null) {
                return;
            }
            ArrayList<String> stringArrayList2 = data.getStringArrayList("packageList");
            int i13 = this.extras.getInt("policy-num", 0);
            if (stringArrayList2 != null) {
                int i14 = this.extras.getInt("userId", this.this$0.mContext.getUserId());
                for (int i15 = 0; i15 < stringArrayList2.size(); i15++) {
                    String str2 = stringArrayList2.get(i15);
                    Slog.v("PDSHandler", "cancelPolicy");
                    PDSController pDSController3 = this.mPDSController;
                    pDSController3.getClass();
                    Slog.e("PDSController", "cancelPolicy" + str2);
                    IPackageManager packageManager = AppGlobals.getPackageManager();
                    synchronized (PDSController.PDSLock) {
                        try {
                            PDSPkgMap pDSPkgMap3 = pDSController3.mPDSRestrictedPackages;
                            if (pDSPkgMap3 == null || pDSPkgMap3.totalSize() == 0) {
                                pDSPackageInfo2 = null;
                            } else {
                                SparseArray sparseArray3 = (SparseArray) pDSPkgMap3.mMap.get(str2);
                                pDSPackageInfo2 =
                                        (PDSPackageInfo)
                                                (sparseArray3 == null
                                                        ? null
                                                        : sparseArray3.get(i14));
                            }
                            if (pDSPackageInfo2 != null) {
                                PDSController.Policy policy = pDSPackageInfo2.appliedPolicy;
                                if (policy != null && policy.num == i13) {
                                    pDSPackageInfo2.appliedPolicy = null;
                                    pDSPackageInfo2.curLevel = 0;
                                    if (i13 == 5) {
                                        pDSPackageInfo2.uds = 0;
                                    }
                                    if (i13 == 10) {
                                        pDSPackageInfo2.mpsm = 0;
                                    }
                                }
                                if (pDSPackageInfo2.appliedPolicy == null) {
                                    try {
                                        packageManager.setPackageStoppedState(
                                                pDSPackageInfo2.name,
                                                false,
                                                pDSPackageInfo2.userId);
                                    } catch (RemoteException unused) {
                                    } catch (IllegalArgumentException e4) {
                                        Slog.w(
                                                "PDSController",
                                                "Failed trying to stop package "
                                                        + pDSPackageInfo2.name
                                                        + ": "
                                                        + e4);
                                    }
                                    PDSPkgMap pDSPkgMap4 = pDSController3.mPDSRestrictedPackages;
                                    String str3 = pDSPackageInfo2.name;
                                    int i16 = pDSPackageInfo2.userId;
                                    SparseArray sparseArray4 =
                                            (SparseArray) pDSPkgMap4.mMap.get(str3);
                                    if (sparseArray4 != null) {
                                        sparseArray4.removeReturnOld(i16);
                                        if (sparseArray4.size() == 0) {
                                            pDSPkgMap4.mMap.remove(str3);
                                        }
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                }
                return;
            }
            Slog.v("PDSHandler", "cancelPolicy(policyNum)");
            PDSController pDSController4 = this.mPDSController;
            pDSController4.getClass();
            Slog.e("PDSController", "cancelpolicy" + i13);
            IPackageManager packageManager2 = AppGlobals.getPackageManager();
            ArrayList arrayList2 = new ArrayList();
            synchronized (PDSController.PDSLock) {
                for (int i17 = 0; i17 < pDSController4.mPDSRestrictedPackages.mMap.size(); i17++) {
                    try {
                        SparseArray sparseArray5 =
                                (SparseArray)
                                        pDSController4.mPDSRestrictedPackages.mMap.valueAt(i17);
                        for (int i18 = 0; i18 < sparseArray5.size(); i18++) {
                            PDSPackageInfo pDSPackageInfo4 =
                                    (PDSPackageInfo) sparseArray5.valueAt(i18);
                            PDSController.Policy policy2 = pDSPackageInfo4.appliedPolicy;
                            if (policy2 != null && policy2.num == i13) {
                                pDSPackageInfo4.curLevel = 0;
                                pDSPackageInfo4.appliedPolicy = null;
                                if (i13 == 5) {
                                    pDSPackageInfo4.uds = 0;
                                }
                                if (i13 == 10) {
                                    pDSPackageInfo4.mpsm = 0;
                                }
                            }
                            if (pDSPackageInfo4.appliedPolicy == null) {
                                arrayList2.add(pDSPackageInfo4);
                            }
                        }
                    } finally {
                    }
                }
            }
            for (int i19 = 0; i19 < arrayList2.size(); i19++) {
                PDSPackageInfo pDSPackageInfo5 = (PDSPackageInfo) arrayList2.get(i19);
                try {
                    packageManager2.setPackageStoppedState(
                            pDSPackageInfo5.name, false, pDSPackageInfo5.userId);
                } catch (RemoteException unused2) {
                } catch (IllegalArgumentException e5) {
                    Slog.w(
                            "PDSController",
                            "Failed trying to stop package " + pDSPackageInfo5.name + ": " + e5);
                }
                PDSPkgMap pDSPkgMap5 = pDSController4.mPDSRestrictedPackages;
                String str4 = pDSPackageInfo5.name;
                int i20 = pDSPackageInfo5.userId;
                SparseArray sparseArray6 = (SparseArray) pDSPkgMap5.mMap.get(str4);
                if (sparseArray6 != null) {
                    sparseArray6.removeReturnOld(i20);
                    if (sparseArray6.size() == 0) {
                        pDSPkgMap5.mMap.remove(str4);
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MainThread extends Thread {
        public final int mPriority;
        public final /* synthetic */ PDSHandler this$0 = PDSHandlerHolder.INSTANCE;

        public MainThread() {
            super("PDSMainThread");
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

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class PDSHandlerHolder {
        public static final PDSHandler INSTANCE = new PDSHandler();
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

    public final void sendFirstTriggerMsgToMainHandler(int i, long j) {
        if (this.mMainHandler == null) {
            return;
        }
        Bundle m = SystemUpdateManagerService$$ExternalSyntheticOutline0.m(i, "policy-num");
        this.mMainHandler.removeMessages(1);
        Message obtainMessage = this.mMainHandler.obtainMessage(1);
        obtainMessage.setData(m);
        this.mMainHandler.sendMessageDelayed(obtainMessage, j);
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
}
