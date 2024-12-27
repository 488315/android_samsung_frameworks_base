package com.android.server.display.mode;

import java.util.HashSet;
import java.util.Objects;

public final class RequestedRefreshRateVote implements Vote {
    public final float mRefreshRate;

    public RequestedRefreshRateVote(float f) {
        this.mRefreshRate = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof RequestedRefreshRateVote)
                && Float.compare(this.mRefreshRate, ((RequestedRefreshRateVote) obj).mRefreshRate)
                        == 0;
    }

    public final int hashCode() {
        return Objects.hash(Float.valueOf(this.mRefreshRate));
    }

    public final String toString() {
        return "RequestedRefreshRateVote{ refreshRate=" + this.mRefreshRate + " }";
    }

    @Override // com.android.server.display.mode.Vote
    public final void updateSummary(VoteSummary voteSummary) {
        ((HashSet) voteSummary.requestedRefreshRates).add(Float.valueOf(this.mRefreshRate));
    }
}
