package com.android.systemui.media.controls.shared.model;

import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.media.controls.shared.model.MediaDataLoadingModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class MediaCommonModel {
    public final boolean canBeRemoved;
    public final MediaDataLoadingModel.Loaded mediaLoadedModel;
    public final long updateTime;

    public MediaCommonModel(MediaDataLoadingModel.Loaded loaded, boolean z, long j) {
        this.mediaLoadedModel = loaded;
        this.canBeRemoved = z;
        this.updateTime = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaCommonModel)) {
            return false;
        }
        MediaCommonModel mediaCommonModel = (MediaCommonModel) obj;
        return Intrinsics.areEqual(this.mediaLoadedModel, mediaCommonModel.mediaLoadedModel) && this.canBeRemoved == mediaCommonModel.canBeRemoved && this.updateTime == mediaCommonModel.updateTime;
    }

    public final int hashCode() {
        return Long.hashCode(this.updateTime) + TransitionData$$ExternalSyntheticOutline0.m(this.mediaLoadedModel.instanceId.hashCode() * 31, 31, this.canBeRemoved);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MediaCommonModel(mediaLoadedModel=");
        sb.append(this.mediaLoadedModel);
        sb.append(", canBeRemoved=");
        sb.append(this.canBeRemoved);
        sb.append(", updateTime=");
        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.updateTime, ")", sb);
    }

    public /* synthetic */ MediaCommonModel(MediaDataLoadingModel.Loaded loaded, boolean z, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(loaded, (i & 2) != 0 ? false : z, (i & 4) != 0 ? 0L : j);
    }
}
