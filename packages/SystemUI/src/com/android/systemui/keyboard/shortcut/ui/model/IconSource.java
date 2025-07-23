package com.android.systemui.keyboard.shortcut.ui.model;

import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.graphics.vector.ImageVector;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IconSource {
    public final ImageVector imageVector;
    public final Painter painter;

    public IconSource() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IconSource)) {
            return false;
        }
        IconSource iconSource = (IconSource) obj;
        return Intrinsics.areEqual(this.imageVector, iconSource.imageVector) && Intrinsics.areEqual(this.painter, iconSource.painter);
    }

    public final int hashCode() {
        ImageVector imageVector = this.imageVector;
        int hashCode = (imageVector == null ? 0 : imageVector.hashCode()) * 31;
        Painter painter = this.painter;
        return hashCode + (painter != null ? painter.hashCode() : 0);
    }

    public final String toString() {
        return "IconSource(imageVector=" + this.imageVector + ", painter=" + this.painter + ")";
    }

    public IconSource(ImageVector imageVector, Painter painter) {
        this.imageVector = imageVector;
        this.painter = painter;
    }

    public /* synthetic */ IconSource(ImageVector imageVector, Painter painter, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : imageVector, (i & 2) != 0 ? null : painter);
    }
}
