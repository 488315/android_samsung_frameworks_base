package com.android.server.appop;

import android.app.AppOpsManager;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseBooleanArray;

import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.pooled.PooledLambda;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppOpsUidStateTrackerImpl$$ExternalSyntheticLambda1
        implements QuadConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean z;
        ArraySet arraySet;
        int i;
        int i2;
        ArraySet arraySet2;
        int i3;
        int intValue = ((Integer) obj2).intValue();
        int intValue2 = ((Integer) obj3).intValue();
        boolean booleanValue = ((Boolean) obj4).booleanValue();
        AppOpsService appOpsService = ((AppOpsService$$ExternalSyntheticLambda12) obj).f$0;
        synchronized (appOpsService) {
            if (intValue2 == Integer.MAX_VALUE) {
                try {
                    appOpsService.onUidProcessDeathLocked(intValue);
                } finally {
                }
            }
            AppOpsService.UidState uidStateLocked =
                    appOpsService.getUidStateLocked(intValue, false);
            int i4 = 0;
            while (true) {
                if (i4 >= appOpsService.mModeWatchers.size()) {
                    z = false;
                    break;
                }
                AppOpsService.ModeCallback modeCallback =
                        (AppOpsService.ModeCallback) appOpsService.mModeWatchers.valueAt(i4);
                modeCallback.getClass();
                if ((modeCallback.mFlags & 1) != 0) {
                    z = true;
                    break;
                }
                i4++;
            }
            if (uidStateLocked != null && booleanValue && z) {
                SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
                SparseBooleanArray foregroundOps =
                        appOpsService.mAppOpsCheckingService.getForegroundOps(intValue);
                for (int i5 = 0; i5 < foregroundOps.size(); i5++) {
                    sparseBooleanArray.put(foregroundOps.keyAt(i5), true);
                }
                String[] packagesForUid = AppOpsService.getPackagesForUid(intValue);
                int userId = UserHandle.getUserId(intValue);
                for (String str : packagesForUid) {
                    SparseBooleanArray foregroundOps2 =
                            appOpsService.mAppOpsCheckingService.getForegroundOps(userId, str);
                    for (int i6 = 0; i6 < foregroundOps2.size(); i6++) {
                        sparseBooleanArray.put(foregroundOps2.keyAt(i6), true);
                    }
                }
                for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
                    if (sparseBooleanArray.valueAt(size)) {
                        int keyAt = sparseBooleanArray.keyAt(size);
                        int i7 = 4;
                        if (appOpsService.mAppOpsCheckingService.getUidMode(
                                                uidStateLocked.uid, keyAt, "default:0")
                                        != AppOpsManager.opToDefaultMode(keyAt)
                                && appOpsService.mAppOpsCheckingService.getUidMode(
                                                uidStateLocked.uid, keyAt, "default:0")
                                        == 4) {
                            appOpsService.mHandler.sendMessage(
                                    PooledLambda.obtainMessage(
                                            new AppOpsService$$ExternalSyntheticLambda15(0),
                                            appOpsService,
                                            Integer.valueOf(keyAt),
                                            Integer.valueOf(uidStateLocked.uid),
                                            Boolean.TRUE,
                                            "default:0"));
                        } else if (!uidStateLocked.pkgOps.isEmpty()
                                && (arraySet = (ArraySet) appOpsService.mOpModeWatchers.get(keyAt))
                                        != null) {
                            int size2 = arraySet.size() - 1;
                            while (size2 >= 0) {
                                if ((((AppOpsService.ModeCallback) arraySet.valueAt(size2)).mFlags
                                                & 1)
                                        != 0) {
                                    int size3 = uidStateLocked.pkgOps.size() - 1;
                                    while (size3 >= 0) {
                                        AppOpsService.Op op =
                                                (AppOpsService.Op)
                                                        ((AppOpsService.Ops)
                                                                        uidStateLocked.pkgOps
                                                                                .valueAt(size3))
                                                                .get(keyAt);
                                        if (op != null) {
                                            if (appOpsService.mAppOpsCheckingService.getPackageMode(
                                                            op.op,
                                                            UserHandle.getUserId(op.uid),
                                                            op.packageName)
                                                    == i7) {
                                                i = size3;
                                                i2 = size2;
                                                arraySet2 = arraySet;
                                                i3 = i7;
                                                appOpsService.mHandler.sendMessage(
                                                        PooledLambda.obtainMessage(
                                                                new AppOpsService$$ExternalSyntheticLambda7(
                                                                        0),
                                                                appOpsService,
                                                                (AppOpsService.ModeCallback)
                                                                        arraySet.valueAt(size2),
                                                                Integer.valueOf(keyAt),
                                                                Integer.valueOf(uidStateLocked.uid),
                                                                (String)
                                                                        uidStateLocked.pkgOps.keyAt(
                                                                                size3),
                                                                "default:0"));
                                                size3 = i - 1;
                                                size2 = i2;
                                                arraySet = arraySet2;
                                                i7 = i3;
                                            }
                                        }
                                        i = size3;
                                        i2 = size2;
                                        arraySet2 = arraySet;
                                        i3 = i7;
                                        size3 = i - 1;
                                        size2 = i2;
                                        arraySet = arraySet2;
                                        i7 = i3;
                                    }
                                }
                                size2--;
                                arraySet = arraySet;
                                i7 = i7;
                            }
                        }
                    }
                }
            }
            if (intValue2 == Integer.MAX_VALUE) {
                return;
            }
            if (uidStateLocked != null) {
                int size4 = uidStateLocked.pkgOps.size();
                for (int i8 = 0; i8 < size4; i8++) {
                    AppOpsService.Ops ops = (AppOpsService.Ops) uidStateLocked.pkgOps.valueAt(i8);
                    int size5 = ops.size();
                    for (int i9 = 0; i9 < size5; i9++) {
                        AppOpsService.Op op2 = (AppOpsService.Op) ops.valueAt(i9);
                        for (int i10 = 0; i10 < op2.mDeviceAttributedOps.size(); i10++) {
                            ArrayMap arrayMap = (ArrayMap) op2.mDeviceAttributedOps.valueAt(i10);
                            for (int i11 = 0; i11 < arrayMap.size(); i11++) {
                                ((AttributedOp) arrayMap.valueAt(i11)).onUidStateChanged(intValue2);
                            }
                        }
                    }
                }
            }
        }
    }
}
