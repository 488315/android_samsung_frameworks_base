package com.android.systemui.unfold;

import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FoldStateChange {
    public final int current;
    public final long dtMillis;
    public final int previous;

    public FoldStateChange(int i, int i2, long j) {
        this.previous = i;
        this.current = i2;
        this.dtMillis = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FoldStateChange)) {
            return false;
        }
        FoldStateChange foldStateChange = (FoldStateChange) obj;
        return this.previous == foldStateChange.previous && this.current == foldStateChange.current && this.dtMillis == foldStateChange.dtMillis;
    }

    public final int hashCode() {
        return Long.hashCode(this.dtMillis) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.current, Integer.hashCode(this.previous) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("FoldStateChange(previous=");
        sb.append(this.previous);
        sb.append(", current=");
        sb.append(this.current);
        sb.append(", dtMillis=");
        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.dtMillis, ")", sb);
    }
}
