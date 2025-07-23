package com.android.systemui.communal.widgets;

import android.content.Context;
import com.android.server.servicewatcher.ServiceWatcher;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GlanceableHubWidgetManagerServiceSupplier implements ServiceWatcher.ServiceSupplier<GlanceableHubWidgetManagerServiceInfo>, UserTracker.Callback {
    public final Executor bgExecutor;
    public final Context context;
    public ServiceWatcher.ServiceChangedListener listener;
    public boolean userAboutToSwitch;
    public final UserTracker userTracker;

    public GlanceableHubWidgetManagerServiceSupplier(Context context, Executor executor, UserTracker userTracker) {
        this.context = context;
        this.bgExecutor = executor;
        this.userTracker = userTracker;
    }

    public final ServiceWatcher.BoundServiceInfo getServiceInfo() {
        return new GlanceableHubWidgetManagerServiceInfo(this.context, ((UserTrackerImpl) this.userTracker).getUserHandle());
    }

    public final boolean hasMatchingService() {
        return !this.userAboutToSwitch && ((UserTrackerImpl) this.userTracker).getUserInfo().isMain();
    }

    @Override // com.android.systemui.settings.UserTracker.Callback
    public final void onBeforeUserSwitching(int i) {
        this.userAboutToSwitch = true;
        ServiceWatcher.ServiceChangedListener serviceChangedListener = this.listener;
        if (serviceChangedListener != null) {
            serviceChangedListener.onServiceChanged();
        }
    }

    @Override // com.android.systemui.settings.UserTracker.Callback
    public final void onUserChanged(int i, Context context) {
        this.userAboutToSwitch = false;
        ServiceWatcher.ServiceChangedListener serviceChangedListener = this.listener;
        if (serviceChangedListener != null) {
            serviceChangedListener.onServiceChanged();
        }
    }

    public final void register(ServiceWatcher.ServiceChangedListener serviceChangedListener) {
        this.listener = serviceChangedListener;
        ((UserTrackerImpl) this.userTracker).addCallback(this, this.bgExecutor);
    }

    public final void unregister() {
        this.listener = null;
        ((UserTrackerImpl) this.userTracker).removeCallback(this);
    }

    public final void alertUnstableService(String str) {
    }
}
