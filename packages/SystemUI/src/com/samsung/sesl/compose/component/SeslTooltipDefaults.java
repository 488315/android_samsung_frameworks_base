package com.samsung.sesl.compose.component;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.PlatformTextStyle;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import com.samsung.sesl.compose.foundation.shape.RoundedCornerShapeKt;
import com.samsung.sesl.compose.foundation.shape.SeslRoundedCornerShape;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslTooltipDefaults {
    public static final SeslTooltipDefaults$$ExternalSyntheticLambda0 NoneCalculatePositionDelta;
    public static final float elevation;
    public static final long elevationColor;
    public static final float internalPaddingHorizontal;
    public static final float internalPaddingVertical;
    public static final float offset;
    public static final float popupAreaBottomInsets;
    public static final float popupAreaLeftInsets;
    public static final float popupAreaRightInsets;
    public static final float popupAreaTopInsets;
    public static final TextStyle textStyle;
    public static final SeslTooltipDefaults INSTANCE = new SeslTooltipDefaults();
    public static final SeslRoundedCornerShape shape = RoundedCornerShapeKt.SeslCircleShape;

    static {
        Color.Companion.getClass();
        long j = Color.Black;
        elevationColor = ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.15f, Color.m461getColorSpaceimpl(j));
        Dp.Companion companion = Dp.Companion;
        elevation = 8;
        float f = 10;
        internalPaddingVertical = f;
        internalPaddingHorizontal = 18;
        popupAreaTopInsets = 0;
        popupAreaLeftInsets = f;
        popupAreaRightInsets = f;
        popupAreaBottomInsets = 50;
        offset = 6;
        long sp = TextUnitKt.getSp(16);
        TextAlign.Companion.getClass();
        textStyle = new TextStyle(0L, sp, new FontWeight(400), (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, TextAlign.Center, 0, 0L, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16744441, (DefaultConstructorMarker) null);
        NoneCalculatePositionDelta = new SeslTooltipDefaults$$ExternalSyntheticLambda0();
    }

    private SeslTooltipDefaults() {
    }
}
