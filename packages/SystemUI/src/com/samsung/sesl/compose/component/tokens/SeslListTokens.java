package com.samsung.sesl.compose.component.tokens;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslListTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslListTokens darkListTokens;
    public static final SeslListTokens lightListTokens;
    public final long scrollbarThumbActivateColor;
    public final long scrollbarThumbInactiveColor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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
        SeslPaletteTokens.INSTANCE.getClass();
        long j = SeslPaletteTokens.Primary_Blue;
        lightListTokens = new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.6f, Color.m461getColorSpaceimpl(j)), SeslPaletteTokens.GRAYSCALE_L7, null);
        darkListTokens = new SeslListTokens(ColorKt.Color(Color.m463getRedimpl(j), Color.m462getGreenimpl(j), Color.m460getBlueimpl(j), 0.6f, Color.m461getColorSpaceimpl(j)), SeslPaletteTokens.GRAYSCALE_D7, null);
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
        return ULong.m3446equalsimpl0(this.scrollbarThumbActivateColor, j) && ULong.m3446equalsimpl0(this.scrollbarThumbInactiveColor, seslListTokens.scrollbarThumbInactiveColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.scrollbarThumbInactiveColor) + (Long.hashCode(this.scrollbarThumbActivateColor) * 31);
    }

    public final String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m("SeslListTokens(scrollbarThumbActivateColor=", Color.m464toStringimpl(this.scrollbarThumbActivateColor), ", scrollbarThumbInactiveColor=", Color.m464toStringimpl(this.scrollbarThumbInactiveColor), ")");
    }

    private SeslListTokens(long j, long j2) {
        this.scrollbarThumbActivateColor = j;
        this.scrollbarThumbInactiveColor = j2;
    }
}
