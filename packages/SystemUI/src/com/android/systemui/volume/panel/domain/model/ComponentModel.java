package com.android.systemui.volume.panel.domain.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ComponentModel {
    public final boolean isAvailable;
    public final String key;

    public ComponentModel(String str, boolean z) {
        this.key = str;
        this.isAvailable = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ComponentModel)) {
            return false;
        }
        ComponentModel componentModel = (ComponentModel) obj;
        return Intrinsics.areEqual(this.key, componentModel.key) && this.isAvailable == componentModel.isAvailable;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isAvailable) + (this.key.hashCode() * 31);
    }

    public final String toString() {
        return "ComponentModel(key=" + this.key + ", isAvailable=" + this.isAvailable + ")";
    }
}
