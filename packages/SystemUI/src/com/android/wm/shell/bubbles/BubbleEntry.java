package com.android.wm.shell.bubbles;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubbleEntry {
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

    public final boolean isBubble() {
        return (this.mSbn.getNotification().flags & 4096) != 0;
    }

    public final void setFlagBubble(boolean z) {
        isBubble();
        if (!z) {
            this.mSbn.getNotification().flags &= -4097;
        } else if (getBubbleMetadata() != null && this.mRanking.canBubble()) {
            this.mSbn.getNotification().flags |= 4096;
        }
        isBubble();
    }
}
