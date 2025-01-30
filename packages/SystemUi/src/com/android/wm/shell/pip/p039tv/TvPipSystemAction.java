package com.android.wm.shell.pip.tv;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Handler;
import com.android.wm.shell.common.TvWindowMenuActionButton;
import com.android.wm.shell.pip.tv.TvPipAction;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipSystemAction extends TvPipAction {
    public final PendingIntent mBroadcastIntent;
    public int mIconResource;
    public int mTitleResource;

    public TvPipSystemAction(int i, int i2, int i3, String str, Context context, TvPipAction.SystemActionsHandler systemActionsHandler) {
        super(i, systemActionsHandler);
        if (i2 == this.mTitleResource) {
            int i4 = this.mIconResource;
        }
        this.mTitleResource = i2;
        this.mIconResource = i3;
        this.mBroadcastIntent = TvPipNotificationController.createPendingIntent(context, str);
    }

    @Override // com.android.wm.shell.pip.tv.TvPipAction
    public final PendingIntent getPendingIntent() {
        return this.mBroadcastIntent;
    }

    @Override // com.android.wm.shell.pip.tv.TvPipAction
    public final void populateButton(TvWindowMenuActionButton tvWindowMenuActionButton, Handler handler) {
        tvWindowMenuActionButton.setContentDescription(tvWindowMenuActionButton.getContext().getString(this.mTitleResource));
        int i = this.mIconResource;
        if (i != 0) {
            tvWindowMenuActionButton.mIconImageView.setImageResource(i);
        }
        tvWindowMenuActionButton.setEnabled(true);
        tvWindowMenuActionButton.setIsCustomCloseAction(false);
    }

    @Override // com.android.wm.shell.pip.tv.TvPipAction
    public final Notification.Action toNotificationAction(Context context) {
        Notification.Action.Builder builder = new Notification.Action.Builder(Icon.createWithResource(context, this.mIconResource), context.getString(this.mTitleResource), this.mBroadcastIntent);
        int i = this.mActionType;
        builder.setSemanticAction(i == 1 || i == 5 ? 4 : 0);
        builder.setContextual(true);
        return builder.build();
    }
}
