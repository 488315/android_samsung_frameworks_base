package com.android.server.flags;

import android.flags.IFeatureFlagsCallback;
import android.flags.SyncableFlag;
import android.os.RemoteException;
import android.util.Slog;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

public final /* synthetic */ class DynamicFlagBinderDelegate$$ExternalSyntheticLambda1 {
    public final /* synthetic */ DynamicFlagBinderDelegate f$0;

    public final void onFlagChanged(String str, String str2, String str3) {
        DynamicFlagBinderDelegate.DynamicFlagData dynamicFlagData;
        HashSet hashSet;
        DynamicFlagBinderDelegate dynamicFlagBinderDelegate = this.f$0;
        if (dynamicFlagBinderDelegate.mDynamicFlags.contains(str, str2)
                && (dynamicFlagData =
                                (DynamicFlagBinderDelegate.DynamicFlagData)
                                        dynamicFlagBinderDelegate.mDynamicFlags.getOrNull(
                                                str, str2))
                        != null) {
            if (str3 == null) {
                if (dynamicFlagData.mValue.equals(dynamicFlagData.mDefaultValue)) {
                    return;
                } else {
                    str3 = dynamicFlagData.mDefaultValue;
                }
            } else if (dynamicFlagData.mValue.equals(str3)) {
                return;
            }
            dynamicFlagData.mValue = str3;
            synchronized (dynamicFlagBinderDelegate.mCallbacks) {
                try {
                    hashSet = new HashSet();
                    for (Integer num : ((HashMap) dynamicFlagBinderDelegate.mCallbacks).keySet()) {
                        num.getClass();
                        if (((HashSet) dynamicFlagData.mPids).contains(num)) {
                            hashSet.addAll(
                                    (Collection)
                                            ((HashMap) dynamicFlagBinderDelegate.mCallbacks)
                                                    .get(num));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            final SyncableFlag syncableFlag = new SyncableFlag(str, str2, str3, true);
            hashSet.forEach(
                    new Consumer() { // from class:
                                     // com.android.server.flags.DynamicFlagBinderDelegate$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            try {
                                ((IFeatureFlagsCallback) obj).onFlagChange(syncableFlag);
                            } catch (RemoteException unused) {
                                Slog.w(
                                        "FeatureFlagsService",
                                        "Failed to communicate flag change to client.");
                            }
                        }
                    });
        }
    }
}
