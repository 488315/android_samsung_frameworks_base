package com.android.wm.shell.common;

import android.animation.ValueAnimator;
import android.util.Slog;
import android.view.InsetsSource;
import com.android.wm.shell.common.DisplayImeController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DisplayImeController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DisplayImeController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DisplayImeController displayImeController = (DisplayImeController) this.f$0;
                displayImeController.mDisplayController.addDisplayWindowListener(displayImeController);
                break;
            default:
                DisplayImeController.PerDisplay perDisplay = (DisplayImeController.PerDisplay) this.f$0;
                InsetsSource peekSource = perDisplay.mInsetsState.peekSource(InsetsSource.ID_IME);
                ValueAnimator valueAnimator = perDisplay.mAnimation;
                if (valueAnimator != null && perDisplay.mAnimationDirection == 1 && peekSource != null && perDisplay.mImeSourceControl != null) {
                    valueAnimator.start();
                    break;
                } else {
                    Slog.e("DisplayImeController", "anim failed. mAnimation=" + perDisplay.mAnimation + " mAnimationDirection=" + perDisplay.mAnimationDirection + " source=" + peekSource + " mImeSourceControl=" + perDisplay.mImeSourceControl);
                    break;
                }
        }
    }
}
