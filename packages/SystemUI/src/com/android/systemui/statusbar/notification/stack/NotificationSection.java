package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.statusbar.notification.row.ExpandableView;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationSection {
    public final int mBucket;
    public ExpandableView mFirstVisibleChild;
    public ExpandableView mLastVisibleChild;

    public NotificationSection(int i) {
        this.mBucket = i;
    }
}
