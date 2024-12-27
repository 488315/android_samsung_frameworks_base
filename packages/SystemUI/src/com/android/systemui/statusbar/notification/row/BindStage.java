package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class BindStage extends BindRequester {
    public final Map mContentParams = new ArrayMap();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
