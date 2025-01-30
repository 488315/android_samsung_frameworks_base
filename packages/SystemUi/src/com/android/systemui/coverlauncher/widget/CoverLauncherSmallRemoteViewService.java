package com.android.systemui.coverlauncher.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverLauncherSmallRemoteViewService extends RemoteViewsService {
    @Override // android.widget.RemoteViewsService
    public final RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CoverLauncherRemoteViewsFactory(this, intent);
    }
}
