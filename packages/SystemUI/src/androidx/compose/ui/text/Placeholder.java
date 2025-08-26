package androidx.compose.ui.text;

import androidx.compose.ui.text.PlaceholderVerticalAlign;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import androidx.compose.ui.unit.TextUnit;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class Placeholder {
    public final long height;
    public final int placeholderVerticalAlign;
    public final long width;

    public /* synthetic */ Placeholder(long j, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, i);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Placeholder)) {
            return false;
        }
        Placeholder placeholder = (Placeholder) obj;
        if (!TextUnit.m868equalsimpl0(this.width, placeholder.width) || !TextUnit.m868equalsimpl0(this.height, placeholder.height)) {
            return false;
        }
        int i = placeholder.placeholderVerticalAlign;
        PlaceholderVerticalAlign.Companion companion = PlaceholderVerticalAlign.Companion;
        return this.placeholderVerticalAlign == i;
    }

    public final int hashCode() {
        TextUnit.Companion companion = TextUnit.Companion;
        int iM = MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.width) * 31, 31, this.height);
        PlaceholderVerticalAlign.Companion companion2 = PlaceholderVerticalAlign.Companion;
        return Integer.hashCode(this.placeholderVerticalAlign) + iM;
    }

    public final String toString() {
        return "Placeholder(width=" + ((Object) TextUnit.m872toStringimpl(this.width)) + ", height=" + ((Object) TextUnit.m872toStringimpl(this.height)) + ", placeholderVerticalAlign=" + ((Object) PlaceholderVerticalAlign.m742toStringimpl(this.placeholderVerticalAlign)) + ')';
    }

    private Placeholder(long j, long j2, int i) {
        this.width = j;
        this.height = j2;
        this.placeholderVerticalAlign = i;
        TextUnit.Companion companion = TextUnit.Companion;
        if ((j & 1095216660480L) == 0) {
            InlineClassHelperKt.throwIllegalArgumentException("width cannot be TextUnit.Unspecified");
        }
        if ((j2 & 1095216660480L) == 0) {
            InlineClassHelperKt.throwIllegalArgumentException("height cannot be TextUnit.Unspecified");
        }
    }
}
