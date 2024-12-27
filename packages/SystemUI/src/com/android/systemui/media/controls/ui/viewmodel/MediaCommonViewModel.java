package com.android.systemui.media.controls.ui.viewmodel;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class MediaCommonViewModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class MediaControl extends MediaCommonViewModel {
        public final MediaControlViewModel controlViewModel;
        public final boolean immediatelyUpdateUi;
        public final InstanceId instanceId;
        public final boolean isMediaFromRec;
        public final Function1 onAdded;
        public final Function1 onRemoved;
        public final Function1 onUpdated;

        public /* synthetic */ MediaControl(InstanceId instanceId, boolean z, MediaControlViewModel mediaControlViewModel, Function1 function1, Function1 function12, Function1 function13, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(instanceId, z, mediaControlViewModel, function1, function12, function13, (i & 64) != 0 ? false : z2);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MediaControl)) {
                return false;
            }
            MediaControl mediaControl = (MediaControl) obj;
            return Intrinsics.areEqual(this.instanceId, mediaControl.instanceId) && this.immediatelyUpdateUi == mediaControl.immediatelyUpdateUi && Intrinsics.areEqual(this.controlViewModel, mediaControl.controlViewModel) && Intrinsics.areEqual(this.onAdded, mediaControl.onAdded) && Intrinsics.areEqual(this.onRemoved, mediaControl.onRemoved) && Intrinsics.areEqual(this.onUpdated, mediaControl.onUpdated) && this.isMediaFromRec == mediaControl.isMediaFromRec;
        }

        @Override // com.android.systemui.media.controls.ui.viewmodel.MediaCommonViewModel
        public final Function1 getOnRemoved() {
            return this.onRemoved;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isMediaFromRec) + ((this.onUpdated.hashCode() + ((this.onRemoved.hashCode() + ((this.onAdded.hashCode() + ((this.controlViewModel.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(this.instanceId.hashCode() * 31, 31, this.immediatelyUpdateUi)) * 31)) * 31)) * 31)) * 31);
        }

        public final String toString() {
            InstanceId instanceId = this.instanceId;
            StringBuilder sb = new StringBuilder("MediaControl(instanceId=");
            sb.append(instanceId);
            sb.append(", immediatelyUpdateUi=");
            sb.append(this.immediatelyUpdateUi);
            sb.append(", controlViewModel=");
            sb.append(this.controlViewModel);
            sb.append(", onAdded=");
            sb.append(this.onAdded);
            sb.append(", onRemoved=");
            sb.append(this.onRemoved);
            sb.append(", onUpdated=");
            sb.append(this.onUpdated);
            sb.append(", isMediaFromRec=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isMediaFromRec, ")");
        }

        public MediaControl(InstanceId instanceId, boolean z, MediaControlViewModel mediaControlViewModel, Function1 function1, Function1 function12, Function1 function13, boolean z2) {
            super(null);
            this.instanceId = instanceId;
            this.immediatelyUpdateUi = z;
            this.controlViewModel = mediaControlViewModel;
            this.onAdded = function1;
            this.onRemoved = function12;
            this.onUpdated = function13;
            this.isMediaFromRec = z2;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class MediaRecommendations extends MediaCommonViewModel {
        public final String key;
        public final boolean loadingEnabled;
        public final Function1 onAdded;
        public final Function1 onRemoved;
        public final Function1 onUpdated;
        public final MediaRecommendationsViewModel recsViewModel;

        public MediaRecommendations(String str, boolean z, MediaRecommendationsViewModel mediaRecommendationsViewModel, Function1 function1, Function1 function12, Function1 function13) {
            super(null);
            this.key = str;
            this.loadingEnabled = z;
            this.recsViewModel = mediaRecommendationsViewModel;
            this.onAdded = function1;
            this.onRemoved = function12;
            this.onUpdated = function13;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MediaRecommendations)) {
                return false;
            }
            MediaRecommendations mediaRecommendations = (MediaRecommendations) obj;
            return Intrinsics.areEqual(this.key, mediaRecommendations.key) && this.loadingEnabled == mediaRecommendations.loadingEnabled && Intrinsics.areEqual(this.recsViewModel, mediaRecommendations.recsViewModel) && Intrinsics.areEqual(this.onAdded, mediaRecommendations.onAdded) && Intrinsics.areEqual(this.onRemoved, mediaRecommendations.onRemoved) && Intrinsics.areEqual(this.onUpdated, mediaRecommendations.onUpdated);
        }

        @Override // com.android.systemui.media.controls.ui.viewmodel.MediaCommonViewModel
        public final Function1 getOnRemoved() {
            return this.onRemoved;
        }

        public final int hashCode() {
            return this.onUpdated.hashCode() + ((this.onRemoved.hashCode() + ((this.onAdded.hashCode() + ((this.recsViewModel.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(this.key.hashCode() * 31, 31, this.loadingEnabled)) * 31)) * 31)) * 31);
        }

        public final String toString() {
            return "MediaRecommendations(key=" + this.key + ", loadingEnabled=" + this.loadingEnabled + ", recsViewModel=" + this.recsViewModel + ", onAdded=" + this.onAdded + ", onRemoved=" + this.onRemoved + ", onUpdated=" + this.onUpdated + ")";
        }
    }

    private MediaCommonViewModel() {
    }

    public abstract Function1 getOnRemoved();

    public /* synthetic */ MediaCommonViewModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
