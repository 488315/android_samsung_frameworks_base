package com.android.wm.shell.common;

import android.animation.ValueAnimator;
import android.util.Slog;
import android.view.InsetsSource;
import com.android.wm.shell.common.DisplayImeController;

/* loaded from: classes3.dex */
public final /* synthetic */ class DisplayImeController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DisplayImeController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                DisplayImeController displayImeController = (DisplayImeController) obj;
                displayImeController.mDisplayController.addDisplayWindowListener(displayImeController, -1);
                break;
            default:
                DisplayImeController.PerDisplay perDisplay = (DisplayImeController.PerDisplay) obj;
                InsetsSource insetsSourcePeekSource = perDisplay.mInsetsState.peekSource(InsetsSource.ID_IME);
                ValueAnimator valueAnimator = perDisplay.mAnimation;
                if (valueAnimator != null && perDisplay.mAnimationDirection == 1 && insetsSourcePeekSource != null && perDisplay.mImeSourceControl != null) {
                    valueAnimator.start();
                    break;
                } else {
                    Slog.e("DisplayImeController", "anim failed. mAnimation=" + perDisplay.mAnimation + " mAnimationDirection=" + perDisplay.mAnimationDirection + " source=" + insetsSourcePeekSource + " mImeSourceControl=" + perDisplay.mImeSourceControl);
                    break;
                }
                break;
        }
    }
}
