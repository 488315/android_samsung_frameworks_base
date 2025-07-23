package com.android.wm.shell.activityembedding;

import android.R;
import android.graphics.Rect;
import android.view.animation.Animation;
import android.window.TransitionInfo;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.transition.TransitionAnimationHelper;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                boolean isOpeningType = TransitionUtil.isOpeningType(change.getMode());
                Animation loadCustomAnimation = activityEmbeddingAnimationSpec.loadCustomAnimation(change.getAnimationOptions(), change.getMode());
                if (loadCustomAnimation == null) {
                    Animation loadAttributeAnimation = TransitionAnimationHelper.loadAttributeAnimation(TransitionAnimationHelper.getTransitionTypeFromInfo(transitionInfo), transitionInfo, change, 0, activityEmbeddingAnimationSpec.mTransitionAnimation, false);
                    if (loadAttributeAnimation != null && loadAttributeAnimation.getShowBackdrop()) {
                        loadCustomAnimation = activityEmbeddingAnimationSpec.mTransitionAnimation.loadDefaultAnimationRes(isOpeningType ? 17432941 : 17432942);
                    } else if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
                        loadCustomAnimation = activityEmbeddingAnimationSpec.mTransitionAnimation.loadDefaultAnimationRes(isOpeningType ? R.anim.ft_avd_toarrow_rectangle_2_pivot_animation : R.anim.ft_avd_toarrow_rectangle_3_animation);
                        if (change.getConfiguration().windowConfiguration.getWindowingMode() == 5) {
                            loadCustomAnimation.setBackdropColor(-16777216);
                            loadCustomAnimation.setShowBackdrop(true);
                        }
                    } else {
                        loadCustomAnimation = activityEmbeddingAnimationSpec.mTransitionAnimation.loadDefaultAnimationRes(isOpeningType ? R.anim.activity_open_enter : R.anim.activity_open_exit);
                    }
                }
                loadCustomAnimation.initialize(rect.width(), rect.height(), rect.width(), rect.height());
                loadCustomAnimation.scaleCurrentDuration(activityEmbeddingAnimationSpec.mTransitionAnimationScaleSetting);
                return loadCustomAnimation;
            default:
                ActivityEmbeddingAnimationSpec activityEmbeddingAnimationSpec2 = this.f$0;
                activityEmbeddingAnimationSpec2.getClass();
                boolean isOpeningType2 = TransitionUtil.isOpeningType(change.getMode());
                Animation loadCustomAnimation2 = activityEmbeddingAnimationSpec2.loadCustomAnimation(change.getAnimationOptions(), change.getMode());
                if (loadCustomAnimation2 == null) {
                    Animation loadAttributeAnimation2 = TransitionAnimationHelper.loadAttributeAnimation(TransitionAnimationHelper.getTransitionTypeFromInfo(transitionInfo), transitionInfo, change, 0, activityEmbeddingAnimationSpec2.mTransitionAnimation, false);
                    if (loadAttributeAnimation2 != null && loadAttributeAnimation2.getShowBackdrop()) {
                        loadCustomAnimation2 = activityEmbeddingAnimationSpec2.mTransitionAnimation.loadDefaultAnimationRes(isOpeningType2 ? 17432939 : 17432940);
                    } else if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
                        loadCustomAnimation2 = activityEmbeddingAnimationSpec2.mTransitionAnimation.loadDefaultAnimationRes(isOpeningType2 ? R.anim.ft_avd_toarrow_rectangle_2_animation : R.anim.ft_avd_toarrow_rectangle_2_pivot_0_animation);
                        if (change.getConfiguration().windowConfiguration.getWindowingMode() == 5) {
                            loadCustomAnimation2.setBackdropColor(-16777216);
                            loadCustomAnimation2.setShowBackdrop(true);
                        }
                    } else {
                        loadCustomAnimation2 = activityEmbeddingAnimationSpec2.mTransitionAnimation.loadDefaultAnimationRes(isOpeningType2 ? R.anim.activity_close_enter : R.anim.activity_close_exit);
                    }
                }
                loadCustomAnimation2.initialize(rect.width(), rect.height(), rect.width(), rect.height());
                loadCustomAnimation2.scaleCurrentDuration(activityEmbeddingAnimationSpec2.mTransitionAnimationScaleSetting);
                return loadCustomAnimation2;
        }
    }
}
