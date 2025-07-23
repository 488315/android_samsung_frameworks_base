package com.android.systemui.flags;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SysPropBooleanFlag implements Flag {

    /* renamed from: default, reason: not valid java name */
    public final boolean f42default;
    public final String name;
    public final String namespace;

    public SysPropBooleanFlag(String str, String str2, boolean z) {
        this.name = str;
        this.namespace = str2;
        this.f42default = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SysPropBooleanFlag)) {
            return false;
        }
        SysPropBooleanFlag sysPropBooleanFlag = (SysPropBooleanFlag) obj;
        return Intrinsics.areEqual(this.name, sysPropBooleanFlag.name) && Intrinsics.areEqual(this.namespace, sysPropBooleanFlag.namespace) && this.f42default == sysPropBooleanFlag.f42default;
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
        return Boolean.hashCode(this.f42default) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.name.hashCode() * 31, 31, this.namespace);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SysPropBooleanFlag(name=");
        sb.append(this.name);
        sb.append(", namespace=");
        sb.append(this.namespace);
        sb.append(", default=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.f42default, ")");
    }

    public /* synthetic */ SysPropBooleanFlag(String str, String str2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? false : z);
    }
}
