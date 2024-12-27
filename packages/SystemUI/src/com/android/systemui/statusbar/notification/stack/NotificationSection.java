package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;

public final class NotificationSection {
    public final int mBucket;
    public ExpandableView mFirstVisibleChild;
    public ExpandableView mLastVisibleChild;

    public NotificationSection(int i) {
        this.mBucket = i;
    }
}
