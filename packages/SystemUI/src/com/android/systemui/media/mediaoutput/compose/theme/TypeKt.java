package com.android.systemui.media.mediaoutput.compose.theme;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.PlatformTextStyle;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.DeviceFontFamilyName;
import androidx.compose.ui.text.font.DeviceFontFamilyNameFontKt;
import androidx.compose.ui.text.font.FontFamily;
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
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class TypeKt {
    public static final TextStyle MediaCardContentArtist(Composer composer) {
        long Color;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-996128775);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        TextStyle.Companion companion = TextStyle.Companion;
        TextStyle secRegular = getSecRegular();
        Color = androidx.compose.ui.graphics.ColorKt.Color(Color.m391getRedimpl(r3), Color.m390getGreenimpl(r3), Color.m388getBlueimpl(r3), 0.7f, Color.m389getColorSpaceimpl(ColorKt.mediaPrimaryColor(composerImpl)));
        TextUnitType.Companion.getClass();
        TextStyle m651copyp1EtxEg$default = TextStyle.m651copyp1EtxEg$default(secRegular, Color, TextUnitKt.pack(13.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 16777212);
        composerImpl.end(false);
        return m651copyp1EtxEg$default;
    }

    public static final TextStyle MediaCardContentTitle(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(472233294);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        TextStyle.Companion companion = TextStyle.Companion;
        TextStyle secSemiBold = getSecSemiBold();
        long mediaPrimaryColor = ColorKt.mediaPrimaryColor(composerImpl);
        TextUnitType.Companion.getClass();
        TextStyle m651copyp1EtxEg$default = TextStyle.m651copyp1EtxEg$default(secSemiBold, mediaPrimaryColor, TextUnitKt.pack(14.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 16777212);
        composerImpl.end(false);
        return m651copyp1EtxEg$default;
    }

    public static final TextStyle MediaCardProgressTime(Composer composer) {
        long Color;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1953791629);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        TextStyle.Companion companion = TextStyle.Companion;
        TextStyle secRegular = getSecRegular();
        Color = androidx.compose.ui.graphics.ColorKt.Color(Color.m391getRedimpl(r3), Color.m390getGreenimpl(r3), Color.m388getBlueimpl(r3), 0.8f, Color.m389getColorSpaceimpl(androidx.compose.ui.graphics.ColorKt.Color(16448255)));
        TextUnitType.Companion.getClass();
        TextStyle m651copyp1EtxEg$default = TextStyle.m651copyp1EtxEg$default(secRegular, Color, TextUnitKt.pack(9.0f, TextUnitType.Sp), null, null, 0L, 0, 0L, null, null, 16777212);
        composerImpl.end(false);
        return m651copyp1EtxEg$default;
    }

    public static final FontListFontFamily getSec() {
        DeviceFontFamilyName.m656constructorimpl("sec");
        return new FontListFontFamily(Arrays.asList(DeviceFontFamilyNameFontKt.m658Fontvxs03AY$default("sec", null, 14)));
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
