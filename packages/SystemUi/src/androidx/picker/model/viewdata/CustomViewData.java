package androidx.picker.model.viewdata;

import android.view.View;
import androidx.picker.model.SpanData;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomViewData implements ViewData, SpanData {
    public final int spanCount;
    public final View view;

    public CustomViewData(View view, int i) {
        this.view = view;
        this.spanCount = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CustomViewData)) {
            return false;
        }
        CustomViewData customViewData = (CustomViewData) obj;
        return Intrinsics.areEqual(this.view, customViewData.view) && this.spanCount == customViewData.spanCount;
    }

    @Override // androidx.picker.model.SpanData
    public final int getSpanCount() {
        return this.spanCount;
    }

    public final int hashCode() {
        View view = this.view;
        return Integer.hashCode(this.spanCount) + ((view == null ? 0 : view.hashCode()) * 31);
    }

    public final String toString() {
        return "CustomViewData(view=" + this.view + ", spanCount=" + this.spanCount + ')';
    }

    public /* synthetic */ CustomViewData(View view, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(view, (i2 & 2) != 0 ? 1 : i);
    }

    @Override // androidx.picker.model.viewdata.ViewData
    public final Object getKey() {
        return this;
    }
}
