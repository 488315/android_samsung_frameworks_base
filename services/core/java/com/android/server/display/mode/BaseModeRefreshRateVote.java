package com.android.server.display.mode;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BaseModeRefreshRateVote implements Vote {
    public final float mAppRequestBaseModeRefreshRate;

    public BaseModeRefreshRateVote(float f) {
        this.mAppRequestBaseModeRefreshRate = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof BaseModeRefreshRateVote) && Float.compare(((BaseModeRefreshRateVote) obj).mAppRequestBaseModeRefreshRate, this.mAppRequestBaseModeRefreshRate) == 0;
    }

    public final int hashCode() {
        return Objects.hash(Float.valueOf(this.mAppRequestBaseModeRefreshRate));
    }

    public final String toString() {
        return "BaseModeRefreshRateVote{ mAppRequestBaseModeRefreshRate=" + this.mAppRequestBaseModeRefreshRate + " }";
    }

    @Override // com.android.server.display.mode.Vote
    public final void updateSummary(VoteSummary voteSummary) {
        if (voteSummary.appRequestBaseModeRefreshRate == FullScreenMagnificationGestureHandler.MAX_SCALE) {
            float f = this.mAppRequestBaseModeRefreshRate;
            if (f > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                voteSummary.appRequestBaseModeRefreshRate = f;
            }
        }
    }
}
