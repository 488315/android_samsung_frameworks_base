package com.android.systemui.qs.bar;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        boolean z = this.videoCallEnabled;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = i * 31;
        boolean z2 = this.voIpTranslatorEnabled;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.micModeEnabled;
        return i4 + (z3 ? 1 : z3 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("VideoCallMicModeStates(videoCallEnabled=");
        sb.append(this.videoCallEnabled);
        sb.append(", voIpTranslatorEnabled=");
        sb.append(this.voIpTranslatorEnabled);
        sb.append(", micModeEnabled=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.micModeEnabled, ")");
    }
}
