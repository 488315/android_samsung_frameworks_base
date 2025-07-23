package com.android.systemui.communal.widgets;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetProviderInfo;
import android.widget.RemoteViews;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AppWidgetHostListenerDelegate implements AppWidgetHost.AppWidgetHostListener {
    public final AppWidgetHost.AppWidgetHostListener listener;
    public final CoroutineScope mainScope;
    public final String tag;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        AppWidgetHostListenerDelegate create(String str, AppWidgetHost.AppWidgetHostListener appWidgetHostListener);
    }

    public AppWidgetHostListenerDelegate(CoroutineScope coroutineScope, String str, AppWidgetHost.AppWidgetHostListener appWidgetHostListener) {
        this.mainScope = coroutineScope;
        this.tag = str;
        this.listener = appWidgetHostListener;
    }

    public final void onUpdateProviderInfo(AppWidgetProviderInfo appWidgetProviderInfo) {
        CoroutineTracingKt.launchTraced$default(this.mainScope, null, null, new AppWidgetHostListenerDelegate$onUpdateProviderInfo$1(this, appWidgetProviderInfo, null), 6);
    }

    public final void onViewDataChanged(int i) {
        CoroutineTracingKt.launchTraced$default(this.mainScope, null, null, new AppWidgetHostListenerDelegate$onViewDataChanged$1(this, i, null), 6);
    }

    public final void updateAppWidget(RemoteViews remoteViews) {
        CoroutineTracingKt.launchTraced$default(this.mainScope, null, null, new AppWidgetHostListenerDelegate$updateAppWidget$1(this, remoteViews, null), 6);
    }
}
