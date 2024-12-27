package com.android.systemui.volume.panel.component.mediaoutput.domain.model;

import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MediaDeviceSessions {
    public final MediaDeviceSession local;
    public final MediaDeviceSession remote;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public MediaDeviceSessions(MediaDeviceSession mediaDeviceSession, MediaDeviceSession mediaDeviceSession2) {
        this.local = mediaDeviceSession;
        this.remote = mediaDeviceSession2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaDeviceSessions)) {
            return false;
        }
        MediaDeviceSessions mediaDeviceSessions = (MediaDeviceSessions) obj;
        return Intrinsics.areEqual(this.local, mediaDeviceSessions.local) && Intrinsics.areEqual(this.remote, mediaDeviceSessions.remote);
    }

    public final int hashCode() {
        MediaDeviceSession mediaDeviceSession = this.local;
        int hashCode = (mediaDeviceSession == null ? 0 : mediaDeviceSession.hashCode()) * 31;
        MediaDeviceSession mediaDeviceSession2 = this.remote;
        return hashCode + (mediaDeviceSession2 != null ? mediaDeviceSession2.hashCode() : 0);
    }

    public final String toString() {
        return "MediaDeviceSessions(local=" + this.local + ", remote=" + this.remote + ")";
    }
}
