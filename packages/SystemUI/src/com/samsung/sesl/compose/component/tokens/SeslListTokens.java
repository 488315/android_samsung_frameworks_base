package com.samsung.sesl.compose.component.tokens;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslListTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslListTokens darkListTokens;
    public static final SeslListTokens lightListTokens;
    public final long scrollbarThumbActivateColor;
    public final long scrollbarThumbInactiveColor;

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
            int[] iArr = new int[SeslListColorSchemeKeyTokens.values().length];
            try {
                iArr[SeslListColorSchemeKeyTokens.ScrollbarThumbActivate.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SeslListColorSchemeKeyTokens.ScrollbarThumbInActivate.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        long Color;
        long Color2;
        SeslPaletteTokens.INSTANCE.getClass();
        long j = SeslPaletteTokens.Primary_Blue;
        Color = ColorKt.Color(Color.m461getRedimpl(j), Color.m460getGreenimpl(j), Color.m458getBlueimpl(j), 0.6f, Color.m459getColorSpaceimpl(j));
        lightListTokens = new SeslListTokens(Color, SeslPaletteTokens.GRAYSCALE_L7, null);
        Color2 = ColorKt.Color(Color.m461getRedimpl(j), Color.m460getGreenimpl(j), Color.m458getBlueimpl(j), 0.6f, Color.m459getColorSpaceimpl(j));
        darkListTokens = new SeslListTokens(Color2, SeslPaletteTokens.GRAYSCALE_D7, null);
    }

    public /* synthetic */ SeslListTokens(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslListTokens)) {
            return false;
        }
        SeslListTokens seslListTokens = (SeslListTokens) obj;
        long j = seslListTokens.scrollbarThumbActivateColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.scrollbarThumbActivateColor, j) && ULong.m3427equalsimpl0(this.scrollbarThumbInactiveColor, seslListTokens.scrollbarThumbInactiveColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.scrollbarThumbInactiveColor) + (Long.hashCode(this.scrollbarThumbActivateColor) * 31);
    }

    public final String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m("SeslListTokens(scrollbarThumbActivateColor=", Color.m462toStringimpl(this.scrollbarThumbActivateColor), ", scrollbarThumbInactiveColor=", Color.m462toStringimpl(this.scrollbarThumbInactiveColor), ")");
    }

    private SeslListTokens(long j, long j2) {
        this.scrollbarThumbActivateColor = j;
        this.scrollbarThumbInactiveColor = j2;
    }
}
