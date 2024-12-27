package com.android.systemui.volume.view.expand;

import com.android.systemui.volume.store.StoreInteractor;
import com.samsung.systemui.splugins.volume.VolumePanelAction;

public abstract /* synthetic */ class VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0 {
    public static void m(VolumePanelAction.Builder builder, boolean z, StoreInteractor storeInteractor, boolean z2) {
        storeInteractor.sendAction(builder.isFromOutside(z).build(), z2);
    }
}
