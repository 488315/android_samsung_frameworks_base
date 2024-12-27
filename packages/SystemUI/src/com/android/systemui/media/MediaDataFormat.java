package com.android.systemui.media;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.systemui.media.controls.shared.model.MediaData;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaDataFormat {
    public final MediaData data;
    public final boolean isImmediately;
    public final boolean isSsReactivated;
    public final String key;
    public final String oldKey;
    public final int receivedSmartspaceCardLatency;

    public MediaDataFormat(String str, String str2, MediaData mediaData, boolean z, int i, boolean z2) {
        this.key = str;
        this.oldKey = str2;
        this.data = mediaData;
        this.isImmediately = z;
        this.receivedSmartspaceCardLatency = i;
        this.isSsReactivated = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaDataFormat)) {
            return false;
        }
        MediaDataFormat mediaDataFormat = (MediaDataFormat) obj;
        return Intrinsics.areEqual(this.key, mediaDataFormat.key) && Intrinsics.areEqual(this.oldKey, mediaDataFormat.oldKey) && Intrinsics.areEqual(this.data, mediaDataFormat.data) && this.isImmediately == mediaDataFormat.isImmediately && this.receivedSmartspaceCardLatency == mediaDataFormat.receivedSmartspaceCardLatency && this.isSsReactivated == mediaDataFormat.isSsReactivated;
    }

    public final int hashCode() {
        int hashCode = this.key.hashCode() * 31;
        String str = this.oldKey;
        return Boolean.hashCode(this.isSsReactivated) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.receivedSmartspaceCardLatency, TransitionData$$ExternalSyntheticOutline0.m((this.data.hashCode() + ((hashCode + (str == null ? 0 : str.hashCode())) * 31)) * 31, 31, this.isImmediately), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MediaDataFormat(key=");
        sb.append(this.key);
        sb.append(", oldKey=");
        sb.append(this.oldKey);
        sb.append(", data=");
        sb.append(this.data);
        sb.append(", isImmediately=");
        sb.append(this.isImmediately);
        sb.append(", receivedSmartspaceCardLatency=");
        sb.append(this.receivedSmartspaceCardLatency);
        sb.append(", isSsReactivated=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isSsReactivated, ")");
    }
}
