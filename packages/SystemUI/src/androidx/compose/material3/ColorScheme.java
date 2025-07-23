package androidx.compose.material3;

import androidx.compose.foundation.OverscrollConfiguration$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public static ColorScheme m256copyCXl9yA$default(ColorScheme colorScheme, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10, long j11, int i) {
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
        sb.append((Object) Color.m462toStringimpl(this.surfaceContainerLowest));
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private ColorScheme(long r76, long r78, long r80, long r82, long r84, long r86, long r88, long r90, long r92, long r94, long r96, long r98, long r100, long r102, long r104, long r106, long r108, long r110, long r112, long r114, long r116, long r118, long r120, long r122, long r124, long r126, long r128, long r130, long r132) {
        /*
            r75 = this;
            androidx.compose.ui.graphics.Color$Companion r0 = androidx.compose.ui.graphics.Color.Companion
            r0.getClass()
            long r60 = androidx.compose.ui.graphics.Color.Unspecified
            r0.getClass()
            r0.getClass()
            r0.getClass()
            r0.getClass()
            r0.getClass()
            r0.getClass()
            r74 = 0
            r62 = r60
            r64 = r60
            r66 = r60
            r68 = r60
            r70 = r60
            r72 = r60
            r1 = r75
            r2 = r76
            r4 = r78
            r6 = r80
            r8 = r82
            r10 = r84
            r12 = r86
            r14 = r88
            r16 = r90
            r18 = r92
            r20 = r94
            r22 = r96
            r24 = r98
            r26 = r100
            r28 = r102
            r30 = r104
            r32 = r106
            r34 = r108
            r36 = r110
            r38 = r112
            r40 = r114
            r42 = r116
            r44 = r118
            r46 = r120
            r48 = r122
            r50 = r124
            r52 = r126
            r54 = r128
            r56 = r130
            r58 = r132
            r1.<init>(r2, r4, r6, r8, r10, r12, r14, r16, r18, r20, r22, r24, r26, r28, r30, r32, r34, r36, r38, r40, r42, r44, r46, r48, r50, r52, r54, r56, r58, r60, r62, r64, r66, r68, r70, r72, r74)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.ColorScheme.<init>(long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long, long):void");
    }
}
