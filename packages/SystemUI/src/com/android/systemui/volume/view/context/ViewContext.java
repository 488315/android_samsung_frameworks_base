package com.android.systemui.volume.view.context;

import android.content.Context;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.store.VolumePanelStore;
import com.samsung.systemui.splugins.volume.VolumePanelState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ViewContext {
    Context getContext();

    VolumePanelState getPanelState$1();

    VolumePanelStore getStore$1();

    VolumeDependencyBase getVolDeps();
}
