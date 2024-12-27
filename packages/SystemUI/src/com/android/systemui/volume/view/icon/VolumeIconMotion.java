package com.android.systemui.volume.view.icon;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.ContextUtils;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class VolumeIconMotion {
    public static final PathInterpolator ALPHA_INTERPOLATOR;
    public final Context context;
    public Animator lastAnimtor;
    public ValueAnimator shockValueAnimator = new ValueAnimator();
    public final StoreInteractor storeInteractor;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        ALPHA_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    }

    public VolumeIconMotion(VolumePanelStore volumePanelStore, Context context) {
        this.context = context;
        this.storeInteractor = new StoreInteractor(null, volumePanelStore);
    }

    public static Animator getVibrationAnimator(View view, float f, float f2, int i) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationX", f, f2 != 0.0f ? (-f) + f2 : 0.0f);
        ofFloat.setDuration(i);
        ofFloat.setInterpolator(new LinearInterpolator());
        return ofFloat;
    }

    public static void startSplashAnimation(final View view) {
        view.setScaleX(0.0f);
        SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.SCALE_X);
        springAnimation.mVelocity = 0.0f;
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.icon.VolumeIconMotion$startSplashAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
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

    public final void cancelLastAnimator() {
        Animator animator = this.lastAnimtor;
        if (animator != null) {
            animator.cancel();
        }
        this.lastAnimtor = null;
    }

    public final void startMaxAnimation(int i, View view, View view2, View view3, View view4, View view5, View view6, ScreenState screenState) {
        cancelLastAnimator();
        ViewVisibilityUtil.INSTANCE.getClass();
        view5.setVisibility(8);
        view.setVisibility(0);
        view6.setVisibility(8);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        int dimenInt = ContextUtils.getDimenInt(R.dimen.volume_media_icon_note_max_x, this.context);
        Context context = this.context;
        ScreenState screenState2 = ScreenState.SCREEN_NORMAL;
        int dimenInt2 = ContextUtils.getDimenInt(screenState == screenState2 ? R.dimen.volume_media_icon_wave_s_max_x : R.dimen.volume_sub_display_media_icon_wave_s_max_x, context);
        int dimenInt3 = ContextUtils.getDimenInt(screenState == screenState2 ? R.dimen.volume_media_icon_wave_l_max_x : R.dimen.volume_sub_display_media_icon_wave_l_max_x, this.context);
        if (screenState != screenState2 && BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG) {
            dimenInt = ContextUtils.getDimenInt(R.dimen.volume_sub_large_display_media_icon_note_max_x, this.context);
            dimenInt2 = ContextUtils.getDimenInt(R.dimen.volume_sub_large_display_media_icon_wave_s_max_x, this.context);
            dimenInt3 = ContextUtils.getDimenInt(R.dimen.volume_sub_large_display_media_icon_wave_l_max_x, this.context);
        }
        if (VolumePanelValues.isRing(i)) {
            dimenInt2 = ContextUtils.getDimenInt(screenState == screenState2 ? R.dimen.volume_sound_icon_wave_s_max_x : R.dimen.volume_sub_display_sound_icon_wave_s_max_x, this.context);
            dimenInt3 = ContextUtils.getDimenInt(screenState == screenState2 ? R.dimen.volume_sound_icon_wave_l_max_x : R.dimen.volume_sub_display_sound_icon_wave_l_max_x, this.context);
            if (screenState != screenState2 && BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG) {
                dimenInt2 = ContextUtils.getDimenInt(R.dimen.volume_sub_large_display_sound_icon_wave_s_max_x, this.context);
                dimenInt3 = ContextUtils.getDimenInt(R.dimen.volume_sub_large_display_sound_icon_wave_l_max_x, this.context);
            }
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.5f));
        animatorSet.playTogether(ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.5f));
        animatorSet.setDuration(150L);
        animatorSet.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ObjectAnimator.ofFloat(view, "x", view.getX(), dimenInt));
        animatorSet2.playTogether(ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimenInt2));
        animatorSet2.playTogether(ObjectAnimator.ofFloat(view3, "x", view3.getX(), dimenInt3));
        animatorSet2.setDuration(200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet);
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.start();
        this.lastAnimtor = animatorSet3;
    }

    public final void startMidAnimation(final int i, final int i2, View view, View view2, View view3, View view4, View view5, View view6, ScreenState screenState) {
        cancelLastAnimator();
        ViewVisibilityUtil.INSTANCE.getClass();
        view5.setVisibility(8);
        view.setVisibility(0);
        view6.setVisibility(8);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        int dimenInt = ContextUtils.getDimenInt(R.dimen.volume_media_icon_note_mid_x, this.context);
        Context context = this.context;
        ScreenState screenState2 = ScreenState.SCREEN_NORMAL;
        int dimenInt2 = ContextUtils.getDimenInt(screenState == screenState2 ? R.dimen.volume_media_icon_wave_s_mid_x : R.dimen.volume_sub_display_media_icon_wave_s_mid_x, context);
        int dimenInt3 = ContextUtils.getDimenInt(screenState == screenState2 ? R.dimen.volume_media_icon_wave_l_mid_x : R.dimen.volume_sub_display_media_icon_wave_l_mid_x, this.context);
        if (screenState != screenState2 && BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG) {
            dimenInt = ContextUtils.getDimenInt(R.dimen.volume_sub_large_display_media_icon_note_mid_x, this.context);
            dimenInt2 = ContextUtils.getDimenInt(R.dimen.volume_sub_large_display_media_icon_wave_s_mid_x, this.context);
            dimenInt3 = ContextUtils.getDimenInt(R.dimen.volume_sub_large_display_media_icon_wave_l_mid_x, this.context);
        }
        if (VolumePanelValues.isRing(i)) {
            dimenInt2 = ContextUtils.getDimenInt(screenState == screenState2 ? R.dimen.volume_sound_icon_wave_s_mid_x : R.dimen.volume_sub_display_sound_icon_wave_s_mid_x, this.context);
            dimenInt3 = ContextUtils.getDimenInt(screenState == screenState2 ? R.dimen.volume_sound_icon_wave_l_mid_x : R.dimen.volume_sub_display_sound_icon_wave_l_mid_x, this.context);
            if (screenState != screenState2 && BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG) {
                dimenInt2 = ContextUtils.getDimenInt(R.dimen.volume_sub_large_display_sound_icon_wave_s_mid_x, this.context);
                dimenInt3 = ContextUtils.getDimenInt(R.dimen.volume_sub_large_display_sound_icon_wave_l_mid_x, this.context);
            }
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.5f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimenInt);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimenInt2);
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(view3, "x", view3.getX(), dimenInt3);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ofFloat3);
        animatorSet2.playTogether(ofFloat4);
        animatorSet2.playTogether(ofFloat5);
        animatorSet2.setDuration(200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.playTogether(animatorSet);
        animatorSet3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.icon.VolumeIconMotion$startMidAnimation$2$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                VolumeIconMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_ICON_ANIMATION_FINISHED).stream(i).iconTargetState(i2).iconCurrentState(2).build(), true);
            }
        });
        animatorSet3.start();
        this.lastAnimtor = animatorSet3;
    }

    public final void startMinAnimation(final int i, final int i2, View view, View view2, View view3, View view4, View view5, View view6, ScreenState screenState) {
        float f;
        cancelLastAnimator();
        ViewVisibilityUtil.INSTANCE.getClass();
        view5.setVisibility(8);
        view.setVisibility(0);
        view6.setVisibility(8);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        Context context = this.context;
        ScreenState screenState2 = ScreenState.SCREEN_NORMAL;
        int dimenInt = ContextUtils.getDimenInt(screenState == screenState2 ? R.dimen.volume_media_icon_note_min_x : R.dimen.volume_sub_display_media_icon_note_min_x, context);
        if (VolumePanelValues.isRing(i)) {
            dimenInt = ContextUtils.getDimenInt(screenState == screenState2 ? R.dimen.volume_sound_icon_spk_min_x : R.dimen.volume_sub_display_sound_icon_spk_min_x, this.context);
            f = 0.3f;
        } else {
            f = 0.0f;
        }
        if (screenState != screenState2 && BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG) {
            dimenInt = ContextUtils.getDimenInt(R.dimen.volume_sub_large_display_media_icon_note_min_x, this.context);
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimenInt);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ofFloat3);
        if (VolumePanelValues.isRing(i)) {
            int dimenInt2 = ContextUtils.getDimenInt(screenState == screenState2 ? R.dimen.volume_sound_icon_wave_s_min_x : R.dimen.volume_sub_display_media_icon_wave_s_mid_x, this.context);
            if (screenState != screenState2 && BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG) {
                dimenInt2 = ContextUtils.getDimenInt(R.dimen.volume_sub_large_display_sound_icon_wave_s_min_x, this.context);
            }
            animatorSet2.playTogether(ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimenInt2));
        }
        animatorSet2.setDuration(200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet);
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.icon.VolumeIconMotion$startMinAnimation$2$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                VolumeIconMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_ICON_ANIMATION_FINISHED).stream(i).iconTargetState(i2).iconCurrentState(1).build(), true);
            }
        });
        animatorSet3.start();
        this.lastAnimtor = animatorSet3;
    }

    public final void startMuteAnimation(int i, View view, View view2, View view3, View view4, View view5, View view6, ScreenState screenState) {
        cancelLastAnimator();
        ViewVisibilityUtil.INSTANCE.getClass();
        view5.setVisibility(0);
        view.setVisibility(4);
        view6.setVisibility(0);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        Context context = this.context;
        ScreenState screenState2 = ScreenState.SCREEN_NORMAL;
        int dimenInt = ContextUtils.getDimenInt(screenState == screenState2 ? R.dimen.volume_media_icon_note_min_x : R.dimen.volume_sub_display_media_icon_note_min_x, context);
        if (VolumePanelValues.isRing(i)) {
            dimenInt = ContextUtils.getDimenInt(R.dimen.volume_sound_icon_spk_min_x, this.context);
        }
        if (screenState != screenState2 && BasicRune.VOLUME_SUB_DISPLAY_WATCHFACE_VOLUME_DIALOG) {
            dimenInt = ContextUtils.getDimenInt(R.dimen.volume_sub_large_display_media_icon_note_min_x, this.context);
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimenInt);
        ofFloat3.setDuration(200L);
        ofFloat3.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animatorSet);
        animatorSet2.playTogether(ofFloat3);
        animatorSet2.start();
        this.lastAnimtor = animatorSet2;
        startSplashAnimation(view6);
    }

    public final void startSoundVibrationAnimation(View view, View view2, View view3, View view4, View view5, View view6) {
        Animator animator = this.lastAnimtor;
        if (animator != null) {
            animator.cancel();
        }
        this.lastAnimtor = null;
        ViewVisibilityUtil.INSTANCE.getClass();
        view.setVisibility(0);
        view5.setVisibility(4);
        view2.setVisibility(4);
        view6.setVisibility(4);
        view3.setVisibility(4);
        view4.setVisibility(4);
        int dimenInt = ContextUtils.getDimenInt(R.dimen.volume_sound_icon_spk_min_x, this.context);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f));
        animatorSet.playTogether(ObjectAnimator.ofFloat(view4, "alpha", view4.getAlpha(), 0.0f));
        animatorSet.setDuration(50L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimenInt);
        ofFloat.setDuration(200L);
        ofFloat.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animatorSet);
        animatorSet2.playTogether(ofFloat);
        animatorSet2.start();
        this.lastAnimtor = animatorSet2;
        startVibrationAnimation(view);
    }

    public final void startVibrationAnimation(View view) {
        cancelLastAnimator();
        float dimenFloat = ContextUtils.getDimenFloat(R.dimen.volume_vibrate_init, this.context);
        float dimenFloat2 = ContextUtils.getDimenFloat(R.dimen.volume_vibrate_offset, this.context);
        float f = -dimenFloat;
        Animator vibrationAnimator = getVibrationAnimator(view, 0.0f, f, 60);
        float f2 = dimenFloat - dimenFloat2;
        Animator vibrationAnimator2 = getVibrationAnimator(view, f, f2, 80);
        float f3 = -(dimenFloat - (dimenFloat2 * 2));
        Animator vibrationAnimator3 = getVibrationAnimator(view, f2, f3, 100);
        Animator vibrationAnimator4 = getVibrationAnimator(view, f3, 0.0f, 120);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(CollectionsKt__CollectionsKt.listOf(vibrationAnimator, vibrationAnimator2, vibrationAnimator3, vibrationAnimator4));
        animatorSet.start();
        this.lastAnimtor = animatorSet;
    }
}
