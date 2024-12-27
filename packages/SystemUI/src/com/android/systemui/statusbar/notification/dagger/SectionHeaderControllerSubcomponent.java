package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;

public interface SectionHeaderControllerSubcomponent {
    SectionHeaderController getHeaderController();

    NodeController getNodeController();
}
