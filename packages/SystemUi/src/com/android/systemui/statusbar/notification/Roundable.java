package com.android.systemui.statusbar.notification;

import java.util.LinkedHashMap;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface Roundable {
    default void applyRoundnessAndInvalidate() {
        getRoundableState().targetView.invalidate();
    }

    default float getBottomCornerRadius() {
        return getMaxRadius() * getBottomRoundness();
    }

    default float getBottomRoundness() {
        return getRoundableState().bottomRoundness;
    }

    default float getMaxRadius() {
        return getRoundableState().maxRadius;
    }

    RoundableState getRoundableState();

    default float getTopCornerRadius() {
        return getMaxRadius() * getTopRoundness();
    }

    default float getTopRoundness() {
        return getRoundableState().topRoundness;
    }

    default boolean hasRoundedCorner() {
        if (getTopRoundness() == 0.0f) {
            return !((getBottomRoundness() > 0.0f ? 1 : (getBottomRoundness() == 0.0f ? 0 : -1)) == 0);
        }
        return true;
    }

    default boolean requestBottomRoundness(float f, SourceType sourceType, boolean z) {
        LinkedHashMap linkedHashMap = (LinkedHashMap) getRoundableState().bottomRoundnessMap;
        Float maxOrNull = CollectionsKt___CollectionsKt.maxOrNull(linkedHashMap.values());
        float floatValue = maxOrNull != null ? maxOrNull.floatValue() : 0.0f;
        if (f == 0.0f) {
            linkedHashMap.remove(sourceType);
        } else {
            linkedHashMap.put(sourceType, Float.valueOf(f));
        }
        Float maxOrNull2 = CollectionsKt___CollectionsKt.maxOrNull(linkedHashMap.values());
        float floatValue2 = maxOrNull2 != null ? maxOrNull2.floatValue() : 0.0f;
        if (floatValue == floatValue2) {
            return false;
        }
        RoundableState roundableState = getRoundableState();
        boolean z2 = (roundableState.targetView.getTag(roundableState.bottomAnimatable.val$animatorTag) != null) && Math.abs(floatValue2 - floatValue) > 0.5f;
        RoundableState roundableState2 = getRoundableState();
        PropertyAnimator.setProperty(roundableState2.targetView, roundableState2.bottomAnimatable, floatValue2, RoundableState.DURATION, z2 || z);
        return true;
    }

    default boolean requestRoundness(float f, float f2, SourceType sourceType, boolean z) {
        return requestTopRoundness(f, sourceType, z) || requestBottomRoundness(f2, sourceType, z);
    }

    default void requestRoundnessReset(SourceType sourceType) {
        requestRoundness(0.0f, 0.0f, sourceType, getRoundableState().targetView.isShown());
    }

    default boolean requestTopRoundness(float f, SourceType sourceType, boolean z) {
        LinkedHashMap linkedHashMap = (LinkedHashMap) getRoundableState().topRoundnessMap;
        Float maxOrNull = CollectionsKt___CollectionsKt.maxOrNull(linkedHashMap.values());
        float floatValue = maxOrNull != null ? maxOrNull.floatValue() : 0.0f;
        if (f == 0.0f) {
            linkedHashMap.remove(sourceType);
        } else {
            linkedHashMap.put(sourceType, Float.valueOf(f));
        }
        Float maxOrNull2 = CollectionsKt___CollectionsKt.maxOrNull(linkedHashMap.values());
        float floatValue2 = maxOrNull2 != null ? maxOrNull2.floatValue() : 0.0f;
        if (floatValue == floatValue2) {
            return false;
        }
        RoundableState roundableState = getRoundableState();
        boolean z2 = (roundableState.targetView.getTag(roundableState.topAnimatable.val$animatorTag) != null) && Math.abs(floatValue2 - floatValue) > 0.5f;
        RoundableState roundableState2 = getRoundableState();
        PropertyAnimator.setProperty(roundableState2.targetView, roundableState2.topAnimatable, floatValue2, RoundableState.DURATION, z2 || z);
        return true;
    }

    default void requestRoundness(float f, float f2, SourceType$Companion$from$1 sourceType$Companion$from$1) {
        requestRoundness(f, f2, sourceType$Companion$from$1, getRoundableState().targetView.isShown());
    }
}
