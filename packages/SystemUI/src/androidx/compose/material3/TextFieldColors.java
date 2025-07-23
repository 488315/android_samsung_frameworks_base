package androidx.compose.material3;

import androidx.compose.foundation.text.selection.TextSelectionColors;
import androidx.compose.ui.graphics.Color;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextFieldColors {
    public final long cursorColor;
    public final long disabledContainerColor;
    public final long disabledIndicatorColor;
    public final long disabledLabelColor;
    public final long disabledLeadingIconColor;
    public final long disabledPlaceholderColor;
    public final long disabledPrefixColor;
    public final long disabledSuffixColor;
    public final long disabledSupportingTextColor;
    public final long disabledTextColor;
    public final long disabledTrailingIconColor;
    public final long errorContainerColor;
    public final long errorCursorColor;
    public final long errorIndicatorColor;
    public final long errorLabelColor;
    public final long errorLeadingIconColor;
    public final long errorPlaceholderColor;
    public final long errorPrefixColor;
    public final long errorSuffixColor;
    public final long errorSupportingTextColor;
    public final long errorTextColor;
    public final long errorTrailingIconColor;
    public final long focusedContainerColor;
    public final long focusedIndicatorColor;
    public final long focusedLabelColor;
    public final long focusedLeadingIconColor;
    public final long focusedPlaceholderColor;
    public final long focusedPrefixColor;
    public final long focusedSuffixColor;
    public final long focusedSupportingTextColor;
    public final long focusedTextColor;
    public final long focusedTrailingIconColor;
    public final TextSelectionColors textSelectionColors;
    public final long unfocusedContainerColor;
    public final long unfocusedIndicatorColor;
    public final long unfocusedLabelColor;
    public final long unfocusedLeadingIconColor;
    public final long unfocusedPlaceholderColor;
    public final long unfocusedPrefixColor;
    public final long unfocusedSuffixColor;
    public final long unfocusedSupportingTextColor;
    public final long unfocusedTextColor;
    public final long unfocusedTrailingIconColor;

    public /* synthetic */ TextFieldColors(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, TextSelectionColors textSelectionColors, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, long j30, long j31, long j32, long j33, long j34, long j35, long j36, long j37, long j38, long j39, long j40, long j41, long j42, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6, j7, j8, j9, j10, textSelectionColors, j11, j12, j13, j14, j15, j16, j17, j18, j19, j20, j21, j22, j23, j24, j25, j26, j27, j28, j29, j30, j31, j32, j33, j34, j35, j36, j37, j38, j39, j40, j41, j42);
    }

    /* renamed from: containerColor-XeAY9LY$material3_release, reason: not valid java name */
    public final long m308containerColorXeAY9LY$material3_release(boolean z, boolean z2, boolean z3) {
        return !z ? this.disabledContainerColor : z2 ? this.errorContainerColor : z3 ? this.focusedContainerColor : this.unfocusedContainerColor;
    }

    /* renamed from: copy-ejIjP34, reason: not valid java name */
    public final TextFieldColors m309copyejIjP34(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, TextSelectionColors textSelectionColors, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, long j30, long j31, long j32, long j33, long j34, long j35, long j36, long j37, long j38, long j39, long j40, long j41, long j42) {
        return new TextFieldColors(j != 16 ? j : this.focusedTextColor, j2 != 16 ? j2 : this.unfocusedTextColor, j3 != 16 ? j3 : this.disabledTextColor, j4 != 16 ? j4 : this.errorTextColor, j5 != 16 ? j5 : this.focusedContainerColor, j6 != 16 ? j6 : this.unfocusedContainerColor, j7 != 16 ? j7 : this.disabledContainerColor, j8 != 16 ? j8 : this.errorContainerColor, j9 != 16 ? j9 : this.cursorColor, j10 != 16 ? j10 : this.errorCursorColor, textSelectionColors == null ? (TextSelectionColors) new Function0() { // from class: androidx.compose.material3.TextFieldColors$copy$11
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return TextFieldColors.this.textSelectionColors;
            }
        }.invoke() : textSelectionColors, j11 != 16 ? j11 : this.focusedIndicatorColor, j12 != 16 ? j12 : this.unfocusedIndicatorColor, j13 != 16 ? j13 : this.disabledIndicatorColor, j14 != 16 ? j14 : this.errorIndicatorColor, j15 != 16 ? j15 : this.focusedLeadingIconColor, j16 != 16 ? j16 : this.unfocusedLeadingIconColor, j17 != 16 ? j17 : this.disabledLeadingIconColor, j18 != 16 ? j18 : this.errorLeadingIconColor, j19 != 16 ? j19 : this.focusedTrailingIconColor, j20 != 16 ? j20 : this.unfocusedTrailingIconColor, j21 != 16 ? j21 : this.disabledTrailingIconColor, j22 != 16 ? j22 : this.errorTrailingIconColor, j23 != 16 ? j23 : this.focusedLabelColor, j24 != 16 ? j24 : this.unfocusedLabelColor, j25 != 16 ? j25 : this.disabledLabelColor, j26 != 16 ? j26 : this.errorLabelColor, j27 != 16 ? j27 : this.focusedPlaceholderColor, j28 != 16 ? j28 : this.unfocusedPlaceholderColor, j29 != 16 ? j29 : this.disabledPlaceholderColor, j30 != 16 ? j30 : this.errorPlaceholderColor, j31 != 16 ? j31 : this.focusedSupportingTextColor, j32 != 16 ? j32 : this.unfocusedSupportingTextColor, j33 != 16 ? j33 : this.disabledSupportingTextColor, j34 != 16 ? j34 : this.errorSupportingTextColor, j35 != 16 ? j35 : this.focusedPrefixColor, j36 != 16 ? j36 : this.unfocusedPrefixColor, j37 != 16 ? j37 : this.disabledPrefixColor, j38 != 16 ? j38 : this.errorPrefixColor, j39 != 16 ? j39 : this.focusedSuffixColor, j40 != 16 ? j40 : this.unfocusedSuffixColor, j41 != 16 ? j41 : this.disabledSuffixColor, j42 != 16 ? j42 : this.errorSuffixColor, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof TextFieldColors)) {
            return false;
        }
        TextFieldColors textFieldColors = (TextFieldColors) obj;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.focusedTextColor, textFieldColors.focusedTextColor) && ULong.m3427equalsimpl0(this.unfocusedTextColor, textFieldColors.unfocusedTextColor) && ULong.m3427equalsimpl0(this.disabledTextColor, textFieldColors.disabledTextColor) && ULong.m3427equalsimpl0(this.errorTextColor, textFieldColors.errorTextColor) && ULong.m3427equalsimpl0(this.focusedContainerColor, textFieldColors.focusedContainerColor) && ULong.m3427equalsimpl0(this.unfocusedContainerColor, textFieldColors.unfocusedContainerColor) && ULong.m3427equalsimpl0(this.disabledContainerColor, textFieldColors.disabledContainerColor) && ULong.m3427equalsimpl0(this.errorContainerColor, textFieldColors.errorContainerColor) && ULong.m3427equalsimpl0(this.cursorColor, textFieldColors.cursorColor) && ULong.m3427equalsimpl0(this.errorCursorColor, textFieldColors.errorCursorColor) && Intrinsics.areEqual(this.textSelectionColors, textFieldColors.textSelectionColors) && ULong.m3427equalsimpl0(this.focusedIndicatorColor, textFieldColors.focusedIndicatorColor) && ULong.m3427equalsimpl0(this.unfocusedIndicatorColor, textFieldColors.unfocusedIndicatorColor) && ULong.m3427equalsimpl0(this.disabledIndicatorColor, textFieldColors.disabledIndicatorColor) && ULong.m3427equalsimpl0(this.errorIndicatorColor, textFieldColors.errorIndicatorColor) && ULong.m3427equalsimpl0(this.focusedLeadingIconColor, textFieldColors.focusedLeadingIconColor) && ULong.m3427equalsimpl0(this.unfocusedLeadingIconColor, textFieldColors.unfocusedLeadingIconColor) && ULong.m3427equalsimpl0(this.disabledLeadingIconColor, textFieldColors.disabledLeadingIconColor) && ULong.m3427equalsimpl0(this.errorLeadingIconColor, textFieldColors.errorLeadingIconColor) && ULong.m3427equalsimpl0(this.focusedTrailingIconColor, textFieldColors.focusedTrailingIconColor) && ULong.m3427equalsimpl0(this.unfocusedTrailingIconColor, textFieldColors.unfocusedTrailingIconColor) && ULong.m3427equalsimpl0(this.disabledTrailingIconColor, textFieldColors.disabledTrailingIconColor) && ULong.m3427equalsimpl0(this.errorTrailingIconColor, textFieldColors.errorTrailingIconColor) && ULong.m3427equalsimpl0(this.focusedLabelColor, textFieldColors.focusedLabelColor) && ULong.m3427equalsimpl0(this.unfocusedLabelColor, textFieldColors.unfocusedLabelColor) && ULong.m3427equalsimpl0(this.disabledLabelColor, textFieldColors.disabledLabelColor) && ULong.m3427equalsimpl0(this.errorLabelColor, textFieldColors.errorLabelColor) && ULong.m3427equalsimpl0(this.focusedPlaceholderColor, textFieldColors.focusedPlaceholderColor) && ULong.m3427equalsimpl0(this.unfocusedPlaceholderColor, textFieldColors.unfocusedPlaceholderColor) && ULong.m3427equalsimpl0(this.disabledPlaceholderColor, textFieldColors.disabledPlaceholderColor) && ULong.m3427equalsimpl0(this.errorPlaceholderColor, textFieldColors.errorPlaceholderColor) && ULong.m3427equalsimpl0(this.focusedSupportingTextColor, textFieldColors.focusedSupportingTextColor) && ULong.m3427equalsimpl0(this.unfocusedSupportingTextColor, textFieldColors.unfocusedSupportingTextColor) && ULong.m3427equalsimpl0(this.disabledSupportingTextColor, textFieldColors.disabledSupportingTextColor) && ULong.m3427equalsimpl0(this.errorSupportingTextColor, textFieldColors.errorSupportingTextColor) && ULong.m3427equalsimpl0(this.focusedPrefixColor, textFieldColors.focusedPrefixColor) && ULong.m3427equalsimpl0(this.unfocusedPrefixColor, textFieldColors.unfocusedPrefixColor) && ULong.m3427equalsimpl0(this.disabledPrefixColor, textFieldColors.disabledPrefixColor) && ULong.m3427equalsimpl0(this.errorPrefixColor, textFieldColors.errorPrefixColor) && ULong.m3427equalsimpl0(this.focusedSuffixColor, textFieldColors.focusedSuffixColor) && ULong.m3427equalsimpl0(this.unfocusedSuffixColor, textFieldColors.unfocusedSuffixColor) && ULong.m3427equalsimpl0(this.disabledSuffixColor, textFieldColors.disabledSuffixColor) && ULong.m3427equalsimpl0(this.errorSuffixColor, textFieldColors.errorSuffixColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.errorSuffixColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m((Long.hashCode(this.unfocusedLabelColor) + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m((this.textSelectionColors.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.focusedTextColor) * 31, 31, this.unfocusedTextColor), 31, this.disabledTextColor), 31, this.errorTextColor), 31, this.focusedContainerColor), 31, this.unfocusedContainerColor), 31, this.disabledContainerColor), 31, this.errorContainerColor), 31, this.cursorColor), 31, this.errorCursorColor)) * 31, 31, this.focusedIndicatorColor), 31, this.unfocusedIndicatorColor), 31, this.disabledIndicatorColor), 31, this.errorIndicatorColor), 31, this.focusedLeadingIconColor), 31, this.unfocusedLeadingIconColor), 31, this.disabledLeadingIconColor), 31, this.errorLeadingIconColor), 31, this.focusedTrailingIconColor), 31, this.unfocusedTrailingIconColor), 31, this.disabledTrailingIconColor), 31, this.errorTrailingIconColor), 31, this.focusedLabelColor)) * 31, 31, this.disabledLabelColor), 31, this.errorLabelColor), 31, this.focusedPlaceholderColor), 31, this.unfocusedPlaceholderColor), 31, this.disabledPlaceholderColor), 31, this.errorPlaceholderColor), 31, this.focusedSupportingTextColor), 31, this.unfocusedSupportingTextColor), 31, this.disabledSupportingTextColor), 31, this.errorSupportingTextColor), 31, this.focusedPrefixColor), 31, this.unfocusedPrefixColor), 31, this.disabledPrefixColor), 31, this.errorPrefixColor), 31, this.focusedSuffixColor), 31, this.unfocusedSuffixColor), 31, this.disabledSuffixColor);
    }

    /* renamed from: indicatorColor-XeAY9LY$material3_release, reason: not valid java name */
    public final long m310indicatorColorXeAY9LY$material3_release(boolean z, boolean z2, boolean z3) {
        return !z ? this.disabledIndicatorColor : z2 ? this.errorIndicatorColor : z3 ? this.focusedIndicatorColor : this.unfocusedIndicatorColor;
    }

    /* renamed from: textColor-XeAY9LY$material3_release, reason: not valid java name */
    public final long m311textColorXeAY9LY$material3_release(boolean z, boolean z2, boolean z3) {
        return !z ? this.disabledTextColor : z2 ? this.errorTextColor : z3 ? this.focusedTextColor : this.unfocusedTextColor;
    }

    private TextFieldColors(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, TextSelectionColors textSelectionColors, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, long j30, long j31, long j32, long j33, long j34, long j35, long j36, long j37, long j38, long j39, long j40, long j41, long j42) {
        this.focusedTextColor = j;
        this.unfocusedTextColor = j2;
        this.disabledTextColor = j3;
        this.errorTextColor = j4;
        this.focusedContainerColor = j5;
        this.unfocusedContainerColor = j6;
        this.disabledContainerColor = j7;
        this.errorContainerColor = j8;
        this.cursorColor = j9;
        this.errorCursorColor = j10;
        this.textSelectionColors = textSelectionColors;
        this.focusedIndicatorColor = j11;
        this.unfocusedIndicatorColor = j12;
        this.disabledIndicatorColor = j13;
        this.errorIndicatorColor = j14;
        this.focusedLeadingIconColor = j15;
        this.unfocusedLeadingIconColor = j16;
        this.disabledLeadingIconColor = j17;
        this.errorLeadingIconColor = j18;
        this.focusedTrailingIconColor = j19;
        this.unfocusedTrailingIconColor = j20;
        this.disabledTrailingIconColor = j21;
        this.errorTrailingIconColor = j22;
        this.focusedLabelColor = j23;
        this.unfocusedLabelColor = j24;
        this.disabledLabelColor = j25;
        this.errorLabelColor = j26;
        this.focusedPlaceholderColor = j27;
        this.unfocusedPlaceholderColor = j28;
        this.disabledPlaceholderColor = j29;
        this.errorPlaceholderColor = j30;
        this.focusedSupportingTextColor = j31;
        this.unfocusedSupportingTextColor = j32;
        this.disabledSupportingTextColor = j33;
        this.errorSupportingTextColor = j34;
        this.focusedPrefixColor = j35;
        this.unfocusedPrefixColor = j36;
        this.disabledPrefixColor = j37;
        this.errorPrefixColor = j38;
        this.focusedSuffixColor = j39;
        this.unfocusedSuffixColor = j40;
        this.disabledSuffixColor = j41;
        this.errorSuffixColor = j42;
    }
}
