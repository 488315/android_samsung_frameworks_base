package com.android.systemui.screenshot.sep.widget;

import android.view.animation.Animation;
import com.android.systemui.screenshot.ScreenshotController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SemScreenshotLayout$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Animation.AnimationListener f$0;

    public /* synthetic */ SemScreenshotLayout$1$$ExternalSyntheticLambda0(Animation.AnimationListener animationListener, int i) {
        this.$r8$classId = i;
        this.f$0 = animationListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SemScreenshotLayout.this.mCallback.finishAnimation();
                break;
            default:
                ScreenshotController.m1637$$Nest$mfinishDismiss(ScreenshotController.this);
                break;
        }
    }
}
