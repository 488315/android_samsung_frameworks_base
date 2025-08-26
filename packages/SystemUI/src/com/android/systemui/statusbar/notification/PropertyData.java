package com.android.systemui.statusbar.notification;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class PropertyData {
    public SpringAnimation animator;
    public Runnable delayRunnable;
    public DynamicAnimation.OnAnimationUpdateListener doubleOvershootAvoidingListener;
    public float finalValue;
    public float offset;
    public float startOffset;

    public PropertyData() {
        this(0.0f, 0.0f, null, null, 0.0f, null, 63, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PropertyData)) {
            return false;
        }
        PropertyData propertyData = (PropertyData) obj;
        return Float.compare(this.finalValue, propertyData.finalValue) == 0 && Float.compare(this.offset, propertyData.offset) == 0 && Intrinsics.areEqual(this.animator, propertyData.animator) && Intrinsics.areEqual(this.delayRunnable, propertyData.delayRunnable) && Float.compare(this.startOffset, propertyData.startOffset) == 0 && Intrinsics.areEqual(this.doubleOvershootAvoidingListener, propertyData.doubleOvershootAvoidingListener);
    }

    public final int hashCode() {
        int iM = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.offset, Float.hashCode(this.finalValue) * 31, 31);
        SpringAnimation springAnimation = this.animator;
        int iHashCode = (iM + (springAnimation == null ? 0 : springAnimation.hashCode())) * 31;
        Runnable runnable = this.delayRunnable;
        int iM2 = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.startOffset, (iHashCode + (runnable == null ? 0 : runnable.hashCode())) * 31, 31);
        DynamicAnimation.OnAnimationUpdateListener onAnimationUpdateListener = this.doubleOvershootAvoidingListener;
        return iM2 + (onAnimationUpdateListener != null ? onAnimationUpdateListener.hashCode() : 0);
    }

    public final String toString() {
        float f = this.finalValue;
        float f2 = this.offset;
        SpringAnimation springAnimation = this.animator;
        Runnable runnable = this.delayRunnable;
        float f3 = this.startOffset;
        DynamicAnimation.OnAnimationUpdateListener onAnimationUpdateListener = this.doubleOvershootAvoidingListener;
        StringBuilder sbM = CubicBezierEasing$$ExternalSyntheticOutline0.m("PropertyData(finalValue=", f, ", offset=", f2, ", animator=");
        sbM.append(springAnimation);
        sbM.append(", delayRunnable=");
        sbM.append(runnable);
        sbM.append(", startOffset=");
        sbM.append(f3);
        sbM.append(", doubleOvershootAvoidingListener=");
        sbM.append(onAnimationUpdateListener);
        sbM.append(")");
        return sbM.toString();
    }

    public PropertyData(float f, float f2, SpringAnimation springAnimation, Runnable runnable, float f3, DynamicAnimation.OnAnimationUpdateListener onAnimationUpdateListener) {
        this.finalValue = f;
        this.offset = f2;
        this.animator = springAnimation;
        this.delayRunnable = runnable;
        this.startOffset = f3;
        this.doubleOvershootAvoidingListener = onAnimationUpdateListener;
    }

    public /* synthetic */ PropertyData(float f, float f2, SpringAnimation springAnimation, Runnable runnable, float f3, DynamicAnimation.OnAnimationUpdateListener onAnimationUpdateListener, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? 0.0f : f2, (i & 4) != 0 ? null : springAnimation, (i & 8) != 0 ? null : runnable, (i & 16) != 0 ? 0.0f : f3, (i & 32) != 0 ? null : onAnimationUpdateListener);
    }
}
