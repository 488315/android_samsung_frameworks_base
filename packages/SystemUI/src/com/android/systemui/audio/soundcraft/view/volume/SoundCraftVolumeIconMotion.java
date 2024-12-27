package com.android.systemui.audio.soundcraft.view.volume;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.keyguard.KeyguardSecPatternViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.volume.util.ContextUtils;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SoundCraftVolumeIconMotion {
    public static final PathInterpolator ALPHA_INTERPOLATOR;
    public final Context context;
    public Runnable iconRunnable;
    public Animator lastAnimator;
    public final Resources resources;
    public final Handler handler = new Handler(Looper.getMainLooper());
    public ValueAnimator shockValueAnimator = new ValueAnimator();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public SoundCraftVolumeIconMotion(Context context) {
        this.context = context;
        this.resources = context.getResources();
    }

    public final void startIconTintColor(final ArrayList arrayList, int i, int i2, boolean z) {
        if (this.shockValueAnimator.isRunning()) {
            this.shockValueAnimator.pause();
        }
        ValueAnimator duration = ValueAnimator.ofArgb(i, i2).setDuration(z ? 0L : 450L);
        this.shockValueAnimator = duration;
        duration.setInterpolator(ALPHA_INTERPOLATOR);
        this.shockValueAnimator.start();
        this.shockValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIconMotion$startIconTintColor$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((ImageView) it.next()).setImageTintList(ColorStateList.valueOf(((Integer) valueAnimator.getAnimatedValue()).intValue()));
                }
            }
        });
    }

    public final void startMaxAnimation(View view, View view2, View view3, View view4, View view5, View view6, boolean z) {
        Animator animator = this.lastAnimator;
        if (animator != null) {
            animator.cancel();
        }
        this.lastAnimator = null;
        ViewVisibilityUtil.INSTANCE.getClass();
        ViewVisibilityUtil.setGone(view5);
        view.setVisibility(0);
        ViewVisibilityUtil.setGone(view6);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        int dimenInt = ContextUtils.getDimenInt(R.dimen.sound_craft_volume_media_icon_note_mid_x, this.context);
        int dimenInt2 = ContextUtils.getDimenInt(R.dimen.sound_craft_volume_media_icon_wave_s, this.context);
        int dimenInt3 = ContextUtils.getDimenInt(R.dimen.sound_craft_volume_media_icon_wave_l, this.context);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.5f));
        animatorSet.playTogether(ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.5f));
        animatorSet.setDuration(z ? 0L : 150L);
        animatorSet.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ObjectAnimator.ofFloat(view, "x", view.getX(), dimenInt));
        animatorSet2.playTogether(ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimenInt2));
        animatorSet2.playTogether(ObjectAnimator.ofFloat(view3, "x", view3.getX(), dimenInt3));
        animatorSet2.setDuration(z ? 0L : 200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet);
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.start();
        this.lastAnimator = animatorSet3;
    }

    public final void startMidAnimation(final int i, final int i2, final View view, final View view2, final View view3, final View view4, final View view5, final View view6, boolean z) {
        Animator animator = this.lastAnimator;
        if (animator != null) {
            animator.cancel();
        }
        this.lastAnimator = null;
        ViewVisibilityUtil.INSTANCE.getClass();
        ViewVisibilityUtil.setGone(view5);
        view.setVisibility(0);
        ViewVisibilityUtil.setGone(view6);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        int dimenInt = ContextUtils.getDimenInt(R.dimen.sound_craft_volume_media_icon_note_mid_x, this.context);
        int dimenInt2 = ContextUtils.getDimenInt(R.dimen.sound_craft_volume_media_icon_wave_s, this.context);
        int dimenInt3 = ContextUtils.getDimenInt(R.dimen.sound_craft_volume_media_icon_wave_l, this.context);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.5f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(z ? 0L : 100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimenInt);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view2, "x", view2.getX(), dimenInt2);
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(view3, "x", view3.getX(), dimenInt3);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ofFloat3);
        animatorSet2.playTogether(ofFloat4);
        animatorSet2.playTogether(ofFloat5);
        animatorSet2.setDuration(z ? 0L : 200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.playTogether(animatorSet);
        Runnable runnable = this.iconRunnable;
        if (runnable != null) {
            this.handler.removeCallbacks(runnable);
        }
        this.iconRunnable = new Runnable() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIconMotion$startMidAnimation$2$2
            @Override // java.lang.Runnable
            public final void run() {
                int i3 = i2;
                if (i3 == 3) {
                    this.startMaxAnimation(view, view2, view3, view4, view5, view6, false);
                } else if (i3 == 1 || i3 == 0) {
                    this.startMinAnimation(i, i3, view, view2, view3, view4, view5, view6, false);
                }
            }
        };
        animatorSet3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIconMotion$startMidAnimation$2$3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
                SoundCraftVolumeIconMotion soundCraftVolumeIconMotion = SoundCraftVolumeIconMotion.this;
                Runnable runnable2 = soundCraftVolumeIconMotion.iconRunnable;
                if (runnable2 != null) {
                    soundCraftVolumeIconMotion.handler.postDelayed(runnable2, 200L);
                }
            }
        });
        animatorSet3.start();
        this.lastAnimator = animatorSet3;
    }

    public final void startMinAnimation(final int i, final int i2, final View view, final View view2, final View view3, final View view4, final View view5, final View view6, boolean z) {
        Animator animator = this.lastAnimator;
        if (animator != null) {
            animator.cancel();
        }
        this.lastAnimator = null;
        ViewVisibilityUtil.INSTANCE.getClass();
        ViewVisibilityUtil.setGone(view5);
        view.setVisibility(0);
        ViewVisibilityUtil.setGone(view6);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        int dimenInt = ContextUtils.getDimenInt(R.dimen.sound_craft_volume_media_icon_note_min_x, this.context);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(z ? 0L : 100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimenInt);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ofFloat3);
        animatorSet2.setDuration(z ? 0L : 200L);
        animatorSet2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
        Runnable runnable = this.iconRunnable;
        if (runnable != null) {
            this.handler.removeCallbacks(runnable);
        }
        this.iconRunnable = new Runnable() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIconMotion$startMinAnimation$3
            @Override // java.lang.Runnable
            public final void run() {
                int i3 = i2;
                if (i3 == 2 || i3 == 3) {
                    this.startMidAnimation(i, i3, view, view2, view3, view4, view5, view6, false);
                } else if (i3 == 0) {
                    this.startMuteAnimation(view, view2, view3, view4, view5, view6, false);
                }
            }
        };
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(animatorSet);
        animatorSet3.playTogether(animatorSet2);
        animatorSet3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIconMotion$startMinAnimation$4$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator2) {
                SoundCraftVolumeIconMotion soundCraftVolumeIconMotion = SoundCraftVolumeIconMotion.this;
                Runnable runnable2 = soundCraftVolumeIconMotion.iconRunnable;
                if (runnable2 != null) {
                    soundCraftVolumeIconMotion.handler.postDelayed(runnable2, 200L);
                }
            }
        });
        animatorSet3.start();
        this.lastAnimator = animatorSet3;
    }

    public final void startMuteAnimation(View view, View view2, View view3, View view4, View view5, final View view6, boolean z) {
        ViewVisibilityUtil.INSTANCE.getClass();
        view5.setVisibility(0);
        view.setVisibility(4);
        view6.setVisibility(0);
        if (view4 != null) {
            view4.setVisibility(8);
            view2.setVisibility(0);
            view3.setVisibility(0);
        }
        int dimensionPixelSize = this.resources.getDimensionPixelSize(R.dimen.volume_media_icon_note_min_x);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "alpha", view2.getAlpha(), 0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view3, "alpha", view3.getAlpha(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.setDuration(z ? 0L : 100L);
        animatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view, "x", view.getX(), dimensionPixelSize);
        ofFloat3.setDuration(z ? 0L : 200L);
        KeyguardSecPatternViewController$$ExternalSyntheticOutline0.m(0.22f, 0.25f, 0.0f, 1.0f, ofFloat3);
        Runnable runnable = this.iconRunnable;
        if (runnable != null) {
            this.handler.removeCallbacks(runnable);
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animatorSet);
        animatorSet2.playTogether(ofFloat3);
        animatorSet2.start();
        this.lastAnimator = animatorSet2;
        if (z) {
            return;
        }
        view6.setScaleX(0.0f);
        SpringAnimation springAnimation = new SpringAnimation(view6, DynamicAnimation.SCALE_X);
        springAnimation.cancel();
        springAnimation.mVelocity = 0.0f;
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.audio.soundcraft.view.volume.SoundCraftVolumeIconMotion$startSplashAnimation$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
                if (f2 == 0.0f) {
                    view6.setPivotX(0.0f);
                    view6.setPivotY(0.0f);
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
}
