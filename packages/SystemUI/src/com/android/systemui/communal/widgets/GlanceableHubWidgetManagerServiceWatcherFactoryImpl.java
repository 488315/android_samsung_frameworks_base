package com.android.systemui.communal.widgets;

import android.content.Context;
import android.os.Handler;
import com.android.server.servicewatcher.ServiceWatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GlanceableHubWidgetManagerServiceWatcherFactoryImpl implements ServiceWatcherFactory {
    public final Context context;
    public final Handler handler;
    public final ServiceWatcher.ServiceSupplier supplier;

    public GlanceableHubWidgetManagerServiceWatcherFactoryImpl(Context context, Handler handler, ServiceWatcher.ServiceSupplier<GlanceableHubWidgetManagerServiceInfo> serviceSupplier) {
        this.context = context;
        this.handler = handler;
        this.supplier = serviceSupplier;
    }
}
