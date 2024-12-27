package com.android.systemui.statusbar.notification.stack;

import android.util.Property;
import androidx.collection.ArraySet;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class AnimationFilter {
    public boolean animateAlpha;
    public boolean animateHeight;
    public boolean animateHideSensitive;
    public boolean animateTopInset;
    public boolean animateX;
    public boolean animateY;
    public boolean animateZ;
    public long customDelay;
    public boolean hasDelays;
    public boolean hasGoToFullShadeEvent;
    public final ArraySet mAnimatedProperties = new ArraySet();

    public final void combineFilter(AnimationFilter animationFilter) {
        this.animateAlpha |= animationFilter.animateAlpha;
        this.animateX |= animationFilter.animateX;
        this.animateY |= animationFilter.animateY;
        this.animateZ |= animationFilter.animateZ;
        this.animateHeight |= animationFilter.animateHeight;
        this.animateTopInset |= animationFilter.animateTopInset;
        this.animateHideSensitive |= animationFilter.animateHideSensitive;
        this.hasDelays |= animationFilter.hasDelays;
        this.mAnimatedProperties.addAll(animationFilter.mAnimatedProperties);
    }

    public final void reset() {
        this.animateAlpha = false;
        this.animateX = false;
        this.animateY = false;
        this.animateZ = false;
        this.animateHeight = false;
        this.animateTopInset = false;
        this.animateHideSensitive = false;
        this.hasDelays = false;
        this.hasGoToFullShadeEvent = false;
        this.customDelay = -1L;
        this.mAnimatedProperties.clear();
    }

    public boolean shouldAnimateProperty(Property property) {
        return this.mAnimatedProperties.contains(property);
    }
}
