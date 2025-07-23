package com.android.systemui.qs.bar;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class VideoCallMicModeStates {
    public final boolean micModeEnabled;
    public final boolean videoCallEnabled;
    public final boolean voIpTranslatorEnabled;

    public VideoCallMicModeStates(boolean z, boolean z2, boolean z3) {
        this.videoCallEnabled = z;
        this.voIpTranslatorEnabled = z2;
        this.micModeEnabled = z3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VideoCallMicModeStates)) {
            return false;
        }
        VideoCallMicModeStates videoCallMicModeStates = (VideoCallMicModeStates) obj;
        return this.videoCallEnabled == videoCallMicModeStates.videoCallEnabled && this.voIpTranslatorEnabled == videoCallMicModeStates.voIpTranslatorEnabled && this.micModeEnabled == videoCallMicModeStates.micModeEnabled;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.micModeEnabled) + TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.videoCallEnabled) * 31, 31, this.voIpTranslatorEnabled);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("VideoCallMicModeStates(videoCallEnabled=");
        sb.append(this.videoCallEnabled);
        sb.append(", voIpTranslatorEnabled=");
        sb.append(this.voIpTranslatorEnabled);
        sb.append(", micModeEnabled=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.micModeEnabled, ")");
    }
}
