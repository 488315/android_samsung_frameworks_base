package com.android.systemui.qs.bar;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VideoCallMicModeResources {
    public final int betweenMargin;
    public final int defaultMargin;
    public final int defaultStartPadding;
    public final int dividerPadding;
    public final int iconPadding;
    public final int iconSize;
    public final int startPadding;
    public final int textContainerPaddingEnd;
    public final int textContainerPaddingStart;

    public VideoCallMicModeResources(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this.iconPadding = i;
        this.iconSize = i2;
        this.defaultStartPadding = i3;
        this.startPadding = i4;
        this.textContainerPaddingStart = i5;
        this.textContainerPaddingEnd = i6;
        this.defaultMargin = i7;
        this.betweenMargin = i8;
        this.dividerPadding = i9;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VideoCallMicModeResources)) {
            return false;
        }
        VideoCallMicModeResources videoCallMicModeResources = (VideoCallMicModeResources) obj;
        return this.iconPadding == videoCallMicModeResources.iconPadding && this.iconSize == videoCallMicModeResources.iconSize && this.defaultStartPadding == videoCallMicModeResources.defaultStartPadding && this.startPadding == videoCallMicModeResources.startPadding && this.textContainerPaddingStart == videoCallMicModeResources.textContainerPaddingStart && this.textContainerPaddingEnd == videoCallMicModeResources.textContainerPaddingEnd && this.defaultMargin == videoCallMicModeResources.defaultMargin && this.betweenMargin == videoCallMicModeResources.betweenMargin && this.dividerPadding == videoCallMicModeResources.dividerPadding;
    }

    public final int hashCode() {
        return Integer.hashCode(this.dividerPadding) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.betweenMargin, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.defaultMargin, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.textContainerPaddingEnd, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.textContainerPaddingStart, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.startPadding, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.defaultStartPadding, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.iconSize, Integer.hashCode(this.iconPadding) * 31, 31), 31), 31), 31), 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("VideoCallMicModeResources(iconPadding=");
        sb.append(this.iconPadding);
        sb.append(", iconSize=");
        sb.append(this.iconSize);
        sb.append(", defaultStartPadding=");
        sb.append(this.defaultStartPadding);
        sb.append(", startPadding=");
        sb.append(this.startPadding);
        sb.append(", textContainerPaddingStart=");
        sb.append(this.textContainerPaddingStart);
        sb.append(", textContainerPaddingEnd=");
        sb.append(this.textContainerPaddingEnd);
        sb.append(", defaultMargin=");
        sb.append(this.defaultMargin);
        sb.append(", betweenMargin=");
        sb.append(this.betweenMargin);
        sb.append(", dividerPadding=");
        return Anchor$$ExternalSyntheticOutline0.m(this.dividerPadding, ")", sb);
    }
}
