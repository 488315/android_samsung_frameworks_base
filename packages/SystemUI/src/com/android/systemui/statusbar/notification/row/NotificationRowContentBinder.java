package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;

/* loaded from: classes3.dex */
public interface NotificationRowContentBinder {

    public class BindParams {
        public final boolean isMinimized;
        public final int redactionType;

        public BindParams(boolean z, int i) {
            this.isMinimized = z;
            this.redactionType = i;
        }
    }

    public interface InflationCallback {
        default void handleInflationException(NotificationEntry notificationEntry, Exception exc) {
            handleInflationException(exc);
        }

        void handleInflationException(Exception exc);

        void onAsyncInflationFinished();

        default void onAsyncInflationFinished(NotificationEntry notificationEntry) {
            onAsyncInflationFinished();
        }
    }

    void bindContent(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, int i, BindParams bindParams, boolean z, RowContentBindStage.AnonymousClass1 anonymousClass1);

    boolean cancelBind(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow);

    void setInflateSynchronously(boolean z);

    void unbindContent(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, int i);
}
