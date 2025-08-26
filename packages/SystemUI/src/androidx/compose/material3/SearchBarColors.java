package androidx.compose.material3;

import androidx.compose.ui.graphics.Color;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class SearchBarColors {
    public final long containerColor;
    public final long dividerColor;
    public final TextFieldColors inputFieldColors;

    public /* synthetic */ SearchBarColors(long j, long j2, TextFieldColors textFieldColors, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, textFieldColors);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SearchBarColors)) {
            return false;
        }
        SearchBarColors searchBarColors = (SearchBarColors) obj;
        long j = searchBarColors.containerColor;
        Color.Companion companion = Color.Companion;
        if (ULong.m3447equalsimpl0(this.containerColor, j)) {
            return ULong.m3447equalsimpl0(this.dividerColor, searchBarColors.dividerColor) && Intrinsics.areEqual(this.inputFieldColors, searchBarColors.inputFieldColors);
        }
        return false;
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return this.inputFieldColors.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.containerColor) * 31, 31, this.dividerColor);
    }

    public /* synthetic */ SearchBarColors(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    private SearchBarColors(long j, long j2) {
        this(j, j2, SearchBarKt.UnspecifiedTextFieldColors, null);
    }

    private SearchBarColors(long j, long j2, TextFieldColors textFieldColors) {
        this.containerColor = j;
        this.dividerColor = j2;
        this.inputFieldColors = textFieldColors;
    }
}
