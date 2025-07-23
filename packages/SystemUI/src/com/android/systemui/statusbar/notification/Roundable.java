package com.android.systemui.statusbar.notification;

import java.util.LinkedHashMap;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface Roundable {
    default void applyRoundnessAndInvalidate() {
        getRoundableState().targetView.invalidate();
    }

    RoundableState getRoundableState();

    default boolean hasRoundedCorner() {
        return (getRoundableState().topRoundness == 0.0f && getRoundableState().bottomRoundness == 0.0f) ? false : true;
    }

    default boolean requestBottomRoundness(float f, SourceType sourceType, boolean z) {
        LinkedHashMap linkedHashMap = (LinkedHashMap) getRoundableState().bottomRoundnessMap;
        Float m3430maxOrNull = CollectionsKt___CollectionsKt.m3430maxOrNull((Iterable) linkedHashMap.values());
        float floatValue = m3430maxOrNull != null ? m3430maxOrNull.floatValue() : 0.0f;
        if (f == 0.0f) {
            linkedHashMap.remove(sourceType);
        } else {
            linkedHashMap.put(sourceType, Float.valueOf(f));
        }
        Float m3430maxOrNull2 = CollectionsKt___CollectionsKt.m3430maxOrNull((Iterable) linkedHashMap.values());
        float floatValue2 = m3430maxOrNull2 != null ? m3430maxOrNull2.floatValue() : 0.0f;
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
        Float m3430maxOrNull = CollectionsKt___CollectionsKt.m3430maxOrNull((Iterable) linkedHashMap.values());
        float floatValue = m3430maxOrNull != null ? m3430maxOrNull.floatValue() : 0.0f;
        if (f == 0.0f) {
            linkedHashMap.remove(sourceType);
        } else {
            linkedHashMap.put(sourceType, Float.valueOf(f));
        }
        Float m3430maxOrNull2 = CollectionsKt___CollectionsKt.m3430maxOrNull((Iterable) linkedHashMap.values());
        float floatValue2 = m3430maxOrNull2 != null ? m3430maxOrNull2.floatValue() : 0.0f;
        if (floatValue == floatValue2) {
            return false;
        }
        RoundableState roundableState = getRoundableState();
        boolean z2 = (roundableState.targetView.getTag(roundableState.topAnimatable.val$animatorTag) != null) && Math.abs(floatValue2 - floatValue) > 0.5f;
        RoundableState roundableState2 = getRoundableState();
        PropertyAnimator.setProperty(roundableState2.targetView, roundableState2.topAnimatable, floatValue2, RoundableState.DURATION, z2 || z);
        return true;
    }
}
