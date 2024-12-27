package com.android.server.appop;

import android.os.Binder;
import android.os.RemoteException;
import android.util.ArraySet;
import com.android.internal.util.function.DecConsumer;
import com.android.server.appop.AppOpsService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppOpsService$$ExternalSyntheticLambda2 implements DecConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10) {
        ArraySet arraySet;
        AppOpsService appOpsService = (AppOpsService) obj;
        ArraySet arraySet2 = (ArraySet) obj2;
        int intValue = ((Integer) obj3).intValue();
        int intValue2 = ((Integer) obj4).intValue();
        String str = (String) obj5;
        String str2 = (String) obj6;
        int intValue3 = ((Integer) obj7).intValue();
        boolean booleanValue = ((Boolean) obj8).booleanValue();
        int intValue4 = ((Integer) obj9).intValue();
        int intValue5 = ((Integer) obj10).intValue();
        appOpsService.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int size = arraySet2.size();
            int i = 0;
            while (i < size) {
                AppOpsService.ActiveCallback activeCallback = (AppOpsService.ActiveCallback) arraySet2.valueAt(i);
                try {
                    arraySet = arraySet2;
                    try {
                        if (!appOpsService.shouldIgnoreCallback(intValue, activeCallback.mCallingPid, activeCallback.mCallingUid)) {
                            activeCallback.mCallback.opActiveChanged(intValue, intValue2, str, str2, intValue3, booleanValue, intValue4, intValue5);
                        }
                    } catch (RemoteException unused) {
                    }
                } catch (RemoteException unused2) {
                    arraySet = arraySet2;
                }
                i++;
                arraySet2 = arraySet;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
