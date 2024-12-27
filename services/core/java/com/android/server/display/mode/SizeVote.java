package com.android.server.display.mode;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;

import java.util.Objects;

public final class SizeVote implements Vote {
    public final int mHeight;
    public final int mMinHeight;
    public final int mMinWidth;
    public final int mWidth;

    public SizeVote(int i, int i2, int i3, int i4) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mMinWidth = i3;
        this.mMinHeight = i4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SizeVote)) {
            return false;
        }
        SizeVote sizeVote = (SizeVote) obj;
        return this.mWidth == sizeVote.mWidth
                && this.mHeight == sizeVote.mHeight
                && this.mMinWidth == sizeVote.mMinWidth
                && this.mMinHeight == sizeVote.mMinHeight;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mWidth),
                Integer.valueOf(this.mHeight),
                Integer.valueOf(this.mMinWidth),
                Integer.valueOf(this.mMinHeight));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SizeVote{ mWidth=");
        sb.append(this.mWidth);
        sb.append(", mHeight=");
        sb.append(this.mHeight);
        sb.append(", mMinWidth=");
        sb.append(this.mMinWidth);
        sb.append(", mMinHeight=");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mMinHeight, sb, " }");
    }

    @Override // com.android.server.display.mode.Vote
    public final void updateSummary(VoteSummary voteSummary) {
        int i;
        int i2 = this.mHeight;
        if (i2 <= 0 || (i = this.mWidth) <= 0) {
            return;
        }
        int i3 = voteSummary.width;
        int i4 = this.mMinHeight;
        int i5 = this.mMinWidth;
        if (i3 == -1 && voteSummary.height == -1) {
            voteSummary.width = i;
            voteSummary.height = i2;
            voteSummary.minWidth = i5;
            voteSummary.minHeight = i4;
            return;
        }
        if (voteSummary.mIsDisplayResolutionRangeVotingEnabled) {
            voteSummary.width = Math.min(i3, i);
            voteSummary.height = Math.min(voteSummary.height, i2);
            voteSummary.minWidth = Math.max(voteSummary.minWidth, i5);
            voteSummary.minHeight = Math.max(voteSummary.minHeight, i4);
        }
    }
}
