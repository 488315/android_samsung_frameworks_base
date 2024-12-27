package com.android.server.enterprise;

import android.os.IBinder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface EnterpriseServiceCallback {
    default boolean hasDeferredBroadcastReceiverToRegister() {
        return false;
    }

    void notifyToAddSystemService(String str, IBinder iBinder);

    void onAdminAdded(int i);

    default void onAdminRemoved(int i) {}

    default void onAdminRemoved(int i, boolean z) {
        onAdminRemoved(i);
    }

    void onPreAdminRemoval(int i);

    default void onUserStarting(int i) {}

    default void registerDeferredBoradcastReceiver() {}

    void systemReady();
}
