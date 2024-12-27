package com.android.systemui.statusbar;

import android.app.Flags;
import android.app.Notification;
import android.app.RemoteInputHistoryItem;
import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class RemoteInputNotificationRebuilder {
    public final Context mContext;

    public RemoteInputNotificationRebuilder(Context context) {
        this.mContext = context;
    }

    public StatusBarNotification rebuildWithRemoteInputInserted(NotificationEntry notificationEntry, CharSequence charSequence, boolean z, String str, Uri uri) {
        RemoteInputHistoryItem[] remoteInputHistoryItemArr;
        RemoteInputHistoryItem[] remoteInputHistoryItemArr2;
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(this.mContext, statusBarNotification.getNotification().clone());
        if (Flags.lifetimeExtensionRefactor()) {
            if (notificationEntry.remoteInputs == null) {
                notificationEntry.remoteInputs = new ArrayList();
            }
            if (charSequence != null || uri != null) {
                notificationEntry.remoteInputs.add(0, uri != null ? new RemoteInputHistoryItem(str, uri, charSequence) : new RemoteInputHistoryItem(charSequence));
            }
            Parcelable[] parcelableArray = statusBarNotification.getNotification().extras.getParcelableArray("android.remoteInputHistoryItems");
            if (parcelableArray != null) {
                final int i = 0;
                final int i2 = 0;
                remoteInputHistoryItemArr2 = (RemoteInputHistoryItem[]) Stream.concat(notificationEntry.remoteInputs.stream(), Arrays.stream(parcelableArray).map(new Function() { // from class: com.android.systemui.statusbar.RemoteInputNotificationRebuilder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        RemoteInputHistoryItem remoteInputHistoryItem = (Parcelable) obj;
                        switch (i) {
                        }
                        return remoteInputHistoryItem;
                    }
                })).toArray(new IntFunction() { // from class: com.android.systemui.statusbar.RemoteInputNotificationRebuilder$$ExternalSyntheticLambda1
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i3) {
                        switch (i2) {
                            case 0:
                                return new RemoteInputHistoryItem[i3];
                            case 1:
                                return new RemoteInputHistoryItem[i3];
                            default:
                                return new RemoteInputHistoryItem[i3];
                        }
                    }
                });
            } else {
                final int i3 = 1;
                remoteInputHistoryItemArr2 = (RemoteInputHistoryItem[]) notificationEntry.remoteInputs.toArray(new IntFunction() { // from class: com.android.systemui.statusbar.RemoteInputNotificationRebuilder$$ExternalSyntheticLambda1
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i32) {
                        switch (i3) {
                            case 0:
                                return new RemoteInputHistoryItem[i32];
                            case 1:
                                return new RemoteInputHistoryItem[i32];
                            default:
                                return new RemoteInputHistoryItem[i32];
                        }
                    }
                });
            }
            recoverBuilder.setRemoteInputHistory(remoteInputHistoryItemArr2);
        } else if (charSequence != null || uri != null) {
            RemoteInputHistoryItem remoteInputHistoryItem = uri != null ? new RemoteInputHistoryItem(str, uri, charSequence) : new RemoteInputHistoryItem(charSequence);
            Parcelable[] parcelableArray2 = statusBarNotification.getNotification().extras.getParcelableArray("android.remoteInputHistoryItems");
            if (parcelableArray2 != null) {
                final int i4 = 1;
                final int i5 = 2;
                remoteInputHistoryItemArr = (RemoteInputHistoryItem[]) Stream.concat(Stream.of(remoteInputHistoryItem), Arrays.stream(parcelableArray2).map(new Function() { // from class: com.android.systemui.statusbar.RemoteInputNotificationRebuilder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        RemoteInputHistoryItem remoteInputHistoryItem2 = (Parcelable) obj;
                        switch (i4) {
                        }
                        return remoteInputHistoryItem2;
                    }
                })).toArray(new IntFunction() { // from class: com.android.systemui.statusbar.RemoteInputNotificationRebuilder$$ExternalSyntheticLambda1
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i32) {
                        switch (i5) {
                            case 0:
                                return new RemoteInputHistoryItem[i32];
                            case 1:
                                return new RemoteInputHistoryItem[i32];
                            default:
                                return new RemoteInputHistoryItem[i32];
                        }
                    }
                });
            } else {
                remoteInputHistoryItemArr = new RemoteInputHistoryItem[]{remoteInputHistoryItem};
            }
            recoverBuilder.setRemoteInputHistory(remoteInputHistoryItemArr);
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
