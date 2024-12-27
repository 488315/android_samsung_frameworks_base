package com.android.server.servicewatcher;

import android.os.IBinder;

public interface ServiceWatcher$ServiceListener {
    void onBind(IBinder iBinder, CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo);

    void onUnbind();
}
