package com.android.systemui.screenshot.message;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LabeledIcon {
    public final Drawable badgedIcon;
    public final CharSequence label;

    public LabeledIcon(CharSequence charSequence, Drawable drawable) {
        this.label = charSequence;
        this.badgedIcon = drawable;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LabeledIcon)) {
            return false;
        }
        LabeledIcon labeledIcon = (LabeledIcon) obj;
        return Intrinsics.areEqual(this.label, labeledIcon.label) && Intrinsics.areEqual(this.badgedIcon, labeledIcon.badgedIcon);
    }

    public final int hashCode() {
        int hashCode = this.label.hashCode() * 31;
        Drawable drawable = this.badgedIcon;
        return hashCode + (drawable == null ? 0 : drawable.hashCode());
    }

    public final String toString() {
        CharSequence charSequence = this.label;
        return "LabeledIcon(label=" + ((Object) charSequence) + ", badgedIcon=" + this.badgedIcon + ")";
    }
}
