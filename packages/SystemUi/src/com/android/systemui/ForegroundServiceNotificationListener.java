package com.android.systemui;

import android.content.Context;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ForegroundServiceNotificationListener {
    public final Context mContext;
    public final ForegroundServiceController mForegroundServiceController;
    public final NotifPipeline mNotifPipeline;

    public ForegroundServiceNotificationListener(Context context, ForegroundServiceController foregroundServiceController, NotifPipeline notifPipeline) {
        this.mContext = context;
        this.mForegroundServiceController = foregroundServiceController;
        this.mNotifPipeline = notifPipeline;
    }
}
