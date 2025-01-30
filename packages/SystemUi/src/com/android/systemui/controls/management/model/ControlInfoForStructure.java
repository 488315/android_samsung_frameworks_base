package com.android.systemui.controls.management.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m41m = AppInfo$$ExternalSyntheticOutline0.m41m(this.controlId, this.structureName.hashCode() * 31, 31);
        boolean z = this.favorite;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return m41m + i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ControlInfoForStructure(structureName=");
        sb.append((Object) this.structureName);
        sb.append(", controlId=");
        sb.append(this.controlId);
        sb.append(", favorite=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.favorite, ")");
    }
}
