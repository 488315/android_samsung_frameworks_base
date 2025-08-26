package com.android.wm.shell.back;

import android.view.animation.Animation;
import android.window.BackNavigationInfo;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.protolog.ShellProtoLogGroup;

/* loaded from: classes3.dex */
public final class CustomAnimationLoader {
    public final TransitionAnimation transitionAnimation;

    public CustomAnimationLoader(TransitionAnimation transitionAnimation) {
        this.transitionAnimation = transitionAnimation;
    }

    public final Animation loadAnimation(BackNavigationInfo.CustomAnimationInfo customAnimationInfo, boolean z) {
        Animation animationLoadAnimationAttr;
        if ((z && customAnimationInfo.getCustomEnterAnim() != 0) || (!z && customAnimationInfo.getCustomExitAnim() != 0)) {
            animationLoadAnimationAttr = this.transitionAnimation.loadAppTransitionAnimation(customAnimationInfo.getPackageName(), z ? customAnimationInfo.getCustomEnterAnim() : customAnimationInfo.getCustomExitAnim());
        } else if (customAnimationInfo.getWindowAnimations() != 0) {
            animationLoadAnimationAttr = this.transitionAnimation.loadAnimationAttr(customAnimationInfo.getPackageName(), customAnimationInfo.getWindowAnimations(), z ? 6 : 7, false);
        } else {
            animationLoadAnimationAttr = null;
        }
        if (animationLoadAnimationAttr == null && z) {
            animationLoadAnimationAttr = this.transitionAnimation.loadDefaultAnimationAttr(6, false);
        }
        if (animationLoadAnimationAttr != null) {
            ProtoLog.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, "custom animation loaded %s", new Object[]{animationLoadAnimationAttr});
            return animationLoadAnimationAttr;
        }
        ProtoLog.e(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, "No custom animation loaded", new Object[0]);
        return animationLoadAnimationAttr;
    }
}
