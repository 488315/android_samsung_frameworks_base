package com.android.systemui.coverlauncher.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CoverLauncherLargeRemoteViewService extends RemoteViewsService {
    @Override // android.widget.RemoteViewsService
    public final RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.i("CoverLauncherRemoteViewsFactory", "onGetViewFactory, id=" + intent.getIntExtra("appWidgetId", 0));
        return new CoverLauncherRemoteViewsFactory(this, intent);
    }
}
