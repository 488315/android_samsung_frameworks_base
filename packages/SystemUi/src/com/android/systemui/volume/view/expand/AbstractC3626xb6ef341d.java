package com.android.systemui.volume.view.expand;

import com.android.systemui.volume.store.StoreInteractor;
import com.samsung.systemui.splugins.volume.VolumePanelAction;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0 */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC3626xb6ef341d {
    /* renamed from: m */
    public static void m227m(VolumePanelAction.Builder builder, boolean z, StoreInteractor storeInteractor, boolean z2) {
        storeInteractor.sendAction(builder.isFromOutside(z).build(), z2);
    }
}
