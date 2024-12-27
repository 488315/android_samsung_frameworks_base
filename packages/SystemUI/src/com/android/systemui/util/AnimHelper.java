package com.android.systemui.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.util.ArrayList;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class AnimHelper {
    public static final int $stable = 0;
    public static final AnimHelper INSTANCE = new AnimHelper();

    public final class AnimPairSet {
        public static final int $stable = 8;
        private final AnimProperty animProperty;
        private final View view;

        public AnimPairSet(View view, AnimProperty animProperty) {
            this.view = view;
            this.animProperty = animProperty;
        }

        public static /* synthetic */ AnimPairSet copy$default(AnimPairSet animPairSet, View view, AnimProperty animProperty, int i, Object obj) {
            if ((i & 1) != 0) {
                view = animPairSet.view;
            }
            if ((i & 2) != 0) {
                animProperty = animPairSet.animProperty;
            }
            return animPairSet.copy(view, animProperty);
        }

        public final View component1() {
            return this.view;
        }

        public final AnimProperty component2() {
            return this.animProperty;
        }

        public final AnimPairSet copy(View view, AnimProperty animProperty) {
            return new AnimPairSet(view, animProperty);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AnimPairSet)) {
                return false;
            }
            AnimPairSet animPairSet = (AnimPairSet) obj;
            return Intrinsics.areEqual(this.view, animPairSet.view) && Intrinsics.areEqual(this.animProperty, animPairSet.animProperty);
        }

        public final AnimProperty getAnimProperty() {
            return this.animProperty;
        }

        public final View getView() {
            return this.view;
        }

        public int hashCode() {
            View view = this.view;
            return this.animProperty.hashCode() + ((view == null ? 0 : view.hashCode()) * 31);
        }

        public String toString() {
            return "AnimPairSet(view=" + this.view + ", animProperty=" + this.animProperty + ")";
        }
    }

    public final class AnimProperty {
        public static final int $stable = 8;
        private AnimationType animType;
        private final int duration;
        private float fromValue;
        private final Interpolator interpolator;
        private final int startDelay;
        private float toValue;

        public AnimProperty(AnimationType animationType, int i, int i2, float f, float f2, Interpolator interpolator) {
            this.animType = animationType;
            this.duration = i;
            this.startDelay = i2;
            this.fromValue = f;
            this.toValue = f2;
            this.interpolator = interpolator;
        }

        public static /* synthetic */ AnimProperty copy$default(AnimProperty animProperty, AnimationType animationType, int i, int i2, float f, float f2, Interpolator interpolator, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                animationType = animProperty.animType;
            }
            if ((i3 & 2) != 0) {
                i = animProperty.duration;
            }
            int i4 = i;
            if ((i3 & 4) != 0) {
                i2 = animProperty.startDelay;
            }
            int i5 = i2;
            if ((i3 & 8) != 0) {
                f = animProperty.fromValue;
            }
            float f3 = f;
            if ((i3 & 16) != 0) {
                f2 = animProperty.toValue;
            }
            float f4 = f2;
            if ((i3 & 32) != 0) {
                interpolator = animProperty.interpolator;
            }
            return animProperty.copy(animationType, i4, i5, f3, f4, interpolator);
        }

        public final AnimationType component1() {
            return this.animType;
        }

        public final int component2() {
            return this.duration;
        }

        public final int component3() {
            return this.startDelay;
        }

        public final float component4() {
            return this.fromValue;
        }

        public final float component5() {
            return this.toValue;
        }

        public final Interpolator component6() {
            return this.interpolator;
        }

        public final AnimProperty copy(AnimationType animationType, int i, int i2, float f, float f2, Interpolator interpolator) {
            return new AnimProperty(animationType, i, i2, f, f2, interpolator);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AnimProperty)) {
                return false;
            }
            AnimProperty animProperty = (AnimProperty) obj;
            return this.animType == animProperty.animType && this.duration == animProperty.duration && this.startDelay == animProperty.startDelay && Float.compare(this.fromValue, animProperty.fromValue) == 0 && Float.compare(this.toValue, animProperty.toValue) == 0 && Intrinsics.areEqual(this.interpolator, animProperty.interpolator);
        }

        public final AnimationType getAnimType() {
            return this.animType;
        }

        public final int getDuration() {
            return this.duration;
        }

        public final float getFromValue() {
            return this.fromValue;
        }

        public final Interpolator getInterpolator() {
            return this.interpolator;
        }

        public final int getStartDelay() {
            return this.startDelay;
        }

        public final float getToValue() {
            return this.toValue;
        }

        public int hashCode() {
            return this.interpolator.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.toValue, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.fromValue, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.startDelay, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.duration, this.animType.hashCode() * 31, 31), 31), 31), 31);
        }

        public final void setAnimType(AnimationType animationType) {
            this.animType = animationType;
        }

        public final void setFromValue(float f) {
            this.fromValue = f;
        }

        public final void setToValue(float f) {
            this.toValue = f;
        }

        public String toString() {
            return "AnimProperty(animType=" + this.animType + ", duration=" + this.duration + ", startDelay=" + this.startDelay + ", fromValue=" + this.fromValue + ", toValue=" + this.toValue + ", interpolator=" + this.interpolator + ")";
        }
    }

    public final class AnimationState {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ AnimationState[] $VALUES;
        public static final AnimationState NONE = new AnimationState(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0);
        public static final AnimationState SHOWING = new AnimationState("SHOWING", 1);
        public static final AnimationState HIDING = new AnimationState("HIDING", 2);

        private static final /* synthetic */ AnimationState[] $values() {
            return new AnimationState[]{NONE, SHOWING, HIDING};
        }

        static {
            AnimationState[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private AnimationState(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static AnimationState valueOf(String str) {
            return (AnimationState) Enum.valueOf(AnimationState.class, str);
        }

        public static AnimationState[] values() {
            return (AnimationState[]) $VALUES.clone();
        }
    }

    public final class AnimationType {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ AnimationType[] $VALUES;
        public static final AnimationType ALPHA = new AnimationType("ALPHA", 0);
        public static final AnimationType TRANSLATION_Y = new AnimationType("TRANSLATION_Y", 1);

        private static final /* synthetic */ AnimationType[] $values() {
            return new AnimationType[]{ALPHA, TRANSLATION_Y};
        }

        static {
            AnimationType[] $values = $values();
            $VALUES = $values;
            $ENTRIES = EnumEntriesKt.enumEntries($values);
        }

        private AnimationType(String str, int i) {
        }

        public static EnumEntries getEntries() {
            return $ENTRIES;
        }

        public static AnimationType valueOf(String str) {
            return (AnimationType) Enum.valueOf(AnimationType.class, str);
        }

        public static AnimationType[] values() {
            return (AnimationType[]) $VALUES.clone();
        }
    }

    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AnimationType.values().length];
            try {
                iArr[AnimationType.ALPHA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AnimationType.TRANSLATION_Y.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private AnimHelper() {
    }

    public static /* synthetic */ AnimatorSet makeAnimSet$default(AnimHelper animHelper, AnimPairSet[] animPairSetArr, ArrayList arrayList, BaseAnimatorListener baseAnimatorListener, int i, Object obj) {
        if ((i & 4) != 0) {
            baseAnimatorListener = null;
        }
        return animHelper.makeAnimSet(animPairSetArr, arrayList, baseAnimatorListener);
    }

    private final Animator makeAnimator(final View view, final AnimProperty animProperty) {
        if (animProperty == null) {
            return null;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(animProperty.getFromValue(), animProperty.getToValue());
        if (view == null) {
            return ofFloat;
        }
        ofFloat.setDuration(animProperty.getDuration());
        ofFloat.setStartDelay(animProperty.getStartDelay());
        ofFloat.setInterpolator(animProperty.getInterpolator());
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.util.AnimHelper$makeAnimator$1$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimHelper.INSTANCE.updateProperty(view, ((Float) valueAnimator.getAnimatedValue()).floatValue(), animProperty.getAnimType());
            }
        });
        return ofFloat;
    }

    public final void updateProperty(View view, float f, AnimationType animationType) {
        int i = WhenMappings.$EnumSwitchMapping$0[animationType.ordinal()];
        if (i == 1) {
            view.setAlpha(f);
        } else {
            if (i != 2) {
                return;
            }
            view.setTranslationY(f);
        }
    }

    public final void initProperty(AnimationType[] animationTypeArr, View... viewArr) {
        for (View view : ArraysKt___ArraysKt.filterNotNull(viewArr)) {
            for (AnimationType animationType : animationTypeArr) {
                INSTANCE.updateProperty(view, 0.0f, animationType);
            }
        }
    }

    public final AnimatorSet makeAnimSet(AnimPairSet[] animPairSetArr, ArrayList<Animator> arrayList, BaseAnimatorListener baseAnimatorListener) {
        for (AnimPairSet animPairSet : animPairSetArr) {
            Animator makeAnimator = INSTANCE.makeAnimator(animPairSet.getView(), animPairSet.getAnimProperty());
            if (makeAnimator != null) {
                arrayList.add(makeAnimator);
            }
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        if (baseAnimatorListener != null) {
            animatorSet.addListener(baseAnimatorListener);
        }
        return animatorSet;
    }

    public class BaseAnimatorListener implements Animator.AnimatorListener {
        public static final int $stable = 8;
        private final boolean debug;
        private boolean isCanceled;
        private final String logPrefix;
        private final String tag;

        public BaseAnimatorListener(String str, String str2, boolean z) {
            this.tag = str;
            this.logPrefix = str2;
            this.debug = z;
        }

        public final boolean getDebug() {
            return this.debug;
        }

        public final String getLogPrefix() {
            return this.logPrefix;
        }

        public final String getTag() {
            return this.tag;
        }

        public final boolean isCanceled() {
            return this.isCanceled;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            if (this.debug) {
                Log.d(this.tag, this.logPrefix + " onAnimationCancel isCanceled");
            }
            this.isCanceled = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (this.debug) {
                Log.d(this.tag, this.logPrefix + " onAnimationEnd isCanceled = " + this.isCanceled);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            if (this.debug) {
                Log.d(this.tag, this.logPrefix + " onAnimationStart");
            }
            this.isCanceled = false;
        }

        public final void setCanceled(boolean z) {
            this.isCanceled = z;
        }

        public /* synthetic */ BaseAnimatorListener(String str, String str2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, (i & 4) != 0 ? false : z);
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }
    }
}
