package com.samsung.sesl.compose.component.tokens;

import androidx.compose.ui.graphics.Color;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslSpinnerTokens {
    public static final Companion Companion = new Companion(null);
    public static final SeslSpinnerTokens darkSpinnerTokens;
    public static final SeslSpinnerTokens lightSpinnerTokens;
    public final long iconColorDefault;
    public final long itemTextColorNormal;

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
            int[] iArr = new int[SeslSpinnerColorSchemeKeyTokens.values().length];
            try {
                iArr[SeslSpinnerColorSchemeKeyTokens.ItemTextColorNormal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SeslSpinnerColorSchemeKeyTokens.IconColorDefault.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        SeslPaletteTokens seslPaletteTokens = SeslPaletteTokens.INSTANCE;
        seslPaletteTokens.getClass();
        long j = SeslPaletteTokens.GRAY_TEXT_L1;
        seslPaletteTokens.getClass();
        lightSpinnerTokens = new SeslSpinnerTokens(j, SeslPaletteTokens.GRAY_TEXT_L2, null);
        Color.Companion.getClass();
        long j2 = Color.White;
        seslPaletteTokens.getClass();
        darkSpinnerTokens = new SeslSpinnerTokens(j2, SeslPaletteTokens.GRAY_TEXT_D2, null);
    }

    public /* synthetic */ SeslSpinnerTokens(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslSpinnerTokens)) {
            return false;
        }
        SeslSpinnerTokens seslSpinnerTokens = (SeslSpinnerTokens) obj;
        long j = seslSpinnerTokens.itemTextColorNormal;
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.itemTextColorNormal, j) && ULong.m3427equalsimpl0(this.iconColorDefault, seslSpinnerTokens.iconColorDefault);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.iconColorDefault) + (Long.hashCode(this.itemTextColorNormal) * 31);
    }

    public final String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m("SeslSpinnerTokens(itemTextColorNormal=", Color.m462toStringimpl(this.itemTextColorNormal), ", iconColorDefault=", Color.m462toStringimpl(this.iconColorDefault), ")");
    }

    private SeslSpinnerTokens(long j, long j2) {
        this.itemTextColorNormal = j;
        this.iconColorDefault = j2;
    }
}
