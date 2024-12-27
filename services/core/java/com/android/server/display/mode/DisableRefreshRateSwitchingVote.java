package com.android.server.display.mode;

import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DisableRefreshRateSwitchingVote implements Vote {
    public final boolean mDisableRefreshRateSwitching;

    public DisableRefreshRateSwitchingVote(boolean z) {
        this.mDisableRefreshRateSwitching = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof DisableRefreshRateSwitchingVote) && this.mDisableRefreshRateSwitching == ((DisableRefreshRateSwitchingVote) obj).mDisableRefreshRateSwitching;
    }

    public final int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mDisableRefreshRateSwitching));
    }

    public final String toString() {
        return OptionalBool$$ExternalSyntheticOutline0.m(" }", new StringBuilder("DisableRefreshRateSwitchingVote{ mDisableRefreshRateSwitching="), this.mDisableRefreshRateSwitching);
    }

    @Override // com.android.server.display.mode.Vote
    public final void updateSummary(VoteSummary voteSummary) {
        voteSummary.disableRefreshRateSwitching = voteSummary.disableRefreshRateSwitching || this.mDisableRefreshRateSwitching;
    }
}
