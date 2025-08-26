package com.android.systemui.media;

import android.media.session.MediaController;

/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationMediaManager$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ NotificationMediaManager f$0;

    @Override // java.lang.Runnable
    public final void run() {
        NotificationMediaManager notificationMediaManager = this.f$0;
        notificationMediaManager.mMediaNotificationKey = null;
        notificationMediaManager.mMediaMetadata = null;
        MediaController mediaController = notificationMediaManager.mMediaController;
        if (mediaController != null) {
            mediaController.unregisterCallback(notificationMediaManager.mMediaListener);
        }
        notificationMediaManager.mMediaController = null;
    }
}
