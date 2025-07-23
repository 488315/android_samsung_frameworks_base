package com.android.systemui.volume.panel.domain.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
