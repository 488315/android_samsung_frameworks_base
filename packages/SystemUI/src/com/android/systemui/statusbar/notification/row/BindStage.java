package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class BindStage extends BindRequester {
    public final Map mContentParams = new ArrayMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface StageCallback {
    }

    public abstract void abortStage(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow);

    public abstract void executeStage(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotifBindPipeline$$ExternalSyntheticLambda2 notifBindPipeline$$ExternalSyntheticLambda2);

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
