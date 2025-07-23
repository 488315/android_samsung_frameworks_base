package com.android.systemui.controls.ui;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecRenderInfo {
    public static final Companion Companion = new Companion(null);
    public static final SparseArray actionIconMap = new SparseArray();
    public static final SparseArray statusIconDrawableMap = new SparseArray();
    public Drawable actionIcon;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
