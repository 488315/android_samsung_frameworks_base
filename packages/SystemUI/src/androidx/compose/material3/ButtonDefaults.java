package androidx.compose.material3;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.material3.tokens.BaselineButtonTokens;
import androidx.compose.material3.tokens.ButtonLargeTokens;
import androidx.compose.material3.tokens.ButtonMediumTokens;
import androidx.compose.material3.tokens.ButtonSmallTokens;
import androidx.compose.material3.tokens.ButtonXLargeTokens;
import androidx.compose.material3.tokens.ButtonXSmallTokens;
import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.material3.tokens.FilledButtonTokens;
import androidx.compose.material3.tokens.OutlinedButtonTokens;
import androidx.compose.material3.tokens.TextButtonTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public final class ButtonDefaults {
    public static final PaddingValuesImpl ContentPadding;
    public static final ButtonDefaults INSTANCE = new ButtonDefaults();
    public static final float IconSpacing;
    public static final float MinHeight;
    public static final float MinWidth;
    public static final PaddingValuesImpl TextButtonContentPadding;

    static {
        BaselineButtonTokens.INSTANCE.getClass();
        float f = BaselineButtonTokens.LeadingSpace;
        float f2 = BaselineButtonTokens.TrailingSpace;
        float f3 = 16;
        Dp.Companion companion = Dp.Companion;
        ButtonSmallTokens.INSTANCE.getClass();
        float f4 = 8;
        PaddingValuesImpl paddingValuesImplM123PaddingValuesa9UjIt4 = PaddingKt.m123PaddingValuesa9UjIt4(f, f4, f2, f4);
        ContentPadding = paddingValuesImplM123PaddingValuesa9UjIt4;
        PaddingKt.m123PaddingValuesa9UjIt4(f3, f4, f2, f4);
        float f5 = 12;
        float f6 = paddingValuesImplM123PaddingValuesa9UjIt4.top;
        float f7 = paddingValuesImplM123PaddingValuesa9UjIt4.bottom;
        TextButtonContentPadding = PaddingKt.m123PaddingValuesa9UjIt4(f5, f6, f5, f7);
        PaddingKt.m123PaddingValuesa9UjIt4(f5, f6, f3, f7);
        MinWidth = 58;
        MinHeight = ButtonSmallTokens.ContainerHeight;
        ButtonXSmallTokens.INSTANCE.getClass();
        ButtonMediumTokens.INSTANCE.getClass();
        ButtonLargeTokens.INSTANCE.getClass();
        ButtonXLargeTokens.INSTANCE.getClass();
        IconSpacing = ButtonSmallTokens.IconLabelSpace;
    }

    private ButtonDefaults() {
    }

    public static ButtonColors buttonColors(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.ButtonDefaults.buttonColors (Button.kt:1216)");
        }
        MaterialTheme.INSTANCE.getClass();
        ButtonColors defaultButtonColors$material3_release = getDefaultButtonColors$material3_release(MaterialTheme.getColorScheme(composer));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return defaultButtonColors$material3_release;
    }

    /* renamed from: buttonColors-ro_MJ88, reason: not valid java name */
    public static ButtonColors m252buttonColorsro_MJ88(long j, long j2, Composer composer, int i) {
        if ((i & 1) != 0) {
            Color.Companion.getClass();
            j = Color.Unspecified;
        }
        long j3 = j;
        if ((i & 2) != 0) {
            Color.Companion.getClass();
            j2 = Color.Unspecified;
        }
        long j4 = j2;
        Color.Companion companion = Color.Companion;
        companion.getClass();
        long j5 = Color.Unspecified;
        companion.getClass();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.ButtonDefaults.buttonColors (Button.kt:1234)");
        }
        MaterialTheme.INSTANCE.getClass();
        ButtonColors buttonColorsM251copyjRlVdoo = getDefaultButtonColors$material3_release(MaterialTheme.getColorScheme(composer)).m251copyjRlVdoo(j3, j4, j5, j5);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return buttonColorsM251copyjRlVdoo;
    }

    public static ButtonColors getDefaultButtonColors$material3_release(ColorScheme colorScheme) {
        ButtonColors buttonColors = colorScheme.defaultButtonColorsCached;
        if (buttonColors != null) {
            return buttonColors;
        }
        FilledButtonTokens.INSTANCE.getClass();
        long jFromToken = ColorSchemeKt.fromToken(colorScheme, FilledButtonTokens.ContainerColor);
        long jFromToken2 = ColorSchemeKt.fromToken(colorScheme, FilledButtonTokens.LabelTextColor);
        long jFromToken3 = ColorSchemeKt.fromToken(colorScheme, FilledButtonTokens.DisabledContainerColor);
        long jColor = ColorKt.Color(Color.m463getRedimpl(jFromToken3), Color.m462getGreenimpl(jFromToken3), Color.m460getBlueimpl(jFromToken3), FilledButtonTokens.DisabledContainerOpacity, Color.m461getColorSpaceimpl(jFromToken3));
        long jFromToken4 = ColorSchemeKt.fromToken(colorScheme, FilledButtonTokens.DisabledLabelTextColor);
        ButtonColors buttonColors2 = new ButtonColors(jFromToken, jFromToken2, jColor, ColorKt.Color(Color.m463getRedimpl(jFromToken4), Color.m462getGreenimpl(jFromToken4), Color.m460getBlueimpl(jFromToken4), FilledButtonTokens.DisabledLabelTextOpacity, Color.m461getColorSpaceimpl(jFromToken4)), null);
        colorScheme.defaultButtonColorsCached = buttonColors2;
        return buttonColors2;
    }

    public static ButtonColors getDefaultOutlinedButtonColors$material3_release(ColorScheme colorScheme) {
        ButtonColors buttonColors = colorScheme.defaultOutlinedButtonColorsCached;
        if (buttonColors != null) {
            return buttonColors;
        }
        Color.Companion.getClass();
        long j = Color.Transparent;
        OutlinedButtonTokens.INSTANCE.getClass();
        long jFromToken = ColorSchemeKt.fromToken(colorScheme, OutlinedButtonTokens.LabelTextColor);
        long jFromToken2 = ColorSchemeKt.fromToken(colorScheme, OutlinedButtonTokens.DisabledLabelTextColor);
        ButtonColors buttonColors2 = new ButtonColors(j, jFromToken, j, ColorKt.Color(Color.m463getRedimpl(jFromToken2), Color.m462getGreenimpl(jFromToken2), Color.m460getBlueimpl(jFromToken2), OutlinedButtonTokens.DisabledLabelTextOpacity, Color.m461getColorSpaceimpl(jFromToken2)), null);
        colorScheme.defaultOutlinedButtonColorsCached = buttonColors2;
        return buttonColors2;
    }

    public static ButtonColors getDefaultTextButtonColors$material3_release(ColorScheme colorScheme) {
        ButtonColors buttonColors = colorScheme.defaultTextButtonColorsCached;
        if (buttonColors != null) {
            return buttonColors;
        }
        Color.Companion.getClass();
        long j = Color.Transparent;
        long jFromToken = ColorSchemeKt.fromToken(colorScheme, ColorSchemeKeyTokens.Primary);
        TextButtonTokens.INSTANCE.getClass();
        long jFromToken2 = ColorSchemeKt.fromToken(colorScheme, TextButtonTokens.DisabledLabelColor);
        ButtonColors buttonColors2 = new ButtonColors(j, jFromToken, j, ColorKt.Color(Color.m463getRedimpl(jFromToken2), Color.m462getGreenimpl(jFromToken2), Color.m460getBlueimpl(jFromToken2), TextButtonTokens.DisabledLabelOpacity, Color.m461getColorSpaceimpl(jFromToken2)), null);
        colorScheme.defaultTextButtonColorsCached = buttonColors2;
        return buttonColors2;
    }

    public static Shape getShape(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.ButtonDefaults.<get-shape> (Button.kt:1176)");
        }
        ButtonSmallTokens.INSTANCE.getClass();
        Shape value = ShapesKt.getValue(ButtonSmallTokens.ContainerShapeRound, composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return value;
    }

    /* renamed from: outlinedButtonColors-ro_MJ88, reason: not valid java name */
    public static ButtonColors m253outlinedButtonColorsro_MJ88(long j, Composer composer) {
        Color.Companion companion = Color.Companion;
        companion.getClass();
        long j2 = Color.Unspecified;
        companion.getClass();
        companion.getClass();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.ButtonDefaults.outlinedButtonColors (Button.kt:1371)");
        }
        MaterialTheme.INSTANCE.getClass();
        ButtonColors buttonColorsM251copyjRlVdoo = getDefaultOutlinedButtonColors$material3_release(MaterialTheme.getColorScheme(composer)).m251copyjRlVdoo(j2, j, j2, j2);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return buttonColorsM251copyjRlVdoo;
    }

    /* renamed from: textButtonColors-ro_MJ88, reason: not valid java name */
    public static ButtonColors m254textButtonColorsro_MJ88(long j, long j2, Composer composer, int i) {
        if ((i & 1) != 0) {
            Color.Companion.getClass();
            j = Color.Unspecified;
        }
        long j3 = j;
        Color.Companion companion = Color.Companion;
        companion.getClass();
        long j4 = Color.Unspecified;
        companion.getClass();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.ButtonDefaults.textButtonColors (Button.kt:1414)");
        }
        MaterialTheme.INSTANCE.getClass();
        ButtonColors buttonColorsM251copyjRlVdoo = getDefaultTextButtonColors$material3_release(MaterialTheme.getColorScheme(composer)).m251copyjRlVdoo(j3, j2, j4, j4);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return buttonColorsM251copyjRlVdoo;
    }
}
