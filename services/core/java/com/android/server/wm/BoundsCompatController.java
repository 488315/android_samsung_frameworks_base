package com.android.server.wm;

import android.content.res.Configuration;

/* loaded from: classes3.dex */
public interface BoundsCompatController {
    void adjustBounds(ActivityRecord activityRecord, Configuration configuration);

    default boolean canHaveSizeCompatBounds(ActivityRecord activityRecord) {
        return false;
    }

    default boolean shouldUpdatePosition() {
        return true;
    }

    default boolean shouldUseSandboxDisplay(ActivityRecord activityRecord) {
        return false;
    }

    default boolean shouldUseSandboxViewBoundsAndMotionEvent(ActivityRecord activityRecord) {
        return false;
    }

    default boolean shouldUseSizeCompatMode(ActivityRecord activityRecord) {
        return false;
    }

    default boolean supportsCustomLetterbox() {
        return true;
    }

    default BoundsCompatUtils getBoundsCompatUtils() {
        return BoundsCompatUtils.get();
    }
}
