package com.android.systemui.bouncer.shared.model;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BouncerActionButtonModel {
    public final String label;
    public final Function0 onClick;
    public final Function0 onLongClick;

    public BouncerActionButtonModel(String str, Function0 function0, Function0 function02) {
        this.label = str;
        this.onClick = function0;
        this.onLongClick = function02;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BouncerActionButtonModel)) {
            return false;
        }
        BouncerActionButtonModel bouncerActionButtonModel = (BouncerActionButtonModel) obj;
        return Intrinsics.areEqual(this.label, bouncerActionButtonModel.label) && Intrinsics.areEqual(this.onClick, bouncerActionButtonModel.onClick) && Intrinsics.areEqual(this.onLongClick, bouncerActionButtonModel.onLongClick);
    }

    public final int hashCode() {
        int hashCode = (this.onClick.hashCode() + (this.label.hashCode() * 31)) * 31;
        Function0 function0 = this.onLongClick;
        return hashCode + (function0 == null ? 0 : function0.hashCode());
    }

    public final String toString() {
        return "BouncerActionButtonModel(label=" + this.label + ", onClick=" + this.onClick + ", onLongClick=" + this.onLongClick + ")";
    }

    public /* synthetic */ BouncerActionButtonModel(String str, Function0 function0, Function0 function02, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, function0, (i & 4) != 0 ? null : function02);
    }
}
