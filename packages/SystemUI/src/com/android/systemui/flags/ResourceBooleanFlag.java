package com.android.systemui.flags;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

public final class ResourceBooleanFlag implements Flag {
    public final String name;
    public final String namespace;
    public final int resourceId;

    public ResourceBooleanFlag(String str, String str2, int i) {
        this.name = str;
        this.namespace = str2;
        this.resourceId = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResourceBooleanFlag)) {
            return false;
        }
        ResourceBooleanFlag resourceBooleanFlag = (ResourceBooleanFlag) obj;
        return Intrinsics.areEqual(this.name, resourceBooleanFlag.name) && Intrinsics.areEqual(this.namespace, resourceBooleanFlag.namespace) && this.resourceId == resourceBooleanFlag.resourceId;
    }

    @Override // com.android.systemui.flags.Flag
    public final String getName() {
        return this.name;
    }

    @Override // com.android.systemui.flags.Flag
    public final String getNamespace() {
        return this.namespace;
    }

    public final int hashCode() {
        return Integer.hashCode(this.resourceId) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.name.hashCode() * 31, 31, this.namespace);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ResourceBooleanFlag(name=");
        sb.append(this.name);
        sb.append(", namespace=");
        sb.append(this.namespace);
        sb.append(", resourceId=");
        return Anchor$$ExternalSyntheticOutline0.m(this.resourceId, ")", sb);
    }
}
