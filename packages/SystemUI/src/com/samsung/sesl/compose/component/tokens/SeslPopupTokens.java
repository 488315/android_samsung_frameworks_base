package com.samsung.sesl.compose.component.tokens;

import android.graphics.drawable.Drawable;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import com.samsung.sesl.compose.component.tokens.SeslDrawableTokens;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SeslPopupTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslPopupTokens darkPopupTokens;
    public static final SeslPopupTokens lightPopupTokens;
    public final long backgroundColor;
    public final long borderColor;
    public final Drawable menuBackground;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[SeslPopupColorSchemeKeyTokens.values().length];
            try {
                iArr[SeslPopupColorSchemeKeyTokens.BackgroundColor.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SeslPopupColorSchemeKeyTokens.BorderColor.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[SeslPopupDrawableSchemeKeyTokens.values().length];
            try {
                iArr2[SeslPopupDrawableSchemeKeyTokens.MenuBackground.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        SeslPaletteTokens seslPaletteTokens = SeslPaletteTokens.INSTANCE;
        seslPaletteTokens.getClass();
        long j = SeslPaletteTokens.GRAYSCALE_L1;
        Color.Companion companion = Color.Companion;
        companion.getClass();
        long j2 = Color.Transparent;
        SeslDrawableTokens.Companion companion2 = SeslDrawableTokens.Companion;
        companion2.getClass();
        Drawable drawable = SeslDrawableTokens.emptyDrawable;
        lightPopupTokens = new SeslPopupTokens(j, j2, drawable, null);
        seslPaletteTokens.getClass();
        long j3 = SeslPaletteTokens.GRAYSCALE_D5;
        companion.getClass();
        companion2.getClass();
        darkPopupTokens = new SeslPopupTokens(j3, j2, drawable, null);
    }

    public /* synthetic */ SeslPopupTokens(long j, long j2, Drawable drawable, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, drawable);
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
        return ULong.m3447equalsimpl0(this.backgroundColor, j) && ULong.m3447equalsimpl0(this.borderColor, seslPopupTokens.borderColor) && Intrinsics.areEqual(this.menuBackground, seslPopupTokens.menuBackground);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return this.menuBackground.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.backgroundColor) * 31, 31, this.borderColor);
    }

    public final String toString() {
        String strM464toStringimpl = Color.m464toStringimpl(this.backgroundColor);
        String strM464toStringimpl2 = Color.m464toStringimpl(this.borderColor);
        Drawable drawable = this.menuBackground;
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("SeslPopupTokens(backgroundColor=", strM464toStringimpl, ", borderColor=", strM464toStringimpl2, ", menuBackground=");
        sbM.append(drawable);
        sbM.append(")");
        return sbM.toString();
    }

    private SeslPopupTokens(long j, long j2, Drawable drawable) {
        this.backgroundColor = j;
        this.borderColor = j2;
        this.menuBackground = drawable;
    }
}
