package com.samsung.sesl.compose.component.tokens;

import androidx.compose.ui.graphics.Color;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslDividerTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslDividerTokens darkDividerTokens;
    public static final SeslDividerTokens lightDividerTokens;
    public final long horizontalDividerColor;
    public final long verticalDividerColor;

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

        static {
            int[] iArr = new int[SeslDividerColorSchemeKeyTokens.values().length];
            try {
                iArr[SeslDividerColorSchemeKeyTokens.HorizontalDividerColor.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SeslDividerColorSchemeKeyTokens.VerticalDividerColor.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        SeslPaletteTokens seslPaletteTokens = SeslPaletteTokens.INSTANCE;
        seslPaletteTokens.getClass();
        long j = SeslPaletteTokens.GRAYSCALE_L6;
        seslPaletteTokens.getClass();
        lightDividerTokens = new SeslDividerTokens(j, SeslPaletteTokens.GRAY_TEXT_D3, null);
        seslPaletteTokens.getClass();
        long j2 = SeslPaletteTokens.GRAYSCALE_D5;
        seslPaletteTokens.getClass();
        darkDividerTokens = new SeslDividerTokens(j2, SeslPaletteTokens.GRAY_TEXT_L4, null);
    }

    public /* synthetic */ SeslDividerTokens(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslDividerTokens)) {
            return false;
        }
        SeslDividerTokens seslDividerTokens = (SeslDividerTokens) obj;
        long j = seslDividerTokens.horizontalDividerColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.horizontalDividerColor, j) && ULong.m3427equalsimpl0(this.verticalDividerColor, seslDividerTokens.verticalDividerColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.verticalDividerColor) + (Long.hashCode(this.horizontalDividerColor) * 31);
    }

    public final String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m("SeslDividerTokens(horizontalDividerColor=", Color.m462toStringimpl(this.horizontalDividerColor), ", verticalDividerColor=", Color.m462toStringimpl(this.verticalDividerColor), ")");
    }

    private SeslDividerTokens(long j, long j2) {
        this.horizontalDividerColor = j;
        this.verticalDividerColor = j2;
    }
}
