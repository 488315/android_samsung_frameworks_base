package com.android.systemui.qs.footer.ui.viewmodel;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Icon;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FooterActionsSecurityButtonViewModel {
    public final Icon icon;
    public final Function2 onClick;
    public final String text;

    public FooterActionsSecurityButtonViewModel(Icon icon, String str, Function2 function2) {
        this.icon = icon;
        this.text = str;
        this.onClick = function2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FooterActionsSecurityButtonViewModel)) {
            return false;
        }
        FooterActionsSecurityButtonViewModel footerActionsSecurityButtonViewModel = (FooterActionsSecurityButtonViewModel) obj;
        return Intrinsics.areEqual(this.icon, footerActionsSecurityButtonViewModel.icon) && Intrinsics.areEqual(this.text, footerActionsSecurityButtonViewModel.text) && Intrinsics.areEqual(this.onClick, footerActionsSecurityButtonViewModel.onClick);
    }

    public final int hashCode() {
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.icon.hashCode() * 31, 31, this.text);
        Function2 function2 = this.onClick;
        return m + (function2 == null ? 0 : function2.hashCode());
    }

    public final String toString() {
        return "FooterActionsSecurityButtonViewModel(icon=" + this.icon + ", text=" + this.text + ", onClick=" + this.onClick + ")";
    }
}
