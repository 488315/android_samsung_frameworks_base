package com.android.systemui.volume.panel.component.mediaoutput.shared.model;

import kotlin.jvm.internal.Intrinsics;

public final class SessionWithPlaybackState {
    public final boolean isPlaybackActive;
    public final MediaDeviceSession session;

    public SessionWithPlaybackState(MediaDeviceSession mediaDeviceSession, boolean z) {
        this.session = mediaDeviceSession;
        this.isPlaybackActive = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SessionWithPlaybackState)) {
            return false;
        }
        SessionWithPlaybackState sessionWithPlaybackState = (SessionWithPlaybackState) obj;
        return Intrinsics.areEqual(this.session, sessionWithPlaybackState.session) && this.isPlaybackActive == sessionWithPlaybackState.isPlaybackActive;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isPlaybackActive) + (this.session.hashCode() * 31);
    }

    public final String toString() {
        return "SessionWithPlaybackState(session=" + this.session + ", isPlaybackActive=" + this.isPlaybackActive + ")";
    }
}
