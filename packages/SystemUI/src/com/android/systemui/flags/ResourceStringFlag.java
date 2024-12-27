package com.android.systemui.flags;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ResourceStringFlag implements Flag {
    public final String name;
    public final String namespace;
    public final int resourceId;
    public final boolean teamfood;

    public ResourceStringFlag(String str, String str2, int i, boolean z) {
        this.name = str;
        this.namespace = str2;
        this.resourceId = i;
        this.teamfood = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResourceStringFlag)) {
            return false;
        }
        ResourceStringFlag resourceStringFlag = (ResourceStringFlag) obj;
        return Intrinsics.areEqual(this.name, resourceStringFlag.name) && Intrinsics.areEqual(this.namespace, resourceStringFlag.namespace) && this.resourceId == resourceStringFlag.resourceId && this.teamfood == resourceStringFlag.teamfood;
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
        return Boolean.hashCode(this.teamfood) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.resourceId, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.name.hashCode() * 31, 31, this.namespace), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ResourceStringFlag(name=");
        sb.append(this.name);
        sb.append(", namespace=");
        sb.append(this.namespace);
        sb.append(", resourceId=");
        sb.append(this.resourceId);
        sb.append(", teamfood=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.teamfood, ")");
    }

    public /* synthetic */ ResourceStringFlag(String str, String str2, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, i, (i2 & 8) != 0 ? false : z);
    }
}
