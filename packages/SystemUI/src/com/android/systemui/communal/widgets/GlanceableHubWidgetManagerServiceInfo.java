package com.android.systemui.communal.widgets;

import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import com.android.server.servicewatcher.ServiceWatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GlanceableHubWidgetManagerServiceInfo extends ServiceWatcher.BoundServiceInfo {
    public GlanceableHubWidgetManagerServiceInfo(Context context, UserHandle userHandle) {
        super((String) null, UserHandle.getUid(userHandle.getIdentifier(), UserHandle.getCallingAppId()), new ComponentName(context.getPackageName(), GlanceableHubWidgetManagerService.class.getName()), 1);
    }
}
