package com.android.systemui.volume.view.subscreen.full;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.systemui.R;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.util.ContextUtils;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class SubFullLayoutVolumePanelMotion {
    public static final PathInterpolator HIDE_INTERPOLATOR;
    public static final PathInterpolator TITLE_TRANSLATION_INTERPOLATOR;
    public Context context;
    public AnimatorSet dualShowAnimation;
    public AnimatorSet expandShowAnimation;
    public SpringAnimation singleShowSpringAnimation;
    public final StoreInteractor storeInteractor = new StoreInteractor(null, null, 3, null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        HIDE_INTERPOLATOR = new PathInterpolator(0.7f, 0.0f, 0.83f, 0.83f);
        TITLE_TRANSLATION_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    }

    public static SpringAnimation getSeekBarTouchDownAnimation(final View view) {
        SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$getSeekBarTouchDownAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                view.setScaleY(f);
            }
        });
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(371.0f);
        springForce.setDampingRatio(1.0f);
        springAnimation.mSpring = springForce;
        return springAnimation;
    }

    public static SpringAnimation getSeekBarTouchUpAnimation(final View view) {
        SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$getSeekBarTouchUpAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                view.setScaleY(f);
            }
        });
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(371.0f);
        springForce.setDampingRatio(1.0f);
        springAnimation.mSpring = springForce;
        return springAnimation;
    }

    public static Animator getVibrationAnimator(View view, float f, float f2, int i) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "translationX", f, f2 != 0.0f ? (-f) + f2 : 0.0f);
        objectAnimatorOfFloat.setDuration(i);
        objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        return objectAnimatorOfFloat;
    }

    public static void startSeekBarTouchDownAnimation(SpringAnimation springAnimation, SpringAnimation springAnimation2, boolean z) {
        if (springAnimation2 != null) {
            if (!springAnimation2.mRunning || !springAnimation2.canSkipToEnd()) {
                springAnimation2 = null;
            }
            if (springAnimation2 != null) {
                springAnimation2.skipToEnd();
            }
        }
        springAnimation.animateToFinalPosition(z ? 1.04f : 1.07f);
    }

    public static void startSplashAnimation(final View view) {
        view.setScaleX(0.0f);
        SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
        springAnimation.cancel();
        springAnimation.mVelocity = 0.0f;
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startSplashAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                if (f2 == 0.0f) {
                    view.setPivotX(0.0f);
                    view.setPivotY(0.0f);
                }
            }
        });
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(300.0f);
        springForce.setDampingRatio(0.58f);
        springAnimation.mSpring = springForce;
        springAnimation.setStartValue(0.0f);
        springAnimation.animateToFinalPosition(1.0f);
    }

    public final void startMaxAnimation(int i, View view, View view2, View view3, View view4, View view5, View view6) {
        ViewVisibilityUtil.INSTANCE.getClass();
        ViewVisibilityUtil.setGone(view5);
        view.setVisibility(0);
        ViewVisibilityUtil.setGone(view6);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        Context context = this.context;
        if (context == null) {
            context = null;
        }
        float dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_note_max_x, context);
        Context context2 = this.context;
        if (context2 == null) {
            context2 = null;
        }
        float dimenFloat2 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_wave_s_max_x, context2);
        Context context3 = this.context;
        if (context3 == null) {
            context3 = null;
        }
        float dimenFloat3 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_wave_l_max_x, context3);
        if (VolumePanelValues.isRing(i)) {
            Context context4 = this.context;
            if (context4 == null) {
                context4 = null;
            }
            dimenFloat2 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_wave_s_max_x, context4);
            Context context5 = this.context;
            dimenFloat3 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_wave_l_max_x, context5 != null ? context5 : null);
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.5f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.5f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat);
        animatorSet.playTogether(objectAnimatorOfFloat2);
        animatorSet.setDuration(150L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimenFloat);
        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimenFloat2);
        ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(view3, "x", view3.getX(), dimenFloat3);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(objectAnimatorOfFloat3);
        animatorSet2.playTogether(objectAnimatorOfFloat4);
        animatorSet2.playTogether(objectAnimatorOfFloat5);
        animatorSet2.setDuration(200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet);
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.start();
    }

    public final void startMidAnimation(final int i, final int i2, View view, View view2, View view3, View view4, View view5, View view6) {
        ViewVisibilityUtil.INSTANCE.getClass();
        ViewVisibilityUtil.setGone(view5);
        view.setVisibility(0);
        ViewVisibilityUtil.setGone(view6);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        Context context = this.context;
        if (context == null) {
            context = null;
        }
        float dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_note_mid_x, context);
        Context context2 = this.context;
        if (context2 == null) {
            context2 = null;
        }
        float dimenFloat2 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_wave_s_mid_x, context2);
        Context context3 = this.context;
        if (context3 == null) {
            context3 = null;
        }
        float dimenFloat3 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_wave_l_mid_x, context3);
        if (VolumePanelValues.isRing(i)) {
            Context context4 = this.context;
            if (context4 == null) {
                context4 = null;
            }
            dimenFloat2 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_wave_s_mid_x, context4);
            Context context5 = this.context;
            dimenFloat3 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_wave_l_mid_x, context5 != null ? context5 : null);
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.5f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat);
        animatorSet.playTogether(objectAnimatorOfFloat2);
        animatorSet.setDuration(100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimenFloat);
        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimenFloat2);
        ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(view3, "x", view3.getX(), dimenFloat3);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(objectAnimatorOfFloat3);
        animatorSet2.playTogether(objectAnimatorOfFloat4);
        animatorSet2.playTogether(objectAnimatorOfFloat5);
        animatorSet2.setDuration(200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.playTogether(animatorSet);
        animatorSet3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startMidAnimation$2$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                this.this$0.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_ICON_ANIMATION_FINISHED).stream(i).iconTargetState(i2).iconCurrentState(2).build(), false);
            }
        });
        animatorSet3.start();
    }

    public final void startMinAnimation(final int i, final int i2, View view, View view2, View view3, View view4, View view5, View view6) {
        float f;
        ViewVisibilityUtil.INSTANCE.getClass();
        ViewVisibilityUtil.setGone(view5);
        view.setVisibility(0);
        ViewVisibilityUtil.setGone(view6);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        Context context = this.context;
        if (context == null) {
            context = null;
        }
        float dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_note_min_x, context);
        if (VolumePanelValues.isRing(i)) {
            Context context2 = this.context;
            if (context2 == null) {
                context2 = null;
            }
            dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_spk_min_x, context2);
            f = 0.3f;
        } else {
            f = 0.0f;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat);
        animatorSet.playTogether(objectAnimatorOfFloat2);
        animatorSet.setDuration(100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimenFloat);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(objectAnimatorOfFloat3);
        if (VolumePanelValues.isRing(i)) {
            Context context3 = this.context;
            animatorSet2.playTogether(ObjectAnimator.ofFloat(view2, "x", view2.getX(), ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_wave_s_min_x, context3 != null ? context3 : null)));
        }
        animatorSet2.setDuration(200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet);
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startMinAnimation$2$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                this.this$0.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_ICON_ANIMATION_FINISHED).stream(i).iconTargetState(i2).iconCurrentState(1).build(), false);
            }
        });
        animatorSet3.start();
    }

    public final void startMuteAnimation(int i, View view, View view2, View view3, View view4, View view5, View view6) {
        float dimenFloat;
        ViewVisibilityUtil.INSTANCE.getClass();
        view5.setVisibility(0);
        view.setVisibility(4);
        view6.setVisibility(0);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        if (VolumePanelValues.isRing(i)) {
            Context context = this.context;
            dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_spk_min_x, context != null ? context : null);
        } else {
            Context context2 = this.context;
            dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_media_icon_note_min_x, context2 != null ? context2 : null);
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat);
        animatorSet.playTogether(objectAnimatorOfFloat2);
        animatorSet.setDuration(100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimenFloat);
        objectAnimatorOfFloat3.setDuration(200L);
        objectAnimatorOfFloat3.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animatorSet);
        animatorSet2.playTogether(objectAnimatorOfFloat3);
        animatorSet2.start();
        startSplashAnimation(view6);
    }

    public final void startSoundVibrationAnimation(View view, View view2, View view3, View view4, View view5, View view6) {
        ViewVisibilityUtil.INSTANCE.getClass();
        view.setVisibility(0);
        view5.setVisibility(4);
        view2.setVisibility(4);
        view6.setVisibility(4);
        view3.setVisibility(4);
        view4.setVisibility(4);
        Context context = this.context;
        if (context == null) {
            context = null;
        }
        float dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_sound_icon_spk_min_x, context);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view4, "alpha", view4.getAlpha(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorOfFloat);
        animatorSet.playTogether(objectAnimatorOfFloat2);
        animatorSet.setDuration(50L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimenFloat);
        objectAnimatorOfFloat3.setDuration(200L);
        objectAnimatorOfFloat3.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animatorSet);
        animatorSet2.playTogether(objectAnimatorOfFloat3);
        animatorSet2.start();
        startVibrationAnimation(view);
    }

    public final void startVibrationAnimation(View view) {
        Context context = this.context;
        if (context == null) {
            context = null;
        }
        float dimenFloat = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_vibrate_init, context);
        Context context2 = this.context;
        float dimenFloat2 = ContextUtils.getDimenFloat(R.dimen.sub_full_volume_vibrate_offset, context2 != null ? context2 : null);
        float f = -dimenFloat;
        float f2 = dimenFloat - dimenFloat2;
        float f3 = -(dimenFloat - (dimenFloat2 * 2));
        List<Animator> listMutableListOf = CollectionsKt__CollectionsKt.mutableListOf(getVibrationAnimator(view, 0.0f, f, 60), getVibrationAnimator(view, f, f2, 80), getVibrationAnimator(view, f2, f3, 100), getVibrationAnimator(view, f3, 0.0f, 120));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(listMutableListOf);
        animatorSet.start();
    }
}
