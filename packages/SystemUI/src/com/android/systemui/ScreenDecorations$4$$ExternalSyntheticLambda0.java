package com.android.systemui;

import com.android.systemui.ScreenDecorations;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
