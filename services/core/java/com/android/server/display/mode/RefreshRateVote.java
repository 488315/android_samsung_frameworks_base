package com.android.server.display.mode;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class RefreshRateVote implements Vote {
    public final float mMaxRefreshRate;
    public final float mMinRefreshRate;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PhysicalVote extends RefreshRateVote {
        @Override // com.android.server.display.mode.RefreshRateVote
        public final boolean equals(Object obj) {
            if (obj instanceof PhysicalVote) {
                return super.equals(obj);
            }
            return false;
        }

        @Override // com.android.server.display.mode.RefreshRateVote
        public final String toString() {
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                    new StringBuilder("PhysicalVote{ "), super.toString(), " }");
        }

        @Override // com.android.server.display.mode.Vote
        public final void updateSummary(VoteSummary voteSummary) {
            voteSummary.minPhysicalRefreshRate =
                    Math.max(voteSummary.minPhysicalRefreshRate, this.mMinRefreshRate);
            float f = voteSummary.maxPhysicalRefreshRate;
            float f2 = this.mMaxRefreshRate;
            voteSummary.maxPhysicalRefreshRate = Math.min(f, f2);
            voteSummary.maxRenderFrameRate = Math.min(voteSummary.maxRenderFrameRate, f2);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RenderVote extends RefreshRateVote {
        @Override // com.android.server.display.mode.RefreshRateVote
        public final boolean equals(Object obj) {
            if (obj instanceof RenderVote) {
                return super.equals(obj);
            }
            return false;
        }

        @Override // com.android.server.display.mode.RefreshRateVote
        public final String toString() {
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                    new StringBuilder("RenderVote{ "), super.toString(), " }");
        }

        @Override // com.android.server.display.mode.Vote
        public final void updateSummary(VoteSummary voteSummary) {
            float f = voteSummary.minRenderFrameRate;
            float f2 = this.mMinRefreshRate;
            voteSummary.minRenderFrameRate = Math.max(f, f2);
            voteSummary.maxRenderFrameRate =
                    Math.min(voteSummary.maxRenderFrameRate, this.mMaxRefreshRate);
            voteSummary.minPhysicalRefreshRate = Math.max(voteSummary.minPhysicalRefreshRate, f2);
        }
    }

    public RefreshRateVote(float f, float f2) {
        this.mMinRefreshRate = f;
        this.mMaxRefreshRate = f2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RefreshRateVote)) {
            return false;
        }
        RefreshRateVote refreshRateVote = (RefreshRateVote) obj;
        return Float.compare(refreshRateVote.mMinRefreshRate, this.mMinRefreshRate) == 0
                && Float.compare(refreshRateVote.mMaxRefreshRate, this.mMaxRefreshRate) == 0;
    }

    public final int hashCode() {
        return Objects.hash(
                Float.valueOf(this.mMinRefreshRate), Float.valueOf(this.mMaxRefreshRate));
    }

    public String toString() {
        return "RefreshRateVote{  mMinRefreshRate="
                + this.mMinRefreshRate
                + ", mMaxRefreshRate="
                + this.mMaxRefreshRate
                + " }";
    }
}
