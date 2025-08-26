package com.android.systemui.media.controls.data.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class MediaSortKeyModel {
    public final boolean active;
    public final InstanceId instanceId;
    public final Boolean isPlaying;
    public final boolean isResume;
    public final long lastActive;
    public final String notificationKey;
    public final int playbackLocation;
    public final long updateTime;

    public MediaSortKeyModel() {
        this(null, 0, false, false, 0L, null, 0L, null, 255, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaSortKeyModel)) {
            return false;
        }
        MediaSortKeyModel mediaSortKeyModel = (MediaSortKeyModel) obj;
        return Intrinsics.areEqual(this.isPlaying, mediaSortKeyModel.isPlaying) && this.playbackLocation == mediaSortKeyModel.playbackLocation && this.active == mediaSortKeyModel.active && this.isResume == mediaSortKeyModel.isResume && this.lastActive == mediaSortKeyModel.lastActive && Intrinsics.areEqual(this.notificationKey, mediaSortKeyModel.notificationKey) && this.updateTime == mediaSortKeyModel.updateTime && Intrinsics.areEqual(this.instanceId, mediaSortKeyModel.instanceId);
    }

    public final int hashCode() {
        Boolean bool = this.isPlaying;
        int iM = MoveResult$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.playbackLocation, (bool == null ? 0 : bool.hashCode()) * 31, 31), 31, this.active), 31, this.isResume), 31, this.lastActive);
        String str = this.notificationKey;
        int iM2 = MoveResult$$ExternalSyntheticOutline0.m((iM + (str == null ? 0 : str.hashCode())) * 31, 31, this.updateTime);
        InstanceId instanceId = this.instanceId;
        return iM2 + (instanceId != null ? instanceId.hashCode() : 0);
    }

    public final String toString() {
        return "MediaSortKeyModel(isPlaying=" + this.isPlaying + ", playbackLocation=" + this.playbackLocation + ", active=" + this.active + ", isResume=" + this.isResume + ", lastActive=" + this.lastActive + ", notificationKey=" + this.notificationKey + ", updateTime=" + this.updateTime + ", instanceId=" + this.instanceId + ")";
    }

    public MediaSortKeyModel(Boolean bool, int i, boolean z, boolean z2, long j, String str, long j2, InstanceId instanceId) {
        this.isPlaying = bool;
        this.playbackLocation = i;
        this.active = z;
        this.isResume = z2;
        this.lastActive = j;
        this.notificationKey = str;
        this.updateTime = j2;
        this.instanceId = instanceId;
    }

    public /* synthetic */ MediaSortKeyModel(Boolean bool, int i, boolean z, boolean z2, long j, String str, long j2, InstanceId instanceId, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : bool, (i2 & 2) != 0 ? 0 : i, (i2 & 4) != 0 ? true : z, (i2 & 8) != 0 ? false : z2, (i2 & 16) != 0 ? 0L : j, (i2 & 32) != 0 ? null : str, (i2 & 64) != 0 ? 0L : j2, (i2 & 128) != 0 ? null : instanceId);
    }
}
