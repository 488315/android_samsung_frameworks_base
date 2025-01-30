package android.media.p008tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes2.dex */
public final class ScramblingStatusEvent extends FilterEvent {
    private final int mScramblingStatus;

    private ScramblingStatusEvent(int scramblingStatus) {
        this.mScramblingStatus = scramblingStatus;
    }

    public int getScramblingStatus() {
        return this.mScramblingStatus;
    }
}
