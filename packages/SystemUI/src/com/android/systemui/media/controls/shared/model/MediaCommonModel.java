package com.android.systemui.media.controls.shared.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.media.controls.shared.model.MediaDataLoadingModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class MediaCommonModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class MediaControl extends MediaCommonModel {
        public final boolean canBeRemoved;
        public final boolean isMediaFromRec;
        public final MediaDataLoadingModel.Loaded mediaLoadedModel;

        public /* synthetic */ MediaControl(MediaDataLoadingModel.Loaded loaded, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(loaded, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MediaControl)) {
                return false;
            }
            MediaControl mediaControl = (MediaControl) obj;
            return Intrinsics.areEqual(this.mediaLoadedModel, mediaControl.mediaLoadedModel) && this.canBeRemoved == mediaControl.canBeRemoved && this.isMediaFromRec == mediaControl.isMediaFromRec;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isMediaFromRec) + TransitionData$$ExternalSyntheticOutline0.m(this.mediaLoadedModel.hashCode() * 31, 31, this.canBeRemoved);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("MediaControl(mediaLoadedModel=");
            sb.append(this.mediaLoadedModel);
            sb.append(", canBeRemoved=");
            sb.append(this.canBeRemoved);
            sb.append(", isMediaFromRec=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isMediaFromRec, ")");
        }

        public MediaControl(MediaDataLoadingModel.Loaded loaded, boolean z, boolean z2) {
            super(null);
            this.mediaLoadedModel = loaded;
            this.canBeRemoved = z;
            this.isMediaFromRec = z2;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class MediaRecommendations extends MediaCommonModel {
        public final SmartspaceMediaLoadingModel recsLoadingModel;

        public MediaRecommendations(SmartspaceMediaLoadingModel smartspaceMediaLoadingModel) {
            super(null);
            this.recsLoadingModel = smartspaceMediaLoadingModel;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof MediaRecommendations) && Intrinsics.areEqual(this.recsLoadingModel, ((MediaRecommendations) obj).recsLoadingModel);
        }

        public final int hashCode() {
            return this.recsLoadingModel.hashCode();
        }

        public final String toString() {
            return "MediaRecommendations(recsLoadingModel=" + this.recsLoadingModel + ")";
        }
    }

    private MediaCommonModel() {
    }

    public /* synthetic */ MediaCommonModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
