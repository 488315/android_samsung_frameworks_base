package androidx.compose.foundation.text.selection;

import androidx.compose.foundation.OverscrollConfiguration$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextSelectionColors {
    public final long backgroundColor;
    public final long handleColor;

    public /* synthetic */ TextSelectionColors(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextSelectionColors)) {
            return false;
        }
        TextSelectionColors textSelectionColors = (TextSelectionColors) obj;
        long j = textSelectionColors.handleColor;
        Color.Companion companion = Color.Companion;
        if (ULong.m3427equalsimpl0(this.handleColor, j)) {
            return ULong.m3427equalsimpl0(this.backgroundColor, textSelectionColors.backgroundColor);
        }
        return false;
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.backgroundColor) + (Long.hashCode(this.handleColor) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SelectionColors(selectionHandleColor=");
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.handleColor, ", selectionBackgroundColor=", sb);
        sb.append((Object) Color.m462toStringimpl(this.backgroundColor));
        sb.append(')');
        return sb.toString();
    }

    private TextSelectionColors(long j, long j2) {
        this.handleColor = j;
        this.backgroundColor = j2;
    }
}
