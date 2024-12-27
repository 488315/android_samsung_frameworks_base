package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.PipelineDumpable;
import com.android.systemui.statusbar.notification.collection.PipelineDumper;

public interface NotifCoordinators extends Coordinator, PipelineDumpable {
    @Override // com.android.systemui.statusbar.notification.collection.PipelineDumpable
    /* synthetic */ void dumpPipeline(PipelineDumper pipelineDumper);
}
