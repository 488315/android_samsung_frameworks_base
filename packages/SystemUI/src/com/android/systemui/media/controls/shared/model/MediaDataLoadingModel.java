package com.android.systemui.media.controls.shared.model;

import com.android.internal.logging.InstanceId;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class MediaDataLoadingModel {

    public final class Loaded extends MediaDataLoadingModel {
        public final InstanceId instanceId;

        public Loaded(InstanceId instanceId) {
            super(null);
            this.instanceId = instanceId;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Loaded) && Intrinsics.areEqual(this.instanceId, ((Loaded) obj).instanceId);
        }

        @Override // com.android.systemui.media.controls.shared.model.MediaDataLoadingModel
        public final InstanceId getInstanceId() {
            return this.instanceId;
        }

        public final int hashCode() {
            return this.instanceId.hashCode();
        }

        public final String toString() {
            return "Loaded(instanceId=" + this.instanceId + ")";
        }
    }

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

    public /* synthetic */ MediaDataLoadingModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract InstanceId getInstanceId();

    private MediaDataLoadingModel() {
    }
}
