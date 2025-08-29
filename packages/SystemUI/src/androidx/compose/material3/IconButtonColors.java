package androidx.compose.material3;

import androidx.compose.ui.graphics.Color;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class IconButtonColors {
    public final long containerColor;
    public final long contentColor;
    public final long disabledContainerColor;
    public final long disabledContentColor;

    public /* synthetic */ IconButtonColors(long j, long j2, long j3, long j4, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4);
    }

    /* renamed from: copy-jRlVdoo, reason: not valid java name */
    public final IconButtonColors m265copyjRlVdoo(long j, long j2, long j3, long j4) {
        return new IconButtonColors(j != 16 ? j : this.containerColor, j2 != 16 ? j2 : this.contentColor, j3 != 16 ? j3 : this.disabledContainerColor, j4 != 16 ? j4 : this.disabledContentColor, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof IconButtonColors)) {
            return false;
        }
        IconButtonColors iconButtonColors = (IconButtonColors) obj;
        Color.Companion companion = Color.Companion;
        return ULong.m3446equalsimpl0(this.containerColor, iconButtonColors.containerColor) && ULong.m3446equalsimpl0(this.contentColor, iconButtonColors.contentColor) && ULong.m3446equalsimpl0(this.disabledContainerColor, iconButtonColors.disabledContainerColor) && ULong.m3446equalsimpl0(this.disabledContentColor, iconButtonColors.disabledContentColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.disabledContentColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.containerColor) * 31, 31, this.contentColor), 31, this.disabledContainerColor);
    }

    private IconButtonColors(long j, long j2, long j3, long j4) {
        this.containerColor = j;
        this.contentColor = j2;
        this.disabledContainerColor = j3;
        this.disabledContentColor = j4;
    }
}
