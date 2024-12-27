package com.android.systemui.media.controls.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class SmartspaceMediaLoadingModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Loaded extends SmartspaceMediaLoadingModel {
        public final boolean isPrioritized;
        public final String key;

        public /* synthetic */ Loaded(String str, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? false : z);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Loaded)) {
                return false;
            }
            Loaded loaded = (Loaded) obj;
            return Intrinsics.areEqual(this.key, loaded.key) && this.isPrioritized == loaded.isPrioritized;
        }

        @Override // com.android.systemui.media.controls.shared.model.SmartspaceMediaLoadingModel
        public final String getKey() {
            return this.key;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isPrioritized) + (this.key.hashCode() * 31);
        }

        public final String toString() {
            return "Loaded(key=" + this.key + ", isPrioritized=" + this.isPrioritized + ")";
        }

        public Loaded(String str, boolean z) {
            super(null);
            this.key = str;
            this.isPrioritized = z;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Removed extends SmartspaceMediaLoadingModel {
        public final boolean immediatelyUpdateUi;
        public final String key;

        public /* synthetic */ Removed(String str, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? true : z);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Removed)) {
                return false;
            }
            Removed removed = (Removed) obj;
            return Intrinsics.areEqual(this.key, removed.key) && this.immediatelyUpdateUi == removed.immediatelyUpdateUi;
        }

        @Override // com.android.systemui.media.controls.shared.model.SmartspaceMediaLoadingModel
        public final String getKey() {
            return this.key;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.immediatelyUpdateUi) + (this.key.hashCode() * 31);
        }

        public final String toString() {
            return "Removed(key=" + this.key + ", immediatelyUpdateUi=" + this.immediatelyUpdateUi + ")";
        }

        public Removed(String str, boolean z) {
            super(null);
            this.key = str;
            this.immediatelyUpdateUi = z;
        }
    }

    private SmartspaceMediaLoadingModel() {
    }

    public abstract String getKey();

    public /* synthetic */ SmartspaceMediaLoadingModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
