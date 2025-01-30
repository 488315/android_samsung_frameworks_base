package com.android.systemui.coverlauncher.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverLauncherSmallWidgetProvider extends CoverLauncherLargeWidgetProvider {
    public static final HashMap sWidgetOptions = new HashMap();

    @Override // com.android.systemui.coverlauncher.widget.CoverLauncherLargeWidgetProvider
    public final int getProviderType() {
        return 2;
    }

    @Override // com.android.systemui.coverlauncher.widget.CoverLauncherLargeWidgetProvider
    public final HashMap getWidgetOptions() {
        return sWidgetOptions;
    }

    @Override // com.android.systemui.coverlauncher.widget.CoverLauncherLargeWidgetProvider, android.appwidget.AppWidgetProvider
    public final void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int i, Bundle bundle) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, i, bundle);
    }

    @Override // com.android.systemui.coverlauncher.widget.CoverLauncherLargeWidgetProvider, android.appwidget.AppWidgetProvider
    public final void onDeleted(Context context, int[] iArr) {
        super.onDeleted(context, iArr);
    }

    @Override // com.android.systemui.coverlauncher.widget.CoverLauncherLargeWidgetProvider, android.appwidget.AppWidgetProvider, android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Log.i("CoverLauncherSmallWidgetProvider", "onReceive");
        super.onReceive(context, intent);
    }

    @Override // com.android.systemui.coverlauncher.widget.CoverLauncherLargeWidgetProvider, android.appwidget.AppWidgetProvider
    public final void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] iArr) {
        super.onUpdate(context, appWidgetManager, iArr);
    }

    @Override // com.android.systemui.coverlauncher.widget.CoverLauncherLargeWidgetProvider
    public final void updateAppWidgetViewWithProvider(Context context, AppWidgetManager appWidgetManager) {
        CoverLauncherLargeWidgetProvider.updateAppWidgetView(context, appWidgetManager, appWidgetManager.getAppWidgetIds(new ComponentName(context, (Class<?>) CoverLauncherSmallWidgetProvider.class)));
    }
}
