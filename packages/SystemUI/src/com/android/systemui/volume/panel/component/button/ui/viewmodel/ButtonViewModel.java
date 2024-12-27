package com.android.systemui.volume.panel.component.button.ui.viewmodel;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ButtonViewModel {
    public final Icon icon;
    public final boolean isActive;
    public final CharSequence label;

    public ButtonViewModel(Icon icon, CharSequence charSequence, boolean z) {
        this.icon = icon;
        this.label = charSequence;
        this.isActive = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ButtonViewModel)) {
            return false;
        }
        ButtonViewModel buttonViewModel = (ButtonViewModel) obj;
        return Intrinsics.areEqual(this.icon, buttonViewModel.icon) && Intrinsics.areEqual(this.label, buttonViewModel.label) && this.isActive == buttonViewModel.isActive;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isActive) + ControlInfo$$ExternalSyntheticOutline0.m(this.icon.hashCode() * 31, 31, this.label);
    }

    public final String toString() {
        CharSequence charSequence = this.label;
        StringBuilder sb = new StringBuilder("ButtonViewModel(icon=");
        sb.append(this.icon);
        sb.append(", label=");
        sb.append((Object) charSequence);
        sb.append(", isActive=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isActive, ")");
    }

    public /* synthetic */ ButtonViewModel(Icon icon, CharSequence charSequence, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(icon, charSequence, (i & 4) != 0 ? true : z);
    }
}
