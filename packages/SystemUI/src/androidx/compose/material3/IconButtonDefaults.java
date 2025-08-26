package androidx.compose.material3;

import androidx.compose.material3.tokens.FilledIconButtonTokens;
import androidx.compose.material3.tokens.LargeIconButtonTokens;
import androidx.compose.material3.tokens.MediumIconButtonTokens;
import androidx.compose.material3.tokens.SmallIconButtonTokens;
import androidx.compose.material3.tokens.StandardIconButtonTokens;
import androidx.compose.material3.tokens.XLargeIconButtonTokens;
import androidx.compose.material3.tokens.XSmallIconButtonTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class IconButtonDefaults {
    public static final IconButtonDefaults INSTANCE = new IconButtonDefaults();

    public final class IconButtonWidthOption {
        public static final Companion Companion = new Companion(null);
        public static final int Uniform = 1;
        public static final int Wide = 2;
        public final int value;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public final boolean equals(Object obj) {
            if (obj instanceof IconButtonWidthOption) {
                return this.value == ((IconButtonWidthOption) obj).value;
            }
            return false;
        }

        public final int hashCode() {
            return Integer.hashCode(this.value);
        }

        public final String toString() {
            int i = this.value;
            return i == 0 ? "Narrow" : i == Uniform ? "Uniform" : i == Wide ? "Wide" : C2paManifestList.UNKNOWN_VALUE;
        }
    }

    static {
        XSmallIconButtonTokens.INSTANCE.getClass();
        SmallIconButtonTokens.INSTANCE.getClass();
        MediumIconButtonTokens.INSTANCE.getClass();
        LargeIconButtonTokens.INSTANCE.getClass();
        XLargeIconButtonTokens.INSTANCE.getClass();
    }

    private IconButtonDefaults() {
    }

    /* renamed from: defaultIconButtonColors-4WTKRHQ$material3_release, reason: not valid java name */
    public static IconButtonColors m266defaultIconButtonColors4WTKRHQ$material3_release(ColorScheme colorScheme, long j) {
        IconButtonColors iconButtonColors = colorScheme.defaultIconButtonColorsCached;
        if (iconButtonColors != null) {
            return iconButtonColors;
        }
        Color.Companion.getClass();
        long j2 = Color.Transparent;
        StandardIconButtonTokens.INSTANCE.getClass();
        IconButtonColors iconButtonColors2 = new IconButtonColors(j2, j, j2, ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), StandardIconButtonTokens.DisabledOpacity, Color.m461getColorSpaceimpl(j)), null);
        colorScheme.defaultIconButtonColorsCached = iconButtonColors2;
        return iconButtonColors2;
    }

    public static IconButtonColors getDefaultFilledIconButtonColors$material3_release(ColorScheme colorScheme) {
        IconButtonColors iconButtonColors = colorScheme.defaultFilledIconButtonColorsCached;
        if (iconButtonColors != null) {
            return iconButtonColors;
        }
        FilledIconButtonTokens.INSTANCE.getClass();
        long jFromToken = ColorSchemeKt.fromToken(colorScheme, FilledIconButtonTokens.ContainerColor);
        long jFromToken2 = ColorSchemeKt.fromToken(colorScheme, FilledIconButtonTokens.Color);
        long jFromToken3 = ColorSchemeKt.fromToken(colorScheme, FilledIconButtonTokens.DisabledContainerColor);
        long jColor = ColorKt.Color(Color.m463getRedimpl(jFromToken3), Color.m462getGreenimpl(jFromToken3), Color.m460getBlueimpl(jFromToken3), FilledIconButtonTokens.DisabledContainerOpacity, Color.m461getColorSpaceimpl(jFromToken3));
        long jFromToken4 = ColorSchemeKt.fromToken(colorScheme, FilledIconButtonTokens.DisabledColor);
        IconButtonColors iconButtonColors2 = new IconButtonColors(jFromToken, jFromToken2, jColor, ColorKt.Color(Color.m463getRedimpl(jFromToken4), Color.m462getGreenimpl(jFromToken4), Color.m460getBlueimpl(jFromToken4), FilledIconButtonTokens.DisabledOpacity, Color.m461getColorSpaceimpl(jFromToken4)), null);
        colorScheme.defaultFilledIconButtonColorsCached = iconButtonColors2;
        return iconButtonColors2;
    }

    public static IconButtonColors iconButtonColors(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.IconButtonDefaults.iconButtonColors (IconButtonDefaults.kt:48)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        long j = ((Color) composerImpl.consume(ContentColorKt.LocalContentColor)).value;
        MaterialTheme.INSTANCE.getClass();
        IconButtonColors iconButtonColorsM266defaultIconButtonColors4WTKRHQ$material3_release = m266defaultIconButtonColors4WTKRHQ$material3_release(MaterialTheme.getColorScheme(composerImpl), j);
        if (!ULong.m3447equalsimpl0(iconButtonColorsM266defaultIconButtonColors4WTKRHQ$material3_release.contentColor, j)) {
            StandardIconButtonTokens.INSTANCE.getClass();
            iconButtonColorsM266defaultIconButtonColors4WTKRHQ$material3_release = iconButtonColorsM266defaultIconButtonColors4WTKRHQ$material3_release.m265copyjRlVdoo(iconButtonColorsM266defaultIconButtonColors4WTKRHQ$material3_release.containerColor, j, iconButtonColorsM266defaultIconButtonColors4WTKRHQ$material3_release.disabledContainerColor, ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), StandardIconButtonTokens.DisabledOpacity, Color.m461getColorSpaceimpl(j)));
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return iconButtonColorsM266defaultIconButtonColors4WTKRHQ$material3_release;
    }

    /* renamed from: iconButtonColors-ro_MJ88, reason: not valid java name */
    public static IconButtonColors m267iconButtonColorsro_MJ88(long j, long j2, Composer composer, int i) {
        if ((i & 1) != 0) {
            Color.Companion.getClass();
            j = Color.Unspecified;
        }
        long j3 = j;
        Color.Companion.getClass();
        long j4 = Color.Unspecified;
        StandardIconButtonTokens.INSTANCE.getClass();
        long jColor = ColorKt.Color(Color.m463getRedimpl(j2), Color.m462getGreenimpl(j2), Color.m460getBlueimpl(j2), StandardIconButtonTokens.DisabledOpacity, Color.m461getColorSpaceimpl(j2));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.IconButtonDefaults.iconButtonColors (IconButtonDefaults.kt:84)");
        }
        MaterialTheme.INSTANCE.getClass();
        IconButtonColors iconButtonColorsM265copyjRlVdoo = m266defaultIconButtonColors4WTKRHQ$material3_release(MaterialTheme.getColorScheme(composer), ((Color) ((ComposerImpl) composer).consume(ContentColorKt.LocalContentColor)).value).m265copyjRlVdoo(j3, j2, j4, jColor);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return iconButtonColorsM265copyjRlVdoo;
    }

    /* renamed from: smallContainerSize-N-wlBFI$default, reason: not valid java name */
    public static long m268smallContainerSizeNwlBFI$default(IconButtonDefaults iconButtonDefaults) {
        float f;
        IconButtonWidthOption.Companion companion = IconButtonWidthOption.Companion;
        companion.getClass();
        int i = IconButtonWidthOption.Uniform;
        iconButtonDefaults.getClass();
        companion.getClass();
        if (i == 0) {
            SmallIconButtonTokens.INSTANCE.getClass();
            f = SmallIconButtonTokens.NarrowLeadingSpace + SmallIconButtonTokens.NarrowTrailingSpace;
            Dp.Companion companion2 = Dp.Companion;
        } else {
            SmallIconButtonTokens.INSTANCE.getClass();
            float f2 = SmallIconButtonTokens.DefaultLeadingSpace;
            f = f2 + f2;
            Dp.Companion companion3 = Dp.Companion;
        }
        SmallIconButtonTokens.INSTANCE.getClass();
        return DpKt.m840DpSizeYgX7TsA(SmallIconButtonTokens.IconSize + f, SmallIconButtonTokens.ContainerHeight);
    }
}
