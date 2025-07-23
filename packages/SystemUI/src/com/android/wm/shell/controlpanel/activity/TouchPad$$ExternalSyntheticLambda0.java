package com.android.wm.shell.controlpanel.activity;

import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class TouchPad$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TouchPad f$0;

    public /* synthetic */ TouchPad$$ExternalSyntheticLambda0(TouchPad touchPad, int i) {
        this.$r8$classId = i;
        this.f$0 = touchPad;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        TouchPad touchPad = this.f$0;
        switch (i) {
            case 0:
                touchPad.startFadeInAnimation(touchPad.mTouchPadLine, true);
                break;
            default:
                View view = touchPad.mOverlayView;
                if (view != null) {
                    view.setVisibility(8);
                    if (touchPad.mOverlayView.isAttachedToWindow()) {
                        touchPad.removeView();
                        break;
                    }
                }
                break;
        }
    }
}
