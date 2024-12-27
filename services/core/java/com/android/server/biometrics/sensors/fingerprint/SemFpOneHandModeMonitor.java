package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.database.ContentObserver;

public final class SemFpOneHandModeMonitor implements SemFpEventListener {
    ContentObserver mContentObserver;
    public final Context mContext;
    public final Injector mInjector;
    public final ServiceProvider mServiceProvider;

    public class Injector {}

    public SemFpOneHandModeMonitor(
            Context context, ServiceProvider serviceProvider, Injector injector) {
        this.mContext = context;
        this.mServiceProvider = serviceProvider;
        this.mInjector = injector;
    }
}
