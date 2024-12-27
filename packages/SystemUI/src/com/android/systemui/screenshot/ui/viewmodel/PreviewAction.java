package com.android.systemui.screenshot.ui.viewmodel;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

public final class PreviewAction {
    public final CharSequence contentDescription;
    public final Function0 onClick;

    public PreviewAction(CharSequence charSequence, Function0 function0) {
        this.contentDescription = charSequence;
        this.onClick = function0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PreviewAction)) {
            return false;
        }
        PreviewAction previewAction = (PreviewAction) obj;
        return Intrinsics.areEqual(this.contentDescription, previewAction.contentDescription) && Intrinsics.areEqual(this.onClick, previewAction.onClick);
    }

    public final int hashCode() {
        return this.onClick.hashCode() + (this.contentDescription.hashCode() * 31);
    }

    public final String toString() {
        return "PreviewAction(contentDescription=" + ((Object) this.contentDescription) + ", onClick=" + this.onClick + ")";
    }
}
