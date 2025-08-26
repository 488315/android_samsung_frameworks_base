package com.android.systemui.communal.widgets;

import android.content.Context;
import android.os.Handler;
import com.android.server.servicewatcher.ServiceWatcher;

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
