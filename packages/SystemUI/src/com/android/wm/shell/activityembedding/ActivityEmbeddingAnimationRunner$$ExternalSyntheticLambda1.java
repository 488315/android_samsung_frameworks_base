package com.android.wm.shell.activityembedding;

import android.R;
import android.graphics.Rect;
import android.view.animation.Animation;
import android.window.TransitionInfo;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.transition.TransitionAnimationHelper;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public final /* synthetic */ class ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityEmbeddingAnimationSpec f$0;

    public /* synthetic */ ActivityEmbeddingAnimationRunner$$ExternalSyntheticLambda1(ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec, int i) {
        this.$r8$classId = i;
        this.f$0 = activityEmbeddingAnimationSpec;
    }

    public final Animation get(TransitionInfo transitionInfo, TransitionInfo.Change change, Rect rect) {
        switch (this.$r8$classId) {
            case 0:
                ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec = this.f$0;
                activityEmbeddingAnimationSpec.getClass();
                boolean zIsOpeningType = TransitionUtil.isOpeningType(change.getMode());
                Animation animationLoadCustomAnimation = activityEmbeddingAnimationSpec.loadCustomAnimation(change.getAnimationOptions(), change.getMode());
                if (animationLoadCustomAnimation == null) {
                    Animation animationLoadAttributeAnimation = TransitionAnimationHelper.loadAttributeAnimation(TransitionAnimationHelper.getTransitionTypeFromInfo(transitionInfo), transitionInfo, change, 0, activityEmbeddingAnimationSpec.mTransitionAnimation, false);
                    if (animationLoadAttributeAnimation != null && animationLoadAttributeAnimation.getShowBackdrop()) {
                        animationLoadCustomAnimation = activityEmbeddingAnimationSpec.mTransitionAnimation.loadDefaultAnimationRes(zIsOpeningType ? 17432941 : 17432942);
                    } else if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
                        animationLoadCustomAnimation = activityEmbeddingAnimationSpec.mTransitionAnimation.loadDefaultAnimationRes(zIsOpeningType ? R.anim.ft_avd_toarrow_rectangle_2_pivot_animation : R.anim.ft_avd_toarrow_rectangle_3_animation);
                        if (change.getConfiguration().windowConfiguration.getWindowingMode() == 5) {
                            animationLoadCustomAnimation.setBackdropColor(-16777216);
                            animationLoadCustomAnimation.setShowBackdrop(true);
                        }
                    } else {
                        animationLoadCustomAnimation = activityEmbeddingAnimationSpec.mTransitionAnimation.loadDefaultAnimationRes(zIsOpeningType ? R.anim.activity_open_enter : R.anim.activity_open_exit);
                    }
                }
                animationLoadCustomAnimation.initialize(rect.width(), rect.height(), rect.width(), rect.height());
                animationLoadCustomAnimation.scaleCurrentDuration(activityEmbeddingAnimationSpec.mTransitionAnimationScaleSetting);
                return animationLoadCustomAnimation;
            default:
                ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec2 = this.f$0;
                activityEmbeddingAnimationSpec2.getClass();
                boolean zIsOpeningType2 = TransitionUtil.isOpeningType(change.getMode());
                Animation animationLoadCustomAnimation2 = activityEmbeddingAnimationSpec2.loadCustomAnimation(change.getAnimationOptions(), change.getMode());
                if (animationLoadCustomAnimation2 == null) {
                    Animation animationLoadAttributeAnimation2 = TransitionAnimationHelper.loadAttributeAnimation(TransitionAnimationHelper.getTransitionTypeFromInfo(transitionInfo), transitionInfo, change, 0, activityEmbeddingAnimationSpec2.mTransitionAnimation, false);
                    if (animationLoadAttributeAnimation2 != null && animationLoadAttributeAnimation2.getShowBackdrop()) {
                        animationLoadCustomAnimation2 = activityEmbeddingAnimationSpec2.mTransitionAnimation.loadDefaultAnimationRes(zIsOpeningType2 ? 17432939 : 17432940);
                    } else if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
                        animationLoadCustomAnimation2 = activityEmbeddingAnimationSpec2.mTransitionAnimation.loadDefaultAnimationRes(zIsOpeningType2 ? R.anim.ft_avd_toarrow_rectangle_2_animation : R.anim.ft_avd_toarrow_rectangle_2_pivot_0_animation);
                        if (change.getConfiguration().windowConfiguration.getWindowingMode() == 5) {
                            animationLoadCustomAnimation2.setBackdropColor(-16777216);
                            animationLoadCustomAnimation2.setShowBackdrop(true);
                        }
                    } else {
                        animationLoadCustomAnimation2 = activityEmbeddingAnimationSpec2.mTransitionAnimation.loadDefaultAnimationRes(zIsOpeningType2 ? R.anim.activity_close_enter : R.anim.activity_close_exit);
                    }
                }
                animationLoadCustomAnimation2.initialize(rect.width(), rect.height(), rect.width(), rect.height());
                animationLoadCustomAnimation2.scaleCurrentDuration(activityEmbeddingAnimationSpec2.mTransitionAnimationScaleSetting);
                return animationLoadCustomAnimation2;
        }
    }
}
