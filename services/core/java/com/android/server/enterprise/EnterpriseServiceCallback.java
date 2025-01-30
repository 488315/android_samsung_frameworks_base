package com.android.server.enterprise;

import android.os.IBinder;

/* loaded from: classes2.dex */
public interface EnterpriseServiceCallback {
  default boolean hasDeferredBroadcastReceiverToRegister() {
    return false;
  }

  void notifyToAddSystemService(String str, IBinder iBinder);

  void onAdminAdded(int i);

  default void onAdminRemoved(int i) {}

  void onPreAdminRemoval(int i);

  default void registerDeferredBoradcastReceiver() {}

  void systemReady();

  default void onAdminRemoved(int i, boolean z) {
    onAdminRemoved(i);
  }
}
