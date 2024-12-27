package com.android.systemui.flags;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SysPropBooleanFlag implements Flag {

    /* renamed from: default, reason: not valid java name */
    public final boolean f38default;
    public final String name;
    public final String namespace;

    public SysPropBooleanFlag(String str, String str2, boolean z) {
        this.name = str;
        this.namespace = str2;
        this.f38default = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SysPropBooleanFlag)) {
            return false;
        }
        SysPropBooleanFlag sysPropBooleanFlag = (SysPropBooleanFlag) obj;
        return Intrinsics.areEqual(this.name, sysPropBooleanFlag.name) && Intrinsics.areEqual(this.namespace, sysPropBooleanFlag.namespace) && this.f38default == sysPropBooleanFlag.f38default;
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
        return Boolean.hashCode(this.f38default) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.name.hashCode() * 31, 31, this.namespace);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SysPropBooleanFlag(name=");
        sb.append(this.name);
        sb.append(", namespace=");
        sb.append(this.namespace);
        sb.append(", default=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.f38default, ")");
    }

    public /* synthetic */ SysPropBooleanFlag(String str, String str2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? false : z);
    }
}
