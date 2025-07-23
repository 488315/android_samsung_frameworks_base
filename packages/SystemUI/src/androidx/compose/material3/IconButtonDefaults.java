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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class IconButtonDefaults {
    public static final IconButtonDefaults INSTANCE = new IconButtonDefaults();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class IconButtonWidthOption {
        public static final Companion Companion = new Companion(null);
        public static final int Uniform = 1;
        public static final int Wide = 2;
        public final int value;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public static IconButtonColors m265defaultIconButtonColors4WTKRHQ$material3_release(ColorScheme colorScheme, long j) {
        long Color;
        IconButtonColors iconButtonColors = colorScheme.defaultIconButtonColorsCached;
        if (iconButtonColors != null) {
            return iconButtonColors;
        }
        Color.Companion.getClass();
        long j2 = Color.Transparent;
        StandardIconButtonTokens.INSTANCE.getClass();
        Color = ColorKt.Color(Color.m461getRedimpl(j), Color.m460getGreenimpl(j), Color.m458getBlueimpl(j), StandardIconButtonTokens.DisabledOpacity, Color.m459getColorSpaceimpl(j));
        IconButtonColors iconButtonColors2 = new IconButtonColors(j2, j, j2, Color, null);
        colorScheme.defaultIconButtonColorsCached = iconButtonColors2;
        return iconButtonColors2;
    }

    public static IconButtonColors getDefaultFilledIconButtonColors$material3_release(ColorScheme colorScheme) {
        long Color;
        long Color2;
        IconButtonColors iconButtonColors = colorScheme.defaultFilledIconButtonColorsCached;
        if (iconButtonColors != null) {
            return iconButtonColors;
        }
        FilledIconButtonTokens.INSTANCE.getClass();
        long fromToken = ColorSchemeKt.fromToken(colorScheme, FilledIconButtonTokens.ContainerColor);
        long fromToken2 = ColorSchemeKt.fromToken(colorScheme, FilledIconButtonTokens.Color);
        Color = ColorKt.Color(Color.m461getRedimpl(r6), Color.m460getGreenimpl(r6), Color.m458getBlueimpl(r6), FilledIconButtonTokens.DisabledContainerOpacity, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, FilledIconButtonTokens.DisabledContainerColor)));
        Color2 = ColorKt.Color(Color.m461getRedimpl(r8), Color.m460getGreenimpl(r8), Color.m458getBlueimpl(r8), FilledIconButtonTokens.DisabledOpacity, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, FilledIconButtonTokens.DisabledColor)));
        IconButtonColors iconButtonColors2 = new IconButtonColors(fromToken, fromToken2, Color, Color2, null);
        colorScheme.defaultFilledIconButtonColorsCached = iconButtonColors2;
        return iconButtonColors2;
    }

    public static IconButtonColors iconButtonColors(Composer composer) {
        long Color;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.IconButtonDefaults.iconButtonColors (IconButtonDefaults.kt:48)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        long j = ((Color) composerImpl.consume(ContentColorKt.LocalContentColor)).value;
        MaterialTheme.INSTANCE.getClass();
        IconButtonColors m265defaultIconButtonColors4WTKRHQ$material3_release = m265defaultIconButtonColors4WTKRHQ$material3_release(MaterialTheme.getColorScheme(composerImpl), j);
        if (!ULong.m3427equalsimpl0(m265defaultIconButtonColors4WTKRHQ$material3_release.contentColor, j)) {
            StandardIconButtonTokens.INSTANCE.getClass();
            Color = ColorKt.Color(Color.m461getRedimpl(j), Color.m460getGreenimpl(j), Color.m458getBlueimpl(j), StandardIconButtonTokens.DisabledOpacity, Color.m459getColorSpaceimpl(j));
            m265defaultIconButtonColors4WTKRHQ$material3_release = m265defaultIconButtonColors4WTKRHQ$material3_release.m264copyjRlVdoo(m265defaultIconButtonColors4WTKRHQ$material3_release.containerColor, j, m265defaultIconButtonColors4WTKRHQ$material3_release.disabledContainerColor, Color);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return m265defaultIconButtonColors4WTKRHQ$material3_release;
    }

    /* renamed from: iconButtonColors-ro_MJ88, reason: not valid java name */
    public static IconButtonColors m266iconButtonColorsro_MJ88(long j, long j2, Composer composer, int i) {
        long Color;
        if ((i & 1) != 0) {
            Color.Companion.getClass();
            j = Color.Unspecified;
        }
        long j3 = j;
        Color.Companion.getClass();
        long j4 = Color.Unspecified;
        StandardIconButtonTokens.INSTANCE.getClass();
        Color = ColorKt.Color(Color.m461getRedimpl(j2), Color.m460getGreenimpl(j2), Color.m458getBlueimpl(j2), StandardIconButtonTokens.DisabledOpacity, Color.m459getColorSpaceimpl(j2));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.IconButtonDefaults.iconButtonColors (IconButtonDefaults.kt:84)");
        }
        MaterialTheme.INSTANCE.getClass();
        IconButtonColors m264copyjRlVdoo = m265defaultIconButtonColors4WTKRHQ$material3_release(MaterialTheme.getColorScheme(composer), ((Color) ((ComposerImpl) composer).consume(ContentColorKt.LocalContentColor)).value).m264copyjRlVdoo(j3, j2, j4, Color);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return m264copyjRlVdoo;
    }

    /* renamed from: smallContainerSize-N-wlBFI$default, reason: not valid java name */
    public static long m267smallContainerSizeNwlBFI$default(IconButtonDefaults iconButtonDefaults) {
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
        return DpKt.m838DpSizeYgX7TsA(SmallIconButtonTokens.IconSize + f, SmallIconButtonTokens.ContainerHeight);
    }
}
