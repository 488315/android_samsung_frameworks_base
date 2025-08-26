package com.samsung.sesl.compose.component.tokens;

import androidx.compose.ui.graphics.Color;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslButtonTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslButtonTokens darkButtonTokens;
    public static final SeslButtonTokens lightButtonTokens;
    public final long containerColor;
    public final long contentColor;

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
            int[] iArr = new int[SeslButtonColorSchemeKeyTokens.values().length];
            try {
                iArr[SeslButtonColorSchemeKeyTokens.ContainerColor.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SeslButtonColorSchemeKeyTokens.ContentColor.ordinal()] = 2;
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
        lightButtonTokens = new SeslButtonTokens(j, SeslPaletteTokens.GRAY_TEXT_L1, null);
        seslPaletteTokens.getClass();
        long j2 = SeslPaletteTokens.GRAYSCALE_D4;
        seslPaletteTokens.getClass();
        darkButtonTokens = new SeslButtonTokens(j2, SeslPaletteTokens.GRAY_TEXT_D1, null);
    }

    public /* synthetic */ SeslButtonTokens(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslButtonTokens)) {
            return false;
        }
        SeslButtonTokens seslButtonTokens = (SeslButtonTokens) obj;
        long j = seslButtonTokens.containerColor;
        Color.Companion companion = Color.Companion;
        return ULong.m3447equalsimpl0(this.containerColor, j) && ULong.m3447equalsimpl0(this.contentColor, seslButtonTokens.contentColor);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.contentColor) + (Long.hashCode(this.containerColor) * 31);
    }

    public final String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m("SeslButtonTokens(containerColor=", Color.m464toStringimpl(this.containerColor), ", contentColor=", Color.m464toStringimpl(this.contentColor), ")");
    }

    private SeslButtonTokens(long j, long j2) {
        this.containerColor = j;
        this.contentColor = j2;
    }
}
