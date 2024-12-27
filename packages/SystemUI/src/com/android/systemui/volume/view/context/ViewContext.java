package com.android.systemui.volume.view.context;

import android.content.Context;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.store.VolumePanelStore;
import com.samsung.systemui.splugins.volume.VolumePanelState;

public interface ViewContext {
    Context getContext();

    VolumePanelState getPanelState$1();

    VolumePanelStore getStore$1();

    VolumeDependencyBase getVolDeps();
}
