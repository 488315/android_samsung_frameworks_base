package com.android.compose.theme.typography;

import androidx.compose.ui.text.font.DeviceFontFamilyName;
import androidx.compose.ui.text.font.DeviceFontFamilyNameFontKt;
import androidx.compose.ui.text.font.Font;
import androidx.compose.ui.text.font.FontFamilyKt;
import androidx.compose.ui.text.font.FontListFontFamily;
import androidx.compose.ui.text.font.FontWeight;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class TypefaceTokens {
    public static final Companion Companion = new Companion(null);
    public static final FontWeight WeightMedium;
    public static final FontWeight WeightRegular;
    public final FontListFontFamily bodyLargeEmphasized;
    public final FontListFontFamily bodyMediumEmphasized;
    public final FontListFontFamily bodySmallEmphasized;
    public final FontListFontFamily brand;
    public final FontListFontFamily displayLargeEmphasized;
    public final FontListFontFamily displayMediumEmphasized;
    public final FontListFontFamily displaySmallEmphasized;
    public final FontListFontFamily headlineLargeEmphasized;
    public final FontListFontFamily headlineMediumEmphasized;
    public final FontListFontFamily headlineSmallEmphasized;
    public final FontListFontFamily labelLargeEmphasized;
    public final FontListFontFamily labelMediumEmphasized;
    public final FontListFontFamily labelSmallEmphasized;
    public final FontListFontFamily plain;
    public final FontListFontFamily titleLargeEmphasized;
    public final FontListFontFamily titleMediumEmphasized;
    public final FontListFontFamily titleSmallEmphasized;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        FontWeight.Companion companion = FontWeight.Companion;
        companion.getClass();
        WeightMedium = FontWeight.Medium;
        companion.getClass();
        WeightRegular = FontWeight.Normal;
    }

    public TypefaceTokens(TypefaceNames typefaceNames) {
        String str = typefaceNames.brand;
        DeviceFontFamilyName.m761constructorimpl(str);
        String str2 = typefaceNames.plain;
        DeviceFontFamilyName.m761constructorimpl(str2);
        DeviceFontFamilyName.m761constructorimpl("variable-display-large-emphasized");
        DeviceFontFamilyName.m761constructorimpl("variable-display-medium-emphasized");
        DeviceFontFamilyName.m761constructorimpl("variable-display-small-emphasized");
        DeviceFontFamilyName.m761constructorimpl("variable-headline-large-emphasized");
        DeviceFontFamilyName.m761constructorimpl("variable-headline-medium-emphasized");
        DeviceFontFamilyName.m761constructorimpl("variable-headline-small-emphasized");
        DeviceFontFamilyName.m761constructorimpl("variable-title-large-emphasized");
        DeviceFontFamilyName.m761constructorimpl("variable-title-medium-emphasized");
        DeviceFontFamilyName.m761constructorimpl("variable-title-small-emphasized");
        DeviceFontFamilyName.m761constructorimpl("variable-body-large-emphasized");
        DeviceFontFamilyName.m761constructorimpl("variable-body-medium-emphasized");
        DeviceFontFamilyName.m761constructorimpl("variable-body-small-emphasized");
        DeviceFontFamilyName.m761constructorimpl("variable-label-large-emphasized");
        DeviceFontFamilyName.m761constructorimpl("variable-label-medium-emphasized");
        DeviceFontFamilyName.m761constructorimpl("variable-label-small-emphasized");
        FontWeight fontWeight = WeightMedium;
        Font fontM763Fontvxs03AY$default = DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default(str, fontWeight, 12);
        FontWeight fontWeight2 = WeightRegular;
        this.brand = FontFamilyKt.FontFamily(fontM763Fontvxs03AY$default, DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default(str, fontWeight2, 12));
        this.plain = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default(str2, fontWeight, 12), DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default(str2, fontWeight2, 12));
        this.displayLargeEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-display-large-emphasized", null, 14));
        this.displayMediumEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-display-medium-emphasized", null, 14));
        this.displaySmallEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-display-small-emphasized", null, 14));
        this.headlineLargeEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-headline-large-emphasized", null, 14));
        this.headlineMediumEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-headline-medium-emphasized", null, 14));
        this.headlineSmallEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-headline-small-emphasized", null, 14));
        this.titleLargeEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-title-large-emphasized", null, 14));
        this.titleMediumEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-title-medium-emphasized", null, 14));
        this.titleSmallEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-title-small-emphasized", null, 14));
        this.bodyLargeEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-body-large-emphasized", null, 14));
        this.bodyMediumEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-body-medium-emphasized", null, 14));
        this.bodySmallEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-body-small-emphasized", null, 14));
        this.labelLargeEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-label-large-emphasized", null, 14));
        this.labelMediumEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-label-medium-emphasized", null, 14));
        this.labelSmallEmphasized = FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m763Fontvxs03AY$default("variable-label-small-emphasized", null, 14));
    }
}
