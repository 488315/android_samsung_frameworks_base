package com.android.systemui.coverlauncher.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/* loaded from: classes2.dex */
public final class CoverLauncherLargeRemoteViewService extends RemoteViewsService {
    @Override // android.widget.RemoteViewsService
    public final RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.i("CoverLauncherRemoteViewsFactory", "onGetViewFactory, id=" + intent.getIntExtra("appWidgetId", 0));
        return new CoverLauncherRemoteViewsFactory(this, intent);
    }
}
