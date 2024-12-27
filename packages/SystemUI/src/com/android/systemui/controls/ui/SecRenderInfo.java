package com.android.systemui.controls.ui;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SecRenderInfo {
    public static final Companion Companion = new Companion(null);
    public static final SparseArray actionIconMap = new SparseArray();
    public static final SparseArray statusIconDrawableMap = new SparseArray();
    public Drawable actionIcon;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public SecRenderInfo(Drawable drawable) {
        this.actionIcon = drawable;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SecRenderInfo) && Intrinsics.areEqual(this.actionIcon, ((SecRenderInfo) obj).actionIcon);
    }

    public final int hashCode() {
        Drawable drawable = this.actionIcon;
        if (drawable == null) {
            return 0;
        }
        return drawable.hashCode();
    }

    public final String toString() {
        return "SecRenderInfo(actionIcon=" + this.actionIcon + ")";
    }
}
