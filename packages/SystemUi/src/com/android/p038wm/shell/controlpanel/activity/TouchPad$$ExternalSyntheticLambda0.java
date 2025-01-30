package com.android.p038wm.shell.controlpanel.activity;

import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TouchPad$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TouchPad f$0;

    public /* synthetic */ TouchPad$$ExternalSyntheticLambda0(TouchPad touchPad, int i) {
        this.$r8$classId = i;
        this.f$0 = touchPad;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                TouchPad touchPad = this.f$0;
                touchPad.startFadeInAnimation(touchPad.mTouchPadLine, true);
                break;
            default:
                TouchPad touchPad2 = this.f$0;
                View view = touchPad2.mOverlayView;
                if (view != null) {
                    view.setVisibility(8);
                    if (touchPad2.mOverlayView.isAttachedToWindow()) {
                        touchPad2.removeView();
                        break;
                    }
                }
                break;
        }
    }
}
