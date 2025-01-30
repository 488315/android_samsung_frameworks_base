package com.android.systemui.media;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.media.controls.models.player.MediaData;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = this.key.hashCode() * 31;
        String str = this.oldKey;
        int hashCode2 = (this.data.hashCode() + ((hashCode + (str == null ? 0 : str.hashCode())) * 31)) * 31;
        boolean z = this.isImmediately;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.receivedSmartspaceCardLatency, (hashCode2 + i) * 31, 31);
        boolean z2 = this.isSsReactivated;
        return m42m + (z2 ? 1 : z2 ? 1 : 0);
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
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.isSsReactivated, ")");
    }
}
