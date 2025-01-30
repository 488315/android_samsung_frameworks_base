package com.android.systemui.flags;

import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SysPropBooleanFlag implements Flag {

    /* renamed from: default, reason: not valid java name */
    public final boolean f830default;

    /* renamed from: id */
    public final int f293id;
    public final String name;
    public final String namespace;

    public SysPropBooleanFlag(int i, String str, String str2, boolean z) {
        this.f293id = i;
        this.name = str;
        this.namespace = str2;
        this.f830default = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SysPropBooleanFlag)) {
            return false;
        }
        SysPropBooleanFlag sysPropBooleanFlag = (SysPropBooleanFlag) obj;
        return this.f293id == sysPropBooleanFlag.f293id && Intrinsics.areEqual(this.name, sysPropBooleanFlag.name) && Intrinsics.areEqual(this.namespace, sysPropBooleanFlag.namespace) && getDefault().booleanValue() == sysPropBooleanFlag.getDefault().booleanValue();
    }

    public final Boolean getDefault() {
        return Boolean.valueOf(this.f830default);
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
        return getDefault().hashCode() + AppInfo$$ExternalSyntheticOutline0.m41m(this.namespace, AppInfo$$ExternalSyntheticOutline0.m41m(this.name, Integer.hashCode(this.f293id) * 31, 31), 31);
    }

    public final String toString() {
        return "SysPropBooleanFlag(id=" + this.f293id + ", name=" + this.name + ", namespace=" + this.namespace + ", default=" + getDefault() + ")";
    }

    public /* synthetic */ SysPropBooleanFlag(int i, String str, String str2, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, str2, (i2 & 8) != 0 ? false : z);
    }
}
