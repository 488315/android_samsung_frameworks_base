package com.android.systemui.coverlauncher.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public final class CoverLauncherLargeRemoteViewService extends RemoteViewsService {
    @Override // android.widget.RemoteViewsService
    public final RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CoverLauncherRemoteViewsFactory(this, intent);
    }
}
