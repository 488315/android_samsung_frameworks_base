package com.android.wm.shell.draganddrop;

import android.content.ClipDescription;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DragAndDropEventLogger {
    public ActivityInfo mActivityInfo;
    public final InstanceIdSequence mIdSequence = new InstanceIdSequence(Integer.MAX_VALUE);
    public InstanceId mInstanceId;
    public final UiEventLogger mUiEventLogger;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum DragAndDropUiEventEnum implements UiEventLogger.UiEventEnum {
        GLOBAL_APP_DRAG_START_ACTIVITY(884),
        GLOBAL_APP_DRAG_START_SHORTCUT(885),
        GLOBAL_APP_DRAG_START_TASK(888),
        GLOBAL_APP_DRAG_DROPPED(887),
        GLOBAL_APP_DRAG_END(886);

        private final int mId;

        DragAndDropUiEventEnum(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public DragAndDropEventLogger(UiEventLogger uiEventLogger) {
        this.mUiEventLogger = uiEventLogger;
    }

    public final void log(DragAndDropUiEventEnum dragAndDropUiEventEnum, ActivityInfo activityInfo) {
        this.mUiEventLogger.logWithInstanceId(dragAndDropUiEventEnum, activityInfo == null ? 0 : activityInfo.applicationInfo.uid, activityInfo == null ? null : activityInfo.applicationInfo.packageName, this.mInstanceId);
    }

    public final InstanceId logStart(DragSession dragSession) {
        DragAndDropUiEventEnum dragAndDropUiEventEnum;
        Intent intent = dragSession.appData;
        InstanceId instanceId = intent != null ? (InstanceId) intent.getParcelableExtra("android.intent.extra.LOGGING_INSTANCE_ID", InstanceId.class) : null;
        this.mInstanceId = instanceId;
        if (instanceId == null) {
            this.mInstanceId = this.mIdSequence.newInstanceId();
        }
        ActivityInfo activityInfo = dragSession.activityInfo;
        this.mActivityInfo = activityInfo;
        if (dragSession.appData != null) {
            ClipDescription description = dragSession.mInitialDragData.getDescription();
            if (description.hasMimeType("application/vnd.android.activity")) {
                dragAndDropUiEventEnum = DragAndDropUiEventEnum.GLOBAL_APP_DRAG_START_ACTIVITY;
            } else if (description.hasMimeType("application/vnd.android.shortcut")) {
                dragAndDropUiEventEnum = DragAndDropUiEventEnum.GLOBAL_APP_DRAG_START_SHORTCUT;
            } else {
                if (!description.hasMimeType("application/vnd.android.task")) {
                    throw new IllegalArgumentException("Not an app drag");
                }
                dragAndDropUiEventEnum = DragAndDropUiEventEnum.GLOBAL_APP_DRAG_START_TASK;
            }
            log(dragAndDropUiEventEnum, this.mActivityInfo);
        } else {
            log(DragAndDropUiEventEnum.GLOBAL_APP_DRAG_START_ACTIVITY, activityInfo);
        }
        return this.mInstanceId;
    }
}
