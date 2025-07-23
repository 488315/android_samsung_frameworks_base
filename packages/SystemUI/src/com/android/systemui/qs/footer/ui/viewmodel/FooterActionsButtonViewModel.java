package com.android.systemui.qs.footer.ui.viewmodel;

import com.android.systemui.common.shared.model.Icon;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FooterActionsButtonViewModel {
    public final int backgroundColor;
    public final Icon icon;
    public final Integer iconTint;
    public final int id;
    public final Function1 onClick;

    public FooterActionsButtonViewModel(int i, Icon icon, Integer num, int i2, Function1 function1) {
        this.id = i;
        this.icon = icon;
        this.iconTint = num;
        this.backgroundColor = i2;
        this.onClick = function1;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FooterActionsButtonViewModel)) {
            return false;
        }
        FooterActionsButtonViewModel footerActionsButtonViewModel = (FooterActionsButtonViewModel) obj;
        return this.id == footerActionsButtonViewModel.id && Intrinsics.areEqual(this.icon, footerActionsButtonViewModel.icon) && Intrinsics.areEqual(this.iconTint, footerActionsButtonViewModel.iconTint) && this.backgroundColor == footerActionsButtonViewModel.backgroundColor && Intrinsics.areEqual(this.onClick, footerActionsButtonViewModel.onClick);
    }

    public final int hashCode() {
        int hashCode = (this.icon.hashCode() + (Integer.hashCode(this.id) * 31)) * 31;
        Integer num = this.iconTint;
        return this.onClick.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.backgroundColor, (hashCode + (num == null ? 0 : num.hashCode())) * 31, 31);
    }

    public final String toString() {
        return "FooterActionsButtonViewModel(id=" + this.id + ", icon=" + this.icon + ", iconTint=" + this.iconTint + ", backgroundColor=" + this.backgroundColor + ", onClick=" + this.onClick + ")";
    }
}
