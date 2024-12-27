package com.android.server.appop;

import android.app.AppOpsManager;
import android.util.ArraySet;

import com.android.internal.app.IAppOpsCallback;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.pooled.PooledLambda;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppOpsService$$ExternalSyntheticLambda15
        implements QuintConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ AppOpsService$$ExternalSyntheticLambda15(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        ArraySet arraySet;
        ArraySet arraySet2;
        int packageUid;
        IAppOpsCallback iAppOpsCallback;
        switch (this.$r8$classId) {
            case 0:
                ((AppOpsService) obj)
                        .notifyOpChangedForAllPkgsInUid(
                                ((Integer) obj2).intValue(),
                                ((Integer) obj3).intValue(),
                                (String) obj5,
                                ((Boolean) obj4).booleanValue());
                return;
            case 1:
                AppOpsService appOpsService = (AppOpsService) obj;
                String str = (String) obj2;
                Integer num = (Integer) obj3;
                int intValue = num.intValue();
                int intValue2 = ((Integer) obj4).intValue();
                int intValue3 = ((Integer) obj5).intValue();
                boolean z = AppOpsService.DEBUG;
                synchronized (appOpsService) {
                    try {
                        ArraySet arraySet3 = (ArraySet) appOpsService.mOpModeWatchers.get(intValue);
                        AppOpsService.Op op = null;
                        if (arraySet3 != null) {
                            arraySet = new ArraySet();
                            arraySet.addAll(arraySet3);
                        } else {
                            arraySet = null;
                        }
                        ArraySet arraySet4 = (ArraySet) appOpsService.mPackageModeWatchers.get(str);
                        if (arraySet4 != null) {
                            if (arraySet == null) {
                                arraySet = new ArraySet();
                            }
                            arraySet.addAll(arraySet4);
                        }
                        arraySet2 = arraySet;
                        if (arraySet2 != null
                                && (iAppOpsCallback = appOpsService.mIgnoredCallback) != null) {
                            arraySet2.remove(
                                    appOpsService.mModeWatchers.get(iAppOpsCallback.asBinder()));
                        }
                        packageUid =
                                appOpsService
                                        .getPackageManagerInternal()
                                        .getPackageUid(str, 4202496L, intValue3);
                        AppOpsService.Ops opsLocked =
                                appOpsService.getOpsLocked(
                                        packageUid, str, null, false, null, false);
                        if (opsLocked != null) {
                            op = appOpsService.getOpLocked(opsLocked, intValue, packageUid, false);
                        }
                        if (op != null && intValue2 == AppOpsManager.opToDefaultMode(op.op)) {
                            appOpsService.pruneOpLocked(op, packageUid, str);
                        }
                        appOpsService.scheduleFastWriteLocked();
                        if (intValue2 != 2) {
                            appOpsService.updateStartedOpModeForUidForDefaultDeviceLocked(
                                    intValue, packageUid, intValue2 == 1);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (arraySet2 == null || packageUid == -1) {
                    return;
                }
                appOpsService.mHandler.sendMessage(
                        PooledLambda.obtainMessage(
                                new AppOpsService$$ExternalSyntheticLambda7(1),
                                appOpsService,
                                arraySet2,
                                num,
                                Integer.valueOf(packageUid),
                                str,
                                "default:0"));
                return;
            default:
                int intValue4 = ((Integer) obj2).intValue();
                int intValue5 = ((Integer) obj3).intValue();
                boolean booleanValue = ((Boolean) obj4).booleanValue();
                boolean z2 = AppOpsService.DEBUG;
                ((AppOpsService) obj)
                        .notifyOpChangedForAllPkgsInUid(
                                intValue4, intValue5, (String) obj5, booleanValue);
                return;
        }
    }
}
