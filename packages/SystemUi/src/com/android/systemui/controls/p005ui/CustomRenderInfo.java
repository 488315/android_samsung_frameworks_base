package com.android.systemui.controls.p005ui;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomRenderInfo {
    public static final Companion Companion = new Companion(null);
    public static final SparseArray actionIconMap = new SparseArray();
    public static final SparseArray statusIconDrawableMap = new SparseArray();
    public Drawable actionIcon;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public CustomRenderInfo(Drawable drawable) {
        this.actionIcon = drawable;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof CustomRenderInfo) && Intrinsics.areEqual(this.actionIcon, ((CustomRenderInfo) obj).actionIcon);
    }

    public final int hashCode() {
        Drawable drawable = this.actionIcon;
        if (drawable == null) {
            return 0;
        }
        return drawable.hashCode();
    }

    public final String toString() {
        return "CustomRenderInfo(actionIcon=" + this.actionIcon + ")";
    }
}
