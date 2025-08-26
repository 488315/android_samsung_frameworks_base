package com.android.systemui.communal.ui.viewmodel;

import android.appwidget.AppWidgetHostView;
import android.util.SizeF;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class UpdateSize implements Request {
    public final SizeF size;
    public final AppWidgetHostView view;

    public UpdateSize(SizeF sizeF, AppWidgetHostView appWidgetHostView) {
        this.size = sizeF;
        this.view = appWidgetHostView;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UpdateSize)) {
            return false;
        }
        UpdateSize updateSize = (UpdateSize) obj;
        return Intrinsics.areEqual(this.size, updateSize.size) && Intrinsics.areEqual(this.view, updateSize.view);
    }

    public final int hashCode() {
        return this.view.hashCode() + (this.size.hashCode() * 31);
    }

    public final String toString() {
        return "UpdateSize(size=" + this.size + ", view=" + this.view + ")";
    }
}
