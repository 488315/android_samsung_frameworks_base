package com.android.systemui.media.mediaoutput.compose.theme;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.PlatformTextStyle;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.DeviceFontFamilyName;
import androidx.compose.ui.text.font.DeviceFontFamilyNameFontKt;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontFamilyKt;
import androidx.compose.ui.text.font.FontListFontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class TypeKt {
    public static final TextStyle DescriptionTextStyle(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-314729347);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.DescriptionTextStyle (Type.kt:100)");
        }
        TextStyle.Companion companion = TextStyle.Companion;
        TextStyle secRegular = getSecRegular();
        long mediaPrimaryColor = ColorKt.mediaPrimaryColor(composerImpl);
        TextUnitType.Companion.getClass();
        TextStyle m754copyp1EtxEg$default = TextStyle.m754copyp1EtxEg$default(secRegular, mediaPrimaryColor, TextUnitKt.pack(14.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return m754copyp1EtxEg$default;
    }

    public static final TextStyle MediaCardContentArtist(Composer composer) {
        long Color;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-996128775);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.MediaCardContentArtist (Type.kt:50)");
        }
        TextStyle.Companion companion = TextStyle.Companion;
        TextStyle secRegular = getSecRegular();
        Color = androidx.compose.ui.graphics.ColorKt.Color(Color.m461getRedimpl(r3), Color.m460getGreenimpl(r3), Color.m458getBlueimpl(r3), 0.7f, Color.m459getColorSpaceimpl(ColorKt.mediaPrimaryColor(composerImpl)));
        TextUnitType.Companion.getClass();
        TextStyle m754copyp1EtxEg$default = TextStyle.m754copyp1EtxEg$default(secRegular, Color, TextUnitKt.pack(13.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return m754copyp1EtxEg$default;
    }

    public static final TextStyle MediaCardContentTitle(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(472233294);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.MediaCardContentTitle (Type.kt:44)");
        }
        TextStyle.Companion companion = TextStyle.Companion;
        TextStyle secSemiBold = getSecSemiBold();
        long mediaPrimaryColor = ColorKt.mediaPrimaryColor(composerImpl);
        TextUnitType.Companion.getClass();
        TextStyle m754copyp1EtxEg$default = TextStyle.m754copyp1EtxEg$default(secSemiBold, mediaPrimaryColor, TextUnitKt.pack(14.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return m754copyp1EtxEg$default;
    }

    public static final TextStyle MediaCardProgressTime(Composer composer) {
        long Color;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1953791629);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.MediaCardProgressTime (Type.kt:56)");
        }
        TextStyle.Companion companion = TextStyle.Companion;
        TextStyle secRegular = getSecRegular();
        Color = androidx.compose.ui.graphics.ColorKt.Color(Color.m461getRedimpl(r3), Color.m460getGreenimpl(r3), Color.m458getBlueimpl(r3), 0.8f, Color.m459getColorSpaceimpl(androidx.compose.ui.graphics.ColorKt.Color(16448255)));
        TextUnitType.Companion.getClass();
        TextStyle m754copyp1EtxEg$default = TextStyle.m754copyp1EtxEg$default(secRegular, Color, TextUnitKt.pack(9.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return m754copyp1EtxEg$default;
    }

    public static final TextStyle TitleTextStyle(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1663019687);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.theme.TitleTextStyle (Type.kt:82)");
        }
        TextStyle.Companion companion = TextStyle.Companion;
        TextStyle secBold = getSecBold();
        long mediaPrimaryColor = ColorKt.mediaPrimaryColor(composerImpl);
        TextUnitType.Companion.getClass();
        TextStyle m754copyp1EtxEg$default = TextStyle.m754copyp1EtxEg$default(secBold, mediaPrimaryColor, TextUnitKt.pack(21.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 0, 16777212);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return m754copyp1EtxEg$default;
    }

    public static final FontListFontFamily getSec() {
        DeviceFontFamilyName.m759constructorimpl("sec");
        return FontFamilyKt.FontFamily(DeviceFontFamilyNameFontKt.m761Fontvxs03AY$default("sec", null, 14));
    }

    public static final TextStyle getSecBold() {
        FontFamily.Companion companion = FontFamily.Companion;
        FontListFontFamily sec = getSec();
        FontWeight.Companion.getClass();
        return new TextStyle(0L, 0L, FontWeight.W700, (FontStyle) null, (FontSynthesis) null, sec, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, 0L, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16777179, (DefaultConstructorMarker) null);
    }

    public static final TextStyle getSecRegular() {
        FontFamily.Companion companion = FontFamily.Companion;
        FontListFontFamily sec = getSec();
        FontWeight.Companion.getClass();
        return new TextStyle(0L, 0L, FontWeight.W400, (FontStyle) null, (FontSynthesis) null, sec, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, 0L, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16777179, (DefaultConstructorMarker) null);
    }

    public static final TextStyle getSecSemiBold() {
        FontFamily.Companion companion = FontFamily.Companion;
        FontListFontFamily sec = getSec();
        FontWeight.Companion.getClass();
        return new TextStyle(0L, 0L, FontWeight.W600, (FontStyle) null, (FontSynthesis) null, sec, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, 0L, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16777179, (DefaultConstructorMarker) null);
    }
}
