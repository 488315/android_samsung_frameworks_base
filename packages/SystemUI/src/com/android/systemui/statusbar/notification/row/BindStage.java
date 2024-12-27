package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Map;

public abstract class BindStage extends BindRequester {
    public final Map mContentParams = new ArrayMap();

    public interface StageCallback {
    }

    public abstract void abortStage(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow);

    public abstract void executeStage(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotifBindPipeline$$ExternalSyntheticLambda1 notifBindPipeline$$ExternalSyntheticLambda1);

    public final Object getStageParams(NotificationEntry notificationEntry) {
        Object obj = ((ArrayMap) this.mContentParams).get(notificationEntry);
        if (obj != null) {
            return obj;
        }
        Log.wtf("BindStage", "Entry does not have any stage parameters. key: " + notificationEntry.mKey);
        return newStageParams();
    }

    public abstract RowContentBindParams newStageParams();
}
