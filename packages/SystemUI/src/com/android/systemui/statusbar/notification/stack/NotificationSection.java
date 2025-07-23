package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationSection {
    public final int mBucket;
    public ExpandableView mFirstVisibleChild;
    public ExpandableView mLastVisibleChild;

    public NotificationSection(int i) {
        this.mBucket = i;
    }
}
