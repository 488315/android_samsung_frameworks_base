package com.android.p038wm.shell.pip.p039tv;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import com.android.p038wm.shell.common.TvWindowMenuActionButton;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class TvPipAction {
    public final int mActionType;
    public final SystemActionsHandler mSystemActionsHandler;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SystemActionsHandler {
        void executeAction(int i);
    }

    public TvPipAction(int i, SystemActionsHandler systemActionsHandler) {
        Objects.requireNonNull(systemActionsHandler);
        this.mActionType = i;
        this.mSystemActionsHandler = systemActionsHandler;
    }

    public void executeAction() {
        this.mSystemActionsHandler.executeAction(this.mActionType);
    }

    public abstract PendingIntent getPendingIntent();

    public abstract void populateButton(TvWindowMenuActionButton tvWindowMenuActionButton, Handler handler);

    public abstract Notification.Action toNotificationAction(Context context);
}
