package androidx.picker.model.viewdata;

import android.view.View;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HeaderFooterViewData implements ViewData {
    public final View view;

    public HeaderFooterViewData(View view) {
        this.view = view;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof HeaderFooterViewData) && Intrinsics.areEqual(this.view, ((HeaderFooterViewData) obj).view);
    }

    public final int hashCode() {
        return this.view.hashCode();
    }

    public final String toString() {
        return "HeaderFooterViewData(view=" + this.view + ')';
    }

    @Override // androidx.picker.model.viewdata.ViewData
    public final Object getKey() {
        return this;
    }
}
