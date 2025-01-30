package com.android.systemui.statusbar;

import android.app.Notification;
import android.app.RemoteInputHistoryItem;
import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Arrays;
import java.util.stream.Stream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RemoteInputNotificationRebuilder {
    public final Context mContext;

    public RemoteInputNotificationRebuilder(Context context) {
        this.mContext = context;
    }

    public StatusBarNotification rebuildWithRemoteInputInserted(NotificationEntry notificationEntry, CharSequence charSequence, boolean z, String str, Uri uri) {
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(this.mContext, statusBarNotification.getNotification().clone());
        if (charSequence != null || uri != null) {
            RemoteInputHistoryItem remoteInputHistoryItem = uri != null ? new RemoteInputHistoryItem(str, uri, charSequence) : new RemoteInputHistoryItem(charSequence);
            Parcelable[] parcelableArray = statusBarNotification.getNotification().extras.getParcelableArray("android.remoteInputHistoryItems");
            recoverBuilder.setRemoteInputHistory(parcelableArray != null ? (RemoteInputHistoryItem[]) Stream.concat(Stream.of(remoteInputHistoryItem), Arrays.stream(parcelableArray).map(new RemoteInputNotificationRebuilder$$ExternalSyntheticLambda0())).toArray(new RemoteInputNotificationRebuilder$$ExternalSyntheticLambda1()) : new RemoteInputHistoryItem[]{remoteInputHistoryItem});
        }
        recoverBuilder.setShowRemoteInputSpinner(z);
        recoverBuilder.setHideSmartReplies(true);
        Notification build = recoverBuilder.build();
        build.contentView = statusBarNotification.getNotification().contentView;
        build.bigContentView = statusBarNotification.getNotification().bigContentView;
        build.headsUpContentView = statusBarNotification.getNotification().headsUpContentView;
        return new StatusBarNotification(statusBarNotification.getPackageName(), statusBarNotification.getOpPkg(), statusBarNotification.getId(), statusBarNotification.getTag(), statusBarNotification.getUid(), statusBarNotification.getInitialPid(), build, statusBarNotification.getUser(), statusBarNotification.getOverrideGroupKey(), statusBarNotification.getPostTime());
    }
}
