package com.android.server.display.mode;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class SupportedModesVote implements Vote {
    public final List mModeIds;

    public SupportedModesVote(List list) {
        this.mModeIds = Collections.unmodifiableList(list);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SupportedModesVote) {
            return this.mModeIds.equals(((SupportedModesVote) obj).mModeIds);
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(this.mModeIds);
    }

    public final String toString() {
        return "SupportedModesVote{ mModeIds=" + this.mModeIds + " }";
    }

    @Override // com.android.server.display.mode.Vote
    public final void updateSummary(VoteSummary voteSummary) {
        List list = voteSummary.supportedModeIds;
        if (list == null) {
            voteSummary.supportedModeIds = this.mModeIds;
        } else {
            list.retainAll(this.mModeIds);
        }
    }
}
