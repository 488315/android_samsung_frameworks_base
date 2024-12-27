package com.android.systemui.media.controls.shared.model;

import com.android.internal.logging.InstanceId;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class MediaDataLoadingModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Loaded extends MediaDataLoadingModel {
        public final boolean immediatelyUpdateUi;
        public final InstanceId instanceId;

        public /* synthetic */ Loaded(InstanceId instanceId, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(instanceId, (i & 2) != 0 ? true : z);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Loaded)) {
                return false;
            }
            Loaded loaded = (Loaded) obj;
            return Intrinsics.areEqual(this.instanceId, loaded.instanceId) && this.immediatelyUpdateUi == loaded.immediatelyUpdateUi;
        }

        @Override // com.android.systemui.media.controls.shared.model.MediaDataLoadingModel
        public final InstanceId getInstanceId() {
            return this.instanceId;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.immediatelyUpdateUi) + (this.instanceId.hashCode() * 31);
        }

        public final String toString() {
            return "Loaded(instanceId=" + this.instanceId + ", immediatelyUpdateUi=" + this.immediatelyUpdateUi + ")";
        }

        public Loaded(InstanceId instanceId, boolean z) {
            super(null);
            this.instanceId = instanceId;
            this.immediatelyUpdateUi = z;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Removed extends MediaDataLoadingModel {
        public final InstanceId instanceId;

        public Removed(InstanceId instanceId) {
            super(null);
            this.instanceId = instanceId;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Removed) && Intrinsics.areEqual(this.instanceId, ((Removed) obj).instanceId);
        }

        @Override // com.android.systemui.media.controls.shared.model.MediaDataLoadingModel
        public final InstanceId getInstanceId() {
            return this.instanceId;
        }

        public final int hashCode() {
            return this.instanceId.hashCode();
        }

        public final String toString() {
            return "Removed(instanceId=" + this.instanceId + ")";
        }
    }

    private MediaDataLoadingModel() {
    }

    public abstract InstanceId getInstanceId();

    public /* synthetic */ MediaDataLoadingModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
