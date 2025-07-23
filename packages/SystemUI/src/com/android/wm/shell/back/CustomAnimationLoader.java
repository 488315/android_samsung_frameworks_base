package com.android.wm.shell.back;

import android.view.animation.Animation;
import android.window.BackNavigationInfo;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.protolog.ShellProtoLogGroup;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CustomAnimationLoader {
    public final TransitionAnimation transitionAnimation;

    public CustomAnimationLoader(TransitionAnimation transitionAnimation) {
        this.transitionAnimation = transitionAnimation;
    }

    public final Animation loadAnimation(BackNavigationInfo.CustomAnimationInfo customAnimationInfo, boolean z) {
        Animation animation;
        if ((z && customAnimationInfo.getCustomEnterAnim() != 0) || (!z && customAnimationInfo.getCustomExitAnim() != 0)) {
            animation = this.transitionAnimation.loadAppTransitionAnimation(customAnimationInfo.getPackageName(), z ? customAnimationInfo.getCustomEnterAnim() : customAnimationInfo.getCustomExitAnim());
        } else if (customAnimationInfo.getWindowAnimations() != 0) {
            animation = this.transitionAnimation.loadAnimationAttr(customAnimationInfo.getPackageName(), customAnimationInfo.getWindowAnimations(), z ? 6 : 7, false);
        } else {
            animation = null;
        }
        if (animation == null && z) {
            animation = this.transitionAnimation.loadDefaultAnimationAttr(6, false);
        }
        if (animation != null) {
            ProtoLog.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, "custom animation loaded %s", new Object[]{animation});
            return animation;
        }
        ProtoLog.e(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, "No custom animation loaded", new Object[0]);
        return animation;
    }
}
