package com.android.systemui.statusbar.notification.collection.listbuilder;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.render.NotifGroupController;

public interface OnAfterRenderGroupListener {
    void onAfterRenderGroup(GroupEntry groupEntry, NotifGroupController notifGroupController);
}
