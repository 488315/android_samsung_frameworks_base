package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import android.graphics.drawable.Drawable;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class FacePile extends ConversationAvatar {
    public final int bottomBackgroundColor;
    public final Drawable bottomIconDrawable;
    public final Drawable topIconDrawable;

    public FacePile(Drawable drawable, Drawable drawable2, int i) {
        super(null);
        this.topIconDrawable = drawable;
        this.bottomIconDrawable = drawable2;
        this.bottomBackgroundColor = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FacePile)) {
            return false;
        }
        FacePile facePile = (FacePile) obj;
        return Intrinsics.areEqual(this.topIconDrawable, facePile.topIconDrawable) && Intrinsics.areEqual(this.bottomIconDrawable, facePile.bottomIconDrawable) && this.bottomBackgroundColor == facePile.bottomBackgroundColor;
    }

    public final int hashCode() {
        Drawable drawable = this.topIconDrawable;
        int hashCode = (drawable == null ? 0 : drawable.hashCode()) * 31;
        Drawable drawable2 = this.bottomIconDrawable;
        return Integer.hashCode(this.bottomBackgroundColor) + ((hashCode + (drawable2 != null ? drawable2.hashCode() : 0)) * 31);
    }

    public final String toString() {
        Drawable drawable = this.topIconDrawable;
        Drawable drawable2 = this.bottomIconDrawable;
        StringBuilder sb = new StringBuilder("FacePile(topIconDrawable=");
        sb.append(drawable);
        sb.append(", bottomIconDrawable=");
        sb.append(drawable2);
        sb.append(", bottomBackgroundColor=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.bottomBackgroundColor, ")", sb);
    }
}
