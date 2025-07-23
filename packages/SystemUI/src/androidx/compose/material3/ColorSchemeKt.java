package androidx.compose.material3;

import androidx.compose.material3.tokens.ColorLightTokens;
import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.ULong;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ColorSchemeKt {
    public static final StaticProvidableCompositionLocal LocalColorScheme = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.material3.ColorSchemeKt$LocalColorScheme$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ColorSchemeKt.m259lightColorSchemeCXl9yA$default(0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, -1, 15);
        }
    });
    public static final StaticProvidableCompositionLocal LocalTonalElevationEnabled = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.material3.ColorSchemeKt$LocalTonalElevationEnabled$1
        @Override // kotlin.jvm.functions.Function0
        public final /* bridge */ /* synthetic */ Object invoke() {
            return Boolean.TRUE;
        }
    });

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ColorSchemeKeyTokens.values().length];
            try {
                iArr[ColorSchemeKeyTokens.Background.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ColorSchemeKeyTokens.Error.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ColorSchemeKeyTokens.ErrorContainer.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ColorSchemeKeyTokens.InverseOnSurface.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ColorSchemeKeyTokens.InversePrimary.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ColorSchemeKeyTokens.InverseSurface.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnBackground.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnError.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnErrorContainer.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnPrimary.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnPrimaryContainer.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnSecondary.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnSecondaryContainer.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnSurface.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnSurfaceVariant.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceTint.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnTertiary.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OnTertiaryContainer.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[ColorSchemeKeyTokens.Outline.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[ColorSchemeKeyTokens.OutlineVariant.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[ColorSchemeKeyTokens.Primary.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr[ColorSchemeKeyTokens.PrimaryContainer.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr[ColorSchemeKeyTokens.Scrim.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr[ColorSchemeKeyTokens.Secondary.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SecondaryContainer.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr[ColorSchemeKeyTokens.Surface.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceVariant.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceBright.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceContainer.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceContainerHigh.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceContainerHighest.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceContainerLow.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceContainerLowest.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr[ColorSchemeKeyTokens.SurfaceDim.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                iArr[ColorSchemeKeyTokens.Tertiary.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr[ColorSchemeKeyTokens.TertiaryContainer.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: contentColorFor-4WTKRHQ, reason: not valid java name */
    public static final long m257contentColorFor4WTKRHQ(ColorScheme colorScheme, long j) {
        long j2 = colorScheme.primary;
        Color.Companion companion = Color.Companion;
        if (ULong.m3427equalsimpl0(j, j2)) {
            return colorScheme.onPrimary;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.secondary)) {
            return colorScheme.onSecondary;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.tertiary)) {
            return colorScheme.onTertiary;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.background)) {
            return colorScheme.onBackground;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.error)) {
            return colorScheme.onError;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.primaryContainer)) {
            return colorScheme.onPrimaryContainer;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.secondaryContainer)) {
            return colorScheme.onSecondaryContainer;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.tertiaryContainer)) {
            return colorScheme.onTertiaryContainer;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.errorContainer)) {
            return colorScheme.onErrorContainer;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.inverseSurface)) {
            return colorScheme.inverseOnSurface;
        }
        boolean m3427equalsimpl0 = ULong.m3427equalsimpl0(j, colorScheme.surface);
        long j3 = colorScheme.onSurface;
        if (m3427equalsimpl0) {
            return j3;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.surfaceVariant)) {
            return colorScheme.onSurfaceVariant;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.surfaceBright)) {
            return j3;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.surfaceContainer)) {
            return j3;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.surfaceContainerHigh)) {
            return j3;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.surfaceContainerHighest)) {
            return j3;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.surfaceContainerLow)) {
            return j3;
        }
        if (ULong.m3427equalsimpl0(j, colorScheme.surfaceContainerLowest)) {
            return j3;
        }
        Color.Companion.getClass();
        return Color.Unspecified;
    }

    /* renamed from: contentColorFor-ek8zF_U, reason: not valid java name */
    public static final long m258contentColorForek8zF_U(long j, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.contentColorFor (ColorScheme.kt:890)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1680907984);
        MaterialTheme.INSTANCE.getClass();
        long m257contentColorFor4WTKRHQ = m257contentColorFor4WTKRHQ(MaterialTheme.getColorScheme(composerImpl), j);
        if (m257contentColorFor4WTKRHQ == 16) {
            m257contentColorFor4WTKRHQ = ((Color) composerImpl.consume(ContentColorKt.LocalContentColor)).value;
        }
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return m257contentColorFor4WTKRHQ;
    }

    public static final long fromToken(ColorScheme colorScheme, ColorSchemeKeyTokens colorSchemeKeyTokens) {
        switch (WhenMappings.$EnumSwitchMapping$0[colorSchemeKeyTokens.ordinal()]) {
            case 1:
                return colorScheme.background;
            case 2:
                return colorScheme.error;
            case 3:
                return colorScheme.errorContainer;
            case 4:
                return colorScheme.inverseOnSurface;
            case 5:
                return colorScheme.inversePrimary;
            case 6:
                return colorScheme.inverseSurface;
            case 7:
                return colorScheme.onBackground;
            case 8:
                return colorScheme.onError;
            case 9:
                return colorScheme.onErrorContainer;
            case 10:
                return colorScheme.onPrimary;
            case 11:
                return colorScheme.onPrimaryContainer;
            case 12:
                return colorScheme.onSecondary;
            case 13:
                return colorScheme.onSecondaryContainer;
            case 14:
                return colorScheme.onSurface;
            case 15:
                return colorScheme.onSurfaceVariant;
            case 16:
                return colorScheme.surfaceTint;
            case 17:
                return colorScheme.onTertiary;
            case 18:
                return colorScheme.onTertiaryContainer;
            case 19:
                return colorScheme.outline;
            case 20:
                return colorScheme.outlineVariant;
            case 21:
                return colorScheme.primary;
            case 22:
                return colorScheme.primaryContainer;
            case 23:
                return colorScheme.scrim;
            case 24:
                return colorScheme.secondary;
            case 25:
                return colorScheme.secondaryContainer;
            case 26:
                return colorScheme.surface;
            case 27:
                return colorScheme.surfaceVariant;
            case 28:
                return colorScheme.surfaceBright;
            case 29:
                return colorScheme.surfaceContainer;
            case 30:
                return colorScheme.surfaceContainerHigh;
            case 31:
                return colorScheme.surfaceContainerHighest;
            case 32:
                return colorScheme.surfaceContainerLow;
            case 33:
                return colorScheme.surfaceContainerLowest;
            case 34:
                return colorScheme.surfaceDim;
            case 35:
                return colorScheme.tertiary;
            case 36:
                return colorScheme.tertiaryContainer;
            default:
                Color.Companion.getClass();
                return Color.Unspecified;
        }
    }

    public static final long getValue(ColorSchemeKeyTokens colorSchemeKeyTokens, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.<get-value> (ColorScheme.kt:1025)");
        }
        MaterialTheme.INSTANCE.getClass();
        long fromToken = fromToken(MaterialTheme.getColorScheme(composer), colorSchemeKeyTokens);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return fromToken;
    }

    /* renamed from: lightColorScheme-C-Xl9yA$default, reason: not valid java name */
    public static ColorScheme m259lightColorSchemeCXl9yA$default(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, long j30, long j31, long j32, int i, int i2) {
        long j33;
        long j34;
        long j35;
        long j36;
        long j37;
        long j38;
        long j39;
        long j40;
        long j41;
        long j42;
        long j43;
        long j44;
        long j45;
        long j46;
        long j47;
        long j48;
        long j49;
        long j50;
        long j51;
        long j52;
        long j53;
        long j54;
        long j55;
        long j56;
        long j57;
        long j58;
        long j59;
        long j60;
        long j61;
        long j62;
        long j63;
        if ((i & 1) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j33 = ColorLightTokens.Primary;
        } else {
            j33 = j;
        }
        if ((i & 2) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j34 = ColorLightTokens.OnPrimary;
        } else {
            j34 = j2;
        }
        if ((i & 4) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j35 = ColorLightTokens.PrimaryContainer;
        } else {
            j35 = j3;
        }
        if ((i & 8) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j36 = ColorLightTokens.OnPrimaryContainer;
        } else {
            j36 = j4;
        }
        if ((i & 16) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j37 = ColorLightTokens.InversePrimary;
        } else {
            j37 = j5;
        }
        if ((i & 32) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j38 = ColorLightTokens.Secondary;
        } else {
            j38 = j6;
        }
        if ((i & 64) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j39 = ColorLightTokens.OnSecondary;
        } else {
            j39 = j7;
        }
        if ((i & 128) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j40 = ColorLightTokens.SecondaryContainer;
        } else {
            j40 = j8;
        }
        if ((i & 256) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j41 = ColorLightTokens.OnSecondaryContainer;
        } else {
            j41 = j9;
        }
        if ((i & 512) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j42 = ColorLightTokens.Tertiary;
        } else {
            j42 = j10;
        }
        if ((i & 1024) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j43 = ColorLightTokens.OnTertiary;
        } else {
            j43 = j11;
        }
        if ((i & 2048) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j44 = ColorLightTokens.TertiaryContainer;
        } else {
            j44 = j12;
        }
        if ((i & 4096) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j45 = ColorLightTokens.OnTertiaryContainer;
        } else {
            j45 = j13;
        }
        if ((i & 8192) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j46 = ColorLightTokens.Background;
        } else {
            j46 = j14;
        }
        if ((i & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j47 = ColorLightTokens.OnBackground;
        } else {
            j47 = j15;
        }
        if ((32768 & i) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j48 = ColorLightTokens.Surface;
        } else {
            j48 = j16;
        }
        if ((65536 & i) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j49 = ColorLightTokens.OnSurface;
        } else {
            j49 = j17;
        }
        if ((131072 & i) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j50 = ColorLightTokens.SurfaceVariant;
        } else {
            j50 = j18;
        }
        if ((262144 & i) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j51 = ColorLightTokens.OnSurfaceVariant;
        } else {
            j51 = j19;
        }
        long j64 = (524288 & i) != 0 ? j33 : j20;
        if ((1048576 & i) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j52 = ColorLightTokens.InverseSurface;
        } else {
            j52 = j21;
        }
        if ((2097152 & i) != 0) {
            ColorLightTokens.INSTANCE.getClass();
            j53 = ColorLightTokens.InverseOnSurface;
        } else {
            j53 = j22;
        }
        ColorLightTokens colorLightTokens = ColorLightTokens.INSTANCE;
        colorLightTokens.getClass();
        long j65 = ColorLightTokens.Error;
        colorLightTokens.getClass();
        long j66 = ColorLightTokens.OnError;
        colorLightTokens.getClass();
        long j67 = ColorLightTokens.ErrorContainer;
        colorLightTokens.getClass();
        long j68 = ColorLightTokens.OnErrorContainer;
        if ((67108864 & i) != 0) {
            colorLightTokens.getClass();
            j54 = ColorLightTokens.Outline;
        } else {
            j54 = j23;
        }
        if ((134217728 & i) != 0) {
            colorLightTokens.getClass();
            j55 = ColorLightTokens.OutlineVariant;
        } else {
            j55 = j24;
        }
        if ((268435456 & i) != 0) {
            colorLightTokens.getClass();
            j56 = ColorLightTokens.Scrim;
        } else {
            j56 = j25;
        }
        if ((536870912 & i) != 0) {
            colorLightTokens.getClass();
            j57 = ColorLightTokens.SurfaceBright;
        } else {
            j57 = j26;
        }
        if ((1073741824 & i) != 0) {
            colorLightTokens.getClass();
            j58 = ColorLightTokens.SurfaceContainer;
        } else {
            j58 = j27;
        }
        if ((i & Integer.MIN_VALUE) != 0) {
            colorLightTokens.getClass();
            j59 = ColorLightTokens.SurfaceContainerHigh;
        } else {
            j59 = j28;
        }
        if ((i2 & 1) != 0) {
            colorLightTokens.getClass();
            j60 = ColorLightTokens.SurfaceContainerHighest;
        } else {
            j60 = j29;
        }
        if ((i2 & 2) != 0) {
            colorLightTokens.getClass();
            j61 = ColorLightTokens.SurfaceContainerLow;
        } else {
            j61 = j30;
        }
        if ((i2 & 4) != 0) {
            colorLightTokens.getClass();
            j62 = ColorLightTokens.SurfaceContainerLowest;
        } else {
            j62 = j31;
        }
        if ((i2 & 8) != 0) {
            colorLightTokens.getClass();
            j63 = ColorLightTokens.SurfaceDim;
        } else {
            j63 = j32;
        }
        return new ColorScheme(j33, j34, j35, j36, j37, j38, j39, j40, j41, j42, j43, j44, j45, j46, j47, j48, j49, j50, j51, j64, j52, j53, j65, j66, j67, j68, j54, j55, j56, j57, j63, j58, j59, j60, j61, j62, null);
    }

    /* renamed from: surfaceColorAtElevation-3ABfNKs, reason: not valid java name */
    public static final long m260surfaceColorAtElevation3ABfNKs(ColorScheme colorScheme, float f) {
        long Color;
        boolean m836equalsimpl0 = Dp.m836equalsimpl0(f, 0);
        long j = colorScheme.surface;
        if (m836equalsimpl0) {
            return j;
        }
        Color = ColorKt.Color(Color.m461getRedimpl(r3), Color.m460getGreenimpl(r3), Color.m458getBlueimpl(r3), ((((float) Math.log(f + 1)) * 4.5f) + 2.0f) / 100.0f, Color.m459getColorSpaceimpl(colorScheme.surfaceTint));
        return ColorKt.m464compositeOverOWjLjI(Color, j);
    }
}
