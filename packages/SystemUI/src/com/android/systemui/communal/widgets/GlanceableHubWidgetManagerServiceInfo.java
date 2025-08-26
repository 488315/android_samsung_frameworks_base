package com.android.systemui.communal.widgets;

import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import com.android.server.servicewatcher.ServiceWatcher;

/* loaded from: classes2.dex */
public final class GlanceableHubWidgetManagerServiceInfo extends ServiceWatcher.BoundServiceInfo {
    public GlanceableHubWidgetManagerServiceInfo(Context context, UserHandle userHandle) {
        super((String) null, UserHandle.getUid(userHandle.getIdentifier(), UserHandle.getCallingAppId()), new ComponentName(context.getPackageName(), GlanceableHubWidgetManagerService.class.getName()), 1);
    }
}
