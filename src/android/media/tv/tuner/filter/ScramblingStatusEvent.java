package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public final class ScramblingStatusEvent extends FilterEvent {
    private final int mScramblingStatus;

    private ScramblingStatusEvent(int i) {
        this.mScramblingStatus = i;
    }

    public int getScramblingStatus() {
        return this.mScramblingStatus;
    }
}
