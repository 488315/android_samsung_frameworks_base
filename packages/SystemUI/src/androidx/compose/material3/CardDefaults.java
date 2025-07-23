package androidx.compose.material3;

import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.material3.tokens.FilledCardTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CardDefaults {
    public static final CardDefaults INSTANCE = new CardDefaults();

    private CardDefaults() {
    }

    /* renamed from: cardColors-ro_MJ88, reason: not valid java name */
    public static CardColors m254cardColorsro_MJ88(long j, long j2, Composer composer, int i) {
        Composer composer2;
        long j3;
        long Color;
        long j4 = j;
        if ((i & 2) != 0) {
            composer2 = composer;
            j3 = ColorSchemeKt.m258contentColorForek8zF_U(j4, composer2);
        } else {
            composer2 = composer;
            j3 = j2;
        }
        Color.Companion.getClass();
        long j5 = Color.Unspecified;
        Color = ColorKt.Color(Color.m461getRedimpl(j3), Color.m460getGreenimpl(j3), Color.m458getBlueimpl(j3), 0.38f, Color.m459getColorSpaceimpl(j3));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.CardDefaults.cardColors (Card.kt:496)");
        }
        MaterialTheme.INSTANCE.getClass();
        CardColors defaultCardColors$material3_release = getDefaultCardColors$material3_release(MaterialTheme.getColorScheme(composer2));
        if (j4 == 16) {
            j4 = defaultCardColors$material3_release.containerColor;
        }
        long j6 = j4;
        if (j3 == 16) {
            j3 = defaultCardColors$material3_release.contentColor;
        }
        long j7 = j3;
        if (j5 == 16) {
            j5 = defaultCardColors$material3_release.disabledContainerColor;
        }
        long j8 = j5;
        if (Color == 16) {
            Color = defaultCardColors$material3_release.disabledContentColor;
        }
        CardColors cardColors = new CardColors(j6, j7, j8, Color, null);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return cardColors;
    }

    public static CardColors getDefaultCardColors$material3_release(ColorScheme colorScheme) {
        long Color;
        long Color2;
        CardColors cardColors = colorScheme.defaultCardColorsCached;
        if (cardColors != null) {
            return cardColors;
        }
        FilledCardTokens.INSTANCE.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens = FilledCardTokens.ContainerColor;
        long fromToken = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens);
        long m257contentColorFor4WTKRHQ = ColorSchemeKt.m257contentColorFor4WTKRHQ(colorScheme, ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens));
        Color = ColorKt.Color(Color.m461getRedimpl(r6), Color.m460getGreenimpl(r6), Color.m458getBlueimpl(r6), FilledCardTokens.DisabledContainerOpacity, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, FilledCardTokens.DisabledContainerColor)));
        long m464compositeOverOWjLjI = ColorKt.m464compositeOverOWjLjI(Color, ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens));
        Color2 = ColorKt.Color(Color.m461getRedimpl(r8), Color.m460getGreenimpl(r8), Color.m458getBlueimpl(r8), 0.38f, Color.m459getColorSpaceimpl(ColorSchemeKt.m257contentColorFor4WTKRHQ(colorScheme, ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens))));
        CardColors cardColors2 = new CardColors(fromToken, m257contentColorFor4WTKRHQ, m464compositeOverOWjLjI, Color2, null);
        colorScheme.defaultCardColorsCached = cardColors2;
        return cardColors2;
    }
}
