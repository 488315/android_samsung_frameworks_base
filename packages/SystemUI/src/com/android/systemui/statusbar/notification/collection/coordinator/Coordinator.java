package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifPipeline;

public interface Coordinator {
    void attach(NotifPipeline notifPipeline);
}
