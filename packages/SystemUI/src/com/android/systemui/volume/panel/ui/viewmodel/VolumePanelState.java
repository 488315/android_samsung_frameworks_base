package com.android.systemui.volume.panel.ui.viewmodel;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class VolumePanelState {
    public final boolean isLargeScreen;
    public final int orientation;

    public VolumePanelState(int i, boolean z) {
        this.orientation = i;
        this.isLargeScreen = z;
        if (i != 1 && i != 2 && i != 0 && i != 3) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown orientation: ").toString());
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VolumePanelState)) {
            return false;
        }
        VolumePanelState volumePanelState = (VolumePanelState) obj;
        return this.orientation == volumePanelState.orientation && this.isLargeScreen == volumePanelState.isLargeScreen;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isLargeScreen) + (Integer.hashCode(this.orientation) * 31);
    }

    public final String toString() {
        return "VolumePanelState(orientation=" + this.orientation + ", isLargeScreen=" + this.isLargeScreen + ")";
    }
}
