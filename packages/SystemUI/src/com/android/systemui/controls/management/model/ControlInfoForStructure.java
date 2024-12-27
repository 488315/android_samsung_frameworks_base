package com.android.systemui.controls.management.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ControlInfoForStructure {
    public final String controlId;
    public final boolean favorite;
    public final CharSequence structureName;

    public ControlInfoForStructure(CharSequence charSequence, String str, boolean z) {
        this.structureName = charSequence;
        this.controlId = str;
        this.favorite = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlInfoForStructure)) {
            return false;
        }
        ControlInfoForStructure controlInfoForStructure = (ControlInfoForStructure) obj;
        return Intrinsics.areEqual(this.structureName, controlInfoForStructure.structureName) && Intrinsics.areEqual(this.controlId, controlInfoForStructure.controlId) && this.favorite == controlInfoForStructure.favorite;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.favorite) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.structureName.hashCode() * 31, 31, this.controlId);
    }

    public final String toString() {
        CharSequence charSequence = this.structureName;
        StringBuilder sb = new StringBuilder("ControlInfoForStructure(structureName=");
        sb.append((Object) charSequence);
        sb.append(", controlId=");
        sb.append(this.controlId);
        sb.append(", favorite=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.favorite, ")");
    }
}
