package com.android.systemui.volume.view.expand;

import com.android.systemui.volume.store.StoreInteractor;
import com.samsung.systemui.splugins.volume.VolumePanelAction;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract /* synthetic */ class VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0 {
    public static void m(VolumePanelAction.Builder builder, boolean z, StoreInteractor storeInteractor, boolean z2) {
        storeInteractor.sendAction(builder.isFromOutside(z).build(), z2);
    }
}
