package androidx.compose.foundation.contextmenu;

import androidx.compose.foundation.OverscrollConfiguration$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ContextMenuColors {
    public final long backgroundColor;
    public final long disabledIconColor;
    public final long disabledTextColor;
    public final long iconColor;
    public final long textColor;

    public /* synthetic */ ContextMenuColors(long j, long j2, long j3, long j4, long j5, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ContextMenuColors)) {
            return false;
        }
        ContextMenuColors contextMenuColors = (ContextMenuColors) obj;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.backgroundColor, contextMenuColors.backgroundColor) && ULong.m3427equalsimpl0(this.textColor, contextMenuColors.textColor) && ULong.m3427equalsimpl0(this.iconColor, contextMenuColors.iconColor) && ULong.m3427equalsimpl0(this.disabledTextColor, contextMenuColors.disabledTextColor) && ULong.m3427equalsimpl0(this.disabledIconColor, contextMenuColors.disabledIconColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.disabledIconColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.backgroundColor) * 31, 31, this.textColor), 31, this.iconColor), 31, this.disabledTextColor);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ContextMenuColors(backgroundColor=");
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.backgroundColor, ", textColor=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.textColor, ", iconColor=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.iconColor, ", disabledTextColor=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.disabledTextColor, ", disabledIconColor=", sb);
        sb.append((Object) Color.m462toStringimpl(this.disabledIconColor));
        sb.append(')');
        return sb.toString();
    }

    private ContextMenuColors(long j, long j2, long j3, long j4, long j5) {
        this.backgroundColor = j;
        this.textColor = j2;
        this.iconColor = j3;
        this.disabledTextColor = j4;
        this.disabledIconColor = j5;
    }
}
