package com.android.p038wm.shell.pip.p039tv;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.android.p038wm.shell.common.TvWindowMenuActionButton;
import com.android.p038wm.shell.pip.p039tv.TvPipAction;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipCustomAction extends TvPipAction {
    public final RemoteAction mRemoteAction;

    public TvPipCustomAction(int i, RemoteAction remoteAction, TvPipAction.SystemActionsHandler systemActionsHandler) {
        super(i, systemActionsHandler);
        Objects.requireNonNull(remoteAction);
        this.mRemoteAction = remoteAction;
    }

    @Override // com.android.p038wm.shell.pip.p039tv.TvPipAction
    public final void executeAction() {
        super.executeAction();
        try {
            this.mRemoteAction.getActionIntent().send();
        } catch (PendingIntent.CanceledException e) {
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -89364644, 0, "%s: Failed to send action, %s", "TvPipCustomAction", String.valueOf(e));
            }
        }
    }

    @Override // com.android.p038wm.shell.pip.p039tv.TvPipAction
    public final PendingIntent getPendingIntent() {
        return this.mRemoteAction.getActionIntent();
    }

    @Override // com.android.p038wm.shell.pip.p039tv.TvPipAction
    public final void populateButton(TvWindowMenuActionButton tvWindowMenuActionButton, Handler handler) {
        if (tvWindowMenuActionButton == null || handler == null) {
            return;
        }
        RemoteAction remoteAction = this.mRemoteAction;
        if (remoteAction.getContentDescription().length() > 0) {
            tvWindowMenuActionButton.setContentDescription(remoteAction.getContentDescription());
        } else {
            tvWindowMenuActionButton.setContentDescription(remoteAction.getTitle());
        }
        tvWindowMenuActionButton.setImageIconAsync(remoteAction.getIcon(), handler);
        int i = this.mActionType;
        tvWindowMenuActionButton.setEnabled((i == 1 || i == 5) || remoteAction.isEnabled());
        tvWindowMenuActionButton.setIsCustomCloseAction(i == 1 || i == 5);
    }

    @Override // com.android.p038wm.shell.pip.p039tv.TvPipAction
    public final Notification.Action toNotificationAction(Context context) {
        RemoteAction remoteAction = this.mRemoteAction;
        Notification.Action.Builder builder = new Notification.Action.Builder(remoteAction.getIcon(), remoteAction.getTitle(), remoteAction.getActionIntent());
        Bundle bundle = new Bundle();
        bundle.putCharSequence("android.pictureContentDescription", remoteAction.getContentDescription());
        bundle.putBoolean("EXTRA_IS_PIP_CUSTOM_ACTION", true);
        builder.addExtras(bundle);
        int i = this.mActionType;
        builder.setSemanticAction(i == 1 || i == 5 ? 4 : 0);
        builder.setContextual(true);
        return builder.build();
    }
}
