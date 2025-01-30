package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface NotificationRowContentBinder {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BindParams {
        public boolean isLowPriority;
        public boolean usesIncreasedHeadsUpHeight;
        public boolean usesIncreasedHeight;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface InflationCallback {
        void handleInflationException(NotificationEntry notificationEntry, Exception exc);

        void onAsyncInflationFinished(NotificationEntry notificationEntry);
    }
}
