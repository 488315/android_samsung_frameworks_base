package com.android.systemui.statusbar.notification;

import java.util.LinkedHashMap;
import kotlin.collections.CollectionsKt___CollectionsKt;

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
        Float fM3449maxOrNull = CollectionsKt___CollectionsKt.m3449maxOrNull((Iterable) linkedHashMap.values());
        float fFloatValue = fM3449maxOrNull != null ? fM3449maxOrNull.floatValue() : 0.0f;
        if (f == 0.0f) {
            linkedHashMap.remove(sourceType);
        } else {
            linkedHashMap.put(sourceType, Float.valueOf(f));
        }
        Float fM3449maxOrNull2 = CollectionsKt___CollectionsKt.m3449maxOrNull((Iterable) linkedHashMap.values());
        float fFloatValue2 = fM3449maxOrNull2 != null ? fM3449maxOrNull2.floatValue() : 0.0f;
        if (fFloatValue == fFloatValue2) {
            return false;
        }
        RoundableState roundableState = getRoundableState();
        boolean z2 = (roundableState.targetView.getTag(roundableState.bottomAnimatable.val$animatorTag) != null) && Math.abs(fFloatValue2 - fFloatValue) > 0.5f;
        RoundableState roundableState2 = getRoundableState();
        PropertyAnimator.setProperty(roundableState2.targetView, roundableState2.bottomAnimatable, fFloatValue2, RoundableState.DURATION, z2 || z);
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
        Float fM3449maxOrNull = CollectionsKt___CollectionsKt.m3449maxOrNull((Iterable) linkedHashMap.values());
        float fFloatValue = fM3449maxOrNull != null ? fM3449maxOrNull.floatValue() : 0.0f;
        if (f == 0.0f) {
            linkedHashMap.remove(sourceType);
        } else {
            linkedHashMap.put(sourceType, Float.valueOf(f));
        }
        Float fM3449maxOrNull2 = CollectionsKt___CollectionsKt.m3449maxOrNull((Iterable) linkedHashMap.values());
        float fFloatValue2 = fM3449maxOrNull2 != null ? fM3449maxOrNull2.floatValue() : 0.0f;
        if (fFloatValue == fFloatValue2) {
            return false;
        }
        RoundableState roundableState = getRoundableState();
        boolean z2 = (roundableState.targetView.getTag(roundableState.topAnimatable.val$animatorTag) != null) && Math.abs(fFloatValue2 - fFloatValue) > 0.5f;
        RoundableState roundableState2 = getRoundableState();
        PropertyAnimator.setProperty(roundableState2.targetView, roundableState2.topAnimatable, fFloatValue2, RoundableState.DURATION, z2 || z);
        return true;
    }
}
