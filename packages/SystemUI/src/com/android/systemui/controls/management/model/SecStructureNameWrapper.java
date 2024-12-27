package com.android.systemui.controls.management.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SecStructureNameWrapper extends SecElementWrapper {
    public final CharSequence displayName;
    public boolean favorite;
    public final boolean needStructureName;
    public final CharSequence structureName;

    public /* synthetic */ SecStructureNameWrapper(CharSequence charSequence, boolean z, CharSequence charSequence2, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(charSequence, z, (i & 4) != 0 ? charSequence : charSequence2, (i & 8) != 0 ? true : z2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SecStructureNameWrapper)) {
            return false;
        }
        SecStructureNameWrapper secStructureNameWrapper = (SecStructureNameWrapper) obj;
        return Intrinsics.areEqual(this.structureName, secStructureNameWrapper.structureName) && this.favorite == secStructureNameWrapper.favorite && Intrinsics.areEqual(this.displayName, secStructureNameWrapper.displayName) && this.needStructureName == secStructureNameWrapper.needStructureName;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.needStructureName) + ControlInfo$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.structureName.hashCode() * 31, 31, this.favorite), 31, this.displayName);
    }

    public final String toString() {
        CharSequence charSequence = this.structureName;
        return "SecStructureNameWrapper(structureName=" + ((Object) charSequence) + ", favorite=" + this.favorite + ", displayName=" + ((Object) this.displayName) + ", needStructureName=" + this.needStructureName + ")";
    }

    public SecStructureNameWrapper(CharSequence charSequence, boolean z, CharSequence charSequence2, boolean z2) {
        super(null);
        this.structureName = charSequence;
        this.favorite = z;
        this.displayName = charSequence2;
        this.needStructureName = z2;
    }
}
