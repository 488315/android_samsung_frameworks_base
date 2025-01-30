package com.android.systemui.flags;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ResourceBooleanFlag implements Flag {

    /* renamed from: id */
    public final int f290id;
    public final String name;
    public final String namespace;
    public final int resourceId;
    public final boolean teamfood;

    public ResourceBooleanFlag(int i, String str, String str2, int i2, boolean z) {
        this.f290id = i;
        this.name = str;
        this.namespace = str2;
        this.resourceId = i2;
        this.teamfood = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResourceBooleanFlag)) {
            return false;
        }
        ResourceBooleanFlag resourceBooleanFlag = (ResourceBooleanFlag) obj;
        return this.f290id == resourceBooleanFlag.f290id && Intrinsics.areEqual(this.name, resourceBooleanFlag.name) && Intrinsics.areEqual(this.namespace, resourceBooleanFlag.namespace) && this.resourceId == resourceBooleanFlag.resourceId && this.teamfood == resourceBooleanFlag.teamfood;
    }

    @Override // com.android.systemui.flags.Flag
    public final String getName() {
        return this.name;
    }

    @Override // com.android.systemui.flags.Flag
    public final String getNamespace() {
        return this.namespace;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.resourceId, AppInfo$$ExternalSyntheticOutline0.m41m(this.namespace, AppInfo$$ExternalSyntheticOutline0.m41m(this.name, Integer.hashCode(this.f290id) * 31, 31), 31), 31);
        boolean z = this.teamfood;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return m42m + i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ResourceBooleanFlag(id=");
        sb.append(this.f290id);
        sb.append(", name=");
        sb.append(this.name);
        sb.append(", namespace=");
        sb.append(this.namespace);
        sb.append(", resourceId=");
        sb.append(this.resourceId);
        sb.append(", teamfood=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.teamfood, ")");
    }

    public /* synthetic */ ResourceBooleanFlag(int i, String str, String str2, int i2, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, str2, i2, (i3 & 16) != 0 ? false : z);
    }
}
