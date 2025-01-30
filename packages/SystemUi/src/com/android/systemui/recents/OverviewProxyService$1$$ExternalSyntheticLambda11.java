package com.android.systemui.recents;

import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda11 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((CentralSurfacesImpl) ((CentralSurfaces) obj)).mCommandQueueCallbacks.togglePanel();
    }
}
