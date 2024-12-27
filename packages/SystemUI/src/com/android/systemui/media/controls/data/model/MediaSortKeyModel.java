package com.android.systemui.media.controls.data.model;

import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaSortKeyModel {
    public final boolean active;
    public final InstanceId instanceId;
    public final Boolean isPlaying;
    public final boolean isPrioritizedRec;
    public final boolean isResume;
    public final long lastActive;
    public final String notificationKey;
    public final int playbackLocation;
    public final long updateTime;

    public MediaSortKeyModel() {
        this(false, null, 0, false, false, 0L, null, 0L, null, 511, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaSortKeyModel)) {
            return false;
        }
        MediaSortKeyModel mediaSortKeyModel = (MediaSortKeyModel) obj;
        return this.isPrioritizedRec == mediaSortKeyModel.isPrioritizedRec && Intrinsics.areEqual(this.isPlaying, mediaSortKeyModel.isPlaying) && this.playbackLocation == mediaSortKeyModel.playbackLocation && this.active == mediaSortKeyModel.active && this.isResume == mediaSortKeyModel.isResume && this.lastActive == mediaSortKeyModel.lastActive && Intrinsics.areEqual(this.notificationKey, mediaSortKeyModel.notificationKey) && this.updateTime == mediaSortKeyModel.updateTime && Intrinsics.areEqual(this.instanceId, mediaSortKeyModel.instanceId);
    }

    public final int hashCode() {
        int hashCode = Boolean.hashCode(this.isPrioritizedRec) * 31;
        Boolean bool = this.isPlaying;
        int m = Scale$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.playbackLocation, (hashCode + (bool == null ? 0 : bool.hashCode())) * 31, 31), 31, this.active), 31, this.isResume), 31, this.lastActive);
        String str = this.notificationKey;
        int m2 = Scale$$ExternalSyntheticOutline0.m((m + (str == null ? 0 : str.hashCode())) * 31, 31, this.updateTime);
        InstanceId instanceId = this.instanceId;
        return m2 + (instanceId != null ? instanceId.hashCode() : 0);
    }

    public final String toString() {
        return "MediaSortKeyModel(isPrioritizedRec=" + this.isPrioritizedRec + ", isPlaying=" + this.isPlaying + ", playbackLocation=" + this.playbackLocation + ", active=" + this.active + ", isResume=" + this.isResume + ", lastActive=" + this.lastActive + ", notificationKey=" + this.notificationKey + ", updateTime=" + this.updateTime + ", instanceId=" + this.instanceId + ")";
    }

    public MediaSortKeyModel(boolean z, Boolean bool, int i, boolean z2, boolean z3, long j, String str, long j2, InstanceId instanceId) {
        this.isPrioritizedRec = z;
        this.isPlaying = bool;
        this.playbackLocation = i;
        this.active = z2;
        this.isResume = z3;
        this.lastActive = j;
        this.notificationKey = str;
        this.updateTime = j2;
        this.instanceId = instanceId;
    }

    public /* synthetic */ MediaSortKeyModel(boolean z, Boolean bool, int i, boolean z2, boolean z3, long j, String str, long j2, InstanceId instanceId, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z, (i2 & 2) != 0 ? null : bool, (i2 & 4) != 0 ? 0 : i, (i2 & 8) != 0 ? true : z2, (i2 & 16) == 0 ? z3 : false, (i2 & 32) != 0 ? 0L : j, (i2 & 64) != 0 ? null : str, (i2 & 128) == 0 ? j2 : 0L, (i2 & 256) == 0 ? instanceId : null);
    }
}
