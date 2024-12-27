package com.android.systemui.screenshot.ui.viewmodel;

import android.graphics.drawable.Drawable;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ActionButtonAppearance {
    public final Drawable customBackground;
    public final CharSequence description;
    public final Drawable icon;
    public final CharSequence label;
    public final boolean tint;

    public ActionButtonAppearance(Drawable drawable, CharSequence charSequence, CharSequence charSequence2) {
        this(drawable, charSequence, charSequence2, false, null, 24, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActionButtonAppearance)) {
            return false;
        }
        ActionButtonAppearance actionButtonAppearance = (ActionButtonAppearance) obj;
        return Intrinsics.areEqual(this.icon, actionButtonAppearance.icon) && Intrinsics.areEqual(this.label, actionButtonAppearance.label) && Intrinsics.areEqual(this.description, actionButtonAppearance.description) && this.tint == actionButtonAppearance.tint && Intrinsics.areEqual(this.customBackground, actionButtonAppearance.customBackground);
    }

    public final int hashCode() {
        Drawable drawable = this.icon;
        int hashCode = (drawable == null ? 0 : drawable.hashCode()) * 31;
        CharSequence charSequence = this.label;
        int m = TransitionData$$ExternalSyntheticOutline0.m(ControlInfo$$ExternalSyntheticOutline0.m((hashCode + (charSequence == null ? 0 : charSequence.hashCode())) * 31, 31, this.description), 31, this.tint);
        Drawable drawable2 = this.customBackground;
        return m + (drawable2 != null ? drawable2.hashCode() : 0);
    }

    public final String toString() {
        Drawable drawable = this.icon;
        CharSequence charSequence = this.label;
        CharSequence charSequence2 = this.description;
        return "ActionButtonAppearance(icon=" + drawable + ", label=" + ((Object) charSequence) + ", description=" + ((Object) charSequence2) + ", tint=" + this.tint + ", customBackground=" + this.customBackground + ")";
    }

    public ActionButtonAppearance(Drawable drawable, CharSequence charSequence, CharSequence charSequence2, boolean z) {
        this(drawable, charSequence, charSequence2, z, null, 16, null);
    }

    public ActionButtonAppearance(Drawable drawable, CharSequence charSequence, CharSequence charSequence2, boolean z, Drawable drawable2) {
        this.icon = drawable;
        this.label = charSequence;
        this.description = charSequence2;
        this.tint = z;
        this.customBackground = drawable2;
    }

    public /* synthetic */ ActionButtonAppearance(Drawable drawable, CharSequence charSequence, CharSequence charSequence2, boolean z, Drawable drawable2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(drawable, charSequence, charSequence2, (i & 8) != 0 ? true : z, (i & 16) != 0 ? null : drawable2);
    }
}
