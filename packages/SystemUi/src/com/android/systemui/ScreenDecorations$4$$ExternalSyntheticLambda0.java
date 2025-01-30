package com.android.systemui;

import com.android.systemui.ScreenDecorations;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class ScreenDecorations$4$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ScreenDecorations$4$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ScreenDecorations screenDecorations = ((ScreenDecorations.C09754) this.f$0).this$0;
                screenDecorations.updateOverlayProviderViews(new Integer[]{Integer.valueOf(screenDecorations.mFaceScanningViewId)});
                break;
            default:
                ((ScreenDecorations.ComponentCallbacksC09765) this.f$0).this$0.mCoverOverlay.rootView.invalidate();
                break;
        }
    }
}
