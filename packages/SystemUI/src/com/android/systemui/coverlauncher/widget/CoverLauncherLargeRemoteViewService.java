package com.android.systemui.coverlauncher.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CoverLauncherLargeRemoteViewService extends RemoteViewsService {
    @Override // android.widget.RemoteViewsService
    public final RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CoverLauncherRemoteViewsFactory(this, intent);
    }
}
