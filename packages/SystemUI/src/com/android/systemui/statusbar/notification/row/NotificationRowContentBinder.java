package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface NotificationRowContentBinder {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BindParams {
        public final boolean isMinimized;
        public final int redactionType;

        public BindParams(boolean z, int i) {
            this.isMinimized = z;
            this.redactionType = i;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
