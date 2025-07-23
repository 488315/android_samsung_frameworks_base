package com.samsung.sesl.compose.component.tokens;

import android.graphics.drawable.Drawable;
import androidx.compose.ui.graphics.Color;
import com.samsung.sesl.compose.component.tokens.SeslDrawableTokens;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslPopupTokens {
    public static final Companion Companion;
    public static final SeslPopupTokens darkPopupTokens;
    public static final SeslPopupTokens lightPopupTokens;
    public final long backgroundColor;
    public final Drawable menuBackground;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[SeslPopupColorSchemeKeyTokens.values().length];
            try {
                iArr[SeslPopupColorSchemeKeyTokens.BackgroundColor.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[SeslPopupDrawableSchemeKeyTokens.values().length];
            try {
                iArr2[SeslPopupDrawableSchemeKeyTokens.MenuBackground.ordinal()] = 1;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        DefaultConstructorMarker defaultConstructorMarker = null;
        Companion = new Companion(defaultConstructorMarker);
        SeslPaletteTokens seslPaletteTokens = SeslPaletteTokens.INSTANCE;
        seslPaletteTokens.getClass();
        long j = SeslPaletteTokens.GRAYSCALE_L1;
        SeslDrawableTokens.Companion companion = SeslDrawableTokens.Companion;
        companion.getClass();
        Drawable drawable = SeslDrawableTokens.emptyDrawable;
        lightPopupTokens = new SeslPopupTokens(j, drawable, defaultConstructorMarker);
        seslPaletteTokens.getClass();
        long j2 = SeslPaletteTokens.GRAYSCALE_D5;
        companion.getClass();
        darkPopupTokens = new SeslPopupTokens(j2, drawable, defaultConstructorMarker);
    }

    public /* synthetic */ SeslPopupTokens(long j, Drawable drawable, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, drawable);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslPopupTokens)) {
            return false;
        }
        SeslPopupTokens seslPopupTokens = (SeslPopupTokens) obj;
        long j = seslPopupTokens.backgroundColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.backgroundColor, j) && Intrinsics.areEqual(this.menuBackground, seslPopupTokens.menuBackground);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return this.menuBackground.hashCode() + (Long.hashCode(this.backgroundColor) * 31);
    }

    public final String toString() {
        return "SeslPopupTokens(backgroundColor=" + Color.m462toStringimpl(this.backgroundColor) + ", menuBackground=" + this.menuBackground + ")";
    }

    private SeslPopupTokens(long j, Drawable drawable) {
        this.backgroundColor = j;
        this.menuBackground = drawable;
    }
}
