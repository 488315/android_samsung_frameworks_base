package com.android.p038wm.shell.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.pm.ActivityInfo;
import android.view.DragEvent;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DragAndDropEventLogger {
    public ActivityInfo mActivityInfo;
    public final InstanceIdSequence mIdSequence = new InstanceIdSequence(Integer.MAX_VALUE);
    public InstanceId mInstanceId;
    public final UiEventLogger mUiEventLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public final InstanceId logStart(DragEvent dragEvent) {
        DragAndDropUiEventEnum dragAndDropUiEventEnum;
        ClipDescription clipDescription = dragEvent.getClipDescription();
        ClipData.Item itemAt = dragEvent.getClipData().getItemAt(0);
        InstanceId parcelableExtra = itemAt.getIntent().getParcelableExtra("android.intent.extra.LOGGING_INSTANCE_ID");
        this.mInstanceId = parcelableExtra;
        if (parcelableExtra == null) {
            this.mInstanceId = this.mIdSequence.newInstanceId();
        }
        this.mActivityInfo = itemAt.getActivityInfo();
        if (clipDescription.hasMimeType("application/vnd.android.activity")) {
            dragAndDropUiEventEnum = DragAndDropUiEventEnum.GLOBAL_APP_DRAG_START_ACTIVITY;
        } else if (clipDescription.hasMimeType("application/vnd.android.shortcut")) {
            dragAndDropUiEventEnum = DragAndDropUiEventEnum.GLOBAL_APP_DRAG_START_SHORTCUT;
        } else {
            if (!clipDescription.hasMimeType("application/vnd.android.task")) {
                throw new IllegalArgumentException("Not an app drag");
            }
            dragAndDropUiEventEnum = DragAndDropUiEventEnum.GLOBAL_APP_DRAG_START_TASK;
        }
        log(dragAndDropUiEventEnum, this.mActivityInfo);
        return this.mInstanceId;
    }
}
