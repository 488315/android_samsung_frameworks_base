package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class BindStage extends BindRequester {
    public final Map mContentParams = new ArrayMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface StageCallback {
    }

    public abstract void abortStage(NotificationEntry notificationEntry);

    public abstract void executeStage(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, NotifBindPipeline$$ExternalSyntheticLambda0 notifBindPipeline$$ExternalSyntheticLambda0);

    public final Object getStageParams(NotificationEntry notificationEntry) {
        Object obj = ((ArrayMap) this.mContentParams).get(notificationEntry);
        if (obj != null) {
            return obj;
        }
        Log.wtf("BindStage", String.format("Entry does not have any stage parameters. key: %s", notificationEntry.mKey));
        return newStageParams();
    }

    public abstract RowContentBindParams newStageParams();
}
