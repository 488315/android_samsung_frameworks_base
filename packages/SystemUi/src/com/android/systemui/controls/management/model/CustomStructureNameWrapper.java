package com.android.systemui.controls.management.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomStructureNameWrapper extends CustomElementWrapper {
    public final CharSequence displayName;
    public boolean favorite;
    public final boolean needStructureName;
    public final CharSequence structureName;

    public /* synthetic */ CustomStructureNameWrapper(CharSequence charSequence, boolean z, CharSequence charSequence2, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(charSequence, z, (i & 4) != 0 ? charSequence : charSequence2, (i & 8) != 0 ? true : z2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CustomStructureNameWrapper)) {
            return false;
        }
        CustomStructureNameWrapper customStructureNameWrapper = (CustomStructureNameWrapper) obj;
        return Intrinsics.areEqual(this.structureName, customStructureNameWrapper.structureName) && this.favorite == customStructureNameWrapper.favorite && Intrinsics.areEqual(this.displayName, customStructureNameWrapper.displayName) && this.needStructureName == customStructureNameWrapper.needStructureName;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = this.structureName.hashCode() * 31;
        boolean z = this.favorite;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int hashCode2 = (this.displayName.hashCode() + ((hashCode + i) * 31)) * 31;
        boolean z2 = this.needStructureName;
        return hashCode2 + (z2 ? 1 : z2 ? 1 : 0);
    }

    public final String toString() {
        return "CustomStructureNameWrapper(structureName=" + ((Object) this.structureName) + ", favorite=" + this.favorite + ", displayName=" + ((Object) this.displayName) + ", needStructureName=" + this.needStructureName + ")";
    }

    public CustomStructureNameWrapper(CharSequence charSequence, boolean z, CharSequence charSequence2, boolean z2) {
        super(null);
        this.structureName = charSequence;
        this.favorite = z;
        this.displayName = charSequence2;
        this.needStructureName = z2;
    }
}
