package com.android.server.display.mode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SupportedRefreshRatesVote implements Vote {
    public final List mRefreshRates;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RefreshRates {
        public final float mPeakRefreshRate;
        public final float mVsyncRate;

        public RefreshRates(float f, float f2) {
            this.mPeakRefreshRate = f;
            this.mVsyncRate = f2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RefreshRates)) {
                return false;
            }
            RefreshRates refreshRates = (RefreshRates) obj;
            return Float.compare(refreshRates.mPeakRefreshRate, this.mPeakRefreshRate) == 0 && Float.compare(refreshRates.mVsyncRate, this.mVsyncRate) == 0;
        }

        public final int hashCode() {
            return Objects.hash(Float.valueOf(this.mPeakRefreshRate), Float.valueOf(this.mVsyncRate));
        }

        public final String toString() {
            return "RefreshRates{ mPeakRefreshRate=" + this.mPeakRefreshRate + ", mVsyncRate=" + this.mVsyncRate + " }";
        }
    }

    public SupportedRefreshRatesVote(List list) {
        this.mRefreshRates = Collections.unmodifiableList(list);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SupportedRefreshRatesVote) {
            return this.mRefreshRates.equals(((SupportedRefreshRatesVote) obj).mRefreshRates);
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(this.mRefreshRates);
    }

    public final String toString() {
        return "SupportedRefreshRatesVote{ mSupportedModes=" + this.mRefreshRates + " }";
    }

    @Override // com.android.server.display.mode.Vote
    public final void updateSummary(VoteSummary voteSummary) {
        List list = voteSummary.supportedRefreshRates;
        if (list == null) {
            voteSummary.supportedRefreshRates = new ArrayList(this.mRefreshRates);
        } else {
            list.retainAll(this.mRefreshRates);
        }
    }
}
