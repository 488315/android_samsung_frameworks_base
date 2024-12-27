package com.android.server.wm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface ExtraDisplayPolicy {
    default int getOtherDisplayId(int i) {
        return -1;
    }

    default boolean hasCoverHome(int i) {
        return false;
    }

    default boolean isDisplayControlledByPolicy(int i) {
        return false;
    }

    default boolean shouldChooseDefaultTaskDisplayArea(int i) {
        return false;
    }

    default boolean shouldNotHandleForcedResizableTaskIfNeeded(int i, int i2) {
        return false;
    }

    default boolean shouldNotTopDisplay(int i) {
        return false;
    }

    default boolean shouldSkipAppTransition(int i) {
        return false;
    }
}
