package com.android.systemui.flags;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0662xaf167275;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ReleasedFlag extends BooleanFlag {

    /* renamed from: id */
    public final int f289id;
    public final String name;
    public final String namespace;
    public final boolean overridden;
    public final boolean teamfood;

    public /* synthetic */ ReleasedFlag(int i, String str, String str2, boolean z, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, str2, (i2 & 8) != 0 ? false : z, (i2 & 16) != 0 ? false : z2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ReleasedFlag)) {
            return false;
        }
        ReleasedFlag releasedFlag = (ReleasedFlag) obj;
        return this.f289id == releasedFlag.f289id && Intrinsics.areEqual(this.name, releasedFlag.name) && Intrinsics.areEqual(this.namespace, releasedFlag.namespace) && this.teamfood == releasedFlag.teamfood && this.overridden == releasedFlag.overridden;
    }

    @Override // com.android.systemui.flags.BooleanFlag
    public final int getId() {
        return this.f289id;
    }

    @Override // com.android.systemui.flags.BooleanFlag, com.android.systemui.flags.Flag
    public final String getName() {
        return this.name;
    }

    @Override // com.android.systemui.flags.BooleanFlag, com.android.systemui.flags.Flag
    public final String getNamespace() {
        return this.namespace;
    }

    @Override // com.android.systemui.flags.BooleanFlag
    public final boolean getOverridden() {
        return this.overridden;
    }

    @Override // com.android.systemui.flags.BooleanFlag
    public final boolean getTeamfood() {
        return this.teamfood;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m41m = AppInfo$$ExternalSyntheticOutline0.m41m(this.namespace, AppInfo$$ExternalSyntheticOutline0.m41m(this.name, Integer.hashCode(this.f289id) * 31, 31), 31);
        boolean z = this.teamfood;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (m41m + i) * 31;
        boolean z2 = this.overridden;
        return i2 + (z2 ? 1 : z2 ? 1 : 0);
    }

    public final String toString() {
        int i = this.f289id;
        String str = this.name;
        String str2 = this.namespace;
        boolean z = this.teamfood;
        boolean z2 = this.overridden;
        StringBuilder m61m = AbstractC0662xaf167275.m61m("ReleasedFlag(id=", i, ", name=", str, ", namespace=");
        m61m.append(str2);
        m61m.append(", teamfood=");
        m61m.append(z);
        m61m.append(", overridden=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(m61m, z2, ")");
    }

    public ReleasedFlag(int i, String str, String str2, boolean z, boolean z2) {
        super(i, str, str2, true, z, z2);
        this.f289id = i;
        this.name = str;
        this.namespace = str2;
        this.teamfood = z;
        this.overridden = z2;
    }
}
