package com.android.systemui.searcle;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.LinearLayout;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.systemui.searcle.SearcleTipAnimHelper;
import java.util.ArrayList;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SearcleTipAnimHelper {
    public static final AnimProperty HIDE_ANIM_BUBBLE_ALPHA_PROPERTY;
    public static final AnimProperty HIDE_ANIM_BUBBLE_SCALE_PROPERTY;
    public static final AnimProperty HIDE_ANIM_CONTENT_ALPHA_PROPERTY;
    public static final AnimationType[] INIT_BUBBLE_PROPERTY_FIELDS;
    public static final AnimationType[] INIT_CONTENT_PROPERTY_FIELDS;
    public static final AnimProperty SHOW_ANIM_BUBBLE_ALPHA_PROPERTY;
    public static final AnimProperty SHOW_ANIM_BUBBLE_SCALE_PROPERTY;
    public static final AnimProperty SHOW_ANIM_BUBBLE_TRANS_PROPERTY;
    public static final AnimProperty SHOW_ANIM_CONTENT_ALPHA_PROPERTY;
    public final Runnable addViewRunnable;
    public final LinearLayout bubbleLayout;
    public final LinearLayout contentLayout;
    public final Context context;
    public AnimatorSet hideAnimSet;
    public final Runnable hideImmediateRunnable;
    public AnimatorSet showAnimSet;
    public final ArrayList showAnimList = new ArrayList();
    public final ArrayList hideAnimList = new ArrayList();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AnimPairSet {
        public final AnimProperty property;
        public final View view;

        public AnimPairSet(View view, AnimProperty animProperty) {
            this.view = view;
            this.property = animProperty;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AnimPairSet)) {
                return false;
            }
            AnimPairSet animPairSet = (AnimPairSet) obj;
            return Intrinsics.areEqual(this.view, animPairSet.view) && Intrinsics.areEqual(this.property, animPairSet.property);
        }

        public final int hashCode() {
            View view = this.view;
            return this.property.hashCode() + ((view == null ? 0 : view.hashCode()) * 31);
        }

        public final String toString() {
            return "AnimPairSet(view=" + this.view + ", property=" + this.property + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AnimProperty {
        public AnimationType animType;
        public final int duration;
        public final float fromValue;
        public final Interpolator interpolator;
        public final int startDelay;
        public float toValue;

        public AnimProperty(AnimationType animationType, int i, int i2, float f, float f2, Interpolator interpolator) {
            this.animType = animationType;
            this.duration = i;
            this.startDelay = i2;
            this.fromValue = f;
            this.toValue = f2;
            this.interpolator = interpolator;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AnimProperty)) {
                return false;
            }
            AnimProperty animProperty = (AnimProperty) obj;
            return this.animType == animProperty.animType && this.duration == animProperty.duration && this.startDelay == animProperty.startDelay && Float.compare(this.fromValue, animProperty.fromValue) == 0 && Float.compare(this.toValue, animProperty.toValue) == 0 && Intrinsics.areEqual(this.interpolator, animProperty.interpolator);
        }

        public final int hashCode() {
            return this.interpolator.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.toValue, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.fromValue, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.startDelay, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.duration, this.animType.hashCode() * 31, 31), 31), 31), 31);
        }

        public final String toString() {
            return "AnimProperty(animType=" + this.animType + ", duration=" + this.duration + ", startDelay=" + this.startDelay + ", fromValue=" + this.fromValue + ", toValue=" + this.toValue + ", interpolator=" + this.interpolator + ")";
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AnimationType {
        public static final /* synthetic */ AnimationType[] $VALUES;
        public static final AnimationType Alpha;
        public static final AnimationType Scale;
        public static final AnimationType TransXOnLTR;
        public static final AnimationType TransXOnRTL;
        public static final AnimationType TransXYOnLTR;
        public static final AnimationType TransXYOnRTL;
        public static final AnimationType TransY;

        static {
            AnimationType animationType = new AnimationType("Alpha", 0);
            Alpha = animationType;
            AnimationType animationType2 = new AnimationType("Scale", 1);
            Scale = animationType2;
            AnimationType animationType3 = new AnimationType("TransY", 2);
            TransY = animationType3;
            AnimationType animationType4 = new AnimationType("TransXYOnLTR", 3);
            TransXYOnLTR = animationType4;
            AnimationType animationType5 = new AnimationType("TransXYOnRTL", 4);
            TransXYOnRTL = animationType5;
            AnimationType animationType6 = new AnimationType("TransXOnLTR", 5);
            TransXOnLTR = animationType6;
            AnimationType animationType7 = new AnimationType("TransXOnRTL", 6);
            TransXOnRTL = animationType7;
            AnimationType[] animationTypeArr = {animationType, animationType2, animationType3, animationType4, animationType5, animationType6, animationType7};
            $VALUES = animationTypeArr;
            EnumEntriesKt.enumEntries(animationTypeArr);
        }

        private AnimationType(String str, int i) {
        }

        public static AnimationType valueOf(String str) {
            return (AnimationType) Enum.valueOf(AnimationType.class, str);
        }

        public static AnimationType[] values() {
            return (AnimationType[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class HideAnimatorListener extends BaseAnimatorListener {
        public HideAnimatorListener(String str) {
            super(SearcleTipAnimHelper.this, str);
        }

        @Override // com.android.systemui.searcle.SearcleTipAnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            SearcleTipAnimHelper.this.hideImmediateRunnable.run();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ShowAnimatorListener extends BaseAnimatorListener {
        public ShowAnimatorListener(String str) {
            super(SearcleTipAnimHelper.this, str);
        }

        @Override // com.android.systemui.searcle.SearcleTipAnimHelper.BaseAnimatorListener, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            SearcleTipAnimHelper.this.addViewRunnable.run();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AnimationType.values().length];
            try {
                iArr[AnimationType.Alpha.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AnimationType.TransY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[AnimationType.TransXOnLTR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[AnimationType.TransXOnRTL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[AnimationType.TransXYOnLTR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[AnimationType.TransXYOnRTL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[AnimationType.Scale.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        PathInterpolator pathInterpolator = new PathInterpolator(1.0f, 1.3f);
        PathInterpolator pathInterpolator2 = new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f);
        PathInterpolator pathInterpolator3 = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        AnimationType animationType = AnimationType.Alpha;
        AnimationType animationType2 = AnimationType.Scale;
        AnimationType animationType3 = AnimationType.TransXOnRTL;
        AnimationType animationType4 = AnimationType.TransY;
        INIT_BUBBLE_PROPERTY_FIELDS = new AnimationType[]{animationType, animationType2, animationType3, animationType4};
        INIT_CONTENT_PROPERTY_FIELDS = new AnimationType[]{animationType};
        SHOW_ANIM_BUBBLE_ALPHA_PROPERTY = new AnimProperty(animationType, 83, 0, 0.0f, 1.0f, pathInterpolator2);
        SHOW_ANIM_BUBBLE_SCALE_PROPERTY = new AnimProperty(animationType2, 167, 0, 0.32f, 1.0f, pathInterpolator);
        SHOW_ANIM_CONTENT_ALPHA_PROPERTY = new AnimProperty(animationType, 167, 0, 0.0f, 1.0f, pathInterpolator3);
        SHOW_ANIM_BUBBLE_TRANS_PROPERTY = new AnimProperty(animationType4, 167, 0, 0.0f, 0.0f, pathInterpolator);
        HIDE_ANIM_BUBBLE_ALPHA_PROPERTY = new AnimProperty(animationType, 167, 0, 1.0f, 0.0f, pathInterpolator3);
        HIDE_ANIM_BUBBLE_SCALE_PROPERTY = new AnimProperty(animationType2, 167, 0, 1.0f, 0.32f, pathInterpolator);
        HIDE_ANIM_CONTENT_ALPHA_PROPERTY = new AnimProperty(animationType, 0, 0, 1.0f, 0.32f, pathInterpolator3);
    }

    public SearcleTipAnimHelper(Context context, Runnable runnable, Runnable runnable2, LinearLayout linearLayout, LinearLayout linearLayout2) {
        this.context = context;
        this.addViewRunnable = runnable;
        this.hideImmediateRunnable = runnable2;
        this.bubbleLayout = linearLayout;
        this.contentLayout = linearLayout2;
    }

    public static void initProperty(AnimationType[] animationTypeArr, View... viewArr) {
        for (View view : viewArr) {
            if (view != null) {
                for (AnimationType animationType : animationTypeArr) {
                    updateProperty(view, 0.0f, animationType);
                }
            }
        }
    }

    public static void updateProperty(View view, float f, AnimationType animationType) {
        switch (WhenMappings.$EnumSwitchMapping$0[animationType.ordinal()]) {
            case 1:
                view.setAlpha(f);
                break;
            case 2:
                view.setTranslationY(f);
                break;
            case 3:
                view.setTranslationX(-f);
                break;
            case 4:
                view.setTranslationX(f);
                break;
            case 5:
                view.setTranslationY(f);
                break;
            case 6:
                view.setTranslationY(f);
                break;
            case 7:
                view.setScaleX(f);
                view.setScaleY(f);
                break;
        }
    }

    public final AnimatorSet makeAnimSet(AnimPairSet[] animPairSetArr, ArrayList arrayList, BaseAnimatorListener baseAnimatorListener) {
        ValueAnimator valueAnimator;
        for (AnimPairSet animPairSet : animPairSetArr) {
            final View view = animPairSet.view;
            final AnimProperty animProperty = animPairSet.property;
            if (animProperty != null) {
                valueAnimator = ValueAnimator.ofFloat(animProperty.fromValue, animProperty.toValue);
                if (view != null) {
                    valueAnimator.setDuration(animProperty.duration);
                    valueAnimator.setStartDelay(animProperty.startDelay);
                    valueAnimator.setInterpolator(animProperty.interpolator);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.searcle.SearcleTipAnimHelper$makeAnimator$1$1$1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            SearcleTipAnimHelper searcleTipAnimHelper = SearcleTipAnimHelper.this;
                            View view2 = view;
                            float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                            SearcleTipAnimHelper.AnimationType animationType = animProperty.animType;
                            SearcleTipAnimHelper.AnimationType[] animationTypeArr = SearcleTipAnimHelper.INIT_BUBBLE_PROPERTY_FIELDS;
                            searcleTipAnimHelper.getClass();
                            SearcleTipAnimHelper.updateProperty(view2, floatValue, animationType);
                        }
                    });
                }
            } else {
                valueAnimator = null;
            }
            if (valueAnimator != null) {
                arrayList.add(valueAnimator);
            }
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        animatorSet.addListener(baseAnimatorListener);
        return animatorSet;
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class BaseAnimatorListener implements Animator.AnimatorListener {
        public BaseAnimatorListener(SearcleTipAnimHelper searcleTipAnimHelper, String str) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }
    }
}
