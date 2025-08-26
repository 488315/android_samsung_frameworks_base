package androidx.compose.material3;

import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.material3.tokens.FilledCardTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;

/* loaded from: classes.dex */
public final class CardDefaults {
    public static final CardDefaults INSTANCE = new CardDefaults();

    private CardDefaults() {
    }

    /* renamed from: cardColors-ro_MJ88, reason: not valid java name */
    public static CardColors m255cardColorsro_MJ88(long j, long j2, Composer composer, int i) {
        Composer composer2;
        long jM259contentColorForek8zF_U;
        long j3 = j;
        if ((i & 2) != 0) {
            composer2 = composer;
            jM259contentColorForek8zF_U = ColorSchemeKt.m259contentColorForek8zF_U(j3, composer2);
        } else {
            composer2 = composer;
            jM259contentColorForek8zF_U = j2;
        }
        Color.Companion.getClass();
        long j4 = Color.Unspecified;
        long jColor = ColorKt.Color(Color.m463getRedimpl(jM259contentColorForek8zF_U), Color.m462getGreenimpl(jM259contentColorForek8zF_U), Color.m460getBlueimpl(jM259contentColorForek8zF_U), 0.38f, Color.m461getColorSpaceimpl(jM259contentColorForek8zF_U));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.CardDefaults.cardColors (Card.kt:496)");
        }
        MaterialTheme.INSTANCE.getClass();
        CardColors defaultCardColors$material3_release = getDefaultCardColors$material3_release(MaterialTheme.getColorScheme(composer2));
        if (j3 == 16) {
            j3 = defaultCardColors$material3_release.containerColor;
        }
        long j5 = j3;
        if (jM259contentColorForek8zF_U == 16) {
            jM259contentColorForek8zF_U = defaultCardColors$material3_release.contentColor;
        }
        long j6 = jM259contentColorForek8zF_U;
        if (j4 == 16) {
            j4 = defaultCardColors$material3_release.disabledContainerColor;
        }
        long j7 = j4;
        if (jColor == 16) {
            jColor = defaultCardColors$material3_release.disabledContentColor;
        }
        CardColors cardColors = new CardColors(j5, j6, j7, jColor, null);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return cardColors;
    }

    public static CardColors getDefaultCardColors$material3_release(ColorScheme colorScheme) {
        CardColors cardColors = colorScheme.defaultCardColorsCached;
        if (cardColors != null) {
            return cardColors;
        }
        FilledCardTokens.INSTANCE.getClass();
        ColorSchemeKeyTokens colorSchemeKeyTokens = FilledCardTokens.ContainerColor;
        long jFromToken = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens);
        long jM258contentColorFor4WTKRHQ = ColorSchemeKt.m258contentColorFor4WTKRHQ(colorScheme, ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens));
        long jFromToken2 = ColorSchemeKt.fromToken(colorScheme, FilledCardTokens.DisabledContainerColor);
        long jM466compositeOverOWjLjI = ColorKt.m466compositeOverOWjLjI(ColorKt.Color(Color.m463getRedimpl(jFromToken2), Color.m462getGreenimpl(jFromToken2), Color.m460getBlueimpl(jFromToken2), FilledCardTokens.DisabledContainerOpacity, Color.m461getColorSpaceimpl(jFromToken2)), ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens));
        long jM258contentColorFor4WTKRHQ2 = ColorSchemeKt.m258contentColorFor4WTKRHQ(colorScheme, ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens));
        CardColors cardColors2 = new CardColors(jFromToken, jM258contentColorFor4WTKRHQ, jM466compositeOverOWjLjI, ColorKt.Color(Color.m463getRedimpl(jM258contentColorFor4WTKRHQ2), Color.m462getGreenimpl(jM258contentColorFor4WTKRHQ2), Color.m460getBlueimpl(jM258contentColorFor4WTKRHQ2), 0.38f, Color.m461getColorSpaceimpl(jM258contentColorFor4WTKRHQ2)), null);
        colorScheme.defaultCardColorsCached = cardColors2;
        return cardColors2;
    }
}
