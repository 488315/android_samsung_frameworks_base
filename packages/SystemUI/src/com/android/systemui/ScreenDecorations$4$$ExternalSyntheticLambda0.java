package com.android.systemui;

import com.android.systemui.ScreenDecorations;

/* loaded from: classes.dex */
public final /* synthetic */ class ScreenDecorations$4$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((ScreenDecorations.AnonymousClass4) obj).this$0.mOverlays[1].rootView.invalidate();
                break;
            default:
                ((ScreenDecorations.AnonymousClass6) obj).this$0.mOverlays[1].rootView.invalidate();
                break;
        }
    }
}
