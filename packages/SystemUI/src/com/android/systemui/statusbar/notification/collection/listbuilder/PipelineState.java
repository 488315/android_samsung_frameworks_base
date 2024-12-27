package com.android.systemui.statusbar.notification.collection.listbuilder;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

public final class PipelineState {
    public int mState = 0;

    public static String getStateName(int i) {
        switch (i) {
            case 0:
                return "STATE_IDLE";
            case 1:
                return "STATE_BUILD_STARTED";
            case 2:
                return "STATE_RESETTING";
            case 3:
                return "STATE_PRE_GROUP_FILTERING";
            case 4:
                return "STATE_GROUPING";
            case 5:
                return "STATE_TRANSFORMING";
            case 6:
                return "STATE_GROUP_STABILIZING";
            case 7:
                return "STATE_SORTING";
            case 8:
                return "STATE_FINALIZE_FILTERING";
            case 9:
                return "STATE_FINALIZING";
            default:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "STATE:");
        }
    }

    public final void incrementTo(int i) {
        if (this.mState == i - 1) {
            this.mState = i;
            return;
        }
        throw new IllegalStateException("Cannot increment from state " + this.mState + " to state " + i);
    }

    public final void requireState() {
        if (this.mState == 0) {
            return;
        }
        throw new IllegalStateException("Required state is <0 but actual state is " + this.mState);
    }
}
