package com.android.wm.shell.bubbles;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubbleEntry {
    public final boolean mIsDismissable;
    public final NotificationListenerService.Ranking mRanking;
    public final StatusBarNotification mSbn;
    public final boolean mShouldSuppressNotificationDot;
    public final boolean mShouldSuppressNotificationList;
    public final boolean mShouldSuppressPeek;

    public BubbleEntry(StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mSbn = statusBarNotification;
        this.mRanking = ranking;
        this.mIsDismissable = z;
        this.mShouldSuppressNotificationDot = z2;
        this.mShouldSuppressNotificationList = z3;
        this.mShouldSuppressPeek = z4;
    }

    public final Notification.BubbleMetadata getBubbleMetadata() {
        return this.mSbn.getNotification().getBubbleMetadata();
    }

    public final String getKey() {
        return this.mSbn.getKey();
    }

    public final boolean isBubble() {
        return (this.mSbn.getNotification().flags & 4096) != 0;
    }

    public final void setFlagBubble(boolean z) {
        isBubble();
        StatusBarNotification statusBarNotification = this.mSbn;
        if (!z) {
            statusBarNotification.getNotification().flags &= -4097;
        } else if (getBubbleMetadata() != null && this.mRanking.canBubble()) {
            statusBarNotification.getNotification().flags |= 4096;
        }
        isBubble();
    }
}
