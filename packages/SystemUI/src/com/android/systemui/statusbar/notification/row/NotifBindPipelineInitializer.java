package com.android.systemui.statusbar.notification.row;

/* loaded from: classes3.dex */
public class NotifBindPipelineInitializer {
    public final NotifBindPipeline mNotifBindPipeline;
    public final RowContentBindStage mRowContentBindStage;

    public NotifBindPipelineInitializer(NotifBindPipeline notifBindPipeline, RowContentBindStage rowContentBindStage) {
        this.mNotifBindPipeline = notifBindPipeline;
        this.mRowContentBindStage = rowContentBindStage;
    }
}
