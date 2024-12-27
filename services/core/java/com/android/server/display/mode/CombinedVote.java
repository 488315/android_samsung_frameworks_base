package com.android.server.display.mode;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CombinedVote implements Vote {
    public final List mVotes;

    public CombinedVote(List list) {
        this.mVotes = Collections.unmodifiableList(list);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CombinedVote) {
            return Objects.equals(this.mVotes, ((CombinedVote) obj).mVotes);
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(this.mVotes);
    }

    public final String toString() {
        return "CombinedVote{ mVotes=" + this.mVotes + " }";
    }

    @Override // com.android.server.display.mode.Vote
    public final void updateSummary(final VoteSummary voteSummary) {
        this.mVotes.forEach(
                new Consumer() { // from class:
                                 // com.android.server.display.mode.CombinedVote$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((Vote) obj).updateSummary(VoteSummary.this);
                    }
                });
    }
}
