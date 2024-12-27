package com.android.systemui.volume.view.expand;

import com.android.systemui.volume.store.StoreInteractor;
import com.samsung.systemui.splugins.volume.VolumePanelAction;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public abstract /* synthetic */ class VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0 {
    public static void m(VolumePanelAction.Builder builder, boolean z, StoreInteractor storeInteractor, boolean z2) {
        storeInteractor.sendAction(builder.isFromOutside(z).build(), z2);
    }
}
