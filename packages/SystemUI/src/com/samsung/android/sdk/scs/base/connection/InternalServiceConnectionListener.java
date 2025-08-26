package com.samsung.android.sdk.scs.base.connection;

import android.content.ComponentName;
import android.os.IBinder;

/* loaded from: classes4.dex */
public interface InternalServiceConnectionListener {
    void onConnected(ComponentName componentName, IBinder iBinder);

    void onDisconnected(ComponentName componentName);

    default void onError() {
    }
}
