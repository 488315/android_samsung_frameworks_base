package com.android.systemui.animation;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DialogCuj {
    public final int cujType;
    public final String tag;

    public DialogCuj(int i, String str) {
        this.cujType = i;
        this.tag = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DialogCuj)) {
            return false;
        }
        DialogCuj dialogCuj = (DialogCuj) obj;
        return this.cujType == dialogCuj.cujType && Intrinsics.areEqual(this.tag, dialogCuj.tag);
    }

    public final int hashCode() {
        int hashCode = Integer.hashCode(this.cujType) * 31;
        String str = this.tag;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        return "DialogCuj(cujType=" + this.cujType + ", tag=" + this.tag + ")";
    }

    public /* synthetic */ DialogCuj(int i, String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : str);
    }
}
