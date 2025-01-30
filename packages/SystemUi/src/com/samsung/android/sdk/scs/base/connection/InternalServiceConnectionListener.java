package com.samsung.android.sdk.scs.base.connection;

import android.content.ComponentName;
import android.os.IBinder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface InternalServiceConnectionListener {
    void onConnected(ComponentName componentName, IBinder iBinder);

    void onDisconnected(ComponentName componentName);

    default void onError() {
    }
}
