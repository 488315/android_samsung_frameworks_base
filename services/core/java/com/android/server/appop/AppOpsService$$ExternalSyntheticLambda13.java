package com.android.server.appop;

import android.os.Binder;
import android.os.RemoteException;
import android.util.ArraySet;

import com.android.internal.util.function.NonaConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AppOpsService$$ExternalSyntheticLambda13
        implements NonaConsumer {
    public final void accept(
            Object obj,
            Object obj2,
            Object obj3,
            Object obj4,
            Object obj5,
            Object obj6,
            Object obj7,
            Object obj8,
            Object obj9) {
        AppOpsService appOpsService = (AppOpsService) obj;
        ArraySet arraySet = (ArraySet) obj2;
        int intValue = ((Integer) obj3).intValue();
        int intValue2 = ((Integer) obj4).intValue();
        String str = (String) obj5;
        String str2 = (String) obj6;
        int intValue3 = ((Integer) obj7).intValue();
        int intValue4 = ((Integer) obj8).intValue();
        int intValue5 = ((Integer) obj9).intValue();
        appOpsService.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int size = arraySet.size();
            for (int i = 0; i < size; i++) {
                AppOpsService.NotedCallback notedCallback =
                        (AppOpsService.NotedCallback) arraySet.valueAt(i);
                try {
                    if (!appOpsService.shouldIgnoreCallback(
                            intValue, notedCallback.mCallingPid, notedCallback.mCallingUid)) {
                        notedCallback.mCallback.opNoted(
                                intValue, intValue2, str, str2, intValue3, intValue4, intValue5);
                    }
                } catch (RemoteException unused) {
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
