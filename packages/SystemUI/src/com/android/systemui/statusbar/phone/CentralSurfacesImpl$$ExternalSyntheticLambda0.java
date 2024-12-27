package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.FoldStateListener;
import com.android.wm.shell.bubbles.Bubbles;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class CentralSurfacesImpl$$ExternalSyntheticLambda0 implements FoldStateListener.OnFoldStateChangeListener, Bubbles.BubbleExpandListener {
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CentralSurfacesImpl$$ExternalSyntheticLambda0(Object obj) {
        this.f$0 = obj;
    }

    @Override // com.android.wm.shell.bubbles.Bubbles.BubbleExpandListener
    public void onBubbleExpandChanged(String str, boolean z) {
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.f$0;
        centralSurfacesImpl.mContext.getMainExecutor().execute(new CentralSurfacesImpl$$ExternalSyntheticLambda34(centralSurfacesImpl, z, str));
    }
}
