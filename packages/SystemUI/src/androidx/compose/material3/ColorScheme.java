package androidx.compose.material3;

import androidx.compose.foundation.OverscrollConfiguration$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ColorScheme {
    public final long background;
    public ButtonColors defaultButtonColorsCached;
    public CardColors defaultCardColorsCached;
    public IconButtonColors defaultFilledIconButtonColorsCached;
    public IconButtonColors defaultIconButtonColorsCached;
    public ButtonColors defaultOutlinedButtonColorsCached;
    public TextFieldColors defaultOutlinedTextFieldColorsCached;
    public SliderColors defaultSliderColorsCached;
    public ButtonColors defaultTextButtonColorsCached;
    public TextFieldColors defaultTextFieldColorsCached;
    public TopAppBarColors defaultTopAppBarColorsCached;
    public final long error;
    public final long errorContainer;
    public final long inverseOnSurface;
    public final long inversePrimary;
    public final long inverseSurface;
    public final long onBackground;
    public final long onError;
    public final long onErrorContainer;
    public final long onPrimary;
    public final long onPrimaryContainer;
    public final long onSecondary;
    public final long onSecondaryContainer;
    public final long onSurface;
    public final long onSurfaceVariant;
    public final long onTertiary;
    public final long onTertiaryContainer;
    public final long outline;
    public final long outlineVariant;
    public final long primary;
    public final long primaryContainer;
    public final long scrim;
    public final long secondary;
    public final long secondaryContainer;
    public final long surface;
    public final long surfaceBright;
    public final long surfaceContainer;
    public final long surfaceContainerHigh;
    public final long surfaceContainerHighest;
    public final long surfaceContainerLow;
    public final long surfaceContainerLowest;
    public final long surfaceDim;
    public final long surfaceTint;
    public final long surfaceVariant;
    public final long tertiary;
    public final long tertiaryContainer;

    public /* synthetic */ ColorScheme(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, long j30, long j31, long j32, long j33, long j34, long j35, long j36, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6, j7, j8, j9, j10, j11, j12, j13, j14, j15, j16, j17, j18, j19, j20, j21, j22, j23, j24, j25, j26, j27, j28, j29, j30, j31, j32, j33, j34, j35, j36);
    }

    /* renamed from: copy-C-Xl9yA$default, reason: not valid java name */
    public static ColorScheme m257copyCXl9yA$default(ColorScheme colorScheme, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, int i) {
        long j12 = (i & 1) != 0 ? colorScheme.primary : j;
        long j13 = colorScheme.onPrimary;
        long j14 = colorScheme.primaryContainer;
        long j15 = colorScheme.onPrimaryContainer;
        long j16 = (i & 16) != 0 ? colorScheme.inversePrimary : j2;
        long j17 = (i & 32) != 0 ? colorScheme.secondary : j3;
        long j18 = colorScheme.onSecondary;
        long j19 = colorScheme.secondaryContainer;
        long j20 = colorScheme.onSecondaryContainer;
        long j21 = colorScheme.tertiary;
        long j22 = colorScheme.onTertiary;
        long j23 = colorScheme.tertiaryContainer;
        long j24 = colorScheme.onTertiaryContainer;
        long j25 = (i & 8192) != 0 ? colorScheme.background : j4;
        long j26 = colorScheme.onBackground;
        long j27 = (i & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 ? colorScheme.surface : j5;
        long j28 = colorScheme.onSurface;
        long j29 = colorScheme.surfaceVariant;
        long j30 = colorScheme.onSurfaceVariant;
        long j31 = colorScheme.surfaceTint;
        long j32 = (i & 1048576) != 0 ? colorScheme.inverseSurface : j6;
        long j33 = (i & 2097152) != 0 ? colorScheme.inverseOnSurface : j7;
        long j34 = (i & 4194304) != 0 ? colorScheme.error : j8;
        long j35 = (i & 8388608) != 0 ? colorScheme.onError : j9;
        long j36 = (i & 16777216) != 0 ? colorScheme.errorContainer : j10;
        long j37 = (i & 33554432) != 0 ? colorScheme.onErrorContainer : j11;
        long j38 = colorScheme.outline;
        long j39 = colorScheme.outlineVariant;
        long j40 = colorScheme.scrim;
        long j41 = colorScheme.surfaceBright;
        long j42 = colorScheme.surfaceDim;
        long j43 = colorScheme.surfaceContainer;
        long j44 = colorScheme.surfaceContainerHigh;
        long j45 = colorScheme.surfaceContainerHighest;
        long j46 = colorScheme.surfaceContainerLow;
        long j47 = colorScheme.surfaceContainerLowest;
        colorScheme.getClass();
        return new ColorScheme(j12, j13, j14, j15, j16, j17, j18, j19, j20, j21, j22, j23, j24, j25, j26, j27, j28, j29, j30, j31, j32, j33, j34, j35, j36, j37, j38, j39, j40, j41, j42, j43, j44, j45, j46, j47, null);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ColorScheme(primary=");
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.primary, "onPrimary=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.onPrimary, "primaryContainer=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.primaryContainer, "onPrimaryContainer=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.onPrimaryContainer, "inversePrimary=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.inversePrimary, "secondary=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.secondary, "onSecondary=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.onSecondary, "secondaryContainer=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.secondaryContainer, "onSecondaryContainer=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.onSecondaryContainer, "tertiary=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.tertiary, "onTertiary=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.onTertiary, "tertiaryContainer=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.tertiaryContainer, "onTertiaryContainer=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.onTertiaryContainer, "background=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.background, "onBackground=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.onBackground, "surface=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.surface, "onSurface=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.onSurface, "surfaceVariant=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.surfaceVariant, "onSurfaceVariant=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.onSurfaceVariant, "surfaceTint=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.surfaceTint, "inverseSurface=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.inverseSurface, "inverseOnSurface=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.inverseOnSurface, "error=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.error, "onError=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.onError, "errorContainer=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.errorContainer, "onErrorContainer=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.onErrorContainer, "outline=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.outline, "outlineVariant=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.outlineVariant, "scrim=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.scrim, "surfaceBright=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.surfaceBright, "surfaceDim=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.surfaceDim, "surfaceContainer=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.surfaceContainer, "surfaceContainerHigh=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.surfaceContainerHigh, "surfaceContainerHighest=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.surfaceContainerHighest, "surfaceContainerLow=", sb);
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.surfaceContainerLow, "surfaceContainerLowest=", sb);
        sb.append((Object) Color.m464toStringimpl(this.surfaceContainerLowest));
        sb.append(')');
        return sb.toString();
    }

    public /* synthetic */ ColorScheme(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, j4, j5, j6, j7, j8, j9, j10, j11, j12, j13, j14, j15, j16, j17, j18, j19, j20, j21, j22, j23, j24, j25, j26, j27, j28, j29);
    }

    private ColorScheme(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29, long j30, long j31, long j32, long j33, long j34, long j35, long j36) {
        this.primary = j;
        this.onPrimary = j2;
        this.primaryContainer = j3;
        this.onPrimaryContainer = j4;
        this.inversePrimary = j5;
        this.secondary = j6;
        this.onSecondary = j7;
        this.secondaryContainer = j8;
        this.onSecondaryContainer = j9;
        this.tertiary = j10;
        this.onTertiary = j11;
        this.tertiaryContainer = j12;
        this.onTertiaryContainer = j13;
        this.background = j14;
        this.onBackground = j15;
        this.surface = j16;
        this.onSurface = j17;
        this.surfaceVariant = j18;
        this.onSurfaceVariant = j19;
        this.surfaceTint = j20;
        this.inverseSurface = j21;
        this.inverseOnSurface = j22;
        this.error = j23;
        this.onError = j24;
        this.errorContainer = j25;
        this.onErrorContainer = j26;
        this.outline = j27;
        this.outlineVariant = j28;
        this.scrim = j29;
        this.surfaceBright = j30;
        this.surfaceDim = j31;
        this.surfaceContainer = j32;
        this.surfaceContainerHigh = j33;
        this.surfaceContainerHighest = j34;
        this.surfaceContainerLow = j35;
        this.surfaceContainerLowest = j36;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private ColorScheme(long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, long j12, long j13, long j14, long j15, long j16, long j17, long j18, long j19, long j20, long j21, long j22, long j23, long j24, long j25, long j26, long j27, long j28, long j29) {
        Color.Companion companion = Color.Companion;
        companion.getClass();
        long j30 = Color.Unspecified;
        companion.getClass();
        companion.getClass();
        companion.getClass();
        companion.getClass();
        companion.getClass();
        companion.getClass();
        this(j, j2, j3, j4, j5, j6, j7, j8, j9, j10, j11, j12, j13, j14, j15, j16, j17, j18, j19, j20, j21, j22, j23, j24, j25, j26, j27, j28, j29, j30, j30, j30, j30, j30, j30, j30, null);
    }
}
