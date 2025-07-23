package com.samsung.android.sdk.scs.base.connection;

import android.content.ComponentName;
import android.os.IBinder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface InternalServiceConnectionListener {
    void onConnected(ComponentName componentName, IBinder iBinder);

    void onDisconnected(ComponentName componentName);

    default void onError() {
    }
}
