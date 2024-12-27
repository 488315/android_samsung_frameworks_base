package com.android.server.enterprise;

import android.os.IBinder;

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
