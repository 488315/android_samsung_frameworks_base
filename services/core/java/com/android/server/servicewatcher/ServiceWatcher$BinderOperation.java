package com.android.server.servicewatcher;

import android.os.IBinder;

public interface ServiceWatcher$BinderOperation {
    default void onError(Throwable th) {}

    void run(IBinder iBinder);
}
