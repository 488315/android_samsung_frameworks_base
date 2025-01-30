package com.android.systemui.statusbar.iconsOnly;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import androidx.dynamicanimation.animation.SpringAnimation;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationIconTransitionController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ NotificationIconTransitionController$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((SpringAnimation) obj).cancel();
                break;
            case 1:
                ((ValueAnimator) obj).cancel();
                break;
            case 2:
                ((AnimatorSet) obj).cancel();
                break;
            case 3:
                ((AnimatorSet) obj).cancel();
                break;
            default:
                SpringAnimation springAnimation = (SpringAnimation) obj;
                if (springAnimation.mRunning) {
                    springAnimation.cancel();
                    break;
                }
                break;
        }
    }
}
